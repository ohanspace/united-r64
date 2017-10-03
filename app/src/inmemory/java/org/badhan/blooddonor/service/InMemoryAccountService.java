package org.badhan.blooddonor.service;

import com.squareup.otto.Subscribe;

import org.badhan.blooddonor.core.MyApplication;
import org.badhan.blooddonor.service.auth.LoginWithUsername;
import org.badhan.blooddonor.service.profile.ChangeAvatar;
import org.badhan.blooddonor.service.profile.ChangePassword;
import org.badhan.blooddonor.service.profile.UpdateProfile;

public class InMemoryAccountService extends BaseInMemoryService {

    public InMemoryAccountService(MyApplication application){
        super(application);
    }

    @Subscribe
    public void updateProfile(UpdateProfile.Request request){
        UpdateProfile.Response response = new UpdateProfile.Response();
        postDelayed(response);
    }


    @Subscribe
    public void changeAvatar(ChangeAvatar.Request request){
        postDelayed(new ChangeAvatar.Response());
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
    public void loginWithUsername(LoginWithUsername.Request request){
        LoginWithUsername.Response response = new LoginWithUsername.Response();
        if (request.username.equals("borhan")){
            response.setPropertyError("username","username should not borhan");
        }
        postDelayed(response);

    }
}
