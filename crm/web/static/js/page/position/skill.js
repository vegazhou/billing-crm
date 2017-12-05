var skill = {
	apiContextPath : '/adminapi/v1',
	lg : function (msg) {},
	type : 'skill',
	dataTable : null,
	init : function () {
		var _self = this;
		_self.initTable(_self);
		
		_self.industryCascade(_self, _self.apiContextPath + "/industries/0/subindustries", "addIndustry1");
		
		$("#addIndustry1").change(function () {
			var indVal = $("#addIndustry1").val();
			if ($.trim(indVal) == "")
				indVal = "-1";
			$("#functions option").remove();
			var url = _self.apiContextPath + "/industries/" + indVal + "/subindustries";
			_self.industryCascade(_self, url, "addIndustry2");
		});
		
		$("#addIndustry2").change(function () {
			var indVal = $("#addIndustry2").val();
			$("#functions option").remove();
			if (indVal == "")
				return false;
			var data = {};
			data.limit = 50;
			var postData = JSON.stringify(data);
			$.ajaxInvoke({
				url : _self.apiContextPath + "/industries/" + indVal + "/functions",
				type : "get",
				dataType : "json",
				data : postData,
				success :
				function (data) {
					if (data != null) {
						for (var d in data.datas) {
							$("#functions").append("<option id='" + data.datas[d].id + "' value='" + data.datas[d].id + "'>" + data.datas[d].name + "</option>");
						}
						$(".select-sysfunction").find("option").each(function () {
							var tmpId = $(this).val();
							$(".select-function").find("option").each(function () {
								if (tmpId == $(this).val()) {
									$("#" + tmpId).remove();
									return false;
								}
							});
						});
					}
				}
			});
		});
		
		$("#addmodule").unbind("click").click(function () {
			$(".select-sysfunction").find("option:selected").each(function () {
				$(".select-function").append("<option value='" + $(this).val() + "'>" + $(this).text() + "</option>");
			});
			$(".select-sysfunction").find("option:selected").remove();
		});
		
		$("#removemodule").unbind("click").click(function () {
			$(".select-function").find("option:selected").each(function () {
				$(".select-sysfunction").append("<option value='" + $(this).val() + "'>" + $(this).text() + "</option>");
			});
			$(".select-function").find("option:selected").remove();
		});
		
		$(".btn-add").unbind("click").click(function () {
			$(".btn-save").attr("disabled", false);
			$(".btn-updateSave").hide();
			$(".btn-save").show();
			$("input:radio").attr('disabled', false);
			$("#test1").prop("checked", true);
			$("#myModalLabel").html("添加能力");
			$("#employeeScale").val("");
			$("#addIndustry1").val("");
			$("#addIndustry2 option:not(:first)").remove();
			$("#functions option").remove();
			$(".select-function option").remove();
			validator.clearForm();
			$("#myModal").modal("show");
		});
		
		$(".btn-search").unbind("click").click(function () {
			_self.dataTable.fnDraw();
		});
		$(".btn-save").unbind("click").click(function () {
			$(".btn-save").attr("disabled", true);
			if (!validator.validate()) {
				$(".btn-save").attr("disabled", false);
				return false;
			}
			var postURL = _self.apiContextPath + "/functions/0/positions/0/skills";
			var behavior1 = {};
			var behavior2 = {};
			var behavior3 = {};
			behavior1.name = $.trim($('#behavior1').val()); ;
			behavior1.description = $.trim($('#behavior1Des').val());
			behavior1.level = 1;
			behavior2.name = $.trim($('#behavior2').val()); ;
			behavior2.description = $.trim($('#behavior2Des').val());
			behavior2.level = 2;
			behavior3.name = $.trim($('#behavior3').val()); ;
			behavior3.description = $.trim($('#behavior3Des').val());
			behavior3.level = 3;
			var behaviors = [behavior1, behavior2, behavior3];
			var data = {};
			var functions = [];
			$(".select-function").find("option").each(function () {
				functions.push($(this).val());
			});
			data.name = $.trim($('#name').val());
			data.type = parseInt($("input:radio[name='type']:checked").val());
			data.employeeScale = $.trim($('#employeeScale').val());
			data.description = $.trim($('#des').val());
			data.functionId = functions;
			data.behaviors = behaviors;
			var postData = JSON.stringify(data);
			$.ajaxInvoke({
				url : postURL,
				data : postData,
				type : "post",
				contentType : "application/json",
				error :
				function (data) {
					if (data.status == "201") {
						msgBox('success', "新增能力成功");
						_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
					} else {
						msgBox('fail', "新增能力失败");
					}
					$("#myModal").modal("hide");
				},
				dataType : "json"
			});
		});
		$(".btn-updateSave").unbind("click").click(function () {
			$(".btn-updateSave").attr("disabled", true);
			if (!validator.validate()) {
				$(".btn-updateSave").attr("disabled", false);
				return false;
			}
			var postURL = _self.apiContextPath + "/functions/0/positions/0/skills/" + $("#u_skillId").val(); ;
			var behavior1 = {};
			var behavior2 = {};
			var behavior3 = {};
			behavior1.name = $.trim($('#behavior1').val()); ;
			behavior1.description = $.trim($('#behavior1Des').val());
			behavior1.level = 1;
			behavior2.name = $.trim($('#behavior2').val()); ;
			behavior2.description = $.trim($('#behavior2Des').val());
			behavior2.level = 2;
			behavior3.name = $.trim($('#behavior3').val()); ;
			behavior3.description = $.trim($('#behavior3Des').val());
			behavior3.level = 3;
			var behaviors = [behavior1, behavior2, behavior3];
			var data = {};
			var functions = [];
			$(".select-function").find("option").each(function () {
				functions.push($(this).val());
			});
			data.skillId = $.trim($("#u_skillId").val());
			data.name = $.trim($('#name').val());
			data.type = parseInt($("input:radio[name='type']:checked").val());
			data.employeeScale = $.trim($('#employeeScale').val());
			data.description = $.trim($('#des').val());
			data.functionId = functions;
			data.behaviors = behaviors;
			var postData = JSON.stringify(data);
			$.ajaxInvoke({
				url : postURL,
				data : postData,
				type : "put",
				contentType : "application/json",
				success : function (data) {
					msgBox('success', "修改能力成功");
					$("#myModal").modal("hide");
					_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
				},
				error : function (data) {
					msgBox('fail', "修改能力失败");
					$("#myModal").modal("hide");
				},
				dataType : "json"
			});
		});
		var rule = {name:{name:"能力名称",method:{required:true,lt:20,},},
			des:{name:"能力描述",method:{required:true,lt:200,},},
			behavior1:{name:"一级行为表现名称",method:{required:true,lt:20,},},
			behavior2:{name:"二级行为表现名称",method:{required:true,lt:20,},},
			behavior3:{name:"三级行为表现名称",method:{required:true,lt:20,},},
			behavior1Des:{name:"一级行为表现描述",method:{required:true,lt:200,},},
			behavior2Des:{name:"二级行为表现描述",method:{required:true,lt:200,},},
			behavior3Des:{name:"三级行为表现描述",method:{required:true,lt:200,},},
		};
		validator.init(rule);
	},
	changeEmpty : function (val) {
		var flag = val != null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g, "") != "";
		return flag;
	},
	jsonPostData : function (self, start, limit, orderby, direction) {
		var _self = self;
		return '{"skillName":"' + $("#s_skillName").val()
		 + '","employeeScale":"' + $("#s_employeeScale").val()
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
			success :function (data) {
				if (data != null) {
					for (var d in data.datas) {
						indObj.append("<option value='" + data.datas[d].id + "'>" + data.datas[d].name + "</option>");
					}
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
					url : _self.apiContextPath + "/functions/0/positions/searchSkills",
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
					$(".select-function option").remove();
					$("#addIndustry1").val("");
					$("#addIndustry2 option:not(:first)").remove();
					$("#functions option").remove();
					$("#myModalLabel").html("修改能力");
					validator.clearForm();
					var skillId = $(this).parents("TR").find("input[type=hidden][name=skillId]").val();
					$("#u_skillId").val(skillId);
					$.ajaxInvoke({
						url : _self.apiContextPath + "/functions/0/positions/0/skills/" + skillId,
						type : "get",
						contentType : "application/json",
						success : function (data) {
							$("#myModal").modal("show");
							$("#name").val(data.name);
							$("#des").val(data.description);
							$("input:radio").removeAttr('checked');
							if (data.type == "1") {
								$("#test1").prop("checked", true);
							} else {
								$("#test2").prop("checked", true);
							}
							$("input:radio").attr('disabled', true);
							$("#employeeScale").find("option").each(function () {
								if (data.employeeScale == $(this).val()) {
									$(this).attr("selected", true);
									return false;
								}
								$(this).attr("selected", false);
							});
							$.each(data.behaviors, function (i, obj) {
								$("#behavior" + obj.level).val(obj.name);
								$("#behavior" + obj.level + "Des").val(obj.description);
							});
							$.each(data.functions, function (i, obj) {
								$(".select-function").append("<option value='" + obj.id + "'>" + obj.name + "</option>");
							});
						},
						dataType : "json"
					});
				});
				$(".btn-detail").unbind("click").click(function () {
					$("#functionDiv").css({
						width : "590px",
						height : "460px",
						margin : "0px auto",
						overflow : "auto"
					});
					var functionId = $(this).parents("TR").find("input[type=hidden][name=functionId]").val();
					var positionId = $(this).parents("TR").find("input[type=hidden][name=positionId]").val();
					$.ajaxInvoke({
						url : _self.apiContextPath + "/industries/" + industryId + "/functions/" + functionId,
						type : "get",
						contentType : "application/json",
						success : function (data) {
							$("#updateFunctionName").val(data.name);
							$("#updateFunctionEname").val(data.eName);
							$.each(data.industries, function (i, obj) {
								var str = "<tr>";
								str += "<td>" + (i + 1);
								$.each(obj, function (key, val) {
									if (key == "id") {
										str += "<input type='hidden' value='" + val + "'></td>";
									}
									if (key == "name" || key == "eName") {
										str += "<td><font>" + val + "</font></td>";
									}
								});
								str += "</tr>";
								$("#functionDetail").append(str);
							});
							$("#myModal2").modal("show");
						},
						dataType : "json"
					});
				});
				$(".btn-delete").unbind("click").click(function () {
					var skillId = $(this).parents("TR").find("input[type=hidden][name=skillId]").val();
					var functionId = $(this).parents("TR").find("input[type=hidden][name=functionId]").val();
					var skillName = $(this).parents("TR").children('td').eq(0).html();
					mConfirm("确定删除【" + skillName + "】？", function () {
						$.ajaxInvoke({
							url : _self.apiContextPath + "/functions/" + functionId + "/positions/0/skills/" + skillId,
							type : "delete",
							contentType : "application/json",
							success : function (data) {
								msgBox('success', "删除能力成功");
								_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
							},
							error : function (data) {
								msgBox('fail', "删除能力失败");
							},
						});
					});
				});
			},
			"fnInitComplete" : function () {},
			"sAjaxDataProp" : "datas",
			"aoColumns" : [{
					"sTitle" : "能力名称",
					"mData" : "name",
				}, {
					"sTitle" : "员工规模", 
					"mData" : "employeeScale", 
					"bSortable" : false, 
					"render" : function (data) {
						return (data == null || data == "") ? "未填写" : data;
					}
				}, {
					"sTitle" : "能力类型", 
					"mData" : "type", 
					"render" : function (data) {
						return data == "1" ? "通用能力" : "非通用能力";
					}
				}, {
					"sTitle" : "能力状态",
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
						return "<input type='hidden' name='skillId' value='" + obj.id + "'/>" + editDom + deleteDom;
					}
				},
			],
		};
		commonSetting4DataTable(setting);
		_self.dataTable = $('#datatable').dataTable(setting);
	},
}
$(document).ready(skill.init());