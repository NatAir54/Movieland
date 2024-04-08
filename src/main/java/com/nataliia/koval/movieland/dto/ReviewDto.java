package com.nataliia.koval.movieland.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewDto {
    private Integer id;
    private UserDto user;
    private String text;
}
