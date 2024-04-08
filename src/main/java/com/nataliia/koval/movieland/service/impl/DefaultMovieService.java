package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.exception.GenreNotFoundException;
import com.nataliia.koval.movieland.exception.MovieNotFoundException;
import com.nataliia.koval.movieland.mapper.MovieMapper;
import com.nataliia.koval.movieland.repository.GenreRepository;
import com.nataliia.koval.movieland.repository.MovieRepository;
import com.nataliia.koval.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultMovieService implements MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDto> findAll(String ratingOrder, String priceOrder) {
        if (ratingOrder == null && priceOrder == null) {
            return mapMoviesToDto(movieRepository.findAll());
        }
        return findAllSorted(ratingOrder, priceOrder);
    }

    @Override
    public List<MovieDto> findThreeRandom() {
        return mapMoviesToDto(movieRepository.findThreeRandom());
    }

    @Override
    public List<MovieDto> findByGenre(String genreId) throws GenreNotFoundException {
        int parsedGenreId = parseId(genreId, GenreNotFoundException.class);
        return mapMoviesToDto(
                genreRepository.findById(parsedGenreId)
                        .map(genre -> movieRepository.findByGenre(genre.getId()))
                        .orElseThrow(() -> new GenreNotFoundException(parsedGenreId))
        );
    }

    @Override
    public MovieDto findById(String movieId) {
        int parsedMovieId = parseId(movieId, MovieNotFoundException.class);
        return movieRepository.findById(parsedMovieId)
                .map(movieMapper::toMovieDto)
                .orElseThrow(() -> new MovieNotFoundException(parsedMovieId));
    }

    private List<MovieDto> findAllSorted(String ratingOrder, String priceOrder) {
        List<Movie> sortedMovies = ratingOrder != null ?
                movieRepository.findAllSortedByRating(ratingOrder) : movieRepository.findAllSortedByPrice(priceOrder);
        return mapMoviesToDto(sortedMovies);
    }

    private List<MovieDto> mapMoviesToDto(List<Movie> movies) {
        return movies.stream()
                .map(movieMapper::toMovieDto)
                .collect(Collectors.toList());
    }

    private int parseId(String id, Class<? extends RuntimeException> exceptionClass) throws RuntimeException {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw instantiateException(exceptionClass, id);
        }
    }

    private RuntimeException instantiateException(Class<? extends RuntimeException> exceptionClass, String id) {
        try {
            return exceptionClass.getDeclaredConstructor(String.class).newInstance(id);
        } catch (Exception ex) {
            return new RuntimeException("Error instantiating exception", ex);
        }
    }
}