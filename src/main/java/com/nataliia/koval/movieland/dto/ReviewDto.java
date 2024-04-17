package com.nataliia.koval.movieland.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Integer id;
    private UserDto user;
    private String text;
}
