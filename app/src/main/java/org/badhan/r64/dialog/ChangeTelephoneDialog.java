package org.badhan.r64.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import org.badhan.r64.activity.ProfileActivity;
import org.badhan.r64.activity.auth.PhoneAuth;
import org.badhan.r64.core.Auth;
import org.badhan.r64.core.BaseDialogFragment;
import org.badhan.r64.entity.Cadre;
import org.badhan.r64.entity.User;
import org.badhan.r64.service.profile.UserDetailsUpdatedEvent;

import java.util.concurrent.TimeUnit;

public class ChangeTelephoneDialog extends BaseDialogFragment implements View.OnClickListener {
    private final String TAG = "change telephone";
    private View dialogView;
    private EditText newTelephoneField;
    private EditText codeField;
    private Button verifyCodeBtn;
    private Button resendBtn;
    private ProgressBar progressBar;
    private Button dialogUpdateBtn;

    private FirebaseAuth firebaseAuth;
    private String phoneVerificationId;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationStateChangedCallbacks;

    @Override
    public Dialog onCreateDialog(Bundle savedState){
        firebaseAuth = FirebaseAuth.getInstance();

        dialogView = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_change_telephone, null, false);

        bindControls();

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setTitle("Change Telephone")
                .setPositiveButton("Update",null)
                .setNegativeButton("Cancel",null)
                .show();
        dialogUpdateBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        dialogUpdateBtn.setOnClickListener(new OnClickTelephoneUpdateBtn());

        return dialog;
    }

    private void bindControls() {
        newTelephoneField = dialogView.findViewById(R.id.change_telephone_dialog_newTelephoneField);
        codeField = dialogView.findViewById(R.id.change_telephone_dialog_codeField);
        verifyCodeBtn = dialogView.findViewById(R.id.change_telephone_dialog_verifyCodeBtn);
        resendBtn = dialogView.findViewById(R.id.change_telephone_dialog_resendBtn);

        progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleSmall);


        verifyCodeBtn.setOnClickListener(this);
        resendBtn.setOnClickListener(this);

        codeField.setVisibility(View.GONE);
        verifyCodeBtn.setVisibility(View.GONE);
        resendBtn.setVisibility(View.GONE);
    }

    private class OnClickTelephoneUpdateBtn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showProgressBar();
            trySendCode();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_telephone_dialog_verifyCodeBtn:
                verifyCode();
                break;
            case R.id.change_telephone_dialog_resendBtn:
                resendCode();
                break;
        }
    }

    private void trySendCode(){
        final String newTelephoneString = newTelephoneField.getText().toString();
        if (newTelephoneString.isEmpty()){
            Toast.makeText(getActivity(),"invalid telephone",Toast.LENGTH_LONG).show();
        }

        setUpVerificationCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                newTelephoneString,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                verificationStateChangedCallbacks);

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

                        newTelephoneField.setEnabled(false);
                        verifyCodeBtn.setEnabled(true);
                        resendBtn.setEnabled(true);
                        dialogUpdateBtn.setEnabled(false);
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
                            Toast.makeText(getActivity(),
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
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            onSuccessfulLogin(user);

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getActivity(),
                                        "invalid credential",Toast.LENGTH_LONG ).show();
                            }
                        }
                    }
                });
    }

    private void verifyCode(){
        String code = codeField.getText().toString();
        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }


    private void resendCode(){
        String telephoneString = newTelephoneField.getText().toString();

        setUpVerificationCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                telephoneString,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                verificationStateChangedCallbacks,
                resendingToken);
    }

    private void onSuccessfulLogin(final FirebaseUser firebaseUser){
        final Auth auth = application.getAuth();
        final User user = auth.getUser();

        //change database user new key and telephone
        final DatabaseReference cadresRef = application.getFirebaseDatabase()
                .getReference("cadres");
        final DatabaseReference cadreRef = cadresRef.child(user.getTelephone());

        cadreRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String newTelephone = firebaseUser.getPhoneNumber();
                Log.e(TAG, "try updating database cadre node:" + newTelephone);

                Cadre cadre = dataSnapshot.getValue(Cadre.class);

                Log.e(TAG, "try updating database cadre node:" + "id: " + cadre.getId() + "name: "+ cadre.getName());
                cadre.setTelephone(newTelephone);
                if (newTelephone != null){
                    cadreRef.removeValue();
                    cadresRef.child(newTelephone).setValue(cadre);

                    //set new telephone to auth user
                    user.setTelephone(newTelephone);
                    auth.setAuthToken(newTelephone);

                    bus.post(new UserDetailsUpdatedEvent(user));

                    Toast.makeText(getActivity(), "telephone updated", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(), "telephone updating failed", Toast.LENGTH_LONG).show();
                }




                dismiss();
                //set auth token to new telephone
                //fire update users detail event

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "database errors");
            }
        });



        bus.post(new UserDetailsUpdatedEvent(user));
    }

    private void showProgressBar(){
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }
    private void hideProgressBar(){
        progressBar.setVisibility(ProgressBar.GONE);
    }


}
