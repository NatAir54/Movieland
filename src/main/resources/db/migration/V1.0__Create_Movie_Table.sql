CREATE SEQUENCE IF NOT EXISTS movie_id_seq START WITH 1 INCREMENT BY 20;

CREATE TABLE IF NOT EXISTS movie (
    id INTEGER PRIMARY KEY DEFAULT nextval('movie_id_seq'),
    name_russian VARCHAR(255) NOT NULL,
    name_native VARCHAR(255) NOT NULL,
    year_of_release VARCHAR(4) NOT NULL,
    rating NUMERIC(4,2) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    picture_path VARCHAR(255) DEFAULT NULL
);

