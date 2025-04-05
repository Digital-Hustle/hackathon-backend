INSERT INTO tns_energy.users (username, password)
VALUES ('user1', 'hashed_password1'),
       ('user2', 'hashed_password2'),
       ('admin', 'hashed_admin_password');

INSERT INTO tns_energy.users_roles (user_id, role)
VALUES (1, 'USER'),
       (2, 'USER'),
       (3, 'USER'),
       (3, 'ADMIN');

INSERT INTO tns_energy.profile (user_id)
VALUES (1),
       (2),
       (3);

INSERT INTO tns_energy.history (id, profile_id)
VALUES (101, 1),
       (102, 2),
       (103, 3);

UPDATE tns_energy.profile
SET history_id = 101
WHERE id = 1;
UPDATE tns_energy.profile
SET history_id = 102
WHERE id = 2;
UPDATE tns_energy.profile
SET history_id = 103
WHERE id = 3;

INSERT INTO tns_energy.report (id, data, history_id)
VALUES (1001, E'\\xDEADBEEF', 101),
       (1002, E'\\xCAFEBABE', 101),
       (1003, E'\\xFEEDFACE', 102),
       (1004, E'\\xBEEFFEED', 103);

INSERT INTO tns_energy.hourly_data (id, day, hour, volume)
VALUES (1, 15, 8, 12.3456789012345),
       (2, 15, 9, 23.4567890123456),
       (3, 15, 10, 34.5678901234567),
       (4, 16, 8, 45.6789012345678),
       (5, 16, 9, 56.7890123456789),
       (6, 16, 10, 67.8901234567890);