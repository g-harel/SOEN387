SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS challenges;
DROP TABLE IF EXISTS decks;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS hands;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE players (
	id   INT         NOT NULL AUTO_INCREMENT,
	--
	user VARCHAR(128) NOT NULL,
	pass VARCHAR(128) NOT NULL,
	--
	PRIMARY KEY (id),
	CONSTRAINT uq_user UNIQUE (user)
);

CREATE TABLE challenges (
	id               INT NOT NULL AUTO_INCREMENT,
	--
	challenger       INT NOT NULL,
	challengee       INT,
	--
	challenge_status INT DEFAULT 0,
	--
	PRIMARY KEY (id),
	FOREIGN KEY (challenger) REFERENCES players (id) ON DELETE CASCADE,
	FOREIGN KEY (challengee) REFERENCES players (id) ON DELETE CASCADE
);

CREATE TABLE decks (
	id         INT  NOT NULL AUTO_INCREMENT,
	--
	player_id  INT  NOT NULL,
	--
	cards      TEXT NOT NULL,
	--
	PRIMARY KEY (id),
	FOREIGN KEY (player_id) REFERENCES players (id) ON DELETE CASCADE
);

CREATE TABLE games (
	id INT NOT NULL AUTO_INCREMENT,
	--
	PRIMARY KEY (id)
);

CREATE TABLE hands (
	id           INT          NOT NULL AUTO_INCREMENT,
	--
	game_id      INT          NOT NULL,
	player_id    INT          NOT NULL,
	deck_id      INT          NOT NULL,
	--
	hand_status  VARCHAR(128) NOT NULL,
	hand_size    INT          NOT NULL,
	deck_size    INT          NOT NULL,
	discard_size INT          NOT NULL,
	bench        TEXT         NOT NULL,
	--
	PRIMARY KEY (id),
	FOREIGN KEY (game_id) REFERENCES games (id) ON DELETE CASCADE,
	FOREIGN KEY (player_id) REFERENCES players (id) ON DELETE CASCADE,
	FOREIGN KEY (deck_id) REFERENCES decks (id) ON DELETE CASCADE
);
