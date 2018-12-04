package org.soen387.model.gamecard;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;

public class GameCardFactory {
	public static GameCard createNew(long gameId, long cardId, long deckId, String status) throws SQLException, MissingMappingException, MapperException {
		return GameCardFactory.createNew(GameCardTDG.maxId(), 1L, gameId, cardId, deckId, status);
	}

	public static GameCard createNew(Long id, long version, long gameId, long cardId, long deckId, String status) throws MissingMappingException, MapperException {
		GameCard d = new GameCard(id, version, gameId, cardId, deckId, status);
		UoW.getCurrent().registerNew(d);
		return d;
	}

	public static GameCard createClean(Long id, long version, long gameId, long cardId, long deckId, String status) {
		GameCard d = new GameCard(id, version, gameId, cardId, deckId, status);
		UoW.getCurrent().registerClean(d);
		return d;
	}
}
