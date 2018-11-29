package org.pokeapp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.HandRDG;
import org.pokeapp.util.View;

@WebServlet("/DrawCard")
public class DrawCard extends HttpServlet {
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
		
		HandRDG updatedHand = HandRDG.draw(Integer.parseInt(game), (int)playerId);
		if (updatedHand == null) {
			new View(req, res).fail("Could not draw.");
			return;
		}

		new View(req, res).success("Successfully drew card.");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
