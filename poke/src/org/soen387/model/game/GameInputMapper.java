package org.soen387.model.game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.ObjectRemovedException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;

public class GameInputMapper {
	private static Game getGame(ResultSet rs) throws SQLException, MapperException {
		if (!rs.next()) throw new MapperException("doesn't exist");

		return GameFactory.createClean(
			rs.getLong("id"),
			rs.getLong("version"),
			rs.getLong("turn"),
			rs.getLong("player1"),
			rs.getLong("deck1"),
			rs.getString("status1"),
			rs.getLong("player2"),
			rs.getLong("deck2"),
			rs.getString("status2")
		);
	}

	private static List<IGame> getGames(ResultSet rs) throws SQLException {
		ArrayList<IGame> l = new ArrayList<IGame>();
		while (rs.next()) {
			l.add(new GameProxy(rs.getLong("id")));
		}
		return l;
	}

	//

	public static List<IGame> findAll() throws SQLException {
		ResultSet rs = GameTDG.findAll();

		return GameInputMapper.getGames(rs);
	}

	public static Game find(Long id) throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, Game.class);
		} catch (DomainObjectNotFoundException e) {
		} catch (ObjectRemovedException e) {}

		return GameInputMapper.getGame(GameTDG.find(id));
	}
}
