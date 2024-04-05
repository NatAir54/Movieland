package com.nataliia.koval.movieland.mapper;

import com.nataliia.koval.movieland.cache.ImmutableGenre;
import com.nataliia.koval.movieland.dto.GenreDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDto toGenreDto(ImmutableGenre genre);
}
