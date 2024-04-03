package com.nataliia.koval.movieland.mapper;

import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDto toGenreDto(Genre genre);
}
