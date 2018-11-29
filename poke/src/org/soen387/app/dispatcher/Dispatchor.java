package org.soen387.app.dispatcher;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;

public abstract class Dispatchor extends Dispatcher {
	private void fwd(String path) {
		try {
			forward(path);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				myResponse.sendError(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void jspFail(String message) {
		myRequest.getSession(true).setAttribute("message", message);
		fwd("WEB-INF/jsp/fail.jsp");
	}

	public void jspSuccess(String message) {
		myRequest.getSession(true).setAttribute("message", message);
		fwd("WEB-INF/jsp/success.jsp");
	}

	//public void players(ArrayList<PlayerRDG> players) {
	//	this.req.setAttribute("players", players);
	//	View.forward("WEB-INF/jsp/players.jsp", this.req, this.res);
	//}

	//public void challenges(ArrayList<ChallengeRDG> challenges) {
	//	this.req.setAttribute("challenges", challenges);
	//	View.forward("WEB-INF/jsp/challenges.jsp", this.req, this.res);
	//}

	//public void deck(DeckRDG deck) {
	//	this.req.setAttribute("deck", deck);
	//	View.forward("WEB-INF/jsp/deck.jsp", this.req, this.res);
	//}

	//public void games(ArrayList<GameRDG> games) {
	//	this.req.setAttribute("games", games);
	//	View.forward("WEB-INF/jsp/games.jsp", this.req, this.res);
	//}

	//public void board(int gameId, ArrayList<HandRDG> hands) {
	//	this.req.setAttribute("hands", hands);
	//	this.req.setAttribute("gameId", gameId);
	//	View.forward("WEB-INF/jsp/board.jsp", this.req, this.res);
	//}

	//public void hand(ArrayList<Integer> hand) {
	//	this.req.setAttribute("hand", hand);
	//	View.forward("WEB-INF/jsp/hand.jsp", this.req, this.res);
	//}
}
