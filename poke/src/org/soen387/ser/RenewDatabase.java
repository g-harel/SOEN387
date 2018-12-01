package org.soen387.ser;

import org.soen387.app.FrontController;
import org.soen387.player.PlayerTDG;

public class RenewDatabase {
	public static void main(String[] args) {
		FrontController.prepareDbRegistry();

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
