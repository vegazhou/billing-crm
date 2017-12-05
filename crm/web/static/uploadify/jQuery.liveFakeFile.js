(function($){
$.fn.liveFakeFile = function(o){
	var settings = {
			reg:"png|jpe?g|gif|bmp",
			btnText:'浏览图片',
			blankImg: "img/blank.gif",
			error: '图片格式不正确',
			imgText:'你已经选择文件'
	};
	var ie7 = $.browser.msie&&($.browser.version == "7.0");
	var moz3 = $.browser.mozilla&&($.browser.version == "3.0");
	return this.each(function(i,v){
		if(o) settings = $.extend(settings, o);
		var wrap = $('<div class="fakefile"></div>');
		var button = $('<button class="uploadify-button swfupload" style="height: 30px; line-height: 30px; width: 120px;"></button>');
		var parent = $(this).parent('.upload-img-btn');
		button.append(settings.btnText);
		wrap.append(button);
		$(this).attr("id",'uploadFile'+(1+i)).appendTo(wrap);
		parent.append(wrap);
		$(this).bind('change',function(){
			var val = $(this).val();
			var imgName = val.slice(val.lastIndexOf("\\")+1);
			if(val.match(new RegExp(".(" + settings.reg + ")$", "i"))){
			}else{
			}
		});
	})
}
})(jQuery)