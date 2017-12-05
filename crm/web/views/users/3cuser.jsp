<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

<title>用户管理</title>
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
					<i class="fa-fw fa fa-home"></i> 管理后台 <span>> 用户管理</span>
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
						<h2>查找用户</h2>
					</header>

					<!-- widget div-->
					<div>
						<!-- widget content -->
						<div class="widget-body no-padding">
							<!-- Toolbar -->
							</br>
							<ul class="nav nav-tabs slide-tab">
								<li class="active last-child"><a href="javascript:void(0);"><i class="fa fa-search"></i>&nbsp;&nbsp;查询条件<b class="caret"></b></a></li>
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
												<select id="channel" name="channel"  class="form-control">
													<option value='' selected>全部</option>
												</select>
												</label>
											</section>

											<section class="col col-1">
												<label class="label text-right">开始日期：</label>
											</section>
											<section class="col col-2">
												<label class="text-left input">
												<input type="text" name="reportStartTime"
													placeholder="点击选择创建日期" id="reportStartTime" readonly="readOnly">
												<span></span>
												</label>
											</section>
											<section class="col col-1">
												<label class="label text-right">结束日期：</label>
											</section>	
											<section class="col col-2">
												<label class="text-left input">
												<input type="text" name="reportEndTime"
													placeholder="点击选择创建日期" id="reportEndTime" readonly="readOnly">
												<span></span>
												</label>
											</section>
										</div>
										<div class="row div-4-cuser" style="display:none;" >

											<section class="col col-1">
												<label class="label text-right">用户搜索：</label> 
											</section>
											<section class="col col-2">
												<label class="input"> <i class="icon-prepend fa fa-home"></i>
													<input type="text" name="name" placeholder="输入手机/邮箱/妮称/姓名搜索" maxlength="20" id="name">
													<span></span>
												</label>
											</section>
											<section class="col col-1">
												<label class="label text-right">渠道名称：</label>
											</section>	
											<section class="col col-2">
												<label class="text-left input">
												<input type="text" name="channelNameKey" placeholder="渠道名称" id="channelNameKey">
												<span></span>
												</label>
											</section>											
											<section class="col col-1">
												<label class="label text-right">用户类型：</label>
											</section>	
											<section class="col col-2">
												<label class="label text-left">
												<select id="usertype" name="usertype"  class="form-control">
													<option value='0' selected>全部</option>
													<option value='1'>无企业号</option>
													<option value='2'>有企业号</option>
												</select>
												</label>
											</section>	
											<!-- 
											<section class="col col-1">
											</section>
											 -->
											<section class="col col-2">
												<label class="label text-left">
													<span class="btn btn-primary btn-search" style="margin-top:5px;padding: 6px 12px;"><i class="fa fa-search"></i> 查询 </span>
												</label>
											</section>	
										</div>
										<div class="row div-4-3cuser" style="display:none;">
											<section class="col col-9">
												<label class="label text-right">
													<span class="btn btn-primary btn-search" style="margin-top:5px;padding: 6px 12px;"><i class="fa fa-search"></i> 查询 </span>
												</label>
											</section>
									</fieldset>
								</form>
							</div>
						</div>
						<!-- end widget content -->
					</div>
					<!-- end widget div -->
				</div>

				<div class="jarviswidget jarviswidgetdiv userlist" style="display:none;">
					<div  class="no-border" style="height:30px;padding-top:2px"><a class="download_usersTableData" href="javascript:void(0);">下载报表数据</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="download_currentPageData" href="javascript:void(0);">下载当前页数据</a>&nbsp;&nbsp;&nbsp;&nbsp;<span class="progress-label" style="display:none;"></span><div id="progressbar" style="width:50%;display:none;"></div></div>
					<div class="well well-sm well-primary no-border">
						<table id="userTable" class="table table-bordered table-hover">
							<thead>

							</thead>
							<tbody>

							</tbody>
							<tfoot>

							</tfoot>
						</table>
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
	<script type="text/javascript" src="${ctx}/static/js/plugin/my97datePicker/WdatePicker.js${randomstr}"></script>
	<script type="text/javascript" src="${ctx}/static/js/page/users/cuser.js?${randomstr}" charset="utf-8"></script>
</body>
</html>