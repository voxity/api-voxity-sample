This snippet discribe event usage.

In goal to use properly this snippet : 

* install the packages given in package.json with the command below
``` 
npm install
```

We assume you retreived an access_token with OAuth2 authorization flow as describe in /node-snippets/authorization_code_grant
* set your access token main.js :
```javascript
var socket = io_client.connect(base_url, {
	path:'/event/v1',
	query:"access_token=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
});
```

* launch the program :
```
node main.js
```

* make a phone call with or to your voxity phone device

* events should be raised