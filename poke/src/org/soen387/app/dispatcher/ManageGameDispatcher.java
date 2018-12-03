package org.soen387.app.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.uow.UoW;
import org.soen387.app.command.RetireCommand;
import org.soen387.app.command.ViewDeckCommand;

public class ManageGameDispatcher extends Dispatcher {
	public void execute() throws ServletException, IOException {
		if (myRequest.getSession(true).getAttribute(RequestAttributes.CURRENT_USER_ID) == null) {
			myHelper.setRequestAttribute("message", "no login");
			forward("/WEB-INF/jsp/fail.jsp");
			return;
		}

		try {
			String action = (String)myRequest.getAttribute("action");
			long gameId = Long.parseLong((String)myRequest.getAttribute("gameId"));
			if (action.equals("Retire")) {
				RetireCommand c = new RetireCommand(myHelper);
				c.gameId = gameId;
				c.playerId = (Long)myRequest.getSession(true).getAttribute(RequestAttributes.CURRENT_USER_ID);
				c.execute();
				UoW.getCurrent().commit();
				myHelper.setRequestAttribute("message", "retired");
				forward("/WEB-INF/jsp/success.jsp");
			} else {
				throw new Exception("no matching action");
			}
		} catch (Exception e) {
			e.printStackTrace();
			myHelper.setRequestAttribute("message", e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}
}
