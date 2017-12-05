var hot={
		apiContextPath:'/adminapi/v1',
		lg:function(msg){
			if(console && console.log){
				//console.log(msg);
			}
		},
		init:function(){
			var _self = this;
			//$("input:radio").attr('disabled',false);
			//data.type = parseInt($("input:radio[name='type']:checked").val());
			$('#searchCourse').unbind().bind('click',function(){
					if($(this).prop("checked")==true){
						$('#courseName').prop("disabled", false);
						$('#typeName').prop("disabled", true);
						$('#type').prop("disabled", true);
					}else{
						$('#courseName').prop("disabled", true);
						$('#typeName').prop("disabled", false);
						$('#type').prop("disabled", false);						
					}
				});
			
			$('.btn-search').unbind().bind('click', function(){
				_self.lg('click btn-search!');
				$('#typeId').val($("input:radio[name='type']:checked").val());
				_self.initTypeDataTable(_self);
			});
			
			$('.btn-search-course').unbind().bind('click', function(){
				_self.lg('click btn-search-course!');
				_self.initCourseTable(_self);
			});
		    
		},
		tableSetting: function(_self, aoColumns, url, type){
			_self.lg('url:' + url);
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
					"aaSorting":[[1, "desc"]],
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
						var postData = {
								"orderby":(orderby?orderby:"hotIndex"),
								"direction":(direction?direction:"asc"),
								"pageSize":(length?length:10000),
								"limit":(length?length:10000),
								"offset":start?start:0,
						};
						
						if(url.indexOf('course') != -1){
							//postData["name"] = $("#typeName").val();
							postData["targetId"] = $("#targetId").val();
							postData["name"] = $("#courseName").val();
						}else{
							postData["type"] = $("input:radio[name='type']:checked").val();
							postData["name"] = $("#typeName").val();
						}
							$.ajaxInvoke( {
								type: type,
								url: url,
								dataType: "json",
								data:postData, 
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

					"sAjaxDataProp":"datas",
					"aoColumns": aoColumns,
			 	};
			 	commonSetting4DataTable(setting);
			 	
				$.each(setting.aoColumns, function(i, item){
					if(item["sTitle"] == "操作"){
						item["bSortable"] = false;
					}
				});
				
				return setting;
			},
		initTypeDataTable:function(self){
			_self = self;
			if(_self.typeDataTable){
				_self.typeDataTable.fnDraw();
				return;
			}

			var aoColumns = [
								
								{ "sTitle": "名称","mData" :"name",},
								{ "sTitle": "热度","mData" :"hotIndex","render": function(data, style , obj){
									return '<a href="#" pid="' + obj.pid + '" data-type="text" data-pk="1" data-title="设置热度" class="typehotsetting">' + (data?data:0) + '</a>';
								},},
								/*
								{ "sTitle": "操作","mData" :"pid","render": function(data, style , obj){
									return "<span class='btn btn-success btn-xs btn-manage' pid='" + data + "'>设置课程</span>";
								},},*/ 
	                   ];
			
			var url = _self.apiContextPath + "/hotspot/list?" + Math.random();
			var setting = _self.tableSetting(_self, aoColumns,url, "get");

			setting.fnDrawCallback = function(){
				_self.lg('nfDrawCallback');
				$('.typeDataDiv').show();

			    //editables 

			    $('.typehotsetting').editable({
			           url: function(params){
			        	   var putData = { type:$('#typeId').val(),
			        			   targetId:$(this).attr("pid"),
			        			   hotIndex:$.trim(params.value),
			        	   }
							$.ajaxInvoke( {
								type: "put",
								url: _self.apiContextPath + "/hotspot?" + Math.random(),
								dataType: "json",
								data: JSON.stringify(putData), 
								success: function(content, status) {
									_self.lg(content);
									_self.lg(status);
								},
								error:function(data){
					            	_self.lg(data);
					            }
							})
			           },
			           type: 'text',
			           pk: 1,
			           validate:function(value){
			        	  if( ( ! /^[1-9]{1}[0-9]*$/.test($.trim(value)) && $.trim(value) != 0 ) || ( $.trim(value) > 100 )){
				        	   return "格式不对。 必须为0~100以内的整数。";
			        	  }
			           },
			           name: $.trim($(this).attr('pid')),
			           title: '设置热度'
			    });
			    
			    console.log($('.typehotsetting'));
				$('.btn-manage').unbind().bind('click',function(){
					_self.lg('click btn-manage');
					$('#targetId').val($.trim($(this).attr('pid')));
					var typeName = "";
					if($('#typeId').val() == 1){
						typeName = "行业";
					}else if($('#typeId').val() == 2){
						typeName = "岗位";
					}else if($('#typeId').val() == 3){
						typeName = "能力";
					}

					typeName = typeName + ":";
					
					$(".course-modal-title").text("课程列表【" + typeName + $($(this).parent().parent()[0].children[0]).text() + "】");
					$("#courseName").val('');
					_self.initCourseTable(_self);
				});
			};
			_self.lg('init typeDataTable');
			_self.typeDataTable = $('#typeDataTable').dataTable(setting);
		},
		initCourseTable:function(self){
			_self = self;
			
			if(_self.courseTable){
				_self.courseTable.fnDraw();
				return;
			}

			var aoColumns = [
								
								{ "sTitle": "名称","mData" :"title",},
								{ "sTitle": "热度","mData" :"hotIndex","render": function(data, style , obj){
									return '<a href="#" pid="' + obj.pid + '" data-type="text" data-pk="1" data-title="设置热度" class="coursehotsetting">&nbsp;&nbsp;' + data + '&nbsp;&nbsp;</a>';
								},},
								/*
								{ "sTitle": "推荐系数","mData" :"recommendIndex","render": function(data, style , obj){
									return '<a href="#" pid="' + obj.pid + '" data-type="text" data-pk="1" data-title="设置推荐系数" class="recommendsetting">&nbsp;&nbsp;' + data + '&nbsp;&nbsp;</a>';
								},},
								{ "sTitle": "操作","mData" :"pid","render": function(data, style , obj){
									return "<span class='btn btn-success btn-xs btn-course-hot' pid='" + data + "'>设置热度</span>";
								},},*/
	                   ];
			
			
			var url = _self.apiContextPath + "/hotspot/courses/list?" + Math.random();
			var setting = _self.tableSetting(_self, aoColumns,url, "get");

			setting.fnDrawCallback = function(){
				_self.lg('nfDrawCallback');
				
				$("#courseHotListTable").modal("show");
				
			    //editables 
			    $('.coursehotsetting').editable({
			           url: function(params){
			        	   var putData = { type:$('#typeId').val(),
			        			   targetId:$('#targetId').val(),
			        			   hotIndex:$.trim(params.value),
			        			   knowledgeId:$.trim($(this).attr('pid')), 
			        	   }
							$.ajaxInvoke( {
								type: "put",
								url: _self.apiContextPath + "/hotspot/courses?" + Math.random(),
								dataType: "json",
								data: JSON.stringify(putData), 
								success: function(content, status) {
									_self.lg(content);
									_self.lg(status);
								},
								error:function(data){
					            	_self.lg(data);
					            }
							})
			           },
			           type: 'text',
			           pk: 1,
			           name: $.trim($(this).attr('pid')),
			           title: '设置热度'
			    });
			    
			    //editables 
			    $('.recommendsetting').editable({
			           url: function(params){
			        	   var putData = { type:$('#typeId').val(),
			        			   targetId:$('#targetId').val(),
			        			   recommendIndex:$.trim(params.value),
			        			   knowledgeId:$.trim($(this).attr('pid')), 
			        	   }
							$.ajaxInvoke( {
								type: "put",
								url: _self.apiContextPath + "/hotspot/courses?" + Math.random(),
								dataType: "json",
								data: JSON.stringify(putData), 
								success: function(content, status) {
									_self.lg(content);
									_self.lg(status);
								},
								error:function(data){
					            	_self.lg(data);
					            }
							})
			           },
			           type: 'text',
			           pk: 1,
			           name: $.trim($(this).attr('pid')),
			           title: '设置推荐系数'
			    });
			};
			_self.lg('init courseTable');
			_self.courseTable = $('#courseTable').dataTable(setting);
		},
}
$(document).ready(hot.init());