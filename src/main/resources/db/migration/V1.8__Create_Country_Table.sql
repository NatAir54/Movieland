CREATE SEQUENCE IF NOT EXISTS country_id_seq START WITH 1 INCREMENT BY 5;

CREATE TABLE IF NOT EXISTS country (
                                     id INTEGER PRIMARY KEY DEFAULT nextval('country_id_seq'),
                                     name VARCHAR(255) NOT NULL
);