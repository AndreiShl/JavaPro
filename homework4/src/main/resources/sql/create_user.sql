CREATE TABLE users
(
    id       bigserial,
    username varchar,
    CONSTRAINT "userid_pkey" PRIMARY KEY (id),
    CONSTRAINT "username_key" UNIQUE (username)
);
