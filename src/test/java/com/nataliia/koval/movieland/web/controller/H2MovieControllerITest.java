package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.config.H2Config;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = H2Config.class)
@ActiveProfiles({"test", "parallel"})
public class H2MovieControllerITest {
    @Autowired
    protected MockMvc mockMvc;

    private static final String URL = "/api/v1/movies";


    @Test
    @DisplayName("Find all movies - should return list of all movies.")
    void findAll_shouldReturnListOfAllMovies() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    @DisplayName("Find all movies - should return correct movie details.")
    void findAll_shouldReturnCorrectMovieDetails() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$[0].nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$[0].yearOfRelease").value("1994"))
                .andExpect(jsonPath("$[0].description").value("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения."))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg"))
                .andExpect(jsonPath("$[0].countries[0].name").value("США"))
                .andExpect(jsonPath("$[0].genres.length()").value(2))
                .andExpect(jsonPath("$[0].reviews").isArray())
                .andExpect(jsonPath("$[0].reviews[0].user").exists());
    }

    @Test
    @DisplayName("Find three random movies - should return list of three random movies.")
    void findThreeRandom_shouldReturnListOfThreeRandomMovies() throws Exception {
        mockMvc.perform(get(URL + "/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("Find movies by genre - should return list of movies by genre with valid id.")
    void findByGenre_shouldReturnListOfMoviesByGenre() throws Exception {
        int genreId = 1;
        mockMvc.perform(get(URL + "/genre/" + genreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    @DisplayName("Find movies by genre with invalid genre ID - should return error message with invalid genre id.")
    void findByGenre_withInvalidGenreId_shouldReturnNotFoundAndEmptyList() throws Exception {
        int invalidGenreId = -1;
        String errorMessage = "Invalid genre ID: " + invalidGenreId + ". Genre ID should be a positive number.";
        mockMvc.perform(get(URL + "/genre/" + invalidGenreId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }

    @Test
    @DisplayName("Find movies by genre - should return error message when genre ID does not exist.")
    void findByGenre_withNonExistentGenreId_shouldThrowGenreNotFoundException() throws Exception {
        int nonExistentGenreId = 1000;
        String errorMessage = "Genre with specified id " + nonExistentGenreId + " not found. Check the request details.";
        mockMvc.perform(get(URL + "/genre/" + nonExistentGenreId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }

    @Test
    @DisplayName("Find all movies sorted by rating in ascending order")
    void findAllSortedByRatingAsc() throws Exception {
        mockMvc.perform(get(URL + "?ratingOrder=asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].rating").value(8.6))
                .andExpect(jsonPath("$[1].rating").value(8.7))
                .andExpect(jsonPath("$[2].rating").value(8.9))
                .andExpect(jsonPath("$[3].rating").value(8.9));
    }

    @Test
    @DisplayName("Find all movies sorted by rating in descending order")
    void findAllSortedByRatingDesc() throws Exception {
        mockMvc.perform(get(URL + "?ratingOrder=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].rating").value(8.9))
                .andExpect(jsonPath("$[1].rating").value(8.9))
                .andExpect(jsonPath("$[2].rating").value(8.7))
                .andExpect(jsonPath("$[3].rating").value(8.6));
    }

    @Test
    @DisplayName("Find all movies sorted by price in ascending order")
    void findAllSortedByPriceAsc() throws Exception {
        mockMvc.perform(get(URL + "?priceOrder=asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].price").value(123.45))
                .andExpect(jsonPath("$[1].price").value(134.67))
                .andExpect(jsonPath("$[2].price").value(150.5))
                .andExpect(jsonPath("$[3].price").value(200.6));
    }

    @Test
    @DisplayName("Find all movies sorted by price in descending order")
    void findAllSortedByPriceDesc() throws Exception {
        mockMvc.perform(get(URL + "?priceOrder=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].price").value(200.6))
                .andExpect(jsonPath("$[1].price").value(150.5))
                .andExpect(jsonPath("$[2].price").value(134.67))
                .andExpect(jsonPath("$[3].price").value(123.45));
    }

    @Test
    @DisplayName("Find all movies sorted by rating with invalid sorting order - should return error message")
    void findAllSortedByRating_InvalidSortingOrder() throws Exception {
        String invalidOrder = "invalid";
        String errorMessage = "Invalid sorting order: " + invalidOrder + ". Sorting order should be 'asc' or 'desc'.";

        mockMvc.perform(get(URL + "?ratingOrder=invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }

    @Test
    @DisplayName("Find all movies sorted by price with invalid sorting order - should return error message")
    void findAllSortedByPrice_InvalidSortingOrder() throws Exception {
        String invalidOrder = "invalid";
        String errorMessage = "Invalid sorting order: " + invalidOrder + ". Sorting order should be 'asc' or 'desc'.";

        mockMvc.perform(get(URL + "?priceOrder=invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }

    @Test
    @DisplayName("Find movie by ID - should return correct movie details")
    void findById_shouldReturnCorrectMovieDetails() throws Exception {
        int movieId = 1;
        mockMvc.perform(get(URL + "/" + movieId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$.nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$.yearOfRelease").value("1994"))
                .andExpect(jsonPath("$.description").value("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения."))
                .andExpect(jsonPath("$.rating").value(8.9))
                .andExpect(jsonPath("$.price").value(123.45))
                .andExpect(jsonPath("$.picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg"))
                .andExpect(jsonPath("$.countries[0].name").value("США"))
                .andExpect(jsonPath("$.genres.length()").value(2));
    }

    @Test
    @DisplayName("Integration test for handling MovieNotFoundException with invalid movie ID")
    void handleMovieNotFoundException_withInvalidMovieId() throws Exception {
        int invalidMovieId = -1;

        mockMvc.perform(get(URL + "/{movieId}", invalidMovieId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value("Invalid movie ID: " + invalidMovieId + ". Movie ID should be a positive number."));
    }

    @Test
    @DisplayName("Find movie by ID - should return correct movie details with reviews")
    void findById_shouldReturnCorrectMovieDetailsWithReviews() throws Exception {
        int movieId = 1;
        mockMvc.perform(get(URL + "/" + movieId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$.reviews").isArray())
                .andExpect(jsonPath("$.reviews.length()").value(2))
                .andExpect(jsonPath("$.reviews[0].user").exists())
                .andExpect(jsonPath("$.reviews[1].user").exists());
    }

    @Test
    @DisplayName("Integration test for GET /api/v1/movies/{movieId} with currency conversion")
    void findById_shouldReturnMovieDetailsWithConvertedPrice() throws Exception {
        int movieId = 1;
        String defaultCurrency = "UAH";
        String targetCurrency = "USD";
        double originalPrice = 123.45;

        mockMvc.perform(get(URL + "/" + movieId + "?currency=" + defaultCurrency))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(movieId))
                .andExpect(jsonPath("$.price").value(originalPrice));

        mockMvc.perform(get(URL + "/" + movieId + "?currency=" + targetCurrency))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(movieId))
                .andExpect(jsonPath("$.price").isNumber())
                .andExpect(jsonPath("$.price").value(greaterThan(0.0)))
                .andExpect(jsonPath("$.price").value(lessThan(originalPrice)));
    }

    @Test
    @DisplayName("Integration test for GET /api/v1/movies/{movieId} with unsupported currency")
    void findById_shouldReturnBadRequestForUnsupportedCurrency() throws Exception {
        int movieId = 1;
        String unsupportedCurrency = "RUB";

        mockMvc.perform(get(URL + "/" + movieId + "?currency=" + unsupportedCurrency))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value("Unsupported currency: " + unsupportedCurrency + ". Price can be converted to USD or EUR."));
    }
}
