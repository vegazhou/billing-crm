var company = {
		apiContextPath:'/adminapi/v1',
		lg:function(msg){
			if(console && console.log){
				console.log(msg);
			}
		},
		durationType:1,
		channelType:1,
		type:'company',
		reportData:null,
		retentionData:null,
		dataTable:null,
		userDataTable:null,
		field:['endTime','totalOrg','newOrg'],
		fieldName:['记录日期','总企业数','新增企业数'],
		init:function(){
			var _self = this;
//			$('section').css({"margin-bottom":"0px;"});
//			$('input').css({"margin-top":"5px","padding-bottom":"0px"});
//			$('td').css({"text-align":"center"});
//			$('form section').addClass('no-padding no-margin');
			
			$('.highcharts-container').before('<div style="float:right;display:none;" id="action_optoins"><div style="position:absolute;right:150px;margin-top:5px;"><a class="downloadReport" href="javascript:void(0);">下载报表数据</a></div>'
					+ '<div class="btn-group"><button class="btn btn-sm btn-primary btn-duration btn-daily" value="1">日</button>'
					+ '<button class="btn btn-sm btn-primary btn-duration btn-weekly" value="2">周</button>'
					+ '<button class="btn btn-sm btn-primary btn-duration btn-monthly" value="3">月</button>'
					+ '</div></div>');
			
			
			$('.downloadReport').unbind().bind('click', function(){
				_self.lg('download report');
				_self.downloadReport(_self);
			});
					
						
			loadChannel('#channel', _self, _self.channelType);
			
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
			
			$('.btn-search').unbind().bind('click', function(){


				var postData = _self.jsonPostData(_self);
				if(!postData){
					return;
				}
					_self.initTable(_self);
					_self.initUserTable(_self);
			        _self.loadRetentionDataTable(_self);

			});
			var now = new Date();
			// now = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 1);
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
						_self.type = id;
						_self.lg('search type:' + id);
						if(id == 'company' || id == "employee"){
							_self.draw(_self);
						}else{
							_self.lg('initTable');
							_self.initTable(_self);
						}
						if(id =='employee'){
							$('.tab-pane.employee').css({height:('' + (550 + $('#retentionDataTable').outerHeight()) + "px"), width:"100%"});
							//div.jarviswidget.chartwidget.no-margin
							
						}
				});
			
//			$('.highcharts-container').css({height:"350px",width:"100%"});
//			$('.tab-pane').css({height:"400px",width:"100%"});
			$('#highcharts-company').css({height:"350px",width:"100%"});
			$('.widget-body.table').css({'margin-top':'100px'});
			$('select').addClass('form-control');
			$('section').addClass('no-padding');
			$('input').parent('label').addClass('input');
			//$('.btn-search').click();
			

			$('.download_userDataTable').unbind().bind('click', function(){
				_self.lg('download channelTableData');
				_self.download_channelTableData(_self);
			
			});
			

			$('.download_orgsTableData').unbind().bind('click', function(){
				_self.lg('download orgsTableData');
				download_pagesData2CSV('download_orgsTableData', '企业用户报表', _self);
			});
		},
		downloadReport : function(self) {
			var _self = self;
//			var data = [ displayData["radar_chart"]["r_label"],
//					displayData["radar_chart"]["r_default"] ];
			var csvContent = "data:text/csv;charset=utf-8,\ufeff";
			if (window.navigator.msSaveOrOpenBlob) {
				csvContent = "\ufeff";
			}
			
			var data = _self.reportData;
			if(!data){
				return;
			}

			csvContent += _self.fieldName.join(",") + "\n";
			data.forEach(function(infoArray, index){
				var newRow = '';
				$.each(_self.field, function(i,item){
					if(i!=0){
						newRow += ',';
					}
					
					if(item == 'endTime'){
						newRow += ( infoArray[item]?infoArray[item].split(' ')[0]:'' );
					}else if(item == 'totalOrg' || item == 'newOrg'){
						newRow += infoArray[item]?infoArray[item]:0;
					}
				});
				
				csvContent += newRow+ "\n";
			}); 

			
			if (window.navigator.msSaveOrOpenBlob) {
				// if browser is IE
				var blob = new Blob([ decodeURIComponent(encodeURI(csvContent)) ],
						{
							type : "text/csv;charset=utf-8;"
						});
				navigator.msSaveBlob(blob, '企业报表.csv');
			} else {
				var encodedUri = encodeURI(csvContent);
				var link = document.createElement("a");
				link.setAttribute("href", encodedUri);
				link.setAttribute("download", "企业报表.csv");
				document.body.appendChild(link);
				link.click();
			}
		},
		setSearchDuration:function(offset){
			var now = new Date();
			var	endTime = now.getFullYear() + '-' + (now.getMonth()<9 ? ('0' + (now.getMonth() + 1)): (now.getMonth() + 1)) + '-' + (now.getDate()<=9 ? ('0' + now.getDate()): now.getDate());
			
			now = new Date(now.getFullYear(), now.getMonth(), now.getDate() - offset);
			var	startTime = now.getFullYear() + '-' + (now.getMonth()<9 ? ('0' + (now.getMonth() + 1)): (now.getMonth() + 1)) + '-' + (now.getDate()<=9 ? ('0' + now.getDate()): now.getDate());
			
			$('#reportStartTime').val(startTime);
			$('#reportEndTime').val(endTime);
		},
		loadChannel:function(){
			var _self = this;
			var contextPath = _self.apiContextPath;
	        $.ajaxInvoke({
	            url: contextPath + "/dicts/mychannels?subtype=" + _self.channelType + "&"  + Math.random(),
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
		loadRetentionDataTable: function(self){
			var _self = self;
			var strStarttime = $('#reportStartTime').val();
			var strEndtime = $('#reportEndTime').val();
			var selectedChannelId = _self.getChannelIds();
			var postData = '{"startTime":"' + strStarttime 
			+ '","endTime":"' + strEndtime
			+ '","channelIds":' + selectedChannelId +'}';
			
				$.ajaxInvoke( {
					type: "POST",
					url: _self.apiContextPath + "/retentionreports/search?" + Math.random(),
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
//							var tds = $(trs[i]).find('td');
							//_self.lg(tds);
							
							
//							$.each(tds, function(i, item){
//								//_self.lg("td value:" + $(item).text());
//							});
						}
						$('.tab-pane.employee').css({height:('' + (550 + $('#retentionDataTable').outerHeight()) + "px"), width:"100%"});
					},
					error:function(data){
		            	_self.lg(data);
		            }
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
		loadIndustry:function(industryId, divId, _this){
			var _self =_this;
			if(!_self){
				_self = this;
			}
			_self.lg('divId:'+divId +';industryId:' + industryId);
			var contextPath = _self.apiContextPath;
	        $.ajaxInvoke({
	            url: contextPath + "/industries/" + industryId + "/subindustries",
	            data: {},
	            success: function(content, status) {
	            	_self.lg(content);
	            	$.each(content.datas, function(i, item){
	            		//_self.lg(item);
	            		$('#' + divId).append("<option value='"+ item.id +"'>" + item.name + "</option>");
	            	})
	            },
	            type:'get'
	        });
		},
		jsonPostData:function(self, start, limit, orderby, direction){
			var _self = self;
			var selectedChannelIds = [];
			var selectedChannelId = $("#channel").children('option:selected').val();
			if(selectedChannelId == ''){
				$("#channel").children('option').each(function(){
					var val = $(this).val();
					if(val != '' ){
						//selectedChannelId = selectedChannelId + ',"' + val + '"'  ;
						selectedChannelIds.push(val);
					}
				}
				)
				
//				selectedChannelId = selectedChannelId.substring(1);
			}else{
//				selectedChannelId = '"' + selectedChannelId + '"';
				selectedChannelIds = [selectedChannelId];
			}
			
			
			
			var reportType = _self.durationType;
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
			$('.jarviswidget').show();
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

			return {"reportType":reportType,
				"startTime": strStartTime, 
				"endTime":strEndtime,
				"channelIds":selectedChannelIds,
				"orderby":(orderby?orderby:"startTime"),
				"direction":(direction?direction:"asc"),
				"offset":(start?start:0),
				"pageSize":(limit?limit:10000)};
		},

		download_orgTableData : function(self) {
			var _self = self;
			var csvContent = "data:text/csv;charset=utf-8,\ufeff";
			if (window.navigator.msSaveOrOpenBlob) {
				csvContent = "\ufeff";
			}
			
			var postData = _self.jsonPostData(_self, 0, 10000000);
			_self.lg(postData);
			if(!postData){
				return;
			}
			var actionType = 'post';
			postData["sourceId"] = postData['channelIds'];
			postData["startTime"] = "";
			postData["endTime"] = "";
			postData["orderby"] = "createTime";
//				aoData.push( _self.jsonPostData(_self) );   
				$.ajaxInvoke( {
					type: "POST",
					url: _self.apiContextPath + "/orgs/search?" + Math.random(),
					dataType: "json",
					data: JSON.stringify(postData), 
					success: function(content, status) {
						_self.lg(content);
						_self.channelTableData = content.datas;
						var columnNameArr = ["企业名称","企业人数","渠道名称","创建时间"];
						var columnDataName = ["orgName","userCount","sourceId","createTime"];
						var cvsFileName = "报表数据1.csv";
						csvContent += columnNameArr.join(",") + "\n";
						content.datas.forEach(function(infoArray, index){
							var newRow = '';
							for(i=0, n=columnDataName.length; i<n; i++){
								var v = infoArray[columnDataName[i]];
								if(columnDataName[i] == 'createTime'){
									v = v.split(' ')[0];
								}else if(columnDataName[i] == 'sourceId'){
									v = $.trim(v);
									if(v && _self.channelData){
										if(_self.channelData && _self.channelData[v]){
											v = _self.channelData[v]; 
										}
									}
								}
								
								
								if(i!=0){
									newRow += ',';
								}
								newRow += v;
							}
							
							csvContent += newRow+ "\n";
						}); 

						
						if (window.navigator.msSaveOrOpenBlob) {
							// if browser is IE
							var blob = new Blob([ decodeURIComponent(encodeURI(csvContent)) ],
									{
										type : "text/csv;charset=utf-8;"
									});
							navigator.msSaveBlob(blob, cvsFileName);
						} else {
							var encodedUri = encodeURI(csvContent);
							var link = document.createElement("a");
							link.setAttribute("href", encodedUri);
							link.setAttribute("download", cvsFileName);
							document.body.appendChild(link);
							link.click();
						}					
					},
					error:function(data){
		            	_self.lg(data);
		            }
				});
		}, 
		download_channelTableData : function(self) {
			var _self = self;
			var csvContent = "data:text/csv;charset=utf-8,\ufeff";
			if (window.navigator.msSaveOrOpenBlob) {
				csvContent = "\ufeff";
			}
			
			var postData = _self.jsonPostData(_self, 0, 10000000);
			_self.lg(postData);
			if(!postData){
				return;
			}
//				aoData.push( _self.jsonPostData(_self) );   
				$.ajaxInvoke( {
					type: "POST",
					url: _self.apiContextPath + "/channelreports/search?" + Math.random(),
					dataType: "json",
					data: JSON.stringify(postData), 
					success: function(content, status) {
						_self.lg(content);
						_self.channelTableData = content;
						var columnNameArr = ["记录日期","总企业数","新企业数","总用户数","新用户数"];
						var columnDataName = ["startTime","totalOrg","newOrg","totalUser","newUser"];
						var cvsFileName = "报表数据1.csv";
						csvContent += columnNameArr.join(",") + "\n";
						content.datas.forEach(function(infoArray, index){
							var newRow = '';
							for(i=0, n=columnDataName.length; i<n; i++){
								if(i!=0){
									newRow += ',';
									newRow += infoArray[columnDataName[i]];
								}else{
									newRow += infoArray[columnDataName[i]].split(' ')[0];
								}
								
							}
							
							csvContent += newRow+ "\n";
						}); 

						
						if (window.navigator.msSaveOrOpenBlob) {
							// if browser is IE
							var blob = new Blob([ decodeURIComponent(encodeURI(csvContent)) ],
									{
										type : "text/csv;charset=utf-8;"
									});
							navigator.msSaveBlob(blob, cvsFileName);
						} else {
							var encodedUri = encodeURI(csvContent);
							var link = document.createElement("a");
							link.setAttribute("href", encodedUri);
							link.setAttribute("download", cvsFileName);
							document.body.appendChild(link);
							link.click();
						}					
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
			
			/*
	        "startTime": "2015-04-07 00:00:00.0",
	        "endTime": "2015-04-08 00:00:00.0",
	        "totalOrg": 33114,
	        "newOrg": 0,
	        "totalUser": 0,
	        "newUser": 0,
	        "activeUser": 0,
	        "totalOrderAmount": 0,
	        "newOrderAmount": 0,
	        "totalOrder": 0,
	        "newOrder": 0,
	        "totalStudyplan": 0,
	        "newStudyplan": 0,
	        "totalStudyHour": 0,
	        "newStudyHour": 0,
	        "totalKnowledge": 1,
	        "newKnowledge": 0
	        */
	        var fieldsName={
	        		"company":["totalOrg", "newOrg"],
		        	"employee":["totalUser", "newUser"],
		        	"deal":["totalOrderAmount", "newOrderAmount"],
		        	"study":["totalStudyplan", "newStudyplan"],
		        	"content":["totalKnowledge", "newKnowledge"],
	        };
	        
	        var isEmployee =  (_self.type == 'employee');
	        var categories = new Array();
	        var series1 = new Array();
	        var series2 = new Array();
	        var series3 = new Array();
	        var series4 = new Array();
	        $.each(_self.reportData, function(i,item){
	        	categories.push(item.startTime.split(' ')[0]);
	        	series1.push(item[fieldsName[_self.type][0]]);
	        	series2.push(item[fieldsName[_self.type][1]]);
	        	if(isEmployee){
	        		series3.push(item['activeUser']);
	        		series4.push(item['retentionRate']?item['retentionRate']*100:0.00);
	        	}
	        });
	        
//	        
//	        if(isEmployee){
//	        	var tempRetention = new Array();
//		        $.each(_self.retentionData, function(i, item){
//		        	tempRetention[item.startTime.split(' ')[0]] = item.retentionRate*100;
//		        });
//		        
//		        $.each(categories, function(i, item){
//		        	if(tempRetention[item]){
//		        		series4.push(tempRetention[item]);
//		        	}else{
//		        		series4.push(0);
//		        	}
//		        });
//			}
		    
	        _self.lg('categories:' + categories);
	        _self.lg('series1:' + series1);
	        _self.lg('series2:' + series2);
	       
	        var options = _self.chartOptions();
	        
	        _self.lg("Draw chart:#highcharts-" + _self.type);
	        options.xAxis[0].categories = categories;
	        if( _self.type == "company"){
	        	_self.lg('company:');
		        options.title.text = '渠道企业增长趋势';
		        options.yAxis[0].title.text = '企业新增数';
		        options.yAxis[1].title.text = '企业总数';
			} else if (_self.type == "employee") {
				_self.lg('employee:');
				options.title.text = '渠道用户增长趋势';
				options.yAxis[0].title.text = '用户新增数';
				options.yAxis[1].title.text = '用户总数';
			} else if (_self.type == "deal") {
				_self.lg('deal:');
				options.title.text = '渠道交易增长趋势';
				options.yAxis[0].title.text = '交易新增数';
				options.yAxis[1].title.text = '交易总数';
			} else if (_self.type == "study") {
				_self.lg('study:');
				options.title.text = '渠道学习增长趋势';
				options.yAxis[0].title.text = '学习新增数';
				options.yAxis[1].title.text = '学习总数';
			} else if (_self.type == "content") {
				_self.lg('content:');
				options.title.text = '渠道内容增长趋势';
				options.yAxis[0].title.text = '内容新增数';
				options.yAxis[1].title.text = '内容总数';
			}
	        options.series[0].name = options.yAxis[1].title.text;
	        options.series[1].name = options.yAxis[0].title.text;
	        options.series[0].data = series1;
	        options.series[1].data = series2;
	        
			options.title.text = options.title.text + '(' + _self.durationName(_self) + ')';
			
			
			$('.highcharts-container').show();
			
		    $("#highcharts-" + _self.type).highcharts(options);
		    $('.btn-duration-group').show();
		},

		chartOptions:function(){
			var options = {
		        chart: {
		            zoomType: 'xy'
		        },
		        title: {
		            text: '渠道企业增长趋势'
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
		        yAxis: [{ // Primary yAxis
		        	//gridLineWidth: 0, // for line only
		            labels: {
		                format: '{value} ',
		                style: {
		                    color: Highcharts.getOptions().colors[1]
		                }
		            },
		            title: {
		                text: '企业新增数',
		                style: {
		                    color: Highcharts.getOptions().colors[1]
		                }
		            },
		            opposite: true
		
		        }, { // Secondary yAxis
		            gridLineWidth: 0,
		            title: {
		                text: '企业总数',
		                style: {
		                    color: Highcharts.getOptions().colors[0]
		                }
		            },
		            labels: {
		                format: '{value} ',
		                style: {
		                    color: Highcharts.getOptions().colors[0]
		                }
		            },
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
		        series: [{
		            name: '企业总数',
		            //type: 'column',
		            type: 'spline',
		            yAxis: 1,
		            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
		            tooltip: {
		                valueSuffix: ' '
		            }
		
		        },
		        {
		            name: '企业新增数',
		            type: 'spline',
		            //yAxis: 2,
		            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
		            tooltip: {
		                valueSuffix: ' '
		            }
		        }]
		    };
			return options;
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
	initTable:function(self){
		_self = self;
		_self.lg("init org table");
		if(_self.dataTable){
			_self.dataTable.fnDraw();
			return;
		}
		
		var aoColumns = [
		                 	{ "sTitle": "企业名称","mData" :"orgName","render" : function(data, style , obj){
								return '<a href="javascript:void(0);" orgid="' + obj.orgId + '" class="btn-viewOrg" >' + data + '</a>';
							},},

							{ "sTitle": "企业人数","mData" :"userCount","render": function(data){
								return data?data:'0';
							},},
							{ "sTitle": "渠道名称","mData" :"sourceId","render": function(data){
								data = $.trim(data);
								if(data && _self.channelData){
									if(_self.channelData && _self.channelData[data]){
										
										return _self.channelData[data]; 
									}
								}
								
								return data;
							},},
							{ "sTitle": "创建时间","mData" :"createTime","render" : function(data){
								return (data && data.length > 19)?data.substring(0,19):'';
							},},
                   ];
		var url = _self.apiContextPath + "/orgs/search";
		_self.lg("init org table. url:" + url);
		var setting = _self.tableSetting(_self, aoColumns,url);
		commonSetting4DataTable(setting);
		setting.fnDrawCallback = function(){
		};
		
		_self.dataTable = $('#reportDataTable').dataTable(setting);
	},
	
	tableSetting: function(_self, aoColumns, url){
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
					
					var postData = _self.jsonPostData(_self, start, length, orderby, direction);
					
					/*
					return '{"reportType":' + reportType
					+ ',"startTime":"' + strStartTime 
					+ '","endTime":"' + strEndtime
					+ '","channelIds":' + selectedChannelId
					+ ',"orderby":"' + (orderby?orderby:"startTime")
					+ '","direction":"' + (direction?direction:"asc")
					+ '","offset":' + (start?start:0)
					+ ',"pageSize":'+ (limit?limit:10000) + '}';
					
					{"sourceId":"360-001","industryId":[],"orderby":"createTime","direction":"desc","pageSize":10,"limit":10,"nameLike":"","offset":0,"startTime":"","endTime":""}
					
*/
					if(!postData){
						return;
					}
					
					var ajaxUrl = url + "?" + Math.random();;
					var actionType = 'post';
					postData["sourceId"] = postData['channelIds'];
					postData["startTime"] = "";
//					postData["endTime"] = "";
					
					_self.lg(postData);
					
						$.ajaxInvoke( {
							type: actionType,
							url: ajaxUrl,
							dataType: "json",
							data:JSON.stringify(postData), 
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
	initUserTable:function(self){
		_self = self;
		if(_self.userDataTable){
			_self.userDataTable.fnDraw();
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
//						aoData.push( _self.jsonPostData(_self) );   
						$.ajaxInvoke( {
							type: "POST",
							url: _self.apiContextPath + "/channelreports/3rdsearch?" + Math.random(),
							dataType: "json",
							data: JSON.stringify(postData), 
							success: function(content, status) {
								_self.lg(content);

								var now = new Date();
								var	maxTime = now.getFullYear() + '-' + (now.getMonth()<9 ? ('0' + (now.getMonth() + 1)): (now.getMonth() + 1)) + '-' + now.getDate();
			
								var add1 = 0;
			  					if($('#reportEndTime').val() == maxTime){
									add1 = 1;
			  					}
				            	_self.reportData = [];
				            	for(var i = content.datas.length-1; i>=add1; i--){
				            		_self.reportData.push(content.datas[i]);
				            	}
				            	_self.draw(_self);

								content['sEcho']=sEcho;
								content['iDisplayStart']=content.paging.offset;
								content['iDisplayLength']=content.paging.limit;
								content['iTotalRecords']=content.paging.count + add1;
								content['iTotalDisplayRecords']=content.paging.count + add1;
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
				              	{ "sTitle": "记录日期","mData" :"startTime","bSortable":false, "render": function(data){ return data?data.split(' ')[0]:'';}, },
								{ "sTitle": "总企业数","mData" :"totalOrg","bSortable":false, },
								{ "sTitle": "新企业数","mData" :"newOrg","bSortable":false, },
					            { "sTitle":"总用户数","mData" :"totalUser","bSortable":false, },
					            { "sTitle":"新用户数","mData" :"newUser","bSortable":false, },
	                      ],
			};
		commonSetting4DataTable(setting);
		_self.userDataTable = $('#userDataTable').dataTable(setting);
	},
}

$(document).ready(company.init());