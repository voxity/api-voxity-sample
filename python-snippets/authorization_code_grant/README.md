This snippet implements the Authorization Code Grant authentication flow as specified by OAUTH2.0 RFC6749

In goal to use properly this snippet : 

* install the packages given in require.txt with the command below
``` 
pip install -r require.txt 
```

* set your application credentials in main.py :
```python
# This information is obtained upon registration of a new Voxity application with OAuth2.0 authentication
client_id = "<your client key>"
client_secret = "<your client secret>"
authorization_base_url = 'https://api.voxity.fr/api/v1/dialog/authorize'
token_url = 'https://api.voxity.fr/api/v1/oauth/token'
redirect_uri = <your redirect_uri registred on voxity service> #in this example you should set it to <http://localhost:5000/callback>
```

* launch the program :
```
python main.py
```

* with your browser, go to http://localhost:5000/

* you will be redirected to voxity authentication page

* enter your user credentials

* Your 10 last days call logs will be displayed as a json format
