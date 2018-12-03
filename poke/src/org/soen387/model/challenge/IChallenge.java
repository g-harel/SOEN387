package org.soen387.model.challenge;

import org.dsrg.soenea.domain.interf.IDomainObject;

public interface IChallenge extends IDomainObject<Long> {
	public abstract long getChallenger();
	public abstract long getChallengee();
	public abstract int getStatus();
	public abstract long getDeckId();

	public abstract void setChallenger(long challenger);
	public abstract void setChallengee(long challengee);
	public abstract void setStatus(int status);
	public abstract void setDeckId(long deckId);
}
