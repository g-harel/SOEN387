package org.soen387.model.deck;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

public class DeckOutputMapper extends GenericOutputMapper<Long, Deck> {
	public void insert(Deck d) throws MapperException {
		try {
			DeckTDG.insert(d.getId(), d.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("insert", e);
		}
	}

	public void update(Deck d) throws MapperException {
		try {
			int c = DeckTDG.update(d.getId(), d.getVersion());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("update", e);
		}
	}

	public void delete(Deck d) throws MapperException {
		try {
			int c = DeckTDG.delete(d.getId(), d.getVersion());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("delete", e);
		}
	}
}
