package com.rgt.ebanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rgt.ebanking.entity.AppUser;


@Repository
public interface UserRepository extends JpaRepository<AppUser, Long>{
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String email);
    boolean existsByUsername(String username);

    /* 
    Added here to tell my future self that we can add sql query in this format 
    
    @Query("SELECT u FROM AppUser u WHERE u.email = :email OR u.username = :username")
    Optional<AppUser> findExisting(@Param("email") String email, @Param("username") String username);
    
    */
}
