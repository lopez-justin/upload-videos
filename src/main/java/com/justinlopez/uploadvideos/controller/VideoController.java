package com.justinlopez.uploadvideos.controller;

import com.justinlopez.uploadvideos.domain.dto.VideoDTO;
import com.justinlopez.uploadvideos.domain.usecase.IVideoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/videos")
public class VideoController {

    private final IVideoUseCase iVideoUseCase;

    @PostMapping("/creator/{creatorId}")
    public ResponseEntity<VideoDTO> createVideo(
            @RequestBody VideoDTO videoDTO,
            @PathVariable Integer creatorId)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(iVideoUseCase.createVideo(videoDTO, creatorId));
    }

    @PostMapping("/{videoId}/upload-video")
    public ResponseEntity<VideoDTO> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @PathVariable Integer videoId)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(iVideoUseCase.uploadVideo(file, videoId));
    }

    @PutMapping("/{videoId}/publish")
    public ResponseEntity<Void> publishVideo(@PathVariable Integer videoId) {
        iVideoUseCase.publishVideo(videoId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<Iterable<VideoDTO>> getPublishedVideos() {
        return ResponseEntity.status(HttpStatus.OK).body(iVideoUseCase.getAllPublishedVideos());
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoDTO> getVideo(@PathVariable Integer videoId) {
        return ResponseEntity.status(HttpStatus.OK).body(iVideoUseCase.getVideoById(videoId));
    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<Iterable<VideoDTO>> getVideosByCreator(@PathVariable Integer creatorId) {
        return ResponseEntity.status(HttpStatus.OK).body(iVideoUseCase.getAllVideosByCreatorId(creatorId));
    }

    @PutMapping("/{videoId}/like/{creatorId}")
    public ResponseEntity<Void> likeVideo(@PathVariable Integer creatorId, @PathVariable Integer videoId) {
        iVideoUseCase.likeVideo(creatorId, videoId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<Map> deleteVideo(@PathVariable Integer videoId) {
        return ResponseEntity.status(HttpStatus.OK).body(iVideoUseCase.deleteVideo(videoId));
    }


}
