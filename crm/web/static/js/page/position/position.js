var position = {
	apiContextPath : '/adminapi/v1',
	lg : function (msg) {},
	type : 'position',
	dataTable : null,
	init : function () {
		var _self = this;
		_self.initTable(_self);
		
		_self.industryCascade(_self, _self.apiContextPath + "/industries/0/subindustries", "industry1");
		_self.industryCascade(_self, _self.apiContextPath + "/orgs/product/getallSkills", "skillId1");
		
		$("#addmodule").unbind("click").click(function () {
			$(".select-sysbehavior").find("option:selected").each(function () {
				$(".select-behavior").append("<option value='" + $(this).val() + "'>" + $(this).text() + "</option>");
			});
			$(".select-sysbehavior").find("option:selected").remove();
		});
		$("#removemodule").unbind("click").click(function () {
			$(".select-behavior").find("option:selected").each(function () {
				$(".select-sysbehavior").append("<option value='" + $(this).val() + "'>" + $(this).text() + "</option>");
			});
			$(".select-behavior").find("option:selected").remove();
		});
		$("#industry1").change(function () {
			var indVal = $("#industry1").val();
			if ($.trim(indVal) == "") indVal = "-1";
			$("#functionId option:not(:first)").remove();
			$("#skillId option:not(:first)").remove();
			$("#behaviors option").remove();
			var url = _self.apiContextPath + "/industries/" + indVal + "/subindustries";
			_self.industryCascade(_self,url, "industry2");
		});
		$("#industry2").change(function () {
			var indVal = $("#industry2").val();
			$("#functionId option:not(:first)").remove();
			$("#skillId option:not(:first)").remove();
			$("#behaviors option").remove();
			if (indVal == "") return false;
			var data = {};
			data.limit = 50;
			var postData = JSON.stringify(data);
			$.ajaxInvoke({
				url : _self.apiContextPath + "/industries/" + indVal + "/functions",
				type : "get",
				dataType : "json",
				data : postData,
				success : function (data) {
					if (data != null) {
						for (var d in data.datas) {
							$("#functionId").append("<option value='" + data.datas[d].id + "'>" + data.datas[d].name + "</option>");
						}
					}
				}
			});
		});
		$("#functionId").change(function () {
			var val = $("#functionId").val();
			$("#skillId option:not(:first)").remove();
			$("#behaviors option").remove();
			if (val == "") return false;
			$.ajaxInvoke({
				url : _self.apiContextPath + "/functions/" + val + "/positions/0/skills",
				type : "get",
				dataType : "json",
				success : function (data) {
					if (data != null) {
						$.each(data, function (i, obj) {
							$("#skillId").append("<option value='" + obj.id + "'>" + obj.name + "</option>");
						});
					}
				}
			});
		});
		$("#skillId").change(function () {
			_self.skillChange(_self,"skillId");
		});
		$("#skillId1").change(function () {
			_self.skillChange(_self,"skillId1");
		});
		$(".btn-add").unbind("click").click(function () {
			$(".btn-save").attr("disabled", false);
			$(".btn-updateSave").hide();
			$(".btn-save").show();
			$(".addPosition").show();
			$(".updatePosition").hide();
			$("#myModalLabel").html("添加岗位");
			$("#skillId option:not(:first)").remove();
			$(".select-sysbehavior option").remove();
			$(".select-behavior option").remove();
			validator.clearForm();
			$("#myModal").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			_self.dataTable.fnDraw();
		});
		$(".btn-save").unbind("click").click(function () {
			// validator.clearForm() ;
			$(".btn-save").attr("disabled", true);
			if (!validator.validate()) {
				$(".btn-save").attr("disabled", false);
				return false;
			}
			var postURL = _self.apiContextPath + "/functions/" + $("#functionId").val() + "/positions";
			var data = {};
			var behaviors = [];
			$(".select-behavior").find("option").each(function () {
				behaviors.push($(this).val());
			});
			data.name = $.trim($('#name').val());
			data.eName = $.trim($('#ename').val());
			data.type = "1";
			data.functionId = $.trim($('#functionId').val());
			data.behaviorId = behaviors;
			var postData = JSON.stringify(data);
			$.ajaxInvoke({
				url : postURL,
				data : postData,
				type : "post",
				contentType : "application/json",
				error : function (data) {
					if (data.status == "201") {
						msgBox('success', "新增岗位成功");
						_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
					} else {
						msgBox('fail', "新增岗位失败");
					}
					$("#myModal").modal("hide");
				},
				dataType : "json"
			});
		});
		$(".btn-updateSave").unbind("click").click(function () {
			$(".btn-updateSave").attr("disabled", true);
			var val = $("#name").val();
			var bRtn = val != null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g, "") != "";
			if (!bRtn) {
				$(".btn-updateSave").attr("disabled", false);
				$("#name").nextAll().filter("span").html("<font color=red>岗位名称不能为空！</font>");
				return false;
			}
			var data = {};
			var behaviors = [];
			$(".select-behavior").find("option").each(function () {
				behaviors.push($(this).val());
			});
			data.name = $.trim($("#name").val());
			data.eName = $.trim($("#ename").val());
			data.behavior = behaviors;
			var postData = JSON.stringify(data);
			var postUrl = _self.apiContextPath + "/functions/" + $("#u_functionId").val() + "/positions/" + $("#u_positionId").val();
			$.ajaxInvoke({
				url : postUrl,
				data : postData,
				type : "put",
				contentType : "application/json",
				success : function (data) {
					msgBox('success', "修改岗位成功");
					$("#myModal").modal("hide");
					_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
				},
				error : function (data) {
					msgBox('fail', "修改岗位失败");
					$("#myModal").modal("hide");
				},
				dataType : "json"
			});
		});
		var rule = {
			name : {
				name : "岗位名称",
				method : {
					required : true,
					lt : 20,
				},
			},
			industry1 : {
				name : "所属一级行业",
				method : {
					required : true,
				},
			},
			industry2 : {
				name : "所属二级行业",
				method : {
					required : true,
				},
			},
			functionId : {
				name : "所属职能",
				method : {
					required : true,
				},
			},
		};
		validator.init(rule);
	},
	changeEmpty : function (val) {
		var flag = val != null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g, "") != "";
		return flag;
	},
	jsonPostData : function (self, start, limit, orderby, direction) {
		var _self = self;
		return '{"functionName":"' + $("#s_functionName").val()
		 + '","positionName":"' + $("#s_positionName").val()
		 + '","orderby":"' + (orderby ? orderby : "createTime")
		 + '","direction":"' + (direction ? direction : "asc")
		 + '","offSet":' + (start ? start : 0)
		 + ',"limit":' + (limit ? limit : 10000) + '}';
	},
	industryCascade : function (self, url, industryId) {
		var _self = self;
		var indObj = $("#" + industryId);
		$("#" + industryId + " option:not(:first)").remove();
		$.ajaxInvoke({
			url : url,
			type : "get",
			dataType : "json",
			data : {},
			success :
			function (data) {
				if (data != null) {
					for (var d in data.datas) {
						indObj.append("<option value='" + data.datas[d].id + "'>" + data.datas[d].name + "</option>");
					}
				}
			}
		});
	},
	skillChange : function(self, id) {
		var _self = self;
		var val = $("#" + id).val();
		$("#behaviors option").remove();
		if (val == "") return false;
		$.ajaxInvoke({
			url : _self.apiContextPath + "/skills/" + val + "/behaviors",
			type : "get",
			dataType : "json",
			success : function (data) {
				if (data != null) {
					for (var d in data.datas) {
						$("#behaviors").append("<option id='" + data.datas[d].beahviorId + "' value='" + data.datas[d].beahviorId + "'>" + data.datas[d].name + "</option>");
					}
					$(".select-sysbehavior").find("option").each(function () {
						var tmpId = $(this).val();
						$(".select-behavior").find("option").each(function () {
							if (tmpId == $(this).val()) {
								$("#" + tmpId).remove();
								return false;
							}
						});
					});
				}
			}
		});
	},
	initTable : function (self) {
		_self = self;
		if (_self.dataTable) {
			_self.dataTable.fnDraw();
			return;
		}
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
				$.ajaxInvoke({
					type : "POST",
					url : _self.apiContextPath + "/functions/searchPositions",
					dataType : "json",
					data : postData,
					success : function (content, status) {
						_self.lg(content);
						content['sEcho'] = sEcho;
						content['iDisplayStart'] = content.paging.offset;
						content['iDisplayLength'] = content.paging.limit;
						content['iTotalRecords'] = content.paging.count;
						content['iTotalDisplayRecords'] = content.paging.count;
						content['sortField'] = orderby; //"createTime";
						content['sortType'] = direction;
						_self.lg(content);
						fnCallback(content);
					},
					error : function (data) {
						_self.lg(data);
					}
				});
			},
			"fnDrawCallback" : function () {
				$(".btn-modify").unbind("click").click(function () {
					$(".btn-updateSave").attr("disabled", false);
					$(".btn-save").hide();
					$(".btn-updateSave").show();
					$(".updatePosition").show();
					$(".addPosition").hide();
					$("#skillId1").val("");
					$(".select-sysbehavior option").remove();
					$(".select-behavior option").remove();
					$("#myModalLabel").html("修改岗位");
					validator.clearForm();
					var functionId = $(this).parents("TR").find("input[type=hidden][name=functionId]").val();
					var positionId = $(this).parents("TR").find("input[type=hidden][name=positionId]").val();
					$("#u_positionId").val(positionId);
					$("#u_functionId").val(functionId);
					$.ajaxInvoke({
						url : _self.apiContextPath + "/functions/" + functionId + "/positions/" + positionId,
						type : "get",
						contentType : "application/json",
						success : function (data) {
							$("#name").val(data.name);
							$("#ename").val(data.eName);
							$.each(data.behaviors, function (i, obj) {
								$(".select-behavior").append("<option value='" + obj.beahviorId + "'>" + obj.name + "</option>");
							});
							$("#myModal").modal("show");
						},
						dataType : "json"
					});
				});
				
				$(".btn-delete").unbind("click").click(function () {
					var functionId = $(this).parents("TR").find("input[type=hidden][name=functionId]").val();
					var positionId = $(this).parents("TR").find("input[type=hidden][name=positionId]").val();
					var type = $(this).parents("TR").find("input[type=hidden][name=type]").val();
					var positionName = $(this).parents("TR").children('td').eq(0).html();
					var txt = "";
					if (type == 1) {
						txt = "<font color='red'>该岗位为通用岗位</font>,确定删除【" + positionName + "】？";
					} else {
						txt = "确定删除【" + positionName + "】？";
					}
					mConfirm(txt, function () {
						$.ajaxInvoke({
							url : _self.apiContextPath + "/functions/" + functionId + "/positions/" + positionId,
							type : "delete",
							contentType : "application/json",
							success : function (data) {
								msgBox('success', "删除岗位成功");
								_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
							},
							error : function (data) {
								msgBox('fail', "删除岗位失败");
							},
						});
					});
				});
			},
			"fnInitComplete" : function () {},
			"sAjaxDataProp" : "datas",
			"aoColumns" : [{
					"sTitle" : "岗位名称", 
					"mData" : "name", 
				}, {
					"sTitle" : "所属职能", 
					"mData" : "functionName",
					"bSortable" : false, 
				}, {
					"sTitle" : "所属行业", 
					"mData" : "industryName",
					"bSortable" : false, 
				}, {
					"sTitle" : "岗位状态",
					"mData" : "status", 
					"render" : function (data) {
						return data == "1" ? "已启用" : "已禁用";
					}
				}, {
					"sTitle" : "创建时间", 
					"mData" : "createTime",
				}, {
					"sTitle" : "操作", 
					"bSortable" : false, 
					"sWidth" : "150px",
					"render" : function (data, dis, obj) {
						editDom = "<span class='btn btn-success btn-xs btn-modify'> 修改 </span> ";
						deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> ";
						return "<input type='hidden' name='functionId' value='" + obj.functionId + "'/>" +
						"<input type='hidden' name='type' value='" + obj.type + "'/>" +
						"<input type='hidden' name='positionId' value='" + obj.id + "'/>" + editDom + deleteDom;
					}
				},
			],
		};
		commonSetting4DataTable(setting);
		_self.dataTable = $('#datatable').dataTable(setting);
	},
}
$(document).ready(position.init());