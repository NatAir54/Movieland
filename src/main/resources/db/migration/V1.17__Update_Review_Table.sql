UPDATE review r
SET movie_id = (
    SELECT id
    FROM movie m
    WHERE m.name_russian = r.movie_name_russian
);

