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
		setting.sAjaxSource = G_CTX_ROOT + "/v1/sap/payments";
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
				"sTitle" : "类型",
				"mData" : "accountType",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if (data == "PREPAID") {
						return "预付";
					} else {
						return "预存";
					}
				}
			}, 
		   

			{
				"sTitle" : "回款金额",
				"mData" : "currentAmount",
				"bSortable" : false,
				"sClass" : "right",
				"render" : function (data) {
					return htmlencode(data);
				}
			} ,

			{
				"sTitle" : "时间",
				"mData" : "operateTime",
				"bSortable" : false,
				"sClass" : "right",
				"render" : function (data) {
					return htmlencode(data);
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
