package org.soen387.model.challenge;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

public class ChallengeOutputMapper extends GenericOutputMapper<Long, Challenge> {
	public void insert(Challenge d) throws MapperException {
		try {
			ChallengeTDG.insert(d.getId(), d.getVersion(), d.getChallenger(), d.getChallengee(), d.getStatus(), d.getDeckId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("insert", e);
		}
	}

	public void update(Challenge d) throws MapperException {
		try {
			int c = ChallengeTDG.update(d.getId(), d.getVersion(), d.getChallenger(), d.getChallengee(), d.getStatus(), d.getDeckId());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("update", e);
		}
	}

	public void delete(Challenge d) throws MapperException {
		try {
			int c = ChallengeTDG.delete(d.getId(), d.getVersion());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("delete", e);
		}
	}
}
