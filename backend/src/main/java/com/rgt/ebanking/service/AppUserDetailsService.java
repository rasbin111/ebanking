package com.rgt.ebanking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rgt.ebanking.entity.AppUser;
import com.rgt.ebanking.event.UserCreatedEvent;
import com.rgt.ebanking.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Autowired
    // private ImageValidator imageValidator;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("No user found iwth email"));
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

    @Transactional
    public AppUser creatUser(AppUser user){
        Optional<AppUser> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()){
            return null;
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        AppUser savedUser = userRepository.save(user);
        eventPublisher.publishEvent(new UserCreatedEvent(savedUser));

        return savedUser;
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


    // public void uploadAvatar(MultipartFile file, Long id){
    //     imageValidator.validateImage(file);

    //     String ext = switch(Objects.requireNonNullElse(file.getContentType(), "")){
    //         case "image/png" -> "png";
    //         case "image/webp" -> "webp";
    //         default -> "jpg";
    //     };

    //     String filename = UUID.randomUUID().toString() + "." + ext;
    //     String uploadDirName = "uploads/user-avatars";
    //     Path uploadDir = Paths.get(uploadDirName).toAbsolutePath();
    //     Path fullPath = uploadDir.resolve(filename);

    //     try{
    //         Files.createDirectories(uploadDir);
    //         file.transferTo(fullPath);
    //     } catch (IOException ex){
    //         throw new RuntimeException("Could not store avatar", ex);
    //     }

    //     AppUser user = userRepository.findById(id)
    //     .orElseThrow(() -> new RuntimeException("User not found"));
    //     user.setAvatarPath(uploadDirName + "/" + filename);
    //     userRepository.save(user);
    // }


}
