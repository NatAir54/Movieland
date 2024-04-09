ALTER TABLE review ADD COLUMN movie_id INTEGER;

ALTER TABLE review
    ADD CONSTRAINT fk_review_movie
        FOREIGN KEY (movie_id)
            REFERENCES movie(id);
