package org.soen387.model.challenge;

import org.dsrg.soenea.domain.DomainObject;

public class Challenge extends DomainObject<Long> implements IChallenge {
	private long challenger;
	private long challengee;
	private int status;
	private long deckId;

	protected Challenge(Long id, long version, long challenger, long challengee, int status, long deckId) {
		super(id, version);
		this.challenger = challenger;
		this.challengee = challengee;
		this.status = status;
		this.deckId = deckId;
	}

	//

	public long getChallenger() {
		return this.challenger;
	}

	public long getChallengee() {
		return this.challengee;
	}

	public int getStatus() {
		return this.status;
	}

	public long getDeckId() {
		return this.deckId;
	}

	//

	public void setChallenger(long challenger) {
		this.challenger = challenger;
	}

	public void setChallengee(long challengee) {
		this.challengee = challengee;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setDeckId(long deckId) {
		this.deckId = deckId;
	}
}
