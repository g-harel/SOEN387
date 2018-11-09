package org.pokeapp.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
