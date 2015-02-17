package controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import model.CommentDAO;
import model.Model;
import bean.CommentBean;
import bean.UserBean;

public class CommentAction extends Action {
	
	CommentDAO commentDAO;
	
	public CommentAction(Model model) {
		commentDAO = model.getCommentDAO();
	}

	@Override
	public String getName() {
		return "comment.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		
		String content = request.getParameter("content");
		String post_id = request.getParameter("post_id");
		Date date = new Date();
		
		CommentBean comment = new CommentBean();
		comment.setComment_time(date);
		comment.setContent(content);
		comment.setPost_id(Integer.parseInt(post_id));
		comment.setUser_id(user.getUser_id());
		
		try {
			commentDAO.create(comment);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "allPostsPage.jsp";
		

	}

}
