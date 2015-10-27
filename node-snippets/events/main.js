var io_client = require( 'socket.io-client' );
var base_url = "https://api.voxity.fr/";

var socket = io_client.connect(base_url, {
	path:'/event/v1',
	query:"access_token=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
});

socket.on('connected', function(data){
	console.log('connected', data);
})

socket.on('error', function(data){
	console.log('errors', data);
})

socket.on('calls.bridged', function(data){
	console.log('new_state.ringing', data);
})

socket.on('calls.ringing', function(data){
	console.log('calls.ringing', data);
})

socket.on('calls.hangup', function(data){
	console.log('calls.hangup', data);
})
