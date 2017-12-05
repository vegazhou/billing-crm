/**
 * parse data table
 */


var adminContextPath = "/api";
var imgUploadedURI = "http://123.59.58.39:88";
var newsUploadLocation = "/data/news";

(function(win){
	var isHistoryFlag = $("#isHistory").val() ;
	$("#isHistory").val("0") ;
	var loadFirst = true ;
	var postDT = {
		parseDT : function( sSource , aoData , fnCallback ){
					var contextArgs = {} ;
					if(self.frameElement && self.frameElement.tagName == "IFRAME" && parent.window.contextArgs){
						contextArgs = parent.window.contextArgs ;
					}
					$("[name^='s_']").each(function(){
						var name = $(this).attr("name") ;
						var value = $(this).val() ;
						if(self.frameElement && self.frameElement.tagName == "IFRAME" 
							&& parent.window.contextArgs && loadFirst && isHistoryFlag =="0"  ){
							if( parent.window.contextArgs[name] ){
								 value =  parent.window.contextArgs[name]  ;
								 $("[name='" + name + "']").val( parent.window.contextArgs[name] ) ;
								 $("select[name='"+name+"']").attr("preVal" , parent.window.contextArgs[name] ) ;
							}
						}
						aoData.push({"name" : name , "value" : value }) ;
						contextArgs[name] = value ;
					});
					if(self.frameElement && self.frameElement.tagName == "IFRAME" ){
						parent.window.contextArgs = contextArgs ;
					}
					url = adminServer + sSource.replace('/lecaiadminapi', adminContextPath) ;
					$.ajax({
						url 	: 	url ,
						type	: 	"post" ,
						async	:	true ,
						data	:	aoData ,
						success	:	fnCallback ,
						dataType:	"json" ,
						headers : {'Source' : '101','Token':token},
						statusCode	:	{
							401	:	function(data){
										console.error("401:"+sSource) ;
										location.href= CTX_ROOT + "/login.jsp?statusCode=401&url="+sSource ;
									},
							404	:	function(){
										console.error("404:"+sSource) ;
									},
							500	:	function(){
										console.error("500:"+sSource) ;
									},
							408	:	function(){
										console.error("408:"+sSource) ;
										//location.href=G_CTX_ROOT+"/login?statusCode=408&url="+sSource ;
									},
						},
						
					});
					loadFirst = false ;
					$("#selfFlag").val("1");
				  },
		paseStatus:	function( data , status , fnCallback ){
						
					} ,
	};
	win.util = postDT ;
})(window) ;


$(".hide-tab-panel").show();
$('.slide-tab li.active').addClass("selected1");
$(".slide-tab li.active").click(function(){
    $(".hide-tab-panel").slideToggle("fast");
    $(this).toggleClass("selected1");
});

/**
*	DataTable Setting
*/
var dataTableLan = {                         
	    "sLengthMenu": "每页显示 _MENU_ 条记录",
	    "sZeroRecords": "没有检索到数据",
	    "sInfo": "共有 _TOTAL_ 条记录",
	    "sInfoEmtpy": "没有数据",
	    "sProcessing":  "<img src='" + CTX_ROOT + "/static/img/lding.gif'/>",
	    "search" : "查询" ,
	    "sInfoEmpty": "共 0 条记录",
	    "bStateSave": false,
	    "oPaginate": {
	        "sFirst": "首页",
	        "sPrevious": "前页",
	        "sNext": "后页",
	        "sLast": "尾页"
	    }
	};
//DataTable language setting
(function(win){
	var lan = dataTableLan;
	var setting = {} ;	
	setting.oLanguage = lan ;	//语言设置 
	setting.bPaginate = true ;  //是否分页。
	setting.sPaginationType =  "full_numbers" ;  //全分页
	setting.bJQueryUI = false ;  //jQueryUI
	setting.bAutoWidth = true ; //自动计算Table宽度
	setting.bProcessing =  true ; //当datatable获取数据时候是否显示正在处理提示信息。
	setting.sServerMethod = "POST";
	setting.bServerSide = true ;
	setting.bFilter = false;
	setting.bStateSave = true ;
	//setting.sDom="<t<'span12 center'p>>";
	setting.aLengthMenu = [[10, 25, 50], [10, 25, 50]] ;
	setting.callbackStack = {
			stack	: [] ,
	};
	setting.callbackStack.stack.push(
		function(){
	
		
			$.checkCasecade({
				parent : "checkbox-checkall" ,
				children : "item-checkbox" 
			});		
		}
	);
	setting.fnDrawCallback = function(){	
		
		for(var callback in setting.callbackStack.stack ){
			setting.callbackStack.stack[callback]() ;
		}		
		$("#datatable_tabletools tr:last-child").css("border-bottom","1px solid #CCCCCC");
	};
	
	
	setting.fnInitComplete = function(setting){		
		$("#datatable_tabletools").css("width","100%");
		//$("#datatable_tabletools th").css("width","auto");
		//$("#datatable_tabletools tr:last-child").css("border-bottom","1px solid #CCCCCC");
	
		
	};
	
	
	setting.fnRowCallback = function(nRow, aData, iDisplayIndex, iDisplayIndexFull){

		//$('tr', nRow).css("border-bottom","1px solid #CCCCCC");
		//$("#datatable_tabletools tr:last-child").css("border-bottom","1px solid #CCCCCC");
	};
	
	setting.fnServerData = function( sSource , aoData , fnCallback ){
		util.parseDT(sSource , aoData , fnCallback ) ;
		$('.checkbox-checkall').prop("checked",false);
	};
	var dataTable ;
	win.setting = setting ;
	win.dataTable = dataTable ;
	
	
})(window) ;





/**
*	Modal Message Alert
*/
//Alert Modal

(function(win){
	var mAlert = function (bMsg , title  , btnMsg ){
		var bodyMsg = typeof(bMsg) == "undefined" || bMsg == "" ? "" : bMsg;
		var titleMsg = typeof(title) == "undefined" || title == "" ? "提示！" : title;
		var buttonMsg = typeof(btnMsg) == "undefined" || btnMsg == "" ? "关闭" : btnMsg;
		$("#alertModelLabel").html( titleMsg ) ;
		$("#alertModelBody").html( bodyMsg ) ;
		$("#alertModelBtnClose").html( buttonMsg ) ;
		$("#alertModal").modal("show");
	};
	win.mAlert = mAlert ;
})(window);

(function(win){
	var mConfirm = function ( bMsg , callBack , title  , btnClose , btnActive, closeCallBack ){
		var bodyMsg = typeof(bMsg) == "undefined" || bMsg == "" ? "" : bMsg;
		var titleMsg = typeof(title) == "undefined" || title == "" ? "提示！" : title;
		var btnCloseMsg = typeof(btnClose) == "undefined" || btnClose == "" ? "取消" : btnClose;
		var btnActiveMsg = typeof(btnActive) == "undefined" || btnActive == "" ? "确定" : btnActive;
		$("#confirmModelLabel").html( titleMsg ) ;
		$("#confirmModelBody").html( bodyMsg ) ;
		$("#confirmModelBtnClose").html( btnCloseMsg ) ;
		$("#confirmModelBtnActive").html( btnActiveMsg ) ;
		if( typeof(callBack) == "function" ){
			$("#confirmModelBtnActive").unbind("click");
			$("#confirmModelBtnActive").click(callBack) ;
		} 
		
		if( typeof(closeCallBack) == "function" ){
			$("#confirmModelBtnClose").unbind("click");
			$("#confirmModelBtnClose").click(closeCallBack) ;
		} 
		$("#confirmModal").modal("show");
	};
	win.mConfirm = mConfirm ;
})(window);


(function(win){
	var timer = "" ;
	var task = function(){
		$("#success-box").addClass("hidden") ;
		$("#warning-box").addClass("hidden") ;
	};
	var msgBox = function( type , body , title ){
		if( type == "fail" ){
			typeof( body ) != "undefined" ? $("#warning-box-body").text(body) :  $("#warning-box-body").text("") ;
			typeof( title ) != "undefined" ? $("#warning-box-title").text(title) :  $("#warning-box-title").text("警告！") ;
			$("#warning-box").removeClass("hidden") ;
		}else{
			typeof( body ) != "undefined" ? $("#success-box-body").text(body) :  $("#success-box-body").text("") ;
			typeof( title ) != "undefined" ? $("#success-box-title").text(title) :  $("#success-box-title").text("提示！") ;
			$("#success-box").removeClass("hidden") ;
		}
		timer = setTimeout(task , 5000 ) ;
	};
	
	$("#warning-box .close").html("");
	$("#success-box .close").html("");
	win.msgBox = msgBox ;

	
})(window) ;
/**
*	Ajax Invoke
*/
(function(jqeury){
	var ajaxInvoke = function ( args ) {
		var url = args.url ;
		var enumType = ["get","GET","post","POST","put","PUT","delete","DELETE"];
		var type = typeof( args.type ) != "undefined" && enumType.indexOf(args.type)!= -1? args.type : "post" ;
	
		var data = typeof( args.data ) != "undefined" ? args.data : "" ;
		var sync = typeof( args.sync ) != "boolean" ? true : args.sync ;
		var success = function(data){} ;
		if( typeof(args.success) == "string" ){
			success = function(data , state){
				mAlert( args.success ) ;
			};
		}else if( typeof(args.success) == "function" ){
			success = args.success ;
		}else{
			success = function( data , status ){
			
			} ;
		}
		
		var error = null ;
		if(typeof(args.error) == "string"){
			error = function(){
				mAlert( args.error ) ;
			};
		}else if( typeof( args.error ) == "function" ){
			error = args.error ;
		}else{
			error = function (data , status ) {
				/**
				if( status == "error" ){
					mAlert("请求失败！");
				}else if( status == "parsererror"){
					mAlert("对象转换失败！");
				}
				*/
			} ;
		}
		var enumDataType = ["html" , "json" ] ;
		
		var dataType = typeof( args.dataType ) != "undefined" && enumDataType.indexOf(args.dataType) != -1 ? args.dataType :"json" ;
		url = adminServer + url.replace('/adminapi', adminContextPath) ;
		//console.log(url);
		$.ajax({
			url		: 	url,
			type	: 	type ,
			async	: 	sync ,
			data 	: 	data ,
			success	: 	success,
			error	: 	error,
			contentType: "application/json; charset=utf-8",
			headers : {'Source' : '101','Token':token},
			statusCode	:	{
				400: function(data) {
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "biz_charge_not_compatible") {
						msgBox('fail', "操作失败，所选业务组成和计费组成无法匹配，请另行选择！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "biz_scheme_is_in_usage") {
						msgBox('fail', "操作失败，该业务组成已经生效！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "biz_scheme_is_not_in_effect") {
						msgBox('fail', "操作失败，该业务组成尚未生效！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "biz_scheme_not_found") {
						msgBox('fail', "操作失败，业务组成不存在！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "charge_scheme_is_in_usage") {
						msgBox('fail', "操作失败，该计费组成已经生效！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "charge_scheme_is_not_in_effect") {
						msgBox('fail', "操作失败，该计费组成尚未生效！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "charge_scheme_not_found") {
						msgBox('fail', "操作失败，计费组成不存在！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "contract_is_in_effect") {
						msgBox('fail', "操作失败，该合同已经生效！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "contract_not_found") {
						msgBox('fail', "操作失败，合同不存在！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "customer_has_signed_contract") {
						msgBox('fail', "无法删除已签单客户！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "customer_not_found") {
						msgBox('fail', "操作失败，客户不存在！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "duplicated_webex_site_draft") {
						msgBox('fail', "操作失败，已存在同名的WebEx站点草案！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "editing_non_draft_product") {
						msgBox('fail', "操作失败，无法编辑该产品信息！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "entity_not_found") {
						msgBox('fail', "发生不可知的内部系统错误，请联系管理员！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "illegal_charge_scheme_argument") {
						msgBox('fail', "发生不可知的内部系统错误，请联系管理员！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "insufficient_privilege") {
						msgBox('fail', "操作失败，你正尝试某些越权操作！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "invalid_date_format") {
						msgBox('fail', "操作失败，输入的时间格式不正确，请再次确认！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "invalid_json_object") {
						msgBox('fail', "操作失败，输入的请求格式不正确，请再次确认！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "invalid_language_value") {
						msgBox('fail', "操作失败，输入的站点语言格式不正确，请再次确认！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "invalid_location") {
						msgBox('fail', "操作失败，输入的站点电话区域表不正确，请再次确认！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "invalid_order_start_date") {
						msgBox('fail', "操作失败，输入的订单起始时间不能早于当前系统时间！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "invalid_ports") {
						msgBox('fail', "操作失败，你所输入最大与会人数限制不在允许范围内！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "invalid_site") {
						msgBox('fail', "操作失败，你所填写的WebEx站点不在可选范围内！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "invalid_webex_sitename") {
						msgBox('fail', "操作失败，你所输入的WebEx站点名称不合法，仅限字母、数字、减号和下划线！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "no_order_in_contract") {
						msgBox('fail', "操作失败，你所提交的合同中未填写任何订单！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "not_a_biz_scheme_template") {
						msgBox('fail', "发生不可知的内部系统错误，请联系管理员！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "not_a_charge_scheme_template") {
						msgBox('fail', "发生不可知的内部系统错误，请联系管理员！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "only_drafting_contract_allowed") {
						msgBox('fail', "操作失败，该操作仅限于起草中的合同！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "only_drafting_order_allowed") {
						msgBox('fail', "操作失败，该操作仅限于起草中的订单！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "only_drafting_product_allowed") {
						msgBox('fail', "操作失败，该操作仅限于起草中的产品！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "only_drafting_scheme_allowed") {
						msgBox('fail', "操作失败，该操作仅限于起草中的方案！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "only_in_effective_product_allowed") {
						msgBox('fail', "操作失败，该操作仅限于正式产品！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "only_waiting_approval_contract_allowed") {
						msgBox('fail', "操作失败，该操作仅限于待审合同！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "only_waiting_approval_order_allowed") {
						msgBox('fail', "操作失败，该操作仅限于待审订单！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "only_waiting_approval_product_allowed") {
						msgBox('fail', "操作失败，该操作仅限于待审产品！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "only_waiting_approval_scheme_allowed") {
						msgBox('fail', "操作失败，该操作仅限于待审方案！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "order_is_in_effect") {
						msgBox('fail', "操作失败，该订单已经生效！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "order_not_found") {
						msgBox('fail', "操作失败，订单不存在！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "product_not_found") {
						msgBox('fail', "操作失败，产品不存在！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "site_already_existed") {
						msgBox('fail', "操作失败，已有WebEx站点使用了该名称！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "site_does_not_belong_to_any_order") {
						msgBox('fail', "操作失败，该站点未关联任何订单！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "site_draft_is_in_usage") {
						msgBox('fail', "操作失败，该WebEx站点草案已被某个订单使用！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "user_already_existed") {
						msgBox('fail', "操作失败，用户已经存在！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "user_not_existed") {
						msgBox('fail', "操作失败，输入的用户名在统一用户中心不存在！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "webex_site_draft_not_found") {
						msgBox('fail', "操作失败，你所输入的WebEx站点草案不存在！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "order_collision_detected") {
						msgBox('fail', "操作失败，合同订单存在冲突项！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "contract_amount_less_than_original") {
						msgBox('fail', "操作失败，变更后合同总金额小于原合同总金额！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "agent_has_agent_product") {
						msgBox('fail', "操作失败，该代理商已有代理产品！");
						return;
					}
					if (data.status == "400" && JSON.parse(data.responseText).error.key == "agent_has_contract") {
						msgBox('fail', "操作失败，该代理商已有代理合同！");
						return;
					}
				},
				
				401	:	function(data){
							console.error("--401:"+url) ;
							$.cookie('iPlanetDirectoryPro', '567', { expires: -1 ,domain:'.ketianyun.com',path:'/'});							
							$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/api'});
							$.cookie('JSESSIONID', '5671',{ expires: -1 ,path:'/api/'});
							location.href=SSO_URL + "/XUI/#login/&goto=" + ADMIN_URL;
							if(JSON.parse(data.responseText).error.message=="Old password is not correct."){
	                             msgBox("fail", "原始密码不正确");
							}else{
							location.href= CTX_ROOT + "/login.jsp?statusCode=401&url="+url ;
							}
						},
				404	:	function(){
							console.error("404:"+url) ;
							//location.href="/admin/login?statusCode=404&url="+url ;
						},
				408	:	function(){
							console.error("408:"+url) ;
							//location.href=G_CTX_ROOT+"/login?statusCode=408&url="+url ;
						},
				500	:	function(){
							console.error("500:"+url) ;
							//location.href="/admin/login?statusCode=500&url="+url ;
						},
					},
			dataType: 	dataType ,
			cache	:	false,
		});
	};
	jqeury.ajaxInvoke = ajaxInvoke ;
})($);





/**
 * Validator
 */
function DataValidator(){
	
	this.rule = {} ;
	this.isBlur = true ; 
	var required = function(field , active , message){
		if( active == "false" ) return true ;
		var msg = message + "不能为空!" ;
		var val = $("#"+field).val() ;
		var bRtn = val!=null && typeof(val) != "undefined" && val.replace(/(^\s*)|(\s*$)/g,"") != "" ;		
		setMsg(field , msg , bRtn ) ;
		return  bRtn;
	};
	var gt = function( field , number , message ){
		if($("#"+field).val()=="")return true ;
		var msg = message + "长度大于等于"+number+"!" ;
		var val = $("#"+field).val() ;
		var bRtn = val.length >= parseInt( number ) ;
		setMsg(field , msg , bRtn ) ;
		return  bRtn;
	} ;
	var lt = function( field , number , message ){
		if($("#"+field).val()=="")return true ;
		var msg = message + "长度小于等于"+number+"!" ;
		var val = $("#"+field).val() ;
		var bRtn = val.length <= parseInt( number ) ;
		setMsg(field , msg , bRtn ) ;
		return  bRtn;
	};
	var email = function( field , active , message ){
		if($("#"+field).val()=="")return true ;
		if( active == "false")return true ;
		var msg = message + "email格式不正确"+"!" ;
		 var pattern=/^([a-zA-Z0-9\._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
		 var val = $("#"+field).val() ;
		 var bRtn =pattern.test(val);
		 setMsg(field , msg , bRtn ) ;
		return  bRtn;
	};
	var date = function( field , active , message ){
		if($("#"+field).val()=="")return true ;
		if( active == "false")return true ;
		var msg = message + "日期格式不正确"+"!" ;
		 var pattern=/^(((20[0-9][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$/; 
		 var val = $("#"+field).val() ;
		 var bRtn =pattern.test(val);
		 setMsg(field , msg , bRtn ) ;
		return  bRtn;
	};
	var phone = function( field , active , message ){
		if($("#"+field).val()=="")return true ;
		if( active == "false")return true ;
		var msg = message + "格式不正确";
		//var isMobile=/^1[3|4|5|8][0-9]\d{4,8}$/; 
		var isMobile=/^[0-9]{11}$/;
		var bRtn = isMobile.test($("#"+field).val()) ;
		setMsg(field , msg , bRtn ) ;
		return bRtn ;
	};
	var isNum = function( field , active , message ){
		if($("#"+field).val()=="")return true ;
		if( active == "false" ) return true ;
		var msg = message + "不是数字类型" ;
		var bRtn = !isNaN( $("#"+field).val()) ;
		setMsg(field , msg , bRtn ) ;
		return bRtn ;
		
	};
	var notNum = function( field , active , message ){
		if($("#"+field).val()=="")return true ;
		if( active == "false" ) return true ;
		var msg = message + "不能全部是数字" ;
		var bRtn = isNaN( $("#"+field).val()) ;
		setMsg(field , msg , bRtn ) ;
		return bRtn ;
		
	};
	var eq = function( field , val , message ){
		if($("#field").val() == "" ) return true ;
		var msg = message + "不一致" ;
		var bRtn = ($("#"+field).val() == $("#"+val).val()) ;
		setMsg( field , msg , bRtn ) ;
		return bRtn ;
	};

	var notnegative = function( field , val , message ){
		if($("#field").val() == "" ) return true ;
		var msg = message + "不能小于0" ;
		var bRtn = ($("#"+field).val() >= 0) ;
		setMsg( field , msg , bRtn ) ;
		return bRtn ;
	};
	
	var notzero = function( field , val , message ){
		if($("#field").val() == "" ) return true ;
		var msg = message + "不能小于0" ;
		var bRtn = ($("#"+field).val() > 0) ;
		setMsg( field , msg , bRtn ) ;
		return bRtn ;
	};
	var fixLen = function( field , val , message ){
		var msg = message + "长度应为"+ val + "位" ;
		var bRtn = ($("#"+field).val().length == val) ;
		setMsg( field , msg , bRtn ) ;
		return bRtn ;
	};
	var setMsg = function( field , msg , b){	
		if(b){
			$("#"+field).nextAll().filter("span").html("") ;
			return ;
		}
		$("#"+field).nextAll().filter("span").html("<font color=red >" +msg+ "</font>") ;
	};
	this.clearForm = function(){
		for( var field in this.rule ){
			if( this.rule[field].defaultValue != "undefined" ){
				$("#"+field).val( this.rule[field].defaultValue ) ;
			}else{
				$("#"+field).val("") ;
			}
			setMsg( field , "" ) ;
		}
	};
	
	var Callback = function(){
		var blurCallbackStack = [];
		var focusCallbackStack = [] ;
		this.setBlurCallback = function(bcs){
			blurCallbackStack = bcs ;
		};
		this.setFocusCallback = function(fcs){
			focusCallbackStack = fcs ;
		};
		this.blureCallback = function(){
			if( blurCallbackStack.length  == 0 ) return false ;
			for( var bc in blurCallbackStack ){
				if( typeof( blurCallbackStack[bc] ) == "function" ){
					if(!blurCallbackStack[bc]() ) break;
				}else if( typeof(blurCallbackStack[bc]) == "string"){
					if(!eval(blurCallbackStack[bc]))break ;
				}
			}
		};
		this.focusCallback = function(){
			if(focusCallbackStack.length == 0 ) return false ;
			for(var fc in focusCallbackStack ){
				if( typeof(focusCallbackStack[fc]) == "function"){
					if(!focusCallbackStack[fc]()) break ;
				}else if(typeof(focusCallbackStack[fc]) == "string"){
					if(!eval(focusCallbackStack[fc]))break ;
				}
				
			}
		};
	};
	
	this.init = function( rule ){
		this.rule = rule ;
		for( var r in rule ){
			var blurCallbackStack = [] ;
			var focusCallbackStack = [] ;
			if(! document.getElementById(r)){

			}
			
			
			var field = r ;
			var name = rule[r].name ;
			var ms = rule[r].method ;
			for( var m in ms ){
				var func = m + "('" + field + "','" + ms[m]+"','" + name +"')" ; 
				blurCallbackStack.push(func) ;
			}
			if( typeof( rule[r].onBlur ) == "function" ){
				blurCallbackStack.push( rule[r].onBlur );
			}
			if( typeof( rule[r].onFocus ) == "function" ){
				focusCallbackStack.push( rule[r].onFocus );
			}
			var callBackObj = new Callback() ;
			callBackObj.setBlurCallback(blurCallbackStack) ;
			callBackObj.setFocusCallback(focusCallbackStack) ;
			if(document.getElementById(r)){
				document.getElementById(r).onblur = callBackObj.blureCallback ;
				document.getElementById(r).onfocus = callBackObj.focusCallback ;
			}
			
		}
	};
	this.validate = function(){
		var success = true ;
		for( var field in this.rule ){
			var fieldSuccess = true;
			var msg = this.rule[field].name ;
			var vs = this.rule[field].method ;
			for( var m in vs ){
				var func = m+"('"+field+"' , '"+vs[m]+"' , '"+ msg +"' )" ;
				if( !eval( func) ){
					success = false ;
					fieldSuccess = false;
					break ;
				}
			}
			
			if(fieldSuccess){
				$("#"+field).nextAll().filter("span").html("") ;
			}
			
		}
		return success ;
	};
}
(function(win){win.validator = new DataValidator() ;})(window) ;

/**
 * 省市区级联操作
 * 使用方法
 * 
 * 方法描述
 * load( parentId ) 根据parentId加载选项
 * init 初始化控件 使用参数依次为 标签ID、ajaxSource、下一结点对象、默认第一行显示文字【key为-1】
 * 使用方法
 * var selCasecade = new SelectCasecade() ;
 * var selCity = new SelectCasecade() ;
 * var selArea = new SelectCasecade() ;
 * selCasecade.init("provence" ,"${ctx}/sysArea/findProvinces" , selCity , "请选择省" ).load() ;
 * selCity.init("city" ,"${ctx}/sysArea/findCities" , selArea , "请选择市") ;
 * selArea.init("area" , "${ctx}/sysArea/findAreas" ,"" , "请选择区" ) ;
 */
function SelectCasecade(){
	var field ;
	var ajaxSource ;
	var nextNode ;
	var key ;
	var fillValue = "" ;
	var header ;
	var dataAllFlag = false ;
	var selectedIndex = 0 ;
	this.load = function( parentId , callback,nodeid,value ){
		var url = ajaxSource ;
		if( parentId == "" ){
			parentId = "-2" ;
		}
		if( typeof( parentId ) != "undefined" && parentId != "-1"){
			url +=  "/" + parentId ;
		}

		$.ajaxInvoke({
			url		:	url ,
			type	:	"post" ,
			dtaType	:	"json" ,
			cache	:	false,
			data	:	{
							dataAllFlag  : dataAllFlag ,
						},
			success	:	function( data , status ){
							if( data.state == "fail" ){
								mAlert( data.msg ) ;
							}else{
								initOption( data.data ) ;
								if( typeof(fillValue)!="undefined" && fillValue!="" ){
									$("#"+field).val(fillValue) ;
								}
								if( selectedIndex != 0 && selectedIndex < $("#"+field).get(0).length ){
									$("#"+field).get(0).selectedIndex =  selectedIndex ;
								}
								if( $("#"+field).attr("preVal") &&  $("#"+field).attr("preVal")!= "" ){
									$("#"+field).val($("#"+field).attr("preVal")) ;
								}
								selectedIndex = 0 ;
								if( typeof( nextNode ) == "object"){
									childNodeLoad() ;
								}
							}
						},
			sync	:	false 
		});
		
		if(typeof( callback ) == "function"){
			callback();
		}
	};
	this.setValue = function ( value ){
		fillValue = value ;
	};
	this.setSelectedIndex = function( index ){
		selectedIndex = index ;
	};
	this.setDataScopeAll = function( flag ){
		if( typeof( flag ) == "boolean" ){
			dataAllFlag = flag ;
		}
	};
	var initOption = function( data ){
		$("#"+field).empty() ;
		if( data.length != 1 ){
			if( typeof( header ) != "undefined" ){
				$("#"+field).append("<option value=''>"+header+"</option>") ;
			}
		}
		data.sort(function(a,b){
			if(a && b){
				return a.name.localeCompare(b.name)
			};//汉字拼音排序方法
		});
		
		for( i = 0 ; i < data.length ; i ++ ){
			$("#"+field).append("<option value='"+data[i].id+"'>"
					+data[i].name+"</option>") ;
		}
		if( data.length == 1 && typeof(nextNode) == "object" ){
			nextNode.load($("#"+field+" option:first").val());
		}
		
	};
	var childNodeLoad = function(){
		nextNode.load( $("#"+field).val() ) ;
	};
	this. init = function( argField , argAjaxSource , argNextNode 
			,  argHeader ,  argValue  ){
		
		if( typeof($("#"+argField).val()) == "undefined" ){
			return this;
		}
		field = argField ;
		ajaxSource = argAjaxSource ;
		nextNode = argNextNode ;
		header = argHeader ;
		fillValue = argValue ;
		
		if( typeof(argNextNode) == "object"){
			nextNode = argNextNode ;
			document.getElementById(field).onchange=childNodeLoad ;
		}
		$("#"+field).empty() ;
		if( typeof( header ) != "undefined" ){
			$("#"+field).append("<option value=''>"+header+"</option>") ;
		}
		return this ;
	};
	this.clearOption = function(){
		$("#"+field).empty() ;
		if( typeof( header ) != "undefined" ){
			$("#"+field).append("<option value=''>"+header+"</option>") ;
		}
	};
	this.val = function(value){
		if(typeof( value ) != "undefined"){
			if( typeof(argNextNode) == "object"){
				childNodeLoad() ;
			}
			return $("#"+field).val(value) ;
		}else{
			return $("#"+field).val() ;
		}
	};

};
/**
 *  	$.checkCasecade({
			parent : "checkbox-checkall" ,
			children : "item-checkbox" 
		});
 */
(function(jquery){
	var checkCasecade = function (arg){
		
		var parent = arg.parent ;
		var children = arg.children ;
		gloParent = parent ;
		gloChildren = children ;
		$("."+parent).unbind("change").bind("change",function(){
			changeChild( children , $(this).is(":checked") ) ;
		});
		
	};
	
	var changeChild = function( name , status ){
			$("."+name).prop("checked" , status );
			
	};
	var checkValues = function( arg ){
		var className = "item-checkbox";
		if(typeof( arg ) != "undefined" ){
			className = arg ;
		}
		var values = [] ;
		$("."+className).each(function(){
			if($(this).is(":checked")){
				values.push($(this).val()) ;
			}
		});
		return values ;
	};
	jquery.checkCasecade = checkCasecade ;
	jquery.checkedValues = checkValues ;
})($) ;


(function(){
	if(self.frameElement && self.frameElement.tagName == "IFRAME") {
		document.body.style.overflow = "scroll" ;
	}
})() ;

/**
*	combox
*/
(function ( jquery ){
	var funBox = function( arg ){
		var combox = {
				arg		:	{
								size	:	10 ,
							},
				data	:	{
					
							},
				init	:	function( arg ){
								var _self = this;
								if( typeof(arg.url) == "string" && arg.url != ""  ){
									combox.arg.url = arg.url ;
								}else{
									console.error("arg.url is undefined") ;
									return ;
								}
								if( typeof(arg.size) == "number" ){
									combox.arg.size = arg.size ;
								}
								if( typeof(arg['z-index']) == "number" ){
									combox.arg['z-index'] = arg['z-index'] ;
								}
								if( typeof( arg.key ) == "string" && arg.key != "" ){
									combox.arg.key = arg.key ;
								}else{
									console.error("arg.key is undefined") ;
									return ;
								}
								if( typeof( arg.value ) == "string" && arg.value != "" ){
									combox.arg.value = arg.value ;
								}else{
									console.error("arg.value is undefined") ;
									return ;
								}
								if( typeof( arg.search ) == "string" && arg.search != "" ){
									combox.arg.search = arg.search ;
								}else{
									console.error("arg.search is undefined") ;
									return ;
								}
								if( typeof( arg.field ) == "string" && arg.field != "" ){
									combox.arg.field = arg.field ;
								}else{
									console.error("arg.field is undefined") ;
									return ;
								}
								if( typeof(arg.callBack) == "function" ){
									combox.callBack = arg.callBack ;
								}

								if( typeof( arg.callbackField ) == "string" && arg.key != "" ){
									combox.arg.callbackField = arg.callbackField ;
								}
								if( typeof( arg.blurCallback ) == "function" ){
									combox.arg.blurCallback = arg.blurCallback ;
									$("#"+combox.arg.field).blur(function(){
										arg.blurCallback() ;
									});
								}
								$("#"+combox.arg.field).unbind().bind('keyup', function(){
									combox.loadData(_self);
								}) ;
								$("#"+combox.arg.field).click(function(){
									$('#'+combox.arg.field).next("ul").toggle() ;
									if($('#'+combox.arg.field).css("display")!="none"){
										combox.loadData(_self) ;
									}
								});
								$("#"+combox.arg.field).blur(function(){
									setTimeout(function(){
										$('#'+combox.arg.field).next("ul").hide() ;
										if(!combox.arg.selected && !$("#"+combox.arg.field).val()){
											$("#"+combox.arg.field).val('');
											$("#"+combox.arg.field).attr('val', '');
										}
									},300);
								});
								var style = '';
								if(combox.arg['z-index']){
									style="z-index:" + combox.arg['z-index'];
								}
								$('#'+combox.arg.field).after('<ul class="dropdown-menu" role="menu"' + style + '></ul><span></span>');
								
								return combox ;
							},
				loadData:	function(self){
								var _self = self;
								combox.arg.selected = false;
								var value = $("#"+combox.arg.field).val() ;
								if( value == combox.oldValue ){
									return ;
								}
								combox.selectedValue.inputBody = value ;
								combox.selectedValue.value ="" ;
								combox.selectedValue.text = "" ;
								if( typeof(combox.callBack) == "function" ){
									(combox.callBack)(combox.selectedValue.value, combox.arg.callbackField) ;
								}
								
								$('#'+combox.arg.field).next('ul').empty() ;
								if( typeof(value) == "undefined" ) return  ;
								value = value.replace(/(^\s+)|(\s+$)/g,"");
								if( value ==  "" ){
									$('#'+combox.arg.field).attr("val", value);
								}
								url = combox.arg.url ;
								type = combox.arg.type ;
								
								var searchKey = arg.search || args;
								var postData = {
										random: Math.random(),
								};
								postData[searchKey] = value;
								$('#'+combox.arg.field).next("ul").css("width" , $('#'+combox.arg.field).css('width'));
								$('#'+combox.arg.field).addClass('ui-autocomplete-loading');
								
								
								$.ajax({
									url		:	url,
									type    :   type,
									data	:	postData,
									headers : {'Source' : '101','Token':token},	
									success	:	function( content , status ){
													var data = content.datas ;
													if(data == null){
														$('#'+combox.arg.field).next('ul').hide() ;
														return ;
													}
													$[combox.arg.field] = _self;
													var count = 0 ;
													for( var d in data ){

														var id = data[d][combox.arg.key];
														var name = data[d][combox.arg.value]
														//$['#"+combox.arg.field + "']
														//$('#'+combox.arg.field).next('ul').append("<li><div title='"+(name)+"' style='padding-left:10px;padding-right:10px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;' onclick=\"javascript:$['#"+combox.arg.field + "'].fixOpt('"+data[d][combox.arg.key]+"' , '"+data[d][combox.arg.value]+"')\">"+name+"</div></li>") ;
														$('#'+combox.arg.field).next('ul').append("<li><a title='"+(name)+"' href=\"javascript:$['"+combox.arg.field + "'].fixOpt('"+data[d][combox.arg.key]+"' , '"+data[d][combox.arg.value]+"')\">"+name+"</a></li>") ;
														if( ++ count >= combox.arg.size){
															break ;
														}
													}
													$('#'+combox.arg.field).removeClass('ui-autocomplete-loading');
													$('#'+combox.arg.field).next('ul').show() ;
												}
								});
							},
							fixOpt			:	function(value , text){
													if( typeof( value ) != "undefined" ){
														combox.selectedValue.value = value ;
														
														$('#'+combox.arg.field).attr("val", value);
														if($('#'+combox.arg.field).next('ul').next('span')){
															$('#'+combox.arg.field).next('ul').next('span').html('');	
														}
														combox.arg.selected = true;
													}
													if( typeof( value ) != "undefined" ){
														combox.selectedValue.text = text ;
														$('#'+combox.arg.field).val(text) ;
														combox.oldValue = text;
														
													}
													$('#'+combox.arg.field).next("ul").hide() ;
													if( typeof(combox.callBack) == "function" ){
														(combox.callBack)(value, combox.arg.callbackField) ;
													}
												},
							selectedValue	:	{
													value		:	"" ,
													text	:	"" ,
													inputBody	:	"" ,
												},
							val				:	function( key ){
														return combox.selectedValue[key] ;
												}
				
			};
		return combox.init( arg );
	};
	jquery.combox = funBox ;
})( $ ) ;

function dynamicSelect(){
	$(".ajaxDynamicData").each(function(){
		var src = $(this).attr("ajaxSource") ;
		var key = $(this).attr("key");
		var text = $(this).attr("text") ;
		if( src == "" || key =="" || text == "" ) return ;
		var obj = $(this) ;
		$.ajaxInvoke({
			url		:	src ,
			success	:	function( content ){
							if(content.data == null){
								return  ;
							}
							var data = content.data ;
							for( var row in data ){
								obj.append("<option value='"+data[row][key]+"'>"+data[row][text]+"</option>") ;
							}
						},
		});
	});
} ;

//Html编码获取Html转义实体
function htmlEncode(value){
  return $('<div/>').text(value).html();
}
//Html解码获取Html实体
function htmlDecode(value){
  return $('<div/>').html(value).text();
}

var adminURI = adminContextPath + '/v1';
function loadChannel(divId, self, channeltype){
	var _self = self; 
	var url = adminURI + "/dicts/mychannels?_=" + Math.random();
	// if(channeltype){
	// 	url = adminURI + "/dicts/mychannels?subtype=" + channeltype + "&_=" + Math.random();
	// }
    $.ajaxInvoke({
        url: url,
        data: {},
        success: function(content, status) {
        	_self.channelData = [];
        	_self.channelIDs = [];
			$.each(content.datas, function(i,item){
				_self.channelData[$.trim(item.channelId)] = item.channelName;
				_self.channelIDs.push($.trim(item.channelId));
			});
			
			
			content.datas.sort(function(a,b){
				if(a && b){
					return a.channelName.localeCompare(b.channelName)
				};//汉字拼音排序方法
			});
			
        	$.each(content.datas, function(i, item){
        		$(divId).append("<option value='"+ item.channelId +"'>" + item.channelName + "</option>");
        	})
        },
        type:'get'
    });
}
function loadArea(areId, divId){
    $.ajaxInvoke({
        url: adminURI + "/areas/" + areId + "/subareas?" + Math.random(),
        data: {},
        success: function(content, status) {
			content.datas.sort(function(a,b){
				if(a && b){
					return a.name.localeCompare(b.name)
				};//汉字拼音排序方法
			});
        	$.each(content.datas, function(i, item){
        		//_self.lg(item);
        		$(divId).append("<option value='"+ item.id +"'>" + item.name + "</option>");
        	})
        },
        type:'get'
    });
}
function loadIndustry(industryId, divId, callback){
    $.ajaxInvoke({
        url: adminURI + "/industries/" + industryId + "/subindustries?" + Math.random(),
        data: {},
        success: function(content, status) {
			content.datas.sort(function(a,b){
				if(a && b){
					return a.name.localeCompare(b.name)
				};//汉字拼音排序方法
			});
        	$.each(content.datas, function(i, item){
        		$(divId).append("<option value='"+ item.id +"'>" + item.name + "</option>");
        	})
        	
        	if(callback){
        		callback();
        	}
        },
        type:'get'
    });
}

function commonSetting4DataTable(tableSetting){
	
    sortIndex = -1;

	$.each(tableSetting.aoColumns, function(i, item){
    	if(item["sTitle"] && item["sTitle"] == '创建时间'){
    		sortIndex = i;
    	}
    	
		item["sClass"] = "center";
		item["asSorting"] = ["desc","asc"];
		
		if(!item["render"]){
			item["render"] = function(data){
				return htmlEncode(data);
			}
		}else{
			var tmpFun = item["render"];
			item["render"] = function(data, obj, style){
				var tmpText = tmpFun(data, obj, style);
				
				if(typeof tmpText === 'string'){
					if(tmpText.indexOf('<') != -1){
						return tmpText;
					}else{
						return htmlEncode(tmpText);
					}
				}else{
					return tmpText;
				}
			}
			
		}
	});
    
    if(sortIndex >= 0){
    	tableSetting.aaSorting = [[sortIndex, "desc"]];
    }
}


 function getMeetingType(meetingType) {
	
	if(meetingType==1){			
		return "MC";
	}else if(meetingType==2){
		return "EC";
	}else if(meetingType==3){
		return "TC";
	}else if(meetingType==4){
		return "SC";
	}else if(meetingType==5){
		return "EE";
	}else{
		return " ";
	}
}
 


 function getcharge(chargeId, hideSave){

		if (hideSave) {
			$(".btn-save-charge").hide();
		}
		
		
		$.ajaxInvoke({
			url 	: 	G_CTX_ROOT+"/v1/charges/"+chargeId ,
			data 	:	"",
			type : "get",
			success	:	function(returnData){
				//returnData= JSON.parse(returnData);
			
			
			
				console.log("data----",returnData);
				if(returnData.type=="MONTHLY_PAY_BY_HOSTS"){
					//Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_HOSTS").val(returnData.HOSTS_AMOUNT);
					$("#smonths_MONTHLY_PAY_BY_HOSTS").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_HOSTS").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_HOSTS").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_HOSTS").val(chargeId);
					$(".btn-save-order-MONTHLY_PAY_BY_HOSTS").attr("disabled", false);
				}	
				if(returnData.type=="CC_CALLCENTER_OLS_MONTHLY_PAY"){
					//Systemuser.getsites(returnData.COMMON_SITE);
					
					$("#sprices_CC_CALLCENTER_OLS_MONTHLY_PAY").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_CC_CALLCENTER_OLS_MONTHLY_PAY").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_OLS_MONTHLY_PAY").val(chargeId);
					$(".btn-save-order-CC_CALLCENTER_OLS_MONTHLY_PAY").attr("disabled", false);
				}
				if(returnData.type=="MONTHLY_PAY_BY_STORAGE"){			
					//Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_STO").val(returnData.STORAGE_SIZE);
					$("#smonths_MONTHLY_PAY_BY_STO").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_STO").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_STO").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_STO").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_STO").attr("disabled", false);
				}
				if(returnData.type=="MONTHLY_PAY_BY_STORAGE_O"){			
					//Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_STO_O").val(returnData.STORAGE_SIZE);
					$("#smonths_MONTHLY_PAY_BY_STO_O").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_STO_O").val(returnData.COMMON_UNIT_PRICE);
					$("#sprices_MONTHLY_PAY_BY_STO_O_OVERPRICE").val(returnData.COMMON_OVERFLOW_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_STO_O").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_STO_O").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_STO_O").attr("disabled", false);
				}
				if(returnData.type=="MONTHLY_PAY_BY_PORTS"){
					//Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_PORTS").val(returnData.PORTS_AMOUNT);
					$("#smonths_MONTHLY_PAY_BY_PORTS").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_PORTS").val(returnData.COMMON_UNIT_PRICE);
                 $("#soverprices_MONTHLY_PAY_BY_PORTS").val(returnData.COMMON_OVERFLOW_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_PORTS").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_PORTS").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_PORTS").attr("disabled", false);
				}
				if(returnData.type=="MONTHLY_PAY_BY_TOTAL_ATTENDEES"){
					//Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val(returnData.PORTS_AMOUNT);
					$("#smonths_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val(returnData.COMMON_UNIT_PRICE);
                 $("#soverprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val(returnData.COMMON_OVERFLOW_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_TOTAL_ATTENDEES").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_TOTAL_ATTENDEES").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_TOTAL_ATTENDEES").attr("disabled", false);
				}
				
				if(returnData.type=="PSTN_MONTHLY_PACKET"){
					//Systemuser.getsites(returnData.COMMON_SITES);
					$("#shosts_MONTHLY_PAY_BY_PSTNMONTH").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#smonths_MONTHLY_PAY_BY_PSTNMONTH").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_PSTNMONTH").val(returnData.MONTHLY_FEE);
					$("#myModal_MONTHLY_PAY_BY_PSTNMONTH").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNMONTH").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNMONTH").attr("disabled", false);
				}
				if(returnData.type=="CC_CALLCENTER_PSTN_MONTHLY_PACKAGE"){
					//Systemuser.getsites(returnData.COMMON_SITES);
					$("#shosts_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#smonths_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").val(returnData.MONTH_AMOUNT);
					$("#sprices_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").val(returnData.MONTHLY_FEE);
					$("#myModal_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").val(chargeId);	
					$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_PACKAGE").attr("disabled", false);
				}
				if(returnData.type=="CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE"){
					//Systemuser.getsites(returnData.COMMON_SITES);
					$("#shosts_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#smonths_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val(returnData.MONTH_AMOUNT);
					$("#sprices_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").val(chargeId);	
					$(".btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE").attr("disabled", false);
				}
				
				if(returnData.type=="CMR_MONTHLY_PAY"){
					//Systemuser.getsites(returnData.COMMON_SITES);
					$("#shosts_CMR_MONTHLY_PAY").val(returnData.PORTS_AMOUNT);
					$("#smonths_CMR_MONTHLY_PAY").val(returnData.MONTH_AMOUNT);
					$("#sprices_CMR_MONTHLY_PAY").val(returnData.COMMON_UNIT_PRICE);
					$("#soverprices_CMR_MONTHLY_PAY").val(returnData.COMMON_OVERFLOW_UNIT_PRICE);
					$("#myModal_CMR_MONTHLY_PAY").modal("show");					
					$("#edit_userid_order_CMR_MONTHLY_PAY").val(chargeId);	
					$(".btn-save-order-CMR_MONTHLY_PAY").attr("disabled", false);
				}	
				
				if(returnData.type=="PSTN_STANDARD_CHARGE"){
					getRates(returnData.PSTN_RATES_ID,"PSTN_STANDARD_CHARGE");
					$("#pstndiscount_MONTHLY_PAY_BY_PSTNALLSTIE").val("");
					$("#comdiscount_MONTHLY_PAY_BY_PSTNALLSTIE").val("");
					//$("#shosts_MONTHLY_PAY_BY_STANDSITE").val(returnData.TOTAL_PRICE);
					$("#s_searchStartTime_STANDSITE").val(returnData.EFFECTIVE_BEFORE);
					//$("#sprices_MONTHLY_PAY_BY_STANDSITE").val(returnData.TOTAL_PRICE);
					$("#myModal_MONTHLY_PAY_BY_STANDSITE").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_STANDSITE").val(returnData.PSTN_RATES_ID);	
					$(".btn-save-order-MONTHLY_PAY_BY_STANDSITE").attr("disabled", false);
				}
				if(returnData.type=="CC_CALLCENTER_PSTN"){
					getRates(returnData.PSTN_RATES_ID,"CC_CALLCENTER_PSTN");
					$("#pstndiscount_CC_CALLCENTER_PSTN").val("");
					$("#comdiscount_CC_CALLCENTER_PSTN").val("");
					//$("#shosts_MONTHLY_PAY_BY_STANDSITE").val(returnData.TOTAL_PRICE);
					//$("#s_searchStartTime_STANDSITE").val(returnData.EFFECTIVE_BEFORE);
					//$("#sprices_MONTHLY_PAY_BY_STANDSITE").val(returnData.TOTAL_PRICE);
					$("#myModal_CC_CALLCENTER_PSTN").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_PSTN").val(returnData.PSTN_RATES_ID);	
					$(".btn-save-order-CC_CALLCENTER_PSTN").attr("disabled", false);
				}
				if(returnData.type=="EC_PAY_PER_USE"){	
					//Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_ECPU").val(returnData.TIMES);
					$("#s_searchStartTime_ECPU").val(returnData.EFFECTIVE_BEFORE);
					$("#sprices_MONTHLY_PAY_BY_ECPU").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_ECPU").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_ECPU").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_ECPU").attr("disabled", false);
				}
				
				if(returnData.type=="EC_PREPAID"){	
					//Systemuser.getsites(returnData.COMMON_SITE);
					$("#shosts_MONTHLY_PAY_BY_ECPP").val(returnData.TOTAL_PRICE);
					$("#s_searchStartTime_ECPP").val(returnData.EFFECTIVE_BEFORE);
					//$("#sprices_MONTHLY_PAY_BY_ECPP").val(returnData.TOTAL_PRICE);
					$("#myModal_MONTHLY_PAY_BY_ECPP").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_ECPP").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_ECPP").attr("disabled", false);
				}
				if(returnData.type=="PSTN_SINGLE_PACKET_FOR_ALL_SITES"){					
					$("#shosts_MONTHLY_PAY_BY_PSTNALLSTIE").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#s_searchStartTime_PSTNALLSTIE").val(returnData.EFFECTIVE_BEFORE);
					$("#sprices_MONTHLY_PAY_BY_PSTNALLSTIE").val(returnData.TOTAL_PRICE);
					$("#myModal_MONTHLY_PAY_BY_PSTNALLSTIE").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNALLSTIE").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNALLSTIE").attr("disabled", false);
				}
				if(returnData.type=="PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES"){
					
					$("#shosts_MONTHLY_PAY_BY_PSTNMUlSTIE").val(returnData.PSTN_PACKAGE_MINUTES);
					$("#s_searchStartTime_PSTNMUlSTIE").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_PSTNMUlSTIE").val(returnData.TOTAL_PRICE);
					$("#myModal_MONTHLY_PAY_BY_PSTNMUlSTIE").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_PSTNMUlSTIE").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_PSTNMUlSTIE").attr("disabled", false);
				}
				if(returnData.type=="TELECOM_CHARGE"){					
								
					$("#sprices_MONTHLY_PAY_BY_TELECOM").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_TELECOM").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_TELECOM").val(chargeId);	
					$(".btn-save-order-MONTHLY_PAY_BY_TELECOM").attr("disabled", false);
				}
				
				if(returnData.type=="MISC_CHARGE"){					
					
					
					$("#myModal_MISC_CHARGE").modal("show");					
					$("#edit_userid_order_MISC_CHARGE").val(chargeId);
					$(".btn-save-order-MISC_CHARGE").attr("disabled", true);
				}
				
				if(returnData.type=="MONTHLY_PAY_BY_ACTIVEHOSTS"){			
				
					//$("#smonths_MONTHLY_PAY_BY_HOSTS").val(returnData.MONTH_AMOUNT);
					$("#sprices_MONTHLY_PAY_BY_ACTIVEHOSTS").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_BY_ACTIVEHOSTS").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_BY_ACTIVEHOSTS").val(chargeId);
					$(".btn-save-order-MONTHLY_PAY_BY_ACTIVEHOSTS").attr("disabled", false);
				}
				
				if(returnData.type=="CC_CALLCENTER_MONTHLY_PAY"){			
					
					//$("#smonths_MONTHLY_PAY_BY_HOSTS").val(returnData.MONTH_AMOUNT);
					$("#sprices_CC_CALLCENTER_MONTHLY_PAY").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_CC_CALLCENTER_MONTHLY_PAY").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_MONTHLY_PAY").val(chargeId);
					$(".btn-save-order-CC_CALLCENTER_MONTHLY_PAY").attr("disabled", false);
				}
				
				if(returnData.type=="CC_CALLCENTER_NUMBER_MONTHLY_PAY"){			
					
					//$("#smonths_MONTHLY_PAY_BY_HOSTS").val(returnData.MONTH_AMOUNT);
					$("#sprices_CC_CALLCENTER_NUMBER_MONTHLY_PAY").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_CC_CALLCENTER_NUMBER_MONTHLY_PAY").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_NUMBER_MONTHLY_PAY").val(chargeId);
					$(".btn-save-order-CC_CALLCENTER_NUMBER_MONTHLY_PAY").attr("disabled", false);
				}
				
				if(returnData.type=="MONTHLY_PAY_PERSONAL_WEBEX"){			
					$("#ssite_MONTHLY_PAY_PERSONAL_WEBEX").nextAll().filter("span").html("");
					//$("#smonths_MONTHLY_PAY_BY_HOSTS").val(returnData.MONTH_AMOUNT);
					$("#ssite_MONTHLY_PAY_PERSONAL_WEBEX").val(returnData.COMMON_SITE);
					$("#sprices_MONTHLY_PAY_PERSONAL_WEBEX").val(returnData.COMMON_UNIT_PRICE);
					$("#myModal_MONTHLY_PAY_PERSONAL_WEBEX").modal("show");					
					$("#edit_userid_order_MONTHLY_PAY_PERSONAL_WEBEX").val(chargeId);
					$(".btn-save-order-MONTHLY_PAY_PERSONAL_WEBEX").attr("disabled", false);
				}
				if(returnData.type=="PSTN_PERSONAL_CHARGE"){
					getRates(returnData.PSTN_RATES_ID,"PSTN_PERSONAL_CHARGE");
					$("#ssite_PSTN_PERSONAL_CHARGE").nextAll().filter("span").html("");
					$("#pstndiscount_PSTN_PERSONAL_CHARGE").val("");
					$("#comdiscount_PSTN_PERSONAL_CHARGE").val("");
					//$("#shosts_MONTHLY_PAY_BY_STANDSITE").val(returnData.TOTAL_PRICE);
					$("#ssite_PSTN_PERSONAL_CHARGE").val(returnData.COMMON_SITE);
					//$("#sprices_MONTHLY_PAY_BY_STANDSITE").val(returnData.TOTAL_PRICE);
					$("#myModal_PSTN_PERSONAL_CHARGE").modal("show");					
					$("#edit_userid_order_PSTN_PERSONAL_CHARGE").val(returnData.PSTN_RATES_ID);	
					$("#edit_userid_order_PSTN_PERSONAL_CHARGE_chargeId").val(chargeId);	
					$(".btn-save-order-PSTN_PERSONAL_CHARGE").attr("disabled", false);
				}
				
				if(returnData.type=="PSTN_PERSONAL_PACKET"){
					$("#ssite_PSTN_PERSONAL_PACKET").nextAll().filter("span").html("");
					$("#ssite_PSTN_PERSONAL_PACKET").val(returnData.COMMON_SITE);
					$("#shosts_PSTN_PERSONAL_PACKET").val(returnData.PSTN_PACKAGE_MINUTES);
					//$("#s_searchStartTime_STANDSITE").val(returnData.EFFECTIVE_BEFORE);
					$("#sprices_PSTN_PERSONAL_PACKET").val(returnData.TOTAL_PRICE);
					$("#myModal_PSTN_PERSONAL_PACKET").modal("show");					
					$("#edit_userid_order_PSTN_PERSONAL_PACKET").val(chargeId);	
					$(".btn-save-order-PSTN_PERSONAL_PACKET").attr("disabled", false);
				}
				if(returnData.type=="CC_CALLCENTER_PSTN_PACKAGE"){					
					$("#shosts_CC_CALLCENTER_PSTN_PACKAGE").val(returnData.PSTN_PACKAGE_MINUTES);
					//$("#s_searchStartTime_STANDSITE").val(returnData.EFFECTIVE_BEFORE);
					$("#sprices_CC_CALLCENTER_PSTN_PACKAGE").val(returnData.TOTAL_PRICE);
					$("#myModal_CC_CALLCENTER_PSTN_PACKAGE").modal("show");					
					$("#edit_userid_order_CC_CALLCENTER_PSTN_PACKAGE").val(chargeId);	
					$(".btn-save-order-CC_CALLCENTER_PSTN_PACKAGE").attr("disabled", false);
				}
				
			},

		});


	}; 
 
 
function getRates  (rateId,ratetype){			
			html="";
			$.ajaxInvoke({
				url 	: 	G_CTX_ROOT+"/v1/pstnrate/"+rateId ,
				data 	:	"",
				type : "get",
				success	:	function(returnData){
					$.each(returnData.rates, function(i, item){
						if(item.code>100){							
							header='<div class="row raterow geater100" codevalue="'+item.code+'" style="display:none">';
						}else{
							header='<div class="row raterow" codevalue="'+item.code+'">';
						}
						html=html+
								
									header+										
													'<section class="col col-3">'
														+'<label class="label text-right">' 
														+'<font color="red"></font>'
															+item.displayName
														+'</label>'
														+'<input type="hidden" name="rateCode" placeholder="" id="" value="'+item.code+'">'
													+'</section>';
						if(item.rate==item.listPriceRate){						
							html=html				+'<section class="col col-4">'														
														+'<label class="input"> <i class=""></i>'
															+'<input type="text" class="comfee" name="'+item.code+'1" placeholder="" id="" value="'+item.rate+'">'
															+'<span></span>'
															+'</label>'
														+'</section>';
						}else{
							html=html					+'<section class="col col-4">'														
							+'<label class="input"> <i class=""></i>'
								+'<input type="text" class="comfee" name="'+item.code+'1" placeholder="" id=""  style="border-color:#FF0000" value="'+item.rate+'">'
								+'<span>初始值为'+item.listPriceRate+'</span>'
								+'</label>'
							+'</section>';
						}
						if(item.serviceRate==item.listPriceServiceRate){
						
							html=html			+'<section class="col col-4">'														
														+'<label class="input"> <i class=""></i>'
															+'<input type="text" class="pstnfee" name="'+item.code+'2" placeholder="" id="" value="'+item.serviceRate+'">'
															+'<span></span>'
															+'</label>'
													+'</section>'	
												+'</div>';
						}else{
							
							html=html			+'<section class="col col-4">'														
							+'<label class="input"> <i class=""></i>'
								+'<input type="text" class="pstnfee" name="'+item.code+'2" placeholder="" id=""   style="border-color:#FF0000" value="'+item.serviceRate+'">'
								+'<span>初始值为'+item.listPriceServiceRate+'</span>'
								+'</label>'
								+'</section>'	
							+'</div>';
							
						}						
		        	})		        	
		        	 $('.ratesection').html("");
		        	 $('.ratesection_'+ratetype).html(html);
					$(".showallornot").text("显示所有国家和地区");
					
				}
			
			});
}; 

$("input").keyup(function(event){ 
    if (event.keyCode == 13){ 
  	  Systemuser.dataTable.fnDraw();
    } 
  }); 
function download_pagesData2CSV(divId, tableName, self){
		var _self = self;
		var header = encodeURI("data:text/csv;charset=utf-8,\ufeff");
		if (window.navigator.msSaveOrOpenBlob) {
			header = "\ufeff";
		}
		
		var content = '';
		var trs = $('#' + divId ).find('TR');
		$.each(trs, function(i, tritem){
			var line = '';
			$.each(tritem.children, function(i, item){
				if(i != 0){
					line = line  + ',';
				}
				
				var itemValue = '';
				var subItem = item;
				while(true){
					if(subItem.children.length > 0){
						_self.lg($(subItem).html());
						subItem = subItem.children[0];
					}else{
						break;
					}
				}
				line = line + $(subItem).text();
				
			});
			content = content + encodeURI(line + "\n");
		});
		
		
		var cvsFileName = tableName  + "-当前页";
		if (window.navigator.msSaveOrOpenBlob) {
			// if browser is IE
			var blob = new Blob([ decodeURIComponent(header + content) ], {
				type : "text/csv;charset=utf-8;"
			});
			navigator.msSaveBlob(blob, cvsFileName  + ".csv" );
		} else {
			var encodedUri = header + content;
			_self.lg("encodedUri length:" + encodedUri.length);
			var link = document.createElement("a");
			link.setAttribute("href", encodedUri);
			link.setAttribute("download", cvsFileName  + ".csv");
			document.body.appendChild(link);
			link.click();
		}
	};