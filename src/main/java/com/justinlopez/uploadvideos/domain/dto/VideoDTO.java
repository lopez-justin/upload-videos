package com.justinlopez.uploadvideos.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class VideoDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String url;

    @NotNull
    @Size(max = 255)
    private String title;

    private LocalDate date;

    private Boolean published;

    CreatorResponseDTO creator;

}