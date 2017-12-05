<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title> 订单管理 </title>
		<meta name="description" content="">
		<meta name="author" content="">

		<!-- Use the correct meta names below for your web application
			 Ref: http://davidbcalhoun.com/2010/viewport-metatag 
			 
		<meta name="HandheldFriendly" content="True">
		<meta name="MobileOptimized" content="320">-->
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<jsp:include page="../public/css.jsp" />
		<style>
			.smart-form section{margin-bottom:0px;position:relative}
		</style>
	</head>
	<body class="">
			<div id="content" style="height:100%">

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 订单管理 <span>> <span class="contractName"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="reseller"></span></span></h1>
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
										<h2></h2>
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
										
									</div>	
											<div class="well well-sm well-light " id="wid-id-c2">
												<div class="btn-group btn-group-md">
												
													<!-- <span class="btn btn-danger btn-delete"><i class="fa fa-trash-o "></i> 批量删除 </span>	 -->
												
														
													<span  class="btn btn-success btn-add-contract"><i class="fa fa-plus"></i> 添加订单 </span>
													
													
												</div>	
												
												<div class="btn-group btn-group-md" style="float:right">
												
													<!-- <span class="btn btn-danger btn-delete"><i class="fa fa-trash-o "></i> 批量删除 </span>	 -->
												
													<span  class="btn btn-success btn-list-pdf"><i class="fa fa-plus"></i> 下载合同 </span>	&nbsp;
													<span  class="btn btn-warning btn-upload"><i class="fa fa-plus"></i> 上传合同 </span>
													<span  class="btn btn-success btn-submit-contract"> 送审 </span>
													
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
							
							
							
							
							<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-c"
									data-widget-colorbutton="false" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-fullscreenbutton="false"
									data-widget-deletebutton="false">
									<header>
										<span class="widget-icon"> <i class="fa fa-table"></i>
										</span>
										<h2></h2>
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
								
											<div class="well well-sm well-light " id="wid-id-c2">
												<div class="btn-group btn-group-md">
												
													<!-- <span class="btn btn-danger btn-delete"><i class="fa fa-trash-o "></i> 批量删除 </span>	 -->
												
														
													<span  class="btn btn-success btn-add"><i class="fa fa-plus"></i> 添加站点 </span>
													
													
												</div>	
											</div>
											<!-- Toolbar End -->
										<table id="datatable_tabletools_sites" class="table table-bordered table-hover">
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
								<h4 class="modal-title" id="myModalLabel">站点添加</h4>
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
															站点名称:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sname" placeholder="请输入站点名称！" id="sname" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>站点名称长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													
												</div>						
								
												
												
										<div class="row">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															主要语言:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_languageid" name="s_sserviceid" class="form-control">														
																
																<option value="SIMPLIFIED_CHINESE">简体中文</option>
																<option value="TRADITIONAL_CHINESE">繁体中文</option>
																<option value="ENGLISH">英语</option>
																<option value="JAPANESE">日语</option>
																<option value="KOREAN">韩语</option>
																<option value="FRENCH">法语</option>
																<option value="GERMAN">德语</option>
																<option value="ITALIAN">意大利语</option>
																<option value="SPANISH">西班牙语</option>
																<option value="SPANISH_CASTILLA">西班牙语(卡斯提尔)</option>																
																<option value="SWEDISH">瑞典语</option>
																<option value="HOLLAND">荷兰语</option>
																<option value="PORTUGUESE">葡萄牙语(巴西)</option>
																<option value="RUSSIAN">俄语</option>
																<option value="TURKEY">土耳其语</option>
																<option value="DANISH">丹麦语</option>
																</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>
												
																		
													
												</div>
											
												
										<div class="row">
													<section class="col col-2">
														<label class="label text-right">
														
															支持语言:
														</label>
													</section>
													<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="SIMPLIFIED_CHINESE">简体中文</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="TRADITIONAL_CHINESE">繁体中文</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="ENGLISH">英语</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="JAPANESE">日语</label></section>	
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="KOREAN">韩语</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="FRENCH">法语</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="GERMAN">德语</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="PORTUGUESE">葡萄牙语(巴西)</label></section>	
												
												
												
												
																		
													
												</div>		
												
											<div class="row">
													<section class="col col-2">
														<label class="label text-right">
														
															
														</label>
													</section>
													<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="SWEDISH">瑞典语</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="HOLLAND">荷兰语</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="ITALIAN">意大利语</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="RUSSIAN">俄语</label></section>	
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="TURKEY">土耳其语</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="DANISH">丹麦语</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="SPANISH">西班牙语</label></section>
												
												<section class="col col-1">
												<label class="label text-right"><input type="checkbox" class="SPANISH_CASTILLA">西班牙语(卡斯提尔)</label></section>	
												</div>
												
												<div class="row">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															时区:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_sserviceid" name="s_sserviceid" class="form-control">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>
												
																		
													
												</div>																									
										<div class="row">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red"> </font>  
															webex PSTN:
														</label>
													</section>
													<section class="col col-2">
																										
														
															<input type="radio" name="pstnradio" class="pstnradio" value="pstn"/>													
																
																
													
															
													
													</section>	
													
													<section class="col col-2">
														<label class="label text-right">
														<font color="red"> </font>  
															TSP:
														</label>
													</section>
													<section class="col col-2">
																										
														
															<input type="radio" name="pstnradio" class="pstnradio" value="tsp"/>													
																
																
													
															
														
													</section>	
													
												</section>
												
																		
													
										</div>						
												
										<div class="row">
													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>  
															电话区域表:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_location" name="s_location" class="form-control">

																<option value="KETIAN_CT_T30">KETIAN_CT_T30</option>
																<option value="KETIAN_CU_T30">KETIAN_CU_T30</option>
																<option value="KETIAN_FREE_T30">KETIAN_FREE_T30</option>
																<option value="KETIAN_GLOBAL_T30">KETIAN_GLOBAL_T30</option>
																<option value="KETIAN_CT">KETIAN_CT</option>
																<option value="KETIAN_CU">KETIAN_CU</option>
																<option value="KETIAN_FREE">KETIAN_FREE</option>
																<option value="KETIAN_GLOBAL">KETIAN_GLOBAL</option>
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
		
		
				<!-- Input Modal -->
				<div class="modal fade" id="myModalContract" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelContract">订单添加</h4>
							</div>
							<div class="modal-body">
							
																																
														
				
								
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
											
											
											
											
											
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right" style="font-weight: bold">
														<font color="red">* </font> 
															订单起始日期:
														</label>
													</section>
													<section class="col col-4">
														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="s_searchStartTime1" placeholder="点击选择订单起始日期" id="s_searchStartTime1" readonly="readOnly">
															<span></span>
														</label>
													</section>										
													
												</div>		
												
												
										<div class="row">
													<section class="col col-2">
														<label class="label text-right" style="font-weight: bold">
														<font color="red">* </font>  
															付款方式:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_payInterval" name="s_payInterval" class="form-control">														
																<option value="MONTHLY">月付</option>
																<option value="QUARTERLY">季付</option>
																<option value="HALF_YEARLY">半年付</option>
																<option value="YEARLY">年付</option>
																<option value="ONE_TIME">一次性付</option>
																
															</select>
															
														</label>
													</section>
													<section class="col col-2">
														<label class="label text-right" style="font-weight: bold">
															业务机会编号:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class="icon-prepend fa fa-tag"></i>
															<input type="text" name="s_bizChance" id="s_bizChance" placeholder="输入业务机会编号">
														</label>
													</section>

												</div>	
												
												
															
																
											</fieldset>
											<fieldset class="producttypedetail">

												<div class="row" style="margin-bottom: 10px">
													<section class="col col-2">
														<label class="label text-right" style="font-weight: bold">
															选择产品:
														</label>
													</section>
													<section class="col col-3">
														<label style="margin:6px 0 2px 0;color:#555">类型:</label>
														<select class="form-control" id="product_type">
															<option value="ALL" selected="selected">所有</option>
															<option value="WEBEX_BY_HOSTS">按用户数计费</option>
															<option value="WEBEX_BY_PORTS">按并发数计费</option>
															<option value="WEBEX_PSTN">PSTN计费</option>
															<option value="WEBEX_AUDIO_PACKAGE">语音包</option>
															<option value="WEBEX_STORAGE">存储</option>
															<option value="MISC">杂项</option>
															<option value="WEBEX_BY_AH">按激活用户数计费</option>
															<option value="WEBEX_CMR">Webex CMR包月</option>
															<option value="CC">天客云</option>
														</select>
													</section>
													<section class="col col-4">
														<label style="margin:6px 0 2px 0;color:#555">产品:</label>
														<div id="s_searchProduct" style="width: 100%">
														</div>
													</section>
												</div>
												<div class="row">
													<section class="col col-2">
														<label class="label text-right" style="font-weight: bold">
															历史选择产品:
														</label>
													</section>
													<section class="col col-10" id="history-product-list" style="margin: 4px 0 1px 0">

													</section>
												</div>

												<div class="row">
													<section class="col col-2">
														<label class="label text-right" style="font-weight: bold">
															<font color="red">* </font>
															已选产品:
														</label>
													</section>
													<section class="col col-6" id="selected-product-list">
													</section>
												</div>

												<!--
												<section class="col col-4">
														<label class="label text-middle">
															<font color="#ff4500">按用户数计费 </font>
															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">

															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">
														<font color="red">&nbsp; </font>  
															
														</label>
												</section>
										
												<div id="hostssection"></div>	
											</fieldset >
											<fieldset class="producttypedetail">
												<section class="col col-4">
														<label class="label text-middle">
															<font color="#ff4500">按并发数计费 </font>
															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">

															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">
														<font color="red">&nbsp; </font>  
															
														</label>
												</section>
												<div id="portssection"></div>	
											</fieldset>
											<fieldset class="producttypedetail">
												<section class="col col-4">
														<label class="label text-middle">
															<font color="#ff4500">PSTN计费 </font>
															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">

															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">
														<font color="red">&nbsp; </font>  
															
														</label>
												</section>
												<div id="pstnsection"></div>	
											</fieldset>
											<fieldset class="producttypedetail">
												<section class="col col-4">
														<label class="label text-middle">
															<font color="#ff4500">语音包</font>
															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">

															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">
														<font color="red">&nbsp; </font>  
															
														</label>
												</section>
												<div id="audiosection"></div>	
											</fieldset>
											<fieldset class="producttypedetail">
											<section class="col col-4 ">
														<label class="label text-middle">
															<font color="#ff4500">存储</font>
															
														</label>
												</section>
												<section class="col col-4 ">
														<label class="label text-middle">

															
														</label>
												</section>
												<section class="col col-4 ">
														<label class="label text-middle">
														<font color="red">&nbsp; </font>  
															
														</label>
												</section>
												<div id="storagesection"></div>	
											</fieldset>
											<fieldset class="producttypedetail">
											<section class="col col-4">
														<label class="label text-middle">
															<font color="#ff4500">杂项</font>
															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">

															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">
														<font color="red">&nbsp; </font>  
															
														</label>
												</section>
												<div id="miscsection"></div>	
											</fieldset>
											
											<fieldset class="producttypedetail">
											<section class="col col-4">
														<label class="label text-middle">
															<font color="#ff4500">按激活用户数计费</font>
															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">

															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">
														<font color="red">&nbsp; </font>  
															
														</label>
												</section>
												<div id="ahsection"></div>	
											</fieldset>
											
											
											<fieldset class="producttypedetail">
											<section class="col col-4">
														<label class="label text-middle">
															<font color="#ff4500">Webex CMR包月</font>
															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">

															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">
														<font color="red">&nbsp; </font>  
															
														</label>
												</section>
												<div id="cmrsection"></div>	
											</fieldset>
											
											<fieldset class="producttypedetail">
											<section class="col col-4">
														<label class="label text-middle">
															<font color="#ff4500">天客云</font>
															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">

															
														</label>
												</section>
												<section class="col col-4">
														<label class="label text-middle">
														<font color="red">&nbsp; </font>  
															
														</label>
												</section>
												<div id="ccsection"></div>
												-->
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
								<button type="button" class="btn btn-primary btn-save-order">	保存 </button>
								<input type="hidden" id="edit_userid_order" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->

<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_HOSTS" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_HOSTS">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_HOSTS" name="s_siteId_MONTHLY_PAY_BY_HOSTS" class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买账户数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_HOSTS" placeholder="请输入客户端！" id="shosts_MONTHLY_PAY_BY_HOSTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>购买账户数长度不大于100! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买月数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="smonths_MONTHLY_PAY_BY_HOSTS" placeholder="请输入月数！" id="smonths_MONTHLY_PAY_BY_HOSTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>购买月数长度不大于100! </b>
															<span></span>
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_HOSTS" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_HOSTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_HOSTS">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_HOSTS" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
<!-- Input Modal -->
				<div class="modal fade" id="myModal_CMR_MONTHLY_PAY" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCMR_MONTHLY_PAY">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_CMR_MONTHLY_PAY" name="s_siteId_CMR_MONTHLY_PAY" class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											    <div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															并发方数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_CMR_MONTHLY_PAY" placeholder="请输入并发数！" id="shosts_CMR_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>并发数长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
											
													
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买月数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="smonths_CMR_MONTHLY_PAY" placeholder="请输入月数！" id="smonths_CMR_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>购买月数长度不大于100! </b>
															<span></span>
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CMR_MONTHLY_PAY" placeholder="请输入单价！" id="sprices_CMR_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
														</label>
													</section>											
												</div>	
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															溢出单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="soverprices_CMR_MONTHLY_PAY" placeholder="请输入单价！" id="soverprices_CMR_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>溢出单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">
															元 / 方 / 月
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
								<button type="button" class="btn btn-primary btn-save-order-CMR_MONTHLY_PAY">	保存 </button>
								<input type="hidden" id="edit_userid_order_CMR_MONTHLY_PAY" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->		
		
		
<!-- Input Modal -->
			<%--并发方数包月购买--%>
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_PORTS" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_PORTS">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_PORTS" name=MONTHLY_PAY_BY_PORTS class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															并发方数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_PORTS" placeholder="请输入并发数！" id="shosts_MONTHLY_PAY_BY_PORTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>并发数长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															月数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="smonths_MONTHLY_PAY_BY_PORTS" placeholder="请输入月数！" id="smonths_MONTHLY_PAY_BY_PORTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月数长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															月
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_PORTS" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_PORTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">
															元 / 方 / 月
														</label>
													</section>												
												</div>
												
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															溢出单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="soverprices_MONTHLY_PAY_BY_PORTS" placeholder="请输入单价！" id="soverprices_MONTHLY_PAY_BY_PORTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>溢出单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 方 / 月
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_PORTS">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_PORTS" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
<!-- Input Modal -->
			<%--并发方数包月购买--%>
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_TOTAL_ATTENDEES" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_TOTAL_ATTENDEES">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_TOTAL_ATTENDEES" name=MONTHLY_PAY_BY_TOTAL_ATTENDEES class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															预付人次:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_TOTAL_ATTENDEES" placeholder="请输入并发数！" id="shosts_MONTHLY_PAY_BY_TOTAL_ATTENDEES" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>并发数长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															月数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="smonths_MONTHLY_PAY_BY_TOTAL_ATTENDEES" placeholder="请输入月数！" id="smonths_MONTHLY_PAY_BY_TOTAL_ATTENDEES" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月数长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															月
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">
															元 / 人次 / 月
														</label>
													</section>												
												</div>
												
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															溢出单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="soverprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES" placeholder="请输入单价！" id="soverprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>溢出单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 人次 / 月
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_TOTAL_ATTENDEES">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_TOTAL_ATTENDEES" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->			
<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_STO" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_STO">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_STO" name=MONTHLY_PAY_BY_PORTS class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															存贮大小:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_STO" placeholder="请输入存贮大小！" id="shosts_MONTHLY_PAY_BY_STO" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>存贮大小长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															月数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="smonths_MONTHLY_PAY_BY_STO" placeholder="请输入月数！" id="smonths_MONTHLY_PAY_BY_STO" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月数长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															月
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_STO" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_STO" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_STO">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_STO" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->		
	<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_STO_O" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_STO_O">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_STO_O" name=MONTHLY_PAY_BY_PORTS class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															存贮大小:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_STO_O" placeholder="请输入存贮大小！" id="shosts_MONTHLY_PAY_BY_STO_O" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>存贮大小长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															月数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="smonths_MONTHLY_PAY_BY_STO_O" placeholder="请输入月数！" id="smonths_MONTHLY_PAY_BY_STO_O" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月数长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															月
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_STO_O" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_STO_O" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 1G
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															溢出单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_STO_O_OVERPRICE" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_STO_O_OVERPRICE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 5G
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_STO_O">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_STO_O" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->				
	
	<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_ECPP" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_ECPP">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_ECPP" name=MONTHLY_PAY_BY_PORTS class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_ECPP" placeholder="请输入总价大小！" id="shosts_MONTHLY_PAY_BY_ECPP" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>								
													
												</div>	
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买有效期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class=""></i>
															<input type="text" name="s_searchStartTime_ECPP" placeholder="点击输入购买有效期" id="s_searchStartTime_ECPP" >
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															月
														</label>
													</section>											
												</div>	
													
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															价格表:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_ECPP" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_ECPP" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_ECPP">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_ECPP" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
		
	<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_ECPU" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_ECPU">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_ECPU" name=MONTHLY_PAY_BY_PORTS class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															次数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_ECPU" placeholder="请输入次数大小！" id="shosts_MONTHLY_PAY_BY_ECPU" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>次数长度不大于100个! </b>
															<span></span>
														</label>
													</section>
																				
													
												</div>	
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买有效期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class=""></i>
															<input type="text" name="s_searchStartTime_ECPU" placeholder="点击输入购买有效期" id="s_searchStartTime_ECPU" >
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															月
														</label>
													</section>											
												</div>	
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_ECPU" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_ECPU" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_ECPU">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_ECPU" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->		
		
	<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_PSTNALLSTIE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_PSTNALLSTIE">资费修改</h4>
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
															总分钟:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_PSTNALLSTIE" placeholder="请输入总分钟大小！" id="shosts_MONTHLY_PAY_BY_PSTNALLSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总分钟长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买有效期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class=""></i>
															<input type="text" name="s_searchStartTime_PSTNALLSTIE" placeholder="点击输入购买有效期" id="s_searchStartTime_PSTNALLSTIE" >
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															月
														</label>
													</section>											
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_PSTNALLSTIE" placeholder="请输入总价！" id="sprices_MONTHLY_PAY_BY_PSTNALLSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-left">														
															元
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_PSTNALLSTIE">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_PSTNALLSTIE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
		
		
	<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_PSTNMUlSTIE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_PSTNMUlSTIE">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_PSTNMUlSTIE" name="s_siteId_MONTHLY_PAY_BY_PSTNMUlSTIE" class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总分钟:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_PSTNMUlSTIE" placeholder="请输入总分钟大小！" id="shosts_MONTHLY_PAY_BY_PSTNMUlSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总分钟长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买有效期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class=""></i>
															<input type="text" name="s_searchStartTime_PSTNMUlSTIE" placeholder="点击输入购买有效期" id="s_searchStartTime_PSTNMUlSTIE" >
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															月
														</label>
													</section>											
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_PSTNMUlSTIE" placeholder="请输入总价！" id="sprices_MONTHLY_PAY_BY_PSTNMUlSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-left">														
															元
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_PSTNMUlSTIE">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_PSTNMUlSTIE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->		
	<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_STANDSITE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_STANDSITE">资费修改</h4>
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
														<font color="red">* </font>  
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_STANDSITE" name=MONTHLY_PAY_BY_PORTS class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red">* </font> 
															截至时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="s_searchStartTime_STANDSITE" placeholder="点击选择截至时期" id="s_searchStartTime_STANDSITE" readonly="readOnly">
															<span></span>
														</label>
													</section>
													<section class="col col-4">														
														<label class=""> 
															<input type="checkbox" class="SUPPORT_BILL_SPLIT">支持分账</label>
															<span></span>
														</label>
													</section>														
												</div>
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															国家地区
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															通信服务费
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															语音服务费
														</label>
													</section>
																						
												</div>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														
															折扣:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="comdiscount_MONTHLY_PAY_BY_PSTNALLSTIE" placeholder="请输入通信服务费折扣！" id="comdiscount_MONTHLY_PAY_BY_PSTNALLSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="pstndiscount_MONTHLY_PAY_BY_PSTNALLSTIE" placeholder="请输入语音服务费折扣！" id="pstndiscount_MONTHLY_PAY_BY_PSTNALLSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
																									
												</div>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary comdiscount_button" >
															使用通信服务费折扣
														</button>
													</section>
													<section class="col col-4">
													
														<button type="button" class="btn btn-primary pstndiscount_button" >
															使用语音服务费折扣
														</button>
													</section>
																						
												</div>		
												
												
												<div class="ratesection ratesection_PSTN_STANDARD_CHARGE"></div>	
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary showallornot" >
															显示所有国家和地区
														</button>
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_STANDSITE">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_STANDSITE" value=""/>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_STANDSITE_RATES" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
	<!-- Input Modal -->
				<div class="modal fade" id="myModal_CC_CALLCENTER_PSTN" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_PSTN">资费修改</h4>
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
														<font color="red">* </font> 
															截至时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="s_searchStartTime_STANDSITE_CC_CALLCENTER_PSTN" placeholder="点击选择截至时期" id="s_searchStartTime_STANDSITE_CC_CALLCENTER_PSTN" readonly="readOnly">
															<span></span>
														</label>
													</section>
																									
												</div>
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															国家地区
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															通信服务费
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															语音服务费
														</label>
													</section>
																						
												</div>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														
															折扣:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="comdiscount_CC_CALLCENTER_PSTN" placeholder="请输入通信服务费折扣！" id="comdiscount_CC_CALLCENTER_PSTN" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="pstndiscount_CC_CALLCENTER_PSTN" placeholder="请输入语音服务费折扣！" id="pstndiscount_CC_CALLCENTER_PSTN" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
																									
												</div>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary comdiscount_button_CC_CALLCENTER_PSTN" >
															使用通信服务费折扣
														</button>
													</section>
													<section class="col col-4">
													
														<button type="button" class="btn btn-primary pstndiscount_button_CC_CALLCENTER_PSTN" >
															使用语音服务费折扣
														</button>
													</section>
																						
												</div>		
												
												
												<div class="ratesection ratesection_CC_CALLCENTER_PSTN"></div>	
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary showallornot" >
															显示所有国家和地区
														</button>
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
								<button type="button" class="btn btn-primary btn-save-order-CC_CALLCENTER_PSTN">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_PSTN" value=""/>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_PSTN_RATES" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->		
	
	
		
<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_PSTNMONTH" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_PSTNMONTH">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_PSTNMONTH" name=MONTHLY_PAY_BY_PORTS class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															每月分钟数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_PSTNMONTH" placeholder="请输入每月分钟数！" id="shosts_MONTHLY_PAY_BY_PSTNMONTH" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>每月分钟数长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															月数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="smonths_MONTHLY_PAY_BY_PSTNMONTH" placeholder="请输入月数！" id="smonths_MONTHLY_PAY_BY_PSTNMONTH" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月数长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															月
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															月费用:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_PSTNMONTH" placeholder="请输入月费用！" id="sprices_MONTHLY_PAY_BY_PSTNMONTH" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月费用长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_PSTNMONTH">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_PSTNMONTH" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
		
		<!-- Input Modal -->
				<div class="modal fade" id="myModal_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_PSTN_MONTHLY_PACKAGE">资费修改</h4>
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
														<font color="red"> </font> 
															每月分钟数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" placeholder="请输入每月分钟数！" id="shosts_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" maxlength="100" disabled="true">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>每月分钟数长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															月费用:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" placeholder="请输入月费用！" id="sprices_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" maxlength="100" >
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月费用长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															截至时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="smonths_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" placeholder="点击选择截至时期" id="smonths_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" readonly="readOnly">
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															
														</label>
													</section>												
												</div>
													<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买数量:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shostsamount_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" placeholder="请输入购买数量！" id="shostsamount_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>购买数量长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
														
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
								<button type="button" class="btn btn-primary btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_PACKAGE">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
		
		
		
		<!-- Input Modal -->
				<div class="modal fade" id="myModal_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE">资费修改</h4>
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
															每月分钟数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" placeholder="请输入每月分钟数！" id="shosts_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>每月分钟数长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" placeholder="请输入月费用！" id="sprices_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月费用长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 分钟
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															截至时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="smonths_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" placeholder="点击选择截至时期" id="smonths_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" readonly="readOnly">
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															
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
								<button type="button" class="btn btn-primary btn-save-order-CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
		
		<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_TERMINATE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_TERMINATE">提前中止</h4>
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
															中止类型:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_TERMINATE" name=s_siteId_MONTHLY_PAY_BY_TERMINATE class="form-control ">
																<option value="bymonth">按计费周期截止</option>
																<option value="byday">按所选日期即日中止</option>

															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															中止时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="s_searchStartTime_TERMINATE" placeholder="点击选择截至时期" id="s_searchStartTime_TERMINATE" readonly="readOnly">
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_TERMINATE">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_TERMINATE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
		
			
<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_TELECOM" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_TELECOM">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_TELECOM" name=MONTHLY_PAY_BY_PORTS class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															Webex 账号:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_TELECOM" placeholder="请输入Webex账号！" id="shosts_MONTHLY_PAY_BY_TELECOM" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>Webex账号长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>
												
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															用户姓名:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="susername_MONTHLY_PAY_BY_TELECOM" placeholder="请输入用户姓名！" id="susername_MONTHLY_PAY_BY_TELECOM" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>用户姓名长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>
												
												
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															初始密码:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="spass_MONTHLY_PAY_BY_TELECOM" placeholder="请输入初始密码！" id="spass_MONTHLY_PAY_BY_TELECOM" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>初始密码长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>
												
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															企业名称:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="senterprisename_MONTHLY_PAY_BY_TELECOM" placeholder="请输入企业名称！" id="senterprisename_MONTHLY_PAY_BY_TELECOM" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>企业名称长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												
												
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															企业编号:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="senterprisecode_MONTHLY_PAY_BY_TELECOM" placeholder="请输入企业编号！" id="senterprisecode_MONTHLY_PAY_BY_TELECOM" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>企业编号长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>								
											
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_TELECOM" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_TELECOM" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买有效期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class=""></i>
															<input type="text" name="smonths_MONTHLY_PAY_BY_TELECOM" placeholder="点击输入购买有效期" id="smonths_MONTHLY_PAY_BY_TELECOM" >
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															月
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_TELECOM">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_TELECOM" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->			
<!-- Input Modal -->
				<div class="modal fade" id="myModal_MISC_CHARGE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMISC_CHARGE">资费修改</h4>
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
														
															总价:
														</label>
													</section>
													
													
													<section class="col col-4">														
														<label class="input"> <i class=""></i>
															<input type="text" name="sprices_PAY_BY_MISC" placeholder="点击输入总价" id="sprices_PAY_BY_MISC" >
															<span></span>
														</label>
													</section>	
													
													<section class="col col-1">
														<label class="label text-left">														
															元
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
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MISC_CHARGE">	保存 </button>
								<input type="hidden" id="edit_userid_order_MISC_CHARGE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
	<div class="modal fade" id="myModalSelectFile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel1">上传合同</h4>
							</div>
							<div class="modal-body">
				
								
								<div class="row fileSelect">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row" id="schoolRow">
													<section class="col col-3">
														<label class="label text-right"> 
															选择导入的pdf文件:
														</label>
													</section>
													<section class="col col-8">
														
														<label class="input input-file state-success" >
															<div class="button">
																  <input type="file" id="file" name="file"/>选择
															</div>
															<input type="text" id="filetxt" readonly=""/>
															<span></span>
														</label>
												</div>		
												<div class="row" id="schoolRow">		
													</section>
													<section class="col col-3">
														<label class="label text-right">合同编号:</label>
													</section>
													<section class="col col-8">
														<label class="select">
															<input type="text" name="contractNumber" placeholder="请输入合同编号！" id="contractNumber" maxlength="256">
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
								<button type="button" class="btn btn-primary btn-upload-pdf">
									上传
								</button>
								<input type="hidden" id="edit_userid" value=""/>
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_ACTIVEHOSTS" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_ACTIVEHOSTS">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> 
														
														
															<select id="s_siteId_MONTHLY_PAY_BY_ACTIVEHOSTS" name="s_siteId_MONTHLY_PAY_BY_ACTIVEHOSTS" class="form-control s_siteId_MONTHLY_PAY_BY_HOSTS">														
																
																
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>			
													
												</div>		
											
											
											
														
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买月数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="smonths_MONTHLY_PAY_BY_ACTIVEHOSTS" placeholder="请输入月数！" id="smonths_MONTHLY_PAY_BY_ACTIVEHOSTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>购买月数长度不大于100! </b>
															<span></span>
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_ACTIVEHOSTS" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_ACTIVEHOSTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
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
								<button type="button" class="btn btn-primary btn-save-order-MONTHLY_PAY_BY_ACTIVEHOSTS">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_ACTIVEHOSTS" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
		<!-- Input Modal -->
				<div class="modal fade" id="myModal_CC_CALLCENTER_MONTHLY_PAY" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_MONTHLY_PAY">资费修改</h4>
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
															座席数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_CC_CALLCENTER_MONTHLY_PAY" placeholder="请输入月数！" id="shosts_CC_CALLCENTER_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>购买月数长度不大于100! </b>
															<span></span>
														</label>
													</section>												
												</div>
											
											
											
														
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															截至时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="smonths_CC_CALLCENTER_MONTHLY_PAY" placeholder="点击选择截至时期" id="smonths_CC_CALLCENTER_MONTHLY_PAY" readonly="readOnly">
															<span></span>
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_CALLCENTER_MONTHLY_PAY" placeholder="请输入单价！" id="sprices_CC_CALLCENTER_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
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
								<button type="button" class="btn btn-primary btn-save-order-CC_CALLCENTER_MONTHLY_PAY">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_MONTHLY_PAY" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
		
				<!-- Input Modal -->
				<div class="modal fade" id="myModal_CC_CALLCENTER_OLS_MONTHLY_PAY" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_OLS_MONTHLY_PAY">资费修改</h4>
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
															座席数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_CC_CALLCENTER_OLS_MONTHLY_PAY" placeholder="请输入月数！" id="shosts_CC_CALLCENTER_OLS_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>购买月数长度不大于100! </b>
															<span></span>
														</label>
													</section>												
												</div>
											
											
											
														
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															截至时期:
														</label>
													</section>
													<section class="col col-4">													
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="smonths_CC_CALLCENTER_OLS_MONTHLY_PAY" placeholder="点击选择截至时期" id="smonths_CC_CALLCENTER_OLS_MONTHLY_PAY" readonly="readOnly">
															<span></span>
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_CALLCENTER_OLS_MONTHLY_PAY" placeholder="请输入单价！" id="sprices_CC_CALLCENTER_OLS_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
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
								<button type="button" class="btn btn-primary btn-save-order-CC_CALLCENTER_OLS_MONTHLY_PAY">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_OLS_MONTHLY_PAY" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		<!-- Input Modal -->
				<div class="modal fade" id="myModal_CC_CALLCENTER_NUMBER_MONTHLY_PAY" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabeCC_CALLCENTER_NUMBER_MONTHLY_PAYS">资费修改</h4>
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
															截至时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="smonths_CC_CALLCENTER_NUMBER_MONTHLY_PAY" placeholder="点击选择截至时期" id="smonths_CC_CALLCENTER_NUMBER_MONTHLY_PAY" readonly="readOnly">
															<span></span>
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_CALLCENTER_NUMBER_MONTHLY_PAY" placeholder="请输入单价！" id="sprices_CC_CALLCENTER_NUMBER_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
														</label>
													</section>											
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买数量:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_CC_CALLCENTER_NUMBER_MONTHLY_PAY" placeholder="请输入购买数量！" id="shosts_CC_CALLCENTER_NUMBER_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>购买数量长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
														
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
								<button type="button" class="btn btn-primary btn-save-order-CC_CALLCENTER_NUMBER_MONTHLY_PAY">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_NUMBER_MONTHLY_PAY" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
						
			<!-- Input Modal -->
			<%--EC按次购买--%>
				<div class="modal fade" id="myModal_MONTHLY_PAY_PERSONAL_WEBEX" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_PERSONAL_WEBEX">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="ssite_MONTHLY_PAY_PERSONAL_WEBEX" placeholder="" id="ssite_MONTHLY_PAY_PERSONAL_WEBEX" maxlength="100" disabled="true">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>站点长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															
														</label>
													</section>													
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买月数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="smonths_MONTHLY_PAY_PERSONAL_WEBEX" placeholder="请输入月数！" id="smonths_MONTHLY_PAY_PERSONAL_WEBEX" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>购买月数长度不大于100! </b>
															<span></span>
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_PERSONAL_WEBEX" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_PERSONAL_WEBEX" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>													
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															账号:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="susername_MONTHLY_PAY_PERSONAL_WEBEX" placeholder="请输入账号！" id="susername_MONTHLY_PAY_PERSONAL_WEBEX" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>账号长度不大于100! </b>
															<span></span>
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red"></font> 
															姓名:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sfullname_MONTHLY_PAY_PERSONAL_WEBEX" placeholder="请输入姓名！" id="sfullname_MONTHLY_PAY_PERSONAL_WEBEX" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>姓名长度不大于100! </b>
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
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_PERSONAL_WEBEX">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_PERSONAL_WEBEX" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
		<!-- Input Modal -->
				<div class="modal fade" id="myModal_PSTN_PERSONAL_CHARGE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelPSTN_PERSONAL_CHARGE">资费修改</h4>
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
														<font color="red">* </font> 
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="ssite_PSTN_PERSONAL_CHARGE" placeholder="请输入站点！" id="ssite_PSTN_PERSONAL_CHARGE" maxlength="100" disabled="true">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>站点长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															
														</label>
													</section>													
												</div>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red">* </font> 
															截至时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="s_searchStartTime_PSTN_PERSONAL_CHARGE" placeholder="点击选择截至时期" id="s_searchStartTime_PSTN_PERSONAL_CHARGE" readonly="readOnly">
															<span></span>
														</label>
													</section>
													<section class="col col-1">														
														
													</section>														
												</div>
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red">* </font> 
															账号:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="susername_PSTN_PERSONAL_CHARGE" placeholder="请输入账号！" id="susername_PSTN_PERSONAL_CHARGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>账号长度不大于100! </b>
															<span></span>
														</label>
													</section>												
												</div>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															国家地区
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															通信服务费
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															语音服务费
														</label>
													</section>
																						
												</div>
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														
															折扣:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="comdiscount_PSTN_PERSONAL_CHARGE" placeholder="请输入通信服务费折扣！" id="comdiscount_PSTN_PERSONAL_CHARGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="pstndiscount_PSTN_PERSONAL_CHARGE" placeholder="请输入语音服务费折扣！" id="pstndiscount_PSTN_PERSONAL_CHARGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
																									
												</div>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary" id="comdiscount_PSTN_PERSONAL_CHARGE_Button">
															使用通信服务费折扣
														</button>
													</section>
													<section class="col col-4">
													
														<button type="button" class="btn btn-primary" id="pstndiscount_PSTN_PERSONAL_CHARGE_Button" >
															使用语音服务费折扣
														</button>
													</section>
																						
												</div>	
												
																						
												
												<div class="ratesection ratesection_PSTN_PERSONAL_CHARGE" ></div>	
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary showallornot" >
															显示所有国家和地区
														</button>
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
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-PSTN_PERSONAL_CHARGE">	保存 </button>
								<input type="hidden" id="edit_userid_order_PSTN_PERSONAL_CHARGE" value=""/>
								<input type="hidden" id="edit_userid_order_PSTN_PERSONAL_CHARGE_chargeId" value=""/>								
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->			
		<!-- Input Modal -->
				<div class="modal fade" id="myModal_PSTN_PERSONAL_PACKET" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelPSTN_PERSONAL_PACKET">资费修改</h4>
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
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="ssite_PSTN_PERSONAL_PACKET" placeholder="请输入站点！" id="ssite_PSTN_PERSONAL_PACKET" maxlength="100" disabled="true">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>站点长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															
														</label>
													</section>													
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															账号:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="susername_PSTN_PERSONAL_PACKET" placeholder="请输入账号！" id="susername_PSTN_PERSONAL_PACKET" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>账号长度不大于100! </b>
															<span></span>
														</label>
													</section>												
												</div>
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总分钟:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_PSTN_PERSONAL_PACKET" placeholder="请输入总分钟大小！" id="shosts_PSTN_PERSONAL_PACKET" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总分钟长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															截至时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="s_searchStartTime_STANDSITE_PSTN_PERSONAL_PACKET" placeholder="点击选择截至时期" id="s_searchStartTime_STANDSITE_PSTN_PERSONAL_PACKET" readonly="readOnly">
															<span></span>
														</label>
													</section>
													<section class="col col-1">														
														
													</section>														
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_PSTN_PERSONAL_PACKET" placeholder="请输入总价！" id="sprices_PSTN_PERSONAL_PACKET" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-left">														
															元
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
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-PSTN_PERSONAL_PACKET">	保存 </button>
								<input type="hidden" id="edit_userid_order_PSTN_PERSONAL_PACKET" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->				
		
			<!-- Input Modal -->
				<div class="modal fade" id="myModal_CC_CALLCENTER_PSTN_PACKAGE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_PSTN_PACKAGE">资费修改</h4>
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
															总分钟:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_CC_CALLCENTER_PSTN_PACKAGE" placeholder="请输入总分钟大小！" id="shosts_CC_CALLCENTER_PSTN_PACKAGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总分钟长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															截至时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="s_searchStartTime_STANDSITE_CC_CALLCENTER_PSTN_PACKAGE" placeholder="点击选择截至时期" id="s_searchStartTime_STANDSITE_CC_CALLCENTER_PSTN_PACKAGE" readonly="readOnly">
															<span></span>
														</label>
													</section>
													<section class="col col-1">														
														
													</section>														
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_CALLCENTER_PSTN_PACKAGE" placeholder="请输入总价！" id="sprices_CC_CALLCENTER_PSTN_PACKAGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-left">														
															元
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
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-CC_CALLCENTER_PSTN_PACKAGE">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_PSTN_PACKAGE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
					
		<!-- End Input Modal -->
			<div class="modal fade" id="myModalErrorMessage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true" style="display:none">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel1">上传合同出错</h4>
							</div>
							<div class="modal-body">
								<div class="row errorMessage">
									<font color=red></font>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-primary btn-reupload">
									重新上传
								</button>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>	
<!-- Input Modal -->
				<div class="modal fade" id="myModaladjustamount" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel1">调整金额</h4>
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
															调整前金额:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" name="oldvalue" placeholder="" id="oldvalue" maxlength="100" disabled >
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>缴费金额长度不大于100个! </b>
															<span></span>
														</label>
													</section>
												</div>
												
												<div class="row">

													<section class="col col-2">
														<label class="label text-right">
														<font color="red">* </font>
															调整后金额:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class="icon-prepend fa fa-user"></i>
															<input type="text" name="newvalue" placeholder="请输入调整后金额" id="newvalue" maxlength="100"  >
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>调整后金额长度不大于100个! </b>
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
								<button type="button" class="btn btn-primary btn-save-adjust">	保存 </button>
								<input type="hidden" id="edit_userid1" value=""/>
								<input type="hidden" id="amountType" value=""/>

							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->				
				
					
			<!-- Input Modal -->
				<div class="modal fade" id="myPdfListModal" tabindex="-1" role="dialog" aria-labelledby="myPdfModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myPdfModalLabel">公司员工添加</h4>
							</div>
							<div class="modal-body">
				
								
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
											
											
											
									<form id="searchForm1" method="POST" action="#" class="smart-form" novalidate="novalidate" onsubmit="return search()">
											<div class="row">
										
										
																					
										    
										
										
											<section class="col col-3 form-group">
												 <span  class="errormsg">  </span>
											</section>
											
										
									
											
											
										</div>
										
										
										
										<div class="row">												
										    
										
										 
											<section class="col col-3 form-group">
												
											
													<input type="hidden" name="s_sserviceid2" placeholder="请输入用户名" id="s_sserviceid2" data-pure-clear-button>
												
											</section>
											
										    
									
											
											<section class="col col-3">
												<label class="label text-left">&nbsp;</label>
											
												
										
											</section>
										</div>
									</form>		
											
											
												<div class="row">
											
													
													
													<table id="datatable_tabletools_users" class="table table-bordered table-hover">
														<thead>
															
														</thead>
														<tbody>
															
														</tbody>
														<tfoot>
			
														</tfoot>
													</table>
				
												</div>					
												
												
										     
											
											</fieldset>
											
											
					
											
	
										</form>
										
										
										</div>
										
										
									</div>
								</div>
				
							</div>
							<%--<div class="modal-footer">--%>
								<%--<button type="button" class="btn btn-default" data-dismiss="modal">--%>
									<%--关闭--%>
								<%--</button>--%>
								<%----%>
								<%----%>
								<%----%>
							<%--</div>--%>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
						
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
		 <script src="<c:url value="/static/js/common/ajaxfileupload.js" />${randomstr} " type="text/javascript"></script>		
		 <script src="<c:url value="/static/js/page/order/order.js" />${randomstr}" type="text/javascript"></script>
		 <script src="<c:url value="/static/js/plugin/my97datePicker/WdatePicker.js" />${randomstr}" type="text/javascript"></script>
		<!-- Search Ajax Example End -->
	</body>
</html>