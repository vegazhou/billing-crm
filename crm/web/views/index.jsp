<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title>科天BSS系统</title>
		<meta name="description" content="">
		<meta name="author" content="">

		<!-- Use the correct meta names below for your web application
			 Ref: http://davidbcalhoun.com/2010/viewport-metatag 
			 
		<meta name="HandheldFriendly" content="True">
		<meta name="MobileOptimized" content="320">-->
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<style>
			.menuActive{
				font-weight:bolder;
				color:white ;
				font-size:15px;
				background-color:#5B5B5B;
			}
		</style>
		<jsp:include page="public/css.jsp" />

		

	</head>
	<body class="" style="overflow:hidden" scroll="no">
		<!-- possible classes: minified, fixed-ribbon, fixed-header, fixed-width-->
      
		<!-- HEADER -->
		
		<header id="header">
			<div id="logo-group">
               
				<!-- PLACE YOUR LOGO HERE -->
				<span id="logo" style="margin-top:5px;margin-left:7px;">
					<!-- <img src="${ctx}/static/img/logo_1.png" alt="科天云管理后台" style="width:135px;height:40px;"> -->
					<img src="${ctx}/static/img/login/logo_new.png" alt="科天云管理后台" style="width:135px;height:40px;">
				</span>
				<!-- END LOGO PLACEHOLDER -->

				<!-- Note: The activity badge color changes when clicked and resets the number to 0
				Suggestion: You may want to set a flag when this happens to tick off all checked messages / notifications -->
				<span id="activity" class="activity-dropdown"> <i class="fa fa-user"></i> <b class="badge"> 21 </b> </span>

				<!-- AJAX-DROPDOWN : control this dropdown height, look and feel from the LESS variable file -->
				<div class="ajax-dropdown">

					<!-- the ID links are fetched via AJAX to the ajax container "ajax-notifications" -->
					<div class="btn-group btn-group-justified" data-toggle="buttons">
						<label class="btn btn-default">
							<input type="radio" name="activity" id="ajax/notify/mail.html">
							消息 (14) </label>
						<label class="btn btn-default">
							<input type="radio" name="activity" id="ajax/notify/notifications.html">
							消息 (3) </label>
						<label class="btn btn-default">
							<input type="radio" name="activity" id="ajax/notify/tasks.html">
							消息 (4) </label>
					</div>

					<!-- notification content -->
					<div class="ajax-notifications custom-scroll">

						<div class="alert alert-transparent">
							<h4></h4>
						</div>

						<i class="fa fa-lock fa-4x fa-border"></i>

					</div>
					<!-- end notification content -->

					<!-- footer: refresh area -->
					<span> Last updated on: 12/12/2013 9:43AM
						<button type="button" data-loading-text="<i class='fa fa-refresh fa-spin'></i> Loading..." class="btn btn-xs btn-default pull-right">
							<i class="fa fa-refresh"></i>
						</button> </span>
					<!-- end footer -->

				</div>
				<!-- END AJAX-DROPDOWN -->
			</div>

			<!-- projects dropdown -->
			<div id="project-context">
				<span class="label"></span> 
				<span id="project-selector" class="popover-trigger-element dropdown-toggle" data-toggle="dropdown">个人信息 <i class="fa fa-angle-down"></i></span>
				<ul class="dropdown-menu">
					<li><a href="javascript:void(0);">用户身份：管理员</a></li>
					<li><a href="javascript:void(0);">用户帐号：<%=(String)session.getAttribute("userName")%></a></li>
					
					<li class="divider"></li>
					<li><a href="${ctx}/logout.jsp" data-logout-msg="退出后您将不能操作系统，确定退出1？"><i class="fa fa-power-off"></i>退出</a></li>
				</ul>
			</div>
			<!-- end projects dropdown -->

			<!-- pulled right: nav area -->
			<div class="pull-right">

				<!-- collapse menu button -->
				<div id="hide-menu" class="btn-header pull-right">
					<span> <a href="javascript:void(0);" title="Collapse Menu"><i class="fa fa-reorder"></i></a> </span>
				</div>
				<!-- end collapse menu -->

				<!-- logout button -->
				<div id="logout" class="btn-header transparent pull-right">
					<span> <a href="${ctx}/logout.jsp" title="退出"
					data-logout-msg="退出后您将不能操作系统，确定退出？"><i class="fa fa-sign-out"></i></a>
				</span>
				</div>
				<!-- end logout button -->

				<!-- search mobile button (this is hidden till mobile view port) -->
				<div id="search-mobile" class="btn-header transparent pull-right">
					<span> <a href="javascript:void(0)" title="Search"><i class="fa fa-search"></i></a> </span>
				</div>
				<!-- end search mobile button -->

				<!-- input: search field -->
				<!-- 
				<form action="#search.html" class="header-search pull-right">
					<input type="text" placeholder="Find reports and more" id="search-fld">
					<button type="submit">
						<i class="fa fa-search"></i>
					</button>
					<a href="javascript:void(0);" id="cancel-search-js" title="Cancel Search"><i class="fa fa-times"></i></a>
				</form>
				-->
				<!-- end input: search field -->

				<!-- multiple lang dropdown : find all flags in the image folder -->
				<!--
				<ul class="header-dropdown-list hidden-xs">
					<li><a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<img alt="" src="${ctx}/static/img/china.png"> <span> 中文 </span> <i
							class="fa fa-angle-down"></i>
					</a>
						<ul class="dropdown-menu pull-right">
							<li class="active"><a href="javascript:void(0);"><img
									alt="" src="${ctx}/static/img/china.png">中文</a></li>
	
						</ul></li>
				</ul>
				-->
				<!-- end multiple lang -->

			</div>
			<!-- end pulled right: nav area -->

		</header>
		<!-- END HEADER -->

		<!-- Left panel : Navigation area -->
		<!-- Note: This width of the aside area can be adjusted through LESS variables -->
		<aside id="left-panel">

			<!-- User info -->
			<div class="login-info">
				<span> <!-- User image size is adjusted inside CSS, it should stay as it --> 
					
					<a href="${ctx}/views/home.jsp"  title="<c:out value="科天云订购系统" />" target="main">
						<span><i class="fa fa-home"></i> <c:out value="科天云业务支撑系统" /></span>
						<i class="fa fa-angle-down"></i>
					</a> 
					
				</span>
			</div>
			<!-- end user info -->

			<!-- NAVIGATION : This navigation is also responsive

			To make this navigation dynamic please make sure to link the node
			(the reference to the nav > ul) after page load. Or the navigation
			will not initialize.
			-->
			<nav>
				<!-- NOTE: Notice the gaps after each icon usage <i></i>..
				Please note that these links work a bit different than
				traditional hre="" links. See documentation for details.
				-->
		
				<ul class="menuitemIndex">
				
					<li class=" M100012">
						<a href="#" class="menuItem" title="<c:out value="销售支持" />"><i class="fa fa-lg fa-fw fa-desktop"></i> <span class="menu-item-parent"><c:out value="销售支持" /></span></a>

					     <ul>
					     	<li class="menuconfig M100003">
						     	<a class="menuItem" href="${ctx}/views/customer/customer.jsp"  target="main" title="<c:out value="客户管理" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="客户管理" /></span></a>
							</li>
							 <li class="menuconfig M100003">
								 <a class="menuItem" href="${ctx}/views/agent/agent.jsp"  target="main" title="<c:out value="代理商管理" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="代理商管理" /></span></a>
							 </li>
							 <li class="menuconfig M100003">
								 <a class="menuItem" href="${ctx}/views/reseller/reseller.jsp"  target="main" title="<c:out value="分销商管理" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="分销商管理" /></span></a>
							 </li>
							<li class="menuconfig M100005">
							     <a class="menuItem" href="${ctx}/views/contract/contract.jsp"  target="main" title="<c:out value="合同管理" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="合同管理" /></span></a>
							</li>
							<li class="menuconfig M100006">
							     <a class="menuItem" href="${ctx}/views/contractaudit/contractaudit.jsp"  target="main" title="<c:out value="合同审核" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="合同审核" /></span></a>
							</li>
					 	</ul>
					</li>     
					
					<li class=" M100013">
						<a href="#" class="menuItem" title="<c:out value="基础设置" />"><i class="fa fa-lg fa-fw fa-cog"></i> <span class="menu-item-parent"><c:out value="基础设置" /></span></a>
						 <ul>
							<li class="menuconfig M100001">
								<a class="menuItem" href="${ctx}/views/product/product.jsp"  target="main" title="<c:out value="产品管理" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="产品管理" /></span></a>
							</li>
		
							<li class="menuconfig M100002">
								<a class="menuItem" href="${ctx}/views/charge/charge.jsp"  target="main" title="<c:out value="资费管理" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="资费管理" /></span></a>
							</li>			
							
						
							
						
							<li class="menuconfig M100004">
							     <a class="menuItem" href="${ctx}/views/business/business.jsp"  target="main" title="<c:out value="业务管理" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="业务管理" /></span></a>
							</li>
							
						
							
							<li class="menuconfig M100007">
							     <a class="menuItem" href="${ctx}/views/productaudit/productaudit.jsp"  target="main" title="<c:out value="产品审核" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="产品审核" /></span></a>
							</li>
							
							<li class="menuconfig M100008">
							     <a class="menuItem" href="${ctx}/views/chargeaudit/chargeaudit.jsp"  target="main" title="<c:out value="资费审核" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="资费审核" /></span></a>
							</li>
							
							<li class="menuconfig M100009">
							     <a class="menuItem" href="${ctx}/views/businessaudit/businessaudit.jsp"  target="main" title="<c:out value="业务审核" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="业务审核" /></span></a>
							</li>
							
							<li class="menuconfig M100011">
							     <a class="menuItem" href="${ctx}/views/sales/sales.jsp"  target="main" title="<c:out value="销售员管理" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="销售员管理" /></span></a>
							</li>
						</ul>
					</li>  
					
					<li class=" M100010">
						<a href="#" class="menuItem" title="<c:out value="帐务管理" />"><i class="fa fa-lg fa-fw fa-credit-card"></i> <span class="menu-item-parent"><c:out value="帐务管理" /></span></a>

					     <ul>
					     	<li class="menuconfig M100010">
						     	<a class="menuItem" href="${ctx}/views/report/billformal.jsp?id=&name=" target="main" title="<c:out value="月账单管理" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="月账单管理" /></span></a>
							</li>
							 <li class="menuconfig M100010">
								 <a class="menuItem" href="${ctx}/views/report/billconfirm.jsp"  target="main" title="<c:out value="月账单生成" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="月账单生成" /></span></a>
							 </li>
							<li class="menuconfig M100010">
							     <a class="menuItem" href="${ctx}/views/report/account.jsp?id=&name="  target="main" title="<c:out value="账户管理" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="账户管理" /></span></a>
							</li>
							<li class="menuconfig M100010">
							     <a class="menuItem" href="${ctx}/views/contractconfirm/contractconfirm.jsp"  target="main" title="<c:out value="首付款收款" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="首付款收款 " /></span></a>
							</li>
							<li class="menuconfig M100010">
						     	<a class="menuItem" href="${ctx}/views/report/billexport.jsp?id=&name=" target="main" title="<c:out value="导出账单" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="导出账单" /></span></a>
							</li>
							<li class="menuconfig M100010">
						     	<a class="menuItem" href="${ctx}/views/report/billpay.jsp?id=&name=" target="main" title="<c:out value="缴费" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="缴费" /></span></a>
							</li>
							<li class="menuconfig M100010">
						     	<a class="menuItem" href="${ctx}/views/report/resellerbillpay.jsp?id=&name=" target="main" title="<c:out value="分销商缴费" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="分销商缴费" /></span></a>
							</li>
							<li class="menuconfig M100010">
						     	<a class="menuItem" href="${ctx}/views/report/billsap.jsp?id=&name=" target="main" title="<c:out value="SAP报表" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="SAP报表" /></span></a>
							</li>
							<li class="menuconfig M100010">
						     	<a class="menuItem" href="${ctx}/views/report/billsappay.jsp?id=&name=" target="main" title="<c:out value="SAP回款" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="SAP回款" /></span></a>
							</li>
							<li class="menuconfig M100010">
						     	<a class="menuItem" href="${ctx}/views/report/billcycle.jsp?id=&name=" target="main" title="<c:out value="周期性费用" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="周期性费用" /></span></a>
							</li>							
							
							
					     
					 	</ul>
					</li>

					<li class=" M1000101">
						<a href="#" class="menuItem" title="<c:out value="报表" />"><i class="fa fa-lg fa-fw fa-bar-chart-o"></i> <span class="menu-item-parent"><c:out value="报表" /></span></a>

						<ul>
							<li class="menuconfig M1000101">
								<a class="menuItem" href="${ctx}/views/report/customerreport.jsp"  target="main" title="<c:out value="客户一览" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="客户一览" /></span></a>
							</li>
							<li class="menuconfig M1000101">
								<a class="menuItem" href="${ctx}/views/report/sitereport.jsp"  target="main" title="<c:out value="站点一览" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="站点一览" /></span></a>
							</li>
							<li class="menuconfig M1000101">
								<a class="menuItem" href="${ctx}/views/report/webexrequest.jsp"  target="main" title="<c:out value="指令查询" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="指令查询" /></span></a>
							</li>
							<li class="menuconfig M1000101">
								<a class="menuItem" href="${ctx}/views/report/billage.jsp"  target="main" title="<c:out value="账龄查询" />"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent"><c:out value="账龄查询" /></span></a>
							</li>
						</ul>
					</li>


				</ul>
			
			</nav>
			<span class="minifyme"> <i class="fa fa-arrow-circle-left hit"></i> </span>

		</aside>
		<!-- END NAVIGATION -->

		<!-- MAIN PANEL -->
		<div id="main" role="main">

			<!-- RIBBON -->
			<div id="ribbon">

				<span class="ribbon-button-alignment"> <span id="refresh" class="btn btn-ribbon" data-title="refresh"  rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> Warning! This will reset all your widget settings." data-html="true"><i class="fa fa-refresh"></i></span> </span>

				<!-- breadcrumb -->
				<ol class="breadcrumb">
					<li>Home</li><li>Dashboard</li>
				</ol>

			</div>
			<!-- END RIBBON -->

			<!-- MAIN CONTENT -->
			<div id="wrap">
				<iframe src="${ctx}/views/home.jsp" id="ifm" scrolling="no" name="main" width="100%" height="100%"  frameborder="0"></iframe>
			</div>
			<!-- END MAIN CONTENT -->

		</div>
		<!-- END MAIN PANEL -->

		<!-- END SHORTCUT AREA -->

		<!--================================================== -->

		<!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
		<jsp:include page="public/js.jsp" />

		<script>
		
		    if(token=="null" || !token){
		    	if(window.localStorage){
					var tokentime = localStorage.getItem("token-time");
					if(tokentime &&  (new Date().getTime() - tokentime) < 2 * 60 * 60 * 1000){
						token = localStorage.getItem("token");
					}
		    	}
		    	if(!token || token=="null"){
		    		top.location.href="${ctx}/login.jsp";
		    	}
		    }
		    setTimeout("top.location.href='${ctx}/login.jsp';",2*60*60*1000);//2*60*60*1000
		    console.log( "------",window.localStorage,$.cookie('iPlanetDirectoryPro'));
		    if( window.localStorage){
				var tokentime = localStorage.getItem("token-time");
				if(tokentime &&  (new Date().getTime() - new Date(parseInt(tokentime)).getTime()) < 2 * 60 * 60 * 1000){
					roleId = localStorage.getItem("roleId");
					orgId = localStorage.getItem("orgId");
				}
			}
			var roles=roleId.split(",");
		    $(".menuconfig").hide();
		    $(".M100012").hide();
		    $(".M100013").hide();
		    $(".M100010").hide();
		    $(".M1000101").hide();
		    var nopriviledge=true;
			$.each(roles, function (key, value) {

			  if(value=="OPERATOR"||value=="SUPER_ADMIN"){
			  	$(".M100012").show();
		    	$(".M100013").show();

				$(".M100001").show();
				$(".M100002").show();
				$(".M100004").show();
				$(".M100003").show();
				$(".M100005").show();
				$(".M100011").show();
				$(".M1000101").show();
				nopriviledge=false;
				}
			
				if(value=="CONTRACT_AUDITOR"||value=="SUPER_ADMIN"){
					$(".M100012").show();		    
					$(".M100006").show();
					$(".M1000101").show();					
					nopriviledge=false;
				}
				
				if(value=="PRODUCT_AUDITOR"||value=="SUPER_ADMIN"){
					$(".M100013").show();
					$(".M100007").show();
					$(".M100009").show();
					$(".M1000101").show();
					nopriviledge=false;
				}
				
				if(value=="CHARGE_AUDITOR"||value=="SUPER_ADMIN"){
					$(".M100013").show();
					$(".M100008").show();
					$(".M1000101").show();
					
					nopriviledge=false;
				}
				
			
				
				if(value=="FIN_AUDITOR"||value=="SUPER_ADMIN"){
					
					$(".M100010").show();
					$(".M1000101").show();
					nopriviledge=false;
				}
				if(value=="READONLY"||value=="SUPER_ADMIN"){		
					
					$(".M1000101").show();
					nopriviledge=false;
				}
				
				
			});
		
			if(nopriviledge){
				$.cookie('iPlanetDirectoryPro', '567', { expires: -1 ,domain:'.ketianyun.com',path:'/'});
					//$.cookie('amlbcookie', '567', { expires: -1 ,domain:'.ketianyun.com',path:'/'});
					$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/orgadmin'});
					$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/orgadmin/'});
					$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/api'});
					$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/api/'});
					location.href=INVALID_URL;
			}
			
			
			/*
		    var menumoduels=moduels.split(";");
		    $(".menuconfig").hide();
		    $.each(menumoduels,function(key,value){
		    	
		    	if(value!=""){
		    		  $("."+value).show();
		    	}
		    
		    })
			 $("."+value).show();*/
			document.getElementById("wrap").style.height=(document.documentElement.clientHeight-110)+"px";
			$(document).ready(function() {
				// DO NOT REMOVE : GLOBAL FUNCTIONS!
				pageSetUp();
				$("#ribbon").html("") ;
				window.menuActive = null ;
				$(".menuParent").click(function(){
					$(".open ul").stop().hide(300) ;
					$(".open").removeClass("open") ;
					if(menuActive != null ){
						menuActive.removeClass("menuActive")
					}
					$(this).addClass("menuActive") ;
					menuActive = $(this) ;
				});
				$(".menuItem").click(function(){
					if(menuActive != null ){
						menuActive.removeClass("menuActive")
					}
					$(this).addClass("menuActive") ;
					menuActive = $(this) ;
				});
			});
			function home(){
				$("#ifm").attr("src","${ctx}/views/home.jsp");
			}
			
			var parameter=window.location.href.split('page=')[1];
			
			if(parameter=="contractaudit"){
				$("#ifm").attr("src","${ctx}/views/contractaudit/contractaudit.jsp");
			}else if(parameter=="productaudit"){
				$("#ifm").attr("src","${ctx}/views/productaudit/productaudit.jsp");
			}else if(parameter=="chargeaudit"){
				$("#ifm").attr("src","${ctx}/views/chargeaudit/chargeaudit.jsp");
			}else if(parameter=="businessaudit"){
				$("#ifm").attr("src","${ctx}/views/businessaudit/businessaudit.jsp");
			}else if(parameter=="contractconfirm"){
				$("#ifm").attr("src","${ctx}/views/contractconfirm/contractconfirm.jsp");
			}			else {
				$("#ifm").attr("src","${ctx}/views/home.jsp");
			}
		</script>
	</body>

</html>