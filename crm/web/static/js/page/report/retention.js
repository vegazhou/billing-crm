var company = {
		apiContextPath:'/adminapi/v1',
		lg:function(msg){

		},
		durationType:1,
		type:'employee',
		reportData:null,
		init:function(){
			var _self = this;
			_self.loadChannel();
//			$('#channel').bind('change', function(){});
			$('.btn-search').unbind().bind('click', function(){

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
					
					selectedChannelId = '[' + selectedChannelId + ']';

			        $.ajaxInvoke({
			            url: _self.apiContextPath + "/basereports/searchRetentionRateList?" + Math.random(),
			            data: _self.jsonPostData(_self.durationType, selectedChannelId),
			            success: function(content, status) {
			            	_self.lg(content);
			            	_self.reportData = content.datas;
			            	_self.draw(_self);
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
			
			$('.highcharts-container').before('<div class="btn-group">' 
													+ '<button class="btn btn-sm btn-primary btn-duration" value="1">日</button>'
													+ '<button class="btn btn-sm btn-primary btn-duration" value="2">周</button>'
													+ '<button class="btn btn-sm btn-primary btn-duration" value="3">月</button>'
													+ '</div>');
			
			
			$('.tab-pane').css({height:"470px"});
			$('.btn-duration').unbind().bind('click', function(){
				$('.btn-duration').removeClass('active');
				$(this).addClass('active');
				_self.durationType = $(this).attr('value');
				//$('.highcharts-container').hide();
				$('.btn-search').click();
			});
			
			var now = new Date();
			now = now.getFullYear() + '-' + (now.getMonth()<9 ? ('0' + (now.getMonth() + 1)): (now.getMonth() + 1)) + '-' + now.getDate();
			$('#reportEndTime').val(now);
		},

		loadChannel:function(){
			var _self = this;
			var contextPath = _self.apiContextPath;
	        $.ajaxInvoke({
	            url: contextPath + "/dicts/mychannels",
	            data: {},
	            success: function(content, status) {
	            	_self.lg(content);
	            	$.each(content.datas, function(i, item){
	            		_self.lg(item);
	            		$('#channel').append("<option value='"+ item.channelId +"'>" + item.channelName + "</option>");
	            	})
	            },
	            type:'get'
	        });
		},
		jsonPostData:function(reportType, channelIds){
			
			var strEndtime = $('#reportEndTime').val();
			var now = new Date();
			var dates = strEndtime.split("-");
			var startTime = new Date(dates[0], dates[1] - 1, dates[2]);

			switch(parseInt(reportType)){
				case 1: // daily
					startTime = new Date(dates[0], dates[1] - 1, dates[2] - 15);
					break;
				case 2://weekly
					startTime = new Date(dates[0], dates[1] - 1, dates[2] -  ( 7 * 15 ));
					break;
				case 3: // monthly
					startTime = new Date(dates[0], dates[1] - 1 - 15, dates[2]);
					break;
			}
			var strStartTime = startTime.getFullYear() + '-' + (startTime.getMonth()<9 ? ('0' + (startTime.getMonth() + 1)): (startTime.getMonth() + 1)) + '-' + startTime.getDate();
			
			return '{"reportType":' + reportType
				+ ',"startTime":"' + strStartTime 
				+ '","endTime":"' + strEndtime
				+ '","channelIds":' + channelIds
				+ ',"orderby":"' + "createTime"
				+ '","direction":"' + "asc"
				+ '","pageSize":'+ 10000 + '}';
		},


	draw:function(self){
		var _self = self;
		_self.lg('Draw chart!');
		
		$('.btn-duration').removeClass('active');
		$('button[value=' + _self.durationType + ']').addClass('active');
        
        var categories = new Array();
        var series0 = new Array();
        
        $.each(_self.reportData, function(i,item){
        	categories.push(item.startTime.split(' ')[0]);
        	series0.push([item.startTime.split(' ')[0], item.retentionRate*100]);
        });
        
        _self.lg('categories:' + categories);
        _self.lg('series1:' + series0);
       
        var options = _self.chartOptions();
        /*
        options.xAxis[0].categories = categories;
        options.title.text = '渠道用户留存率趋势';
        options.yAxis[0].title.text = '用户留存率';
        options.series[0].name = options.yAxis[0].title.text;
        options.series[0].data = series0;
        */
        options.series[0].data = series0;
        $("#highcharts-employee").html("");
	    $("#highcharts-employee").highcharts(options);
	},

	chartOptions:function(){
		var options = {
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: '用户率留存率分析'
		        },
		        /*
		        subtitle: {
		            text: 'Source: <a href="http://en.wikipedia.org/wiki/List_of_cities_proper_by_population">Wikipedia</a>'
		        },
		        */
		        xAxis: {
		            type: 'category',
		            labels: {
		                rotation: -45,
		                style: {
		                    fontSize: '13px',
		                    fontFamily: 'Verdana, sans-serif'
		                }
		            }
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '用户留存率 (%)'
		            }
		        },
		        legend: {
		            enabled: false
		        },
		        tooltip: {
		            pointFormat: '留存率<b>{point.y:.1f}%</b>'
		        },
		        series: [{
		            name: 'Population',
		            data: [
		                ['Shanghai', 23.7],
		            ],
		            dataLabels: {
		                enabled: true,
		                rotation: -90,
		                color: '#FFFFFF',
		                align: 'right',
		                format: '{point.y:.1f}', // one decimal
		                y: 10, // 10 pixels down from the top
		                style: {
		                    fontSize: '13px',
		                    fontFamily: 'Verdana, sans-serif'
		                }
		            }
		        }]
		    };
		return options;
	},
}

$(document).ready(company.init());