package org.soen387.player;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;

public class PlayerLogoutDispatcher extends Dispatcher {
	public void execute() throws ServletException, IOException {
		myRequest.getSession(true).invalidate();
		forward("/WEB-INF/jsp/success.jsp");
	}
}
