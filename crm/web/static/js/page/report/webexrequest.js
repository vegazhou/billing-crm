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
        setting.sAjaxSource = G_CTX_ROOT + "/v1/wbxreq/query";
        setting.callbackStack.stack.push(function () {
            $(".rolelistName").html("角色列表");
            self.bindButtonEvent();
        });

        self.bindLaterEvent();
        setting.aoColumns = [
            {
                "sTitle" : "客户名称",
                "mData" : "customer_NAME",
                "bSortable" : true,
                "sClass" : "center",
                "render" : function (data) {
                    return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
                }
            },
            
            {
                "sTitle" : "站点",
                "mData" : "siteName",
                "bSortable" : true,
                "sClass" : "center",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },
            
            
            {
                "sTitle" : "类型",
                "mData" : "type",
                "bSortable" : false,
                "sClass" : "center",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },
            
            {
                "sTitle" : "状态",
                "mData" : "state",
                "bSortable" : false,
                "sClass" : "center",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },
            
            {
                "sTitle" : "生成时间",
                "mData" : "createtime",
                "bSortable" : true,
                "sClass" : "center",
                "render" : function (data) {
                    return htmlencode(data);
                }
            },

            {
        		"sTitle" : "报文",
        		"mData" : "ok",
        		"bSortable" : false,
        		"sClass" : "center",
        		"render" : function (data,dis, obj) {
        			if(obj.type=="SITE"){
        				data=  "<input type='hidden' name='wbx_PROV_STR' value='" + obj.wbx_PROV_STR + "'/><span class='btn btn-success btn-xs btn-wbx_PROV_STR'> 请求报文</span>"+
        				"  <input type='hidden' name='cmd_RESULT' value='" + obj.cmd_RESULT + "'/><span class='btn btn-success btn-xs btn-cmd_RESULT'> 应答报文</span>"+
        				"  <input type='hidden' name='call_BACK_STR' value='" + obj.call_BACK_STR + "'/><span class='btn btn-success btn-xs btn-call_BACK_STR'> 回调报文</span>";
        				
        			}else{
        				data=  "<input type='hidden' name='wbx_PROV_STR' value='" + obj.wbx_PROV_STR + "'/><span class='btn btn-success btn-xs btn-wbx_PROV_STR'> 请求报文</span>";
        			}
        			return data ;
        		}
        	}, ];
        setting.aaSorting = [[4, "desc"]];
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

    	$(".btn-wbx_PROV_STR").unbind("click").click(function () {
			
			validator.clearForm();
			
			var parentTR = $(this).parents("TR"); 
			
			$("#detail").html(parentTR.find("input[type=hidden][name=wbx_PROV_STR]").val());
			
			
			
			
			$("#myModal").modal("show");
		});	

    	
    	$(".btn-cmd_RESULT").unbind("click").click(function () {
			
			validator.clearForm();
			
			var parentTR = $(this).parents("TR"); 
			
			$("#detail").html(parentTR.find("input[type=hidden][name=cmd_RESULT]").val());
			
			
			
			
			$("#myModal").modal("show");
		});	
    	
    	
    	
    	$(".btn-call_BACK_STR").unbind("click").click(function () {
			
			validator.clearForm();
			
			var parentTR = $(this).parents("TR"); 
			
			$("#detail").html(parentTR.find("input[type=hidden][name=call_BACK_STR]").val());
			
			
			
			
			$("#myModal").modal("show");
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
