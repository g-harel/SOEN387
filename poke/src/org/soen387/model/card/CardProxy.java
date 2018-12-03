package org.soen387.model.card;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

public class CardProxy extends DomainObjectProxy<Long, Card> implements ICard {
	protected CardProxy(Long id) {
		super(id);
	}

	protected Card getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		try {
			return CardInputMapper.find(id);
		}  catch (Exception e) {
			throw new DomainObjectCreationException(e.getMessage(),e);
		}
	}

	//

	public long getDeckId() {
		return this.getInnerObject().getDeckId();
	}

	public String getType() {
		return this.getInnerObject().getType();
	}

	public String getName() {
		return this.getInnerObject().getName();
	}

	public String getBase() {
		return this.getInnerObject().getBase();
	}

	//

	public void setType(String type) {
		this.getInnerObject().setType(type);
	}

	public void setName(String name) {
		this.getInnerObject().setName(name);
	}

	public void setBase(String base) {
		this.getInnerObject().setBase(base);
	}
}
