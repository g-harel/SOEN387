package org.soen387.model.game;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

public class GameProxy extends DomainObjectProxy<Long, Game> implements IGame {
	protected GameProxy(Long id) {
		super(id);
	}

	protected Game getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return GameInputMapper.find(id);
		}  catch (Exception e) {
			throw new DomainObjectCreationException(e.getMessage(),e);
		}
	}

	//

	public long getTurn() {
		return this.getInnerObject().getTurn();
	}

	public long getPlayer1() {
		return this.getInnerObject().getPlayer1();
	}

	public long getDeck1() {
		return this.getInnerObject().getDeck1();
	}

	public String getStatus1() {
		return this.getInnerObject().getStatus1();
	}

	public long getPlayer2() {
		return this.getInnerObject().getPlayer2();
	}

	public long getDeck2() {
		return this.getInnerObject().getDeck2();
	}

	public String getStatus2() {
		return this.getInnerObject().getStatus2();
	}

	//

	public void setTurn(long turn) {
		this.getInnerObject().setTurn(turn);
	}

	public void setStatus1(String status1) {
		this.getInnerObject().setStatus1(status1);
	}

	public void setStatus2(String status2) {
		this.getInnerObject().setStatus2(status2);
	}
}
