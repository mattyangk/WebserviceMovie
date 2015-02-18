package model;

import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import bean.CommentBean;
import bean.PostBean;

public class CommentDAO extends GenericDAO<CommentBean> {

	public CommentDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(CommentBean.class, tableName, connectionPool);
	}
	
	public CommentBean[] getCommentsByPostId(int post_id) throws RollbackException {
		CommentBean[] comments = match(MatchArg.and(MatchArg.equals("post_id", post_id)));
		return comments;
	}
	
	public CommentBean[] getRecentCommentsByUserId(int user_id,Date start_date)throws RollbackException {
		System.out.println("System is in active");
		CommentBean[] comments = match(MatchArg.and(
				MatchArg.equals("user_id",user_id),
				MatchArg.greaterThan("comment_time",start_date)));
		if (comments == null) {
			System.out.println("null comments");
			return null;
		}
		return comments;
	}
	public int getCommentsCountbyPostId(int post_id) throws RollbackException {
		CommentBean[] comments = match(MatchArg.equals("post_id",post_id));
		return comments.length;
	}
	
}
