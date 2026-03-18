package com.rgt.ebanking.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rgt.ebanking.entity.AppUser;
import com.rgt.ebanking.repository.UserRepository;
import com.rgt.ebanking.validation.ImageValidator;

import jakarta.transaction.Transactional;

@Service
public class AppUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageValidator imageValidator;

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


    public void uploadAvatar(MultipartFile file, Long id){
        imageValidator.validateImage(file);

        String ext = switch(Objects.requireNonNullElse(file.getContentType(), "")){
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            default -> "jpg";
        };

        String filename = UUID.randomUUID().toString() + "." + ext;
        String uploadDirName = "uploads/user-avatars";
        Path uploadDir = Paths.get(uploadDirName).toAbsolutePath();
        Path fullPath = uploadDir.resolve(filename);

        try{
            Files.createDirectories(uploadDir);
            file.transferTo(fullPath);
        } catch (IOException ex){
            throw new RuntimeException("Could not store avatar", ex);
        }

        AppUser user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));
        user.setAvatarPath(uploadDirName + "/" + filename);
        userRepository.save(user);
    }
}
