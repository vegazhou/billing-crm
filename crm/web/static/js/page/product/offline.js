var offline = {
		apiContextPath:'/adminapi/v1',
		lg:function(msg){

		},
		type:'offline',
		reportData:null,
		dataTable:null,
		editor:null,
		init:function(){
			var _self = this;
			_self.initTable(_self);
			_self.initEditor(_self);
			
			$(document).on("change","#file",function(){
				$("#filetxt").val($("#file").val());
				_self.ajaxFileUpload(_self,"file","logoimage");
			});
			
			$(document).on("change","#file1",function(){
				$("#filetxt1").val($("#file1").val());
				_self.ajaxFileUpload(_self,"file1","postimage");
			});
			
			$('.btn-changelogo').unbind("click").click(function(){
				$(".logofile").show();
				$(".logodiv").hide();
				$("#logoimage").val("");
				$("#filetxt").val("");
			});
			
			$('.btn-changepost').unbind("click").click(function(){
				$(".postfile").show();
				$(".postdiv").hide();
				$("#postimage").val("");
				$("#filetxt1").val("");
			});
			
			$('#startTime').unbind().bind('click', function () {
				WdatePicker({
					skin : 'twoer',
					dateFmt : 'yyyy-MM-dd HH:mm:ss',
					//maxDate : maxTime
				});
				return false;
			});
			
			$('.btn-search').unbind("click").click(function(){
				_self.dataTable.fnDraw();
			});
			
			$(".btn-add").unbind("click").click(function(){
				validator.clearForm() ;
				$("#filetxt").val("");
				$("#filetxt1").val("");
				$(".btn-save").show();
				$(".btn-updateSave").hide();
				$(".logofile").show();
				$(".logodiv").hide();
				$(".postfile").show();
				$(".postdiv").hide();
				$(".btn-save").attr("disabled",false);
				_self.editorSet(_self,"");
				$("#myModalLabel").html("添加线下课程");
				$("#myModal").modal("show") ;
			});
			
			$(".btn-save").unbind("click").click(function(){
				$(".btn-save").attr("disabled",true);
				if(!validator.validate()){
					$(".btn-save").attr("disabled",false);
					return false;
				}
				var data = {};
				data.title = $.trim($("#title").val());
				data.duration = $.trim(parseInt($("#duration").val()));
				data.startTime = $.trim($("#startTime").val());
				data.teacherIds = $.trim($("#teacherIds").val());
				data.description = _self.editor.getContent();
				data.location = $.trim($("#location").val());
				data.poster = $.trim($("#postimage").val());
				data.logoUrl = $.trim($("#logoimage").val());
				data.price = $.trim(parseInt($("#price").val()));
				data.maxbookCount = $.trim(parseInt($("#maxbookCount").val()));
				data.vendorName = $.trim($("#vendorName").val());
				
				var postData=JSON.stringify(data);	
				$.ajaxInvoke({
					url : _self.apiContextPath + "/offlineCourses" ,
					type : "post",
					data : postData,
					dataType: "json",
					success : function (data){
						msgBox('success', "添加线下课程成功");
						$("#myModal").modal("hide");
						_self.dataTable.fnDraw();
					},
					error	:	function (data){
						if(data.status == "201"){
							msgBox('success', "添加线下课程成功");
							$("#myModal").modal("hide");
							_self.dataTable.fnDraw();
						}else{
							msgBox('fail', "添加线下课程失败");
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
				data.title = $.trim($("#title").val());
				data.duration = $.trim(parseInt($("#duration").val()));
				data.startTime = $.trim($("#startTime").val());
				data.teacherIds = $.trim($("#teacherIds").val());
				data.description = _self.editor.getContent();
				data.location = $.trim($("#location").val());
				data.liveurl = $.trim($("#liveurl").val());
				data.playbackurl = $.trim($("#playbackurl").val());
				data.poster = $.trim($("#postimage").val());
				data.logoUrl = $.trim($("#logoimage").val());
				
				var postData=JSON.stringify(data);	
				$.ajaxInvoke({
					url : _self.apiContextPath + "/offlineCourses/"+$("#pid").val(),
					type : "put",
					data : postData,
					dataType: "json",
					success : function (data){
						msgBox('success', "修改线下课程成功");
						$("#myModal").modal("hide");
						_self.dataTable.fnDraw();
					},
					error	:	function (data){
						msgBox('fail', "修改线下课程失败");
						$("#myModal").modal("hide");
					},
				});
			});
			
			var rule = {
				title :{name:"课程标题",method:{required:true,lt:50,},},
				startTime :{name:"开始时间",method:{required:true,date:true,},},
				teacherIds :{name:"讲师名称",method:{required:true,lt:50,},},
				duration :{name:"课程时长",method:{required:true,isNum:true,},},
				location :{name:"授课地址",method:{required:false,lt:50,},},
				price :{name:"门票价格",method:{required:true,isNum:true,},},
				maxbookCount :{name:"人数上限",method:{required:true,isNum:true,},},
				logoimage :{name:"封面图片",method:{required:true,lt:200,},},
				postimage :{name:"海报图片",method:{required:true,lt:200,},},
				vendorName :{name:"机构名称",method:{required:true,lt:200,},},
			};
			validator.init( rule ) ;
		},
		initEditor:function(self){
			var _self = self;
			_self.editor = new baidu.editor.ui.Editor({
				/*initialFrameWidth:760,*/
				initialFrameHeight : 300,
				initialContent : "",
				textarea : 'htmlPart'
			});
			_self.editor.render("myEditor");
		},
		editorSet:function(self,content){
			var _self = self;
			_self.editor.ready(function(){
				_self.editor.setContent(content,false);
			});
		},
		ajaxFileUpload:function(self,fileId,imageId){
			var _self = self;
			var url = G_CTX_ROOT+"/v1/news/upload";
			url = adminServer + url.replace('/lecaiadminapi', adminContextPath) ;
			//console.log(url);
		    $.ajaxFileUpload({
		        url: url,
		        //headers:{'Source' : '101','Token':token},
		        data:{'Token':token},
		        secureuri:false, //是否启用安全提交,默认为false
		        fileElementId:fileId, //文件选择框的id属性
		        dataType:'json', 
		        success:function(data, status){
		        	$("#"+imageId).val(data.imgUrl);
		        },
		        error:function(data, status, e){ 
		        	$("#"+imageId).val(data.imgUrl);
		        }
		    });
		},
		changeEmpty:function(val){
			var flag = val!=null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g,"") != "" ;
			return flag;
		},
		jsonPostData:function(self, start, limit, orderby, direction){
			var _self = self;
			var flag = _self.changeEmpty($("#s_status").val());
			var status = flag==false?0:$("#s_status").val();
			var title = $("#s_title").val();
			return '{"status":"' + status
				+ '","title":"' + title
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
						//console.log(postData);
						if(!postData){
							return;
						}
	//						aoData.push( _self.jsonPostData(_self) );   
							$.ajaxInvoke( {
								type: "POST",
								url: _self.apiContextPath + "/offlineCourses/getAllList",
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
						$(".btn-publish").unbind("click").click(function(){
				            var lineId = $(this).parents("TR").find("input[type=hidden][name=lineId]").val();
				            var lineName = $(this).parents("TR").children('td').eq(0).html();
				            var data = {};
				            data.status = 2;
				            var postData=JSON.stringify(data);	
				            mConfirm("确定发布【"+lineName+"】？", function(){
								$.ajaxInvoke({
									url : _self.apiContextPath + "/offlineCourses/"+lineId ,
									type : "put",
									data : postData,
									dataType: "json",
									success : function (data){
										msgBox('success', "发布线下课程成功");
										_self.dataTable.fnDraw();
									},
									error	:	function (data){
										msgBox('fail', "发布线下课程失败");
									},
								});
				            });
						});
						
						$(".btn-cancel").unbind("click").click(function(){
				            var lineId = $(this).parents("TR").find("input[type=hidden][name=lineId]").val();
				            var lineName = $(this).parents("TR").children('td').eq(0).html();
				            var data = {};
				            data.status = 1;
				            var postData=JSON.stringify(data);	
				            mConfirm("确定撤销【"+lineName+"】？", function(){
								$.ajaxInvoke({
									url : _self.apiContextPath + "/offlineCourses/"+lineId ,
									type : "put",
									data : postData,
									dataType: "json",
									success : function (data){
										msgBox('success', "撤销线下课程成功");
										_self.dataTable.fnDraw();
									},
									error	:	function (data){
										msgBox('fail', "撤销线下课程失败");
									},
								});
				            });
						});
						
						$(".btn-delete").unbind("click").click(function(){
				            var lineId = $(this).parents("TR").find("input[type=hidden][name=lineId]").val();
				            var lineName = $(this).parents("TR").children('td').eq(0).html();
				            mConfirm("确定删除【"+lineName+"】？", function(){
								$.ajaxInvoke({
									url : _self.apiContextPath + "/offlineCourses/"+lineId ,
									type :  "delete",
									dataType: "json",
									success : function (data){
										msgBox('success', "删除线下课程成功");
										_self.dataTable.fnDraw();
									},
									error	:	function (data){
										msgBox('fail', "删除线下课程失败");
									},
								});
				            });
						});	
						
						$(".btn-modify").unbind("click").click( function(){
							validator.clearForm() ;
							$(".btn-updateSave").attr("disabled",false);
							var lineId = $(this).parents("TR").find("input[type=hidden][name=lineId]").val();
							$.ajaxInvoke({
								url : _self.apiContextPath + "/offlineCourses/"+lineId ,
								type :  "get",
								dataType: "json",
								success	:	function(data){
									$("#pid").val(data.pid);
									$("#title").val(data.title);
									$("#duration").val(data.duration);
									var time = data.startTime;
									$("#startTime").val(time.substring(0,time.length-2));
									$("#teacherIds").val(data.teacherIds);
									_self.editorSet(_self,data.description);
									$("#location").val(data.location);
									$("#price").val(data.price);
									$("#maxbookCount").val(data.maxbookCount);
									$("#postimage").val(data.poster);
									$("#logoimage").val(data.logoUrl);
									$("#postimg").attr({"src":data.poster,"alt":data.poster,"title":"海报图片"});
									$("#logoimg").attr({"src":data.logoUrl,"alt":data.logoUrl,"title":"封面图片"});
									$("#vendorName").val(data.vendorName);
									$("#status").val(data.status);
									$("#myModalLabel").html("修改线下课程");
									$(".btn-save").hide();
									$(".btn-updateSave").show();
									$(".logofile").hide();
									$(".logodiv").show();
									$(".postfile").hide();
									$(".postdiv").show();
									$("#myModal").modal("show");
								},
							});		
						});
					},
					"fnInitComplete":function(){
						//$('#reportDataTable_length').hide();
						//$('#reportDataTable').show();
						//$('#container2').css({height: $('#reportDataTable').height});
					},
					"sAjaxDataProp":"datas",
					"aoColumns": [
									{ "sTitle": "课程标题","mData" :"title",},
									{ "sTitle": "开始时间","mData" :"startTime",},
									{ "sTitle": "课程讲师","mData" :"teacherIds",
										"render": function(data) {
											var obj = data.split(";");
											var result = "";
											$.each(obj,function(i,value){
												if(value != "") result += value+" ";
											});
											return result;
										}
									},
									{ "sTitle": "时长","mData" :"duration","render": function(data) {return data+"分钟";}},
									{ "sTitle": "课程状态","mData" :"status",
										"render": function(data) {
											var type = "";
											if(data==1) type="已保存";
											if(data==2) type="报名中";
											if(data==3) type="报名结束";
											if(data==4) type="已过期";
											if(data==5) type="已取消";
											return type;
										}
									},
									{ "sTitle": "创建时间","mData" :"createTime",},
									{ "sTitle": "操作", "mData" :"", "bSortable" : false , "sWidth":"180px",
										"render": function(data,dis,obj) {
											var editDom = "",publishDom="",cancelDom="";
											deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> ";
											if(obj.status==1){
												editDom = "<span class='btn btn-success btn-xs btn-modify'> 修改 </span> " ;
												publishDom = "<span class='btn btn-primary btn-xs btn-publish'> 发布 </span> ";
											} else { 
												cancelDom = "<span class='btn btn-warning btn-xs btn-cancel'> 撤销 </span> " ;
											}
											return "<input type='hidden' name='lineId' value='"+obj.pid+"'/>" + 
												   "<input type='hidden' name='lineStatus' value='"+obj.status+"'/>" +
												   editDom + publishDom + cancelDom + deleteDom ;
									    }
									}
		                      ],
				};
			commonSetting4DataTable(setting);
			_self.dataTable = $('#datatable').dataTable(setting);
		},
	}

$(document).ready(offline.init());