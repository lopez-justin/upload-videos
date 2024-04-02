package com.justinlopez.uploadvideos.domain.service;

import com.justinlopez.uploadvideos.domain.dto.CreatorRequestDTO;
import com.justinlopez.uploadvideos.domain.dto.CreatorResponseDTO;
import com.justinlopez.uploadvideos.domain.usecase.ICloudinaryService;
import com.justinlopez.uploadvideos.domain.usecase.ICreatorUseCase;
import com.justinlopez.uploadvideos.exception.CreatorNotExistException;
import com.justinlopez.uploadvideos.exception.ImageUploadFailedException;
import com.justinlopez.uploadvideos.persistence.entity.CreatorEntity;
import com.justinlopez.uploadvideos.persistence.mapper.ICreatorMapper;
import com.justinlopez.uploadvideos.persistence.repository.ICreatorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CreatorService implements ICreatorUseCase {

    private final ICreatorJpaRepository iCreatorJpaRepository;

    private final ICreatorMapper iCreatorMapper;

    private final ICloudinaryService iCloudinaryService;

    @Override
    public CreatorResponseDTO createCreator(CreatorRequestDTO creatorRequestDTO) {
        // Convert RequestDTO to Entity
        CreatorEntity creatorEntity = iCreatorMapper.toCreatorEntity(creatorRequestDTO);
        // Save Entity and return converted Entity to ResponseDTO
        return iCreatorMapper.toCreatorResponseDTO(iCreatorJpaRepository.save(creatorEntity));
    }

    @Override
    public CreatorResponseDTO uploadCreatorImage(Integer creatorId, MultipartFile file) {
        CreatorEntity creator = iCreatorJpaRepository.findById(creatorId)
                .orElseThrow(() -> new CreatorNotExistException(creatorId.toString()));

        Map result;
        String imageUrl;

        try {
            result = iCloudinaryService.upload(file);
            imageUrl = (String) result.get("url");
        } catch (IOException e) {
            throw new ImageUploadFailedException("Failed to upload image");
        }

        creator.setImage(imageUrl);

        return iCreatorMapper.toCreatorResponseDTO(iCreatorJpaRepository.save(creator));
    }

    @Override
    public CreatorResponseDTO getCreatorById(Integer id) {
        CreatorEntity creatorEntity = iCreatorJpaRepository.findById(id)
                .orElseThrow(() -> new CreatorNotExistException(id.toString()));
        return iCreatorMapper.toCreatorResponseDTO(creatorEntity);
    }

    @Override
    public boolean deleteCreator(Integer id) {
        if (iCreatorJpaRepository.findById(id).isPresent()) {
            iCreatorJpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<CreatorResponseDTO> getAllCreators() {
        return iCreatorMapper.toCreatorResponseDTOList(iCreatorJpaRepository.findAll());
    }

    @Override
    public void switchFollow(Integer followerId, Integer followedId) {
        CreatorEntity follower = iCreatorJpaRepository.findById(followerId)
                .orElseThrow(() -> new CreatorNotExistException(followerId.toString()));
        CreatorEntity followed = iCreatorJpaRepository.findById(followedId)
                .orElseThrow(() -> new CreatorNotExistException(followedId.toString()));

        Set<Integer> follows = follower.getFollows();
        boolean isFollowing = follows.contains(followedId);

        if (isFollowing) {
            follows.remove(followedId);
        } else {
            follows.add(followedId);
        }
        follower.setFollows(follows);

        iCreatorJpaRepository.save(follower);
    }



}
