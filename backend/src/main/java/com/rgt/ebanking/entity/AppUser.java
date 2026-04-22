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
@Table(name="app_users")
public class AppUser implements  UserDetails{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int Id;

    @Column(unique=true)
    private String username;

    @Column(unique=true, nullable=false)
    private String email;

    private String firstName;
    private String middleName;
    private String lastName;

    private String password;

    private String gender;
    private String role;

    private String avatarPath;

    @Override
    @JsonIgnore
    public Collection<? extends  GrantedAuthority> getAuthorities(){
        if (this.role == null || this.role.trim().isEmpty()){
            return List.of(new SimpleGrantedAuthority("NORMAL_USER"));
        }
        String roleName = this.role.startsWith("ROLE_") ? this.role : "ROLE_" + this.role;
        
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getUsername(){
        return this.email;
    }

    public String getRealusername(){
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){

        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}
