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
		setting.sAjaxSource = G_CTX_ROOT + "/v1/tbill/query";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
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
				"sTitle" : "导出语音详单",
				"mData" : "contractNam1",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data, dis, obj) {
					if (obj.feeType == "11") {
						var viewDom = "<span class=\"btn btn-info btn-xs btn-exportexcel\">导出明细</span> ";

						return viewDom;
					} else{
						return "";
					}
				}
			}
			
			 
			 ];
		setting.aaSorting = [[1, "desc"]];
		setting.bStateSave = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
	},

	bindButtonEvent : function () {
		console.log("binlding");
		
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
		
		
		$(".btn-exportexcel").unbind("click").click(function () {			
			var customerId = $("#s_sserviceid3").val();
			var accountPeriod = $("#s_searchStartTime_STANDSITE3").val();
			//var feeType = $(this).parents("TR").find("input[type=hidden][name=feeType]").val();
			location.href=G_CTX_ROOT + "/v1/billexport/exportpstnexcel/"+customerId+"/"+accountPeriod;		
			

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
			notzero:true,
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
