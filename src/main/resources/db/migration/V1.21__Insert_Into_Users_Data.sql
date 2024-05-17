INSERT INTO users (nickname, email, password_hash, is_admin) VALUES
                                 ('Наташа Админ', 'ruba.fish1500@gmail.com', crypt('neja', gen_salt('bf')), true);