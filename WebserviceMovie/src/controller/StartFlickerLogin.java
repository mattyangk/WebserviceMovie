package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

public class StartFlickerLogin extends Action {
	
	public StartFlickerLogin(Model model) {

	}
	
	public String getName() {
		return "startFlickerLogin.do";
	}
	
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		OAuthService Flickerservice;
		
		if(session.getAttribute("Fservice")!=null){
			Flickerservice = (OAuthService) session.getAttribute("Fservice");
		}else{
			Flickerservice=new ServiceBuilder()
							.provider(FlickrApi.class)
							.callback("http://localhost:8080/WebserviceMovie/loginFlicker.do")
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
