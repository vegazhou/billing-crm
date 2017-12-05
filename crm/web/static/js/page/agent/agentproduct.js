


var OrderColumns=[
 {
		"sTitle" : "产品名称",
		"mData" : "displayName",
		"bSortable" : true,
		"sClass" : "center",
		"render" : function (data) {
			return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
		}
	}, 
	
	
	
	
	
	
	
	
	
	
	{
		"sTitle" : "状态",
		"mData" : "state",
		"bSortable" : false,
		"sClass" : "center",
		"render" : function (data) {
			if (data == "DRAFT") {
				return "草稿";
			} else if (data == "WAITING_APPROVAL") {
				return "待审核";
			} else if (data == "IN_EFFECT") {
				return "已生效";
			}else if (data == "END_OF_LIFE") {
				return "已下架";
			}
			return data;
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
			editChargeDom = "<span class=\"btn btn-success btn-xs btn-charge\">修改资费</span> ";
			activeChargeDom = "<span class=\"btn btn-success btn-xs btn-active\">产品上架</span> ";
			dectiveChargeDom = "<span class=\"btn btn-danger btn-xs btn-deactive\">产品下架</span> ";
			viewChargeDom = "<span class=\"btn btn-success btn-xs btn-charge-view\">查看资费</span> ";
			deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> ";
			if (obj.pid == null) {

			} else {
				if (obj.state == "DRAFT") {
					buttons = editChargeDom + activeChargeDom + deleteDom;
				} else if (obj.state == "IN_EFFECT") {
					buttons = viewChargeDom + dectiveChargeDom;
				}
				else {
					buttons = viewChargeDom;
				}
			}
			return buttons + "<input type='hidden' name='chargeId' value='" + obj.chargeSchemeId + "'/><input type='hidden' name='sysId' value='" + obj.pid + "'/>";
		}
	}, ];

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
		Systemuser.contractId=parameter.substring(0,parameter.indexOf("&"));
		Systemuser.contractName=decodeURIComponent(parameter.split('&name=')[1].substring(0,parameter.split('&name=')[1].indexOf("&")));
		Systemuser.status=decodeURIComponent(parameter.split('&status=')[1]);
		$(".contractName").html(Systemuser.contractName);
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/agent/queryproductforagent/"+Systemuser.contractId;
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = OrderColumns;
		setting.aaSorting = [[0, "desc"]];
		setting.bStateSave = false;
		setting.bPaginate = true;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		
		
	
		
		
	},

	
	bindButtonEvent : function () {
		console.log("binlding");
		
		
		 $(".btn-charge-view").unbind("click").click(function () {
	            validator.clearForm();
				//$("#sname").nextAll().filter("span").html("");
				$(".btn-save-charge").attr("disabled", false);
				var parentTR = $(this).parents("TR"); 
				var chargeId = parentTR.find("input[type=hidden][name=chargeId]").val();
				
				getcharge(chargeId, true);
	        });
		
        $(".btn-charge").unbind("click").click(function () {
            validator.clearForm();
            //$("#sname").nextAll().filter("span").html("");
            $(".btn-save-charge").show();
            $(".btn-save-charge").attr("disabled", false);
            var parentTR = $(this).parents("TR");
            var chargeId = parentTR.find("input[type=hidden][name=chargeId]").val();

            getcharge(chargeId, false);
        });
		
		$(".btn-active").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=accountnameforedit]").val();
			var message = "确定将产品【" + uName + "】上架？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/agent/onshelf/" + id,
					type: "get",
					success: function () {
						msgBox('success', "上架成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function () {
						msgBox('fail', "上架失败");
						Systemuser.dataTable.fnDraw();

					},
					dataType: "json"
				});
			});
		});
		
		
		
		
		$(".btn-deactive").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=accountnameforedit]").val();
			var message = "确定将产品【" + uName + "】下架？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/agent/offshelf/" + id,
					type: "get",
					success: function () {
						msgBox('success', "下架成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function () {
						msgBox('fail', "下架失败");
						Systemuser.dataTable.fnDraw();

					},
					dataType: "json"
				});
			});
		});
		
		
		
		$(".btn-delete").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=accountnameforedit]").val();
			var message = "确定删除产品【" + uName + "】？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/product/" + id,
					type: "delete",
					success: function () {
						msgBox('success', "产品删除成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function () {
						console.log("----------");
						msgBox('success', "产品删除成功");
						Systemuser.dataTable.fnDraw();
					},
					dataType: "json"
				});
			});
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
			$("#myModalContract").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});	
		
		 
	
		
		
		
		
	
		
		
		 $(".showallornot").unbind("click").click(function () {
			 
				if($(".showallornot").text()=="显示所有国家和地区"){
					$(".showallornot").text("隐藏部分国家和地区");
					
					
				}else{
					$(".showallornot").text("显示所有国家和地区");
					
				}
				$(".geater100").toggle();
	          
	      });
		 
		
		
		$(".btn-save-order").unbind("click").click(function () {
			console.log("99991---");
			
			$(".btn-save-order").attr("disabled", true);
			if($.trim($('#edit_userid_order').val())==""){
				productId=$('input[name="rateCode"]:checked').val();
				var business = {};
				business.productIds = [];
				$(".checkbox-item").each(function(){
					if($(this).is(':checked')){
						business.productIds.push($(this).val());
					}
				}
						
				);

				business.agentId = Systemuser.contractId;
				
				var postURL = G_CTX_ROOT + "/v1/agent/draftproduct";
				var update = false;
				var calltype = "post";
			}else{
				var business = {};
					


				
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
					
					msgBox('success', "添加成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModalContract").modal("hide");
					$('#sname').val("");
					

					msgBox('fail', "操作失败");

					Systemuser.refresh();
				},
				dataType: "html"
			});
		});
		
		
	     $(".btn-save-order-MONTHLY_PAY_BY_ACTIVEHOSTS").unbind("click").click(function () {
				console.log("9999---");
				
				$(".btn-save-order-MONTHLY_PAY_BY_ACTIVEHOSTS").attr("disabled", true);
		
					var business = {};
					//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_ACTIVEHOSTS').val());	
					business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_ACTIVEHOSTS').val());	
					//business.HOSTS_AMOUNT = $.trim($('#shosts_MONTHLY_PAY_BY_ACTIVEHOSTS').val());
					//business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_ACTIVEHOSTS').val());
					
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
					//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_HOSTS').val());	
					business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_HOSTS').val());	
					//business.HOSTS_AMOUNT = $.trim($('#shosts_MONTHLY_PAY_BY_HOSTS').val());
					//business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_HOSTS').val());
					
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
	        
	        
	        $(".btn-save-order-CMR_MONTHLY_PAY").unbind("click").click(function () {
				console.log("9999---");
				
				$(".btn-save-order-CMR_MONTHLY_PAY").attr("disabled", true);
		
					var business = {};
					//business.COMMON_SITE = $.trim($('#s_siteId_CMR_MONTHLY_PAY').val());	
					business.COMMON_UNIT_PRICE = $.trim($('#sprices_CMR_MONTHLY_PAY').val());	
					business.COMMON_OVERFLOW_UNIT_PRICE = $.trim($('#soverprices_CMR_MONTHLY_PAY').val());
					//business.HOSTS_AMOUNT = $.trim($('#shosts_MONTHLY_PAY_BY_HOSTS').val());
					//business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_HOSTS').val());
					
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
					//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_PORTS').val());	
					business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_PORTS').val());
	                business.COMMON_OVERFLOW_UNIT_PRICE = $.trim($('#soverprices_MONTHLY_PAY_BY_PORTS').val());


					
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
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_STO").unbind("click").click(function () {
				
				
				$(".btn-save-order-MONTHLY_PAY_BY_STO").attr("disabled", true);
		
					var business = {};
					//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_STO').val());	
					business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_STO').val());	
					//business.STORAGE_SIZE = $.trim($('#shosts_MONTHLY_PAY_BY_STO').val());
					//business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_STO').val());
					
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
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_ECPP").unbind("click").click(function () {
				
				
				$(".btn-save-order-MONTHLY_PAY_BY_ECPP").attr("disabled", true);
		
					var business = {};
					//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_ECPP').val());	
					//business.TOTAL_PRICE = $.trim($('#shosts_MONTHLY_PAY_BY_ECPP').val());
					//business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_ECPP').val());
					
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
					//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_ECPU').val());	
					//business.TIMES = $.trim($('#shosts_MONTHLY_PAY_BY_ECPU').val());
					business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_ECPU').val());	
					//business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_ECPU').val());
					
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
					//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_PSTNALLSTIE').val());	
					business.TOTAL_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_PSTNALLSTIE').val());
					business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_MONTHLY_PAY_BY_PSTNALLSTIE').val());
					//business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_PSTNALLSTIE').val());
					
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
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").unbind("click").click(function () {
				
				
				$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").attr("disabled", true);
				var pstnRates4Put = {};
					pstnRates4Put.rates=[];
					pstnRates4Put.pid=$.trim($('#edit_userid_order_MONTHLY_PAY_BY_STANDSITE').val());
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
					
					var postURL = G_CTX_ROOT + "/v1/pstnrate/"+$.trim($('#edit_userid_order_MONTHLY_PAY_BY_STANDSITE').val());
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
						$("#myModal_MONTHLY_PAY_BY_STANDSITE").modal("hide");
						
						
						msgBox('success', "资费修改成功");
						Systemuser.refresh();
					},
					error : function (data) {
						$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").attr("disabled", false);
						validator.clearForm();
						$("#edit_userid_order_MONTHLY_PAY_BY_STANDSITE").val("");
						$("#myModal_MONTHLY_PAY_BY_STANDSITE").modal("hide");
						
						
						msgBox('fail', "资费修改失败");
						Systemuser.refresh();
					},
				});
			});
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_PSTNMONTH").unbind("click").click(function () {
				
				var modulesArray = new Array();
				$(".btn-save-order-MONTHLY_PAY_BY_PSTNMONTH").attr("disabled", true);
		
					var business = {};
					//modulesArray.push($.trim($('#s_siteId_MONTHLY_PAY_BY_PSTNMONTH').val()));
					//business.COMMON_SITES = modulesArray;	
					business.MONTHLY_FEE = $.trim($('#sprices_MONTHLY_PAY_BY_PSTNMONTH').val());	
					business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_MONTHLY_PAY_BY_PSTNMONTH').val());
					//business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_PSTNMONTH').val());
					
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
			
			$(".btn-save-order-MONTHLY_PAY_BY_PSTNMUlSTIE").unbind("click").click(function () {
				
				
				$(".btn-save-order-MONTHLY_PAY_BY_PSTNMUlSTIE").attr("disabled", true);
			
					var business = {};
					//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_PSTNMUlSTIE').val());	
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
			$(".btn-save-order-MONTHLY_PAY_BY_TELECOM").unbind("click").click(function () {
				
				
				$(".btn-save-order-MONTHLY_PAY_BY_TELECOM").attr("disabled", true);
		
					var business = {};
					//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_TELECOM').val());	
					business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_TELECOM').val());	
					business.WEBEX_ID = $.trim($('#shosts_MONTHLY_PAY_BY_TELECOM').val());				
					
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
		
		
	},
	
	
	
	
    
	
	
	
	getUserDetailOrder : function (userid) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/order/" + userid,
			data : "",
			type : "get",
			success : function (data) {
				console.log("----",data);
				$("#"+data.productId).attr("checked","true");
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

	
	
	
	getallproducts: function (orderid){


		
		Systemuser.optionscontract="";
		$.ajaxInvoke({
			url 	: 	G_CTX_ROOT+"/v1/product/listpublicagent"  ,
			data 	:	"",
			type : "get",
			success	:	function(returnData){
				//returnData= JSON.parse(returnData);

				var hostssection=Systemuser.getSectionHtml(returnData.WEBEX_BY_HOSTS);
				var portssection=Systemuser.getSectionHtml(returnData.WEBEX_BY_PORTS);
				var pstnsection=Systemuser.getSectionHtmlForbig(returnData.WEBEX_PSTN);
				var audiosection=Systemuser.getSectionHtmlForbig(returnData.WEBEX_AUDIO_PACKAGE);
				var storagesection=Systemuser.getSectionHtml(returnData.WEBEX_STORAGE);
				var miscsection=Systemuser.getSectionHtml(returnData.MISC);
				var ahsection=Systemuser.getSectionHtml(returnData.WEBEX_BY_AH);
				var cmrsection=Systemuser.getSectionHtml(returnData.WEBEX_CMR);

				$('#hostssection').html(hostssection);
				$('#portssection').html(portssection);
				$('#pstnsection').html(pstnsection);
				$('#audiosection').html(audiosection);
				$('#storagesection').html(storagesection);
				$('#miscsection').html(miscsection);
				$('#ahsection').html(ahsection);
				$('#cmrsection').html(cmrsection);
				if(orderid!=""){
					Systemuser.getUserDetailOrder(orderid);
				}

				
				
			},

		});




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

	refresh:function(){
		Systemuser.dataTable.fnDraw();
		Systemuser.userTable.fnDraw();
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
$(document).ready(Systemuser.init());
