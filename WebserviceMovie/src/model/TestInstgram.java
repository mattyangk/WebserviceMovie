package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestInstgram {

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		// TODO Auto-generated method stub
		String code = "4321aed20ae84eeeb78be6403e43dc8a";
		String client_ID = "030fb9d9991b47bfa2f9839d4518f581";
		String client_secret = "5a94a639db7c4dcbbdd26cdb29ff97b5";
		String redirect_url = "http://54.200.36.186:8080";

		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(
				"https://api.instagram.com/oauth/access_token");
		List<NameValuePair> params = new ArrayList<NameValuePair>(5);
		params.add(new BasicNameValuePair("client_id",
				"030fb9d9991b47bfa2f9839d4518f581"));
		params.add(new BasicNameValuePair("client_secret",
				"5a94a639db7c4dcbbdd26cdb29ff97b5"));
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		params.add(new BasicNameValuePair("redirect_uri",
				"http://54.200.36.186:8080"));
		params.add(new BasicNameValuePair("code",
				"4321aed20ae84eeeb78be6403e43dc8a"));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		// Execute and get the response.
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();

		if (entity != null) {

			String responseString = EntityUtils.toString(entity);

			System.out.println(responseString);

		}

	}

}
