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
                "bSortable": true,
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
                        return "按账户数包月";
                    } else if (data == "MONTHLY_PAY_BY_PORTS") {
                        return "按并发数包月";
                    } else if (data == "EC_PAY_PER_USE") {
                        return "EC按次计费";
                    } else if (data == "EC_PREPAID") {
                        return "EC预存计费";
                    } else if (data == "MONTHLY_PAY_BY_STORAGE") {
                        return "WebEx存储包月";
                    } else if (data == "PSTN_STANDARD_CHARGE") {
                        return "PSTN语音标准计费";
                    } else if (data == "PSTN_PACKAGE_CHARGE") {
                        return "PSTN语音标准计费";
                    } else if (data == "PSTN_MONTHLY_PACKET") {
                        return "PSTN语音包月";
                    } else if (data == "PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES") {
                        return "PSTN语音增量包";
                    } else if (data == "PSTN_SINGLE_PACKET_FOR_ALL_SITES") {
                        return "PSTN语音增量包(全站点适用)";
                    } else if (data == "MONTHLY_PAY_BY_HOSTS") {
                        return "按激活用户数包月"
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
                    }if (data == "CC_CALLCENTER_MONTHLY_PAY") {
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
            }, {
                "sTitle": "操作",
                "mData": "f6",
                "bSortable": false,
                "sClass": "center",
                "sWidth": "15%",
                "render": function (data, dis, obj) {
                    viewDom = "<span class='btn btn-info btn-xs btn-detail'>查看</span> ";
                	approveDom = "<span class=\"btn btn-success btn-xs btn-approve\">审核通过</span> ";
                    declineDom = "<span class=\"btn btn-danger btn-xs btn-decline\">退回</span> ";
					
					return viewDom + approveDom + declineDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
                }
            },];
        setting.aaSorting = [[0, "desc"]];
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
			var chargeId = parentTR.find("input[type=hidden][name=sysId]").val()
			
		
			
			getcharge(chargeId);
        });



        $(".btn-search").unbind("click").click(function () {
            Systemuser.dataTable.fnDraw();
        });
        $(".btn-approve").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=productnameforedit]").val();
				var message = "确定审核通过【" + uName + "】？";
				mConfirm(message, function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/charges/approve/"+id,
						type : "get",
						success : function (data) {
							msgBox('success', "操作成功");
							
								Systemuser.dataTable.fnDraw();
							
						},
						error : function (data) {
							msgBox('success', "操作失败");
							
								Systemuser.dataTable.fnDraw();
							
						},
						dataType : "html"
					});
				});
			
		});

        $(".btn-decline").unbind("click").click(function () {
            var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
            var target = $(this).parents("TR");
            var tds = $(target).children('td');
            var uName = $(tds[0]).find("input[name=productnameforedit]").val();
            var message = "确定退回【" + uName + "】？";
            mConfirm(message, function () {
                $.ajaxInvoke({
                    url : G_CTX_ROOT + "/v1/charges/decline/"+id,
                    type : "get",
                    success : function (data) {
                        msgBox('success', "操作成功");

                        Systemuser.dataTable.fnDraw();

                    },
                    error : function (data) {
                        msgBox('success', "操作失败");

                        Systemuser.dataTable.fnDraw();

                    },
                    dataType : "html"
                });
            });

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
