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
import org.soen387.player.Player;
import org.soen387.player.PlayerOutputMapper;
import org.soen387.player.PlayerRegisterDispatcher;

@WebServlet("/Poke/*")
public class FrontController extends Servlet {
	private static final long serialVersionUID = 1458841306687817284L;
	
	public static void prepareDbRegistry() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			MySQLConnectionFactory f = new MySQLConnectionFactory(null, null, null, null);
			f.defaultInitialization();
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
		UoW.initMapperFactory(myDomain2MapperMapper);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
		try {
			this.preProcessRequest(request, response);

			UoW.newCurrent();

			DbRegistry.getDbConnection().setAutoCommit(false);
			DbRegistry.getDbConnection().createStatement().execute("START TRANSACTION;");
			
			String path = request.getPathInfo();
			Logging.log("Path: " + path);
			Dispatcher d = FrontController.getDispatcher(path);
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
	
	private static boolean match(String pattern, String path) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(path);
		return m.matches();
	}
	
	private static Dispatcher getDispatcher(String path) throws Exception {
		if (FrontController.match("/Player/Register", path)) return new PlayerRegisterDispatcher();
		// if (FrontController.match("Poke/Player/Login", path)) return new PlayerLoginDispatcher();
		// if (FrontController.match("Poke/Player/Logout", path)) return new PlayerLogoutDispatcher();
		// if (FrontController.match("Poke/Deck", path)) return new DeckManageDispatcher();
		// if (FrontController.match("Poke/Player", path)) return new PlayerListDispatcher();
		
		// TODO match groups
		// if (FrontController.match("Poke/Deck/(\\d+)", path)) return new DeckManageDispatcher();
		// if (FrontController.match("Poke/Player/(\\d+)/Challenge", path)) return new ChallengePlayerDispatcher();
		// if (FrontController.match("Poke/Challenge/(\\d+)/(Accept)", path)) return new ChallengePlayerDispatcher();

		throw new Exception("no matching dispatcher");
	}
}
