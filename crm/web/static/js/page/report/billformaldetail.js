var EETableColumnsUser = [
				     
                  	{
                  		"sTitle": "流水号",
                  		"mData": "sequence",
                  		"bSortable": true,
                  		"sClass": "center",
                  		"render": function (data) {
                  			return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" + htmlencode(data);
                  		}
                  	},
                  	
                  	{
                  		"sTitle": "调整前金额",
                  		"mData": "primalAmount",
                  		"bSortable": true,
                  		"sClass": "center",
                  		"render": function (data) {
                  			return htmlencode(data);
                  		}
                  	},

                  	{
                  		"sTitle": "调整后金额",
                  		"mData": "currentAmount",
                  		"bSortable": true,
                  		"sClass": "center",
                  		"render": function (data) {
                  			return htmlencode(data);
                  		}
                  	},
                  	
                  	{
                  		"sTitle": "操作员",
                  		"mData": "operator",
                  		"bSortable": false,
                  		"sClass": "center",
                  		"render": function (data) {
                  			return htmlencode(data);
                  		}
                  	},
                  	{
                  		"sTitle": "备注",
                  		"mData": "comments",
                  		"bSortable": false,
                  		"sClass": "center",
                  		"render": function (data) {
                  			return htmlencode(data);
                  		}
                  	}
                	];



var Systemuser = {
	dataTable : null,
	options : "",
	row_count : 0,
	orgservicelist:null,
	allorgservicelist:null,
	deletedorgservicelist:null,
	userservicelist:[],
	init : function () {
		$("#searchcol-tab-panel").hide();
		var parameter=window.location.href.split('company=')[1];
		$("#s_sserviceid3").val(decodeURIComponent(parameter.substring(0,parameter.indexOf("&"))));
		$("#s_searchStartTime_STANDSITE3").val(decodeURIComponent(parameter.split('&accountPeriod=')[1]));
		$(".contractName").html(Systemuser.contractName);
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/fbill/querydetail";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
			{
				"sTitle" : "流水号",
				"mData" : "id",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},

			{
				"sTitle" : "客户名称",
				"mData" : "customerName",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
		                     
		    {
				"sTitle" : "帐单周期",
				"mData" : "accountPeriod",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			}, 
			
			{
				"sTitle" : "金额",
				"mData" : "amount",
				"bSortable" : false,
				"sClass" : "right",
				"render" : function (data) {
					return htmlencode(data);
				}
			},

			{
				"sTitle" : "未支付金额",
				"mData" : "unpaidAmount",
				"bSortable" : false,
				"sClass" : "right",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			
			{
				"sTitle" : "费用类型",
				"mData" : "feeType",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if (data == "1") {
						return "合同首付款";
					} else if (data == "10") {
						return "数据会议费用";
					} else if (data == "11") {
						return "电话语音费用";
					} else if (data == "12") {
						return "WebEx存储费用";
					} else if (data == "13") {
						return "溢出费用";
					}else if (data == "21") {
						return "CC首付款";
					}else if (data == "22") {
						return "CC费用";
					}
					return "-";
				}
			},

			{
				"sTitle" : "产品名称",
				"mData" : "productName",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},

			{
				"sTitle" : "归属合同",
				"mData" : "contractName",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data, dis, obj) {
					var link = "/crm/views/order/order.jsp?id=" + obj.contractId + "&name=";
					return "<a href='" + link  + "'>" + htmlencode(data) + "</a>";
				}
			},

			{
				"sTitle" : "操作",
				"mData" : "contractName",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data, dis, obj) {
					var tuneDom = "<span class=\"btn btn-warning btn-xs btn-tune\">调账</span> ";
					var tunelogDom = "<span class=\"btn btn-info btn-xs btn-log\">查看调账记录</span> ";
					var buttons = tuneDom;
					if (obj.tuneCount > 0) {
						buttons = buttons + tunelogDom;
					}

					if (obj.id != "") {
						return buttons + "<input type='hidden' name='sysId' value='" + obj.id + "'/><input type='hidden' name='oldvalue' value='" + obj.amount + "'/>";
					} else {
						return "";
					}
				}
			}
			
			 
			 ];
		setting.aaSorting = [[1, "desc"]];
		setting.bStateSave = false;
		setting.bPaginate = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		Systemuser.initMoney();
	},
	initMoney : function () {
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/billadjustlog/query";
		//Systemuser.dataTable1=null;
		//$('#datatable_tabletools_users').dataTable(null);
		//self.bindLaterEvent();
		setting.aoColumns = EETableColumnsUser;
		setting.aaSorting = [[0, "desc"]];
		setting.bStateSave = false;
		$("#s_sserviceid2").val(0);
		Systemuser.dataTable1 = $('#datatable_tabletools_users').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
	},
	bindButtonEvent : function () {
		console.log("binlding");
		
		$(".btn-tune").unbind("click").click(function () {
			
			validator.clearForm();
			$("#sname").nextAll().filter("span").html("");
			$(".btn-save").attr("disabled", false);
			var parentTR = $(this).parents("TR"); 
			var contractId = parentTR.find("input[type=hidden][name=sysId]").val();
			$("#edit_userid").val(contractId);
			$("#soldvalue").val(parentTR.find("input[type=hidden][name=oldvalue]").val());
			$("#scomment").val("");
			//Systemuser.getContractInfo(contractId);
			$("#myModalLabel").html("调帐");
			
			$(".btn-save").show();
			$("#myModal").modal("show");
		});	
		
		$(".btn-contract").unbind("click").click(function () {
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
			window.location.href = '../order/order.jsp?id='+$(this).parents("TR").find("input[type=hidden][name=sysId]").val()+"&name="+uName;
			
		});
		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});
		
		$("#s_sserviceid").unbind("change").change(function () {
			Systemuser.refresh();
		});
		
		$(".btn-log").unbind("click").click(function () {
			
			
			validator.clearForm();			
			//$("#myModalLabel").html("资金流水");
			//$('#sname').val("").prop("disabled", false);
			$("#s_sserviceid2").val($(this).parents("TR").find("input[type=hidden][name=sysId]").val());
			Systemuser.dataTable1.fnDraw();
			
			$("#myModal1").modal("show");
		});
		
		 $(".btn-save").unbind("click").click(function () {
				
				if (!validator.validate()) {
					return false;
				}
				
			
			
				/*
				$(".select-role").find("option").each(function () {
					modulesArray.push($(this).val());
				});
				if (modulesArray.length == 0) {
					$("#srole").nextAll().filter("span").html("<font color=red >所选模块不能为空！</font>");
					return false;
				}*/
				$(".btn-save").attr("disabled", true);
				
					var business = {};
					business.id = $.trim($('#edit_userid').val());		
					business.amount = $.trim($('#sname').val());	
					business.comments = $.trim($('#scomment').val());
					var postURL = G_CTX_ROOT + "/v1/fbill/tune/"+$.trim($('#edit_userid').val());
					var update = false;
					var calltype = "post";
					
				
				var postData = JSON.stringify(business);
				$.ajaxInvoke({
					url : postURL,
					type : calltype,
					data : postData,
					success : function (data) {
					
						$(".btn-save").attr("disabled", false);
						validator.clearForm();
						$("#edit_userid").val("");
						$("#myModal").modal("hide");
						$('#sname').val("");
						
						msgBox('success', "调帐成功");
						Systemuser.dataTable.fnDraw();
					},
					error : function (data) {
						
						
						$(".btn-save").attr("disabled", false);
						validator.clearForm();
						$("#edit_userid").val("");
						$("#myModal").modal("hide");
						$('#sname').val("");
						
						msgBox('success', "调帐失败");
						Systemuser.dataTable.fnDraw();
					},
				});
			});
		
		 $('#s_searchStartTime_STANDSITE').unbind().bind('click', Systemuser.initDatePicker);

	},


	getContractInfo : function (contractId) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/contract/" + contractId,
			data : "",
			type : "get",
			success : function (data) {
				console.log("----",data);
				$("#sname").val(data.displayName);
			},
		});
	},



	refresh:function(){
		Systemuser.dataTable.fnDraw();
	},

	initDatePicker	: function() {
		
        WdatePicker({
            skin: 'twoer',
            dateFmt: 'yyyyMM'
        });
        return false;
	},
	

	bindLaterEvent:function(){
		console.log("bindinglater");
	}


}
var rule = {
	sname : {
		name : "新支付金额",
		method : {
			required : true,
			isNum:true,
			notnegative:true,
			lt : 100,
		}
		
	},

	scomment : {
		name : "备注",
		method : {
			required : true,
			lt : 100,
		}
	}
	
};
validator.init(rule);
validator.validate();
$(document).ready(Systemuser.init());
