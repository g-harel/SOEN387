DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS challenges;

CREATE TABLE players (
	id   INT         NOT NULL AUTO_INCREMENT,
	user VARCHAR(32) NOT NULL,
	pass VARCHAR(64) NOT NULL,
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
	FOREIGN KEY (challengee) REFERENCES players (id) ON DELETE CASCADE,
);
