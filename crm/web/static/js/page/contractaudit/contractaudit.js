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
		setting.sAjaxSource = G_CTX_ROOT + "/v1/contract/query";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
			{
				"sTitle" : "公司名",
				"mData" : "company",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
		    {
				"sTitle" : "合同名称",
				"mData" : "displayName",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return "<input name='contractnameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
				}
			}, 
			{
				"sTitle" : "状态",
				"mData" : "status",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if (data == "DRAFT") {
						return "起草中";
					} else if (data == "WAITING_APPROVAL") {
						return "待审";
					} else if (data == "IN_EFFECT") {
						return "生效中";
					} else if (data == "WAITING_FIN_APPROVAL") {
						return "财务收款确认中";
					} else if (data == "END_OF_LIFE") {
						return "已中止";
					}
					return data;
				}
			},
			
			{
				"sTitle" : "分销/代理商",
				"mData" : "agentName",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if(data!=null){return htmlencode(data);}else{
						return "";
					}
					
				}
			},
			{
				"sTitle" : "创建日期",
				"mData" : "draftDate",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},

			 {
				"sTitle" : "操作",
				"mData" : "f6",
				"bSortable" : false,
				"sClass" : "center",
				"sWidth" : "30%",

				"render" : function (data, dis, obj) {				
					
					
					var orderDom = "<span class=\"btn btn-info btn-xs btn-contract\">查看订单</span> ";
					
			

					 var approveDom = "<span class=\"btn btn-success btn-xs btn-approve\">审核通过</span> ";
					 var declineDom = "<span class=\"btn btn-danger btn-xs btn-decline\">退回</span> ";
					 return orderDom+approveDom + declineDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
				 }
			}, ];
		setting.aaSorting = [[2, "desc"]];
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
			var contractId = parentTR.find("input[type=hidden][name=sysId]").val()
			$("#edit_userid").val(contractId);
		

			Systemuser.getContractInfo(contractId);
			$("#myModalLabel").html("合同修改");
			
			$(".btn-save").show();
			$("#myModal").modal("show");
		});	
		
		
		$(".btn-contract").unbind("click").click(function () {
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
			window.location.href = '../order/order.jsp?id='+$(this).parents("TR").find("input[type=hidden][name=sysId]").val()+"&name="+uName;
			
		});
	
		

		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});
		
		$("#s_sserviceid").unbind("change").change(function () {
			
			Systemuser.refresh();
		
		});
		
		
	
		
		$(".btn-approve").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
				var message = "确定审批通过【" + uName + "】？";
				var btnApprove = $(this);
				mConfirm(message, function () {
					btnApprove.attr("disabled",true);
					btnApprove.text("通过中...");
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/contract/approve/"+id,
						type : "get",
						success : function (data) {
							msgBox('success', "操作成功");

							btnApprove.attr("disabled",false);
							btnApprove.text("审核通过");

								Systemuser.dataTable.fnDraw();
							
						},
						error : function (data) {
							msgBox('fail', "操作失败");

								btnApprove.attr("disabled",false);
								btnApprove.text("审核通过");
							
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
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
			var message = "<font color='red'>确定将【" + uName + "】退回？</font>";
			mConfirm(message, function () {
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/v1/contract/decline/"+id,
					type : "get",
					success : function (data) {
						msgBox('success', "操作成功");

						Systemuser.dataTable.fnDraw();

					},
					error : function (data) {
						msgBox('fail', "操作失败");
						Systemuser.dataTable.fnDraw();
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
				business.customerId = $.trim($('#s_sserviceid').val());
				
			
				
				
				
				var postURL = G_CTX_ROOT + "/v1/contract/";
				var update = false;
				var calltype = "post";
			}else{
				var business = {};
				business.displayName = $.trim($('#sname').val());		
				
				
				var postURL = G_CTX_ROOT + "/v1/contract/"+$.trim($('#edit_userid').val());
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
					
					msgBox('success', "合同修改成功");
					Systemuser.dataTable.fnDraw();
				},
				error : function (data) {
					
					
					$(".btn-save").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModal").modal("hide");
					$('#sname').val("");
					
					msgBox('fail', "合同操作失败");
					Systemuser.dataTable.fnDraw();
				},
			});
		});
	},


	getContractInfo : function (contractId) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/contract/" + contractId,
			data : "",
			type : "get",
			success : function (data) {
				console.log("----",data);
				$("#sname").val(data.displayName);
				
							
				//$("#sadmin").prop("disabled", true);			
				
				
				
			
			},
		});
	},



	refresh:function(){
		Systemuser.dataTable.fnDraw();
		
	},


	

	bindLaterEvent:function(){
		console.log("bindinglater");
		

	

	}


}
var rule = {
	
	
	sname : {
		name : "合同名称",
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
