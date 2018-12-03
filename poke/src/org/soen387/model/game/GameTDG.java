package org.soen387.model.game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.SoenEAConnection;
import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class GameTDG {
	public static final String TABLE = "games";

	private static final String CREATE =
		"CREATE TABLE IF NOT EXISTS " + GameTDG.TABLE + " (" +
		"    id        INT          NOT NULL AUTO_INCREMENT,   " +
		"    version   INT          NOT NULL,                  " +
		"    turn      INT          NOT NULL,                  " +
		"    player1   INT          NOT NULL,                  " +
		"    deck1     INT          NOT NULL,                  " +
		"    status1   VARCHAR(128) NOT NULL,                  " +
		"    player2   INT          NOT NULL,                  " +
		"    deck2     INT          NOT NULL,                  " +
		"    status2   VARCHAR(128) NOT NULL,                  " +
		"    PRIMARY KEY (id)                                  " +
		") ENGINE=InnoDB;                                      ";
	private static final String DROP = "DROP TABLE IF EXISTS " + GameTDG.TABLE;

	private static final String INSERT = "INSERT INTO " + GameTDG.TABLE + " (id, version, turn, player1, deck1, status1, player2, deck2, status2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE " + GameTDG.TABLE + " SET version=version+1, turn=?, status1=?, status2=? WHERE id=? AND version=?";
	private static final String DELETE = "DELETE FROM " + GameTDG.TABLE + " WHERE id=? AND version=?";

	private static final String FINDALL = "SELECT * FROM " + GameTDG.TABLE;
	private static final String FIND = "SELECT * FROM " + GameTDG.TABLE + " WHERE id=?";

	//

	public static Long maxId() throws SQLException {
		return UniqueIdFactory.getMaxId(GameTDG.TABLE, "id");
	}

	public static void createTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), GameTDG.CREATE);
	}

	public static void dropTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), GameTDG.DROP);
	}

	//

	public static int insert(Long id, long version, long turn, long player1, long deck1, String status1, long player2, long deck2, String status2) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(GameTDG.INSERT);
		s.setLong(1, id);
		s.setLong(2, version);
		s.setLong(3, turn);
		s.setLong(4, player1);
		s.setLong(5, deck1);
		s.setString(6, status1);
		s.setLong(7, player2);
		s.setLong(8, deck2);
		s.setString(9, status2);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int update(Long id, long version, long turn, String status1, String status2) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(GameTDG.UPDATE);
		s.setLong(1, turn);
		s.setString(2, status1);
		s.setString(3, status2);
		s.setLong(4, id);
		s.setLong(5, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int delete(Long id, long version) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(GameTDG.DELETE);
		s.setLong(1, id);
		s.setLong(2, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	//

	public static ResultSet findAll() throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(GameTDG.FINDALL);

		return SQLLogger.processQuery(s);
	}

	public static ResultSet find(Long id) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(GameTDG.FIND);
		s.setLong(1, id);

		return SQLLogger.processQuery(s);
	}
}
