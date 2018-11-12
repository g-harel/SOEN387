package org.pokeapp.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.HandRDG;
import org.pokeapp.util.View;

@WebServlet("/ViewHand")
public class ViewHand extends HttpServlet {
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
		for (HandRDG h : hands) {
			if (h.getPlayerId() == (int)playerId) {
				ArrayList<Integer> cards = new ArrayList<Integer>();
				for (int i = 0; i < h.getHandSize(); i++) {
					cards.add(i);
				}
				new View(req, res).hand(cards);
				return;
			}
		}

		new View(req, res).fail("Could not find hand.");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
