var EETableColumnsUser = [
				     
                  	{
                  		"sTitle": "金额",
                  		"mData": "currentAmount",
                  		"bSortable": true,
                  		"sClass": "right",
                  		"render": function (data) {
                  			return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" + htmlencode(data);
                  		}
                  	},
                  	
                  	{
                  		"sTitle": "操作类型",
                  		"mData": "operateType",
                  		"bSortable": false,
                  		"sClass": "center",
                  		"render": function (data) {
                  			if (data == "1") {
								return "系统扣费";
							} else if (data == "2") {
								return "存款缴费";
							} else if (data == "3") {
								return "系统退费";
							}
							return "未知操作";
						}
                  	},

                  	{
                  		"sTitle": "发生时间",
                  		"mData": "operateTime",
                  		"bSortable": true,
                  		"sClass": "center",
                  		"render": function (data) {
                  			return htmlencode(data);
                  		}
                  	},
                  	
                  	{
                  		"sTitle": "操作员",
                  		"mData": "operator",
                  		"bSortable": true,
                  		"sClass": "center",
                  		"render": function (data) {
                  			return htmlencode(data);
                  		}
                  	}
                	];



var Systemuser = {
	dataTable : null,
	dataTable1 : null,
	options : "",
	row_count : 0,
	orgservicelist:null,
	allorgservicelist:null,
	deletedorgservicelist:null,
	userservicelist:[],
	init : function () {
		var parameter=window.location.href.split('id=')[1];
		Systemuser.contractId=parameter.substring(0,parameter.indexOf("&"));
		Systemuser.contractName=decodeURIComponent(parameter.split('&name=')[1]);
		$("#s_sserviceid1").val(Systemuser.contractName);
		$("#s_customerid").val(Systemuser.contractId);
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/orderreport/query";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
		                     {
		                 		"sTitle" : "产品名称",
		                 		"mData" : "productName",
		                 		"bSortable" : true,
		                 		"sClass" : "center",
		                 		"render" : function (data) {
		                 			return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
		                 		}
		                 	}, 
		                 	{
		                 		"sTitle" : "起始时间",
		                 		"mData" : "startTime",
		                 		"bSortable" : true,
		                 		"sClass" : "center",
		                 		"render" : function (data) {
		                 			return htmlencode(data) ;
		                 		}
		                 	},
		                 	
		                 	
		                 	{
		                 		"sTitle" : "结束时间",
		                 		"mData" : "endTime",
		                 		"bSortable" : false,
		                 		"sClass" : "center",
		                 		"render" : function (data) {
		                 			return htmlencode(data) ;
		                 		}
		                 	},
		                 	
		                 	{
		                 		"sTitle" : "通过时间",
		                 		"mData" : "placedDate",
		                 		"bSortable" : false,
		                 		"sClass" : "center",
		                 		"render" : function (data) {
		                 			return htmlencode(data) ;
		                 		}
		                 	},
		                 	
		                 	{
		                 		"sTitle" : "付款周期",
		                 		"mData" : "payInterval",
		                 		"bSortable" : false,
		                 		"sClass" : "center",
		                 		"render" : function (data) {
									if (data == -1) {
										return "一次付清";
									} else if (data == 1) {
										return "月付";
									} else if (data == 3) {
										return "季付";
									} else if (data == 6) {
										return "半年付";
									} else if (data == 12) {
										return "年付";
									} else if (data == "" || data == null) {
										return "";
									}
									return htmlencode(data) + "个月" ;
		                 		}
		                 	},
		                 	
		                 	{
		                 		"sTitle" : "首付款金额",
		                 		"mData" : "firstInstallment",
		                 		"bSortable" : false,
		                 		"sClass" : "right",
		                 		"render" : function (data, dis, obj) {
		                 			if (obj.fromOriginalContract == true) {
		                 				return "-"
		                 			} else {
		                 				return htmlencode(data);
		                 			}
		                 		}
		                 	},
		                 	
		                 	{
		                 		"sTitle" : "总金额",
		                 		"mData" : "totalAmount",
		                 		"bSortable" : false,
		                 		"sClass" : "right",
		                 		"render" : function (data) {
		                 			return htmlencode(data) ;
		                 		}
		                 	},
		                 	
		                 	
		                 	{
		                 		"sTitle" : "所属合同",
		                 		"mData" : "contractName",
		                 		"bSortable" : false,
		                 		"sClass" : "center",
		                 		"render" : function (data,dis, obj) {
									if (obj.contractId == "" || obj.contractName == "") {
										return ""
									} else {
										var link = "/crm/views/order/order.jsp?id=" + obj.contractId + "&name=";
										return "<a href='" + link + "'>" + htmlencode(data) + "</a>";
									}
		                 		}
		                 	}
			 
			 ];
		setting.aaSorting = [[1, "desc"]];
		setting.bStateSave = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		
		//Systemuser.initMoney();
	},
	
	
	initMoney : function () {
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/current/query"+$("#s_sserviceid2").val();
		//Systemuser.dataTable1=null;
		//$('#datatable_tabletools_users').dataTable(null);
		//self.bindLaterEvent();
		setting.aoColumns = EETableColumnsUser;
		setting.aaSorting = [[2, "desc"]];
		setting.bStateSave = false;
		Systemuser.dataTable1 = $('#datatable_tabletools_users').dataTable(setting);
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
			$("#oldvalue").nextAll().filter("span").html("");
			$(".btn-save1").attr("disabled", false);
			var parentTR = $(this).parents("TR"); 
			var contractId = parentTR.find("input[type=hidden][name=customerid]").val();
			var amountType = parentTR.find("input[type=hidden][name=amountType]").val();
			$("#edit_userid1").val(contractId);	
			$("#amountType").val(amountType);	
			$("#myModalLabel1").html("缴费");			
			$(".btn-save1").show();
			$("#myModal1").modal("show");
		});
		
		
		$(".btn-contract").unbind("click").click(function () {
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
			window.location.href = '../order/order.jsp?id='+$(this).parents("TR").find("input[type=hidden][name=sysId]").val()+"&name="+uName;
			
		});
	
		
		$(".btn-money").unbind("click").click(function () {
			
			$("#sname").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();			
			$("#myModalLabel").html("资金流水");
			$('#sname').val("").prop("disabled", false);
			$("#s_sserviceid2").val($(this).parents("TR").find("input[type=hidden][name=sysId]").val());
			Systemuser.dataTable1.fnDraw();
			$("#edit_userid").val("");
			$(".btn-save").show();		
			$(".btn-save").attr("disabled", false);
			$("#myModal").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			//$("#s_customerid").val("");
			Systemuser.dataTable.fnDraw();
		});
		
		$("#s_sserviceid").unbind("change").change(function () {
			
			Systemuser.refresh();
		
		});
		
		 $('#s_searchStartTime_STANDSITE').unbind().bind('click', Systemuser.initDatePicker);	

	
		



		$(".btn-send").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
			var message = "确定将合同【" + uName + "】送审？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/contract/send/" + id,
					type: "get",
					success: function (data) {
						msgBox('success', "送审成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "送审失败");
						Systemuser.dataTable.fnDraw();

					},
					dataType: "html"
				});
			});
		});


		$(".btn-withdraw").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
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




		
		$(".btn-save1").unbind("click").click(function () {
			console.log("9999---");
			var modulesArray = new Array();
			var deletesArray = new Array();
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
		
				var business = {};
				business.accountType = $.trim($('#amountType').val());		
				business.customerId = $.trim($('#edit_userid1').val());
				business.amount = $.trim($('#oldvalue').val());
				var postURL = G_CTX_ROOT + "/v1/account/deposit";
				var update = false;
				var calltype = "post";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				success : function (data) {
				
					$(".btn-save1").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid1").val("");
					$("#myModal1").modal("hide");
					$('#oldvalue').val("");
					
					msgBox('success', "缴费成功");
					Systemuser.dataTable.fnDraw();
				},
				error : function (data) {
					
					
					$(".btn-save1").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid1").val("");
					$("#myModal1").modal("hide");
					$('#oldvalue').val("");
					
					msgBox('success', "缴费成功");
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
	
	
	oldvalue : {
		name : "缴费金额",
		method : {
			required : true,
			lt : 100,
		},
		
	}
	
};
validator.init(rule);
validator.validate();
$(document).ready(Systemuser.init());
