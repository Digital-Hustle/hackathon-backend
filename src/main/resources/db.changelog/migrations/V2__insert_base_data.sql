INSERT INTO users (username, password)
VALUES ('user1', 'hashed_password1'),
       ('user2', 'hashed_password2'),
       ('admin', 'hashed_admin_password');

INSERT INTO users_roles (user_id, role)
VALUES (1, 'USER'),
       (2, 'USER'),
       (3, 'USER'),
       (3, 'ADMIN');

INSERT INTO profile (user_id)
VALUES (1),
       (2),
       (3);

INSERT INTO report (data, user_id)
VALUES (E'\\xDEADBEEF', 1),
       (E'\\xCAFEBABE', 1),
       (E'\\xFEEDFACE', 2),
       (E'\\xBEEFFEED', 3);

INSERT INTO hourly_data (day, hour, volume)
VALUES (15, 8, 12.3456789012345),
       (15, 9, 23.4567890123456),
       (15, 10, 34.5678901234567),
       (16, 8, 45.6789012345678),
       (16, 9, 56.7890123456789),
       (16, 10, 67.8901234567890);