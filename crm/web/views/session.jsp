<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<% 
String token=request.getParameter("token");
String modules=request.getParameter("modules");
String userId=request.getParameter("userId");
String userName=request.getParameter("userName");
String orgId=request.getParameter("orgId");
System.out.println("here"+token+modules+" userId"+userId+" userName"+userName);
session.setAttribute("token",token);
session.setAttribute("modules",modules);
session.setAttribute("userId",userId);
session.setAttribute("userName",userName);
session.setAttribute("orgId",orgId);
%>
