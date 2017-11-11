package org.badhan.r64.service.profile;

import org.badhan.r64.entity.User;

public class UserDetailsUpdatedEvent {
    public User user;

    public UserDetailsUpdatedEvent(User user){
        this.user = user;
    }
}
