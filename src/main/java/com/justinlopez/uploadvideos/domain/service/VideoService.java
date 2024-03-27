package com.justinlopez.uploadvideos.domain.service;

import com.justinlopez.uploadvideos.domain.dto.VideoDTO;
import com.justinlopez.uploadvideos.domain.usecase.IVideoUseCase;
import com.justinlopez.uploadvideos.exception.CreatorNotExistException;
import com.justinlopez.uploadvideos.exception.VideoNotExistException;
import com.justinlopez.uploadvideos.persistence.entity.CreatorEntity;
import com.justinlopez.uploadvideos.persistence.entity.VideoEntity;
import com.justinlopez.uploadvideos.persistence.mapper.ICreatorMapper;
import com.justinlopez.uploadvideos.persistence.mapper.IVideoMapper;
import com.justinlopez.uploadvideos.persistence.repository.ICreatorJpaRepository;
import com.justinlopez.uploadvideos.persistence.repository.IVideoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VideoService implements IVideoUseCase {

    private final IVideoJpaRepository iVideoJpaRepository;
    private final ICreatorJpaRepository iCreatorJpaRepository;
    private final IVideoMapper iVideoMapper;
    private final ICreatorMapper iCreatorMapper;

    @Override
    public VideoDTO createVideo(VideoDTO videoDTO, Integer creatorId) {
        CreatorEntity creator = iCreatorJpaRepository
                .findById(creatorId)
                .orElseThrow(() -> new CreatorNotExistException(creatorId.toString()));
        // Todo: Add cloudinary service to upload video
        videoDTO.setDate(LocalDate.now());
        videoDTO.setPublished(false);
        videoDTO.setCreator(iCreatorMapper.toCreatorResponseDTO(creator));
        VideoEntity saved = iVideoJpaRepository.save(iVideoMapper.toVideoEntity(videoDTO));

        return iVideoMapper.toVideoDto(saved);
    }

    @Override
    public VideoDTO getVideoById(Integer id) {
        return iVideoMapper.toVideoDto(iVideoJpaRepository
                .findById(id)
                .orElseThrow(() -> new VideoNotExistException(id.toString())));
    }

    @Override
    public boolean deleteVideo(Integer id) {
        if (iVideoJpaRepository.findById(id).isPresent()) {
            iVideoJpaRepository.deleteById(id);
            return true;
        }
        return false;
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
