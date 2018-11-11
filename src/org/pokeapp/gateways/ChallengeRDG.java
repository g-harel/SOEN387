package org.pokeapp.gateways;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.pokeapp.util.Database;

public class ChallengeRDG {
	public static final int STATUS_OPEN = 0;
	public static final int STATUS_REFUSED = 1;
	public static final int STATUS_WITHDRAWN = 2;
	public static final int STATUS_ACCEPTED = 3;

	public static ChallengeRDG insert(int challenger, int challengee) {
		ChallengeRDG c = null;
		Connection conn = null;

		try {
			conn = Database.getConnection();

			PreparedStatement statement = conn.prepareStatement(
				"INSERT INTO challenges (challenger, challengee) VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			statement.setInt(1, challenger);
			statement.setInt(2, challengee);

			int touched = statement.executeUpdate();
			if (touched > 0) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					int id = resultSet.getInt(1);
					c = new ChallengeRDG(id, challenger, challengee, ChallengeRDG.STATUS_OPEN);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return c;
	}

	public static ArrayList<ChallengeRDG> find() {
		ArrayList<ChallengeRDG> challenges = new ArrayList<ChallengeRDG>();
		Connection conn = null;

		try {
			conn = Database.getConnection();

			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"SELECT * from challenges;"
			);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int challenger = resultSet.getInt("challenger");
				int challengee = resultSet.getInt("challengee");
				int status = resultSet.getInt("challenge_status");
				ChallengeRDG c = new ChallengeRDG(id, challenger, challengee, status);
				challenges.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return challenges;
	}

	public static ChallengeRDG find(int id) {
		ChallengeRDG c = null;
		Connection conn = null;

		try {
			conn = Database.getConnection();

			PreparedStatement statement = conn.prepareStatement(
				"SELECT * FROM challenges WHERE id=?;"
			);
			statement.setInt(1, id);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int challenger = resultSet.getInt("challenger");
				int challengee = resultSet.getInt("challengee");
				int status = resultSet.getInt("challenge_status");
				c = new ChallengeRDG(id, challenger, challengee, status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.closeConnection(conn);
		}

		return c;
	}

	private int id;
	private int challenger;
	private int challengee;
	private int status;

	private ChallengeRDG(int id, int challenger, int challengee, int status) {
		this.id = id;
		this.challenger = challenger;
		this.challengee = challengee;
		this.status = status;
	}

	public int getId() {
		return this.id;
	}

	public int getChallenger() {
		return this.challenger;
	}

	public int getChallengee() {
		return this.challengee;
	}

	public int getStatus() {
		return this.status;
	}
}
