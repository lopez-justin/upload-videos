package com.justinlopez.uploadvideos.persistence.mapper;

import com.justinlopez.uploadvideos.domain.dto.CreatorRequestDTO;
import com.justinlopez.uploadvideos.domain.dto.CreatorResponseDTO;
import com.justinlopez.uploadvideos.persistence.entity.CreatorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICreatorMapper {

    CreatorRequestDTO toCreatorRequestDTO(CreatorEntity creatorEntity);

    CreatorResponseDTO toCreatorResponseDTO(CreatorEntity creatorEntity);

    @Mapping(target = "follows", ignore = true)
    @Mapping(target = "likedVideos", ignore = true)
    @Mapping(target = "videos", ignore = true)
    CreatorEntity toCreatorEntity(CreatorRequestDTO creatorRequestDTO);

    List<CreatorResponseDTO> toCreatorResponseDTOList(List<CreatorEntity> creatorEntityList);

}
