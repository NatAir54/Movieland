package com.nataliia.koval.movieland.mapper;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Country;
import com.nataliia.koval.movieland.entity.Genre;
import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto toDto(Movie movie);

    @Mapping(target = "genres", source = "genres")
    @Mapping(target = "countries", source = "countries")
    @Mapping(target = "reviews", source = "reviews")
    MovieDto toDto(Movie movie, Set<Genre> genres, Set<Country> countries, Set<Review> reviews);


    List<MovieDto> toDtoList(List<Movie> movieList);
}
