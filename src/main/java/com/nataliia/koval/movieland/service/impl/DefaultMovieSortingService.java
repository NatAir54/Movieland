package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.exception.InvalidSortingException;
import com.nataliia.koval.movieland.repository.MovieRepository;
import com.nataliia.koval.movieland.service.MovieSortingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultMovieSortingService implements MovieSortingService {

    private final MovieRepository movieRepository;


    @Override
    public List<Movie> findAllSorted(String ratingOrder, String priceOrder) {
        return ratingOrder == null ?
                sortByPrice(priceOrder) :
                sortByRating(ratingOrder);
    }

    @Override
    public List<Movie> findByGenreSorted(int genreId, String ratingOrder, String priceOrder) {
        return ratingOrder == null ?
                sortByPrice(genreId, priceOrder) :
                sortByRating(genreId, ratingOrder);
    }

    private List<Movie> sortByPrice(String priceOrder) {
        Sort sort = switch (priceOrder.toLowerCase()) {
            case "asc" -> Sort.by("price").ascending();
            case "desc" -> Sort.by("price").descending();
            default -> throw new InvalidSortingException(priceOrder);
        };
        return movieRepository.findAll(sort);
    }

    private List<Movie> sortByRating(String ratingOrder) {
        Sort sort = switch (ratingOrder.toLowerCase()) {
            case "asc" -> Sort.by("rating").ascending();
            case "desc" -> Sort.by("rating").descending();
            default -> throw new InvalidSortingException(ratingOrder);
        };
        return movieRepository.findAll(sort);
    }

    private List<Movie> sortByPrice(int genreId, String priceOrder){
        return switch (priceOrder.toLowerCase()) {
            case "asc" -> movieRepository.findByGenreSortByPriceAsc(genreId);
            case "desc" -> movieRepository.findByGenreSortByPriceDesc(genreId);
            default -> throw new InvalidSortingException(priceOrder);
        };
    }

    private List<Movie> sortByRating(int genreId, String ratingOrder){
        return switch (ratingOrder.toLowerCase()) {
            case "asc" -> movieRepository.findByGenreSortByRatingAsc(genreId);
            case "desc" -> movieRepository.findByGenreSortByRatingDesc(genreId);
            default -> throw new InvalidSortingException(ratingOrder);
        };
    }
}
