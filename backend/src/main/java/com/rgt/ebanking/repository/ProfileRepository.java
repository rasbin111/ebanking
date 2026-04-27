package com.rgt.ebanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rgt.ebanking.entity.Profile;

public interface ProfileRepository extends  JpaRepository<Profile, Long>{
    Optional<Profile> findByStrId(String strId);
}