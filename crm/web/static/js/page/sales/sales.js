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
        setting.sAjaxSource = G_CTX_ROOT + "/v1/salesman/query";
        setting.callbackStack.stack.push(function () {
            $(".rolelistName").html("角色列表");
            self.bindButtonEvent();
        });

        self.bindLaterEvent();
        setting.aoColumns = [
            {
                "sTitle": "销售员姓名",
                "mData": "displayName",
                "bSortable": true,
                "sClass": "center",
                "render": function (data) {
                    return "<input name='productnameforedit' type='hidden' value='" + data + "'/>" + htmlencode(data);
                }
            },
            {
                "sTitle": "邮件",
                "mData": "email",
                "bSortable": true,
                "sClass": "center",
                "render": function (data) {
                    return data;
                }
            },
            

            {
                "sTitle": "状态",
                "mData": "enabled",
                "bSortable": false,
                "sClass": "center",
                "render": function (data) {
                    if (data == 1) {
                        return "启用";
                    } else if (data == 0) {
                        return "停用";
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
                 
                    editDom = "<span class=\"btn btn-success btn-xs btn-detail\">修改</span> ";
                    deleteDom = "<span class=\"btn btn-danger btn-xs btn-delete\">删除</span> ";
                    active = "<span class=\"btn btn-danger btn-xs btn-active\">启用</span> ";
                    deactive = "<span class=\"btn btn-info btn-xs btn-active\">停用</span> ";
                    var buttons = "";

                    if (obj.enabled == 1) {
                        buttons = deactive + editDom + deleteDom;
                    } else if (obj.enabled == 0) {
                        buttons = active + editDom + deleteDom ;
                    } 
                    return buttons + "<input type='hidden' name='Status' value='"+obj.enabled+"'/><input type='hidden' name='sysId' value='" + obj.pid + "'/>";
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
			var userid = parentTR.find("input[type=hidden][name=sysId]").val();
			var status=parentTR.find("input[type=hidden][name=Status]").val();
			$("#edit_userid").val(userid);
			$("#status").val(status);
			Systemuser.getUserDetail(userid);
        });


        $(".btn-add").unbind("click").click(function () {
            $(".portssection").hide();
            $("#sname").nextAll().filter("span").html("");
            
            updateObj = null;
            validator.clearForm();
            $("#myModalLabel").html("新增销售员");
            $('#sname').val("").prop("disabled", false);
            $('#semail').val("").prop("disabled", false);
            $('#s_sserviceid').val("WEBEX_EC").prop("disabled", false);
            $("#edit_userid").val("");
            $(".btn-save").show();
            $(".btn-save").attr("disabled", false);
            $("#myModal").modal("show");
        });
        $(".btn-search").unbind("click").click(function () {
            Systemuser.dataTable.fnDraw();
        });		 
		 
        $(".btn-active").unbind("click").click(function(){
            var groupId = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
            var groupStatus = $(this).parents("TR").find("input[type=hidden][name=Status]").val();
            var groupName = $(this).parents("TR").children('td').eq(0).html();
            var active = groupStatus==1 ? "停用" : "启用";
            var data = {};
            data.name="name";
            data.email="email";
            data.enabled = groupStatus==1 ? 0 : 1;
            var postData=JSON.stringify(data);	
            mConfirm("确定"+active+"【"+groupName+"】？", function(){
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/v1/salesman/changestatus/"+groupId ,
					type : "put",
					data : postData,
					dataType: "json",
					success : function (data){
						msgBox('success', active+"修改状态成功");
						Systemuser.dataTable.fnDraw();
					},
					error	:	function (data){
						msgBox('fail', active+"修改状态失败");
					},
				});
            });
		});
	
   


        $(".btn-delete").unbind("click").click(function () {
            var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
            var target = $(this).parents("TR");
            var tds = $(target).children('td');
            var uName = $(tds[0]).find("input[name=productnameforedit]").val();
            var message = "确定删除销售员【" + uName + "】？";

            mConfirm(message, function () {
                $.ajaxInvoke({
                    url: G_CTX_ROOT + "/v1/salesman/" + id,
                    type: "delete",
                    success: function (data) {
                        msgBox('success', "销售员删除成功");
                        Systemuser.dataTable.fnDraw();
                    },
                    error: function (data) {
                    	
                    	if (data.status == "400" && JSON.parse(data.responseText).error.key == "salesman_has_contract") {
    						msgBox('fail', "销售员有合同无法删除！");
    						return;
    					}
                        msgBox('fail', "销售员删除失败");
                        Systemuser.dataTable.fnDraw();
                    },
                    dataType: "html"
                });
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
                business.name = $.trim($('#sname').val());
                business.email = $.trim($('#semail').val());


                var postURL = G_CTX_ROOT + "/v1/salesman/";
                var update = false;
                var calltype = "post";
            } else {
            	
                var business = {};
                business.name = $.trim($('#sname').val());
                business.email = $.trim($('#semail').val());
                business.enabled = $.trim($('#status').val());

                var postURL = G_CTX_ROOT + "/v1/salesman/" + $.trim($('#edit_userid').val());
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
                	if ($.trim($('#edit_userid').val()) == "") {
                    	msgBox('success', "新增收费员成功");
                    }else{
                    	msgBox('success', "修改收费员成功");
                    }
                    $(".btn-save").attr("disabled", false);
                    validator.clearForm();
                    $("#edit_userid").val("");
                    $("#myModal").modal("hide");
                    $('#sname').val("");
                    
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
    
    
 
	
	
	
    getUserDetail: function (userid) {
        $.ajaxInvoke({
            url: G_CTX_ROOT + "/v1/salesman/" + userid,
            data: "",
            type: "get",
            success: function (data) {
                
                $("#sname").val(data.name);
                $("#semail").val(data.email);
                $("#myModalLabel").html("修改销售员");
    			
    			$(".btn-save").show();
    			$("#myModal").modal("show");
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
        name: "姓名",
        method: {
            required: true,
            lt: 100,
        }
       
    },

	semail: {
    name: "邮件",
    method: {
        required: true,
        lt: 100,
    }
    
}

};
validator.init(rule);
validator.validate();
$(document).ready(Systemuser.init());
