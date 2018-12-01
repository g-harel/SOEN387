package org.soen387.player;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

public class PlayerProxy extends DomainObjectProxy<Long, Player> implements IPlayer {
	protected PlayerProxy(Long id) {
		super(id);
	}

	protected Player getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return PlayerInputMapper.find(id);
		}  catch (Exception e) {
			throw new DomainObjectCreationException(e.getMessage(),e);
		}
	}

	//

	public String getUser() {
		return this.getInnerObject().getUser();
	}

	public String getPass() {
		return this.getInnerObject().getPass();
	}

	//

	public void setUser(String user) {
		this.getInnerObject().setUser(user);
	}

	public void setPass(String pass) {
		this.getInnerObject().setPass(pass);
	}
}
