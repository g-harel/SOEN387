package org.soen387.model.gamecard;

import org.dsrg.soenea.domain.interf.IDomainObject;

public interface IGameCard extends IDomainObject<Long> {
	public abstract long getGameId();
	public abstract long getCardId();
	public abstract long getDeckId();
	public abstract String getStatus();

	public abstract void setStatus(String name);
}
