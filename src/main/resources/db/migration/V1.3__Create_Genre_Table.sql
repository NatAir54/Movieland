CREATE SEQUENCE IF NOT EXISTS genre_id_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE IF NOT EXISTS genre (
    id INTEGER PRIMARY KEY DEFAULT nextval('genre_id_seq'),
    name VARCHAR(255) NOT NULL
);