package com.justinlopez.uploadvideos.domain.usecase;

import com.justinlopez.uploadvideos.domain.dto.CreatorRequestDTO;
import com.justinlopez.uploadvideos.domain.dto.CreatorResponseDTO;

import java.util.List;

public interface ICreatorUseCase {

    CreatorResponseDTO createCreator(CreatorRequestDTO creatorRequestDTO);

    CreatorResponseDTO getCreatorById(Integer id);

    boolean deleteCreator(Integer id);

    List<CreatorResponseDTO> getAllCreators();

    void switchFollow(Integer followerId, Integer followedId); // Added method to switch follow status

}
