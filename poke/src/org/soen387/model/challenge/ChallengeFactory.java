package org.soen387.model.challenge;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;

public class ChallengeFactory {
	public static Challenge createNew(long challenger, long challengee, int status, long deckId) throws SQLException, MissingMappingException, MapperException {
		return ChallengeFactory.createNew(ChallengeTDG.maxId(), 1L, challenger, challengee, status, deckId);
	}

	public static Challenge createNew(Long id, long version, long challenger, long challengee, int status, long deckId) throws MissingMappingException, MapperException {
		Challenge d = new Challenge(id, version, challenger, challengee, status, deckId);
		UoW.getCurrent().registerNew(d);
		return d;
	}

	public static Challenge createClean(Long id, long version, long challenger, long challengee, int status, long deckId) {
		Challenge d = new Challenge(id, version, challenger, challengee, status, deckId);
		UoW.getCurrent().registerClean(d);
		return d;
	}
}
