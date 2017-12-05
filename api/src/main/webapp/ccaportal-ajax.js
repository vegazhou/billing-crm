if (!window.console) {var console = {};}
if (!console.log) {console.log = function() {};}
if (!console.debug) {console.debug = function() {};}
if (!ccaportal)
{
	var ccaportal = {};
}
ccaportal.ajax = {
	_parent: this,
	_tokenGenerator : null,
	error_html: '<div class="alert alert-warning system_error_msg alert-dismissable"> '+
				'	<button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>'+
				'	<strong>System Error.</strong>Try again later or, if the problem persists, contact your system administrator'+
				'	<a href="mailto:atcsg-soc@cisco.com" class="alert-link" style="color:#444">atcsg-soc@cisco.com</a>.'+
				'<div>',
	
	callAPI : function(options, token) {
		$.ajax({
			type : options.type,

			url : encodeURI(options.url),

			dataType: "json",
			
			headers : {'Authorization' : token, 'Cache-Control' : 'no-cache', 'Pragma' : 'no-cache'},

			data: options.data,
			
			cache: false,
			
			async: options.async,
			
			contentType: options.contentType,//options.contentType ? options.contentType:'application/x-www-form-urlencoded; charset=UTF-8',

			success:function(data){
				
				if (data)
				{	
					console.log("apisuccess",data);
						
						
						options.success.apply(ccaportal.ajax._parent,[data]);
					
				}
				
			},

			error:function(data){
				console.log("apifailed",data);
				if( typeof options.error == 'function'){
					
					options.error.apply(ccaportal.ajax._parent,[data]);
				}
			}
		});
	},
	
	tokenError : function(msg) {
		window.location.href = "/ccaportal/logout?CCAPORTAL_CSRFTOKEN=" + $('#csrf_token_input').val();
	},
	
	refreshToken : function() {
		var self = this;
		if(ccaportal.ajax._tokenGenerator)
		{
			clearTimeout(ccaportal.ajax._tokenGenerator);
		}
		var token = $("input[name='token']").val();
		var expires = $("input[name='expires']").val();
		//console.debug(expires);
		var refreshToken = $("input[name='refreshToken']").val();
		ccaportal.ajax._tokenGenerator = setTimeout(function(){
			//lock token
			$("input[name='tokenLock']").val(1);
			//refresh token
			$.ajax({
				type : 'GET',
				dataType : 'json',
				contentType : 'application/json',
				headers : {'Authorization' : token, 'Cache-Control' : 'no-cache', 'Pragma' : 'no-cache'},
				url : '/ccaportal/refresh_token/' + ccaportal.utils.urlEncode(refreshToken),
				cache: false,			
				success : function(data){
					if (data.content.health.status == "OK") {
						var newToken = data.content.data;
						expires = newToken.expires_in;
						token = newToken.access_token;
						$("input[name='token']").val(token);
						$("input[name='expires']").val(expires * 1000 - 5 * 60 * 1000);
						//unlock token
						$("input[name='tokenLock']").val(0);
						//reset timer
						self.refreshToken();						
					} else {
						self.tokenError("Refresh token error!");
					}
				},
				error: function(){
					self.tokenError("Refresh token error!");
				}
			});
		},expires);
	},
	
	wait : function(times, token, options) {
		var self = this;
		if(times > 0) {
			setTimeout(function(){
				if((parseInt($("input[name='tokenLock']").val()) == 0) && (token != $("input[name='token']").val())) {
					var newToken = $("input[name='token']").val();
					//call api use new token
					self.callAPI(options, newToken);
				} else {
					self.wait(times - 1, token, options);			
				}
			},200);
		} else {
			self.tokenError("Token timeout");
		}
	},

	request:function(options){//url, type, data,  success, error
		var self = this;
		if(options.parent){
			ccaportal.ajax._parent = options.parent;
		}
		console.log("=============1");
		var token = $("input[name='token']").val();
		var expires = $("input[name='expires']").val();
		var refreshToken = $("input[name='refreshToken']").val();
		if(parseInt($("input[name='tokenLock']").val()) == 0) {
			//console.debug("token is: " + token);
			this.callAPI(options, token);
		} else if (parseInt($("input[name='tokenLock']").val()) == 1) {
			//wait until token refreshed
			console.log("=============");
			self.wait(15, token, options);
		}			
				
	}
}