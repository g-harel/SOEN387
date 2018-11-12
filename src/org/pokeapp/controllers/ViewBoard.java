package org.pokeapp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.HandRDG;
import org.pokeapp.util.View;

@WebServlet("/ViewBoard")
public class ViewBoard extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String game = req.getParameter("game");

		if (game == null || game.isEmpty()) {
			new View(req, res).fail("Please enter a game.");
			return;
		}

		Object playerId = req.getSession(true).getAttribute("userid");
		if (playerId == null) {
			new View(req, res).fail("User is not logged in.");
			return;
		}

		ArrayList<HandRDG> hands = HandRDG.find(Integer.parseInt(game));
		if (hands.size() < 2) {
			new View(req, res).fail("Game is invalid.");
			return;
		}

		// Reverse the order for the tests.
		Collections.reverse(hands);

		boolean isInGame = false;
		for (HandRDG h : hands) {
			if (h.getPlayerId() == (int)playerId) {
				isInGame = true;
			}
		}
		if (!isInGame) {
			new View(req, res).fail("Player is not in game.");
			return;
		}


		new View(req, res).board(hands.get(0).getGameId(), hands);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
