package org.soen387.app.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.domain.command.CommandException;
import org.soen387.app.command.LoginCommand;

public class LoginDispatcher extends Dispatcher {
	public void execute() throws ServletException, IOException {
		LoginCommand c = new LoginCommand(myHelper);
		try {
			myRequest.getSession(true).invalidate();
			c.execute();
			myRequest.getSession(true).setAttribute(RequestAttributes.CURRENT_USER_ID, c.currentPlayer.getId());
			myHelper.setRequestAttribute("message", "logged in");
			forward("/WEB-INF/jsp/success.jsp");
		} catch (CommandException e) {
			e.printStackTrace();
			myHelper.setRequestAttribute("message", e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}
}
