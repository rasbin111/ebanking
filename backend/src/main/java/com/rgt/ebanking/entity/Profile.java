package com.rgt.ebanking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="profiles")

public class Profile {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long profileId;

    @Column(unique=true)
    private String strId;
    
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String avatarPath;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName="user_id")
    private AppUser appUser;


}