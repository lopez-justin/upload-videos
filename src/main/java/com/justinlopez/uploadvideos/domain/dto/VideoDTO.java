package com.justinlopez.uploadvideos.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class VideoDTO {

    private Integer id;

    private String url;

    private String title;

    private LocalDate date;

    private Boolean published;

    private String cloudinaryId;

    CreatorResponseDTO creator;

}