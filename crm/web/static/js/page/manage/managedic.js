var dict = {
	apiContextPath : '/adminapi/v1',
	lg : function (msg) {},
	type : 'dict',
	dataTable : null,
	init : function () {
		var _self = this;
		_self.initTable(_self);
		
		$(".btn-search").unbind("click").click(function () {
			_self.dataTable.fnDraw();
		});
		
		$(".btn-add").unbind("click").click(function () {
			validator.clearForm();
			$("#edit_dicid").val("");
			$("#stype").val($("#s_type").val());
			$("#stypename").text($("#s_type").find("option:selected").text());
			$("#myModalLabel").html("字典添加");
			$('#sdicnum').val("");
			$('#sname').val("");
			$('#sdesc').val("");
			$(".btn-save").attr("disabled", false);
			$("#sdicnum").attr("disabled", false);
			$("#myModal").modal("show");
		});
		
		$(".btn-save").unbind("click").click(function () {
			$(".btn-save").attr("disabled", true);
			if (!validator.validate()) {
				$(".btn-save").attr("disabled", false);
				return false;
			}
			if (!_self.validPriority()) {
				$(".btn-save").attr("disabled", false);
				return false;
			}
			var sysdict = {};
			sysdict.dictId = $.trim($('#sdicnum').val());
			sysdict.name = $.trim($('#sname').val());
			sysdict.type = $.trim($('#stype').val());
			sysdict.value = $.trim($('#spriority').val());
			var postData = JSON.stringify(sysdict);
			var postURL = _self.apiContextPath + "/dicts";
			var update = false;
			var calltype = "post";
			if ($("#edit_dicid").val() != "") {
				update = true;
				var calltype = "put";
				postURL = _self.apiContextPath + "/dicts/" + $("#edit_dicid").val();
			}
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				success : function (data) {
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_dicid").val("");
					$("#myModal").modal("hide");
					$('#sdicnum').val("");
					$('#sname').val("");
					$('#sdesc').val("");
					$('#stype').val("");
					$('#spriority').val("");
					$("#stypename").val("");
					msgBox('success', "字典修改成功");
					_self.dataTable.fnDraw();
				},
				error : function (data) {
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_dicid").val("");
					$("#myModal").modal("hide");
					$('#sdicnum').val("");
					$('#sname').val("");
					$('#sdesc').val("");
					$('#stype').val("");
					$('#spriority').val("");
					$("#stypename").val("");
					msgBox('success', "字典添加成功");
					_self.dataTable.fnDraw();
				},
			});
		});
		
		var rule = {
			sname : {
				name : "字典名称",
				method : {
					required : true,
					lt : 50,
				},
			},
			spriority : {
				name : "权重",
				method : {
					required : true,
					isNum : true,
				},
			},
		};
		validator.init(rule);
	},
	validPriority : function () {
		var value = $('#spriority').val();
		var reg = /^(?:0|[1-9][0-9]?|100)$/;
		if (!reg.test($.trim(value))) {
			$('#spriority').nextAll('span').html('<font color=red>请输入0-100间的整数</font>');
			return false;
		}
		$('#spriority').nextAll('span').html("");
		return true;
	},
	changeEmpty : function (val) {
		var flag = val != null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g, "") != "";
		return flag;
	},
	jsonPostData : function (self, start, limit, orderby, direction) {
		var _self = self;
		var status = $("#s_status").val();
		var typeflag = _self.changeEmpty($("#s_type").val());
		var type = typeflag == false ? 0 : $("#s_type").val();
		return '{"status":"' + status
		 + '","type":' + type
		 + ',"orderby":"' + (orderby ? orderby : "createTime")
		 + '","direction":"' + (direction ? direction : "asc")
		 + '","offSet":' + (start ? start : 0)
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
				"sTitle" : "字典编号",
				"mData" : "pid",
				"sWidth" : "30%",
			}, {
				"sTitle" : "字典名称",
				"mData" : "name",
			}, {
				"sTitle" : "状态",
				"mData" : "status",
				"render" : function (data) {
					return data == 1 ? "已启用" : "已禁用";
				}
			}, {
				"sTitle" : "权重",
				"mData" : "value",
			}, {
				"sTitle" : "创建时间",
				"mData" : "createTime",
				"render" : function (data) {
					return data.substring(0, data.length - 2);
				}
			}, {
				"sTitle" : "操作",
				"mData" : "pid",
				"bSortable" : false,
				"sWidth" : "220px",
				"render" : function (data, dis, obj) {
					var editDom = "",enableDom = "",deleteDom = "";
					editDom = "<span class='btn btn-success btn-xs btn-detail'> 修改 </span> ";
					deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span>";
					enableDom = obj.status == "1" ? "<span class='btn btn-danger btn-xs btn-active'> 禁用 </span> " : "<span class='btn btn-warning btn-xs btn-active'> 启用  </span> ";
					return "<input type='hidden' name='dicId' value='" + obj.pid + "'/>" +
					"<input type='hidden' name='dictStatus' value='" + obj.status + "'/>" + 
					editDom + enableDom + deleteDom ;
				}
			}
		];
		var url = _self.apiContextPath + "/dicts/searchDicts";
		var setting = _self.tableSetting(_self, aoColumns, url, 'POST');
		setting.fnDrawCallback = function () {
			$(".btn-detail").unbind("click").click(function () {
				var dicId = $(this).parents("TR").find("input[type=hidden][name=dicId]").val();
				var dicName = $(this).parents("TR").children('td').eq(1).html();
				var spriority = $(this).parents("TR").children('td').eq(3).html();
				$("#edit_dicid").val(dicId);
				$("#stype").val($("#s_type").val());
				$("#stypename").text($("#s_type").find("option:selected").text());
				$("#sdicnum").val(dicId);
				$("#sname").val(dicName);
				$("#spriority").val(spriority);
				$("#myModalLabel").html("字典修改");
				$(".btn-save").attr("disabled", false);
				$("#sdicnum").attr("disabled", true);
				$("#myModal").modal("show");
			});
			$(".btn-active").unbind("click").click(function () {
				var dicId = $(this).parents("TR").find("input[type=hidden][name=dicId]").val();
				var dicName = $(this).parents("TR").children('td').eq(1).html();
				var status = $(this).parents("TR").find("input[type=hidden][name=dictStatus]").val();
				var msg = status == "0" ? "启用" : "禁用";
				var sysdict = {};
				sysdict.status = status == "0" ? 1 : 0;
				var postData = JSON.stringify(sysdict);
				mConfirm("确定" + msg + "【" + dicName + "】？", function () {
					$.ajaxInvoke({
						url : _self.apiContextPath + "/dicts/" + dicId,
						type : "put",
						data : postData,
						success : function (data) {
							msgBox("success", msg + "字典成功");
							_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
						},
						error : function (data) {
							msgBox('fail', msg + "字典成功");
						},
					});
				});
			});
			$(".btn-delete").unbind("click").click(function () {
				var dicId = $(this).parents("TR").find("input[type=hidden][name=dicId]").val();
				var dicName = $(this).parents("TR").children('td').eq(1).html();
				mConfirm("确定删除【" + dicName + "】？", function () {
					var checkeds = [];
					checkeds.push(dicId);
					$.ajaxInvoke({
						url : _self.apiContextPath + "/dicts/" + dicId,
						type : "delete",
						success : function (data) {
							msgBox("success", "删除字典成功");
							_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
						},
						error : function (data) {
							msgBox('fail', "删除字典失败");
						},
					});
				});
			});
		};
		setting.bDestroy = true;
		_self.dataTable = $('#dictsDatatable').dataTable(setting);
	},
}
$(document).ready(dict.init());