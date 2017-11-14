package org.badhan.r64.service;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Subscribe;

import org.badhan.r64.activity.auth.PhoneAuth;
import org.badhan.r64.core.Auth;
import org.badhan.r64.core.MyApplication;
import org.badhan.r64.entity.Cadre;
import org.badhan.r64.entity.User;
import org.badhan.r64.service.auth.LoginResponse;
import org.badhan.r64.service.auth.LoginWithEmail;
import org.badhan.r64.service.auth.LoginWithExternalProvider;
import org.badhan.r64.service.auth.LoginWithLocalToken;
import org.badhan.r64.service.auth.LoginWithUsername;
import org.badhan.r64.service.auth.Register;
import org.badhan.r64.service.auth.RegisterWithExternalProvider;
import org.badhan.r64.service.profile.CadreDetailsUpdatedEvent;
import org.badhan.r64.service.profile.ChangeAvatar;
import org.badhan.r64.service.profile.ChangePassword;
import org.badhan.r64.service.profile.UpdateProfile;
import org.badhan.r64.service.profile.UserDetailsUpdatedEvent;

public class InMemoryAccountService extends BaseInMemoryService {

    public InMemoryAccountService(MyApplication application){
        super(application);
    }

    @Subscribe
    public void updateProfile(final UpdateProfile.Request request){
        final UpdateProfile.Response response = new UpdateProfile.Response();

        String telephone = application.getAuth().getUser().getTelephone();
        if (telephone.isEmpty()){
            response.setOperationError("invalid user phone");
            bus.post(response);
            return;
        }

        final DatabaseReference cadreRef = application.getFirebaseDatabase()
                .getReference("cadres/" + telephone);

//        cadreRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//               response.cadre = dataSnapshot.getValue(Cadre.class);
//
//               if (response.cadre != null){
//                   Log.e("profile update",response.cadre.getName());
//                   response.cadre.setCadreId(request.getCadreId());
//                   response.cadre.setName(request.getDisplayName());
//                   response.cadre.setEmail(request.getEmail());
//                   response.cadre.setBatch(request.getEmail());
//                   response.cadre.setHomeDistrict(request.getHomeDistrict());
//                   response.cadre.setPostingAddress(request.getPostingAddress());
//                   response.cadre.setBloodGroup(request.getBloodGroup());
//                   response.cadre.setUniversity(request.getUniversity());
//                   response.cadre.setSession(request.getSession());
//
//                   cadreRef.setValue(response.cadre);
//                   bus.post(response);
//                   bus.post(new CadreDetailsUpdatedEvent(response.cadre));
//               }else {
//                   response.setOperationError("no cadres found on that telephone");
//                   bus.post(response);
//               }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        cadreRef.child("cadreId").setValue(request.getCadreId());
        cadreRef.child("name").setValue(request.getDisplayName());
        cadreRef.child("email").setValue(request.getEmail());
        cadreRef.child("batch").setValue(request.getBatch());
        cadreRef.child("homeDistrict").setValue(request.getHomeDistrict());
        cadreRef.child("postingAddress").setValue(request.getPostingAddress());
        cadreRef.child("bloodGroup").setValue(request.getBloodGroup());
        cadreRef.child("university").setValue(request.getUniversity());
        cadreRef.child("session").setValue(request.getSession());

        User user = application.getAuth().getUser();
        user.setDisplayName(request.getDisplayName());
        bus.post(new UserDetailsUpdatedEvent(user));
        bus.post(response);
//
//        bus.post(response);

//        invokeDelayed(new Runnable() {
//            @Override
//            public void run() {
//                User user = application.getAuth().getUser();
//                user.setDisplayName(request.displayName);
//                user.setEmail(request.email);
//
//                bus.post(response);
//                bus.post(new UserDetailsUpdatedEvent(user));
//            }
//        }, 1000,1500);
    }


    @Subscribe
    public void changeAvatar(final ChangeAvatar.Request request){
        final ChangeAvatar.Response response = new ChangeAvatar.Response();

        final User user = application.getAuth().getUser();
        final String avatarUri = request.newAvatarUri;
        Log.e("get avatar uri",avatarUri);
        //TODO avatarUri is empty
        DatabaseReference userRef = application.getFirebaseDatabase()
                .getReference("cadres/"+ user.getTelephone());

        userRef.child("avatarUrl").setValue(avatarUri)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                user.setAvatarUrl(avatarUri);
                bus.post(new UserDetailsUpdatedEvent(user));
                bus.post(response);
            }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    response.setOperationError("avatar url did not persist");
                    bus.post(response);
                }
            });

    }

    @Subscribe
    public void changePassword(ChangePassword.Request request){
        ChangePassword.Response response = new ChangePassword.Response();

        if (!request.newPassword.equals(request.confirmNewPassword))
            response.setPropertyError("confirmNewPassword","confirm password did not matched");
        if (request.newPassword.length() < 4)
            response.setPropertyError("newPassword","password must be greater than 3 characters");

        postDelayed(response);
    }

    @Subscribe
    public void loginWithEmail(final LoginWithEmail.Request request){
        final LoginWithEmail.Response response = new LoginWithEmail.Response();
        final FirebaseAuth mAuth = application.getFirebaseAuth();

        mAuth.signInWithEmailAndPassword(request.email, request.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("login", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginUser(response);

                        } else {
                            // If sign in fails, display a message to the user.
                            response.setPropertyError("email","email or password is wrong");
                            response.setPropertyError("password","email or password is wrong");
                            Log.w("login", "signInWithEmail:failure", task.getException());
                            response.setOperationError("login failed");
                        }

                        bus.post(response);
                    }
                });
    }

    //token is actually the telephone
    @Subscribe
    public void loginWithLocalToken(LoginWithLocalToken.Request request){
        final LoginWithLocalToken.Response response = new LoginWithLocalToken.Response();
        String telephone = request.authToken;

        //Log.e("locatl token", telephone);
        DatabaseReference cadreRef = application.getFirebaseDatabase()
                .getReference("cadres/"+telephone);
        cadreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User attemptedUser = dataSnapshot.getValue(User.class);
                if (attemptedUser == null){
                    response.setOperationError("user not found");
                }else{
                    User user = application.getAuth().getUser();
                    user.setLoggedIn(true);
                    user.setId(attemptedUser.getId());
                    user.setRollNo(attemptedUser.getRollNo());
                    user.setCadreId(attemptedUser.getCadreId());
                    user.setTelephone(attemptedUser.getTelephone());
                    user.setEmail(attemptedUser.getEmail());
                    user.setCadreType(attemptedUser.getCadreType());
                    user.setBatch(attemptedUser.getBatch());
                    user.setName(attemptedUser.getName());
                    user.setHomeDistrict(attemptedUser.getHomeDistrict());
                    user.setPostingAddress(attemptedUser.getPostingAddress());
                    user.setBloodGroup(attemptedUser.getBloodGroup());
                    user.setUniversity(attemptedUser.getUniversity());
                    user.setSession(attemptedUser.getSession());
                    user.setGroup(attemptedUser.getGroup());
                    user.setAvatarUrl(attemptedUser.getAvatarUri());

                    bus.post(new UserDetailsUpdatedEvent(user));

                }

                bus.post(response);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Subscribe
    public void register(Register.Request request){
        final Register.Response response = new Register.Response();
        final FirebaseAuth mAuth = application.getFirebaseAuth();
        String email = request.email;
        String password = request.password;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("register", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginUser(response);
                        } else {
                            response.setOperationError("login failed");
                            // If sign in fails, display a message to the user.
                            Log.e("register", "createUserWithEmail:failure",
                                    task.getException());
                        }

                        bus.post(response);
                    }
                });
    }



    private void loginUser(LoginResponse response){
        Auth auth = application.getAuth();
        User user = auth.getUser();

        user.setDisplayName("Borhan chowdhury");
        user.setEmail("borhan.chittagong@gmail.com");
        user.setAvatarUrl("https://en.gravatar.com/avatar/1");
        user.setLoggedIn(true);
        user.setId(123);

        auth.setAuthToken("fakeauthtoken");

        response.id = user.getId();
        response.displayName = user.getDisplayName();
        response.email = user.getEmail();

        response.authToken = auth.getAuthToken();

        bus.post(new UserDetailsUpdatedEvent(user));
    }


}
