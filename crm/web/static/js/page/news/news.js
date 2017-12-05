var news = {
	apiContextPath : '/adminapi/v1',
	lg : function (msg) {},
	type : 'news',
	dataTable : null,
	browseDataTable : null,
	editor : null,
	init : function () {
		var _self = this;
		_self.initTable(_self);
		
		$(document).on("change","#file",function(){
			$("#filetxt").val($("#file").val());
			_self.ajaxFileUpload(_self, "file", "image");
		});
		
		$.combox({
		    field: 'newsLink5',
		    type:  'get',
		    url: G_CTX_ROOT+"/v1/webinar/findByTitle",
		    key: 'pid',
		    value: 'title',
		    size: 10,
		    search: "title",  //postJson name名称
		    'z-index':999,
		    callBack: function(){}
		});
		
		$.combox({
		    field: 'newsLink6',
		    type:  'get',
		    url: G_CTX_ROOT+"/v1/offlineCourses/findByTitle",
		    key: 'pid',
		    value: 'title',
		    size: 10,
		    search: "title",  //postJson name名称
		    'z-index':999,
		    callBack: function(){}
		});
		
		$(".btn-replaceImage").unbind("click").click(function () {
			$(".image").hide();
			$(".imageReplace").show();
		});
		
		$(".btn-add").unbind("click").click(function () {
			validator.clearForm();
		});
		
		$('.btn-search').unbind("click").click(function () {
			_self.dataTable.fnDraw();
		});
		
		$("#newsType").unbind("change").change(function () {
			_self.checktype($(this).val());
		});
		
		$(".btn-save").unbind("click").click(function () {
			$(".btn-save").attr("disabled", true);
			if (!validator.validate()) {
				$(".btn-save").attr("disabled", false);
				return false;
			}
			var typeId = parseInt($.trim($("#newsType").val()));
			if(typeId != 1){
				if ($("#image").val() == "") {
					$("#image_vali").html("<font color='red'>图片url为空!</font>");
					$(".btn-save").attr("disabled", false);
					return false;
				}
				$("#image_vali").html("");
			}
			if (typeId == 5||typeId == 6) {
				if ($("#newsLink"+typeId).val() == "") {
					$("#linktxt"+typeId).show();
					$(".btn-save").attr("disabled", false);
					return false;
				}
				$("#linktxt"+typeId).hide();
			}
			if(typeId==3||typeId==4||typeId==7||typeId==9){
				if($("#staticUrl").val() == ""){
					$("#staticUrltxt").show();
					$(".btn-save").attr("disabled", false);
					return false;
				}
				$("#staticUrltxt").hide();
			}
			var boxtype = 0;
			var postURL = G_CTX_ROOT + "/v1/news";
			var data = {};
			data.title = $.trim($("#newsTitle").val());
			data.type = typeId;
			data.coverLarge = $.trim($("#image").val());
			data.link = $.trim($("#newsLink"+data.type).attr('val'));
			var box1 = $("#box1").prop("checked");
			var box2 = $("#box2").prop("checked");
			if(box1) boxtype = parseInt($("#box1").val());
			if(box2) boxtype = parseInt($("#box2").val());
			if(box1 && box2) boxtype = 0;
			data.subType = boxtype;
			data.stitle = typeId == 1?$.trim($("#newsStitle").val()):data.title;
			if(typeId == 1||typeId == 2){
				data.content = editor.getContent();
			}else{
				data.content = $.trim($("#newsLink"+data.type).val());
			}
			if(typeId == 3||typeId == 4||typeId == 7||typeId == 9){
				data.staticUrl = $.trim($("#staticUrl").val());
				data.mobileUrl = $.trim($("#mobileUrl").val());
			}
			
			var postData = JSON.stringify(data);
			$.ajaxInvoke({
				url : postURL,
				data : postData,
				type : "post",
				contentType : "application/json",
				success : function (data) {
					mAlert("新增资讯成功");
				},
				error : function (data) {
					if (data.status == "201") {
						mAlert("新增资讯成功");
					} else {
						var obj = JSON.parse(data.responseText);
						mAlert("新增资讯失败，" + obj.error.message);
					}
					setTimeout("window.location.href = 'news.jsp';", 3000);
				},
				dataType : "json"
			});
		
		});
		
		$(".btn-updateSave").unbind("click").click(function () {
			$(".btn-updateSave").attr("disabled", true);
			var typeId = parseInt($.trim($("#updateType").val()));
			if (typeId == 5||typeId == 6) {
				if ($("#newsLink"+typeId).val() == "") {
					$("#linktxt"+typeId).show();
					$(".btn-updateSave").attr("disabled", false);
					return false;
				}
				$("#linktxt"+typeId).hide();
			}
			if(typeId == 3||typeId== 4||typeId == 7||typeId == 9){
				if($("#staticUrl").val() == ""){
					$("#staticUrltxt").show();
					$(".btn-updateSave").attr("disabled", false);
					return false;
				}
				$("#staticUrltxt").hide();
			}
			var boxtype = 0;
			var newsId = $("#newsId").val();
			var data = {};
			data.title = $.trim($("#newsTitle").val());
			data.type = typeId;
			data.coverLarge = $.trim($("#image").val());
			data.link = $.trim($("#newsLink"+data.type).attr('val'));
			var box1 = $("#box1").prop("checked");
			var box2 = $("#box2").prop("checked");
			if(box1) boxtype = parseInt($("#box1").val());
			if(box2) boxtype = parseInt($("#box2").val());
			if(box1 && box2) boxtype = 0;
			data.subType = boxtype;
			if(typeId == 1) data.stitle = $.trim($("#newsStitle").val());
			if(typeId == 1||typeId == 2){
				data.content = editor.getContent();
			}else{
				data.content = $.trim($("#newsLink"+data.type).val());
			}
			if(typeId == 3||typeId == 4||typeId == 7||typeId == 9){
				data.staticUrl = $.trim($("#staticUrl").val());
				data.mobileUrl = $.trim($("#mobileUrl").val());
			}
		
			var postData = JSON.stringify(data);
			$.ajaxInvoke({
				url : G_CTX_ROOT + "/v1/news/" + newsId,
				data : postData,
				type : "put",
				contentType : "application/json",
				success : function (data) {
					mAlert("修改资讯成功");
					setTimeout("window.location.href = 'news.jsp';", 3000);
				},
				error : function (data) {
					var obj = JSON.parse(data.responseText);
					mAlert("修改资讯失败，" + obj.error.message);
					setTimeout("window.location.href = 'news.jsp';", 3000);
				},
				dataType : "json"
			});
		
		});
		
		var rule = {newsTitle : {name : "标题名称",method : {required : true,lt : 50,},},
			newsType : {name : "内容类型",method : {required : true,},},
		};
		validator.init(rule);
	},
	checktype : function (typeId) {
		function newLink(linkid){
			if(linkid == "5"){
				$(".newsLink4").hide();
				$(".newsLink5").show();
				$(".newsLink6").hide();
			}else if(linkid == "6"){
				$(".newsLink4").hide();
				$(".newsLink5").hide();
				$(".newsLink6").show();
			}
		}
		function checkFlag(stitleRow,image,boxRow,courseRow,editorRow,staticUrlRow,mobileRow) {
			stitleRow == true ? $(".stitleRow").show() : $(".stitleRow").hide();
			image == true ? $(".image").show() : $(".image").hide();
			boxRow == true ? $(".boxRow").show() : $(".boxRow").hide();
			courseRow == true ? $(".courseRow").show() : $(".courseRow").hide();
			editorRow == true ? $(".editorRow").show() : $(".editorRow").hide();
			staticUrlRow == true ? $(".staticUrlRow").show() : $(".staticUrlRow").hide();
			mobileRow == true ? $(".mobileRow").show() : $(".mobileRow").hide();
		}
		
		if (typeId == ""){
			checkFlag(false,false,false,false,false,false,false);
		}else if(typeId == "1"){
			$(".imagetxt").html("(200*113)");
			checkFlag(true,true,false,false,true,false,false);
		}else if (typeId == "2"){
			$(".imagetxt").html("(225*96)");
			checkFlag(false,true,true,false,true,false,false);
		}else if (typeId == "3"){
			$(".imagetxt").html("(723*286)");
			checkFlag(false,true,false,false,false,true,true);
		}else if (typeId == "4"){
			$(".imagetxt").html("(899*290)");
			checkFlag(false,true,false,false,false,true,true);
		}else if (typeId == "7"){
			$(".imagetxt").html("(1200*100)");
			checkFlag(false,true,false,false,false,true,false);
		}else if (typeId == "9"){
			$(".imagetxt").html("(130*63)");
			checkFlag(false,true,false,false,false,true,false);
		}else{
			$(".imagetxt").html("(1280*330)");
			newLink(typeId);
			checkFlag(false,true,false,true,false,false,false);
		}
	},
	ajaxFileUpload : function (self, fileId, imageId) {
		var _self = self;
		var url = G_CTX_ROOT + "/v1/news/upload";
		url = adminServer + url.replace('/lecaiadminapi', adminContextPath);
		$.ajaxFileUpload({
			url : url,
			data : {'Token' : token},
			secureuri : false, //是否启用安全提交,默认为false
			fileElementId : fileId, //文件选择框的id属性
			dataType : 'json',
			success : function (data, status) {
				$("#" + imageId).val(data.imgUrl);
			},
			error : function (data, status, e) {
				$("#" + imageId).val(data.imgUrl);
			}
		});
	},
	changeEmpty : function (val) {
		var flag = val != null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g, "") != "";
		return flag;
	},
	jsonGetData : function (self, start, limit, orderby, direction) {
		var _self = self;
		return '?orderby=' + (orderby ? orderby : "createTime")
		 + '&direction=' + (direction ? direction : "asc")
		 + '&offset=' + (start ? start : 0)
		 + '&limit=' + (limit ? limit : 10000) + '';
	},
	jsonPostData : function (self, start, limit, orderby, direction) {
		var _self = self;
		var statusflag = _self.changeEmpty($("#s_status").val());
		var status = statusflag == false ? 0 : $("#s_status").val();
		var typeflag = _self.changeEmpty($("#s_type").val());
		var type = typeflag == false ? 0 : $("#s_type").val();
		var title = $("#s_title").val();
		return '{"status":' + status
		 + ',"type":' + type
		 + ',"title":"' + title
		 + '","orderby":"' + (orderby ? orderby : "createTime")
		 + '","direction":"' + (direction ? direction : "asc")
		 + '","offset":' + (start ? start : 0)
		 + ',"limit":' + (limit ? limit : 10000) + '}';
	},
	tableSetting : function (self, aoColumns, url, type) {
		var _self = self;
		var setting = {
			"oLanguage" : dataTableLan,
			"aLengthMenu" : [[10, 25, 50], [10, 25, 50]],
			"bjQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bProcessing" : true,
			"bPaginate" : true,
			"bServerSide" : true,
			"bFilter" : false,
			"bInfo" : true,
			"bStateSave" : false,
			"bAutoWidth" : true,
			"aaSorting" : [[0, "desc"]],
			"fnServerData" : function (sSource, aoData, fnCallback) {
				_self.lg("aoData:" + JSON.stringify(aoData));
				var start = 0;
				var length = 10;
				var columns = null;
				var sEcho = 1;
				var order = [];
				$.each(aoData, function (i, item) {
					var name = item['name'];
					var value = item['value'];
					if (name == "start") {
						start = value;
					} else if (name == 'length') {
						length = value;
					} else if (name == 'draw') {
						sEcho = value;
					} else if (name == 'order') {
						order = value;
					} else if (name == 'columns') {
						columns = value;
					}
				});

				var orderby = columns[order[0].column].data;
				var direction = order[0].dir;
				if(type == "POST"){
					var postData = _self.jsonPostData(_self, start, length, orderby, direction);
					if (!postData) {
						return;
					}
				}else{
					url = _self.apiContextPath + "/news/" + $("#newsId").val() + "/browse";;
					url = url + _self.jsonGetData(_self, start, length, orderby, direction);
				}
				
				$.ajaxInvoke({
					type : type,
					url : url,
					dataType : "json",
					data : postData,
					success : function (content, status) {
						_self.lg(content);
						content['sEcho'] = sEcho;
						content['iDisplayStart'] = content.paging.offset;
						content['iDisplayLength'] = content.paging.limit;
						content['iTotalRecords'] = content.paging.count;
						content['iTotalDisplayRecords'] = content.paging.count;
						content['sortField'] = orderby; // "createTime";
						content['sortType'] = direction;
						fnCallback(content);
					},
					error : function (data) {
						_self.lg(data);
					}
				});
			},
			"sAjaxDataProp" : "datas",
			"aoColumns" : aoColumns,
		};
		commonSetting4DataTable(setting);

		return setting;
	},
	initTable : function (self) {
		var _self = self;
		if (_self.dataTable) {
			_self.dataTable.fnDraw();
			return;
		}
		var aoColumns = [{
			"sTitle" : "标题名称",
			"mData" : "title",
			"bSortable" : false,
			}, {
				"sTitle" : "内容类型",
				"mData" : "",
				"render" : function (data, dis, obj) {
					var type = obj.type;
					var subType = obj.subType;
					if (type == 1) return "资讯";
					if (type == 2){
						var typeName = "";
						if(subType == 0){
							typeName = "通用";
						}else if(subType == 1){
							typeName = "B端";
						}else {
							typeName = "C端";
						}
						return typeName+"活动";
					} 
					if (type == 3) return "企业轮播";
					if (type == 4) return "首页轮播";
					if (type == 5) return "直播广告";
					if (type == 6) return "线下广告";
					if (type == 7) return "专题广告";
					return "焦点小图";
				}
			}, {
				"sTitle" : "状态", 
				"mData" : "status", 
				"render" : function (data) {
					var type = "";
					if (data == 1)
						type = "已保存";
					if (data == 2)
						type = "已发布";
					return type;
				}
			}, {
				"sTitle" : "查看", 
				"mData" : "", 
				"bSortable" : false,
				"render" : function () {
					searchDom = "<span class='btn btn-success btn-xs btn-detail'> 详情 </span> ";
					return searchDom;
				}
			}, {
				"sTitle" : "创建时间",
				"mData" : "createTime",
				"render" : function (data) {
					return data.substring(0, data.length - 2);
				}
			}, {
				"sTitle" : "发布时间", 
				"mData" : "publishTime",
				"render" : function (data) {
					return data == "" ? "未发布" : data.substring(0, data.length - 2);
				}
			}, {
				"sTitle" : "浏览次数", 
				"mData" : "browseTime",
				"render" : function (data) {
					return "<a href='javascript:void(0);' class='btn-times'>"+data+"</a>";
				}
			}, {
				"sTitle" : "创建人",
				"mData" : "creator", 
			}, {
				"sTitle" : "操作", 
				"mData" : "", 
				"bSortable" : false,
				"sWidth" : "180px",
				"render" : function (data, dis, obj) {
					var editDom = "",
					publishDom = "",
					updDom = "",
					cancelDom = "";
					deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> ";
					if (obj.status == 1) {
						editDom = "<span class='btn btn-success btn-xs btn-modify'> 修改 </span> ";
						publishDom = "<span class='btn btn-primary btn-xs btn-publish'> 发布 </span> ";
					} else {
						if (obj.stick == 0) {
							updDom = "<span class='btn btn-success btn-xs btn-upd'> 置顶 </span> ";
						} else {
							updDom = "<span class='btn btn-warning btn-xs btn-upd'> 取消置顶 </span> ";
						}
						cancelDom = "<span class='btn btn-warning btn-xs btn-cancel'> 撤销 </span> ";
					}
					return "<input type='hidden' name='newsId' value='" + obj.pid + "'/>" +
					"<input type='hidden' name='newsStatus' value='" + obj.status + "'/>" +
					"<input type='hidden' name='newsStick' value='" + obj.stick + "'/>" +
					editDom + publishDom + updDom + cancelDom + deleteDom;
				}
			}
		];
		
		var url = _self.apiContextPath + "/news/searchNews";
		var setting = _self.tableSetting(_self, aoColumns, url, 'POST');
		setting.fnDrawCallback = function() {
			$(".btn-modify").unbind("click").click(function () {
				var newsId = $(this).parents("TR").find("input[type=hidden][name=newsId]").val();
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/v1/news/" + newsId,
					type : "get",
					contentType : "application/json",
					success : function (data) {
						var content = data.content;
						content = content.replace(/\"/g, "'");
						content = content.replace(/“/g, "&quot;");
						content = content.replace(/”/g, "&quot;");
						content = content.replace(/&quot;/g, "'");
						var str = "<input type='hidden' name='title' value='" + data.title + "'/>";
						str += "<input type='hidden' name='stitle' value='" + data.stitle + "'/>";
						str += "<input type='hidden' name='newsId' value='" + data.pid + "'/>";
						str += "<input type='hidden' name='content' value=\"" + content + "\"/>";
						str += "<input type='hidden' name='status' value='" + data.status + "'/>";
						str += "<input type='hidden' name='staticUrl' value='" + data.staticUrl + "'/>";
						str += "<input type='hidden' name='mobileUrl' value='" + data.mobileUrl + "'/>";
						str += "<input type='hidden' name='type' value='" + data.type + "'/>";
						str += "<input type='hidden' name='subType' value='" + data.subType + "'/>";
						str += "<input type='hidden' name='image' value='" + data.coverLarge + "'/>";
						str += "<input type='hidden' name='newsLink' value='" + data.link + "'/>";
						$("#updateForm").append(str);
						$("#updateForm").submit();
					},
					dataType : "json"
				});
			});
	
			$(".btn-detail").unbind("click").click(function () {
				var newsId = $(this).parents("TR").find("input[type=hidden][name=newsId]").val();
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/v1/news/" + newsId,
					type : "get",
					contentType : "application/json",
					success : function (data) {
						var content = data.content;
						content = content.replace(/\"/g, "'");
						content = content.replace(/“/g, "&quot;");
						content = content.replace(/”/g, "&quot;");
						content = content.replace(/&quot;/g, "'");
						var str = "<input type='hidden' name='title' value='" + data.title + "'/>";
						str += "<input type='hidden' name='stitle' value='" + data.stitle + "'/>";
						str += "<input type='hidden' name='content' value=\"" + content + "\"/>";
						str += "<input type='hidden' name='status' value='" + data.status + "'/>";
						str += "<input type='hidden' name='staticUrl' value='" + data.staticUrl + "'/>";
						str += "<input type='hidden' name='mobileUrl' value='" + data.mobileUrl + "'/>";
						str += "<input type='hidden' name='type' value='" + data.type + "'/>";
						str += "<input type='hidden' name='subType' value='" + data.subType + "'/>";
						str += "<input type='hidden' name='image' value='" + data.coverLarge + "'/>";
						str += "<input type='hidden' name='newsLink' value='" + data.link + "'/>";
						$("#detailForm").append(str);
						$("#detailForm").submit();
					},
					dataType : "json"
				});
			});
	
			$(".btn-delete").unbind("click").click(function () {
				var newsId = $(this).parents("TR").find("input[type=hidden][name=newsId]").val();
				var newsName = $(this).parents("TR").children('td').eq(0).html();
				mConfirm("确定删除【" + newsName + "】？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/news/" + newsId,
						type : "delete",
						contentType : "application/json",
						success : function (data) {
							msgBox('success', "删除资讯成功");
							_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
						},
						error : function (data) {
							msgBox('fail', "删除资讯失败");
						},
					});
				});
			});
	
			$(".btn-publish").unbind("click").click(function () {
				var newsId = $(this).parents("TR").find("input[type=hidden][name=newsId]").val();
				var newsName = $(this).parents("TR").children('td').eq(0).html();
				var data = {};
				data.status = 2;
				var postData = JSON.stringify(data);
				mConfirm("确定发布【" + newsName + "】？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/news/" + newsId,
						type : "put",
						data : postData,
						contentType : "application/json",
						success : function (data) {
							msgBox('success', "发布资讯成功");
							_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
						},
						error : function (data) {
							msgBox('fail', "发布资讯失败");
						},
					});
				});
			});
	
			$(".btn-upd").unbind("click").click(function () {
				var newsId = $(this).parents("TR").find("input[type=hidden][name=newsId]").val();
				var stick = $(this).parents("TR").find("input[type=hidden][name=newsStick]").val();
				var newsName = $(this).parents("TR").children('td').eq(0).html();
				var updname = "置顶";
				var data = {};
				if (stick == 0)
					data.stick = 1;
				if (stick == 1) {
					data.stick = 0;
					updname = "取消置顶";
				}
				var postData = JSON.stringify(data);
				mConfirm("确定" + updname + "【" + newsName + "】？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/news/" + newsId,
						type : "put",
						data : postData,
						contentType : "application/json",
						success : function (data) {
							msgBox('success', "操作资讯成功");
							_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
						},
						error : function (data) {
							msgBox('fail', "操作资讯失败");
						},
					});
				});
			});
	
			$(".btn-cancel").unbind("click").click(function () {
				var newsId = $(this).parents("TR").find("input[type=hidden][name=newsId]").val();
				var newsName = $(this).parents("TR").children('td').eq(0).html();
				var data = {};
				data.status = 1;
				var postData = JSON.stringify(data);
				mConfirm("确定撤销【" + newsName + "】？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/news/" + newsId,
						type : "put",
						data : postData,
						contentType : "application/json",
						success : function (data) {
							msgBox('success', "撤销资讯成功");
							_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
						},
						error : function (data) {
							msgBox('fail', "撤销资讯失败");
						},
					});
				});
			});
			
			$(".btn-times").unbind("click").click(function () {
				var newsId = $(this).parents("TR").find("input[type=hidden][name=newsId]").val();
				if ($('#newsId').val() != newsId) {
					$('#newsId').val(newsId);
				}
				_self.initBrowseTable(newsId, _self);
				$("#browseModal").modal("show");
			});
		};
		
		_self.dataTable = $('#newsDatatable').dataTable(setting);
	},
	initBrowseTable : function (id,self) {
		var _self = self;
		if (_self.browseDataTable) {
			_self.browseDataTable.fnDraw();
			return;
		} 
		var aoColumns = [{"sTitle" : "浏览日期","mData" : "days","bSortable" : false,},
		                 {"sTitle" : "浏览次数","mData" : "count","bSortable" : false,}];
		var url = _self.apiContextPath + "/news/" + id + "/browse";
		var setting = _self.tableSetting(_self, aoColumns, url, 'GET');
		setting.fnDrawCallback = function() {};
		_self.browseDataTable = $('#browseDatatable').dataTable(setting);
	},
}

$(document).ready(news.init());
