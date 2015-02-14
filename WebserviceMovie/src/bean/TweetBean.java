package bean;


public class TweetBean{
	private String text;
	private String screen_name;
	private long favourites_count;
	private String tag;
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getFavourites_count() {
		return favourites_count;
	}
	public void setFavourites_count(long favourites_count) {
		this.favourites_count = favourites_count;
	}
	public String getScreen_name() {
		return screen_name;
	}
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	
	
}
