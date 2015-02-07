

package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import databeans.EmployeeBean;
import databeans.CustomerBean;


public class Model {
	private CustomerDAO customerDAO;
	private EmployeeDAO employeeDAO;
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;

	public Model(ServletConfig config) throws ServletException{
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL, "root", "");
			customerDAO  = new  CustomerDAO(pool, "Customer");
			employeeDAO = new EmployeeDAO(pool,"Employee");
			transactionDAO = new TransactionDAO(pool,"Transaction");
			fundDAO = new FundDAO(pool,"Fund");
			positionDAO = new PositionDAO(pool,"Position");
			fundPriceHistoryDAO = new FundPriceHistoryDAO(pool,"Fund_Price_History");

			
			if (employeeDAO.getCount() == 0){
				// create default employee
				createDefaultEmployee();
			}
			
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
	public EmployeeDAO getEmployeeDAO() { return employeeDAO; }
	public FundDAO getFundDAO() { return fundDAO; }
	public FundPriceHistoryDAO getFundPriceHistoryDAO() { return fundPriceHistoryDAO; }
	public PositionDAO getPositionDAO() { return positionDAO; }
	public TransactionDAO getTransactionDAO() { return transactionDAO; }
	
	public void createDefaultEmployee() throws RollbackException{
		   
		   EmployeeBean initialEmployee = new EmployeeBean();
		   initialEmployee.setUsername("admin");
		   initialEmployee.setPassword("123456");
		   initialEmployee.setFirstname("Barack");
		   initialEmployee.setLastname("Obama");
		   employeeDAO.createAutoIncrement(initialEmployee);
	}
	
	public void createDefaultCustomer() throws RollbackException{
		   
		   CustomerBean initialCustomer = new CustomerBean();
		   initialCustomer.setUsername("defaultcustomer");
		   initialCustomer.setPassword("123456");
		   initialCustomer.setFirstname("Default");
		   initialCustomer.setLastname("Customer");
		   initialCustomer.setState("Pennsylvania");
		   initialCustomer.setCity("Pittsburgh");
		   initialCustomer.setCash(10000);
		   initialCustomer.setBalance(10000);
		   initialCustomer.setZip("15213");
		   initialCustomer.setAddr_line1("5000 Forbes Ave");
		   initialCustomer.setAddr_line2("ISR Lounge");
		   customerDAO.createAutoIncrement(initialCustomer);
	}
	
}
