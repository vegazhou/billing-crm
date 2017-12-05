var Systemuser = {
    dataTable: null,
    options: "",
    row_count: 0,
    orgservicelist: null,
    allorgservicelist: null,
    deletedorgservicelist: null,
    userservicelist: [],
    init: function () {
        var self = this;
        pageSetUp();
        setting.sAjaxSource = G_CTX_ROOT + "/v1/charges/queryTemplates";
        setting.callbackStack.stack.push(function () {
            $(".rolelistName").html("角色列表");
            self.bindButtonEvent();
        });

        self.bindLaterEvent();
        setting.aoColumns = [
            {
                "sTitle": "资费方案名称",
                "mData": "displayName",
                "bSortable": false,
                "sClass": "center",
                "render": function (data) {
                    return "<input name='productnameforedit' type='hidden' value='" + data + "'/>" + htmlencode(data);
                }
            },

            {
                "sTitle": "类型",
                "mData": "type",
                "bSortable": false,
                "sClass": "center",
                "render": function (data) {
                    if (data == "MONTHLY_PAY_BY_HOSTS") {
                        return "WebEx按用户数包月";
                    } else if (data == "MONTHLY_PAY_BY_PORTS") {
                        return "WebEx按并发数包月";
                    } else if (data == "EC_PAY_PER_USE") {
                        return "EC按次购买";
                    } else if (data == "EC_PREPAID") {
                        return "EC预存";
                    } else if (data == "MONTHLY_PAY_BY_STORAGE") {
                        return "WebEx存储包月";
                    } else if (data == "PSTN_STANDARD_CHARGE") {
                        return "WebEx电话语音标准计费";
                    } else if (data == "PSTN_PACKAGE_CHARGE") {
                        return "PSTN语音标准计费";
                    } else if (data == "PSTN_MONTHLY_PACKET") {
                        return "WebEx电话语音包月";
                    } else if (data == "PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES") {
                        return "WebEx电话语音增量包";
                    } else if (data == "PSTN_SINGLE_PACKET_FOR_ALL_SITES") {
                        return "WebEx电话语音增量包(覆盖全站点)";
                    } else if (data == "TELECOM_CHARGE") {
                        return "电信会易通";
                    } else if (data == "MISC_CHARGE") {
                        return "杂项收费";
                    }else if (data == "MONTHLY_PAY_BY_ACTIVEHOSTS") {
                        return "WebEx按激活用户数计费";
                    }else if (data == "CMR_MONTHLY_PAY") {
                        return "WebEx CMR包月";
                    } else if (data == "MONTHLY_PAY_BY_STORAGE_O") {
                        return "WebEx溢出存储包月";
                    }else if (data == "MONTHLY_PAY_BY_TOTAL_ATTENDEES") {
                        return "WebEx按总参会人次包月";
                    }else if (data == "MONTHLY_PAY_PERSONAL_WEBEX") {
                        return "WebEx个人会议";
                    }else if (data == "PSTN_PERSONAL_CHARGE") {
                        return "个人电话语音";
                    }else if (data == "PSTN_PERSONAL_PACKET") {
                        return "个人电话语音包";
                    }else if (data == "CC_CALLCENTER_MONTHLY_PAY") {
                        return "天客云电话座席按用户包月";
                    }else if (data == "CC_CALLCENTER_NUMBER_MONTHLY_PAY") {
                        return "天客云中继号码按个数包月";
                    }else if (data == "CC_CALLCENTER_PSTN") {
                        return "天客云语音通话基础费率";
                    }else if (data == "CC_CALLCENTER_PSTN_PACKAGE") {
                        return "天客云电话语音套餐包";
                    }else if (data == "CC_CALLCENTER_PSTN_MONTHLY_PACKAGE") {
                        return "天客云电话语音包月";
                    }else if (data == "CC_CALLCENTER_OLS_MONTHLY_PAY") {
                        return "天客云在线座席按用户包月";
                    }else if (data == "CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE") {
                        return "天客云最低月消费包";
                    }
                    
                    return data;
                }
            },


            {
                "sTitle": "状态",
                "mData": "status",
                "bSortable": false,
                "sClass": "center",
                "render": function (data) {
                    if (data == "DRAFT") {
                        return "草稿";
                    } else if (data == "WAITING_APPROVAL") {
                        return "待审核";
                    } else if (data == "IN_EFFECT") {
                        return "已生效";
                    }
                    return data;
                }
            },

            {
                "sTitle": "创建时间",
                "mData": "createdTime",
                "bSortable": true,
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
                    sendApproveDom = "<span class=\"btn btn-warning btn-xs btn-send\">送审</span> ";
                    editDom = "<span class=\"btn btn-success btn-xs btn-edit\">修改</span> ";
                    deleteDom = "<span class=\"btn btn-danger btn-xs btn-delete\">删除</span> ";
                    withdrawDom = "<span class=\"btn btn-danger btn-xs btn-withdraw\">撤回</span> ";
                    viewDom = "<span class=\"btn btn-info btn-xs btn-detail\">查看</span> ";
                    var buttons = "";

                    if (obj.status == "DRAFT") {
                        buttons = sendApproveDom + editDom + deleteDom;
                    } else if (obj.status == "WAITING_APPROVAL") {
                        buttons = viewDom + withdrawDom ;
                    } else if (obj.status == "IN_EFFECT") {
                        buttons = viewDom;
                    }
                    return buttons + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
                }
            },];
        setting.aaSorting = [[3, "desc"]];
        setting.bStateSave = false;
        Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
        $("#datatable_tabletools_filter").html("");
    },
    bindButtonEvent: function () {
        console.log("binlding");


        $(".btn-detail").unbind("click").click(function () {
            validator.clearForm();
			//$("#sname").nextAll().filter("span").html("");
			$(".btn-save-charge").attr("disabled", false);
			var parentTR = $(this).parents("TR"); 
			var chargeId = parentTR.find("input[type=hidden][name=sysId]").val();
			
			getcharge(chargeId, true);
        });

        $(".btn-edit").unbind("click").click(function () {
            validator.clearForm();
            //$("#sname").nextAll().filter("span").html("");
            $(".btn-save-charge").show();
            $(".btn-save-charge").attr("disabled", false);
            var parentTR = $(this).parents("TR");
            var chargeId = parentTR.find("input[type=hidden][name=sysId]").val();

            getcharge(chargeId, false);
        });


        $(".btn-add").unbind("click").click(function () {
            $(".portssection").hide();
            $("#sname").nextAll().filter("span").html("");
            updateObj = null;
            validator.clearForm();
            $("#myModalLabel").html("新增计费方案");
            $('#sname').val("").prop("disabled", false);
            $('#s_sserviceid').val("WEBEX_EC").prop("disabled", false);
            $("#edit_userid").val("");
            $(".btn-save").show();
            $(".btn-save").attr("disabled", false);
            $("#myModal").modal("show");
        });
        $(".btn-search").unbind("click").click(function () {
            Systemuser.dataTable.fnDraw();
        });


        $(".btn-send").unbind("click").click(function () {
            var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
            var target = $(this).parents("TR");
            var tds = $(target).children('td');
            var uName = $(tds[0]).find("input[name=productnameforedit]").val();
            var message = "确定将计费方案【" + uName + "】送审？";

            mConfirm(message, function () {
                $.ajaxInvoke({
                    url: G_CTX_ROOT + "/v1/charges/send/" + id,
                    type: "get",
                    success: function (data) {
                        msgBox('success', "送审成功");
                        Systemuser.dataTable.fnDraw();
                    },
                    error: function (data) {
                        msgBox('fail', "送审失败");
                        Systemuser.dataTable.fnDraw();

                    },
                    dataType: "json"
                });
            });
        });
        $('#s_searchStartTime1').unbind().bind('click', Systemuser.initDatePicker);		
    	
		 $('#s_searchStartTime_ECPP').unbind().bind('click', Systemuser.initDatePicker);
		 
		 $('#s_searchStartTime_PSTNALLSTIE').unbind().bind('click', Systemuser.initDatePicker);	
		 
		 $('#s_searchStartTime_STANDSITE').unbind().bind('click', Systemuser.initDatePicker);
		 
		 $('#s_searchStartTime_ECPU').unbind().bind('click', Systemuser.initDatePicker);		 
		 
		 
        $(".btn-withdraw").unbind("click").click(function () {
            var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
            var target = $(this).parents("TR");
            var tds = $(target).children('td');
            var uName = $(tds[0]).find("input[name=productnameforedit]").val();
            var message = "确定将计费方案【" + uName + "】撤回？";

            mConfirm(message, function () {
                $.ajaxInvoke({
                    url: G_CTX_ROOT + "/v1/charges/withdraw/" + id,
                    type: "get",
                    success: function (data) {
                        msgBox('success', "撤回成功");
                        Systemuser.dataTable.fnDraw();
                    },
                    error: function (data) {
                        msgBox('fail', "撤回失败");
                        Systemuser.dataTable.fnDraw();

                    },
                    dataType: "json"
                });
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


        $(".btn-delete").unbind("click").click(function () {
            var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
            var target = $(this).parents("TR");
            var tds = $(target).children('td');
            var uName = $(tds[0]).find("input[name=productnameforedit]").val();
            var message = "确定删除计费方案【" + uName + "】？";

            mConfirm(message, function () {
                $.ajaxInvoke({
                    url: G_CTX_ROOT + "/v1/charges/" + id,
                    type: "delete",
                    success: function (data) {
                        msgBox('success', "资费删除成功");
                        Systemuser.dataTable.fnDraw();
                    },
                    error: function (data) {
                        msgBox('fail', "资费删除失败");
                        Systemuser.dataTable.fnDraw();
                    },
                    dataType: "html"
                });
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
        
        
        $(".btn-save-order-CC_CALLCENTER_MONTHLY_PAY").unbind("click").click(function () {
			console.log("9999---");
			
			$(".btn-save-order-CC_CALLCENTER_MONTHLY_PAY").attr("disabled", true);
	
				var business = {};
				//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_ACTIVEHOSTS').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_CC_CALLCENTER_MONTHLY_PAY').val());	
				//business.HOSTS_AMOUNT = $.trim($('#shosts_MONTHLY_PAY_BY_ACTIVEHOSTS').val());
				//business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_ACTIVEHOSTS').val());
				
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
        
        
        $(".btn-save-order-CC_CALLCENTER_NUMBER_MONTHLY_PAY").unbind("click").click(function () {
			console.log("9999---");
			
			$(".btn-save-order-CC_CALLCENTER_NUMBER_MONTHLY_PAY").attr("disabled", true);
	
				var business = {};
				//business.COMMON_SITE = $.trim($('#s_siteId_CC_CALLCENTER_NUMBER_MONTHLY_PAY').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_CC_CALLCENTER_NUMBER_MONTHLY_PAY').val());	
				//business.HOSTS_AMOUNT = $.trim($('#shosts_MONTHLY_PAY_BY_ACTIVEHOSTS').val());
				//business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_ACTIVEHOSTS').val());
				
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
        
        $(".btn-save-order-CC_CALLCENTER_OLS_MONTHLY_PAY").unbind("click").click(function () {

			
			$(".btn-save-order-CC_CALLCENTER_OLS_MONTHLY_PAY").attr("disabled", true);
	
				var business = {};
			
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_CC_CALLCENTER_OLS_MONTHLY_PAY').val());	
				
				
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
		
		
		$(".btn-save-order-MONTHLY_PAY_BY_TOTAL_ATTENDEES").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_TOTAL_ATTENDEES").attr("disabled", true);
	
				var business = {};
				//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_PORTS').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES').val());
                business.COMMON_OVERFLOW_UNIT_PRICE = $.trim($('#soverprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES').val());


				
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
		
		
		$(".btn-save-order-MONTHLY_PAY_BY_STO_O").unbind("click").click(function () {
			
			
			$(".btn-save-order-MONTHLY_PAY_BY_STO_O").attr("disabled", true);
	
				var business = {};
				//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_BY_STO').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_STO_O').val());
				business.COMMON_OVERFLOW_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_BY_STO_O_OVERPRICE').val());	
				//business.STORAGE_SIZE = $.trim($('#shosts_MONTHLY_PAY_BY_STO').val());
				//business.MONTH_AMOUNT = $.trim($('#smonths_MONTHLY_PAY_BY_STO').val());
				
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
		
		
		$(".btn-save-order-PSTN_PERSONAL_CHARGE").unbind("click").click(function () {
			if($.trim($('#ssite_PSTN_PERSONAL_CHARGE').val())==""){
				$("#ssite_PSTN_PERSONAL_CHARGE").nextAll().filter("span").html("<font color='red'>站点不能为空</font>") ;
				return;
			}
			
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
		
		
		
		$(".btn-save-order-CC_CALLCENTER_PSTN").unbind("click").click(function () {
			
			
			$(".btn-save-order-CC_CALLCENTER_PSTN").attr("disabled", true);
			var pstnRates4Put = {};
				pstnRates4Put.rates=[];
				pstnRates4Put.pid=$.trim($('#edit_userid_order_CC_CALLCENTER_PSTN').val());
				$(".raterow").each(function(){
					var rate = {};					
					rate.code=$(this).find("input[type=hidden][name=rateCode]").val();
					rate.rate=parseFloat($(this).find("input[type=text][name="+rate.code+"1]").val());
					rate.serviceRate=parseFloat($(this).find("input[type=text][name="+rate.code+"2]").val());
					pstnRates4Put.rates.push(rate);
				});
				
				//business.COMMON_SITES = new Array($.trim($('#s_siteId_CC_CALLCENTER_PSTN').val()));
				//business.TOTAL_PRICE = $.trim($('#sprices_CC_CALLCENTER_PSTN').val());	
				//business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_CC_CALLCENTER_PSTN').val());
				//business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_STANDSITE').val());
				
				var postURL = G_CTX_ROOT + "/v1/pstnrate/"+$.trim($('#edit_userid_order_CC_CALLCENTER_PSTN').val());
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
					$("#myModal_CC_CALLCENTER_PSTN").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-CC_CALLCENTER_PSTN").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_PSTN").val("");
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
		
		
		$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").unbind("click").click(function () {
			
			var modulesArray = new Array();
			$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").attr("disabled", true);
	
				var business = {};
				//modulesArray.push($.trim($('#s_siteId_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE').val()));
				//business.COMMON_SITES = modulesArray;	
				business.MONTHLY_FEE = $.trim($('#sprices_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE').val());	
				business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE').val());
				//business.MONTH_AMOUNT = $.trim($('#smonths_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE').val());
				
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
		
		
	$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").unbind("click").click(function () {
			
			var modulesArray = new Array();
			$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").attr("disabled", true);
	
				var business = {};
				//modulesArray.push($.trim($('#s_siteId_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE').val()));
				//business.COMMON_SITES = modulesArray;	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE').val());	
				business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE').val());
				//business.MONTH_AMOUNT = $.trim($('#smonths_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE').val());
				
				var postURL = G_CTX_ROOT + "/v1/charges/"+$.trim($('#edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE').val());
				var update = false;
				var calltype = "put";
				
			
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
                dataType: "html",
				success : function (data) {
				
					$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val("");
					$("#myModal_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").modal("hide");
					
					
					msgBox('success', "资费修改成功");
					Systemuser.refresh();
				},
				error : function (data) {
					$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val("");
					$("#myModal_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").modal("hide");
					
					
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
		
		
		$(".btn-save-order-PSTN_PERSONAL_PACKET").unbind("click").click(function () {
			
			if($.trim($('#ssite_PSTN_PERSONAL_PACKET').val())==""){
				$("#ssite_PSTN_PERSONAL_PACKET").nextAll().filter("span").html("<font color='red'>站点不能为空</font>") ;
				return;
			}
			$(".btn-save-order-PSTN_PERSONAL_PACKET").attr("disabled", true);
		
				var business = {};
				//business.COMMON_SITE = $.trim($('#s_siteId_PSTN_PERSONAL_PACKET').val());	
				business.TOTAL_PRICE = $.trim($('#sprices_PSTN_PERSONAL_PACKET').val());	
				business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_PSTN_PERSONAL_PACKET').val());
				//business.EFFECTIVE_BEFORE = $.trim($('#s_searchStartTime_STANDSITE').val());
				business.COMMON_SITE = $.trim($('#ssite_PSTN_PERSONAL_PACKET').val());	
				
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
				
				business.TOTAL_PRICE = $.trim($('#sprices_CC_CALLCENTER_PSTN_PACKAGE').val());	
				business.PSTN_PACKAGE_MINUTES = $.trim($('#shosts_CC_CALLCENTER_PSTN_PACKAGE').val());
				
				
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
		
		
		
		$(".btn-save-order-MONTHLY_PAY_PERSONAL_WEBEX").unbind("click").click(function () {
			if($.trim($('#ssite_MONTHLY_PAY_PERSONAL_WEBEX').val())==""){
				$("#ssite_MONTHLY_PAY_PERSONAL_WEBEX").nextAll().filter("span").html("<font color='red'>站点不能为空</font>") ;
				return;
			}
			
			$(".btn-save-order-MONTHLY_PAY_PERSONAL_WEBEX").attr("disabled", true);
	
				var business = {};
				//business.COMMON_SITE = $.trim($('#s_siteId_MONTHLY_PAY_PERSONAL_WEBEX').val());	
				business.COMMON_UNIT_PRICE = $.trim($('#sprices_MONTHLY_PAY_PERSONAL_WEBEX').val());	
				business.COMMON_SITE = $.trim($('#ssite_MONTHLY_PAY_PERSONAL_WEBEX').val());				
				
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

        $(".btn-save").unbind("click").click(function () {
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
            if ($.trim($('#edit_userid').val()) == "") {
                var business = {};
                business.displayName = $.trim($('#sname').val());
                business.chargeType = $.trim($('#s_sserviceid').val());


                var postURL = G_CTX_ROOT + "/v1/charges/";
                var update = false;
                var calltype = "post";
            } else {
                var business = {};
                business.displayName = $.trim($('#sname').val());
                business.SESSION_PORTS = $.trim($('#s_ports').val());


                var postURL = G_CTX_ROOT + "/v1/biz/" + $.trim($('#edit_userid').val());
                var update = false;
                var calltype = "put";

            }
            var postData = JSON.stringify(business);
            $.ajaxInvoke({
                url: postURL,
                type: calltype,
                data: postData,
                dataType: "html",
                success: function (data) {

                    $(".btn-save").attr("disabled", false);
                    validator.clearForm();
                    $("#edit_userid").val("");
                    $("#myModal").modal("hide");
                    $('#sname').val("");

                    msgBox('success', "新增资费成功");
                    Systemuser.dataTable.fnDraw();
                },
                error: function (data) {

                    $(".btn-save").attr("disabled", false);
                    validator.clearForm();
                    $("#edit_userid").val("");
                    $("#myModal").modal("hide");
                    $('#sname').val("");

                    msgBox('fail', "操作失败");
                    Systemuser.dataTable.fnDraw();
                },
            });
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
	
	
	savesites: function (sitevalue){
		$(".btn-save-order-PSTN_PERSONAL_CHARGE").attr("disabled", true);
		
		var business = {};
		//business.COMMON_SITE = $.trim($('#s_siteId_PSTN_PERSONAL_CHARGE').val());	
		
		business.COMMON_SITE = $.trim($('#ssite_PSTN_PERSONAL_CHARGE').val());				
		
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
	
	
	
	
    getUserDetail: function (userid) {
        $.ajaxInvoke({
            url: G_CTX_ROOT + "/v1/charges/" + userid,
            data: "",
            type: "get",
            success: function (data) {
                console.log("----", data);
                $("#sname").val(data.displayName);
                $("#s_sserviceid").val(data.type).prop("disabled", true);
                $("#s_ports").val(data.SESSION_PORTS);

                //$("#sadmin").prop("disabled", true);


            },
        });
    },
    
    
	initDatePicker	: function() {
		console.log("timer");
        WdatePicker({
            skin: 'twoer',
            dateFmt: 'yyyy-MM-dd'
           
        });
        return false;
	},

	refresh:function(){
		Systemuser.dataTable.fnDraw();
		//Systemuser.userTable.fnDraw();
	},
    bindLaterEvent: function () {
        console.log("bindinglater");


    }


}
var rule = {


    sname: {
        name: "资费方案名称",
        method: {
            required: true,
            lt: 100,
        },
        onBlur: Systemuser.checkName
    }

};
validator.init(rule);
validator.validate();
$(document).ready(Systemuser.init());
