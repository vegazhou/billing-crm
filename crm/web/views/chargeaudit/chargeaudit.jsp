<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title> 计费方案审核 </title>
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
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 计费方案审核 <span>> 待审计费方案列表</span></h1>
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
										<h2>待审计费方案列表</h2>
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
										<!-- Toolbar -->
										<div class="hide-tab-panel">
										<form id="searchForm" method="POST" action="#" class="smart-form" novalidate="novalidate" onsubmit="return search()">
										<div class="row">
											
											<section class="col col-3">
												<input type="hidden" name="s_state" placeholder="请输入合同名称" data-pure-clear-button value="WAITING_APPROVAL">
												<span class="btn btn-primary btn-sm btn-search"><i class="fa fa-refresh"></i> 刷新 </span>
										
											</section>
										</div>
									</form>		
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
								<h4 class="modal-title" id="myModalLabel">添加计费方案</h4>
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
															计费方案名称:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" name="sname" placeholder="请输入计费方案名称！" id="sname" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>计费方案名称长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													
												</div>						
								
												
												
										
											
												
												
												
												
														
												
										        
										        <div class="row">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															计费类型:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_sserviceid" name="s_sserviceid" class="form-control">														
																
																<option value="MONTHLY_PAY_BY_HOSTS">WebEx按用户数包月计费</option>
																<option value="MONTHLY_PAY_BY_PORTS">WebEx按并发数包月计费</option>
																<option value="EC_PAY_PER_USE">EC按次购买计费</option>
																<option value="EC_PREPAID">EC预存计费</option>
																<option value="MONTHLY_PAY_BY_STORAGE">WebEx存储计费</option>
																<option value="PSTN_STANDARD_CHARGE">WebEx电话语音标准计费</option>
																<%--<option value="PSTN_PACKAGE_CHARGE">PSTN_PACKAGE_CHARGE</option>--%>
																<option value="PSTN_MONTHLY_PACKET">WebEx电话语音包月计费</option>
																<option value="PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES">WebEx电话语音叠加包计费</option>
																<option value="PSTN_SINGLE_PACKET_FOR_ALL_SITES">WebEx电话语音叠加包(覆盖全站点)计费</option>
																<option value="TELECOM_CHARGE">电信会议通计费</option>
																</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>
												
																		
													
												</div>
										        
										 
												 <div class="row portssection">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															每月包分钟数:	
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														    <input type="text" name="s_MinutesPerMonth" placeholder="请输入每月包分钟数!" id="s_MinutesPerMonth" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>每月包分钟数不大于100个! </b>															
															<span></span>													
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>
												
																		
													
												</div>
												
												<div class="row portssection">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															月租费:														
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														    <input type="text" name="s_PricePerMonth" placeholder="请输入月租费!" id="s_PricePerMonth" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月租费不大于100个! </b>															
															<span></span>													
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>
												
																		
													
												</div>		
												
												<div class="row portssection">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															包分钟数:														
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														    <input type="text" name="packetMinutes" placeholder="请输入包分钟数!" id="packetMinutes" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>包分钟数不大于100个! </b>															
															<span></span>													
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															分钟
														</label>
												</section>
												
																		
													
												</div>				        
										 
												<div class="row portssection">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															价格:														
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														    <input type="text" name="packetPrice" placeholder="请输入价格!" id="packetPrice" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>价格不大于100个! </b>															
															<span></span>													
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															元
														</label>
												</section>
												
																		
													
												</div>

												
												
												<div class="row portssection">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															月租费:														
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														    <input type="text" name="unitPrice" placeholder="请输入月租费!" id="unitPrice" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月租费不大于100个! </b>															
															<span></span>													
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															元/月
														</label>
												</section>
												
																		
													
												</div>
												
											
											
											<div class="row portssection">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															单价:														
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														    <input type="text" name="unitPricePPU" placeholder="请输入单价!" id="unitPricePPU" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价不大于100个! </b>															
															<span></span>													
															
														</label>
													</section>	
													
													<section class="col col-1">
														<label class="label text-right"> 
															元/场
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
								<input type="hidden" id="edit_userid" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->

    <jsp:include page="../charge/fee.jsp" />		
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
		 <script src="<c:url value="/static/js/page/chargeaudit/chargeaudit.js" />${randomstr} " type="text/javascript"></script>
		<!-- Search Ajax Example End -->
	</body>
</html>