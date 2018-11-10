package org.pokeapp.gateways;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.pokeapp.util.Database;

public class PlayerRDG {
	public static PlayerRDG insert(String user, String pass) {
		PlayerRDG p = null;
		Connection conn;

		try {
			conn = Database.getConnection();

			PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO players (user, pass) VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			statement.setString(1, user);
			statement.setString(2, pass);

			int touched = statement.executeUpdate();
			if (touched > 0) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					p = new PlayerRDG(id, user, pass);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return p;
	}

	public static ArrayList<PlayerRDG> find() {
		ArrayList<PlayerRDG> players = new ArrayList<PlayerRDG>();
		Connection conn = null;

		try {
			conn = Database.getConnection();

			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"SELECT * from players;"
			);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String user = resultSet.getString("user");
				String pass = resultSet.getString("pass");
				PlayerRDG p = new PlayerRDG(id, user, pass);
				players.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return players;
	}

	public static PlayerRDG find(String user) {
		PlayerRDG p = null;
		Connection conn = null;

		try {
			conn = Database.getConnection();

			PreparedStatement statement = conn.prepareStatement(
				"SELECT id, user, pass FROM players WHERE user=?;"
			);
			statement.setString(1, user);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String pass = resultSet.getString("pass");
				p = new PlayerRDG(id, user, pass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return p;
	}

	public static PlayerRDG find(String user, String pass) {
		PlayerRDG p = PlayerRDG.find(user);
		if (p == null) {
			return null;
		}

		if (p.pass.equals(pass)) {
			return p;
		}

		return null;
	}

	private int id;
	private String user;
	private String pass;

	private PlayerRDG(int id, String user, String pass) {
		this.id = id;
		this.user = user;
		this.pass = pass;
	}

	public int getId() {
		return this.id;
	}

	public String getUser() {
		return this.user;
	}
}
