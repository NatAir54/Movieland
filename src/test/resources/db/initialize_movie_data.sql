CREATE SEQUENCE IF NOT EXISTS movie_id_seq START WITH 1 INCREMENT BY 20;

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
    ('Список Шиндлера', 'Schindler''s List', '1993', 8.7, 150.5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg');


CREATE SEQUENCE IF NOT EXISTS genre_id_seq START WITH 1 INCREMENT BY 5;

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
SELECT m.id AS movie_id, g.id AS genre_id
FROM movie m
CROSS JOIN genre g
WHERE (
    (m.name_russian = 'Побег из Шоушенка' AND g.name IN ('драма', 'криминал'))
        OR (m.name_russian = 'Зеленая миля' AND g.name IN ('фэнтези', 'драма', 'криминал', 'детектив'))
        OR (m.name_russian = 'Форрест Гамп' AND g.name IN ('драма', 'мелодрама'))
        OR (m.name_russian = 'Список Шиндлера' AND g.name IN ('драма', 'биография'))
    )
  AND NOT EXISTS (
    SELECT 1
    FROM movie_genre mg
    WHERE mg.movie_id = m.id AND mg.genre_id = g.id
);


ALTER TABLE IF EXISTS movie
ADD COLUMN IF NOT EXISTS description TEXT;

UPDATE movie
SET description =
        CASE name_russian
            WHEN 'Побег из Шоушенка' THEN 'Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.'
            WHEN 'Зеленая миля' THEN 'Обвиненный в страшном преступлении, Джон Коффи оказывается в блоке смертников тюрьмы «Холодная гора». Вновь прибывший обладал поразительным ростом и был пугающе спокоен, что, впрочем, никак не влияло на отношение к нему начальника блока Пола Эджкомба, привыкшего исполнять приговор.'
            WHEN 'Форрест Гамп' THEN 'От лица главного героя Форреста Гампа, слабоумного безобидного человека с благородным и открытым сердцем, рассказывается история его необыкновенной жизни.Фантастическим образом превращается он в известного футболиста, героя войны, преуспевающего бизнесмена. Он становится миллиардером, но остается таким же бесхитростным, глупым и добрым. Форреста ждет постоянный успех во всем, а он любит девочку, с которой дружил в детстве, но взаимность приходит слишком поздно.'
            WHEN 'Список Шиндлера' THEN 'Фильм рассказывает реальную историю загадочного Оскара Шиндлера, члена нацистской партии, преуспевающего фабриканта, спасшего во время Второй мировой войны почти 1200 евреев.'
            END;




CREATE SEQUENCE IF NOT EXISTS country_id_seq START WITH 1 INCREMENT BY 20;

CREATE TABLE IF NOT EXISTS country (
    id INTEGER PRIMARY KEY DEFAULT nextval('country_id_seq'),
    name VARCHAR(255) NOT NULL
);

INSERT INTO country (name) VALUES
                               ('США'),
                               ('Франция'),
                               ('Великобритания'),
                               ('Италия'),
                               ('Германия'),
                               ('Япония'),
                               ('Испания');


CREATE TABLE IF NOT EXISTS movie_country (
    movie_id INTEGER REFERENCES movie(id) ON DELETE CASCADE,
    country_id INTEGER REFERENCES country(id) ON DELETE CASCADE,
    PRIMARY KEY (movie_id, country_id)
);

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
  AND NOT EXISTS (
    SELECT 1
    FROM movie_country mc
    WHERE mc.movie_id = m.id AND mc.country_id = c.id
);


CREATE SEQUENCE IF NOT EXISTS users_id_seq START WITH 1 INCREMENT BY 20;

CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY DEFAULT nextval('users_id_seq'),
    nickname VARCHAR(255) NOT NULL
);

INSERT INTO users (nickname) VALUES
                              ('Дарлин Эдвардс'),
                              ('Габриэль Джексон'),
                              ('Рональд Рейнольдс'),
                              ('Нил Паркер'),
                              ('Трэвис Райт'),
                              ('Джесси Паттерсон'),
                              ('Амелия Кэннеди'),
                              ('Деннис Крейг'),
                              ('Дэрил Брайант'),
                              ('Айда Дэвис');

CREATE SEQUENCE IF NOT EXISTS review_id_seq START WITH 1 INCREMENT BY 20;

CREATE TABLE IF NOT EXISTS review (
    id INTEGER PRIMARY KEY DEFAULT nextval('review_id_seq'),
    movie_name_russian VARCHAR(255),
    user_id INTEGER REFERENCES users(id),
    movie_id INTEGER REFERENCES movie(id),
    text TEXT
);

INSERT INTO review (movie_name_russian, user_id, text)
SELECT 'Побег из Шоушенка', u.id, 'Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.'
FROM users u
WHERE u.nickname = 'Дарлин Эдвардс';

INSERT INTO review (movie_name_russian, user_id, text)
SELECT 'Побег из Шоушенка', u.id, 'Кино это является, безусловно, «со знаком качества». Что же до первого места в рейтинге, то, думаю, здесь имело место быть выставление «десяточек» от большинства зрителей вкупе с раздутыми восторженными откликами кинокритиков. Фильм атмосферный. Он драматичный. И, конечно, заслуживает того, чтобы находиться довольно высоко в мировом кинематографе.'
FROM users u
WHERE u.nickname = 'Габриэль Джексон';

INSERT INTO review (movie_name_russian, user_id, text)
SELECT 'Зеленая миля', u.id, 'Перестал удивляться тому, что этот фильм занимает сплошь первые места во всевозможных кино рейтингах. Особенно я люблю когда при экранизации литературного произведение из него в силу специфики кинематографа исчезает ирония и появляется некий сверхреализм, обязанный удерживать зрителя у экрана каждую отдельно взятую секунду.'
FROM users u
WHERE u.nickname = 'Рональд Рейнольдс';

INSERT INTO review (movie_name_russian, user_id, text)
SELECT 'Форрест Гамп', u.id, 'Много еще можно сказать об этом шедевре. И то, что он учит верить, и то, чтобы никогда не сдаваться… Но самый главный девиз я увидел вот в этой фразе: «Занимайся жизнью, или занимайся смертью».'
FROM users u
WHERE u.nickname = 'Нил Паркер';

INSERT INTO review (movie_name_russian, user_id, text)
SELECT 'Список Шиндлера', u.id, 'Очень хороший фильм, необычный сюжет, я бы даже сказала непредсказуемый. Такие фильмы уже стали редкостью.'
FROM users u
WHERE u.nickname = 'Трэвис Райт';


UPDATE review r
SET movie_id = (
    SELECT id
    FROM movie m
    WHERE m.name_russian = r.movie_name_russian
);


CREATE INDEX idx_movie_rating ON movie (rating);
CREATE INDEX idx_movie_price ON movie (price);


ALTER TABLE IF EXISTS users
    ADD COLUMN IF NOT EXISTS email VARCHAR(255) NOT NULL UNIQUE DEFAULT '',
    ADD COLUMN IF NOT EXISTS password_hash VARCHAR(255) NOT NULL DEFAULT '';

CREATE EXTENSION IF NOT EXISTS pgcrypto;

UPDATE users
SET
    email = CASE
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
                        WHEN nickname = 'Рональд Рейнольдс' THEN crypt('paco', gen_salt('bf'))
                        WHEN nickname = 'Дарлин Эдвардс' THEN crypt('bricks', gen_salt('bf'))
                        WHEN nickname = 'Габриэль Джексон' THEN crypt('hjkl', gen_salt('bf'))
                        WHEN nickname = 'Дэрил Брайант' THEN crypt('exodus', gen_salt('bf'))
                        WHEN nickname = 'Нил Паркер' THEN crypt('878787', gen_salt('bf'))
                        WHEN nickname = 'Трэвис Райт' THEN crypt('smart', gen_salt('bf'))
                        WHEN nickname = 'Амелия Кэннеди' THEN crypt('beaker', gen_salt('bf'))
                        WHEN nickname = 'Айда Дэвис' THEN crypt('pepsi1', gen_salt('bf'))
                        WHEN nickname = 'Джесси Паттерсон' THEN crypt('tommy', gen_salt('bf'))
                        WHEN nickname = 'Деннис Крейг' THEN crypt('coldbeer', gen_salt('bf'))
        END;












