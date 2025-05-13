package com.social.eduqualis.dtos;

import com.social.eduqualis.entity.enums.VideoCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO {

    private UserDTO user;
    private VideoCategory category;
    private String description;
    private String videoPath;
    private LocalDateTime createdAt;
    private String videoName;

    public VideoDTO(UserDTO user, VideoCategory category, String description) {
        this.user = user;
        this.category = category;
        this.description = description;
        this.createdAt = LocalDateTime.now();

    }

}
