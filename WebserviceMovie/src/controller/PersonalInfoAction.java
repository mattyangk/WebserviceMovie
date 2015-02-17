package controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import bean.UserBean;
import model.UserDAO;
import model.Model;

public class PersonalInfoAction extends Action {
	
	UserDAO userDAO;
	
	public PersonalInfoAction(Model model) {
		userDAO = model.getUserDAO();
	}

	@Override
	public String getName() {
		return "homePage.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		int user_id = ((UserBean)request.getSession().getAttribute("user")).getUser_id();
		try {
			UserBean user = userDAO.read(user_id);
			
			
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "homePage.jsp";
	}

}
