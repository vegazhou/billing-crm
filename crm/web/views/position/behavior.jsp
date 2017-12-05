<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title>行为表现管理</title>
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
			<div id="content" style="height:100%">

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 行为表现管理 <span>> 行为表现列表</span></h1>
					</div>
					
				</div>

				<!-- 提示信息 -->
				
				<!-- 提示信息 -->
				<div class="alert alert-danger fade in hidden" id="warning-box">
					<button class="close" data-dismiss="alert">×</button>
					<i class="fa-fw fa fa-warning"></i> <strong id="warning-box-title"></strong><span
						id="warning-box-body"></span>
				</div>
		
				<div class="alert alert-success fade in hidden" id="success-box">
					<button class="close" data-dismiss="alert">×</button>
					<i class="fa-fw fa fa-warning"></i> <strong id="success-box-title"></strong><span
						id="success-box-body"></span>
				</div>				


				<!-- search widget -->
				<section id="widget-grid" class="">
				
				<!-- widget grid -->
				
				
					<!-- row -->
				
				
						<!-- NEW WIDGET START -->
						<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		
				
							<!-- Widget ID (each widget will need unique ID)-->
							<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-c" data-widget-colorbutton="false" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-fullscreenbutton="false"
									data-widget-deletebutton="false">
								<!-- widget options:
								usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">
				
								data-widget-colorbutton="false"
								data-widget-editbutton="false"
								data-widget-togglebutton="false"
								data-widget-deletebutton="false"
								data-widget-fullscreenbutton="false"
								data-widget-custombutton="false"
								data-widget-collapsed="true"
								data-widget-sortable="false"
				
								-->
								<header>
									<span class="widget-icon"> <i class="fa fa-table"></i> </span>
									<h2>行为表现列表</h2>
				
								</header>
				
								<!-- widget div-->
								<div>
				
									<!-- widget edit box -->
									<div class="jarviswidget-editbox">
										<!-- This area used as dropdown edit box -->
				
									</div>
									<!-- end widget edit box -->
				
									<!-- widget content -->
									<div class="widget-body no-padding">
										</br>
										<ul class="nav nav-tabs slide-tab">
											<li class="active last-child"><a href="javascript:void(0);"><i
													class="fa fa-search"></i></i>&nbsp;&nbsp;查询条件<b class="caret"></b></a></li>
											<li class="extend-btn"></li>
										</ul>
										
										<div class="hide-tab-panel">
										<!-- Toolbar -->
										<form id="searchForm" method="POST" action="#" class="smart-form" novalidate="novalidate" onsubmit="return search()">
										
										<div class="row">
											
											<section class="col col-3 form-group">
												<label class="label text-left">行为表现名称:</label>
												<label class="input"> <i class="icon-prepend fa fa-user"></i><!-- fa-envelope-o -->
													<input type="text" id="s_behaviorName" placeholder="请输入行为表现名称" maxlength="20" data-pure-clear-button>
													
												</label>
											</section>
											
											<section class="col col-3 form-group">
												<label class="label text-left">能力名称:</label>
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text" id="skillId" placeholder="请输入能力名称" val=""/>
													</div>
													<input type="hidden" name="s_skillId" id="s_skillId" />
												</label>
											</section>
											
											<section class="col col-3">
												<!-- <label class="label text-right">&nbsp;</label> -->
												&nbsp;<br><br>
													<span class="btn btn-primary btn-sm btn-search" style="margin-top:5px"><i class="fa fa-search"></i> 查询 </span>
													<input type="hidden" id="u_behaviorId">
													<input type="hidden" id="u_skillId">
														
											</section>
										</div>
										
									</form>
									</div>	
											<div class="well well-sm well-light " id="wid-id-c2">
												<div class="btn-group btn-group-md">
													
													<%-- <span class='btn btn-danger btn-deleteAll'><i class='fa fa-trash-o'></i> 批量删除 </span>	--%>		
													<%-- <span class="btn btn-success btn-add"><i class="fa fa-plus"></i> 添加 </span> --%>
													<%-- <span class='btn btn-success btn-import'><i class='fa fa-file-o'></i> 批量导入 </span> --%> 
												</div>	
											</div>
											<!-- Toolbar End -->
										<table id="datatable" class="table table-bordered table-hover">
											<thead></thead>
											<tbody></tbody>
											<tfoot></tfoot>
										</table>
									</div>
									<!-- end widget content -->
								</div>
								<!-- end widget div -->
							</div>
							<!-- end widget -->
						</article>
						<!-- WIDGET END -->
					<!-- end row -->
				
				</section>
				<!-- end widget grid -->
		</div>
	
	<jsp:include page="../public/js.jsp" />
	<script type=text/javascript src="<c:url value ="/static/js/page/position/behavior.js"/>"></script>
	</body>
</html>