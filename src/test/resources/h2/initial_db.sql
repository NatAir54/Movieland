CREATE TABLE IF NOT EXISTS genre
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS movie
(
    id              BIGINT PRIMARY KEY,
    name_russian    VARCHAR(255)   NOT NULL,
    name_native     VARCHAR(255)   NOT NULL,
    year_of_release VARCHAR(4)     NOT NULL,
    rating          NUMERIC(4, 2)  NOT NULL,
    price           NUMERIC(10, 2) NOT NULL,
    picture_path    VARCHAR(255)   NOT NULL,
    version         int            DEFAULT 0,
    description     TEXT
);

CREATE TABLE IF NOT EXISTS movie_genre
(
    movie_id INTEGER REFERENCES movie (id) ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genre (id) ON DELETE CASCADE,
    PRIMARY KEY (movie_id, genre_id)
);

CREATE TABLE IF NOT EXISTS country
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS movie_country
(
    movie_id   INTEGER REFERENCES movie (id) ON DELETE CASCADE,
    country_id INTEGER REFERENCES country (id) ON DELETE CASCADE,
    PRIMARY KEY (movie_id, country_id)
);

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT PRIMARY KEY,
    nickname VARCHAR(255) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS review
(
    id                 BIGINT PRIMARY KEY,
    movie_name_russian VARCHAR(255),
    user_id            INTEGER REFERENCES users (id),
    movie_id           INTEGER REFERENCES movie (id),
    text               TEXT
);