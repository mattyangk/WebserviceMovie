package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import bean.UserBean;

public class UserDAO extends GenericDAO<UserBean> {

	public UserDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(UserBean.class, tableName, connectionPool);
	}
	
	public UserBean getCustomerByUsername(String username)
			throws RollbackException {
		UserBean[] user = match(MatchArg.equalsIgnoreCase("username",
				username));
		if (user.length != 1) {
			System.out.println("not correct number of customers");
			return null;
		}
		return user[0];
	}

}
