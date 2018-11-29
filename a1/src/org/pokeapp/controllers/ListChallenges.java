package org.pokeapp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.ChallengeRDG;
import org.pokeapp.util.View;

@WebServlet("/ListChallenges")
public class ListChallenges extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (req.getSession(true).getAttribute("userid") == null) {
			new View(req, res).fail("User is not logged in.");
			return;
		}

		new View(req, res).challenges(ChallengeRDG.find());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
