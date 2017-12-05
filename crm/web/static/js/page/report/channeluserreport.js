var report = {
	apiContextPath : '/adminapi/v1',
	lg : function(msg) {
		if (console && console.log) {
			console.log(msg);
		}
	},
	init : function() {
		_self = this;
		_self.init4A(_self);
		_self.init4B(_self);
		_self.init4C(_self);
		_self.init4D(_self);
		$('#reportID').unbind().bind('change', function(){
			var reportid = $(this).children('option:selected').val();
			_self.lg(reportid);
			$('.div-report').hide();
			$('#' + reportid).show();
		});


		var now = new Date();
		now = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 1);
		var maxTime = now.getFullYear() + '-' + (now.getMonth() < 9 ? ('0' + (now.getMonth() + 1)) : (now.getMonth() + 1)) + '-' + now.getDate();

		$('.time-selector').unbind().bind('click', function() {
			WdatePicker({
				skin : 'twoer',
				dateFmt : 'yyyy-MM-dd',
				maxDate : maxTime
			});
			return false;
		});

		loadChannel('.channel-control', _self);
	},
	init4B:function(self){
		var _self = self;
		$('.btn-search-B').unbind().bind('click', function() {
    		_self.lg("downloadReport_B");

    		$('.btn-search').attr('disabled', true);
    		$('.downloadprogessdiv').show();
    		//$('.downloadprogessdiv span').html('0%');
    		$('.downloadprogessdiv img').show();
    		var data = null;
    		
    		
    		$.ajaxInvoke({
    			url : _self.apiContextPath + "/reportalldata?" + Math.random(),
    			data : {
    				starttime:$('#reportStartTime-B').val(),
    				endtime:$('#reportEndTime-B').val(),
    			},
    			success : function(content, status) {
    				
    				// var columnNameArr = [ "报告日期","登录用户总数", "登录企业用户总数", "活跃企业数（用户登录）", "在线学习企业总数", "在线学习用户总数","课程上传总数", "用户点击总数：乐才", "用户点击总数：文库", "课程总搜索数",
    				//                       "企业新导入人数","老企业新导入人数","时长3-5分钟企业数","时长5-10分钟 企业用户数","时长3-5分钟个人用户数","时长5-10分钟企业数","时长5-10分钟企业用户数","时长5-10分钟个人用户数","时长10分钟以上企业数","时长10分钟以上企业用户数", "时长10分钟以上个人用户数", "时长3分钟以上企业数", "时长3分钟以上用户数","活路企业数(总学习时长10分钟以上+有5人以上有学习记录)" ];
    				// var columnDataName = [ "rptDate","loginUserCount", "loginOrgUserCount", "activeLoginOrgCount", "onlineStudyOrgCount", "onlineStudyUserCount", "uploadedCourseCount", "todayLecaiStudyCount", "todayDocumentStudyCount", "courseSearchCount",
    				//                        "importedUserCount","existingOrgImportedUserCount","studyLevel1OrgCount","studyLevel1OrgUserCount","studyLevel1PureCUserCount","studyLevel2OrgCount","studyLevel2OrgUserCount","studyLevel2PureCUserCount", "studyLevel3OrgCount","studyLevel3OrgUserCount", "studyLevel3PureCUserCount", "studyTotalLevelOrgCount", "studyTotalLevelUserCount","activeStudyOrgCount_1" ];
    				var columnNameArr = [ "报告日期","登录用户总数", "企业登录总数", "在线学习用户总数", "在线学习企业总数", "课程上传总数", "课程点击总数", "文库点击总数", "课程总搜索数",
    				                      "企业学习时长", "个人学习时长","时长10分钟以上企业数","时长10分钟以上企业用户数", "时长10分钟以上个人用户数", "时长3分钟以上企业数", "时长3分钟以上用户数" ];
    				var columnDataName = [ "rptDate","loginUserCount", "activeLoginOrgCount", "onlineStudyUserCount", "onlineStudyOrgCount",  "uploadedCourseCount", "todayLecaiStudyCount", "todayDocumentStudyCount", "courseSearchCount",
    				                       "totalOrgStudyTime", "totalCuserStudyTime","studyLevel3OrgUserCount", "studyLevel3PureCUserCount", "studyTotalLevelOrgCount", "studyTotalLevelUserCount" ];


    				var cvsFileName = "运营报表.csv";
    				_self.exportCSV(content.datas, columnNameArr, columnDataName, cvsFileName, _self);
    			},
    			type : 'get'
    		});
		});		
	},
	init4A:function(self){
		var _self = self;

		$('.btn-search-A').unbind().bind('click', function() {
			_self.lg('download report');
			
			
			
			var valid = true;
			if(!$('#reportStartTime-A').val()){
				valid = false;
				$('#reportStartTime-A').next('span').html('<font color=red>请设置开始时间！</font>');
			}else{
				$('#reportStartTime-A').next('span').html('');
			}

			if(!$('#reportEndTime-A').val()){
				valid = false;
				$('#reportEndTime-A').next('span').html('<font color=red>请设置结束时间！</font>');
			}else{
				$('#reportEndTime-A').next('span').html('');
			}

      	  	if( ! /^[1-9]{1}[0-9]*$/.test($.trim($('#userCount').val()))){
      	  		valid = false;
      	  		$('#userCount').next('span').html("<font color=red>请输入整数数字！</font>");
      	  	}else{
      	  		$('#userCount').next('span').html('');
      	  	}
			
      	  	if(!$("#channel").children('option:selected').val()){
      	  		valid = false;
      	  		$('#channel').next('span').html("<font color=red>请选择渠道！</font>");
      	  	}else{
      	  		$('#channel').next('span').html('');
      	  	}
      	  	
      	  	if(!valid){
      	  		return false;
      	  	}
			var columnNameArr = [ "姓名", "手机号", "邮件地址", "最近登录时间", "创建时间", "企业名称", "企业创建时间" ];
			var columnDataName = [ "fullName", "mobile", "email",  "lastLoginTime", "createTime", "orgId", "creator"];
			var cvsFileName = "用户报表.csv";
			
			$('.btn-search').attr('disabled', true);
			$('.downloadprogessdiv').show();
			$('.downloadprogessdiv span').html('');
			$('.downloadprogessdiv img').show();
			var data = null;
			
			
			$.ajaxInvoke({
				url : _self.apiContextPath + "/channelreports/orguser?" + Math.random(),
			
				data : {
					channelid:$("#channel").children('option:selected').val(),
					starttime:$('#reportStartTime-A').val(),
					endtime:$('#reportEndTime-A').val(),
					usercount:$('#userCount').val(),
				},
				success : function(content, status) {
					
					_self.exportCSV(content.datas, columnNameArr, columnDataName, cvsFileName, _self);
				},
				type : 'get'
			});
		});
	},
	init4C:function(self){
		var _self = self;

		$('.btn-search-C').unbind().bind('click', function() {
			_self.lg('download report');
			
			var valid = true;
			if(!$('#reportStartTime-C').val()){
				valid = false;
				$('#reportStartTime-C').next('span').html('<font color=red>请设置开始时间！</font>');
			}else{
				$('#reportStartTime-C').next('span').html('');
			}

			if(!$('#reportEndTime-C').val()){
				valid = false;
				$('#reportEndTime-C').next('span').html('<font color=red>请设置结束时间！</font>');
			}else{
				$('#reportEndTime-C').next('span').html('');
			}

      	  	if(!valid){
      	  		return false;
      	  	}
			var columnNameArr = [  "报告日期", "导入人数", "企业代码", "企业名称", "创建时间",  "渠道名称", "联系人", "联系电话", "联系邮件地址"];
			var columnDataName = ["updateTime", "type", "code",  "orgName",  "createTime", "sourceId", "contactName", "contactPhone", "contactEmail"];
			var cvsFileName = "老企业新导用户报表.csv";
			
			$('.btn-search').attr('disabled', true);
			$('.downloadprogessdiv').show();
			//$('.downloadprogessdiv span').html('0%');
			$('.downloadprogessdiv img').show();
			var data = null;
			
			
			$.ajaxInvoke({
				url : _self.apiContextPath + "/channelreports/orgsimporteduser?" + Math.random(),
				data : {
					starttime:$('#reportStartTime-C').val(),
					endtime:$('#reportEndTime-C').val(),
				},
				success : function(content, status) {
					
					_self.exportCSV(content, columnNameArr, columnDataName, cvsFileName, _self);
				},
				type : 'get'
			});
		});
	},
	init4D:function(self){
		var _self = self;

		$('.btn-search-D').unbind().bind('click', function() {
			_self.lg('download report');
			var columnNameArr = [  "报告日期", "新增企业数","新增用户数","导入用户数","导入用户数",  "订单总数", "订单总额"];
			var columnDataName = ["rptDate", "newOrg", "newUser", "existingOrgImportedUserCount", "todayOrder", "todayOrderAmount"];
			var cvsFileName = "用户运营日常报表.csv";
			
			$('.btn-search').attr('disabled', true);
			$('.downloadprogessdiv').show();
			//$('.downloadprogessdiv span').html('0%');
			$('.downloadprogessdiv img').show();
			var data = null;
			
			
			$.ajaxInvoke({
				url : _self.apiContextPath + "/reportalldata?" + Math.random(),
				data : {
					starttime:$('#reportStartTime-D').val(),
					endtime:$('#reportEndTime-D').val(),
				},
				success : function(content, status) {
					_self.exportCSV(content.datas, columnNameArr, columnDataName, cvsFileName, _self);
				},
				type : 'get'
			});
		});
	},
	exportCSV: function(data, columnNameArr, columnDataName, cvsFileName, self){
		var _self = self;
		_self.lg('export csf file. data lenght:');
		if (!data || data.length == 0) {
			$('.btn-search').attr('disabled', false);
			$('.downloadprogessdiv img').hide();
			$('.downloadprogessdiv span').html("没有数据可供下载!");
			return;
		}

		var csvContent = "data:text/csv;charset=utf-8,\ufeff";
		if (window.navigator.msSaveOrOpenBlob) {
			csvContent = "\ufeff";
		}
		

		csvContent += columnNameArr.join(",") + "\n";
		var dataLength = data.length;
		data.forEach(function(infoArray, index) {
			var newRow = '';
			for (i = 0, n = columnDataName.length; i < n; i++) {
				if (i != 0) {
					newRow += ',';
				}
				var v = infoArray[columnDataName[i]];
				if(columnDataName[i].indexOf('Time') != -1){
					v = (v && v.length > 19) ? v.substring(0, 19) : v;
				}else if(columnDataName[i].indexOf('Date') != -1){
					v = (v && v.length >= 19) ? v.substring(0, 10) : v;
				}

				if(!v){
					v = 0;
				}
				//$('.downloadprogessdiv span').html(Math.round(( i / dataLength) * 100) + "%");
				
				newRow += v;
			}
			csvContent += newRow + "\n";
		});

		if (window.navigator.msSaveOrOpenBlob) {
			// if browser is IE
			var blob = new Blob([ decodeURIComponent(encodeURI(csvContent)) ], {
				type : "text/csv;charset=utf-8;"
			});
			$('.downloadprogessdiv span').html("100%");
			navigator.msSaveBlob(blob, cvsFileName);
		} else {
			var encodedUri = encodeURI(csvContent);
			var link = document.createElement("a");
			link.setAttribute("href", encodedUri);
			link.setAttribute("download", cvsFileName);
			document.body.appendChild(link);
			//$('.downloadprogessdiv span').html("100%");
			link.click();
		}
		$('.btn-search').attr('disabled', false);
		$('.downloadprogessdiv').hide();

	}
}

$(document).ready(report.init());