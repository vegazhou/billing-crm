var TICK = "<img src=\"" + CTX_ROOT + "/static/img/tick.png\">";
var CROSS = "<img src=\"" + CTX_ROOT + "/static/img/cross.png\">";
var EETableColumns = [
	{
		"sTitle": "用户名",
		"mData": "userName",
		"bSortable": true,
		"sClass": "center",
		"render": function (data) {
			return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" + htmlencode(data);
		}
	},

	{
		"sTitle": "姓名",
		"mData": "fullName",
		"bSortable": true,
		"sClass": "center",
		"render": function (data) {
			return htmlencode(data);
		}
	},

	{
		"sTitle": "开通日期",
		"mData": "grantTime",
		"bSortable": true,
		"sClass": "center",
		"render": function (data) {
			return htmlencode(data);
		}
	},

	{
		"sTitle": "截止日期",
		"mData": "expirationDate",
		"bSortable": true,
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
		"sTitle": "网络会议(MC)",
		"mData": "mcEnabled",
		"bSortable": true,
		"sClass": "center",
		"render": function (data) {
			if (data == 1) {
				return TICK;
			} else {
				return CROSS;
			}
		}
	},

	{
		"sTitle": "网络直播(EC)",
		"mData": "ecEnabled",
		"bSortable": true,
		"sClass": "center",
		"render": function (data) {
			if (data == 1) {
				return TICK;
			} else {
				return CROSS;
			}
		}
	},

	{
		"sTitle": "在线培训(TC)",
		"mData": "tcEnabled",
		"bSortable": false,
		"sClass": "center",
		"render": function (data) {
			if (data == 1) {
				return TICK;
			} else {
				return CROSS;
			}
		}
	},
	{
		"sTitle": "远程支持(SC)",
		"mData": "scEnabled",
		"bSortable": false,
		"sClass": "center",
		"render": function (data) {
			if (data == 1) {
				return TICK;
			} else {
				return CROSS;
			}
		}
	},

	{
		"sTitle": "操作",
		"mData": "f6",
		"bSortable": false,
		"sClass": "center",
		"sWidth": "15%",
		"render": function (data, dis, obj) {
			if (obj.orgName == null&&($("#s_sserviceid").find("option:selected").text().indexOf('WEBEX EE') > 0)) {
				enableDom = "<span class=\"btn btn-success btn-xs btn-detail\">修改</span>  ";
			} else {
				enableDom = "";
			}
			return enableDom  + "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> "+ "<input type='hidden' name='sysId' value='" + obj.userServiceGrantPid + "'/>";
		}
	}];


var EETableColumnsUser = [
					{
						"sTitle": '<input type="checkbox" class="checkbox-checkall" />',
						"mData": "pid",
						//自定义列
						"bSortable" : false ,//排序
						"sClass": "center", 
						"sWidth":"5%",
						"render": function(data) {
					        return "<input type='checkbox' class='item-checkbox' name='checkuser' value='" + data +"'>";
					    }
					},      
                  	{
                  		"sTitle": "用户名",
                  		"mData": "userName",
                  		"bSortable": true,
                  		"sClass": "center",
                  		"render": function (data) {
                  			return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" + htmlencode(data);
                  		}
                  	},

                  	{
                  		"sTitle": "真实姓名",
                  		"mData": "fullName",
                  		"bSortable": true,
                  		"sClass": "center",
                  		"render": function (data) {
                  			return htmlencode(data);
                  		}
                  	},

                  	{
                		"sTitle": "网络会议(MC)",
                		"mData": "mcEnabled",
                		"bSortable": false,
                		"sClass": "center",
                		"render": function (data) {
                			return "<input class='MC' type='checkbox'>";
                		}
                	},

                	{
                		"sTitle": "网络直播(EC)",
                		"mData": "ecEnabled",
                		"bSortable": false,
                		"sClass": "center",
                		"render": function (data) {
                			return "<input class='EC' type='checkbox'>";
                		}
                	},

                	{
                		"sTitle": "在线培训(TC)",
                		"mData": "tcEnabled",
                		"bSortable": false,
                		"sClass": "center",
                		"render": function (data) {
                			return "<input class='TC' type='checkbox'>";
                		}
                	},
                	{
                		"sTitle": "远程支持(SC)",
                		"mData": "scEnabled",
                		"bSortable": false,
                		"sClass": "center",
                		"render": function (data) {
                			return "<input class='SC' type='checkbox'>";
                		}
                	}];

var Systemuser = {
	dataTable : null,
	options : "",
	row_count : 0,
	orgservicelist:null,
	deletedorgservicelist:null,
	userTable:null,
	init : function () {

		var self = this;

		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/userservice/query/1";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		setting.aoColumns = EETableColumns;
		setting.aaSorting = [[0, "desc"]];
		setting.bStateSave = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		
		
		setting.sAjaxSource = G_CTX_ROOT + "/v1/userservice/query/0";
		
		setting.aoColumns = EETableColumnsUser;
		setting.aaSorting = [[1, "desc"]];
		setting.bStateSave = false;
		Systemuser.userTable = $('#datatable_tabletools_users').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		
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
	
		$(".btn-detail").unbind("click").click(function () {
			$(".btn-user-mc").show();
			validator.clearForm();
			$("#editusername").nextAll().filter("span").html("");
			$(".btn-edit").attr("disabled", false);
			var parentTR = $(this).parents("TR");
			var userid = parentTR.find("input[type=hidden][name=sysId]").val();
			$("#edit_serviceid").val(userid);
			$(".editmc").prop("checked",false);
			$(".editsc").prop("checked",false);
			$(".edittc").prop("checked",false);
			$(".editec").prop("checked",false);
			//Systemuser.getalldetails("", true, userid);
			Systemuser.getUserDetail(userid);
			$("#editusername").val($.trim(parentTR.find("input[name=accountnameforedit]").val())).prop("disabled", true);
			
		
			$("#myModalLabeledit").html("用户修改");
			
			$(".btn-edit").show();
			$("#myModaledit").modal("show");
		});


	

		$(".btn-add").unbind("click").click(function () {
			$(".btn-user-mc").show();
			$("#srole").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();
			$(".errormsg").html("");
			
			$("#myModalLabel").html("用户添加");
			
			Systemuser.userTable.fnDraw();
			if ($("#s_sserviceid").find("option:selected").text().indexOf('WEBEX EE') > 0) {
				Systemuser.userTable.fnSetColumnVis(3, true);
				Systemuser.userTable.fnSetColumnVis(4, true);
				Systemuser.userTable.fnSetColumnVis(5, true);
				Systemuser.userTable.fnSetColumnVis(6, true);
			} else {
				Systemuser.userTable.fnSetColumnVis(3, false);
				Systemuser.userTable.fnSetColumnVis(4, false);
				Systemuser.userTable.fnSetColumnVis(5, false);
				Systemuser.userTable.fnSetColumnVis(6, false);
			}
			
			$('#meetingType').val("EE");
			$('#sphone').val("").prop("disabled", false);
			$(".area").prop("checked", false).prop("disabled", false);
			//Systemuser.getallrolesAndchannels("", true);
			$("#selectedzone").html("");
			$(".passwordrow").show();
			$(".btn-save").show();
			$(".btn-create-zone").show();
			$(".btn-save").attr("disabled", false);
			$("#myModal").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			Systemuser.refresh();
			
		});
		
		
		
		$("#s_sserviceid").unbind("change").change(function () {
			
			Systemuser.refresh();
		
		});
		
		$(".btn-search-user").unbind("click").click(function () {
			
			Systemuser.userTable.fnDraw();
		
		});
		



		$(".btn-delete").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();

		
				mConfirm("是否删除？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/userservice/"+orgId+"/"+id,
						type : "delete",
						success : function (data) {
							console.log("garfield11");
							msgBox('success', "服务删除成功");
							
								Systemuser.dataTable.fnDraw();
							
						},
						error : function (data) {
							console.log("garfield112");
							if(data.status=="400"){
							msgBox('fail', "服务删除失败。 原因："+JSON.parse(data.responseText).error.message);
							}
								Systemuser.dataTable.fnDraw();
							
						},
						dataType : "json"
					});
				});
			
		});


		$(".btn-save").unbind("click").click(function () {
		

			var modulesArray = [];
			$("input[type=checkbox][name=checkuser]:checked").each(function () {
				if (!$(this).hasClass("checkbox-checkall")) {
					
					OrgUserService4Create={};
					OrgUserService4Create.serviceId=$("#s_sserviceid").val();
					OrgUserService4Create.userId=$(this).val();
					if ($("#s_sserviceid").find("option:selected").text().indexOf('WEBEX EE') > 0) {
						var MC=$(this).parents("TR").find(".MC").prop("checked");
						var SC=$(this).parents("TR").find(".SC").prop("checked");
						var TC=$(this).parents("TR").find(".TC").prop("checked");
						var EC=$(this).parents("TR").find(".EC").prop("checked");
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
					
				}
			});
			console.log("modulesArray",modulesArray);
           
			var data = {};
			
			data.orgUserService4Create = modulesArray;


			var postData = JSON.stringify(data);
			var postURL = G_CTX_ROOT + "/v1/userservice/";
			var update = false;
			var calltype = "post";
		
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				success : function (data) {

					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					
					$("#myModal").modal("hide");
					$('#surl').val("");
					$('#slicense').val("");

					msgBox('success', "用户修改成功");
					Systemuser.dataTable.fnDraw();
				},
				error : function (data) {

					if (data.status == "400" && JSON.parse(data.responseText).error.key == "apis.service.validation.service.existed") {
						$(".btn-save").attr("disabled", false);
						$("#surl").nextAll().filter("span").html("<font color=red >服务站点已经存在！</font>");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "license.exhausted") {
						$(".btn-save").attr("disabled", false);
						$(".errormsg").html("<font color=red >服务许可数不够！</font>");
						return;
					}
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModal").modal("hide");
					$('#surl').val("");
					$('#slicense').val("");

					msgBox('success', "后台用户增加成功");
					Systemuser.dataTable.fnDraw();
				},
			});
		});
		
		
		
		
		$(".btn-edit").unbind("click").click(function () {		
	
					
					OrgUserService4Create={};
					OrgUserService4Create.serviceId=$("#edit_serviceid").val();
					OrgUserService4Create.userId=$("#edit_userid").val();

						var MC=$(".editmc").prop("checked");
						var SC=$(".editsc").prop("checked");
						var TC=$(".edittc").prop("checked");
						var EC=$(".editec").prop("checked");
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
					
		
					
				
			
			console.log("OrgUserService4Create",OrgUserService4Create);
           
		

			var postData = JSON.stringify(OrgUserService4Create);
			var postURL = G_CTX_ROOT + "/v1/userservice/"+$("#edit_serviceid").val();
			
			var calltype = "put";
		
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				success : function (data) {

					$(".btn-edit").attr("disabled", false);
					validator.clearForm();
					
					$("#myModaledit").modal("hide");
					

					msgBox('success', "用户修改成功");
					Systemuser.dataTable.fnDraw();
				},
				error : function (data) {

					if (data.status == "400" ) {
						$(".btn-save").attr("disabled", false);
						$("#editusername").nextAll().filter("span").html("<font color=red >用户修改失败！"+JSON.parse(data.responseText).error.message+"</font>");
						return;
					}
					$(".btn-edit").attr("disabled", false);
					validator.clearForm();
					

					msgBox('success', "用户修改成功");
					Systemuser.dataTable.fnDraw();
				},
			});
		});
	},
	
	
	
	
	

	getallservices: function (){



		$.ajaxInvoke({
			url 	: 	G_CTX_ROOT+"/v1/orgs/orgservice/"+orgId+"?listAll=true" ,
			data 	:	"",
			type : "get",
			success	:	function(returnData){
				//returnData= JSON.parse(returnData);

				$.each(returnData, function(key,value) {
					if(value.serviceType=="WEBEX"){
						Systemuser.options += '<option value="' + value.pid + '">' + value.serviceName + ' (WEBEX '+ value.meetingType+')'+'       可用数'+value.notUsedLicense+'</option>';
					} else {
						Systemuser.options += '<option value="' + value.pid + '">' + value.serviceName + ' ('+value.serviceType+')'+'      可用数'+value.notUsedLicense+'</option>';
					}

				});

				$('#s_sserviceid').html(Systemuser.options);
				Systemuser.init();
				Systemuser.refresh();
			},

		});




	},


	
	getUserDetail : function (userid) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/userservice/" + userid,
			data : "",
			type : "get",
			success : function (data) {
				if(data.ecService==1){
					$(".editec").prop("checked",true);
				}
				if(data.mcService==1){
					$(".editmc").prop("checked",true);
				}
				if(data.tcService==1){
					$(".edittc").prop("checked",true);
				}
				if(data.scService==1){
					$(".editsc").prop("checked",true);
				}
				
				$("#edit_userid").val(data.motherId);
				$("#edit_serviceid").val(data.pid);
			},
		});
	},
	refresh:function(){
		Systemuser.dataTable.fnDraw();
		if ($("#s_sserviceid").find("option:selected").text().indexOf('WEBEX EE') > 0) {
			Systemuser.dataTable.fnSetColumnVis(4, true);
			Systemuser.dataTable.fnSetColumnVis(5, true);
			Systemuser.dataTable.fnSetColumnVis(6, true);
			Systemuser.dataTable.fnSetColumnVis(7, true);
		} else {
			Systemuser.dataTable.fnSetColumnVis(4, false);
			Systemuser.dataTable.fnSetColumnVis(5, false);
			Systemuser.dataTable.fnSetColumnVis(6, false);
			Systemuser.dataTable.fnSetColumnVis(7, false);
			//Systemuser.dataTable.fnSetColumnVis(5, false);
		}
	}
	
	

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

$(document).ready(Systemuser.getallservices());
