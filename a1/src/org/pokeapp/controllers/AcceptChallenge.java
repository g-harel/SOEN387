package org.pokeapp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.ChallengeRDG;
import org.pokeapp.gateways.DeckRDG;
import org.pokeapp.gateways.GameRDG;
import org.pokeapp.gateways.HandRDG;
import org.pokeapp.util.View;

@WebServlet("/AcceptChallenge")
public class AcceptChallenge extends HttpServlet {
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
		if (existingChallenge.getChallengee() != (int)playerId) {
			new View(req, res).fail("User is not the challengee.");
			return;
		}
		if (existingChallenge.getStatus() != ChallengeRDG.STATUS_OPEN) {
			new View(req, res).fail("Challenge is not open.");
			return;
		}
		
		ChallengeRDG acceptedChallenge = ChallengeRDG.updateStatus(Integer.parseInt(challenge), ChallengeRDG.STATUS_ACCEPTED);
		if (acceptedChallenge == null) {
			new View(req, res).fail("Could not accept challenge.");
			return;
		}
		
		GameRDG game = GameRDG.insert();
		if (game == null) {
			new View(req, res).fail("Could not create game.");
			return;
		}
		
		DeckRDG challengerDeck = DeckRDG.findByPlayer(acceptedChallenge.getChallenger());
		if (challengerDeck == null) {
			new View(req, res).fail("Could not read challenger deck.");
			return;
		}
		
		HandRDG challengeeHand = HandRDG.insert(
			game.getId(),
			acceptedChallenge.getChallengee(),
			challengeeDeck.getId(),
			HandRDG.STATUS_PLAYING,
			0,
			challengerDeck.getCards().size(),
			0,
			""
		);
		if (challengeeHand == null) {
			new View(req, res).fail("Could not create challengee hand.");
			return;
		}
		
		HandRDG challengerHand = HandRDG.insert(
			game.getId(),
			acceptedChallenge.getChallenger(),
			challengerDeck.getId(),
			HandRDG.STATUS_PLAYING,
			0,
			challengerDeck.getCards().size(),
			0,
			""
		);
		if (challengerHand == null) {
			new View(req, res).fail("Could not create challenger hand.");
			return;
		}

		new View(req, res).success("Challenge accepted.");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
