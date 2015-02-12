

package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import bean.CustomerBean;


public class Model {
	private CustomerDAO customerDAO;
	
	private TMDBRetriever tmdbRetriever;


	public Model(ServletConfig config) throws ServletException{
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL, "root", "");
			customerDAO  = new  CustomerDAO(pool, "Customer");
			tmdbRetriever = new TMDBRetriever();

			
			
			if (customerDAO.getCount() == 0){
				// create default customer
				createDefaultCustomer();
			}
			 		
		} catch (DAOException e) {
			throw new ServletException(e);
		} catch (RollbackException e) {
			e.printStackTrace();
		} 
	}
	
	public CustomerDAO getCustomerDAO() { return customerDAO; }

	public TMDBRetriever getTMDBRetriever() { return tmdbRetriever;	}
	
	
	public void createDefaultCustomer() throws RollbackException{
		   
		   CustomerBean initialCustomer = new CustomerBean();

		   customerDAO.createAutoIncrement(initialCustomer);
	}
	
}
