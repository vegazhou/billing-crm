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
        setting.sAjaxSource = G_CTX_ROOT + "/v1/billage/query";
        setting.callbackStack.stack.push(function () {
            $(".rolelistName").html("角色列表");
            self.bindButtonEvent();
        });

        self.bindLaterEvent();
        setting.aoColumns = [
            {
                "sTitle" : "客户名称",
                "mData" : "display_name",
                "bSortable" : true,
                "sClass" : "center",
                "sWidth" : "12%",
                "render" : function (data) {
                    return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
                }
            },
            
            {
                "sTitle" : "信用期内（30天）",
                "mData" : "within30",
                "bSortable" : true,
                "sClass" : "center",
                "sWidth" : "11%",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },
            
            
            {
                "sTitle" : "超出信用期（30天内）",
                "mData" : "exceedwithin30",
                "bSortable" : true,
                "sClass" : "center",
                "sWidth" : "11%",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },
            
            {
                "sTitle" : "超出信用期（31天-60天）",
                "mData" : "exceedwithin60",
                "bSortable" : true,
                "sClass" : "center",
                "sWidth" : "11%",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },
            
            {
                "sTitle" : "超出信用期（61天-90天）",
                "mData" : "exceedwithin90",
                "bSortable" : true,
                "sClass" : "center",
                "sWidth" : "11%",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },

            {
                "sTitle" : "超出信用期（91天-120天）",
                "mData" : "exceedwithin120",
                "bSortable" : true,
                "sWidth" : "11%",
                "sClass" : "center",
                "render" : function (data) {
                    return htmlencode(data);
                }
            }, 
            
            
            {
                "sTitle" : "超出信用期（121天-150天）",
                "mData" : "exceedwithin150",
                "bSortable" : true,
                "sClass" : "center",
                "sWidth" : "11%",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },
            
            
            {
                "sTitle" : "超出信用期（151天-180天）",
                "mData" : "exceedwithin180",
                "bSortable" : true,
                "sClass" : "center",
                "sWidth" : "11%",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },
            {
                "sTitle" : "超出信用期（超过180天）",
                "mData" : "exceedover180",
                "bSortable" : true,
                "sClass" : "center",
                "sWidth" : "11%",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },
            
            ];
       
        setting.bStateSave = false;
        Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
        $("#datatable_tabletools_filter").html("");
    },
    bindButtonEvent : function () {
        console.log("binlding");


    

        $(".btn-order").unbind("click").click(function () {
            var target = $(this).parents("TR");
            var tds = $(target).children('td');
            var uName = $(tds[0]).find("input[name=accountnameforedit]").val();
            window.location.href = '../report/orderreport.jsp?id='+$(this).parents("TR").find("input[type=hidden][name=sysId]").val()+"&name="+uName;

        });



    	$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
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






                var postURL = G_CTX_ROOT + "/v1/customer/";
                var update = false;
                var calltype = "post";
            }else{
                var business = {};
                business.displayName = $.trim($('#sname').val());


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

                    msgBox('success', "客户修改成功");
                    Systemuser.dataTable.fnDraw();
                },
                error : function (data) {

                    if (data.status == "400" && JSON.parse(data.responseText).error.key == "org.domain.already.claimed") {
                        $(".btn-save").attr("disabled", false);
                        $("#sdomain").nextAll().filter("span").html("<font color=red >域名已经被使用！</font>");
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

                    msgBox('success', "客户增加成功");
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
    }

};
validator.init(rule);
validator.validate();
$(document).ready(Systemuser.init());
