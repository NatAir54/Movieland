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
