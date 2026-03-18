package com.rgt.ebanking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rgt.ebanking.entity.AppUser;
import com.rgt.ebanking.service.AppUserService;

@Controller
@RequestMapping("/users")
public class AppUserController {
    
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/")
    public @ResponseBody List<AppUser> getAllUsers(){
        return appUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getuserById(@PathVariable Long id){
        AppUser user = appUserService.getUserById(id);
        
        if (user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser user){
        return new ResponseEntity<AppUser>(appUserService.creatUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser user){
        AppUser updatedUser = appUserService.updatUser(id, user);
        return new ResponseEntity<AppUser>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
        boolean isDeleted = appUserService.deleteUser(id);

        if (isDeleted){
            return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(isDeleted, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/upload/avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id,  @RequestParam("file") MultipartFile file){
        
        appUserService.uploadAvatar(file, id);
        return ResponseEntity.ok("Upload Successful");
    }

}
