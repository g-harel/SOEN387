package org.pokeapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	// Configuration is loaded once.
	private static String username = Config.read("DB_USERNAME");
	private static String password = Config.read("DB_PASSWORD");
	private static String host = Config.read("DB_HOST");
	private static String port = Config.read("DB_PORT");

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");

		return DriverManager.getConnection(
			"jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.username
				+ "?user=" + Database.username
				+ "&password=" + Database.password
				+ "&characterEncoding=UTF-8"
				+ "&useUnicode=true"
				+ "&useJDBCCompliantTimezoneShift=true"
				+ "&useLegacyDatetimeCode=false"
				+ "&serverTimezone=UTC"
				+ "&autoReconnect=true"
		);
	}

	public static void closeConnection(Connection conn) {
		if (conn == null) {
			return;
		}

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
