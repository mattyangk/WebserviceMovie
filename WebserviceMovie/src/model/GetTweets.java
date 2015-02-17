package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
//import org.mybeans.form.FormBeanFactory;
//import formbean.SearchTweetsForm;







import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import bean.DisplayBean;
import bean.TweetBean;

public class GetTweets {

	/*	private static FormBeanFactory<SearchTweetsForm> formBeanFactory = FormBeanFactory
			.getInstance(SearchTweetsForm.class);

	 */	public String getName() {
		 return "getTweets.do";
	 }

	 /*public static TweetBean[] performGetTweets(String movie) {
		 List<String> errors = new ArrayList<String>();
		 try {

			 if (errors.size() != 0) {
				 // return "c_login.jsp";
			 }
			 int count = 30;
			 if (movie != null) {

				 String token = requestBearerToken("https://api.twitter.com/oauth2/token");

				 String queryUrlString = "https://api.twitter.com/1.1/search/tweets.json?q="
						 + URLEncoder.encode(movie)+ "&count="+ count+ "&lang=en";
				 System.out.println("after request");
				 TweetBean[] tweetBeanArray =
						 fetchTimelineTweet(queryUrlString,token);

				 return tweetBeanArray;// tweetBeanArray;
			 }
			 return new TweetBean[0];
		 } catch (Exception e) {
			 errors.add(e.getMessage());
			 return new TweetBean[0];
		 }
	 }*/

	 public static String encode(String value) 
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


	 public static JSONObject newPerformGetTweets(String movie,boolean doNeedMore) {
		 JSONObject obj = null;
		 List<String> errors = new ArrayList<String>();
		// ArrayList<DisplayBean> tweetBeanArray = new ArrayList<DisplayBean>();

		 try {

			 if (errors.size() != 0) {
				 // return "c_login.jsp";
			 }
			 int count = 100;
			 if (movie != null) {

				 String token = requestBearerToken("https://api.twitter.com/oauth2/token");
				 String queryUrlString;
				 if(doNeedMore){
					 System.out.println("q : "+movie);
						String[] qry = movie.split("&");
						System.out.println("qry :"+qry);
						String max_id = qry[0];
						System.out.println("max_id : "+max_id);
						max_id = max_id.substring(1);
						System.out.println("max_id, after remving? : "+max_id);
						String query = qry[1];
						
						System.out.println("query :"+query);
					 queryUrlString = "https://api.twitter.com/1.1/search/tweets.json?"
							 + query+ "&count="+ count+ "&lang=en&"+max_id;
					 System.out.println("new call : "+queryUrlString);
				 }
				 else{
					 queryUrlString = "https://api.twitter.com/1.1/search/tweets.json?q="
							 + encode(movie)+ "&count="+ count+ "&lang=en";
					 System.out.println("old call : "+queryUrlString);
				 }

				 System.out.println("queryUrlString :"+queryUrlString);
				 System.out.println("after request");
				/* tweetBeanArray =
						 newFetchTimelineTweet(queryUrlString,token);*/

				 String endPointUrl = queryUrlString;
				 HttpsURLConnection connection = null;
				 try {
					 URL url = new URL(endPointUrl);
					 System.out.println("url :"+url);
					 connection = (HttpsURLConnection) url.openConnection();
					 connection.setDoOutput(true);
					 connection.setDoInput(true);
					 connection.setRequestMethod("GET");
					 connection.setRequestProperty("Host", "api.twitter.com");
					 connection.setRequestProperty("User-Agent", "WebserviceMovie");
					 connection.setRequestProperty("Authorization", "Bearer "
							 + token);
					 connection.setUseCaches(false);
				 } catch (MalformedURLException e) {
					 throw new IOException("Invalid endpoint URL specified.", e);
				 } catch (ProtocolException e) {
					 System.out.println("Exception : "+e.getMessage());
				 } finally {
					 if (connection != null) {
						 connection.disconnect();
					 }
				 }

				 System.out.println();
				 obj = (JSONObject) JSONValue.parse(readResponse(connection));

				 return obj;
				 
				 //return tweetBeanArray;// tweetBeanArray;
			 }
			// return tweetBeanArray;
			 return obj;
		 } catch (Exception e) {
			 errors.add(e.getMessage());
			 //return tweetBeanArray;
			 return obj;
		 }
	 }

	 /*public static TweetBean[]  performGetHashTags(String movie, String keyword) {
		 List<String> errors = new ArrayList<String>();

		 try {

			 if (errors.size() != 0) {
				 // return "c_login.jsp";
			 }
			 System.out.println("search is :"+movie);
			 int count = 100;
			 if (movie != null) {

				 String token = requestBearerToken("https://api.twitter.com/oauth2/token");

				 String queryUrlString = "https://api.twitter.com/1.1/search/tweets.json?q=" + keyword
						 + "&count="+count+"&lang=en";

				 TweetBean[] tweetBeanArray = fetchTweetwithTags(queryUrlString,
						 token);
				 if (tweetBeanArray != null) {
					 //request.setAttribute("tweets", tweetBeanArray);
					 System.out.println("array length is " + tweetBeanArray.length);
					 return tweetBeanArray;

				 }
			 }
			 // System.out.println("Tweet result is \n" + tweet+"]]]]");
			 //HttpSession session = request.getSession();

			 return null;
		 } catch (Exception e) {
			 errors.add(e.getMessage());
			 return null;
		 }
	 }
*/
	 /**
	  * @param args
	  */

	 // Encodes the consumer key and secret to create the basic authorization key
	 private static String encodeKeys(String consumerKey, String consumerSecret) {
		 try {
			 String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
			 String encodedConsumerSecret = URLEncoder.encode(consumerSecret,
					 "UTF-8");

			 String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
			 byte[] encodedBytes = Base64.encodeBase64(fullKey.getBytes());
			 // taking binary data into text
			 // it's more easily transmitted in things like e-mail and HTML form
			 // data.
			 return new String(encodedBytes);
		 } catch (UnsupportedEncodingException e) {
			 return new String();
		 }
	 }

	 // Constructs the request for requesting a bearer token and returns that
	 // token as a string
	 private static String requestBearerToken(String endPointUrl)
			 throws IOException {
		 HttpsURLConnection connection = null;
		 String encodedCredentials = encodeKeys("GrH7cFptpx1agB8PJZtME2eKu",
				 "SLaUl5X65VkUl75E0ta38jg49LgaOVwgRx5xZltP8lfIw5Zg0p");

		 try {
			 URL url = new URL(endPointUrl);
			 connection = (HttpsURLConnection) url.openConnection();
			 connection.setDoOutput(true);
			 connection.setDoInput(true);
			 connection.setRequestMethod("POST");
			 connection.setRequestProperty("Host", "twitter.com");
			 connection.setRequestProperty("User-Agent", "WebserviceMovie"); // ?
			 connection.setRequestProperty("Authorization", "Basic "
					 + encodedCredentials);
			 connection.setRequestProperty("Content-Type",
					 "application/x-www-form-urlencoded;charset=UTF-8");
			 connection.setRequestProperty("Content-Length", "29");
			 connection.setUseCaches(false);

			 writeRequest(connection, "grant_type=client_credentials");

			 // Parse the JSON response into a JSON mapped object to fetch fields
			 // from.
			 JSONObject obj = (JSONObject) JSONValue
					 .parse(readResponse(connection));

			 System.out.println("obj :"+obj);
			 if (obj != null) {
				 String tokenType = (String) obj.get("token_type");
				 String token = (String) obj.get("access_token");

				 System.out.println("tokenType : "+tokenType);
				 System.out.println("token : "+token);

				 System.out.println("((tokenType.equals(\"bearer\")) && (token != null)) ? token : \"\" " + (((tokenType.equals("bearer")) && (token != null)) ? token : ""));
				 return ((tokenType.equals("bearer")) && (token != null)) ? token
						 : "";
			 }
			 return new String();
		 } catch (MalformedURLException e) {
			 throw new IOException("Invalid endpoint URL specified.", e);
		 } finally {
			 if (connection != null) {
				 connection.disconnect();
			 }
		 }
	 }

	 /* private static TweetBean[] fetchTimelineTweet(String endPointUrl,
			 String bearerToken) throws IOException {
		 HttpsURLConnection connection = null;

		 try {
			 URL url = new URL(endPointUrl);
			 System.out.println("url :"+url);
			 connection = (HttpsURLConnection) url.openConnection();
			 connection.setDoOutput(true);
			 connection.setDoInput(true);
			 connection.setRequestMethod("GET");
			 connection.setRequestProperty("Host", "api.twitter.com");
			 connection.setRequestProperty("User-Agent", "WebserviceMovie");
			 connection.setRequestProperty("Authorization", "Bearer "
					 + bearerToken);
			 connection.setUseCaches(false);

			 System.out.println();
			 JSONObject obj = (JSONObject) JSONValue
					 .parse(readResponse(connection));

			 System.out.println("obj :"+obj);

			 JSONArray msg = (JSONArray) obj.get("statuses");
			 Iterator<JSONObject> iterator = msg.iterator();

			 ArrayList<TweetBean> resultArrayList = new ArrayList<TweetBean>();
			 while (iterator.hasNext()) {
				 JSONObject next = iterator.next();
				 String text = (String) next.get("text");
				 JSONObject user = (JSONObject) iterator.next().get("user");
				 String screen_name = (String) user.get("screen_name");
				 TweetBean tBean = new TweetBean();
				 tBean.setText(text);
				 tBean.setScreen_name(screen_name);
				 resultArrayList.add(tBean);
			 }
			 // System.out.print(resultArrayList.size() + "\n");
			 TweetBean[] twArray = new TweetBean[resultArrayList.size()];
			 for (int i = 0; i < resultArrayList.size(); i++)
				 twArray[i] = resultArrayList.get(i);
			 return twArray;
		 } catch (MalformedURLException e) {
			 throw new IOException("Invalid endpoint URL specified.", e);
		 } finally {
			 if (connection != null) {
				 connection.disconnect();
			 }
		 }
	 }*/

	/* private static JSONObject getJSONTweets(String endPointUrl,
			 String bearerToken) throws IOException{
		 HttpsURLConnection connection = null;
		 try {
			 URL url = new URL(endPointUrl);
			 System.out.println("url :"+url);
			 connection = (HttpsURLConnection) url.openConnection();
			 connection.setDoOutput(true);
			 connection.setDoInput(true);
			 connection.setRequestMethod("GET");
			 connection.setRequestProperty("Host", "api.twitter.com");
			 connection.setRequestProperty("User-Agent", "WebserviceMovie");
			 connection.setRequestProperty("Authorization", "Bearer "
					 + bearerToken);
			 connection.setUseCaches(false);
		 } catch (MalformedURLException e) {
			 throw new IOException("Invalid endpoint URL specified.", e);
		 } catch (ProtocolException e) {
			 System.out.println("Exception : "+e.getMessage());
		 } finally {
			 if (connection != null) {
				 connection.disconnect();
			 }
		 }

		 System.out.println();
		 JSONObject obj = (JSONObject) JSONValue.parse(readResponse(connection));

		 return obj;
	 }*/
	public static ArrayList<DisplayBean> newFetchTimelineTweet(String movie) throws IOException {
		 //HttpsURLConnection connection = null;
		 ArrayList<DisplayBean> resultArrayList = new ArrayList<DisplayBean>();

		 try {
			 /* URL url = new URL(endPointUrl);
			 System.out.println("url :"+url);
			 connection = (HttpsURLConnection) url.openConnection();
			 connection.setDoOutput(true);
			 connection.setDoInput(true);
			 connection.setRequestMethod("GET");
			 connection.setRequestProperty("Host", "api.twitter.com");
			 connection.setRequestProperty("User-Agent", "WebserviceMovie");
			 connection.setRequestProperty("Authorization", "Bearer "
					 + bearerToken);
			 connection.setUseCaches(false);

			 System.out.println();
			 JSONObject obj = (JSONObject) JSONValue.parse(readResponse(connection));*/

			 JSONObject obj = newPerformGetTweets(movie, false);
			 System.out.println("obj :"+obj);

			 System.out.println("hhi obj :"+obj);


			 JSONArray msg = (JSONArray) obj.get("statuses");
			 System.out.println("msg : "+msg);
			 JSONObject search_meatadata = (JSONObject) obj.get("search_metadata");
			 System.out.println("search_meatadata :"+search_meatadata);
			 Iterator<JSONObject> iterator = msg.iterator();

			 SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

			 //Iterator<JSONObject> iterator = ((List<>) msg).iterator();
			 int cntr=0;
			 while (resultArrayList.size() < 15) {
				 System.out.println("while loop cntr : "+(++cntr));
				 /* for(int i=0;i<msg.size();i++){*/
				 while(iterator.hasNext()){
					 /*msg.get(i);*/

					 JSONObject next = (JSONObject) iterator.next();
					 String text = (String) next.get("text");
					 String creationDate = (String) next.get("created_at");

					 JSONObject user = (JSONObject)  next.get("user");
					 String screen_name = (String) user.get("screen_name");
					 String name = (String) user.get("name");
					 String profileImageUrl = (String) user.get("profile_image_url");

					 JSONObject entities = (JSONObject) next.get("entities");
					 //System.out.println("entities :"+entities);
					 if(entities.get("media")==null){
						 //System.out.println("media null");
						 continue;

					 }
					 System.out.println("media not null");
					 JSONArray media = (JSONArray) entities.get("media");
					 System.out.println("media :"+media);
					 Iterator<JSONObject> iteratorMedia = media.iterator();
					 String mediaUrl = "hi";
					 while(iteratorMedia.hasNext()){
						 JSONObject mediaNext = (JSONObject) iteratorMedia.next();
						 System.out.println("iteratorMedia.next() :"+mediaNext.get("media_url"));
						 mediaUrl = (String)  (mediaNext.get("media_url"));
						 System.out.println("mediaUrl : "+mediaUrl);
						 break;
					 }

					 /*if(mediaUrl==null){
					 System.out.println("mediaUrl"+mediaUrl);
					 continue;
				 }*/
					 DisplayBean movieBean = new DisplayBean();

					 movieBean.setUser_name(name);
					 //movieBean.setScreen_name(screen_name);
					 movieBean.setText(text);
					 movieBean.setProfile_url(profileImageUrl);
					 Date date = (Date)sdf.parse(creationDate);
					 movieBean.setDate(date);
					 movieBean.setPhoto_url(mediaUrl);
					 movieBean.setSource("Twitter");

					 System.out.println("-------------------------------------------------------------");
					 System.out.println("name :"+name);
					 System.out.println("screen_name :"+screen_name);
					 System.out.println("text :"+text);
					 System.out.println("profileImageUrl :"+profileImageUrl);
					 System.out.println("mediaUrl :"+mediaUrl);
					 System.out.println("creationDate: "+creationDate);
					 System.out.println("date : "+date);

					 resultArrayList.add(movieBean);
				 }
				 
				 search_meatadata = (JSONObject) obj.get("search_metadata");
				 if ( search_meatadata.get("next_results")==null) {
					 System.out.println("no next_results");
					 return resultArrayList;
				 }
				 String moreResults = (String) search_meatadata.get("next_results");
				 
				 obj = newPerformGetTweets(moreResults, true);
				 msg = (JSONArray) obj.get("statuses");
				 iterator = msg.iterator();
				 

				 System.out.println("next result " + moreResults);
				 
				 System.out.println("inside obj :"+obj);
				 System.out.println("-------------------------------------------------------------------------");
				 System.out.println("size : " + resultArrayList.size());
				 System.out.println("-------------------------------------------------------------------------");
			 }


		 } catch (ParseException e) {
			 System.out.println("Exception : "+e.getMessage());
		 } 
		 System.out.println("size : " + resultArrayList.size());
		 return resultArrayList;
	 }

/*	 private static TweetBean[] fetchTweetwithTags(String endPointUrl,
			 String bearerToken) throws IOException {
		 HttpsURLConnection connection = null;

		 try {
			 URL url = new URL(endPointUrl);
			 connection = (HttpsURLConnection) url.openConnection();
			 connection.setDoOutput(true);
			 connection.setDoInput(true);
			 connection.setRequestMethod("GET");
			 connection.setRequestProperty("Host", "api.twitter.com");
			 connection.setRequestProperty("User-Agent", "WebserviceMovie");
			 connection.setRequestProperty("Authorization", "Bearer "
					 + bearerToken);
			 connection.setUseCaches(false);
			 System.out.print("***********"+connection);
			 JSONObject obj = (JSONObject) JSONValue
					 .parse(readResponse(connection));

			 JSONArray msg = (JSONArray) obj.get("statuses");
			 Iterator<JSONObject> iterator = msg.iterator();
			 // int i = 0;
			 ArrayList<TweetBean> resultArrayList = new ArrayList<TweetBean>();
			 while (iterator.hasNext()) {
				 JSONObject next = iterator.next();
				 //				System.out.println("next " + next.toString());
				 JSONObject entities = (JSONObject) next.get("entities");
				 //				System.out.println("entites " + entities.toString());
				 JSONArray hashtags = (JSONArray) entities.get("hashtags");
				 if ( hashtags == null || hashtags.size() == 0 ) {
					 continue;
				 } else {
					 JSONObject tag = (JSONObject) hashtags.get(0);
					 String content = (String) tag.get("text");
					 //					System.out.println(content );
					 TweetBean tBean = new TweetBean();
					 tBean.setTag(content);
					 resultArrayList.add(tBean);

				 }


			 }
			 System.out.print(resultArrayList.size() + "\n");
			 TweetBean[] twArray = new TweetBean[resultArrayList.size()];
			 for (int i = 0; i < resultArrayList.size(); i++)
				 twArray[i] = resultArrayList.get(i);
			 // return (tweet != null) ? tweet : "";
			 System.out.print(twArray.length + "\n");
			 return twArray;
		 } catch (MalformedURLException e) {
			 throw new IOException("Invalid endpoint URL specified.", e);
		 } finally {
			 if (connection != null) {
				 connection.disconnect();
			 }
		 }
	 }*/


	 // Writes a request to a connection
	 private static boolean writeRequest(HttpsURLConnection connection,
			 String textBody) {
		 try {
			 BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
					 connection.getOutputStream()));
			 wr.write(textBody);
			 wr.flush();
			 wr.close();

			 return true;
		 } catch (IOException e) {
			 return false;
		 }
	 }

	 // Reads a response for a given connection and returns it as a string.
	 private static String readResponse(HttpsURLConnection connection) {
		 try {
			 StringBuilder str = new StringBuilder();
			 //			System.out.println("!!!!!!"+connection.toString());
			 BufferedReader br = new BufferedReader(new InputStreamReader(
					 connection.getInputStream()));
			 String line = "";
			 while ((line = br.readLine()) != null) {
				 str.append(line + System.getProperty("line.separator"));
			 }
			 return str.toString();
		 } catch (IOException e) {
			 return new String();
		 }
	 }


	 /*	 public static void postTweets(String tweet) {
		 List<String> errors = new ArrayList<String>();
		 try {

			 if (errors.size() != 0) {
				 // return "c_login.jsp";
			 }
			 if (tweet != null) {

				 String token = requestBearerToken("https://api.twitter.com/oauth2/token");

				 String queryUrlString = "https://api.twitter.com/1.1/statuses/update.json?status="
						 + URLEncoder.encode(tweet);
				 System.out.println("after request");
				 postTweet(queryUrlString,token);

				 return;// tweetBeanArray;
			 }
			 return;
		 } catch (Exception e) {
			 errors.add(e.getMessage());
			 return;
		 }
	 }

	 private static void postTweet(String endPointUrl,
			 String bearerToken) throws IOException {
		 HttpsURLConnection connection = null;

		 try {
			 URL url = new URL(endPointUrl);
			 connection = (HttpsURLConnection) url.openConnection();
			 connection.setDoOutput(true);
			 connection.setDoInput(true);
			 connection.setRequestMethod("POST");
			 connection.setRequestProperty("Host", "api.twitter.com");
			 connection.setRequestProperty("User-Agent", "WebserviceMovie");
			 connection.setRequestProperty("Authorization", "Bearer "
					 + bearerToken);
			 connection.setUseCaches(false);

			 System.out.println();
			 JSONObject obj = (JSONObject) JSONValue
					 .parse(readResponse(connection));

			 System.out.println("obj :"+obj);

			 String tweet_id = (String) obj.get("id_str");
			 System.out.println("tweet_id :"+tweet_id);

			 return ;
		 } catch (MalformedURLException e) {
			 throw new IOException("Invalid endpoint URL specified.", e);
		 } finally {
			 if (connection != null) {
				 connection.disconnect();
			 }
		 }
	 }*/

	 public static void main(String[] args) {
		 ArrayList<DisplayBean> disp = new ArrayList<>();
		try {
			disp = GetTweets.newFetchTimelineTweet("interstellar");
		} catch (IOException e) {
			System.out.println("exception : "+e.getMessage());
		}
		 System.out.println(disp.size());
		 for (DisplayBean t : disp) {
			 System.out.println("----");
			 System.out.println(t.getUser_name());
			 System.out.println(t.getText());
		 }

		 System.out.println();

		 /* TweetBean[] twbeen = GetTweets.performGetTweets("jupiter ascending");
		 System.out.println(twbeen.length);
		 for (TweetBean t : twbeen) {
			 System.out.println("----");
			 System.out.println(t.getScreen_name());
			 System.out.println(t.getText());
		 }*/

		 // postTweets("hi all !");

		 /*tweetBeanArray = GetTweets.performGetHashTags("bighero", "recent");
		System.out.println(tweetBeanArray.length);
		for (TweetBean t : tweetBeanArray) {
			System.out.println(t.getText());
		}*/
	 }


}
