package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Genre;
import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.exception.GenreNotFoundException;
import com.nataliia.koval.movieland.exception.InvalidSortingException;
import com.nataliia.koval.movieland.mapper.MovieMapper;
import com.nataliia.koval.movieland.repository.GenreRepository;
import com.nataliia.koval.movieland.repository.MovieRepository;
import com.nataliia.koval.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public List<MovieDto> findByGenre(int genreId) throws GenreNotFoundException {
        Optional<Genre> optionalGenre = genreRepository.findById(genreId);
        if (optionalGenre.isPresent()) {
            return mapMoviesToDto(movieRepository.findByGenre(genreId));
        }
        throw new GenreNotFoundException(genreId);
    }

    private List<MovieDto> findAllSorted(String ratingOrder, String priceOrder) {
        List<Movie> sortedMovies = ratingOrder != null ? getSortedByRating(ratingOrder) : getSortedByPrice(priceOrder);
        return mapMoviesToDto(sortedMovies);
    }

    private List<Movie> getSortedByRating(String ratingOrder) {
        if (isValidSortingOrder(ratingOrder)) {
            return ratingOrder.equalsIgnoreCase("asc") ?
                    movieRepository.findAllSortedByRatingAsc() :
                    movieRepository.findAllSortedByRatingDesc();
        }
        throw new InvalidSortingException(ratingOrder);
    }

    private List<Movie> getSortedByPrice(String priceOrder) {
        if (isValidSortingOrder(priceOrder)) {
            return priceOrder.equalsIgnoreCase("asc") ?
                    movieRepository.findAllSortedByPriceAsc() :
                    movieRepository.findAllSortedByPriceDesc();
        }
        throw new InvalidSortingException(priceOrder);
    }

    private boolean isValidSortingOrder(String order) {
        return order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc");
    }

    private List<MovieDto> mapMoviesToDto(List<Movie> movies) {
        return movies.stream()
                .map(movieMapper::toMovieDto)
                .collect(Collectors.toList());
    }
}
