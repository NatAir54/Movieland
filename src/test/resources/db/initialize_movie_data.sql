CREATE SEQUENCE IF NOT EXISTS movie_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS movie (
    id INTEGER PRIMARY KEY DEFAULT nextval('movie_id_seq'),
    name_russian VARCHAR(255) NOT NULL,
    name_native VARCHAR(255) NOT NULL,
    year_of_release VARCHAR(4) NOT NULL,
    rating NUMERIC(4,2) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    picture_path VARCHAR(255) NOT NULL
);

INSERT INTO movie (name_russian, name_native, year_of_release, rating, price, picture_path)
VALUES
    ('Побег из Шоушенка', 'The Shawshank Redemption', '1994', 8.9, 123.45, 'https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Зеленая миля', 'The Green Mile', '1999', 8.9, 134.67, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1._SY209_CR0,0,140,209_.jpg'),
    ('Форрест Гамп', 'Forrest Gump', '1994', 8.6, 200.6, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR2,0,140,209_.jpg'),
    ('Список Шиндлера', 'Schindler''s List', '1993', 8.7, 150.5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg'),
    ('1+1', 'Intouchables', '2011', 8.3, 120.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxNDA3MDQwNl5BMl5BanBnXkFtZTcwNTU4Mzc1Nw@@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Начало', 'Inception', '2010', 8.6, 130.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Жизнь прекрасна', 'La vita è bella', '1997', 8.2, 145.99, 'https://images-na.ssl-images-amazon.com/images/M/MV5BYmJmM2Q4NmMtYThmNC00ZjRlLWEyZmItZTIwOTBlZDQ3NTQ1XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Бойцовский клуб', 'Fight Club', '1999', 8.4, 119.99, 'https://images-na.ssl-images-amazon.com/images/M/MV5BZGY5Y2RjMmItNDg5Yy00NjUwLThjMTEtNDc2OGUzNTBiYmM1XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Звёздные войны: Эпизод 4 – Новая надежда', 'Star Wars', '1977', 8.1, 198.98, 'https://images-na.ssl-images-amazon.com/images/M/MV5BYTUwNTdiMzMtNThmNS00ODUzLThlMDMtMTM5Y2JkNWJjOGQ2XkEyXkFqcGdeQXVyNzQ1ODk3MTQ@._V1._SX140_CR0,0,140,209_.jpg'),
    ('Звёздные войны: Эпизод 5 – Империя наносит ответный удар', 'Star Wars: Episode V - The Empire Strikes Back', '1980', 8.2, 198.98, 'https://images-na.ssl-images-amazon.com/images/M/MV5BYmViY2M2MTYtY2MzOS00YjQ1LWIzYmEtOTBiNjhlMGM0NjZjXkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1._SX140_CR0,0,140,209_.jpg'),
    ('Унесённые призраками', 'Sen to Chihiro no kamikakushi', '2001', 8.6, 145.9, 'https://images-na.ssl-images-amazon.com/images/M/MV5BOGJjNzZmMmUtMjljNC00ZjU5LWJiODQtZmEzZTU0MjBlNzgxL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Титаник', 'Titanic', '1997', 7.9, 150.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Пролетая над гнездом кукушки', 'One Flew Over the Cuckoo''s Nest', '1975', 8.7, 180.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BZjA0OWVhOTAtYWQxNi00YzNhLWI4ZjYtNjFjZTEyYjJlNDVlL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMTQ3Njg3MQ@@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Ходячий замок', 'Hauru no ugoku shiro', '2004', 8.2, 130.5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BZTRhY2QwM2UtNWRlNy00ZWQwLTg3MjktZThmNjQ3NTdjN2IxXkEyXkFqcGdeQXVyMzg2MzE2OTE@._V1._SY209_CR5,0,140,209_.jpg'),
    ('Гладиатор', 'Gladiator', '2000', 8.6, 175.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Большой куш', 'Snatch.', '2000', 8.5, 160.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTA2NDYxOGYtYjU1Mi00Y2QzLTgxMTQtMWI1MGI0ZGQ5MmU4XkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1._SY209_CR1,0,140,209_.jpg'),
    ('Темный рыцарь', 'The Dark Knight', '2008', 8.5, 199.99, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Как приручить дракона', 'How to Train Your Dragon', '2010', 8.2, 182.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjA5NDQyMjc2NF5BMl5BanBnXkFtZTcwMjg5ODcyMw@@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Молчание ягнят', 'The Silence of the Lambs', '1990', 8.3, 150.5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR1,0,140,209_.jpg'),
    ('Гран Торино', 'Gran Torino', '2008', 8.1, 100.5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTc5NTk2OTU1Nl5BMl5BanBnXkFtZTcwMDc3NjAwMg@@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Хороший, плохой, злой', 'Il buono, il brutto, il cattivo', '1979', 8.5, 130.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BOTQ5NDI3MTI4MF5BMl5BanBnXkFtZTgwNDQ4ODE5MDE@._V1._SX140_CR0,0,140,209_.jpg'),
    ('Укрощение строптивого', 'Il bisbetico domato', '1980', 7.7, 120.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTc5NTM5OTY0Nl5BMl5BanBnXkFtZTcwNjg1MjcyMQ@@._V1._SY209_CR3,0,140,209_.jpg'),
    ('Блеф', 'Bluff storia di truffe e di imbroglioni', '1976', 7.6, 100.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjk5YmMxMjMtMTlkNi00YTI5LThhYTMtOTk2NmNiNzQwMzI0XkEyXkFqcGdeQXVyMTQ3Njg3MQ@@._V1._SX140_CR0,0,140,209_.jpg'),
    ('Джанго освобожденный', 'Django Unchained', '2012', 8.5, 170.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjIyNTQ5NjQ1OV5BMl5BanBnXkFtZTcwODg1MDU4OA@@._V1._SY209_CR0,0,140,209_.jpg'),
    ('Танцующий с волками', 'Dances with Wolves', '1990', 8.0, 120.55, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTY3OTI5NDczN15BMl5BanBnXkFtZTcwNDA0NDY3Mw@@._V1._SX140_CR0,0,140,209_.jpg');

CREATE SEQUENCE IF NOT EXISTS genre_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS genre (
    id INTEGER PRIMARY KEY DEFAULT nextval('genre_id_seq'),
    name VARCHAR(255) NOT NULL
    );

INSERT INTO genre (name) VALUES
    ('драма'),
    ('криминал'),
    ('фэнтези'),
    ('детектив'),
    ('мелодрама'),
    ('биография'),
    ('комедия'),
    ('фантастика'),
    ('боевик'),
    ('триллер'),
    ('приключения'),
    ('аниме'),
    ('мультфильм'),
    ('семейный'),
    ('вестерн');

CREATE TABLE IF NOT EXISTS movie_genre (
    movie_id INTEGER REFERENCES movie(id) ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genre(id) ON DELETE CASCADE,
    PRIMARY KEY (movie_id, genre_id)
    );

INSERT INTO movie_genre (movie_id, genre_id)
SELECT
    m.id AS movie_id,
    g.id AS genre_id
FROM
    movie m
    CROSS JOIN genre g
WHERE
    (m.name_russian = 'Побег из Шоушенка' AND g.name IN ('драма', 'криминал'))
   OR (m.name_russian = 'Зеленая миля' AND g.name IN ('фэнтези', 'драма', 'криминал', 'детектив'))
   OR (m.name_russian = 'Форрест Гамп' AND g.name IN ('драма', 'мелодрама'))
   OR (m.name_russian = 'Список Шиндлера' AND g.name IN ('драма', 'биография'))
   OR (m.name_russian = '1+1' AND g.name IN ('драма', 'комедия', 'биография'))
   OR (m.name_russian = 'Начало' AND g.name IN ('фантастика', 'боевик', 'триллер', 'драма', 'детектив'))
   OR (m.name_russian = 'Жизнь прекрасна' AND g.name IN ('драма', 'мелодрама', 'комедия'))
   OR (m.name_russian = 'Бойцовский клуб' AND g.name IN ('триллер', 'драма', 'криминал'))
   OR (m.name_russian = 'Звёздные войны: Эпизод 4 – Новая надежда' AND g.name IN ('фантастика', 'фэнтези', 'боевик', 'приключения'))
   OR (m.name_russian = 'Звёздные войны: Эпизод 5 – Империя наносит ответный удар' AND g.name IN ('фантастика', 'фэнтези', 'боевик', 'приключения'))
   OR (m.name_russian = 'Унесённые призраками' AND g.name IN ('аниме', 'мультфильм', 'фэнтези', 'приключения', 'семейный'))
   OR (m.name_russian = 'Титаник' AND g.name IN ('драма', 'мелодрама'))
   OR (m.name_russian = 'Пролетая над гнездом кукушки/One Flew Over the Cuckoo''s Nest' AND g.name = 'драма')
   OR (m.name_russian = 'Ходячий замок/Hauru no ugoku shiro' AND g.name IN ('аниме', 'мультфильм', 'фэнтези', 'приключения'))
   OR (m.name_russian = 'Гладиатор/Gladiator' AND g.name IN ('боевик', 'драма'))
   OR (m.name_russian = 'Большой куш/Snatch.' AND g.name IN ('криминал', 'комедия'))
   OR (m.name_russian = 'Темный рыцарь/The Dark Knight' AND g.name IN ('фантастика', 'боевик', 'триллер', 'криминал', 'драма'))
   OR (m.name_russian = 'Как приручить дракона/How to Train Your Dragon' AND g.name IN ('мультфильм', 'фэнтези', 'комедия', 'приключения', 'семейный'))
   OR (m.name_russian = 'Молчание ягнят/The Silence of the Lambs' AND g.name IN ('триллер', 'криминал', 'детектив', 'драма'))
   OR (m.name_russian = 'Гран Торино/Gran Torino' AND g.name = 'драма')
   OR (m.name_russian = 'Хороший, плохой, злой/Il buono, il brutto, il cattivo' AND g.name = 'вестерн')
   OR (m.name_russian = 'Укрощение строптивого/Il bisbetico domato' AND g.name = 'комедия')
   OR (m.name_russian = 'Блеф/Bluff storia di truffe e di imbroglioni' AND g.name IN ('комедия', 'криминал'))
   OR (m.name_russian = 'Джанго освобожденный/Django Unchained' AND g.name IN ('драма', 'вестерн', 'приключения', 'комедия'))
   OR (m.name_russian = 'Танцующий с волками/Dances with Wolves' AND g.name IN ('драма', 'приключения', 'вестерн'));