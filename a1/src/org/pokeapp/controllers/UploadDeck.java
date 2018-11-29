package org.pokeapp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.DeckRDG;
import org.pokeapp.util.View;

@WebServlet("/UploadDeck")
public class UploadDeck extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String cards = req.getParameter("deck");

		if (cards == null || cards.isEmpty()) {
			new View(req, res).fail("Please enter a deck.");
			return;
		}

		Object user = req.getSession(true).getAttribute("userid");
		if (user == null) {
			new View(req, res).fail("User is not logged in.");
			return;
		}
		
		DeckRDG newDeck = DeckRDG.insert((int)user, cards);
		if (newDeck == null) {
			new View(req, res).fail("Deck was not created.");
			return;
		}

		new View(req, res).success("Deck was successfully uploaded.");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
