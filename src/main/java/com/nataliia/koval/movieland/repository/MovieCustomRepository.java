package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Movie;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MovieCustomRepository {

    List<Movie> findByGenre(int genreId);

    List<Movie> findByGenreAndSort(int genreId, String sortBy, Sort.Direction direction);
}
