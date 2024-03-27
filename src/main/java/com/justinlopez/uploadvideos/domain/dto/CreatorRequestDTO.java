package com.justinlopez.uploadvideos.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class CreatorRequestDTO {

    private Integer id;

    @NotBlank // This annotation is used to validate that the field is not null or empty
    @Size(min = 3, max = 50) // This annotation is used to validate the size of the field
    private String name;

    @NotBlank
    @Email
    @Size(min = 3, max = 50)
    private String email;

    @NotBlank
    @Size(min = 8, max = 255)
    private String password;

    private String image;

}
