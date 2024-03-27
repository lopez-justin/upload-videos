package com.justinlopez.uploadvideos.persistence.repository;

import com.justinlopez.uploadvideos.persistence.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface IVideoJpaRepository extends JpaRepository<VideoEntity, Integer> {

    Set<VideoEntity> findByPublished(Boolean published);

    Set<VideoEntity> findAllByCreatorId(Integer idCreator);


}