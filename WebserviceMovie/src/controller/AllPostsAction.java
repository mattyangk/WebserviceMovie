package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import bean.CommentAndUserBean;
import bean.CommentBean;
import bean.PostAndCommentsBean;
import bean.PostBean;
import bean.UserBean;
import model.CommentDAO;
import model.Model;
import model.PostDAO;
import model.UserDAO;

public class AllPostsAction extends Action {

	PostDAO postDAO;
	CommentDAO commentDAO;
	UserDAO userDAO;
	
	public AllPostsAction(Model model) {
		postDAO = model.getPostDAO();
		commentDAO = model.getCommentDAO();
		userDAO = model.getUserDAO();
	}
	
	@Override
	public String getName() {
		return "allPostsPage.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		

		try {
			PostBean[] allPosts = postDAO.getAllPosts();
			if (allPosts == null || allPosts.length == 0) {
				return "allPostsPage.jsp";
			}
			PostAndCommentsBean[] allPostsAndComments = new PostAndCommentsBean[allPosts.length];
			for (int i = 0; i < allPosts.length; i++) {
				UserBean user = userDAO.read(allPosts[i].getUser_id());
				PostAndCommentsBean postAndComment = new PostAndCommentsBean();
				postAndComment.setCategory(allPosts[i].getCategory());
				postAndComment.setContent(allPosts[i].getContent());
				postAndComment.setImagePath(allPosts[i].getImagePath());
				postAndComment.setPost_id(allPosts[i].getPost_id());
				postAndComment.setUser_id(allPosts[i].getUser_id());
				postAndComment.setUser_photo_url(user.getImagePath());
				postAndComment.setUser_name(user.getUsername());
				
				CommentBean[] comments = commentDAO.getCommentsByPostId(allPosts[i].getPost_id());
				List<CommentAndUserBean> tmpComments = new ArrayList<CommentAndUserBean>();
				for (CommentBean comment : comments) {
					CommentAndUserBean commentAndUser = new CommentAndUserBean();
					UserBean commentUser = userDAO.read(comment.getUser_id());
					commentAndUser.setComment_id(comment.getComment_id());
					commentAndUser.setComment_time(comment.getComment_time());
					commentAndUser.setContent(comment.getContent());
					commentAndUser.setPost_id(comment.getPost_id());
					commentAndUser.setUser_id(comment.getUser_id());
					commentAndUser.setUser_name(user.getUsername());
					commentAndUser.setUser_photo_url(user.getImagePath());
					tmpComments.add(commentAndUser);
				}
				postAndComment.setComments(tmpComments);
				allPostsAndComments[i] = postAndComment;			
			}
			
			request.setAttribute("allPosts", allPostsAndComments);
			
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return "allPostsPage.jsp";
	}

}
