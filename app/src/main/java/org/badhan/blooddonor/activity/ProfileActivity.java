package org.badhan.blooddonor.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.soundcloud.android.crop.Crop;
import com.squareup.otto.Subscribe;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseAuthActivity;
import org.badhan.blooddonor.dialog.ChangePasswordDialog;
import org.badhan.blooddonor.entity.User;
import org.badhan.blooddonor.service.profile.ChangeAvatar;
import org.badhan.blooddonor.service.profile.UpdateProfile;
import org.badhan.blooddonor.service.profile.UserDetailsUpdatedEvent;
import org.badhan.blooddonor.view.MainNavDrawer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends BaseAuthActivity {
    private static final int REQUEST_SELECT_AVATAR = 1;

    private static final int STATE_VIEWING = 1;
    private static final int STATE_EDITING = 2;

    private static final String BUNDLE_STATE = "BUNDLE_STATE";
    private static boolean isProgressBarVisible;

    private int currentState;
    private EditText displayNameField;
    private EditText emailField;
    private ActionMode editProfileActionMode;

    private ImageView avatarImage;
    private View avatarChangeAction;
    private View avatarChangeProgress;
    private File tempImageFile;

    private ProgressBar profileUpdatingProgressBar;

    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        setNavDrawer(new MainNavDrawer(this));

        changeTabletLayout();

        bindControls();

        User user = application.getAuth().getUser();
        getSupportActionBar().setTitle(user.getDisplayName());

        if (savedState == null){
            displayNameField.setText(user.getDisplayName());
            emailField.setText(user.getEmail());
            changeState(STATE_VIEWING);
        }else
            changeState(savedState.getInt(BUNDLE_STATE));

        if (isProgressBarVisible)
            showProgressBar(true);

    }

    private void bindControls() {
        avatarImage = findViewById(R.id.profile_activity_avatarImage);
        avatarChangeAction = findViewById(R.id.profile_activity_avatarChangeAction);
        avatarChangeProgress = findViewById(R.id.profile_activity_avatarChangeProgressFrame);
        profileUpdatingProgressBar = findViewById(R.id.profile_activity_progressBar);
        displayNameField = findViewById(R.id.profile_activity_displayNameField);
        emailField = findViewById(R.id.profile_activity_emailField);

        avatarChangeProgress.setVisibility(View.GONE);
        profileUpdatingProgressBar.setVisibility(ProgressBar.GONE);

        avatarImage.setOnClickListener(new OnAvatarChangeClickHandler());
        avatarChangeAction.setOnClickListener(new OnAvatarChangeClickHandler());
    }

    private void changeTabletLayout() {
        if (isTablet){
            View editFieldsLayout = findViewById(R.id.profile_activity_editFieldsLayout);
            RelativeLayout.LayoutParams  params = (RelativeLayout.LayoutParams) editFieldsLayout.getLayoutParams();

            params.setMargins(params.topMargin,0,0,0);

            params.removeRule(RelativeLayout.BELOW);
            params.addRule(RelativeLayout.END_OF, R.id.profile_activity_avatarImage);

            editFieldsLayout.setLayoutParams(params);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState){
        super.onSaveInstanceState(savedState);
        savedState.putInt(BUNDLE_STATE, currentState);
    }

    private class OnAvatarChangeClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            selectNewAvatar();
        }

    }

    private void selectNewAvatar() {
        tempImageFile = new File(getExternalCacheDir(), "temp_avatar.jpg");

        List<Intent> otherImageCaptureIntents = new ArrayList<>();
        List<ResolveInfo> otherImageCaptureActivity = getPackageManager()
                .queryIntentActivities(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 0);

        for (ResolveInfo info : otherImageCaptureActivity){
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempImageFile));

            otherImageCaptureIntents.add(captureIntent);
        }

        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);
        selectImageIntent.setAction(Intent.ACTION_PICK);
        selectImageIntent.setType("image/*");

        Intent chooser = Intent.createChooser(selectImageIntent,"Chooser avatar");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                otherImageCaptureIntents.toArray(new Parcelable[otherImageCaptureIntents.size()]) );

        startActivityForResult(chooser, REQUEST_SELECT_AVATAR);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != RESULT_OK){
            tempImageFile.delete();
            return;
        }
        if (requestCode == REQUEST_SELECT_AVATAR){
            handleSelectedImage(data);
        }
        else if (requestCode == Crop.REQUEST_CROP){
            avatarChangeProgress.setVisibility(View.VISIBLE);
            bus.post(new ChangeAvatar.Request(Uri.fromFile(tempImageFile)));
           // avatarImage.setImageResource(0);
            //avatarImage.setImageURI(Uri.fromFile(tempImageFile));
        }
    }
    @Subscribe
    public void onAvatarUpdated(ChangeAvatar.Response response){
        avatarChangeProgress.setVisibility(View.GONE);
        //todo handle error
        if (!response.succeed())
            response.showErrorToast(this);
    }

    @Subscribe
    public void onProfileUpdated(UpdateProfile.Response response){
        if (!response.succeed()){
            response.showErrorToast(this);
            changeState(STATE_EDITING);
        }
        displayNameField.setError(response.getPropertyError("displayName"));
        emailField.setError(response.getPropertyError("email"));
        showProgressBar(false);
    }

    private void showProgressBar(boolean visible) {
        if (visible){
            profileUpdatingProgressBar.setVisibility(ProgressBar.VISIBLE);
        }
        else
            profileUpdatingProgressBar.setVisibility(ProgressBar.GONE);

        isProgressBarVisible = visible; //important if activity recreated
    }

    private void handleSelectedImage(Intent data) {
        Uri tempImageFileUri = Uri.fromFile(this.tempImageFile);
        Uri selectedImageFileUri;
        //TODO selection from gallery not working in actual phone
        if (data != null && data.getData() != null){
            //chosen from gallery
            selectedImageFileUri = data.getData();
        }else {
            //chosen from camera
            selectedImageFileUri = tempImageFileUri;
        }

        Crop.of(selectedImageFileUri, tempImageFileUri)
                .asSquare()
                .start(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.profile_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.profile_activity_menu_edit){
            changeState(STATE_EDITING);
            return true;
        }
        else if (item.getItemId() == R.id.profile_activity_menu_change_password){
            showChangePasswordDialog();
            return true;
        }
        return false;
    }

    private void showChangePasswordDialog() {
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        ChangePasswordDialog dialog = new ChangePasswordDialog();
        dialog.show(transaction, null);
    }

    private void changeState(int state) {
        if (currentState == state)
            return;
        currentState = state;
        if (state == STATE_VIEWING){
            displayNameField.setEnabled(false);
            emailField.setEnabled(false);
            avatarChangeAction.setVisibility(View.VISIBLE);

            if (editProfileActionMode != null){
                editProfileActionMode.finish();
                editProfileActionMode = null;
            }

        }else if (state == STATE_EDITING){
            displayNameField.setEnabled(true);
            emailField.setEnabled(true);
            avatarChangeAction.setVisibility(View.GONE);

            editProfileActionMode = toolbar.startActionMode(new EditProfileActionCallback());
        }else
            throw new IllegalArgumentException("invalid state code: "+state);
    }

    private class EditProfileActionCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.profile_activity_menu_edit, menu);
            return true;
        }
        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.profile_activity_menu_edit_done){
                showProgressBar(true);
                changeState(STATE_VIEWING);

                UpdateProfile.Request request =
                        new UpdateProfile.Request(displayNameField.getText().toString(),
                                emailField.getText().toString());
                bus.post(request);

                changeState(STATE_VIEWING);
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            if (currentState != STATE_VIEWING)
                changeState(STATE_VIEWING);
        }

    }


    @Subscribe
    public void onUserDetailsUpdated(UserDetailsUpdatedEvent event){
        getSupportActionBar().setTitle(event.user.getDisplayName());
    }
   
}
