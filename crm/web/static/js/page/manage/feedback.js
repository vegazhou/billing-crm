var feedback = {
	apiContextPath : '/adminapi/v1',
	lg : function (msg) {},
	type : 'feedback',
	dataTable : null,
	init : function () {
		var _self = this;
		_self.initTable(_self);

		$(".btn-search").unbind("click").click(function () {
			_self.dataTable.fnDraw();
		});

		$(".btn-save").unbind("click").click(function () {
			$(".btn-save").attr("disabled", false);
			if (!_self.changeEmpty($("#status").val())) {
				$(".btn-save").attr("disabled", false);
				$("#status").nextAll().filter("span").html("<font color=red>请选择审核状态！</font>");
				return false;
			}
			var data = {};
			data.status = $("#status").val();
			data.message = $("#message").val();
			var postData = JSON.stringify(data);
			var postUrl = G_CTX_ROOT + "/v1/orgs/feedback/" + $("#u_feedbackId").val();
			$.ajaxInvoke({
				url : postUrl,
				data : postData,
				type : "put",
				contentType : "application/json",
				success : function (data) {
					msgBox('success', "审核反馈成功");
					$("#myModal").modal("hide");
					_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
				},
				error : function (data) {
					msgBox('fail', "审核反馈失败");
					$("#myModal").modal("hide");
				},
			});
		});

		$(".btn-detail").unbind("click").click(function () {
			$("#feedbackDiv").css({
				width : "690px",
				height : "400px",
				margin : "0px auto",
				overflow : "auto"
			});

			$("#feedbackDetail tr").remove();
			$.ajaxInvoke({
				url : G_CTX_ROOT + "/v1/orgs/feedback/" + $("#u_feedbackId").val(),
				type : "get",
				contentType : "application/json",
				success : function (data) {
					if (data != "") {
						$.each(data, function (i, obj) {
							var str = "<tr>";
							str += "<td>" + (i + 1);
							$.each(obj, function (key, val) {
								if (key == "commentId") {
									str += "<input type='hidden' value='" + val + "'></td>";
								}
								if (key == "content" || key == "createTime") {
									str += "<td><font>" + val + "</font></td>";
								}
							});
							str += "</tr>";
							$("#feedbackDetail").append(str);
						});
					} else {
						$("#feedbackDetail").append("<tr><td colspan='3'>该反馈还没有评论信息</td></tr>");
					}
					$("#myModal1").modal("show");
				},
				dataType : "json"
			});
		});

	},
	changeEmpty : function (val) {
		var flag = val != null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g, "") != "";
		return flag;
	},
	jsonPostData : function (self, start, limit, orderby, direction) {
		var _self = self;
		var statusflag = _self.changeEmpty($("#s_status").val());
		var status = statusflag == false ? 0 : $("#s_status").val();
		var title = $("#s_title").val();
		return '{"status":' + status
		 + ',"title":"' + title
		 + '","orderby":"' + (orderby ? orderby : "createTime")
		 + '","direction":"' + (direction ? direction : "asc")
		 + '","offset":' + (start ? start : 0)
		 + ',"limit":' + (limit ? limit : 10000) + '}';
	},
	tableSetting : function (self, aoColumns, url, type) {
		var _self = self;
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
				if (type == "POST") {
					var postData = _self.jsonPostData(_self, start, length, orderby, direction);
					if (!postData) {
						return;
					}
				}

				$.ajaxInvoke({
					type : type,
					url : url,
					dataType : "json",
					data : postData,
					success : function (content, status) {
						_self.lg(content);
						content['sEcho'] = sEcho;
						content['iDisplayStart'] = content.paging.offset;
						content['iDisplayLength'] = content.paging.limit;
						content['iTotalRecords'] = content.paging.count;
						content['iTotalDisplayRecords'] = content.paging.count;
						content['sortField'] = orderby; // "createTime";
						content['sortType'] = direction;
						fnCallback(content);
					},
					error : function (data) {
						_self.lg(data);
					}
				});
			},
			"sAjaxDataProp" : "datas",
			"aoColumns" : aoColumns,
		};
		commonSetting4DataTable(setting);
		return setting;
	},
	initTable : function (self) {
		var _self = self;
		if (_self.dataTable) {
			_self.dataTable.fnDraw();
			return;
		}
		var aoColumns = [{
				"sTitle" : "标题名称",
				"mData" : "title",
			}, {
				"sTitle" : "反馈状态",
				"mData" : "status",
				"render" : function (data) {
					if (data == "1")
						data = "<font color='red'>解决中</font>";
					if (data == "2")
						data = "已解决";
					if (data == "3")
						data = "待评估";
					return data;
				}
			}, {
				"sTitle" : "反馈人员",
				"mData" : "creator",
			}, {
				"sTitle" : "反馈人电话",
				"mData" : "phone",
				"render" : function (data) {
					return data == "" ? "未填写" : data;
				}
			}, {
				"sTitle" : "创建时间",
				"mData" : "createTime",
			}, {
				"sTitle" : "操作",
				"bSortable" : false,
				"sWidth" : "150px",
				"render" : function (data, dis, obj) {
					editDom = "<span class='btn btn-success btn-xs btn-modify'> 审核 </span> ";
					deleteDom = "<span class='btn btn-danger btn-xs btn-delete'> 删除 </span> ";
					return "<input type='hidden' name='feedbackId' value='" + obj.pid + "'/>" +
					"<input type='hidden' name='status' value='" + obj.status + "'/>" +
					"<input type='hidden' name='imageurl' value='" + (IMAGE_PATH + obj.imageurl) + "'/>" +
					"<input type='hidden' name='detail' value='" + obj.detail + "'/>" + editDom + deleteDom;
				}
			},
		];

		var url = _self.apiContextPath + "/orgs/feedback/searchFeedbacks";
		var setting = _self.tableSetting(_self, aoColumns, url, 'POST');
		setting.fnDrawCallback = function () {
			$(".btn-modify").unbind("click").click(function () {
				var title = $(this).parents("TR").children('td').eq(0).html();
				var creator = $(this).parents("TR").children('td').eq(2).html();
				var detail = $(this).parents("TR").find("input[type=hidden][name=detail]").val();
				var imageurl = $(this).parents("TR").find("input[type=hidden][name=imageurl]").val();
				var message = $(this).parents("TR").find("input[type=hidden][name=message]").val();
				var feedbackId = $(this).parents("TR").find("input[type=hidden][name=feedbackId]").val();
				var status = $(this).parents("TR").find("input[type=hidden][name=status]").val();
				$("#status").find("option").each(function () {
					if (status == $(this).val()) {
						$(this).attr("selected", true);
						return false;
					}
					$(this).attr("selected", false);
				});
				$("#title").val(title);
				$("#detail").val(detail);
				$("#creator").val(creator);
				$("#message").val(message);
				$("#u_feedbackId").val(feedbackId);
				if (imageurl == "")
					$("#image").html("<div>未上传图片</div>");
				if (imageurl != "") {
					$("#imagetxt").css({
						"line-height" : "200px"
					});
					$("#image").html("<img style='width:320px;height:200px;' src='" + imageurl + "' alt='" + title + "'/>");
				}
				$("#myModal").modal("show");
			});

			$(".btn-delete").unbind("click").click(function () {
				var feedbackId = $(this).parents("TR").find("input[type=hidden][name=feedbackId]").val();
				var title = $(this).parents("TR").children('td').eq(0).html();
				mConfirm("确定删除【" + title + "】？", function () {
					$.ajaxInvoke({
						url : G_CTX_ROOT + "/v1/orgs/feedback/" + feedbackId,
						type : "delete",
						contentType : "application/json",
						success : function (data) {
							msgBox('success', "删除反馈成功");
							_self.dataTable.fnPageChange(parseInt($(".current").html()) - 1);
						},
						error : function (data) {
							msgBox('fail', "删除反馈失败");
						},
					});
				});
			});
		};
		_self.dataTable = $('#feedbackDatatable').dataTable(setting);
	},
}
$(document).ready(feedback.init());