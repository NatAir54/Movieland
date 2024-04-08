CREATE SEQUENCE IF NOT EXISTS review_id_seq START WITH 1 INCREMENT BY 20;

CREATE TABLE review (
    id INTEGER PRIMARY KEY DEFAULT nextval('review_id_seq'),
    user_id INTEGER REFERENCES "user"(id),
    text TEXT
);



