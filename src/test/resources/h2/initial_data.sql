INSERT INTO genre (id, name)
VALUES (1, 'драма'),
       (11, 'криминал'),
       (21, 'фэнтези'),
       (31, 'детектив'),
       (41, 'мелодрама'),
       (51, 'биография'),
       (61, 'комедия'),
       (71, 'фантастика'),
       (81, 'боевик'),
       (91, 'триллер'),
       (101, 'приключения'),
       (111, 'аниме'),
       (112, 'мультфильм'),
       (113, 'семейный'),
       (114, 'вестерн');


INSERT INTO movie (id, name_russian, name_native, year_of_release, rating, price, picture_path, version)
VALUES
    (1, 'Побег из Шоушенка', 'The Shawshank Redemption', '1994', 8.9, 123.45, 'https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg', 0),
    (21, 'Зеленая миля', 'The Green Mile', '1999', 8.9, 134.67, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1._SY209_CR0,0,140,209_.jpg', 0),
    (41, 'Форрест Гамп', 'Forrest Gump', '1994', 8.6, 200.6, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR2,0,140,209_.jpg', 0),
    (61, 'Список Шиндлера', 'Schindler''s List', '1993', 8.7, 150.5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg', 0);


INSERT INTO movie_genre (movie_id, genre_id)
SELECT m.id AS movie_id, g.id AS genre_id
FROM movie m
         CROSS JOIN genre g
WHERE (
    (m.name_russian = 'Побег из Шоушенка' AND g.name IN ('драма', 'криминал'))
        OR (m.name_russian = 'Зеленая миля' AND g.name IN ('фэнтези', 'драма', 'криминал', 'детектив'))
        OR (m.name_russian = 'Форрест Гамп' AND g.name IN ('драма', 'мелодрама'))
        OR (m.name_russian = 'Список Шиндлера' AND g.name IN ('драма', 'биография'))
    )
  AND NOT EXISTS (SELECT 1
                  FROM movie_genre mg
                  WHERE mg.movie_id = m.id
                    AND mg.genre_id = g.id);


ALTER TABLE movie
    ALTER COLUMN description SET DATA TYPE TEXT;


UPDATE movie
SET description =
        CASE name_russian
            WHEN 'Побег из Шоушенка'
                THEN 'Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.'
            WHEN 'Зеленая миля'
                THEN 'Обвиненный в страшном преступлении, Джон Коффи оказывается в блоке смертников тюрьмы «Холодная гора». Вновь прибывший обладал поразительным ростом и был пугающе спокоен, что, впрочем, никак не влияло на отношение к нему начальника блока Пола Эджкомба, привыкшего исполнять приговор.'
            WHEN 'Форрест Гамп'
                THEN 'От лица главного героя Форреста Гампа, слабоумного безобидного человека с благородным и открытым сердцем, рассказывается история его необыкновенной жизни.Фантастическим образом превращается он в известного футболиста, героя войны, преуспевающего бизнесмена. Он становится миллиардером, но остается таким же бесхитростным, глупым и добрым. Форреста ждет постоянный успех во всем, а он любит девочку, с которой дружил в детстве, но взаимность приходит слишком поздно.'
            WHEN 'Список Шиндлера'
                THEN 'Фильм рассказывает реальную историю загадочного Оскара Шиндлера, члена нацистской партии, преуспевающего фабриканта, спасшего во время Второй мировой войны почти 1200 евреев.'
            END;


INSERT INTO country (id, name)
VALUES (1, 'США'),
       (21, 'Франция'),
       (41, 'Великобритания'),
       (61, 'Италия'),
       (81, 'Германия'),
       (101, 'Япония'),
       (121, 'Испания');


INSERT INTO movie_country (movie_id, country_id)
SELECT m.id AS movie_id, c.id AS country_id
FROM movie m
         CROSS JOIN country c
WHERE (
    (m.name_russian = 'Побег из Шоушенка' AND c.name = 'США')
        OR (m.name_russian = 'Зеленая миля' AND c.name = 'США')
        OR (m.name_russian = 'Форрест Гамп' AND c.name = 'США')
        OR (m.name_russian = 'Список Шиндлера' AND c.name = 'США')
    )
  AND NOT EXISTS (SELECT 1
                  FROM movie_country mc
                  WHERE mc.movie_id = m.id
                    AND mc.country_id = c.id);


INSERT INTO users (id, nickname, is_admin)
VALUES (1, 'Дарлин Эдвардс', false),
       (21, 'Габриэль Джексон', false),
       (41, 'Рональд Рейнольдс', false),
       (61, 'Нил Паркер', false),
       (81, 'Трэвис Райт', false),
       (101, 'Джесси Паттерсон', false),
       (121, 'Амелия Кэннеди', false),
       (141, 'Деннис Крейг', false),
       (161, 'Дэрил Брайант', false),
       (181, 'Айда Дэвис', false);


ALTER TABLE review
    ALTER COLUMN text SET DATA TYPE TEXT;


INSERT INTO review (id, movie_name_russian, user_id, text)
SELECT 1, 'Побег из Шоушенка',
       u.id,
       'Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.'
FROM users u
WHERE u.nickname = 'Дарлин Эдвардс';

INSERT INTO review (id, movie_name_russian, user_id, text)
SELECT 21, 'Побег из Шоушенка',
       u.id,
       'Кино это является, безусловно, «со знаком качества». Что же до первого места в рейтинге, то, думаю, здесь имело место быть выставление «десяточек» от большинства зрителей вкупе с раздутыми восторженными откликами кинокритиков. Фильм атмосферный. Он драматичный. И, конечно, заслуживает того, чтобы находиться довольно высоко в мировом кинематографе.'
FROM users u
WHERE u.nickname = 'Габриэль Джексон';

INSERT INTO review (id, movie_name_russian, user_id, text)
SELECT 41, 'Зеленая миля',
       u.id,
       'Перестал удивляться тому, что этот фильм занимает сплошь первые места во всевозможных кино рейтингах. Особенно я люблю когда при экранизации литературного произведение из него в силу специфики кинематографа исчезает ирония и появляется некий сверхреализм, обязанный удерживать зрителя у экрана каждую отдельно взятую секунду.'
FROM users u
WHERE u.nickname = 'Рональд Рейнольдс';

INSERT INTO review (id, movie_name_russian, user_id, text)
SELECT 61, 'Форрест Гамп',
       u.id,
       'Много еще можно сказать об этом шедевре. И то, что он учит верить, и то, чтобы никогда не сдаваться… Но самый главный девиз я увидел вот в этой фразе: «Занимайся жизнью, или занимайся смертью».'
FROM users u
WHERE u.nickname = 'Нил Паркер';

INSERT INTO review (id, movie_name_russian, user_id, text)
SELECT 81, 'Список Шиндлера',
       u.id,
       'Очень хороший фильм, необычный сюжет, я бы даже сказала непредсказуемый. Такие фильмы уже стали редкостью.'
FROM users u
WHERE u.nickname = 'Трэвис Райт';


UPDATE review r
SET movie_id = (SELECT id
                FROM movie m
                WHERE m.name_russian = r.movie_name_russian);


CREATE INDEX IF NOT EXISTS idx_movie_rating ON movie (rating);
CREATE INDEX IF NOT EXISTS idx_movie_price ON movie (price);


ALTER TABLE IF EXISTS users
    ADD COLUMN IF NOT EXISTS email VARCHAR(255) NOT NULL DEFAULT '';

ALTER TABLE IF EXISTS users
    ADD COLUMN IF NOT EXISTS password_hash VARCHAR(255) NOT NULL DEFAULT '';


CREATE ALIAS IF NOT EXISTS gen_salt FOR "com.nataliia.koval.movieland.h2.samples.HashUtils.generateSalt";
CREATE ALIAS IF NOT EXISTS crypt FOR "com.nataliia.koval.movieland.h2.samples.HashUtils.hash";

UPDATE users
SET email = CASE
                WHEN nickname = 'Рональд Рейнольдс' THEN 'ronald.reynolds66@example.com'
                WHEN nickname = 'Дарлин Эдвардс' THEN 'darlene.edwards15@example.com'
                WHEN nickname = 'Габриэль Джексон' THEN 'gabriel.jackson91@example.com'
                WHEN nickname = 'Дэрил Брайант' THEN 'daryl.bryant94@example.com'
                WHEN nickname = 'Нил Паркер' THEN 'neil.parker43@example.com'
                WHEN nickname = 'Трэвис Райт' THEN 'travis.wright36@example.com'
                WHEN nickname = 'Амелия Кэннеди' THEN 'amelia.kennedy58@example.com'
                WHEN nickname = 'Айда Дэвис' THEN 'ida.davis80@example.com'
                WHEN nickname = 'Джесси Паттерсон' THEN 'jessie.patterson68@example.com'
                WHEN nickname = 'Деннис Крейг' THEN 'dennis.craig82@example.com'
    END,
    password_hash = CASE
                        WHEN nickname = 'Рональд Рейнольдс' THEN crypt('paco', gen_salt())
                        WHEN nickname = 'Дарлин Эдвардс' THEN crypt('bricks', gen_salt())
                        WHEN nickname = 'Габриэль Джексон' THEN crypt('hjkl', gen_salt())
                        WHEN nickname = 'Дэрил Брайант' THEN crypt('exodus', gen_salt())
                        WHEN nickname = 'Нил Паркер' THEN crypt('878787', gen_salt())
                        WHEN nickname = 'Трэвис Райт' THEN crypt('smart', gen_salt())
                        WHEN nickname = 'Амелия Кэннеди' THEN crypt('beaker', gen_salt())
                        WHEN nickname = 'Айда Дэвис' THEN crypt('pepsi1', gen_salt())
                        WHEN nickname = 'Джесси Паттерсон' THEN crypt('tommy', gen_salt())
                        WHEN nickname = 'Деннис Крейг' THEN crypt('coldbeer', gen_salt())
        END;

INSERT INTO users (id, nickname, email, password_hash, is_admin)
VALUES (201, 'Наташа Админ', 'ruba.fish1500@gmail.com', crypt('neja', gen_salt()), true);










