package org.soen387.app.command;

import java.util.List;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.card.ICard;

public class ChallengePlayerCommand extends Command {
	public List<ICard> cards;
	public String playerId;
	
	public ChallengePlayerCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {		
		if (this.playerId == null || this.playerId.equals("")) throw new CommandException("missing playerId");

		try {
			// TODO this.cards = CardInputMapper.findByDeck(Long.parseLong(playerId));			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't read");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
