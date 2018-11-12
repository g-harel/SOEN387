package org.pokeapp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.DeckRDG;
import org.pokeapp.util.View;

@WebServlet("/ViewDeck")
public class ViewDeck extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Object playerId = req.getSession(true).getAttribute("userid");
		if (playerId == null) {
			new View(req, res).fail("User is not logged in.");
			return;
		}
		
		DeckRDG deck = DeckRDG.findByPlayer((int)playerId);
		if (deck == null) {
			new View(req, res).fail("User does not have a deck.");
			return;
		}

		new View(req, res).deck(deck);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
