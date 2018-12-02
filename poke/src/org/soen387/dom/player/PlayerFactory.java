package org.soen387.dom.player;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;

public class PlayerFactory {
	public static Player createNew(String user, String pass, Long deckId) throws SQLException, MissingMappingException, MapperException {
		return PlayerFactory.createNew(PlayerTDG.maxId(), 1L, user, pass, deckId);
	}

	public static Player createNew(Long id, long version, String user, String pass, Long deckId) throws MissingMappingException, MapperException {
		Player d = new Player(id, version, user, pass, deckId);
		UoW.getCurrent().registerNew(d);
		return d;
	}

	public static Player createClean(Long id, long version, String user, String pass, Long deckId) {
		Player d = new Player(id, version, user, pass, deckId);
		UoW.getCurrent().registerClean(d);
		return d;
	}
}
