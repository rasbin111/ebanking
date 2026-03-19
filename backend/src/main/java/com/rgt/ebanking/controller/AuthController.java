package com.rgt.ebanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgt.ebanking.dto.response.LoginResponse;
import com.rgt.ebanking.entity.AppUser;
import com.rgt.ebanking.security.JWTUtil;
import com.rgt.ebanking.service.AppUserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    AppUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateuser(@RequestBody AppUser user) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            AppUser appUser = (AppUser) authentication.getPrincipal();
            String access = jwtUtil.generateAccessToken(appUser.getUsername());
            String refresh = jwtUtil.generateRefreshToken(appUser.getUsername());
            LoginResponse response = LoginResponse.builder()
                    .username(appUser.getUsername())
                    .email(appUser.getEmail())
                    .accessToken(access)
                    .refreshToken(refresh)
                    .build();
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody AppUser user) {
        if (userDetailsService.creatUser(user) == null) {
            return ResponseEntity.badRequest()
                    .body("Error: User with this email: " + user.getEmail() + " already exists");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        }
    }
}
