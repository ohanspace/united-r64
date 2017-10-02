package org.badhan.blooddonor.service;

import com.squareup.otto.Subscribe;

import org.badhan.blooddonor.core.MyApplication;

public class InMemoryAccountService extends BaseInMemoryService {

    public InMemoryAccountService(MyApplication application){
        super(application);
    }

    @Subscribe
    public void updateProfile(AccountService.ProfileUpdateRequest request){
        AccountService.ProfileUpdateResponse response = new AccountService.ProfileUpdateResponse();
        postDelayed(response);
    }


    @Subscribe
    public void changeAvatar(AccountService.AvatarChangeRequest request){
        postDelayed(new AccountService.AvatarChangeResponse());
    }

    @Subscribe
    public void changePassword(AccountService.PasswordChangeRequest request){
        AccountService.PasswordChangeResponse response = new AccountService.PasswordChangeResponse();

        if (!request.newPassword.equals(request.confirmNewPassword))
            response.setPropertyError("confirmNewPassword","confirm password did not matched");
        if (request.newPassword.length() < 4)
            response.setPropertyError("newPassword","password must be greater than 3 characters");

        postDelayed(response);
    }
}
