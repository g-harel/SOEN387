package org.soen387.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.Servlet;
import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.service.MySQLConnectionFactory;
import org.dsrg.soenea.service.logging.Logging;
import org.dsrg.soenea.service.threadLocal.DbRegistry;
import org.dsrg.soenea.service.threadLocal.ThreadLocalTracker;
import org.dsrg.soenea.uow.MapperFactory;
import org.dsrg.soenea.uow.UoW;
import org.soen387.app.dispatcher.ManageDecksDispatcher;
import org.soen387.app.dispatcher.PlayerListDispatcher;
import org.soen387.app.dispatcher.LoginDispatcher;
import org.soen387.app.dispatcher.LogoutDispatcher;
import org.soen387.app.dispatcher.ManageDeckDispatcher;
import org.soen387.app.dispatcher.RegisterDispatcher;
import org.soen387.model.card.Card;
import org.soen387.model.card.CardOutputMapper;
import org.soen387.model.deck.Deck;
import org.soen387.model.deck.DeckOutputMapper;
import org.soen387.model.player.Player;
import org.soen387.model.player.PlayerOutputMapper;

@WebServlet("/Poke/*")
public class FrontController extends Servlet {
	private static final long serialVersionUID = 1458841306687817284L;

	public static void prepareDbRegistry() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			MySQLConnectionFactory f = new MySQLConnectionFactory(null, null, null, null);
			f.defaultInitialization();
			DbRegistry.setTablePrefix("");
			DbRegistry.setConFactory(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    FrontController.prepareDbRegistry();

		MapperFactory myDomain2MapperMapper = new MapperFactory();
		myDomain2MapperMapper.addMapping(Player.class, PlayerOutputMapper.class);
		myDomain2MapperMapper.addMapping(Deck.class, DeckOutputMapper.class);
		myDomain2MapperMapper.addMapping(Card.class, CardOutputMapper.class);
		UoW.initMapperFactory(myDomain2MapperMapper);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
		try {
			this.preProcessRequest(request, response);

			UoW.newCurrent();

			DbRegistry.getDbConnection().setAutoCommit(false);
			DbRegistry.getDbConnection().createStatement().execute("START TRANSACTION;");

			Logging.log("Path: " + request.getPathInfo());
			Dispatcher d = FrontController.getDispatcher(request);
			d.init(request, response);
			d.execute();

			this.postProcessRequest(request, response);

			DbRegistry.getDbConnection().createStatement().execute("ROLLBACK;");
			DbRegistry.closeDbConnectionIfNeeded();

			ThreadLocalTracker.purgeThreadLocal();
		} catch (Exception e) {
			Logging.logError(e);
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/fail.jsp").include(request, response);
		}
	}

	private static boolean match(String pattern, HttpServletRequest request, String pathParams) {
		String path = request.getPathInfo();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(path);
		
		if (m.matches() && pathParams != null && !pathParams.equals("")) {
			String[] params = pathParams.split(", ");
			for (int i = 0; i < params.length; i++) {
				request.setAttribute(params[i], m.group(i+1));
			}
		}
		
		return m.matches();
	}

	private static boolean match(String pattern, HttpServletRequest request) {
		return FrontController.match(pattern, request, "");
	}

	private static Dispatcher getDispatcher(HttpServletRequest request) throws Exception {
		if (FrontController.match("/Player/Register", request)) return new RegisterDispatcher();
		if (FrontController.match("/Player/Login", request)) return new LoginDispatcher();
		if (FrontController.match("/Player/Logout", request)) return new LogoutDispatcher();
		if (FrontController.match("/Player", request)) return new PlayerListDispatcher();
		
		if (FrontController.match("/Deck/(\\d+)", request, "deckId")) return new ManageDeckDispatcher();
		if (FrontController.match("/Deck", request)) return new ManageDecksDispatcher();

		// if (FrontController.match("/Player/(\\d+)/Challenge", path)) return new ChallengePlayerDispatcher();
		// if (FrontController.match("/Challenge/(\\d+)/(Accept)", path)) return new ChallengePlayerDispatcher();

		throw new Exception("no matching dispatcher for '" + request.getPathInfo() + "'");
	}
}
