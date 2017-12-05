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
		setting.sAjaxSource = G_CTX_ROOT + "/v1/product/query";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
		    {
				"sTitle" : "产品名称",
				"mData" : "displayName",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return "<input name='productnameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
				}
			},

			{
				"sTitle" : "是否代理产品",
				"mData" : "agent",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if (data == true) {
						return "是";
					} else {
						return "否";
					}
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
					}else if (data == "END_OF_LIFE") {
						return "已终止";
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
					sendApproveDom = "<span class=\"btn btn-warning btn-xs btn-send\">送审</span> ";
					editDom = "<span class=\"btn btn-success btn-xs btn-detail\">修改</span> ";
					deleteDom = "<span class=\"btn btn-danger btn-xs btn-delete\">删除</span> ";

					withdrawDom = "<span class=\"btn btn-danger btn-xs btn-withdraw\">撤回</span> ";

					viewDom = "<span class=\"btn btn-info btn-xs btn-detail\">查看</span> ";


					if (obj.status == "DRAFT") {
						return sendApproveDom + editDom + deleteDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
					} else if (obj.status == "WAITING_APPROVAL") {
						return withdrawDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
					} else if (obj.status == "IN_EFFECT") {
						return viewDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
					}else if (obj.status == "END_OF_LIFE") {
						return "";
					}
				}
			}, ];
		setting.aaSorting = [[0, "desc"]];
		setting.bStateSave = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
	},
	bindButtonEvent : function () {
		console.log("binlding");



		$(".btn-detail").unbind("click").click(function () {
			$(".btn-user-mc").show();
			$(".portssection").hide();
			validator.clearForm();
			$("#srole").nextAll().filter("span").html("");
			$(".btn-save").attr("disabled", false);
			var parentTR = $(this).parents("TR"); 
			var userid = parentTR.find("input[type=hidden][name=sysId]").val()
			$("#edit_userid").val(userid);
			$("#sname").prop("disabled", false);
			
			//Systemuser.getalldetails("", true, userid);
		
			
			Systemuser.getUserDetail(userid);
			
			$("#myModalLabel").html("产品修改");
			
			$(".btn-save").show();
			$("#myModal").modal("show");
		});	
	
		
		$(".btn-add").unbind("click").click(function () {
			$(".portssection").hide();
			$("#sname").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();
			Systemuser.getallbiz("","");
			$("#myModalLabel").html("产品添加");
			$('#sname').val("").prop("disabled", false);
			$('#isTrial').prop("checked", false);
			$('#isAgent').prop("checked", false);
			$('#s_sserviceid').val("WEBEX_EC").prop("disabled", false);	
			$("#edit_userid").val("");
			$(".btn-save").show();		
			$(".btn-save").attr("disabled", false);
			$("#myModal").modal("show");
		});
	
		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});
	
		$("#s_sserviceid").unbind("change").change(function () {
			console.log("--------------1",this.value);
			if(this.value==""){return;};
			Systemuser.getallchargeforbiz(this.value,"");
			
		});


		$(".btn-send").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=productnameforedit]").val();
			var message = "确定将产品【" + uName + "】送审？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/product/send/" + id,
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


		$(".btn-withdraw").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[0]).find("input[name=productnameforedit]").val();
			var message = "确定将产品【" + uName + "】撤回？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/product/withdraw/" + id,
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
			var uName = $(tds[0]).find("input[name=productnameforedit]").val();
			var message = "确定删除产品【" + uName + "】？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/product/" + id,
					type: "delete",
					success: function (data) {
						msgBox('success', "产品删除成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						console.log("----------");
						msgBox('success', "产品删除成功");
						Systemuser.dataTable.fnDraw();
					},
					dataType: "json"
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
			if($.trim($('#edit_userid').val())==""){
				var business = {};
				business.displayName = $.trim($('#sname').val());
				business.bizId = $.trim($('#s_sserviceid').val());
				business.chargeSchemeId = $.trim($('#s_schargeid').val());
				business.trial = $('#isTrial').prop("checked");
				business.agent = $('#isAgent').prop("checked");
				
				
				var postURL = G_CTX_ROOT + "/v1/product/";
				var update = false;
				var calltype = "post";
			}else{
				var business = {};
				business.displayName = $.trim($('#sname').val());
				business.bizId = $.trim($('#s_sserviceid').val());
				business.chargeSchemeId = $.trim($('#s_schargeid').val());
				business.trial = $('#isTrial').prop("checked");
				business.agent = $('#isAgent').prop("checked");
				
				
				
				var postURL = G_CTX_ROOT + "/v1/product/"+$.trim($('#edit_userid').val());
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
					
					msgBox('success', "产品修改成功");
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
					
					msgBox('success', "产品增加成功");
					Systemuser.dataTable.fnDraw();
				},
			});
		});
	},
    
	
	getUserDetail : function (userid) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/product/" + userid,
			data : "",
			type : "get",
			success : function (data) {
				console.log("----",data);
				$("#sname").val(data.displayName);
				Systemuser.getallbiz(data.bizId,data.chargeSchemeId);
				//$("#s_ports").val(data.SESSION_PORTS);
				if(data.trial){		
					$("#isTrial").prop("checked", true);			
				}else{
					$("#isTrial").prop("checked", false);	
				}
				
				if(data.agent){		
					$("#isAgent").prop("checked", true);			
				}else{
					$("#isAgent").prop("checked", false);	
				}
				
				
			
			},
		});
	},

	
	getallbiz: function (bizid,chargeid){ 

		
		
		
		
		 $.ajaxInvoke({
				url 	: 	G_CTX_ROOT+"/v1/biz/listtemplates" ,
				data 	:	"",
				type    : "get",
				success	:	function(returnData){
								//returnData= JSON.parse(returnData);
								Systemuser.options='<option value="">请选择业务</option>';
								$.each(returnData, function(key,value) { 
									Systemuser.options += '<option value="' + value.id + '">' + value.displayName + '</option>';

									
								});
								
								$('#s_sserviceid').html(Systemuser.options);
							    if(bizid!=""){
							    	$('#s_sserviceid').val(bizid);
							    	 Systemuser.getallchargeforbiz(bizid,chargeid);
							    }
							   
							
							},
				
			});
		
		
	
	
	},
	
	getallchargeforbiz: function (bizid,chargeid){ 		
		
		 $.ajaxInvoke({
				url 	: 	G_CTX_ROOT+"/v1/charges/listallchargeforbiz/"+bizid ,
				data 	:	"",
				type    : "get",
				success	:	function(returnData){
								//returnData= JSON.parse(returnData);
								Systemuser.options="";
								$.each(returnData, function(key,value) { 
									Systemuser.options += '<option value="' + value.id + '">' + value.displayName + '</option>';

									
								});
								
								$('#s_schargeid').html(Systemuser.options);
								 if(chargeid!=""){
								    	$('#s_schargeid').val(chargeid);
								    }
							
							},
				
			});
		
		
	
	
	},
	

	bindLaterEvent:function(){
		console.log("bindinglater");
		

	

	}

}
var rule = {
	
	
	/*
	sdomain : {
		name : "企业域名",
		method : {
			required : true,
			notNum : true,
			lt : 2000,
		},
		onBlur : Systemuser.checkDomain
	},*/
	sname : {
		name : "产品名称",
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
