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

@WebServlet("/RefuseChallenge")
public class RefuseChallenge extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String challenge = req.getParameter("challenge");

		if (challenge == null || challenge.isEmpty()) {
			new View(req, res).fail("Please enter a challenge.");
			return;
		}

		Object playerId = req.getSession(true).getAttribute("userid");
		if (playerId == null) {
			new View(req, res).fail("User is not logged in.");
			return;
		}
		
		DeckRDG challengeeDeck = DeckRDG.findByPlayer((int)playerId);
		if (challengeeDeck == null) {
			new View(req, res).fail("User does not have a deck.");
			return;
		}
		
		ChallengeRDG existingChallenge = ChallengeRDG.find(Integer.parseInt(challenge));
		if (existingChallenge == null) {
			new View(req, res).fail("Could not find challenge.");
			return;
		}
		if (existingChallenge.getChallengee() != (int)playerId && existingChallenge.getChallenger() != (int)playerId) {
			new View(req, res).fail("User is not the challenger or the challengee.");				
			return;
		}
		if (existingChallenge.getStatus() != ChallengeRDG.STATUS_OPEN) {
			new View(req, res).fail("Challenge is not open.");
			return;
		}
		
		int newStatus = existingChallenge.getChallengee() == (int)playerId
			? ChallengeRDG.STATUS_REFUSED
			: ChallengeRDG.STATUS_WITHDRAWN;
		
		ChallengeRDG refusedChallenge = ChallengeRDG.updateStatus(Integer.parseInt(challenge), newStatus);
		if (refusedChallenge == null) {
			new View(req, res).fail("Could not refuse challenge.");
			return;
		}

		new View(req, res).success("Challenge refused.");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
