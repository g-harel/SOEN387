package org.soen387.player;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.domain.command.CommandException;

public class PlayerLoginDispatcher extends Dispatcher {
	public void execute() throws ServletException, IOException {
		PlayerLoginCommand c = new PlayerLoginCommand(myHelper);
		try {
			myRequest.getSession(true).invalidate();
			c.execute();
			myRequest.getSession(true).setAttribute(RequestAttributes.CURRENT_USER_ID, c.currentPlayer.getId());
			forward("/WEB-INF/jsp/success.jsp");
		} catch (CommandException e) {
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}
}
