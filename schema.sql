SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS challenges;
DROP TABLE IF EXISTS decks;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE players (
	id   INT         NOT NULL AUTO_INCREMENT,
	user VARCHAR(128) NOT NULL,
	pass VARCHAR(128) NOT NULL,
	--
	PRIMARY KEY (id),
	CONSTRAINT uq_user UNIQUE (user)
);

CREATE TABLE challenges (
	id               INT NOT NULL AUTO_INCREMENT,
	challenger       INT NOT NULL,
	challengee       INT,
	challenge_status INT DEFAULT 0,
	--
	PRIMARY KEY (id),
	FOREIGN KEY (challenger) REFERENCES players (id) ON DELETE CASCADE,
	FOREIGN KEY (challengee) REFERENCES players (id) ON DELETE CASCADE
);

CREATE TABLE decks (
	id         INT NOT NULL AUTO_INCREMENT,
	player_id  INT NOT NULL,
	cards      TEXT NOT NULL,
	--
	PRIMARY KEY (id),
	FOREIGN KEY (player_id) REFERENCES players (id) ON DELETE CASCADE
);
