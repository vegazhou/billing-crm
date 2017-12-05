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
		
		$("#s_sserviceid1").val("");
		$("#s_customerid").val("");
		var self = this;
		pageSetUp();
		$("#s_searchStartTime_STANDSITE").val(Systemuser.getNowFormatDate());
		setting.sAjaxSource = G_CTX_ROOT + "/v1/tbill/periodicFeeGrouped";
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
				"sTitle" : "金额",
				"mData" : "amount",
				"bSortable" : false,
				"sClass" : "right",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			
			
			
			{
				"sTitle" : "周期",
				"mData" : "accountPeriod",
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
				
					
					var viewDom1 = "<span class=\"btn btn-info btn-xs btn-detail\">查看明细</span> ";

					var buttons = viewDom1;

					
					return buttons+  "<input type='hidden' name='companyId' value='" + obj.customerId + "'/><input type='hidden' name='accountPeriod' value='" + obj.accountPeriod + "'/>";
					
				}
			}
		
			
			
			 
			 ];
		//setting.aaSorting = [[2, "desc"]];
		setting.bStateSave = false;
		setting.bPaginate = false;

		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		
	},
	

	bindButtonEvent : function () {
		console.log("binlding");

		

		


		
	
		
		
		
		
		
		
		

		$(".btn-search").unbind("click").click(function () {
			
				$("#s_customerid").val("");
				Systemuser.dataTable.fnDraw();
			
			
		});
		
		$("#s_sserviceid").unbind("change").change(function () {
			
			Systemuser.refresh();
		
		});
		
		 $('#s_searchStartTime_STANDSITE').unbind().bind('click', Systemuser.initDatePicker);


		
			$(".btn-detail").unbind("click").click(function () {
				
				window.location.href = '../report/billcycledetail.jsp?company='+$(this).parents("TR").find("input[type=hidden][name=companyId]").val()+"&accountPeriod="+$(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();
				
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

//validator.init(rule);
validator.validate();
$(document).ready(Systemuser.init());
