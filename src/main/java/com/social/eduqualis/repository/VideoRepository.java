package com.social.eduqualis.repository;

import com.social.eduqualis.entity.User;
import com.social.eduqualis.entity.Video;
import com.social.eduqualis.entity.enums.VideoCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findByVideoPath(String videoPath);

    Video findByUser(User user);

    Video findByCategory(VideoCategory category);

    Video findByVideoName(String videoName);

    List<Video> findAllByUser(User user);
}
