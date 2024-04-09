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


DO $$
    DECLARE
        movie_data TEXT[] := ARRAY[
            'Побег из Шоушенка', 'Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.',
            'Зеленая миля', 'Обвиненный в страшном преступлении, Джон Коффи оказывается в блоке смертников тюрьмы «Холодная гора». Вновь прибывший обладал поразительным ростом и был пугающе спокоен, что, впрочем, никак не влияло на отношение к нему начальника блока Пола Эджкомба, привыкшего исполнять приговор.',
            'Форрест Гамп', 'От лица главного героя Форреста Гампа, слабоумного безобидного человека с благородным и открытым сердцем, рассказывается история его необыкновенной жизни.Фантастическим образом превращается он в известного футболиста, героя войны, преуспевающего бизнесмена. Он становится миллиардером, но остается таким же бесхитростным, глупым и добрым. Форреста ждет постоянный успех во всем, а он любит девочку, с которой дружил в детстве, но взаимность приходит слишком поздно.',
            'Список Шиндлера', 'Фильм рассказывает реальную историю загадочного Оскара Шиндлера, члена нацистской партии, преуспевающего фабриканта, спасшего во время Второй мировой войны почти 1200 евреев.',
            '1+1', 'Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, — молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается привнести в размеренную жизнь аристократа дух приключений.',
            'Начало', 'Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. Редкие способности Кобба сделали его ценным игроком в привычном к предательству мире промышленного шпионажа, но они же превратили его в извечного беглеца и лишили всего, что он когда-либо любил.',
            'Жизнь прекрасна', 'Во время II Мировой войны в Италии в концлагерь были отправлены евреи, отец и его маленький сын. Жена, итальянка, добровольно последовала вслед за ними. В лагере отец сказал сыну, что все происходящее вокруг является очень большой игрой за приз в настоящий танк, который достанется тому мальчику, который сможет не попасться на глаза надзирателям. Он сделал все, чтобы сын поверил в игру и остался жив, прячась в бараке.',
            'Бойцовский клуб', 'Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.',
            'Звёздные войны: Эпизод 4 – Новая надежда', 'Татуин. Планета-пустыня. Уже постаревший рыцарь Джедай Оби Ван Кеноби спасает молодого Люка Скайуокера, когда тот пытается отыскать пропавшего дроида. С этого момента Люк осознает свое истинное назначение: он один из рыцарей Джедай. В то время как гражданская война охватила галактику, а войска повстанцев ведут бои против сил злого Императора, к Люку и Оби Вану присоединяется отчаянный пилот-наемник Хан Соло, и в сопровождении двух дроидов, R2D2 и C-3PO, этот необычный отряд отправляется на поиски предводителя повстанцев — принцессы Леи. Героям предстоит отчаянная схватка с устрашающим Дартом Вейдером — правой рукой Императора и его секретным оружием — «Звездой Смерти».',
            'Звёздные войны: Эпизод 5 – Империя наносит ответный удар', 'Борьба за Галактику обостряется в пятом эпизоде космической саги. Войска Императора начинают массированную атаку на повстанцев и их союзников. Хан Соло и принцесса Лейя укрываются в Заоблачном Городе, в котором их и захватывает Дарт Вейдер, в то время как Люк Скайуокер находится на таинственной планете джунглей Дагоба. Там Мастер — джедай Йода обучает молодого рыцаря навыкам обретения Силы. Люк даже не предполагает, как скоро ему придется воспользоваться знаниями старого Мастера: впереди битва с превосходящими силами Императора и смертельный поединок с Дартом Вейдером.',
            'Унесённые призраками', 'Маленькая Тихиро вместе с мамой и папой переезжают в новый дом. Заблудившись по дороге, они оказываются в странном пустынном городе, где их ждет великолепный пир. Родители с жадностью набрасываются на еду и к ужасу девочки превращаются в свиней, став пленниками злой колдуньи Юбабы, властительницы таинственного мира древних богов и могущественных духов.',
            'Титаник', 'Молодые влюбленные Джек и Роза находят друг друга в первом и последнем плавании «непотопляемого» Титаника. Они не могли знать, что шикарный лайнер столкнется с айсбергом в холодных водах Северной Атлантики, и их страстная любовь превратится в схватку со смертью…',
            'Пролетая над гнездом кукушки', 'Сымитировав помешательство в надежде избежать тюремного заключения, Рэндл Патрик МакМерфи попадает в психиатрическую клинику, где почти безраздельным хозяином является жестокосердная сестра Милдред Рэтчед. МакМерфи поражается тому, что прочие пациенты смирились с существующим положением вещей, а некоторые — даже сознательно пришли в лечебницу, прячась от пугающего внешнего мира. И решается на бунт. В одиночку.',
            'Ходячий замок', 'Злая ведьма заточила 18-летнюю Софи в тело старухи. В поисках того, кто поможет ей вернуться к своему облику, Софи знакомится с могущественным волшебником Хаулом и его демоном Кальцифером. Кальцифер должен служить Хаулу по договору, условия которого он не может разглашать. Девушка и демон решают помочь друг другу избавиться от злых чар…',
            'Гладиатор', 'В великой Римской империи не было военачальника, равного генералу Максимусу. Непобедимые легионы, которыми командовал этот благородный воин, боготворили его и могли последовать за ним даже в ад. Но случилось так, что отважный Максимус, готовый сразиться с любым противником в честном бою, оказался бессилен против вероломных придворных интриг. Генерала предали и приговорили к смерти. Чудом избежав гибели, Максимус становится гладиатором.',
            'Большой куш', 'Четырехпалый Френки должен был переправить краденый алмаз из Англии в США своему боссу Эви. Но вместо этого герой попадает в эпицентр больших неприятностей. Сделав ставку на подпольном боксерском поединке, Френки попадает в круговорот весьма нежелательных событий. Вокруг героя и его груза разворачивается сложная интрига с участием множества колоритных персонажей лондонского дна — русского гангстера, троих незадачливых грабителей, хитрого боксера и угрюмого громилы грозного мафиози. Каждый норовит в одиночку сорвать Большой Куш.',
            'Темный рыцарь', 'Бэтмен поднимает ставки в войне с криминалом. С помощью лейтенанта Джима Гордона и прокурора Харви Дента он намерен очистить улицы от преступности, отравляющей город. Сотрудничество оказывается эффективным, но скоро они обнаружат себя посреди хаоса, развязанного восходящим криминальным гением, известным испуганным горожанам под именем Джокер.',
            'Как приручить дракона', 'Вы узнаете историю подростка Иккинга, которому не слишком близки традиции его героического племени, много лет ведущего войну с драконами. Мир Иккинга переворачивается с ног на голову, когда он неожиданно встречает дракона Беззубика, который поможет ему и другим викингам увидеть привычный мир с совершенно другой стороны…',
            'Молчание ягнят', 'Психопат похищает и убивает молодых женщин по всему Среднему Западу Америки. ФБР, уверенное в том, что все преступления совершены одним и тем же человеком, поручает агенту Клариссе Старлинг встретиться с заключенным-маньяком, который мог бы объяснить следствию психологические мотивы серийного убийцы и тем самым вывести на его след.',
            'Гран Торино', 'Вышедший на пенсию автомеханик Уолт Ковальски проводит дни, починяя что-то по дому, попивая пиво и раз в месяц заходя к парикмахеру. И хотя последним желанием его недавно почившей жены было совершение им исповеди, Уолту — ожесточившемуся ветерану Корейской войны, всегда держащему свою винтовку наготове, — признаваться в общем-то не в чем. Да и нет того, кому он доверял бы в той полной мере, в какой доверяет своей собаке Дейзи.',
            'Хороший, плохой, злой', 'В разгар гражданской войны таинственный стрелок скитается по просторам Дикого Запада. У него нет ни дома, ни друзей, ни компаньонов, пока он не встречает двоих незнакомцев, таких же безжалостных и циничных. По воле судьбы трое мужчин вынуждены объединить свои усилия в поисках украденного золота. Но совместная работа — не самое подходящее занятие для таких отъявленных бандитов, как они. Компаньоны вскоре понимают, что в их дерзком и опасном путешествии по разоренной войной стране самое важное — никому не доверять и держать пистолет наготове, если хочешь остаться в живых.',
            'Укрощение строптивого', 'Категорически не приемлющий женского общества грубоватый фермер вполне счастлив и доволен своей холостяцкой жизнью. Но неожиданно появившаяся в его жизни героиня пытается изменить его взгляды на жизнь и очаровать его. Что же из этого получится…',
            'Блеф', 'Белль Дюк имеет старые счеты с Филиппом Бэнгом, который отбывает свой срок за решёткой. Для того чтобы поквитаться с Филиппом, Белль Дюк вступает в сговор с другим аферистом по имени Феликс, чтобы тот организовал побег Филиппа Бенга из тюрьмы. Побег удаётся, но парочка заодно обманывает и Белль Дюк, исчезнув прямо из-под её носа. Выясняется, что и Филипп Бэнг, в свою очередь, не прочь отомстить Белль Дюк. Для этого он задумывает грандиозную мистификацию, сродни покерному блефу…',
            'Джанго освобожденный', 'Эксцентричный охотник за головами, также известный как «Дантист», промышляет отстрелом самых опасных преступников. Работенка пыльная, и без надежного помощника ему не обойтись. Но как найти такого и желательно не очень дорогого? Беглый раб по имени Джанго — прекрасная кандидатура. Правда, у нового помощника свои мотивы — кое с чем надо разобраться…',
            'Танцующий с волками', 'Действие фильма происходит в США во времена Гражданской войны. Лейтенант американской армии Джон Данбар после ранения в бою просит перевести его на новое место службы ближе к западной границе США. Место его службы отдалённый маленький форт. Непосредственный его командир покончил жизнь самоубийством, а попутчик Данбара погиб в стычке с индейцами после того, как довез героя до места назначения. Людей, знающих, что Данбар остался один в форте и должен выжить в условиях суровой природы, и в соседстве с кажущимися негостеприимными коренными обитателями Северной Америки, просто не осталось. Казалось, он покинут всеми. Постепенно лейтенант осваивается, он ведет записи в дневнике…'
            ];
        movie_info TEXT;
BEGIN
    -- Add the 'description' column if it doesn't exist
    BEGIN
        ALTER TABLE movie ADD COLUMN IF NOT EXISTS description TEXT;
    EXCEPTION
        WHEN duplicate_column THEN
            -- 'description' column already exists, do nothing
    END;

        -- Update descriptions for specific movies
    FOR i IN 1..array_length(movie_data, 1) BY 2 LOOP
        movie_info := movie_data[i];
        UPDATE movie
        SET description = movie_data[i+1]
        WHERE name_russian = movie_info;
    END LOOP;
END $$;


CREATE SEQUENCE IF NOT EXISTS country_id_seq START WITH 1 INCREMENT BY 5;

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
        OR (m.name_russian = '1+1' AND c.name = 'Франция')
        OR (m.name_russian = 'Начало' AND c.name IN ('США', 'Великобритания'))
        OR (m.name_russian = 'Жизнь прекрасная' AND c.name = 'Италия')
        OR (m.name_russian = 'Бойцовский клуб' AND c.name IN ('США', 'Германия'))
        OR (m.name_russian = 'Звёздные войны: Эпизод 4 – Новая надежда' AND c.name = 'США')
        OR (m.name_russian = 'Звёздные войны: Эпизод 5 – Империя наносит ответный удар' AND c.name = 'США')
        OR (m.name_russian = 'Унесённые призраками' AND c.name = 'Япония')
        OR (m.name_russian = 'Титаник' AND c.name = 'США')
        OR (m.name_russian = 'Пролетая над гнездом кукушки' AND c.name = 'США')
        OR (m.name_russian = 'Ходячий замок' AND c.name = 'Япония')
        OR (m.name_russian = 'Гладиатор' AND c.name IN ('США', 'Великобритания'))
        OR (m.name_russian = 'Большой куш' AND c.name IN ('Великобритания', 'США'))
        OR (m.name_russian = 'Темный рыцарь' AND c.name IN ('США', 'Великобритания'))
        OR (m.name_russian = 'Как приручить дракона' AND c.name = 'США')
        OR (m.name_russian = 'Молчание ягнят' AND c.name = 'США')
        OR (m.name_russian = 'Гран Торино' AND c.name IN ('США', 'Германия'))
        OR (m.name_russian = 'Хороший, плохой, злой' AND c.name IN ('Италия', 'Испания', 'Германия', 'США'))
        OR (m.name_russian = 'Укрощение строптивого' AND c.name = 'Италия')
        OR (m.name_russian = 'Блеф' AND c.name = 'Италия')
        OR (m.name_russian = 'Джанго освобожденный' AND c.name = 'США')
        OR (m.name_russian = 'Танцующий с волками' AND c.name IN ('США', 'Великобритания'))
    )
  AND NOT EXISTS (
    SELECT 1
    FROM movie_country mc
    WHERE mc.movie_id = m.id AND mc.country_id = c.id
);


CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 1 INCREMENT BY 5;

CREATE TABLE IF NOT EXISTS "user" (
    id INTEGER PRIMARY KEY DEFAULT nextval('user_id_seq'),
    name VARCHAR(255) NOT NULL
);

INSERT INTO "user" (name) VALUES
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






