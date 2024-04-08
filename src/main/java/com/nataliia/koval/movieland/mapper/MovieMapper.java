package com.nataliia.koval.movieland.mapper;


import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    @Mapping(target = "countries", source = "countries")
    @Mapping(target = "genres", source = "genres")
    MovieDto toMovieDto(Movie movie);
}
