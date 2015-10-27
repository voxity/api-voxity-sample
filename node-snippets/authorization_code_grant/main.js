var express = require('express')
  , app = express()
  ;
 
var oauth2 = require('simple-oauth2')({
  clientID: "xxxxxxx",
  clientSecret: "xxxxxxxxxxxxxxxx",
  site: 'https://api.voxity.fr/api/v1',
  tokenPath: '/oauth/token',
  authorizationPath : '/dialog/authorize'
});
 
// Authorization uri definition 
var authorization_uri = oauth2.authCode.authorizeURL({
  redirect_uri: 'http://localhost:5000/callback'
});

// Initial page redirecting to Github 
app.get('/', function (req, res) {
    res.redirect(authorization_uri);
});

// Callback service parsing the authorization token and asking for the access token 
app.get('/callback', function (req, res) {
  var code = req.query.code;
  oauth2.authCode.getToken({
    code: code,
    redirect_uri: 'http://localhost:5000/callback'
  
  }, function (error, result) {
    if (error) { console.log('Access Token Error', error.message); }
    token = oauth2.accessToken.create(result);
    res.send('Here is your token : '+JSON.stringify(token.token));
  });
});
 
app.listen(5000);
 
console.log('Express server started on port 5000');