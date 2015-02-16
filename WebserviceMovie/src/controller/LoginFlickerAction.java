package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;

import org.json.JSONObject;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import formbean.LoginFlickerForm;

public class LoginFlickerAction extends Action {
		
	private static final String PROTECTED_RESOURCE_URL = "https://api.flickr.com/services/rest/";
	
	private FormBeanFactory<LoginFlickerForm> formBeanFactory = FormBeanFactory
			.getInstance(LoginFlickerForm.class);
	
	public LoginFlickerAction(Model model) {
		
	}
	
	public String getName() {
		return "loginFlicker.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			HttpSession session = request.getSession();

			LoginFlickerForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);//?
			
			session.setAttribute("Foauth_token", form.getOauth_token());
			session.setAttribute("Foauth_verifier", form.getOauth_verifier());
			
			OAuthService Fservice = (OAuthService) session.getAttribute("Fservice");
			Token requestToken = (Token) session.getAttribute("FrequestToken");
			
			System.out.println("Foauth_token : "+form.getOauth_token());
			System.out.println("Foauth_verifier :"+form.getOauth_verifier());
			
			Verifier verifier = new Verifier(form.getOauth_verifier());
			
			System.out.println("verifier :"+verifier);
			
			System.out.println("Trading the Request Token for an Access Token...");
			Token accessToken = Fservice.getAccessToken(requestToken, verifier);
			System.out.println("Got the Access Token!");
			System.out.println("(if you're curious, it looks like this: " + accessToken + " )");
			System.out.println();

			//---------------------Now let's go and ask for a protected resource!-----------------------------------------------------
			System.out.println("Now we're going to access a protected resource...");
			OAuthRequest FlickerRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			Fservice.signRequest(accessToken, FlickerRequest);
			Response response = FlickerRequest.send();
			System.out.println("Got it! Lets see what we found...");
			System.out.println();
			System.out.println(response.getBody());
			
			
			
			
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "index.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "index.jsp";
		}
		
		return "movieHome.do";
	}
	
	
	
	
	

}
