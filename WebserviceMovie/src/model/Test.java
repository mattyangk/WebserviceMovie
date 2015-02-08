package model;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;


public class Test {
	
	public static void main(String[] args) throws Exception {
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://127.0.0.1:9999/cp2/static_site/index.html");
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("param-1", "12345"));
		params.add(new BasicNameValuePair("param-2", "Hello!"));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		

		
		//Execute and get the response.
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
		    InputStream instream = entity.getContent();
		    try {
		        System.out.println(instream.toString());
		    } finally {
		        instream.close();
		    }
		}
	}
	
	

}



