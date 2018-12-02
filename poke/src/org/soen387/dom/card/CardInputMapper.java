package org.soen387.dom.card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.ObjectRemovedException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;

public class CardInputMapper {
	private static Card getCard(ResultSet rs) throws SQLException, MapperException {
		if (!rs.next()) throw new MapperException("doesn't exist");

		return CardFactory.createClean(
			rs.getLong("id"),
			rs.getLong("version"),
			rs.getLong("deck_id"),
			rs.getString("type"),
			rs.getString("name")
		);
	}

	private static List<ICard> getCards(ResultSet rs) throws SQLException {
		ArrayList<ICard> l = new ArrayList<ICard>();
		while (rs.next()) {
			l.add(new CardProxy(rs.getLong("id")));
		}
		return l;
	}

	//

	public static List<ICard> findAll() throws SQLException {
		ResultSet rs = CardTDG.findAll();

		return CardInputMapper.getCards(rs);
	}

	public static Card find(Long id) throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, Card.class);
		} catch (DomainObjectNotFoundException e) {
		} catch (ObjectRemovedException e) {}

		return CardInputMapper.getCard(CardTDG.find(id));
	}

	public static Card findByDeck(long deckId) throws SQLException, MapperException {
		return CardInputMapper.getCard(CardTDG.findByDeck(deckId));
	}
}
