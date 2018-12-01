package org.soen387.domain.model.player;

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
		"    id   LONG         NOT NULL AUTO_INCREMENT,      " +
		"    user VARCHAR(128) NOT NULL,                     " +
		"    pass VARCHAR(128) NOT NULL,                     " +
		"    PRIMARY_KEY(id),                                " +
		"    CONSTRAINT uq_user UNIQUE(user)                 " +
		") ENGINE=InnoDB;                                    ";
	private static final String DROP = "DROP TABLE " + PlayerTDG.TABLE;

	private static final String INSERT = "INSERT INTO " + PlayerTDG.TABLE + " (id, version, user, pass) VALUES (?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE " + PlayerTDG.TABLE + " SET version=version+1, user=?, pass=? WHERE id=? AND version=?";
	private static final String DELETE = "DELETE FROM " + PlayerTDG.TABLE + " WHERE id=? AND version=?";

	private static final String FINDALL = "SELECT * FROM " + PlayerTDG.TABLE;
	private static final String FIND = "SELECT * FROM " + PlayerTDG.TABLE + " WHERE id=?";

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

	public static int insert(Long id, long version, String user, String pass) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(PlayerTDG.INSERT);
		s.setLong(1, id);
		s.setLong(2, version);
		s.setString(2, user);
		s.setString(3, pass);

		int n = SQLLogger.processUpdate(s);
		s.close();
		return n;
	}

	public static int update(Long id, long version, String user, String pass) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(PlayerTDG.UPDATE);
		s.setString(1, user);
		s.setString(2, pass);
		s.setLong(3,  id);
		s.setLong(4, version);

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

		ResultSet rs = SQLLogger.processQuery(s);
		s.close();
		return rs;
	}

	public static ResultSet find(Long id) throws SQLException {
		SoenEAConnection c = DbRegistry.getDbConnection();

		PreparedStatement s = c.prepareStatement(PlayerTDG.FIND);
		s.setLong(1, id);

		ResultSet rs = SQLLogger.processQuery(s);
		s.close();
		return rs;
	}
}
