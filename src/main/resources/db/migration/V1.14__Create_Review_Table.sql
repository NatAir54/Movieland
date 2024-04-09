CREATE SEQUENCE IF NOT EXISTS review_id_seq START WITH 1 INCREMENT BY 20;

CREATE TABLE IF NOT EXISTS Review (
    id INTEGER PRIMARY KEY DEFAULT nextval('review_id_seq'),
    movie_name_russian VARCHAR(255),
    user_id INTEGER REFERENCES "user"(id),
    text TEXT
);
