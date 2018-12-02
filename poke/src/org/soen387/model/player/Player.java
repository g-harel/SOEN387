package org.soen387.model.player;

import org.dsrg.soenea.domain.DomainObject;

public class Player extends DomainObject<Long> implements IPlayer {
	private String user;
	private String pass;
	private Long deckId;

	protected Player(Long id, long version, String user, String pass, Long deckId) {
		super(id, version);
		this.user = user;
		this.pass = pass;
		this.deckId = deckId;
	}

	//

	public String getUser() {
		return this.user;
	}

	public String getPass() {
		return this.pass;
	}

	public Long getDeckId() {
		if (this.deckId == null || this.deckId == -1L) return null;
		return this.deckId;
	}

	//

	public void setUser(String user) {
		this.user = user;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setDeckId(Long deckId) {
		this.deckId = deckId;
	}
}
