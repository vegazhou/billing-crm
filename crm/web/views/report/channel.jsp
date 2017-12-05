<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

<title>渠道分析</title>
<meta name="description" content="">
<meta name="author" content="">

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<jsp:include page="../public/css.jsp" />
</head>
<body class="">
	<div id="content">

		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<h1 class="page-title txt-color-blueDark">
					<i class="fa-fw fa fa-home"></i> 管理后台 <span>> 渠道分析</span>
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
						<h2>渠道分析</h2>
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
								<form action class="smart-form ">
									<fieldset>
										<div class="row" >
											<section class="col col-1">
												<label class="label text-right">选择渠道：</label>
											</section>	
											<section class="col col-2">
												<label class="label text-left"><select id="channel" name="channel">
													<option value='' selected>全部</option>
												</select></label>
											</section>
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

										</div>
										<div class="row form-group" >
											<section class="col col-1">
												<label class="label text-right">渠道名称：</label>
											</section>	
											<section class="col col-2">
												<label class="text-left input">
												<input type="text" name="channelNameKey" placeholder="渠道名称" id="channelNameKey" data-pure-clear-button>
												<span></span>
												</label>
											</section>
											<section class="col col-1">
											</section>
											<section class="col col-2">
												<label class="input label text-left">
													<span class="btn btn-primary btn-one-week">
														最近1周 </span>
													<span class="btn btn-primary btn-two-week">
														最近2周 </span>
													<span class="btn btn-primary btn-one-month">
														最近30天 </span>
												</label>
											</section>
											<section class="col col-1">
												<label class="input label text-left"><span
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

				<div class="jarviswidget chartwidget no-margin" style="display:none;">
					<ul class="nav nav-tabs pull-left in" id="index-top-tab">
						<li class="active"><a data-toggle="tab" href="#container1"
							value="company"><span class="hidden-mobile hidden-tablet">企业</span></a>
						</li>
						<li class=""><a data-toggle="tab" href="#container2"
							value="employee"><span class="hidden-mobile hidden-tablet">员工</span></a>
						</li>
						<li class=""><a data-toggle="tab" href="#container3"
							value="deal"><span class="hidden-mobile hidden-tablet">交易金额</span></a>
						</li>
						<li class=""><a data-toggle="tab" href="#container4"
							value="dealcount"><span class="hidden-mobile hidden-tablet">定单数</span></a>
						</li>
						<li class=""><a data-toggle="tab" href="#container5"
							value="study"><span class="hidden-mobile hidden-tablet">学习时间</span></a>
						</li>
						<li class=""><a data-toggle="tab" href="#container6"
							value="content"><span class="hidden-mobile hidden-tablet">内容</span></a>
						</li>
					</ul>

					<div class="fade active in no-padding">
						
							<div class="tab-content">
								<div class="tab-pane chartonly fade active in" id="container1" data-highcharts-chart="company">
									<div class="highcharts-container" id="highcharts-company"></div>

								</div>
								<div class="tab-pane employee fade" id="container2" data-highcharts-chart="employee">
									<div class="highcharts-container" id="highcharts-employee"></div>
									<div class="widget-body table">
										<div class="row no-space">
										
											<table id="retentionDataTable" class="table table-bordered table-hover" style='width:100%'>
												<thead>
													<tr><th colspan='11'><header style='margin-top:20px;'><h2 class='no-margin no-padding'> <b> 用户留存率：</b></h2></header></th></tr>
													<tr><th>首次使用时间</th><th>新增用户</th><th colspan="9">留存率</th></tr>
													<tr><th colspan='2'></th><th>1天后</th><th>2天后</th><th>3天后</th><th>4天后</th><th>5天后</th><th>6天后</th><th>7天后</th><th>14天后</th><th>30天后</th></tr>
												</thead>
												<tbody>
													<tr><td>日期</td><td>新增数</td><td>1天后</td><td>2天后</td><td>3天后</td><td>4天后</td><td>5天后</td><td>6天后</td><td>7天后</td><td>14天后</td><td>30天后</td></tr>
												</tbody>
												<tfoot>
					
												</tfoot>
											</table>
										</div>
									</div>
								</div>
								
								<div class="tab-pane chartonly fade" id="container3" data-highcharts-chart="deal">
									<div class="highcharts-container" id="highcharts-deal"></div>
								</div>
								<div class="tab-pane chartonly fade" id="container4" data-highcharts-chart="dealcount">
									<div class="highcharts-container" id="highcharts-dealcount"></div>
								</div>
								<div class="tab-pane chartonly fade" id="container5" data-highcharts-chart="study">
									<div class="highcharts-container" id="highcharts-study"></div>
								</div>
								<div class="tab-pane chartonly fade" id="container6" data-highcharts-chart="content">
									<div class="highcharts-container" id="highcharts-content"></div>
								</div>
							</div>
						
					</div>
				</div>
				<!-- end widget -->

				<div class="jarviswidget table" style="display:none;">
					<div class="widget-body table">
						<div><a class="download_orgsTableData" href="javascript:void(0);">下载报表数据</a></div>
						<div class="row no-space">
							<table id="orgsDataTable"
								class="table table-bordered table-hover">
								<thead>

								</thead>
								<tbody>

								</tbody>
								<tfoot>

								</tfoot>
							</table>
						</div>
						<div class="row no-space" style="height:40px;"></div>
						<div><a class="download_channelTableData" href="javascript:void(0);">下载报表数据</a></div>
						<div class="row no-space">
							<table id="channelDataTable"
								class="table table-bordered table-hover">
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
		src="${ctx}/static/js/page/report/channel.js?4${randomstr}"
		charset="utf-8"></script>
</body>
</html>