package org.soen387.app.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.card.CardFactory;
import org.soen387.model.deck.Deck;
import org.soen387.model.deck.DeckFactory;
import org.soen387.model.player.Player;
import org.soen387.model.player.PlayerInputMapper;
import org.soen387.model.player.PlayerOutputMapper;

public class UploadDeckCommand extends Command {
	public Player currentPlayer = null;
	public long playerId;

	public UploadDeckCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {
		String data = this.helper.getString("deck");

		if (data == null || data.equals("")) throw new CommandException("missing deck");
		
		String[] lines = data.split("\n");
		if (lines.length != 40) {
			throw new CommandException("wrong size");
		}

		try {
			Deck deck = DeckFactory.createNew();
	
			for (String line : lines) {
				Pattern pattern = Pattern.compile("^([ept]) \\\"([A-Z][a-z]+)\\\"(?: \\\"([A-Z][a-z]+)\\\")?$");
				Matcher m = pattern.matcher(line);
				if (!m.matches()) {
					throw new CommandException("invalid card '" + line + "'");
				}
				CardFactory.createNew(deck.getId(), m.group(1), m.group(2), m.group(3));
			}
			
			Player p = PlayerInputMapper.find(this.playerId);
			p.setDeckId(deck.getId());
			new PlayerOutputMapper().update(p);
		} catch (CommandException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't save");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
