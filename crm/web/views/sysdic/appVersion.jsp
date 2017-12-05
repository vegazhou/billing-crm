<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title>APP版本管理</title>
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
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> APP版本管理 <span>> APP版本列表</span></h1>
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
									<h2>APP版本列表</h2>
				
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
												<label class="label text-left">APP版本号:</label>
												<label class="input"> <i class="icon-prepend fa fa-user"></i><!-- fa-envelope-o -->
													<input type="text" id="s_versionNum" placeholder="请输入APP版本号" maxlength="20" data-pure-clear-button>
												</label>
											</section>
											
											<section class="col col-3 form-group">
												<label class="label text-left">HTML版本号:</label>
												<label class="input"> <i class="icon-prepend fa fa-user"></i><!-- fa-envelope-o -->
													<input type="text" id="s_htmlVersionNum" placeholder="请输入HTML版本号" maxlength="20" data-pure-clear-button>
												</label>
											</section>
											
											<section class="col col-3">
												<label class="label text-left">类型:</label>
												<select id="s_type" class="form-control">
													<option selected="selected" value="">请选择类型</option>
													<option value="1">Android</option>
													<option value="2">IOS</option>							
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
											<div class="well well-sm well-light " id="wid-id-c2">
												<div class="btn-group btn-group-md">
													
													<%-- <span class='btn btn-danger btn-deleteAll'><i class='fa fa-trash-o'></i> 批量删除 </span>	--%>		
													 <span class="btn btn-success btn-add"><i class="fa fa-plus"></i> 添加 </span> 
													<%-- <span class='btn btn-success btn-test'><i class='fa fa-file-o'></i> XML测试 </span> --%>
												</div>	
											</div>
											<!-- Toolbar End -->
										<table id="datatable" class="table table-bordered table-hover">
											<thead></thead>
											<tbody></tbody>
											<tfoot></tfoot>
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
					<div class="modal-dialog" style="width:700px;">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel">APP版本添加</h4>
							</div>
							<div class="modal-body">
				
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">
													  <section class="col col-2">
														<label class="label text-right"> 
															
														</label>
													</section>
													<section class="col col-10">
														<label class="input" style="text-align:right;vertical-align:middle;">
															<font color="red">* 必填项</font>
														</label>
													</section>
												</div>
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> 版本类型:
														</label>
													</section>
													<section class="col col-9">
														<div class="label">
															<input type="radio" name="type" value="1" id="type1" checked /> Android
															 &emsp;&emsp;&emsp;&emsp;&emsp;&emsp; 
															<input type="radio" name="type" value="2" id="type2" /> IOS
														</div>
													</section>
												</div>
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> &nbsp; APP版本号:
														</label>
													</section>
													<section class="col col-9">
														<label class="input"> <i class="icon-prepend fa fa-question-circle"></i>
															<input type="text" placeholder="请输入APP版本号" id="versionNum" maxlength="50">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>版本号长度不大于50个! </b>
															<span></span>
														</label>
													</section>
												</div>
												<div class="row">	
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red">* </font> 
															APP版本描述:
														</label>
													</section>
													<section class="col col-9">
														<label class="input"> <i class="icon-prepend fa fa-list-alt"></i>
															<input type="text" placeholder="请输入APP版本描述" id="desc" maxlength="200">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>版本描述长度不大于200个! </b>
															<span></span>
														</label>
													</section>
												</div>
												
												<div class="row">
													<section class="col col-3">
														<label class="label text-right">
															<font color="red">* </font>  
															APP版本下载地址:
														</label>
													</section>
													<section class="col col-9">
														<label class="input"> <i class="icon-prepend fa fa-list-alt"></i>
															<input type="text" placeholder="请输入APP版本下载地址" id="url" maxlength="200">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>下载地址长度不大于200个!</b>
															<span></span>
														</label>
													</section>	
												</div>
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> &nbsp; HTML版本号:
														</label>
													</section>
													<section class="col col-9">
														<label class="input"> <i class="icon-prepend fa fa-question-circle"></i>
															<input type="text" placeholder="请输入HTML版本号" id="htmlVersionNum" maxlength="50">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>版本号长度不大于50个! </b>
															<span></span>
														</label>
													</section>
												</div>
												<div class="row">	
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red">* </font> 
															HTML版本描述:
														</label>
													</section>
													<section class="col col-9">
														<label class="input"> <i class="icon-prepend fa fa-list-alt"></i>
															<input type="text" placeholder="请输入HTML版本描述" id="htmlDesc" maxlength="200">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>版本描述长度不大于200个! </b>
															<span></span>
														</label>
													</section>
												</div>
												
												<div class="row">
													<section class="col col-3">
														<label class="label text-right">
															<font color="red">* </font>  
															HTML版本下载地址:
														</label>
													</section>
													<section class="col col-9">
														<label class="input"> <i class="icon-prepend fa fa-list-alt"></i>
															<input type="text" placeholder="请输入HTML版本下载地址" id="htmlUrl" maxlength="200">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>下载地址长度不大于200个!</b>
															<span></span>
														</label>
													</section>	
												</div>
										
											</fieldset>
											
										</form>
										</div>
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-primary btn-save">	保存 </button>
								<button type="button" class="btn btn-primary btn-updateSave"> 保存 </button>
								<input type="hidden" id="pid">
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
		
	<jsp:include page="../public/js.jsp" />
	<script src="<c:url value="/static/js/page/sysdic/appVersion.js"/>" type="text/javascript" charset="utf-8"></script>
	</body>
</html>