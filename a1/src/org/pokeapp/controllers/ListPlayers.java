package org.pokeapp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.PlayerRDG;
import org.pokeapp.util.View;

@WebServlet("/ListPlayers")
public class ListPlayers extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (req.getSession(true).getAttribute("userid") == null) {
			new View(req, res).fail("User is not logged in.");
			return;
		}

		new View(req, res).players(PlayerRDG.find());
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);	
	}
}
