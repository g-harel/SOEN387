package org.soen387.app.command;

import java.util.List;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.card.ICard;
import org.soen387.model.challenge.ChallengeFactory;
import org.soen387.model.deck.Deck;
import org.soen387.model.deck.DeckInputMapper;
import org.soen387.model.player.PlayerInputMapper;

public class ChallengePlayerCommand extends Command {
	public List<ICard> cards;
	public Long challengerId;
	public String challengeeId;
	
	public ChallengePlayerCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {		
		if (this.challengerId == null) throw new CommandException("missing challengerId");
		if (this.challengeeId == null || this.challengeeId.equals("")) throw new CommandException("missing challengeeId");

		long deckId = this.helper.getLong("deck");
		
		if (this.challengerId.toString().equals(challengeeId)) throw new CommandException("self challenge");

		try {
			long challengeeId = Long.parseLong(this.challengeeId);
			PlayerInputMapper.find(challengeeId);
			Deck deck = DeckInputMapper.find(deckId);
			if (deck.getPlayerId() != this.challengerId) {
				throw new Exception("not deck owner");
			}
			ChallengeFactory.createNew(this.challengerId, challengeeId, 0, deckId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't create");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
