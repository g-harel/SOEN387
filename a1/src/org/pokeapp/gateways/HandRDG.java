package org.pokeapp.gateways;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.pokeapp.util.Database;

public class HandRDG {
	public static final String STATUS_PLAYING = "playing";
	public static final String STATUS_RETIRED = "retired";

	protected static HandRDG readResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		int gameId = resultSet.getInt("game_id");
		int playerId = resultSet.getInt("player_id");
		int deckId = resultSet.getInt("deck_id");
		String status = resultSet.getString("hand_status");
		int handSize = resultSet.getInt("hand_size");
		int deckSize = resultSet.getInt("deck_size");
		int discardSize = resultSet.getInt("discard_size");
		String bench = resultSet.getString("bench");
		return new HandRDG(id, gameId, playerId, deckId, status, handSize, deckSize, discardSize, bench);
	}

	public static HandRDG insert(int gameId, int playerId, int deckId,String status, int handSize, int deckSize, int discardSize, String bench) {
		HandRDG h = null;
		Connection conn = null;

		try {
			conn = Database.getConnection();

			PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO hands (" +
					"game_id," +
					"player_id," +
					"deck_id," +
					"hand_status," +
					"hand_size," +
					"deck_size," +
					"discard_size," +
					"bench" +
				") VALUES (?, ?, ?, ?, ?, ?, ?, ?);",
				Statement.RETURN_GENERATED_KEYS
			);
			statement.setInt(1, gameId);
			statement.setInt(2, playerId);
			statement.setInt(3, deckId);
			statement.setString(4, status);
			statement.setInt(5, handSize);
			statement.setInt(6, deckSize);
			statement.setInt(7, discardSize);
			statement.setString(8, bench);

			int touched = statement.executeUpdate();
			if (touched > 0) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					h = new HandRDG(id, gameId, playerId, deckId, status, handSize, deckSize, discardSize, bench);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return h;
	}

	public static ArrayList<HandRDG> find(int gameId) {
		ArrayList<HandRDG> hands = new ArrayList<HandRDG>();
		Connection conn = null;

		try {
			conn = Database.getConnection();

			PreparedStatement statement = conn.prepareStatement(
				"SELECT * from hands WHERE game_id=?;"
			);
			statement.setInt(1, gameId);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				hands.add(HandRDG.readResultSet(resultSet));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return hands;
	}

	public static HandRDG draw(int gameId, int playerId) {
		HandRDG h = null;
		Connection conn = null;

		try {
			conn = Database.getConnection();

			PreparedStatement udpateStatement = conn.prepareStatement(
				"UPDATE hands SET hand_size=hand_size+1, deck_size=deck_size-1 WHERE game_id=? AND player_id=?;"
			);
			udpateStatement.setInt(1, gameId);
			udpateStatement.setInt(2, playerId);

			int touched = udpateStatement.executeUpdate();
			if (touched == 0) {
				return null;
			}

			PreparedStatement statement = conn.prepareStatement(
				"SELECT * from hands WHERE game_id=? AND player_id=?;"
			);
			statement.setInt(1, gameId);
			statement.setInt(2, playerId);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				h = HandRDG.readResultSet(resultSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return h;
	}

	public static HandRDG retire(int gameId, int playerId) {
		HandRDG h = null;
		Connection conn = null;

		try {
			conn = Database.getConnection();

			PreparedStatement updateStatement = conn.prepareStatement(
				"UPDATE hands SET hand_status=? WHERE game_id=? AND player_id=?;"
			);
			updateStatement.setString(1, HandRDG.STATUS_RETIRED);
			updateStatement.setInt(2, gameId);
			updateStatement.setInt(3, playerId);

			int touched = updateStatement.executeUpdate();
			if (touched == 0) {
				return null;
			}

			PreparedStatement statement = conn.prepareStatement(
				"SELECT * from hands WHERE game_id=? AND player_id=?;"
			);
			statement.setInt(1, gameId);
			statement.setInt(2, playerId);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				h = HandRDG.readResultSet(resultSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return h;
	}

	private static ArrayList<String> parse(String bench) {
		ArrayList<String> b = new ArrayList<String>();

		String[] indecies = new String(bench).split(",");
		for (String index : indecies) {
			if (!index.trim().equals("")) {
				b.add(index.trim());				
			}
		}

		return b;
	}

	private int id;
	private int gameId;
	private int playerId;
	private int deckId;
	private String status;
	private int handSize;
	private int deckSize;
	private int discardSize;
	private ArrayList<String> bench;

	private HandRDG(int id, int gameId, int playerId, int deckId, String status, int handSize, int deckSize, int discardSize, String bench) {
		this.id = id;
		this.gameId = gameId;
		this.playerId = playerId;
		this.deckId = deckId;
		this.status = status;
		this.handSize = handSize;
		this.deckSize = deckSize;
		this.discardSize = discardSize;
		this.bench = HandRDG.parse(bench);
	}

	public int getId() {
		return this.id;
	}

	public int getGameId() {
		return this.gameId;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	public int getDeckId() {
		return this.deckId;
	}

	public String getStatus() {
		return this.status;
	}

	public int getHandSize() {
		return this.handSize;
	}

	public int getDeckSize() {
		return this.deckSize;
	}

	public int getDiscardSize() {
		return this.discardSize;
	}

	public ArrayList<String> getBench() {
		return this.bench;
	}
}
