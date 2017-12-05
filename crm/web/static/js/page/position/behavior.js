var behavior = {
	apiContextPath : '/adminapi/v1',
	lg : function (msg) {},
	type : 'behavior',
	dataTable : null,
	init : function () {
		var _self = this;
		_self.initTable(_self);
		
		$(".btn-search").unbind("click").click(function () {
			_self.dataTable.fnDraw();
		});
		
		$.combox({
		    field: 'skillId',
		    type:  'get',
		    url: G_CTX_ROOT + "/v1/functions/0/positions/findSkillByName",
		    key: 'id',
		    value: 'name',
		    size: 10,
		    search: "args",  //postJson name名称
		    'z-index':999,
		    callBack: function(){}
		});
		
	},
	changeEmpty : function (val) {
		var flag = val != null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g, "") != "";
		return flag;
	},
	jsonPostData : function (self, start, limit, orderby, direction) {
		var _self = self;
		return '{"behaviorName":"' + $("#s_behaviorName").val()
		 + '","skillId":"' + $("#skillId").attr("val")
		 + '","orderby":"' + (orderby ? orderby : "createTime")
		 + '","direction":"' + (direction ? direction : "asc")
		 + '","offSet":' + (start ? start : 0)
		 + ',"limit":' + (limit ? limit : 10000) + '}';
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
				if (!postData) {
					return;
				}
				$.ajaxInvoke({
					type : "POST",
					url : _self.apiContextPath + "/skills/searchBehaviors",
					dataType : "json",
					data : postData,
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
			"fnDrawCallback" : function () {},
			"fnInitComplete" : function () {},
			"sAjaxDataProp" : "datas",
			"aoColumns" : [{
					"sTitle" : "行为表现名称", 
					"mData" : "name", 
				}, {
					"sTitle" : "行为级别", 
					"mData" : "level", 
					"render" : function (data) {
						var level;
						if (data == "1") {
							level = "一级";
						} else if (data == "2") {
							level = "二级";
						} else {
							level = "三级";
						}
						return level;
					}
				}, {
					"sTitle" : "所属能力", 
					"mData" : "skillName", 
					"bSortable" : false,
				}, {
					"sTitle" : "行为表现状态", 
					"mData" : "status", 
					"render" : function (data) {
						return data == "1" ? "已启用" : "已禁用";
					}
				}, {
					"sTitle" : "创建时间", 
					"mData" : "createTime",
				},
			],
		};
		commonSetting4DataTable(setting);
		_self.dataTable = $('#datatable').dataTable(setting);
	},
}
$(document).ready(behavior.init());
