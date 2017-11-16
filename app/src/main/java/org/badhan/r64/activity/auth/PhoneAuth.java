package org.badhan.r64.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.badhan.r64.R;
import org.badhan.r64.activity.MainActivity;
import org.badhan.r64.activity.ProfileActivity;
import org.badhan.r64.core.Auth;
import org.badhan.r64.core.BaseActivity;
import org.badhan.r64.entity.Cadre;
import org.badhan.r64.entity.User;
import org.badhan.r64.service.profile.UserDetailsUpdatedEvent;

import java.util.concurrent.TimeUnit;

public class PhoneAuth extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "phone auth";

    private EditText telephoneField;
    private EditText codeField;
    private Button loginBtn;
    private Button guestLoginBtn;
    private Button resendBtn;
    private Button verifyCodeBtn;
    private View progressBarFrame;

    private FirebaseAuth firebaseAuth;
    private String phoneVerificationId;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationStateChangedCallbacks;
    private static String knownTelephone;
    private static User attemptedUser;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_phone_auth);

        firebaseAuth = FirebaseAuth.getInstance();

        telephoneField = findViewById(R.id.phone_auth_activity_telephoneField);
        codeField = findViewById(R.id.phone_auth_activity_codeField);
        loginBtn = findViewById(R.id.phone_auth_activity_loginBtn);
        resendBtn = findViewById(R.id.phone_auth_activity_resendBtn);
        verifyCodeBtn = findViewById(R.id.phone_auth_activity_verifyCodeBtn);
        progressBarFrame = findViewById(R.id.phone_auth_activity_progressBarFrame);
        guestLoginBtn = findViewById(R.id.phone_auth_activity_guestLoginBtn);

        loginBtn.setOnClickListener(this);
        guestLoginBtn.setOnClickListener(this);

        verifyCodeBtn.setVisibility(View.GONE);
        resendBtn.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.phone_auth_activity_loginBtn:
                showProgressBar();
                sendCode();
                break;
            case R.id.phone_auth_activity_verifyCodeBtn:
                showProgressBar();
                verifyCode();
                break;
            case R.id.phone_auth_activity_resendBtn:
                showProgressBar();
                resendCode();
                break;
            case R.id.phone_auth_activity_guestLoginBtn:
                guestLogin();
        }
    }

    private void guestLogin(){
        User user = application.getAuth().getUser();
        user.setDisplayName("Guest User");
        user.setTelephone(" ");
        user.setAvatarUrl(" ");
        user.setGuest(true);
        user.setLoggedIn(true);

        bus.post(new UserDetailsUpdatedEvent(user));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendCode(){
        final String telephoneString = telephoneField.getText().toString();

        if (telephoneString.isEmpty()){
            Toast.makeText(this,"invalid telephone",Toast.LENGTH_LONG).show();
        }
        DatabaseReference userRef = application.getFirebaseDatabase()
                .getReference("cadres/"+telephoneString);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attemptedUser = dataSnapshot.getValue(User.class);
                if (attemptedUser == null){
                    Toast.makeText(PhoneAuth.this,"unknown telephone no given",Toast.LENGTH_LONG).show();
                    hideProgressBar();
                }else{
                    Toast.makeText(PhoneAuth.this,"known telephone!!",Toast.LENGTH_LONG).show();
                    //try login
                    knownTelephone = telephoneString;
                    trySendCode();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public final void trySendCode(){
        setUpVerificationCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                knownTelephone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationStateChangedCallbacks);
    }

    public void resendCode() {
        String telephoneString = telephoneField.getText().toString();

        setUpVerificationCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                telephoneString,
                60,
                TimeUnit.SECONDS,
                this,
                verificationStateChangedCallbacks,
                resendingToken);
    }



    private void verifyCode(){
        String code = codeField.getText().toString();
        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }



    private void setUpVerificationCallbacks() {
        verificationStateChangedCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(String verificationId,
                                       PhoneAuthProvider.ForceResendingToken token) {
                    hideProgressBar();
                    phoneVerificationId = verificationId;
                    resendingToken = token;

                    codeField.setVisibility(View.VISIBLE);
                    verifyCodeBtn.setVisibility(View.VISIBLE);

                    telephoneField.setEnabled(false);
                    verifyCodeBtn.setEnabled(true);
                    resendBtn.setEnabled(true);
                    loginBtn.setEnabled(false);
                    loginBtn.setVisibility(View.GONE);
                    guestLoginBtn.setVisibility(View.GONE);
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {

                    signInWithPhoneAuthCredential(credential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                        Log.e(TAG, "Invalid credential: "
                                + e.getLocalizedMessage());
                        Toast.makeText(PhoneAuth.this,
                                "invalid credential",Toast.LENGTH_LONG ).show();
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        // SMS quota exceeded
                        Log.d(TAG, "SMS Quota exceeded.");
                    }
                }
            };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        onSuccessfulLogin(user);

                    } else {
                        if (task.getException() instanceof
                                FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(PhoneAuth.this,
                                    "invalid credential",Toast.LENGTH_LONG ).show();
                        }
                    }
                }
            });
    }


    private void onSuccessfulLogin(FirebaseUser firebaseUser){
        Auth auth = application.getAuth();
        User user = auth.getUser();

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

        Log.e("auth user",Integer.toString(user.getId()));

        //user.setAvatarUrl("https://en.gravatar.com/avatar/1");
        user.setLoggedIn(true);
        user.setGuest(false);
        auth.setAuthToken(attemptedUser.getTelephone());

        bus.post(new UserDetailsUpdatedEvent(user));
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Login succeed",Toast.LENGTH_SHORT).show();
        finish();
    }


    private void showProgressBar(){
        progressBarFrame.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        progressBarFrame.setVisibility(View.GONE);
    }
}
