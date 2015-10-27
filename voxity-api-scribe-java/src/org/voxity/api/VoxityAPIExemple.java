package org.voxity.api;

import java.awt.Desktop;
import java.net.URL;
import java.util.*;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
import org.voxity.api.Voxity2Api;

public class VoxityAPIExemple {
	
	private static final String PROTECTED_RESOURCE_URL = "https://api.voxity.fr/api/v1/channels";
	private static final Token EMPTY_TOKEN = null;
	
	public static void main(String[] args)
	  {
	    // Replace these with your own api key and secret
	    String apiKey = "<yourAPIKEY>";
	    String apiSecret = "<your client secret>";
	    OAuthService service = new ServiceBuilder()
	                                  .provider(Voxity2Api.class)
	                                  .apiKey(apiKey)
	                                  .apiSecret(apiSecret)
	                                  .callback("http://localhost:9000/")
	                                  .build();
	    Scanner in = new Scanner(System.in);

	    System.out.println("=== Voxity OAuth Workflow ===");
	    System.out.println();

	    // Obtain the Authorization URL
	    System.out.println("Fetching the Authorization URL...");
	    String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
	    //OPEN LOACAL BROWSER
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(new URL(authorizationUrl).toURI());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    System.out.println("And paste the authorization code here");
	    System.out.print(">>");
	    Verifier verifier = new Verifier(in.nextLine());
	    System.out.println();
	    
	    // Trade the Request Token and Verfier for the Access Token
	    System.out.println("Trading the Request Token for an Access Token...");
	    Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
	    System.out.println("Got the Access Token!");
	    System.out.println("(if your curious it looks like this: " + accessToken + " )");
	    System.out.println();

	    // Now let's go and ask for a protected resource!
	    System.out.println("Now we're going to access a protected resource...");
	    OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
	    service.signRequest(accessToken, request);
	    Response response = request.send();
	    System.out.println("Got it! Lets see what we found...");
	    System.out.println();
	    System.out.println(response.getCode());
	    System.out.println(response.getBody());

	    System.out.println();
	    System.out.println("Thats it man! Go and build something awesome with Scribe! :)");

	  }

}
