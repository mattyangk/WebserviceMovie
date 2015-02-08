package model;

import java.io.InputStream;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class TMDBRetriever {
	
	HttpClient httpClient;
	
	private static final String key = "e3d6e2a242e56f63200526c0c531a92c";
	
	private static final String urlString = "https://api.themoviedb.org/3/";
	
	
	public List<MovieBean> getMoviesByTitle(String title) {
		httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(urlString + "search/movie?api_key="+key);
		
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		
		if (entity != null) {
			InputStream instream = entity.getContent();
			
		}
		return null;
	}
	
	public List<MovieBean> getTopMovies() {
		
	}
	

}
