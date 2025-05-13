package com.social.eduqualis.dtos;

import com.social.eduqualis.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;
    private String password;
    private String email;
    private String photoPath;
    private List<VideoDTO> videos;

    public UserDTO (String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserDTO (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(String username) {
        this.username = username;
    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.photoPath = user.getPhotoPath();
    }


}
