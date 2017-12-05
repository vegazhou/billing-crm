var Systemuser = {
	totalSalesman : [],
	dataTable : null,
	options : "",
	row_count : 0,
	orgservicelist:null,
	allorgservicelist:null,
	deletedorgservicelist:null,
	userservicelist:[],
	init : function () {
		$.fn.modal.Constructor.prototype.enforceFocus = function () {};
		Systemuser.addIndustry();
		Systemuser.totalSalesman = [];
		var self = this;
		pageSetUp();
		setting.sAjaxSource = G_CTX_ROOT + "/v1/customer/query";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
		    {
				"sTitle" : "客户名称",
				"mData" : "displayName",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
				}
			},
			
			
			{
				"sTitle" : "录入时间",
				"mData" : "createDate",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},
			{
				"sTitle" : "客户编码",
				"mData" : "code",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return htmlencode(data);
				}
			},

			{
				"sTitle" : "SAP同步状态",
				"mData" : "sapSynced",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					if (data) {
						return "<font color='green'>已同步</font>"
					} else {
						return "<font color='red'>未同步</font>"
					}
				}
			},

			{
				"sTitle" : "操作",
				"mData" : "f6",
				"bSortable" : false,
				"sClass" : "center",
				"sWidth" : "30%",
				"render" : function (data, dis, obj) {
					editDom = "";
					synDom = "<span class='btn btn-warning btn-xs btn-sap'> 同步至SAP </span>";
					deleteDom = "";
					
					editDom = "<span class=\"btn btn-success btn-xs btn-addcontract\">增加合同</span> <span class=\"btn btn-success btn-xs btn-detail\">修改</span> ";

					deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> ";

					return editDom + deleteDom + synDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
				}
			}, ];
		setting.aaSorting = [[1, "desc"]];
		setting.bStateSave = true;
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
			var userid = parentTR.find("input[type=hidden][name=sysId]").val()
			$("#edit_userid").val(userid);
		
			//$("#sname").val(parentTR.find("input[name=accountnameforedit]").val()).prop("disabled", true);
			//$("#edit_username").val($.trim(parentTR.find("input[name=accountnameforedit]").val())).prop("disabled", false);
			Systemuser.getUserDetail(userid);
			$("#myModalLabel").html("客户修改");
			
			$(".btn-save").show();
			$("#myModal").modal("show");
		});	
	
		
		$(".btn-add").unbind("click").click(function () {
			
			$("#sname").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();			
			$("#myModalLabel").html("客户添加");
			$('#sname').val("").prop("disabled", false);
			$('#address').val("");
			$('#phone').val("");
			$('#isVat').val("");
			$('#vatNo').val("");
			$('#bank').val("");
			$('#scontactphone').val("");
			$('#scontactemail').val("");
			$('#scontactname').val("");
			$('#bankAccount').val("");
			$('#level').val("");
			$("#edit_userid").val("");
			console.log('hello2');
			$('#mainIndustry').val(0);
			$('#industry').empty();
			$('#industry').append("<option value='"+0+"'>未知行业</option>");
			$('#industry').val(0);
			$('#isRel').prop("checked", false);
			$(".btn-save").show();		
			$(".btn-save").attr("disabled", false);
			$("#myModal").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});
		
		
		$(".btn-addcontract").unbind("click").click(function () {
			
			$("#snamecontract").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();			
			//$("#myModalLabel").html("客户添加");
			$('#snamecontract').val("").prop("disabled", false);
			Systemuser.getallservices();
			Systemuser.getallagents();
			$("#edit_useridcontract").val($(this).parents("TR").find("input[type=hidden][name=sysId]").val());
			$(".btn-savecontract").show();		
			$(".btn-savecontract").attr("disabled", false);
			$("#myModalcontract").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});

		$(".btn-delete").unbind("click").click(function () {
		

			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();

		
				mConfirm("是否删除？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/customer/"+id,
						type : "delete",
						success : function (data) {
							msgBox('success', "客户删除成功");
							
								Systemuser.dataTable.fnDraw();
							
						},
						error: function (data) {
							if (data.status == "400" && JSON.parse(data.responseText).error.key == "customer_has_signed_contract") {
								msgBox('fail', "不能删除已签单客户！");
								return;
							}

							Systemuser.dataTable.fnDraw();

						},
						dataType : "json"
					});
				});
			
		});


		$(".btn-sap").unbind("click").click(function () {
			var id = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();

			mConfirm("将客户数据同步至SAP系统？", function () {
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/v1/customer/sapsync/"+id,
					type : "get",
					success : function (data) {
						msgBox('success', "SAP数据同步成功");
						Systemuser.dataTable.fnDraw();
					},
					error: function (data) {
						msgBox('fail', "SAP数据同步 失败");

					},
					dataType : "html"
				});
			});

		});
	
		
		
		$(".btn-savecontract").unbind("click").click(function () {
			
			var modulesArray = new Array();
			var deletesArray = new Array();
		
			
		
		
			/*
			$(".select-role").find("option").each(function () {
				modulesArray.push($(this).val());
			});
			if (modulesArray.length == 0) {
				$("#srole").nextAll().filter("span").html("<font color=red >所选模块不能为空！</font>");
				return false;
			}*/
			$(".btn-savecontract").attr("disabled", true);
			if($.trim($('#edit_useridcontract').val())!=""){
				
				var business = {};
				business.displayName = $.trim($('#snamecontract').val());
				business.customerId = $.trim($('#edit_useridcontract').val());
				business.salesmanId = $.trim($('#s_sserviceid').val());
				business.isRegistered = $('#isRegistered').prop("checked");
				business.comments = $.trim($('#comments').val());
			
				if($.trim($('#s_agentid').val())!=""){
					
					business.agentId=$.trim($('#s_agentid').val());
				}
				
				
				var postURL = G_CTX_ROOT + "/v1/contract/";
				var update = false;
				var calltype = "post";
			}else{
				var business = {};
				business.displayName = $.trim($('#snamecontract').val());		
				business.customerId = $.trim($('#edit_useridcontract').val());
				business.salesmanId = $.trim($('#s_sserviceid').val());
				business.isRegistered = $('#isRegistered').prop("checked");
				business.comments = $.trim($('#comments').val());
				
				var postURL = G_CTX_ROOT + "/v1/contract/"+$.trim($('#edit_useridcontract').val());
				var update = false;
				var calltype = "put";
				
			}
			var postData = JSON.stringify(business);
			$.ajaxInvoke({
				url : postURL,
				type : calltype,
				data : postData,
				success : function (data) {
				
					$(".btn-savecontract").attr("disabled", false);
					validator.clearForm();
					$("#edit_useridcontract").val("");
					$("#myModalcontract").modal("hide");
					$('#snamecontract').val("");
					window.location.href = '../order/order.jsp?id='+data.id+"&name="+data.displayName+"&status=起草中";;
					msgBox('success', "合同修改成功");
					//Systemuser.dataTable.fnDraw();
				},
				error : function (data) {
					
					
					$(".btn-savecontract").attr("disabled", false);
					validator.clearForm();
					$("#edit_userid").val("");
					$("#myModalcontract").modal("hide");
					$('#snamecontract').val("");
					
					msgBox('fail', "合同操作失败");
					Systemuser.dataTable.fnDraw();
				},
			});
		});

		$("#mainIndustry").unbind("change").change(function() {
			var mainId = $(this).val();
			$("#industry").empty();
			var strFilterIndustry = '';
			Systemuser.industryArray.forEach(function(value, index, array){
				console.log(value);
				if(mainId == value.main){
					strFilterIndustry += "<option value='"+value.id+"'>"+value.name+"</option>"
				}
			});
			$("#industry").append(strFilterIndustry);
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
				
				business.address = $.trim($('#address').val());
				business.phone = $.trim($('#phone').val());
				business.isVat = $.trim($('#isVat').val());
				business.vatNo = $.trim($('#vatNo').val());
				business.bank = $.trim($('#bank').val());
				business.contactName = $.trim($('#scontactname').val());
				business.contactEmail = $.trim($('#scontactemail').val());
				business.contactPhone = $.trim($('#scontactphone').val());
				business.bankAccount = $.trim($('#bankAccount').val());
				business.level = $.trim($('#level').val());
				business.industry = $("#industry").val();
				business.rel = $('#isRel').prop("checked");
				
				var postURL = G_CTX_ROOT + "/v1/customer/";
				var update = false;
				var calltype = "post";
			}else{
				var business = {};
				business.displayName = $.trim($('#sname').val());	
				
				business.address = $.trim($('#address').val());
				business.phone = $.trim($('#phone').val());
				business.isVat = $.trim($('#isVat').val());
				business.vatNo = $.trim($('#vatNo').val());
				business.bank = $.trim($('#bank').val());
				business.contactName = $.trim($('#scontactname').val());
				business.contactEmail = $.trim($('#scontactemail').val());
				business.contactPhone = $.trim($('#scontactphone').val());
				business.bankAccount = $.trim($('#bankAccount').val());
				business.level = $.trim($('#level').val());
				business.industry = $("#industry").val();
				business.rel = $('#isRel').prop("checked");
				
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
				
				$("#address").val(data.address);
				$("#phone").val(data.phone);
				$("#isVat").val(""+data.vat);
				$("#vatNo").val(data.vatNo);
				$("#bank").val(data.bank);
				$("#bankAccount").val(data.bankAccount);
				$("#level").val(data.level);
				$("#scontactname").val(data.contactName);
				$("#scontactemail").val(data.contactEmail);
				$("#scontactphone").val(data.contactPhone);

				var industry = data.industry;
				$('#mainIndustry').empty();
				$('#industry').empty();
				var mainId;
				Systemuser.industryArray.forEach(function(value, index, array){
					if (value.id == industry){
						console.log(value);
						mainId = value.main;
					}
				})

				var strMainIndustry = '';
				Systemuser.mainIndustryArray.forEach(function(item, index, array){
					strMainIndustry += "<option value='"+index+"'>"+item+"</option>"
				})
				$('#mainIndustry').append(strMainIndustry);
				$('#mainIndustry').val(mainId);

				var strFilterIndustry = '';
				Systemuser.industryArray.forEach(function(value, index, array){
					if(mainId == value.main){
						strFilterIndustry += "<option value='"+value.id+"'>"+value.name+"</option>"
					}
				});
				$("#industry").append(strFilterIndustry);

				$("#industry").val(industry);
							
				//$("#sadmin").prop("disabled", true);			
				
				 if(data.rel){		
						$("#isRel").prop("checked", true);			
					}else{
						$("#isRel").prop("checked", false);	
				}
				
			
			},
		});
	},

	

	getallservices: function (){



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

				Systemuser.totalSalesman = [];
				for (var i in returnData) {
					Systemuser.totalSalesman.push({"id":returnData[i].id,"text":returnData[i].name});
				}
				console.log(Systemuser.totalSalesman);
				$('#s_sserviceid').select2({
					"data": Systemuser.totalSalesman,
					"placeholder":'选择销售员'
				});
				//Systemuser.init();
				
			},

		});




	},

	getallagents: function (){



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

				$('#s_agentid').html(options);
				//Systemuser.init();
				
			},

		});




	},
	

	bindLaterEvent:function(){
		console.log("bindinglater");
		

	

	},

	addIndustry:function(){
		//<option value='"+value+"'>"+text+"</option>
		var strMainIndustry = '';
		Systemuser.mainIndustryArray.forEach(function(value, index, array){
			strMainIndustry += "<option value='"+index+"'>"+value+"</option>"
		})
		$('#mainIndustry').append(strMainIndustry);
		$('#industry').append("<option value='"+0+"'>未知行业</option>");
	},

	mainIndustryArray : [
		"未知行业","教育","培训","传统制造业","汽车","高科技制造业","金融","地产及酒店会展媒体","互联网","医疗医药","专业服务","零售贸易","军工、能源及政府","其他"
	],

	industryArray : [
	{id:0,main:0,name:"未知行业"},
	{id:1,main:1,name:"政府教育机关，包括各类学校"},
	{id:2,main:2,name:"各类培训机构"},
	{id:3,main:3,name:"航空航天（民用）"},
	{id:4,main:3,name:"机械制造及零部件行业"},
	{id:5,main:4,name:"汽车"},
	{id:6,main:4,name:"汽车零部件"},
	{id:7,main:4,name:"整车厂"},
	{id:8,main:4,name:"汽车设计"},
	{id:9,main:5,name:"高科技制造"},
	{id:10,main:5,name:"电子制造"},
	{id:11,main:5,name:"IT硬件"},
	{id:12,main:6,name:"银行"},
	{id:13,main:6,name:"证券"},
	{id:14,main:6,name:"保险"},
	{id:15,main:6,name:"基金"},
	{id:16,main:6,name:"资产管理"},
	{id:17,main:6,name:"跟银行、理财相关的公司、在线平台"},
	{id:18,main:7,name:"地产和房地产开发、经营、管理与服务"},
	{id:19,main:7,name:"地产酒店行业网上平台"},
	{id:20,main:7,name:"会务、展览、广电、媒体公司"},
	{id:21,main:7,name:"酒店"},
	{id:22,main:8,name:"电商"},
	{id:23,main:8,name:"IT软件在线支付"},
	{id:24,main:8,name:"网站"},
	{id:25,main:8,name:"互联网创新公司"},
	{id:26,main:9,name:"医药、医疗器械生产及销售相关"},
	{id:27,main:9,name:"医院、诊所及医疗医药政府机关"},
	{id:28,main:10,name:"律所"},
	{id:29,main:10,name:"会计师事务所"},
	{id:30,main:10,name:"咨询"},
	{id:31,main:10,name:"人力资源"},
	{id:32,main:10,name:"外包"},
	{id:33,main:10,name:"设计"},
	{id:34,main:10,name:"中介"},
	{id:35,main:11,name:"实体店面零售商"},
	{id:36,main:11,name:"批发"},
	{id:37,main:11,name:"分销/经销商"},
	{id:38,main:11,name:"进出口贸易（主营业务为生产制造的除外）"},
	{id:39,main:12,name:"所有军工企业"},
	{id:40,main:12,name:"能源类企业（如风水电、石油天然气等）"},
	{id:41,main:12,name:"政府事业单位"},
	{id:42,main:13,name:"以上行业外所有，包括食品、餐饮、物流、化工、服装等"}]
}

var rule = {
	
	
	sname : {
		name : "客户名称",
		method : {
			required : true,
			lt : 100,
		},
		onBlur : Systemuser.checkName
	},
	scontactname : {
		name : "联系人姓名",
		method : {
			required : true,
			lt : 100,
		},
		
	},
	scontactemail : {
		name : "联系人邮件",
		method : {
			required : true,
			lt : 500,
		},
		onBlur : Systemuser.checkName
	},
	
	scontactphone : {
		name : "联系人电话",
		method : {
			required : true,
			lt : 50,
		},
		
	}
	
};
validator.init(rule);
validator.validate();
$(document).ready(Systemuser.init());
