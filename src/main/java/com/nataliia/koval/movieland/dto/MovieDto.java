package com.nataliia.koval.movieland.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MovieDto {
    private int id;
    private String nameRussian;
    private String nameNative;
    private String yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;
    private Set<CountryDto> countries = new HashSet<>();
    private Set<GenreDto> genres =  new HashSet<>();
    private Set<ReviewDto> reviews =  new HashSet<>();
}
