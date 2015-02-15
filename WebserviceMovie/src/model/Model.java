

package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import bean.UserBean;


public class Model {
	private UserDAO userDAO;
	
	private TMDBRetriever tmdbRetriever;


	public Model(ServletConfig config) throws ServletException{
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
			userDAO  = new  UserDAO(pool, "User");
			tmdbRetriever = new TMDBRetriever();

			
			
			if (userDAO.getCount() == 0){
				// create default customer
				createDefaultCustomer();
			}
			 		
		} catch (DAOException e) {
			throw new ServletException(e);
		} catch (RollbackException e) {
			e.printStackTrace();
		} 
	}
	
	public UserDAO getUserDAO() { return userDAO; }

	public TMDBRetriever getTMDBRetriever() { return tmdbRetriever;	}
	
	
	public void createDefaultCustomer() throws RollbackException{
		   
		   UserBean initialCustomer = new UserBean();

		   userDAO.createAutoIncrement(initialCustomer);
	}
	
}
