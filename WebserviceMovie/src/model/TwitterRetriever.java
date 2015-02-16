package model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import bean.MovieBean;
import bean.MovieTweetBean;
import bean.TweetBean;

public class TwitterRetriever {

	HttpClient httpClient;

	private String base_url;

	SimpleDateFormat formatter;

	private static final String key = "e3d6e2a242e56f63200526c0c531a92c";

	private static final String urlString = "https://api.themoviedb.org/3/";

	public TwitterRetriever() {
		/*httpClient = HttpClients.createDefault();
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
		}*/

	}


	private String twitter_consumer_key = "GrH7cFptpx1agB8PJZtME2eKu";
	private String twitter_consumer_secret = "SLaUl5X65VkUl75E0ta38jg49LgaOVwgRx5xZltP8lfIw5Zg0p";	

	public String encode(String value) 
	{
		String encoded = null;
		try {
			encoded = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException ignore) {
		}
		StringBuilder buf = new StringBuilder(encoded.length());
		char focus;
		for (int i = 0; i < encoded.length(); i++) {
			focus = encoded.charAt(i);
			if (focus == '*') {
				buf.append("%2A");
			} else if (focus == '+') {
				buf.append("%20");
			} else if (focus == '%' && (i + 1) < encoded.length()
					&& encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
				buf.append('~');
				i += 2;
			} else {
				buf.append(focus);
			}
		}
		return buf.toString();
	}

	private static String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException 
	{
		SecretKey secretKey = null;

		byte[] keyBytes = keyString.getBytes();
		secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(secretKey);

		byte[] text = baseString.getBytes();

		return new String(Base64.encodeBase64(mac.doFinal(text))).trim();
	}

	public JSONObject searchTweets(String q, String access_token, String access_token_secret)
	{
		JSONObject jsonresponse = new JSONObject();

		String oauth_token = access_token;
		String oauth_token_secret = access_token_secret;

		// generate authorization header
		String get_or_post = "GET";
		String oauth_signature_method = "HMAC-SHA1";

		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");
		String oauth_nonce = uuid_string; // any relatively random alphanumeric string will work here

		// get the timestamp
		Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauth_timestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds

		// the parameter string must be in alphabetical order
		// this time, I add 3 extra params to the request, "lang", "result_type" and "q".
		String parameter_string = "lang=en&oauth_consumer_key=" + twitter_consumer_key + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + 
				"&oauth_timestamp=" + oauth_timestamp + "&oauth_token=" + encode(oauth_token) + "&oauth_version=1.0&q=" + encode(q) + "&result_type=mixed";	
		System.out.println("parameter_string=" + parameter_string);
		String twitter_endpoint = "https://api.twitter.com/1.1/search/tweets.json";
		String twitter_endpoint_host = "api.twitter.com";
		String twitter_endpoint_path = "/1.1/search/tweets.json";
		String signature_base_string = get_or_post + "&"+ encode(twitter_endpoint) + "&" + encode(parameter_string);
		System.out.println("signature_base_string=" + signature_base_string);

		// this time the base string is signed using twitter_consumer_secret + "&" + encode(oauth_token_secret) instead of just twitter_consumer_secret + "&"
		String oauth_signature = "";
		try {
			oauth_signature = computeSignature(signature_base_string, twitter_consumer_secret + "&" + encode(oauth_token_secret));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String authorization_header_string = "OAuth oauth_consumer_key=\"" + twitter_consumer_key + "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" + oauth_timestamp + 
				"\",oauth_nonce=\"" + oauth_nonce + "\",oauth_version=\"1.0\",oauth_signature=\"" + encode(oauth_signature) + "\",oauth_token=\"" + encode(oauth_token) + "\"";
		System.out.println("authorization_header_string=" + authorization_header_string);


		HttpParams params = new SyncBasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");
		HttpProtocolParams.setUserAgent(params, "HttpCore/1.1");
		HttpProtocolParams.setUseExpectContinue(params, false);

		HttpProcessor httpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
				// Required protocol interceptors
				new RequestContent(),
				new RequestTargetHost(),
				// Recommended protocol interceptors
				new RequestConnControl(),
				new RequestUserAgent(),
				new RequestExpectContinue()});

		HttpRequestExecutor httpexecutor = new HttpRequestExecutor();
		HttpContext context = new BasicHttpContext(null);
		HttpHost host = new HttpHost(twitter_endpoint_host,443);
		DefaultHttpClientConnection conn = new DefaultHttpClientConnection();

		context.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
		context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, host);

		try {
			try {
				SSLContext sslcontext = SSLContext.getInstance("TLS");
				sslcontext.init(null, null, null);
				SSLSocketFactory ssf = sslcontext.getSocketFactory();
				Socket socket = ssf.createSocket();
				socket.connect(
						new InetSocketAddress(host.getHostName(), host.getPort()), 0);
				conn.bind(socket, params);

				// the following line adds 3 params to the request just as the parameter string did above. They must match up or the request will fail.
				BasicHttpEntityEnclosingRequest request2 = new BasicHttpEntityEnclosingRequest("GET", twitter_endpoint_path + "?lang=en&result_type=mixed&q=" + encode(q));
				request2.setParams(params);
				request2.addHeader("Authorization", authorization_header_string); // always add the Authorization header
				httpexecutor.preProcess(request2, httpproc, context);
				HttpResponse response2 = httpexecutor.execute(request2, conn, context);
				response2.setParams(params);
				httpexecutor.postProcess(response2, httpproc, context);

				if(response2.getStatusLine().toString().indexOf("500") != -1)
				{
					jsonresponse.put("response_status", "error");
					jsonresponse.put("message", "Twitter auth error.");
				}
				else
				{
					// if successful, the response should be a JSONObject of tweets
					JSONObject jo = new JSONObject(EntityUtils.toString(response2.getEntity()));
					if(jo.has("errors"))
					{
						jsonresponse.put("response_status", "error");
						String message_from_twitter = jo.getJSONArray("errors").getJSONObject(0).getString("message");
						if(message_from_twitter.equals("Invalid or expired token") || message_from_twitter.equals("Could not authenticate you"))
							jsonresponse.put("message", "Twitter auth error.");
						else
							jsonresponse.put("message", jo.getJSONArray("errors").getJSONObject(0).getString("message"));
					}
					else
					{
						jsonresponse.put("twitter_jo", jo); // this is the full result object from Twitter
					}

					conn.close();
				}   
			}
			catch(HttpException he) 
			{	
				System.out.println(he.getMessage());
				jsonresponse.put("response_status", "error");
				jsonresponse.put("message", "searchTweets HttpException message=" + he.getMessage());
			} 
			catch(NoSuchAlgorithmException nsae) 
			{	
				System.out.println(nsae.getMessage());
				jsonresponse.put("response_status", "error");
				jsonresponse.put("message", "searchTweets NoSuchAlgorithmException message=" + nsae.getMessage());
			} 					
			catch(KeyManagementException kme) 
			{	
				System.out.println(kme.getMessage());
				jsonresponse.put("response_status", "error");
				jsonresponse.put("message", "searchTweets KeyManagementException message=" + kme.getMessage());
			} 	
			finally {
				conn.close();
			}
		} 
		catch(JSONException jsone)
		{

		}
		catch(IOException ioe)
		{

		}
		return jsonresponse;
	}

	public MovieTweetBean[] getTweetByMovieName(HttpServletRequest request, String title) {
		
		ArrayList<MovieTweetBean> resultArrayList = new ArrayList<MovieTweetBean>();
		
		try {
			HttpSession session = request.getSession();

			Twitter twitter = new Twitter();

			OAuthService service = (OAuthService) session.getAttribute("service");
			Token requestToken = (Token) session.getAttribute("requestToken");

			Verifier verifier = new Verifier((String) session.getAttribute("oauth_verifier"));

			System.out.println("Trading the Request Token for an Access Token...");
			Token accessToken = service.getAccessToken(requestToken, verifier);
			System.out.println("Got the Access Token!");
			System.out.println("(if you're curious, it looks like this: " + accessToken + " )");
			System.out.println();


			String access_token = accessToken.getToken();
			String access_token_secret = accessToken.getSecret();

			System.out.println("access_token : "+access_token);
			System.out.println("access_token_secret : "+access_token_secret);
			JSONObject obj = (searchTweets(title, access_token, access_token_secret));
			System.out.println("hhi obj :"+obj);

			JSONObject msgIn = (JSONObject) obj.get("twitter_jo");
			JSONArray msg = (JSONArray) msgIn.get("statuses");

			//Iterator<JSONObject> iterator = ((List<>) msg).iterator();

			for(int i=0;i<msg.length();i++){
				msg.get(i);
			/*}
			while (iterator.hasNext()) {*/
				JSONObject next = (JSONObject) msg.get(i);
				String text = (String) next.get("text");
				String creationDate = (String) next.get("created_at");
				
				JSONObject user = (JSONObject) ((JSONObject) msg.get(i)).get("user");
				String screen_name = (String) user.get("screen_name");
				String name = (String) user.get("name");
				String profileImageUrl = (String) user.get("profile_image_url");
				
				JSONObject entities = (JSONObject) ((JSONObject) msg.get(i)).get("entities");
				JSONObject media = (JSONObject) entities.get("media");
				String mediaUrl = (String) media.get("media_url");
				
				MovieTweetBean movieBean = new MovieTweetBean();
				
				movieBean.setName(name);
				movieBean.setScreen_name(screen_name);
				movieBean.setText(text);
				movieBean.setUserImageUrl(profileImageUrl);
				movieBean.setTweetDate(creationDate);
				movieBean.setTweetImageUrl(mediaUrl);
				
				System.out.println("name :"+name);
				System.out.println("screen_name :"+screen_name);
				System.out.println("text :"+text);
				System.out.println("profileImageUrl :"+profileImageUrl);
				System.out.println("mediaUrl :"+mediaUrl);
				
				resultArrayList.add(movieBean);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.print(resultArrayList.size() + "\n");
		MovieTweetBean[] twArray = new MovieTweetBean[resultArrayList.size()];
		for (int i = 0; i < resultArrayList.size(); i++)
			twArray[i] = resultArrayList.get(i);
		return twArray;

	}

	/*public MovieBean getMovieById(String id) {
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
	}*/

}
