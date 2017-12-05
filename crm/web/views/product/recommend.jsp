<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

<title>推荐课程</title>
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
					<i class="fa-fw fa fa-home"></i> 管理后台 <span>> 推荐课程</span>
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
						<span class="widget-icon"><i class="fa fa-thumbs-o-up"></i>
						</span>
						<h2> 推荐课程设置</h2>
					</header>

					<!-- widget div-->
					<div>
						<!-- widget content -->
						<div class="widget-body no-padding">
							<!-- Toolbar -->

							<div class="well well-sm well-light no-border no-padding" id="wid-id-c1">
								<form action class="smart-form  no-padding">
									<fieldset class="no-padding">
										<div class="row" style="height:20px;">
										</div>
										<div class="row">
											<section class="col col-2"  style="margin-bottom:0px;">
												<label class="label text-right">推荐爆款课程：&emsp;</label>
											</section>	
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course4_1" placeholder="查找设置推荐爆款课程" cTypeId="4" recommendIndex=1 >
													</div>
												</label>
											</section>
										</div>
										<div class="row">
											<section class="col col-9"><hr/></section>
										</div>
										<div class="row">
											<section class="col col-2">
												<label class="label text-right">推荐行业课程：&emsp;</label>
											</section>	
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course1_6" placeholder="查找设置推荐行业课程" cTypeId="1" recommendIndex=6 >
													</div>
												</label>
											</section>
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course1_5" placeholder="查找设置推荐行业课程" cTypeId="1" recommendIndex=5>
													</div>
												</label>
											</section>
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course1_4" placeholder="查找设置推荐行业课程" cTypeId="1" recommendIndex=4>
													</div>
												</label>
											</section>
										</div>
										<div class="row">
											<section class="col col-2">
												<label class="label text-right"></label>
											</section>	
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course1_3" placeholder="查找设置推荐行业课程" cTypeId="1" recommendIndex=3>
													</div>
												</label>
											</section>
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course1_2" placeholder="查找设置推荐行业课程" cTypeId="1" recommendIndex=2>
													</div>
												</label>
											</section>
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course1_1" placeholder="查找设置推荐行业课程" cTypeId="1" recommendIndex=1>
													</div>
												</label>
											</section>
										</div>
										<div class="row">
											<section class="col col-9"><hr/></section>
										</div>
										<div class="row">
											<section class="col col-2">
												<label class="label text-right">推荐岗位课程：&emsp;</label>
											</section>	
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course2_5" placeholder="查找设置推荐岗位课程" cTypeId="2" recommendIndex=5>
													</div>
												</label>
											</section>
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course2_4" placeholder="查找设置推荐岗位课程" cTypeId="2" recommendIndex=4>
													</div>
												</label>
											</section>
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course2_3" placeholder="查找设置推荐岗位课程" cTypeId="2" recommendIndex=3>
													</div>
												</label>
											</section>
										</div>
										<div class="row">
											<section class="col col-2">
												<label class="label text-right"></label>
											</section>	
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course2_2" placeholder="查找设置推荐岗位课程" cTypeId="2" recommendIndex=2>
													</div>
												</label>
											</section>
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course2_1" placeholder="查找设置推荐岗位课程" cTypeId="2" recommendIndex=1>
													</div>
												</label>
											</section>
										</div>
										<div class="row">
											<section class="col col-9"><hr/></section>
										</div>
										<div class="row">
											<section class="col col-2">
												<label class="label text-right">推荐能力课程：&emsp;</label>
											</section>	
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course3_5" placeholder="查找设置推荐能力课程" cTypeId="3" recommendIndex=5>
													</div>
												</label>
											</section>
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course3_4" placeholder="查找设置推荐能力课程" cTypeId="3" recommendIndex=4>
													</div>
												</label>
											</section>
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course3_3" placeholder="查找设置推荐能力课程" cTypeId="3" recommendIndex=3>
													</div>
												</label>
											</section>
										</div>
										<div class="row">
											<section class="col col-2">
												<label class="label text-right"></label>
											</section>
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course3_2" placeholder="查找设置推荐能力课程" cTypeId="3" recommendIndex=2>
													</div>
												</label>
											</section>
											<section class="col col-2">
												<label class="select">
													<div class="btn-group input">
														<input class="form-control" type="text"  id="course3_1" placeholder="查找设置推荐能力课程" cTypeId="3" recommendIndex=1>
													</div>
												</label>
											</section>
										</div>

										<div class='row'>
											<section class="col col-9">
												<label class="label text-right">
													<span class="btn btn-primary btn-success btn-save" style="padding: 6px 12px;"> &nbsp;&nbsp;<i class="fa fa-save"></i> 保存&nbsp;&nbsp;  </span>
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

				<!-- end widget -->
				
				<div class="jarviswidget jarviswidget-color-blueDark">
					<header>
						<span class="widget-icon"><i class="fa fa-thumbs-o-up"></i>
						</span>
						<h2> 二级行业推荐课程设置</h2>
					</header>

					<!-- widget div-->
					<div>
						<!-- widget content -->
						<div class="widget-body no-padding">
							<!-- Toolbar -->
							
							<div class="well well-sm well-light no-border no-padding" id="wid-id-c2">
								<form action class="smart-form  no-padding">
										<div class="row" style="height:25px;">
										</div>
										<div class="row">
											<section class="col col-2">
												<label class="label text-right"><b><span style="color:#ff0000;font-size:16px;">显示行业：</span></b></label>
											</section>	
											<section class="col col-4">
												<label class="label text-left"><select id="industryId" name="industryId"  class="form-control">
													<option value='' selected>全部</option>
												</select></label>
											</section>
										</div>
										<div class="row">
											<section class="col col-9"><hr/></section>
										</div>
									<fieldset class="no-padding industry-fieldset" >
									</fieldset>
								</form>
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
	<script type="text/javascript" src="${ctx}/static/js/page/product/recommend.js${randomstr}" charset="utf-8"></script>
</body>
</html>