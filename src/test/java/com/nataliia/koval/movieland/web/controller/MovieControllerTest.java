package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieControllerTest {
    @Mock
    private MovieService movieService;
    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Unit test for findAll() method")
    void findAll_ReturnsListOfMovies() {

        List<MovieDto> movies = createMovieDtos();

        when(movieService.findAll()).thenReturn(movies);

        List<MovieDto> result = movieController.findAll();

        verify(movieService).findAll();
        assertEquals(movies, result);
    }

    @Test
    @DisplayName("Unit test for findThreeRandom() method")
    void findThreeRandom_ReturnsListOfThreeMovies() {

        List<MovieDto> movies = createMovieDtos();

        when(movieService.findThreeRandom()).thenReturn(movies.subList(0, 3));

        List<MovieDto> result = movieController.findThreeRandom();

        verify(movieService).findThreeRandom();
        assertEquals(3, result.size());
    }

    private List<MovieDto> createMovieDtos() {
        List<MovieDto> movies = new ArrayList<>();
        movies.add(new MovieDto(1, "Побег из Шоушенка", "The Shawshank Redemption", "1994", 8.9, 123.45, "https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg"));
        movies.add(new MovieDto(2, "Зеленая миля", "The Green Mile", "1999", 8.9, 134.67, "https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1._SY209_CR0,0,140,209_.jpg"));
        movies.add(new MovieDto(3, "Форрест Гамп", "Forrest Gump", "1994", 8.6, 200.6, "https://images-na.ssl-images-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR2,0,140,209_.jpg"));
        movies.add(new MovieDto(4, "Список Шиндлера", "Schindler's List", "1993", 8.7, 150.5, "https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg"));
        return movies;
    }
}
