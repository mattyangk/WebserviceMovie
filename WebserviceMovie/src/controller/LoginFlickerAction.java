package controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.Model;

import org.json.JSONObject;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Request;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import util.Encoder;
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
		
		String comment = request.getParameter("comment");
		String ori_poster = request.getParameter("ori_poster");
		String ori_text = request.getParameter("ori_text");
		String imagePath = request.getParameter("imagePath");
		String category = request.getParameter("category");
		String isRepost = request.getParameter("isRepost");
		String user_id = request.getParameter("user_id");
		String source = request.getParameter("source");
		String photoID = request.getParameter("photoID");
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			HttpSession session = request.getSession();

			LoginFlickerForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);// ?

			session.setAttribute("Foauth_token", form.getOauth_token());
			session.setAttribute("Foauth_verifier", form.getOauth_verifier());

			OAuthService Fservice = (OAuthService) session
					.getAttribute("Fservice");
			Token requestToken = (Token) session.getAttribute("FrequestToken");

			System.out.println("Foauth_token : " + form.getOauth_token());
			System.out.println("Foauth_verifier :" + form.getOauth_verifier());

			Verifier verifier = new Verifier(form.getOauth_verifier());

			System.out.println("verifier :" + verifier);

			System.out
					.println("Trading the Request Token for an Access Token...");
			Token accessToken = Fservice.getAccessToken(requestToken, verifier);
			System.out.println("Got the Access Token!");
			System.out.println("(if you're curious, it looks like this: "
					+ accessToken + " )");

			session.setAttribute("FaccessToken", accessToken);

			System.out.println();

			OAuthRequest flickerRequest = new OAuthRequest(Verb.GET,
					PROTECTED_RESOURCE_URL);
			flickerRequest.addQuerystringParameter("method",
					"flickr.test.login");
			Fservice.signRequest(accessToken, flickerRequest);
			Response FlickrResponse = flickerRequest.send();

			String body = FlickrResponse.getBody();
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder
					.parse(new InputSource(new StringReader(body)));

			NodeList userList = doc.getElementsByTagName("user");
			for (int i = 0; i < userList.getLength(); i++) {
				Node node = userList.item(i);
				System.out.println(node.getAttributes().getNamedItem("id")
						.getNodeValue());
			}

			NodeList nameList = doc.getElementsByTagName("username");
			for (int i = 0; i < nameList.getLength(); i++) {
				Node node = nameList.item(i);
				System.out.println(node.getTextContent());
			}

			System.out.println("Got it! Lets see what we found...");
			System.out.println();
			System.out.println(body);

			// ---------------------Now let's go and ask for a protected
			// resource!-----------------------------------------------------
			System.out
					.println("Now we're going to access a protected resource...");
			/*
			 * OAuthRequest FlickerRequest = new OAuthRequest(Verb.GET,
			 * PROTECTED_RESOURCE_URL); Fservice.signRequest(accessToken,
			 * FlickerRequest); Response response = FlickerRequest.send();
			 * System.out.println("Got it! Lets see what we found...");
			 * System.out.println(); System.out.println(response.getBody());
			 */

		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "index.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "index.jsp";
		}

		return "post.do?comment="+ Encoder.encode(comment)
				+ "&ori_poster=" + Encoder.encode(ori_poster) + "&ori_text="
				+ Encoder.encode(ori_text) + "&imagePath=" + Encoder.encode(imagePath)
				+ "&category=" + Encoder.encode(category) + "&isRepost=" + Encoder.encode(isRepost)
				+ "&user_id=" + user_id + "&source=" + Encoder.encode(source) + "&photoID=" + photoID;
	}

}
