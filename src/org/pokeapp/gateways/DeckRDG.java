package org.pokeapp.gateways;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.pokeapp.util.Database;

public class DeckRDG {
	public static DeckRDG insert(int playerId, String cards) {
		DeckRDG d = null;
		Connection conn = null;

		try {
			conn = Database.getConnection();

			PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO decks (player_id, cards) VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			statement.setInt(1, playerId);
			statement.setString(2, cards);

			int touched = statement.executeUpdate();
			if (touched > 0) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					d = new DeckRDG(id, playerId, cards);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return d;
	}

	public static DeckRDG find(int id) {
		DeckRDG d = null;
		Connection conn = null;

		try {
			conn = Database.getConnection();

			PreparedStatement statement = conn.prepareStatement(
				"SELECT * FROM decks WHERE id=?;"
			);
			statement.setInt(1, id);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int playerId = resultSet.getInt("player_id");
				String cards = resultSet.getString("cards");
				d = new DeckRDG(id, playerId, cards);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return d;
	}

	public static DeckRDG findByPlayer(int playerId) {
		DeckRDG d = null;
		Connection conn = null;

		try {
			conn = Database.getConnection();

			PreparedStatement statement = conn.prepareStatement(
				"SELECT * FROM decks WHERE player_id=? ORDER BY id DESC LIMIT 1;"
			);
			statement.setInt(1, playerId);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String cards = resultSet.getString("cards");
				d = new DeckRDG(id, playerId, cards);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return d;
	}

	private static ArrayList<Map<String, String>> parse(String cards) throws Exception {
		ArrayList<Map<String, String>> c = new ArrayList<Map<String, String>>();

		String[] lines = new String(cards).split("\n");
		for (String line : lines) {
			if (!line.matches("^[ept] \"[A-Z][a-z]+\"$")) {
				throw new Exception("Invalid card: " + line);
			}
			Map<String, String> card = new HashMap<String, String>();
			card.put("type", line.substring(0, 1));
			card.put("name", line.substring(3, line.length()-1));
			c.add(card);
		}
		
		if (c.size() != 40) {
			throw new Exception("Invalid deck size: " + c.size());
		}

		return c;
	}

	private int id;
	private int playerId;
	private ArrayList<Map<String, String>> cards;

	private DeckRDG(int id, int playerId, String cards) throws Exception {
		this.id = id;
		this.playerId = playerId;
		this.cards = DeckRDG.parse(cards);
	}

	public int getId() {
		return this.id;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	public ArrayList<Map<String, String>> getCards() {
		return this.cards;
	}
}
