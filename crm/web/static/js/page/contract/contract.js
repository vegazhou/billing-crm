var EETableColumnsUser = [
				     
                  	{
                  		"sTitle": "合同名称",
                  		"mData": "pdfName",
                  		"bSortable": false,
                  		"sClass": "right",
                  		"render": function (data) {
                  			 return "<input name='productnameforedit' type='hidden' value='" + data + "'/>" + htmlencode(data);
                  		}
                  	},
                  	
                  	

                  	{
                  		"sTitle": "合同编号",
                  		"mData": "contractNumber",
                  		"bSortable": false,
                  		"sClass": "center",
                  		"render": function (data) {
                  			return htmlencode(data);
                  		}
                  	},
                  	
                  	{
                  		"sTitle": "操作",
                  		"mData": "f6",
                  		"bSortable": false,
                  		"sClass": "center",
                  		"render": function (data, dis, obj) {
                  		    deleteDom = "<span class=\"btn btn-danger btn-xs btn-deletepdf\">删除</span> ";
							exportDom = "<span class=\"btn btn-info btn-xs btn-exportpdf\">下载</span> ";
							return exportDom+deleteDom+  "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";

                  			
                  			
                  		}
                  	}
                	];



var Systemuser = {
	totalSalesman : [],
	dataTable : null,
	dataTable1 : null,
	options : "",
	row_count : 0,
	orgservicelist:null,
	allorgservicelist:null,
	deletedorgservicelist:null,
	userservicelist:[],
	init : function () {
		$.fn.modal.Constructor.prototype.enforceFocus = function () {};
		Systemuser.totalSalesman = [];
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
				"sTitle" : "客户名称",
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
						return "审核中";
					} else if (data == "IN_EFFECT") {
						return "已生效";
					} else if (data == "WAITING_FIN_APPROVAL") {
						return "财务收款确认中";
					} else if (data == "END_OF_LIFE") {
						return "已中止";
					}
					return data;
				}
			},
			{
				"sTitle" : "销售员姓名 ",
				"mData" : "salesManName",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
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
				"sTitle" : "分销/代理商",
				"mData" : "agentName",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if (data == null) {
						return "";
					} else {
						return htmlencode(data);
					}
				}
			},

			{
				"sTitle" : "合同操作",
				"mData" : "f6",
				"bSortable" : false,
				"sClass" : "center",
				"sWidth" : "13%",
				"render" : function (data, dis, obj) {
					var uploadDom = "<span class=\"btn btn-success btn-xs btn-list-pdf\">下载合同</span> <span class=\"btn btn-success btn-xs btn-upload\">上传合同</span> <input type='hidden' name='sysId' value='" + obj.pid + "'/>";
					return uploadDom;
				}
			},
			 {
				"sTitle" : "操作",
				"mData" : "f6",
				"bSortable" : false,
				"sClass" : "center",
				"sWidth" : "18%",
				"render" : function (data, dis, obj) {
				
					
					var orderDom = "<span class=\"btn btn-success btn-xs btn-contract\">编辑订单</span> ";
					var renameDom = "<span class=\"btn btn-success btn-xs btn-detail\">修改基础信息</span> ";
					var sendApproveDom = "<span class=\"btn btn-warning btn-xs btn-send\">送审</span> ";
					var deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> ";
					var withdrawDom = "<span class=\"btn btn-danger btn-xs btn-withdraw\">撤回</span> ";
					var viewDom = "<span class=\"btn btn-info btn-xs btn-contract\">查看订单</span> ";
					var alterDom = "<span class=\"btn btn-warning btn-xs btn-alter\">合同变更</span> ";

					if (obj.status == "DRAFT") {
						return renameDom + orderDom + sendApproveDom + deleteDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/><input type='hidden' name='alter' value='" + obj.alter + "'/>";
					} else if (obj.status == "WAITING_APPROVAL") {
						return viewDom + withdrawDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
					} else if (obj.status == "IN_EFFECT") {
						return viewDom + alterDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
					} else if (obj.status == "WAITING_FIN_APPROVAL") {
						return viewDom + withdrawDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
					}
					return "";
				}
			}, ];
		setting.aaSorting = [[4, "desc"]];
		setting.bStateSave = false;
		
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		Systemuser.initMoney();
	},
	
	initMoney : function () {
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/contractpdf/query"+$("#s_sserviceid2").val();
		//Systemuser.dataTable1=null;
		//$('#datatable_tabletools_users').dataTable(null);
		//self.bindLaterEvent();
		setting.aoColumns = EETableColumnsUser;
		setting.aaSorting = [[2, "desc"]];
		setting.bStateSave = false;
		setting.bPaginate = false;
		Systemuser.dataTable1 = $('#datatable_tabletools_users').dataTable(setting);
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

	
		
		$("#file").val('').unbind("change").change(function () {			
			 
			    $('#filetxt').val($(this).val());
		
		});

		$(".btn-detail").unbind("click").click(function () {
		
			validator.clearForm();
			$("#sname").nextAll().filter("span").html("");
			$(".btn-save").attr("disabled", false);
			var parentTR = $(this).parents("TR"); 
			var contractId = parentTR.find("input[type=hidden][name=sysId]").val();
			var alter = parentTR.find("input[type=hidden][name=alter]").val()
			$("#edit_userid").val(contractId);
			//zzz
			Systemuser.getallservices(contractId,alter);

			
			$("#myModalLabel").html("合同修改");
			
			$(".btn-save").show();
			$("#myModal").modal("show");
		});	
		
		$(".btn-list-pdf").unbind("click").click(function () {
			
			$("#sname").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();			
			$("#myPdfModalLabel").html("合同列表");
			$('#sname').val("").prop("disabled", false);
			$("#s_sserviceid2").val($(this).parents("TR").find("input[type=hidden][name=sysId]").val());
			Systemuser.dataTable1.fnDraw();
			$("#edit_userid").val("");
			$(".btn-save").show();		
			$(".btn-save").attr("disabled", false);
			$("#myPdfListModal").modal("show");
		});
		
		$(".btn-upload-pdf").unbind("click").click(function () {
			if($('#filetxt').val()==""){
				$("#filetxt").nextAll().filter("span").html("<font color=red >请上传文件!</font>");
				return;
			}
			if($('#contractNumber').val()==""){
				$("#contractNumber").nextAll().filter("span").html("<font color=red >请输入合同编号!</font>");
				return;
			}
			var contractId = $("#edit_userid").val();
		    $.ajaxFileUpload({
		        url:G_CTX_ROOT + "/v1/contractpdf/uploadlarge/" +contractId+"/"+$('#contractNumber').val(),
		        secureuri:false,                       //是否启用安全提交,默认为false
		        fileElementId:'file',           //文件选择框的id属性
		        dataType:'json',                      //服务器返回的格式,可以是json或xml等
		        success:function(data, status){        //服务器响应成功时的处理函数


		        	if(data.success){
		        		mAlert("上传成功!");
		        		
		        		//dataTable.fnDraw();
		        		
		        		$("#childClassID").val("");
			        	$("#myModalErrorMessage").hide();
		        		$("#myModalSelectFile").modal("hide");
		        		$("#edit_userid").val("");
			        }else{
			        	var errorMessage = $(".errorMessage font");

			        	var msg = "";
			        	if(data.detail){
				        	

			        			msg =  "&nbsp;&nbsp;&nbsp;&nbsp;"+ data.detail + "<br>";
			        		
		        		}
		        		errorMessage.html(msg);
		        		$("#myModalSelectFile").modal("hide");
		        		$("#myModalErrorMessage").modal("show");
		        		$("#edit_userid").val("");
			        }
		        },
		        error:function(data, status, e){ //服务器响应失败时的处理函数

		        	mAlert("导入失败，请联系系统管理员！");
		        	$("#file").val("");
	        		$("#childClassID").val("");
	        		$("#myModalErrorMessage").modal("hide") ;
	        		$("#myModalSelectFile").modal("hide");
	        		$("#edit_userid").val("");
		        }
		    });		
		});	
		$(".btn-contract").unbind("click").click(function () {
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
			var status= $(tds[2]).html();
			window.location.href = '../order/order.jsp?id='+$(this).parents("TR").find("input[type=hidden][name=sysId]").val()+"&name="+encodeURIComponent(uName)+"&status="+status;
			
		});
	
		
		$(".btn-add").unbind("click").click(function () {
			
			$("#sname").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();			
			$("#myModalLabel").html("合同添加");
			$('#sname').val("").prop("disabled", false);
			
			$("#edit_userid").val("");
			$(".btn-save").show();		
			$(".btn-save").attr("disabled", false);
			$("#myModal").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});
		
		$("#s_sserviceid").unbind("change").change(function () {
			
			Systemuser.refresh();
		
		});
		
		
		$(".btn-upload").unbind("click").click(function () {
			$('#filetxt').val("");
			var parentTR = $(this).parents("TR"); 
			var userid = parentTR.find("input[type=hidden][name=sysId]").val();
			$("#contractNumber").nextAll().filter("span").html("");
			$("#filetxt").nextAll().filter("span").html("");
			$('#contractNumber').val("").prop("disabled", false);
			$("#edit_userid").val(userid);
			$("#myModalSelectFile").modal("show");
			
		});
		
		
		$(".btn-deletepdf").unbind("click").click(function () {
            var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
            var target = $(this).parents("TR");
            var tds = $(target).children('td');
            var uName = $(tds[0]).find("input[name=productnameforedit]").val();
            var message = "确定删除pdf【" + uName + "】？";

            mConfirm(message, function () {
                $.ajaxInvoke({
                    url: G_CTX_ROOT + "/v1/contractpdf/" + id,
                    type: "delete",
                    success: function (data) {
                        msgBox('success', "PDF删除成功");
                        Systemuser.dataTable1.fnDraw();
                    },
                    error: function (data) {
                        msgBox('fail', "PDF删除失败");
                        Systemuser.dataTable1.fnDraw();
                    },
                    dataType: "html"
                });
            });
        });
		
		$(".btn-delete").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();

		
				mConfirm("<font color='red'>警告！合同一旦删除将无法恢复，是否删除？</font>", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/contract/"+id,
						type : "delete",
						success : function (data) {
							msgBox('success', "合同删除成功");
							
								Systemuser.dataTable.fnDraw();
							
						},
						error : function (data) {
							msgBox('fail', "合同删除失败");
							
								Systemuser.dataTable.fnDraw();
							
						},
						dataType : "html"
					});
				});
			
		});


		$(".btn-send").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
			var message = "确定将合同【" + uName + "】送审？";
			var btnSend = $(this);
			mConfirm(message, function () {
				btnSend.attr("disabled",true);
				btnSend.text("送审中...");
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/contract/send/" + id,
					type: "get",
					success: function (data) {
						msgBox('success', "送审成功");
						btnSend.attr("disabled",false);
						btnSend.text("送审");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "送审失败");
						btnSend.attr("disabled",false);
						btnSend.text("送审");
						Systemuser.dataTable.fnDraw();

					},
					dataType: "html"
				});
			});
		});


		$(".btn-withdraw").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
			var message = "确定将合同【" + uName + "】撤回？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/contract/withdraw/" + id,
					type: "get",
					success: function (data) {
						msgBox('success', "撤回成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "撤回失败");
						Systemuser.dataTable.fnDraw();

					},
					dataType: "html"
				});
			});
		});



		$(".btn-alter").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
			var message = "确定将对合同【" + uName + "】做变更？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/contract/alter/" + id,
					type: "get",
					success: function (data) {
						msgBox('success', "创建变更草案成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "创建变更草案失败");
						Systemuser.dataTable.fnDraw();

					},
					dataType: "html"
				});
			});
		});
		
		$(".btn-reupload").unbind("click").click(function () {
			
			$("#myModalSelectFile").modal("show");
    		$("#myModalErrorMessage").modal("hide");
		});
		
		$(".btn-exportpdf").unbind("click").click(function () {
			
			var pdfid = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			//var accountPeriod = $(this).parents("TR").find("input[type=hidden][name=accountPeriod]").val();
			//var feeType = $(this).parents("TR").find("input[type=hidden][name=feeType]").val();
			location.href=G_CTX_ROOT + "/v1/contractpdf/exportpdflarge/"+pdfid;		
			

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
				business.salesMan = $.trim($('#s_sserviceid').val());
				if($.trim($('#s_agentid').val())!=""){
					
					business.agentId=$.trim($('#s_agentid').val());
				}
				business.isRegistered = $('#isRegistered').prop("checked");
				business.comments = $.trim($('#comments').val());
				
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

	getallagents: function (contractId,alter){



		$.ajaxInvoke({
			url 	: 	G_CTX_ROOT+"/v1/agent/listallagents" ,
			data 	:	"",
			type : "get",
			success	:	function(returnData){
				//returnData= JSON.parse(returnData);

			
				options='<option value="">无</option>';
				$.each(returnData, function(key,value) { 
					options += '<option value="' + value.pid + '">' + value.displayName + '</option>';

					
				});
             
                	$('#s_agentid').html(options).attr("disabled", true);
               
				//Systemuser.init();
				Systemuser.getContractInfo(contractId);
				
			},

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

				//aabbccdd

				//$("#s_sserviceid").val(data.salesManId);
				$("#s_sserviceid").select2('val',data.salesManId)
				$("#s_agentid").val(data.agentId);			
				//$("#sadmin").prop("disabled", true);			
				if(data.registered){		
					$("#isRegistered").prop("checked", true);			
				}else{
					$("#isRegistered").prop("checked", false);	
				}
				$("#comments").val(data.comments);		
				
				
			
			},
		});
	},

	getallservices: function (contractId,alter){


		Systemuser.options="";
		$.ajaxInvoke({
			url 	: 	G_CTX_ROOT+"/v1/salesman/enabledsales" ,
			data 	:	"",
			type : "get",
			success	:	function(returnData){
				//returnData= JSON.parse(returnData);

			
				/*
				$.each(returnData, function(key,value) { 
					Systemuser.options += '<option value="' + value.id + '">' + value.name + '</option>';
				});

				$('#s_sserviceid').html(Systemuser.options);
				*/
				//Systemuser.init();
				Systemuser.getallagents(contractId,alter);
				Systemuser.totalSalesman = [];
				for (var i in returnData) {
					Systemuser.totalSalesman.push({"id":returnData[i].id,"text":returnData[i].name});
				}
				$('#s_sserviceid').select2({
					"data": Systemuser.totalSalesman,
					"placeholder":'选择销售员'
				});
				
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
