package org.soen387.model.card;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.SoenEAConnection;
import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class CardTDG {
	public static final String TABLE = "cards";

	private static final String CREATE =
		"CREATE TABLE IF NOT EXISTS " + CardTDG.TABLE + " (" +
		"    id      INT          NOT NULL AUTO_INCREMENT,   " +
		"    version INT          NOT NULL,                  " +
		"    deck_id INT          NOT NULL,                  " +
		"    type    VARCHAR(128) NOT NULL,                  " +
		"    name    VARCHAR(128) NOT NULL,                  " +
		"    base    VARCHAR(128) NOT NULL,                  " +
		"    PRIMARY KEY (id)                                " +
		") ENGINE=InnoDB;                                    ";
	private static final String DROP = "DROP TABLE IF EXISTS " + CardTDG.TABLE;

	private static final String INSERT = "INSERT INTO " + CardTDG.TABLE + " (id, version, deck_id, type, name, base) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE " + CardTDG.TABLE + " SET version=version+1, type=?, name=? base=? WHERE id=? AND version=?";
	private static final String DELETE = "DELETE FROM " + CardTDG.TABLE + " WHERE id=? AND version=?";

	private static final String FINDALL = "SELECT * FROM " + CardTDG.TABLE;
	private static final String FIND = "SELECT * FROM " + CardTDG.TABLE + " WHERE id=?";
	private static final String FINDD = "SELECT * FROM " + CardTDG.TABLE + " WHERE deck_id=?";

	//

	public static Long maxId() throws SQLException {
		return UniqueIdFactory.getMaxId(CardTDG.TABLE, "id");
	}

	public static void createTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), CardTDG.CREATE);
	}

	public static void dropTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), CardTDG.DROP);
	}

	//

	public static int insert(Long id, long version, long deckId, String type, String name, String base) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(CardTDG.INSERT);
		s.setLong(1, id);
		s.setLong(2, version);
		s.setLong(3, deckId);
		s.setString(4, type);
		s.setString(5, name);
		if (base == null) base = "";
		s.setString(6, base);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int update(Long id, long version, String type, String name, String base) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(CardTDG.UPDATE);
		s.setString(1, type);
		s.setString(2, name);
		s.setString(3, base);
		s.setLong(3,  id);
		s.setLong(4, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int delete(Long id, long version) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(CardTDG.DELETE);
		s.setLong(1, id);
		s.setLong(2, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	//

	public static ResultSet findAll() throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(CardTDG.FINDALL);

		return SQLLogger.processQuery(s);
	}

	public static ResultSet find(Long id) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(CardTDG.FIND);
		s.setLong(1, id);

		return SQLLogger.processQuery(s);
	}

	public static ResultSet findByDeck(long deckId) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(CardTDG.FINDD);
		s.setLong(1, deckId);

		return SQLLogger.processQuery(s);
	}
}
