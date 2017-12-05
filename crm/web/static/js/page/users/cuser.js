function progress(max, timeout) {
	var val = $("#progressbar").progressbar("value") || 0;

	$("#progressbar").progressbar("value", val + 1);

	if (val < max && val < 98) {
		setTimeout("progress(" + max + ");", timeout);
	}
}

var page = {
	apiContextPath : '/adminapi/v1',
	lg : function(msg) {
		if (console && console.log) {
			console.log(msg);
		}
	},
	channelData:null,
	progressbar : $("#progressbar"),
	progressLabel : $(".progress-label"),
	init : function() {
		var _self = this;
		_self.loadChannel('#channel', _self);
		
		_self.progressbar.progressbar({
			value : false,
			change : function() {
				_self.progressLabel.text(_self.progressbar.progressbar("value") + "%");
			},
			complete : function() {
				_self.progressLabel.text("完成！");
			}
		});
		
		if(window.location.href.indexOf('3cuser.jsp') != -1){
			_self.thirdpart = true;
			$('.div-4-3cuser').show();
		}else{
			$('.div-4-cuser').show();
		}


		$('#reportEndTime').unbind().bind('click', function() {
			WdatePicker({
				skin : 'twoer',
				dateFmt : 'yyyy-MM-dd',
				maxDate : '%y-%M-%d'
			});
			return false;
		});

		$('#reportStartTime').unbind().bind('click', function() {
			WdatePicker({
				skin : 'twoer',
				dateFmt : 'yyyy-MM-dd',
				maxDate : '%y-%M-%d'
			});
			return false;
		});
		$('#channelNameKey').unbind().bind('blur', function() {

			var validChannelNameKey = true;

			if ($(this).val()) {
				var selectedChannelId = [];

				var nameKey = $.trim($(this).val());
				$("#channel").children('option').each(function() {
					var val = $(this).val();
					var text = $(this).text();
					if (text.indexOf(nameKey) != -1) {
						selectedChannelId.push(val);
					}
				});
				if (selectedChannelId.length == 0) {
					validChannelNameKey = false;
				}
			}
			;

			if (!validChannelNameKey) {
				$("#channelNameKey").next().html("<font color='red'>不存在所查询渠道，请重新查询！</font>");
			} else {
				$("#channelNameKey").next().empty();
			}
		});

		$('.btn-search').unbind().bind('click', function() {
			_self.lg('click btn-search!');
			$(".userlist").hide();
			_self.initUserTable(_self);
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
	
	download_pagesData: function(self){
		var _self = self;
		var header = encodeURI("data:text/csv;charset=utf-8,\ufeff");
		if (window.navigator.msSaveOrOpenBlob) {
			header = "\ufeff";
		}
		
		var content = '';
		var trs = $('#userTable').find('TR');
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
		
		
		var cvsFileName = "用户数据-当前页";
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


		var columnNameArr = [ "手机", "邮箱",  "姓名", "企业名称", "来源", "最近登录", "创建时间" ];
		var columnDataName = [ "mobile", "email",  "fullName", "creator", "updater", "lastLoginTime", "createTime"];
		var cvsFileName = "用户报表数据";
		header += encodeURI(columnNameArr.join(",") + "\n");
		var csvContent = '';
		var maxLength = (window.chrome)? 5000:20000;
		var fetchLength = (window.chrome)? 500:1000;
		var postData = _self.jsonPostData(_self, 0, fetchLength, "createTime", "desc");
		
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
			url : _self.apiContextPath + "/orgusers/search?" + Math.random(),
			dataType : "json",
			data : JSON.stringify(postData),
			success : function(content, status) {
				_self.lg(content);
				_self.channelTableData = content.datas;

				content.datas.forEach(function(infoArray, index) {
					var newRow = '';
					for (i = 0, n = columnDataName.length; i < n; i++) {
						var v = infoArray[columnDataName[i]];
						if (columnDataName[i] == 'createTime' || columnDataName[i] == 'lastLoginTime') {
							v = (v && v.length > 19) ? v.substring(0, 19) : '';
						} else if (columnDataName[i] == 'sourceId') {
							v = $.trim(v);
							if (v && _self.channelData) {
								if (_self.channelData && _self.channelData[v]) {
									v = _self.channelData[v];
								}
							}
						} else if (columnDataName[i] == 'industryId') {
							v = $.trim(v);
							if (v && _self.industryData) {
								if (_self.industryData && _self.industryData[v]) {
									v = _self.industryData[v];
								}
							}
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
	loadChannel:function(id, self){
		var _self = self;
		var contextPath = _self.apiContextPath;
		// contextPath + "/dicts/mychannels?subtype=2&_=" + Math.random(),
		var url =  contextPath + "/dicts/mychannels?_=" + Math.random();
        $.ajaxInvoke({
            url: url,
            data: {},
            success: function(content, status) {
            	_self.lg(content);
            	_self.channelData = [];
				$.each(content.datas, function(i,item){
					_self.channelData[$.trim(item.channelId)] = item.channelName;
				});
				
            	$.each(content.datas, function(i, item){
            		//_self.lg(item);
            		$(id).append("<option value='"+ item.channelId +"'>" + item.channelName + "</option>");
            	})
            },
            type:'get'
        });
	},
	jsonPostData : function(self, start, limit, orderby, direction) {
		var _self = self;

		var strEndTime = $('#reportEndTime').val();
		var strStartTime = $('#reportStartTime').val();

		var selectedChannelId = [];
		
		if(!$("#channel").children('option:selected').val()){
			
			$("#channel").children('option').each(function(){
				var val = $(this).val();
				_self.lg(val);
				if(val){
					selectedChannelId.push(val);
				}
			}
			)
		}else{
			selectedChannelId.push($("#channel").children('option:selected').val());
		}
		
		var validChannelNameKey = true;
		if ($("#channelNameKey").val()) {
			selectedChannelId = [];
			var nameKey = $.trim($("#channelNameKey").val());
			$("#channel").children('option').each(function() {
				var val = $(this).val();
				var text = $(this).text();
				if (text.indexOf(nameKey) != -1) {
					selectedChannelId.push(val);
				}
			});

			if (selectedChannelId.length == 0) {
				validChannelNameKey = false;
			}

		}

		if (!validChannelNameKey) {
			$('#userTable_processing').hide();
			$("#channelNameKey").next().html("<font color='red'>不存在所查询渠道，请重新查询！</font>");
			return null;
		} else {
			$("#channelNameKey").next().empty();
		}
		return {
			"sourceId" : selectedChannelId,
			"usertype": $('#usertype').val(),
			"orderby" : (orderby ? orderby : "createTime"),
			"direction" : (direction ? direction : "asc"),
			"pageSize" : (limit ? limit : 10000),
			"limit" : (limit ? limit : 10000),
			"nameLike" : $('#name').val(),
			"offset" : (start ? start : 0),
			"startTime" : strStartTime,
			"endTime" : strEndTime
		};
	},
	tableSetting : function(_self, aoColumns) {
		var setting = {
			"oLanguage" : dataTableLan,

			"aLengthMenu" : [ [ 10, 25, 50 ], [ 10, 25, 50 ] ],
			"bjQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bProcessing" : true,
			"bPaginate" : true,
			"bServerSide" : true,
			"bFilter" : false,
			"bInfo" : true,
			"bStateSave" : false,
			"bAutoWidth" : true,
			"aaSorting" : [ [ 4, "desc" ] ],
			"fnServerData" : function(sSource, aoData, fnCallback) {
				_self.lg("aoData:" + JSON.stringify(aoData));

				var start = 0;
				var length = 10;
				var columns = null;
				var sEcho = 1;
				var order = [];
				$.each(aoData, function(i, item) {
					var name = item['name'];
					var value = item['value'];
					if (name == "start") {
						start = value;
					} else if (name == 'length') {
						length = value;
					} else if (name == 'draw') {
						sEcho = value;
					} else if (name == 'order') {
						order = value;
					} else if (name == 'columns') {
						columns = value;
					}
				});

				var orderby = columns[order[0].column].data;
				var direction = order[0].dir;

				var postData = _self.jsonPostData(_self, start, length, orderby, direction);
				if (!postData) {
					return;
				}
				var newUrl = _self.apiContextPath + "/orgusers/search?" + Math.random();
				$('.userlist').show();
				$.ajaxInvoke({
					type : 'post',
					url : newUrl,
					dataType : "json",
					data : JSON.stringify(postData),
					success : function(content, status) {
						_self.lg(content);
						content['sEcho'] = sEcho;
						content['iDisplayStart'] = content.paging.offset;
						content['iDisplayLength'] = content.paging.limit;
						content['iTotalRecords'] = content.paging.count;
						content['iTotalDisplayRecords'] = content.paging.count;
						content['sortField'] = orderby; // "createTime";
						content['sortType'] = direction;
						_self.lg(content);
						fnCallback(content);
					},
					error : function(data) {
						_self.lg(data);
					}
				});
			},

			"sAjaxDataProp" : "datas",
			"aoColumns" : aoColumns,
		};

		commonSetting4DataTable(setting);
		$.each(setting.aoColumns, function(i, item) {
			if (item["sTitle"] == "操作") {
				item["bSortable"] = false;
			}
		});

		return setting;
	},
	initUserTable : function(self) {
		_self = self;
		_self.lg(' == initUserTable ');
		if (_self.userTable) {
			_self.userTable.fnDraw();
			return;
		}

		var aoColumns = [

		{
			"sTitle" : "手机",
			"mData" : "mobile",
		}, {
			"sTitle" : "邮箱",
			"mData" : "email",
		// }, {
		// 	"sTitle" : "昵称",
		// 	"mData" : "nickName",
		}, {
			"sTitle" : "姓名",
			"mData" : "fullName",
		}, {
			"sTitle" : "企业名称",
			// "bSortable" : false,
			"mData" : "creator",
			"render" : function(data) {
				return data ? data:"无";
			}
		}, {
			"sTitle" : "来源",
			"mData" : "updater",
			"render" : function(data) {
				data = $.trim(data);
				if (data && _self.channelData) {
					if (_self.channelData && _self.channelData[data]) {

						return _self.channelData[data];
					}
				}

				return data;
			},
		}, {
			"sTitle" : "最近登录",
			"mData" : "lastLoginTime",
			"render" : function(data) {
				return (data && data.length > 19) ? data.substring(0, 19) : '';
			},
		}, {
			"sTitle" : "创建时间",
			"mData" : "createTime",
			"render" : function(data) {
				return (data && data.length > 19) ? data.substring(0, 19) : '';
			},

		}, ];

		var setting = _self.tableSetting(_self, aoColumns);

		setting.fnDrawCallback = function() {
			_self.lg('nfDrawCallback');
		};
		_self.lg('init userTable');
		_self.userTable = $('#userTable').dataTable(setting);
	},
}
$(document).ready(page.init());