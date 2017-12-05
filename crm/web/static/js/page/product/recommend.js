var recommend = {
	apiContextPath : adminContextPath + "/v1",
	lg : function (msg) {
		if (console && console.log) {
			console.log(msg);
		}
	},
	loadType : function () {
		$.ajaxInvoke({
			url : _self.apiContextPath + "/hotspot/recommendcourses/list",
			dataType : "json",
			type : 'get',
			data : {
				orderby : "hotIndex",
				direction : 'asc',
				name : $(".typeahead").val()
			},
			success : function (data) {
				$.each(data.datas, function(i, item){
					var obj = null;
					if(!item.targetId){
						obj = $('#course' + item.type + '_' + item.hotIndex);
						$('#course' + item.type + '_' + item.hotIndex).val(item.productName);
						$('#course' + item.type + '_' + item.hotIndex).attr('val', item.knowledgeId);
					}else{
						var industryID = "id_" + item.targetId.replace(/-/g, '_') + "_" + item.hotIndex;
						obj = $('#'+industryID);
					}
					obj.val(item.productName);
					obj.attr('val', item.knowledgeId);
				});
			}
		});
	},
	init : function () {
		_self = this;
		$('.btn-save').attr('disabled', 'disabled');
		function enableSave() {
			$('.btn-save').removeAttr('disabled');
		}
		$.each($('#wid-id-c1 input[type=text]'), function(i, item){
			var id = "id_" + industryId + "_" + i.toString();
			$.combox({
				field : $(item).attr('id'),
				url : _self.apiContextPath + "/hotspot/courses/list?type=" + $(item).attr('cTypeId') + "&" + Math.random(),
				key : 'knowledgeId',
				value : 'productName',
				size : 10,
				search : 'name',
				callBack : enableSave,
			});
			
		});
		

		$('.btn-save').unbind().bind('click', function () {
			$('.btn-save').attr('disabled', 'disabled');
			var data = {};
			data["recommendCourses"] = [];
			$.each($('input[type=text]'), function (i, item) {
				_self.lg($(item));

				if ($(item).val()) {
					var t = {
						type : $(item).attr('cTypeId'),
						hotIndex : $(item).attr('recommendIndex'),
						knowledgeId : $(item).attr('val'),
					}
					data["recommendCourses"].push(t);
				}
			});
			$.ajaxInvoke({
				type : "put",
				url : _self.apiContextPath + "/hotspot/courses/batchadd?" + Math.random(),
				dataType : "json",
				data : JSON.stringify(data),
				success : function (content, status) {
					_self.lg(content);
					_self.lg(status);
					mAlert("保存成功");
				},
				error : function (data) {
					_self.lg(data);
					enableSave();
					mAlert("保存失败");
				}
			})

		});
		
	    
		$.ajaxInvoke({
			type : "get",
			url : _self.apiContextPath + "/industries?" + Math.random(),
			dataType : "json",
			data : JSON.stringify({}),
			success : function (content, status) {
				var industryData = [];
				$.each(content.datas, function(i, item){
					$.each(item.datas, function(x, xitem){
						_self.addIndustry(xitem.id, xitem.name);
						industryData.push({name:xitem.name,id:xitem.id});
					});
				});
				_self.lg(content);
				_self.lg(status);
				
				industryData.sort(function(a,b){
					if(a && b){
						return a.name.localeCompare(b.name)
					};//汉字拼音排序方法
				});
				
				_self.lg("=====================" + industryData.length);
			    $.each(industryData, function(i, item){
//			    	_self.lg(item);
//			    	_self.lg('item.id:' + item.id + "; item.name:" + item.name);
			    	$("#industryId").append("<option value='"+ item.id +"'>" + item.name + "</option>");
			    });
				//mAlert("读取成功");
				_self.loadType();
			},
			error : function (data) {
				_self.lg(data);
				//enableSave();
				//mAlert("保存失败");
			}
		});
		
		$('#industryId').unbind().bind('change', function(){
			var industryId = $(this).children('option:selected').val();
			_self.lg(industryId);
			if(industryId == ''){
				$('.div_industryId').show();
			}else{
				_self.lg('industryId ='+ industryId);
				$('.div_industryId').hide();
				$('.div_' + industryId.replace(/-/g, '_')).show();
			}
		});


	},
	
	addIndustry:function(industry, industryName, self){
		var industryId = industry.replace(/-/g, '_');
		var div = 	'' +
		'<div class="row div_industryId div_' + industryId +'" >' +
		'	<section class="col col-2">'+
		'		<label class="label text-right">' + industryName + '：&emsp;</label>'+
		'	</section>	'+
		'	<section class="col col-2">'+
		'		<label class="select">'+
		'			<div class="btn-group input">'+
		'				<input class="form-control id_'+ industryId + '" type="text"  id="id_' + industryId + '_6" placeholder="查找设置推荐课程" industryId="' + industry + '" recommendIndex=6>'+
		'			</div>'+
		'		</label>'+
		'	</section>'+
		'	<section class="col col-2">'+
		'		<label class="select">'+
		'			<div class="btn-group input">'+
		'				<input class="form-control id_'+ industryId + '" type="text"  id="id_' + industryId + '_5" placeholder="查找设置推荐课程" industryId="' + industry + '" recommendIndex=5>'+
		'			</div>'+
		'		</label>'+
		'	</section>'+
		'	<section class="col col-2">'+
		'		<label class="select">'+
		'			<div class="btn-group input">'+
		'				<input class="form-control id_'+ industryId + '" type="text"  id="id_' + industryId + '_4" placeholder="查找设置推荐课程" industryId="' + industry + '" recommendIndex=4>'+
		'			</div>'+
		'		</label>'+
		'	</section>'+
		'</div>'+
		'<div class="row div_industryId div_' + industryId +'">'+
		'	<section class="col col-2">'+
		'		<label class="label text-right"></label>'+
		'	</section>'+
		'	<section class="col col-2">'+
		'		<label class="select">'+
		'			<div class="btn-group input">'+
		'				<input class="form-control id_'+ industryId + '" type="text"  id="id_' + industryId + '_3" placeholder="查找设置推荐课程" industryId="' + industry + '" recommendIndex=3>'+
		'			</div>'+
		'		</label>'+
		'	</section>'+
		'	<section class="col col-2">'+
		'		<label class="select">'+
		'			<div class="btn-group input">'+
		'				<input class="form-control id_'+ industryId + '" type="text"  id="id_' + industryId + '_2" placeholder="查找设置推荐课程" industryId="' + industry + '"  recommendIndex=2>'+
		'			</div>'+
		'		</label>'+
		'	</section>'+
		'	<section class="col col-2">'+
		'		<label class="select">'+
		'			<div class="btn-group input">'+
		'				<input class="form-control id_'+ industryId + '" type="text"  id="id_' + industryId + '_1" placeholder="查找设置推荐课程" industryId="' + industry + '"  recommendIndex=1>'+
		'			</div>'+
		'		</label>'+
		'	</section>'+
		//	<section class="col col-2">'+
		//		<label class="label text-right"></label>'+
		//	</section>'+
		'	<section class="col col-1">'+
		'		<label class="label text-right" style="margin-bottom:0px;">'+
		'			<span class="btn btn-primary btn-success btn-save-'+ industryId +'" style="padding: 6px 12px;" disabled> &nbsp;&nbsp;<i class="fa fa-save"></i> 保存&nbsp;&nbsp;  </span>'+
		'		</label>'+
		'	</section>'+
		'</div>'+
		'<div class="row div_industryId div_' + industryId +'">'+
		'	<section class="col col-9">'+
		'		<hr/>'+
		'	</section>'+
		'</div>';
		
		$('.industry-fieldset').append(div);
		$('.btn-save-' + industryId).unbind().bind('click', function(){
			$(this).attr('disabled', true);
			_self.lg("save data for industry " + industryId );
			
			var data = {};
			data["recommendCourses"] = [];
			
			$.each($('.id_' + industryId), function(i, item){
				_self.lg($(item).attr('industryId') + '=' + $(item).attr('val'));
				if ($(item).val()) {
					var t = {
						type : 1,
						hotIndex : $(item).attr('recommendIndex'),
						knowledgeId : $(item).attr('val'),
					}
					data["recommendCourses"].push(t);
				}
			});
			
			$.ajaxInvoke({
				type : "put",
				url : _self.apiContextPath + "/hotspot/courses/batchadd?targetId=" + industryId.replace(/_/g, '-') + "&" + Math.random(),
				dataType : "json",
				data : JSON.stringify(data),
				success : function (content, status) {
					_self.lg(content);
					_self.lg(status);
					mAlert("保存成功");
				},
				error : function (data) {
					_self.lg(data);
					enableSave();
					mAlert("保存失败");
				}
			})
		});
		
		$.each($('.div_' + industryId +' input[type=text]'), function(i, item){
			$.combox({
				field : $(item).attr('id'),
				url : _self.apiContextPath + "/hotspot/courses/list?type=1&" + Math.random(),
				key : 'knowledgeId',
				value : 'productName',
				size : 10,
				search : 'name',
				callBack : function(){
					$('.btn-save-' + industryId).attr('disabled', false);
				}
			});
			
		});

	}
}
$(document).ready(recommend.init());
