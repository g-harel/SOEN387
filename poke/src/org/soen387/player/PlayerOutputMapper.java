package org.soen387.player;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

public class PlayerOutputMapper extends GenericOutputMapper<Long, Player> {
	public void insert(Player d) throws MapperException {
		try {
			PlayerTDG.insert(d.getId(), d.getVersion(), d.getUser(), d.getPass());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("insert player", e);
		}
	}

	public void update(Player d) throws MapperException {
		try {
			int c = PlayerTDG.update(d.getId(), d.getVersion(), d.getUser(), d.getPass());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("update player", e);
		}
	}

	public void delete(Player d) throws MapperException {
		try {
			int c = PlayerTDG.delete(d.getId(), d.getVersion());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("delete player", e);
		}
	}
}
