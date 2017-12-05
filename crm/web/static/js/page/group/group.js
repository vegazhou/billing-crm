var group = {
	apiContextPath : '/adminapi/v1',
	lg : function (msg) {},
	type : 'group',
	reportData : null,
	dataTable : null,
	editor : null,
	init : function () {
		var _self = this;
		_self.initTable(_self);

		$('.btn-search').unbind("click").click(function () {
			var startTime = _self.changeEmpty($("#startTime").val(), '');
			var endTime = _self.changeEmpty($("#endTime").val(), '');
			if (startTime == "" && endTime != "") {
				$('#startTime').nextAll('span').html('<br><font color="red">请设置开始时间！</font>');
				return false;
			} else {
				$('#startTime').nextAll('span').html('');
			}
			if (endTime == "" && startTime != "") {
				$('#endTime').nextAll('span').html('<br><font color="red">请设置结束时间！</font>');
				return false;
			} else {
				$('#endTime').nextAll('span').html('');
			}
			_self.dataTable.fnDraw();
		});

		var now = new Date();
		now = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 1);
		var maxTime = now.getFullYear() + '-' + (now.getMonth() < 9 ? ('0' + (now.getMonth() + 1)) : (now.getMonth() + 1)) + '-' + now.getDate();

		$('#endTime').unbind().bind('click', function () {
			WdatePicker({
				skin : 'twoer',
				dateFmt : 'yyyy-MM-dd',
				maxDate : maxTime
			});
			return false;
		});

		$('#startTime').unbind().bind('click', function () {
			WdatePicker({
				skin : 'twoer',
				dateFmt : 'yyyy-MM-dd',
				maxDate : maxTime
			});
			return false;
		});

	},
	changeEmpty : function (val, target) {
		var flag = val != null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g, "") != "";
		flag = flag == false ? target : val;
		return flag;
	},
	jsonPostData : function (self, start, limit, orderby, direction) {
		var _self = self;
		var groupName = _self.changeEmpty($("#groupName").val(), '');
		var startTime = _self.changeEmpty($("#startTime").val(), '');
		var endTime = _self.changeEmpty($("#endTime").val(), '');

		return '?groupName=' + groupName
		 + '&startTime=' + startTime
		 + '&endTime=' + endTime
		 + '&orderby=' + (orderby ? orderby : "createTime")
		 + '&direction=' + (direction ? direction : "asc")
		 + '&offset=' + (start ? start : 0)
		 + '&limit=' + (limit ? limit : 10000) + '';
	},
	initTable : function (self) {
		_self = self;
		if (_self.dataTable) {
			_self.dataTable.fnDraw();
			return;
		}
		var setting = {
			"oLanguage" : dataTableLan,
			"aLengthMenu" : [[10, 25, 50], [10, 25, 50]],
			"bjQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bProcessing" : true,
			"bPaginate" : true,
			"bServerSide" : true,
			"bFilter" : false,
			"bInfo" : true,
			"bStateSave" : false,
			"bAutoWidth" : true,
			"aaSorting" : [[0, "desc"]],
			"fnServerData" : function (sSource, aoData, fnCallback) {
				_self.lg("aoData:" + JSON.stringify(aoData));
				var start = 0;
				var length = 10;
				var columns = null;
				var sEcho = 1;
				var order = [];
				$.each(aoData, function (i, item) {
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
				//console.log(postData);
				if (!postData) {
					return;
				}
				//						aoData.push( _self.jsonPostData(_self) );
				$.ajaxInvoke({
					type : "GET",
					url : _self.apiContextPath + "/groups" + postData,
					dataType : "json",
					//data: postData,
					success : function (content, status) {
						_self.lg(content);
						content['sEcho'] = sEcho;
						content['iDisplayStart'] = content.paging.offset;
						content['iDisplayLength'] = content.paging.limit;
						content['iTotalRecords'] = content.paging.count;
						content['iTotalDisplayRecords'] = content.paging.count;
						content['sortField'] = orderby; //"createTime";
						content['sortType'] = direction;
						_self.lg(content);
						fnCallback(content);
					},
					error : function (data) {
						_self.lg(data);
					}
				});
			},
			"fnDrawCallback" : function () {

				$('.typehotsetting').editable({
					url : function (params) {
						//var d = new $.Deferred;
						//if(params.value === 'abc') {
						//	return d.reject('error message'); //returning error via deferred object
						//}
						var putData = {
							hotIndex : parseInt($.trim(params.value)),
						}
						$.ajaxInvoke({
							type : "put",
							url : _self.apiContextPath + "/groups/" + $(this).attr("pid"),
							dataType : "json",
							data : JSON.stringify(putData),
							success : function (content, status) {
								_self.lg(content);
								_self.lg(status);
							},
							error : function (data) {
								_self.lg(data);
							}
						})
					},
					validate : function (value) {
						var reg = /^(?:0|[1-9][0-9]?|100)$/;
						if (!reg.test($.trim(value))) {
							return '请输入0-100间的整数';
						}
					},
					type : 'text',
					pk : 1,
					name : $.trim($(this).attr('pid')),
					title : '设置热度'
				});
				
				$(".btn-active").unbind("click").click(function(){
		            var groupId = $(this).parents("TR").find("input[type=hidden][name=groupId]").val();
		            var groupStatus = $(this).parents("TR").find("input[type=hidden][name=groupStatus]").val();
		            var groupName = $(this).parents("TR").children('td').eq(0).html();
		            var active = groupStatus==1 ? "禁用" : "启用";
		            var data = {};
		            data.status = groupStatus==1 ? 0 : 1;
		            var postData=JSON.stringify(data);	
		            mConfirm("确定"+active+"【"+groupName+"】？", function(){
						$.ajaxInvoke({
							url : _self.apiContextPath + "/groups/"+groupId ,
							type : "put",
							data : postData,
							dataType: "json",
							success : function (data){
								msgBox('success', active+"圈子成功");
								_self.dataTable.fnDraw();
							},
							error	:	function (data){
								msgBox('fail', active+"圈子失败");
							},
						});
		            });
				});
			},
			"fnInitComplete" : function () {
				
			},
			"sAjaxDataProp" : "datas",
			"aoColumns" : [
			    {"sTitle" : "圈子名称","mData" : "name",}, 
			    {"sTitle" : "圈子类别","mData" : "tagName","bSortable" : false,"render" : function (data){return data?data:"未设置"}},
			    {"sTitle" : "手机","mData" : "phone","bSortable" : false,},
			    {"sTitle" : "邮箱","mData" : "email","bSortable" : false,},
			    {"sTitle" : "人数","mData" : "memberCount",},
			    {"sTitle" : "话题","mData" : "topicCount","bSortable" : false,},
			    {"sTitle" : "干货","mData" : "drycargoCount","bSortable" : false,},
			    {"sTitle" : "分享","mData" : "shareCount","bSortable" : false,},
			    {"sTitle" : "课程","mData" : "courseCount","bSortable" : false,},
			    {"sTitle" : "热度","mData" : "hotIndex","render" : function (data, dis, obj) {
						return '<a href="#" pid="' + obj.pid + '" data-type="text" data-pk="1" data-title="设置热度" class="typehotsetting">' + (data?data:0) + '</a>';
				},},
				{"sTitle" : "创建时间","mData" : "createTime",},
				{ "sTitle": "操作", "mData" :"", "bSortable" : false , "render": function(data,dis,obj) {
						var enableDom = "";
						if(obj.status==1){
							enableDom = "<span class='btn btn-danger btn-xs btn-active'> 禁用 </span> ";
						} else { 
							enableDom = "<span class='btn btn-success btn-xs btn-active'> 启用 </span> ";
						}
						return "<input type='hidden' name='groupId' value='"+obj.pid+"'/>" + 
							   "<input type='hidden' name='groupStatus' value='"+obj.status+"'/>" + enableDom ;
				    }
				}
			],
		};
		commonSetting4DataTable(setting);
		_self.dataTable = $('#groupDatatable').dataTable(setting);
	},
}

$(document).ready(group.init());
