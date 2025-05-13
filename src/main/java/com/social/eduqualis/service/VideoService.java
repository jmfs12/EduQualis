package com.social.eduqualis.service;

import com.social.eduqualis.dtos.VideoDTO;
import com.social.eduqualis.entity.User;
import com.social.eduqualis.entity.Video;
import com.social.eduqualis.exceptions.ObjectNotFoundException;
import com.social.eduqualis.exceptions.FailToSaveVideoException;
import com.social.eduqualis.repository.UserRepository;
import com.social.eduqualis.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Value("${eduqualis.video.dir}")
    private String VIDEO_DIR;

    @Autowired
    public VideoService(VideoRepository videoRepository, UserRepository userRepository) {
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

    public void addVideo(VideoDTO videoDTO, MultipartFile file)  {
        User user = userRepository.findByUsername(videoDTO.getUser().getUsername());
        if (user == null) {
            throw new ObjectNotFoundException("User not found");
        }

        try {
            File directory = new File(VIDEO_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = VIDEO_DIR + file.getOriginalFilename();
            File dest = new File(filePath);
            file.transferTo(dest);

            videoDTO.setVideoPath(filePath);

            Video video = new Video(videoDTO);
            video.setUser(user);
            video.setVideoName(file.getOriginalFilename());
            videoRepository.save(video);
        } catch (IOException e){
            throw new FailToSaveVideoException("Error saving video file");
        }
    }

    public Resource getVideo(String videoName) {
        try {
            Video video = videoRepository.findByVideoName(videoName);
            if (video == null) {
                throw new ObjectNotFoundException("Video not found");
            }

            Path path = Path.of(video.getVideoPath());
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists()) {
                throw new ObjectNotFoundException("Video not found");
            }

            return resource;
        } catch (IOException e) {
            throw new ObjectNotFoundException("Error retrieving video file");
        }
    }

}
