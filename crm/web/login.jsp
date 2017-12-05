<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<% session.invalidate();%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<script>
	if (self && self.frameElement && self.frameElement.tagName && self.frameElement.tagName == "IFRAME" && top && top.location && top.location.href) {
		console.log("here");
		top.location.href = "${ctx}/login.jsp";
	}
	CTX_ROOT = '${ctx}';
</script>
<script src="<c:url value="/static/js/libs/jquery-1.9.1.min.js"/>"></script>
<script src="<c:url value="/static/js/common/common.js"/>${randomstr}"></script>
	<script src="<c:url value="/static/js/common/static.js"/>${randomstr}"></script>
<script src="<c:url value="/static/js/libs/jquery.cookie.js"/>"></script>
<script>
var cookie=$.cookie('iPlanetDirectoryPro');
console.log("1-----------",cookie);
if(cookie==undefined){    console.log("11-----------");
      

			
    $.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/api'});
	$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/api/'});
	//location.href=adminContextPath+"/sso?mu=orgadmin";
	location.href=SSO_URL + "/XUI/#login/&goto=" + ADMIN_URL;
	console.log("-----------");
}else{
    console.log("11-----------");
      var postURL = adminContextPath + "/v1/orgs/orgusers/getuserinfor";      	
	 url = adminServer + postURL.replace('/adminapi', adminContextPath) ;
				// var postData=JSON.stringify(login);

				$.ajax({
					url 	: 	url ,
					type	   : 	"post",
					dataType: "json",
			        contentType: "application/json; charset=utf-8",
					headers : {'Source' : '101','Token':cookie},
					data : "",
					success	:	function(data){
								console.log("here---");
								var modules="";
								//for (var index = 0; index < data.modules.length ; index ++) {
									//modules  =modules+";"+data.modules[index];
								//}
                                //setCookie("Token",data.token);
                                if (window.localStorage) {
								    localStorage.setItem("token", cookie);
								    localStorage.setItem("token-time", new Date().getTime());
								    localStorage.setItem("roleId", data.roles);
								    localStorage.setItem("orgId", data.orgId);
								    localStorage.setItem("userName", data.userName);
								}
                                storeSession(cookie,modules,data.userId,data.userName,data.orgId);
                                var parameter=window.location.href.split('page=')[1];
                                if(parameter!=""){
                                	console.log("here118");
                                	location.href="views/index.jsp?page="+parameter;
                                }else{
                                	console.log("here119");
                                	location.href="views/index.jsp";
                                }
								},
					error:function(data){
								console.log("here---1");
								$.cookie('iPlanetDirectoryPro', '567', { expires: -1 ,domain:'.ketianyun.com',path:'/'});
								console.log("----==",$.cookie('iPlanetDirectoryPro'));
								$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/api'});
								$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/api/'});
								location.href= SSO_URL + "/XUI/#login/&goto=" + ADMIN_URL;
							
								
								$("#msg").css("display","inline-block");
				 	            $("#msg").text("用户名或密码错误");
								}
				});
}
console.log("789",cookie);

function storeSession(token,modules,userId,userName,orgId){
				var postURL ="views/session.jsp";				
				$.ajax({
					url 	: 	postURL ,
					type	   : 	"post",
					dataType: "json",
			        contentType: "application/x-www-form-urlencoded",
					headers : {'Source' : '101'},
					data : {token:token,modules:modules,userId:userId,userName:userName,orgId:orgId
					
					},
					success	:	function(){
								//location.href="views/index.jsp";
								console.log("--------datahere111");
								},
					error:function(data){
				        //location.href="views/index.jsp";
						console.log("--------datahere");
					}			
					
				});
		}
</script>
<meta charset="utf-8">
<title>科天云后台管理</title>
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<style type="text/css">
body, html {
	margin: 0px;
	padding: 0px;
	height: 100%;
	font-family: "微软雅黑";
	
}
#waring-borwser{
	font-size: 16px;
}
#content {
	width: 100%;
	height: 100%;
	background: url(${ctx}/static/img/login/image_bg.png) no-repeat;
	background-size: cover; 
	-webkit-background-size: cover;/* 兼容Webkit内核浏览器如Chrome和Safari */ 
	-o-background-size: cover;/* 兼容Opera */ 
}

#head {
	width: 100%;
	height: 100px;
	background-image: url(${ctx}/static/img/login/head.png);
}

#footer {
	clear: both;
	position: absolute;
	bottom: 0px;
	width: 100%;
	height: 100px;
	background-image: url(${ctx}/static/img/login/head.png);
}

#head img {
	margin-top: 10px;
	margin-left: 25%;
}

#footer img {
	margin-top: 25px;
	margin-left: 36%;
}

#center_ {
	max-width:1140px;
	width: expression(this.width > 1140 ? 1140: 400);
	height: 384px;
	background-image: url(${ctx}/static/img/login/center.png);
	background-repeat: no-repeat;
	margin: 8% auto;
}

#login {
	width: 300px;
	height: 200px;
	margin:auto;
	padding-top:15%;
}
#login a{
	background:url(${ctx}/static/img/login/button.png) no-repeat;
	display:block;
	width:90px;
	height:40px;
}
#login a:HOVER{
	background:url(${ctx}/static/img/login/button1.png) no-repeat;
	display:block;
	width:90px;
	height:40px;
}
.cssInput {
	border: 1px solid #7A6F6F;
	border: 1px solid #7A6F6F \9; /*IE*/
	width: 250px;
	height: 25px; /*非IE高度*/
	height: 25px \9; /*IE高度*/
	padding-left: 5px; /*all*/
	line-height: 20px \9; /*IE*/
	-moz-border-radius: 3px; /*Firefox*/
	-webkit-border-radius: 3px; /*Safari和Chrome*/
	border-radius: 3px; /*IE9+*/
	background-color: white;
	outline: none;
}

.cssInput:focus { /*IE8+*/
	border-color: #78BAED;
	outline: 1px solid #78BAED; /*chrome*/
}
#msg_{
	background-image:url(${ctx}/static/img/login/header_bg.png);
	color:white;
	text-align:left;
	width:246px;
	padding:5px;
	position: absolute;
	top:-35px;
	font-family: "微软雅黑";
	font-size: 14px;
}
#msg{
	background-image:url(${ctx}/static/img/login/header_bg.png);
	color:white;
	text-align:left;
	width:210px;
	padding:5px 20px;
	font-family: "微软雅黑";
	font-size: 14px;
}

.screen-bg {position: fixed;left:0;top:0;width: 100%;height: 100%;z-index:-1;}
</style>
</head>

</html>