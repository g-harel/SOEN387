package org.soen387.ser;

import org.soen387.app.PokeFC;
import org.soen387.domain.model.player.PlayerTDG;

public class RenewDatabase {
	public static void main(String[] args) {
		PokeFC.prepareDbRegistry("");

		try {
			PlayerTDG.dropTable();
		} catch(Exception e) {
			e.printStackTrace();
		}

		try {
			PlayerTDG.createTable();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
