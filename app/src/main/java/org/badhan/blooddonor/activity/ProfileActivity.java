package org.badhan.blooddonor.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.soundcloud.android.crop.Crop;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseAuthActivity;
import org.badhan.blooddonor.view.MainNavDrawer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends BaseAuthActivity {
    private static final int REQUEST_SELECT_AVATAR = 1;
    private ImageView avatarImage;
    private View avatarChangeAction;
    private View avatarChangeProgress;
    private File tempImageFile;
    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        setNavDrawer(new MainNavDrawer(this));

        changeTabletLayout();

        avatarImage = findViewById(R.id.profile_activity_avatarImage);
        avatarChangeAction = findViewById(R.id.profile_activity_avatarChangeAction);
        avatarChangeProgress = findViewById(R.id.profile_activity_avatarChangeProgressFrame);
        tempImageFile = new File(getExternalCacheDir(), "temp_avatar.jpg");

        avatarChangeProgress.setVisibility(View.GONE);

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

    private class OnAvatarChangeClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            selectNewAvatar();
        }
    }

    private void selectNewAvatar() {
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
        }else if (requestCode == Crop.REQUEST_CROP){
            //TODO send cropped image to server for persistence
            avatarImage.setImageResource(0);
            avatarImage.setImageURI(Uri.fromFile(tempImageFile));
        }


    }

    private void handleSelectedImage(Intent data) {
        Uri tempImageFileUri = Uri.fromFile(this.tempImageFile);
        Uri selectedImageFileUri;
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

}
