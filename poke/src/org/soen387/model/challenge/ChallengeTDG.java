package org.soen387.model.challenge;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.SoenEAConnection;
import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class ChallengeTDG {
	public static final String TABLE = "challenges";

	private static final String CREATE =
		"CREATE TABLE IF NOT EXISTS " + ChallengeTDG.TABLE + " (" +
		"    id         INT          NOT NULL AUTO_INCREMENT,   " +
		"    version    INT          NOT NULL,                  " +
		"    challenger INT          NOT NULL,                  " +
		"    challengee INT          NOT NULL,                  " +
		"    status     INT          NOT NULL,                  " +
		"    deck_id    INT          NOT NULL,                  " +
		"    PRIMARY KEY (id)                                   " +
		") ENGINE=InnoDB;                                       ";
	private static final String DROP = "DROP TABLE IF EXISTS " + ChallengeTDG.TABLE;

	private static final String INSERT = "INSERT INTO " + ChallengeTDG.TABLE + " (id, version, challenger, challengee, status, deck_id) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE " + ChallengeTDG.TABLE + " SET version=version+1, challenger=?, challengee=?, status=?, deck_id=? WHERE id=? AND version=?";
	private static final String DELETE = "DELETE FROM " + ChallengeTDG.TABLE + " WHERE id=? AND version=?";

	private static final String FINDALL = "SELECT * FROM " + ChallengeTDG.TABLE;
	private static final String FIND = "SELECT * FROM " + ChallengeTDG.TABLE + " WHERE id=?";

	//

	public static Long maxId() throws SQLException {
		return UniqueIdFactory.getMaxId(ChallengeTDG.TABLE, "id");
	}

	public static void createTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), ChallengeTDG.CREATE);
	}

	public static void dropTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), ChallengeTDG.DROP);
	}

	//

	public static int insert(Long id, long version, long challenger, long challengee, int status, long deckId) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(ChallengeTDG.INSERT);
		s.setLong(1, id);
		s.setLong(2, version);
		s.setLong(3, challenger);
		s.setLong(4, challengee);
		s.setInt(5, status);
		s.setLong(6, deckId);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int update(Long id, long version, long challenger, long challengee, int status, long deckId) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(ChallengeTDG.UPDATE);
		s.setLong(1, challenger);
		s.setLong(2, challengee);
		s.setInt(3, status);
		s.setLong(4, deckId);
		s.setLong(5,  id);
		s.setLong(6, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int delete(Long id, long version) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(ChallengeTDG.DELETE);
		s.setLong(1, id);
		s.setLong(2, version);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	//

	public static ResultSet findAll() throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(ChallengeTDG.FINDALL);

		return SQLLogger.processQuery(s);
	}

	public static ResultSet find(Long id) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(ChallengeTDG.FIND);
		s.setLong(1, id);

		return SQLLogger.processQuery(s);
	}
}
