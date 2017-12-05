<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title>管理后台 </title>
		<meta name="description" content="">
		<meta name="author" content="">

		<!-- Use the correct meta names below for your web application
			 Ref: http://davidbcalhoun.com/2010/viewport-metatag 
			 
		<meta name="HandheldFriendly" content="True">
		<meta name="MobileOptimized" content="320">-->
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<jsp:include page="../public/css.jsp" />
	</head>
	<body class="">
	<object type='application/x-shockwave-flash' id='OsPlayer' name='OsPlayer' align='middle' width='800' height='500' 
																			data='osplayer.swf' codebase='swflash.cab#version=10,0,0'>
																		<param name='flashvars' value='autostart=true&provider=enc&file=<%=(String)request.getParameter("url")%>&skin=osskin.zip' id="urlplay">
																		<param name='wmode' value='transparent'>
											</object>
	
	</body>
</html>