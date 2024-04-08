package com.nataliia.koval.movieland.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.nataliia.koval.movieland.dto.GenreDto;
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

import java.util.List;
import java.util.Comparator;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

@AutoConfigureMockMvc
@WebMvcTest(MovieController.class)
class MovieControllerTest {
    private static final String URL = "/api/v1/movies";

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

        mockMvc.perform(get(URL))
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

        mockMvc.perform(get(URL + "/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    @DisplayName("Find movies by genre - should return list of movies by genre with valid id.")
    void findByGenre_ReturnsListOfMoviesByGenre() throws Exception {
        String genreId = "1";
        List<MovieDto> movies = createMovieDtos();

        when(movieService.findByGenre(genreId)).thenReturn(movies);

        mockMvc.perform(get(URL + "/genre/{genreId}", genreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(movies.size()));
    }

    @Test
    @DisplayName("Find movies by genre - should return error message with negative genre id.")
    void findByGenre_ReturnsErrorMessageWithNegativeGenreId() throws Exception {
        String invalidGenreId = "-1";
        int parsedGenreId = Integer.parseInt(invalidGenreId);
        String errorMessage = "Invalid genre ID: " + invalidGenreId + ". Genre ID should be a positive number.";

        when(movieService.findByGenre(invalidGenreId)).thenThrow(new GenreNotFoundException(parsedGenreId));

        mockMvc.perform(get(URL + "/genre/{genreId}", invalidGenreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }

    @Test
    @DisplayName("Find movies by genre - should return error message when genre ID does not exist.")
    void findByGenre_ReturnsErrorMessageWhenGenreIdNotExists() throws Exception {
        String nonExistingGenreId = "1000";
        int parsedGenreId = Integer.parseInt(nonExistingGenreId);
        String errorMessage = "Genre with specified id " + nonExistingGenreId + " not found. Check the request details.";

        when(movieService.findByGenre(nonExistingGenreId)).thenThrow(new GenreNotFoundException(parsedGenreId));

        mockMvc.perform(get(URL + "/genre/{genreId}", nonExistingGenreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }

    @Test
    @DisplayName("Find movies by genre - should return error message with non-integer genre id.")
    void findByGenre_ReturnsErrorMessageWithNonIntegerGenreId() throws Exception {
        String nonIntegerGenreId = "abc";
        String errorMessage = "Invalid genre ID: " + nonIntegerGenreId + ". Genre ID should be a positive number.";

        when(movieService.findByGenre(nonIntegerGenreId)).thenThrow(new GenreNotFoundException(nonIntegerGenreId));

        mockMvc.perform(get(URL + "/genre/{genreId}", nonIntegerGenreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }


    @Test
    @DisplayName("Find all movies - should return list of all movies sorted by rating in descending order.")
    void findAll_SortedByRatingDescending() throws Exception {
        List<MovieDto> movies = createMovieDtos();
        movies.sort(Comparator.comparing(MovieDto::getRating).reversed());

        when(movieService.findAll("desc", null)).thenReturn(movies);

        mockMvc.perform(get(URL + "?ratingOrder=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(movies.size()));

        for (int i = 0; i < movies.size(); i++) {
            mockMvc.perform(get(URL + "?ratingOrder=desc"))
                    .andExpect(jsonPath("$[" + i + "].rating").value(movies.get(i).getRating()));
        }
    }

    @Test
    @DisplayName("Find all movies - should return list of all movies sorted by rating in ascending order.")
    void findAll_SortedByRatingAscending() throws Exception {
        List<MovieDto> movies = createMovieDtos();
        movies.sort(Comparator.comparing(MovieDto::getRating));

        when(movieService.findAll("asc", null)).thenReturn(movies);

        mockMvc.perform(get(URL + "?ratingOrder=asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(movies.size()));

        for (int i = 0; i < movies.size(); i++) {
            mockMvc.perform(get(URL + "?ratingOrder=asc"))
                    .andExpect(jsonPath("$[" + i + "].rating").value(movies.get(i).getRating()));
        }
    }

    @Test
    @DisplayName("Find all movies - should return list of all movies sorted by price in descending order.")
    void findAll_SortedByPriceDescending() throws Exception {
        List<MovieDto> movies = createMovieDtos();
        when(movieService.findAll(null, "desc")).thenReturn(movies);

        mockMvc.perform(get(URL + "?priceOrder=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(movies.size()));

        for (int i = 0; i < movies.size(); i++) {
            mockMvc.perform(get(URL + "?priceOrder=desc"))
                    .andExpect(jsonPath("$[" + i + "].rating").value(movies.get(i).getRating()));
        }
    }

    @Test
    @DisplayName("Find all movies - should return list of all movies sorted by price in ascending order.")
    void findAll_SortedByPriceAscending() throws Exception {
        List<MovieDto> movies = createMovieDtos();
        when(movieService.findAll(null, "asc")).thenReturn(movies);

        mockMvc.perform(get(URL + "?priceOrder=asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(movies.size()));

        for (int i = 0; i < movies.size(); i++) {
            mockMvc.perform(get(URL + "?priceOrder=asc"))
                    .andExpect(jsonPath("$[" + i + "].rating").value(movies.get(i).getRating()));
        }
    }

    private List<MovieDto> createMovieDtos() {
        List<MovieDto> movies = new ArrayList<>();

        GenreDto dramaGenre = new GenreDto(1, "драма");
        Set<GenreDto> genres = new HashSet<>();
        genres.add(dramaGenre);

        String description1 = "description1";
        String description2 = "description2";
        String description3 = "description3";
        String description4 = "description4";

        movies.add(new MovieDto(1, "Побег из Шоушенка", "The Shawshank Redemption", "1994", description1, 8.9, 123.45, "https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg", genres));
        movies.add(new MovieDto(2, "Зеленая миля", "The Green Mile", "1999", description2, 8.9, 134.67, "https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1._SY209_CR0,0,140,209_.jpg", genres));
        movies.add(new MovieDto(3, "Форрест Гамп", "Forrest Gump", "1994", description3, 8.6, 200.6, "https://images-na.ssl-images-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR2,0,140,209_.jpg", genres));
        movies.add(new MovieDto(4, "Список Шиндлера", "Schindler's List", "1993", description4,8.7, 150.5, "https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg", genres));
        return movies;
    }
}
