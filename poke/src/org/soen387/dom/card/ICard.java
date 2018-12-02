package org.soen387.dom.card;

import org.dsrg.soenea.domain.interf.IDomainObject;

public interface ICard extends IDomainObject<Long> {
	public abstract long getDeckId();
	public abstract String getType();
	public abstract String getName();

	public abstract void setType(String type);
	public abstract void setName(String name);
}
