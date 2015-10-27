package org.voxity.api;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class APIVoxityExempleClickToCall {
	private static final java.io.File DATA_STORE_DIR =
		      new java.io.File(System.getProperty("user.home"), ".store/voxity_sample");
	 
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** OAuth 2 scope. */
	private static final String SCOPE = "read";

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String TOKEN_SERVER_URL = "https://api.voxity.fr/api/v1/oauth/token";
    private static final String AUTHORIZATION_SERVER_URL = "https://api.voxity.fr/api/v1/dialog/authorize";
	
    private static Credential authorize() throws Exception {
        OAuth2ClientCredentials.errorIfNotSpecified();
        // set up authorization code flow
        AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken
            .authorizationHeaderAccessMethod(),
            HTTP_TRANSPORT,
            JSON_FACTORY,
            new GenericUrl(TOKEN_SERVER_URL),
            new ClientParametersAuthentication(
                OAuth2ClientCredentials.API_KEY, OAuth2ClientCredentials.API_SECRET),
            OAuth2ClientCredentials.API_KEY,
            AUTHORIZATION_SERVER_URL).setScopes(Arrays.asList(SCOPE))
            .setDataStoreFactory(DATA_STORE_FACTORY).build();
        // authorize
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost(
            OAuth2ClientCredentials.DOMAIN).setPort(OAuth2ClientCredentials.PORT).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
      }
    
    private static void run(HttpRequestFactory requestFactory) throws IOException {
        VoxityUrl url = new VoxityUrl("https://api.voxity.fr/api/v1/channels");

        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("exten", "0481680110");
        HttpRequest request = requestFactory.buildPostRequest(url, new UrlEncodedContent(paramsMap));
        
        ClickToCallFeed clickFeed = request.execute().parseAs(ClickToCallFeed.class);
        if (clickFeed.list.isEmpty()) {
          System.out.println("No click found.");
        } else {
        	System.out.println(clickFeed.list.size() + " channels found:");
            for (ClickToCall channel : clickFeed.list) {
              System.out.println();
              System.out.println("-----------------------------------------------");
              System.out.println("message: " + channel.message);
            }
        }
      }
	
	public static void main(String[] args) {
	    try {
	      DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
	      final Credential credential = authorize();
	      HttpRequestFactory requestFactory =
	          HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
	            @Override
	            public void initialize(HttpRequest request) throws IOException {
	              credential.initialize(request);
	              request.setParser(new JsonObjectParser(JSON_FACTORY));
	            }
	          });
	      run(requestFactory);
	      // Success!
	      return;
	    } catch (IOException e) {
	      System.err.println(e.getMessage());
	    } catch (Throwable t) {
	      t.printStackTrace();
	    }
	    System.exit(1);
	  }

}
