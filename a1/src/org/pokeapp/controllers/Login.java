package org.pokeapp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.PlayerRDG;
import org.pokeapp.util.View;

@WebServlet("/Login")
public class Login extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String user = req.getParameter("user");
		String pass = req.getParameter("pass");

		if (user == null || user.isEmpty() || pass == null || pass.isEmpty()) {
			new View(req, res).fail("Please enter both a username and password.");
			return;
		}

		PlayerRDG existingPlayer = PlayerRDG.find(user, pass);
		if (existingPlayer == null) {
			new View(req, res).fail("Unrecognized username and password combination.");
			return;
		}
		
		req.getSession(true).setAttribute("userid", existingPlayer.getId());
		new View(req, res).success("User has been successfully logged in.");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);	
	}
}
