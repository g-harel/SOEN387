package org.soen387.ser;

import org.soen387.app.FrontController;
import org.soen387.model.card.CardTDG;
import org.soen387.model.challenge.ChallengeTDG;
import org.soen387.model.deck.DeckTDG;
import org.soen387.model.game.GameTDG;
import org.soen387.model.gamecard.GameCardTDG;
import org.soen387.model.player.PlayerTDG;

public class RenewDatabase {
	public static void main(String[] args) {
		FrontController.prepareDbRegistry();

		try {
			CardTDG.dropTable();
			DeckTDG.dropTable();
			PlayerTDG.dropTable();
			ChallengeTDG.dropTable();
			GameTDG.dropTable();
			GameCardTDG.dropTable();
		} catch(Exception e) {
			e.printStackTrace();
		}

		try {
			PlayerTDG.createTable();
			DeckTDG.createTable();
			CardTDG.createTable();
			ChallengeTDG.createTable();
			GameTDG.createTable();
			GameCardTDG.createTable();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
