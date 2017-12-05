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
		setting.sAjaxSource = G_CTX_ROOT + "/v1/confirmbill/query";
		if (!$("#s_searchStartTime_STANDSITE").val()) {
			$("#s_searchStartTime_STANDSITE").val(Systemuser.getNowFormatDate());
		}
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
			{
				"sTitle": '<input type="checkbox" class="checkbox-checkall" />',
				"mData": "pid",
				//自定义列
				"bSortable" : false ,//排序
				"sClass": "center", 
				"sWidth":"5%",
				"render": function(data, dis, obj) {
			        return "<input type='checkbox' class='item-checkbox' value='" + data +"'><input type='hidden' name='accountPeriod' value='" + obj.accountPeriod + "'/>";
			    }
			},                 
			{
				"sTitle" : "客户名称",
				"mData" : "company",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},

			{
				"sTitle" : "客户编号",
				"mData" : "companyCode",
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
				"sTitle" : "总金额",
				"mData" : "amount",
				"bSortable" : true,
				"sClass" : "center",
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
					if (data == true) {
						return "<font color='green'>已确认</font>"
					} else {
						return "<font color='red'>未确认</font>"
					}
				}
			},
			
			{
					"sTitle" : "操作",
					"mData" : "f6",
					"bSortable" : false,
					"sClass" : "center",
					"sWidth" : "30%",
					"render" : function (data, dis, obj) {
						
						var viewDom = "<span class=\"btn btn-info btn-xs btn-detail\">查看明细</span> ";
						var recalcDom = "<span class=\"btn btn-warning btn-xs btn-recalc\">重新计算</span> ";

						var buttons = viewDom + recalcDom;
						
						return buttons +
							"<input type='hidden' name='company' value='" + obj.companyId + "'/><input type='hidden' name='accountPeriod' value='" + obj.accountPeriod + "'/>";
					}
			},
			 
			 ];
		setting.aaSorting = [[4, "desc"]];
		setting.bStateSave = true;
		
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		
		
	},
	bindButtonEvent : function () {
		console.log("binlding");
		
		
		$(".btn-detail").unbind("click").click(function () {
		
			window.location.href = '../report/billtemp.jsp?company='+$(this).parents("TR").find("input[type=hidden][name=company]").val()+"&accountPeriod="+$(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();
			
		});
	
		

		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});
		
		$("#s_sserviceid").unbind("change").change(function () {
			
			Systemuser.refresh();
		
		});
		
		 $('#s_searchStartTime_STANDSITE').unbind().bind('click', Systemuser.initDatePicker);	

	
		

		
		
		$(".btn-confirm").unbind("click").click(function(){

            var checkeds = [];
            $("input[type=checkbox]:checked").each(function(){
				if(!$(this).hasClass("checkbox-checkall")){
					var business = {};
					business.accountPeriod = $(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();
					business.customerId =$(this).val() 				
					checkeds.push(business);
				}	
			});
            var request={};
			request.confirmations = checkeds;
			if (checkeds.length > 0) {
				mConfirm("是否确认？", function () {
					var postData = JSON.stringify(request);
					$.ajaxInvoke({
						url: G_CTX_ROOT + "/v1/fbill/confirm",
						success: function (data) {
							Systemuser.dataTable.fnDraw();
						},
						error: "操作失败",
						data: postData,
						dataType: "json"
					});
				});
            
            }else{
				   mAlert("请选择你要确认的账单！","提示");
			}
		});


		$(".btn-recalc-all").unbind("click").click(function() {

			var accountPeriod = $("#s_searchStartTime_STANDSITE").val();

			var message = "将进行本月账单重计？";
			var request={};
			request.accountPeriod = accountPeriod;

			mConfirm(message, function () {
				$("#myModalConfirmDialog").modal("show");
				var postData = JSON.stringify(request);
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/fbill/recalcall",
					type: "post",
					data: postData,
					success: function (data) {
						msgBox('success', "操作成功");
						$("#myModalConfirmDialog").modal("hide");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "操作失败");
						$("#myModalConfirmDialog").modal("hide");
						Systemuser.dataTable.fnDraw();
					},
					dataType: "html"
				});
			});
		});

		$(".btn-recalc").unbind("click").click(function () {
			var accountPeriod = $(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();
			var customerId = $(this).parents("TR").find("input[type=hidden][name=company]").val();
			var target = $(this).parents("TR");


			var message = "确定将进行账单重计？";
			var request={};
			request.accountPeriod = accountPeriod;
			request.customerIds = [];
			request.customerIds.push(customerId);

			mConfirm(message, function () {
				$("#myModalConfirmDialog").modal("show");
				var postData = JSON.stringify(request);
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/fbill/recalc",
					type: "post",
					data: postData,
					success: function (data) {
						msgBox('success', "操作成功");
						$("#myModalConfirmDialog").modal("hide");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "操作失败");
						$("#myModalConfirmDialog").modal("hide");
						Systemuser.dataTable.fnDraw();
					},
					dataType: "html"
				});
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
