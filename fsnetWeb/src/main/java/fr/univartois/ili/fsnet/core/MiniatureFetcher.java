package fr.univartois.ili.fsnet.core;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.actions.utils.ImageManager;

/**
 * Servlet implementation class MiniatureFetcher
 */
public class MiniatureFetcher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo().split("\\.")[0].substring(1);
		ImageManager
				.sendUserMiniature(Integer.valueOf(path), request, response);
	}

}