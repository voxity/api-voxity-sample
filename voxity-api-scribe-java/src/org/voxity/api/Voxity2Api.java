package org.voxity.api;

import org.voxity.api.OAuth20VoxityImpl;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.*;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

public class Voxity2Api extends DefaultApi20 {
	private static final String BASE_URL = "https://api.voxity.fr/";
	private static final String AUTHORIZATION_URL = BASE_URL + "api/v1/dialog/authorize?client_id=%s&response_type=code&redirect_uri=%s";

	@Override
	public String getAccessTokenEndpoint()
	{
		return BASE_URL +"api/v1/oauth/token";
	}

	@Override
	public String getAuthorizationUrl(OAuthConfig config)
	{
		Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Foursquare2 does not support OOB");
		return String.format(AUTHORIZATION_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
	}

	@Override
	public AccessTokenExtractor getAccessTokenExtractor()
	{
	  return new JsonTokenExtractor();
	}
	
	@Override
    public Verb getAccessTokenVerb(){
          return Verb.POST;
    }
	
	public OAuthService createService(OAuthConfig config)
	{
		return new OAuth20VoxityImpl(this, config);
	}
}