package bean;

import java.util.Date;
import java.util.List;

public class PostAndCommentsBean {
	private int post_id;
	private int user_id;
	private String user_photo_url;
	private String user_name;
	private String imagePath;
	private String content;
	private String category;
	private Date postDate;
	List<CommentAndUserBean> comments;
	
	
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_photo_url() {
		return user_photo_url;
	}
	public void setUser_photo_url(String user_photo_url) {
		this.user_photo_url = user_photo_url;
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
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public List<CommentAndUserBean> getComments() {
		return comments;
	}
	public void setComments(List<CommentAndUserBean> comments) {
		this.comments = comments;
	}


}
