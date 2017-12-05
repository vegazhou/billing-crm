var appVersion = {
		apiContextPath:'/adminapi/v1',
		lg:function(msg){},
		type:'appVersion',
		dataTable:null,
		init:function(){
			var _self = this;
			_self.initTable(_self);
			
			$('.btn-search').unbind("click").click(function(){
				_self.dataTable.fnDraw();
			});
			
			$(".btn-add").unbind("click").click(function(){
				validator.clearForm() ;
				$("input:radio[name='type']").prop("disabled",false);
				$(".btn-save").show();
				$(".btn-updateSave").hide();
				$(".btn-save").attr("disabled",false);
				$("#myModalLabel").html("添加APP版本");
				$("#myModal").modal("show") ;
			});
			
			$(".btn-save").unbind("click").click(function(){
				$(".btn-save").attr("disabled",true);
				if(!validator.validate()){
					$(".btn-save").attr("disabled",false);
					return false;
				}
				var data = {};
				data.type = parseInt($("input:radio[name='type']:checked").val());
				data.versionNum = $.trim($("#versionNum").val());
				data.description = $.trim($("#desc").val());
				data.url = $.trim($("#url").val());
				data.htmlVersionNum = $.trim($("#htmlVersionNum").val());
				data.htmlDescription = $.trim($("#htmlDesc").val());
				data.htmlUrl = $.trim($("#htmlUrl").val());
				
				var postData=JSON.stringify(data);	
				$.ajaxInvoke({
					url : _self.apiContextPath + "/appVersion" ,
					type : "post",
					data : postData,
					dataType: "json",
					success : function (data){
						msgBox('success', "添加APP版本成功");
						$("#myModal").modal("hide");
						_self.dataTable.fnDraw();
					},
					error	:	function (data){
						if(data.status == "201"){
							msgBox('success', "添加APP版本成功");
							$("#myModal").modal("hide");
							_self.dataTable.fnDraw();
						}else{
							msgBox('fail', "添加APP版本失败");
						}
					},
				});
			});	
			
			$(".btn-updateSave").unbind("click").click(function(){
				$(".btn-updateSave").attr("disabled",true);
				if(!validator.validate()){
					$(".btn-updateSave").attr("disabled",false);
					return false;
				}
				var data = {};
				data.versionNum = $.trim($("#versionNum").val());
				data.description = $.trim($("#desc").val());
				data.url = $.trim($("#url").val());
				data.htmlVersionNum = $.trim($("#htmlVersionNum").val());
				data.htmlDescription = $.trim($("#htmlDesc").val());
				data.htmlUrl = $.trim($("#htmlUrl").val());
				
				var postData=JSON.stringify(data);	
				$.ajaxInvoke({
					url : _self.apiContextPath + "/appVersion/"+$("#pid").val(),
					type : "put",
					data : postData,
					dataType: "json",
					success : function (data){
						msgBox('success', "修改APP版本成功");
						$("#myModal").modal("hide");
						_self.dataTable.fnDraw();
					},
					error	:	function (data){
						msgBox('fail', "修改APP版本失败");
						$("#myModal").modal("hide");
					},
				});
			});
			
			var rule = {
				versionNum :{name:"版本号",method:{required:true,lt:50,},},
				desc :{name:"版本描述",method:{required:true,lt:200,},},
				url :{name:"下载地址",method:{required:true,lt:200,},},
			};
			validator.init( rule ) ;
		},
		changeEmpty:function(val){
			var flag = val!=null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g,"") != "" ;
			return flag;
		},
		jsonPostData:function(self, start, limit, orderby, direction){
			var _self = self;
			var flag = _self.changeEmpty($("#s_type").val());
			var type = flag==false?0:$("#s_type").val();
			var versionNum = $("#s_versionNum").val();
			var htmlVersionNum = $("#s_htmlVersionNum").val();
			return '{"type":' + type
				+ ',"versionNum":"' + versionNum
				+ '","htmlVersionNum":"' + htmlVersionNum
				+ '","orderby":"' + (orderby?orderby:"createTime")
				+ '","direction":"' + (direction?direction:"asc")
				+ '","offset":' + (start?start:0)
				+ ',"limit":'+ (limit?limit:10000) + '}';
		},
		initTable:function(self){
			_self = self;
			if(_self.dataTable){
				_self.dataTable.fnDraw();
				return;
			}
			var setting = {
					"oLanguage":dataTableLan,		
					"aLengthMenu":[[10, 25, 50], [10, 25, 50]],
					"bjQueryUI":true,
					"sPaginationType":"full_numbers",
					"bProcessing":true,
					"bPaginate":true,
					"bServerSide":true,
					"bFilter":false,
					"bInfo":true,
					"bStateSave":false,
					"bAutoWidth":true,
					"aaSorting":[[0, "desc"]],
					"fnServerData":function(sSource, aoData, fnCallback){
						_self.lg("aoData:" + JSON.stringify(aoData));
						var start = 0;
						var length = 10;
						var columns = null;
						var sEcho = 1;
						var order = [];
						$.each(aoData, function(i,item){
							var name = item['name'];
							var value = item['value'];
							if(name == "start"){
								start = value;
							}else if(name == 'length'){
								length = value;
							}else if(name == 'draw'){
								sEcho = value;
							}else if(name == 'order'){
								order = value;
							}else if(name == 'columns'){
								columns = value;
							}
						});
						
						var orderby = columns[order[0].column].data;
						var direction = order[0].dir;
						var postData = _self.jsonPostData(_self, start, length, orderby, direction);
						if(!postData){
							return;
						}
							$.ajaxInvoke( {
								type: "POST",
								url: _self.apiContextPath + "/appVersion/searchAppVersions",
								dataType: "json",
								data: postData, 
								success: function(content, status) {
									_self.lg(content);
									content['sEcho']=sEcho;
									content['iDisplayStart']=content.paging.offset;
									content['iDisplayLength']=content.paging.limit;
									content['iTotalRecords']=content.paging.count;
									content['iTotalDisplayRecords']=content.paging.count;
									content['sortField']= orderby; //"createTime";
									content['sortType']= direction;
									_self.lg(content);
									fnCallback(content);
								},
								error:function(data){
					            	_self.lg(data);
					            }
							});
						},  
					"fnDrawCallback":function(){
						
						$(".btn-active").unbind("click").click(function(){
				            var appId = $(this).parents("TR").find("input[type=hidden][name=appId]").val();
				            var appName = $(this).parents("TR").children('td').eq(0).html();
				            var status = $(this).parents("TR").find("input[type=hidden][name=appStatus]").val();
				            var msg = status == "0" ? "启用" : "禁用";
				            var data = {};
				            data.status = status == "0" ? 1 : 0;
				            var postData=JSON.stringify(data);	
				            mConfirm("确定"+msg+"【"+appName+"】？", function(){
								$.ajaxInvoke({
									url : _self.apiContextPath + "/appVersion/"+appId ,
									type : "put",
									data : postData,
									dataType: "json",
									success : function (data){
										msgBox("success", msg + "APP版本成功");
										_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
									},
									error	:	function (data){
										msgBox('fail', msg + "APP版本失败");
									},
								});
				            });
						});
										
						$(".btn-modify").unbind("click").click( function(){
							validator.clearForm() ;
							$(".btn-updateSave").attr("disabled",false);
							var appId = $(this).parents("TR").find("input[type=hidden][name=appId]").val();
							$.ajaxInvoke({
								url : _self.apiContextPath + "/appVersion/"+appId ,
								type :  "get",
								dataType: "json",
								success	:	function(data){
									$("#pid").val(data.pid);
									$("#versionNum").val(data.versionNum);
									$("#desc").val(data.description);
									$("#url").val(data.url);
									$("#htmlVersionNum").val(data.htmlVersionNum);
									$("#htmlDesc").val(data.htmlDescription);
									$("#htmlUrl").val(data.htmlUrl);
									if (data.type == "1") {
										$("#type1").prop("checked", true);
									} else {
										$("#type2").prop("checked", true);
									}
									$("input:radio[name='type']").prop("disabled",true);
									$(".btn-save").hide();
									$(".btn-updateSave").show();
									$("#myModal").modal("show");
								},
							});		
						});
					},
					"fnInitComplete":function(){
					},
					"sAjaxDataProp":"datas",
					"aoColumns": [
									{ "sTitle": "APP版本号","mData" :"versionNum",},
									{ "sTitle": "HTML版本号","mData" :"htmlVersionNum",},
									{ "sTitle": "版本类型","mData" :"type",
										"render": function(data) {
											return data==1?'Android':'IOS';
										}
									},
									{ "sTitle": "版本状态","mData" :"status",
										"render": function(data) {
											return data==1?'启用':'禁用';
										}
									},
									{ "sTitle": "创建时间","mData" :"createTime",},
									{ "sTitle": "操作", "mData" :"", "bSortable" : false , "sWidth":"180px",
										"render": function(data,dis,obj) {
											var editDom = "",activeDom="";
											editDom = "<span class='btn btn-success btn-xs btn-modify'> 修改 </span> " ;
											if(obj.status==1){
												activeDom = "<span class='btn btn-danger btn-xs btn-active'> 禁用 </span> ";
											} else { 
												activeDom = "<span class='btn btn-warning btn-xs btn-active'> 启用 </span> " ;
											}
											return "<input type='hidden' name='appId' value='"+obj.pid+"'/>" + 
													"<input type='hidden' name='appStatus' value='"+obj.status+"'/>" +
													editDom + activeDom ;
									    }
									}
		                      ],
				};
			commonSetting4DataTable(setting);
			_self.dataTable = $('#datatable').dataTable(setting);
		},
	}

$(document).ready(appVersion.init());