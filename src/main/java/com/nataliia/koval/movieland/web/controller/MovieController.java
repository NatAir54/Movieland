package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> findAll(
            @RequestParam(required = false) String ratingOrder,
            @RequestParam(required = false) String priceOrder) {

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
