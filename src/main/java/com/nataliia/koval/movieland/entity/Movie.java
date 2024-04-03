package com.nataliia.koval.movieland.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name="movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sequence")
    @SequenceGenerator(name = "movie_sequence", sequenceName = "movie_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "name_russian")
    private String nameRussian;
    @Column(name = "name_native")
    private String nameNative;
    @Column(name = "year_of_release")
    private String yearOfRelease;
    @Column(name = "rating")
    private double rating;
    @Column(name = "price")
    private double price;
    @Column(name= "picture_path")
    private String picturePath;

    @ManyToMany
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;
}
