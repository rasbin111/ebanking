package com.rgt.ebanking.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(unique = true)
    private String username;

    private String firstName;
    private String middleName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;
    
    private String gender;
    private String role;
    private String avatarPath;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 1. Check if the role field itself is null
        if (this.role == null || this.role.trim().isEmpty()) {
            // Return a default role so it doesn't crash
            return List.of(new SimpleGrantedAuthority("NORMAL_USER"));
        }

        // 2. Spring Security expectations:
        // If your DB has "ADMIN", SimpleGrantedAuthority should usually be "ROLE_ADMIN"
        String roleName = this.role.startsWith("ROLE_") ? this.role : "ROLE_" + this.role;

        return List.of(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Set to true so the account never "expires"
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Set to true so the account is never "locked"
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Set to true so passwords never "expire"
    }

    @Override
    public boolean isEnabled() {
        return true; // Set to true so the user is "active"
    }

}