package bean;
import java.util.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("user_id, post_id")
public class comment {
private String user_id;
private String post_id;
private Date comment_time;
private String content;
private String type;
public String getUser_id() {
	return user_id;
}
public void setUser_id(String user_id) {
	this.user_id = user_id;
}
public String getPost_id() {
	return post_id;
}
public void setPost_id(String post_id) {
	this.post_id = post_id;
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
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}




}
