package org.soen387.model.gamecard;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

public class GameCardProxy extends DomainObjectProxy<Long, GameCard> implements IGameCard {
	protected GameCardProxy(Long id) {
		super(id);
	}

	protected GameCard getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return GameCardInputMapper.find(id);
		}  catch (Exception e) {
			throw new DomainObjectCreationException(e.getMessage(),e);
		}
	}

	//

	public long getGameId() {
		return this.getInnerObject().getGameId();
	}

	public long getCardId() {
		return this.getInnerObject().getCardId();
	}

	public long getDeckId() {
		return this.getInnerObject().getDeckId();
	}

	public String getStatus() {
		return this.getInnerObject().getStatus();
	}

	//

	public void setStatus(String status) {
		this.getInnerObject().setStatus(status);
	}
}
