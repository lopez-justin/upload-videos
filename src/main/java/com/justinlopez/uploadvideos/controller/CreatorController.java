package com.justinlopez.uploadvideos.controller;

import com.justinlopez.uploadvideos.domain.dto.CreatorRequestDTO;
import com.justinlopez.uploadvideos.domain.dto.CreatorResponseDTO;
import com.justinlopez.uploadvideos.domain.usecase.ICreatorUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/creators")
public class CreatorController {

    private final ICreatorUseCase iCreatorUseCase;

    // Create
    @PostMapping
    public ResponseEntity<CreatorResponseDTO> createCreator(@Valid @RequestBody CreatorRequestDTO creatorRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iCreatorUseCase.createCreator(creatorRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<CreatorResponseDTO>> getAllCreators() {
        return ResponseEntity.ok(iCreatorUseCase.getAllCreators());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreatorResponseDTO> getCreatorById(@PathVariable Integer id) {
        return ResponseEntity.ok(iCreatorUseCase.getCreatorById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCreator(@PathVariable Integer id) {
        if (iCreatorUseCase.deleteCreator(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{creatorId}/follow/{followedId}")
    public ResponseEntity<Void> switchFollow(@PathVariable Integer creatorId, @PathVariable Integer followedId) {
        iCreatorUseCase.switchFollow(creatorId, followedId);
        return ResponseEntity.noContent().build();
    }


}
