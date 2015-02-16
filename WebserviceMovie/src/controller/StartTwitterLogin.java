package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

public class StartTwitterLogin extends Action{

	public StartTwitterLogin(Model model) {

	}

	public String getName() {
		return "startTwitterLogin.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		OAuthService service;
		
		if(session.getAttribute("service")!=null){
			service = (OAuthService) session.getAttribute("service");
		}
		else{
			service = new ServiceBuilder()
							.provider(TwitterApi.SSL.class)
							.callback("http://localhost:8080/WebserviceMovie/loginTwitter.do")
							.apiKey("GrH7cFptpx1agB8PJZtME2eKu")
							.apiSecret("SLaUl5X65VkUl75E0ta38jg49LgaOVwgRx5xZltP8lfIw5Zg0p")
							.build();
		}
		

		System.out.println("=== Twitter's OAuth Workflow ===");
		System.out.println();

		//-------------------------Obtain the Request Token------------------------------------------------------------------------
		System.out.println("Fetching the Request Token...");
		Token requestToken = service.getRequestToken();
		System.out.println("Got the Request Token!");
		System.out.println();


		System.out.println("Now go and authorize Scribe here:");
		System.out.println(service.getAuthorizationUrl(requestToken));

		String url = service.getAuthorizationUrl(requestToken);
		
		session.setAttribute("service", service);
		session.setAttribute("requestToken", requestToken);
		
		return url;
		
		//--------------------------Opening the Authentication Page------------------------------------------------------------------  
		/*try {
			Desktop.getDesktop().browse(new URI(service.getAuthorizationUrl(requestToken)));
		} catch (UnsupportedOperationException ignore) {
		} catch (IOException ignore) {
		} catch (URISyntaxException e) {
			throw new AssertionError(e);
		}


		System.out.println("And paste the verifier here");
		System.out.print(">>");
		Verifier verifier = new Verifier(in.nextLine());
		System.out.println();
*/
	/*	//-----------------Trade the Request Token and Verfier for the Access Token----------------------------------------------
		System.out.println("Trading the Request Token for an Access Token...");
		Token accessToken = service.getAccessToken(requestToken, verifier);
		System.out.println("Got the Access Token!");
		System.out.println("(if you're curious, it looks like this: " + accessToken + " )");
		System.out.println();

		//---------------------Now let's go and ask for a protected resource!-----------------------------------------------------
		System.out.println("Now we're going to access a protected resource...");
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service.signRequest(accessToken, request);
		Response response = request.send();
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(response.getBody());

*/


		///------------

/*
		try {
			HttpSession session = request.getSession();

			LoginTwitterForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			session.setAttribute("oauth_token", form.getOauth_token());
			session.setAttribute("oauth_verifier", form.getOauth_verifier());

		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "index.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "index.jsp";
		}*/

		//return "index.jsp";
	}
}
