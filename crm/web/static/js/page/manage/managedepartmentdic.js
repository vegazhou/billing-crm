var Sysdic = {
		apiContextPath:'/adminapi/v1',
		lg:function(msg){
			if(console && console.log){
				console.log(msg);
			}
		},
		actionType:null,
		dictId:null,
		industryLevel2ParentData:null,
		init :function(){
			var _self = this;

	        $.ajaxInvoke({
	            url: _self.apiContextPath + "/industries/?" + Math.random(),
	            data: {},
	            success: function(content, status) {
	            	_self.industryLevel2ParentData = [];
					$.each(content.datas, function(i,item){
						if(item.datas){
							$.each(item.datas, function(i2,item2){
								_self.industryLevel2ParentData[$.trim(item2.id)] = $.trim(item.id);
							});
						}
					});
	            },
	            type:'get'
	        });
	        
			loadIndustry(0, '#industry');
			loadIndustry(0, '#industry2');
			$('#industry').unbind().bind('change', function(){
				var selectedIndustryId = $(this).children('option:selected').val();
				_self.lg(selectedIndustryId);
				if(selectedIndustryId == '' || selectedIndustryId == 'common'){
					$('.subin-col').hide();
					$('#subindustry').empty();
				}else{
					$('#subindustry').empty();
					$('#subindustry').append("<option value=''>全部子行业</option>");
					loadIndustry(selectedIndustryId, '#subindustry');
					$('.subin-col').show();
				}
			});
			
			$('#subindustry').unbind().bind('change', function(){
	
			});

			$('#industry2').unbind().bind('change', function(){
				var selectedIndustryId = $(this).children('option:selected').val();
				_self.lg(selectedIndustryId);
				if(selectedIndustryId == ''){
					$('.subin-div').hide();
					$('#subindustry2').empty();
					
					_self.resetFunctionSelect();
				}else{
					$('#subindustry2').empty();
					$('#subindustry2').append("<option value=''>请选择行业</option>");
					loadIndustry(selectedIndustryId, '#subindustry2');
					$('.subin-div').show();
				}
			});
			
			$('#subindustry2').unbind().bind('change', function(){
				if($(this).val() == ''){
					_self.resetFunctionSelect(_self, this);
				}else{
					_self.resetFormAfterIndustryChanged(_self, $(this).val());
				}
			});
			
			$('.btn-search').unbind().bind('click', function(){
				_self.initTemplateTable(_self);
				$('.departmenttemplatelist').show();
			});
			$('.btn-add').unbind().bind('click', function(){
				if(_self.actionType && 'add' != _self.actionType){
					$('#industry2').val('');
					$('#subindustry2').empty();
					$('#subindustry2').val('');
					$('#departmentName').val('');
					$('.subin-div').hide();
					_self.resetFormForEmptyIndustry(_self);
					_self.resetFunctionSelect();
					_self.loadCommonFunction(_self);
					_self.actionType = 'add';
				}
				$('.org-modal-title').text('新增部门');
				$("#addDepartment").modal("show");
			});
			
			$('.btn-save').unbind().bind('click', function(){
				if(!validator.validate()){
					return false;
				}
				var industryId = $('#industry2').val();
				
				var subindustryId = $('#subindustry2').val();
				
				if(subindustryId != ''){
					industryId = subindustryId;
				}
				var functionids = '';
				$.each($('#functionId').multiselect("widget").find("input:checked"), function(i, item){
					functionids = functionids + $(this).val() + ";";
				});
				
				if($('#departmentName').val() == ''){
					return false;
				}
				if(functionids == ''){
					 $('.warning').append('<font color=red>请选择职能！</font>');
					 return false;
				}
				_self.lg('functionIDs:' + functionids);
				var name = industryId;
				if(name == ''){
					name = '通用职能部门';
				}
				var value = $('#departmentName').val() + ':1:' + functionids;
				if($('#parentDepartment').val() != ''){
					value = $('#departmentName').val() + ':2:' + functionids;
				}
				
				if($('#parentDepartment').val() != ''){
					value = $('#parentDepartment').val() + ':' + value;
				}else{
					value = 0 + ':' + value;
				}
				var putData = {
				    "type": 7,
				    "name": name,
				    "value": value,
				}
				
				var type = 'post';
				var url = _self.apiContextPath + "/dicts" + "?" + Math.random();
				if(_self.actionType == 'edit'){
					type = 'put';
					url = _self.apiContextPath + "/dicts/" + _self.dictId + "?" + Math.random();
				}
				$.ajaxInvoke( {
					type: type,
					url: url,
					dataType: "json",
					data: JSON.stringify(putData), 
					success: function(content, status) {
						_self.lg(content);
						_self.lg(status);
						_self.lg("Add new department success!");
						$("#addDepartment").modal("hide");
						_self.initTemplateTable(_self);
					},
					error:function(data){
		            	_self.lg(data);
		            }
				});
				
				
				
				_self.resetFunctionSelect();
				_self.loadCommonFunction(_self);
				$('#departmentName').val('');
				$('#subindustry2').val('');
//				$('#subindustry2').hide();
				$('.subin-div').hide();
				$('#industry2').val('');
				$('#parentDepartment').val('');
				
				_self.lg('redraw');
				
			});
			
			var rule = {
					"departmentName": {
			            name: "部门名称",
			            method: {
			                required: true,
			            },
			        },
			}

			$('#industry2').val('');
			$('#subindustry2').val('');
			_self.resetFunctionSelect();
			//$('#functionId').append("<option value=''>全部</option>");
			_self.loadCommonFunction(_self);
			
			validator.init(rule);
			_self.initTemplateTable(_self);
		},
		resetFormForEmptyIndustry:function(_self, _this){
			$('#parentDepartment').empty();
			$('#parentDepartment').append("<option value=''>不选</option>");
//			_self.loadCommonFunction(_self);
		},
		resetFormAfterIndustryChanged:function(_self, industryId, parentDepartmentID, selectedfunctions){
		    var newUrl = _self.apiContextPath + "/dicts/department/template?industryid=" + encodeURIComponent(industryId) + "&" + Math.random();
			var postData = {
					"orderby":"value",
					"direction":"asc",
					"pageSize":10000,
					"limit":10000,
					"offset":0,
					};
			$.ajaxInvoke( {
				type: 'get',
				url: newUrl,
				dataType: "json",
				data:postData, 
				success: function(content, status) {
					$('#parentDepartment').empty();
					$('#parentDepartment').append("<option value=''>不选</option>");
		        	$.each(content.datas, function(i, item){
		        		if(item.value.split(':')[2] == '1'){
		        			$('#parentDepartment').append("<option value='"+ item.dictId +"'>" + item.value.split(':')[1] + "</option>");
		        		}
		        	});
		        	_self.lg('parentDepartmentID:' + parentDepartmentID);
		        	if(parentDepartmentID){
		        		$('#parentDepartment').val(parentDepartmentID);
		        	}
				},
				error:function(data){
	            	_self.lg(data);
	            }
			});
			
			$.ajaxInvoke({
		        url: adminURI + "/industries/" +  encodeURIComponent(industryId) + "/functions?" + Math.random(),
		        data: {},
		        success: function(data) {
		        	_self.lg(data);
					data.datas.sort(function(a,b){
						if(a && b){
							return a.name.localeCompare(b.name)
						};//汉字拼音排序方法
					});
					_self.resetFunctionSelect();
					if(selectedfunctions){
						_self.lg("selectedfunctions >>>>>>>>>> " + selectedfunctions);
					}
		        	$.each(data.datas, function(i, item){
		        	    var selected = ''; 
		        	    _self.lg('curent item function id >>>>>>>>> ' + item.id);
		        	    if(selectedfunctions){
			        		$.each(selectedfunctions, function(i,functionId){
			        			_self.lg('function id >>>>>>>>> ' + functionId);
				        		if(functionId == item.id){
				        			selected = 'selected';
				        		}
				        	});
		        	    }
		        		
		        		$('#functionId').append("<option value='"+ item.id +"' "  + selected + " >" + item.name + "</option>");
		        	});

		        	
		        	
		        	_self.lg(' >>>>>>>>>>>>>>>>>>>>>>>>>>>>> start init multi select');
					$('#functionId').multiselect({
						//header: "Choose only TWO items!",
						checkAllText: '全选',
						uncheckAllText: '清空',
						noneSelectedText: '请选择',
						selectedText: '# 已选择',
						   click: function(e){
						       if( $(this).multiselect("widget").find("input:checked").length == 0 ){
						           $('.warning').append('<font color=red>请选择职能！</font>');
						       } else {
						    	   $('.warning').empty();
						       }
						   },
					});
					
		        },
		        type:'get'
		    });
		},
		loadCommonFunction:function(_self, selectedfunctions){
			var commonFunctionsUrl = _self.apiContextPath+"/industries/functions?"+ Math.random();
			var data = {};
			data.limit = 10000;
			$.ajaxInvoke({
				url  : 	commonFunctionsUrl,
				type :  "get",
				dataType:	"json",
				data : data,
				//headers : {'Source' : '101','Token':token},
				success	:	
					function(data){
						_self.resetFunctionSelect();
			        	$.each(data, function(i, item){
			        		
			        	    var selected = ''; 
			        	    _self.lg('curent item function id >>>>>>>>> ' + item.id);
			        	    if(selectedfunctions){
				        		$.each(selectedfunctions, function(i,functionId){
				        			_self.lg('function id >>>>>>>>> ' + functionId);
					        		if(functionId == item.id){
					        			selected = 'selected';
					        		}
					        	});
			        	    }
			        		$('#functionId').append("<option value='"+ item.id +"' " + selected + " >" + item.name + "</option>");
			        	});
			        	
						$('#functionId').multiselect({
							//header: "Choose only TWO items!",
							checkAllText: '全选',
							uncheckAllText: '清空',
							noneSelectedText: '请选择',
							selectedText: '# 已选择',
							   click: function(e){
							       if( $(this).multiselect("widget").find("input:checked").length == 0 ){
							           $('.warning').append('<font color=red>请选择职能！</font>');
							       } else {
							    	   $('.warning').empty();
							       }
							   }
						});
					}
			});
		},
		resetFunctionSelect:function(){
			$('.functionSelect').empty().append('<select id="functionId" name="functionId" multiple="multiple" class="form-control"></select>');
		},
		tableSetting: function(_self, aoColumns){
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
					"aaSorting":[[4, "desc"]],
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
						
						if("dictId" == orderby){
							orderby = 'pid';
						}
						var postData = {
							"orderby":(orderby?orderby:"createTime"),
							"direction":(direction?direction:"asc"),
							"pageSize":(length?length:10000),
							"limit":(length?length:10000),
							"offset":(start?start:0),
							};
							
						var industId = $('#subindustry').val()? $('#subindustry').val() : $('#industry').val() ;
						_self.lg($('#industry'));
							_self.lg('industId:'+industId);
						    var newUrl = _self.apiContextPath + "/dicts/department/template?industryid=" + industId + "&" + Math.random();
							$.ajaxInvoke( {
								type: 'get',
								url: newUrl,
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
		initTemplateTable:function(self){
			_self = self;
			_self.lg(' == initTemplateTable ' );
			if(_self.departmentTemplateTable){
				_self.departmentTemplateTable.fnDraw();
				return;
			}
			
			var aoColumns = [
								/*{
								    "sTitle": "<input type='checkbox' class='mycheckbox-checkall' id=''/>",
								    "mData": "dictId",
								    "sWidth": "5%",
								    "bSortable": false,
								    "render": function(data) {
								    	if(data){
								    		return "<input type='checkbox' class='item-checkbox checkable' value='" + data + "'>";	
								    	}
								    }
								
								},*/

								{ "sTitle": "部门","mData" :"value","render":function(data){
									return data?data.split(':')[1]:"";
								},},
								{ "sTitle": "行业","mData" :"name","render":function(data){
									return data?data.split(':')[0]:"";;
								},},
								{ "sTitle": "上级部门","mData" :"value","bSortable": false,"render":function(data){
									return data?data.split(':')[0]:"";
								},},
								{ "sTitle": "职能","mData" :"value","bSortable": false,"render":function(data){
									return data?data.split(':')[3]:"";
								},},
								{ "sTitle": "创建时间","mData" :"createTime","render" : function(data){
									return (data && data.length > 19)?data.substring(0,19):'';
								},},
								{ "sTitle": "操作","mData" :"dictId","render" : function(data){
									var result = "<nobr><span class='btn btn-danger btn-xs btn-delete' pid='" + data + "'>删除</span> ";
									result = result.concat("<span class='btn btn-success btn-xs btn-edit' pid='" + data + "'>编辑</span> ");
									return result.concat('</nobr>');
								},},
	                   ];
			
			var url = _self.apiContextPath + "/dicts/department/template?" + Math.random();
			var setting = _self.tableSetting(_self, aoColumns);

			setting.fnDrawCallback = function() {
				_self.lg('nfDrawCallback');
				
				$('.btn-delete').unbind().bind('click', function() {
					var pid = $(this).attr('pid');
					mConfirm("是否确定删除？", function(){
						$.ajaxInvoke({
							type : 'DELETE',
							url : _self.apiContextPath + "/dicts/" + pid,
							dataType : "json",
							data : {},
							success : function(content, status) {
								_self.lg(content);
								_self.lg(status);
								_self.lg("delete success!");
								_self.departmentTemplateTable.fnDraw();
							},
							error : function(data) {
								_self.lg(data);
							}
						});
					})
				});
				
				$('.btn-edit').unbind().bind('click', function() {
					var pid = $(this).attr('pid');
					_self.dictId = pid;
					_self.actionType = 'edit';
					$('.org-modal-title').text('编辑部门');
					_self.resetFunctionSelect();
					_self.loadCommonFunction(_self);
					$('#departmentName').val('');
					$('#subindustry2').val('');
//					$('#subindustry2').hide();
					$('.subin-div').hide();
					$('#industry2').val('');
					$('#parentDepartment').val('');

					
					var dict = '';
					//var parentDict = '';
						$.ajaxInvoke({
							type : 'GET',
							url : _self.apiContextPath + "/dicts/" + pid,
							dataType : "json",
							data : {},
							success : function(content, status) {
								_self.lg(content);
								_self.lg(status);
								//_self.departmentTemplateTable.fnDraw();
								dict = content;
								
//								if(dict.name){
//									$.ajaxInvoke({
//										type : 'GET',
//										url : _self.apiContextPath + "/dicts/" + dict.value.split(':')[0],
//										dataType : "json",
//										data : {},
//										success : function(content, status) {
//											_self.lg(content);
//											_self.lg(status);
//											_self.lg("delete success!");
//											//_self.departmentTemplateTable.fnDraw();
//											parentDict = content;
//										},
//										error : function(data) {
//											_self.lg(data);
//										}
//									});
//								}
								$('#departmentName').val(dict.value.split(':')[1]);
								
								var parentDepartmentID = '';
								if(dict.value.split(':')[0]!='0'){
									_self.lg(dict.value.split(':')[0]);
									parentDepartmentID = dict.value.split(':')[0];
								}
								
								if('通用职能部门' == dict.name){
									$('#industry2').val('');
									_self.resetFormForEmptyIndustry(_self);
									$('#subindustry2').val('');
									
									_self.loadCommonFunction(_self, dict.value.split(':')[3].split(';'));
									
									validator.validate();
									$("#addDepartment").modal("show");
								}else{
									_self.lg('Industry ID: >>>>>>>' + dict.name);
									if(_self.industryLevel2ParentData[dict.name]){
										_self.lg('parent industry ID:'+_self.industryLevel2ParentData[dict.name]);
										$('#industry2').val(_self.industryLevel2ParentData[dict.name]);
	
										
										$('#subindustry2').empty();
										$('#subindustry2').append("<option value=''>请选择行业</option>");
										loadIndustry(_self.industryLevel2ParentData[dict.name], '#subindustry2', function(){
											$('#subindustry2').val(dict.name);
											_self.resetFormAfterIndustryChanged(_self, dict.name, parentDepartmentID, dict.value.split(':')[3].split(';'));
										});
										
										$('.subin-div').show();
										validator.validate();
										$("#addDepartment").modal("show");
									}else{
										$('#industry2').val(dict.name);
									}
								}

//								var postData = {
//										"orderby":"value",
//										"direction":"asc",
//										"pageSize":10000,
//										"limit":10000,
//										"offset":0,
//										};
//										
//									    var newUrl = _self.apiContextPath + "/dicts/department/template?industryid=" + encodeURIComponent(dict.name) + "&" + Math.random();
//										$.ajaxInvoke( {
//											type: 'get',
//											url: newUrl,
//											dataType: "json",
//											data:postData, 
//											success: function(content, status) {
//												
//											},
//											error:function(data){
//								            	_self.lg(data);
//								            }
//										});
								_self.lg(dict.value);

//								
//								if(dict.value.split(':')[0]!='0'){
//									$.ajaxInvoke({
//										type : 'GET',
//										url : _self.apiContextPath + "/dicts/" + dict.value.split(':')[0],
//										dataType : "json",
//										data : {},
//										success : function(content, status) {
//											_self.departmentTemplateTable.fnDraw();
//											parentDict = content;
//										},
//										error : function(data) {
//											_self.lg(data);
//										}
//									});
//								}
							},
							error : function(data) {
								_self.lg(data);
							}
						});
				});
			};
			_self.lg('init initTemplateTable');
			_self.departmentTemplateTable = $('#departmentTemplateTable').dataTable(setting);
		},
}		

$(document).ready(Sysdic.init());		
		