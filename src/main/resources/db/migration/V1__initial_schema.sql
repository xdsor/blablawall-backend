create schema if not exists wall_backend;
create schema if not exists wall_backend_flyway;

CREATE TABLE IF NOT EXISTS wall_backend.users
(
    id                VARCHAR(256) PRIMARY KEY,
    name              TEXT    NOT NULL,
    profile_image_url TEXT,
    activated         BOOLEAN NOT NULL,
    invited_by        VARCHAR(256),
    date_joined       TIMESTAMP NOT NULL,
    CONSTRAINT fk_invited_by FOREIGN KEY (invited_by) REFERENCES wall_backend.users (id)
);

CREATE TABLE IF NOT EXISTS wall_backend.posts
(
    id         BIGSERIAL PRIMARY KEY,
    title      TEXT      NOT NULL,
    content    TEXT      NOT NULL,
    author_id  VARCHAR(256)    NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_post_author FOREIGN KEY (author_id) REFERENCES wall_backend.users (id)
);

CREATE TABLE IF NOT EXISTS wall_backend.post_replies
(
    id        BIGSERIAL PRIMARY KEY,
    post_id   BIGINT NOT NULL,
    author_id VARCHAR(256) NOT NULL,
    text      TEXT   NOT NULL,
    reply_to  BIGINT,
    posted_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_post_reply_post FOREIGN KEY (post_id) REFERENCES wall_backend.posts (id),
    CONSTRAINT fk_post_reply_author FOREIGN KEY (author_id) REFERENCES wall_backend.users (id),
    CONSTRAINT fk_reply_to FOREIGN KEY (reply_to) REFERENCES wall_backend.post_replies (id)
);

CREATE TABLE IF NOT EXISTS wall_backend.post_likes
(
    post_id BIGINT NOT NULL,
    user_id VARCHAR(256) NOT NULL,
    PRIMARY KEY (post_id, user_id),
    CONSTRAINT fk_post_like_post FOREIGN KEY (post_id) REFERENCES wall_backend.posts (id),
    CONSTRAINT fk_post_like_user FOREIGN KEY (user_id) REFERENCES wall_backend.users (id)
);
