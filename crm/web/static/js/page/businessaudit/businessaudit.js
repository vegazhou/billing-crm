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
		setting.sAjaxSource = G_CTX_ROOT + "/v1/biz/queryTemplates";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
		    {
				"sTitle" : "业务名称",
				"mData" : "displayName",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return "<input name='biznameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
				}
			}, 
			
			{
				"sTitle" : "类型",
				"mData" : "type",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if (data == "WEBEX_MC") {
						return "Meeting Center";
					} else if (data == "WEBEX_EC") {
						return "Event Center";
					} else if (data == "WEBEX_TC") {
						return "Training Center";
					} else if (data == "WEBEX_SC") {
						return "Support Center";
					} else if (data == "WEBEX_EE") {
						return "WebEx Enterprise Edition";
					} else if (data == "WEBEX_STORAGE") {
						return "WebEx存储服务";
					} else if (data == "WEBEX_PSTN") {
						return "WebEx电话语音";
					}
					return data;
				}
			},
			
			
			{
				"sTitle" : "状态",
				"mData" : "status",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
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
				"sTitle" : "操作",
				"mData" : "f6",
				"bSortable" : false,
				"sClass" : "center",
				"sWidth" : "15%",
				"render" : function (data, dis, obj) {
					viewDom = "<span class='btn btn-info btn-xs btn-detail'>查看</span> ";
					approveDom = "<span class=\"btn btn-success btn-xs btn-approve\">审核通过</span> ";
					declineDom = "<span class=\"btn btn-danger btn-xs btn-decline\">退回</span> ";
					
					return viewDom + approveDom + declineDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
					
				}
			}, ];
		setting.aaSorting = [[0, "desc"]];
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
			$(".portssection").show();
			validator.clearForm();
			$("#sname").nextAll().filter("span").html("");
			$(".btn-save").attr("disabled", false);
			var parentTR = $(this).parents("TR"); 
			var userid = parentTR.find("input[type=hidden][name=sysId]").val()
			$("#edit_userid").val(userid);
		
			//$("#sname").val(parentTR.find("input[name=accountnameforedit]").val()).prop("disabled", true);
			//$("#edit_username").val($.trim(parentTR.find("input[name=accountnameforedit]").val())).prop("disabled", false);
			Systemuser.getUserDetail(userid);
			$("#myModalLabel").html("业务修改");
			
			$(".btn-save").show();
			$("#myModal").modal("show");
		});	
	
		
		$(".btn-add").unbind("click").click(function () {
			$(".portssection").hide();
			$("#sname").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();			
			$("#myModalLabel").html("业务添加");
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
			var uName = $(tds[0]).find("input[name=biznameforedit]").val();
			var message = "确定将业务方案【" + uName + "】送审？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/biz/send/" + id,
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
		
		$(".btn-approve").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=biznameforedit]").val();
				var message = "确定审核通过【" + uName + "】？";
				mConfirm(message, function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/biz/approve/"+id,
						type : "get",
						success : function (data) {
							msgBox('success', "审核成功");
							
								Systemuser.dataTable.fnDraw();
							
						},
						error : function (data) {
							msgBox('success', "审核成功1");
							
								Systemuser.dataTable.fnDraw();
							
						},
						dataType : "json"
					});
				});
			
		});


		$(".btn-decline").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=biznameforedit]").val();
			var message = "确定退回【" + uName + "】？";
			mConfirm(message, function () {
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/v1/biz/decline/"+id,
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


		$(".btn-withdraw").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=biznameforedit]").val();
			var message = "确定将业务方案【" + uName + "】撤回？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/biz/withdraw/" + id,
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


		$(".btn-delete").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=biznameforedit]").val();
			var message = "确定删除业务方案【" + uName + "】？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/biz/" + id,
					type: "delete",
					success: function (data) {
						msgBox('success', "删除成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('success', "删除失败");
						Systemuser.dataTable.fnDraw();
					},
					dataType: "json"
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
				business.bizType = $.trim($('#s_sserviceid').val());
				
			
				
				
				
				var postURL = G_CTX_ROOT + "/v1/biz/";
				var update = false;
				var calltype = "post";
			}else{
				var business = {};
				business.displayName = $.trim($('#sname').val());
				business.SESSION_PORTS = $.trim($('#s_ports').val());
				
			
				
				
				
				var postURL = G_CTX_ROOT + "/v1/biz/"+$.trim($('#edit_userid').val());
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
					
					msgBox('success', "业务修改成功");
					Systemuser.dataTable.fnDraw();
				},
				error : function (data) {
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
    
	
	getUserDetail : function (userid) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/biz/" + userid,
			data : "",
			type : "get",
			success : function (data) {
				console.log("----",data);
				$("#sname").val(data.displayName);
				$("#s_sserviceid").val(data.type).prop("disabled", true);
				
				
				if(data.type=="WEBEX_MC"){
					$(".scportssection").hide();
					$(".mcportssection").show();
					$(".tcportssection").hide();
					$(".ecportssection").hide();
					$(".eeportssection").hide();
					$("#s_ports_mc").val(data.SESSION_PORTS).prop("disabled", true);
				}
				
				if(data.type=="WEBEX_TC"){
					$(".scportssection").hide();
					$(".mcportssection").hide();
					$(".tcportssection").show();
					$(".ecportssection").hide();
					$(".eeportssection").hide();
					$("#s_ports_tc").val(data.SESSION_PORTS).prop("disabled", true);
				}
				
				if(data.type=="WEBEX_EC"){
					$(".scportssection").hide();
					$(".mcportssection").hide();
					$(".tcportssection").hide();
					$(".ecportssection").show();
					$(".eeportssection").hide();
					$("#s_ports_ec").val(data.SESSION_PORTS).prop("disabled", true);
				}
				//$("#sadmin").prop("disabled", true);			
				
				if(data.type=="WEBEX_SC"){
					$(".scportssection").show();
					$(".mcportssection").hide();
					$(".tcportssection").hide();
					$(".ecportssection").hide();
					$(".eeportssection").hide();
					$("#s_ports_sc").val(data.SESSION_PORTS).prop("disabled", true);
				}

				if(data.type=="WEBEX_EE"){
					$(".scportssection").hide();
					$(".mcportssection").hide();
					$(".tcportssection").hide();
					$(".ecportssection").hide();
					$(".eeportssection").show();
					$("#s_ports_ee").val(data.SESSION_PORTS).prop("disabled", true);
				}

			},
		});
	},

	

	


	

	bindLaterEvent:function(){
		console.log("bindinglater");
		

	

	}


}
var rule = {
	
	
	sname : {
		name : "业务名称",
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
