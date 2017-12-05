var webinar = {
	apiContextPath : '/adminapi/v1',
	lg : function (msg) {},
	type : 'webinar',
	dataTable : null,
	browseDataTable : null,
	registCountDataTable : null,
	editor : null,
	init : function () {
		var _self = this;
		_self.initTable(_self);
		_self.initEditor(_self);

		$(document).on("change", "#file", function () {
			$("#filetxt").val($("#file").val());
			_self.ajaxFileUpload(_self, "file", "logoimage");
		});

		$(document).on("change", "#file1", function () {
			$("#filetxt1").val($("#file1").val());
			_self.ajaxFileUpload(_self, "file1", "postimage");
		});

		$('.btn-changelogo').unbind("click").click(function () {
			$(".logofile").show();
			$(".logodiv").hide();
			$("#logoimage").val("");
			$("#filetxt").val("");
		});

		$('.btn-changepost').unbind("click").click(function () {
			$(".postfile").show();
			$(".postdiv").hide();
			$("#postimage").val("");
			$("#filetxt1").val("");
		});

		$('#startTime').unbind().bind('click', function () {
			WdatePicker({
				skin : 'twoer',
				dateFmt : 'yyyy-MM-dd HH:mm:ss',
				//maxDate : maxTime
			});
			return false;
		});

		$('.btn-search').unbind("click").click(function () {
			_self.dataTable.fnDraw();
		});

		$(".btn-add").unbind("click").click(function () {
			validator.clearForm();
			$("#filetxt").val("");
			$("#filetxt1").val("");
			$(".btn-save").show();
			$(".btn-updateSave").hide();
			$(".logofile").show();
			$(".logodiv").hide();
			$(".postfile").show();
			$(".postdiv").hide();
			$(".btn-save").attr("disabled", false);
			_self.editorSet(_self, "");
			$("#myModalLabel").html("添加直播课程");
			$("#myModal").modal("show");
		});

		$(".btn-save").unbind("click").click(function () {
			$(".btn-save").attr("disabled", true);
			if (!validator.validate()) {
				$(".btn-save").attr("disabled", false);
				return false;
			}
			var data = {};
			data.title = $.trim($("#title").val());
			data.duration = $.trim(parseInt($("#duration").val()));
			data.startTime = $.trim($("#startTime").val());
			data.teacherIds = $.trim($("#teacherIds").val());
			data.description = _self.editor.getContent();
			data.location = $.trim($("#location").val());
			data.liveurl = $.trim($("#liveurl").val());
			data.playbackurl = $.trim($("#playbackurl").val());
			data.poster = $.trim($("#postimage").val());
			data.logoUrl = $.trim($("#logoimage").val());

			var postData = JSON.stringify(data);
			$.ajaxInvoke({
				url : _self.apiContextPath + "/webinar",
				type : "post",
				data : postData,
				dataType : "json",
				success : function (data) {
					msgBox('success', "添加直播课程成功");
					$("#myModal").modal("hide");
					_self.dataTable.fnDraw();
				},
				error : function (data) {
					if (data.status == "201") {
						msgBox('success', "添加直播课程成功");
						$("#myModal").modal("hide");
						_self.dataTable.fnDraw();
					} else {
						msgBox('fail', "添加直播课程失败");
					}
				},
			});
		});

		$(".btn-updateSave").unbind("click").click(function () {
			$(".btn-updateSave").attr("disabled", true);
			if (!validator.validate()) {
				$(".btn-updateSave").attr("disabled", false);
				return false;
			}
			var data = {};
			data.title = $.trim($("#title").val());
			data.duration = $.trim(parseInt($("#duration").val()));
			data.startTime = $.trim($("#startTime").val());
			data.teacherIds = $.trim($("#teacherIds").val());
			data.description = _self.editor.getContent();
			data.location = $.trim($("#location").val());
			data.liveurl = $.trim($("#liveurl").val());
			data.playbackurl = $.trim($("#playbackurl").val());
			data.poster = $.trim($("#postimage").val());
			data.logoUrl = $.trim($("#logoimage").val());

			var postData = JSON.stringify(data);
			$.ajaxInvoke({
				url : _self.apiContextPath + "/webinar/" + $("#pid").val(),
				type : "put",
				data : postData,
				dataType : "json",
				success : function (data) {
					msgBox('success', "修改直播课程成功");
					$("#myModal").modal("hide");
					_self.dataTable.fnDraw();
				},
				error : function (data) {
					msgBox('fail', "修改直播课程失败");
					$("#myModal").modal("hide");
				},
			});
		});

		var rule = {title : {name : "课程标题",method : {required : true,lt : 50,},},
			startTime : {name : "开始时间",method : {required : true,date : false,},},
			teacherIds : {name : "讲师名称",method : {required : true,lt : 50,},},
			duration : {name : "直播时长",method : {required : true,isNum : true,},},
			location : {name : "现场地址",method : {required : false,lt : 50,},},
			logoimage : {name : "封面图片",method : {required : true,lt : 200,},},
			postimage : {name : "海报图片",method : {required : true,lt : 200,},},
			liveurl : {name : "直播地址",method : {required : true,lt : 200,},},
			playbackurl : {name : "回放地址",method : {required : false,lt : 200,},},
		};
		validator.init(rule);
	},
	initEditor : function (self) {
		var _self = self;
		_self.editor = new baidu.editor.ui.Editor({
				/*initialFrameWidth:760,*/
				initialFrameHeight : 300,
				initialContent : "",
				textarea : 'htmlPart'
			});
		_self.editor.render("myEditor");
	},
	editorSet : function (self, content) {
		var _self = self;
		_self.editor.ready(function () {
			_self.editor.setContent(content, false);
		});
	},
	ajaxFileUpload : function (self, fileId, imageId) {
		var _self = self;
		var url = G_CTX_ROOT + "/v1/news/upload";
		url = adminServer + url.replace('/lecaiadminapi', adminContextPath);
		//console.log(url);
		$.ajaxFileUpload({
			url : url,
			//headers:{'Source' : '101','Token':token},
			data : {
				'Token' : token
			},
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
		var flag = _self.changeEmpty($("#s_status").val());
		var status = flag == false ? 0 : $("#s_status").val();
		var title = $("#s_title").val();
		return '{"status":"' + status
		 + '","title":"' + title
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
				var postData = _self.jsonPostData(_self, start, length, orderby, direction);
				if (!postData) {
					return;
				}
				var actionType = 'GET';
				if($('#type').val() == "list"){
					actionType = "POST";	
				}else if($('#type').val() == "browse"){ 
					actionType = "GET";
					url = _self.apiContextPath + "/webinar/" + $("#webId").val() + "/browse";
					url = url + _self.jsonGetData(_self, start, length, orderby, direction);
				}else if($('#type').val() == "registCount"){ 
					actionType = "GET";
					url = _self.apiContextPath + "/webinar/" + $("#webId").val() + "/registCountDetail";
					url = url + _self.jsonGetData(_self, start, length, orderby, direction);
				}else if($('#type').val() == "liveCount"){ 
					actionType = "GET";
					url = _self.apiContextPath + "/webinar/" + $("#webId").val() + "/1/playMapCountDetail";
					url = url + _self.jsonGetData(_self, start, length, orderby, direction);
				}else if($('#type').val() == "playbackCount"){ 
					actionType = "GET";
					url = _self.apiContextPath + "/webinar/" + $("#webId").val() + "/2/playMapCountDetail";
					url = url + _self.jsonGetData(_self, start, length, orderby, direction);
				}
				
				$.ajaxInvoke({
					type : actionType,
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

//		$.each(setting.aoColumns, function (i, item) {
//			if (item["sTitle"] == "操作") {
//				item["bSortable"] = false;
//			}
//		});

		return setting;
	},
	initTable : function (self) {
		var _self = self;
		if (_self.dataTable) {
			_self.dataTable.fnDraw();
			return;
		}
		var aoColumns = [{
				"sTitle" : "课程标题",
				"mData" : "title",
			}, {
				"sTitle" : "直播时间",
				"mData" : "startTime",
				"render" : function (data) {
					return data.substring(0, data.length - 2);
				}
			}, {
				"sTitle" : "课程讲师",
				"mData" : "teacherIds",
				"render" : function (data) {
					var obj = data.split(";");
					var result = "";
					$.each(obj, function (i, value) {
						if (value != "")
							result += value + " ";
					});
					return result;
				}
			}, {
				"sTitle" : "直播时长",
				"mData" : "duration",
				"render" : function (data) {
					return data + "分钟";
				}
			}, {
				"sTitle" : "课程状态",
				"mData" : "status",
				"render" : function (data) {
					var type = "";
					if (data == 1) type = "已保存";
					if (data == 2) type = "预约中";
					if (data == 3) type = "直播中";
					if (data == 4) type = "已结束";
					if (data == 5) type = "已取消";
					return type;
				}
			}, {
				"sTitle" : "浏览次数",
				"mData" : "browseCount",
				"render" : function (data) {
					return "<a href='javascript:void(0);' class='btn-browse'>" + data + "</a>";
				}
			},{
				"sTitle" : "预约人数",
				"mData" : "registCount",
				"bSortable" : false,
				"render" : function (data) {
					return "<a href='javascript:void(0);' class='btn-registCount'>" + data + "</a>";
				}
			},{
				"sTitle" : "观看直播人数",
				"mData" : "liveCount",
				"bSortable" : false,
				"render" : function (data) {
					return "<a href='javascript:void(0);' class='btn-liveCount'>" + data + "</a>";
				}
			},{
				"sTitle" : "观看回放人数",
				"mData" : "playbackCount",
				"bSortable" : false,
				"render" : function (data) {
					return "<a href='javascript:void(0);' class='btn-playbackCount'>" + data + "</a>";
				}
			}, {
				"sTitle" : "创建时间",
				"mData" : "createTime",
				"render" : function (data) {
					return data.substring(0, data.length - 2);
				}
			}, {
				"sTitle" : "操作",
				"mData" : "",
				"bSortable" : false,
				"sWidth" : "200px",
				"render" : function (data, dis, obj) {
					var editDom = "",publishDom = "",cancelDom = "",startDom = "",endDom = "",
					deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> ";
					editDom = "<span class='btn btn-success btn-xs btn-modify'> 修改 </span> ";
					if (obj.status == 1) {
						publishDom = "<span class='btn btn-primary btn-xs btn-publish'> 发布 </span> ";
					} else {
						cancelDom = "<span class='btn btn-warning btn-xs btn-cancel'> 撤销 </span> ";
						if (obj.status == 2) {
							startDom = "<span class='btn btn-success btn-xs btn-start'> 开始直播 </span> ";
						} else if (obj.status == 3) {
							endDom = "<span class='btn btn-primary btn-xs btn-end'> 结束直播 </span> ";
						} 
					}
					return "<input type='hidden' name='webId' value='" + obj.pid + "'/>" +
					"<input type='hidden' name='webStatus' value='" + obj.status + "'/>" +
					startDom + endDom + editDom + publishDom + cancelDom + deleteDom;
				}
			}
		];
		$("#type").val("list");
		var url = _self.apiContextPath + "/webinar/searchWebinars";
		var setting = _self.tableSetting(_self, aoColumns, url, 'list');
		setting.fnDrawCallback = function() {
			$(".btn-publish").unbind("click").click(function () {
				var webId = $(this).parents("TR").find("input[type=hidden][name=webId]").val();
				var webName = $(this).parents("TR").children('td').eq(0).html();
				var data = {};
				data.status = 2;
				var postData = JSON.stringify(data);
				mConfirm("确定发布【" + webName + "】？", function () {
					$.ajaxInvoke({
						url : _self.apiContextPath + "/webinar/" + webId,
						type : "put",
						data : postData,
						dataType : "json",
						success : function (data) {
							msgBox('success', "发布直播课程成功");
							_self.dataTable.fnDraw();
						},
						error : function (data) {
							msgBox('fail', "发布直播课程失败");
						},
					});
				});
			});

			$(".btn-cancel").unbind("click").click(function () {
				var webId = $(this).parents("TR").find("input[type=hidden][name=webId]").val();
				var webName = $(this).parents("TR").children('td').eq(0).html();
				var data = {};
				data.status = 1;
				var postData = JSON.stringify(data);
				mConfirm("确定撤销【" + webName + "】？", function () {
					$.ajaxInvoke({
						url : _self.apiContextPath + "/webinar/" + webId,
						type : "put",
						data : postData,
						dataType : "json",
						success : function (data) {
							msgBox('success', "撤销直播课程成功");
							_self.dataTable.fnDraw();
						},
						error : function (data) {
							msgBox('fail', "撤销直播课程失败");
						},
					});
				});
			});

			$(".btn-start").unbind("click").click(function () {
				var webId = $(this).parents("TR").find("input[type=hidden][name=webId]").val();
				var webName = $(this).parents("TR").children('td').eq(0).html();
				var data = {};
				data.status = 3;
				var postData = JSON.stringify(data);
				mConfirm("确定开始直播【" + webName + "】？", function () {
					$.ajaxInvoke({
						url : _self.apiContextPath + "/webinar/" + webId,
						type : "put",
						data : postData,
						dataType : "json",
						success : function (data) {
							msgBox('success', "开始直播课程成功");
							_self.dataTable.fnDraw();
						},
						error : function (data) {
							msgBox('fail', "开始直播课程失败");
						},
					});
				});
			});

			$(".btn-end").unbind("click").click(function () {
				var webId = $(this).parents("TR").find("input[type=hidden][name=webId]").val();
				var webName = $(this).parents("TR").children('td').eq(0).html();
				var data = {};
				data.status = 4;
				var postData = JSON.stringify(data);
				mConfirm("确定结束直播【" + webName + "】？", function () {
					$.ajaxInvoke({
						url : _self.apiContextPath + "/webinar/" + webId,
						type : "put",
						data : postData,
						dataType : "json",
						success : function (data) {
							msgBox('success', "结束直播课程成功");
							_self.dataTable.fnDraw();
						},
						error : function (data) {
							msgBox('fail', "结束直播课程失败");
						},
					});
				});
			});

			$(".btn-delete").unbind("click").click(function () {
				var webId = $(this).parents("TR").find("input[type=hidden][name=webId]").val();
				var webName = $(this).parents("TR").children('td').eq(0).html();
				mConfirm("确定删除【" + webName + "】？", function () {
					$.ajaxInvoke({
						url : _self.apiContextPath + "/webinar/" + webId,
						type : "delete",
						dataType : "json",
						success : function (data) {
							msgBox('success', "删除直播课程成功");
							_self.dataTable.fnDraw();
						},
						error : function (data) {
							msgBox('fail', "删除直播课程失败");
						},
					});
				});
			});

			$(".btn-playback").unbind("click").click(function () {
				$("#backPid").val($(this).parents("TR").find("input[type=hidden][name=webId]").val());
				$("#backTitle").val($(this).parents("TR").children('td').eq(0).html());
				$("#backTeacherIds").val($(this).parents("TR").children('td').eq(2).html());
				$("#backDuration").val($(this).parents("TR").children('td').eq(3).html());
				$("#backStartTime").val($(this).parents("TR").children('td').eq(1).html());
				$("#myModal1").modal("show");
			});

			$(".btn-modify").unbind("click").click(function () {
				validator.clearForm();
				$(".btn-updateSave").attr("disabled", false);
				var webId = $(this).parents("TR").find("input[type=hidden][name=webId]").val();
				$.ajaxInvoke({
					url : _self.apiContextPath + "/webinar/" + webId,
					type : "get",
					dataType : "json",
					success : function (data) {
						$("#pid").val(data.pid);
						$("#title").val(data.title);
						$("#duration").val(data.duration);
						var time = data.startTime;
						$("#startTime").val(time.substring(0, time.length - 2));
						$("#teacherIds").val(data.teacherIds);
						_self.editorSet(_self, data.description);
						$("#location").val(data.location);
						$("#liveurl").val(data.liveurl);
						$("#playbackurl").val(data.playbackurl);
						$("#postimage").val(data.poster);
						$("#logoimage").val(data.logoUrl);
						$("#postimg").attr({
							"src" : data.poster,
							"alt" : data.poster,
							"title" : "海报图片"
						});
						$("#logoimg").attr({
							"src" : data.logoUrl,
							"alt" : data.logoUrl,
							"title" : "封面图片"
						});
						$("#status").val(data.status);
						$("#myModalLabel").html("修改直播课程");
						$(".btn-save").hide();
						$(".btn-updateSave").show();
						$(".logofile").hide();
						$(".logodiv").show();
						$(".postfile").hide();
						$(".postdiv").show();
						$("#myModal").modal("show");
					},
				});
			});
			
			$(".btn-browse").unbind("click").click(function () {
				var webId = $(this).parents("TR").find("input[type=hidden][name=webId]").val();
				if ($('#webId').val() != webId) $('#webId').val(webId);
				if ($('#type').val() != type) $('#type').val("browse");
				_self.initBrowseTable( _self, webId);
				$("#browseModal").modal("show");
			});
			
			$(".btn-registCount").unbind("click").click(function () {
				var webId = $(this).parents("TR").find("input[type=hidden][name=webId]").val();
				if ($('#webId').val() != webId) $('#webId').val(webId);
				if ($('#type').val() != type) $('#type').val("registCount");
				var url = _self.apiContextPath + "/webinar/" + webId + "/registCountDetail";
				_self.initRegistCountTable(_self, webId, url, 'registCount');
				$("#registCountModal").modal("show");
			});
			
			$(".btn-liveCount").unbind("click").click(function () {
				var webId = $(this).parents("TR").find("input[type=hidden][name=webId]").val();
				if ($('#webId').val() != webId) $('#webId').val(webId);
				if ($('#type').val() != type) $('#type').val("liveCount");
				var url = _self.apiContextPath + "/webinar/" + webId + "/1/playMapCountDetail";
				_self.initRegistCountTable(_self, webId, url, 'liveCount');
				$("#registCountModal").modal("show");
			});
			
			$(".btn-playbackCount").unbind("click").click(function () {
				var webId = $(this).parents("TR").find("input[type=hidden][name=webId]").val();
				if ($('#webId').val() != webId) $('#webId').val(webId);
				if ($('#type').val() != type) $('#type').val("playbackCount");
				var url = _self.apiContextPath + "/webinar/" + webId + "/2/playMapCountDetail";
				_self.initRegistCountTable(_self, webId, url, 'playbackCount');
				$("#registCountModal").modal("show");
			});
		};
		_self.dataTable = $('#webinarDatatable').dataTable(setting);
	},
	initBrowseTable : function (self,id) {
		var _self = self;
		if (_self.browseDataTable) {
			_self.browseDataTable.fnDraw();
			return;
		} 
		var aoColumns = [{"sTitle" : "浏览日期","mData" : "days","bSortable" : false,},
		                 {"sTitle" : "浏览次数","mData" : "count","bSortable" : false,}];
		var url = _self.apiContextPath + "/webinar/" + id + "/browse";
		var setting = _self.tableSetting(_self, aoColumns, url, 'browse');
		setting.fnDrawCallback = function() {};
		_self.browseDataTable = $('#browseDatatable').dataTable(setting);
	},
	initRegistCountTable : function (self,id,url) {
		var _self = self;
		if (_self.registCountDataTable) {
			_self.registCountDataTable.fnDraw();
			return;
		} 
		var aoColumns = [{
				"sTitle" : "昵称",
				"mData" : "nickName",
				"bSortable" : false,
			}, {
				"sTitle" : "手机/邮箱",
				"bSortable" : false,
				"render" : function (data, dis, obj) {
					var mobile = obj.mobile;
					var email = obj.email;
					if(mobile != "" && email == ""){
						return mobile ;
					} else if(mobile == "" && email != ""){
						return email;
					} else {
						return mobile + "/" + email;
					}
				}
			}, {
				"sTitle" : "所属企业数",
				"bSortable" : false,
				"render" : function (data, dis, obj) {
					return '<a href="javascript:void(0);" data-toggle="tooltip" data-content="'+ obj.orgName.replace(/;/g,'<br>') +'" rel="popover" data-original-title="<strong>所属企业名称</strong>">'+ obj.orgCount +'</a>';
				}
			}, {
				"sTitle" : "注册时间",
				"mData" : "registTime",
				"bSortable" : false,
				"render" : function (data) {
					return data.substring(0, data.length - 2);
				}
			}, {
				"sTitle" : "点击时间",
				"mData" : "createTime",
				"bSortable" : true,
				"render" : function (data) {
					return data.substring(0, data.length - 2);
				}
			},
		];
		var setting = _self.tableSetting(_self, aoColumns, url, type);
		setting.fnDrawCallback = function() {
			$('[data-toggle="tooltip"]').popover({
				placement:'right',trigger:"hover",html:true,container:"#registCountModal",
			});
		};
		setting.aaSorting=[[4, "desc"]];
		_self.registCountDataTable = $('#registCountDataTable').dataTable(setting);
	},
	
}

$(document).ready(webinar.init());
