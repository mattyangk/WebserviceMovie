package model;

import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import bean.PostBean;
import bean.UserBean;

public class PostDAO extends GenericDAO<PostBean> {

	public PostDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(PostBean.class, tableName, connectionPool);
	}
	
	public PostBean[] getAllPosts() throws RollbackException {
		return match();
	}
	
	public PostBean[] getRecentPostsByUserId(int user_id,Date start_date)throws RollbackException {
		PostBean[] posts = match(MatchArg.and(
				MatchArg.equals("user_id",user_id),
				MatchArg.greaterThan("postDate",start_date)));
		if (posts == null) {
			System.out.println("null posts");
			return null;
		}
		return posts;
	}		
	}

