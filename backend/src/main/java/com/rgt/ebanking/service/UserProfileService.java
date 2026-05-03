package com.rgt.ebanking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rgt.ebanking.entity.Profile;
import com.rgt.ebanking.repository.ProfileRepository;

@Service
public class UserProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile createProfile(Profile profile) {
        try {
            return profileRepository.save(profile);
        } catch (Exception e) {
            throw e;       
        }
    }
}