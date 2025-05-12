package com.social.eduqualis.entity;

import com.social.eduqualis.dtos.VideoDTO;
import com.social.eduqualis.entity.enums.VideoCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Video {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "category")
    private VideoCategory category;

    @Column(name = "description")
    private String description;
    @Column(name = "video_path")
    private String videoPath;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "video_name")
    private String videoName;

    public Video(VideoDTO videoDTO){

        this.category = videoDTO.getCategory();
        this.description = videoDTO.getDescription();
        this.videoPath = videoDTO.getVideoPath();
        this.createdAt = LocalDateTime.now();

    }

}
