package com.rgt.ebanking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rgt.ebanking.entity.AppUser;
import com.rgt.ebanking.repository.UserRepository;

@Service
public class AppUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public List<AppUser> getAllUsers(){
        return userRepository.findAll();
    }


    public AppUser getUserById(long id){
        Optional<AppUser> user = userRepository.findById(id);

        if (user.isPresent()){
            return user.get();
        } else {
            return null;
        }
    }

    public AppUser creatUser(AppUser user){
        Optional<AppUser> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()){
            return existingUser.get();
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public AppUser updatUser(Long id, AppUser user){
        boolean userExists = userRepository.existsById(id);

        if (userExists){
            AppUser editUser = userRepository.save(user);
            return editUser;
        } else{
            AppUser newUser = userRepository.save(user);
            return newUser;
        }
    }

    public boolean deleteUser(Long id){
        try{
            userRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
