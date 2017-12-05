var EETableColumnsUser = [
				     
                  	{
                  		"sTitle": "合同名称",
                  		"mData": "pdfName",
                  		"bSortable": false,
                  		"sClass": "right",
                  		"render": function (data) {
                  			 return "<input name='productnameforedit' type='hidden' value='" + data + "'/>" + htmlencode(data);
                  		}
                  	},
                  	
                  	

                  	{
                  		"sTitle": "合同编号",
                  		"mData": "contractNumber",
                  		"bSortable": false,
                  		"sClass": "center",
                  		"render": function (data) {
                  			return htmlencode(data);
                  		}
                  	},
                  	
                  	{
                  		"sTitle": "操作",
                  		"mData": "f6",
                  		"bSortable": false,
                  		"sClass": "center",
                  		"render": function (data, dis, obj) {
                  		    deleteDom = "<span class=\"btn btn-danger btn-xs btn-deletepdf\">删除</span> ";
							exportDom = "<span class=\"btn btn-info btn-xs btn-exportpdf\">下载</span> ";
							return exportDom+deleteDom+  "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";

                  			
                  			
                  		}
                  	}
                	];


var SiteColumns = [
	{
		"sTitle": "站点名称",
		"mData": "siteName",
		"bSortable": false,
		"sClass": "center",
		"render": function (data) {
			return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" + htmlencode(data);
		}
	},

	{
		"sTitle": "主要语言",
		"mData": "primaryLanguage",
		"bSortable": false,
		"sClass": "center",
		"render": function (data) {
			return htmlencode(data);
		}
	},

	{
		"sTitle": "时区",
		"mData": "timeZone",
		"bSortable": false,
		"sClass": "center",
		"render": function (data) {
			return htmlencode(data);
		}
	},

	{
		"sTitle": "国家",
		"mData": "countryCode",
		"bSortable": false,
		"sClass": "center",
		"render": function (data) {
			if (data == null) {
				return "无限制"
			} else {
				return htmlencode(data);
			}
		}
	},

	{
		"sTitle": "地区",
		"mData": "location",
		"bSortable": false,
		"sClass": "center",
		"render": function (data) {
			return htmlencode(data);
		}
	},





	{
		"sTitle": "操作",
		"mData": "f6",
		"bSortable": false,
		"sClass": "center",
		"sWidth": "15%",
		"render": function (data, dis, obj) {
			var buttons = "";
			viewDom = "<span class='btn btn-success btn-xs btn-detail'>查看</span> ";
			editDom = "<span class='btn btn-success btn-xs btn-detail'>修改</span>  ";
			deleteDom = "<span class='btn btn-danger btn-xs btn-delete'>删除</span> ";

			
			return editDom +deleteDom+ "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
		}
	}];


var OrderColumns=[
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
		"sTitle": "站点名称",
		"mData": "siteName",
		"bSortable": true,
		"sClass": "center",
		"render": function (data) {
			if(data==null){
				return "";
			}
			else{
				return htmlencode(data);
			}
			
		}
	},
	{
		"sTitle" : "起始时间",
		"mData" : "startTime",
		"bSortable" : true,
		"sClass" : "center",
		"render" : function (data) {
			return data ;
		}
	},
	
	
	{
		"sTitle" : "结束时间",
		"mData" : "endTime",
		"bSortable" : true,
		"sClass" : "center",
		"render" : function (data) {
			return data ;
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
		"bSortable" : true,
		"sClass" : "right",
		"render" : function (data, dis, obj) {
			if (obj.fromOriginalContract == true) {
				return "-"
			} else {
				var tuneButton = "";
				if (data != 0 && obj.state == 'DRAFT') {
					tuneButton = " <span class=\"btn btn-warning btn-xs btn-tunefi\">调整金额</span>";
				}
				return data + tuneButton;
			}
		}
	},
	
	{
		"sTitle" : "总金额",
		"mData" : "totalAmount",
		"bSortable" : true,
		"sClass" : "right",
		"render" : function (data) {
			return data ;
		}
	},
	
	
	{
		"sTitle" : "错误提示",
		"mData" : "ok",
		"bSortable" : false,
		"sClass" : "center",
		"render" : function (data,dis, obj) {
			if(data==false){
				data=  "<a href='#' title='"+obj.errorMessage+"'><img src='/crm/static/img/exclamation.png'></a>"
			}else{
				data="";
			}
			return data ;
		}
	},
	
	
	 {
		"sTitle" : "操作",
		"mData" : "f6",
		"bSortable" : false,
		"sClass" : "center",
		"sWidth" : "15%",
		"render" : function (data, dis, obj) {
			var buttons = "";
			editMetaDom = "<span class=\"btn btn-success btn-xs btn-detail-order\">修改设置</span> ";
			editChargeDom = "<span class=\"btn btn-success btn-xs btn-charge\">修改资费</span> ";
			viewChargeDom = "<span class=\"btn btn-success btn-xs btn-charge\">查看资费</span> ";
			terminateDom = "<span class='btn btn-danger btn-xs btn-terminate'>提前中止</span> ";
			deleteDom = "<span class='btn btn-danger btn-xs btn-delete-order'> 删除 </span> ";
			if (obj.pid == null) {

			} else {
				if (obj.state == "DRAFT") {
					buttons = editChargeDom + editMetaDom + deleteDom;
					if (obj.fromOriginalContract == true) {
						buttons = viewChargeDom + terminateDom;
					}
				} else {
					buttons = viewChargeDom;
				}
			}
			return buttons + "<input type='hidden' name='chargeId' value='" + obj.chargeId + "'/><input type='hidden' name='oldamount' value='" + obj.firstInstallment + "'/><input type='hidden' name='sysId' value='" + obj.pid + "'/>";
		}
	}, ];

var Systemuser = {
	totalProductList : [],
	selectedProductList : [],
	dataTable : null,
	dataTable1 : null,
	options : "",
	row_count : 0,
	orgservicelist:null,
	allorgservicelist:null,
	deletedorgservicelist:null,
	userservicelist:[],
	init : function () {
		Systemuser.totalProductList = [];
		var parameter=window.location.href.split('id=')[1];
		Systemuser.contractId=parameter.substring(0,parameter.indexOf("&"));
		Systemuser.contractName=decodeURIComponent(parameter.split('&name=')[1].substring(0,parameter.split('&name=')[1].indexOf("&")));
		Systemuser.status=decodeURIComponent(parameter.split('&status=')[1]);
		Systemuser.reseller=decodeURIComponent(parameter.split('&reseller=')[1]);
		$(".contractName").html(Systemuser.contractName);
		if(Systemuser.reseller!='undefined'){
			if(Systemuser.reseller!='null'){
				$(".reseller").html("分销/代理商:"+Systemuser.reseller);
			}
		}
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/order/query/"+Systemuser.contractId;
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = OrderColumns
		setting.aaSorting = [[0, "desc"]];
		setting.bStateSave = false;
		setting.bPaginate = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		
		
		setting.sAjaxSource = G_CTX_ROOT + "/v1/wbxsite/query/"+Systemuser.contractId;
		
		setting.aoColumns = SiteColumns;
		setting.aaSorting = [[0, "desc"]];
		setting.bStateSave = false;
		setting.bPaginate = false;
		Systemuser.userTable = $('#datatable_tabletools_sites').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		Systemuser.initMoney();
		if(Systemuser.status=='起草中'){
			$(".btn-submit-contract").show();
		}else{
			$(".btn-submit-contract").hide();
		}
		
	},
	initMoney : function () {
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/contractpdf/query"+$("#s_sserviceid2").val();
		//Systemuser.dataTable1=null;
		//$('#datatable_tabletools_users').dataTable(null);
		//self.bindLaterEvent();
		setting.aoColumns = EETableColumnsUser;
		setting.aaSorting = [[2, "desc"]];
		setting.bStateSave = false;
		setting.bPaginate = false;
		Systemuser.dataTable1 = $('#datatable_tabletools_users').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
	},
	
	bindButtonEvent : function () {
		console.log("binlding");
		$(".btn-submit-contract").unbind("click").click(function () {
			var id = Systemuser.contractId;		
			var uName = Systemuser.contractName;
			var message = "确定将合同【" + uName + "】送审？";

			mConfirm(message, function () {
				$(".btn-submit-contract").attr("disabled", true);
				$(".btn-submit-contract").text("送审中...");
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/contract/send/" + id,
					type: "get",
					success: function (data) {
						msgBox('success', "送审成功");
						$(".btn-submit-contract").text("送审");
						$(".btn-submit-contract").attr("disabled", false);
						window.location.href = '../contract/contract.jsp';
					},
					error: function (data) {
						msgBox('fail', "送审失败");
						$(".btn-submit-contract").text("送审");
						$(".btn-submit-contract").attr("disabled", false);

					},
					dataType: "html"
				});
			});
		});
		$("#file").val('').unbind("change").change(function () {			
			 
		    $('#filetxt').val($(this).val());
	
		});
		$(".btn-reupload").unbind("click").click(function () {
			
			$("#myModalSelectFile").modal("show");
    		$("#myModalErrorMessage").modal("hide");
		});
		
		$(".btn-exportpdf").unbind("click").click(function () {
			
			var pdfid = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			//var accountPeriod = $(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();
			//var feeType = $(this).parents("TR").find("input[type=hidden][name=feeType]").val();
			location.href=G_CTX_ROOT + "/v1/contractpdf/exportpdflarge/"+pdfid;		
			

		});
		$(".btn-upload").unbind("click").click(function () {
			$('#filetxt').val("");
			
			var userid = Systemuser.contractId;
			$("#contractNumber").nextAll().filter("span").html("");
			$("#filetxt").nextAll().filter("span").html("");
			$('#contractNumber').val("").prop("disabled", false);
			$("#edit_userid").val(userid);
			$("#myModalSelectFile").modal("show");
			
		});
		
		
		$(".btn-deletepdf").unbind("click").click(function () {
            var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
            var target = $(this).parents("TR");
            var tds = $(target).children('td');
            var uName = $(tds[0]).find("input[name=productnameforedit]").val();
            var message = "确定删除pdf【" + uName + "】？";

            mConfirm(message, function () {
                $.ajaxInvoke({
                    url: G_CTX_ROOT + "/v1/contractpdf/" + id,
                    type: "delete",
                    success: function (data) {
                        msgBox('success', "PDF删除成功");
                        Systemuser.dataTable1.fnDraw();
                    },
                    error: function (data) {
                        msgBox('fail', "PDF删除失败");
                        Systemuser.dataTable1.fnDraw();
                    },
                    dataType: "html"
                });
            });
        });
		$(".btn-save-adjust").unbind("click").click(function () {
			console.log("99991---");
			
			$(".btn-save-adjust").attr("disabled", true);
		
				var business = {};
					
				business.orderId = $.trim($('#edit_userid1').val());
				business.amount = $.trim($('#newvalue').val());	
				
				var postURL = G_CTX_ROOT + "/v1/order/tunefi/";
				var update = false;
				var calltype = "post";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				success : function (data) {
				
					$(".btn-save-adjust").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid1").val("");
					$("#myModaladjustamount").modal("hide");
					
					
					msgBox('success', "更新成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-adjust").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid1").val("");
					$("#myModaladjustamount").modal("hide");
					
					

					msgBox('fail', "操作失败");

					Systemuser.refresh();
				},
				dataType: "html"
			});
		});
		
		$(".btn-tunefi").unbind("click").click(function () {
			
			$("#sname").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();			
			var parentTR = $(this).parents("TR"); 
			var userid = parentTR.find("input[type=hidden][name=sysId]").val();
			var oldvalue=parentTR.find("input[type=hidden][name=oldamount]").val();
			
			$("#edit_userid1").val(userid);
			$('#oldvalue').val(oldvalue);
			$('#newvalue').val("");
			$("#s_sserviceid2").val(Systemuser.contractId);
			//Systemuser.dataTable1.fnDraw();
			
			$(".btn-save").show();		
			$(".btn-save").attr("disabled", false);
			$("#myModaladjustamount").modal("show");
		});
		
		$(".btn-list-pdf").unbind("click").click(function () {
			
			$("#sname").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();			
			$("#myPdfModalLabel").html("合同列表");
			$('#sname').val("").prop("disabled", false);
			$("#s_sserviceid2").val(Systemuser.contractId);
			Systemuser.dataTable1.fnDraw();
			$("#edit_userid").val("");
			$(".btn-save").show();		
			$(".btn-save").attr("disabled", false);
			$("#myPdfListModal").modal("show");
		});
		
		$(".btn-upload-pdf").unbind("click").click(function () {
			if($('#filetxt').val()==""){
				$("#filetxt").nextAll().filter("span").html("<font color=red >请上传文件!</font>");
				return;
			}
			if($('#contractNumber').val()==""){
				$("#contractNumber").nextAll().filter("span").html("<font color=red >请输入合同编号!</font>");
				return;
			}
			var contractId = $("#edit_userid").val();
		    $.ajaxFileUpload({
		        url:G_CTX_ROOT + "/v1/contractpdf/uploadlarge/" +contractId+"/"+$('#contractNumber').val(),
		        secureuri:false,                       //是否启用安全提交,默认为false
		        fileElementId:'file',           //文件选择框的id属性
		        dataType:'json',                      //服务器返回的格式,可以是json或xml等
		        success:function(data, status){        //服务器响应成功时的处理函数


		        	if(data.success){
		        		mAlert("上传成功!");
		        		
		        		//dataTable.fnDraw();
		        		
		        		$("#childClassID").val("");
			        	$("#myModalErrorMessage").hide();
		        		$("#myModalSelectFile").modal("hide");
		        		$("#edit_userid").val("");
			        }else{
			        	var errorMessage = $(".errorMessage font");

			        	var msg = "";
			        	if(data.detail){
				        	

			        			msg =  "&nbsp;&nbsp;&nbsp;&nbsp;"+ data.detail + "<br>";
			        		
		        		}
		        		errorMessage.html(msg);
		        		$("#myModalSelectFile").modal("hide");
		        		$("#myModalErrorMessage").modal("show");
		        		$("#edit_userid").val("");
			        }
		        },
		        error:function(data, status, e){ //服务器响应失败时的处理函数

		        	mAlert("导入失败，请联系系统管理员！");
		        	$("#file").val("");
	        		$("#childClassID").val("");
	        		$("#myModalErrorMessage").modal("hide") ;
	        		$("#myModalSelectFile").modal("hide");
	        		$("#edit_userid").val("");
		        }
		    });		
		});	
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
			$("#sname").nextAll().filter("span").html("");
			$(".btn-save").attr("disabled", false);
			var parentTR = $(this).parents("TR"); 
			var userid = parentTR.find("input[type=hidden][name=sysId]").val()
			$("#edit_userid").val(userid);
		
			//$("#sname").val(parentTR.find("input[name=accountnameforedit]").val()).prop("disabled", true);
			//$("#edit_username").val($.trim(parentTR.find("input[name=accountnameforedit]").val())).prop("disabled", false);
			Systemuser.getUserDetail(userid);
			$("#myModalLabel").html("站点修改");
			$('#sname').val("").prop("disabled", true);
			$("#edit_username").val("");			
			$("input[type='checkbox']").prop("checked", false);
			$("#s_languageid").val("");
			$("#s_sserviceid").val("");	
			$("#s_location").val("");			
			$(".btn-save").show();
			$("#myModal").modal("show");
		});	
		
		
		$(".btn-detail-order").unbind("click").click(function () {
			
			validator.clearForm();
			
			$('.producttypedetail').hide();
			
			
			$(".btn-save-order").attr("disabled", false);
			$("#s_productId").attr("disabled", true);
			var parentTR = $(this).parents("TR"); 
			var userid = parentTR.find("input[type=hidden][name=sysId]").val()
			$("#edit_userid_order").val(userid);
		
			//$("#sname").val(parentTR.find("input[name=accountnameforedit]").val()).prop("disabled", true);
			//$("#edit_username").val($.trim(parentTR.find("input[name=accountnameforedit]").val())).prop("disabled", false);
			Systemuser.getallproducts(userid);
			
			$("#myModalLabelContract").html("订单修改");

			$("#s_productId").val("");
			$("#s_searchStartTime1").val("");
			$("#s_payInterval").val("");
			$(".btn-save").show();
			$("#myModalContract").modal("show");
		});


		$(".pstndiscount_button").unbind("click").click(function () {
			if($("#pstndiscount_MONTHLY_PAY_BY_PSTNALLSTIE").val()==""){
				return;
			}
			if($("#pstndiscount_MONTHLY_PAY_BY_PSTNALLSTIE").val()>=1){

				$("#pstndiscount_MONTHLY_PAY_BY_PSTNALLSTIE").nextAll().filter("span").html("<font color=red >输入折扣不能大于1!</font>") ;
				return ;
			}
			$("#pstndiscount_MONTHLY_PAY_BY_PSTNALLSTIE").nextAll().filter("span").html("") ;
			$(".raterow").each(function(){

				code=$(this).find("input[type=hidden][name=rateCode]").val();
				originalvalue =$(this).find("input[type=text][name="+code+"2]").val()*100;
				discount=$("#pstndiscount_MONTHLY_PAY_BY_PSTNALLSTIE").val()*100;
				rate=originalvalue*discount;
				var str = Math.round(rate/100)/100;
				$(this).find("input[type=text][name="+code+"2]").val(str);

			});


		});


		$(".comdiscount_button").unbind("click").click(function () {
			if($("#comdiscount_MONTHLY_PAY_BY_PSTNALLSTIE").val()==""){
				return;
			}
			if($("#comdiscount_MONTHLY_PAY_BY_PSTNALLSTIE").val()>=1){

				$("#comdiscount_MONTHLY_PAY_BY_PSTNALLSTIE").nextAll().filter("span").html("<font color=red >输入折扣不能大于1!</font>") ;
				return ;
			}
			$("#comdiscount_MONTHLY_PAY_BY_PSTNALLSTIE").nextAll().filter("span").html("") ;

			$(".raterow").each(function(){

				code=$(this).find("input[type=hidden][name=rateCode]").val();
				originalvalue =$(this).find("input[type=text][name="+code+"1]").val()*100;
				discount=$("#comdiscount_MONTHLY_PAY_BY_PSTNALLSTIE").val()*100;
				rate=originalvalue*discount;
				var str = Math.round(rate/100)/100;
				$(this).find("input[type=text][name="+code+"1]").val(str);

			});

		});


		$(".pstndiscount_button_CC_CALLCENTER_PSTN").unbind("click").click(function () {
			if($("#pstndiscount_CC_CALLCENTER_PSTN").val()==""){
				return;
			}
			if($("#pstndiscount_CC_CALLCENTER_PSTN").val()>=1){

				$("#pstndiscount_CC_CALLCENTER_PSTN").nextAll().filter("span").html("<font color=red >输入折扣不能大于1!</font>") ;
				return ;
			}
			$("#pstndiscount_CC_CALLCENTER_PSTN").nextAll().filter("span").html("") ;
			$(".raterow").each(function(){

				code=$(this).find("input[type=hidden][name=rateCode]").val();
				originalvalue =$(this).find("input[type=text][name="+code+"2]").val()*100;
				discount=$("#pstndiscount_CC_CALLCENTER_PSTN").val()*100;
				rate=originalvalue*discount;
				var str = Math.round(rate/100)/100;
				$(this).find("input[type=text][name="+code+"2]").val(str);

			});


		});


		$(".comdiscount_button_CC_CALLCENTER_PSTN").unbind("click").click(function () {
			if($("#comdiscount_CC_CALLCENTER_PSTN").val()==""){
				return;
			}
			if($("#comdiscount_CC_CALLCENTER_PSTN").val()>=1){

				$("#comdiscount_CC_CALLCENTER_PSTN").nextAll().filter("span").html("<font color=red >输入折扣不能大于1!</font>") ;
				return ;
			}
			$("#comdiscount_CC_CALLCENTER_PSTN").nextAll().filter("span").html("") ;

			$(".raterow").each(function(){

				code=$(this).find("input[type=hidden][name=rateCode]").val();
				originalvalue =$(this).find("input[type=text][name="+code+"1]").val()*100;
				discount=$("#comdiscount_CC_CALLCENTER_PSTN").val()*100;
				rate=originalvalue*discount;
				var str = Math.round(rate/100)/100;
				$(this).find("input[type=text][name="+code+"1]").val(str);

			});

		});



		$("#pstndiscount_PSTN_PERSONAL_CHARGE_Button").unbind("click").click(function () {
			if($("#pstndiscount_PSTN_PERSONAL_CHARGE").val()==""){
				return;
			}

			if($("#pstndiscount_PSTN_PERSONAL_CHARGE").val()>=1){

				$("#pstndiscount_PSTN_PERSONAL_CHARGE").nextAll().filter("span").html("<font color=red >输入折扣不能大于1!</font>") ;
				return ;
			}
			$("#pstndiscount_PSTN_PERSONAL_CHARGE").nextAll().filter("span").html("") ;
			$(".raterow").each(function(){

				code=$(this).find("input[type=hidden][name=rateCode]").val();
				originalvalue =$(this).find("input[type=text][name="+code+"2]").val()*100;
				discount=$("#pstndiscount_PSTN_PERSONAL_CHARGE").val()*100;
				rate=originalvalue*discount;
				var str = Math.round(rate/100)/100;
				$(this).find("input[type=text][name="+code+"2]").val(str);

			});


		});


		$("#comdiscount_PSTN_PERSONAL_CHARGE_Button").unbind("click").click(function () {
			if($("#comdiscount_PSTN_PERSONAL_CHARGE").val()==""){
				return;
			}
			if($("#comdiscount_PSTN_PERSONAL_CHARGE").val()>=1){

				$("#comdiscount_PSTN_PERSONAL_CHARGE").nextAll().filter("span").html("<font color=red >输入折扣不能大于1!</font>") ;
				return ;
			}
			$("#comdiscount_PSTN_PERSONAL_CHARGE").nextAll().filter("span").html("") ;
			$(".raterow").each(function(){

				code=$(this).find("input[type=hidden][name=rateCode]").val();
				originalvalue =$(this).find("input[type=text][name="+code+"1]").val()*100;
				discount=$("#comdiscount_PSTN_PERSONAL_CHARGE").val()*100;
				rate=originalvalue*discount;
				var str = Math.round(rate/100)/100;
				$(this).find("input[type=text][name="+code+"1]").val(str);

			});

		});

		$(".pstnradio").unbind("click").click(function () {
			console.log("---",$("input[name='pstnradio'][value='pstn']").prop("checked"));
			if($("input[name='pstnradio'][value='pstn']").prop("checked")==true){

				Systemuser.createPSTNoptions();
			}else{
				Systemuser.createTSPoptions();
			}

		});
		$(".btn-charge").unbind("click").click(function () {

			validator.clearForm();

			//$("#sname").nextAll().filter("span").html("");
			$(".btn-save-charge").attr("disabled", false);
			var parentTR = $(this).parents("TR");
			var chargeId = parentTR.find("input[type=hidden][name=chargeId]").val();

			Systemuser.getcharge(chargeId);

		});


		$(".btn-add").unbind("click").click(function () {

			$("#sname").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();
			$("#myModalLabel").html("站点添加");
			$('#sname').val("").prop("disabled", false);
			$("#edit_username").val("");
			$("#edit_userid").val("");
			$("input[type='checkbox']").prop("checked", false);
			$("#s_languageid").val("");
			$("#s_sserviceid").val("");
			$("#s_location").val("");
			$(".btn-save").show();
			$("input[name='pstnradio'][value='pstn']").prop("checked",true);
			Systemuser.createPSTNoptions();
			$(".btn-save").attr("disabled", false);
			$("#myModal").modal("show");
		});

		$(".btn-terminate").unbind("click").click(function () {


			updateObj = null;
			validator.clearForm();
			var parentTR = $(this).parents("TR");
			var userid = parentTR.find("input[type=hidden][name=sysId]").val()

			$("#edit_userid_order_MONTHLY_PAY_BY_TERMINATE").val(userid);

			$(".btn-save-order-MONTHLY_PAY_BY_TERMINATE").show();
			$(".btn-save-order-MONTHLY_PAY_BY_TERMINATE").attr("disabled", false);
			$("#myModal_MONTHLY_PAY_BY_TERMINATE").modal("show");
		});

		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});

		$(".btn-add-contract").unbind("click").click(function () {
			$(".producttypedetail").show();
			$("#snamecontract").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();
			Systemuser.getallproducts("");
			$('#sname').val("").prop("disabled", false);
			$("#s_productId").attr("disabled", false);
			$("#edit_userid_order").val("");
			$(".btn-save").show();
			$(".btn-save").attr("disabled", false);
			$("#s_productId").attr("disabled", false);
			$("#s_bizChance").val("");
			$("#selected-product-list").empty();
			$("#myModalContract").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});

		//complete-alt
		$.fn.modal.Constructor.prototype.enforceFocus = function () {};
		$('#s_searchProduct').select2({
			"data": Systemuser.totalProductList,
			"placeholder":'选择产品'
		});

		$('#product_type').off().on("change",function(){
			var type = $(this).val();
			var filterProductList = Systemuser.totalProductList.filter(function(product){
				if(type !== 'ALL') {
					return product.group === type;
				} else {
					return true;
				}
			})
			$('#s_searchProduct').select2({
				"data": filterProductList,
				"placeholder":'选择产品'
			});
		})

		$('#s_searchProduct').off().on("select2-selecting",function(e){
			var selectedId = e.val;
			var selectedText = e.object.text;
			var selectedGroup = e.object.group;
			var productInfo = {"id":selectedId,"text":selectedText,"group":selectedGroup};
			if(JSON.stringify(Systemuser.selectedProductList).indexOf(JSON.stringify(productInfo)) === -1){
				Systemuser.selectedProductList.push(productInfo);
				Systemuser.getProductListHtml(productInfo);
			}
		});

		$('#history-product-list').off().on('click','.add-history',function(){
			var selectedId = $(this).data('productid');
			var selectedText = $(this).data('producttext');
			var selectedGroup = $(this).data('productgroup');
			var productInfo = {"id":selectedId,"text":selectedText,"group":selectedGroup};
			if(JSON.stringify(Systemuser.selectedProductList).indexOf(JSON.stringify(productInfo)) === -1){
				Systemuser.selectedProductList.push(productInfo);
				Systemuser.getProductListHtml(productInfo);
			}
		});

		$('#selected-product-list').off().on('click','.del-product-cmd',function(){
			var productLi = $(this).parent()
			var originProductText = productLi.text();
			Systemuser.selectedProductList = Systemuser.selectedProductList.filter(function(obj){
				if(productLi.parent().find('li').length === 1){
					productLi.parent().remove();
				} else{
					productLi.remove();
				}
				return obj.text !== originProductText;
			});
			return false;
		});
		 $('#s_searchStartTime1').unbind().bind('click', Systemuser.initDatePicker);		
	
		 //$('#s_searchStartTime_ECPP').unbind().bind('click', Systemuser.initDatePicker);
		 
		 //$('#s_searchStartTime_PSTNALLSTIE').unbind().bind('click', Systemuser.initDatePicker);	
		 
		 $('#s_searchStartTime_STANDSITE').unbind().bind('click', Systemuser.initDatePicker);	
		 
		 $('#s_searchStartTime_STANDSITE_CC_CALLCENTER_PSTN').unbind().bind('click', Systemuser.initDatePicker);
		 
		 $('#s_searchStartTime_TERMINATE').unbind().bind('click', Systemuser.initDatePicker);
		 
		 
		 $('#s_searchStartTime_PSTN_PERSONAL_CHARGE').unbind().bind('click', Systemuser.initDatePicker);

		 
		 $('#s_searchStartTime_STANDSITE_PSTN_PERSONAL_PACKET').unbind().bind('click', Systemuser.initDatePicker);
		 
		 $('#s_searchStartTime_STANDSITE_CC_CALLCENTER_PSTN_PACKAGE').unbind().bind('click', Systemuser.initDatePicker);
		 
		 $('#smonths_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE').unbind().bind('click', Systemuser.initDatePicker);
		 
		 $('#smonths_CC_CALLCENTER_OLS_MONTHLY_PAY').unbind().bind('click', Systemuser.initDatePicker);
	
		 $('#smonths_CC_CALLCENTER_MONTHLY_PAY').unbind().bind('click', Systemuser.initDatePicker);

		 $('#smonths_CC_CALLCENTER_NUMBER_MONTHLY_PAY').unbind().bind('click', Systemuser.initDatePicker);
		 
		 $('#smonths_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE').unbind().bind('click', Systemuser.initDatePicker);

		 
		$(".btn-delete").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();

		
				mConfirm("是否删除？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/wbxsite/"+id,
						type : "delete",
						success : function (data) {
							msgBox('success', "站点删除成功");
							
								Systemuser.refresh();
							
						},
						error : function (data) {
							msgBox('fail', "站点删除失败");
							
								Systemuser.refresh();
							
						},
						dataType : "html"
					});
				});
			
		});
		
		
		$(".btn-delete-order").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();

		
				mConfirm("是否删除？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/order/"+id,
						type : "delete",
						success : function (data) {
							msgBox('success', "订单删除成功");
							
								Systemuser.refresh();
							
						},
						error : function (data) {
							msgBox('fail', "订单删除失败");
							
								Systemuser.refresh();
							
						},
						dataType : "html"
					});
				});
			
		});
	
		
		$(".btn-save").unbind("click").click(function () {
			console.log("9999---");
			var modulesArray = new Array();
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
			business.siteName = $.trim($('#sname').val());
			business.timeZone = $.trim($('#s_sserviceid').val());
			business.primaryLanguage = $.trim($('#s_languageid').val());
			business.timeZone = $.trim($('#s_sserviceid').val());
			business.contractId = Systemuser.contractId;
			business.location = $.trim($('#s_location').val());
			
			if($('.SIMPLIFIED_CHINESE').prop("checked")){
				modulesArray.push("SIMPLIFIED_CHINESE");
			}
			if($('.TRADITIONAL_CHINESE').prop("checked")){
				modulesArray.push("TRADITIONAL_CHINESE");
			}
			if($('.ENGLISH').prop("checked")){
				modulesArray.push("ENGLISH");
			}
			if($('.JAPANESE').prop("checked")){
				modulesArray.push("JAPANESE");
			}
			if($('.KOREAN').prop("checked")){
				modulesArray.push("KOREAN");
			}
			if($('.FRENCH').prop("checked")){
				modulesArray.push("FRENCH");
			}
			if($('.GERMAN').prop("checked")){
				modulesArray.push("GERMAN");
			}
			if($('.ITALIAN').prop("checked")){
				modulesArray.push("ITALIAN");
			}
			if($('.SPANISH').prop("checked")){
				modulesArray.push("SPANISH");
			}
			if($('.SPANISH_CASTILLA').prop("checked")){
				modulesArray.push("SPANISH_CASTILLA");
			}
			if($('.SWEDISH').prop("checked")){
				modulesArray.push("SWEDISH");
			}
			if($('.PORTUGUESE').prop("checked")){
				modulesArray.push("PORTUGUESE");
			}
			if($('.RUSSIAN').prop("checked")){
				modulesArray.push("RUSSIAN");
			}
			if($('.TURKEY').prop("checked")){
				modulesArray.push("TURKEY");
			}
			if($('.HOLLAND').prop("checked")){
				modulesArray.push("HOLLAND");
			}
			if($('.DANISH').prop("checked")){
				modulesArray.push("DANISH");
			}
			business.additionalLanguages=modulesArray;
			if($.trim($('#edit_userid').val())==""){
				
				var postURL = G_CTX_ROOT + "/v1/wbxsite/";
				var update = false;
				var calltype = "post";
			}else{
				
				var postURL = G_CTX_ROOT + "/v1/wbxsite/"+$.trim($('#edit_userid').val());
				var update = false;
				var calltype = "put";
				
			}
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
					
					msgBox('success', "站点修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "only_drafting_contract_allowed") {
						$(".btn-save").attr("disabled", false);
						validator.clearForm();
						$("#edit_userid").val("");
						$("#myModal").modal("hide");
						$('#sname').val("");
						
						msgBox('fail', "只有草稿合同可以增加站点！");
						Systemuser.dataTable.fnDraw();
						return;
					}
					
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "user.already.existed") {
						$(".btn-save").attr("disabled", false);
						$("#sadmin").nextAll().filter("span").html("<font color=red >管理员账号已经被使用！</font>");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "duplicated.crm.cutomer.id") {
						$(".btn-save").attr("disabled", false);
						$("#scrmcustomerid").nextAll().filter("span").html("<font color=red >CRMCustomerID已经被使用！</font>");
						return;
					}
					
					
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModal").modal("hide");
					$('#sname').val("");
					
					msgBox('success', "站点增加成功");
					Systemuser.refresh();
				},
			});
		});
		
		
		 $(".showallornot").unbind("click").click(function () {
			 
				if($(".showallornot").text()=="显示所有国家和地区"){
					$(".showallornot").text("隐藏部分国家和地区");
					
					
				}else{
					$(".showallornot").text("显示所有国家和地区");
					
				}
				$(".geater100").toggle();
	          
	      });
		 
		 $(".btn-save-order-MONTHLY_PAY_BY_ACTIVEHOSTS").unbind("click").click(function () {
				console.log("9999---");
				
				$(".btn-save-order-MONTHLY_PAY_BY_ACTIVEHOSTS").attr("disabled", true);
		
					var business = {};
					business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_ACTIVEHOSTS').val());	
					business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_ACTIVEHOSTS').val());	
					business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_ACTIVEHOSTS').val());
					
					var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_ACTIVEHOSTS').val());
					var update = false;
					var calltype = "put";
					
				
				var postData = JSON.stringify(business);
				$.ajaxInvoke({
					url : postURL,
					type : calltype,
					data : postData,
					dataType: "html",
					success : function (data) {
					
						$(".btn-save-order-MONTHLY_PAY_BY_ACTIVEHOSTS").attr("disabled", false);
						validator.clearForm();
						$("#edit_userid_order_MONTHLY_PAY_BY_ACTIVEHOSTS").val("");
						$("#myModal_MONTHLY_PAY_BY_ACTIVEHOSTS").modal("hide");
						
						
						msgBox('success', "资费修改成功");
						Systemuser.refresh();
					},
					error : function (data) {
						$(".btn-save-order-MONTHLY_PAY_BY_ACTIVEHOSTS").attr("disabled", false);
						validator.clearForm();
						$("#edit_userid_order_MONTHLY_PAY_BY_ACTIVEHOSTS").val("");
						$("#myModal_MONTHLY_PAY_BY_ACTIVEHOSTS").modal("hide");
						
						
						msgBox('fail', "资费修改失败");
						Systemuser.refresh();
					},
				});
			}); 
		 
		
		$(".btn-save-order-MONTHLY_PAY_BY_HOSTS").unbind("click").click(function () {
			console.log("9999---");
			
			$(".btn-save-order-MONTHLY_PAY_BY_HOSTS").attr("disabled", true);
	
				var business = {};
				business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_HOSTS').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_HOSTS').val());	
				business.HOSTS_AMOUNT = $.trim($('#shosts_MONTHLY_PAY_BY_HOSTS').val());
				business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_HOSTS').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_HOSTS').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_HOSTS").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_HOSTS").val("");
					$("#myModal_MONTHLY_PAY_BY_HOSTS").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_HOSTS").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_HOSTS").val("");
					$("#myModal_MONTHLY_PAY_BY_HOSTS").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-CC_CALLCENTER_MONTHLY_PAY").unbind("click").click(function () {
			console.log("9999---");
			
			$(".btn-save-order-CC_CALLCENTER_MONTHLY_PAY").attr("disabled", true);
	
				var business = {};
				
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_CC_CALLCENTER_MONTHLY_PAY').val());	
				business.HOSTS_AMOUNT = $.trim($('#shosts_CC_CALLCENTER_MONTHLY_PAY').val());
				business.EFFECTIVE_BEFORE = $.trim($('#smonths_CC_CALLCENTER_MONTHLY_PAY').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_CC_CALLCENTER_MONTHLY_PAY').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-CC_CALLCENTER_MONTHLY_PAY").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_MONTHLY_PAY").val("");
					$("#myModal_CC_CALLCENTER_MONTHLY_PAY").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-CC_CALLCENTER_MONTHLY_PAY").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_MONTHLY_PAY").val("");
					$("#myModal_CC_CALLCENTER_MONTHLY_PAY").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-CC_CALLCENTER_OLS_MONTHLY_PAY").unbind("click").click(function () {
			console.log("9999---");
			
			$(".btn-save-order-CC_CALLCENTER_OLS_MONTHLY_PAY").attr("disabled", true);
	
				var business = {};
				
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_CC_CALLCENTER_OLS_MONTHLY_PAY').val());	
				business.HOSTS_AMOUNT = $.trim($('#shosts_CC_CALLCENTER_OLS_MONTHLY_PAY').val());
				business.EFFECTIVE_BEFORE = $.trim($('#smonths_CC_CALLCENTER_OLS_MONTHLY_PAY').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_CC_CALLCENTER_OLS_MONTHLY_PAY').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-CC_CALLCENTER_OLS_MONTHLY_PAY").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_OLS_MONTHLY_PAY").val("");
					$("#myModal_CC_CALLCENTER_OLS_MONTHLY_PAY").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-CC_CALLCENTER_OLS_MONTHLY_PAY").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_OLS_MONTHLY_PAY").val("");
					$("#myModal_CC_CALLCENTER_OLS_MONTHLY_PAY").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-CC_CALLCENTER_NUMBER_MONTHLY_PAY").unbind("click").click(function () {
			console.log("9999---");
			
			$(".btn-save-order-CC_CALLCENTER_NUMBER_MONTHLY_PAY").attr("disabled", true);
	
				var business = {};
				business.COMMON_SITE = $.trim($('#s_siteId_CC_CALLCENTER_NUMBER_MONTHLY_PAY').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_CC_CALLCENTER_NUMBER_MONTHLY_PAY').val());	
				business.HOSTS_AMOUNT = $.trim($('#shosts_CC_CALLCENTER_NUMBER_MONTHLY_PAY').val());
				business.EFFECTIVE_BEFORE = $.trim($('#smonths_CC_CALLCENTER_NUMBER_MONTHLY_PAY').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_CC_CALLCENTER_NUMBER_MONTHLY_PAY').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-CC_CALLCENTER_NUMBER_MONTHLY_PAY").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_NUMBER_MONTHLY_PAY").val("");
					$("#myModal_CC_CALLCENTER_NUMBER_MONTHLY_PAY").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-CC_CALLCENTER_NUMBER_MONTHLY_PAY").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_NUMBER_MONTHLY_PAY").val("");
					$("#myModal_CC_CALLCENTER_NUMBER_MONTHLY_PAY").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		
		$(".btn-save-order-CMR_MONTHLY_PAY").unbind("click").click(function () {
			console.log("9999---");
			
			$(".btn-save-order-CMR_MONTHLY_PAY").attr("disabled", true);
	
				var business = {};
				business.PORTS_AMOUNT = $.trim($('#shosts_CMR_MONTHLY_PAY').val());
				business.COMMON_OVERFLOW_UNIT_PRICE = $.trim($('#soverprices_CMR_MONTHLY_PAY').val());
				business.COMMON_SITE = $.trim($('#s_siteId_CMR_MONTHLY_PAY').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_CMR_MONTHLY_PAY').val());	
				business.MONTH_AMOUNT = $.trim($('#smonths_CMR_MONTHLY_PAY').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_CMR_MONTHLY_PAY').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-CMR_MONTHLY_PAY").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CMR_MONTHLY_PAY").val("");
					$("#myModal_CMR_MONTHLY_PAY").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-CMR_MONTHLY_PAY").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CMR_MONTHLY_PAY").val("");
					$("#myModal_CMR_MONTHLY_PAY").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-MONTHLY_PAY_BY_PORTS").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_PORTS").attr("disabled", true);
	
				var business = {};
				business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_PORTS').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_PORTS').val());
				business.COMMON_OVERFLOW_UNIT_PRICE = $.trim($('#soverprices_MONTHLY_PAY_BY_PORTS').val());
				business.PORTS_AMOUNT = $.trim($('#shosts_MONTHLY_PAY_BY_PORTS').val());
				business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_PORTS').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_PORTS').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_PORTS").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_PORTS").val("");
					$("#myModal_MONTHLY_PAY_BY_PORTS").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_PORTS").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_PORTS").val("");
					$("#myModal_MONTHLY_PAY_BY_PORTS").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-MONTHLY_PAY_BY_TOTAL_ATTENDEES").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_TOTAL_ATTENDEES").attr("disabled", true);
	
				var business = {};
				business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_TOTAL_ATTENDEES').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES').val());
				business.COMMON_OVERFLOW_UNIT_PRICE = $.trim($('#soverprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES').val());
				business.PORTS_AMOUNT = $.trim($('#shosts_MONTHLY_PAY_BY_TOTAL_ATTENDEES').val());
				business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_TOTAL_ATTENDEES').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_TOTAL_ATTENDEES').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_TOTAL_ATTENDEES").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val("");
					$("#myModal_MONTHLY_PAY_BY_TOTAL_ATTENDEES").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_TOTAL_ATTENDEES").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val("");
					$("#myModal_MONTHLY_PAY_BY_TOTAL_ATTENDEES").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-MONTHLY_PAY_BY_STO").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_STO").attr("disabled", true);
	
				var business = {};
				business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_STO').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_STO').val());	
				business.STORAGE_SIZE = $.trim($('#shosts_MONTHLY_PAY_BY_STO').val());
				business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_STO').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_STO').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_STO").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_STO").val("");
					$("#myModal_MONTHLY_PAY_BY_STO").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_STO").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_STO").val("");
					$("#myModal_MONTHLY_PAY_BY_STO").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		$(".btn-save-order-PSTN_PERSONAL_PACKET").unbind("click").click(function () {
			
			
			$(".btn-save-order-PSTN_PERSONAL_PACKET").attr("disabled", true);
		
				var business = {};
				//business.COMMON_SITE = $.trim($('#s_siteId_PSTN_PERSONAL_PACKET').val());	
				business.TOTAL_PRICE = $.trim($('#sprices_PSTN_PERSONAL_PACKET').val());	
				business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_PSTN_PERSONAL_PACKET').val());
				business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_STANDSITE_PSTN_PERSONAL_PACKET').val());
				business.DISPLAY_NAME = $.trim($('#susername_PSTN_PERSONAL_PACKET').val());

				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_PSTN_PERSONAL_PACKET').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-PSTN_PERSONAL_PACKET").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_PSTN_PERSONAL_PACKET").val("");
					$("#myModal_PSTN_PERSONAL_PACKET").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-PSTN_PERSONAL_PACKET").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_PSTN_PERSONAL_PACKET").val("");
					$("#myModal_PSTN_PERSONAL_PACKET").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});	
		
		$(".btn-save-order-CC_CALLCENTER_PSTN_PACKAGE").unbind("click").click(function () {
			
			
			$(".btn-save-order-CC_CALLCENTER_PSTN_PACKAGE").attr("disabled", true);
		
				var business = {};
				//business.COMMON_SITE = $.trim($('#s_siteId_CC_CALLCENTER_PSTN_PACKAGE').val());	
				business.TOTAL_PRICE = $.trim($('#sprices_CC_CALLCENTER_PSTN_PACKAGE').val());	
				business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_CC_CALLCENTER_PSTN_PACKAGE').val());
				business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_STANDSITE_CC_CALLCENTER_PSTN_PACKAGE').val());
				

				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_CC_CALLCENTER_PSTN_PACKAGE').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-CC_CALLCENTER_PSTN_PACKAGE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_PSTN_PACKAGE").val("");
					$("#myModal_CC_CALLCENTER_PSTN_PACKAGE").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-CC_CALLCENTER_PSTN_PACKAGE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_PSTN_PACKAGE").val("");
					$("#myModal_CC_CALLCENTER_PSTN_PACKAGE").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});		
		$(".btn-save-order-MONTHLY_PAY_BY_STO_O").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_STO_O").attr("disabled", true);
	
				var business = {};
				business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_STO_O').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_STO_O').val());	
				business.STORAGE_SIZE = $.trim($('#shosts_MONTHLY_PAY_BY_STO_O').val());
				business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_STO_O').val());
				business.COMMON_OVERFLOW_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_STO_O_OVERPRICE').val());	
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_STO_O').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_STO_O").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_STO_O").val("");
					$("#myModal_MONTHLY_PAY_BY_STO_O").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_STO_O").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_STO_O").val("");
					$("#myModal_MONTHLY_PAY_BY_STO_O").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-MONTHLY_PAY_BY_TELECOM").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_TELECOM").attr("disabled", true);
	
				var business = {};
				business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_TELECOM').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_TELECOM').val());
				business.DISPLAY_NAME = $.trim($('#susername_MONTHLY_PAY_BY_TELECOM').val());	
				business.INITIAL_PASSWORD = $.trim($('#spass_MONTHLY_PAY_BY_TELECOM').val());	
				business.ENTERPRISE_CODE = $.trim($('#senterprisecode_MONTHLY_PAY_BY_TELECOM').val());	
				business.ENTERPRISE_NAME = $.trim($('#senterprisename_MONTHLY_PAY_BY_TELECOM').val());	
				business.WEBEX_ID = $.trim($('#shosts_MONTHLY_PAY_BY_TELECOM').val());				
				business.HOSTS_AMOUNT = 1;
				business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_TELECOM').val());
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_TELECOM').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_TELECOM").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_TELECOM").val("");
					$("#myModal_MONTHLY_PAY_BY_TELECOM").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_TELECOM").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_TELECOM").val("");
					$("#myModal_MONTHLY_PAY_BY_TELECOM").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});	
		
		
		$(".btn-save-order-MONTHLY_PAY_BY_ECPP").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_ECPP").attr("disabled", true);
	
				var business = {};
				business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_ECPP').val());	
				business.TOTAL_PRICE = $.trim($('#shosts_MONTHLY_PAY_BY_ECPP').val());				
				business.MONTH_AMOUNT = $.trim($('#s_searchStartTime_ECPP').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_ECPP').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_ECPP").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_ECPP").val("");
					$("#myModal_MONTHLY_PAY_BY_ECPP").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_ECPP").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_ECPP").val("");
					$("#myModal_MONTHLY_PAY_BY_ECPP").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-MONTHLY_PAY_BY_ECPU").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_ECPU").attr("disabled", true);
	
				var business = {};
				business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_ECPU').val());	
				business.TIMES = $.trim($('#shosts_MONTHLY_PAY_BY_ECPU').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_ECPU').val());	
				business.MONTH_AMOUNT = $.trim($('#s_searchStartTime_ECPU').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_ECPU').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_ECPU").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_ECPU").val("");
					$("#myModal_MONTHLY_PAY_BY_ECPU").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_ECPU").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_ECPU").val("");
					$("#myModal_MONTHLY_PAY_BY_ECPU").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});


		$(".btn-save-order-MONTHLY_PAY_BY_PSTNALLSTIE").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_PSTNALLSTIE").attr("disabled", true);
		
				var business = {};
				business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_PSTNALLSTIE').val());	
				business.TOTAL_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_PSTNALLSTIE').val());	
				business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_MONTHLY_PAY_BY_PSTNALLSTIE').val());
				business.MONTH_AMOUNT = $.trim($('#s_searchStartTime_PSTNALLSTIE').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_PSTNALLSTIE').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNALLSTIE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNALLSTIE").val("");
					$("#myModal_MONTHLY_PAY_BY_PSTNALLSTIE").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNALLSTIE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNALLSTIE").val("");
					$("#myModal_MONTHLY_PAY_BY_PSTNALLSTIE").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		$(".btn-save-order-MONTHLY_PAY_BY_PSTNMUlSTIE").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_PSTNMUlSTIE").attr("disabled", true);
		
				var business = {};
			var sites = [];
			sites.push($.trim($('#s_siteId_MONTHLY_PAY_BY_PSTNMUlSTIE').val()));
				business.COMMON_SITES = sites;
				business.TOTAL_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_PSTNMUlSTIE').val());	
				business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_MONTHLY_PAY_BY_PSTNMUlSTIE').val());
				business.MONTH_AMOUNT = $.trim($('#s_searchStartTime_PSTNMUlSTIE').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_PSTNMUlSTIE').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNMUlSTIE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNMUlSTIE").val("");
					$("#myModal_MONTHLY_PAY_BY_PSTNMUlSTIE").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNMUlSTIE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNMUlSTIE").val("");
					$("#myModal_MONTHLY_PAY_BY_PSTNMUlSTIE").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").attr("disabled", true);
		
				var business = {};
				business.COMMON_SITES = new Array($.trim($('#s_siteId_MONTHLY_PAY_BY_STANDSITE').val()));
				//business.TOTAL_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_STANDSITE').val());	
				//business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_MONTHLY_PAY_BY_STANDSITE').val());
				business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_STANDSITE').val());
				business.SUPPORT_BILL_SPLIT=0;
				if($('.SUPPORT_BILL_SPLIT').prop("checked")){
					business.SUPPORT_BILL_SPLIT=1;
				}
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_STANDSITE').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_STANDSITE").val("");
					//$("#myModal_MONTHLY_PAY_BY_STANDSITE").modal("hide");
					
					Systemuser.saverates();
					//msgBox('success', "资费修改成功");
					//Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_STANDSITE").val("");
					$("#edit_userid_order_MONTHLY_PAY_BY_STANDSITE_RATES").val("");
					$("#myModal_MONTHLY_PAY_BY_STANDSITE").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		
		$(".btn-save-order-CC_CALLCENTER_PSTN").unbind("click").click(function () {
			
			
			$(".btn-save-order-CC_CALLCENTER_PSTN").attr("disabled", true);
		
				var business = {};
				
				business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_STANDSITE_CC_CALLCENTER_PSTN').val());
			
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_CC_CALLCENTER_PSTN').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-CC_CALLCENTER_PSTN").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_PSTN").val("");
					//$("#myModal_CC_CALLCENTER_PSTN").modal("hide");
					
					Systemuser.saveccrates();
					//msgBox('success', "资费修改成功");
					//Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-CC_CALLCENTER_PSTN").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_PSTN").val("");
					$("#edit_userid_order_CC_CALLCENTER_PSTN_RATES").val("");
					$("#myModal_CC_CALLCENTER_PSTN").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-MONTHLY_PAY_BY_PSTNMONTH").unbind("click").click(function () {
			
			var modulesArray = new Array();
			$(".btn-save-order-MONTHLY_PAY_BY_PSTNMONTH").attr("disabled", true);
	
				var business = {};
				modulesArray.push($.trim($('#s_siteId_MONTHLY_PAY_BY_PSTNMONTH').val()));
				business.COMMON_SITES = modulesArray;	
				business.MONTHLY_FEE = $.trim($('#sprices_MONTHLY_PAY_BY_PSTNMONTH').val());	
				business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_MONTHLY_PAY_BY_PSTNMONTH').val());
				business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_PSTNMONTH').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_PSTNMONTH').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNMONTH").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNMONTH").val("");
					$("#myModal_MONTHLY_PAY_BY_PSTNMONTH").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNMONTH").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNMONTH").val("");
					$("#myModal_MONTHLY_PAY_BY_PSTNMONTH").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").unbind("click").click(function () {
			
		
			$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").attr("disabled", true);
	
				var business = {};
				
				business.MONTHLY_FEE = $.trim($('#sprices_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE').val());	
				//business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE').val());
				business.HOSTS_AMOUNT = $.trim($('#shostsamount_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE').val());
				business.EFFECTIVE_BEFORE = $.trim($('#smonths_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").val("");
					$("#myModal_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").val("");
					$("#myModal_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").unbind("click").click(function () {
			
			
			$(".btn-save-order-CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").attr("disabled", true);
	
				var business = {};
				
				business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE').val());
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE').val());
				business.EFFECTIVE_BEFORE = $.trim($('#smonths_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val("");
					$("#myModal_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val("");
					$("#myModal_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		$(".btn-save-order-MISC_CHARGE").unbind("click").click(function () {
			
			
			$(".btn-save-order-MISC_CHARGE").attr("disabled", true);
	
				var business = {};
				
				business.TOTAL_PRICE = $.trim($('#sprices_PAY_BY_MISC').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MISC_CHARGE').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MISC_CHARGE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MISC_CHARGE").val("");
					$("#myModal_MISC_CHARGE").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MISC_CHARGE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MISC_CHARGE").val("");
					$("#myModal_MISC_CHARGE").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-MONTHLY_PAY_PERSONAL_WEBEX").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_PERSONAL_WEBEX").attr("disabled", true);
	
				var business = {};
				
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_PERSONAL_WEBEX').val());
				business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_PERSONAL_WEBEX').val());
				business.DISPLAY_NAME = $.trim($('#susername_MONTHLY_PAY_PERSONAL_WEBEX').val());
				business.FULL_NAME = $.trim($('#sfullname_MONTHLY_PAY_PERSONAL_WEBEX').val());
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_MONTHLY_PAY_PERSONAL_WEBEX').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_PERSONAL_WEBEX").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_PERSONAL_WEBEX").val("");
					$("#myModal_MONTHLY_PAY_PERSONAL_WEBEX").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-MONTHLY_PAY_PERSONAL_WEBEX").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_PERSONAL_WEBEX").val("");
					$("#myModal_MONTHLY_PAY_PERSONAL_WEBEX").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});
		
		
		$(".btn-save-order-MONTHLY_PAY_BY_TERMINATE").unbind("click").click(function () {
			console.log("9999---");
			
			$(".btn-save-order-MONTHLY_PAY_BY_TERMINATE").attr("disabled", true);
	
				var business = {};
				business.type  = $.trim($('#s_siteId_MONTHLY_PAY_BY_TERMINATE').val());	
				business.date = $.trim($('#s_searchStartTime_TERMINATE').val());	
				
				var postURL = G_CTX_ROOT + "/v1/order/terminate/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_TERMINATE').val());
				var update = false;
				var calltype = "post";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				success : function (data) {
				
					$(".btn-save-order-MONTHLY_PAY_BY_TERMINATE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_TERMINATE").val("");
					$("#myModal_MONTHLY_PAY_BY_TERMINATE").modal("hide");
					
					
					msgBox('success', "中止成功");
					Systemuser.refresh();
				},
				error : function (data) {
					
				
					
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "user.already.existed") {
						$(".btn-save").attr("disabled", false);
						$("#sadmin").nextAll().filter("span").html("<font color=red >管理员账号已经被使用！</font>");
						return;
					}
				
					
					
					$(".btn-save-order-MONTHLY_PAY_BY_TERMINATE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_MONTHLY_PAY_BY_TERMINATE").val("");
					$("#myModal_MONTHLY_PAY_BY_TERMINATE").modal("hide");
					
					
					msgBox('fail', "中止失败");
					Systemuser.refresh();
				},
				dataType: "html"
			});
		});
		
		
		$(".btn-save-order-PSTN_PERSONAL_CHARGE").unbind("click").click(function () {
		
			
			$(".btn-save-order-PSTN_PERSONAL_CHARGE").attr("disabled", true);
			var pstnRates4Put = {};
				pstnRates4Put.rates=[];
				pstnRates4Put.pid=$.trim($('#edit_userid_order_PSTN_PERSONAL_CHARGE').val());
				$(".raterow").each(function(){
					var rate = {};					
					rate.code=$(this).find("input[type=hidden][name=rateCode]").val();
					rate.rate=parseFloat($(this).find("input[type=text][name="+rate.code+"1]").val());
					rate.serviceRate=parseFloat($(this).find("input[type=text][name="+rate.code+"2]").val());
					pstnRates4Put.rates.push(rate);
				});
				
				//business.COMMON_SITES = new Array($.trim($('#s_siteId_PSTN_PERSONAL_CHARGE').val()));
				//business.TOTAL_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_STANDSITE').val());	
				//business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_MONTHLY_PAY_BY_STANDSITE').val());
				//business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_STANDSITE').val());
				
				var postURL = G_CTX_ROOT + "/v1/pstnrate/"+$.trim($('#edit_userid_order_PSTN_PERSONAL_CHARGE').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(pstnRates4Put);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
                dataType: "html",
				success : function (data) {			
				
					Systemuser.savesites();
				},
				error : function (data) {
					$(".btn-save-order-PSTN_PERSONAL_CHARGE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_PSTN_PERSONAL_CHARGE").val("");
					$("#myModal_PSTN_PERSONAL_CHARGE").modal("hide");
					
					
					msgBox('fail', "资费修改失败");
					Systemuser.refresh();
				},
			});
		});

		$("#myModalContract").on('show.bs.modal',function(){
			Systemuser.selectedProductList = [];
			//$('#product_type').find("option[value='ALL']").attr("selected",true);
		});
		
		
		$(".btn-save-order").unbind("click").click(function () {
			console.log("99991---");
			
			$(".btn-save-order").attr("disabled", true);
			if($.trim($('#edit_userid_order').val())==""){
				productId=$('input[name="rateCode"]:checked').val();
				var business = {};
				business.productIds = [];

				var list = JSON.parse(JSON.stringify(Systemuser.selectedProductList));
				for (var i = 0; i<list.length; i++) {
					business.productIds.push(list[i].id);
				}
				/*
				$(".checkbox-item").each(function(){
					if($(this).is(':checked')){
						business.productIds.push($(this).val());
					}
				});
				*/
				business.startDate = $.trim($('#s_searchStartTime1').val())+" 00:00:00";
				business.payInterval = $.trim($('#s_payInterval').val());
				business.bizChance = $.trim($('#s_bizChance').val());
				business.contractId = Systemuser.contractId;
				
				var postURL = G_CTX_ROOT + "/v1/order/";
				var update = false;
				var calltype = "post";
			}else{
				var business = {};
					
				business.startDate = $.trim($('#s_searchStartTime1').val())+" 00:00:00";
				business.payInterval = $.trim($('#s_payInterval').val());
				business.bizChance = $.trim($('#s_bizChance').val());
				
				var postURL = G_CTX_ROOT + "/v1/order/"+$.trim($('#edit_userid_order').val());
				var update = false;
				var calltype = "put";
				
			}
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				success : function (data) {
				
					$(".btn-save-order").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModalContract").modal("hide");
					$('#sname').val("");

					//添加历史记录
					var arrHistoryRecord = []
					if ($.cookie('bssHistoryProducts')) {
						arrHistoryRecord = JSON.parse($.cookie('bssHistoryProducts'));
					}

					if (Array.isArray(arrHistoryRecord) && arrHistoryRecord.length > 0){
						while(list && list.length){
							var ele = list.shift();
							if(JSON.stringify(arrHistoryRecord).indexOf(JSON.stringify(ele)) === -1){
								arrHistoryRecord.push(ele);
							}
						}
						while(arrHistoryRecord.length > 10){
							console.log('now',arrHistoryRecord.length);
							arrHistoryRecord.shift();
						}
					} else {
						arrHistoryRecord = list;
					}
					var strHistoryProducts = JSON.stringify(arrHistoryRecord);
					$.cookie('bssHistoryProducts', null);
					$.cookie('bssHistoryProducts', strHistoryProducts, { expires: 30 });

					Systemuser.selectedProductList = [];
					$('#selected-product-list').empty();
					$('#s_bizChance').val('');

					msgBox('success', "添加成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModalContract").modal("hide");
					$('#sname').val("");

					Systemuser.selectedProductList = [];
					$('#selected-product-list').empty();

					msgBox('fail', "操作失败");

					Systemuser.refresh();
				},
				dataType: "html"
			});
		});
	},
	
	
	
	
    
	
	getUserDetail : function (userid) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/wbxsite/draft/" + userid,
			data : "",
			type : "get",
			success : function (data) {
				console.log("----",data);
				$("#sname").val(data.siteName);
				$("#edit_username").val(data.siteName);				
				$("#s_languageid").val(data.primaryLanguage);
				$("#s_sserviceid").val(data.timeZone);				
				if(data.location.substring(0,3)=="TSP"){					
					Systemuser.createTSPoptions();
					$("input[name='pstnradio'][value='tsp']").prop("checked",true);
				}else{
					Systemuser.createPSTNoptions();
					$("input[name='pstnradio'][value='pstn']").prop("checked",true);
				}
				$("#s_location").val(data.location);
				//$("#sadmin").prop("disabled", true);			
				$.each(data.additionalLanguage, function (key, value) {
					if(value=="SIMPLIFIED_CHINESE"){
						$('.SIMPLIFIED_CHINESE').prop("checked",true);
					}
					if(value=="TRADITIONAL_CHINESE"){
						$('.TRADITIONAL_CHINESE').prop("checked",true);
					}
					if(value=="ENGLISH"){
						$('.ENGLISH').prop("checked",true);
					}
					if(value=="JAPANESE"){
						$('.JAPANESE').prop("checked",true);
					}
					if(value=="KOREAN"){
						$('.KOREAN').prop("checked",true);
					}
					if(value=="FRENCH"){
						$('.FRENCH').prop("checked",true);
					}
					if(value=="GERMAN"){
						$('.GERMAN').prop("checked",true);
					}
					if(value=="ITALIAN"){
						$('.ITALIAN').prop("checked",true);
					}
					if(value=="SPANISH"){
						$('.SPANISH').prop("checked",true);
					}
					if(value=="SPANISH_CASTILLA"){
						$('.SPANISH_CASTILLA').prop("checked",true);
					}
					if(value=="SWEDISH"){
						$('.SWEDISH').prop("checked",true);
					}
					if(value=="HOLLAND"){
						$('.HOLLAND').prop("checked",true);
					}
					if(value=="PORTUGUESE"){
						$('.PORTUGUESE').prop("checked",true);
					}
					if(value=="RUSSIAN"){
						$('.RUSSIAN').prop("checked",true);
					}
					if(value=="TURKEY"){
						$('.TURKEY').prop("checked",true);
					}
					if(value=="DANISH"){
						$('.DANISH').prop("checked",true);
					}
				
						
				});
				
				
			
			},
		});
	},
	
	getUserDetailOrder : function (userid) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/order/" + userid,
			data : "",
			type : "get",
			success : function (data) {
				console.log("----",data);
				$("#"+data.productId).attr("checked","true");
				$("#s_searchStartTime1").val(data.effectiveStartDate);
				var pi = 'MONTHLY';
				if (data.payInterval == 3) {
					pi = 'QUARTERLY';
				} else if (data.payInterval == 6) {
					pi = 'HALF_YEARLY';
				} else if (data.payInterval == 12) {
					pi = 'YEARLY';
				} else if (data.payInterval == -1) {
					pi = 'ONE_TIME';
				}
				$("#s_payInterval").val(pi);

				if(data.bizChance){
					$("#s_bizChance").val(data.bizChance);
				}
				//$("#sadmin").prop("disabled", true);			
			}
		});
	},
	
	checkName : function (userid) {
		if($.trim($("#edit_username").val())==$.trim($('#sname').val())){
			return;
		}
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/wbxsite/validate/" +Systemuser.contractId+"/"+$.trim($('#sname').val()),
			data : "",
			type : "get",
			success : function (data) {
				console.log("----",data);
				$("#sname").val(data.displayName);
				
							
				//$("#sadmin").prop("disabled", true);			
				
				
				
			
			},
			
			error : function (data) {
				
				
				if (data.status == "409") {
					$(".btn-save").attr("disabled", false);
					$("#sname").nextAll().filter("span").html("<font color=red >站点名字已经被使用！</font>");
					return;
				}
				
				
				
			},
		});
	},

	getallservices: function (){


		console.log("here99------------");
		$.ajaxInvoke({
			url 	: 	G_CTX_ROOT+"/v1/timezone/query" ,
			data 	:	"",
			type : "get",
			success	:	function(returnData){
				//returnData= JSON.parse(returnData);

			
				
				$.each(returnData, function(key,value) { 
					Systemuser.options += '<option value="' + value.value + '">' + value.displayName + '</option>';

					
				});

				$('#s_sserviceid').html(Systemuser.options);
				Systemuser.init();
				//Systemuser.refresh();
			},

		});




	},
	
	
	getallproducts: function (orderid){


		
		Systemuser.optionscontract="";
		$.ajaxInvoke({
			url 	: 	G_CTX_ROOT+"/v1/product/list/" + Systemuser.contractId ,
			data 	:	"",
			type : "get",
			success	:	function(returnData){
				//returnData= JSON.parse(returnData);

				//读取历史记录
				if ($.cookie('bssHistoryProducts')){
					var arrHistoryRecord = JSON.parse($.cookie('bssHistoryProducts'));
					if (arrHistoryRecord) {
						$('#history-product-list').empty();
						for (var i = 0; i< arrHistoryRecord.length; i++) {
							$('#history-product-list').append('<button type="button" class="btn btn-default btn-xs add-history" data-productid="' + arrHistoryRecord[i].id + '"data-producttext="' + arrHistoryRecord[i].text + '"data-productgroup="' + arrHistoryRecord[i].group + '" style="margin: 0 5px 3px 0">' + arrHistoryRecord[i].text + '</span>');
						}
					}
				}
				var originProductList = [];
				for(var i in returnData){
					if (returnData.hasOwnProperty(i)) {
						for (var j in returnData[i]) {
							returnData[i][j].group = i;
						}
						originProductList = originProductList.concat(returnData[i]);
					}
				}
				var stringifyTotalProductList = JSON.stringify(Systemuser.totalProductList);
				for (var i = 0 ;i < originProductList.length ;i++){
					if (stringifyTotalProductList.indexOf(originProductList[i].pid) === -1) {
						Systemuser.totalProductList.push({"id":originProductList[i].pid,"text":originProductList[i].displayName,"group":originProductList[i].group})
					}
				}

				var hostssection=Systemuser.getSectionHtml(returnData.WEBEX_BY_HOSTS);//按用户数计费
				var portssection=Systemuser.getSectionHtml(returnData.WEBEX_BY_PORTS);//按并发数计费
				var pstnsection=Systemuser.getSectionHtmlForbig(returnData.WEBEX_PSTN);//PSTN计费
				var audiosection=Systemuser.getSectionHtmlForbig(returnData.WEBEX_AUDIO_PACKAGE);//语音包
				var storagesection=Systemuser.getSectionHtml(returnData.WEBEX_STORAGE);//存储
				var miscsection=Systemuser.getSectionHtml(returnData.MISC);//杂项
				var ahsection=Systemuser.getSectionHtml(returnData.WEBEX_BY_AH);//按激活用户数计费
				var cmrsection=Systemuser.getSectionHtml(returnData.WEBEX_CMR);//Webex CMR包月
				var ccsection=Systemuser.getSectionHtml(returnData.CC);//天客云
				
				$('#hostssection').html(hostssection);
				$('#portssection').html(portssection);
				$('#pstnsection').html(pstnsection);
				$('#audiosection').html(audiosection);
				$('#storagesection').html(storagesection);
				$('#miscsection').html(miscsection);
				$('#ahsection').html(ahsection);
				$('#cmrsection').html(cmrsection);
				$('#ccsection').html(ccsection);
				if(orderid!=""){
					Systemuser.getUserDetailOrder(orderid);
				}

				
				
			},

		});




	},
	getProductListHtml:function(productInfo){
		var li = "<li class='list-group-item' style='padding:0 5px;height: 26px;line-height: 26px'>"
			+ productInfo.text
			+ "<span class='pull-right btn btn-danger btn-xs fa fa-times del-product-cmd' style='margin-top: 5px;'></span>"
			+ "</li>";
		var idGroup = "#" + productInfo.group;
		if ( $(idGroup).length > 0 ) {
			$(idGroup).append(li);
		} else {
			var groupTitle = '';
			switch(productInfo.group)
			{
				case 'WEBEX_BY_HOSTS':
					groupTitle = '按用户数计费';
					break;
				case 'WEBEX_BY_PORTS':
					groupTitle = '按并发数计费';
					break;
				case 'WEBEX_PSTN':
					groupTitle = 'PSTN计费';
					break;
				case 'WEBEX_AUDIO_PACKAGE':
					groupTitle = '语音包';
					break;
				case 'WEBEX_STORAGE':
					groupTitle = '存储';
					break;
				case 'MISC':
					groupTitle = '杂项';
					break;
				case 'WEBEX_BY_AH':
					groupTitle = '按激活用户数计费';
					break;
				case 'WEBEX_CMR':
					groupTitle = 'Webex CMR包月';
					break;
				case 'CC':
					groupTitle = '天客云';
					break;
				default :
					groupTitle = productInfo.group;
			}
			var ul = "<ul class='list-group' id='"
					+ productInfo.group
					+ "'>"
					+ "<h6 style='margin: 4px 0'>"
					+ groupTitle
					+ "</h6>"
					+ li
					+ "</ul>"
			$('#selected-product-list').append(ul);
		}
	},
	getSectionHtml:function(datas){
		var hostssection="";
		var i=0;
		if (datas == null) {
			return "";
		}
		$.each(datas, function(key,value) { 
			if(i%3==0){
				hostssection +='<div class="row">';
			}
			
			hostssection +='<section class="col col-0.5">'
				+'<input type="checkbox" class="checkbox-item" placeholder="" id="'+value.pid+'" value="'+value.pid+'">'
				+'</section>'
				+'<section class="col col-3">'
				+'<label class="label text-left" style="margin-bottom: 0px">'
				+'<font color="red"></font>'
					+value.displayName
				+'</label>'
				+'</section>';
			
			if(i%3==2){
				hostssection +='</div>';
			}
			i++;
			
		});
		
		return hostssection;
	},
	
	getSectionHtmlForbig:function(datas){
		if (datas == null) {
			return "";
		}
		var hostssection="";
		var i=0;
		$.each(datas, function(key,value) { 
			if(i%2==0){
				hostssection +='<div class="row ">';
			}
			hostssection +='<section class="col col-0.5">'
				+'<input type="checkbox" class="checkbox-item" placeholder="" id="'+value.pid+'" value="'+value.pid+'">'
				+'</section>'
				+'<section class="col col-4.5">'
				+'<label class="label text-left">' 
				+'<font color="red"></font>'
					+value.displayName
				+'</label>'
				+'</section>';
			
			if(i%2==1){
				hostssection +='</div>';
			}
			i++;
			
		});
		
		return hostssection;
	},
	
	getcharge: function (chargeId){


		console.log("here9919------------");
		
		$.ajaxInvoke({
			url 	: 	G_CTX_ROOT+"/v1/charges/"+chargeId ,
			data 	:	"",
			type : "get",
			success	:	function(returnData){
				//returnData= JSON.parse(returnData);
			
			
			
				console.log("data----",returnData);
				if(returnData.type=="MONTHLY_PAY_BY_ACTIVEHOSTS"){
					Systemuser.getsites(returnData.COMMON_SITE);
				
					$("#smonths_MONTHLY_PAY_BY_ACTIVEHOSTS").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_ACTIVEHOSTS").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_ACTIVEHOSTS").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_ACTIVEHOSTS").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_ACTIVEHOSTS").attr("disabled", false);
				}	
				if(returnData.type=="MONTHLY_PAY_BY_HOSTS"){
					Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_HOSTS").val(returnData.HOSTS_AMOUNT);
					$("#smonths_MONTHLY_PAY_BY_HOSTS").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_HOSTS").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_HOSTS").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_HOSTS").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_HOSTS").attr("disabled", false);
				}
				if(returnData.type=="CC_CALLCENTER_MONTHLY_PAY"){					
					$("#shosts_CC_CALLCENTER_MONTHLY_PAY").val(returnData.HOSTS_AMOUNT);
					$("#smonths_CC_CALLCENTER_MONTHLY_PAY").val(returnData.EFFECTIVE_BEFORE);
					$("#sprices_CC_CALLCENTER_MONTHLY_PAY").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_CC_CALLCENTER_MONTHLY_PAY").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_MONTHLY_PAY").val(chargeId);	
					$(".btn-save-order-CC_CALLCENTER_MONTHLY_PAY").attr("disabled", false);
				}
				if(returnData.type=="CC_CALLCENTER_OLS_MONTHLY_PAY"){					
					$("#shosts_CC_CALLCENTER_OLS_MONTHLY_PAY").val(returnData.HOSTS_AMOUNT);
					$("#smonths_CC_CALLCENTER_OLS_MONTHLY_PAY").val(returnData.EFFECTIVE_BEFORE);
					$("#sprices_CC_CALLCENTER_OLS_MONTHLY_PAY").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_CC_CALLCENTER_OLS_MONTHLY_PAY").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_OLS_MONTHLY_PAY").val(chargeId);	
					$(".btn-save-order-CC_CALLCENTER_OLS_MONTHLY_PAY").attr("disabled", false);
				}
				if(returnData.type=="CC_CALLCENTER_NUMBER_MONTHLY_PAY"){			
					$("#smonths_CC_CALLCENTER_NUMBER_MONTHLY_PAY").val(returnData.EFFECTIVE_BEFORE);
					$("#sprices_CC_CALLCENTER_NUMBER_MONTHLY_PAY").val(returnData.COMMON_UNIT_PRICE);
					$("#shosts_CC_CALLCENTER_NUMBER_MONTHLY_PAY").val(returnData.HOSTS_AMOUNT);
					$("#myModal_CC_CALLCENTER_NUMBER_MONTHLY_PAY").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_NUMBER_MONTHLY_PAY").val(chargeId);	
					$(".btn-save-order-CC_CALLCENTER_NUMBER_MONTHLY_PAY").attr("disabled", false);
				}
				if(returnData.type=="CMR_MONTHLY_PAY"){
					Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_CMR_MONTHLY_PAY").val(returnData.PORTS_AMOUNT);
					$("#smonths_CMR_MONTHLY_PAY").val(returnData.MONTH_AMOUNT);
					$("#sprices_CMR_MONTHLY_PAY").val(returnData.COMMON_UNIT_PRICE);
					$("#soverprices_CMR_MONTHLY_PAY").val(returnData.COMMON_OVERFLOW_UNIT_PRICE);
					$("#myModal_CMR_MONTHLY_PAY").modal("show");					
					$("#edit_userid_order_CMR_MONTHLY_PAY").val(chargeId);	
					$(".btn-save-order-CMR_MONTHLY_PAY").attr("disabled", false);
				}			
				
				if(returnData.type=="MONTHLY_PAY_BY_STORAGE"){			
					Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_STO").val(returnData.STORAGE_SIZE);
					$("#smonths_MONTHLY_PAY_BY_STO").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_STO").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_STO").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_STO").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_STO").attr("disabled", false);
				}
				if(returnData.type=="MONTHLY_PAY_BY_STORAGE_O"){			
					Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_STO_O").val(returnData.STORAGE_SIZE);
					$("#smonths_MONTHLY_PAY_BY_STO_O").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_STO_O").val(returnData.COMMON_UNIT_PRICE);
					$("#sprices_MONTHLY_PAY_BY_STO_O_OVERPRICE").val(returnData.COMMON_OVERFLOW_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_STO_O").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_STO_O").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_STO_O").attr("disabled", false);
				}
				if(returnData.type=="MONTHLY_PAY_BY_PORTS"){
					Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_PORTS").val(returnData.PORTS_AMOUNT);
					$("#smonths_MONTHLY_PAY_BY_PORTS").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_PORTS").val(returnData.COMMON_UNIT_PRICE);
					$("#soverprices_MONTHLY_PAY_BY_PORTS").val(returnData.COMMON_OVERFLOW_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_PORTS").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_PORTS").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_PORTS").attr("disabled", false);
				}
				
				if(returnData.type=="MONTHLY_PAY_BY_TOTAL_ATTENDEES"){
					Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val(returnData.PORTS_AMOUNT);
					$("#smonths_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val(returnData.COMMON_UNIT_PRICE);
					$("#soverprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val(returnData.COMMON_OVERFLOW_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_TOTAL_ATTENDEES").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_TOTAL_ATTENDEES").attr("disabled", false);
				}
				
				if(returnData.type=="PSTN_MONTHLY_PACKET"){
					Systemuser.getsites(returnData.COMMON_SITES);
					$("#shosts_MONTHLY_PAY_BY_PSTNMONTH").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#smonths_MONTHLY_PAY_BY_PSTNMONTH").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_PSTNMONTH").val(returnData.MONTHLY_FEE);
					$("#myModal_MONTHLY_PAY_BY_PSTNMONTH").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNMONTH").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNMONTH").attr("disabled", false);
				}
				
				if(returnData.type=="CC_CALLCENTER_PSTN_MONTHLY_PACKAGE"){	
					$("#shostsamount_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").val(returnData.HOSTS_AMOUNT);
					$("#shosts_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#smonths_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").val(returnData.EFFECTIVE_BEFORE);
					$("#sprices_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").val(returnData.MONTHLY_FEE);
					$("#myModal_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").val(chargeId);	
					$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").attr("disabled", false);
				}
				
				if(returnData.type=="CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE"){					
					$("#shosts_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#smonths_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val(returnData.EFFECTIVE_BEFORE);
					$("#sprices_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").modal("show");					
					$("#edit_userid_order_CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val(chargeId);	
					$(".btn-save-order-CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").attr("disabled", false);
				}
				
				if(returnData.type=="PSTN_STANDARD_CHARGE"){
					Systemuser.getsites(returnData.COMMON_SITES);
					$("#pstndiscount_MONTHLY_PAY_BY_PSTNALLSTIE").val("");
					$("#comdiscount_MONTHLY_PAY_BY_PSTNALLSTIE").val("");
					getRates(returnData.PSTN_RATES_ID,"PSTN_STANDARD_CHARGE");
					//$("#shosts_MONTHLY_PAY_BY_STANDSITE").val(returnData.TOTAL_PRICE);
					$("#s_searchStartTime_STANDSITE").val(returnData.EFFECTIVE_BEFORE);
					if(returnData.SUPPORT_BILL_SPLIT==1){
						$(".SUPPORT_BILL_SPLIT").prop("checked", true);
					}else{
						$(".SUPPORT_BILL_SPLIT").prop("checked", false);
					}
					//$("#sprices_MONTHLY_PAY_BY_STANDSITE").val(returnData.TOTAL_PRICE);
					$("#myModal_MONTHLY_PAY_BY_STANDSITE").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_STANDSITE").val(chargeId);	
					$("#edit_userid_order_MONTHLY_PAY_BY_STANDSITE_RATES").val(returnData.PSTN_RATES_ID);	
					$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").attr("disabled", false);
				}
				
				if(returnData.type=="CC_CALLCENTER_PSTN"){					
					$("#pstndiscount_CC_CALLCENTER_PSTN").val("");
					$("#comdiscount_CC_CALLCENTER_PSTN").val("");
					getRates(returnData.PSTN_RATES_ID,"CC_CALLCENTER_PSTN");
					//$("#shosts_MONTHLY_PAY_BY_STANDSITE").val(returnData.TOTAL_PRICE);
					$("#s_searchStartTime_STANDSITE_CC_CALLCENTER_PSTN").val(returnData.EFFECTIVE_BEFORE);
				
					$("#myModal_CC_CALLCENTER_PSTN").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_PSTN").val(chargeId);	
					$("#edit_userid_order_CC_CALLCENTER_PSTN_RATES").val(returnData.PSTN_RATES_ID);	
					$(".btn-save-order-CC_CALLCENTER_PSTN").attr("disabled", false);
				}
				
				if(returnData.type=="EC_PAY_PER_USE"){	
					Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_ECPU").val(returnData.TIMES);
					$("#s_searchStartTime_ECPU").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_ECPU").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_ECPU").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_ECPU").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_ECPU").attr("disabled", false);
				}
				
				if(returnData.type=="EC_PREPAID"){	
					Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_ECPP").val(returnData.TOTAL_PRICE);
					$("#s_searchStartTime_ECPP").val(returnData.MONTH_AMOUNT);
					//$("#sprices_MONTHLY_PAY_BY_ECPP").val(returnData.TOTAL_PRICE);
					$("#myModal_MONTHLY_PAY_BY_ECPP").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_ECPP").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_ECPP").attr("disabled", false);
				}
				if(returnData.type=="PSTN_SINGLE_PACKET_FOR_ALL_SITES"){					
					$("#shosts_MONTHLY_PAY_BY_PSTNALLSTIE").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#s_searchStartTime_PSTNALLSTIE").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_PSTNALLSTIE").val(returnData.TOTAL_PRICE);
					$("#myModal_MONTHLY_PAY_BY_PSTNALLSTIE").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNALLSTIE").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNALLSTIE").attr("disabled", false);
				}
				if(returnData.type=="PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES"){
					Systemuser.getsites(returnData.COMMON_SITES);
					$("#shosts_MONTHLY_PAY_BY_PSTNMUlSTIE").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#s_searchStartTime_PSTNMUlSTIE").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_PSTNMUlSTIE").val(returnData.TOTAL_PRICE);
					$("#myModal_MONTHLY_PAY_BY_PSTNMUlSTIE").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNMUlSTIE").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNMUlSTIE").attr("disabled", false);
				}
				if(returnData.type=="TELECOM_CHARGE"){			
					Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_TELECOM").val(returnData.WEBEX_ID);					
					$("#sprices_MONTHLY_PAY_BY_TELECOM").val(returnData.COMMON_UNIT_PRICE);
					$("#smonths_MONTHLY_PAY_BY_TELECOM").val(returnData.MONTH_AMOUNT);
					$("#senterprisecode_MONTHLY_PAY_BY_TELECOM").val(returnData.ENTERPRISE_CODE);					
					$("#senterprisename_MONTHLY_PAY_BY_TELECOM").val(returnData.ENTERPRISE_NAME);
					$("#spass_MONTHLY_PAY_BY_TELECOM").val(returnData.INITIAL_PASSWORD);					
					$("#susername_MONTHLY_PAY_BY_TELECOM").val(returnData.DISPLAY_NAME);
					$("#myModal_MONTHLY_PAY_BY_TELECOM").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_TELECOM").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_TELECOM").attr("disabled", false);
				}
				if(returnData.type=="MISC_CHARGE"){					
					
					$("#sprices_PAY_BY_MISC").val(returnData.TOTAL_PRICE);	
					$("#myModal_MISC_CHARGE").modal("show");					
					$("#edit_userid_order_MISC_CHARGE").val(chargeId);
					$(".btn-save-order-MISC_CHARGE").attr("disabled", false);
				}
				if(returnData.type=="MONTHLY_PAY_PERSONAL_WEBEX"){			
					$("#ssite_MONTHLY_PAY_PERSONAL_WEBEX").nextAll().filter("span").html("");
					//$("#smonths_MONTHLY_PAY_BY_HOSTS").val(returnData.MONTH_AMOUNT);
					$("#ssite_MONTHLY_PAY_PERSONAL_WEBEX").val(returnData.COMMON_SITE);
					$("#sprices_MONTHLY_PAY_PERSONAL_WEBEX").val(returnData.COMMON_UNIT_PRICE);
					$("#smonths_MONTHLY_PAY_PERSONAL_WEBEX").val(returnData.MONTH_AMOUNT);
					$("#susername_MONTHLY_PAY_PERSONAL_WEBEX").val(returnData.DISPLAY_NAME);
					$("#sfullname_MONTHLY_PAY_PERSONAL_WEBEX").val(returnData.FULL_NAME);
					$("#myModal_MONTHLY_PAY_PERSONAL_WEBEX").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_PERSONAL_WEBEX").val(chargeId);
					$(".btn-save-order-MONTHLY_PAY_PERSONAL_WEBEX").attr("disabled", false);
				}
				
				if(returnData.type=="PSTN_PERSONAL_CHARGE"){
					getRates(returnData.PSTN_RATES_ID,"PSTN_PERSONAL_CHARGE");
					$("#ssite_PSTN_PERSONAL_CHARGE").nextAll().filter("span").html("");
					$("#pstndiscount_PSTN_PERSONAL_CHARGE").val("");
					$("#comdiscount_PSTN_PERSONAL_CHARGE").val("");
					$("#s_searchStartTime_PSTN_PERSONAL_CHARGE").val(returnData.EFFECTIVE_BEFORE);
					$("#susername_PSTN_PERSONAL_CHARGE").val(returnData.DISPLAY_NAME);
					//$("#shosts_MONTHLY_PAY_BY_STANDSITE").val(returnData.TOTAL_PRICE);
					$("#ssite_PSTN_PERSONAL_CHARGE").val(returnData.COMMON_SITE);
					//$("#sprices_MONTHLY_PAY_BY_STANDSITE").val(returnData.TOTAL_PRICE);
					$("#myModal_PSTN_PERSONAL_CHARGE").modal("show");					
					$("#edit_userid_order_PSTN_PERSONAL_CHARGE").val(returnData.PSTN_RATES_ID);	
					$("#edit_userid_order_PSTN_PERSONAL_CHARGE_chargeId").val(chargeId);	
					$(".btn-save-order-PSTN_PERSONAL_CHARGE").attr("disabled", false);
				}
				
				if(returnData.type=="PSTN_PERSONAL_PACKET"){
					$("#ssite_PSTN_PERSONAL_PACKET").nextAll().filter("span").html("");
					$("#ssite_PSTN_PERSONAL_PACKET").val(returnData.COMMON_SITE);
					$("#susername_PSTN_PERSONAL_PACKET").val(returnData.DISPLAY_NAME);
					$("#shosts_PSTN_PERSONAL_PACKET").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#s_searchStartTime_STANDSITE_PSTN_PERSONAL_PACKET").val(returnData.EFFECTIVE_BEFORE);
					$("#sprices_PSTN_PERSONAL_PACKET").val(returnData.TOTAL_PRICE);
					$("#myModal_PSTN_PERSONAL_PACKET").modal("show");					
					$("#edit_userid_order_PSTN_PERSONAL_PACKET").val(chargeId);	
					$(".btn-save-order-PSTN_PERSONAL_PACKET").attr("disabled", false);
				}
				if(returnData.type=="CC_CALLCENTER_PSTN_PACKAGE"){
				
					$("#shosts_CC_CALLCENTER_PSTN_PACKAGE").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#s_searchStartTime_STANDSITE_CC_CALLCENTER_PSTN_PACKAGE").val(returnData.EFFECTIVE_BEFORE);
					$("#sprices_CC_CALLCENTER_PSTN_PACKAGE").val(returnData.TOTAL_PRICE);
					$("#myModal_CC_CALLCENTER_PSTN_PACKAGE").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_PSTN_PACKAGE").val(chargeId);	
					$(".btn-save-order-CC_CALLCENTER_PSTN_PACKAGE").attr("disabled", false);
				}
			},

		});
	},
	
	getsites: function (sitevalue){
		console.log("sites------------");
		Systemuser.optionscontract="";
		$.ajaxInvoke({
			url 	: 	G_CTX_ROOT+"/v1/wbxsite/candidates/"+Systemuser.contractId ,
			data 	:	"",
			type : "get",
			success	:	function(returnData){
				//returnData= JSON.parse(returnData);			
				console.log("data",returnData);	
				$.each(returnData, function(key,value) { 
					Systemuser.optionscontract += '<option value="' + value + '">' + value + '</option>';

					
				});
				$('.s_siteId_MONTHLY_PAY_BY_HOSTS').html(Systemuser.optionscontract);
				if(sitevalue instanceof Array){
					$('.s_siteId_MONTHLY_PAY_BY_HOSTS').val(sitevalue[0]);
				}else{
					$('.s_siteId_MONTHLY_PAY_BY_HOSTS').val(sitevalue);
				}
			},

		});
	},
	
	saverates:function(){
		//$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").attr("disabled", true);
		var pstnRates4Put = {};
			pstnRates4Put.rates=[];
			pstnRates4Put.pid=$.trim($('#edit_userid_order_MONTHLY_PAY_BY_STANDSITE_RATES').val());
			$(".raterow").each(function(){
				var rate = {};					
				rate.code=$(this).find("input[type=hidden][name=rateCode]").val();
				rate.rate=parseFloat($(this).find("input[type=text][name="+rate.code+"1]").val());
				rate.serviceRate=parseFloat($(this).find("input[type=text][name="+rate.code+"2]").val());
				pstnRates4Put.rates.push(rate);
			});
			
			//business.COMMON_SITES = new Array($.trim($('#s_siteId_MONTHLY_PAY_BY_STANDSITE').val()));
			//business.TOTAL_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_STANDSITE').val());	
			//business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_MONTHLY_PAY_BY_STANDSITE').val());
			//business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_STANDSITE').val());
			
			var postURL = G_CTX_ROOT + "/v1/pstnrate/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_STANDSITE_RATES').val());
			var update = false;
			var calltype = "put";
			
		
		var postData = JSON.stringify(pstnRates4Put);
		$.ajaxInvoke({
			url : postURL,
			type : calltype,
			data : postData,
            dataType: "html",
			success : function (data) {
			
				$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").attr("disabled", false);
				validator.clearForm();
				$("#edit_userid_order_MONTHLY_PAY_BY_STANDSITE").val("");
				$("#edit_userid_order_MONTHLY_PAY_BY_STANDSITE_RATES").val("");
				$("#myModal_MONTHLY_PAY_BY_STANDSITE").modal("hide");
				
				
				msgBox('success', "资费修改成功");
				Systemuser.refresh();
			},
			error : function (data) {
				$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").attr("disabled", false);
				validator.clearForm();
				$("#edit_userid_order_MONTHLY_PAY_BY_STANDSITE").val("");
				$("#edit_userid_order_MONTHLY_PAY_BY_STANDSITE_RATES").val("");
				$("#myModal_MONTHLY_PAY_BY_STANDSITE").modal("hide");
				
				
				msgBox('fail', "资费修改失败");
				Systemuser.refresh();
			},
		});
		
	},
	
	
	saveccrates:function(){
		//$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").attr("disabled", true);
		var pstnRates4Put = {};
			pstnRates4Put.rates=[];
			pstnRates4Put.pid=$.trim($('#edit_userid_order_CC_CALLCENTER_PSTN_RATES').val());
			$(".raterow").each(function(){
				var rate = {};					
				rate.code=$(this).find("input[type=hidden][name=rateCode]").val();
				rate.rate=parseFloat($(this).find("input[type=text][name="+rate.code+"1]").val());
				rate.serviceRate=parseFloat($(this).find("input[type=text][name="+rate.code+"2]").val());
				pstnRates4Put.rates.push(rate);
			});
			
			//business.COMMON_SITES = new Array($.trim($('#s_siteId_CC_CALLCENTER_PSTN').val()));
			//business.TOTAL_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_STANDSITE').val());	
			//business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_MONTHLY_PAY_BY_STANDSITE').val());
			//business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_STANDSITE').val());
			
			var postURL = G_CTX_ROOT + "/v1/pstnrate/"+$.trim($('#edit_userid_order_CC_CALLCENTER_PSTN_RATES').val());
			var update = false;
			var calltype = "put";
			
		
		var postData = JSON.stringify(pstnRates4Put);
		$.ajaxInvoke({
			url : postURL,
			type : calltype,
			data : postData,
            dataType: "html",
			success : function (data) {
			
				$(".btn-save-order-CC_CALLCENTER_PSTN").attr("disabled", false);
				validator.clearForm();
				$("#edit_userid_order_CC_CALLCENTER_PSTN").val("");
				$("#edit_userid_order_CC_CALLCENTER_PSTN_RATES").val("");
				$("#myModal_CC_CALLCENTER_PSTN").modal("hide");
				
				
				msgBox('success', "资费修改成功");
				Systemuser.refresh();
			},
			error : function (data) {
				$(".btn-save-order-CC_CALLCENTER_PSTN").attr("disabled", false);
				validator.clearForm();
				$("#edit_userid_order_CC_CALLCENTER_PSTN").val("");
				$("#edit_userid_order_CC_CALLCENTER_PSTN_RATES").val("");
				$("#myModal_CC_CALLCENTER_PSTN").modal("hide");
				
				
				msgBox('fail', "资费修改失败");
				Systemuser.refresh();
			},
		});
		
	},
	
	savesites: function (sitevalue){
		$(".btn-save-order-PSTN_PERSONAL_CHARGE").attr("disabled", true);
		
		var business = {};
		//business.COMMON_SITE = $.trim($('#s_siteId_PSTN_PERSONAL_CHARGE').val());	
		business.DISPLAY_NAME = $.trim($('#susername_PSTN_PERSONAL_CHARGE').val());
		business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_PSTN_PERSONAL_CHARGE').val());				
		
		var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_PSTN_PERSONAL_CHARGE_chargeId').val());
		var update = false;
		var calltype = "put";
		
	
		var postData = JSON.stringify(business);
		$.ajaxInvoke({
			url : postURL,
			type : calltype,
			data : postData,
			dataType: "html",
			success : function (data) {
			
				$(".btn-save-order-PSTN_PERSONAL_CHARGE").attr("disabled", false);
				validator.clearForm();
				$("#edit_userid_order_PSTN_PERSONAL_CHARGE").val("");
				$("#myModal_PSTN_PERSONAL_CHARGE").modal("hide");
				
				
				msgBox('success', "资费修改成功");
				Systemuser.refresh();
			},
			error : function (data) {
				$(".btn-save-order-PSTN_PERSONAL_CHARGE").attr("disabled", false);
				validator.clearForm();
				$("#edit_userid_order_PSTN_PERSONAL_CHARGE").val("");
				$("#myModal_PSTN_PERSONAL_CHARGE").modal("hide");
				
				
				msgBox('fail', "资费修改失败");
				Systemuser.refresh();
			},
		});
	},

	refresh:function(){
		Systemuser.dataTable.fnDraw();
		Systemuser.userTable.fnDraw();
	},
	
	
	createPSTNoptions:function(){
		options='<option value="KETIAN_CT_T30">KETIAN_CT_T30</option>'+
		'<option value="KETIAN_CU_T30">KETIAN_CU_T30</option>'+
		'<option value="KETIAN_FREE_T30">KETIAN_FREE_T30</option>'+
		'<option value="KETIAN_GLOBAL_T30">KETIAN_GLOBAL_T30</option>'+
		'<option value="KETIAN_CT">KETIAN_CT</option>'+
		'<option value="KETIAN_CU">KETIAN_CU</option>'+
		'<option value="KETIAN_FREE">KETIAN_FREE</option>'+
		'<option value="KETIAN_GLOBAL">KETIAN_GLOBAL</option>';
		$("#s_location").html(options);
	},

	
	createTSPoptions:function(){	
		options='<option value="TSP_BIZ2101_RP">TSP_BIZ2101_RP</option>'+
		'<option value="TSP_BIZ2101_BA">TSP_BIZ2101_BA</option>'+
		'<option value="TSP_BIZ2101_ACER">TSP_BIZ2101_ACER</option>'+
		'<option value="TSP_BIZ2101_JNJCHINA">TSP_BIZ2101_JNJCHINA</option>'+
		'<option value="TSP_BIZ2101_HISUNPFIZER">TSP_BIZ2101_HISUNPFIZER</option>'+
		'<option value="TSP_BIZ1001_RP">TSP_BIZ1001_RP</option>'+
		'<option value="TSP_BIZ1001_BA">TSP_BIZ1001_BA</option>'+
		'<option value="TSP_ARK_1">TSP_ARK_1</option>'+
		'<option value="TSP_PGI_APAC_GM3">TSP_PGI_APAC_GM3</option>'+
		'<option value="TSP_INTERCALL">TSP_INTERCALL</option>';	
		$("#s_location").html(options);
	},
	initDatePicker	: function() {
		console.log("timer");
        WdatePicker({
            skin: 'twoer',
            dateFmt: 'yyyy-MM-dd'
           
        });
        return false;
	},
	
	
	
	

	bindLaterEvent:function(){
		console.log("bindinglater");
		

	

	}


}
var rule = {
	
	
	sname : {
		name : "站点名称",
		method : {
			required : true,
			lt : 100,
		},
		onBlur : Systemuser.checkName
	}
	
};
validator.init(rule);
validator.validate();
$(document).ready(Systemuser.getallservices());
