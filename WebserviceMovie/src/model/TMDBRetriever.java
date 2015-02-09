package model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bean.MovieBean;

public class TMDBRetriever {

	HttpClient httpClient;

	private String base_url;

	SimpleDateFormat formatter;

	private static final String key = "e3d6e2a242e56f63200526c0c531a92c";

	private static final String urlString = "https://api.themoviedb.org/3/";

	public TMDBRetriever() {
		httpClient = HttpClients.createDefault();
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		// get base url
		HttpGet httpGet = new HttpGet(urlString + "configuration");
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String responseString = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(responseString);
			base_url = json.getJSONObject("images").getString("base_url");

			System.out.println("base_url" + base_url);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<MovieBean> getMoviesByKeyword(String keyword) {
		List<MovieBean> list = new ArrayList<MovieBean>();
		HttpGet httpGet = new HttpGet(urlString + "search/movie?api_key=" + key
				+ "&query=" + keyword);

		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String responseString = EntityUtils.toString(entity);
				System.out.println(responseString);
				JSONObject json = new JSONObject(responseString);
				int page = json.getInt("page");
				System.out.println("page: " + page);
				JSONArray result = json.getJSONArray("results");

				for (int i = 0; i < result.length(); i++) {
					MovieBean movie = getBasicInfo(result.getJSONObject(i));
					expandMovieBean(movie);
					list.add(movie);
					// System.out.println("id: "+ id + " title: " + title +
					// " rating: " + rating + " imagePath: " + imagePath);
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<MovieBean> getPopularMovies() throws Exception {
		String opString = "movie/popular?api_key=" + key;
		
		List<MovieBean> list = getMovieListFromJSON(opString);
		
		return list;
	}

	public List<MovieBean> getNowplayingMovies() throws Exception {
		String opString = "movie/now_playing?api_key=" + key;
		
		List<MovieBean> list = getMovieListFromJSON(opString);
		
		return list;

	}

	public List<MovieBean> getSimilarMovies(int id) throws Exception {
		String opString = "movie/" + id + "/similar?api_key=" + key;
		
		List<MovieBean> list = getMovieListFromJSON(opString);

		return list;
	}

	private List<MovieBean> getMovieListFromJSON(String opString)
			throws Exception {

		List<MovieBean> list = new ArrayList<MovieBean>();

		HttpGet httpGet = new HttpGet(urlString + opString);

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			JSONObject json = new JSONObject(responseString);
			JSONArray result = json.getJSONArray("results");

			for (int i = 0; i < result.length(); i++) {
				MovieBean movie = getBasicInfo(result.getJSONObject(i));
				expandMovieBean(movie);
				list.add(movie);
			}

		}
		return list;

	}

	private void expandMovieBean(MovieBean movie) {
		
	}

	private MovieBean getBasicInfo(JSONObject result) throws JSONException,
			ParseException {
		MovieBean movie = new MovieBean();
		int id = result.getInt("id");
		String title = result.getString("title");
		double rating = result.getDouble("vote_average");
		String imagePath = result.getString("poster_path");
		String date = result.getString("release_date");

		movie.setTitle(title);
		movie.setMovieId(id);
		movie.setRate(rating);
		movie.setImagePath(base_url + "original" + imagePath);
		movie.setDate(formatter.parse(date));

		return movie;
	}

}
