package org.soen387.app.dispatcher;

import java.io.IOException;
import javax.servlet.ServletException;
import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.uow.UoW;
import org.soen387.app.command.ChallengePlayerCommand;

public class ChallengePlayerDispatcher extends Dispatcher {
	public void execute() throws ServletException, IOException {
		if (myRequest.getSession(true).getAttribute(RequestAttributes.CURRENT_USER_ID) == null) {
			myHelper.setRequestAttribute("message", "no login");
			forward("/WEB-INF/jsp/fail.jsp");
			return;
		}

		try {
			ChallengePlayerCommand c = new ChallengePlayerCommand(myHelper);
			c.playerId = (String)myRequest.getAttribute("playerId");
			c.execute();
			UoW.getCurrent().commit();
			myHelper.setRequestAttribute("message", "challenged");
			forward("/WEB-INF/jsp/success.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			myHelper.setRequestAttribute("message", e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}
}
