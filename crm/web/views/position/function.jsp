<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title>职能管理</title>
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
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 职能管理 <span>> 职能列表</span></h1>
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
									<h2>职能列表</h2>
				
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
											<section class="col col-3">
												<label class="label text-left">一级行业:</label>
												<select id="s_industry1" class="form-control">
														<option selected="selected" value="-1">请选择一级行业</option>
												</select>
											</section>
											
											<section class="col col-3">
												<label class="label text-left">二级行业:</label>
												<select id="s_industry2" class="form-control">
														<option selected="selected" value="">请选择二级行业</option>
												</select>
											</section>
											
											<section class="col col-3 form-group">
												<label class="label text-left">职能名称:</label>
												<label class="input"> <i class="icon-prepend fa fa-user"></i><!-- fa-envelope-o -->
													<input type="text" id="s_functionName" placeholder="请输入职能名称" maxlength="20" data-pure-clear-button>
												</label>
											</section>
											
											<section class="col col-3 form-group">
												<label class="label text-left">职能英文名称:</label>
												<label class="input"> <i class="icon-prepend fa fa-user"></i><!-- fa-envelope-o -->
													<input type="text" id="s_functionEname" placeholder="请输入职能英文名称" maxlength="50" data-pure-clear-button>
												</label>
											</section>
											
										</div>
										<div class="row">
											<section class="col col-3">
												<label class="label text-left">职能类型:</label>
												<select id="s_type2" class="form-control">
														<option selected="selected" value="">全部</option>
														<option value="2">通用职能</option>
														<option value="1">非通用职能</option>
												</select>
											</section>
											
											<section class="col col-3">
												&nbsp;<br><br>
												<label class="input">
													<span class="btn btn-primary btn-sm btn-search" style="margin-top:5px"><i class="fa fa-search"></i> 查询 </span>
													<input type="hidden" name="s_industryId">
													<input type="hidden" id="u_industryId">
													<input type="hidden" id="u_functionId">
												</label>
											</section>
										</div>
									</form>
									</div>	
									<div class="well well-sm well-light " id="wid-id-c2">
												<div class="btn-group btn-group-md">
													<%--
													<span class='btn btn-danger btn-deleteAll'><i class='fa fa-trash-o'></i> 批量删除 </span>			
													<span class="btn btn-success btn-add"><i class="fa fa-plus"></i> 添加 </span>
													<span class="btn btn-success btn-modify"><i class="fa fa-plus"></i> 修改 </span>
													 <span class='btn btn-success btn-import'><i class='fa fa-file-o'></i> 批量导入 </span> --%> 
												</div>	
											</div>
											<!-- Toolbar End -->
										<table id="dataTable" class="table table-bordered table-hover">
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
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel">添加职能</h4>
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
													<section class="col col-8">
														<label class="input" style="text-align:right;vertical-align:middle;">
															<font color="red">* 必填项</font>
														</label>
													</section>
												</div>
												
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> 职能名称:
														</label>
													</section>
													<section class="col col-8">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="请输入职能名称" id="addFunctionName" maxlength="20">
															<b class="tooltip tooltip-top-left">
																<i class="fa fa-warning txt-color-teal"></i>
																字符长度不大于20个!
															</b>
															<span></span>
														</label>
													</section>
												</div>
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> 职能英文名称:
														</label>
													</section>
													<section class="col col-8">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="请输入职能英文名称" id="addFunctionEname" maxlength="50">
															<b class="tooltip tooltip-top-left">
																<i class="fa fa-warning txt-color-teal"></i>
																字符长度不大于50个!
															</b>
															<span></span>
														</label>
													</section>
												</div>
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> 职能一级行业:
														</label>
													</section>
													<section class="col col-8">
														<select id="addIndustry1" class="form-control">
																<option selected="selected" value="">请选择一级行业</option>
														</select>
														<span></span>
													</section>
												</div>
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> 职能二级行业:
														</label>
													</section>
													<section class="col col-8">
														<select id="addIndustry2" class="form-control">
																<option selected="selected" value="">请选择二级行业</option>
														</select>
														<span></span>
													</section>
												</div>
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> 职能类型:
														</label>
													</section>
													<section class="col col-8">
														<input type="radio" value="1" checked placeholder="系统默认" name="addType1" maxlength="10">系统默认
														&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
														<input type="radio" value="2" placeholder="机构自定义" name="addType1" maxlength="10">机构自定义
														<span></span>
													</section>
									 			</div>
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> 是否为通用职能:
														</label>
													</section>
													<section class="col col-8">
														<input type="radio" value="2" checked placeholder="通用职能" name="addType2" maxlength="10">通用职能
														&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
														<input type="radio" value="1" placeholder="非通用职能" name="addType2" maxlength="10">非通用职能
														<span></span>
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
								<button type="button" class="btn btn-primary btn-save">
									保存
								</button>
								<input type="hidden" id="edit_userid" value=""/>
								<input type="hidden" id="s_teacherId" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->

	
		<!-- =========================================模态框==========================================-->
		<!--
			调用方式：
				$("#id").modal("show");   //手动呼出
				$("#id").modal("hide");   //手动隐藏
				$("#id").modal("toggle"); //手动切换
		-->
		<!-- Input Modal -->
				<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel">修改职能</h4>
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
													<section class="col col-8">
														<label class="input" style="text-align:right;vertical-align:middle;">
															<font color="red">* 必填项</font>
														</label>
													</section>
												</div>
												
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> 职能名称:
														</label>
													</section>
													<section class="col col-8">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="请输入职能名称" id="updateFunctionName" maxlength="20">
															<b class="tooltip tooltip-top-left">
																<i class="fa fa-warning txt-color-teal"></i>
																字符长度不大于20个!
															</b>
															<span></span>
														</label>
													</section>
												</div>
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> 职能英文名称:
														</label>
													</section>
													<section class="col col-8">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" placeholder="请输入职能英文名称" id="updateFunctionEname" maxlength="50">
															<b class="tooltip tooltip-top-left">
																<i class="fa fa-warning txt-color-teal"></i>
																字符长度不大于50个!
															</b>
															<span></span>
														</label>
													</section>
												</div>
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> 职能状态:
														</label>
													</section>
													<section class="col col-8">
														<span style="line-height:36px">
															<input type="radio" value="1" disabled checked id="updateStatus_1" name="updateStatus">启用
															&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
															<input type="radio" value="0" disabled id="updateStatus_2" name="updateStatus">禁用
															<span></span>
														</span>
													</section>
												</div>
												<div class="row">
													<section class="col col-3">
														<label class="label text-right"> 
															<font color="red">*</font> 职能类型:
														</label>
													</section>
													<section class="col col-8">
														<span style="line-height:36px">
															<input type="radio" value="2" disabled checked id="updateType2_1" name="updateType2" maxlength="10">通用职能
															&emsp;&emsp;&emsp;&emsp;
															<input type="radio" value="1" disabled id="updateType2_2" name="updateType2" maxlength="10">非通用职能
															<span></span>
														</span>
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
								<button type="button" class="btn btn-primary btn-updateSave">
									保存
								</button>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
	<!-- =========================================模态框==========================================-->
		<!--
			调用方式：
				$("#id").modal("show");   //手动呼出
				$("#id").modal("hide");   //手动隐藏
				$("#id").modal("toggle"); //手动切换
		-->
		<!-- Input Modal -->
				<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" >
						<div class="modal-content" style="width:600px;height:600px">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="">职能详情</h4>
							</div>
							<div class="modal-body" id="functionDiv">
				
								<div class="row">
										
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											
											<form id="checkout-form" class="smart-form" novalidate="novalidate">
											
											<fieldset>
												<table  class="table table-bordered table-hover" style="text-align:center">
													<thead>
														<tr>
															<td style="width:25px">编号</td>
															<td>行业名称</td>
															<td>行业英文名称</td>
														</tr>
													</thead>
													<tbody id="functionDetail">
														
													</tbody>
													<tfoot>
		
													</tfoot>
												</table>
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
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
	
	<jsp:include page="../public/js.jsp" />
	<script src="<c:url value="/static/js/page/position/function.js"/>" type="text/javascript" charset="utf-8"></script>
	</body>
</html>