package org.soen387.app.dispatcher.challenge;

import java.io.IOException;
import javax.servlet.ServletException;
import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;

public class ChallengePlayer extends Dispatcher {
	public void execute() throws ServletException, IOException {
		myRequest.setAttribute("message", "not implemented");
		forward("/WEB-INF/jsp/fail.jsp");
	}
}
