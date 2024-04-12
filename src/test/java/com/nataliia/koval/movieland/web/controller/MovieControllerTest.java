package com.nataliia.koval.movieland.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.dto.CountryDto;
import com.nataliia.koval.movieland.dto.ReviewDto;
import com.nataliia.koval.movieland.dto.UserDto;

import com.nataliia.koval.movieland.exception.GenreNotFoundException;
import com.nataliia.koval.movieland.exception.InvalidSortingException;
import com.nataliia.koval.movieland.service.MovieService;
import org.jetbrains.annotations.NotNull;
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
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    @DisplayName("Find all movies - should return list of all movies available.")
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
        int genreId = 1;
        List<MovieDto> movies = createMovieDtos();

        when(movieService.findByGenre(genreId)).thenReturn(movies);

        mockMvc.perform(get(URL + "/genre/{genreId}", genreId))
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

        mockMvc.perform(get(URL + "/genre/{genreId}", invalidGenreId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }

    @Test
    @DisplayName("Find movies by genre - should return error message when genre ID does not exist.")
    void findByGenre_ReturnsErrorMessageWhenGenreIdNotExists() throws Exception {
        int nonExistingGenreId = 1000;
        String errorMessage = "Genre with specified id " + nonExistingGenreId + " not found. Check the request details.";

        when(movieService.findByGenre(nonExistingGenreId)).thenThrow(new GenreNotFoundException(nonExistingGenreId));

        mockMvc.perform(get(URL + "/genre/{genreId}", nonExistingGenreId))
                .andExpect(status().isNotFound())
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

    @Test
    @DisplayName("Find all movies - should throw InvalidSortingException when sorting order is invalid for rating")
    void findAll_ThrowsInvalidSortingExceptionForInvalidRatingOrder() throws Exception {
        String invalidRatingOrder = "invalid";
        String errorMessage = "Invalid sorting order: " + invalidRatingOrder + ". Sorting order should be 'asc' or 'desc'.";

        when(movieService.findAll(invalidRatingOrder, null)).thenThrow(new InvalidSortingException(invalidRatingOrder));

        mockMvc.perform(get(URL + "?ratingOrder=" + invalidRatingOrder))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }

    @Test
    @DisplayName("Find all movies - should throw InvalidSortingException when sorting order is invalid for price")
    void findAll_ThrowsInvalidSortingExceptionForInvalidPriceOrder() throws Exception {
        String invalidPriceOrder = "invalid";
        String errorMessage = "Invalid sorting order: " + invalidPriceOrder + ". Sorting order should be 'asc' or 'desc'.";

        when(movieService.findAll(null, invalidPriceOrder)).thenThrow(new InvalidSortingException(invalidPriceOrder));

        mockMvc.perform(get(URL + "?priceOrder=" + invalidPriceOrder))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }

    @Test
    @DisplayName("Find movie by ID - should return movie with correct details.")
    void findById_ReturnsMovieWithCorrectDetails() throws Exception {
        int movieId = 1;
        MovieDto movie = createMovieDtos().getFirst();

        String currency = "UAH";

        when(movieService.findById(movieId, currency)).thenReturn(movie);

        mockMvc.perform(get(URL + "/{movieId}", movieId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(movie.getId()))
                .andExpect(jsonPath("$.nameRussian").value(movie.getNameRussian()))
                .andExpect(jsonPath("$.nameNative").value(movie.getNameNative()))
                .andExpect(jsonPath("$.yearOfRelease").value(movie.getYearOfRelease()))
                .andExpect(jsonPath("$.description").value(movie.getDescription()))
                .andExpect(jsonPath("$.rating").value(movie.getRating()))
                .andExpect(jsonPath("$.price").value(movie.getPrice()))
                .andExpect(jsonPath("$.picturePath").value(movie.getPicturePath()))
                .andExpect(jsonPath("$.nameNative").value(movie.getNameNative()))
                .andExpect(jsonPath("$.countries", hasSize(movie.getCountries().size())))
                .andExpect(jsonPath("$.genres", hasSize(movie.getGenres().size())))
                .andExpect(jsonPath("$.reviews", hasSize(movie.getReviews().size())));
    }

    private List<MovieDto> createMovieDtos() {
        List<MovieDto> movies = new ArrayList<>();

        GenreDto dramaGenre = new GenreDto(1, "драма");
        Set<GenreDto> genres = Collections.singleton(dramaGenre);

        CountryDto country = new CountryDto(1, "США");
        Set<CountryDto> countries = Collections.singleton(country);

        Set<ReviewDto> reviews = getReviewDtos();

        movies.add(new MovieDto(1, "Побег из Шоушенка", "The Shawshank Redemption", "1994", "description1", 8.9, 123.45, "picturePath1", countries, genres, reviews));
        movies.add(new MovieDto(2, "Зеленая миля", "The Green Mile", "1999", "description2", 8.9, 134.67, "picturePath2", countries, genres, reviews));
        movies.add(new MovieDto(3, "Форрест Гамп", "Forrest Gump", "1994", "description3", 8.6, 200.6, "picturePath3", countries, genres, reviews));
        movies.add(new MovieDto(4, "Список Шиндлера", "Schindler's List", "1993", "description4",8.7, 150.5, "picturePath4", countries, genres, reviews));
        return movies;
    }

    @NotNull
    private static Set<ReviewDto> getReviewDtos() {
        UserDto user = new UserDto(1, "Дарлин Эдвардс");
        ReviewDto review = new ReviewDto(1, user, "Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.");
        return Collections.singleton(review);
    }
}
