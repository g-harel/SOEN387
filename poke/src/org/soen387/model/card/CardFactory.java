package org.soen387.model.card;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;

public class CardFactory {
	public static Card createNew(long deckId, String type, String name, String base) throws SQLException, MissingMappingException, MapperException {
		return CardFactory.createNew(CardTDG.maxId(), 1L, deckId, type, name, base);
	}

	public static Card createNew(Long id, long version, long deckId, String type, String name, String base) throws MissingMappingException, MapperException {
		Card d = new Card(id, version, deckId, type, name, base);
		UoW.getCurrent().registerNew(d);
		return d;
	}

	public static Card createClean(Long id, long version, long deckId, String type, String name, String base) {
		Card d = new Card(id, version, deckId, type, name, base);
		UoW.getCurrent().registerClean(d);
		return d;
	}
}
