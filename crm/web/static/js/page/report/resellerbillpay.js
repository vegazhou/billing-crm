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
		setting.sAjaxSource = G_CTX_ROOT + "/v1/fbill/resellervoip";
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
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
                 
		                     
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
				"sTitle" : "产品名称",
				"mData" : "productName",
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
					
					var viewDom1 = "<span class=\"btn btn-info btn-xs btn-detail\">扣款</span> ";

					var buttons = viewDom1;

					
					return buttons+  							"<input type='hidden' name='total' value='" + obj.amount + "'/><input type='hidden' name='totalnotpay' value='" + obj.unpaidAmount + "'/><input type='hidden' name='id' value='" + obj.id + "'/>";

					
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


	
	
		
		 $('#s_searchStartTime_STANDSITE').unbind().bind('click', Systemuser.initDatePicker);

		
		
		
		
		

		$(".btn-search").unbind("click").click(function () {
			if($.trim($("#s_sserviceid").val())==""){
				msgBox('fail', "请输入客户名称");
				return;
			}
			if($.trim($("#s_searchStartTime_STANDSITE").val())==""){
				msgBox('fail', "请输入账单周期");
				return;
			}else{
				var date=$.trim($("#s_searchStartTime_STANDSITE").val());
				var strDate=date.substring(0,4)+"/"+date.substring(4,6)+"/";
				newDate =new Date(date.substring(0,4),date.substring(4,6)-1,"01");
				newDate.setMonth(newDate.getMonth()+1); 
				if((newDate.getMonth()+1)<10){
					resultDate=newDate.getFullYear()+"/0"+(newDate.getMonth()+1)+"/09";
				}else{
					resultDate=newDate.getFullYear()+"/"+(newDate.getMonth()+1)+"/09";
				}
				$("#startdetailcycletime").html(strDate+"09");
				$("#enddetailcycletime").html(resultDate);
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
