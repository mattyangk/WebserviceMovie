package model;

import javax.net.ssl.HttpsURLConnection;

public class TMDBRetriever {
	
	private HttpsURLConnection connection;
	
	private static final String key = "e3d6e2a242e56f63200526c0c531a92c";
	
	private static final String urlString = "https://api.themoviedb.org/3/";
	
	public TMDBRetriever() {
		
	}

}
