package org.voxity.api;

public class OAuth2ClientCredentials {

	  /** Value of the "API Key". */
	  public static final String API_KEY = "YOUR API KEY";

	  /** Value of the "API Secret". */
	  public static final String API_SECRET = "YOUR CLIENT SECRET";

	  /** Port in the "Callback URL". */
	  public static final int PORT = 9000;

	  /** Domain name in the "Callback URL". */
	  public static final String DOMAIN = "localhost";

	  public static void errorIfNotSpecified() {
	    if (API_KEY.startsWith("Enter ") || API_SECRET.startsWith("Enter ")) {
	      System.out.println(
	          "Enter API Key and API Secret from https://client.voxity.fr/voxity-api/configuration"
	          + " into API_KEY and API_SECRET in " + OAuth2ClientCredentials.class);
	      System.exit(1);
	    }
	  }
}
