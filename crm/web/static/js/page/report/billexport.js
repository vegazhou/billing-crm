



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
		
		var self = this;
		pageSetUp();
		
	
		
		self.bindButtonEvent();
	
		
		self.bindLaterEvent();
		
	},
	

	bindButtonEvent : function () {
		console.log("binlding");

		$(".btn-sendmailall-all").unbind("click").click(function () {
			if($('#s_searchStartTime_STANDSITE').val()==""){
				msgBox('fail', "请选择账单周期");
				return;
			}
			location.href=G_CTX_ROOT + "/v1/billexport/createinvoicepdf/"+$('#s_searchStartTime_STANDSITE').val() ;
	
				
	

			

		});

		
	
		

		$(".btn-search").unbind("click").click(function () {
			console.log("here", $('#s_searchStartTime_STANDSITE').val());
			if($('#s_searchStartTime_STANDSITE').val()==""){
				msgBox('fail', "请选择账单周期");
				return;
			}
			location.href=G_CTX_ROOT + "/v1/billexport/export/"+$('#s_searchStartTime_STANDSITE').val() ;
		});
		
		
		$(".btn-invoice").unbind("click").click(function () {
			console.log("here", $('#s_searchStartTime_STANDSITE').val());
			if($('#s_searchStartTime_STANDSITE').val()==""){
				msgBox('fail', "请选择账单周期");
				return;
			}
			location.href=G_CTX_ROOT + "/v1/billexport/exportinvoice/"+$('#s_searchStartTime_STANDSITE').val() ;
		});
		
		
		$(".btn-export-all").unbind("click").click(function () {
			console.log("here", $('#s_searchStartTime_STANDSITE').val());
			if($('#s_searchStartTime_STANDSITE').val()==""){
				msgBox('fail', "请选择账单周期");
				return;
			}
			location.href=G_CTX_ROOT + "/v1/billexport/exportallpdf/"+$('#s_searchStartTime_STANDSITE').val() ;
		});
		
		

		$(".btn-create-all").unbind("click").click(function () {		
			
				console.log("here", $('#s_searchStartTime_STANDSITE').val());
			if($('#s_searchStartTime_STANDSITE').val()==""){
				msgBox('fail', "请选择账单周期");
				return;
			}
			var form = {};
			form.customerId = "11";
			form.accountPeriod = $('#s_searchStartTime_STANDSITE').val();
			//form.feeType = feeType;
			var postData = JSON.stringify(form);
		
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/v1/billexport/createallpdf",
					data : postData,
					type : "post",
					success : function (data) {
						//msgBox('success', "扣款成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "生成失败");

					},
					dataType : "html"
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
