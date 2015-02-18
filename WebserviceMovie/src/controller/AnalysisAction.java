package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import bean.UserBean;
import bean.CommentBean;
import bean.PostBean;
import model.UserDAO;
import model.CommentDAO;
import model.PostDAO;
import model.Model;

public class AnalysisAction extends Action {
	private UserDAO userDAO;
	private CommentDAO commentDAO;
	private PostDAO postDAO;

	public AnalysisAction(Model model) {
		userDAO = model.getUserDAO();
		commentDAO = model.getCommentDAO();
		postDAO = model.getPostDAO();
	}

	@Override
	public String getName() {
		return "analysis.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		System.out.println(user);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		Date start_date = c.getTime();
		if (user != null) {
			int passiveCommentCount = passiveCommentCount(user.getUser_id(),
					start_date);
			int activeCommentCount = activeCommentCount(user.getUser_id(),
					start_date);
			HashMap<String, Integer> catepreferenceMap = commentTypeAndCount(
					user.getUser_id(), start_date);
			HashMap<Date, Integer> postTrendMap = postTrend(user.getUser_id(),
					start_date);
			request.setAttribute("passiveCommentCount", passiveCommentCount);
			request.setAttribute("activeCommentCount", activeCommentCount);
			request.setAttribute("catepreferenceMap", catepreferenceMap);
			request.setAttribute("postTrendMap", postTrendMap);
			return "Analysis.jsp";
		} else
			return "Analysis.jsp";
	}

	public int activeCommentCount(int user_id, Date start_date) {
		CommentBean[] comments;
		
		try {
			comments = commentDAO.getRecentCommentsByUserId(
					user_id, start_date);
			System.out.println("System is in active");
			return comments.length;
		} catch (RollbackException e) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public int passiveCommentCount(int user_id, Date start_date) {
		int count = 0;
		try {
			PostBean[] posts = postDAO.getRecentPostsByUserId(user_id,
					start_date);
			for (PostBean p : posts) {
				int temp = commentDAO.getCommentsCountbyPostId(p.getPost_id());
				count = count + temp;
			}
			System.out.println("System is in passive");
			return count;
		} catch (RollbackException e) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public HashMap<String, Integer> commentTypeAndCount(int user_id,
			Date start_date) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		try {
			PostBean[] posts = postDAO.getRecentPostsByUserId(user_id,
					start_date);
			for (PostBean p : posts) {
				String type = p.getCategory();
				if (map.containsKey(type)) {
					map.put(type, map.get(type) + 1);
				} else {
					map.put(type, 1);
				}
			}
			return map;
		} catch (RollbackException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public HashMap<Date, Integer> postTrend(int user_id, Date start_date) {
		HashMap<Date, Integer> map = new HashMap<Date, Integer>();

		try {
			PostBean[] posts = postDAO.getRecentPostsByUserId(user_id,
					start_date);
			for (PostBean p : posts) {
				Date type = p.getPostDate();
				if (map.containsKey(type)) {
					map.put(type, map.get(type) + 1);
				} else {
					map.put(type, 1);
				}
			}
			return map;
		} catch (RollbackException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
