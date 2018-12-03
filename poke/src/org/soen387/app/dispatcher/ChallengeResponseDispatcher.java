package org.soen387.app.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.uow.UoW;
import org.soen387.app.command.ChallengeResponseCommand;

public class ChallengeResponseDispatcher extends Dispatcher {
	public void execute() throws ServletException, IOException {
		if (myRequest.getSession(true).getAttribute(RequestAttributes.CURRENT_USER_ID) == null) {
			myHelper.setRequestAttribute("message", "no login");
			forward("/WEB-INF/jsp/fail.jsp");
			return;
		}

		try {
			ChallengeResponseCommand c = new ChallengeResponseCommand(myHelper);
			c.playerId = (Long)myRequest.getSession(true).getAttribute(RequestAttributes.CURRENT_USER_ID);
			c.challengeId = Long.parseLong((String)myRequest.getAttribute("challengeId"));
			c.status = (String)myRequest.getAttribute("action");
			c.execute();
			UoW.getCurrent().commit();
			myHelper.setRequestAttribute("message", "responded");
			forward("/WEB-INF/jsp/success.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			myHelper.setRequestAttribute("message", e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}
}
