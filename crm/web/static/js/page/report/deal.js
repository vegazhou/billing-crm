var company = {
		apiContextPath:'/adminapi/v1',
		lg:function(msg){

		},
		durationType:1,
		type:'deal',
		reportData:null,
		retentionData:null,
		dataTable:null,
		contractDataTable:null,
		field:['orgName','activeUser','totalUser', 'newUser','totalKnowledge','newKnowledge','totalOrder','newOrder','totalOrderAmount','newOrderAmount','totalStudyHour','newStudyHour','totalStudyplan','newStudyplan','endTime', 'retentionRate'],
		fieldName:['企业名称','活跃用户数','总用户数', '新用户数','总知识','新知识','总订单','新订单','总订单金额','新订单金额','总学习小时数','新学习小时数','总学习计划数','新学习计划数','记录日期','用户留存率'],
		init:function(){
			var _self = this;
			$('section').css({"margin-bottom":"0px;"});
			$('input').css({"margin-top":"5px","padding-bottom":"0px"});
			$('td').css({"text-align":"center"});
			$('form section').addClass('no-padding no-margin');
			
			$('.highcharts-container').before('<div style="float:right;display:none;" id="action_optoins"><div style="position:absolute;right:150px;margin-top:5px;"><a class="downloadReport" href="javascript:void(0);">下载报表数据</a></div>'
					+ '<div class="btn-group"><button class="btn btn-sm btn-primary btn-duration btn-daily" value="1">日</button>'
					+ '<button class="btn btn-sm btn-primary btn-duration btn-weekly" value="2">周</button>'
					+ '<button class="btn btn-sm btn-primary btn-duration btn-monthly" value="3">月</button>'
					+ '</div></div>');
			
			
			$('.downloadReport').unbind().bind('click', function(){
				_self.lg('download report');
				_self.downloadReport(_self);
			});
					
						
			_self.loadChannel();
			_self.loadIndustry(0, 'industry');
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
					_self.loadIndustry(selectedIndustryId, 'subindustry', _self);
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


					if(_self.type != 'deal'){
						_self.initTable(_self);
						return;
					}
					var postData = _self.jsonPostData(_self);
					if(!postData){
						return;
					}
			        $.ajaxInvoke({
			            url: _self.apiContextPath + "/channelreports/search?" + Math.random(),
			            data: postData,
			            success: function(content, status) {
			            	_self.lg(content);
			            	_self.reportData = content.datas;
			            	
			            	_self.draw(_self);
//			            	$('#highcharts-deal').css({"height":""});
					        _self.initUserTable(_self);
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
						if(id == 'deal'){
							_self.draw(_self);
						}else{
							_self.lg('initTable');
							_self.initTable(_self);
						}
						
				});
			
//			$('.highcharts-container').css({height:"350px",width:"100%"});
//			$('.tab-pane').css({height:"400px",width:"100%"});
			$('#highcharts-deal').css({height:"350px",width:"100%"});
			$('.widget-body.table').css({'margin-top':'100px'});
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

			csvContent += _self.fieldName.join(",") + "\n";
			data.forEach(function(infoArray, index){
				var newRow = '';
				$.each(_self.field, function(i,item){
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
				navigator.msSaveBlob(blob, '用户分析报表.csv');
			} else {
				var encodedUri = encodeURI(csvContent);
				var link = document.createElement("a");
				link.setAttribute("href", encodedUri);
				link.setAttribute("download", "用户分析报表.csv");
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
		jsonPostData:function(self, limit, orderby, direction){
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
				+ '","pageSize":'+ (limit?limit:10000) + '}';
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
		        	"content":["totalKnowledge", "newKnowledge"]
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
	        	series3.push(item['totalOrder']);
	        	series4.push(item['newOrder']);
	        });
	        
	        _self.lg('categories:' + categories);
	        _self.lg('series1:' + series1);
	        _self.lg('series2:' + series2);

	        var options = _self.chartOptions();
	        
	        	options.yAxis.push({ // Tertiary yAxis
		            gridLineWidth: 0,
		            title: {
		                text: '总订单',
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
		                text: '新订单',
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
		            name: '总订单',
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
		            name: '新订单',
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
	        options.xAxis[0].categories = categories;
			options.title.text = '订单趋势分析';
			options.yAxis[0].title.text = '总订单金额';
			options.yAxis[1].title.text = '新订单金额';
	        options.series[0].name = options.yAxis[0].title.text;
	        options.series[1].name = options.yAxis[1].title.text;
	        options.series[0].data = series1;
	        options.series[1].data = series2;
	        
	        
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
			$('#action_optoins').show();
		    $("#highcharts-" + _self.type).highcharts(options);
		    _self.lg("#highcharts-" + _self.type)

		    
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
	        yAxis: [{ // Secondary yAxis
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
	
	        }, 
	        ],
	        tooltip: {
	            shared: true,
//	            useHtml:true,
//	            headerFormat:'<small>{point.key}</small><table>',
//	            pointFormat:'<tr><td style="color:{series.color}">{series.com}:</td> + <td style="text-align:right"><b>{point.y}</b></td></tr>',
//	            footerFormat:'</table>',
//	            valueDecimals:2
	         
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
	initTable:function(self){
		_self = self;
		if(_self.dataTable){
			_self.dataTable.fnDraw();
			return;
		}
//		$('#reportDataTable').hide();
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
					var postData = _self.jsonPostData(_self, length, orderby, direction);
					if(!postData){
						return;
					}
//						aoData.push( _self.jsonPostData(_self) );   
						$.ajaxInvoke( {
							type: "POST",
							url: _self.apiContextPath + "/basereports/search?" + Math.random(),
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
				"aoColumns": [
								{ "sTitle": "企业名称","mData" :"orgName",	},
								{"sTitle": "总用户数","mData" :"totalUser",},
								{ "sTitle": "新用户数","mData" :"newUser",},
								{ "sTitle": "活跃用户数","mData" :"activeUser",},
								{ "sTitle": "用户留存率(%)","mData" :"retentionRate","render": function(data){ return (data?data*100:0.00).toFixed(2);},},
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
	                      ],
			};
		commonSetting4DataTable(setting);
		_self.dataTable = $('#reportDataTable').dataTable(setting);
	},
	initUserTable:function(self){
		_self = self;
		if(_self.contractDataTable){
			_self.contractDataTable.fnDraw();
			return;
		}
//		$('#reportDataTable').hide();
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
					var postData = _self.jsonPostData(_self, length, orderby, direction);
					if(!postData){
						return;
					}
//						aoData.push( _self.jsonPostData(_self) );   
						$.ajaxInvoke( {
							type: "POST",
							url: _self.apiContextPath + "/channelreports/search?" + Math.random(),
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
				"aoColumns": [
				              	{ "sTitle": "记录日期","mData" :"startTime","render": function(data){ return data?data.split(' ')[0]:'';}, },
								{"sTitle": "总订单金额","mData" :"totalOrderAmount",},
								{ "sTitle": "新订单金额","mData" :"newOrderAmount",},
								{ "sTitle": "总订单","mData" :"activeUser",},
								{ "sTitle": "新订单","mData" :"newOrder",},
								
//								{"sTitle": "总知识",	"mData" :"totalKnowledge",},
//								{ "sTitle": "新知识","mData" :"newKnowledge",},
//								{"sTitle": "总订单",	"mData" :"totalOrder",},
//								{ "sTitle": "新订单","mData" :"newOrder",},
//								{"sTitle": "总订单金额",	"mData" :"totalOrderAmount",},
//								{"sTitle": "新订单金额","mData" :"newOrderAmount",},
//								{"sTitle": "总学习小时数","mData" :"totalStudyHour",},
//								{"sTitle": "新学习小时数","mData" :"newStudyHour",},
//								{"sTitle": "总学习计划数","mData" :"totalStudyplan",},
//								{"sTitle": "新学习计划数","mData" :"newStudyplan",},
	                      ],
			};
		commonSetting4DataTable(setting);
		_self.contractDataTable = $('#contractDataTable').dataTable(setting);
	},
}

$(document).ready(company.init());