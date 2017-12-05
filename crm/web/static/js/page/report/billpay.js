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
		
		//$("#s_sserviceid1").val("");
		$("#s_customerid").val("");
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/fbill/billsgbattable";
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
				"sTitle" : "客户编号",
				"mData" : "customerCode",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},

			{
				"sTitle" : "账期",
				"mData" : "accountPeriod",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},

			 {
				"sTitle" : "账户类型",
				"mData" : "amountType",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if (data == "PREPAID") {
						return "WebEx预付";
					} else if (data == "DEPOSIT") {
						return "WebEx预存";
					} else if (data == "CC_DEPOSIT") {
						return "天客云"
					}
					return "";
				}
			}, 
		   

			{
				"sTitle" : "总金额",
				"mData" : "amount",
				"bSortable" : false,
				"sClass" : "right",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			
			{
				"sTitle" : "未缴金额",
				"mData" : "unpaidAmount",
				"bSortable" : false,
				"sClass" : "right",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			
		
			
			{
				"sTitle" : "操作",
				"mData" : "unpaidAmount",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data, dis, obj) {
					var viewDom = "<span class=\"btn btn-info btn-xs btn-detail\">缴费</span> ";
					
					var viewDom1 = "<span class=\"btn btn-info btn-xs btn-detail-writeoff\">扣款</span> ";

					var buttons = viewDom+viewDom1;

					
					return buttons+  "<input type='hidden' name='sysId' value='" + obj.pid + "'/><input type='hidden' name='amountType' value='" + obj.amountType + "'/>"+
					"<input type='hidden' name='companyId' value='" + obj.companyId + "'/><input type='hidden' name='accountPeriod' value='" + obj.accountPeriod + "'/>";
					
				}
			}
			 
			 ];
		//setting.aaSorting = [[2, "desc"]];
		setting.bStateSave = true;
		setting.bPaginate = false;
		setting.columnDefs = [{
			targets: [0,1,2],
			createdCell: function (td, cellData, rowData, row, col) {
				var rowspan = rowData.rowSpan;
				if (rowspan > 1) {
					$(td).attr('rowspan', rowspan);
					$(td).css('vertical-align','middle');
				}
				if (rowspan == 0) {
					$(td).remove();
				}
			}
		}];

		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");

	},
	

	bindButtonEvent : function () {
		console.log("binlding");

		$(".btn-detail-writeoff").unbind("click").click(function () {
			
			window.location.href = '../report/billwriteoff.jsp?company='+$(this).parents("TR").find("input[type=hidden][name=companyId]").val()+"&accountPeriod="+$(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();
			
		});

		$(".btn-detail").unbind("click").click(function () {		
			validator.clearForm();
			$("#oldvalue").nextAll().filter("span").html("");
			$(".btn-save1").attr("disabled", false);
			var parentTR = $(this).parents("TR"); 
			var contractId = parentTR.find("input[type=hidden][name=companyId]").val();
			var amountType = parentTR.find("input[type=hidden][name=amountType]").val();
			var accountperiod = parentTR.find("input[type=hidden][name=accountPeriod]").val();
			$("#edit_userid1").val(contractId);	
			$("#amountType").val(amountType);
			$("#accountperiod").val(accountperiod);	
			$("#myModalLabel1").html("缴费");			
			$(".btn-save1").show();
			$("#myModal1").modal("show");
		});


		$(".btn-save1").unbind("click").click(function () {
			console.log("9999---");
			var modulesArray = new Array();
			var deletesArray = new Array();
			if (!validator.validate()) {
				return false;
			}
			
			$(".btn-save1").attr("disabled", true);

		
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
				business.accountPeriod = $.trim($('#accountperiod').val());
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
					//$("#edit_userid1").val("");
					$("#myModal1").modal("hide");
					$('#oldvalue').val("");
					
					msgBox('success', "缴费成功");
					window.location.href = '../report/billwriteoff.jsp?company='+$.trim($('#edit_userid1').val())+"&accountPeriod="+$.trim($('#accountperiod').val());
					
				},
				error : function (data) {
					
					
					$(".btn-save1").attr("disabled", false);
					validator.clearForm();
					//$("#edit_userid1").val("");
					$("#myModal1").modal("hide");
					$('#oldvalue').val("");
					
					msgBox('success', "缴费成功");
					window.location.href = '../report/billwriteoff.jsp?company='+$.trim($('#edit_userid1').val())+"&accountPeriod="+$.trim($('#accountperiod').val());
				},
			});
		});


		$("#s_sserviceid1").keydown(function(e){
			var key = e.which;
			if (key === 13) {
				$('#btn-search').trigger("click");
				return false;
			}
		});

		$(".btn-search").unbind("click").click(function () {
			if($("#s_sserviceid1").val()==""){
				msgBox('fail', "请输入客户名称");
			}else{
				$("#s_customerid").val("");
				Systemuser.dataTable.fnDraw();
			}
			
		});
		
		$("#s_sserviceid").unbind("change").change(function () {
			
			Systemuser.refresh();
		
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
