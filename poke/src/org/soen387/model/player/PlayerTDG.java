package org.soen387.model.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.SoenEAConnection;
import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class PlayerTDG {
	public static final String TABLE = "players";

	private static final String CREATE =
		"CREATE TABLE IF NOT EXISTS " + PlayerTDG.TABLE + " (" +
		"    id      INT          NOT NULL AUTO_INCREMENT,   " +
		"    version INT          NOT NULL,                  " +
		"    user    VARCHAR(128) NOT NULL,                  " +
		"    pass    VARCHAR(128) NOT NULL,                  " +
		"    deck_id INT,                                    " +
		"    PRIMARY KEY (id),                               " +
		"    CONSTRAINT uq_user UNIQUE (user)                " +
		") ENGINE=InnoDB;                                    ";
	private static final String DROP = "DROP TABLE IF EXISTS " + PlayerTDG.TABLE;

	private static final String INSERT = "INSERT INTO " + PlayerTDG.TABLE + " (id, version, user, pass, deck_id) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE " + PlayerTDG.TABLE + " SET version=version+1, user=?, pass=?, deck_id=? WHERE id=? AND version=?";
	private static final String DELETE = "DELETE FROM " + PlayerTDG.TABLE + " WHERE id=? AND version=?";

	private static final String FINDALL = "SELECT * FROM " + PlayerTDG.TABLE;
	private static final String FIND = "SELECT * FROM " + PlayerTDG.TABLE + " WHERE id=?";
	private static final String FINDU = "SELECT * FROM " + PlayerTDG.TABLE + " WHERE user=?";

	//

	public static Long maxId() throws SQLException {
		return UniqueIdFactory.getMaxId(PlayerTDG.TABLE, "id");
	}

	public static void createTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), PlayerTDG.CREATE);
	}

	public static void dropTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), PlayerTDG.DROP);
	}

	//

	public static int insert(Long id, long version, String user, String pass, Long deckId) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		if (deckId == null) deckId = -1L;

		PreparedStatement s = c.prepareStatement(PlayerTDG.INSERT);
		s.setLong(1, id);
		s.setLong(2, version);
		s.setString(3, user);
		s.setString(4, pass);
		s.setLong(5, deckId);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int update(Long id, long version, String user, String pass, Long deckId) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		if (deckId == null) deckId = -1L;

		PreparedStatement s = c.prepareStatement(PlayerTDG.UPDATE);
		s.setString(1, user);
		s.setString(2, pass);
		s.setLong(3, deckId);
		s.setLong(4,  id);
		s.setLong(5, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int delete(Long id, long version) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(PlayerTDG.DELETE);
		s.setLong(1, id);
		s.setLong(2, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	//

	public static ResultSet findAll() throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(PlayerTDG.FINDALL);

		return SQLLogger.processQuery(s);
	}

	public static ResultSet find(Long id) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(PlayerTDG.FIND);
		s.setLong(1, id);

		return SQLLogger.processQuery(s);
	}

	public static ResultSet find(String user) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(PlayerTDG.FINDU);
		s.setString(1, user);

		return SQLLogger.processQuery(s);
	}
}
