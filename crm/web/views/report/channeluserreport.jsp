<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

<title>报表导出</title>
<meta name="description" content="">
<meta name="author" content="">

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<jsp:include page="../public/css.jsp" />
</head>
<body class="">
	<div id="content">

		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<h1 class="page-title txt-color-blueDark">
					<i class="fa-fw fa fa-home"></i> 管理后台 <span>> 报表导出</span>
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
						<h2>报表导出</h2>
					</header>

							<div class="well well-sm well-light no-border no-padding" id="wid-id-c2">
								<form action class="smart-form  no-padding">
										<div class="row" style="height:25px;">
										</div>
										<div class="row">
											<section class="col col-1">
												<label class="label text-right"><b><span >选择报表：</span></b></label>
											</section>	
											<section class="col col-5">
												<label class="label text-left"><select id="reportID" name="reportID"  class="form-control">
													<option value='report-A' selected>报表A(用户报表)</option>
													<option value='report-B'>报表B(运营报表)</option>
													<option value='report-C'>报表C(老企业新导入用户)</option>
													<option value='report-D'>报表D（用户运营日常报表）</option>
												</select></label>
												<span>（如遇Chrome崩溃，请使用Firefox或IE导出报表）</span>
											</section>
										</div>
										<div class="row">
											<section class="col col-8"><hr/></section>
										</div>
									<fieldset class="no-padding industry-fieldset" >
									</fieldset>
								</form>
							</div>
								<div class='div-report' id='report-A'>
									<form action class="smart-form  no-padding">
										<fieldset class="no-padding">
											<div class="row">
												<section class="col col-6" style="padding-bottom:0px;margin-bottom:0px;height:30px;margin-top:10px;">
													<label class="label text-right"><font color=red>*</font> 为必输项</label>
												</section>	
											</div>
											<div class="row">
												<section class="col col-1">
													<label class="label text-right"><font color=red>*</font> 选择渠道：</label>
												</section>	
												<section class="col col-2">
													<label class="text-left input">
													<select id="channel" name="channel" class="form-control channel-control">
														<option value='' selected>&nbsp;&nbsp;&nbsp;&nbsp;------&nbsp;&nbsp;&nbsp;&nbsp;请选择&nbsp;&nbsp;&nbsp;&nbsp;------&nbsp;&nbsp;&nbsp;&nbsp;</option>
													</select><span></span>
													</label>
												</section>
												<section class="col col-1">
													<label class="label text-right"><font color=red>*</font> 导入人数：</label> 
												</section>
												<section class="col col-2">
													<label class="input"> <i class="icon-prepend fa fa-home"></i>
														<input type="text" name="userCount" placeholder="输入导入人数" maxlength="20" id="userCount"  class="form-control">
														<span></span>
													</label>
												</section>
											</div>
											<div class="row">
												<section class="col col-1">
													<label class="label text-right"><font color=red>*</font> 开始日期：</label>
												</section>	
												<section class="col col-2">
													<label class="text-left input">
													<input type="text" name="reportStartTime-A" class='time-selector'
														placeholder="点击选择日期" id="reportStartTime-A" readonly="readOnly">
													<span></span>
													</label>
												</section>
												<section class="col col-1">
													<label class="label text-right"><font color=red>*</font> 结束日期：</label>
												</section>	
												<section class="col col-2">
													<label class="text-left input">
													<input type="text" name="reportEndTime-A" class='time-selector'
														placeholder="点击选择日期" id="reportEndTime-A" readonly="readOnly">
													<span></span>
													</label>
												</section>
												<section class="col col-2">
													<label class="label text-right">
														<span class="btn btn-primary btn-search btn-search-A" style="padding: 6px 12px;"> 导出 </span>
													</label>
												</section>
											</div>
										</fieldset>
									</form>
								</div>
								<div id='report-B' class='div-report' style='display:none'>
									<form action class="smart-form  no-padding">
										<fieldset class="no-padding">
											<div class="row">
												<section class="col col-1">
													<label class="label text-right">开始日期：</label>
												</section>	
												<section class="col col-2">
													<label class="text-left input">
													<input type="text" name="reportStartTime-B" class='time-selector'
														placeholder="点击选择日期" id="reportStartTime-B" readonly="readOnly">
													<span></span>
													</label>
												</section>
												<section class="col col-1">
													<label class="label text-right">结束日期：</label>
												</section>	
												<section class="col col-2">
													<label class="text-left input">
													<input type="text" name="reportEndTime-B" class='time-selector'
														placeholder="点击选择日期" id="reportEndTime-B" readonly="readOnly">
													<span></span>
													</label>
												</section>
												<section class="col col-2">
													<label class="label text-right">
														<span class="btn btn-primary btn-search btn-search-B" style="padding: 6px 12px;"> 导出 </span>
													</label>
												</section>
											</div>
										</fieldset>
									</form>
								</div>
								<div class='div-report' id='report-C'  style='display:none'>
									<form action class="smart-form  no-padding">
										<fieldset class="no-padding">
											<div class="row">
												<section class="col col-6" style="padding-bottom:0px;margin-bottom:0px;height:30px;margin-top:10px;">
													<label class="label text-right"><font color=red>*</font> 为必输项</label>
												</section>	
											</div>

											<div class="row">
												<section class="col col-1">
													<label class="label text-right"><font color=red>*</font> 开始日期：</label>
												</section>	
												<section class="col col-2">
													<label class="text-left input">
													<input type="text" name="reportStartTime-C" class='time-selector'
														placeholder="点击选择日期" id="reportStartTime-C" readonly="readOnly">
													<span></span>
													</label>
												</section>
												<section class="col col-1">
													<label class="label text-right"><font color=red>*</font> 结束日期：</label>
												</section>	
												<section class="col col-2">
													<label class="text-left input">
													<input type="text" name="reportEndTime-C" class='time-selector'
														placeholder="点击选择日期" id="reportEndTime-C" readonly="readOnly">
													<span></span>
													</label>
												</section>
												<section class="col col-2">
													<label class="label text-right">
														<span class="btn btn-primary btn-search btn-search-C" style="padding: 6px 12px;"> 导出 </span>
													</label>
												</section>
											</div>
										</fieldset>
									</form>
								</div>
								<div class='div-report' id='report-D'  style='display:none'>
									<form action class="smart-form  no-padding">
										<fieldset class="no-padding">
											<div class="row">
												<section class="col col-1">
													<label class="label text-right">开始日期：</label>
												</section>	
												<section class="col col-2">
													<label class="text-left input">
													<input type="text" name="reportStartTime-D" class='time-selector'
														placeholder="点击选择日期" id="reportStartTime-D" readonly="readOnly">
													<span></span>
													</label>
												</section>
												<section class="col col-1">
													<label class="label text-right">结束日期：</label>
												</section>	
												<section class="col col-2">
													<label class="text-left input">
													<input type="text" name="reportEndTime-D" class='time-selector'
														placeholder="点击选择日期" id="reportEndTime-D" readonly="readOnly">
													<span></span>
													</label>
												</section>
												<section class="col col-2">
													<label class="label text-right">
														<span class="btn btn-primary btn-search btn-search-D" style="padding: 6px 12px;"> 导出 </span>
													</label>
												</section>
											</div>
										</fieldset>
									</form>
								</div>
							</div>
						</div>
						<!-- end widget content -->
					</div>
					<!-- end widget div -->
				</div>

				<div class="downloadprogessdiv container" style="display:none;">
					<div class="text-center no-border no-padding"><img src='${ctx}/static/img/lding.gif'/><span></span></div>
				</div>
				<!-- end widget -->
			</article>
			<!-- WIDGET END -->
			<!-- end row -->
		</section>
		<!-- end widget grid -->
	</div>
	<!-- End Input Modal -->

	<jsp:include page="../public/js.jsp" />
	<script type="text/javascript" src="${ctx}/static/js/plugin/my97datePicker/WdatePicker.js${randomstr}"></script>
	<script type="text/javascript" src="${ctx}/static/js/page/report/channeluserreport.js${randomstr}" charset="utf-8"></script>
</body>
</html>