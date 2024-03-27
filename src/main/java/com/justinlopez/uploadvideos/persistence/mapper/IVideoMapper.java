package com.justinlopez.uploadvideos.persistence.mapper;

import com.justinlopez.uploadvideos.domain.dto.VideoDTO;
import com.justinlopez.uploadvideos.persistence.entity.VideoEntity;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ICreatorMapper.class})
public interface IVideoMapper {

    VideoEntity toVideoEntity(VideoDTO videoDTO);

    VideoDTO toVideoDto(VideoEntity videoEntity);

    Set<VideoDTO> toVideoDtoList(Set<VideoEntity> videoEntities);

}