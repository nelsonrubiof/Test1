/**
*
*	Login
*	Rodrigo Russell / rodrigo.russell@eureka.cl
*	Create: 24/03/2014
*	Update: 02/05/2014
*
**/
var BASE_REST = URL.login;
var Login = {

	verificar: function() {
		if(typeof($.cookie('data')) == 'undefined') {
			console.error('No logueado');
			location.href='login.html';
			return false;
		}
		eval('var data = ' + Base64.decode($.cookie('data')));
		if(!data.time || !data.token) {
			console.error('No logueado');
			location.href='login.html';
			return false;
		}
		return true;
	},

	getUsername: function() {
		this.verificar();
		eval('var data = ' + Base64.decode($.cookie('data')));
		return data.username;
	},

	getToken: function() {
		this.verificar();
		eval('var data = ' + Base64.decode($.cookie('data')));
		return data.token;
	},

	getTime: function() {
		this.verificar();
		eval('var data = ' + Base64.decode($.cookie('data')));
		return data.time;
	},

	logIn: function(username, password) {
		password = Sha256.hash(password);
		$.ajax({
			url: BASE_REST + 'loginApp/' + username + '/' + password + '/6/test',
			type: 'post',
			crossDomain: true,
			success: function(response) {
				var data = "{" +
					"username: '" + username + "'," +
					"password: '" + password + "'," +
					"time: " + Math.floor(new Date().getTime() / 1000) + "," +
					"token:" + response +
				"}";
				$.cookie('data', Base64.encode(data));
				location.href='index.html?s=stores/read';
			},
			error: function(request, status, error) {
				console.error(request.responseText);
				console.log(status);
        		console.error(error);
				//console.log('ERROR');
				//console.error(response);
			},
			statusCode: {
				403: function() {
					console.error('Forbidden');
				},
				404: function() {
					console.error('Not found');
				},
				500: function() {
					console.error('Internal Server Error');
				}
			}
		});
	},

	logOut: function() {
		$.ajax({
			url: BASE_REST + 'logout/' + this.getToken(),
			type: 'get',
			crossDomain: true,
			success: function(response) {
				$.removeCookie('data');
				Login.verificar();
			},
			error: function(request, status, error) {
				console.error(request.responseText);
				console.log(status);
        		console.error(error);
				//console.log('ERROR');
				//console.error(response);
			},
			statusCode: {
				403: function() {
					console.error('Forbidden');
				},
				404: function() {
					console.error('Not found');
				},
				500: function() {
					console.error('Internal Server Error');
				}
			}
		});
	}
}

var statusErrorMap = {
	'400' : "Server understood the request but request content was invalid.",
	'401' : "Unauthorised access.",
	'403' : "Forbidden resouce can't be accessed",
	'500' : "Internal Server Error.",
	'503' : "Service Unavailable"
};
