package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.service.GenreService;
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

class GenreControllerTest {

    @Mock
    private GenreService genreService;

    @InjectMocks
    private GenreController genreController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Unit test for findAll() method")
    void findAll_ReturnsListOfGenres() {

        List<GenreDto> genres = createGenreDtos();

        when(genreService.findAll()).thenReturn(genres);

        List<GenreDto> result = genreController.findAll();

        verify(genreService).findAll();
        assertEquals(genres, result);
    }

    private List<GenreDto> createGenreDtos() {
        List<GenreDto> genres = new ArrayList<>();
        genres.add(new GenreDto(1, "Action"));
        genres.add(new GenreDto(2, "Drama"));
        genres.add(new GenreDto(3, "Comedy"));
        return genres;
    }
}
