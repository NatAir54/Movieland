package com.nataliia.koval.movieland.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


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
}
