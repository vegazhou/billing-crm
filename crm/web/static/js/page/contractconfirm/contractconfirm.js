var Systemuser = {
	dataTable : null,
	options : "",
	row_count : 0,
	orgservicelist:null,
	allorgservicelist:null,
	deletedorgservicelist:null,
	userservicelist:[],
	init : function () {
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/contractconfirm/query";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
		    {
				"sTitle" : "合同名称",
				"mData" : "displayName",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return "<input name='contractnameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
				}
			},
			{
				"sTitle" : "分销代理",
				"mData" : "agent",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if (data == null) {
						return "";
					} else {
						return htmlencode(data);
					}
				}
			},
			{
				"sTitle" : "状态",
				"mData" : "status",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if (data == "DRAFT") {
						return "起草中";
					} else if (data == "WAITING_APPROVAL") {
						return "审核中";
					} else if (data == "IN_EFFECT") {
						return "生效中";
					} else if (data == "WAITING_FIN_APPROVAL") {
						return "财务收款确认中";
					} else if (data == "END_OF_LIFE") {
						return "已中止";
					}
					return data;
				}
			},
			
			{
				"sTitle" : "首付款金额",
				"mData" : "firstInstallment",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			{
				"sTitle" : "创建日期",
				"mData" : "draftDate",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},

			 {
				"sTitle" : "操作",
				"mData" : "f6",
				"bSortable" : false,
				"sClass" : "center",
				"sWidth" : "30%",

				"render" : function (data, dis, obj) {		
					var orderDom = "<span class=\"btn btn-info btn-xs btn-contract\">查看订单</span> ";

					var withdrawDom = "<span class=\"btn btn-danger btn-xs btn-withdraw\">退回</span> ";
					 var approveDom = "<span class=\"btn btn-success btn-xs btn-detail\">收款确认</span> ";
					 
					 return orderDom+approveDom +withdrawDom+"<input type='hidden' name='sysId' value='" + obj.pid + "'/><input type='hidden' name='oldvalue' value='" + obj.firstInstallment + "'/>";
				 }
			}, ];
		setting.aaSorting = [[3, "desc"]];
		setting.bStateSave = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
	},
	bindButtonEvent : function () {
		console.log("binlding");
		$(".btn-init").unbind("click").click(function () {	
			var target = $(this).parents("TR");
			var userid = target.find("input[type=hidden][name=sysId]").val();
			var tds = $(target).children('td');
			var uName = $(tds[0]).find('a').html();
			mConfirm("确定初始化用户【" + uName + "】的密码？", function () {
				$.ajaxInvoke({
					url : "systemuser/initPwd",
					data : {
						id : userid
					},
					success : function (data) {
						if (data && data.msg) {
							data.msg = data.msg.replace('{0}', uName);
						}
						msgBox(data.state, data.msg);
					},
					error : "操作失败",
				});
			});
		});


		$(".btn-detail").unbind("click").click(function () {		
			validator.clearForm();
			$("#sname").nextAll().filter("span").html("");
			$(".btn-save").attr("disabled", false);
			var parentTR = $(this).parents("TR"); 
			var contractId = parentTR.find("input[type=hidden][name=sysId]").val();
			var oldvalue = parentTR.find("input[type=hidden][name=oldvalue]").val();
			$("#edit_userid").val(contractId);	
			$("#oldvalue").val(oldvalue);
			$("#myModalLabel").html("首付款收款");
			$(".btn-save").show();
			Systemuser.getContractWithdraw(contractId);
			$(".shade").css("display","block");
			$(".btn-save").attr("disabled",true);
			$("#myModal").modal("show");
		});
		
		
		$(".btn-contract").unbind("click").click(function () {
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=contractnameforedit]").val();
			window.location.href = '../order/order.jsp?id='+$(this).parents("TR").find("input[type=hidden][name=sysId]").val()+"&name="+uName;
			
		});		
		
	
		
		$(".btn-add").unbind("click").click(function () {
			
			$("#sname").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();			
			$("#myModalLabel").html("合同添加");
			$('#sname').val("").prop("disabled", false);
			
			$("#edit_userid").val("");
			$(".btn-save").show();		
			$(".btn-save").attr("disabled", false);
			$("#myModal").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});
		
		$("#s_sserviceid").unbind("change").change(function () {
			
			Systemuser.refresh();
		
		});
		
		
	
		$(".btn-withdraw").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=contractnameforedit]").val();
			var message = "确定将合同【" + uName + "】撤回？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/contract/withdraw/" + id,
					type: "get",
					success: function (data) {
						msgBox('success', "撤回成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "撤回失败");
						Systemuser.dataTable.fnDraw();

					},
					dataType: "html"
				});
			});
		});









	
	
		
		$(".btn-save").unbind("click").click(function () {
			console.log("9999---");
			var modulesArray = new Array();
			var deletesArray = new Array();

			var webexReceivedAmount = $.trim($('#webexReceivedAmount').val());
			var ccReceivedAmount = $.trim($('#ccReceivedAmount').val());
			$(this).tooltip('destroy');
			if (!webexReceivedAmount || !ccReceivedAmount) {
				//msgBox('fail', "必须填写实收金额");
				$(this).tooltip({title:'存在尚未填写的实收金额'});
				$(this).tooltip('show');
				var This = $(this);
				setTimeout(function(){
					This.tooltip('destroy')
				},1000);
				return false;
			}
			
			$(".btn-save").attr("disabled", true);
		   
		
				var business = {};
				business.receivedAmount = webexReceivedAmount;
				business.ccReceivedAmount = ccReceivedAmount;


			var postURL = G_CTX_ROOT + "/v1/contract/finapprove/"+$.trim($('#edit_userid').val());
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
					
					msgBox('success', "首付款确认成功");
					Systemuser.dataTable.fnDraw();
				},
				error : function (data) {
					
					
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModal").modal("hide");
					$('#sname').val("");

					Systemuser.dataTable.fnDraw();
				},
			});
		});
	},


	getContractInfo : function (contractId) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/contract/" + contractId,
			data : "",
			type : "get",
			success : function (data) {
				console.log("----",data);
				$("#sname").val(data.displayName);
				
							
				//$("#sadmin").prop("disabled", true);			
				
				
				
			
			}
		});
	},

	getContractWithdraw : function (contractId) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/contractconfirm/detail/" + contractId,
			data : "",
			type : "get",
			success : function (data) {
				console.log("----",data);
				//$("#withdrawvalue").val(data.refund);
				//$("#finalvalue").val(data.firstInstallment-data.refund);
				//$("#sadmin").prop("disabled", true);
				$("#webexFirstInstallment").text(data.webexFirstInstallment);
				$("#ccFirstInstallment").text(data.ccFirstInstallment);
				$("#webexRefund").text(data.webexRefund);
				$("#ccRefund").text(data.ccRefund);
				var webexFinalInstallment = data.webexFirstInstallment - data.webexRefund;
				var ccFinalInstallment = data.ccFirstInstallment - data.ccRefund;
				$("#webexFinalInstallment").text(webexFinalInstallment);
				$("#ccFinalInstallment").text(ccFinalInstallment);
				var webexReceivedAmount = webexFinalInstallment == 0 ? 0:'';
				var ccReceivedAmount = ccFinalInstallment == 0 ? 0:'';
				$("#webexReceivedAmount").val(webexReceivedAmount);
				$("#ccReceivedAmount").val(ccReceivedAmount);
				$(".shade").css("display","none");
				$(".btn-save").attr("disabled",false);
			}
		});
	},


	refresh:function(){
		Systemuser.dataTable.fnDraw();
		
	},


	

	bindLaterEvent:function(){
		console.log("bindinglater");
		

	

	}


}
var rule = {
	
	
	sname : {
		name : "首付款金额",
		method : {
			required : true,
			lt : 100,
		}
		
	}
	
};
validator.init(rule);
validator.validate();
$(document).ready(function(){
	Systemuser.init();
});
