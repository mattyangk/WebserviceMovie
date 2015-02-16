package controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import bean.PostBean;
import bean.UserBean;
import model.Model;
import model.PostDAO;

public class PostAction extends Action {

	PostDAO postDAO;
	
	public PostAction(Model model) {
		postDAO = model.getPostDAO();
	}
	
	@Override
	public String getName() {
		return "post.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		String comment = request.getParameter("comment");
		String ori_poster = request.getParameter("ori_poster");
		String ori_text = request.getParameter("ori_text");
		String imagePath = request.getParameter("imagePath");
		String category = request.getParameter("category");
		String isRepost = request.getParameter("isRepost");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		
		PostBean post = new PostBean();
		post.setCategory(category);
		post.setContent(comment+"//@"+ori_poster + " " + ori_text);
		post.setImagePath(imagePath);
		post.setUser_id(user.getUser_id());
		
		System.out.println("is repost?" + isRepost);
		
		try {
			postDAO.create(post);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "homePage.do";
		
	}

}
