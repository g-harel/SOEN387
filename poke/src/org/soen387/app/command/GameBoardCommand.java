package org.soen387.app.command;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.game.GameInputMapper;
import org.soen387.model.gamecard.GameCardInputMapper;
import org.soen387.model.game.Game;

public class GameBoardCommand extends Command {
	public long gameId;
	public long playerId;

	public Game game;
	public int hand1;
	public int deck1;
	public int hand2;
	public int deck2;

	public GameBoardCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {
		try {
			this.game = GameInputMapper.find(gameId);
			if (this.playerId != this.game.getPlayer1() && this.playerId != this.game.getPlayer2()) {
				throw new Exception("not in the game");
			}
			this.hand1 = GameCardInputMapper.find(this.game.getId(), this.game.getDeck1(), "hand").size();
			this.deck1 = GameCardInputMapper.find(this.game.getId(), this.game.getDeck1(), "deck").size();
			this.hand2 = GameCardInputMapper.find(this.game.getId(), this.game.getDeck2(), "hand").size();
			this.deck2 = GameCardInputMapper.find(this.game.getId(), this.game.getDeck2(), "deck").size();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't read");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
