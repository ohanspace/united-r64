package org.badhan.r64.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.badhan.r64.R;
import org.badhan.r64.core.BaseDialogFragment;
import org.badhan.r64.service.profile.ChangePassword;

public class ChangeTelephoneDialog extends BaseDialogFragment {
    private View dialogView;
    private EditText newTelephone;
    private ProgressBar progressBar;

    @Override
    public Dialog onCreateDialog(Bundle savedState){
        dialogView = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_change_telephone, null, false);

        bindControls();

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setTitle("Change Telephone")
                .setPositiveButton("Update",null)
                .setNegativeButton("Cancel",null)
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new OnClickTelephoneUpdateBtn());
        return dialog;
    }

    private void bindControls() {
        newTelephone = dialogView.findViewById(R.id.dialog_change_password_newPassword);
    }

    private class OnClickTelephoneUpdateBtn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleSmall);
            progressBar.setVisibility(ProgressBar.VISIBLE);
            //send verification code
        }
    }


}
