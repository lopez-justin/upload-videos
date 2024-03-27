package com.justinlopez.uploadvideos.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "creators")
public class CreatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_creator")
    private Integer id;

    private String name;

    private String email;

    private String password;

    private String image;

    @ElementCollection
    @CollectionTable(name = "creator_follows", joinColumns = @JoinColumn(name = "id_creator"))
    private Set<Integer> follows;

    @ElementCollection
    @CollectionTable(name = "creator_liked_videos", joinColumns = @JoinColumn(name = "id_creator"))
    private Set<Integer> likedVideos;

}
