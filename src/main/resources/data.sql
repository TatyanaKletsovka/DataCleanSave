INSERT INTO `role`(`role_name`)
VALUES ('SUPER_ADMIN'),
       ('ADMIN'),
       ('USER');

INSERT INTO `user`
(`id`,
 `first_name`,
 `last_name`,
 `email`,
 `password`,
 `role_id`,
 `created_at`,
 `updated_at`,
 `disabled_at`,
 `enabled`)
VALUES
    (1,
     "John",
     "Snow",
     'super_admin@gmail.com',
     '$2a$10$kB.PezNY0q2ltTJxvtZJd.n4TLM6ZTGMU/uuVXqcYZxAmYB/Y3S.q',
     1,
     NOW(),
     null,
     null,
     1);
