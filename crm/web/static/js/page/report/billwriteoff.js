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
		$("#s_sserviceid3").val(Systemuser.customerId);
		$("#s_searchStartTime_STANDSITE3").val(decodeURIComponent(parameter.split('&accountPeriod=')[1]));
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/fbill/querydetail";
		//$("#s_searchStartTime_STANDSITE").val(Systemuser.getNowFormatDate());
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent(Systemuser.customerId);
		setting.aoColumns = [
			{
				"sTitle": '<input type="checkbox" class="checkbox-checkall" />',
				"mData": "pid",
				//自定义列
				"bSortable" : false ,//排序
				"sClass": "center", 
				"sWidth":"5%",
				"render": function(data, dis, obj) {
					if(obj.id!=""){
						return "<input type='checkbox' class='item-checkbox' value='" + data +"'><input type='hidden' name='accountPeriod' value='" + obj.accountPeriod + "'/>";
					}else{
						return "";
					}
			    }
			}, 
			
			{
				"sTitle" : "流水号",
				"mData" : "id",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
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
				"sTitle" : "产品类型",
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
					if(data!=null){
						return htmlencode(data);
					}else{
						return ""
					}
				}
			}, 
			
			{
				"sTitle" : "结束时间",
				"mData" : "endDate",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if(data!=null){
						return htmlencode(data);
					}else{
						return ""
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
			},
			
			{
				"sTitle" : "未支付金额",
				"mData" : "unpaidAmount",
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
					"sWidth" : "10%",
					"render" : function (data, dis, obj) {
						
						var viewDom = "<span class=\"btn btn-info btn-xs btn-detail\">扣款</span> ";
						var buttons = viewDom ;
						
						return buttons +
							"<input type='hidden' name='total' value='" + obj.amount + "'/><input type='hidden' name='totalnotpay' value='" + obj.unpaidAmount + "'/><input type='hidden' name='id' value='" + obj.id + "'/>";
					}
			},
			 
			 ];
		setting.aaSorting = [[4, "desc"]];
		setting.bStateSave = false;
		setting.bPaginate = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		
		
	},
	bindButtonEvent : function () {
		console.log("binlding");
		

	
		

	
	


		$(".item-checkbox").unbind("click").click(function () {
			
			console.log("here",$(this).prop("checked"));
		    if($(this).prop("checked")){
		    	$("#total").html(Number($("#total").html())+Number($(this).parents("TR").find("input[type=hidden][name=total]").val()));
		    	$("#totalnotpay").html(Number($("#totalnotpay").html())+Number($(this).parents("TR").find("input[type=hidden][name=totalnotpay]").val()));
		    	
		    	
		    }else{
		    	$("#total").html(Number($("#total").html())-Number($(this).parents("TR").find("input[type=hidden][name=total]").val()));
		    	$("#totalnotpay").html(Number($("#totalnotpay").html())-Number($(this).parents("TR").find("input[type=hidden][name=totalnotpay]").val()));
		    }
		});
		
		$(".checkbox-checkall").unbind("click").click(function () {
		
			  if($(this).prop("checked")){
				  var total = 0;
				  var totalnotpay =0 ;
				  $(".item-checkbox").each(function(){ 					
					  total =total + Number($(this).parents("TR").find("input[type=hidden][name=total]").val());
					  totalnotpay =totalnotpay + Number($(this).parents("TR").find("input[type=hidden][name=totalnotpay]").val());	
							
					});
				    $("#total").html(total.toFixed(2));
			    	$("#totalnotpay").html(totalnotpay.toFixed(2));
				  
			    	
			    }else{
			    	$("#total").html(0);
			    	$("#totalnotpay").html(0);
			    }
		
		});

		$(".btn-confirm").unbind("click").click(function(){

            var checkeds = [];
            $("input[type=checkbox]:checked").each(function(){
				if(!$(this).hasClass("checkbox-checkall")){
					var amount = $(this).parents("TR").find("input[type=hidden][name=id]").val();					
					checkeds.push(amount);
				}	
			});
            var request={};
			request.billSequences = checkeds;
			if (checkeds.length > 0) {
				mConfirm("是否扣款？", function () {
					var postData = JSON.stringify(request);
					$.ajaxInvoke({
						url: G_CTX_ROOT + "/v1/fbill/charge2",
						success: function (data) {
							 mAlert("操作成功！","提示");
							Systemuser.bindLaterEvent(Systemuser.customerId);
							Systemuser.dataTable.fnDraw();
							$("#total").html(0);
					    	$("#totalnotpay").html(0);
						},
						error: "操作失败",
						data: postData,
						dataType: "json"
					});
				});
            
            }else{
				   mAlert("请选择你要扣款的账单！","提示");
			}
		});
		
		
		$(".btn-detail").unbind("click").click(function(){

            var checkeds = [];
            var amount = $(this).parents("TR").find("input[type=hidden][name=id]").val();					
			checkeds.push(amount);
            var request={};
			request.billSequences = checkeds;
		
				mConfirm("是否扣款？", function () {
					var postData = JSON.stringify(request);
					$.ajaxInvoke({
						url: G_CTX_ROOT + "/v1/fbill/charge2",
						success: function (data) {
							 mAlert("操作成功！","提示");
							 Systemuser.bindLaterEvent(Systemuser.customerId);
							Systemuser.dataTable.fnDraw();
						},
						error: "操作失败",
						data: postData,
						dataType: "json"
					});
				});
            
          
		});


	},







	refresh:function(){
		Systemuser.dataTable.fnDraw();
		
	},


	

	bindLaterEvent:function(customerId){
		console.log("bindinglater");
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/account/bycustomer/"+customerId,
			data : "",
			type : "get",
			success : function (data) {				
				 $("#repay").html(data.prepaid.balance);
				 $("#resave").html(data.deposit.balance);
				 $("#ccDeposit").html(data.ccDeposit.balance);
			},
			error: function (data) {
				

			},		
		});

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
