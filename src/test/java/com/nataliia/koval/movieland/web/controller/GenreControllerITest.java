package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.AbstractBaseITest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SqlGroup({
        @Sql(scripts = "classpath:db/initialize_genre_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS),
        @Sql(scripts = "classpath:db/remove_genre_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
})
class GenreControllerITest extends AbstractBaseITest {

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
