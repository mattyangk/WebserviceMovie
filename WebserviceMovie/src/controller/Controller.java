package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UserBean;
import model.Model;

@SuppressWarnings("serial")
@MultipartConfig
public class Controller extends HttpServlet {

	public void init() throws ServletException {
		Model model = new Model(getServletConfig());

		Action.add(new LoginAction(model));
		Action.add(new GetMoviesAction(model));
		Action.add(new MovieHomeAction(model));
		Action.add(new PersonalInfoAction(model));
		Action.add(new SearchAction(model));
		Action.add(new LoginTwitterAction(model));
		Action.add(new StartTwitterLogin(model));
		Action.add(new LoginFlickerAction(model));
		Action.add(new StartFlickerLogin(model));
		Action.add(new PersonalInfoAction(model));
		Action.add(new AnalysisAction(model));
		Action.add(new PostAction(model));
		Action.add(new AllPostsAction(model));
		Action.add(new CommentAction(model));
		Action.add(new PostNewAction(model));

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextPage = performTheAction(request);
		sendToNextPage(nextPage, request, response);
	}

	/*
	 * Extracts the requested action and (depending on whether the user is
	 * logged in) perform it (or make the user login).
	 * 
	 * @param request
	 * 
	 * @return the next page (the view)
	 */
	private String performTheAction(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String servletPath = request.getServletPath();
		UserBean user = (UserBean) session.getAttribute("user");
		String action = getActionName(servletPath);
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		// System.out.println("servletPath="+servletPath+" requestURI="+request.getRequestURI()+"  user="+user);
		if (action.equals("aboutUs.do")) {
			return "aboutUs.jsp";
		}

		if (action.equals("contactUs.do")) {
			return "contactUs.jsp";
		}

		if (action.equals("index.do")) {
			// Allow these actions without logging in

			return Action.perform(action, request);
		}

		// Let the logged in user run his chosen action
		return Action.perform(action, request);
	}

	/*
	 * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
	 * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
	 * page (the view) This is the common case
	 */
	private void sendToNextPage(String nextPage, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					request.getServletPath());
			return;
		}

		if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
		}

		if (nextPage.endsWith(".jsp")) {
			RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
					+ nextPage);
			d.forward(request, response);
			return;
		}
		if (nextPage.contains("?")) {
//			String prefix = nextPage.substring(0, nextPage.indexOf('?'));
			response.sendRedirect(nextPage);
			return;
		}

		if (nextPage.startsWith("https://")) {
			response.sendRedirect(nextPage);
			return;
		}
		if (nextPage.startsWith("http://")) {
			response.sendRedirect(nextPage);
			return;
		}

		throw new ServletException(Controller.class.getName()
				+ ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
	}

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
	private String getActionName(String path) {
		// We're guaranteed that the path will start with a slash
		int slash = path.lastIndexOf('/');
		return path.substring(slash + 1);
	}
}
