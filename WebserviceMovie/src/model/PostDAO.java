package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import bean.PostBean;

public class PostDAO extends GenericDAO<PostBean> {

	public PostDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(PostBean.class, tableName, connectionPool);
	}
	
}
