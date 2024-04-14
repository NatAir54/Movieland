package com.nataliia.koval.movieland.mapper;

import com.nataliia.koval.movieland.service.cache.ImmutableGenre;
import com.nataliia.koval.movieland.dto.GenreDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    List<GenreDto> toDtoList(List<ImmutableGenre> genreList);
}
