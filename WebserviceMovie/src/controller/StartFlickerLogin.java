package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import util.Encoder;

public class StartFlickerLogin extends Action {
	
	public StartFlickerLogin(Model model) {

	}
	
	public String getName() {
		return "startFlickerLogin.do";
	}
	
	public String perform(HttpServletRequest request) {
		
		String comment = request.getParameter("comment");
		String ori_poster = request.getParameter("ori_poster");
		String ori_text = request.getParameter("ori_text");
		String imagePath = request.getParameter("imagePath");
		String category = request.getParameter("category");
		String isRepost = request.getParameter("isRepost");
		String user_id = request.getParameter("user_id");
		String source = request.getParameter("source");
		String photoID = request.getParameter("photoID");

		
		System.out.println("comment:" + comment);
		System.out.println("ori_poster" + ori_poster);
		
		HttpSession session = request.getSession();
		OAuthService Flickerservice;
		
		if(session.getAttribute("Fservice")!=null){
			Flickerservice = (OAuthService) session.getAttribute("Fservice");
		}else{
			Flickerservice=new ServiceBuilder()
							.provider(FlickrApi.class)
							.callback("http://54.200.36.186:8080/WebserviceMovie/loginFlicker.do?comment="+ Encoder.encode(comment)
									+ "&ori_poster=" + Encoder.encode(ori_poster) + "&ori_text="
									+ Encoder.encode(ori_text) + "&imagePath=" + Encoder.encode(imagePath)
									+ "&category=" + Encoder.encode(category) + "&isRepost=" + Encoder.encode(isRepost)
									+ "&user_id=" + user_id + "&source=" + Encoder.encode(source) + "&photoID=" + photoID)
							.apiKey("c5e304c63826ddfa8bbcf81cbdf0f902")
							.apiSecret("3136b7874d63bdbc")
							.build();
			
			
		}
		
		System.out.println("=== Flicker's OAuth Workflow ===");
		System.out.println();
		
		System.out.println("Fetching the Request Token...");
		Token FrequestToken = Flickerservice.getRequestToken();
		System.out.println("Got the Request Token!");
		System.out.println();
		
		System.out.println("Now go and authorize Scribe here:");
		System.out.println(Flickerservice.getAuthorizationUrl(FrequestToken));
		
		String url = Flickerservice.getAuthorizationUrl(FrequestToken);
		session.setAttribute("Fservice", Flickerservice);
		session.setAttribute("FrequestToken", FrequestToken);
		
		System.out.println("Fservice : "+Flickerservice);
		System.out.println("FrequestToken :"+FrequestToken);
		
		return url;
	}	
}
