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
		setting.sAjaxSource = G_CTX_ROOT + "/v1/orgs/query";
		setting.callbackStack.stack.push(function () {
			$(".rolelistName").html("角色列表");
			self.bindButtonEvent();
		});
		
		self.bindLaterEvent();
		setting.aoColumns = [
		    {
				"sTitle" : "企业名称",
				"mData" : "orgname",
				"bSortable" : true,
				"sClass" : "center",
				"render" : function (data) {
					return "<input name='accountnameforedit' type='hidden' value='" + data + "'/>" +htmlencode(data);
				}
			}, {
				"sTitle" : "企业域名",
				"mData" : "orgdomain",
				"bSortable" : false,
				"sClass" : "center",
				"render" : function (data) {
					return data==null?"": htmlencode(data);
				}
			}, 
			
			
			{
				"sTitle" : "状态",
				"mData" : "status",
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
					
					editDom = "<span class=\"btn btn-success btn-xs btn-detail\">修改</span> ";
				
					if (obj.f0 != userId) {
						enableDom = obj.status == "1" ? "<span class='btn btn-danger btn-xs btn-active'>禁用  </span> " : "<span class='btn btn-warning btn-xs btn-active'>启用 </span> ";
					} else {
						enableDom = "";
					}
					return editDom + enableDom + deleteDom + "<input type='hidden' name='sysId' value='" + obj.pid + "'/>";
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

		$(".btn-active").unbind("click").click(function () {
			var target = $(this).parents("TR");
			var tds = $(target).children('td');
			var userid = $(this).parents("TR").find("input[type=hidden][name=sysId]").val();
			var statusObj = $(tds[2]);
			var uName = $(tds[0]).find("input[name=accountnameforedit]").val();
			var activeMsg = "确定禁用企业【" + uName + "】？";
			var isActive = (statusObj.html().indexOf("启用") > 0);
			var status = 0;
			if (!isActive) {
				activeMsg = "确定启用企业【" + uName + "】？";
				status = 1;
			}
			var org = {};
			org.status = status;
			org.orgName = $(tds[0]).text();
			org.domain = $(tds[1]).text();
			var postData = JSON.stringify(org);
			mConfirm(activeMsg, function () {
				$.ajaxInvoke({
					url : G_CTX_ROOT + "/v1/orgs/" + userid,
					data : postData,
					type : "put",
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
						msgBox("", "企业状态已改变");
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
			$("#sadmin").prop("disabled", false);
			//Systemuser.getalldetails("", true, userid);
			$("#sdomain").nextAll().filter("span").html("");
			$("#scrmcustomerid").nextAll().filter("span").html("");
			Systemuser.getallservicesbytype("WEBEX");
			Systemuser.getUserDetail(userid);
			$("#saccount").val(parentTR.find("input[name=accountnameforedit]").val()).prop("disabled", true);
			$("#edit_username").val($.trim(parentTR.find("input[name=accountnameforedit]").val())).prop("disabled", false);
			$("#sfpassword").val("111111");
			$("#spassword").val("111111");
			$(".passwordrow").hide();
			$(".area").prop("checked", false).prop("disabled", false);
			$("#myModalLabel").html("企业修改");
			$("#selectedzone").html("");
			$("#selectedzoneim").html("");
			$(".UserService").prop("checked", false);
			$(".UserIM").prop("checked", false);
			$(".btn-save").show();
			$("#myModal").modal("show");
		});	
	
		
		$(".btn-add").unbind("click").click(function () {
			$(".btn-user-mc").show();
			$("#srole").nextAll().filter("span").html("");
			updateObj = null;
			validator.clearForm();
			$("#edit_userid").val("");
			$("#myModalLabel").html("企业添加");
			$('#sname').val("").prop("disabled", false);
			$('#sdomain').val("").prop("disabled", false);
			$("#sadmin").val("").prop("disabled", false);
			$('#sfullname').val("");
			$('#sfinfullname').val("");
			$('#scrmcustomerid').val("");			
			$('#sfinadmin').val("");
			$('#webexURL').val("");
			$('#license').val("");
			$('#meetingType').val("EE");
			$('#sphone').val("").prop("disabled", false);
			$(".area").prop("checked", false).prop("disabled", false);
			Systemuser.getallservicesbytype("WEBEX");
			$("#selectedzone").html("");
			$("#selectedzoneim").html("");
			$("#sdomain").nextAll().filter("span").html("");
			$("#scrmcustomerid").nextAll().filter("span").html("");
			$(".UserService").prop("checked", false);
			$(".UserIM").prop("checked", false);
			$(".passwordrow").show();
			$(".btn-save").show();
			$(".btn-create-zone").show();
			$(".btn-save").attr("disabled", false);
			$("#myModal").modal("show");
		});
		$(".btn-search").unbind("click").click(function () {
			Systemuser.dataTable.fnDraw();
		});
		$(".btn-create-zone").unbind("click").click(function () {
			Systemuser.createregionselect();
		});
		
		$(".btn-create-zone-im").unbind("click").click(function () {
			var hasim=false
			$(".zonerowim").each(function () {
				
				alert("IM只能选一个");
				hasim=true;
				
			});
			if(!hasim){
				Systemuser.createimselect();
			}
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
	
		
		$(".btn-save").unbind("click").click(function () {
			console.log("9999---");
			var modulesArray = new Array();
			var deletesArray = new Array();
			if (!validator.validate()) {
				return false;
			}
			
			if($.trim($('#sadmin').val())!=""&&$.trim($('#sfullname').val())==""){
				$("#sfullname").nextAll().filter("span").html("<font color=red >真实姓名不能为空！</font>");
				return false;
				
			}
			if($.trim($('#sfinadmin').val())!=""&&$.trim($('#sfinfullname').val())==""){
				$("#sfinfullname").nextAll().filter("span").html("<font color=red >真实姓名不能为空！</font>");
				return false;
				
			}
			$(".zonerowim").each(function () {				
				 modulesArray.push($(this).attr("id"));
			});
			
			$(".zonerow").each(function () {
				/*OrgUserService4Create={};
				OrgUserService4Create.serviceType="WEBEX";
				OrgUserService4Create.serviceLicense=$(this).find(".license").val();
				OrgUserService4Create.meetingType=Systemuser.getMeetingType($(this).find(".meetingType").val());
				OrgUserService4Create.serviceUrl=$(this).find(".webexURL").val();*/
				
				
				modulesArray.push($(this).attr("id"));
				
			});
		
			/*
			$(".select-role").find("option").each(function () {
				modulesArray.push($(this).val());
			});
			if (modulesArray.length == 0) {
				$("#srole").nextAll().filter("span").html("<font color=red >所选模块不能为空！</font>");
				return false;
			}*/
			$(".btn-save").attr("disabled", true);
			var org = {};
			org.orgName = $.trim($('#sname').val());
			org.domain = $.trim($('#sdomain').val());
			org.contactEmail = $.trim($('#sadmin').val());
			org.contactName = $.trim($('#sfullname').val());
			org.finAdminEmail = $.trim($('#sfinadmin').val());
			org.finAdminName = $.trim($('#sfinfullname').val());
			org.crmCustomerId = $.trim($('#scrmcustomerid').val());
		
			if($(".UserService").prop("checked")){
				org.userService=1;
			}else{
				org.userService=0;
			}
			org.service4Create=modulesArray;
		
			
			
			var postURL = G_CTX_ROOT + "/v1/orgs/";
			var update = false;
			var calltype = "post";
			if ($("#edit_userid").val() != "") {
				postURL = G_CTX_ROOT + "/v1/orgs/" + $("#edit_userid").val();
				update = true;
				var calltype = "put";
				$.each(Systemuser.userservicelist, function (key, value) {
					var deleted=true;
					
					$(".zonerow").each(function () {
						if(value==$(this).attr("id")){
							deleted=false;
						  }
						
					});
					
					$(".zonerowim").each(function () {
						if(value==$(this).attr("id")){
							deleted=false;
						  }
						
					});
					
					if(deleted){
						deletesArray.push(value);
					}
				
						
				});
				org.orgService4Delete = deletesArray;
				
				
				
			} else {}
			var postData = JSON.stringify(org);
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
					$('#saccount').val("");
					$('#spassword').val("");
					$('#sfpassword').val("");
					$('#sphone').val("");
					msgBox('success', "企业修改成功");
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
					$('#saccount').val("");
					$('#spassword').val("");
					$('#sfpassword').val("");
					$('#sphone').val("");
					msgBox('success', "企业增加成功");
					Systemuser.dataTable.fnDraw();
				},
			});
		});
	},
    
	
	getUserDetail : function (userid) {
		$.ajaxInvoke({
			url : G_CTX_ROOT + "/v1/orgs/" + userid,
			data : "",
			type : "get",
			success : function (data) {
				$("#sname").val(data.orgName);
				$("#sdomain").val(data.domain);
				$("#sadmin").val(data.contactEmail);
				$("#sfullname").val(data.contactName);
				$("#sfinadmin").val(data.finAdminEmail);
				$("#sfinfullname").val(data.finAdminName);
				$("#scrmcustomerid").val(data.crmCustomerId);
				$("#sname").prop("disabled", true);
				//$("#sadmin").prop("disabled", true);
				
				Systemuser.userservicelist=new Array();
				$.each(data.listServiceMap, function (key, value) {
					if(value.autoGrant==1){
						checked="checked";
					}else{
						checked="";
					}
					Systemuser.orgservicelist=data.listServiceMap;
					Systemuser.userservicelist.push(value.pid);
					if(value.serviceType=="WEBEX"){
						html = '<div class="row zonerow"  id="'+value.pid+'">' + 
						'<section class="col col-1">' + 
						'<label class="label text-right">' + 'URL' + '</label>' + '</section>' + '<section class="col col-3">' +
						'<label class="input"><input type="text" class="webexURL" id="" disabled value="'+value.serviceUrl+'" >	' +  
						'</label>' + '</section>' + 
						'<section class="col col-1">' + '<label class="label text-right">' +
						'License:' + '</label>' + '</section>' + '<section class="col col-2">' + '<label class="input">' + 
						'<input type="text" class="license" id="" disabled value="'+value.serviceLicense+'" >	'+
						'</label>' + 
						'</section>' + '<section class="col col-1">' + '<label class="label text-right">' + '服务类型' + '</label>' + '</section>' + 
						'<section class="col col-1">' + 
						'<label class="input">' + 
						'<input type="text" class="meetingType" id="" disabled value="'+value.meetingType+'" >'	+
						'</label>' + '</section>' + 
						'<section class="col col-1">' + 
						'<label class="label text-right">' + '自动配置' + '</label>' + '</section>' + 
						'<section class="col col-1">' + 
						'<label class="">' + 
						'<input type="checkbox" class="autoGrant" id="" disabled '+checked+'>'	+
						'</label>' + '</section>' + 
						'<section class="col col-1">' + '<span class="btn btn-danger btn-sm btn-deletezone" serviceId="'+value.pid+'">删除 </span>' + '</section>' + '</div>';
						$("#selectedzone").append(html);
					}else if(value.serviceType=="IM"){
						
						html = '<div class="row zonerowim" id="'+value.pid+'">' + 
						'<section class="col col-1">' + 
						'<label class="label text-right">' + 'License' + '</label>' + '</section>' + '<section class="col col-3">' +
						'<label class="input"><input type="text" class="license" id="" disabled value="'+value.serviceLicense+'" >	' +  
						'</label>' + '</section>' + 
						
						'<section class="col col-1">' + '<span class="btn btn-danger btn-sm btn-deletezone-im" serviceId="'+value.pid+'" serviceName="'+value.serviceName+'">删除 </span>' + '</section>' + '</div>';

						$("#selectedzoneim").append(html);
					}
					
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

	
	createregionselect : function (provinceValue, cityValue, areaValue) {
		
		console.log("---",Systemuser.allorgservicelist);
		var selectedid=$("#s_sserviceid").val();
		var checked="";
		var html="";
		$.each(Systemuser.allorgservicelist, function(key,value) { 
			if(value==null){return;}
			if(value.pid==selectedid){
			
				if(value.autoGrant==1){
					checked="checked";
				}else{
					checked="";
				}
				html = '<div class="row zonerow" id="'+value.pid+'">' + 
				'<section class="col col-1">' + 
				'<label class="label text-right">' + 'URL' + '</label>' + '</section>' + '<section class="col col-3">' +
				'<label class="input"><input type="text" class="webexURL" id="" disabled value="'+value.serviceUrl+'" >	' +  
				'</label>' + '</section>' + 
				'<section class="col col-1">' + '<label class="label text-right">' +
				'License:' + '</label>' + '</section>' + '<section class="col col-2">' + '<label class="input">' + 
				'<input type="text" class="license" id="" disabled value="'+value.serviceLicense+'" >	'+
				'</label>' + 
				'</section>' + 
				'<section class="col col-1">' + '<label class="label text-right">' + '服务类型' + '</label>' + '</section>' + 
				'<section class="col col-1">' + 
				'<label class="input">' + 
				'<input type="text" class="meetingType" id="" disabled value="'+value.meetingType+'" >'	+
				'</label>' + '</section>' + 
				'<section class="col col-1">' + '<label class="label text-right">' + '自动配置' + '</label>' + '</section>' + 
				'<section class="col col-1">' + 
				'<label class="">' + 
				'<input type="checkbox" class="autoGrant" id="" disabled '+checked+'>'	+
				'</label>' + '</section>' + 
				'<section class="col col-1">' + '<span class="btn btn-danger btn-sm btn-deletezone" serviceId="'+value.pid+'">删除 </span>' + '</section>' + '</div>';
				$("#s_sserviceid").find("option[value='" + value.pid + "']").remove();	
				Systemuser.allorgservicelist.splice(key,1);
			}
			
		});
		
		$("#selectedzone").append(html);
		
		Systemuser.row_count++;
	},
	
createimselect : function () {
		
		console.log("---",Systemuser.allorgservicelist);
		var selectedid=$("#s_simid").val();
		
		var html="";
		$.each(Systemuser.allorgservicelist, function(key,value) { 
			if(value==null){return;}
			if(value.pid==selectedid){
			
			
				html = '<div class="row zonerowim" id="'+value.pid+'">' + 
				'<section class="col col-1">' + 
				'<label class="label text-right">' + 'License' + '</label>' + '</section>' + '<section class="col col-3">' +
				'<label class="input"><input type="text" class="license" id="" disabled value="'+value.serviceLicense+'" >	' +  
				'</label>' + '</section>' + 
				
				'<section class="col col-1">' + '<span class="btn btn-danger btn-sm btn-deletezone-im" serviceId="'+value.pid+'" serviceName="'+value.serviceName+'">删除 </span>' + '</section>' + '</div>';
				$("#s_simid").find("option[value='" + value.pid + "']").remove();	
				Systemuser.allorgservicelist.splice(key,1);
			}
			
		});
		
		$("#selectedzoneim").append(html);
		
		Systemuser.row_count++;
	},
	getMeetingType : function (meetingType) {
		
		if(meetingType=="MC"){			
			return 1;
		}else if(meetingType=="EC"){
			return 2;
		}else if(meetingType=="TC"){
			return 3;
		}else if(meetingType=="SC"){
			return 4;
		}else if(meetingType=="EE"){
			return 5;
		}else{
			return 0;
		}
	},
	
	getallservicesbytype: function (serviceType){		
		
		
		
		 $.ajaxInvoke({
				url 	: 	G_CTX_ROOT+"/v1/orgs/orgservicebytype/"+serviceType ,
				data 	:	"",
				type : "get",
				success	:	function(returnData){
								//returnData= JSON.parse(returnData);
								webexoptions="";
								imoptions="";
								Systemuser.allorgservicelist=new Array();
								$.each(returnData, function(key,value) { 
									if(value.serviceType=="WEBEX"){
										Systemuser.allorgservicelist.push(value);
										webexoptions += '<option value="' + value.pid + '">' + value.serviceUrl + ' ('+value.meetingType+')'+' ('+value.serviceLicense+')'+'</option>';
									}else{
										imoptions += '<option value="' + value.pid + '">' +value.serviceName+' ('+value.serviceLicense+')'+'</option>';
										Systemuser.allorgservicelist.push(value);
									}
									
								});
								
								$('#s_sserviceid').html(webexoptions);
								$('#s_simid').html(imoptions);
								
							},
				
			});
		
		
	
	
	},
	bindLaterEvent:function(){
		console.log("bindinglater");
		
	$("#selectedzoneim").on("click", ".btn-deletezone-im", function () {
			
			
			var serviceid=$(this).attr("serviceId");
			var serviceName=$(this).attr("serviceName");
			var servicelicense=$(this).parent().parent().find(".license").val();
		
			/*if($(this).attr("serviceId")!=null){
			   console.log("---")
			   $.each(Systemuser.orgservicelist, function (key, value) {
				    if(serviceid==$(this).pid){
					console.log("====",$(this));
					Systemuser.deletedorgservicelist.push(serviceid);
				    }
					
				});
			console.log("---here",Systemuser.deletedorgservicelist);
			}*/
			imoptions = '<option value="' + serviceid + '">' +serviceName+' ('+servicelicense+')'+'</option>';
			$('#s_simid').append(imoptions);
			$(this).parent().parent().find("webexURL").val();
			
			service={};
			service.pid=serviceid;
			service.serviceType="IM";
			service.serviceName=serviceName;
			service.serviceLicense=servicelicense;
			
			Systemuser.allorgservicelist.push(service);
			$(this).parent().parent().remove();
		});
	
	$("#selectedzone").delegate(".btn-deletezone", "click", function () {
		
		console.log("---",$(this).attr("serviceId"));
		console.log("dd",Systemuser.allorgservicelist);
		var serviceid=$(this).attr("serviceId");
		var serviceurl=$(this).parent().parent().find(".webexURL").val();
		var servicelicense=$(this).parent().parent().find(".license").val();
		var meetingType=$(this).parent().parent().find(".meetingType").val();
		var autoGrant=$(this).parent().parent().find(".autoGrant").prop("checked")==true?1:0;
		/*if($(this).attr("serviceId")!=null){
		   console.log("---")
		   $.each(Systemuser.orgservicelist, function (key, value) {
			    if(serviceid==$(this).pid){
				console.log("====",$(this));
				Systemuser.deletedorgservicelist.push(serviceid);
			    }
				
			});
		console.log("---here",Systemuser.deletedorgservicelist);
		}*/
		webexoptions = '<option value="' + serviceid + '">' + serviceurl + ' ('+meetingType+')'+' ('+servicelicense+')'+'</option>';
		$('#s_sserviceid').append(webexoptions);
		$(this).parent().parent().find("webexURL").val();
		
		service={};
		service.pid=serviceid;
		service.meetingType=meetingType;
		service.serviceLicense=servicelicense;
		service.serviceType="WEBEX";
		service.serviceUrl=serviceurl;
		service.autoGrant=autoGrant;
		Systemuser.allorgservicelist.push(service);
		$(this).parent().parent().remove();
		});
	},
	checkDomain : function () {
		console.log("here----------11");
		var domain = $.trim($("#sdomain").val());
		if (domain == "") {
			$("#sdomain").nextAll().filter("span").html("");
			$(".btn-save").attr("disabled", false);
		} else {
			
			var domains = domain.split(";");
			
			$.each(domains, function(i, item){
				
				var pattern=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
				var val = "asd@"+item ;
				var bRtn =pattern.test(val); 
				console.log("here",i,item,bRtn,val);
				if(!bRtn){
					$("#sdomain").nextAll().filter("span").html("<font color=red >企业域名格式不正确！</font>");
					$(".btn-save").attr("disabled", false);
					return;
				}
			});
			$.ajaxInvoke({
				url : G_CTX_ROOT + "/v1/orgs/orgusers/checkUserAccount/" + userAccount,
				type : "post",
				success : function (data) {
					console.log("here----------",data);
					if (data.state == "success") {
						$("#saccount").nextAll().filter("span").html("<font color=red >该帐号已存在，请重输入！</font>");
						$(".btn-save").attr("disabled", true);
						return false;
					} else {
						$("#saccount").nextAll().filter("span").html("");
						$(".btn-save").attr("disabled", false);
					}
				}
			});
		}
	}

}
var rule = {
	
	sadmin : {
		name : "管理员",
		method : {			
			email : true,
			lt : 100,
		},
		defaultValue : ""
	},
	sfullname : {
		name : "真实姓名",
		method : {				
			lt : 20,
		},
		defaultValue : ""
	},
	sfinadmin : {
		name : "财务管理员",
		method : {			
			email : true,
			lt : 100,
		},
		defaultValue : ""
	},
	sfinfullname : {
		name : "真实姓名",
		method : {				
			lt : 20,
		},
		defaultValue : ""
	},
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
		name : "企业名称",
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
