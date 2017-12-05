var page = {
	apiContextPath : '/adminapi/v1',
	lg : function(msg) {
		if (console && console.log) {
			console.log(msg);
		}
	},
	totalCount:0,
	pageLength:200,
	init: function(){
		_self = this;
		
		$(".btn-search").unbind().bind('click', function(){
			_self.loadWords(1, 1, _self);
		});
		
		$('.slide-tab li.active').addClass("selected1");
		$(".slide-tab li.active").unbind().bind('click', function(){
			$(".wordslistDiv").slideToggle("fast", function(){
			    $(".hide-tab-panel").slideToggle("fast");
			});
		    
		    $(this).toggleClass("selected1");
		});
		
		
		$('.btn-eraser').unbind().bind('click', function(){
			$('.add-tags').tagsinput('removeAll');
		});

		$('.btn-save').unbind('click').bind('click', function(){
			_self.lg(' .btn-save click ');
			$(this).attr('disabled', true);
			var words = $('.add-tags').val();
			var submitWords = words.split(/,|，/);
			$.each(submitWords, function(i, item){
				_self.lg("i=" + item);
			});
			var data = {};
			data.words = submitWords;
			var myObj = this;
			$.ajaxInvoke({
				type : "post",
				url : _self.apiContextPath + "/sensitivewords?" + Math.random(),
				dataType : "json",
				data : JSON.stringify(data),
				success : function(content, status) {
					_self.lg(content);
					_self.lg(status);
					_self.lg('successful to submit : ' + words);
					mAlert("保存成功");
				},
				error : function(data) {
					_self.lg(data);
					mAlert("保存失败");
					$(myObj).attr('disabled', false);
				}
			});
		});
		
		$('.delete-tags').tagsinput({
			  itemValue: 'value',
			  itemText: 'text',
			  freeInput:false,
		});
		$('.delete-tags').bind('itemRemoved', function(event) {
			  // event.item: contains the item
			_self.lg('  ---  itemRemoved  ---- ');
			_self.lg(event.item);
			mConfirm('确定删除敏感词【' + event.item.text + '】？',function(){
				_self.deleteWord(event.item, _self);
			}, '', '', '', function(){
				$('.delete-tags').tagsinput('add', event.item);
			});
		});
		
		$('.add-tags').tagsinput({
			freeInput:true,
			allowDuplicates: false,
			trimValue: true,
		});
		$('.add-tags').bind('itemAdded', function(event){
			_self.lg('  ---  itemAdded  ---- ');
			$('.btn-save').attr('disabled', false);
		});
		$("#searchWord").pureClearButton();
	},

	initPagination: function(self){
		
		var _self = self;
		var count = _self.totalCount;
		var size = _self.pageLength;
		_self.lg("count:" + count + "; size:" + size);
		
		if(count){
			$('#errorMsg').empty();
	        $(".pagination").jqPaginator({
	        	totalCounts: count, //totalPages: Math.ceil(_self.totalCount / _self.pageLength),
	        	pageSize:size,
	            visiblePages: 10,
	            currentPage: 1,
	            first: '<li class="first"><a href="javascript:void(0);">第一页<\/a><\/li>',
	            prev: '<li class="prev"><a href="javascript:void(0);">上一页<\/a><\/li>',
	            next: '<li class="next"><a href="javascript:void(0);">下一页<\/a><\/li>',
	            last: '<li class="last"><a href="javascript:void(0);">最后一页<\/a><\/li>',
	            page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
	            
	            onPageChange: function (n) {
	            	_self.loadWords(n, 0, _self);
	            }
	        });
	        $('.wordslist').show();
		}else{
	        $('#errorMsg').html('<font color=red>没有数据！</font>');
	        $('.wordslist').hide();
		}
	},
		
	deleteWord:function(obj, self){
		var _self = self;
		var pid = obj.value;
		$.ajaxInvoke({
			type : "delete", // 1/orgs/{orgId}/orgusers/{orgUserId}
			url : _self.apiContextPath + "/sensitivewords/" + pid + "?" + Math.random(),
			dataType : "json",
			success : function(content, status) {
				_self.lg(content);
				_self.lg(status);
				_self.lg("successful to delete "  + pid);
			},
			error : function(data) {
				_self.lg(data);
			}
		});
	},
	loadWords:function(n, initPagination, self){
		_self = self;
		var postData = {}; 
		postData.orderby = "createTime";
		postData.direction = "desc";
		postData.limit = _self.pageLength;
		postData.pageSize = postData.limit;
		postData.offset = (n-1) * postData.limit;
		
		var searchWord = $('#searchWord').val();
		postData.searchkey = searchWord;
		
		var x = '';
		$.ajaxInvoke({
			type : "get",
			url : _self.apiContextPath + "/sensitivewords/search" + "?" + Math.random(),
			dataType : "json",
			data : postData,
			success : function(content, status) {
				_self.lg(content);
				_self.totalCount = content.paging.count;
				var length = content.datas.length;
				$('.delete-tags').tagsinput('removeAll');
				$.each(content.datas, function(i, item){
					$('.delete-tags').tagsinput('add', { "value": item.pid , "text": item.word  });
					
				});
				

				_self.lg(_self);
				if(initPagination){
					_self.initPagination(_self);
				}
			},
			error : function(data) {
				_self.lg(data);
			}
		});
        
	},
}
$(document).ready(page.init());