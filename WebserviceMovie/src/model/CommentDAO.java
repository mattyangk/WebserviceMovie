package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import bean.CommentBean;

public class CommentDAO extends GenericDAO<CommentBean> {

	public CommentDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(CommentBean.class, tableName, connectionPool);
	}
	
}
