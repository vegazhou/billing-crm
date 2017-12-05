var functions = {
	apiContextPath : '/adminapi/v1',
	lg : function (msg) {},
	type : 'functions',
	dataTable : null,
	init : function () {
		var _self = this;
		_self.initTable(_self);
		
		_self.industryCascade(_self, _self.apiContextPath + "/industries/0/subindustries", "s_industry1");
		_self.industryCascade(_self, _self.apiContextPath + "/industries/0/subindustries", "addIndustry1");
		
		$("#s_industry1").change(function () {
			var indVal = $("#s_industry1").val();
			var url = _self.apiContextPath + "/industries/" + indVal + "/subindustries";
			_self.industryCascade(_self, url, "s_industry2");
		});
		$("#s_industry2").change(function () {
			$("input[name='s_industryId']").val($("#s_industry2").val());
		});
		$("#addIndustry1").change(function () {
			var indVal = $("#addIndustry1").val();
			var url = _self.apiContextPath + "/industries/" + indVal + "/subindustries";
			_self.industryCascade(_self, url, "addIndustry2");
		});
		$(".btn-search").unbind("click").click(function () {
			var indVal = $("#s_industry2").val();
			if ($("#s_industry1").val() != "-1" && indVal == "") {
				mAlert("请选择行业信息后再进行查询");
				return;
			}
			_self.dataTable.fnDraw();
		});
		
		$(".btn-updateSave").unbind("click").click(function () {
			$(".btn-updateSave").attr("disabled", true);
			if (!validator.validate()) {
				$(".btn-updateSave").attr("disabled", false);
				return false;
			}
			var data = {};
			data.name = $.trim($("#updateFunctionName").val());
			data.ename = $.trim($("#updateFunctionEname").val());
			var postData = JSON.stringify(data);
			var postUrl = _self.apiContextPath + "/industries/" + $("#u_industryId").val() + "/functions/" + $("#u_functionId").val();
			$.ajaxInvoke({
				url : postUrl,
				data : postData,
				type : "put",
				contentType : "application/json",
				success : function (data) {
					msgBox('success', "修改职能成功");
					$("#myModal1").modal("hide");
					_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
				},
			});
		});
		var rule = {updateFunctionName:{name:"职能名称",method:{required:true,lt:20,},},
			updateFunctionEname:{name:"职能英文名称",method:{required:true,},},
		};
		validator.init(rule);
	},
	changeEmpty : function (val) {
		var flag = val != null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g, "") != "";
		return flag;
	},
	jsonPostData : function (self, start, limit, orderby, direction) {
		var _self = self;
		var industryId = $("#s_industry2").val();
		var functionName = $("#s_functionName").val();
		var functionEname = $("#s_functionEname").val();
		var functionType2 = $("#s_type2").val();		
		return '{"industryId":"' + industryId
		 + '","functionName":"' + functionName
		 + '","functionEname":"' + functionEname
		 + '","functionType2":"' + functionType2
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
					url : _self.apiContextPath + "/industries/searchFunctions",
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
					validator.clearForm();
					var functionId = $(this).parents("TR").find("input[type=hidden][name=functionId]").val();
					var industryId = $(this).parents("TR").find("input[type=hidden][name=industryId]").val();
					$("#u_industryId").val(industryId);
					$("#u_functionId").val(functionId);
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/industries/" + industryId + "/functions/" + functionId,				
						type : "get",
						contentType : "application/json",
						success : function (data) {
							$("#updateFunctionName").val(data.name);
							$("#updateFunctionEname").val(data.eName);
							data.status == "0" ? $("#updateStatus_2").attr("checked", true) : $("#updateStatus_1").attr("checked", true);
							data.type1 == "1";
							data.type2 == "1" ? $("#updateType2_2").attr("checked", true) : $("#updateType2_1").attr("checked", true);
							$("#myModal1").modal("show");
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
					$("#functionDetail tr").remove();
					var functionId = $(this).parents("TR").find("input[type=hidden][name=functionId]").val();
					var industryId = $(this).parents("TR").find("input[type=hidden][name=industryId]").val();
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/industries/" + industryId + "/functions/" + functionId,
						type : "get",
						contentType : "application/json",
						success : function (data) {
							$("#updateFunctionName").val(data.name);
							$("#updateFunctionEname").val(data.eName);
							var dataList = data.industries;
							if (dataList.length > 0) {
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
							} else {
								$("#functionDetail").append("<tr><td colspan='3'>该职能没有绑定行业信息</td></tr>");
							}
							$("#myModal2").modal("show");
						},
						dataType : "json"
					});
				});
				
			},
			"fnInitComplete" : function () {},
			"sAjaxDataProp" : "datas",
			"aoColumns" : [
				//{
				//	"sTitle": "<input type='checkbox' class='checkbox-checkall' id='' value=''/>",
				//	"mData": "teacherId",
				//	"sClass": "center",
				//	"sWidth":"2%",
				//	"bSortable" : false ,
				//	"render": function(data) {
				//		var datas = data.split(",");
				//		if(datas[1] == "true"){
				//			return "<input type='checkbox' class='item-checkbox' name='box' value='"+datas[0]+"'>";
				//		} else {
				//			return "<input type='checkbox' class='item-checkbox' name='box' disabled value='"+datas[0]+"'>";
				//		}
				//    }
				//},
				{
					"sTitle" : "职能名称", 
					"mData" : "name",  
				}, {
					"sTitle" : "职能英文名称", 
					"mData" : "eName", 
				}, {
					"sTitle" : "所属行业", 
					"mData" : "industryName", 
					"bSortable" : false,
				}, {
					"sTitle" : "职能行业信息", 
					"mData" : "id", 
					"bSortable" : false, 
					"sWidth" : "100px",
					"render" : function (data) {
						teachInfoQueryDom = "<span class='btn btn-success btn-xs btn-detail'> 详情</span>";
						return "<input type='hidden' name='functionId' value='" + data + "'/>" + teachInfoQueryDom;
					}
				}, {
					"sTitle" : "职能类型", 
					"mData" : "type2", 
					"render" : function (data) {
						return data == "1" ? "非通用职能" : "通用职能";
					}
				}, {
					"sTitle" : "创建时间", 
					"mData" : "createTime", 
				}, {
					"sTitle" : "操作", 
					"mData" : "industryId", 
					"bSortable" : false,
					"sWidth" : "150px",
					"render" : function (data) {
						editDom = "<span class='btn btn-success btn-xs btn-modify'> 修改 </span> ";
						return "<input type='hidden' name='industryId' value='" + data + "'/>" + editDom;
					}
				},
			],
		};
		commonSetting4DataTable(setting);
		_self.dataTable = $('#dataTable').dataTable(setting);
	},
}
$(document).ready(functions.init());
