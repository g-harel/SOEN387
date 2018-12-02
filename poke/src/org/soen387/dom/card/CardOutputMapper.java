package org.soen387.dom.card;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

public class CardOutputMapper extends GenericOutputMapper<Long, Card> {
	public void insert(Card d) throws MapperException {
		try {
			CardTDG.insert(d.getId(), d.getVersion(), d.getDeckId(), d.getType(), d.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("insert card", e);
		}
	}

	public void update(Card d) throws MapperException {
		try {
			int c = CardTDG.update(d.getId(), d.getVersion(), d.getType(), d.getName());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("update card", e);
		}
	}

	public void delete(Card d) throws MapperException {
		try {
			int c = CardTDG.delete(d.getId(), d.getVersion());
			if (c == 0) throw new LostUpdateException((String)null);
			d.setVersion(d.getVersion()+1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MapperException("delete card", e);
		}
	}
}
