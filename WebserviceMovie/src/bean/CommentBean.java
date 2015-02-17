package bean;
import java.util.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("comment_id")
public class CommentBean {
private int comment_id;
private int post_id;
private int user_id;
private Date comment_time;
private String content;



public int getComment_id() {
	return comment_id;
}
public void setComment_id(int comment_id) {
	this.comment_id = comment_id;
}

public int getPost_id() {
	return post_id;
}
public void setPost_id(int post_id) {
	this.post_id = post_id;
}
public int getUser_id() {
	return user_id;
}
public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public Date getComment_time() {
	return comment_time;
}
public void setComment_time(Date comment_time) {
	this.comment_time = comment_time;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}

}
