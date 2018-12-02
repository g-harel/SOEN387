package org.soen387.dom.deck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.ObjectRemovedException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;

public class DeckInputMapper {
	private static Deck getDeck(ResultSet rs) throws SQLException, MapperException {
		if (!rs.next()) throw new MapperException("doesn't exist");

		return DeckFactory.createClean(
			rs.getLong("id"),
			rs.getLong("version")
		);
	}

	private static List<IDeck> getDecks(ResultSet rs) throws SQLException {
		ArrayList<IDeck> l = new ArrayList<IDeck>();
		while (rs.next()) {
			l.add(new DeckProxy(rs.getLong("id")));
		}
		return l;
	}

	//

	public static List<IDeck> findAll() throws SQLException {
		ResultSet rs = DeckTDG.findAll();

		return DeckInputMapper.getDecks(rs);
	}

	public static Deck find(Long id) throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, Deck.class);
		} catch (DomainObjectNotFoundException e) {
		} catch (ObjectRemovedException e) {}

		return DeckInputMapper.getDeck(DeckTDG.find(id));
	}
}
