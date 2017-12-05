<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title>线下课程管理</title>
		<meta name="description" content="">
		<meta name="author" content="">

		<!-- Use the correct meta names below for your web application
			 Ref: http://davidbcalhoun.com/2010/viewport-metatag 
			 
		<meta name="HandheldFriendly" content="True">
		<meta name="MobileOptimized" content="320">-->
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<jsp:include page="../public/css.jsp" />
		<script type="text/javascript" src="${ctx}/static/js/plugin/my97datePicker/WdatePicker.js"></script>
		<script type=text/javascript src="<c:url value ="/static/ueditor/ueditor.config.js" />"></script>
		<script type=text/javascript src="<c:url value ="/static/ueditor/ueditor.all.js"/>"></script>
		<LINK rel=stylesheet href="<c:url value ="/static/ueditor/themes/default/css/ueditor.css"/>"/>
	</head>
	<body class="">
			<div id="content" style="height:100%">

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 线下课程管理 <span>> 线下课程列表</span></h1>
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
									<h2>线下课程列表</h2>
				
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
												<label class="label text-left">标题名称:</label>
												<label class="input"> <i class="icon-prepend fa fa-user"></i><!-- fa-envelope-o -->
													<input type="text" id="s_title" placeholder="请输入标题名称" maxlength="20" data-pure-clear-button>
												</label>
											</section>
											
											<section class="col col-3">
												<label class="label text-left">状态:</label>
												<select id="s_status" class="form-control">
													<option selected="selected" value="">请选择状态</option>
													<option value="1">已保存</option>
													<option value="2">报名中</option>
													<option value="3">报名结束</option>
													<option value="4">已过期</option>								
												</select>
											</section>
											
											<section class="col col-3">
												<!-- <label class="label text-right">&nbsp;</label> -->
												&nbsp;<br><br>
													<span class="btn btn-primary btn-sm btn-search" style="margin-top:5px"><i class="fa fa-search"></i> 查询 </span>
													
											</section>
										</div>
										
									</form>
									</div>	
										<%-- </c:if> --%> 
										
											
											<div class="well well-sm well-light " id="wid-id-c2">
												<div class="btn-group btn-group-md">
													
													<%-- <span class='btn btn-danger btn-deleteAll'><i class='fa fa-trash-o'></i> 批量删除 </span>	--%>		
													 <span class="btn btn-success btn-add"><i class="fa fa-plus"></i> 添加 </span> 
													<%-- <span class='btn btn-success btn-import'><i class='fa fa-file-o'></i> 批量导入 </span> --%> 
												</div>	
											</div>
											<!-- Toolbar End -->
										<table id="datatable" class="table table-bordered table-hover">
											<thead>
												
											</thead>
											<tbody>
												
											</tbody>
											<tfoot>

											</tfoot>
										</table>
										<input type="hidden" name="s_recordCount" id="s_recordCount" value="">
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
				<!-- 
				<form id="detailForm" method="post" action="newsDetail.jsp" ></form>
				<form id="updateForm" method="post" action="newsUpdate.jsp" ></form>
				 -->
		</div>
		
		<!-- =========================================模态框==========================================-->
		<!--
			调用方式：
				$("#id").modal("show");   //手动呼出
				$("#id").modal("hide");   //手动隐藏
				$("#id").modal("toggle"); //手动切换
		-->
		<!-- Input Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:900px;">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel">添加线下课程</h4>
							</div>
							<div class="modal-body">
				
								
								<div class="row">
										
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											
											<form id="checkout-form" class="smart-form" novalidate="novalidate">
											
											<fieldset>
												
												<div class="row">
													  <section class="col col-3">
														<label class="label text-right"> 
															
														</label>
													</section>
													<section class="col col-9">
														<label class="input" style="text-align:right;vertical-align:middle;">
															<font color="red">* 必填项</font>
														</label>
													</section>
												</div>
												
												<div class="row">
													<section class="col col-1">
														<label class="label text-right"> 
															<font color="red">*</font> 课程标题:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="请输入课程标题" id="title" maxlength="50">
															<b class="tooltip tooltip-top-left">
																<i class="fa fa-warning txt-color-teal"></i>
																字符长度不大于50个!
															</b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-right"> 
															<font color="red">*</font> 开始时间:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="点击选择日期" id="startTime" readonly/>
															<span></span>
														</label>
													</section>
												</div>
												
												<div class="row">
													<section class="col col-1">
														<label class="label text-right"> 
															<font color="red">*</font> 讲师名称:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="请输入讲师名称,多个讲师以分号相隔" id="teacherIds" maxlength="50">
															<b class="tooltip tooltip-top-left">
																<i class="fa fa-warning txt-color-teal"></i>
																字符长度不大于50个!
															</b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-right"> 
															<font color="red">&nbsp;</font> 授课地址:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="请输入授课地址" id="location" maxlength="50">
															<b class="tooltip tooltip-top-left">
																<i class="fa fa-warning txt-color-teal"></i>
																字符长度不大于50个!
															</b>
															<span></span>
														</label>
													</section>
												</div>
												
												<div class="row">
													<section class="col col-1">
														<label class="label text-right"> 
															<font color="red">*</font> 课程时长:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="请输入课程时长,以分钟为单位" id="duration" maxlength="3">
															<b class="tooltip tooltip-top-left">
																<i class="fa fa-warning txt-color-teal"></i>
																请输入数字,范围为0-999!
															</b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-right"> 
															<font color="red">*</font> 人数上限:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="请输入报名人数上限" id="maxbookCount" maxlength="5">
															<b class="tooltip tooltip-top-left">
																<i class="fa fa-warning txt-color-teal"></i>
																请输入数字,范围为0-99999!
															</b>
															<span></span>
														</label>
													</section>
												</div>
												
												<div class="row">
													<section class="col col-1">
														<label class="label text-right"> 
															<font color="red">*</font> 机构名称:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="请输入供课机构名称" id="vendorName" maxlength="200">
															<b class="tooltip tooltip-top-left">
																<i class="fa fa-warning txt-color-teal"></i>
																字符长度不大于200个!
															</b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-right"> 
															<font color="red">*</font> 门票价格:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="请输入门票价格" id="price" maxlength="5">
															<b class="tooltip tooltip-top-left">
																<i class="fa fa-warning txt-color-teal"></i>
																请输入数字,范围为0-99999!
															</b>
															<span></span>
														</label>
													</section>
												</div>
												
												<div class="row">
													<section class="col col-1 logofile">
														<label class="label text-right"> 
															<font color="red">*</font> 封面图片:
														</label>
													</section>
													<section class="col col-5 image logofile" style="">
														<label class="input input-file state-success" >
															<div class="button" >
																  <input type="file" id="file" name="file" />选择
															</div>
															<input type="text" id="filetxt" readonly placeholder="请选择280*210像素图片" style="height:33px"/>
															<input type="hidden" id="logoimage" >
															<span></span>
														</label>
													</section>
													
													<section class="col col-1 logodiv">
														<label class="label text-right" style="line-height:150px"> 
															<font color="red">*</font> 封面图片:
														</label>
													</section>
													<section class="col col-5 image logodiv" >
														<label class="input input-file state-success" style="line-height:150px">
															<img style="width:245px;height:150px;" id="logoimg"> &nbsp; &nbsp; 
															<span class='btn btn-success btn-xs btn-changelogo'> 替换 </span>
														</label>
													</section>
													
													<section class="col col-1 postfile">
														<label class="label text-right"> 
															<font color="red">*</font> 海报图片:
														</label>
													</section>
													<section class="col col-5 image postfile" style="">
														<label class="input input-file state-success" >
															<div class="button" >
																  <input type="file" id="file1" name="file" />选择
															</div>
															<input type="text" id="filetxt1" readonly placeholder="请选择478*269像素图片" style="height:33px"/>
															<input type="hidden" id="postimage" >
															<span></span>
														</label>
													</section>
													
													<section class="col col-1 postdiv">
														<label class="label text-right" style="line-height:150px"> 
															<font color="red">*</font> 海报图片:
														</label>
													</section>
													<section class="col col-5 image postdiv" >
														<label class="input input-file state-success" style="line-height:150px">
															<img style="width:245px;height:150px;" id="postimg"> &nbsp; &nbsp; 
															<span class='btn btn-success btn-xs btn-changepost'> 替换 </span>
														</label>
													</section>
												</div>
												
												<div class="row">					
													<section class="col col-12">
														<DIV id=myEditor style="width: 100%; height: 300px"></DIV>
													</section>
												</div>
											</fieldset>
										</form>
										</div>
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<input type="hidden" id="pid">
								<input type="hidden" id="status">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-primary btn-save">
									保存
								</button>
								<button type="button" class="btn btn-primary btn-updateSave">
									保存
								</button>
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
	<jsp:include page="../public/js.jsp" />
	<script src="<c:url value="/static/js/page/product/offline.js"/>" type="text/javascript" charset="utf-8"></script>
	<script src="<c:url value="/static/js/page/news/ajaxfileupload.js"/>" type="text/javascript" charset="utf-8"></script>
	</body>
</html>