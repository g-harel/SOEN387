package org.soen387.model.deck;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

public class DeckProxy extends DomainObjectProxy<Long, Deck> implements IDeck {
	protected DeckProxy(Long id) {
		super(id);
	}

	protected Deck getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return DeckInputMapper.find(id);
		}  catch (Exception e) {
			throw new DomainObjectCreationException(e.getMessage(),e);
		}
	}
}
