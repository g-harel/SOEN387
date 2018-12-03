package org.soen387.app.command;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.game.GameInputMapper;
import org.soen387.model.game.Game;

public class GameBoardCommand extends Command {
	public long gameId;
	public long playerId;
	public Game game;

	public GameBoardCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {
		try {
			this.game = GameInputMapper.find(gameId);
			if (this.playerId != this.game.getPlayer1() && this.playerId != this.game.getPlayer2()) {
				throw new Exception("not in the game");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't read");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
