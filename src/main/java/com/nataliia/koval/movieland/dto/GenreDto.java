package com.nataliia.koval.movieland.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenreDto {
    private final Integer id;
    private final String name;
}
