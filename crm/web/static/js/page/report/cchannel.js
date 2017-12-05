function progress(max, timeout) {
	var val = $("#progressbar").progressbar("value") || 0;

	$("#progressbar").progressbar("value", val + 1);

	if (val < max && val < 98) {
		setTimeout("progress(" + max + ");", timeout);
	}
}
var company = {
		apiContextPath:'/adminapi/v1',
		lg:function(msg){
			if(console && console.log){
				console.log(msg);
			}
		},
		thirdpart:false,
		durationType:1,
		type:'employee',
		channelType:2,
		reportData:null,
		retentionData:null,
		dataTable:null,
		userDataTable:null,

		columnNameArr : {startTime: "记录日期", 
			totalUser:"总用户数", 
			newUser:"新增用户数", 
			activeUser:"活跃用户数",
			activeOldUser:"活跃老用户数", 
			totalStudyHour:"总学习时间(分钟)", 
			newStudyHour:"新增学习时间(分钟)",
			totalOrg:"总企业号",
			newOrg:"新增总企业号",
			newCourseBrowse:"课件学习人次",
			totalOrder:"总订单",
			newOrder:"新增订单",
			totalOrderAmount:"总金额",newOrderAmount:"新增金额" },

		progressbar : $("#progressbar"),
		progressLabel : $(".progress-label"),
		init:function(){
			var _self = this;
						
			loadChannel('#channel', _self, _self.channelType);
			if(window.location.href.indexOf('3cchannel.jsp') != -1){
				_self.thirdpart = true;
				$('.hidden-for-3c').hide();

				_self.columnNameArr = {startTime: "记录日期", 
					totalUser:"总用户数", 
					newUser:"新增用户数", 
					activeUser:"活跃用户数",
					activeOldUser:"活跃老用户数", 
				}
			};
			$('.btn-one-week').unbind().bind('click', function(){
				_self.setSearchDuration(7);
				$('.btn-search').click();
			});

			$('.btn-two-week').unbind().bind('click', function(){
				_self.setSearchDuration(14);
				$('.btn-search').click();
			});

			$('.btn-one-month').unbind().bind('click', function(){
				_self.setSearchDuration(30);
				$('.btn-search').click();
			});
			
			var downloadChannelUserTable = function(){
				$('.download_channelUserTable').unbind();
				_self.lg('download channelUserTable');
				_self.download_PageTableData("channelUserTable" , "渠道用户数据报表", _self);
				$('.download_channelUserTable').unbind().bind('click', downloadChannelUserTable);
			};
			$('.download_channelUserTable').unbind().bind('click', downloadChannelUserTable);
			

			$('.btn-search').unbind().bind('click', function(){
				var postData = _self.jsonPostData(_self);
				if(postData){
					
					_self.lg('search:' + postData);
			        $.ajaxInvoke({
			            url: _self.apiContextPath + "/cuserreport?" + Math.random(),
			            data: postData,
			            success: function(content, status) {
			            	_self.lg('click search: result:' + content);
			            	_self.reportData = content.datas;
//			            	_self.csvFileName = _self.getFileName(_self);
			            	_self.draw(_self);
			            	_self.initUserTable(_self);
			            	if(!_self.thirdpart){
			            		_self.loadRetentionDataTable(_self);
			            	}
			            	_self.initChannelUserTable(_self);
			            },
			            dataType:'json',
			            type:'post'
			        });
			        
				}
			});
			var now = new Date();
			now = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 1);
			var	maxTime = now.getFullYear() + '-' + (now.getMonth()<9 ? ('0' + (now.getMonth() + 1)): (now.getMonth() + 1)) + '-' + now.getDate();
			
			$('#reportEndTime').unbind().bind('click',  function() {
			    WdatePicker({
			        skin: 'twoer',
			        dateFmt: 'yyyy-MM-dd',
			        maxDate:maxTime
			    });
			    return false;
			});

			$('#reportStartTime').unbind().bind('click',  function() {
			    WdatePicker({
			        skin: 'twoer',
			        dateFmt: 'yyyy-MM-dd',
			        maxDate:maxTime
			    });
			    return false;
			});
			
			//$('#container1').css({height:"390px"});
			//$('.tab-pane').css({height:"440px"});
			$('.btn-duration').unbind().bind('click', function(){
				$('.btn-duration').removeClass('active');
				$(this).addClass('active');
				_self.durationType = $(this).attr('value');
				$('.btn-search').click();
			});
			
			$('#index-top-tab a[data-toggle="tab"]').on('shown.bs.tab',
					function(e) {
						$(this).tab('show');
						var id = $(this).attr('value');
						_self.type = '' + id;
						_self.draw(_self);
					});
			
			_self.progressbar.progressbar({
				value : false,
				change : function() {
					_self.progressLabel.text(_self.progressbar.progressbar("value") + "%");
				},
				complete : function() {
					_self.progressLabel.text("完成！");
				}
			});
			

			_self.download_usersData_bind(_self);
			_self.download_pageData_bind(_self);

		},
		download_usersData_bind : function(self) {
			var _self = self;
			_self.progressbar.hide();
			_self.progressLabel.show();
			$('.download_usersTableData').unbind().bind('click', function() {
				_self.lg("disable download_usersTableData");
				$('.download_usersTableData').unbind();
				_self.download_usersData(self);
			});
		},

		download_pageData_bind : function(self) {
			var _self = self;
			$('.download_currentPageData').unbind().bind('click', function() {
				_self.lg("disable download_currentPageData");
				$('.download_currentPageData').unbind();
				_self.download_pagesData(self);
			});
		},
		download_PageTableData: function(tableDiv, tableName, self){
			var _self = self;
			var header = encodeURI("data:text/csv;charset=utf-8,\ufeff");
			if (window.navigator.msSaveOrOpenBlob) {
				header = "\ufeff";
			}
			
			var content = '';
			var trs = $('#' + tableDiv).find('TR');
			$.each(trs, function(i, tritem){
				var line = '';
				$.each(tritem.children, function(i, item){
					if(i != 0){
						line = line  + ',';
					}
					
					var itemValue = '';
					var subItem = item;
					while(true){
						if(subItem.children.length > 0){
							_self.lg($(subItem).html());
							subItem = subItem.children[0];
						}else{
							break;
						}
					}
					line = line + $(subItem).text();
					
				});
				content = content + encodeURI(line + "\n");
			});
			
			
			var cvsFileName = tableName + "-当前页";
			if (window.navigator.msSaveOrOpenBlob) {
				// if browser is IE
				var blob = new Blob([ decodeURIComponent(header + content) ], {
					type : "text/csv;charset=utf-8;"
				});
				navigator.msSaveBlob(blob, cvsFileName  + ".csv" );
			} else {
				var encodedUri = header + content;
				_self.lg("encodedUri length:" + encodedUri.length);
				var link = document.createElement("a");
				link.setAttribute("href", encodedUri);
				link.setAttribute("download", cvsFileName  + ".csv");
				document.body.appendChild(link);
				link.click();
			}
		},
		download_pagesData: function(self){
			var _self = self;
			var header = encodeURI("data:text/csv;charset=utf-8,\ufeff");
			if (window.navigator.msSaveOrOpenBlob) {
				header = "\ufeff";
			}
			
			var content = '';
			var trs = $('#userDataTable').find('TR');
			$.each(trs, function(i, tritem){
				var line = '';
				$.each(tritem.children, function(i, item){
					if(i != 0){
						line = line  + ',';
					}
					
					var itemValue = '';
					var subItem = item;
					while(true){
						if(subItem.children.length > 0){
							_self.lg($(subItem).html());
							subItem = subItem.children[0];
						}else{
							break;
						}
					}
					line = line + $(subItem).text();
					
				});
				content = content + encodeURI(line + "\n");
			});
			
			
			var cvsFileName = "用户运营数据-当前页";
			if (window.navigator.msSaveOrOpenBlob) {
				// if browser is IE
				var blob = new Blob([ decodeURIComponent(header + content) ], {
					type : "text/csv;charset=utf-8;"
				});
				navigator.msSaveBlob(blob, cvsFileName  + ".csv" );
			} else {
				var encodedUri = header + content;
				_self.lg("encodedUri length:" + encodedUri.length);
				var link = document.createElement("a");
				link.setAttribute("href", encodedUri);
				link.setAttribute("download", cvsFileName  + ".csv");
				document.body.appendChild(link);
				link.click();
			}
			_self.download_pageData_bind(_self);
		},

		download_usersData : function(self) {
			var _self = self;
			_self.progressbar.show();
			_self.progressLabel.show();
			_self.progressbar.progressbar("value", 0);
			var header = encodeURI("data:text/csv;charset=utf-8,\ufeff");
			if (window.navigator.msSaveOrOpenBlob) {
				header = "\ufeff";
			}
			var columnDataName = [];
			var columnNameArr = [];
			$.each(_self.columnNameArr, function(i, val) {  
			    // console.log("Key:" + i + ", Value:" + val); 
			    columnNameArr.push(val); 
			    columnDataName.push(i);

			});  

			var cvsFileName = "用户运营数据";
			header += encodeURI(columnNameArr.join(",") + "\n");
			var csvContent = '';
			var maxLength = (window.chrome)? 5000:20000;
			var fetchLength = (window.chrome)? 500:2000;
			var postData = _self.jsonPostData(_self, 0, fetchLength, "startTime", "desc");
			
			_self.lg(postData);
			if (!postData) {
				return;
			}
			
			_self.loadUserData(_self, postData, maxLength, 0, 0, csvContent, columnDataName, function(index, data) {
				_self.lg("---- save file ---");
				if(data.length == 0){
					return;
				}
				if (window.navigator.msSaveOrOpenBlob) {
					// if browser is IE
					var blob = new Blob([ decodeURIComponent(header + data) ], {
						type : "text/csv;charset=utf-8;"
					});
					navigator.msSaveBlob(blob, cvsFileName + ((window.chrome && index > 0) ? "." + index : "") + ".csv" );
				} else {
					var encodedUri = header + data;
					_self.lg("encodedUri length:" + encodedUri.length);
					var link = document.createElement("a");
					link.setAttribute("href", encodedUri);
					link.setAttribute("download", cvsFileName + ((window.chrome && index > 0) ? "." + index : "") + ".csv");
					document.body.appendChild(link);
					link.click();
				}
			});
		},
		loadUserData : function(self, postData, maxlength, currentLength, index, csvContent, columnDataName,  callback) {

			var _self = self;
			_self.lg("csvContent length:" + csvContent.length );
			var timeStart = new Date();

			$.ajaxInvoke({
				type : "POST",
				url : _self.apiContextPath + "/cuserreport?" + Math.random(),
				dataType : "json",
				data : postData,
				success : function(content, status) {
					_self.lg(content);
					_self.channelTableData = content.datas;

					content.datas.forEach(function(infoArray, index) {
						var newRow = '';
						for (i = 0, n = columnDataName.length; i < n; i++) {
							var v = infoArray[columnDataName[i]];
							if (columnDataName[i] == 'startTime') {
								v = (v && v.length > 19) ? v.substring(0, 19) : '';
							}
							if (i != 0) {
								newRow += ',';
							}
							newRow += v;
						}

						csvContent += encodeURI(newRow + "\n");
					});

					var dataLength = content.paging.count;
					var nextStart = 0;
					var start = postData.offset;
					var length = postData.limit;
					if (dataLength > start + content.datas.length) {
						
						var nextAction = function(){
							nextStart = start + length;
							_self.progressbar.progressbar("value", Math.round((nextStart / dataLength) * 100));
							
							progress(((nextStart + length) / dataLength) * 100, (new Date().getTime() - timeStart.getTime())/(100/dataLength/length));
							
								_self.lg("继续下载");
								currentLength = currentLength + content.datas.length;
								if(currentLength >= maxlength && window.chrome){
									index ++;
									callback(index, csvContent);
									csvContent = '';
									currentLength = 0;
								}
							
							_self.lg("nextStart:" + nextStart + "; maxlength:" + maxlength + "; nextStart/maxlength=" + nextStart/maxlength);
							postData.offset =  nextStart;
							_self.lg(postData);
							_self.loadUserData(_self, postData, maxlength, currentLength, index, csvContent, columnDataName, callback);
						}
						if(maxlength < dataLength && start == 0 && window.chrome){
							mConfirm("为避免下载数据过多, 导致谷歌浏览器崩溃， <br>系统会自动以多个文件的形式分开下载。<br><br><br><br>点击【确定】继续，点击【取消】取消本次下载。", 
							nextAction, "", "", "", function(){
								_self.lg("取消下载");
								_self.progressLabel.text("取消！");
								_self.download_usersData_bind(_self);
								csvContent = '';
							});
						}else{
							nextAction();
						}
						
					} else {
						_self.progressbar.progressbar("value", 100);
						if(maxlength <= dataLength){
							index ++;
						}
						$("#progressbar").hide();
						_self.download_usersData_bind(_self);
						callback(index, csvContent);
					}

				},
				error : function(data) {
					_self.lg(data);
				}
			});
		},
		setSearchDuration:function(offset){
			var now = new Date();
			now = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 1);
			var	endTime = now.getFullYear() + '-' + (now.getMonth()<9 ? ('0' + (now.getMonth() + 1)): (now.getMonth() + 1)) + '-' + now.getDate();
			
			now = new Date(now.getFullYear(), now.getMonth(), now.getDate() - offset);
			var	startTime = now.getFullYear() + '-' + (now.getMonth()<9 ? ('0' + (now.getMonth() + 1)): (now.getMonth() + 1)) + '-' + now.getDate();
			
			$('#reportStartTime').val(startTime);
			$('#reportEndTime').val(endTime);
		},
		
		loadChannel:function(){
			var _self = this;
			var contextPath = _self.apiContextPath;
	        $.ajaxInvoke({
	            url: contextPath + "/dicts/mychannels?subtype=" +_self.channelType + "&"  + Math.random(),
	            data: {},
	            success: function(content, status) {
	            	_self.lg(content);
	            	$.each(content.datas, function(i, item){
	            		//_self.lg(item);
	            		$('#channel').append("<option value='"+ item.channelId +"'>" + item.channelName + "</option>");
	            	})
	            },
	            type:'get'
	        });
		},
		getChannelIds:function(){
			var selectedChannelId = $("#channel").children('option:selected').val();
			
			if(selectedChannelId == ''){
				$("#channel").children('option').each(function(){
					var val = $(this).val();
					if(val != '' ){
						selectedChannelId = selectedChannelId + ',"' + val + '"'  ;
					}
				}
				)
				
				selectedChannelId = selectedChannelId.substring(1);
			}else{
				selectedChannelId = '"' + selectedChannelId + '"';
			}
			
			
			
			if($("#channelNameKey").val()){
				selectedChannelId = '';
				var nameKey = $("#channelNameKey").val();
				$("#channel").children('option').each(function(){
					var val = $(this).val();
					var text = $(this).text();
					if(text.indexOf(nameKey) != -1 ){
						selectedChannelId = selectedChannelId + ',"' + val + '"'  ;
					}
				}
				)
				selectedChannelId = selectedChannelId.substring(1);
			}
			
			selectedChannelId = '[' + selectedChannelId + ']';
			
			return selectedChannelId;
		},
		jsonPostData:function(self, start, limit, orderby, direction, reportType){
			
			var _self = self;
			var selectedChannelId =  _self.getChannelIds();
			
			var strEndtime = $('#reportEndTime').val();
			var strStartTime = $('#reportStartTime').val();
			
			if(strEndtime == '' && strStartTime == ''){
				$('.btn-one-week').click();
				return;
			}
			
			var valid = true;
			if(!strEndtime){
				$('#reportEndTime').nextAll('span').html('<br><font color="red">请设置结束时间！</font>');
				valid = false;
			}else{
				$('#reportEndTime').nextAll('span').html('');
			}
			if(!strStartTime){
				$('#reportStartTime').nextAll('span').html('<br><font color="red">请设置开始时间！</font>');
				valid = false;
			}else{
				$('#reportStartTime').nextAll('span').html('');
			}
			
			if(!valid){
				return null;
			}
			var strStarttime = $('#reportStartTime').val();
			var arrStart = strStarttime.split("-");
			var startTime = new Date(arrStart[0], arrStart[1] - 1, arrStart[2]);

			var strEndtime = $('#reportEndTime').val();
			var arrEnd = strEndtime.split("-");
			var endTime = new Date(arrEnd[0], arrEnd[1] - 1, arrEnd[2]);

			var offsetDays = (endTime.getTime() - startTime.getTime()) / (24 * 3600 * 1000);
			$('.btn-daily').hide();
			$('.btn-weekly').hide();
			$('.btn-monthly').hide();

			if(offsetDays >= 7 * 26 * 2){
				$('.btn-monthly').show();
			}else if(offsetDays < 7 * 26 * 2 && offsetDays >= 7 * 26){
				$('.btn-weekly').show();
				$('.btn-monthly').show();				
			}else if(offsetDays < 7 * 26 && offsetDays >= 30){
				$('.btn-monthly').show();
				$('.btn-weekly').show();
				$('.btn-daily').show();				
			}else if(offsetDays < 30 && offsetDays >= 7){
				$('.btn-weekly').show();
				$('.btn-daily').show();				
			}else{
				$('.btn-daily').show();
			}
			_self.lg('offsetDays:' + offsetDays);
			
			return '{"reportType":' + (reportType?reportType:_self.durationType)
				+ ',"startTime":"' + strStarttime 
				+ '","endTime":"' + strEndtime
				+ '","channelIds":' + selectedChannelId
//				+ ',"orderby":"' + "createTime"
//				+ '","direction":"' + "asc"
//				+ '","pageSize":'+ 10000 + '}';
				+ ',"orderby":"' + (orderby?orderby:"startTime")
				+ '","direction":"' + (direction?direction:"asc")
				+ '","offset":' + (start?start:0)
				+ ',"pageSize":'+ (limit?limit:10000) + '}';
			
		},
		initChannelUserTable:function(self){
			_self.lg(' -- initChannelUserTable -- ');
		_self = self;
		if(_self.channelUserTable){
			_self.channelUserTable.fnDraw();
			return;
		}
//		$('#reportDataTable').hide();
		var setting = {
				"oLanguage":dataTableLan,
				
				"aLengthMenu":[[10, 25, 50], [10, 25, 50]],
				"bjQueryUI":true,
				"sPaginationType":"full_numbers",
				"bProcessing":true,
				"bPaginate":false,
				"bServerSide":true,
				"bFilter":false,
				"bInfo":false,
				"bStateSave":false,
				"bAutoWidth":true,
				"aaSorting":[[2, "desc"]],
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
					var postData = _self.jsonPostData(_self, start, 10000, orderby, direction, 1);
					_self.lg(postData);
					if(!postData){
						return;
					}
					$('.channelUserTable').show();
//						aoData.push( _self.jsonPostData(_self) );   
						$.ajaxInvoke( {
							type: "POST",
							url: _self.apiContextPath + "/cchannelreports/users?" + Math.random(),
							dataType: "json",
							data: postData, 
							success: function(content, status) {
								_self.lg(content);
								_self.orgsTableData = content.datas;
								content['sEcho']=sEcho;
								content['iDisplayStart']=content.paging.offset;
								content['iDisplayLength']=content.paging.limit;
								content['iTotalRecords']=content.paging.count;
								content['iTotalDisplayRecords']=content.paging.count;
								content['sortField']= orderby; //"createTime";
								content['sortType']= direction;

								var totalOrg = 0;
								var newOrg = 0;
								var totalUser = 0;
								var newUser = 0;
								$.each(content.datas, function(i,item){
									totalOrg += item.totalOrg;
									newOrg += item.newOrg;
									totalUser += item.totalUser;
									newUser += item.newUser;
								});
								if(content.datas.length > 0 ){
								   var newdata = {};
								   newdata.channelName = "所有渠道统计值";
								   newdata.channelId = "";
								   // newdata.totalOrg = totalOrg;
								   // newdata.newOrg = newOrg;
								   newdata.totalUser = totalUser;
								   newdata.newUser = newUser;
								   content.datas.push(newdata);
								}
								
								_self.lg(content);
								fnCallback(content);
							},
							error:function(data){
				            	_self.lg(data);
				            }
						});
					},  
				"nfDrawCallback":function(){
				},
				"fnInitComplete":function(){
				},
				"sAjaxDataProp":"datas",
				"aoColumns": [
				            {"sTitle": "渠道名称","mData" :"channelName", "bSortable": false},
				            { "sTitle":"渠道编号","mData" :"channelId",},
							{ "sTitle":"总用户数","mData" :"totalUser",},
							{"sTitle": "新增用户数","mData" :"newUser",},							
	                      ],
			};
		commonSetting4DataTable(setting);
		
	    var columnLength = setting.aoColumns.length; 
	    var columnWith = 100/columnLength;
	    
	    setting.aoColumns.forEach(function(value, index, array1){
	    	
	    	value["defaultValue"] = "";
	    	value["sWidth"] = columnWith + "%";
	    	value["sClass"] = "center";
	    });
	    
		_self.lg('channelUserTable');
		_self.channelUserTable = $('#channelUserTable').dataTable(setting);
	},
	initUserTable:function(self){
		_self = self;
		if(_self.userDataTable){
			_self.userDataTable.fnDraw();
			return;
		}
		_self.lg("initUserTable");
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
					$('.tableDatadiv').show();
						$.ajaxInvoke( {
							type: "post",
							//cuserreport?starttime=20150803&endtime=20150909&reportype=weekly&channelid=C100001
							url: _self.apiContextPath + "/cuserreport?" + Math.random(),
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
								fnCallback(content);
							},
							error:function(data){
				            	_self.lg(data);
				            }
						});
					},  
				"nfDrawCallback":function(){
					//$('#reportDataTable_paginate').css({"float":"right"});
				},
				"fnInitComplete":function(){
					//$('#reportDataTable_length').hide();
					//$('#reportDataTable').show();
					//$('#container2').css({height: $('#reportDataTable').height});
				},
				"sAjaxDataProp":"datas",
				"aoColumns": [
				              	{ "sTitle": "记录日期","mData" :"startTime","render": function(data){ return data?data.split(' ')[0]:'';}, },
								{ "sTitle": "总用户数","mData" :"totalUser",},
								{ "sTitle": "新增用户数","mData" :"newUser",},
					            { "sTitle":"活跃用户数","mData" :"activeUser",},
					            { "sTitle":"活跃老用户数","mData" :"activeOldUser",},
					            { "sTitle":"总学习时间(分钟)","mData" :"totalStudyHour",},
					            { "sTitle":"新增学习时间(分钟)","mData" :"newStudyHour",},
					            { "sTitle":"总企业号","mData" :"totalOrg",},
					            { "sTitle":"新增总企业号","mData" :"newOrg",},
					            { "sTitle":"课件学习人次","mData" :"newCourseBrowse",},
					            { "sTitle":"总订单","mData" :"totalOrder",},
					            { "sTitle":"新增订单","mData" :"newOrder",},
					            { "sTitle":"总金额","mData" :"totalOrderAmount",},
					            { "sTitle":"新增金额","mData" :"newOrderAmount",},
	                      ],
			};
		commonSetting4DataTable(setting);
		if(_self.thirdpart){
			var columns = setting.aoColumns;
			for(var i=5, n=columns.length; i<n; i++){
				columns[i].bVisible =false;
			}
		}
		_self.userDataTable = $('#userDataTable').dataTable(setting);
	},
	durationName:function(self){
		var _self = self;
		var name = '';
		switch(parseInt(_self.durationType)){
			case 1: // daily
				name = '日线';
				break;
			case 2://weekly
				name = '周线';
				break;
			case 3: // monthly
				name = '月线';
				break;
		}
		
		return name;
	},
	loadRetentionDataTable: function(self){
		var _self = self;
		$('.retentionTable').hide();
		var strStarttime = $('#reportStartTime').val();
		var strEndtime = $('#reportEndTime').val();
		var selectedChannelId = _self.getChannelIds();
		var postData = '{"startTime":"' + strStarttime 
			+ '","endTime":"' + strEndtime
			+ '","channelIds":' + selectedChannelId +'}';
		
			$.ajaxInvoke( {
				type: "POST",
				url: _self.apiContextPath + "/cretentionreports/search?" + Math.random(),
				dataType: "json",
				data: postData, 
				success: function(content, status) {
					_self.lg(content);
					
					var tbody = $('#retentionDataTable tbody');
					tbody.empty();
					for(i=0,n=content.datas.length; i<n; i++){
						var data = content.datas[i];
						var rates = data.retentionRates;
						var tr = $('<tr></tr>').append('<td>' + data.createDate + '</td><td>' + data.newUser + '</td><td>'  + rates[0] + '</td><td>' + rates[1] + '</td><td>' + rates[2] + '</td><td>' + rates[3] + '</td><td>' + rates[4] + '</td><td>' + rates[5] + '</td><td>' + rates[6] + '</td><td>' + rates[7] + '</td><td>' + rates[8] + '</td>');
						tbody.append(tr);
					}
					$('.retentionTable').show();
					// $('.tab-pane.employee').css({height:('' + (450 + $('#retentionDataTable').outerHeight()) + "px"), width:"100%"});
				},
				error:function(data){
	            	_self.lg(data);
	            }
			});
			

	},
	draw:function(self){
		var _self = self;
		_self.lg('Draw chart!');
		
		$('.btn-duration').removeClass('active');
		$('button[value=' + _self.durationType + ']').addClass('active');
		
        var chartsetting ={
	        	"employee":{field:["totalUser", "newUser"], title: "用户趋势"},
	        	"study":{field:["totalStudyHour", "newStudyHour"],title:"学习时间"},
	        	"order":{field:["totalOrder", "newOrder", "totalOrderAmount", "newOrderAmount"], title: "订单趋势"},
	        	"org":{field:["totalOrg", "newOrg"], title: "企业号趋势"},
	        	"browse":{field:["newCourseBrowse"], title: "课程点击趋势"},
        };

        var fields = chartsetting[_self.type].field;
        var categories = new Array();
        var serieses = [];
        for(var i=0, n=fields.length; i<n; i++){
        	serieses.push(new Array());
        }
        $.each(_self.reportData, function(i,item){
        	categories.push(item.startTime.split(' ')[0]);
	        for(var i=0, n=fields.length; i<n; i++){
	        	serieses[i].push(item[fields[i]]);
	        }
        });
        
        var options = _self.chartOptions();
        options.yAxis = [];
        options.series = [];

        for(var i=0, n=fields.length; i<n; i++){
        	options.yAxis.push({ // Tertiary yAxis
	            gridLineWidth: 0,
	            title: {
	                text: _self.columnNameArr[fields[i]],
	                style: {
	                    color: Highcharts.getOptions().colors[i]
	                }
	            },
	            labels: {
	                format: '{value} ',
	                style: {
	                    color: Highcharts.getOptions().colors[i]
	                }
	            },
	            opposite: true
	        });
        	
        	options.series.push({
	            name:  _self.columnNameArr[fields[i]],
	            type: 'spline',
	            yAxis: i,
	            data: serieses[i],
	            marker: {
	                enabled: false
	            },
	            dashStyle: 'shortdot',
	            tooltip: {
	                valueSuffix: ' '
	            }
	        });
	    }
        _self.lg("Draw chart:#highcharts-" + _self.type);
        options.xAxis[0].categories = categories;
		options.title.text = chartsetting[_self.type].title;
		$('.chartwidget').show();
	    $("#highcharts-" + _self.type).highcharts(options);
	    $('.btn-duration-group').show();
	},

	chartOptions:function(){
		var options = {
	        chart: {
	            zoomType: 'xy'
	        },
	        title: {
	            text: ''
	        },
	        /*
	        subtitle: {
	            text: 'Source: WorldClimate.com'
	        },
	        */
	        xAxis: [{
	            categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
	            crosshair: false
	        }],
	        tooltip: {
	            shared: true,
	            style:{
	            	width:'200px',
	            }
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'left',
	            x: 80,
	            verticalAlign: 'top',
	            y: 55,
	            floating: true,
	            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
	        },
	    };
		return options;
	},
}

$(document).ready(company.init());