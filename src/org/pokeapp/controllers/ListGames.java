package org.pokeapp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.GameRDG;
import org.pokeapp.gateways.HandRDG;
import org.pokeapp.util.View;

@WebServlet("/ListGames")
public class ListGames extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Object playerId = req.getSession(true).getAttribute("userid");
		if (playerId == null) {
			new View(req, res).fail("User is not logged in.");
			return;
		}

		ArrayList<GameRDG> games = GameRDG.find();
		for (GameRDG g : games) {
			ArrayList<HandRDG> hands = HandRDG.find(g.getId());

			// Reverse the order for the tests.
			Collections.reverse(hands);

			for (HandRDG h : hands) {
				g.addPlayer(h.getPlayerId());
			}
		}

		new View(req, res).games(games);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
