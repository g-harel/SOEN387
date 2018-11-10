package org.pokeapp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.ChallengeRDG;
import org.pokeapp.util.View;

@WebServlet("/ChallengePlayer")
public class ChallengePlayer extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String player = req.getParameter("player");

		if (player == null || player.isEmpty()) {
			new View(req, res).fail("Please enter a player.");
			return;
		}

		int challenger = (int)req.getSession(true).getAttribute("userid");
		int challengee = Integer.parseInt(player);
		
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
