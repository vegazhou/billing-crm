var Systemuser = {
	dataTable : null,
	options : "",
	first : false,
	row_count : 0,
	customerId :"",
	orgservicelist:null,
	allorgservicelist:null,
	deletedorgservicelist:null,
	userservicelist:[],
	init : function () {
		
		var parameter=window.location.href.split('company=')[1];
		Systemuser.customerId=decodeURIComponent(parameter.substring(0,parameter.indexOf("&")));
		$("#s_sserviceid").val(Systemuser.customerId);
		$("#s_searchStartTime_STANDSITE").val(decodeURIComponent(parameter.split('&accountPeriod=')[1]));
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/tbill/periodicFeeDetailed";
		//$("#s_searchStartTime_STANDSITE").val(Systemuser.getNowFormatDate());
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			//self.bindButtonEvent();
		});
		
		
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
				"sTitle" : "产品名称",
				"mData" : "productName",
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
				"sTitle" : "起始时间",
				"mData" : "startDate",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			}, 
			
			{
				"sTitle" : "结束时间",
				"mData" : "endDate",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			
			{
				"sTitle" : "付费周期",
				"mData" : "payInterval",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if (data == 1) {
						return "月付"
					} else if (data == 3){
						return "季付"
					}else if (data == 6){
						return "半年付"
					}else if (data == 12){
						return "年付"
					}
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
			}
			
			


			
			
		
			 
			 ];
		
		setting.bStateSave = false;
		setting.bPaginate = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		
		
	},



}

$(document).ready(Systemuser.init());
