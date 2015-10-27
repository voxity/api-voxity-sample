from requests_oauthlib import OAuth2Session
from flask import Flask, request, redirect, session, url_for
from flask.json import jsonify
import os

app = Flask(__name__)

# This information is obtained upon registration of a new GitHub OAuth
base_url = "https://api.voxity.fr"
client_id = "<your client key>"
client_secret = "<your client secret>"
authorization_base_url = 'https://api.voxity.fr/api/v1/dialog/authorize'
token_url = 'https://api.voxity.fr/api/v1/oauth/token'
redirect_uri = "<your redirect_uri registred on voxity service>" #in this example you should set it to <http://localhost:5000/callback>

@app.route("/")
def demo():
    """Step 1: User Authorization.

    Redirect the user/resource owner to the OAuth provider (i.e. Github)
    using an URL with a few key OAuth parameters.
    """
    voxity = OAuth2Session(client_id, redirect_uri=redirect_uri)
    authorization_url, state = voxity.authorization_url(authorization_base_url)

    # State is used to prevent CSRF, keep this for later.
    session['oauth_state'] = state
    return redirect(authorization_url)


# Step 2: User authorization, this happens on the provider.
@app.route("/callback", methods=["GET"])
def callback():
    """ Step 3: Retrieving an access token.

    The user has been redirected back from the provider to your registered
    callback URL. With this redirection comes an authorization code included
    in the redirect URL. We will use that to obtain an access token.
    """

    voxity = OAuth2Session(client_id, state=session['oauth_state'], redirect_uri=redirect_uri)
    token = voxity.fetch_token(token_url, client_secret=client_secret,
                               authorization_response=request.url)

    print "your token :"
    print token

    # At this point you can fetch protected resources but lets save
    # the token and show how this is done from a persisted token
    # in /profile.
    session['oauth_token'] = token
    return redirect(url_for('.profile'))


@app.route("/profile", methods=["GET"])
def profile():
    """Fetching a protected resource using an OAuth 2 token.
    """
    voxity = OAuth2Session(client_id, token=session['oauth_token'])
    return jsonify(voxity.get(base_url+'/calls/logs?lastdays=10').json())


if __name__ == "__main__":
    # This allows us to use a plain HTTP callback
    os.environ['DEBUG'] = "1"
    os.environ['OAUTHLIB_INSECURE_TRANSPORT'] = "1"
    app.secret_key = os.urandom(24)
    app.run(debug=True)