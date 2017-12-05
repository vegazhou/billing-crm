<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title> 业务管理 </title>
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
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 业务审核 <span>> 待审业务列表</span></h1>
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
										<h2>业务列表</h2>
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

		<!-- End Input Modal -->

<!-- Input Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel">业务添加</h4>
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
															业务名称:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" name="sname" placeholder="请输入业务名称！" id="sname" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>业务名称长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													
												</div>
										        
										        <div class="row">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															业务类型:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_sserviceid" name="s_sserviceid" class="form-control">
																<option value="WEBEX_MC">MC</option>
																<option value="WEBEX_EC">EC</option>
																<option value="WEBEX_TC">TC</option>
																<option value="WEBEX_SC">SC</option>
																<option value="WEBEX_EE">EE</option>
																</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>
												
																		
													
												</div>
										        
										 
												 <div class="row mcportssection">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															最大与会人数:														:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_ports_mc" name="s_ports_mc" class="form-control">														
																
																
																<option value="3">3</option>
																<option value="8">8</option>
																<option value="10">10</option>
																<option value="25">25</option>
																<option value="30">30</option>																
																<option value="50">50</option>
																<option value="100">100</option>
																<option value="200">200</option>
																<option value="999">999</option>
																<option value="1000">1000</option>
																</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>
												
																		
													
												</div>
												
														
												<div class="row tcportssection">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															最大与会人数:														:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_ports_tc" name="s_ports_tc" class="form-control">														
																
																
																<option value="30">30</option>
																<option value="50">50</option>
																<option value="100">100</option>
																<option value="200">200</option>
																<option value="300">300</option>
																<option value="1000">1000</option>
																</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>
												
																		
													
												</div>
										        
										 
												
												<div class="row ecportssection">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															最大与会人数:														:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_ports_ec" name="s_ports_ec" class="form-control">														
																
																
																<option value="100">100</option>
																<option value="300">300</option>
																<option value="500">500</option>
																<option value="1000">1000</option>
																<option value="2000">2000</option>
																<option value="3000">3000</option>
																</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>
													
												</div>
												<div class="row scportssection">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															最大与会人数:														:
														</label>
													</section>
													<section class="col col-4">
														<label class="input">
															<select id="s_ports_sc" name="s_ports_sc" class="form-control">
																<option value="5">5</option>
															</select>
															
														</label>
													</section>
														<section class="col col-1">
														<label class="label text-right">
														</label>
												</section>
												</div>

												<div class="row eeportssection">
													<section class="col col-2">
														<label class="label text-right">
															<font color="red">* </font>
															最大与会人数: :
														</label>
													</section>
													<section class="col col-4">
														<label class="input">
															<select id="s_ports_ee" name="s_ports_ee"
																	class="form-control">
																<option value="200">200</option>
																<option value="1000">1000</option>
															</select>
														</label>
													</section>

													<section class="col col-1">
														<label class="label text-right">
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
		 <script src="<c:url value="/static/js/page/businessaudit/businessaudit.js" />${randomstr} " type="text/javascript"></script>
		<!-- Search Ajax Example End -->
	</body>
</html>