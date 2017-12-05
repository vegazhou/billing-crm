<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

<title>敏感词管理</title>
<meta name="description" content="">
<meta name="author" content="">

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<jsp:include page="../public/css.jsp" />
<style type="text/css">
	._dataTables_length {display:none}
	div.dataTables_length select {width:80px}
	
	.image_logo {
    max-height:200px; 
    max-width:200px;
    width:expression( (this.width > 200 && this.width > this.height) ? '200px': this.width+'px');
    height:expression( (this.height > 200 && this.height > this.width) ? '200px': this.height+'px');
    border: 0px;
    vertical-align: middle;
}

	.s_word{
		display:inline;
		font-size:14px;
	}
	.s_delete{
		display:inline;
	}
	.s_word_h{
	 	/* background-color: #0099cc; */
	 	color:#296191;
	 	font-family:"微软雅黑", "黑体";
	 	border-color: #296191;
	 	border-width:2px;
	 	border-style: solid;
	}
	.s_word_m{
		background-color: #0099cc;
	}
	

	input::-ms-clear{display:none;}
</style>
</head>
<body class="">
	<div id="content">

		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<h1 class="page-title txt-color-blueDark">
					<i class="fa-fw fa fa-home"></i> 管理后台 <span>> 敏感词管理</span>
				</h1>
			</div>

		</div>

		<!-- search widget -->
		<section id="widget-grid" class="">
			<!-- NEW WIDGET START -->
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<!-- Widget ID (each widget will need unique ID)-->
				<div class="jarviswidget jarviswidget-color-blueDark">
					<header>
						<span class="widget-icon"> <i class="fa fa-table"></i>
						</span>
						<h2>删除敏感词</h2>
					</header>

					<!-- widget div-->
					<div>
						<!-- widget content -->
						<div class="widget-body no-padding">
							<!-- Toolbar -->
							</br>
							<ul class="nav nav-tabs slide-tab">
								<li class="active last-child"><a href="javascript:void(0);"><i class="fa fa-search"></i>&nbsp;&nbsp;敏感词查询<b class="caret"></b></a></li>
								<li class="extend-btn"></li>
							</ul>

							<div class=" hide-tab-panel well well-sm well-light no-border no-padding"
								id="wid-id-c2">
								<form action class="smart-form  no-padding">
									<fieldset class="no-padding">
										<div class="row" style="padding-left:20px; padding-top:20px;padding-bottom:0px;">
											<section class="col col-4 form-group">
												<label class="input"> <i class="icon-prepend fa fa-search"></i>
													<input type="text" name="searchWord" value="" placeholder="输入敏感词查询" tabindex="1" maxlength="20" id="searchWord" data-pure-clear-button>
												</label>
												<span id='errorMsg' style='margin-left:30px;'></span>
											</section>

											<section class="col col-2">
												<label class="label text-left" style="padding-left:20px;">
													<span class="btn btn-primary btn-sm btn-search" ><i class="fa fa-search"></i>&nbsp; 查 询  </span>
												</label>
											</section>	
										</div>
									</fieldset>
								</form>
							</div>
						</div>
						<!-- end widget content -->
					</div>
					<!-- end widget div -->
				</div>

				<div class="jarviswidget jarviswidgetdiv wordslistDiv">
					<div class="well well-sm well-primary no-border wordslist"  style = 'display:none;'>
                		<ul class="pagination"></ul>
                			<div class="form-group">
								<!-- <label>点击X删除</label>   -->
								<input  id="pagination-text" class="form-control delete-tags" value=''>
							</div>
					</div>
				</div>

				<!-- end widget -->
				
								<!-- Widget ID (each widget will need unique ID)-->
				<div class="jarviswidget jarviswidget-color-blueDark">
					<header>
						<span class="widget-icon"> <i class="fa fa-table"></i>
						</span>
						<h2>增加敏感词</h2>
					</header>

					<!-- widget div-->
					<div>
						<!-- widget content -->
						<div class="widget-body no-padding">
							<div class=" well well-sm well-light no-border no-padding" id="wid-id-c2">
								<form action class="smart-form  no-padding">
									<fieldset class="no-padding">
										<div class="row" style="padding-left:20px; padding-top:20px;padding-bottom:0px;">
											<section style="margin-left:80px;margin-right:80px">
													<div class="form-group">
														<label>请输入敏感词，词与词以逗号分隔或直接按回车键确定。</label>
														<input class="form-control add-tags" value="">
													</div> 
											</section>
										</div>
										
										<div class="row" style="padding-left:20px; padding-top:20px;padding-bottom:0px;">
											<section  style="margin-left:80px;margin-right:80px">
												<label class="label text-left">
													<span class="btn btn-primary btn-sm btn-save"  disabled><i class="fa fa-save"></i> &nbsp;保 存  </span>
													&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
													<span class="btn btn-default btn-sm btn-eraser" ><i class="fa fa-eraser"></i> &nbsp;清 除  </span>
												</label>
											</section>	
										</div>
									</fieldset>
								</form>
							</div>
						</div>
					</div>
				</div>
			</article>
			<!-- WIDGET END -->
			<!-- end row -->
		</section>
		<!-- end widget grid -->
	</div>
	<!-- End Input Modal -->

	<jsp:include page="../public/js.jsp" />
	<script type="text/javascript" src="${ctx}/static/js/plugin/jqPaginator/jqPaginator.min.js${randomstr}"></script>
	<script type="text/javascript" src="${ctx}/static/js/plugin/bootstrap-tags/bootstrap-tagsinput.min.js${randomstr}"></script>
	<script type="text/javascript" src="${ctx}/static/js/page/sysdic/sensitivewords.js?${randomstr}" charset="utf-8"></script>
</body>
</html>