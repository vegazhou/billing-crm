<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<meta charset="utf-8">
		<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->
		<title>资讯管理</title>
		<meta name="description" content="">
		<meta name="author" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes">

		<jsp:include page="../public/css.jsp" />
		<script type=text/javascript src="<c:url value ="/static/ueditor/ueditor.config.js" />"></script>
		<script type=text/javascript src="<c:url value ="/static/ueditor/ueditor.all.js"/>"></script>
		<LINK rel=stylesheet href="<c:url value ="/static/ueditor/themes/default/css/ueditor.css"/>"/>
		<script type="text/javascript" src="<c:url value ="/static/js/page/news/ColorPicker.js" />" charset="utf-8"></script>
	</head>
<body>
	<div id="content">
		<input type="hidden" id="picUrlVal" value=""/>
		<input type="hidden" id="picUrl2Val" value=""/>
		
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<h1 class="page-title txt-color-blueDark">
					<i class="fa-fw fa fa-home"></i> 资讯管理 <span>>修改资讯信息</span>
				</h1>
			</div>
		
		</div>
		<!-- NEW COL START -->
		<article class="col-sm-12 col-md-12 col-lg-12">
		<!-- Widget ID (each widget will need unique ID)-->
		<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false"
			data-widget-editbutton="false" data-widget-custombutton="false">
			<header>
				<span class="widget-icon"> <i class="fa fa-edit"></i> </span>
			</header>
			
			<div>
			<!-- widget edit box -->
				<div class="jarviswidget-editbox">
					<!-- This area used as dropdown edit box -->
				</div>
			<!-- widget content -->
				<div class="widget-body no-padding">
					<form  class="smart-form" novalidate="novalidate">
						<header>
							<strong>基本信息</strong>
							<label class="input"
									style="text-align:right;vertical-align:middle;margin-top:10px;"> <font
									color="red" style="font-size: 13px">* 为必填项</font>
							</label>
						</header>
						
						<fieldset>
							<div class="row">
							<section class="col col-5">
								<label class="label text_left">
									<font color=red>* </font>标题名称：
								</label>
								<label class="input">
									<i class="icon-prepend fa fa-list-alt"></i>
									<input type="text" placeholder="请输入标题名称！" id="newsTitle" class="required" maxlength="50" value="<%=request.getParameter("title")%>">
									<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>字符长度不大于50个! </b>
									<span></span>
								</label>
							</section>
							
							<section class="col col-5">
								<label class="label text_left" >
									<font color=red>* </font>资讯类型：
								</label>
									<select id="updateType" class="form-control required" disabled>
										<option value="">请选择资讯类型</option>
										<option value="1">资讯</option>
										<option value="2">活动</option>
										<option value="3">企业轮播</option>
										<option value="4">首页轮播</option>
										<option value="5">直播广告</option>
										<option value="6">线下广告</option>
										<option value="7">专题广告</option>
										<option value="9">焦点小图</option>
									</select>
									<span></span>
							</section>
						</div>
						<div class="row">
							<section class="col col-5 stitleRow" style="display:none">
								<label class="label text_left">
									<font color=red> </font>短标题名称：
								</label>
								<label class="input">
									<i class="icon-prepend fa fa-list-alt"></i>
									<input id="newsStitle" type="text" placeholder="请输入短标题名称！" class="required" maxlength="20" value="<%=request.getParameter("stitle")%>">
									<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>字符长度不大于20个! </b>
									<span></span>
								</label>
							</section>
							
							<section class="col col-3 image" >
								<label class="label text-left">图片：<span class="imagetxt"></span></label>
								<label class="input input-file state-success" >
									<img style="width:300px;height:150px" src="<%=request.getParameter("image")%>" alt="<%=request.getParameter("image")%>">
								</label>
							</section>
							<section class="col col-2 image" >
								<label class="label text-left">&nbsp;</label>
								<label class="input input-file state-success" >
									<span class='btn btn-success btn-replaceImage' style="padding:6px 12px"><i class='fa fa-plus'></i> 替换图片 </span>
								</label>
							</section>
							
							<section class="col col-3 imageReplace" style="display:none;">
								<label class="label text-left">图片：<span class="imagetxt"></span></label>
								<label class="input input-file state-success" >
									<div class="button" >
										  <input type="file" id="file" name="file" />选择
									</div>
									<input type="text" id="filetxt" readonly="" style="height:33px"/>
									<input type="hidden" id="image" value="<%=request.getParameter("image")%>">
									<span id="image_vali"></span>
									<span id="file_vali"></span>
								</label>
							</section>
							<section class="col col-2 imageReplace" style="display:none;"></section>
							<section class="col col-2 boxRow" style="display:none">
								<label class="label text_left">
									<font color=red>* </font>应用场景：
								</label>
								<input type="checkbox" name="newsbox" id="box1" value="1" > B端 &emsp; &emsp; &emsp; &emsp;
								<input type="checkbox" name="newsbox" id="box2" value="2" > C端
								<span></span>
							</section>
							<section class="col col-5 mobileRow" style="display:none">
								<label class="label text_left">
									<font color=red> </font>手机端地址：
								</label>
								<label class="input">
									<i class="icon-prepend fa fa-list-alt"></i>
									<input id="mobileUrl" class="form-control" type="text" value="<%=request.getParameter("mobileUrl")%>">
									<div id="mobileUrltxt" style="display:none"><font color="red">手机端页面地址不能都空！</font></div>
								</label>	
							</section>
							<%-- 
							<section class="col col-1 bgcolor" >
								<label class="label text_left">
									<font color=red> </font>背景色：
								</label>
								<label class="input text-left" >
									<div id="colordiv" style="width:100px;height:30px;border:1px solid #999;background:#<%=request.getParameter("color")%>" ></div>
									<div id="colortxt" style="width:100px;line-height:30px;display:none" >未选择背景色</div>
								</label>
							</section>
							<section class="col col-2 bgcolor" >
								<label class="label text-left">&nbsp;</label>
								<label class="input input-file state-success" >
									<span class='btn btn-success btn-replaceBg' style="padding:6px 12px"><i class='fa fa-plus'></i> 替换背景 </span>
								</label>
							</section>
							
							<section class="col col-2 bgReplace" style="display:none">
								<label class="label text_left">
									<font color=red> </font>背景色：
								</label>
								<label class="input text-left" >
									<i class="icon-prepend fa fa-list-alt"></i>
									<input type="text" name="color" id="color" placeholder="请点击右侧按钮选择背景色！" disabled
									  onclick="showcolorpicker(this,event,'color')">
									<input type="hidden" id="updateColor" value="#<%=request.getParameter("color")%>"/>
									<span></span>
								</label>
							</section>
							<section class="col col-1 bgReplace" style="display:none">
								<label class="label text_left">
									<font color=red> </font>&nbsp;
								</label>
								<label class="label">
									<script>CreateCPBtn('color');</script>
								</label>
							</section>
							--%>
						</div>
						<div class="row courseRow" >
							<section class="col col-5">
								<label class="label text_left">
									<font color=red> </font>课程信息：
								</label>
								<label class="input newsLink4">
									<i class="icon-prepend fa fa-list-alt"></i>
									<input id="newsLink4" class="form-control" type="text" placeholder="请选择课程信息！">
									<div id="linktxt4" style="display:none"><font color="red">课程信息不能为空！</font></div>
								</label>
								<label class="input newsLink5" style="display:none">
									<i class="icon-prepend fa fa-list-alt"></i>
									<input id="newsLink5" class="form-control" type="text" placeholder="请选择课程信息！">
									<div id="linktxt5" style="display:none"><font color="red">课程信息不能为空！</font></div>
								</label>
								<label class="input newsLink6" style="display:none">
									<i class="icon-prepend fa fa-list-alt"></i>
									<input id="newsLink6" class="form-control" type="text" placeholder="请选择课程信息！">
									<div id="linktxt6" style="display:none"><font color="red">课程信息不能为空！</font></div>
								</label>
							</section>
						</div>
						
						<div class="row staticUrlRow" style="display:none">
							<section class="col col-5">
								<label class="label text_left">
									<font color=red> </font>页面地址：
								</label>
								<label class="input">
									<i class="icon-prepend fa fa-list-alt"></i>
									<input id="staticUrl" class="form-control" type="text" placeholder="请输入页面地址！" value="<%=request.getParameter("staticUrl")%>">
									<div id="staticUrltxt" style="display:none"><font color="red">页面地址不能都空！</font></div>
								</label>
								
							</section>
						</div>
						
						<div class="row editorRow" style="display:none">
							<input type="hidden" id="newsContent" value="<%=request.getParameter("content")%>"/>					
							<section class="col col-10">
								<DIV id=myEditor style="width: 100%; height: 500px"></DIV>
							</section>
						</div>
						</fieldset>	
					</form>
				</div>	
				
				<!-- widget content -->
				<div align="right">
					<fieldset>
						<div class="modal-footer">
						<a class="btn btn-default" href="javascript:window.history.back();">返回</a>
						<button type="button" class="btn btn-primary btn-updateSave" >
							保存
						</button>
						<input type="hidden" id="newsId" value="<%=request.getParameter("newsId")%>">
						</div>
					</fieldset>
				</div>
			<!-- end widget content -->

		</div>

		</article>
	<!-- END COL -->

	</div>
	</div>
</body>	

<!-- end widget grid -->
<jsp:include page="../public/js.jsp" />

<script type="text/javascript">
	var editor;
	var content;
	$(document).ready(function() {
		function addEditor(){
			content = $("#newsContent").val();
			editor = new baidu.editor.ui.Editor({
				/*initialFrameWidth:760,*/
				initialFrameHeight : 300,
				initialContent : content,
				zIndex:900,
				textarea : 'htmlPart'
			});
			editor.render("myEditor"); 
		}
		
		var typeId = '<%=request.getParameter("type")%>';
		if(typeId == "1" || typeId == "2"){
			addEditor();
		}
		news.checktype(typeId);
		if(typeId == "3"||typeId == "4"||typeId == "7"||typeId == "9") $(".staticUrlRow").show(); 
		$("#newsLink"+typeId).val("<%=request.getParameter("content")%>");
		$("#newsLink"+typeId).attr('val','<%=request.getParameter("newsLink")%>');
		
		$("#updateType").find("option").each(function(){
			if(typeId==$(this).val()){
				$(this).attr("selected",true);
				return false;
			}
			$(this).attr("selected",false);
		});
		
		var subType = '<%=request.getParameter("subType")%>';
		if(subType == 0){
			$("#box1").prop("checked",true);
			$("#box2").prop("checked",true);
		}else if(subType == 1){
			$("#box1").prop("checked",true);
		}else{
			$("#box2").prop("checked",true);
		}
	});
</script>
<script src="<c:url value="/static/js/page/news/ajaxfileupload.js"/>" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value="/static/js/page/news/news.js"/>" type="text/javascript" charset="utf-8"></script>
</html>
