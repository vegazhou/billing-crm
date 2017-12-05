var page = {
		apiContextPath:'/adminapi/v1',
		lg:function(msg){

		},
		durationType:1,
		type:'employee',
		reportData:null,
		industryData:null,
		channelData:null,
		init:function(){
			var _self = this;
//			$('section').css({"margin-bottom":"0px;"});
//			$('input').css({"margin-top":"5px","padding-bottom":"0px"});
//			$('td').css({"text-align":"center"});
//			$('form section').addClass('no-padding no-margin');
			
			$('.highcharts-container').before('<div style="float:right;display:none;" class="div_action_options"><div style="position:absolute;right:150px;margin-top:5px;"><a class="downloadReport" href="javascript:void(0);">下载报表数据</a></div>'
					+ '<div class="btn-group"><button class="btn btn-sm btn-primary btn-duration btn-daily" value="1">日</button>'
					+ '<button class="btn btn-sm btn-primary btn-duration btn-weekly" value="2">周</button>'
					+ '<button class="btn btn-sm btn-primary btn-duration btn-monthly" value="3">月</button>'
					+ '</div></div>');
			
			
			$('.downloadReport').unbind().bind('click', function(){
				_self.lg('download report');
				_self.downloadReport(_self);
			});
					
						
			loadChannel('#channel', _self);
			loadIndustry(0, '#industry');
			
	        $.ajaxInvoke({
	            url: _self.apiContextPath + "/industries/?" + Math.random(),
	            data: {},
	            success: function(content, status) {
	            	_self.industryData = [];
					$.each(content.datas, function(i,item){
						_self.industryData[$.trim(item.id)] = item.name;
						if(item.datas){
							$.each(item.datas, function(i2,item2){
								_self.industryData[$.trim(item2.id)] = item2.name;
							});
						}
					});
					_self.lg(_self.industryData);
	            },
	            type:'get'
	        });
	        
//			$('#channel').bind('change', function(){});
			$('#industry').bind('change', function(){
				var selectedIndustryId = $(this).children('option:selected').val();
				_self.lg(selectedIndustryId);
				if(selectedIndustryId == ''){
					$('.subin-col').hide();
					$('#subindustry').empty();
				}else{
					$('#subindustry').empty();
					$('#subindustry').append("<option value=''>全部</option>");
					loadIndustry(selectedIndustryId, '#subindustry');
					$('.subin-col').show();
				}
			});

			
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
					if(_self.type == 'order'){
						_self.initOrderTable(_self);
						return;
					}
					var postData = _self.jsonPostData(_self);
					_self.lg('postData:' + postData);
					if(!postData){
						return;
					}
					_self.lg('postData:' + postData);
			        $.ajaxInvoke({
			            url: _self.apiContextPath + "/channelreports/search?" + Math.random(),
			            data: postData,
			            success: function(content, status) {
			            	_self.lg(content);
			            	_self.reportData = content.datas;
			            	
			            	_self.draw(_self);
//			            	$('#highcharts-employee').css({"height":""});
			            	/*
			            	$.each(content.datas, function(i, item){
			            		_self.lg(item);
			            	});
			            	*/
			            },
			            dataType:'json',
			            type:'post',
			            error:function(data){
			            	_self.lg(data);
			            }
			        });
			        

			});
			$('#reportEndTime').unbind().bind('click',  function() {
			    WdatePicker({
			        skin: 'twoer',
			        dateFmt: 'yyyy-MM-dd',
			        maxDate:'%y-%M-%d'
			    });
			    return false;
			});

			$('#reportStartTime').unbind().bind('click',  function() {
			    WdatePicker({
			        skin: 'twoer',
			        dateFmt: 'yyyy-MM-dd',
			        maxDate:'%y-%M-%d'
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
						if(id != 'order'){
							_self.draw(_self);
						}else{
							_self.lg('initOrderTable');
							_self.initOrderTable(_self);
						}
						
				});
			
//			$('.highcharts-container').css({height:"350px",width:"100%"});
//			$('.tab-pane').css({height:"400px",width:"100%"});
			$('.highcharts-container').css({height:"350px",width:"100%"});
			$('.widget-body.table').css({'margin-top':'85px'});
			$('select').addClass('form-control');
			$('section').addClass('no-padding');
			$('input').parent('label').addClass('input');
			//$('.btn-search').click();
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
			var field = ['endTime','totalOrg','newOrg','totalUser','newUser','activeUser','retentionRate','totalKnowledge','newKnowledge','totalOrder','newOrder','totalOrderAmount','newOrderAmount','totalStudyHour','newStudyHour','totalStudyplan','newStudyplan'];
			var fieldName = ['记录日期','总企业数','新企业数','总用户数', '新用户数','活跃用户数','用户留存率','总知识','新知识','总订单','新订单','总订单金额','新订单金额','总学习小时数','新学习小时数','总学习计划数','新学习计划数'];

			csvContent += fieldName.join(",") + "\n";
			data.forEach(function(infoArray, index){
				var newRow = '';
				$.each(field, function(i,item){
					if(i!=0){
						newRow += ',';
					}
					
					if(item == 'endTime'){
						newRow += ( infoArray[item]?infoArray[item].split(' ')[0]:'' );
					}else{
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
				navigator.msSaveBlob(blob, '运营管理分析报表.csv');
			} else {
				var encodedUri = encodeURI(csvContent);
				var link = document.createElement("a");
				link.setAttribute("href", encodedUri);
				link.setAttribute("download", "运营管理分析报表.csv");
				document.body.appendChild(link);
				link.click();
			}
		},
		setSearchDuration:function(offset){
			var now = new Date();
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
	            url: contextPath + "/dicts/mychannels?" + Math.random(),
	            data: {},
	            success: function(content, status) {
	            	_self.lg(content);
	            	_self.channelData = [];
					$.each(content.datas, function(i,item){
						_self.channelData[$.trim(item.channelId)] = item.channelName;
					});
					
	            	$.each(content.datas, function(i, item){
	            		//_self.lg(item);
	            		$('#channel').append("<option value='"+ item.channelId +"'>" + item.channelName + "</option>");
	            	})
	            },
	            type:'get'
	        });
		},
		loadIndustry:function(industryId, divId, _this){
			var _self =_this;
			if(!_self){
				_self = this;
			}
			_self.lg('divId:'+divId +';industryId:' + industryId);
			var contextPath = _self.apiContextPath;
	        $.ajaxInvoke({
	            url: contextPath + "/industries/" + industryId + "/subindustries?" + Math.random(),
	            data: {},
	            success: function(content, status) {
	            	_self.lg(content);
	            	/*
	            	var index = 0;
	            	if(industryId != 0){
	            		index = 1;
	            	}	            	
	            	_self.industryData[index] = [];
	            	
					$.each(content.datas, function(i,item){
						_self.industryData[index][$.trim(item.id)] = item.name;
					});
					*/
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
			var selectedChannelId = $("#channel").children('option:selected').val();
			var selectedIndustryId = $("#industry").children('option:selected').val();
			
			var selectedSubIndustryId = '';
			if(selectedIndustryId){
				selectedSubIndustryId = $("#subindustry").children('option:selected').val();
				if(selectedSubIndustryId){
					selectedIndustryId = selectedSubIndustryId;
				}
			}
			
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
			
			selectedChannelId = '[' + selectedChannelId + ']';
			if(selectedIndustryId){
				selectedIndustryId = '["' + selectedIndustryId + '"]';
			}else{
				selectedIndustryId = '[]';
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

			return '{"reportType":' + reportType
				+ ',"startTime":"' + strStartTime 
				+ '","endTime":"' + strEndtime
				+ '","channelIds":' + selectedChannelId
				+ ',"industryIds":' + selectedIndustryId
				+ ',"orderby":"' + (orderby?orderby:"startTime")
				+ '","direction":"' + (direction?direction:"asc")
				+ '","offset":' + (start?start:0)
				+ ',"pageSize":'+ (limit?limit:10000) + '}';
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
		        	"employee":["totalUser", "newUser", 'activeUser', 'retentionRate'],
		        	"deal":["totalOrderAmount", "newOrderAmount", 'totalOrder', 'newOrder'],
		        	"study":["totalStudyplan", "newStudyplan",'totalStudyHour','newStudyHour'],
		        	"knowledge":["totalKnowledge", "newKnowledge"]
	        };
	        var categories = new Array();
	        var series1 = new Array();
	        var series2 = new Array();
	        var series3 = new Array();
	        var series4 = new Array();
	        
	        
	        $.each(_self.reportData, function(i,item){
	        	categories.push(item.startTime.split(' ')[0]);
	        	series1.push(item[fieldsName[_self.type][0]]);
	        	series2.push(item[fieldsName[_self.type][1]]);
	        	if(_self.type == 'employee' || _self.type == 'deal' || _self.type == 'study'){
	        		var value1 = item[fieldsName[_self.type][2]];
	        		var value2 = item[fieldsName[_self.type][3]];
	        		series3.push(value1);
	        		if(_self.type == 'employee'){
	        			series4.push(value2?parseFloat(value2)*100:0.00);
	        		}else{
	        			series4.push(value2);
	        		}
	        	}
	        });
	        
	        _self.lg('categories:' + categories);
	        _self.lg('series1:' + series1);
	        _self.lg('series2:' + series2);
	        _self.lg('_self.type:' + _self.type);
	        var options = _self.chartOptions();

	        options.xAxis[0].categories = categories;

	        var axisName={
	        		"company":["企业趋势分析", "企业总数","企业新增数"],
		        	"employee":["用户趋势分析", "用户总数", '用户新增数',"用户活跃数"],
		        	"deal":["订单趋势分析", "订单总金额", '订单新增额','总订单','新订单'],
		        	"study":["学习趋势分析", "计划总数","计划新增数","时间总数(小时)","时间新增数(小时)"],
		        	"knowledge":["内容趋势分析", "内容总数量", '内容新增数']
	        };
	        
			options.title.text = axisName[_self.type][0];
			options.yAxis[0].title.text = axisName[_self.type][1];
			options.yAxis[1].title.text = axisName[_self.type][2];
	        
	        options.series[0].name = options.yAxis[0].title.text;
	        options.series[1].name = options.yAxis[1].title.text;
	        options.series[0].data = series1;
	        options.series[1].data = series2;
	        if(_self.type == 'employee'){
	        	options.yAxis.push({ // Tertiary yAxis
		            gridLineWidth: 0,
		            title: {
		                text: '用户活跃数',
		                style: {
		                    color: Highcharts.getOptions().colors[2]
		                }
		            },
		            labels: {
		                format: '{value} ',
		                style: {
		                    color: Highcharts.getOptions().colors[2]
		                }
		            },
		            opposite: true
		        });
	        	
	        	options.series.push({
		            name: '用户活跃数',
		            type: 'spline',
		            yAxis: 2,
		            data: series3,
		            marker: {
		                enabled: false
		            },
		            dashStyle: 'shortdot',
		            tooltip: {
		                valueSuffix: ' ',

		            },
		        });
	        	
	        	options.yAxis[2].title.text = axisName[_self.type][3];
	    	    options.series[2].name = options.yAxis[2].title.text;
	        }else if(_self.type == 'deal' ||  _self.type == 'study'){
	        	options.yAxis.push({ // Tertiary yAxis
		            gridLineWidth: 0,
		            title: {
		                text: '用户活跃数',
		                style: {
		                    color: Highcharts.getOptions().colors[2]
		                }
		            },
		            labels: {
		                format: '{value} ',
		                style: {
		                    color: Highcharts.getOptions().colors[2]
		                }
		            },
		            opposite: true
		        });
		        
	        	options.yAxis.push({ // Tertiary yAxis
		            gridLineWidth: 0,
		            title: {
		                text: '用户留存数(%)',
		                style: {
		                    color: Highcharts.getOptions().colors[3]
		                }
		            },
		            labels: {
		                format: '{value} ',
		                style: {
		                    color: Highcharts.getOptions().colors[3]
		                }
		            },
		            opposite: true
		        });
	        	
	        	options.series.push({
		            name: '用户活跃数',
		            type: 'spline',
		            yAxis: 2,
		            data: series3,
		            marker: {
		                enabled: false
		            },
		            dashStyle: 'shortdot',
		            tooltip: {
		                valueSuffix: ' ',

		            },
		        });

	        	options.series.push({
		            name: '用户留存数(%)',
		            type: 'spline',
		            yAxis: 3,
		            data: series4,
		            marker: {
		                enabled: false
		            },
		            dashStyle: 'shortdot',
		            tooltip: {
		                valueSuffix: ' ',
		                valueDecimals:2,
		            }
		        });
	        	
	        	if(_self.type == 'deal' || _self.type == 'study'){
	        		options.yAxis[2].title.text = axisName[_self.type][3];
	        		options.yAxis[3].title.text = axisName[_self.type][4];
	    	        options.series[2].name = options.yAxis[2].title.text;
	    	        options.series[3].name = options.yAxis[3].title.text;
	        	}
	        }	        
			switch(parseInt(_self.durationType)){
				case 1: // daily
					options.title.text = options.title.text + '(日线)';
					break;
				case 2://weekly
					options.title.text = options.title.text + '(周线)';
					break;
				case 3: // monthly
					options.title.text = options.title.text + '(年线)';
					break;
			}
			
			$('.highcharts-container').show();
		    $("#highcharts-" + _self.type).highcharts(options);
		    $('.div_action_options').show();
		    _self.lg("#highcharts-" + _self.type)
		    eval('_self.lg("_self.init' + _self.type + 'Table(_self)");');
	        eval('_self.init' + _self.type + 'Table(_self)');
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
	        yAxis: [
	                 { // Secondary yAxis
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
	        },
	        { // Primary yAxis
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
	
	        },],
	        tooltip: {
	            shared: true,
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'left',
	            x: 80,
	            verticalAlign: 'top',
	            y: 55,
	            floating: true,
	            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
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
	initOrderTable:function(self){
		_self = self;
		if(_self.orderDataTable){
			_self.orderDataTable.fnDraw();
			return;
		}
		var aoColumns = [
								{ "sTitle": "企业名称","mData" :"orgName",	},
								{"sTitle": "渠道","mData" :"channelId","render": function(data){
									data = $.trim(data);
									if(data && _self.channelData){
										$.each(_self.channelData, function(i,item){
											if(item.channelId == data){
												return item.channelName;
											}
										});
									}
									return data;
								},},
								{"sTitle": "行业","mData" :"industryId","render": function(data){
									data = $.trim(data);
									if(data && _self.industryData){
										if(_self.industryData && _self.industryData[data]){
											return _self.industryData[data]; 
										}
									}
									
									return data;
								},},
								{"sTitle": "总用户数","mData" :"totalUser",},
								{ "sTitle": "新用户数","mData" :"newUser",},
								{ "sTitle": "活跃用户数","mData" :"activeUser",},
								//{ "sTitle": "用户留存率(%)","mData" :"retentionRate","render": function(data){ return (data?data*100:0.00).toFixed(2);},},
								{"sTitle": "总知识",	"mData" :"totalKnowledge",},
								{ "sTitle": "新知识","mData" :"newKnowledge",},
								{"sTitle": "总订单",	"mData" :"totalOrder",},
								{ "sTitle": "新订单","mData" :"newOrder",},
								{"sTitle": "总订单金额",	"mData" :"totalOrderAmount",},
								{"sTitle": "新订单金额","mData" :"newOrderAmount",},
								{"sTitle": "总学习小时数","mData" :"totalStudyHour",},
								{"sTitle": "新学习小时数","mData" :"newStudyHour",},
								{"sTitle": "总学习计划数","mData" :"totalStudyplan",},
								{"sTitle": "新学习计划数","mData" :"newStudyplan",},
	                      ];
		var setting = _self.tableSetting(_self, aoColumns, _self.apiContextPath + "/basereports/search?" + Math.random());
		_self.orderDataTable = $('#reportDataTable').dataTable(setting);
	},
	tableSetting: function(_self, aoColumns, url){
		
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
				
				var postUrl = _self.apiContextPath + "/channelreports/search?" + Math.random();;
				if(url){
					postUrl = url;
					var strStarttime = $('#reportStartTime').val();
					var strEndtime = $('#reportEndTime').val();
					var arrEnd = strEndtime.split("-");
					var startTime = new Date(arrEnd[0], arrEnd[1] - 1, arrEnd[2]-1);

					var newStartTime = startTime.getFullYear() + '-' + (startTime.getMonth()<9 ? ('0' + (startTime.getMonth() + 1)): (startTime.getMonth() + 1)) + '-' + startTime.getDate();
					postData = postData.replace(strStarttime, newStartTime);
					postData = postData.replace('"reportType":' + _self.durationType, '"reportType":1');
				}
					$.ajaxInvoke( {
						type: "POST",
						url: postUrl,
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
			"nfDrawCallback":function(){
				//$('#reportDataTable_paginate').css({"float":"right"});
			},
			"fnInitComplete":function(){
				//$('#reportDataTable_length').hide();
				//$('#reportDataTable').show();
				//$('#container2').css({height: $('#reportDataTable').height});
			},
			"sAjaxDataProp":"datas",
			"aoColumns": aoColumns,
	 	};
		$.each(setting.aoColumns, function(i, item){
			item["sClass"] = "center";
			item["asSorting"] = ["desc","asc"];
			
			if(!item["render"]){
				item["render"] = function(data){
					return htmlEncode(data);
				}
			}else{
				var tmpFun = item["render"];
				item["render"] = function(data){
					var tmpText = tmpFun(data);
					
					if(tmpText.indexOf('<') != -1){
						_self.lg('tmpText:' + tmpText);
						return tmpText;
					}else{
						_self.lg('htmlEncode tmpText:' + htmlEncode(tmpText));
						return htmlEncode(tmpText);
					}
				}
				
			}
		});
		
		return setting;
	},
	initcompanyTable:function(self){
		_self = self;
		if(_self.companyDataTable){
			_self.companyDataTable.fnDraw();
			return;
		}
		var aoColumns = [
			              	{ "sTitle": "记录日期","mData" :"startTime","render": function(data){ return data?data.split(' ')[0]:'';}, },
							{"sTitle": "总企业数","mData" :"totalOrg",},
							{ "sTitle": "新企业数","mData" :"newOrg",},
                   ];
		var setting = _self.tableSetting(_self, aoColumns);

		_self.companyDataTable = $('#companyDataTable').dataTable(setting);
	},
	initemployeeTable:function(self){
		_self = self;
		if(_self.employeeDataTable){
			_self.employeeDataTable.fnDraw();
			return;
		}
		var aoColumns = [
			              	{ "sTitle": "记录日期","mData" :"startTime","render": function(data){ return data?data.split(' ')[0]:'';}, },
							{"sTitle": "总用户数","mData" :"totalUser",},
							{ "sTitle": "新用户数","mData" :"newUser",},
							{ "sTitle": "活跃用户数","mData" :"activeUser",},
							//{ "sTitle": "用户留存率(%)","mData" :"retentionRate","render": function(data){ return (data?data*100:0.00).toFixed(2);},},
                   ];
		var setting = _self.tableSetting(_self, aoColumns);

		_self.employeeDataTable = $('#userDataTable').dataTable(setting);
	},
	initdealTable:function(self){
		_self = self;
		if(_self.dealDataTable){
			_self.dealDataTable.fnDraw();
			return;
		}
		var aoColumns = [
				              	{ "sTitle": "记录日期","mData" :"startTime","render": function(data){ return data?data.split(' ')[0]:'';}, },
								{"sTitle": "总订单金额","mData" :"totalOrderAmount",},
								{ "sTitle": "新订单金额","mData" :"newOrderAmount",},
								{ "sTitle": "总订单","mData" :"activeUser",},
								{ "sTitle": "新订单","mData" :"newOrder",},
	                      ];
		var setting = _self.tableSetting(_self, aoColumns);

		_self.dealDataTable = $('#contractDataTable').dataTable(setting);
	},
	initknowledgeTable:function(self){
		_self = self;
		if(_self.knowledgeDataTable){
			_self.knowledgeDataTable.fnDraw();
			return;
		}
		var aoColumns = [
				              	{ "sTitle": "记录日期","mData" :"startTime","render": function(data){ return data?data.split(' ')[0]:'';}, },
								{"sTitle": "内容总数量","mData" :"totalKnowledge",},
								{ "sTitle": "内容新增数","mData" :"newKnowledge",},
	                      ];
		var setting = _self.tableSetting(_self, aoColumns);
		_self.knowledgeDataTable = $('#knowledgeDataTable').dataTable(setting);
	},
	initstudyTable:function(self){
		_self = self;
		if(_self.studyDataTable){
			_self.studyDataTable.fnDraw();
			return;
		}
		var aoColumns = [
				              	{ "sTitle": "记录日期","mData" :"startTime","render": function(data){ return data?data.split(' ')[0]:'';}, },
								{"sTitle": "计划总数","mData" :"totalStudyplan",},
								{ "sTitle": "计划新增数","mData" :"newStudyplan",},
								{"sTitle": "时间总数（小时）","mData" :"totalStudyHour",},
								{ "sTitle": "时间新增数（小时）","mData" :"newStudyHour",},
	                      ];
		var setting = _self.tableSetting(_self, aoColumns);
		_self.studyDataTable = $('#studyDataTable').dataTable(setting);
	},
}

$(document).ready(page.init());