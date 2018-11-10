package org.pokeapp.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.PlayerRDG;
import org.pokeapp.util.View;

@WebServlet("/Register")
public class Register extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String user = req.getParameter("user");
		String pass = req.getParameter("pass");

		if (user == null || user.isEmpty() || pass == null || pass.isEmpty()) {
			new View(req, res).fail("Please enter both a username and a password.");
			return;
		}

		PlayerRDG existingPlayer = PlayerRDG.find(user);
		if (existingPlayer != null) {
			new View(req, res).fail("That user has already registered.");
			return;
		}

		PlayerRDG newPlayer = PlayerRDG.insert(user, pass);
		if (newPlayer == null) {
			new View(req, res).fail("User could not be registered.");
			return;
		}

		req.getSession(true).setAttribute("id", newPlayer.getId());
		new View(req, res).success("User was successfully registered.");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
	}
}
