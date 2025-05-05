package com.social.eduqualis.controller;

import com.social.eduqualis.dtos.UserDTO;
import com.social.eduqualis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @GetMapping("/list")
    public UserDTO findUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.findByUsername(username)).getBody();
    }

    @GetMapping("/all")
    public List<UserDTO> findAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users).getBody();
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllUser() {
        userService.deleteAllUser();
    }

    @PutMapping("/update/username")
    @ResponseStatus(HttpStatus.OK)
    public void updateUsername(@RequestBody String oldUsername, @RequestBody String newUsername) {
        userService.updateUsername(oldUsername, newUsername);
    }

    @PutMapping("/update/email")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmail(@RequestBody String username, @RequestBody String email) {
        userService.updateEmail(username, email);
    }

    @PutMapping("/update/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@RequestBody String username, @RequestBody String password) {
        userService.updatePassword(username, password);
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadUser(@RequestParam("file") MultipartFile file, String username) {
        userService.setPhotoPath(file, username);
    }
}
