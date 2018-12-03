package org.soen387.app.dispatcher;

import java.io.IOException;
import javax.servlet.ServletException;
import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.uow.UoW;
import org.soen387.app.command.UploadDeckCommand;
import org.soen387.app.command.ViewDecksCommand;

public class ManageDeckDispatcher extends Dispatcher {
	public void execute() throws ServletException, IOException {
		if (myRequest.getSession(true).getAttribute(RequestAttributes.CURRENT_USER_ID) == null) {
			myHelper.setRequestAttribute("message", "no login");
			forward("/WEB-INF/jsp/fail.jsp");
			return;
		}

		try {			
			if (myRequest.getMethod().equals("POST")) {
				new UploadDeckCommand(myHelper).execute();
				UoW.getCurrent().commit();
				myHelper.setRequestAttribute("message", "uploaded");
				forward("/WEB-INF/jsp/success.jsp");
			} else if (myRequest.getMethod().equals("GET")) {
				ViewDecksCommand c = new ViewDecksCommand(myHelper);
				c.execute();
				UoW.getCurrent().commit();
				myHelper.setRequestAttribute("decks", c.decks);
				forward("/WEB-INF/jsp/decks.jsp");
			} else {
				myHelper.setRequestAttribute("message", "bad method");
				forward("/WEB-INF/jsp/fail.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			myHelper.setRequestAttribute("message", e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}
}
