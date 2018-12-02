package org.soen387.model.deck;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;

public class DeckFactory {
	public static Deck createNew() throws SQLException, MissingMappingException, MapperException {
		return DeckFactory.createNew(DeckTDG.maxId(), 1L);
	}

	public static Deck createNew(Long id, long version) throws MissingMappingException, MapperException {
		Deck d = new Deck(id, version);
		UoW.getCurrent().registerNew(d);
		return d;
	}

	public static Deck createClean(Long id, long version) {
		Deck d = new Deck(id, version);
		UoW.getCurrent().registerClean(d);
		return d;
	}
}
