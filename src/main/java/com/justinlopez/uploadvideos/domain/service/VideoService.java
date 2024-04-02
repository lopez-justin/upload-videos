package com.justinlopez.uploadvideos.domain.service;

import com.justinlopez.uploadvideos.domain.dto.VideoDTO;
import com.justinlopez.uploadvideos.domain.usecase.ICloudinaryService;
import com.justinlopez.uploadvideos.domain.usecase.IVideoUseCase;
import com.justinlopez.uploadvideos.exception.CreatorNotExistException;
import com.justinlopez.uploadvideos.exception.VideoNotExistException;
import com.justinlopez.uploadvideos.exception.VideoUploadFailedException;
import com.justinlopez.uploadvideos.persistence.entity.CreatorEntity;
import com.justinlopez.uploadvideos.persistence.entity.VideoEntity;
import com.justinlopez.uploadvideos.persistence.mapper.ICreatorMapper;
import com.justinlopez.uploadvideos.persistence.mapper.IVideoMapper;
import com.justinlopez.uploadvideos.persistence.repository.ICreatorJpaRepository;
import com.justinlopez.uploadvideos.persistence.repository.IVideoJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoService implements IVideoUseCase {

    private final IVideoJpaRepository iVideoJpaRepository;
    private final ICreatorJpaRepository iCreatorJpaRepository;
    private final IVideoMapper iVideoMapper;
    private final ICreatorMapper iCreatorMapper;
    private final ICloudinaryService iCloudinaryService;

    @Override
    public VideoDTO createVideo(VideoDTO videoDTO, Integer creatorId) {
        CreatorEntity creator = iCreatorJpaRepository
                .findById(creatorId)
                .orElseThrow(() -> new CreatorNotExistException(creatorId.toString()));

        videoDTO.setDate(LocalDate.now());
        videoDTO.setPublished(false);
        videoDTO.setCreator(iCreatorMapper.toCreatorResponseDTO(creator));
        VideoEntity saved = iVideoJpaRepository.save(iVideoMapper.toVideoEntity(videoDTO));
        log.error("Video saved: {}", saved);
        return iVideoMapper.toVideoDto(saved);
    }

    @Override
    public VideoDTO uploadVideo(MultipartFile file, Integer videoId) {
        VideoEntity video = iVideoJpaRepository
                .findById(videoId)
                .orElseThrow(() -> new VideoNotExistException(videoId.toString()));

        Map uploadResult = null;
        String videoUrl = null;
        try {
            uploadResult = iCloudinaryService.uploadVideo(file);
            videoUrl = (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new VideoUploadFailedException("Failed to upload video");
        }

        video.setCloudinaryId((String) uploadResult.get("public_id"));
        video.setUrl(videoUrl);
        VideoEntity saved = iVideoJpaRepository.save(video);

        return iVideoMapper.toVideoDto(saved);
    }

    @Override
    public VideoDTO getVideoById(Integer id) {
        return iVideoMapper.toVideoDto(iVideoJpaRepository
                .findById(id)
                .orElseThrow(() -> new VideoNotExistException(id.toString())));
    }

    // delete video in cloudinary
    @Override
    public boolean deleteVideo(Integer id) {
        VideoEntity video = iVideoJpaRepository
                .findById(id)
                .orElseThrow(() -> new VideoNotExistException(id.toString()));

        try {
            String publicId = video.getCloudinaryId();

            Map result = iCloudinaryService.delete(publicId);
            log.error("Video deleted: {}", result);
            iVideoJpaRepository.delete(video);
            return true;
        } catch (IOException e) {
            throw new VideoUploadFailedException("Failed to delete video");
        }
    }

    @Override
    public Set<VideoDTO> getAllPublishedVideos() {
        return iVideoMapper.toVideoDtoList(iVideoJpaRepository.findAllByPublished(true));
    }

    @Override
    public Set<VideoDTO> getAllVideosByCreatorId(Integer creatorId) {
        return iVideoMapper.toVideoDtoList(iVideoJpaRepository.findAllByCreatorId(creatorId));
    }

    @Override
    public void publishVideo(Integer videoId) {
        VideoEntity video = iVideoJpaRepository
                .findById(videoId)
                .orElseThrow(() -> new VideoNotExistException(videoId.toString()));
        video.setPublished(true);
        iVideoJpaRepository.save(video);
    }

}
