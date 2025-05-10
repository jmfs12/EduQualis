package com.social.eduqualis.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.social.eduqualis.dtos.AuthResponse;
import com.social.eduqualis.dtos.UserDTO;
import com.social.eduqualis.entity.User;
import com.social.eduqualis.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final FirebaseAuth firebaseAuth;

    @Autowired
    public UserController(UserService userService, FirebaseAuth firebaseAuth) {
        this.userService = userService;
        this.firebaseAuth = firebaseAuth;

    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        try{
            return userService.registerUser(userDTO);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        try{
            return ResponseEntity.ok(userService.loginUser(userDTO));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO userDTO) {
        try {
            userService.deleteUser(userDTO.getUsername());
            return ResponseEntity.ok("User deleted successfully");
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadUser(@RequestParam("file") MultipartFile file, @RequestParam String username) {
        try {
            String url = userService.setPhotoPath(file, username);
            return ResponseEntity.created(URI.create(url)).body("File uploaded successfully");
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Could not upload file: " + e.getMessage());
        }
    }
}
