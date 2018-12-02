package org.soen387.model.player;

import org.dsrg.soenea.domain.interf.IDomainObject;

public interface IPlayer extends IDomainObject<Long> {
	public abstract String getUser();
	public abstract String getPass();
	public abstract Long getDeckId();

	public abstract void setUser(String user);
	public abstract void setPass(String pass);
	public abstract void setDeckId(Long deckId);
}
