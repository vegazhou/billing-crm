<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

<title>留存率分析</title>
<meta name="description" content="">
<meta name="author" content="">

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<jsp:include page="../public/css.jsp" />
</head>
<body class="">
	<div>
		<div class="area-top clearfix ng-scope">
			<div class="pull-left header">
				<h3 class="title ng-binding">&nbsp;&nbsp;留存率分析</h3>
			</div>
		</div>

		<div class="container-fruid ng-scope>
			<section>
				<div class="row">
					<article class="col-sm-12">
						<div class="jarviswidget">
						    
							<div style="border:0px;height:55px;">
								选择渠道：
								<select id="channel" name="channel">
									<option value='' selected>全部</option>
								</select>
								&nbsp;&nbsp;&nbsp;&nbsp;
								报表结束日期：<input type="text" name="reportEndTime" placeholder="点击选择日期" id="reportEndTime" readonly="readOnly">
								<span></span> &nbsp;&nbsp;&nbsp;&nbsp; <label class="input"><span class="btn btn-primary btn-search" style="padding: 6px 12px;"><i class="fa fa-search"></i> 查询 </span></label> 
							</div>
							&nbsp;<br/>
							<!-- 
							<ul class="nav nav-tabs pull-right in" id="index-top-tab">
								<li class="active"><a data-toggle="tab" href="#container1" value="employee"><span class="hidden-mobile hidden-tablet">用户留存率</span></a>
								</li>
							</ul>
							-->
							<div class="widget-body">
								<div class="tab-pane fade active in padding-10 no-padding-bottom">
									<div class="row no-space">
										<div class="tab-content">
											<div class="tab-pane fade active in" id="container1" data-highcharts-chart="employee">
												<div class="highcharts-container" id="highcharts-employee"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
					</article>
				</div>
			</section>
		</div>
	</div>
	<!-- End Input Modal -->

	<jsp:include page="../public/js.jsp" />
	<script type="text/javascript" src="${ctx}/static/js/plugin/my97datePicker/WdatePicker.js${randomstr}"></script>
	<script type="text/javascript" src="${ctx}/static/js/highcharts.js${randomstr}" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/static/js/page/report/retention.js${randomstr}" charset="utf-8"></script>
</body>
</html>