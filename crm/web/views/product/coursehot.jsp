<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

<title>热度管理</title>
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
</style>
</head>
<body class="">
	<div id="content">

		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<h1 class="page-title txt-color-blueDark">
					<i class="fa-fw fa fa-home"></i> 管理后台 <span>> 热度管理</span>
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
						<h2>热度设置</h2>
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
												<label class="label text-right">选择分类：</label>
											</section>	
											<section class="col col-3">
												<label class="label text-left">
												<div class="inline-group">
												<label class="radio"><input name="type" type="radio" value="1" checked><i></i>行业</label>
												<label class="radio"><input name="type" type="radio" value="2"><i></i>岗位</label>
												<label class="radio"><input name="type" type="radio" value="3"><i></i>能力</label>
												</div>
												</label>
											</section>
											<section class="col col-1">
												<label class="label text-right">类型名称：</label> 
											</section>
											<section class="col col-2">
												<label class="input"> <i class="icon-prepend fa fa-home"></i>
													<input type="text" name="typeName" placeholder="输入所属类型的名称" maxlength="20" id="typeName">
													<span></span>
												</label>
											</section>
											<section class="col col-1">
											</section>
											<section class="col col-2">
												<label class="label text-left">
													<span class="btn btn-primary btn-search" style="padding: 6px 12px;"><i class="fa fa-search"></i> 查询 </span>
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

				<input type="hidden" id="typeId"/>
				<div class="jarviswidget typeDataDiv" style="display:none;">
								
					<div class="well well-sm well-primary no-border">

						<table id="typeDataTable" class="table table-bordered table-hover">
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

<div class="modal fade" id="courseHotListTable">
	<div class="modal-dialog">
		<div class="modal-content" style="width:700px">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title course-modal-title">课程列表</h4>
			</div>
			
			<div class="modal-body">
				<div class="widget-body">
				<input type="hidden" id="targetId"/>
								<form action class="smart-form  no-padding">
									<fieldset class="no-padding">
										<div class="row" style="padding-top:10px;padding-bottom:0px;">
											<section class="col col-2">
												<label class="label text-right">课程名称：</label> 
											</section>
											<section class="col col-4">
												<label class="input text-left">
													<input type="text" name="courseName" placeholder="输入课程名称" maxlength="20" id="courseName">
													<span></span>
												</label>
											</section>
											<section class="col col-2">
												<label class="label text-left">
													<span class="btn btn-primary btn-search-course"  style="padding: 6px 12px;"><i class="fa fa-search"></i> 查询 </span>
												</label>
											</section>
										</div>
									</fieldset>
								</form>		
				<br>
				<div class="jarviswidget coursediv">
					<div class="well well-sm well-primary no-border">

						<table id="courseTable" class="table table-bordered table-hover">
							<thead>

							</thead>
							<tbody>

							</tbody>
							<tfoot>

							</tfoot>
						</table>
					</div>
				</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>	

	<jsp:include page="../public/js.jsp" />
	<script type="text/javascript" src="${ctx}/static/js/plugin/x-editable/moment.min.js${randomstr}" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/static/js/plugin/x-editable/jquery.mockjax.min.js${randomstr}" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/static/js/plugin/x-editable/x-editable.min.js${randomstr}" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/static/js/page/product/coursehot.js${randomstr}" charset="utf-8"></script>
</body>
</html>