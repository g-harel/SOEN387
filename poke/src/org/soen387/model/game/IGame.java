package org.soen387.model.game;

import org.dsrg.soenea.domain.interf.IDomainObject;

public interface IGame extends IDomainObject<Long> {
	public long getTurn();
	public long getPlayer1();
	public long getDeck1();
	public String getStatus1();
	public long getPlayer2();
	public long getDeck2();
	public String getStatus2();

	public void setTurn(long turn);
	public void setStatus1(String status1);
	public void setStatus2(String status2);
}
