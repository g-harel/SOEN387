package org.soen387.app.command;

import java.util.List;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.card.CardInputMapper;
import org.soen387.model.card.ICard;
import org.soen387.model.challenge.Challenge;
import org.soen387.model.challenge.ChallengeInputMapper;
import org.soen387.model.challenge.ChallengeOutputMapper;
import org.soen387.model.game.Game;
import org.soen387.model.game.GameFactory;
import org.soen387.model.gamecard.GameCard;
import org.soen387.model.gamecard.GameCardFactory;
import org.soen387.model.gamecard.GameCardOutputMapper;

public class ChallengeResponseCommand extends Command {
	public List<ICard> cards;
	public long playerId;
	public long challengeId;
	public String status;
	
	public ChallengeResponseCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {
		String deckId = this.helper.getString("deck");
		String challengeVersion = this.helper.getString("version");
		
		if (challengeVersion == null || challengeVersion.equals("")) throw new CommandException("missing challengeVersion");

		try {
			Challenge c = ChallengeInputMapper.find(this.challengeId);
			if (c.getVersion() != Long.parseLong(challengeVersion)) {
				throw new Exception("wrong version");
			}

			if (c.getStatus() != 0) {
				throw new Exception("already responded");
			}

			int status;
			if (this.status.equals("Accept")) {
				if (deckId == null || deckId.equals("")) throw new CommandException("missing deckId");
				status = 3;
			} else if (this.status.equals("Refuse")) {
				status = 1;
			} else if (this.status.equals("Withdraw")) {
				status = 2;
			} else {
				throw new Exception("bad status");
			}
			c.setStatus(status);
			
			if (c.getChallengee() != this.playerId && status != 2) {
				throw new Exception("not challengee");
			}

			new ChallengeOutputMapper().update(c);
			
			// challenger is player1
			Game g = GameFactory.createNew(0, c.getChallenger(), c.getDeckId(), "playing", c.getChallengee(), Long.parseLong(deckId), "playing");

			boolean first1 = true;
			for (ICard card : CardInputMapper.findByDeck(g.getDeck1())) {
				String s = "deck";
				if (first1) {
					s = "hand";
					first1 = false;
				}
				GameCardFactory.createNew(g.getId(), card.getId(), g.getDeck1(), s);
			}
			for (ICard card : CardInputMapper.findByDeck(g.getDeck2())) {
				GameCardFactory.createNew(g.getId(), card.getId(), g.getDeck2(), "deck");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't read");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
