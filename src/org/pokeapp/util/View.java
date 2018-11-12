package org.pokeapp.util;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pokeapp.gateways.ChallengeRDG;
import org.pokeapp.gateways.DeckRDG;
import org.pokeapp.gateways.GameRDG;
import org.pokeapp.gateways.HandRDG;
import org.pokeapp.gateways.PlayerRDG;

public class View {
	private static void forward(String path, HttpServletRequest req, HttpServletResponse res) {
		try {
			req.getRequestDispatcher(path).forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				res.sendError(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private HttpServletRequest req;
	private HttpServletResponse res;

	public View(HttpServletRequest req, HttpServletResponse res) {
		this.req = req;
		this.res = res;
	}

	public void fail(String message) {
		this.req.setAttribute("message", message);
		View.forward("WEB-INF/jsp/fail.jsp", this.req, this.res);
	}

	public void success(String message) {
		this.req.setAttribute("message", message);
		View.forward("WEB-INF/jsp/success.jsp", this.req, this.res);
	}

	public void players(ArrayList<PlayerRDG> players) {
		this.req.setAttribute("players", players);
		View.forward("WEB-INF/jsp/players.jsp", this.req, this.res);
	}

	public void challenges(ArrayList<ChallengeRDG> challenges) {
		this.req.setAttribute("challenges", challenges);
		View.forward("WEB-INF/jsp/challenges.jsp", this.req, this.res);
	}
	
	public void deck(DeckRDG deck) {
		this.req.setAttribute("deck", deck);
		View.forward("WEB-INF/jsp/deck.jsp", this.req, this.res);
	}
	
	public void games(ArrayList<GameRDG> games) {
		this.req.setAttribute("games", games);
		View.forward("WEB-INF/jsp/games.jsp", this.req, this.res);
	}

	public void board(int gameId, ArrayList<HandRDG> hands) {
		this.req.setAttribute("hands", hands);
		this.req.setAttribute("gameId", gameId);
		View.forward("WEB-INF/jsp/board.jsp", this.req, this.res);
	}
}
