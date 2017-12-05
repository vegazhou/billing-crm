var subject = {
	apiContextPath : '/adminapi/v1',
	lg : function (msg) {},
	type : 'subject',
	reportData : null,
	dataTable : null,
	editor : null,
	init : function () {
		var _self = this;
		_self.initTable(_self);
		_self.initEditor(_self);
		$('.btn-search').unbind("click").click(function () {
			_self.dataTable.fnDraw();
		});
		$(".btn-add").unbind("click").click(function () {
			validator.clearForm();
			_self.inputDisable(_self, false);
			$(".btn-save").show();
			$(".btn-updateSave").hide();
			$(".btn-save").attr("disabled", false);
			$("#myModalLabel").html("添加专题");
			$(".staticUrlRow").hide();
			setTimeout(function () {
				_self.editorSet(_self, "");
				_self.editorCommand(_self);
			}, 300);
			$("#myModal").modal("show");
		});
		$("#type").unbind("change").change(function () {
			var type = $(this).val();
			if (type == "") {
				_self.editorSet(_self, "");
				return false;
			}
			$.ajaxInvoke({
				url : _self.apiContextPath + "/news/subject/" + type,
				type : "get",
				dataType : "json",
				success : function (data) {
					_self.editorSet(_self, data.content);
					_self.editorCommand(_self);
				},
				error : function (data) {
					_self.editorSet(_self, data.content);
					_self.editorCommand(_self);
				},
			});
		});
		$(".btn-save").unbind("click").click(function () {
			$(".btn-save").attr("disabled", true);
			if (!validator.validate()) {
				$(".btn-save").attr("disabled", false);
				return false;
			}
			var data = {};
			data.title = $.trim($("#title").val());
			data.type = 8;
			data.content = _self.editor.getContent();
			data.link = $.trim($("#type").val());
			var postData = JSON.stringify(data);
			$.ajaxInvoke({
				url : _self.apiContextPath + "/news",
				type : "post",
				data : postData,
				dataType : "json",
				success : function (data) {
					msgBox('success', "添加专题成功");
					$("#myModal").modal("hide");
					_self.dataTable.fnDraw();
				},
				error : function (data) {
					if (data.status == "201") {
						msgBox('success', "添加专题成功");
						$("#myModal").modal("hide");
						_self.dataTable.fnDraw();
					} else {
						msgBox('fail', "添加专题失败");
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
			data.content = _self.editor.getContent();
			var postData = JSON.stringify(data);
			$.ajaxInvoke({
				url : _self.apiContextPath + "/news/" + $("#pid").val(),
				type : "put",
				data : postData,
				dataType : "json",
				success : function (data) {
					msgBox('success', "修改专题成功");
					$("#myModal").modal("hide");
					_self.dataTable.fnDraw();
				},
				error : function (data) {
					msgBox('fail', "修改专题失败");
					$("#myModal").modal("hide");
				},
			});
		});
		var rule = {
			title : {
				name : "标题名称",
				method : {
					required : true,
					lt : 50,
				},
			},
			type : {
				name : "模板类型",
				method : {
					required : true,
				},
			},
		};
		validator.init(rule);
	},
	initEditor : function (self) {
		var _self = self;
		_self.editor = new baidu.editor.ui.Editor({
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
	editorCommand : function (self) {
		var _self = self;
		_self.editor.ready(function () {
			var flag = _self.editor.queryCommandState("source");
			if (flag == 0) {
				_self.editor.execCommand("source");
			} else {
				_self.editor.execCommand("source");
				_self.editor.execCommand("source");
			}
		});
	},
	inputDisable : function (self, flag) {
		var _self = self;
		if (flag) {
			$("#title").prop("disabled", true);
			$("#type").prop("disabled", true);
			$("#staticUrl").prop("disabled", true);
		} else {
			$("#title").prop("disabled", false);
			$("#type").prop("disabled", false);
			$("#staticUrl").prop("disabled", false);
		}
	},
	ajaxFileUpload : function (self, fileId, imageId) {
		var _self = self;
		var url = G_CTX_ROOT + "/v1/news/upload";
		url = adminServer + url.replace('/lecaiadminapi', adminContextPath);
		$.ajaxFileUpload({
			url : url,
			data : {
				'Token' : token
			},
			secureuri : false,
			fileElementId : fileId,
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
		var title = $("#s_title").val();
		return '{"status":' + status
		 + ',"type":' + 8
		 + ',"module":' + 1
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
				if (type == "POST") {
					var postData = _self.jsonPostData(_self, start, length, orderby, direction);
					if (!postData) {
						return;
					}
				} else {
					url = _self.apiContextPath + "/news/" + $("#subjectId").val() + "/browse"; ;
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
						content['sortField'] = orderby;
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
			}, {
				"sTitle" : "专题状态",
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
				"render" : function (data) {
					detailDom = "<span class='btn btn-success btn-xs btn-detail'> 详情 </span> ";
					return detailDom;
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
					return "<a href='javascript:void(0);' class='btn-times'>" + data + "</a>";
				}
			}, {
				"sTitle" : "操作",
				"mData" : "",
				"bSortable" : false,
				"sWidth" : "200px",
				"render" : function (data, dis, obj) {
					var editDom = "",
					publishDom = "",
					cancelDom = "";
					deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> ";
					if (obj.status == 1) {
						editDom = "<span class='btn btn-success btn-xs btn-modify'> 修改 </span> ";
						publishDom = "<span class='btn btn-primary btn-xs btn-publish'> 发布 </span> ";
					} else {
						cancelDom = "<span class='btn btn-warning btn-xs btn-cancel'> 撤销 </span> ";
					}
					return "<input type='hidden' name='subjectId' value='" + obj.pid + "'/>" + "<input type='hidden' name='subjectStatus' value='" + obj.status + "'/>" +
					editDom + publishDom + cancelDom + deleteDom;
				}
			}
		];
		var url = _self.apiContextPath + "/news/searchNews";
		var setting = _self.tableSetting(_self, aoColumns, url, 'POST');
		setting.fnDrawCallback = function () {
			$(".btn-publish").unbind("click").click(function () {
				var subjectId = $(this).parents("TR").find("input[type=hidden][name=subjectId]").val();
				var subjectName = $(this).parents("TR").children('td').eq(0).html();
				var data = {};
				data.status = 2;
				var postData = JSON.stringify(data);
				mConfirm("确定发布【" + subjectName + "】？", function () {
					$.ajaxInvoke({
						url : _self.apiContextPath + "/news/" + subjectId,
						type : "put",
						data : postData,
						dataType : "json",
						success : function (data) {
							msgBox('success', "发布专题成功");
							_self.dataTable.fnDraw();
						},
						error : function (data) {
							msgBox('fail', "发布专题失败");
						},
					});
				});
			});
			$(".btn-cancel").unbind("click").click(function () {
				var subjectId = $(this).parents("TR").find("input[type=hidden][name=subjectId]").val();
				var subjectName = $(this).parents("TR").children('td').eq(0).html();
				var data = {};
				data.status = 1;
				var postData = JSON.stringify(data);
				mConfirm("确定撤销【" + subjectName + "】？", function () {
					$.ajaxInvoke({
						url : _self.apiContextPath + "/news/" + subjectId,
						type : "put",
						data : postData,
						dataType : "json",
						success : function (data) {
							msgBox('success', "撤销专题成功");
							_self.dataTable.fnDraw();
						},
						error : function (data) {
							msgBox('fail', "撤销专题失败");
						},
					});
				});
			});
			$(".btn-delete").unbind("click").click(function () {
				var subjectId = $(this).parents("TR").find("input[type=hidden][name=subjectId]").val();
				var subjectName = $(this).parents("TR").children('td').eq(0).html();
				mConfirm("确定删除【" + subjectName + "】？", function () {
					$.ajaxInvoke({
						url : _self.apiContextPath + "/news/" + subjectId,
						type : "delete",
						dataType : "json",
						success : function (data) {
							msgBox('success', "删除专题成功");
							_self.dataTable.fnDraw();
						},
						error : function (data) {
							msgBox('fail', "删除专题失败");
						},
					});
				});
			});
			$(".btn-modify").unbind("click").click(function () {
				validator.clearForm();
				$(".btn-updateSave").attr("disabled", false);
				$("#myModalLabel").html("修改专题");
				$(".btn-save").hide();
				$(".btn-updateSave").show();
				$(".staticUrlRow").show();
				_self.inputDisable(_self, false);
				var subjectId = $(this).parents("TR").find("input[type=hidden][name=subjectId]").val();
				$.ajaxInvoke({
					url : _self.apiContextPath + "/news/" + subjectId,
					type : "get",
					dataType : "json",
					success : function (data) {
						$("#type").find("option").each(function () {
							if (data.link == $(this).val()) {
								$(this).prop("selected", true);
								return false;
							}
						});
						$("#pid").val(data.pid);
						$("#title").val(data.title);
						$("#staticUrl").val(data.staticUrl);
						_self.editorSet(_self, data.content);
						setTimeout(function () {
							_self.editorCommand(_self);
						}, 300);
						$("#myModal").modal("show");
					},
				});
			});
			$(".btn-detail").unbind("click").click(function () {
				$("#myModalLabel").html("查看专题");
				$(".btn-save").hide();
				$(".btn-updateSave").hide();
				$(".staticUrlRow").show();
				var subjectId = $(this).parents("TR").find("input[type=hidden][name=subjectId]").val();
				$.ajaxInvoke({
					url : _self.apiContextPath + "/news/" + subjectId,
					type : "get",
					dataType : "json",
					success : function (data) {
						$("#type").find("option").each(function () {
							if (data.link == $(this).val()) {
								$(this).prop("selected", true);
								return false;
							}
						});
						$("#pid").val(data.pid);
						$("#title").val(data.title);
						$("#staticUrl").val(data.staticUrl);
						_self.editorSet(_self, data.content);
						setTimeout(function () {
							_self.editorCommand(_self);
						}, 300);
						_self.inputDisable(_self, true);
						$("#myModal").modal("show");
					},
				});
			});
			$(".btn-times").unbind("click").click(function () {
				var subjectId = $(this).parents("TR").find("input[type=hidden][name=subjectId]").val();
				if ($('#subjectId').val() != subjectId) {
					$('#subjectId').val(subjectId);
				}
				_self.initBrowseTable(subjectId, _self);
				$("#browseModal").modal("show");
			});
		};
		_self.dataTable = $('#subjectDatatable').dataTable(setting);
	},
	initBrowseTable : function (id, self) {
		var _self = self;
		if (_self.browseDataTable) {
			_self.browseDataTable.fnDraw();
			return;
		}
		var aoColumns = [{"sTitle" : "浏览日期","mData" : "days","bSortable" : false,},
		                 {"sTitle" : "浏览次数","mData" : "count","bSortable" : false,}];
		var url = _self.apiContextPath + "/news/" + id + "/browse";
		var setting = _self.tableSetting(_self, aoColumns, url, 'GET');
		setting.fnDrawCallback = function () {};
		_self.browseDataTable = $('#browseDatatable').dataTable(setting);
	},
}
$(document).ready(subject.init());
