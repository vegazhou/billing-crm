<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title> 服务管理 </title>
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
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 服务管理 <span>> 服务列表</span></h1>
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
		
				
						 <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-c"
									data-widget-colorbutton="false" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-fullscreenbutton="false"
									data-widget-deletebutton="false">
									<header>
										<span class="widget-icon"> <i class="fa fa-table"></i>
										</span>
										<h2>服务列表</h2>
									</header>
				
								<!-- widget div-->
								<div>
				
									<!-- widget edit box -->
									<div class="jarviswidget-editbox">
										<!-- This area used as dropdown edit box -->
				
									</div>
									<!-- end widget edit box -->
				                   	</br>
									<!-- widget content -->
									<div class="widget-body no-padding">  
									
									    	<ul class="nav nav-tabs slide-tab">
												<li class="active last-child"><a href="javascript:void(0);"><i
														class="fa fa-search"></i></i>&nbsp;&nbsp;查询条件<b class="caret"></b></a></li>
												<li class="extend-btn"></li>
											</ul>
										<!-- Toolbar -->
										<div class="hide-tab-panel">
										<form id="searchForm" method="POST" action="#" class="smart-form" novalidate="novalidate" onsubmit="return search()">
										<div class="row">
										    <section class="col col-3 form-group">
												<label class="label text-left">服务名称:</label>
												<label class="input"> <i class="icon-prepend fa fa-user"></i>
													<input type="text" name="s_sservicename" placeholder="请输入服务名称" data-pure-clear-button>
												</label>
											</section>
										
											<section class="col col-3 form-group">
												<label class="label text-left">服务站点:</label>
												<label class="input"> <i class="icon-prepend fa fa-user"></i>
													<input type="text" name="s_sdomain" placeholder="请输入服务站点" data-pure-clear-button>
												</label>
											</section>
										
											<section class="col col-3 form-group">
												<label class="label text-left">服务关联企业:</label>
												<label class="input"> <i class="icon-prepend fa fa-user"></i>
													<input type="text" name="s_sname" placeholder="请输入服务关联企业" id="s_sname" data-pure-clear-button>
												</label>
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
												
													<!-- <span class="btn btn-danger btn-delete"><i class="fa fa-trash-o "></i> 批量删除 </span>	 -->
												
														
													<span  class="btn btn-success btn-add"><i class="fa fa-plus"></i> 添加 </span>
													
													
												</div>	
											</div>
											<!-- Toolbar End -->
										<table id="datatable_tabletools" class="table table-bordered table-hover">
											<thead>
												
											</thead>
											<tbody>
												
											</tbody>
											<tfoot>

											</tfoot>
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
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel">公司员工添加</h4>
							</div>
							<div class="modal-body">
				
								
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
											
											<div class="row">
											
													<section class="col col-1">
														<label class="label text-right"> 
														<font color="red">* </font> 
															服务名称:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" name="sservicename" placeholder="请输入服务名称！" id="sservicename" maxlength="200">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>服务名称长度不大于200个! </b>
															<span></span>
														</label>
													</section>
													
													
												</div>
												
													<div class="row">
													<section class="col col-1">
														<label class="label text-right">
														<font color="red">* </font>  
															服务类型:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> 
														
														
															<select id="serviceType" name="serviceType" class="form-control">
																
																
																<option value="IM" selected="">IM</option>
																<option value="WEBEX" selected="">WEBEX</option>
																<option value="HCS" selected="">HCS</option>																
															</select>
															
														</label>
													</section>													
													
													
												</div>
												<div class="row">
											        <div class="imtypesection">
													<section class="col col-1">
														<label class="label text-right"> 
														<font color="red">* </font> 
															服务站点:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" name="surl" placeholder="请输入服务站点！" id="surl" maxlength="2000">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>服务站点长度不大于2000个! </b>
															<span></span>
														</label>
													</section>
													</div>
													<section class="col col-1">
														<label class="label text-right">
														<font color="red">* </font>  
															总许可数:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" name="slicense" placeholder="请输入总许可数!" id="slicense" maxlength="7">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总许可数长度不大于8个! </b>															
															<span></span>
														</label>
													</section>
												</div>
											
								
												
												
										
												<div class="row meetingtypesection" style="display:none">
													<section class="col col-1">
														<label class="label text-right">
														<font color="red">* </font>  
															会议类型:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> 
														
														
															<select id="meetingType" name="meetingType" class="form-control">
																
																
																<option value="1" selected="">MC</option>
																<option value="2" selected="">EC</option>
																<option value="3" selected="">TC</option>
																<option value="4" selected="">SC</option>
																<option value="5" selected="">EE</option>
																</select>
															
														</label>
													</section>
													
													
												
													
													
													
													
												</div>
												   <div class="row" id="IMsection">
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="autoGrant"></label></section>
												<section class="col col-1"><div class="">
												<label class="label text-left">自动配置用户服务</label></div>	
												</section></div>
												
												<div class="row">
												<section class="col col-1">
														<label class="label text-right">													
															过期天数:
														</label>
													</section>
													<section class="col col-5">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" name="sservicename"  placeholder="请输入过期天数！" id="inputModel" maxlength="5">
															 <select name="EngineModel" id="SelectModel" size="5" style="height: auto; margin-left: 0px; position: absolute; display: none;">
															 	<option value="1">1天</option>
															  	<option value="30">30天</option>
															   	<option value="60">60天</option>
															    <option value="90">90天</option>
															     <option value="365">365天</option>
															 </select>
															 <b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>服务名称长度不大于200个! </b>
															 
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
								<input type="hidden" id="edit_userid" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->


    <input type="hidden" id="loginuserid" value=${userid}>
	<jsp:include page="../public/js.jsp" />

		<script type="text/javascript">
		
		var updateObj = null;
		
		function htmlencode(s){  
    		var div = document.createElement('div');  
    		div.appendChild(document.createTextNode(s));  
    		return div.innerHTML;  
		}  
		function htmldecode(s){  
    		var div = document.createElement('div');  
    		div.innerHTML = s;  
    		return div.innerText || div.textContent;  
		}

       	<c:if test="${Root == true}">	
		    var isRoot=true;            
		</c:if>                
		
		<c:if test="${Root == false}">	
		     var isRoot=false;            
		</c:if>   
			
           
			
			


		
		</script>
		 <script src="<c:url value="/static/js/page/orgservice/orgservice.js" />${randomstr}" type="text/javascript"></script>
		<!-- Search Ajax Example End -->
	</body>
</html>