package org.soen387.app.command;

import java.util.List;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.game.Game;
import org.soen387.model.game.GameInputMapper;
import org.soen387.model.gamecard.GameCardInputMapper;
import org.soen387.model.gamecard.IGameCard;

public class ViewHandCommand extends Command {
	public List<IGameCard> cards;
	public long gameId;
	public long playerId;
	
	public ViewHandCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {
		try {
			Game g = GameInputMapper.find(this.gameId);
			
			long deckId;
			if (this.playerId == g.getPlayer1()) {
				deckId = g.getDeck1();
			} else if (this.playerId == g.getPlayer2()) {
				deckId = g.getDeck2();
			} else {
				throw new Exception("not in the game");
			}
			
			this.cards = GameCardInputMapper.find(this.gameId, deckId, "hand");			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't read");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
