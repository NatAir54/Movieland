package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.service.MovieService;
import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;
import com.nataliia.koval.movieland.web.controller.entity.MovieAddRequest;
import com.nataliia.koval.movieland.web.controller.entity.MovieEditRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @RequestParam(required = false) String priceOrder
    ) {
        return movieService.findAll(ratingOrder, priceOrder);
    }


    @GetMapping("/random")
    public List<MovieDto> findThreeRandom() {
        return movieService.findThreeRandom();
    }


    @GetMapping("/genre/{genreId}")
    public List<MovieDto> findByGenreId(
            @PathVariable int genreId,
            @RequestParam(required = false) String ratingOrder,
            @RequestParam(required = false) String priceOrder) {

        return movieService.findByGenre(genreId, ratingOrder, priceOrder);
    }


    @GetMapping("/{movieId}")
    public MovieDto findById(
            @PathVariable int movieId,
            @RequestParam(required = false, defaultValue = "UAH") String currency
    ) {
        CurrencySupported requestedCurrency = CurrencySupported.parseCurrency(currency.toUpperCase());
        return movieService.findById(movieId, requestedCurrency);
    }

    @PostMapping
    public ResponseEntity<MovieDto> addMovie(@RequestHeader("Authorization") String authorizationHeader, @RequestBody MovieAddRequest movieRequest) {
        MovieDto savedMovie = movieService.addMovie(movieRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDto> editMovie(@PathVariable int movieId, @RequestBody MovieEditRequest movieEditRequest, @RequestHeader("Authorization") String authorizationHeader) {
        MovieDto updatedMovie = movieService.editMovie(movieId, movieEditRequest);
        return ResponseEntity.ok(updatedMovie);
    }
}
