package org.pokeapp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.util.View;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String user = (String)req.getSession(true).getAttribute("login");

		new View(req, res).success("User '" + user + "' has been successfully logged out.");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);	
	}
}
