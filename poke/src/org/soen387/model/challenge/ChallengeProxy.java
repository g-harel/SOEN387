package org.soen387.model.challenge;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

public class ChallengeProxy extends DomainObjectProxy<Long, Challenge> implements IChallenge {
	protected ChallengeProxy(Long id) {
		super(id);
	}

	protected Challenge getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return ChallengeInputMapper.find(id);
		}  catch (Exception e) {
			throw new DomainObjectCreationException(e.getMessage(),e);
		}
	}

	//

	public long getChallenger() {
		return this.getInnerObject().getChallenger();
	}

	public long getChallengee() {
		return this.getInnerObject().getChallengee();
	}

	public int getStatus() {
		return this.getInnerObject().getStatus();
	}

	public long getDeckId() {
		return this.getInnerObject().getDeckId();
	}

	//

	public void setChallenger(long challenger) {
		this.getInnerObject().setChallenger(challenger);
	}

	public void setChallengee(long challengee) {
		this.getInnerObject().setChallengee(challengee);
	}

	public void setStatus(int status) {
		this.getInnerObject().setStatus(status);
	}

	public void setDeckId(long deckId) {
		this.getInnerObject().setDeckId(deckId);
	}
}
