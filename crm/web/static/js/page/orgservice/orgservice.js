var Systemuser = {
	dataTable : null,
	options : "",
	row_count : 0,
	orgservicelist:null,
	deletedorgservicelist:null,
	init : function () {
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/orgservice/query";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		setting.aoColumns = [
			{
				"sTitle" : "服务名称",
				"mData" : "serviceName",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" + ( data==null?"": htmlencode(data)); 
					
				}
			},                       
		                     
			{
				"sTitle" : "服务类型",
				"mData" : "type",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},                  
		                     
		                     
		     {
				"sTitle" : "总许可数",
				"mData" : "serviceLicense",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			}, 
			
			
			{
				"sTitle" : "未用许可数",
				"mData" : "notUsedLicense",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			
		
			
			{
				"sTitle" : "服务关联企业",
				"mData" : "orgName",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return data==null?"": htmlencode(data);
				}
			},
			
			{
				"sTitle" : "服务站点",
				"mData" : "serviceUrl",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return data==null?"": htmlencode(data);
				}
			},	
			/*
			{
				"sTitle" : "过期天数",
				"mData" : "expireDays",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return data==null?"": htmlencode(data);
				}
			},	*/
			
			{
				"sTitle" : "会议类型",
				"mData" : "meetingType",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return data==null?"": htmlencode(data);
				}
			}, 
			{
				"sTitle" : "自动配置用户服务",
				"mData" : "autoConfig",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return data == 1 ? "自动" : "手动";
				}
			},
			{
				"sTitle" : "状态",
				"mData" : "disabled",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return data == "0" ? "已启用" : "已禁用";
				}
			},
			
			{
				"sTitle" : "操作",
				"mData" : "f6",
				"bSortable" : false,
				"sClass" : "center",
				"sWidth" : "15%",
				"render" : function (data, dis, obj) {
					editDom = obj.disabled == 0 ? "<span class='btn btn-danger btn-xs btn-active'>禁用  </span> " : "<span class='btn btn-warning btn-xs btn-active'>启用 </span> ";

					if (obj.orgName == null) {
						enableDom =    "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> ";
					} else {
						enableDom = "";
					}
					return  editDom+enableDom +"<span class=\"btn btn-success btn-xs btn-detail\">修改</span>  "+  "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
				}
			}, ];
		setting.aaSorting = [[0, "desc"]];
		setting.bStateSave = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
	},
	bindButtonEvent : function () {
		 var engine = $('select[name="EngineModel"]');
		engines=[1,2,3];
		 var focus = false;
	        $("#inputModel").focus(function () {
	            focus = true;
	            $(this).next().css('display', 'block');
	        }).blur(function () {
	            if (focus) {
	                $(this).next().css('display', 'none');
	            }
	        }).keyup(function () {
	           
	        }).next().mousedown(function () {
	            focus = false;
	        }).change(function () {
	            $(this).css('display', 'none').prev().val(this.value);
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
	
		
		$("#serviceType").unbind("change").change(function () {
			
			if($("#serviceType").val()=="WEBEX"){
				console.log("here1");
				$(".meetingtypesection").show();
			}else{
				$(".meetingtypesection").hide();
				
			}
			
			if($("#serviceType").val()=="IM"){
				
				$(".imtypesection").hide();
				$("#surl").val("IM");
			}else{
				$(".imtypesection").show();
				$("#surl").val("");
			}
			
			
		});
		$(".btn-active").unbind("click").click(function () {
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var userid = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var statusObj = $(tds[8]);
			var uName = $(tds[0]).find("input[name=accountnameforedit]").val();
			var activeMsg = "确定禁用服务【" + uName + "】？";
			var isActive = (statusObj.html().indexOf("启用") > 0);
			var status = 1;
			if (!isActive) {
				activeMsg = "确定启用服务【" + uName + "】？";
				status = 0;
			}
			var user = {};
			user.disabled = status;
			user.fullName = $(tds[1]).text();
			user.mobile = $(tds[2]).text();
			var postData = JSON.stringify(user);
			mConfirm(activeMsg, function () {
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/v1/orgservice/changestatus/" + userid+"/"+status,
					data : postData,
					type : "get",
					success : function (data) {
						var activeObj = $(target).find(".btn-active");
						if (isActive) {
							statusObj.html("已禁用");
							activeObj.html("启用");
							activeObj.removeClass("btn-danger").addClass("btn-warning");
						} else {
							statusObj.html("已启用");
							activeObj.html("禁用");
							activeObj.removeClass("btn-warning").addClass("btn-danger");
						}
						msgBox("", "服务状态已改变");
					},
				});
			});
		});
		$(".btn-detail").unbind("click").click(function () {
			$(".btn-user-mc").show();
			validator.clearForm();
			$("#srole").nextAll().filter("span").html("");
			$(".btn-save").attr("disabled", false);
			var parentTR = $(this).parents("TR"); 
			var userid = parentTR.find("input[type=hidden][name=sysId]").val()
			$("#edit_userid").val(userid);
			$('#inputModel').val("");
			
			//Systemuser.getalldetails("", true, userid);
			Systemuser.getUserDetail(userid);
			$("#saccount").val(parentTR.find("input[name=accountnameforedit]").val()).prop("disabled", true);
			$("#edit_username").val($.trim(parentTR.find("input[name=accountnameforedit]").val())).prop("disabled", false);
			$("#sfpassword").val("111111");
			$("#spassword").val("111111");
			$(".passwordrow").hide();
			$(".area").prop("checked", false).prop("disabled", false);
			$("#myModalLabel").html("服务修改");
			$("#selectedzone").html("");
			$(".btn-save").show();
			$("#myModal").modal("show");
		});
		
		

		
		$(".btn-add").unbind("click").click(function () {
			$(".btn-user-mc").show();
			$("#srole").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();
			$("#edit_userid").val("");
			$("#myModalLabel").html("服务添加");
			$('#sservicename').val("").prop("disabled", false);
			$('#surl').val("").prop("disabled", false);				
			$('#license').val("");
			$('#inputModel').val("");
			$('#meetingType').val("EE");		
			$("#meetingType").prop("disabled", false);
			$("#serviceType").prop("disabled", false);
			
			
			$(".autoGrant").prop("checked", false).prop("disabled", false);
			//Systemuser.getallrolesAndchannels("", true);
			$("#serviceType").val("WEBEX");
			$(".imtypesection").show();
			$(".meetingtypesection").show();
			
			$("#surl").val("");
			$(".btn-save").show();
		
			$(".btn-save").attr("disabled", false);
			$("#myModal").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});	

		
		$(".btn-delete").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();

		
				mConfirm("是否删除？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/orgservice/"+id,
						type : "delete",
						success : function (data) {
							msgBox('success', "服务删除成功");
							
								Systemuser.dataTable.fnDraw();
							
						},
						error : function (data) {
							msgBox('success', "服务删除成功");
							
								Systemuser.dataTable.fnDraw();
							
						},
						dataType : "json"
					});
				});
			
		});
	
		
		$(".btn-save").unbind("click").click(function () {
			
			var modulesArray = new Array();
		
			if (!validator.validate()) {
				return false;
			}
	
		
		
			$(".btn-save").attr("disabled", true);
			var service = {};
			service.serviceLicense = $.trim($('#slicense').val());
			if($("#edit_userid").val() == ""){
				service.serviceName = $.trim($('#sservicename').val());
				service.serviceUrl = $.trim($('#surl').val());
				service.meetingType = $.trim($('#meetingType').val());
				service.serviceType=$.trim($('#serviceType').val());
			}
			if($(".autoGrant").prop("checked")){
				service.autoGrant=1;
			}else{
				service.autoGrant=0;
			}
			if(service.meetingType=="IM"){
				$("#surl").val("");
			}
			service.expirationPeriod=$.trim($('#inputModel').val());
			
			
			var postData = JSON.stringify(service);
			var postURL = G_CTX_ROOT + "/v1/orgservice/";
			var update = false;
			var calltype = "post";
			if ($("#edit_userid").val() != "") {
				postURL = G_CTX_ROOT + "/v1/orgservice/" + $("#edit_userid").val();
				update = true;
				var calltype = "put";
			} else {}
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				success : function (data) {
				
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModal").modal("hide");
					$('#surl').val("");
					$('#slicense').val("");
					
					msgBox('success', "服务修改成功");
					Systemuser.dataTable.fnDraw();
				},
				error : function (data) {
					
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "service.servicename.used") {
						$(".btn-save").attr("disabled", false);
						$("#sservicename").nextAll().filter("span").html("<font color=red >服务名称已经存在！</font>");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "service.already.defined") {
						$(".btn-save").attr("disabled", false);
						$("#surl").nextAll().filter("span").html("<font color=red >服务站点已经存在！</font>");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "webex.service.definition.conflicted") {
						$(".btn-save").attr("disabled", false);
						$("#surl").nextAll().filter("span").html("<font color=red >服务站点定义冲突！</font>");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "license.exhausted") {
						$(".btn-save").attr("disabled", false);
						$("#slicense").nextAll().filter("span").html("<font color=red >许可数不能少于已经使用数量！</font>");
						return;
					}
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModal").modal("hide");
					$('#surl').val("");
					$('#slicense').val("");
					
					msgBox('success', "服务增加成功");
					Systemuser.dataTable.fnDraw();
				},
			});
		});
	},
    
	
	getUserDetail : function (userid) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/orgservice/" + userid,
			data : "",
			type : "get",
			success : function (data) {
				console.log("her",data,data.serviceName);
				$("#surl").val(data.serviceUrl);
				$("#sservicename").val(data.serviceName);
				$("#slicense").val(data.serviceLicense);
				$("#serviceType").val(data.serviceType);
				$("#inputModel").val(data.expirationPeriod);
				if(data.serviceType=="WEBEX"){
					$(".meetingtypesection").show();
				}else{
					$(".meetingtypesection").hide();
				}
				
				if(data.serviceType=="IM"){
					$(".imtypesection").hide();
					$("#surl").val("IM");
				}else{
					$(".imtypesection").show();
				}
				
				$("#meetingType").val(data.meetingType);
				
				if(data.autoGrant==1){
					$(".autoGrant").prop("checked",true);
				}else{
					$(".autoGrant").prop("checked",false);
				}
				
				$("#meetingType").prop("disabled", true);
				$("#serviceType").prop("disabled", true);
				$("#surl").prop("disabled", true);
				$("#sservicename").prop("disabled", true);
				//$(".autoGrant").prop("disabled", true);
			},
		});
	},
	

}
var rule = {
	slicense : {
		name : "总许可数",
		method : {
			required : true,
			isNum : true,
			lt : 7,
		},
		defaultValue : ""
	},
	
	inputModel : {
		name : "过期天数",
		method : {			
			isNum : true,
			lt : 5,
		},
		defaultValue : ""
	},
	
	sservicename : {
		name : "服务名称",
		method : {
			required : true,			
			lt : 200,
		},
		defaultValue : ""
	},
	surl : {
		name : "服务站点",
		method : {
			required : true,
			notNum : true,
			lt : 2000,
		},
		onBlur : Systemuser.checkDomain
	}
	
};
validator.init(rule);
validator.validate();
$(document).ready(Systemuser.init());
