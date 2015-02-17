package model;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import bean.DisplayBean;



public class testFlicker2 {
	
	HttpClient httpClient;
	
	private String base_url;
	
	private ArrayList<String>photoIDs;
	private ArrayList<String>photoUrls;
	private ArrayList<String>ownerIDs;
	private int len=0;
	private static final String App_Key = "397398943649392be4db5f52711dc781";
	private static final String SearchMethod = "flickr.photos.search";
	private static final String originalUrl = "https://api.flickr.com/services/rest/?";
	private List<DisplayBean> flickrs;
	
	
	public testFlicker2(String tag) throws ClientProtocolException, IOException, ParserConfigurationException, SAXException, ParseException
	{
		String ThisTag=tag;
		flickrs=new ArrayList<DisplayBean>();
		httpClient = HttpClients.createDefault();
		String allPhotoUrl=originalUrl+"method="+SearchMethod+"&api_key="+App_Key+"&tags="+encode(tag);
		HttpGet httpGet = new HttpGet(allPhotoUrl);
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			//System.out.println(responseString);
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.parse(new InputSource(new StringReader(
					responseString)));
			

			//System.out.println("root name: " + doc.getDocumentElement().getNodeName());
			
			NodeList sizeList = doc.getElementsByTagName("photo");//照片记录数
			len=sizeList.getLength();
			//System.out.println(sizeList.getLength());
		    photoIDs=new ArrayList<String>();
		    photoUrls=new ArrayList<String>();
			ownerIDs=new ArrayList<String>();
			
			
			for (int i = 0; i < sizeList.getLength(); i++) {
				Node node = sizeList.item(i);
				DisplayBean bean=new DisplayBean();
				bean.setPhotoID(node.getAttributes().getNamedItem("id").getNodeValue());
				//System.out.print(b);
				
				//photoIDs.add(node.getAttributes().getNamedItem("id").getNodeValue());
				//ownerIDs.add(node.getAttributes().getNamedItem("owner").getNodeValue());
				System.out.println(node.getAttributes().getNamedItem("id").getNodeValue());
				
				/////////////////get photoURL////////////
				String ImagePath=originalUrl+"method=flickr.photos.getSizes&api_key="+App_Key+"&tags="+encode(tag)+"&photo_id="+node.getAttributes().getNamedItem("id").getNodeValue();
				httpClient = HttpClients.createDefault();
				httpGet = new HttpGet(ImagePath);
				response = httpClient.execute(httpGet);
				entity = response.getEntity();
				if(entity!=null)
				{
					responseString = EntityUtils.toString(entity);
					//System.out.println(responseString);
					
					factory = DocumentBuilderFactory
							.newInstance();
					builder = factory.newDocumentBuilder();

					doc = builder.parse(new InputSource(new StringReader(
							responseString)));
					
					NodeList photoList=doc.getElementsByTagName("size");
					len=photoList.getLength();
					//System.out.println(len);
					for(int j=0;j<photoList.getLength();j++)
					{
						Node photoNode=photoList.item(j);
						if(photoNode.getAttributes().getNamedItem("label").getNodeValue().equals("Medium 640"))
						{
						     bean.setWidth(photoNode.getAttributes().getNamedItem("width").getNodeValue());
						     bean.setHeight(photoNode.getAttributes().getNamedItem("height").getNodeValue());
							 bean.setPhoto_url(photoNode.getAttributes().getNamedItem("source").getNodeValue());
							//System.out.println(photoNode.getAttributes().getNamedItem("source").getNodeValue());
						}
						
					}
				}
				////////////////////////////////////////////////////////////
				
			String photoInfo=originalUrl+"method=flickr.photos.getInfo&api_key=397398943649392be4db5f52711dc781&photo_id="+node.getAttributes().getNamedItem("id").getNodeValue();
			httpClient = HttpClients.createDefault();
			httpGet = new HttpGet(photoInfo);
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			if (entity != null) {
				responseString = EntityUtils.toString(entity);
				factory = DocumentBuilderFactory
						.newInstance();
				builder = factory.newDocumentBuilder();
				doc = builder.parse(new InputSource(new StringReader(
						responseString)));
				
				NodeList DateList=doc.getElementsByTagName("dates");
				
				for(int k=0;k<DateList.getLength();k++)
				{
					Node date=DateList.item(k);
					String Desdate=date.getAttributes().getNamedItem("taken").getNodeValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date photoDate=sdf.parse(Desdate);
					bean.setDate(photoDate);
				}
				
				NodeList descriptionList=doc.getElementsByTagName("description");
				
				for(int t=0;t<descriptionList.getLength();t++)
				{
					Node description=descriptionList.item(t);
					bean.setText(description.getTextContent());
				}
				
		     }
			
			/////////////////////////////////////////////////////////
			String photoPoserURL=originalUrl+"method=flickr.people.getInfo&api_key=397398943649392be4db5f52711dc781"+"&user_id="+node.getAttributes().getNamedItem("owner").getNodeValue();
			//System.out.println(photoPoserURL);
			httpClient = HttpClients.createDefault();
			httpGet = new HttpGet(photoPoserURL);
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			if (entity != null) {
				responseString = EntityUtils.toString(entity);
				factory = DocumentBuilderFactory
						.newInstance();
				builder = factory.newDocumentBuilder();
				doc = builder.parse(new InputSource(new StringReader(
						responseString)));
				NodeList UserList=doc.getElementsByTagName("username");
				for(int tt=0;tt<UserList.getLength();tt++)
				{
					Node user=UserList.item(tt);
					bean.setUser_name(user.getTextContent());
					System.out.println("user: "+user.getTextContent());
				}
				
				NodeList profileList=doc.getElementsByTagName("person");
				for(int l=0;l<profileList.getLength();l++)
				{
					Node person=profileList.item(l);
					String sid=person.getAttributes().getNamedItem("nsid").getNodeValue();
					String farm=person.getAttributes().getNamedItem("iconfarm").getNodeValue();
					String server=person.getAttributes().getNamedItem("iconserver").getNodeValue();
					String profileUrl="http://farm"+farm+".staticflickr.com/"+server+"/buddyicons/"+sid+".jpg";
					if(farm.equals("0")||server.equals("0"))
						{
						  bean.setProfile_url("images/pic10.jpg");
						}
					else
					{
						bean.setProfile_url(profileUrl);
					}
				}
		     }
			bean.setSource("Flickr");
			
			
			
			flickrs.add(bean);
			if(i>10)
				break;
			
			
		}
			
			
	  }
     }
	
    public List<DisplayBean> getALL()
	{
		return flickrs;
	}
    
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
		
}
