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

INSERT INTO movie (id, name_russian, name_native, year_of_release, rating, price, picture_path)
VALUES
    (1, 'Побег из Шоушенка', 'The Shawshank Redemption', '1994', 8.9, 123.45, 'https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg'),
    (2, 'Зеленая миля', 'The Green Mile', '1999', 8.9, 134.67, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1._SY209_CR0,0,140,209_.jpg'),
    (3, 'Форрест Гамп', 'Forrest Gump', '1994', 8.6, 200.6, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR2,0,140,209_.jpg'),
    (4, 'Список Шиндлера', 'Schindler''s List', '1993', 8.7, 150.5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg'),
    (5, '1+1', 'Intouchables', '2011', 8.3, 120.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxNDA3MDQwNl5BMl5BanBnXkFtZTcwNTU4Mzc1Nw@@._V1._SY209_CR0,0,140,209_.jpg'),
    (6, 'Начало', 'Inception', '2010', 8.6, 130.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1._SY209_CR0,0,140,209_.jpg'),
    (7, 'Жизнь прекрасна', 'La vita è bella', '1997', 8.2, 145.99, 'https://images-na.ssl-images-amazon.com/images/M/MV5BYmJmM2Q4NmMtYThmNC00ZjRlLWEyZmItZTIwOTBlZDQ3NTQ1XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR0,0,140,209_.jpg'),
    (8, 'Бойцовский клуб', 'Fight Club', '1999', 8.4, 119.99, 'https://images-na.ssl-images-amazon.com/images/M/MV5BZGY5Y2RjMmItNDg5Yy00NjUwLThjMTEtNDc2OGUzNTBiYmM1XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg'),
    (9, 'Звёздные войны: Эпизод 4 – Новая надежда', 'Star Wars', '1977', 8.1, 198.98, 'https://images-na.ssl-images-amazon.com/images/M/MV5BYTUwNTdiMzMtNThmNS00ODUzLThlMDMtMTM5Y2JkNWJjOGQ2XkEyXkFqcGdeQXVyNzQ1ODk3MTQ@._V1._SX140_CR0,0,140,209_.jpg'),
    (10, 'Звёздные войны: Эпизод 5 – Империя наносит ответный удар', 'Star Wars: Episode V - The Empire Strikes Back', '1980', 8.2, 198.98, 'https://images-na.ssl-images-amazon.com/images/M/MV5BYmViY2M2MTYtY2MzOS00YjQ1LWIzYmEtOTBiNjhlMGM0NjZjXkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1._SX140_CR0,0,140,209_.jpg'),
    (11, 'Унесённые призраками', 'Sen to Chihiro no kamikakushi', '2001', 8.6, 145.9, 'https://images-na.ssl-images-amazon.com/images/M/MV5BOGJjNzZmMmUtMjljNC00ZjU5LWJiODQtZmEzZTU0MjBlNzgxL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1._SY209_CR0,0,140,209_.jpg'),
    (12, 'Титаник', 'Titanic', '1997', 7.9, 150.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1._SY209_CR0,0,140,209_.jpg'),
    (13, 'Пролетая над гнездом кукушки', 'One Flew Over the Cuckoo''s Nest', '1975', 8.7, 180.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BZjA0OWVhOTAtYWQxNi00YzNhLWI4ZjYtNjFjZTEyYjJlNDVlL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMTQ3Njg3MQ@@._V1._SY209_CR0,0,140,209_.jpg'),
    (14, 'Ходячий замок', 'Hauru no ugoku shiro', '2004', 8.2, 130.5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BZTRhY2QwM2UtNWRlNy00ZWQwLTg3MjktZThmNjQ3NTdjN2IxXkEyXkFqcGdeQXVyMzg2MzE2OTE@._V1._SY209_CR5,0,140,209_.jpg'),
    (15, 'Гладиатор', 'Gladiator', '2000', 8.6, 175.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg'),
    (16, 'Большой куш', 'Snatch.', '2000', 8.5, 160.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTA2NDYxOGYtYjU1Mi00Y2QzLTgxMTQtMWI1MGI0ZGQ5MmU4XkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1._SY209_CR1,0,140,209_.jpg'),
    (17, 'Темный рыцарь', 'The Dark Knight', '2008', 8.5, 199.99, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1._SY209_CR0,0,140,209_.jpg'),
    (18, 'Как приручить дракона', 'How to Train Your Dragon', '2010', 8.2, 182.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjA5NDQyMjc2NF5BMl5BanBnXkFtZTcwMjg5ODcyMw@@._V1._SY209_CR0,0,140,209_.jpg'),
    (19, 'Молчание ягнят', 'The Silence of the Lambs', '1990', 8.3, 150.5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR1,0,140,209_.jpg'),
    (20, 'Гран Торино', 'Gran Torino', '2008', 8.1, 100.5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTc5NTk2OTU1Nl5BMl5BanBnXkFtZTcwMDc3NjAwMg@@._V1._SY209_CR0,0,140,209_.jpg'),
    (21, 'Хороший, плохой, злой', 'Il buono, il brutto, il cattivo', '1979', 8.5, 130.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BOTQ5NDI3MTI4MF5BMl5BanBnXkFtZTgwNDQ4ODE5MDE@._V1._SX140_CR0,0,140,209_.jpg'),
    (22, 'Укрощение строптивого', 'Il bisbetico domato', '1980', 7.7, 120.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTc5NTM5OTY0Nl5BMl5BanBnXkFtZTcwNjg1MjcyMQ@@._V1._SY209_CR3,0,140,209_.jpg'),
    (23, 'Блеф', 'Bluff storia di truffe e di imbroglioni', '1976', 7.6, 100.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjk5YmMxMjMtMTlkNi00YTI5LThhYTMtOTk2NmNiNzQwMzI0XkEyXkFqcGdeQXVyMTQ3Njg3MQ@@._V1._SX140_CR0,0,140,209_.jpg'),
    (24, 'Джанго освобожденный', 'Django Unchained', '2012', 8.5, 170.0, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjIyNTQ5NjQ1OV5BMl5BanBnXkFtZTcwODg1MDU4OA@@._V1._SY209_CR0,0,140,209_.jpg'),
    (25, 'Танцующий с волками', 'Dances with Wolves', '1990', 8.0, 120.55, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTY3OTI5NDczN15BMl5BanBnXkFtZTcwNDA0NDY3Mw@@._V1._SX140_CR0,0,140,209_.jpg');
