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
    public List<MovieDto> findByGenre(int genreId) {
        return mapMoviesToDto(
                genreRepository.findById(genreId)
                        .map(genre -> movieRepository.findByGenre(genre.getId()))
                        .orElseThrow(() -> new GenreNotFoundException(genreId))
        );
    }

    @Override
    public MovieDto findById(int movieId) {
        return movieRepository.findById(movieId)
                .map(movieMapper::toMovieDto)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }

    private List<MovieDto> findAllSorted(String ratingOrder, String priceOrder) {
        List<Movie> sortedMovies = ratingOrder != null ?
                movieRepository.findAllSortedByRating(ratingOrder) : movieRepository.findAllSortedByPrice(priceOrder);
        return mapMoviesToDto(sortedMovies);
    }

    private List<MovieDto> mapMoviesToDto(List<Movie> movies) {
        return movieMapper.toMovieDtoList((movies));
    }
}