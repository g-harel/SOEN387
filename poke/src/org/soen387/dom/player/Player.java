package org.soen387.dom.player;

import org.dsrg.soenea.domain.DomainObject;

public class Player extends DomainObject<Long> implements IPlayer {
	private String user;
	private String pass;

	protected Player(Long id, long version, String user, String pass) {
		super(id, version);
		this.user = user;
		this.pass = pass;
	}

	//

	public String getUser() {
		return this.user;
	}

	public String getPass() {
		return this.pass;
	}

	//

	public void setUser(String user) {
		this.user = user;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
