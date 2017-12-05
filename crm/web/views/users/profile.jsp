<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

		<title> 校信后台Admin </title>
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
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 管理后台<span>> 修改用户密码</span></h1>
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
					<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
						<div class="jarviswidget" id="wid-id-c"  data-widget-colorbutton="false" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-fullscreenbutton="false"
									data-widget-deletebutton="false">
							<header>
								<span class="widget-icon"> <i class="fa fa-edit"></i> </span>
							</header>
								
							<!-- widget div-->
							<div>
								<!-- widget content -->
								<div class="widget-body">
									<form id="searchForm" method="POST" action="#" class="smart-form" novalidate="novalidate" onsubmit="return search()">
										<div class="row">
											<section class="col col-1">
												<label class="label text-right">原始密码:</label>
											</section>
											<section class="col col-3">
												<label class="input"> <i class="icon-prepend fa fa-lock"></i>
													<input type="password" name="oldPassword" placeholder="请输入原始密码" id="oldPassword">
													<b class="tooltip tooltip-top-left"> <i class="fa fa-warning txt-color-teal"></i> 请输入原始密码</b>
													<span></span>
												</label>
											</section>
										</div>
										<div class="row">
											<section class="col col-1">
												<label class="label text-right">新密码:</label>
											</section>
											<section class="col col-3">
												<label class="input"> <i class="icon-prepend fa fa-lock"></i>
													<input type="password" name="newPassword" placeholder="请输入新密码" id="newPassword" class="length-range-6-18" maxlength=18>
													<b class="tooltip tooltip-top-left"> <i class="fa fa-warning txt-color-teal"></i> 请输入新密码</b>
													<span></span>
												</label>
											</section>
										</div>
										<div class="row">
											<section class="col col-1">
												<label class="label text-right">确认密码:</label>
											</section>
											<section class="col col-3">
												<label class="input"> <i class="icon-prepend fa fa-lock"></i>
													<input type="password" name="confirmPassword" placeholder="请再次输入新密码" id="confirmPassword" class="length-range-6-18" maxlength=18>
													<b class="tooltip tooltip-top-left"> <i class="fa fa-warning txt-color-teal"></i> 请再次输入新密码</b>
													<span></span>
												</label>
											</section>
										</div>																				
									 	<div class="row">
											<section class="col col-1">
												<label class="label text-right"></label>
											</section>  
											<section class="col col-3">
											
												<span class="btn btn-primary btn-sm btn-save"><i class="fa fa-save"></i> 保存 </span>
										
											</section>
										</div>
									</form>		
								</div>
								<!-- end widget content -->	
							</div>
							<!-- end widget div -->
												
						</div>
					</article>
				<!-- widget grid -->
				</section>
					
			</div>
			
			
	<jsp:include page="../public/js.jsp" />

	<script type="text/javascript">
		// DO NOT REMOVE : GLOBAL FUNCTIONS!
		$(document).ready(function() {

			
			pageSetUp();
			
			var rule = {
				oldPassword :{
					name : "原始密码" ,
					method : {
						required : true ,
						lt		 :	18 ,
						gt		 : 0,
					},
				},
				newPassword :{
					name : "新密码" ,
					method : {
						required : true ,
						lt		 :	18 ,
						gt		 : 6,
					},
				},
				confirmPassword :{
					name : "确认密码" ,
					method : {
						required : true ,
						lt		 :	18 ,
						gt		 : 6,
					},
				},
			};

			validator.init( rule ) ;
			//validator.validate() ;
			
			$(".btn-save").click(function(){
				var valid = true;
				if(!validator.validate()){
					valid = false;
				}

				if($("#newPassword").val() != $("#confirmPassword").val()){
					$("#confirmPassword").nextAll("span").html("<font color=red>新密码和确认密码不一致。 请重设！</font>");
					valid = false;
				}
				
				if(!valid){

					return;
				}
				
				password={}
				password.oldPassword=$.trim($("#oldPassword").val());
				password.newPassword=$.trim($("#newPassword").val());
				var postData=JSON.stringify(password);
				$.ajaxInvoke({
						url 	: 	G_CTX_ROOT+ "/v1/admin/adminusers/"+userId+"/password" ,
						type    :   "put",
						success	:	function(data){
							msgBox("success", "用户密码已经修改");
							

								//dataTable.fnDraw();
								validator.clearForm();
								$("#oldPassword").val('');
								$("#newPassword").val('');
								$("#confirmPassword").val('');
								$("#oldPassword").nextAll("span").html('');
								$("#newPassword").nextAll("span").html('');
								$("#confirmPassword").nextAll("span").html('');
							
						},
						error	:	function(data){
							
							if(JSON.parse(data.responseText).error.message=="Old password is not correct."){
                             msgBox("fail", "原始密码不正确");
							}	
							
						},
						data: postData
						
					});
			});
			
			$(".length-range-6-18").keyup(function(){
				
				var passwordObj = $(this);

				var len = passwordObj.val().length;
				if( (len < 6) || (len > 18 ) ){ 
					$(this).nextAll("span").html("<font color=red>输入值的长度应该在 6 至 18 之间,当前长度为" + len + "</font>");
				}else{
					$(this).nextAll("span").html("");
				}
			});
		});
	</script>
	</body>
</html>