package org.pokeapp.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.util.Database;

@WebServlet("/Login")
public class Login extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		
		try {
			Connection conn = Database.getConnection();
			
	        Statement statement = conn.createStatement();
	        
	        ResultSet resultSet = statement.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES;");
	        if (resultSet.next()) {
	        	String name = resultSet.getString("TABLE_NAME");
	        	System.out.println(name);
	        }
	        
	        resultSet.close();
	        statement.close();
	        conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		if(user == null || user.isEmpty() || pass == null || pass.isEmpty() ) {
			request.setAttribute("message", "Please enter both a username and a password.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
		} else if(pass.equals(Register.registeredMap.get(user))) {
			request.setAttribute("message", "Successfully logged in.");
			request.getSession(true).setAttribute("login", user);
			request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
		} else {
			request.setAttribute("message", "I do not recognize that username and password combination.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);	
	}
}
