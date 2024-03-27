package com.justinlopez.uploadvideos.domain.dto;

import lombok.*;

import java.util.Set;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class CreatorResponseDTO {

    private Integer id;

    private String name;

    private String email;

    private String image;

    private Set<Integer> follows;

    private Set<Integer> likedVideos;


}
