package org.soen387.model.gamecard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.SoenEAConnection;
import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class GameCardTDG {
	public static final String TABLE = "gamecards";

	private static final String CREATE =
		"CREATE TABLE IF NOT EXISTS " + GameCardTDG.TABLE + " (" +
		"    id      INT          NOT NULL AUTO_INCREMENT,   " +
		"    version INT          NOT NULL,                  " +
		"    game_id INT          NOT NULL,                  " +
		"    card_id INT          NOT NULL,                  " +
		"    deck_id INT          NOT NULL,                  " +
		"    status  VARCHAR(128) NOT NULL,                  " +
		"    PRIMARY KEY (id)                                " +
		") ENGINE=InnoDB;                                    ";
	private static final String DROP = "DROP TABLE IF EXISTS " + GameCardTDG.TABLE;

	private static final String INSERT = "INSERT INTO " + GameCardTDG.TABLE + " (id, version, game_id, card_id, deck_id, status) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE " + GameCardTDG.TABLE + " SET version=version+1, status=? WHERE id=? AND version=?";
	private static final String DELETE = "DELETE FROM " + GameCardTDG.TABLE + " WHERE id=? AND version=?";

	private static final String FINDALL = "SELECT * FROM " + GameCardTDG.TABLE;
	private static final String FIND = "SELECT * FROM " + GameCardTDG.TABLE + " WHERE id=?";
	private static final String FINDS = "SELECT * FROM " + GameCardTDG.TABLE + " WHERE game_id=? AND deck_id=? AND status=?";

	//

	public static Long maxId() throws SQLException {
		return UniqueIdFactory.getMaxId(GameCardTDG.TABLE, "id");
	}

	public static void createTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), GameCardTDG.CREATE);
	}

	public static void dropTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), GameCardTDG.DROP);
	}

	//

	public static int insert(Long id, long version, long gameId, long cardId, long deckId, String status) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(GameCardTDG.INSERT);
		s.setLong(1, id);
		s.setLong(2, version);
		s.setLong(3, gameId);
		s.setLong(4, cardId);
		s.setLong(5, deckId);
		s.setString(6, status);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int update(Long id, long version, String status) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(GameCardTDG.UPDATE);
		s.setString(1, status);
		s.setLong(2,  id);
		s.setLong(3, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int delete(Long id, long version) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(GameCardTDG.DELETE);
		s.setLong(1, id);
		s.setLong(2, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	//

	public static ResultSet findAll() throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(GameCardTDG.FINDALL);

		return SQLLogger.processQuery(s);
	}

	public static ResultSet find(Long id) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(GameCardTDG.FIND);
		s.setLong(1, id);

		return SQLLogger.processQuery(s);
	}

	public static ResultSet find(long gameId, long deckId, String status) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(GameCardTDG.FINDS);
		s.setLong(1, gameId);
		s.setLong(2, deckId);
		s.setString(3, status);

		return SQLLogger.processQuery(s);
	}
}
