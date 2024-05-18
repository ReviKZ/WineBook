CREATE TABLE IF NOT EXISTS "User" (
    id SERIAL NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS "Post" (
    id SERIAL NOT NULL,
    author_id INT,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(8000) NOT NULL,
    likes INT,
    dislikes INT,
    PRIMARY KEY(id),
    CONSTRAINT fk_user
        FOREIGN KEY(author_id)
            REFERENCES "User"(id)
);