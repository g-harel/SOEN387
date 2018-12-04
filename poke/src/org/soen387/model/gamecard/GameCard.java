package org.soen387.model.gamecard;

import org.dsrg.soenea.domain.DomainObject;

public class GameCard extends DomainObject<Long> implements IGameCard {
	private long gameId;
	private long cardId;
	private long deckId;
	private String status;

	protected GameCard(Long id, long version, long gameId, long cardId, long deckId, String status) {
		super(id, version);
		this.gameId = gameId;
		this.cardId = cardId;
		this.deckId = deckId;
		this.status = status;
	}

	//

	public long getGameId() {
		return this.gameId;
	}

	public long getCardId() {
		return this.cardId;
	}

	public long getDeckId() {
		return this.deckId;
	}

	public String getStatus() {
		return this.status;
	}

	//

	public void setStatus(String status) {
		this.status = status;
	}
}
