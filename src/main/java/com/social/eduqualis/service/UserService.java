package com.social.eduqualis.service;

import com.social.eduqualis.dtos.UserDTO;
import com.social.eduqualis.entity.User;
import com.social.eduqualis.exceptions.UserAlreadyExistsException;
import com.social.eduqualis.exceptions.UserNotFoundException;
import com.social.eduqualis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UserService {
    UserRepository UserRepository;

    private static final String PHOTO_DIR = "/src/main/resources/photos/";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.UserRepository = userRepository;
    }

    public UserDTO registerUser(UserDTO userDTO) {
        if (UserRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (UserRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = new User(userDTO);
        UserRepository.save(user);

        return userDTO;
    }

    public UserDTO findByUsername(String username) {
        User user = UserRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return new UserDTO(user.getUsername(), user.getPassword(), user.getEmail());
    }

    public List<UserDTO> findAllUsers() {
        return UserRepository.findAll()
                .stream()
                .map(user -> new UserDTO(user.getUsername(), user.getEmail(), user.getPassword()))
                .toList();
    }

    public void deleteUserByUsername(String username) {
        User user = UserRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        UserRepository.delete(user);
    }

    public void deleteAllUser() {
        UserRepository.deleteAll();
    }

    public void updateUsername(String oldUsername, String username) {
        User user = UserRepository.findByUsername(oldUsername);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (UserRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        user.setUsername(username);
        UserRepository.save(user);
    }

    public void updatePassword(String username, String password) {
        User user = UserRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        user.setPassword(password);
        UserRepository.save(user);
    }

    public void updateEmail(String username, String email) {
        User user = UserRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (UserRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        user.setEmail(email);
        UserRepository.save(user);
    }

    public boolean havePhoto(String username) {
        User user = UserRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user.getPhotoPath() != null;
    }

    public void setPhotoPath(MultipartFile file, String username) {

        User user = UserRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        File directory = new File(PHOTO_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String filePath = PHOTO_DIR + file.getOriginalFilename();
        File dest = new File(filePath);
        //file.transferTo(dest);
        user.setPhotoPath(filePath);
        UserRepository.save(user);

    }


}
