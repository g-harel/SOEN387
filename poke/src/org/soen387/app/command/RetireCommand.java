package org.soen387.app.command;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.game.Game;
import org.soen387.model.game.GameInputMapper;
import org.soen387.model.game.GameOutputMapper;

public class RetireCommand extends Command {
	public long gameId;
	public long playerId;

	public RetireCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {
		try {
			Game game = GameInputMapper.find(gameId);
			if (this.playerId != game.getPlayer1() && this.playerId != game.getPlayer2()) {
				throw new Exception("not in the game");
			}
			if (this.playerId == game.getPlayer1()) {
				game.setStatus1("retired");
			}
			if (this.playerId == game.getPlayer2()) {
				game.setStatus2("retired");
			}
			new GameOutputMapper().update(game);;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't read");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
