package com.nataliia.koval.movieland.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.exception.GenreNotFoundException;
import com.nataliia.koval.movieland.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@WebMvcTest(MovieController.class)
class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Find all movies - should return list of all movies.")
    void findAll_ReturnsListOfMovies() throws Exception {
        List<MovieDto> movies = createMovieDtos();
        when(movieService.findAll(null, null)).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.size()").value(movies.size()));
    }

    @Test
    @DisplayName("Find three random movies - should return list of three random movies.")
    void findThreeRandom_ReturnsListOfThreeRandomMovies() throws Exception {
        List<MovieDto> movies = createMovieDtos();
        when(movieService.findThreeRandom()).thenReturn(movies.subList(0, 3));

        mockMvc.perform(get("/api/v1/movies/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    @DisplayName("Find movies by genre - should return list of movies by genre with valid id.")
    void findByGenre_ReturnsListOfMoviesByGenre() throws Exception {
        int genreId = 1;
        List<MovieDto> movies = createMovieDtos();
        when(movieService.findByGenre(genreId)).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies/genre/{genreId}", genreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(movies.size()));
    }

    @Test
    @DisplayName("Find movies by genre - should return error message with invalid genre id.")
    void findByGenre_ReturnsErrorMessageWithInvalidGenreId() throws Exception {
        int invalidGenreId = -1;
        String errorMessage = "Invalid genre ID: " + invalidGenreId + ". Genre ID should be a positive number.";
        when(movieService.findByGenre(invalidGenreId)).thenThrow(new GenreNotFoundException(invalidGenreId));

        mockMvc.perform(get("/api/v1/movies/genre/{genreId}", invalidGenreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }

    @Test
    @DisplayName("Find movies by genre - should return error message when genre ID does not exist.")
    void findByGenre_ReturnsErrorMessageWhenGenreIdNotExists() throws Exception {
        int nonExistingGenreId = 1000;
        String errorMessage = "Genre with specified id " + nonExistingGenreId + " not found. Check the request details.";
        when(movieService.findByGenre(nonExistingGenreId)).thenThrow(new GenreNotFoundException(nonExistingGenreId));

        mockMvc.perform(get("/api/v1/movies/genre/{genreId}", nonExistingGenreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
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
