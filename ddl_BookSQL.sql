CREATE TABLE book
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    view_count     BIGINT NULL,
    download_count BIGINT NULL,
    author         VARCHAR(255) NULL,
    category       VARCHAR(255) NULL,
    title          VARCHAR(255) NULL,
    content        VARCHAR(255) NULL,
    create_date    datetime NULL,
    CONSTRAINT pk_book PRIMARY KEY (id)
);

CREATE INDEX idx_author_title ON book (author, title);

CREATE INDEX idx_title ON book (title);