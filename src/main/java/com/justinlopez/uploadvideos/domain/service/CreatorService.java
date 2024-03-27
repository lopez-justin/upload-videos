package com.justinlopez.uploadvideos.domain.service;

import com.justinlopez.uploadvideos.domain.dto.CreatorRequestDTO;
import com.justinlopez.uploadvideos.domain.dto.CreatorResponseDTO;
import com.justinlopez.uploadvideos.domain.usecase.ICreatorUseCase;
import com.justinlopez.uploadvideos.exception.CreatorNotExistException;
import com.justinlopez.uploadvideos.persistence.entity.CreatorEntity;
import com.justinlopez.uploadvideos.persistence.mapper.ICreatorMapper;
import com.justinlopez.uploadvideos.persistence.repository.ICreatorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CreatorService implements ICreatorUseCase {

    private final ICreatorJpaRepository iCreatorJpaRepository;

    private final ICreatorMapper iCreatorMapper;

    @Override
    public CreatorResponseDTO createCreator(CreatorRequestDTO creatorRequestDTO) {
        // Convert RequestDTO to Entity
        CreatorEntity creatorEntity = iCreatorMapper.toCreatorEntity(creatorRequestDTO);
        // Save Entity and return converted Entity to ResponseDTO
        return iCreatorMapper.toCreatorResponseDTO(iCreatorJpaRepository.save(creatorEntity));
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
