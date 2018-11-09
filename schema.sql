DROP TABLE IF EXISTS players;

CREATE TABLE players (
    id   INT         NOT NULL AUTO_INCREMENT,
    user VARCHAR(32) NOT NULL,
    pass VARCHAR(64) NOT NULL,
    --
    PRIMARY KEY (id),
    CONSTRAINT uq_user UNIQUE (user)
);
