package org.badhan.r64.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.badhan.r64.R;
import org.badhan.r64.core.BaseAuthActivity;
import org.badhan.r64.dialog.ChangePasswordDialog;
import org.badhan.r64.dialog.ChangeTelephoneDialog;
import org.badhan.r64.entity.User;
import org.badhan.r64.service.profile.ChangeAvatar;
import org.badhan.r64.service.profile.UpdateProfile;
import org.badhan.r64.service.profile.UserDetailsUpdatedEvent;
import org.badhan.r64.view.MainNavDrawer;

import java.io.File;


public class ProfileActivity extends BaseAuthActivity {
    private static final int REQUEST_SELECT_AVATAR = 1;

    private static final int STATE_VIEWING = 1;
    private static final int STATE_EDITING = 2;

    private static final String BUNDLE_STATE = "BUNDLE_STATE";
    private static boolean isProgressBarVisible;

    private int currentState;
    private EditText displayNameField;
    private EditText emailField;
    private EditText cadreIdField;
    private EditText batchField;
    private EditText homeDistrictField;
    private EditText postingAddressField;
    private EditText bloodGroupField;
    private EditText universityField;
    private EditText sessionField;

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
            cadreIdField.setText(user.getCadreId());
            batchField.setText(user.getBatch());
            homeDistrictField.setText(user.getHomeDistrict());
            postingAddressField.setText(user.getPostingAddress());
            bloodGroupField.setText(user.getBloodGroup());
            universityField.setText(user.getUniversity());
            sessionField.setText(user.getSession());

            changeState(STATE_VIEWING);
        }else
            changeState(savedState.getInt(BUNDLE_STATE));

        if (isProgressBarVisible)
            showProgressBar(true);

        downloadAvatarIfExist();

    }

    private void downloadAvatarIfExist(){
        User user = application.getAuth().getUser();
        user.getAvatarStorageRef().getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(ProfileActivity.this)
                        .load(uri.toString())
                        .placeholder(R.drawable.ic_action_profile)
                        .error(R.drawable.ic_action_profile)
                        .into(avatarImage);
            }
        });
    }

    private void bindControls() {
        avatarImage = findViewById(R.id.profile_activity_avatarImage);
        avatarChangeAction = findViewById(R.id.profile_activity_avatarChangeAction);
        avatarChangeProgress = findViewById(R.id.profile_activity_avatarChangeProgressFrame);
        profileUpdatingProgressBar = findViewById(R.id.profile_activity_progressBar);

        displayNameField = findViewById(R.id.profile_activity_displayNameField);
        emailField = findViewById(R.id.profile_activity_emailField);
        cadreIdField = findViewById(R.id.profile_activity_cadreIdField);
        batchField = findViewById(R.id.profile_activity_batchField);
        homeDistrictField = findViewById(R.id.profile_activity_homeDistrictField);
        postingAddressField = findViewById(R.id.profile_activity_postingAddressField);
        bloodGroupField = findViewById(R.id.profile_activity_bloodGroupField);
        universityField = findViewById(R.id.profile_activity_universityField);
        sessionField = findViewById(R.id.profile_activity_session);

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
            chooseAvatar();
        }

    }

    private void chooseAvatar() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SELECT_AVATAR);


//        List<Intent> otherImageCaptureIntents = new ArrayList<>();
//        List<ResolveInfo> otherImageCaptureActivity = getPackageManager()
//                .queryIntentActivities(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 0);
//
//        for (ResolveInfo info : otherImageCaptureActivity){
//            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            captureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//            captureIntent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
//            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempImageFile));
//
//            otherImageCaptureIntents.add(captureIntent);
//        }
//
//        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);
//        selectImageIntent.setAction(Intent.ACTION_PICK);
//        selectImageIntent.setType("image/*");
//
//        Intent chooser = Intent.createChooser(selectImageIntent,"Chooser avatar");
//        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,
//                otherImageCaptureIntents.toArray(new Parcelable[otherImageCaptureIntents.size()]) );
//
//        startActivityForResult(chooser, REQUEST_SELECT_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != RESULT_OK){
            tempImageFile.delete();
            return;
        }
        if (requestCode == REQUEST_SELECT_AVATAR){
            if (isStoragePermissionGranted()){
                handleSelectedImage(data);
            }else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == Crop.REQUEST_CROP){
            uploadCroppedImage();
            // avatarImage.setImageResource(0);
            //avatarImage.setImageURI(Uri.fromFile(tempImageFile));
        }
    }

    private void handleSelectedImage(Intent data) {
        tempImageFile = new File(getExternalCacheDir(), "temp_avatar.jpg");
        Uri tempImageFileUri = Uri.fromFile(tempImageFile);

        if (data != null && data.getData() != null){
            Uri localUri = data.getData();
            Crop.of(localUri, tempImageFileUri)
                    .asSquare()
                    .start(this);
        }
//        Uri tempImageFileUri = Uri.fromFile(this.tempImageFile);
//        Uri selectedImageFileUri;
//        //TODO selection from gallery not working in actual phone
//        if (data != null && data.getData() != null){
//            //chosen from gallery
//            selectedImageFileUri = data.getData();
//        }else {
//            //chosen from camera
//            selectedImageFileUri = tempImageFileUri;
//        }
//
//        Crop.of(selectedImageFileUri, tempImageFileUri)
//                .asSquare()
//                .start(this);
    }

    private void uploadCroppedImage(){
        avatarChangeProgress.setVisibility(View.VISIBLE);
        final User user = application.getAuth().getUser();
        String remoteFileName = "userId_"+user.getId()+".jpg";
        final StorageReference avatarRef = application.getFirebaseStorage()
                .getReference("cadreAvatars").child(remoteFileName);

        avatarRef.putFile(Uri.fromFile(tempImageFile))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(ProfileActivity.this,"avatar uploaded",Toast.LENGTH_SHORT).show();
                        Log.e("avatar uri", avatarRef.getPath());
                        bus.post(new ChangeAvatar.Request(avatarRef.getPath()));

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this,"avatar upload failed",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("profile image pick","Permission is granted");
                return true;
            } else {

                Log.v("profile image pick","Permission is revoked");
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("profile image pick","Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("on request permission","Permission: "+permissions[0]+ "was "+grantResults[0]);
            chooseAvatar();

        }
    }

    @Subscribe
    public void onAvatarUpdated(ChangeAvatar.Response response){
        avatarChangeProgress.setVisibility(View.GONE);
        //todo handle error
        if (!response.succeed())
            response.showErrorToast(this);

        downloadAvatarIfExist();

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
//        else if (item.getItemId() == R.id.profile_activity_menu_change_password){
//            showChangePasswordDialog();
//            return true;
//        }
        else if (item.getItemId() == R.id.profile_activity_change_telephone){
            showChangeTelephoneDialog();
            return true;
        }
        return false;
    }

    private void showChangeTelephoneDialog(){
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        ChangeTelephoneDialog dialog = new ChangeTelephoneDialog();
        dialog.show(transaction, null);
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
            batchField.setEnabled(false);
            cadreIdField.setEnabled(false);
            homeDistrictField.setEnabled(false);
            postingAddressField.setEnabled(false);
            bloodGroupField.setEnabled(false);
            universityField.setEnabled(false);
            sessionField.setEnabled(false);


            avatarChangeAction.setVisibility(View.VISIBLE);

            if (editProfileActionMode != null){
                editProfileActionMode.finish();
                editProfileActionMode = null;
            }

        }else if (state == STATE_EDITING){
            displayNameField.setEnabled(true);
            emailField.setEnabled(true);
            batchField.setEnabled(true);
            cadreIdField.setEnabled(true);
            homeDistrictField.setEnabled(true);
            postingAddressField.setEnabled(true);
            bloodGroupField.setEnabled(true);
            universityField.setEnabled(true);
            sessionField.setEnabled(true);

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

                UpdateProfile.Request request = new UpdateProfile.Request();
                request.setDisplayName(displayNameField.getText().toString());
                request.setEmail(emailField.getText().toString());
                request.setCadreId(cadreIdField.getText().toString());
                request.setBatch(batchField.getText().toString());
                request.setHomeDistrict(homeDistrictField.getText().toString());
                request.setPostingAddress(postingAddressField.getText().toString());
                request.setBloodGroup(bloodGroupField.getText().toString());
                request.setUniversity(universityField.getText().toString());
                request.setSession(sessionField.getText().toString());

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
        Toast.makeText(this, "Profile update successful", Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle(event.user.getDisplayName());
    }

   
}
