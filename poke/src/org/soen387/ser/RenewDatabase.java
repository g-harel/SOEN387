package org.soen387.ser;

import org.soen387.app.FrontController;
import org.soen387.model.card.CardTDG;
import org.soen387.model.deck.DeckTDG;
import org.soen387.model.player.PlayerTDG;

public class RenewDatabase {
	public static void main(String[] args) {
		FrontController.prepareDbRegistry();

		try {
			CardTDG.dropTable();
			DeckTDG.dropTable();
			PlayerTDG.dropTable();
		} catch(Exception e) {
			e.printStackTrace();
		}

		try {
			PlayerTDG.createTable();
			DeckTDG.createTable();
			CardTDG.createTable();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
