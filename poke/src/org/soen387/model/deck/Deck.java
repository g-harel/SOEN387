package org.soen387.model.deck;

import org.dsrg.soenea.domain.DomainObject;

public class Deck extends DomainObject<Long> implements IDeck {
	private long playerId;
	
	protected Deck(Long id, long version, long playerId) {
		super(id, version);
		this.playerId = playerId;
	}
	
	//
	
	public long getPlayerId() {
		return this.playerId;
	}
	
	//
	
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
}
