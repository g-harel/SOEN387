package org.pokeapp.gateways;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.pokeapp.util.Database;

class Card {
	private String type;
	private String name;

	public Card(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}
}

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

	private static ArrayList<Card> parse(String cards) throws Exception {
		ArrayList<Card> c = new ArrayList<Card>();

		String[] lines = new String(cards).split("\n");
		for (String line : lines) {
			if (!line.matches("^[ept] \"[A-Z][a-z]+\"$")) {
				throw new Exception("Invalid card: " + line);
			}
			c.add(new Card(line.substring(0, 1), line.substring(3, line.length()-1)));
		}
		
		if (c.size() != 40) {
			throw new Exception("Invalid deck size: " + c.size());
		}

		return c;
	}

	private int id;
	private int playerId;
	private ArrayList<Card> cards;

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

	public ArrayList<Card> getCards() {
		return (ArrayList<Card>) this.cards.clone();
	}
}
