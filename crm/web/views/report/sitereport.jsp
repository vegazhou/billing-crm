<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title> 站点管理 </title>
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
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 站点管理 <span>> 站点列表</span></h1>
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
										<h2>站点列表</h2>
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
												<label class="label text-left">站点名称:</label>
												<label class="input"> <i class="icon-prepend fa fa-user"></i>
													<input type="text" id="s_sserviceid1" name="s_sserviceid1" placeholder="请输入站点名称" data-pure-clear-button>
												</label>
											</section>







											<section class="col col-3">
												<label class="label text-left">&nbsp;</label>

												<span class="btn btn-primary btn-sm btn-search"><i class="fa fa-search"></i> 查询 </span>

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
													<section class="col col-2 mc5sitesetion">
														<label class="label text-right"> 
														<font color="red">* </font> 
															M5 Migrate Site
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
																<option value="TSP_BIZ2101_RP">TSP_BIZ2101_RP</option>
																<option value="TSP_BIZ2101_BA">TSP_BIZ2101_BA</option>
																<option value="TSP_BIZ2101_ACER">TSP_BIZ2101_ACER</option>
																<option value="TSP_BIZ2101_JNJCHINA">TSP_BIZ2101_JNJCHINA</option>
																<option value="TSP_BIZ2101_HISUNPFIZER">TSP_BIZ2101_HISUNPFIZER</option>
																<option value="TSP_BIZ1001_RP">TSP_BIZ1001_RP</option>
																<option value="TSP_BIZ1001_BA">TSP_BIZ1001_BA</option>
																<option value="TSP_ARK_1">TSP_ARK_1</option>
																<option value="TSP_PGI_APAC_GM3">TSP_PGI_APAC_GM3</option>
																<option value="TSP_INTERCALL">TSP_INTERCALL</option>
															</select>
															
														</label>
													</section>	
													
														<section class="col col-1">
														<label class="label text-right"> 
															
														</label>
												</section>
												
																		
													
												</div>
										        
										 
											<div class="row basicconfig">
													<section class="col col-2">
														<label class="label text-right">
														
															基本配置:
														</label>
													</section>
													<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="audioBroadCast">AudioBroadCast</label></section>
												
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="callBack">CallBack</label></section>
												
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="internationalCallBack">InternationalCallBack</label></section>
												
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="tollCallIn">TollCallIn</label></section>	
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="tollFreeCallIn">TollFreeCallIn</label></section>		
												
													
												</div>												
												<div class="row basicconfig" >
													<section class="col col-2">
														<label class="label text-right">
														
														
														</label>
													</section>
													<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="globalCallIn">GlobalCallIn</label></section>
												
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="cloudConnectedAudio">CloudConnectedAudio</label></section>
												
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="voip">Voip</label></section>
												
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="sipInOut">SipInOut</label></section>	
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="storageOverage">StorageOverage</label></section>		
												
													
												</div>	
												
												<div class="row basicconfig">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															存储空间:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="storageCapacity" placeholder="" id="storageCapacity" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i> </b>
															<span></span>
														</label>
													</section>
													
												</div>	
												
												
												
												<div class="row mcLicenseModel">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															MC:
														</label>
													</section>
																									
													<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="mcLicenseOverage">mcLicenseOverage</label></section>
												
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="mcAttendeeOverage">mcAttendeeOverage</label></section>
													
												</div>	
												
												
												<div class="row mcLicenseModel">
											
													<section class="col col-2">
														<label class="label text-right"> 
														
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="mcLicenseVolume" placeholder="" id="mcLicenseVolume" maxlength="100">mcLicenseVolume
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i> </b>
															<span></span>
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="mcAttendeeCapacity" placeholder="" id="mcAttendeeCapacity" maxlength="100">mcAttendeeCapacity
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i> </b>
															<span></span>
														</label>
													</section>
													
													
												</div>	
												
												
												
												<div class="row scLicenseModel">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															SC:
														</label>
													</section>
																									
													<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="scLicenseOverage">scLicenseOverage</label></section>
												
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="scAttendeeOverage">scAttendeeOverage</label></section>
													
												</div>	
												
												
												<div class="row scLicenseModel">
											
													<section class="col col-2">
														<label class="label text-right"> 
														
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="scLicenseVolume" placeholder="" id="scLicenseVolume" maxlength="100">scLicenseVolume
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i> </b>
															<span></span>
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="scAttendeeCapacity" placeholder="" id="scAttendeeCapacity" maxlength="100">scAttendeeCapacity
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i> </b>
															<span></span>
														</label>
													</section>
													
													
												</div>
												
												
												<div class="row tcLicenseModel">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															TC:
														</label>
													</section>
																									
													<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="tcLicenseOverage">tcLicenseOverage</label></section>
												
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="tcAttendeeOverage">tcAttendeeOverage</label></section>
													
												</div>	
												
												
												<div class="row tcLicenseModel">
											
													<section class="col col-2">
														<label class="label text-right"> 
														
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="tcLicenseVolume" placeholder="" id="tcLicenseVolume" maxlength="100">tcLicenseVolume
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i> </b>
															<span></span>
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="tcAttendeeCapacity" placeholder="" id="tcAttendeeCapacity" maxlength="100">tcAttendeeCapacity
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i> </b>
															<span></span>
														</label>
													</section>
													
													
												</div>	
												
												
												<div class="row ecLicenseModel">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															EC:
														</label>
													</section>
																									
													<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="ecLicenseOverage">ecLicenseOverage</label></section>
												
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="ecAttendeeOverage">ecAttendeeOverage</label></section>
													
												</div>	
												
												
												<div class="row ecLicenseModel">
											
													<section class="col col-2">
														<label class="label text-right"> 
														
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="ecLicenseVolume" placeholder="" id="ecLicenseVolume" maxlength="100">ecLicenseVolume
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i> </b>
															<span></span>
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="ecAttendeeCapacity" placeholder="" id="ecAttendeeCapacity" maxlength="100">ecAttendeeCapacity
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i> </b>
															<span></span>
														</label>
													</section>
													
													
												</div>				
												
												
												<div class="row eeLicenseModel">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															EE:
														</label>
													</section>
																									
													<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="eeLicenseOverage">eeLicenseOverage</label></section>
												
												<section class="col col-2">
												<label class="label text-right"><input type="checkbox" class="eeAttendeeOverage">eeAttendeeOverage</label></section>
													
												</div>	
												
												
												<div class="row eeLicenseModel">
											
													<section class="col col-2">
														<label class="label text-right"> 
														
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="eeLicenseVolume" placeholder="" id="eeLicenseVolume" maxlength="100">eeLicenseVolume
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i> </b>
															<span></span>
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="eeAttendeeCapacity" placeholder="" id="eeAttendeeCapacity" maxlength="100">eeAttendeeCapacity
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i> </b>
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
		 <script src="<c:url value="/static/js/page/report/sitereport.js" />${randomstr} " type="text/javascript"></script>
		<!-- Search Ajax Example End -->
	</body>
</html>