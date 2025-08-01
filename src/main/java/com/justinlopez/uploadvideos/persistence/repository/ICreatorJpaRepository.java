package com.justinlopez.uploadvideos.persistence.repository;

import com.justinlopez.uploadvideos.persistence.entity.CreatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICreatorJpaRepository extends JpaRepository<CreatorEntity, Integer>{

}
