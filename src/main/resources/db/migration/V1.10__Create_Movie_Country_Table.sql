CREATE TABLE IF NOT EXISTS movie_country (
                                           movie_id INTEGER REFERENCES movie(id) ON DELETE CASCADE,
                                           country_id INTEGER REFERENCES country(id) ON DELETE CASCADE,
                                           PRIMARY KEY (movie_id, country_id)
);