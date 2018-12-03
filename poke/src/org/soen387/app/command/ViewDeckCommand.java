package org.soen387.app.command;

import java.util.List;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.card.CardInputMapper;
import org.soen387.model.card.ICard;

public class ViewDeckCommand extends Command {
	public List<ICard> cards;
	public String deckId;
	
	public ViewDeckCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {		
		if (this.deckId == null || this.deckId.equals("")) throw new CommandException("missing deckId");

		try {
			this.cards = CardInputMapper.findByDeck(Long.parseLong(deckId));			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't read");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
