<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Basic Styles -->
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/static/css/bootstrap.min.css"/>">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value= "/static/css/font-awesome.min.css"/>">

<!-- SmartAdmin Styles : Please note (smartadmin-production.css) was created using LESS variables -->
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/static/css/smartadmin-production.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/static/css/smartadmin-skins.css" />">
<!-- Demo purpose only: goes with demo.js, you can delete this css when designing your own WebApp -->
<!-- FAVICONS -->
<%--<link rel="shortcut icon" href="<c:url value="/static/img/favicon/logosm.jpg" />" type="image/x-icon">--%>
<%--<link rel="icon" href="<c:url value="/static/img/favicon/logosm.jpg" />" type="image/x-icon">--%>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/static/css/extra_style.css" />">


	<script type="text/javascript">
		       var token = '<%=(String)session.getAttribute("token")%>';
               var moduels = '<%=(String)session.getAttribute("modules")%>';
               var userId = '<%=(String)session.getAttribute("userId")%>';
               var orgId = '<%=(String)session.getAttribute("orgId")%>';
               var G_CTX_ROOT='/api';
               <%
               if(session.getAttribute("randomstr") == null){
            	 session.setAttribute("randomstr", "?" + java.lang.Math.random());
               }
               request.setAttribute("ctx", request.getContextPath());
               %>
               var IMAGE_PATH = 'http://pic.yxt.com/';  //本地测试http://7xi2ao.com2.z0.glb.qiniucdn.com/
               var CTX_ROOT='<%=request.getContextPath()%>';
               var picUrl="http://stream1.yunxuetang.com/";
               
               
               if( window.localStorage){
				var tokentime = localStorage.getItem("token-time");
				if(tokentime &&  (new Date().getTime() - new Date(parseInt(tokentime)).getTime()) < 2 * 60 * 60 * 1000){
					roleId = localStorage.getItem("roleId");
					orgId = localStorage.getItem("orgId");
				}
				}
		</script>