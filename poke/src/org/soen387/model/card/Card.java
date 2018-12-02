package org.soen387.model.card;

import org.dsrg.soenea.domain.DomainObject;

public class Card extends DomainObject<Long> implements ICard {
	private long deckId;
	private String type;
	private String name;
	private String base;

	protected Card(Long id, long version, long deckId, String type, String name, String base) {
		super(id, version);
		this.deckId = deckId;
		this.type = type;
		this.name = name;
		this.base = base;
	}

	//
	
	public long getDeckId() {
		return this.deckId;
	}

	public String getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}
	
	public String getBase() {
		return this.base;
	}

	//

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setBase(String base) {
		this.base = base;
	}
}
