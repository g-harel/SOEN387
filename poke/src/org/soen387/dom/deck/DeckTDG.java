package org.soen387.dom.deck;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.SoenEAConnection;
import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class DeckTDG {
	public static final String TABLE = "decks";

	private static final String CREATE =
		"CREATE TABLE IF NOT EXISTS " + DeckTDG.TABLE + " (" +
		"    id      INT          NOT NULL AUTO_INCREMENT,   " +
		"    version INT          NOT NULL,                  " +
		"    PRIMARY KEY (id),                               " +
		") ENGINE=InnoDB;                                    ";
	private static final String DROP = "DROP TABLE IF EXISTS " + DeckTDG.TABLE;

	private static final String INSERT = "INSERT INTO " + DeckTDG.TABLE + " (id, version) VALUES (?, ?)";
	private static final String UPDATE = "UPDATE " + DeckTDG.TABLE + " SET version=version+1 WHERE id=? AND version=?";
	private static final String DELETE = "DELETE FROM " + DeckTDG.TABLE + " WHERE id=? AND version=?";

	private static final String FINDALL = "SELECT * FROM " + DeckTDG.TABLE;
	private static final String FIND = "SELECT * FROM " + DeckTDG.TABLE + " WHERE id=?";

	//

	public static Long maxId() throws SQLException {
		return UniqueIdFactory.getMaxId(DeckTDG.TABLE, "id");
	}

	public static void createTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), DeckTDG.CREATE);
	}

	public static void dropTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), DeckTDG.DROP);
	}

	//

	public static int insert(Long id, long version) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(DeckTDG.INSERT);
		s.setLong(1, id);
		s.setLong(2, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int update(Long id, long version) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(DeckTDG.UPDATE);
		s.setLong(1, id);
		s.setLong(2, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int delete(Long id, long version) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(DeckTDG.DELETE);
		s.setLong(1, id);
		s.setLong(2, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	//

	public static ResultSet findAll() throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(DeckTDG.FINDALL);

		return SQLLogger.processQuery(s);
	}

	public static ResultSet find(Long id) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(DeckTDG.FIND);
		s.setLong(1, id);

		return SQLLogger.processQuery(s);
	}
}
