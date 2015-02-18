package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;

public class LogoutAction extends Action {
	
	public LogoutAction(Model model) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "logout.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		return "LoginAction.do";
	}

}
