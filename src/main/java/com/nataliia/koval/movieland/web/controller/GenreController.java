package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<GenreDto> findAll() {
        return genreService.findAll();
    }
}
