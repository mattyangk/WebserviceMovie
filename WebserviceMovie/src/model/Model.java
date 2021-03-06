

package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;


import bean.UserBean;


public class Model {
	private UserDAO userDAO;
	private PostDAO postDAO;
	private CommentDAO commentDAO;
	
	private TMDBRetriever tmdbRetriever;
	private TwitterRetriever twitterRetriever;

	public Model(ServletConfig config) throws ServletException{
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL, "root", "");
			userDAO  = new  UserDAO(pool, "User");
			postDAO = new PostDAO(pool, "Post");
			commentDAO = new CommentDAO(pool,"Comment");
			tmdbRetriever = new TMDBRetriever();
			twitterRetriever = new TwitterRetriever();
			
			
			if (userDAO.getCount() == 0){
				 //create default customer
				createDefaultUser();
			}
			 		
		} catch (DAOException e) {
			throw new ServletException(e);
		} catch (RollbackException e) {
			e.printStackTrace();
		} 
	}
	
	public UserDAO getUserDAO() { return userDAO; }
	
	public CommentDAO getCommentDAO() { return commentDAO; }
	
	public PostDAO getPostDAO() { return postDAO; }

	public TMDBRetriever getTMDBRetriever() { return tmdbRetriever;	}
	
	public TwitterRetriever getTwitterRetriever() {
		return twitterRetriever;
	}

	public void createDefaultUser() throws RollbackException{
		   
		   UserBean initialCustomer = new UserBean();
		   initialCustomer.setUsername("1");
		   initialCustomer.setPassword("1");		   
		   initialCustomer.setImagePath("ISR Lounge");
		   userDAO.createAutoIncrement(initialCustomer);
	}
}
