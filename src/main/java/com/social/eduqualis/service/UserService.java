package com.social.eduqualis.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.social.eduqualis.dtos.AuthResponse;
import com.social.eduqualis.dtos.UserDTO;
import com.social.eduqualis.entity.User;
import com.social.eduqualis.exceptions.UserAlreadyExistsException;
import com.social.eduqualis.exceptions.UserNotFoundException;
import com.social.eduqualis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository UserRepository;

    @Value("${eduqualis.photo.dir}")
    private String PHOTO_DIR;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.UserRepository = userRepository;
    }

    public UserDTO registerUser(UserDTO userDTO) throws FirebaseAuthException {
        if (UserRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (UserRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(userDTO.getEmail())
                .setPassword(userDTO.getPassword())
                .setDisplayName(userDTO.getUsername());

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

        User user = new User(userDTO);
        user.setFirebaseUid(userRecord.getUid());
        UserRepository.save(user);

        return userDTO;
    }

    public AuthResponse loginUser(UserDTO userDTO) throws IOException {
        User user = UserRepository.findByEmail(userDTO.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new UserNotFoundException("Invalid password");
        }

        return new AuthResponse(
                FirebaseAuthService.signInWithEmailAndPassword(userDTO.getEmail(), userDTO.getPassword()),
                user.getUsername());

    }

    public void deleteUser(String username) throws FirebaseAuthException{
        User user = UserRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        UserRepository.delete(user);

        FirebaseAuth.getInstance().deleteUser(user.getFirebaseUid());
    }

    public String setPhotoPath(MultipartFile file, String username) throws IOException {

        User user = UserRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        File directory = new File(PHOTO_DIR);
        if (!directory.exists()){
            directory.mkdirs();
        }
        String filePath = PHOTO_DIR + file.getOriginalFilename();
        File dest = new File(filePath);
        file.transferTo(dest);
        user.setPhotoPath(filePath);
        UserRepository.save(user);
        return filePath;
    }
}
