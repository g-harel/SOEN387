package org.soen387.app.command;

import java.util.List;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.deck.DeckInputMapper;
import org.soen387.model.deck.IDeck;

public class ViewDecksCommand extends Command {
	public List<IDeck> decks;
	
	public ViewDecksCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {
		try {
			this.decks = DeckInputMapper.findAll();			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't read");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
