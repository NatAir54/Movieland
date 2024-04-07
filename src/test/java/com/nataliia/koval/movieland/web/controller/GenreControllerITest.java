package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.dto.MovieDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GenreControllerITest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Integration test for GET /api/v1/genres")
    void findAll_shouldReturnStatusOkAndContentTypeApplicationJson() {
        ResponseEntity<List<MovieDto>> responseEntity = testRestTemplate.exchange(
                "/api/v1/genres",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        List<MovieDto> genres = responseEntity.getBody();
        assertNotNull(genres);
        assertFalse(genres.isEmpty());
    }

}