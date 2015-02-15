package controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

public class Login
{
  private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
  
  public static void main(String[] args)
  {
    // --------If you choose to use a callback, "oauth_verifier" will be the return value by Twitter (request param)----------
    OAuthService service = new ServiceBuilder()
                                .provider(TwitterApi.SSL.class)
                                .callback("https://www.facebook.com")
                                .apiKey("GrH7cFptpx1agB8PJZtME2eKu")
                                .apiSecret("SLaUl5X65VkUl75E0ta38jg49LgaOVwgRx5xZltP8lfIw5Zg0p")
                                .build();
    Scanner in = new Scanner(System.in);

    System.out.println("=== Twitter's OAuth Workflow ===");
    System.out.println();

    //-------------------------Obtain the Request Token------------------------------------------------------------------------
    System.out.println("Fetching the Request Token...");
    Token requestToken = service.getRequestToken();
    System.out.println("Got the Request Token!");
    System.out.println();

      
    System.out.println("Now go and authorize Scribe here:");
    System.out.println(service.getAuthorizationUrl(requestToken));
   
  //--------------------------Opening the Authentication Page------------------------------------------------------------------  
    try {
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

    //-----------------Trade the Request Token and Verfier for the Access Token----------------------------------------------
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

  }

}