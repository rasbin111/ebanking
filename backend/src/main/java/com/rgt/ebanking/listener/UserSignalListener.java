package com.rgt.ebanking.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.rgt.ebanking.entity.AppUser;
import com.rgt.ebanking.entity.Profile;
import com.rgt.ebanking.event.UserCreatedEvent;
import com.rgt.ebanking.service.UserProfileService;

@Component
public class UserSignalListener {

    @Autowired
    private UserProfileService profileService;

    @EventListener
    public void handleUserCreated(UserCreatedEvent event) {
        AppUser user = event.getUser();

        Profile profile = new Profile();
        profile.setStrId(user.getEmail());
        profile.setAppUser(user);

        Profile newProfile = profileService.createProfile(profile);
        if (newProfile != null) {
            System.out.println("New profile created: " + newProfile.getStrId());
        } else {
            System.err.println("Profile creation FAILED for user: " + user.getEmail());
        }
    }
}