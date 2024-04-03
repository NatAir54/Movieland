package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/random")
    public List<MovieDto> findThreeRandom() {
        return movieService.findThreeRandom();
    }

    @GetMapping("/genre/{genreId}")
    public List<MovieDto> findByGenre(@PathVariable("genreId") int genreId) {
        return movieService.findByGenre(genreId);
    }
}
