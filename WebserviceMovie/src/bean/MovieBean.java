package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieBean {
	
	private String movieId;
	private String title;
	private double rate;
	private String imagePath;
	private String description;
	private String category;
	private Date date;
	private List<String> director;
	private List<String> casts;
	
	public MovieBean() {
		director = new ArrayList<String>();
		casts = new ArrayList<String>();
	}
	
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<String> getDirector() {
		return director;
	}
	public void setDirector(List<String> director) {
		this.director = director;
	}
	public List<String> getCasts() {
		return casts;
	}
	public void setCasts(List<String> casts) {
		this.casts = casts;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	

}
