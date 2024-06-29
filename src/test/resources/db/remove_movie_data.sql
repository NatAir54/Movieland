ALTER TABLE movie_genre
    DROP CONSTRAINT IF EXISTS movie_genre_movie_id_fkey;
ALTER TABLE movie_country
    DROP CONSTRAINT IF EXISTS movie_country_movie_id_fkey;
ALTER TABLE review
    DROP CONSTRAINT IF EXISTS review_movie_id_fkey;

DELETE FROM movie;
DROP TABLE IF EXISTS movie;
