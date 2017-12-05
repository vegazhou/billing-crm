<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en-us">
<head>
<script src="<c:url value="/static/js/libs/jquery-1.9.1.min.js"/>"></script>
<script src="<c:url value="/static/js/common/static.js"/>"></script>
<script src="<c:url value="/static/js/libs/jquery.cookie.js"/>"></script>
<script>
/*
var adminServer = "http://localhost:8081"; //default local server. Example: http://localhost:8080
var adminContextPath = "/api";
var cookie=$.cookie('iPlanetDirectoryPro');
  var postURL = adminContextPath + "/v1/orgs/orgusers/setcookie";      	
	 url = adminServer + postURL.replace('/adminapi', adminContextPath) ;
	console.log("------12223",  $.cookie('iPlanetDirectoryPro'));
	
	$.ajax({
					url 	: 	url ,
					type	   : 	"get",
					dataType: "json",
			        contentType: "application/json; charset=utf-8",
					headers : {'Source' : '101','Token':cookie},
					data : "",
					success	:	function(data){
								console.log("here---");
								$.cookie('iPlanetDirectoryPro', '567', { expires: -1 ,domain:'.ketianyun.com',path:'/'});
								console.log("----==",$.cookie('iPlanetDirectoryPro'));
								//location.href="http://www.ketianyun.com:8081/api/sso?mu=http://www.ketianyun.com:8081/orgadmin/";
								
								},
					error:function(data){
								console.log("here---1");
								$.cookie('iPlanetDirectoryPro', '567', { expires: -1 ,domain:'.ketianyun.com',path:'/'});
								console.log("----==",$.cookie('iPlanetDirectoryPro'));
								//location.href="http://www.ketianyun.com:8081/api/sso?mu=http://www.ketianyun.com:8081/orgadmin/";
							
								
								
								}
				});*/
	
	
	$.cookie('iPlanetDirectoryPro', '567', { expires: -1 ,domain:'.ketianyun.com',path:'/'});
	//$.cookie('amlbcookie', '567', { expires: -1 ,domain:'.ketianyun.com',path:'/'});
	$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/orgadmin'});
	$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/orgadmin/'});
	$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/api'});
	$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/api/'});
	
	console.log("------212",$.cookie('iPlanetDirectoryPro'),$.cookie('JSESSIONID'));
	
	location.href= SSO_URL + "/XUI/#login/&goto=" + ADMIN_URL;
</script>
</head>

</html>