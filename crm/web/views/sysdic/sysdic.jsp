<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title>管理后台 </title>
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
			<div id="content">

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 管理后台 <span>> 字典管理</span></h1>
					</div>
					
				</div>

				<!-- 提示信息 -->
				
				<div class="alert alert-danger fade in hidden" id="warning-box">
					<button class="close" data-dismiss="alert">
						×
					</button>
					<i class="fa-fw fa fa-warning"></i>
					<strong id="warning-box-title"></strong><span id="warning-box-body"></span>
				</div>
				
				<div class="alert alert-success fade in hidden" id="success-box">
					<button class="close" data-dismiss="alert">
						×
					</button>
					<i class="fa-fw fa fa-warning"></i>
					<strong id="success-box-title"></strong><span id="success-box-body"></span>
				</div>					


				<!-- search widget -->
				<section id="widget-grid" class="">
					
				<!-- widget grid -->
					<!-- row -->
				
						<!-- NEW WIDGET START -->
						<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3" data-widget-colorbutton="false" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-fullscreenbutton="false"
									data-widget-deletebutton="false">
							<header>
								<span class="widget-icon"> <i class="fa fa-resize-vertical"></i> </span>
								<h2 class="font-md">条件查询</h2>				
													
							</header>
								<!-- widget div-->
								<div>
				
									<!-- widget edit box -->
									<div class="jarviswidget-editbox">
										
									</div>
									<!-- end widget edit box -->
									</br>
									<!-- widget content -->
									<div class="widget-body no-padding">
										<!-- Toolbar -->
										<ul class="nav nav-tabs slide-tab">
												<li class="active last-child"><a href="javascript:void(0);"><i
														class="fa fa-search"></i>&nbsp;&nbsp;查询条件<b class="caret"></b></a></li>
												<li class="extend-btn"></li>
											</ul>
										<!-- Toolbar -->
										<div class="hide-tab-panel">
														<form id="searchForm" method="POST" action="#" class="smart-form" novalidate="novalidate" onsubmit="return search()">
										<div class="row">
											<section class="col col-3 form-group">
												<label class="label text-left">字典名称:</label>
												<label class="input"> <i class="icon-prepend fa fa-user"></i><!-- fa-envelope-o -->
												    <input type="text" id="s_name" name="s_name" placeholder="请输入字典名称" maxlength="20" data-pure-clear-button>
												</label>
											</section>
											
											<section class="col col-3">
												<label class="label text-left">字典类型:</label>
												<select id="s_type" name="s_type" class="form-control">
													<option selected="selected" value="1">企业规模</option>
													<option value="2">渠道</option>
													<option value="3">模块</option>
													<option value="4">code</option>
													<option value="11">邮箱黑名单</option>
													<option value="12">手机号黑名单</option>
													<option value="13">运营白名单</option>
												</select>
											</section>
											<section class="col col-3">
												<label class="label text-left">状态:</label>
												<select id="s_status" name="s_status" class="form-control">
													<option selected="selected" value="">所有状态</option>
													<option value="0">禁用</option><option value="1">启用</option>
													
												</select>
											</section>
											<section class="col col-3">
												<label class="label text-left">&nbsp;</label>
											
												<span class="btn btn-primary btn-sm btn-search"><i class="fa fa-search"></i> 查询 </span>
											
											</section>
										</div>
									</form>	
									</div>
											<div class="well well-sm well-light " id="wid-id-c2">
												<div class="btn-group btn-group-md">
													<span  class="btn btn-success btn-add"><i class="fa fa-plus"></i> 添加 </span>
												</div>	
											</div>
											<!-- Toolbar End -->
										<table id="dictsDatatable" class="table table-bordered table-hover">
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
		<!-- =========================================模态框==========================================-->
		<!--
			调用方式：
				$("#id").modal("show");   //手动呼出
				$("#id").modal("hide");   //手动隐藏
				$("#id").modal("toggle"); //手动切换
		-->
		<!-- Input Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:800px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel">字典添加</h4>
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
															字典编号:
														</label>
													</section>
													<section class="col col-6">
														<label class="input"> <i class="icon-prepend fa fa-question-circle"></i>
															<input type="text" name="spid" placeholder="不输入则自动生成！" id="spid" >
															<span></span>
														</label>
													</section>
													</div>
													<div class="row"  id='dictsubtypeDiv' style='display:none;'>	
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															子类型:
														</label>
													</section>
													<section class="col col-6">
														<select id="s_dictsubtype" name="s_dictsubtype" class="form-control" disabled>
															<option value="1" selected="selected">企业渠道</option>
															<option value="2">用户渠道</option>
														</select>
													</section>
													</div>													
													<div class="row">	
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															字典名称:
														</label>
													</section>
													<section class="col col-6">
														<label class="input"> <i class="icon-prepend fa fa-list-alt"></i>
															<input type="text" name="sname" placeholder="字典名称" id="sname" maxlength="20">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>字典名称长度不大于20个! </b>
															
															<span></span>
														</label>
													</section>
												</div>
												
												<div class="row">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">&nbsp;</font>  
															字典类型:
														</label>
													</section>
													<section class="col col-6">
														<label class="label text-left"> 															
															<span id="stypename"></span>
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
								<input type="hidden" id="edit_dicid" />
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->

	<jsp:include page="../public/js.jsp" />
	<script src="<c:url value="/static/js/page/sysdic/sysdic.js" /> " type="text/javascript"></script>
	</body>
</html>