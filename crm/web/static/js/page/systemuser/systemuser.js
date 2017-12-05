var Systemuser = {
	dataTable : null,
	options : "",
	row_count : 0,
	userservicelist:[],
	init : function () {
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/orgs/orgusers/query";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		setting.aoColumns = [
		    {
				"sTitle" : "用户名",
				"mData" : "f1",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return "<input name='accountnameforedit' type='hidden' value='" + data + "'/><a href='javascript:void(0);' class='btn-detail'>" + htmlencode(data) + "</a>";
				}
			}, {
				"sTitle" : "真实姓名",
				"mData" : "f2",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			}, {
				"sTitle" : "联系电话",
				"mData" : "f3",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			}, 
			
			 {
				"sTitle" : "角色",
				"mData" : "f4",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					enableDom = data == "002" ? "管理员 " : "普通用户 ";
					if(data=="004"){
						enableDom="财务管理员";
					}
					return enableDom;
				}
			},
			{
				"sTitle" : "状态",
				"mData" : "f5",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return data == "1" ? "已启用" : "已禁用";
				}
			}, {
				"sTitle" : "操作",
				"mData" : "f6",
				"bSortable" : false,
				"sClass" : "center",
				"sWidth" : "15%",
				"render" : function (data, dis, obj) {
					editDom = "";
					enableDom = "";
					deleteDom = "";
					if (systemuserPerms.editPerm) {
						editDom = "<span class=\"btn btn-success btn-xs btn-detail\">修改</span> ";
					}
					if (systemuserPerms.editPerm && obj.f0 != userId) {
						enableDom = obj.f5 == "1" ? "<span class='btn btn-danger btn-xs btn-active'>禁用  </span> " : "<span class='btn btn-warning btn-xs btn-active'>启用 </span> ";
					} else {
						enableDom = "";
					}
					return editDom + enableDom + deleteDom + "<input type='hidden' name='sysId' value='" + obj.f0 + "'/>";
				}
			}, ];
		setting.aaSorting = [[0, "desc"]];
		setting.bStateSave = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		Systemuser._initFile();
	},
	bindButtonEvent : function () {
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
	
		$(".btn-active").unbind("click").click(function () {
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var userid = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var statusObj = $(tds[4]);
			var uName = $(tds[0]).find("input[name=accountnameforedit]").val();
			var activeMsg = "确定禁用用户【" + uName + "】？";
			var isActive = (statusObj.html().indexOf("启用") > 0);
			var status = 0;
			if (!isActive) {
				activeMsg = "确定启用用户【" + uName + "】？";
				status = 1;
			}
			var user = {};
			user.status = status;
			user.fullName = $(tds[1]).text();
			user.mobile = $(tds[2]).text();
			var postData = JSON.stringify(user);
			var submitPath = G_CTX_ROOT + "/v1/orgs/orgusers/" + userid + "/enable";
			if (isActive) {
				submitPath = G_CTX_ROOT + "/v1/orgs/orgusers/" + userid + "/disable";
			}

			mConfirm(activeMsg, function () {
				$.ajaxInvoke({
					url : submitPath,
					data : postData,
					type : "post",
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
						msgBox("", "账号状态已改变");
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
			$("#sname").prop("disabled", false);
			$("#sphone").prop("disabled", false);
			$("#roleId").prop("disabled", false);
			$("#type").prop("disabled", false);
			$(".adminrole").prop("checked",false);
			Systemuser.getallrolesAndchannels(orgId, true,false,userid);
			
			//Systemuser.getalldetails("", true, userid);
			
			$("#saccount").val(parentTR.find("input[name=accountnameforedit]").val()).prop("disabled", true);
			$("#edit_username").val($.trim(parentTR.find("input[name=accountnameforedit]").val())).prop("disabled", false);
			$("#sfpassword").val("111111");
			$("#spassword").val("111111");
			$(".passwordrow").hide();
			$(".area").prop("checked", false).prop("disabled", false);
			$("#myModalLabel").html("账号修改");
			$("#selectedzone").html("");
			$(".btn-save").show();
			$("#myModal").modal("show");
		});
		$(".btn-detailinfor").unbind("click").click(function () {
			validator.clearForm();
			$("#srole").nextAll().filter("span").html("");
			var parentTR = $(this).parents("TR");
			var userid = parentTR.find("input[type=hidden][name=sysId]").val();
			$("#edit_userid").val(userid);
			var hiddens = parentTR.find("input[type=hidden]");
			$("#sphone").val($(hiddens[1]).val()).prop("disabled", true);
			$("#sname").val($(hiddens[2]).val()).prop("disabled", true);
			var roleid = $(hiddens[3]).val();
			$(".adminrole").prop("checked",false);
			Systemuser.getUserDetail(userid);
			//Systemuser.getalldetails("", true, userid);
			$("#saccount").val(parentTR.find("input[name=accountnameforedit]").val()).prop("disabled", true);
			$("#roleId").prop("disabled", true);
			$("#type").prop("disabled", true);
			$(".passwordrow").hide();
			$(".area").prop("checked", false).prop("disabled", true);
			$("#myModalLabel").html("用户信息");
			$("#selectedzone").html("");
			$(".btn-save").hide();
			$(".btn-user-mc").hide();
			$("#myModal").modal("show");
		});
		$(".btn-add").unbind("click").click(function () {
			$(".btn-user-mc").show();
			$("#srole").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();
			$("#edit_userid").val("");
			$("#myModalLabel").html("用户添加");
			$('#sname').val("").prop("disabled", false);
			$('#saccount').val("").prop("disabled", false);
			$("#roleId").prop("disabled", false);
			$("#type").prop("disabled", false);
			$('#spassword').val("");
			$('#sfpassword').val("");
			$('#sphone').val("").prop("disabled", false);
			$(".adminrole").prop("checked",false);
			$(".area").prop("checked", false).prop("disabled", false);
			Systemuser.getallrolesAndchannels(orgId, true,true,"");
			$("#selectedzone").html("");
			$(".passwordrow").show();
			$(".btn-save").show();
			$(".btn-create-zone").show();
			$(".btn-save").attr("disabled", false);
			$("#myModal").modal("show");
		});
		
		$(".btn-upload").unbind("click").click(function () {
			var validSubmit=true;
			var fileObj = $("#file");
            var filepath = fileObj.val();
            console.log("fileObj:" + filepath);
            if (!filepath) {
                $('#fileError').html('<font color=red>请选择需要导入的文件！</font>');
                validSubmit = false;
            }
            
            var extStart = filepath.lastIndexOf(".");
            var ext = filepath.substring(extStart, filepath.length).toUpperCase();

            if (filepath && ext != ".XLS" && ext != ".XLSX") {
                $('#fileError').html('<font color=red>文件类型不对，请下载使用Excel模板文件！</font>');
                validSubmit = false;
            }



            if (!validSubmit) {
                return false;
            }

            var _this = this;
            //开始上传文件时显示一个图片,文件上传完成将图片隐藏
            //$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});

            //执行上传文件操作的函数
            $.ajaxFileUpload({
                url: G_CTX_ROOT + "/v1/orgs/orgusers/upload",
                headers : {'Source' : '101','Token':token},
                secureuri: false, //是否启用安全提交,默认为false
                fileElementId: 'file', //文件选择框的id属性
                dataType: 'json', //服务器返回的格式,可以是json或xml等
                success: function(data, status) { //服务器响应成功时的处理函数
                    console.log(data);
                    console.log("success");
                    if (data.success) {

                        //dataTable.fnDraw();

                        $("#import_childClassID").val("");
                        Systemuser.hideAllDialog();
                        mAlert("批量导入成功!");
                        Systemuser.dataTable.fnDraw();
                    } else {
                        var errorMessage = $(".errorMessage font");
                        console.log(data.detail);
                        var msg = "";
                        if (data.detail) {
                            data.detail.forEach(function(val, i) {
                                console.log(val);
                                msg = msg + "&nbsp;&nbsp;&nbsp;&nbsp;" + val + "<br>";
                            });
                        }
                        errorMessage.html(msg);
                        Systemuser.showErrorDialog();
                    }
                },
                error: function(data, status, e) { //服务器响应失败时的处理函数
                    console.log(e);
                    mAlert("导入失败，请联系系统管理员！");
                    //_initFile();
                    $("#import_childClassID").val("");
                    Systemuser.hideAllDialog();
                }
            });
		});
	
		$(".btn-reupload").unbind("click").click(function () {	  
			Systemuser.showUploadDialog();
        });
		
		$(".btn-import").unbind("click").click(function () {
			$('#filetxt').val('');
        	$('#filetxt').nextAll('span').html('');
			$("#myModalSelectFile").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});
		$(".btn-delete").unbind("click").click(function () {
			var checkeds = [];
			$("input[type=checkbox]:checked").each(function () {
				if (!$(this).hasClass("checkbox-checkall")) {
					checkeds.push($(this).val());
				}
			});
			if (checkeds.length > 0) {
				mConfirm("是否删除？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/systemuser/delete",
						success : function (data) {
							msgBox(data.state, data.msg);
							if (data.state == "success") {
								Systemuser.dataTable.fnDraw();
							}
						},
						error : "操作失败",
						data : {
							id : checkeds
						},
						dataType : "json"
					});
				});
			} else {
				mAlert("请选择你要删除的信息！", "提示");
			}
		});
		$(".btn-delete-button").unbind("click").click(function () {
			var roleid = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var username = $(this).parents("TR").find("input[name=accountnameforedit]").val();
			mConfirm("确定删除用户【" + username + "】?", function () {
				var checkeds = [];
				checkeds.push(roleid);
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/systemuser/delete",
					data : {
						id : checkeds
					},
					success : function (data) {
						msgBox(data.state, data.msg);
						if (data.state == "success") {
							Systemuser.dataTable.fnDraw();
						}
					}
				});
			});
		});
		
		
		$("#addmodule").unbind("click").click(function () {
			Systemuser.addModule();
		});
		$("#addmodule").unbind("dblclick").dblclick(function () {
			Systemuser.addModule();
		});
		$("#removemodule").unbind("click").click(function () {
			Systemuser.removeModule();
		});
		
		$(".btn-save").unbind("click").click(function () {
			console.log("9999---");
			var modulesArray = new Array();
			var deletesArray = new Array();
			
			if (!validator.validate()) {
				return false;
			}
		
			if($(".UserIM").prop("checked")){
				 OrgUserService4Create={};
				 OrgUserService4Create.serviceId=$(".UserIM").val();
				modulesArray.push(OrgUserService4Create);
			}
			$(".select-role").find("option").each(function () {
				OrgUserService4Create={};
				OrgUserService4Create.serviceId=$(this).val();
				if($(this).text().indexOf("EE")>0){
					var MC=$("#EEservice").find("#"+$(this).val()).children(".MC").prop("checked");
					var SC=$("#EEservice").find("#"+$(this).val()).children(".SC").prop("checked");
					var TC=$("#EEservice").find("#"+$(this).val()).children(".TC").prop("checked");
					var EC=$("#EEservice").find("#"+$(this).val()).children(".EC").prop("checked");
					if(MC==true){
						OrgUserService4Create.mcService=1;
					}
					if(SC==true){
						OrgUserService4Create.scService=1;
					}
					if(TC==true){
						OrgUserService4Create.tcService=1;
					}
					if(EC==true){
						OrgUserService4Create.ecService=1;
					}
					console.log("===",OrgUserService4Create);
				}
				
				modulesArray.push(OrgUserService4Create);				
				
			
				
				
			});
			
	
			/*
			if (modulesArray.length == 0) {
				$("#srole").nextAll().filter("span").html("<font color=red >所选模块不能为空！</font>");
				return false;
			}*/
			$(".btn-save").attr("disabled", true);
			var user = {};
			user.fullName = $.trim($('#sname').val());
			user.password = $.trim($('#spassword').val());
			user.userName = $.trim($('#saccount').val());
			user.mobile = $.trim($('#sphone').val());
			var roleStr="";
			/*
			if($(".adminrole").prop("checked")){
				roleStr="500";
				if($(".finadminrole").prop("checked")){
					roleStr="500;400";
				}
					
			}else if($(".finadminrole").prop("checked")){				
				roleStr="400";
			}*/
			
			if($(".adminrole").prop("checked")){
				roleStr="500";			
					
			}else {				
				roleStr="001";
			}
			
			user.roleId = roleStr;
			user.type = $.trim($('#type').val());
			user.orgUserService4Create = modulesArray;
			
			
			var postURL = G_CTX_ROOT + "/v1/orgs/orgusers/";
			var update = false;
			var calltype = "post";
			if ($("#edit_userid").val() != "") {
				postURL = G_CTX_ROOT + "/v1/orgs/orgusers/" + $("#edit_userid").val();
				update = true;
				var calltype = "put";
				
				$.each(Systemuser.userservicelist, function (key, value) {
					var deleted=true;
					$(".select-role").find("option").each(function () {
						if(value==$(this).val()){
							deleted=false
						  }
						
					});
					
					if(deleted){
						deletesArray.push(value);
					}
				
						
				});
				user.orgUserService4Delete = deletesArray;
				
			} else {}
			var postData = JSON.stringify(user);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				success : function (data) {
					if ($("#edit_userid").val() != "") {
						msgBox('success', "用户修改成功");
					}else{
						msgBox('success', "用户增加成功");
					}
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModal").modal("hide");
					$('#sname').val("");
					$('#saccount').val("");
					$('#spassword').val("");
					$('#sfpassword').val("");
					$('#sphone').val("");
					
					Systemuser.dataTable.fnDraw();
				},
				error : function (data) {
					
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "user.already.existed") {
						$(".btn-save").attr("disabled", false);
						$("#saccount").nextAll().filter("span").html("<font color=red >用户名已经存在！</font>");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "service.setting.change.failed") {
						$(".btn-save").attr("disabled", false);
						validator.clearForm();
						$("#edit_userid").val("");
						$("#myModal").modal("hide");
						$('#sname').val("");
						$('#saccount').val("");
						$('#spassword').val("");
						$('#sfpassword').val("");
						$('#sphone').val("");
						msgBox('fail', "同步webex服务失败");
						Systemuser.dataTable.fnDraw();
						return;
					}
					if ($("#edit_userid").val() != "") {
						msgBox('success', "用户修改成功");
					}else{
						msgBox('success', "用户增加成功");
					}
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModal").modal("hide");
					$('#sname').val("");
					$('#saccount').val("");
					$('#spassword').val("");
					$('#sfpassword').val("");
					$('#sphone').val("");
					
					Systemuser.dataTable.fnDraw();
				},
			});
		});
	},


	checkUser : function () {
		console.log("here----------");
		var userAccount = $.trim($("#saccount").val());
		if (userAccount == "") {
			$("#saccount").nextAll().filter("span").html("");
			$(".btn-save").attr("disabled", false);
		} else {
			if ($("#edit_username").val() == userAccount) {
				return;
			}
			
			
			var user = {};
			user.email = $.trim($('#saccount').val());
			
			
			var postData = JSON.stringify(user);	
			$.ajaxInvoke({
				url : G_CTX_ROOT + "/v1/orgs/orgusers/checkUserAccount",
				data :postData,
				type : "post",
				success : function (data) {
					console.log("here----------2",data);
					if (data.state == "success") {
						$("#saccount").nextAll().filter("span").html("<font color=red >该帐号已存在，请重输入！</font>");
						$(".btn-save").attr("disabled", true);
						return false;
					} else {
						$("#saccount").nextAll().filter("span").html("");
						$(".btn-save").attr("disabled", false);
					}
				},
				
				error : function (data) {
					console.log("here----------3",data);
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "apis.orguser.validation.userExisted") {
						$(".btn-save").attr("disabled", false);
						$("#saccount").nextAll().filter("span").html("<font color=red >用户名已经存在！</font>");
						return;
					}
				
				}
			});
		}
	},


	getallrolesAndchannels : function (orgId, canedit,add,userId) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/orgs/orgservice/"+orgId,
			type : "get",
			data : "",
			success : function (returnData) {
				Systemuser.options = '';
				Systemuser.selectedoptions = '';
				$("#EEservice").html("");
				$.each(returnData, function (key, value) {
					if(value.serviceType=="WEBEX"){	
						
					if(add)	{
						if(value.autoGrant!=1){
							Systemuser.options += '<option value="' + value.pid + '">' + value.serviceUrl +' ('+value.meetingType+')</option>';
						}else{
							Systemuser.selectedoptions += '<option value="' + value.pid + '">' + value.serviceUrl +' ('+value.meetingType+')</option>';
							if(value.meetingType=="EE"){
								$("#EEservice").append("<div id='"+ value.pid+"'>"+ value.serviceUrl+
										"<br>  <input type='checkbox' class='MC'  >MC <input type='checkbox' class='TC'>TC "+
										"<input type='checkbox' class='EC'>EC <input type='checkbox' class='SC'>SC</div>");
							}
						
						
						}
					}else{
						Systemuser.options += '<option value="' + value.pid + '">' + value.serviceUrl +' ('+value.meetingType+')</option>';
					}	
						
						
						
					}else{
						html='<section class="col col-1">'+
						'<label class="label text-right">'+ 
							'IM'+
						'</label>'+
						'</section>'+
						'<section class="col col-1">'+
						'<div class="">'+
								'<label class="label text-left">'+ 
									'<input type="checkbox" class="UserIM" value="'+value.pid+'">'+	
								'</label>'+
					    '</div>	'+
						'</section>';
						$("#IMsection").html(html);
					}
				});
				$('.select-role').html("");
					$('.select-sysrole').html(Systemuser.options);
			
					$('.select-role').html(Systemuser.selectedoptions);	
				
				
				
				if (!add) {
					Systemuser.getUserDetail(userId);
				}
				if (!canedit) {
					$('.select-role').prop("disabled", true);
				} else {
					$('.select-role').prop("disabled", false);
				}
			
			},
		});
	},
	
	showUploadDialog: function() {
           $("#myModalErrorMessage").modal("hide");
           $("#myModalSelectFile").modal("show");
    },
    showErrorDialog: function() {
          $("#myModalSelectFile").modal("hide");
          $("#myModalfileinprogress").modal("hide");
          $("#myModalErrorMessage").modal("show");
    },

	hideAllDialog: function() {
          $("#myModalErrorMessage").modal("hide");
          $("#myModalSelectFile").modal("hide");
    },
	
	addModule : function () {
		$(".select-sysrole").find("option:selected").each(function () {
			$(".select-role").append("<option value='" + $(this).val() + "'>" + $(this).text() + "</option>");
			if($(this).text().indexOf("EE")>0){
				$("#EEservice").append("<div id='"+ $(this).val()+"'>"+ $(this).text()+
						"<br>  <input type='checkbox' class='MC'  >MC <input type='checkbox' class='TC'>TC "+
						"<input type='checkbox' class='EC'>EC <input type='checkbox' class='SC'>SC</div>");
			}
		});
		$(".select-sysrole").find("option:selected").remove();
	},
	removeModule : function () {
		$(".select-role").find("option:selected").each(function () {
			$(".select-sysrole").append("<option value='" + $(this).val() + "'>" + $(this).text() + "</option>");
			if($(this).text().indexOf("EE")>0){
				console.log("here",$("#EEservice").find("#"+$(this).val()));
				$("#EEservice").find("#"+$(this).val()).remove();
			}
		
		});
	
		$(".select-role").find("option:selected").remove();
		
	},
	getUserDetail : function (userid) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/orgs/orgusers/" + userid,
			data : "",
			type : "get",
			success : function (data) {
				$("#sphone").val(data.mobile);
				$("#sname").val(data.fullName);
				$("#type").val(data.type);
				console.log("df--",data.roles);
				//var roleArray=data.roles.split(";");
				
				$.each(data.roles, function (key, value) {	
					if(value=="500"){
						$(".adminrole").prop("checked",true);
					}/*
					if(value=="400"){
						$(".finadminrole").prop("checked",true);					
					}*/
				});	
				Systemuser.userservicelist=new Array();
				$.each(data.userServiceMap, function (key, value) {
					
						Systemuser.userservicelist.push(value.pid);
						ECService="";
						MCService="";
						TCService="";
						SCService="";
						if(value.ecService==1){
							ECService="checked";
						}
						if(value.mcService==1){
							MCService="checked"
						}
						if(value.tcService==1){
							TCService="checked"
						}
						if(value.scService==1){
							SCService="checked"
						}
						
						$(".select-role").append("<option value='" + value.pid + "'>" + value.serviceUrl + ' ('+value.meetingType+')</option>');
						if(value.meetingType=="EE"){
						$("#EEservice").append("<div id='"+ value.pid+"'>"+ value.serviceUrl+
								"<br>  <input type='checkbox' class='MC' "+MCService+">MC <input type='checkbox' class='TC' "+TCService+">TC "+
								"<input type='checkbox' class='EC' "+ECService+">EC <input type='checkbox' class='SC' "+SCService+">SC</div>");
						}
						
						
					
					if(value.serviceType=="IM"){
						
						$(".UserIM").prop("checked",true);
					}
				});
				
				
				
				
				$(".select-sysrole").find("option").each(function () {
					optionid = $(this).val();
					optionidtext = $(this).text();
					$.each(data.userServiceMap, function (key, value) {
						if (optionid == value.pid) {
							
							
							$(".select-sysrole").find("option[value='" + value.pid + "']").remove();
							
							
						}
						
					});
				});
				/*
				$(".select-sysrole").find("option").each(function () {
					optionid = $(this).val();
					optionidtext = $(this).text();
					$.each(data.modules, function (key, value) {
						if (optionid == value) {
							$(".select-role").append("<option value='" + optionid + "'>" + optionidtext + "</option>");
							$(".select-sysrole").find("option[value='" + value + "']").remove();
						}
					});
				});
				$(".select-syschannel").find("option").each(function () {
					optionid = $(this).val();
					optionidtext = $(this).text();
					if (data.channels != null && data.channels != "") {
						$.each(data.channels, function (key, value) {
							if (optionid == value) {
								$(".select-channel").append("<option value='" + optionid + "'>" + optionidtext + "</option>");
								$(".select-syschannel").find("option[value='" + value + "']").remove();
							}
						});
					}
				});
				*/
			},
		});
	},
	
	 _fileOnchange : function() {
		console.log($('#file'));
	    if ($(this).val()) {
	        $('#fileError').html('');
	    }
	    $('#filetxt').val($(this).val());
	    
	},

   _initFile : function(){
		$('#file').unbind().val('').bind('change', Systemuser._fileOnchange);
	}
}
var rule = {
	sphone : {
		name : "联系电话",
		method : {
			
			phone : true,
			lt : 20,
		},
		defaultValue : "账户"
	},
	saccount : {
		name : "用户名",
		method : {
			required : true,
			email : true,
			lt : 100,
		},
		
	},
	sname : {
		name : "真实姓名",
		method : {
			required : true,
			lt : 20,
		}
	}
};
validator.init(rule);
validator.validate();
$(document).ready(Systemuser.init());
