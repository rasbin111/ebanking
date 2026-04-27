package com.rgt.ebanking.event;

import com.rgt.ebanking.entity.AppUser;

public class UserCreatedEvent {
    private final AppUser user;
    public UserCreatedEvent(AppUser user) {
        this.user = user;
    }

    public AppUser getUser() {
        return user;
    }
}