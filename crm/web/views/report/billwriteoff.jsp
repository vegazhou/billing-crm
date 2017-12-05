<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title> 账单 </title>
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
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 账务管理 <span>> 回款 </span></h1>
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
										<h2>账单列表</h2>
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
									
									    	<ul class="nav nav-tabs slide-tab" style="display:none">
												<li class="active last-child"><a href="javascript:void(0);"><i
														class="fa fa-search"></i></i>&nbsp;&nbsp;查询条件<b class="caret"></b></a></li>
												<li class="extend-btn"></li>
											</ul>
										<!-- Toolbar -->
										<div class="hide-tab-panel">
										<form id="searchForm" method="POST" action="#" class="smart-form" novalidate="novalidate" onsubmit="return search()">
										<div class="row">
										
										  <section class="col col-3 form-group" style="display:none">
												<label class="label text-left">客户名称:</label>	
												<label class="input"> <i class="icon-prepend fa fa-user"></i>											
													<input type="text" name="s_sserviceid3" placeholder="请输入客户名称" data-pure-clear-button id="s_sserviceid3" disabled="true">	
												</label>
											</section>
											<section class="col col-3 form-group" style="display:none">
												<label class="label text-left">账单周期:</label>
												
												
												<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="s_searchStartTime_STANDSITE3" placeholder="点击选择账单周期" id="s_searchStartTime_STANDSITE3" readonly="readOnly">
															<span></span>
												</label>
											</section>
										
									
											
										
									
											
		
										</div>
									</form>		
									</div>
									<div class="well well-sm well-light " id="wid-id-c2">
												<div class="btn-group btn-group-md">												
														&nbsp;
												</div>	
												
												<div class="btn-group btn-group-md" style=" position:absolute;left:100px;">					
													预付款帐户余额: <span  id="repay" style="font-weight: bold;"></span>
												</div>	
												<div class="btn-group btn-group-md">					
													
												</div>	
												<div class="btn-group btn-group-md" style=" position:absolute;left:400px;">					
													预存款账户余额: <span  id="resave" style="font-weight: bold;"></span>
												</div>
												<div class="btn-group btn-group-md">

												</div>
												<div class="btn-group btn-group-md" style=" position:absolute;left:700px;">
													天客云账户余额: <span  id="ccDeposit" style="font-weight: bold;"></span>
												</div>
									</div>
									
									
									
									
									
									
									
										<div class="well well-sm well-light " id="wid-id-c2">
												<div class="btn-group btn-group-md">												
													<span  class="btn btn-success btn-confirm">一键扣款</span>													
												</div>	
												
												<div class="btn-group btn-group-md" style=" position:absolute;left:100px;">					
													所选总金额: <span  id="total"></span>
												</div>	
												<div class="btn-group btn-group-md">					
													
												</div>	
												<div class="btn-group btn-group-md" style=" position:absolute;left:400px;">					
													所选未支付金额: <span  id="totalnotpay"></span>
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
								<h4 class="modal-title" id="myModalLabel">调账</h4>
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
														<font color="red">* </font> 
															原支付金额:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" name="soldvalue" placeholder="" id="soldvalue" maxlength="100" disabled>
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>! </b>
															<span></span>
														</label>
													</section>
													
													
												</div>						
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															新支付金额:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" name="sname" placeholder="请输入新支付金额！" id="sname" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>新支付金额长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													
												</div>		
												
												
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															备注:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" name="scomment" placeholder="请输入备注！" id="scomment" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>备注长度不大于100个! </b>
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

			<!-- Input Modal -->
				<div class="modal fade" id="myModalConfirmDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelcontract">账单计算中</h4>
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
															账单计算中
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
		<script src="<c:url value="/static/js/plugin/my97datePicker/WdatePicker.js" />${randomstr}" type="text/javascript"></script>		
		<script src="<c:url value="/static/js/page/report/billwriteoff.js" />${randomstr} " type="text/javascript"></script>
		<!-- Search Ajax Example End -->
	</body>
</html>