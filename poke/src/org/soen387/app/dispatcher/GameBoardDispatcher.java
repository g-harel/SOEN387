package org.soen387.app.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.application.servlet.impl.RequestAttributes;
import org.dsrg.soenea.uow.UoW;
import org.soen387.app.command.GameBoardCommand;

public class GameBoardDispatcher extends Dispatcher {
	public void execute() throws ServletException, IOException {
		if (myRequest.getSession(true).getAttribute(RequestAttributes.CURRENT_USER_ID) == null) {
			myHelper.setRequestAttribute("message", "no login");
			forward("/WEB-INF/jsp/fail.jsp");
			return;
		}

		try {
			GameBoardCommand c = new GameBoardCommand(myHelper);
			c.gameId = Long.parseLong((String)myRequest.getAttribute("gameId"));
			c.playerId = (Long)myRequest.getSession(true).getAttribute(RequestAttributes.CURRENT_USER_ID);
			c.execute();
			UoW.getCurrent().commit();
			myHelper.setRequestAttribute("game", c.game);
			myHelper.setRequestAttribute("handsize1", c.hand1);
			myHelper.setRequestAttribute("handsize2", c.hand2);
			myHelper.setRequestAttribute("decksize1", c.deck1);
			myHelper.setRequestAttribute("decksize2", c.deck2);
			forward("/WEB-INF/jsp/board.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			myHelper.setRequestAttribute("message", e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}
}
