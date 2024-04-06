INSERT INTO movie_genre (movie_id, genre_id)
SELECT m.id AS movie_id, g.id AS genre_id
FROM movie m
CROSS JOIN genre g
WHERE (
    (m.name_russian = 'Побег из Шоушенка' AND g.name IN ('драма', 'криминал'))
        OR (m.name_russian = 'Зеленая миля' AND g.name IN ('фэнтези', 'драма', 'криминал', 'детектив'))
        OR (m.name_russian = 'Форрест Гамп' AND g.name IN ('драма', 'мелодрама'))
        OR (m.name_russian = 'Список Шиндлера' AND g.name IN ('драма', 'биография'))
        OR (m.name_russian = '1+1' AND g.name IN ('драма', 'комедия', 'биография'))
        OR (m.name_russian = 'Начало' AND g.name IN ('фантастика', 'боевик', 'триллер', 'драма', 'детектив'))
        OR (m.name_russian = 'Жизнь прекрасна' AND g.name IN ('драма', 'мелодрама', 'комедия'))
        OR (m.name_russian = 'Бойцовский клуб' AND g.name IN ('триллер', 'драма', 'криминал'))
        OR (m.name_russian = 'Звёздные войны: Эпизод 4 – Новая надежда' AND
            g.name IN ('фантастика', 'фэнтези', 'боевик', 'приключения'))
        OR (m.name_russian = 'Звёздные войны: Эпизод 5 – Империя наносит ответный удар' AND
            g.name IN ('фантастика', 'фэнтези', 'боевик', 'приключения'))
        OR (m.name_russian = 'Унесённые призраками' AND
            g.name IN ('аниме', 'мультфильм', 'фэнтези', 'приключения', 'семейный'))
        OR (m.name_russian = 'Титаник' AND g.name IN ('драма', 'мелодрама'))
        OR (m.name_russian = 'Пролетая над гнездом кукушки/One Flew Over the Cuckoo''s Nest' AND g.name = 'драма')
        OR (m.name_russian = 'Ходячий замок/Hauru no ugoku shiro' AND
            g.name IN ('аниме', 'мультфильм', 'фэнтези', 'приключения'))
        OR (m.name_russian = 'Гладиатор/Gladiator' AND g.name IN ('боевик', 'драма'))
        OR (m.name_russian = 'Большой куш/Snatch.' AND g.name IN ('криминал', 'комедия'))
        OR (m.name_russian = 'Темный рыцарь/The Dark Knight' AND
            g.name IN ('фантастика', 'боевик', 'триллер', 'криминал', 'драма'))
        OR (m.name_russian = 'Как приручить дракона/How to Train Your Dragon' AND
            g.name IN ('мультфильм', 'фэнтези', 'комедия', 'приключения', 'семейный'))
        OR (m.name_russian = 'Молчание ягнят/The Silence of the Lambs' AND
            g.name IN ('триллер', 'криминал', 'детектив', 'драма'))
        OR (m.name_russian = 'Гран Торино/Gran Torino' AND g.name = 'драма')
        OR (m.name_russian = 'Хороший, плохой, злой/Il buono, il brutto, il cattivo' AND g.name = 'вестерн')
        OR (m.name_russian = 'Укрощение строптивого/Il bisbetico domato' AND g.name = 'комедия')
        OR (m.name_russian = 'Блеф/Bluff storia di truffe e di imbroglioni' AND g.name IN ('комедия', 'криминал'))
        OR (m.name_russian = 'Джанго освобожденный/Django Unchained' AND
            g.name IN ('драма', 'вестерн', 'приключения', 'комедия'))
        OR (m.name_russian = 'Танцующий с волками/Dances with Wolves' AND g.name IN ('драма', 'приключения', 'вестерн'))
    )
AND NOT EXISTS (
    SELECT 1
    FROM movie_genre mg
    WHERE mg.movie_id = m.id AND mg.genre_id = g.id
);