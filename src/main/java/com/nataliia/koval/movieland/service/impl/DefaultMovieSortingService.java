package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.exception.InvalidSortingException;
import com.nataliia.koval.movieland.repository.MovieCustomRepository;
import com.nataliia.koval.movieland.repository.MovieBaseRepository;
import com.nataliia.koval.movieland.service.MovieSortingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultMovieSortingService implements MovieSortingService {

    private final MovieBaseRepository movieBaseRepository;
    private final MovieCustomRepository movieCustomRepository;


    @Override
    public List<Movie> findAllSortByPriceOrRating(String ratingOrder, String priceOrder) {
        return ratingOrder == null ?
                sortBy("price", priceOrder) :
                sortBy("rating", ratingOrder);
    }

    @Override
    public List<Movie> findByGenreSortByPriceOrRating(int genreId, String ratingOrder, String priceOrder) {
        boolean sortByRating = (ratingOrder != null);
        String order = sortByRating ? ratingOrder : priceOrder;

        return switch (order.toLowerCase()) {
            case "asc" -> movieCustomRepository.findByGenreAndSort(genreId, sortByRating ? "rating" : "price", Sort.Direction.ASC);
            case "desc" -> movieCustomRepository.findByGenreAndSort(genreId, sortByRating ? "rating" : "price", Sort.Direction.DESC);
            default -> throw new InvalidSortingException(order);
        };
    }

    private List<Movie> sortBy(String field, String order) {
        Sort sort = switch (order.toLowerCase()) {
            case "asc" -> Sort.by(field).ascending();
            case "desc" -> Sort.by(field).descending();
            default -> throw new InvalidSortingException(order);
        };
        return movieBaseRepository.findAll(sort);
    }
}
