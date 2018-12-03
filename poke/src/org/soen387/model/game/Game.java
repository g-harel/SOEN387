package org.soen387.model.game;

import org.dsrg.soenea.domain.DomainObject;

public class Game extends DomainObject<Long> implements IGame {
	private long turn;
	private long player1;
	private long deck1;
	private String status1;
	private long player2;
	private long deck2;
	private String status2;

	protected Game(Long id, long version, long turn, long player1, long deck1, String status1, long player2, long deck2, String status2) {
		super(id, version);
		this.turn = turn;
		this.player1 = player1;
		this.deck1 = deck1;
		this.status1 = status1;
		this.player2 = player2;
		this.deck2 = deck2;
		this.status2 = status2;
	}

	//

	public long getTurn() {
		return this.turn;
	}

	public long getPlayer1() {
		return this.player1;
	}

	public long getDeck1() {
		return this.deck1;
	}

	public String getStatus1() {
		return this.status1;
	}

	public long getPlayer2() {
		return this.player2;
	}

	public long getDeck2() {
		return this.deck2;
	}

	public String getStatus2() {
		return this.status2;
	}

	//

	public void setTurn(long turn) {
		this.turn = turn;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}
}
