package org.soen387.model.game;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

public class GameOutputMapper extends GenericOutputMapper<Long, Game> {
	public void insert(Game d) throws MapperException {
		try {
			GameTDG.insert(d.getId(), d.getVersion(), d.getTurn(), d.getPlayer1(), d.getDeck1(), d.getStatus1(), d.getPlayer2(), d.getDeck2(), d.getStatus2());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("insert", e);
		}
	}

	public void update(Game d) throws MapperException {
		try {
			int c = GameTDG.update(d.getId(), d.getVersion(), d.getTurn(), d.getStatus1(), d.getStatus2());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("update", e);
		}
	}

	public void delete(Game d) throws MapperException {
		try {
			int c = GameTDG.delete(d.getId(), d.getVersion());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("delete", e);
		}
	}
}
