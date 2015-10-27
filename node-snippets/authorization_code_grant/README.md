This snippet implements the Authorization Code Grant authentication flow as specified by OAUTH2.0 RFC6749

In goal to use properly this snippet : 

* install the packages given in package.json with the command below
``` 
npm install
```

* set your application credentials in main.js :
```javascript
# This information is obtained upon registration of a new Voxity application with OAuth2.0 authentication

var oauth2 = require('simple-oauth2')({
  clientID: "xxxxxxx",
  clientSecret: "xxxxxxxxxxxxxxxx",
  site: 'https://api.voxity.fr/api/v1',
  tokenPath: '/oauth/token',
  authorizationPath : '/dialog/authorize'
});

var authorization_uri = oauth2.authCode.authorizeURL({
  redirect_uri: 'xxxxxxxxxxxxxxxxxxxx' #in this example you should set it to <http://localhost:5000/callback>
});
```

* launch the program :
```
node main.js
```

* with your browser, go to http://localhost:5000/

* you will be redirected to voxity authentication page

* enter your user credentials

* An access token will be diplayed on the screen
