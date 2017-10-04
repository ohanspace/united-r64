package org.badhan.blooddonor.service;

import com.squareup.otto.Subscribe;

import org.badhan.blooddonor.core.Auth;
import org.badhan.blooddonor.core.MyApplication;
import org.badhan.blooddonor.entity.User;
import org.badhan.blooddonor.service.auth.LoginResponse;
import org.badhan.blooddonor.service.auth.LoginWithExternalProvider;
import org.badhan.blooddonor.service.auth.LoginWithLocalToken;
import org.badhan.blooddonor.service.auth.LoginWithUsername;
import org.badhan.blooddonor.service.auth.Register;
import org.badhan.blooddonor.service.auth.RegisterWithExternalProvider;
import org.badhan.blooddonor.service.profile.ChangeAvatar;
import org.badhan.blooddonor.service.profile.ChangePassword;
import org.badhan.blooddonor.service.profile.UpdateProfile;
import org.badhan.blooddonor.service.profile.UserDetailsUpdatedEvent;

public class InMemoryAccountService extends BaseInMemoryService {

    public InMemoryAccountService(MyApplication application){
        super(application);
    }

    @Subscribe
    public void updateProfile(final UpdateProfile.Request request){
        final UpdateProfile.Response response = new UpdateProfile.Response();

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                User user = application.getAuth().getUser();
                user.setDisplayName(request.displayName);
                user.setEmail(request.email);

                bus.post(response);
                bus.post(new UserDetailsUpdatedEvent(user));
            }
        }, 1000,1500);
    }


    @Subscribe
    public void changeAvatar(final ChangeAvatar.Request request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                User user = application.getAuth().getUser();
                user.setAvatarUrl(request.newAvatarUri.toString());

                bus.post(new ChangeAvatar.Response());
                bus.post(new UserDetailsUpdatedEvent(user));
            }
        },3000,4000);
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
    public void loginWithUsername(final LoginWithUsername.Request request){
        final LoginWithUsername.Response response = new LoginWithUsername.Response();

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                if (request.username.equals("borhan"))
                    response.setPropertyError("username","username should not borhan");
                loginUser(response);
                bus.post(response);
            }
        }, 2000,3000);


    }

    @Subscribe
    public void loginWithExternalProvider(LoginWithExternalProvider.Request request){
        final LoginWithExternalProvider.Response response = new LoginWithExternalProvider.Response();
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                loginUser(response);
                bus.post(response);
            }
        },2000,3000);
    }

    @Subscribe
    public void register(Register.Request request){
        final Register.Response response = new Register.Response();

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                loginUser(response);
                bus.post(response);
            }
        },2000,3000);
    }

    @Subscribe
    public void registerWithExternalProvider(RegisterWithExternalProvider.Request request){
        final RegisterWithExternalProvider.Response response = new RegisterWithExternalProvider.Response();

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                loginUser(response);
                bus.post(response);
            }
        },2000,3000);
    }


    @Subscribe
    public void loginWithLocalToken(LoginWithLocalToken.Request request){
        final LoginWithLocalToken.Response response = new LoginWithLocalToken.Response();

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                loginUser(response);
                bus.post(response);
            }
        }, 2000,3000);
    }



    private void loginUser(LoginResponse response){
        Auth auth = application.getAuth();
        User user = auth.getUser();

        user.setDisplayName("Borhan chowdhury");
        user.setUsername("borhan");
        user.setEmail("borhan.chittagong@gmail.com");
        user.setAvatarUrl("https://en.gravatar.com/avatar/1");
        user.setLoggedIn(true);
        user.setId(123);

        auth.setAuthToken("fakeauthtoken");

        response.id = user.getId();
        response.displayName = user.getDisplayName();
        response.email = user.getEmail();
        response.username = user.getUsername();
        response.avatarUrl = user.getAvatarUrl();

        response.authToken = auth.getAuthToken();

        bus.post(new UserDetailsUpdatedEvent(user));
    }


}
