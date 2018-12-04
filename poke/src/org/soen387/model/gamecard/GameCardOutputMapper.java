package org.soen387.model.gamecard;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

public class GameCardOutputMapper extends GenericOutputMapper<Long, GameCard> {
	public void insert(GameCard d) throws MapperException {
		try {
			GameCardTDG.insert(d.getId(), d.getVersion(), d.getGameId(), d.getCardId(), d.getDeckId(), d.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("insert", e);
		}
	}

	public void update(GameCard d) throws MapperException {
		try {
			int c = GameCardTDG.update(d.getId(), d.getVersion(), d.getStatus());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("update", e);
		}
	}

	public void delete(GameCard d) throws MapperException {
		try {
			int c = GameCardTDG.delete(d.getId(), d.getVersion());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("delete", e);
		}
	}
}
