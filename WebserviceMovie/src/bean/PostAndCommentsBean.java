package bean;

import java.util.Date;
import java.util.List;

public class PostAndCommentsBean {
	private int post_id;
	private int user_id;
	private String imagePath;
	private String content;
	private String category;
	private Date postDate;
	List<CommentBean> commets;
	
	
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
	public List<CommentBean> getCommets() {
		return commets;
	}
	public void setCommets(List<CommentBean> commets) {
		this.commets = commets;
	}
	
	

}
