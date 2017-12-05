<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

<title>企业管理</title>
<meta name="description" content="">
<meta name="author" content="">

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<jsp:include page="../public/css.jsp" />
<style type="text/css">
	._dataTables_length {display:none}
	div.dataTables_length select {width:80px}
	
	.image_logo {
    max-height:20px; 
    max-width:20px;
    width:expression( (this.width > 20 && this.width > this.height) ? '20px': this.width+'px');
    height:expression( (this.height > 20 && this.height > this.width) ? '20px': this.height+'px');
    border: 0px;
    vertical-align: middle;
}
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/jquery.multiselect.css" />
</head>
<body class="">
	<div id="content">

		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<h1 class="page-title txt-color-blueDark">
					<i class="fa-fw fa fa-home"></i> 管理后台 <span>> 企业管理</span>
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
						<h2>查找企业</h2>
					</header>

					<!-- widget div-->
					<div>
						<!-- widget content -->
						<div class="widget-body no-padding">
							<!-- Toolbar -->
							</br>
							<ul class="nav nav-tabs slide-tab">
								<li class="active last-child"><a href="javascript:void(0);"><i class="fa fa-search"></i>&nbsp;&nbsp;查询条件<b class="caret"></b></a></li>
								<li class="extend-btn"></li>
							</ul>

							<div class=" hide-tab-panel well well-sm well-light no-border no-padding"
								id="wid-id-c2">
								<form action class="smart-form  no-padding">
									<fieldset class="no-padding">
										<div class="row" style="padding-top:10px;padding-bottom:0px;">
											<section class="col col-1">
												<label class="label text-right">选择行业：</label>
											</section>	
											<section class="col col-2">
												<label class="label text-left">
												<select id="industry" name="industry" class="form-control">
													<option value='' selected>全部行业</option>
													<option value='common'>通用行业</option>
												</select>
												</label>
											</section>
											<section class="col col-1  subin-col" style='display:none;'>
												<label class="label text-right">子行业：</label> 
											</section>
											<section class="col col-2  subin-col" style='display:none;'>
												<label  class="label text-left">
												<select id="subindustry" name="subindustry" class="form-control">
													<option value='' selected>全部子行业</option>
												</select>
												</label>
											</section>
											<section class="col col-2">
												<label class="input label text-center"><span class="btn btn-primary btn-search" style="padding: 6px 12px;"><i class="fa fa-search"></i> 查询 </span> </label>
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

				<div class="jarviswidget jarviswidgetdiv departmenttemplatelist">

					<div class="well well-sm well-light  no-border" id="wid-id-c2">
						<div class="btn-group btn-group-md  no-border">
							<span class="btn btn-success btn-add"><i class="fa fa-plus"></i> 添加 </span>
						</div>
					</div>

					<div class="well well-sm well-primary no-border">

						<table id="departmentTemplateTable" class="table table-bordered table-hover">
							<thead>

							</thead>
							<tbody>

							</tbody>
							<tfoot>

							</tfoot>
						</table>
					</div>
				</div>
				<!-- end widget -->
			</article>
			<!-- WIDGET END -->
			<!-- end row -->
		</section>
		<!-- end widget grid -->
	</div>
	<!-- End Input Modal -->
	<jsp:include page="adddepartment.jsp" />
	<jsp:include page="../public/js.jsp" />
	<script type="text/javascript" src="${ctx}/static/js/jquery.multiselect.js${randomstr}" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/static/js/page/manage/managedepartmentdic.js${randomstr}" charset="utf-8"></script>
</body>
</html>