import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class Test {
	
	public static void main(String[] args) throws Exception {
		
		HttpClient httpClient = HttpClients.createDefault();
//		HttpPost httpPost = new HttpPost("http://127.0.0.1:9999/cp2/static_site/index.html");
//		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
//		params.add(new BasicNameValuePair("param-1", "12345"));
//		params.add(new BasicNameValuePair("param-2", "Hello!"));
//		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		
		String key = "e3d6e2a242e56f63200526c0c531a92c";
		
		String urlString = "https://api.themoviedb.org/3/";
		
		String query = "&query=interstellar";
		
		HttpGet httpGet = new HttpGet(urlString + "search/movie?api_key=" + key + query);
		
		
		//Execute and get the response.
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
		    	int id = result.getJSONObject(i).getInt("id");
		    	String title = result.getJSONObject(i).getString("title");
		    	double rating = result.getJSONObject(i).getDouble("vote_average");
		    	String imagePath = result.getJSONObject(i).getString("poster_path");
		    	System.out.println("id: "+ id + " title: " + title + " rating: " + rating + " imagePath: " + imagePath);
		    }
		    
		}
	}
	
	

}



