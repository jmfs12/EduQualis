package com.social.eduqualis.controller;

import com.social.eduqualis.dtos.VideoDTO;
import com.social.eduqualis.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addVideo(@RequestPart VideoDTO videoDTO, @RequestPart MultipartFile file) {
        videoService.addVideo(videoDTO, file);
    }

    @GetMapping(value = "/{videoName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getVideo(@PathVariable String videoName) {
        Resource video = videoService.getVideo(videoName);
        return ResponseEntity.ok()
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(video);
    }
}
