package model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class testFlicker {

	public static void main(String[] args) throws ClientProtocolException,
			IOException, DocumentException {
		// TODO Auto-generated method stub
		
		/*String client_ID = "030fb9d9991b47bfa2f9839d4518f581";
		String client_secret = "5a94a639db7c4dcbbdd26cdb29ff97b5";
		String redirect_url = "http://54.200.36.186:8080";
		String initialHttp="https://instagram.com/oauth/authorize/?client_id=030fb9d9991b47bfa2f9839d4518f581&redirect_uri=http://54.200.36.186:8080&response_type=token";
		HttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(initialHttp);
		HttpResponse response = client.execute(request);
		Header[] headers = response.getAllHeaders();
		//HttpEntity entity = response.getEntity();
		
		//System.out.println(EntityUtils.toString(entity));
		System.out.println("state line" + response.getStatusLine().getStatusCode());
		
		for(int i=0;i<headers.length;i++)
		{
		 	//HeaderElement[] elements = headers[i].getElements();
		 	System.out.println("key: " + headers[i].getName() + "value: " + headers[i].getValue());
		}
			
		
	}
	*/
		
		
		String original="https://api.flickr.com/services/rest/?";
		String method="flickr.photos.search";
		String app_key="397398943649392be4db5f52711dc781";
		String testURL=original+"method="+method+"&api_key="+app_key;
		String testURL2="https://api.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=397398943649392be4db5f52711dc781&tags=Interstellar&photo_id=16323507450";
		
		HttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(testURL2);
		HttpResponse response = client.execute(request);
		
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			System.out.println(responseString);

		}
		
		Document responseDocument = null;
		InputStream instream = entity.getContent();
		SAXReader reader = new SAXReader(); 
	
		responseDocument = reader.read(instream);
		Element root = responseDocument.getRootElement();
		
	    System.out.println("Root: " + root.getName());
	    
	    /////////
	  

	
		
		
		
		
		
	}
	}
