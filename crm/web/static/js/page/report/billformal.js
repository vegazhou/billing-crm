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
	dataTable1 : null,
	options : "",
	row_count : 0,
	orgservicelist:null,
	allorgservicelist:null,
	deletedorgservicelist:null,
	userservicelist:[],
	init : function () {
		/*
		var parameter=window.location.href.split('id=')[1];
		Systemuser.contractId=parameter.substring(0,parameter.indexOf("&"));
		Systemuser.contractName=decodeURIComponent(parameter.split('&name=')[1]);
		$("#s_sserviceid1").val(Systemuser.contractName);
		*/
		$("#s_customerid").val();
		var self = this;
		pageSetUp();
		if (!$("#s_searchStartTime_STANDSITE").val()) {
			$("#s_searchStartTime_STANDSITE").val(Systemuser.getNowFormatDate());
		}
		setting.sAjaxSource = G_CTX_ROOT + "/v1/fbill/query";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
			{
				"sTitle" : "客户名称",
				"mData" : "customerName",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},

			{
				"sTitle" : "客户编码",
				"mData" : "customerCode",
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
				"bSortable" : true,
				"sClass" : "right",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			
			{
				"sTitle" : "未支付金额",
				"mData" : "unpaidAmount",
				"bSortable" : true,
				"sClass" : "right",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			{
				"sTitle" : "确认状态",
				"mData" : "confirmed",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					if (data) {
						return "<font color='green'>已确认</font>"
					} else {
						return "<font color='red'>未确认</font>"
					}

				}
			},
			{
				"sTitle" : "PDF账单",
				"mData" : "sap",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data, dis, obj) {

					var msg="";
					var createDom="";
					var exportDom="";
					if (obj.confirmed) {
						if (obj.pdfStatus == "IN_PROGRESS") {
							var msg = "<font color='green'>制作中</font> ";

						} else if (obj.pdfStatus == "COMPLETED" && obj.pdfFailTasks == 0) {
							exportDom = "<span class=\"btn btn-info btn-xs btn-exportpdf\">下载</span> ";
							createDom = "<span class=\"btn btn-warning btn-xs btn-createpdf\">重新制作PDF</span> ";
						} else if (obj.pdfStatus == "COMPLETED" && obj.pdfFailTasks != 0) {
							msg = "<font color='red'>有异常</font> ";
							var createDom = "<span class=\"btn btn-warning btn-xs btn-createpdf\">重新制作PDF</span> ";
						} else {
							msg = "<font color='green'></font> ";
							createDom = "<span class=\"btn btn-info btn-xs btn-createpdf\">制作PDF</span> ";
						}
						return msg + createDom + exportDom;
					} else {
						return "";
					}
				}
			},
			
			{
				"sTitle" : "操作",
				"mData" : "unpaidAmount",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data, dis, obj) {
					var chargeDom = "<span class=\"btn btn-danger btn-xs btn-charge\">扣款</span> ";
					var viewDom = "<span class=\"btn btn-info btn-xs btn-detail\">查看明细</span> ";	
					if (obj.pdfStatus == "COMPLETED" && obj.pdfFailTasks == 0) {
						var sendMailDom = "<span class=\"btn btn-info btn-xs btn-sendmail\">发邮件</span> ";				
					}else{
						var sendMailDom = "";					
					}
					var tuneDom = "<span class=\"btn btn-warning btn-xs btn-tune\">调账</span> ";
					var tunelogDom = "<span class=\"btn btn-info btn-xs btn-log\">查看调账记录</span> ";

					var buttons = viewDom+sendMailDom;

					if (obj.unpaidAmount > 0) {
						buttons = chargeDom + buttons;
					}
					return buttons+  "<input type='hidden' name='sysId' value='" + obj.pid + "'/><input type='hidden' name='feeType' value='" + obj.feeType + "'/>"+
					"<input type='hidden' name='companyId' value='" + obj.companyId + "'/><input type='hidden' name='accountPeriod' value='" + obj.accountPeriod + "'/>";
					
				}
			}
			 
			 ];
		setting.aaSorting = [[3, "desc"]];
		setting.bStateSave = true;
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
		setting.bStateSave = true;
		Systemuser.dataTable1 = $('#datatable_tabletools_users').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
	},
	bindButtonEvent : function () {
		console.log("binlding");

		$(".btn-detail").unbind("click").click(function () {
			
			window.location.href = '../report/billformaldetail.jsp?company='+$(this).parents("TR").find("input[type=hidden][name=companyId]").val()+"&accountPeriod="+$(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();
			
		});
		
		$(".btn-log").unbind("click").click(function () {
			
			
			validator.clearForm();			
			//$("#myModalLabel").html("资金流水");
			//$('#sname').val("").prop("disabled", false);
			$("#s_sserviceid2").val($(this).parents("TR").find("input[type=hidden][name=sysId]").val());
			Systemuser.dataTable1.fnDraw();
			
			$("#myModal1").modal("show");
		});




		$(".btn-charge").unbind("click").click(function () {
			var customerId = $(this).parents("TR").find("input[type=hidden][name=companyId]").val();
			var accountPeriod = $(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();

			var form = {};
			form.customerId = customerId;
			form.accountPeriod = accountPeriod;

			var postData = JSON.stringify(form);
			mConfirm("从相应账户进行扣款？", function () {
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/v1/fbill/charge",
					data : postData,
					type : "post",
					success : function (data) {
						msgBox('success', "扣款成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "扣款失败");

					},
					dataType : "html"
				});
			});

		});
	
		
		$(".btn-createpdf").unbind("click").click(function () {
			var customerId = $(this).parents("TR").find("input[type=hidden][name=companyId]").val();
			var accountPeriod = $(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();
			//var feeType = $(this).parents("TR").find("input[type=hidden][name=feeType]").val();
			var form = {};
			form.customerId = customerId;
			form.accountPeriod = accountPeriod;
			//form.feeType = feeType;
			var postData = JSON.stringify(form);
		
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/v1/billexport/createpdf",
					data : postData,
					type : "post",
					success : function (data) {
						//msgBox('success', "扣款成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "扣款失败");

					},
					dataType : "html"
				});
			

		});
		
		
		$(".btn-sendmail").unbind("click").click(function () {
			var customerId = $(this).parents("TR").find("input[type=hidden][name=companyId]").val();
			var accountPeriod = $(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();
			//var feeType = $(this).parents("TR").find("input[type=hidden][name=feeType]").val();
			var form = {};
			form.customerId = customerId;
			form.accountPeriod = accountPeriod;
			//form.feeType = feeType;
			var postData = JSON.stringify(form);
		
				
				
				mConfirm("确认给客户发送邮件？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/billexport/sendmail",
						data : postData,
						type : "post",
						success : function (data) {
							msgBox('success', "邮件发送成功");
							Systemuser.dataTable.fnDraw();
						},
						error: function (data) {
							msgBox('fail', "邮件发送失败");

						},
						dataType : "html"
					});
				});

			

		});
		
		
		$(".btn-exportpdf").unbind("click").click(function () {
			var cookie=$.cookie('iPlanetDirectoryPro');
			console.log("cookie",cookie);
			var customerId = $(this).parents("TR").find("input[type=hidden][name=companyId]").val();
			var accountPeriod = $(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();
			//var feeType = $(this).parents("TR").find("input[type=hidden][name=feeType]").val();
			location.href=G_CTX_ROOT + "/v1/billexport/exportpdf/"+customerId+"/"+accountPeriod;		
			

		});
		
		

		$(".btn-search").unbind("click").click(function () {
			$("#s_customerid").val("");
			Systemuser.dataTable.fnDraw();
		});
		
		$("#s_sserviceid").unbind("change").change(function () {
			
			Systemuser.refresh();
		
		});
		
		 $('#s_searchStartTime_STANDSITE').unbind().bind('click', Systemuser.initDatePicker);


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
		

	

	},

	getNowFormatDate:function () {
		var date = new Date();
		var seperator1 = "-";
		var seperator2 = ":";
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}else{
			month=month+"";
		}

		var currentdate = year   + month ;
		return currentdate;
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
