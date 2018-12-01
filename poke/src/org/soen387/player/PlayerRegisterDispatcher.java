package org.soen387.player;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.uow.UoW;

public class PlayerRegisterDispatcher extends Dispatcher {
	public void execute() throws ServletException, IOException {		
		String user = myRequest.getParameter("user");
		if (user == null) {
			myRequest.setAttribute("message", "no user param");
			forward("/WEB-INF/jsp/fail.jsp");
			return;
		}

		String pass = myRequest.getParameter("pass");
		if (pass == null) {
			myRequest.setAttribute("message", "no pass param");
			forward("/WEB-INF/jsp/fail.jsp");
			return;
		}

		PlayerRegisterCommand c = new PlayerRegisterCommand(myHelper);
		try {
			myRequest.getSession(true).invalidate();
			c.execute();
			myRequest.getSession(true).setAttribute(RequestAttributes.CURRENT_USER_ID, c.currentPlayer.getId());
			UoW.getCurrent().commit();
			forward("/WEB-INF/jsp/success.jsp");
		} catch (Exception e) {
			myHelper.setRequestAttribute("message", e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}
}
