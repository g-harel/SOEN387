package org.soen387.model.gamecard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.ObjectRemovedException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;

public class GameCardInputMapper {
	private static GameCard getGameCard(ResultSet rs) throws SQLException, MapperException {
		if (!rs.next()) throw new MapperException("doesn't exist");

		return GameCardFactory.createClean(
			rs.getLong("id"),
			rs.getLong("version"),
			rs.getLong("game_id"),
			rs.getLong("card_id"),
			rs.getLong("deck_id"),
			rs.getString("status")
		);
	}

	private static List<IGameCard> getGameCards(ResultSet rs) throws SQLException {
		ArrayList<IGameCard> l = new ArrayList<IGameCard>();
		while (rs.next()) {
			l.add(new GameCardProxy(rs.getLong("id")));
		}
		return l;
	}

	//

	public static List<IGameCard> findAll() throws SQLException {
		ResultSet rs = GameCardTDG.findAll();

		return GameCardInputMapper.getGameCards(rs);
	}

	public static GameCard find(Long id) throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, GameCard.class);
		} catch (DomainObjectNotFoundException e) {
		} catch (ObjectRemovedException e) {}

		return GameCardInputMapper.getGameCard(GameCardTDG.find(id));
	}
	
	public static List<IGameCard> find(long gameId, long deckId, String status) throws SQLException {
		ResultSet rs = GameCardTDG.find(gameId, deckId, status);
		
		return GameCardInputMapper.getGameCards(rs);
	}
}
