package org.badhan.blooddonor.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.badhan.blooddonor.R;

public class ChangePasswordDialog extends BaseDialogFragment {
    private View dialogView;
    private EditText currentPasswordField;
    private EditText newPasswordField;
    private EditText confirmNewPasswordField;

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
            //TODO send new password to persistence server
            Toast.makeText(getActivity(), "password updated!", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
