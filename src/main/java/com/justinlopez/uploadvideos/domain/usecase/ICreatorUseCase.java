package com.justinlopez.uploadvideos.domain.usecase;

import com.justinlopez.uploadvideos.domain.dto.CreatorRequestDTO;
import com.justinlopez.uploadvideos.domain.dto.CreatorResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICreatorUseCase {

    CreatorResponseDTO createCreator(CreatorRequestDTO creatorRequestDTO);

    CreatorResponseDTO uploadCreatorImage(Integer creatorId, MultipartFile file);

    CreatorResponseDTO getCreatorById(Integer id);

    boolean deleteCreator(Integer id);

    List<CreatorResponseDTO> getAllCreators();

    void switchFollow(Integer followerId, Integer followedId); // Added method to switch follow status

}
