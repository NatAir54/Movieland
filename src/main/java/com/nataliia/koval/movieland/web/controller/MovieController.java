package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.service.MovieService;
import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> findAll(
            @RequestParam(required = false) String ratingOrder,
            @RequestParam(required = false) String priceOrder
    ) {
        return movieService.findAll(ratingOrder, priceOrder);
    }

    @GetMapping("/random")
    public List<MovieDto> findThreeRandom() {
        return movieService.findThreeRandom();
    }

    @GetMapping("/genre/{genreId}")
    public List<MovieDto> findByGenreId(@PathVariable int genreId) {
        return movieService.findByGenre(genreId);
    }


    @GetMapping("/{movieId}")
    public MovieDto findById(
            @PathVariable int movieId,
            @RequestParam(required = false, defaultValue = "UAH") String currency
    ) {
        CurrencySupported.validate(currency);

        CurrencySupported requestedCurrency = CurrencySupported.valueOf(currency.toUpperCase());
        return movieService.findById(movieId, requestedCurrency);
    }
}
