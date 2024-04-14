package com.nataliia.koval.movieland.mapper;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface MovieMapper {
    @Mapping(target = "countries", source = "countries")
    @Mapping(target = "genres", source = "genres")
    @Mapping(target = "reviews", source = "reviews")
    MovieDto toDto(Movie movie);

    List<MovieDto> toDtoList(List<Movie> movieList);
}
