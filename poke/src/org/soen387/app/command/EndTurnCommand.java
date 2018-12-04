package org.soen387.app.command;

import java.util.List;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.gamecard.IGameCard;
import org.soen387.model.game.Game;
import org.soen387.model.game.GameInputMapper;
import org.soen387.model.game.GameOutputMapper;
import org.soen387.model.gamecard.GameCardFactory;
import org.soen387.model.gamecard.GameCardInputMapper;
import org.soen387.model.gamecard.GameCardOutputMapper;
import org.soen387.model.player.Player;
import org.soen387.model.player.PlayerInputMapper;

public class EndTurnCommand extends Command {
	public long gameId;
	public long version;
	public long playerId;
	
	public EndTurnCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {
		try {
			Game g = GameInputMapper.find(this.gameId);
			Player p = PlayerInputMapper.find(this.playerId);
			
			if (g.getVersion() != this.version) {
				throw new Exception("wrong version");
			}
			
			long deckId = -1;
			if (p.getId() == g.getPlayer1()) {
				deckId = g.getDeck1();
				if (g.getTurn()%2 == 1) {
					throw new Exception("not turn");
				}
			} else if (p.getId() == g.getPlayer2()) {
				deckId = g.getDeck2();
				if (g.getTurn()%2 == 0) {
					throw new Exception("not turn");
				}
			} else {
				throw new Exception("not in the game");
			}

			List<IGameCard> gcs = GameCardInputMapper.find(this.gameId, deckId, "deck");
			if (gcs.size() < 1) {
				throw new Exception("no cards");
			}
			IGameCard gc = gcs.get(0);
			gc.setStatus("hand");
			new GameCardOutputMapper().update(GameCardFactory.createClean(gc.getId(), gc.getVersion(), gc.getDeckId(), gc.getCardId(), gc.getDeckId(), gc.getStatus()));

			g.setTurn(g.getTurn()+1);
			new GameOutputMapper().update(g);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't read");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
