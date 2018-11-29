package org.pokeapp.gateways;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.pokeapp.util.Database;

public class GameRDG {
	public static GameRDG insert() {
		GameRDG g = null;
		Connection conn = null;

		try {
			conn = Database.getConnection();

			PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO games () VALUES ();",
				Statement.RETURN_GENERATED_KEYS
			);

			int touched = statement.executeUpdate();
			if (touched > 0) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					g = new GameRDG(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return g;
	}

	public static ArrayList<GameRDG> find() {
		ArrayList<GameRDG> games = new ArrayList<GameRDG>();
		Connection conn = null;

		try {
			conn = Database.getConnection();

			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"SELECT * from games;"
			);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				GameRDG g = new GameRDG(id);
				games.add(g);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return games;
	}

	private int id;
	private ArrayList<Integer> players;

	private GameRDG(int id) {
		this.id = id;
		this.players = new ArrayList<Integer>();
	}

	public int getId() {
		return this.id;
	}
	
	public ArrayList<Integer> getPlayers() {
		return this.players;
	}
	
	public void addPlayer(int playerId) {
		this.players.add(playerId);
	}
}
