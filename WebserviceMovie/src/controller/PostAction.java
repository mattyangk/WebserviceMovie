package controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import util.Encoder;
import bean.PostBean;
import bean.UserBean;
import model.Model;
import model.PostDAO;

public class PostAction extends Action {

	PostDAO postDAO;
	private static final String PROTECTED_RESOURCE_URL = "https://api.flickr.com/services/rest/";

	public PostAction(Model model) {
		postDAO = model.getPostDAO();
	}

	@Override
	public String getName() {
		return "post.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		String comment = request.getParameter("comment");
		String ori_poster = request.getParameter("ori_poster");
		String ori_text = request.getParameter("ori_text");
		String imagePath = request.getParameter("imagePath");
		String category = request.getParameter("category");
		String isRepost = request.getParameter("isRepost");
		String source = request.getParameter("source");
		String photoID = request.getParameter("photoID");
		System.out.println("photo id is: " + photoID);
		UserBean user = (UserBean) request.getSession().getAttribute("user");

		HttpSession session = request.getSession();

		if (isRepost!=null&&isRepost.equals("repost")) {

			if (source.equals("Flickr")) {
				if (session.getAttribute("FaccessToken") == null) {
					String url= "startFlickerLogin.do?comment=" + Encoder.encode(comment)
							+ "&ori_poster=" + Encoder.encode(ori_poster) + "&ori_text="
							+ Encoder.encode(ori_text) + "&imagePath=" + Encoder.encode(imagePath)
							+ "&category=" + Encoder.encode(category) + "&isRepost=" + Encoder.encode(isRepost)
							+ "&user_id=" + user.getUser_id() + "&source=" + Encoder.encode(source) + "&photoID=" + photoID;
					System.out.println(url);
					return url;
				} else {
					Token accessToken=(Token)session.getAttribute("FaccessToken");
					System.out.println("Token is:" + accessToken.getToken());
					OAuthRequest flickrRequest=new OAuthRequest(Verb.POST,PROTECTED_RESOURCE_URL);
					flickrRequest.addQuerystringParameter("method", "flickr.photos.comments.addComment");
					flickrRequest.addBodyParameter("photo_id", photoID);
					flickrRequest.addBodyParameter("comment_text", comment);
					OAuthService Flickerservice = (OAuthService) session.getAttribute("Fservice");
					Flickerservice.signRequest(accessToken, flickrRequest);
					Response flickrResponse = flickrRequest.send();
					System.out.println(flickrResponse.getBody());	

					// post this back to flickr
				}
			} 
			// twitter
			else {
				if (session.getAttribute("loggedTwitter") == null) {
					String url= "startTwitterLogin.do?comment=" + Encoder.encode(comment)
							+ "&ori_poster=" + Encoder.encode(ori_poster) + "&ori_text="
							+ Encoder.encode(ori_text) + "&imagePath=" + Encoder.encode(imagePath)
							+ "&category=" + Encoder.encode(category) + "&isRepost=" + Encoder.encode(isRepost)
							+ "&user_id=" + user.getUser_id() + "&source=" + Encoder.encode(source);
					System.out.println(url);
					return url;
				} else {

					try{
						OAuthService service= (OAuthService) session.getAttribute("service");
						Token accessToken = (Token) session.getAttribute("accessToken");
						String tweet = URLEncoder.encode(comment,"UTF-8");
						String urlTweet="https://api.twitter.com/1.1/statuses/update.json?status="+tweet;
						System.out.println("request: "+urlTweet);
						OAuthRequest request2 = new OAuthRequest(Verb.POST, urlTweet);
						service.signRequest(accessToken, request2);
						System.out.println("REQUEST: " + request2.getUrl());
						Response response2 = request2.send();
						System.out.println("Response body:"+response2.getBody());
					} catch(Exception e){
						e.getMessage();
					}
				}
			}

		}

		PostBean post = new PostBean();
		post.setCategory(category);
		post.setContent(comment + "//@" + ori_poster + " " + ori_text);
		post.setImagePath(imagePath);
		post.setUser_id(user.getUser_id());

		System.out.println("is repost?" + isRepost);

		try {
			postDAO.create(post);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "homePage.do";

	}

}
