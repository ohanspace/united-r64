package org.badhan.r64.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.otto.Subscribe;

import org.badhan.r64.core.Auth;
import org.badhan.r64.core.MyApplication;
import org.badhan.r64.entity.User;
import org.badhan.r64.service.auth.LoginResponse;
import org.badhan.r64.service.auth.LoginWithEmail;
import org.badhan.r64.service.auth.LoginWithExternalProvider;
import org.badhan.r64.service.auth.LoginWithLocalToken;
import org.badhan.r64.service.auth.LoginWithUsername;
import org.badhan.r64.service.auth.Register;
import org.badhan.r64.service.auth.RegisterWithExternalProvider;
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

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                User user = application.getAuth().getUser();
                user.setDisplayName(request.displayName);
                user.setEmail(request.email);

                bus.post(response);
                bus.post(new UserDetailsUpdatedEvent(user));
            }
        }, 1000,1500);
    }


    @Subscribe
    public void changeAvatar(final ChangeAvatar.Request request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                User user = application.getAuth().getUser();
                user.setAvatarUrl(request.newAvatarUri.toString());

                bus.post(new ChangeAvatar.Response());
                bus.post(new UserDetailsUpdatedEvent(user));
            }
        },3000,4000);
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

//        invokeDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (request.email.equals("borhan"))
//                    response.setPropertyError("email","email should not borhan");
//                loginUser(response);
//                bus.post(response);
//            }
//        }, 2000,3000);


    }

    @Subscribe
    public void loginWithLocalToken(LoginWithLocalToken.Request request){
        final LoginWithLocalToken.Response response = new LoginWithLocalToken.Response();

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                loginUser(response);
                bus.post(response);
            }
        }, 2000,3000);
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

//        invokeDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loginUser(response);
//                bus.post(response);
//            }
//        },2000,3000);
    }



    private void loginUser(LoginResponse response){
        Auth auth = application.getAuth();
        User user = auth.getUser();

        user.setDisplayName("Borhan chowdhury");
        user.setUsername("borhan");
        user.setEmail("borhan.chittagong@gmail.com");
        user.setAvatarUrl("https://en.gravatar.com/avatar/1");
        user.setLoggedIn(true);
        user.setId(123);

        auth.setAuthToken("fakeauthtoken");

        response.id = user.getId();
        response.displayName = user.getDisplayName();
        response.email = user.getEmail();
        response.username = user.getUsername();
        response.avatarUrl = user.getAvatarUrl();

        response.authToken = auth.getAuthToken();

        bus.post(new UserDetailsUpdatedEvent(user));
    }


}
