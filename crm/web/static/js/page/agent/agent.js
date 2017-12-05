var Systemuser = {
    dataTable : null,
    options : "",
    row_count : 0,
    orgservicelist:null,
    allorgservicelist:null,
    deletedorgservicelist:null,
    userservicelist:[],
    init : function () {
        var self = this;
        pageSetUp();
        setting.sAjaxSource = G_CTX_ROOT + "/v1/agent/query";
        setting.callbackStack.stack.push(function () {
            $(".rolelistName").html("角色列表");
            self.bindButtonEvent();
        });

        self.bindLaterEvent();
        setting.aoColumns = [
            {
                "sTitle" : "代理商名称",
                "mData" : "displayName",
                "bSortable" : true,
                "sClass" : "center",
                "render" : function (data) {
                    return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
                }
            },


            {
                "sTitle" : "录入时间",
                "mData" : "createDate",
                "bSortable" : true,
                "sClass" : "center",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },
            {
                "sTitle" : "客户编码",
                "mData" : "code",
                "bSortable" : false,
                "sClass" : "center",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },

            {
                "sTitle" : "SAP同步状态",
                "mData" : "sapSynced",
                "bSortable" : false,
                "sClass" : "center",
                "render" : function (data) {
                    if (data) {
                        return "<font color='green'>已同步</font>"
                    } else {
                        return "<font color='red'>未同步</font>"
                    }
                }
            },

            {
                "sTitle" : "操作",
                "mData" : "f6",
                "bSortable" : false,
                "sClass" : "center",
                "sWidth" : "30%",
                "render" : function (data, dis, obj) {
                    editDom = "";
                    synDom = "<span class='btn btn-warning btn-xs btn-sap'> 同步至SAP </span>";

                    editDom = "<span class=\"btn btn-success btn-xs btn-addcontract\">管理代理产品</span> <span class=\"btn btn-success btn-xs btn-detail\">修改</span> ";

                    deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> ";

                    return editDom + deleteDom + synDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
                }
            }, ];
        setting.aaSorting = [[1, "desc"]];
        setting.bStateSave = false;
        Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
        $("#datatable_tabletools_filter").html("");
    },
    bindButtonEvent : function () {
        console.log("binlding");
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
            $("#myModalLabel").html("代理商编辑");

            $(".btn-save").show();
            $("#myModal").modal("show");
        });


        $(".btn-add").unbind("click").click(function () {

            $("#sname").nextAll().filter("span").html("");
            updateObj = null;
            validator.clearForm();
            $("#myModalLabel").html("代理商添加");
            $('#sname').val("").prop("disabled", false);
            $('#address').val("");
            $('#phone').val("");
            $('#isVat').val("");
            $('#vatNo').val("");
            $('#bank').val("");
            $('#scontactphone').val("");
            $('#scontactemail').val("");
            $('#scontactname').val("");
            $('#bankAccount').val("");
            $('#level').val("");
            $("#edit_userid").val("");
            $(".btn-save").show();
            $(".btn-save").attr("disabled", false);
            $("#myModal").modal("show");
        });
        $(".btn-search").unbind("click").click(function () {
            Systemuser.dataTable.fnDraw();
        });


		$(".btn-addcontract").unbind("click").click(function () {
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=accountnameforedit]").val();
			var status= $(tds[0]).html();
			window.location.href = '../agent/agentproduct.jsp?id='+$(this).parents("TR").find("input[type=hidden][name=sysId]").val()+"&name="+uName+"&status="+status;
			
		});



        $(".btn-delete").unbind("click").click(function () {


            var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();


            mConfirm("是否删除？", function () {
                $.ajaxInvoke({
                    url : G_CTX_ROOT + "/v1/customer/"+id,
                    type : "delete",
                    success : function (data) {
                        msgBox('success', "客户删除成功");

                        Systemuser.dataTable.fnDraw();

                    },
                    error: function (data) {
                        if (data.status == "400" && JSON.parse(data.responseText).error.key == "customer_has_signed_contract") {
                            msgBox('fail', "不能删除已签单客户！");
                            return;
                        }

                        Systemuser.dataTable.fnDraw();

                    },
                    dataType : "html"
                });
            });

        });


        $(".btn-sap").unbind("click").click(function () {
            var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();

            mConfirm("将客户数据同步至SAP系统？", function () {
                $.ajaxInvoke({
                    url : G_CTX_ROOT + "/v1/customer/sapsync/"+id,
                    type : "get",
                    success : function (data) {
                        msgBox('success', "SAP数据同步成功");
                        Systemuser.dataTable.fnDraw();
                    },
                    error: function (data) {
                        msgBox('fail', "SAP数据同步 失败");

                    },
                    dataType : "html"
                });
            });

        });




        $(".btn-save").unbind("click").click(function () {
            console.log("9999---");
            var modulesArray = new Array();
            var deletesArray = new Array();
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
            if($.trim($('#edit_userid').val())==""){
                var business = {};
                business.displayName = $.trim($('#sname').val());

                business.address = $.trim($('#address').val());
                business.phone = $.trim($('#phone').val());
                business.isVat = $.trim($('#isVat').val());
                business.vatNo = $.trim($('#vatNo').val());
                business.bank = $.trim($('#bank').val());
                business.contactName = $.trim($('#scontactname').val());
                business.contactEmail = $.trim($('#scontactemail').val());
                business.contactPhone = $.trim($('#scontactphone').val());
                business.bankAccount = $.trim($('#bankAccount').val());
                business.level = $.trim($('#level').val());


                var postURL = G_CTX_ROOT + "/v1/agent/";
                var update = false;
                var calltype = "post";
            }else{
                var business = {};
                business.displayName = $.trim($('#sname').val());

                business.address = $.trim($('#address').val());
                business.phone = $.trim($('#phone').val());
                business.isVat = $.trim($('#isVat').val());
                business.vatNo = $.trim($('#vatNo').val());
                business.bank = $.trim($('#bank').val());
                business.contactName = $.trim($('#scontactname').val());
                business.contactEmail = $.trim($('#scontactemail').val());
                business.contactPhone = $.trim($('#scontactphone').val());
                business.bankAccount = $.trim($('#bankAccount').val());
                business.level = $.trim($('#level').val());


                var postURL = G_CTX_ROOT + "/v1/customer/"+$.trim($('#edit_userid').val());
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

                    msgBox('success', "操作成功");
                    Systemuser.dataTable.fnDraw();
                },
                error : function (data) {
                    $(".btn-save").attr("disabled", false);
                    validator.clearForm();
                    $("#edit_userid").val("");
                    $("#myModal").modal("hide");
                    $('#sname').val("");

                    msgBox('success', "操作成功");
                    Systemuser.dataTable.fnDraw();
                },
            });
        });
    },


    getUserDetail : function (userid) {
        $.ajaxInvoke({
            url : G_CTX_ROOT + "/v1/customer/" + userid,
            data : "",
            type : "get",
            success : function (data) {
                console.log("----",data);
                $("#sname").val(data.displayName);

                $("#address").val(data.address);
                $("#phone").val(data.phone);
                $("#isVat").val(""+data.vat);
                $("#vatNo").val(data.vatNo);
                $("#bank").val(data.bank);
                $("#bankAccount").val(data.bankAccount);
                $("#level").val(data.level);
                $("#scontactname").val(data.contactName);
                $("#scontactemail").val(data.contactEmail);
                $("#scontactphone").val(data.contactPhone);

                //$("#sadmin").prop("disabled", true);




            },
        });
    },




    bindLaterEvent:function(){
        console.log("bindinglater");

    }


}
var rule = {


    sname : {
        name : "客户名称",
        method : {
            required : true,
            lt : 100,
        },
        onBlur : Systemuser.checkName
    },
    scontactname : {
        name : "联系人姓名",
        method : {
            required : true,
            lt : 100,
        },

    },
    scontactemail : {
        name : "联系人邮件",
        method : {
            required : true,
            lt : 500,
        },
        onBlur : Systemuser.checkName
    },

    scontactphone : {
        name : "联系人电话",
        method : {
            required : true,
            lt : 50,
        },

    }

};
validator.init(rule);
validator.validate();
$(document).ready(Systemuser.init());
