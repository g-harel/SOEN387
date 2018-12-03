package org.soen387.model.game;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;

public class GameFactory {
	public static Game createNew(long turn, long player1, long deck1, String status1, long player2, long deck2, String status2) throws SQLException, MissingMappingException, MapperException {
		return GameFactory.createNew(GameTDG.maxId(), 1L, turn, player1, deck1, status1, player2, deck2, status2);
	}

	public static Game createNew(Long id, long version, long turn, long player1, long deck1, String status1, long player2, long deck2, String status2) throws MissingMappingException, MapperException {
		Game d = new Game(id, version, turn, player1, deck1, status1, player2, deck2, status2);
		UoW.getCurrent().registerNew(d);
		return d;
	}

	public static Game createClean(Long id, long version, long turn, long player1, long deck1, String status1, long player2, long deck2, String status2) {
		Game d = new Game(id, version, turn, player1, deck1, status1, player2, deck2, status2);
		UoW.getCurrent().registerClean(d);
		return d;
	}
}
