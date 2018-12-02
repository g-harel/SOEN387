package org.soen387.app.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.uow.UoW;

public class RegisterDispatcher extends Dispatcher {
	public void execute() throws ServletException, IOException {
		org.soen387.app.command.RegisterCommand c = new org.soen387.app.command.RegisterCommand(myHelper);
		try {
			myRequest.getSession(true).invalidate();
			c.execute();
			myRequest.getSession(true).setAttribute(RequestAttributes.CURRENT_USER_ID, c.currentPlayer.getId());
			UoW.getCurrent().commit();
			myHelper.setRequestAttribute("message", "registered");
			forward("/WEB-INF/jsp/success.jsp");
		} catch (Exception e) {
			myHelper.setRequestAttribute("message", e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}
}
