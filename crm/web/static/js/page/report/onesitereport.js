var Systemuser = {
	dataTable : null,
	options : "",
	row_count : 0,
	orgservicelist:null,
	allorgservicelist:null,
	deletedorgservicelist:null,
	userservicelist:[],
	init : function () {
		var parameter=window.location.href.split('id=')[1];
		Systemuser.contractId=parameter.substring(0,parameter.indexOf("&"));
		//Systemuser.contractName=decodeURIComponent(parameter.split('&name=')[1]);
		//$("#s_sserviceid1").val(Systemuser.contractName);
		$("#s_siteid").val(Systemuser.contractId);
		$("#pagetitle").val("客户列表");
		
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/sitereport/queryonesite";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
			{
				"sTitle" : "站点名称",
				"mData" : "siteName",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},                  
		                     
		                     
		    {
				"sTitle" : "客户名称",
				"mData" : "customer_NAME",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return "<input name='contractnameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
				}
			}, 
			{
				"sTitle" : "产品名称",
				"mData" : "product_NAME",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},  
			{
				"sTitle" : "起始时间",
				"mData" : "effectivestartdate",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			{
				"sTitle" : "结束时间",
				"mData" : "effectiveenddate",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			{
				"sTitle" : "操作",
				"mData" : "contractId",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data,dis, obj) {
					if (obj.contractId == "" || obj.contractId == null) {
						return ""
					} else {
						var link = "/crm/views/order/order.jsp?id=" + obj.contractId + "&name="+encodeURIComponent(obj.contract_NAME)+"&reseller="+encodeURIComponent(obj.reseller);
						return "<a href='" + link + "'>查看合同</a>";
					}
				}
			}
			
			  ];
		setting.aaSorting = [[0, "desc"]];
		setting.bStateSave = false;
		Systemuser.dataTable = $('#datatable_tabletools').dataTable(setting);
		$("#datatable_tabletools_filter").html("");
		Systemuser.getallservices();
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
			var userid = parentTR.find("input[type=hidden][name=sysId]").val()
			$("#edit_userid").val(userid);
		
			//$("#sname").val(parentTR.find("input[name=accountnameforedit]").val()).prop("disabled", true);
			//$("#edit_username").val($.trim(parentTR.find("input[name=accountnameforedit]").val())).prop("disabled", false);
			Systemuser.getUserDetail(userid);
			$("#myModalLabel").html("站点信息");
			$('#sname').val("").prop("disabled", true);
			$("#edit_username").val("");			
			$("input[type='checkbox']").prop("checked", false);
			$("#s_languageid").val("");
			$("#s_sserviceid").val("");	
			$("#s_location").val("");
			$("#storageCapacity").val("");
			$('.scLicenseModel').hide();
			$('.ecLicenseModel').hide();
			$('.tcLicenseModel').hide();
			$('.mcLicenseModel').hide();
			$('.eeLicenseModel').hide();
			$("input[type='text']").val("");
			$(".btn-save").show();
			$("#myModal").modal("show");
		});	
		
		
		$(".btn-contract").unbind("click").click(function () {
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
			window.location.href = '../order/order.jsp?id='+$(this).parents("TR").find("input[type=hidden][name=sysId]").val()+"&name="+uName;
			
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
							msgBox('success', "合同删除失败");
							
								Systemuser.dataTable.fnDraw();
							
						},
						dataType : "json"
					});
				});
			
		});


		$(".btn-send").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var uName = $(tds[1]).find("input[name=contractnameforedit]").val();
			var message = "确定将合同【" + uName + "】送审？";

			mConfirm(message, function () {
				$.ajaxInvoke({
					url: G_CTX_ROOT + "/v1/contract/send/" + id,
					type: "get",
					success: function (data) {
						msgBox('success', "送审成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "送审失败");
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
					
					msgBox('success', "合同操作失败");
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
	
	
	getUserDetail : function (userid) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/wbxsite/" + userid,
			data : "",
			type : "get",
			success : function (data) {
				console.log("----",data);
				$("#sname").val(data.siteName);
				$("#edit_username").val(data.siteName);				
				$("#s_languageid").val(data.primaryLanguage);
				$("#s_sserviceid").val(data.timeZone);	
				$("#s_location").val(data.location);
				$("#storageCapacity").val(data.storageCapacity);
				if(data.callBack==1){
					$('.callBack').prop("checked",true);
				}
				if(data.audioBroadCast==1){
					$('.audioBroadCast').prop("checked",true);
				}
				if(data.internationalCallBack==1){
					$('.internationalCallBack').prop("checked",true);
				}
				if(data.tollCallIn==1){
					$('.tollCallIn').prop("checked",true);
				}
				if(data.tollFreeCallIn==1){
					$('.tollFreeCallIn').prop("checked",true);
				}
				
				if(data.globalCallIn==1){
					$('.globalCallIn').prop("checked",true);
				}
				
				if(data.cloudConnectedAudio==1){
					$('.cloudConnectedAudio').prop("checked",true);
				}
				
				if(data.voip==1){
					$('.voip').prop("checked",true);
				}
				
				if(data.sipInOut==1){
					$('.sipInOut').prop("checked",true);
				}
				
				if(data.mcLicenseModel!=null){
					$('.mcLicenseModel').show();
					if(data.mcLicenseOverage==1){
						$('.mcLicenseOverage').prop("checked",true);
					}
					
					if(data.mcAttendeeOverage==1){
						$('.mcAttendeeOverage').prop("checked",true);
					}
					$("#mcLicenseVolume").val(data.mcLicenseVolume);
					$("#mcAttendeeCapacity").val(data.mcAttendeeCapacity);
					
				}
				
				if(data.scLicenseModel!=null){
					$('.scLicenseModel').show();
					if(data.scLicenseOverage==1){
						$('.scLicenseOverage').prop("checked",true);
					}
					
					if(data.scAttendeeOverage==1){
						$('.scAttendeeOverage').prop("checked",true);
					}
					$("#scLicenseVolume").val(data.scLicenseVolume);
					$("#scAttendeeCapacity").val(data.scAttendeeCapacity);
					
				}
				
				if(data.tcLicenseModel!=null){
					$('.tcLicenseModel').show();
					if(data.tcLicenseOverage==1){
						$('.tcLicenseOverage').prop("checked",true);
					}
					
					if(data.tcAttendeeOverage==1){
						$('.tcAttendeeOverage').prop("checked",true);
					}
					$("#tcLicenseVolume").val(data.tcLicenseVolume);
					$("#tcAttendeeCapacity").val(data.tcAttendeeCapacity);
					
				}
				
				if(data.ecLicenseModel!=null){
					$('.ecLicenseModel').show();
					if(data.ecLicenseOverage==1){
						$('.ecLicenseOverage').prop("checked",true);
					}
					
					if(data.ecAttendeeOverage==1){
						$('.ecAttendeeOverage').prop("checked",true);
					}
					$("#ecLicenseVolume").val(data.ecLicenseVolume);
					$("#ecAttendeeCapacity").val(data.ecAttendeeCapacity);
					
				}
				
				if(data.eeLicenseModel!=null){
					$('.eeLicenseModel').show();
					if(data.eeLicenseOverage==1){
						$('.eeLicenseOverage').prop("checked",true);
					}
					
					if(data.eeAttendeeOverage==1){
						$('.eeAttendeeOverage').prop("checked",true);
					}
					$("#eeLicenseVolume").val(data.eeLicenseVolume);
					$("#eeAttendeeCapacity").val(data.eeAttendeeCapacity);
					
				}
				
				//$("#sadmin").prop("disabled", true);	
				languages=data.additionalLanguage.split(";");
				$.each(languages, function (key, value) {
					if(value=="SIMPLIFIED_CHINESE"){
						$('.SIMPLIFIED_CHINESE').prop("checked",true);
					}
					if(value=="TRADITIONAL_CHINESE"){
						$('.TRADITIONAL_CHINESE').prop("checked",true);
					}
					if(value=="ENGLISH"){
						$('.ENGLISH').prop("checked",true);
					}
					if(value=="JAPANESE"){
						$('.JAPANESE').prop("checked",true);
					}
					if(value=="KOREAN"){
						$('.KOREAN').prop("checked",true);
					}
					if(value=="FRENCH"){
						$('.FRENCH').prop("checked",true);
					}
					if(value=="GERMAN"){
						$('.GERMAN').prop("checked",true);
					}
					if(value=="ITALIAN"){
						$('.ITALIAN').prop("checked",true);
					}
					if(value=="SPANISH"){
						$('.SPANISH').prop("checked",true);
					}
					if(value=="SPANISH_CASTILLA"){
						$('.SPANISH_CASTILLA').prop("checked",true);
					}
					if(value=="SWEDISH"){
						$('.SWEDISH').prop("checked",true);
					}
					if(value=="HOLLAND"){
						$('.HOLLAND').prop("checked",true);
					}
					if(value=="PORTUGUESE"){
						$('.PORTUGUESE').prop("checked",true);
					}
					if(value=="RUSSIAN"){
						$('.RUSSIAN').prop("checked",true);
					}
					if(value=="TURKEY"){
						$('.TURKEY').prop("checked",true);
					}
					if(value=="DANISH"){
						$('.DANISH').prop("checked",true);
					}
				
						
				});
				
				
			
			},
		});
	},

	getallservices: function (){


		console.log("here99------------");
		$.ajaxInvoke({
			url 	: 	G_CTX_ROOT+"/v1/timezone/query" ,
			data 	:	"",
			type : "get",
			success	:	function(returnData){
				//returnData= JSON.parse(returnData);

			
				
				$.each(returnData, function(key,value) { 
					Systemuser.options += '<option value="' + value.value + '">' + value.displayName + '</option>';

					
				});

				$('#s_sserviceid').html(Systemuser.options);
				
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
