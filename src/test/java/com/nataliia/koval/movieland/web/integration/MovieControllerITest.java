package com.nataliia.koval.movieland.web.integration;

import com.nataliia.koval.movieland.service.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(scripts = "classpath:db/initialize_movie_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/remove_movie_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class MovieControllerITest {

    private static final String URL = "/api/v1/movies";

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.3");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

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
                .andExpect(jsonPath("$[0].genres[0].name").value("криминал"));
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
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error_message").value(errorMessage));
    }

    @Test
    @DisplayName("Find movies by genre - should return error message when genre ID does not exist.")
    void findByGenre_withNonExistentGenreId_shouldThrowGenreNotFoundException() throws Exception {
        int nonExistentGenreId = 1000;
        String errorMessage = "Genre with specified id " + nonExistentGenreId + " not found. Check the request details.";
        mockMvc.perform(get(URL + "/genre/" + nonExistentGenreId))
                .andExpect(status().isOk())
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
                .andExpect(jsonPath("$[3].price").value(123.45));
    }
}
