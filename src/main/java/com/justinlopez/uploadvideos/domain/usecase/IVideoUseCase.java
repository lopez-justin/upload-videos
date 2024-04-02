package com.justinlopez.uploadvideos.domain.usecase;

import com.justinlopez.uploadvideos.domain.dto.VideoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface IVideoUseCase {

    VideoDTO createVideo(VideoDTO videoDTO, Integer creatorId);

    // Upload video
    VideoDTO uploadVideo(MultipartFile file, Integer videoId);

    VideoDTO getVideoById(Integer id);

    boolean deleteVideo(Integer id);

    Set<VideoDTO> getAllPublishedVideos();

    Set<VideoDTO> getAllVideosByCreatorId(Integer creatorId);

    void publishVideo(Integer videoId);

}
