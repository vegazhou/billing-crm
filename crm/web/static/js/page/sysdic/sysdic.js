var dict = {
	apiContextPath : '/adminapi/v1',
	lg : function (msg) {},
	type : 'dict',
	dataTable : null,
	init : function () {
		var _self = this;
		_self.initTable(_self);
		
		$(".btn-add").unbind("click").click(function () {
			validator.clearForm();
			$("#edit_dicid").val("");
			$("#spid").val("");
			$("#stype").val($("#s_type").val());
			$("#stypename").text($("#s_type").find("option:selected").text());
			$("#myModalLabel").html("字典添加");
			$('#sname').val("");
			$(".btn-save").attr("disabled", false);
			$("#spid").attr("disabled", false);
			$('#dictsubtypeDiv').hide();
			if ($("#s_type").find("option:selected").val() == 2) {
				$('#dictsubtypeDiv').show();
				$("#s_dictsubtype").val('');
			}
			$("#myModal").modal("show");
		});
		$("#s_type").unbind("change").change(function () {
			if ($(this).val() == 13) {
				$(".btn-add").hide();
			} else {
				$(".btn-add").show();
			}
		});
		$(".btn-save").unbind("click").click(function () {	
			if (!validator.validate()) {
				return false;
			}
			$(".btn-save").attr("disabled", true);
			var sysdict = {};
			sysdict.dictId = $.trim($('#spid').val());
			sysdict.name = $.trim($('#sname').val());
			sysdict.type = parseInt($.trim($('#s_type').val()));
			if ($("#s_type").children('option:selected').val() == 2) {
				sysdict.subtype = parseInt($.trim($("#s_dictsubtype").find('option:selected').val()));
			}
			var postData = JSON.stringify(sysdict);
			var postURL = _self.apiContextPath + "/dicts";
			var update = false;
			var calltype = "post";
			if ($("#edit_dicid").val() != "") {
				update = true;
				var calltype = "put";
				postURL = _self.apiContextPath + "/dicts/" + $("#edit_dicid").val();
			} else {}
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				success : function (data) {
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_dicid").val("");
					$("#myModal").modal("hide");
					$('#spid').val("");
					$('#sname').val("");
					$('#stype').val("");
					$('#s_dictsubtype').val("");
					$("#stypename").val("");
					msgBox('success', "字典修改成功");
					_self.dataTable.fnDraw();
				},
				error : function (data) {
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_dicid").val("");
					$("#myModal").modal("hide");
					$('#spid').val("");
					$('#sname').val("");
					$('#stype').val("");
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
					lt : 20,
				},
			},
		};
		validator.init(rule);
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
		//var statusflag = _self.changeEmpty($("#s_status").val());
		var status = $("#s_status").val();
		var typeflag = _self.changeEmpty($("#s_type").val());
		var type = typeflag == false ? 0 : $("#s_type").val();
		var name = $("#s_name").val();
		return '{"status":"' + status
		 + '","type":' + type
		 + ',"name":"' + name
		 + '","orderby":"' + (orderby ? orderby : "createTime")
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
				"bSortable" : true,
				"sWidth" : "20%",
			}, {
				"sTitle" : "字典名称",
				"mData" : "name",
				"bSortable" : true,
			}, {
				"sTitle" : "子类型",
				"mData" : "subtype",
				"bSortable" : true,
				"bVisible" : false,
				"render" : function (data) {
					return data == "1" ? "企业渠道" : "用户渠道";
				}
			}, {
				"sTitle" : "状态",
				"mData" : "status",
				"bSortable" : true,
				"render" : function (data) {
					return data == "1" ? "已启用" : "已禁用";
				}
			}, {
				"sTitle" : "创建时间",
				"mData" : "createTime",
				"sWidth" : "20%",
				"render" : function (data) {
					return data.substring(0, data.length - 2);
				}
			}, {
				"sTitle" : "操作",
				"bSortable" : false,
				"sWidth" : "200px",
				"render" : function (data, dis, obj) {
					var editDom = "",
					enableDom = "",
					deleteDom = "";
					if (obj.type == "1" || obj.type == "2" || obj.type == "3" || obj.type == "4") {
						editDom = "<span class='btn btn-success btn-xs btn-detail'> 修改 </span> ";
						enableDom = obj.status == "1" ? "<span class='btn btn-danger btn-xs btn-active'> 禁用 </span> " : "<span class='btn btn-warning btn-xs btn-active'> 启用  </span> ";
					} else if (obj.type == "13") {
						enableDom = obj.value == "" ? "<span class='btn btn-warning btn-xs btn-clear'> 清除 </span> " : "<span class='btn btn-primary btn-xs btn-recover'> 恢复 </span> ";
					}
					if(obj.type != "13") deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span>";
					return "<input type='hidden' name='dicId' value='" + obj.pid + "'/>" +
					"<input type='hidden' name='dictStatus' value='" + obj.status + "'/>" + 
					"<input type='hidden' name='subtype' value='" + obj.subtype + "'/>" + 
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
				$("#stypename").text($("#s_type").find("option:selected").text());
				$("#spid").val(dicId);
				$("#edit_dicid").val(dicId);
				$("#sname").val(dicName);
				$("#myModalLabel").html("字典修改");
				$(".btn-save").attr("disabled", false);
				$("#spid").attr("disabled", true);
				$('#dictsubtypeDiv').hide();
				if ($("#s_type").find("option:selected").val() == 2) {
					$('#dictsubtypeDiv').show();
					$("#s_dictsubtype").val($(this).parents("TR").find("input[name='subtype']").val());
				}
				$("#myModal").modal("show");
			});
			$(".btn-search").unbind("click").click(function () {
				if (($("#s_type").children('option:selected').val() == 2 && !setting.aoColumns[2].bVisible)
					 || ($("#s_type").children('option:selected').val() != 2 && setting.aoColumns[2].bVisible)) {
					if ($("#s_type").children('option:selected').val() == 2) {
						setting.aoColumns[2].bVisible = true;
					} else {
						setting.aoColumns[2].bVisible = false;
					}
					_self.dataTable = $('#dictsDatatable').dataTable(setting);
					$("#datatable_tabletools_filter").html("");
				} else {
					_self.dataTable.fnDraw();
				}
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
			$(".btn-clear").unbind("click").click(function () {
				var dicId = $(this).parents("TR").find("input[type=hidden][name=dicId]").val();
				var dicName = $(this).parents("TR").children('td').eq(1).html();
				mConfirm("确定清除【" + dicName + "】？", function () {
					$.ajaxInvoke({
						url : _self.apiContextPath + "/dicts/" + dicId + "/1",
						type : "get",
						dataType : "json",
						success : function (data) {
							msgBox('success', "清除白名单号码成功");
							_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
						},
						error : function (data) {
							msgBox('fail', "清除白名单号码失败");
						},
					});
				});
			});
			$(".btn-recover").unbind("click").click(function () {
				var dicId = $(this).parents("TR").find("input[type=hidden][name=dicId]").val();
				var dicName = $(this).parents("TR").children('td').eq(1).html();
				mConfirm("确定恢复【" + dicName + "】？", function () {
					$.ajaxInvoke({
						url : _self.apiContextPath + "/dicts/" + dicId + "/2",
						type : "get",
						dataType : "json",
						success : function (data) {
							msgBox('success', "恢复白名单号码成功");
							_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
						},
						error : function (data) {
							msgBox('fail', "恢复白名单号码失败");
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
