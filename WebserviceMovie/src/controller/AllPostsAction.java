package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import bean.CommentBean;
import bean.PostAndCommentsBean;
import bean.PostBean;
import model.CommentDAO;
import model.Model;
import model.PostDAO;

public class AllPostsAction extends Action {

	PostDAO postDAO;
	CommentDAO commentDAO;
	
	public AllPostsAction(Model model) {
		postDAO = model.getPostDAO();
		commentDAO = model.getCommentDAO();
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
				PostAndCommentsBean postAndComment = new PostAndCommentsBean();
				postAndComment.setCategory(allPosts[i].getCategory());
				postAndComment.setContent(allPosts[i].getContent());
				postAndComment.setImagePath(allPosts[i].getImagePath());
				postAndComment.setPost_id(allPosts[i].getPost_id());
				postAndComment.setUser_id(allPosts[i].getUser_id());
				
				CommentBean[] comments = commentDAO.getCommentsByPostId(allPosts[i].getPost_id());
				List<CommentBean> tmpComments = new ArrayList<CommentBean>();
				for (CommentBean comment : comments) {
					tmpComments.add(comment);
				}
				postAndComment.setCommets(tmpComments);
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
