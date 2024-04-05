ALTER TABLE movie_genre DROP CONSTRAINT IF EXISTS movie_genre_movie_id_fkey;
DELETE FROM movie;
DROP TABLE IF EXISTS movie;
