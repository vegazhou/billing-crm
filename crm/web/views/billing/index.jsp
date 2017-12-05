<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="utf-8">
    <title> PSTN计费/PDF账单/发送账单 </title>
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/font-awesome.min.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/libs/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/billing/react-with-addons.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/billing/react-dom.js"></script>
</head>
<body>
<div id="c">loading...</div>
<script type="text/javascript">
    REACT_ROUTER_BASE_NAME = '<%=request.getContextPath()%>/views/billing';
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/billing/main.js"></script>
</body>
</html>