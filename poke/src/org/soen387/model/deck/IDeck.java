package org.soen387.model.deck;

import org.dsrg.soenea.domain.interf.IDomainObject;

public interface IDeck extends IDomainObject<Long> {
	public long getPlayerId();
	
	public void setPlayerId(long playerId);
}
