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
		HttpGet httpGet = new HttpGet(urlString + "configuration?api_key="
				+ key);
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

	public MovieBean getMovieById(String id) {
		MovieBean movie = new MovieBean();
		HttpGet httpGet = new HttpGet(urlString + "movie/" + id + "?api_key="
				+ key);

		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String responseString = EntityUtils.toString(entity);
				System.out.println(responseString);
				JSONObject json = new JSONObject(responseString);

				String title = json.getString("title");
				double rating = json.getDouble("vote_average");
				String imagePath = json.getString("poster_path");
				String date = json.getString("release_date");
				String category = json.getJSONArray("genres").getJSONObject(0)
						.getString("name");
				String description = json.getString("overview");
				
				movie.setTitle(title);
				movie.setMovieId("" + id);
				movie.setRate(rating);
				movie.setImagePath(base_url + "w780" + imagePath);
				movie.setDate(formatter.parse(date));
				movie.setCategory(category);
				movie.setDescription(description);

				expandMovieBean(movie);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return movie;

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
					addOverview(movie);
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
			System.out.println("########\n" + responseString);
			JSONObject json = new JSONObject(responseString);
			JSONArray result = json.getJSONArray("results");

			for (int i = 0; i < result.length(); i++) {
				MovieBean movie = getBasicInfo(result.getJSONObject(i));
				addOverview(movie);
				list.add(movie);
			}

		}
		return list;

	}

	private void expandMovieBean(MovieBean movie) throws Exception {
		String id = movie.getMovieId();

		// get casts and directors
		HttpGet httpGet = new HttpGet(urlString + "movie/" + id
				+ "/credits?api_key=" + key);

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			
			System.out.println(responseString);
			
			JSONObject json = new JSONObject(responseString);
			JSONArray casts = json.getJSONArray("cast");
			JSONArray crew = json.getJSONArray("crew");

			for (int i = 0; i < casts.length() && i < 5; i++) {
				movie.getCasts().add(casts.getJSONObject(i).getString("name"));
				System.out.println(casts.getJSONObject(i).getString("name"));
			}

			for (int i = 0; i < crew.length(); i++) {
				if (crew.getJSONObject(i).getString("job").equals("Director")) {
					movie.getDirector().add(
							crew.getJSONObject(i).getString("name"));
				}

			}
		}

	}

	public void addOverview(MovieBean movie) throws Exception {
		String id = movie.getMovieId();
		HttpGet httpGet = new HttpGet(urlString + "movie/" + id + "?api_key="
				+ key);

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			JSONObject json = new JSONObject(responseString);
			String description = json.getString("overview");
			if (description.length() > 150) {
				int spaceIndex = 0;
				int i = 150;
				while (i < description.length()) {
					if (description.charAt(i) == ' ') {
						spaceIndex = i;
						break;
					}
					i++;
				}
				if (i == description.length()) {
					spaceIndex = description.length();
				}
				description = description.substring(0, spaceIndex)
						+ "... Read More";
			}

			String category = json.getJSONArray("genres").getJSONObject(0)
					.getString("name");
			movie.setDescription(description);
			movie.setCategory(category);
		}
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
		movie.setMovieId("" + id);
		movie.setRate(rating);
		movie.setImagePath(base_url + "w780" + imagePath);
		movie.setDate(formatter.parse(date));

		return movie;
	}

}
