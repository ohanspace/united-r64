package org.badhan.blooddonor.service.profile;

import org.badhan.blooddonor.entity.User;

public class UserDetailsUpdatedEvent {
    public User user;

    public UserDetailsUpdatedEvent(User user){
        this.user = user;
    }
}
