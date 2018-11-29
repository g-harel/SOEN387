package org.pokeapp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.ChallengeRDG;
import org.pokeapp.gateways.DeckRDG;
import org.pokeapp.util.View;

@WebServlet("/ChallengePlayer")
public class ChallengePlayer extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String player = req.getParameter("player");

		if (player == null || player.isEmpty()) {
			new View(req, res).fail("Please enter a player.");
			return;
		}

		Object challengerId = req.getSession(true).getAttribute("userid");
		if (challengerId == null) {
			new View(req, res).fail("User is not logged in.");
			return;
		}
		
		int challenger = (int)req.getSession(true).getAttribute("userid");
		int challengee = Integer.parseInt(player);
		
		if (challenger == challengee) {
			new View(req, res).fail("Cannot challenge self.");
			return;
		}
		
		DeckRDG challengerDeck = DeckRDG.findByPlayer(challenger);
		if (challengerDeck == null) {
			new View(req, res).fail("User does not have a deck.");
			return;
		}
		
		ChallengeRDG newChallenge = ChallengeRDG.insert(challenger, challengee);
		if (newChallenge == null) {
			new View(req, res).fail("Player could not be challenged.");
			return;
		}

		new View(req, res).success("Player was successfully challenged.");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
