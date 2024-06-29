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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest(classes = H2Config.class)
@ActiveProfiles({"test", "parallel"})
public class H2GenreControllerITest {
    @Autowired
    protected MockMvc mockMvc;

    private static final String URL = "/api/v1/genres";


    @Test
    @DisplayName("Test findAll - should return list of all genres.")
    void findAll_shouldReturnListOfAllGenres() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(15));
    }

    @Test
    @DisplayName("Test findAll - should return correct genre details.")
    void findAll_shouldReturnCorrectGenreDetails() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("драма"))
                .andExpect(jsonPath("$[1].name").value("криминал"))
                .andExpect(jsonPath("$[2].name").value("фэнтези"))
                .andExpect(jsonPath("$[3].name").value("детектив"))
                .andExpect(jsonPath("$[4].name").value("мелодрама"))
                .andExpect(jsonPath("$[5].name").value("биография"))
                .andExpect(jsonPath("$[6].name").value("комедия"))
                .andExpect(jsonPath("$[7].name").value("фантастика"));
    }
}
