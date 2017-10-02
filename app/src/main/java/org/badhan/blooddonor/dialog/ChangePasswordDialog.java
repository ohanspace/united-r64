package org.badhan.blooddonor.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseDialogFragment;
import org.badhan.blooddonor.service.AccountService;

public class ChangePasswordDialog extends BaseDialogFragment {
    private View dialogView;
    private EditText currentPasswordField;
    private EditText newPasswordField;
    private EditText confirmNewPasswordField;
    private ProgressBar progressBar;

    @Override
    public Dialog onCreateDialog(Bundle savedState){
        dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_password, null, false);

        bindControls();

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setTitle("Change Password")
                .setPositiveButton("Update",null)
                .setNegativeButton("Cancel",null)
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new OnClickPasswordUpdateBtn());
        return dialog;
    }

    private void bindControls() {
        currentPasswordField = dialogView.findViewById(R.id.dialog_change_password_currentPassword);
        newPasswordField = dialogView.findViewById(R.id.dialog_change_password_newPassword);
        confirmNewPasswordField = dialogView.findViewById(R.id.dialog_change_password_confirmNewPassword);

        if (!application.getAuth().getUser().isHasPassword())
            currentPasswordField.setVisibility(View.GONE);
    }

    private class OnClickPasswordUpdateBtn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleSmall);
            progressBar.setVisibility(ProgressBar.VISIBLE);

            bus.post(new AccountService.PasswordChangeRequest(
                    currentPasswordField.getText().toString(),
                    newPasswordField.getText().toString(),
                    confirmNewPasswordField.getText().toString()
            ));
        }
    }


    @Subscribe
    public void onPasswordChanged(AccountService.PasswordChangeResponse response){
        if (response.succeed()){
            Toast.makeText(getActivity(), "Password updated", Toast.LENGTH_LONG).show();
            dismiss();
            application.getAuth().getUser().setHasPassword(true);
            return;
        }

        currentPasswordField.setError(response.getPropertyError("currentPassword"));
        newPasswordField.setError(response.getPropertyError("newPassword"));
        confirmNewPasswordField.setError(response.getPropertyError("confirmNewPassword"));

        response.showErrorToast(getActivity());
    }
}
