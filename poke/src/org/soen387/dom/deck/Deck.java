package org.soen387.dom.deck;

import org.dsrg.soenea.domain.DomainObject;

public class Deck extends DomainObject<Long> implements IDeck {
	protected Deck(Long id, long version) {
		super(id, version);
	}
}
