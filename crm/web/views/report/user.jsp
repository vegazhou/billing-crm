<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

<title>网站数据分析</title>
<meta name="description" content="">
<meta name="author" content="">

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<jsp:include page="../public/css.jsp" />
<style type="text/css">
	._dataTables_length {display:none}
	div.dataTables_length select {width:80px}
</style>
</head>
<body class="">
	<div id="content">

		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<h1 class="page-title txt-color-blueDark">
					<i class="fa-fw fa fa-home"></i> 管理后台 <span>> 网站数据分析</span>
				</h1>
			</div>

		</div>

		<!-- search widget -->
		<section id="widget-grid" class="">
			<!-- NEW WIDGET START -->
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<!-- Widget ID (each widget will need unique ID)-->
				<div class="jarviswidget jarviswidget-color-blueDark">
					<header>
						<span class="widget-icon"> <i class="fa fa-table"></i>
						</span>
						<h2>网站数据分析</h2>
					</header>

					<!-- widget div-->
					<div>
						<!-- widget content -->
						<div class="widget-body no-padding">
							<!-- Toolbar -->
							</br>
							<ul class="nav nav-tabs slide-tab">
								<li class="active last-child"><a href="javascript:void(0);"><i
										class="fa fa-search"></i>&nbsp;&nbsp;查询条件<b class="caret"></b></a></li>
								<li class="extend-btn"></li>
							</ul>

							<div class=" hide-tab-panel well well-sm well-light no-border no-padding"
								id="wid-id-c2">
								<form action class="smart-form  no-padding">
									<fieldset class="no-padding">
										<div class="row" style="padding-top:10px;padding-bottom:0px;">
											<section class="col col-1">
												<label class="label text-right">选择渠道：</label>
											</section>	
											<section class="col col-2">
												<label class="label text-left">
												<select id="channel" name="channel">
													<option value='' selected>全部</option>
												</select>
												</label>
											</section>
											<section class="col col-1">
												<label class="label text-right">选择行业：</label>
											</section>	
											<section class="col col-2">
												<label class="label text-left">
												<select id="industry" name="industry">
													<option value='' selected>全部</option>
												</select>
												</label>
											</section>
											<section class="col col-1  subin-col" style='display:none;'>
												<label class="label text-right">子行业：</label> 
											</section>
											<section class="col col-2  subin-col" style='display:none;'>
												<label  class="label text-left">
												<select id="subindustry" name="subindustry">
													<option value='' selected>全部</option>
												</select>
												</label>
											</section>
										</div>
										<div class="row" style="padding-top:0px;padding-bottom:0px;">
											<section class="col col-1">
												<label class="label text-right">开始日期：</label>
											</section>
											<section class="col col-2">
												<label class="text-left">
												<input type="text" name="reportStartTime"
													placeholder="点击选择日期" id="reportStartTime" readonly="readOnly">
												<span></span>
												</label>
											</section>
											<section class="col col-1">
												<label class="label text-right">结束日期：</label>
											</section>	
											<section class="col col-2">
												<label class="text-left">
												<input type="text" name="reportEndTime"
													placeholder="点击选择日期" id="reportEndTime" readonly="readOnly">
												<span></span>
												</label>
											</section>

											<section class="col col-3">
												<label class="input label text-center">
													<span class="btn btn-primary btn-one-week">
														最近1周 </span>
													<span class="btn btn-primary btn-two-week">
														最近2周 </span>
													<span class="btn btn-primary btn-one-month">
														最近30天 </span>
												</label>
											</section>
											<section class="col col-2">
												<label class="input label text-center"><span
													class="btn btn-primary btn-search"
													style="padding: 6px 12px;"><i class="fa fa-search"></i>
														查询 </span>
														</label>
											</section>
										</div>
									</fieldset>
								</form>
							</div>
						</div>
						<!-- end widget content -->
					</div>
					<!-- end widget div -->
				</div>
				
				<div class="jarviswidget" style="display:none;">
					<ul class="nav nav-tabs pull-left in" id="index-top-tab">
						<li class="active"><a data-toggle="tab" href="#container1"
							value="employee"><span class="hidden-mobile hidden-tablet">用户趋势</span></a>
						</li>
						<li class=""><a data-toggle="tab" href="#container2"
							value="deal"><span class="hidden-mobile hidden-tablet">订单趋势</span></a>
						</li>
						<li class=""><a data-toggle="tab" href="#container3"
							value="company"><span class="hidden-mobile hidden-tablet">企业趋势</span></a>
						</li>
						<li class=""><a data-toggle="tab" href="#container4"
							value="knowledge"><span class="hidden-mobile hidden-tablet">内容趋势</span></a>
						</li>
						<li class=""><a data-toggle="tab" href="#container5"
							value="study"><span class="hidden-mobile hidden-tablet">学习趋势</span></a>
						</li>
						<li class=""><a data-toggle="tab" href="#containerx"
							value="order"><span class="hidden-mobile hidden-tablet">企业排行</span></a>
						</li>
					</ul>

					<div class="tab-pane fade active in ">
						<div class="tab-content">
							<div class="tab-pane fade active in" id="container1"
								data-highcharts-chart="employee">
								<div class="highcharts-container" id="highcharts-employee"></div>
									<div class="widget-body table">
										<div class="row no-space">
											<table id="userDataTable" class="table table-bordered table-hover">
												<thead>
					
												</thead>
												<tbody>
					
												</tbody>
												<tfoot>
					
												</tfoot>
											</table>
										</div>
									</div>
							</div>
							<div class="tab-pane fade" id="container2"
								data-highcharts-chart="deal">
								<div class="highcharts-container" id="highcharts-deal"></div>
									<div class="widget-body table">
										<div class="row no-space">
											<table id="contractDataTable" class="table table-bordered table-hover">
												<thead>
					
												</thead>
												<tbody>
					
												</tbody>
												<tfoot>
					
												</tfoot>
											</table>
										</div>
									</div>
							</div>
							<div class="tab-pane fade" id="container3"
								data-highcharts-chart="company">
								<div class="highcharts-container" id="highcharts-company"></div>
									<div class="widget-body table">
										<div class="row no-space">
											<table id="companyDataTable" class="table table-bordered table-hover">
												<thead>
					
												</thead>
												<tbody>
					
												</tbody>
												<tfoot>
					
												</tfoot>
											</table>
										</div>
									</div>
							</div>
							<div class="tab-pane fade" id="container4"
								data-highcharts-chart="knowledge">
								<div class="highcharts-container" id="highcharts-knowledge"></div>
									<div class="widget-body table">
										<div class="row no-space">
											<table id="knowledgeDataTable" class="table table-bordered table-hover">
												<thead>
					
												</thead>
												<tbody>
					
												</tbody>
												<tfoot>
					
												</tfoot>
											</table>
										</div>
									</div>
							</div>
							<div class="tab-pane fade" id="container5"
								data-highcharts-chart="study">
								<div class="highcharts-container" id="highcharts-study"></div>
									<div class="widget-body table">
										<div class="row no-space">
											<table id="studyDataTable" class="table table-bordered table-hover">
												<thead>
					
												</thead>
												<tbody>
					
												</tbody>
												<tfoot>
					
												</tfoot>
											</table>
										</div>
									</div>
							</div>
							<div class="tab-pane fade" id="containerx">
								<div class="well well-sm well-primary no-border no-padding no-margin">

										<table id="reportDataTable" class="table table-bordered table-hover">
											<thead>
												
											</thead>
											<tbody>
												
											</tbody>
											<tfoot>

											</tfoot>
										</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- end widget -->
			</article>
			<!-- WIDGET END -->
			<!-- end row -->
		</section>
		<!-- end widget grid -->
	</div>
	<!-- End Input Modal -->

	<jsp:include page="../public/js.jsp" />
	<script type="text/javascript"
		src="${ctx}/static/js/plugin/my97datePicker/WdatePicker.js${randomstr}"></script>
	<script type="text/javascript"
		src="${ctx}/static/js/highcharts.js${randomstr}" charset="utf-8"></script>
	<script type="text/javascript"
		src="${ctx}/static/js/page/report/user.js${randomstr}"
		charset="utf-8"></script>
</body>
</html>