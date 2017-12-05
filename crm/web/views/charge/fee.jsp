		<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!-- Input Modal -->
			<%--按账号数包月--%>
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_HOSTS" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_HOSTS">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_HOSTS" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_HOSTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
														</label>
													</section>											
												</div>					
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_HOSTS">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_HOSTS" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
	<!-- Input Modal -->
			<%--按账号数包月--%>
				<div class="modal fade" id="myModal_CC_CALLCENTER_OLS_MONTHLY_PAY" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_OLS_MONTHLY_PAY">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_CALLCENTER_OLS_MONTHLY_PAY" placeholder="请输入单价！" id="sprices_CC_CALLCENTER_OLS_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
														</label>
													</section>											
												</div>					
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-CC_CALLCENTER_OLS_MONTHLY_PAY">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_OLS_MONTHLY_PAY" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->		
<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_PORTS" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_PORTS">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_PORTS" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_PORTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 方 / 月
														</label>
													</section>												
												</div>
												
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															溢出单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="soverprices_MONTHLY_PAY_BY_PORTS" placeholder="请输入单价！" id="soverprices_MONTHLY_PAY_BY_PORTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>溢出单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">
															元 / 方 / 月
														</label>
													</section>												
												</div>							
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_PORTS">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_PORTS" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_TOTAL_ATTENDEES" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_TOTAL_ATTENDEES">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 人次 / 月
														</label>
													</section>												
												</div>
												
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															溢出单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="soverprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES" placeholder="请输入单价！" id="soverprices_MONTHLY_PAY_BY_TOTAL_ATTENDEES" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>溢出单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">
															元 / 人次 / 月
														</label>
													</section>												
												</div>							
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_TOTAL_ATTENDEES">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_TOTAL_ATTENDEES" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->			
<!-- Input Modal -->
			<%--WebEx存储--%>
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_STO" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_STO">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_STO" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_STO" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
														</label>
													</section>												
												</div>
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_STO">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_STO" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->		
			
	<!-- Input Modal -->
			<%--WebEx存储--%>
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_STO_O" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_STO_O">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_STO_O" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_STO_O" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 1G / 月
														</label>
													</section>												
												</div>
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															溢出单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_STO_O_OVERPRICE" placeholder="请输入溢出单价！" id="sprices_MONTHLY_PAY_BY_STO_O_OVERPRICE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 5G / 月
														</label>
													</section>												
												</div>
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_STO_O">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_STO_O" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
	<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_ECPP" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_ECPP">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															阶梯费率表:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_ECPP" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_ECPP" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>												
												</div>
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_ECPP">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_ECPP" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
		
	<!-- Input Modal -->
			<%--EC按次购买--%>
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_ECPU" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_ECPU">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_ECPU" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_ECPU" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>													
												</div>
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_ECPU">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_ECPU" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->		
		
	<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_PSTNALLSTIE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_PSTNALLSTIE">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总分钟数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_PSTNALLSTIE" placeholder="请输入总分钟大小！" id="shosts_MONTHLY_PAY_BY_PSTNALLSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总分钟长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">
															分钟
														</label>
													</section>
													
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_PSTNALLSTIE" placeholder="请输入总价！" id="sprices_MONTHLY_PAY_BY_PSTNALLSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>
																									
												</div>
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_PSTNALLSTIE">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_PSTNALLSTIE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
	<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_STANDSITE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_STANDSITE">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">
											<fieldset>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															国家地区
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															通信服务费
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															语音服务费
														</label>
													</section>
																						
												</div>
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														
															折扣:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="comdiscount_MONTHLY_PAY_BY_PSTNALLSTIE" placeholder="请输入通信服务费折扣！" id="comdiscount_MONTHLY_PAY_BY_PSTNALLSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="pstndiscount_MONTHLY_PAY_BY_PSTNALLSTIE" placeholder="请输入语音服务费折扣！" id="pstndiscount_MONTHLY_PAY_BY_PSTNALLSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
																									
												</div>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary comdiscount_button" >
															使用通信服务费折扣
														</button>
													</section>
													<section class="col col-4">
													
														<button type="button" class="btn btn-primary pstndiscount_button" >
															使用语音服务费折扣
														</button>
													</section>
																						
												</div>	
												
																						
												
												<div class="ratesection ratesection_PSTN_STANDARD_CHARGE"></div>	
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary showallornot" >
															显示所有国家和地区
														</button>
													</section>
													
																						
												</div>		
											</fieldset>
										</form>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_STANDSITE">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_STANDSITE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
	
	
	<!-- Input Modal -->
				<div class="modal fade" id="myModal_CC_CALLCENTER_PSTN" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_PSTN">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">
											<fieldset>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															国家地区
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															通信服务费
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															语音服务费
														</label>
													</section>
																						
												</div>
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														
															折扣:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="comdiscount_CC_CALLCENTER_PSTN" placeholder="请输入通信服务费折扣！" id="comdiscount_CC_CALLCENTER_PSTN" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="pstndiscount_CC_CALLCENTER_PSTN" placeholder="请输入语音服务费折扣！" id="pstndiscount_CC_CALLCENTER_PSTN" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
																									
												</div>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary comdiscount_button_CC_CALLCENTER_PSTN" >
															使用通信服务费折扣
														</button>
													</section>
													<section class="col col-4">
													
														<button type="button" class="btn btn-primary pstndiscount_button_CC_CALLCENTER_PSTN" >
															使用语音服务费折扣
														</button>
													</section>
																						
												</div>	
												
																						
												
												<div class="ratesection ratesection_CC_CALLCENTER_PSTN"></div>	
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary showallornot" >
															显示所有国家和地区
														</button>
													</section>
													
																						
												</div>		
											</fieldset>
										</form>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-CC_CALLCENTER_PSTN">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_PSTN" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->		
		
<!-- Input Modal -->
			<%--PSTN包月套餐--%>
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_PSTNMONTH" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_PSTNMONTH">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															每月分钟数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_PSTNMONTH" placeholder="请输入每月分钟数！" id="shosts_MONTHLY_PAY_BY_PSTNMONTH" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>每月分钟数长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">
															分钟
														</label>
													</section>
												</div>		

												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															月租费:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_PSTNMONTH" placeholder="请输入月费用！" id="sprices_MONTHLY_PAY_BY_PSTNMONTH" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月租费长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
														</label>
													</section>												
												</div>
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_PSTNMONTH">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_PSTNMONTH" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
		<!-- Input Modal -->
			<%--PSTN包月套餐--%>
				<div class="modal fade" id="myModal_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_PSTN_MONTHLY_PACKAGE">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															每月分钟数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" placeholder="请输入每月分钟数！" id="shosts_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>每月分钟数长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">
															分钟
														</label>
													</section>
												</div>		

												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															月租费:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" placeholder="请输入月费用！" id="sprices_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月租费长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
														</label>
													</section>												
												</div>
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_PACKAGE">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_PACKAGE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
		
				<!-- Input Modal -->
			<%--PSTN包月套餐--%>
				<div class="modal fade" id="myModal_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															每月分钟数:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" placeholder="请输入每月分钟数！" id="shosts_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>每月分钟数长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">
															分钟
														</label>
													</section>
												</div>		

												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" placeholder="请输入单价！" id="sprices_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>月租费长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元 / 分钟
														</label>
													</section>												
												</div>
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_PSTN_MONTHLY_MIN_CHARGE_PACKAGE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_PSTNMUlSTIE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_PSTNMUlSTIE">资费修改</h4>
							</div>
							<div class="modal-body">
														
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>										
											
												
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总分钟:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_MONTHLY_PAY_BY_PSTNMUlSTIE" placeholder="请输入总分钟大小！" id="shosts_MONTHLY_PAY_BY_PSTNMUlSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总分钟长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>		
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															购买有效期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class=""></i>
															<input type="text" name="s_searchStartTime_PSTNMUlSTIE" placeholder="点击输入购买有效期" id="s_searchStartTime_PSTNMUlSTIE" >
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															月
														</label>
													</section>											
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_PSTNMUlSTIE" placeholder="请输入总价！" id="sprices_MONTHLY_PAY_BY_PSTNMUlSTIE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>
																									
												</div>
												
												
															
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_PSTNMUlSTIE">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_PSTNMUlSTIE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->		
<!-- Input Modal -->
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_TELECOM" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_TELECOM">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
											
											
											
											
												
											
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_TELECOM" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_TELECOM" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>												
												</div>
												
												
															
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_TELECOM">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_TELECOM" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
		
		
		<!-- Input Modal -->
				<div class="modal fade" id="myModal_MISC_CHARGE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMISC_CHARGE">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
											
											
											
											
												
											
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														
															暂无可配置项:
														</label>
													</section>
																				
												</div>
												
												
															
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MISC_CHARGE">	保存 </button>
								<input type="hidden" id="edit_userid_order_MISC_CHARGE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
		
		<%--按激活数--%>
				<div class="modal fade" id="myModal_MONTHLY_PAY_BY_ACTIVEHOSTS" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_BY_ACTIVEHOSTS">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_BY_ACTIVEHOSTS" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_BY_ACTIVEHOSTS" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
														</label>
													</section>											
												</div>					
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_BY_ACTIVEHOSTS">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_BY_ACTIVEHOSTS" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->			
		
				<%--按激活数--%>
				<div class="modal fade" id="myModal_CC_CALLCENTER_MONTHLY_PAY" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_MONTHLY_PAY">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_CALLCENTER_MONTHLY_PAY" placeholder="请输入单价！" id="sprices_CC_CALLCENTER_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
														</label>
													</section>											
												</div>					
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-CC_CALLCENTER_MONTHLY_PAY">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_MONTHLY_PAY" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->		<%--按激活数--%>
				<div class="modal fade" id="myModal_CC_CALLCENTER_NUMBER_MONTHLY_PAY" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_NUMBER_MONTHLY_PAY">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_CALLCENTER_NUMBER_MONTHLY_PAY" placeholder="请输入单价！" id="sprices_CC_CALLCENTER_NUMBER_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>	
													<section class="col col-1">
														<label class="label text-left">														
															元 / 月
														</label>
													</section>											
												</div>					
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-CC_CALLCENTER_NUMBER_MONTHLY_PAY">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_NUMBER_MONTHLY_PAY" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
			<!-- Input Modal -->
			<%--EC按次购买--%>
				<div class="modal fade" id="myModal_CMR_MONTHLY_PAY" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCMR_MONTHLY_PAY">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CMR_MONTHLY_PAY" placeholder="请输入单价！" id="sprices_CMR_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>													
												</div>
												
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															溢出单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="soverprices_CMR_MONTHLY_PAY" placeholder="请输入单价！" id="soverprices_CMR_MONTHLY_PAY" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>溢出单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">
															元 / 方 / 月
														</label>
													</section>												
												</div>				
											</fieldset>
											
											
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-CMR_MONTHLY_PAY">	保存 </button>
								<input type="hidden" id="edit_userid_order_CMR_MONTHLY_PAY" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
		
		
			<!-- Input Modal -->
			<%--EC按次购买--%>
				<div class="modal fade" id="myModal_MONTHLY_PAY_PERSONAL_WEBEX" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelMONTHLY_PAY_PERSONAL_WEBEX">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="ssite_MONTHLY_PAY_PERSONAL_WEBEX" placeholder="请输入站点！" id="ssite_MONTHLY_PAY_PERSONAL_WEBEX" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>站点长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															
														</label>
													</section>													
												</div>
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															单价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_MONTHLY_PAY_PERSONAL_WEBEX" placeholder="请输入单价！" id="sprices_MONTHLY_PAY_PERSONAL_WEBEX" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>单价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>													
												</div>
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-MONTHLY_PAY_PERSONAL_WEBEX">	保存 </button>
								<input type="hidden" id="edit_userid_order_MONTHLY_PAY_PERSONAL_WEBEX" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->	
		
<!-- Input Modal -->
				<div class="modal fade" id="myModal_PSTN_PERSONAL_CHARGE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelPSTN_PERSONAL_CHARGE">资费修改</h4>
							</div>
							<div class="modal-body">							
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">
											<fieldset>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red">* </font> 
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="ssite_PSTN_PERSONAL_CHARGE" placeholder="请输入站点！" id="ssite_PSTN_PERSONAL_CHARGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>站点长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															
														</label>
													</section>													
												</div>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															国家地区
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															通信服务费
														</label>
													</section>
													<section class="col col-4">
														<label class="label text-left"> 
														<font color="red"></font>
															语音服务费
														</label>
													</section>
																						
												</div>
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														
															折扣:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="comdiscount_PSTN_PERSONAL_CHARGE" placeholder="请输入通信服务费折扣！" id="comdiscount_PSTN_PERSONAL_CHARGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="pstndiscount_PSTN_PERSONAL_CHARGE" placeholder="请输入语音服务费折扣！" id="pstndiscount_PSTN_PERSONAL_CHARGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
																									
												</div>
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary" id="comdiscount_PSTN_PERSONAL_CHARGE_Button">
															使用通信服务费折扣
														</button>
													</section>
													<section class="col col-4">
													
														<button type="button" class="btn btn-primary" id="pstndiscount_PSTN_PERSONAL_CHARGE_Button" >
															使用语音服务费折扣
														</button>
													</section>
																						
												</div>	
												
																						
												
												<div class="ratesection ratesection_PSTN_PERSONAL_CHARGE"></div>	
												
												<div class="row">											
													<section class="col col-3">
														<label class="label text-right"> 
														<font color="red"></font>
															
														</label>
													</section>
													<section class="col col-4">
														<button type="button" class="btn btn-primary showallornot" >
															显示所有国家和地区
														</button>
													</section>
													
																						
												</div>		
											</fieldset>
										</form>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-PSTN_PERSONAL_CHARGE">	保存 </button>
								<input type="hidden" id="edit_userid_order_PSTN_PERSONAL_CHARGE" value=""/>
								<input type="hidden" id="edit_userid_order_PSTN_PERSONAL_CHARGE_chargeId" value=""/>								
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->			
		
<!-- Input Modal -->
				<div class="modal fade" id="myModal_PSTN_PERSONAL_PACKET" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelPSTN_PERSONAL_PACKET">资费修改</h4>
							</div>
							<div class="modal-body">
														
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>										
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															站点:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="ssite_PSTN_PERSONAL_PACKET" placeholder="请输入站点！" id="ssite_PSTN_PERSONAL_PACKET" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>站点长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													<section class="col col-1">
														<label class="label text-left">														
															
														</label>
													</section>													
												</div>
												
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总分钟:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_PSTN_PERSONAL_PACKET" placeholder="请输入总分钟大小！" id="shosts_PSTN_PERSONAL_PACKET" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总分钟长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>
												<!--  	
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															截至时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="s_searchStartTime_STANDSITE" placeholder="点击选择截至时期" id="s_searchStartTime_STANDSITE" readonly="readOnly">
															<span></span>
														</label>
													</section>
													<section class="col col-1">														
														
													</section>														
												</div>
												-->	
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_PSTN_PERSONAL_PACKET" placeholder="请输入总价！" id="sprices_PSTN_PERSONAL_PACKET" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>
																									
												</div>
												
												
															
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-PSTN_PERSONAL_PACKET">	保存 </button>
								<input type="hidden" id="edit_userid_order_PSTN_PERSONAL_PACKET" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
		
		
		<!-- Input Modal -->
				<div class="modal fade" id="myModal_CC_CALLCENTER_PSTN_PACKAGE" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width:880px">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabelCC_CALLCENTER_PSTN_PACKAGE">资费修改</h4>
							</div>
							<div class="modal-body">
														
								<div class="row">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">

											<fieldset>										
											
												
												<div class="row">
											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总分钟:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="shosts_CC_CALLCENTER_PSTN_PACKAGE" placeholder="请输入总分钟大小！" id="shosts_CC_CALLCENTER_PSTN_PACKAGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总分钟长度不大于100个! </b>
															<span></span>
														</label>
													</section>								
													
												</div>
												<!--  	
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															截至时期:
														</label>
													</section>
													<section class="col col-4">														
														<label class="input"> <i class="icon-prepend fa fa-calendar"></i>
															<input type="text" name="s_searchStartTime_STANDSITE" placeholder="点击选择截至时期" id="s_searchStartTime_STANDSITE" readonly="readOnly">
															<span></span>
														</label>
													</section>
													<section class="col col-1">														
														
													</section>														
												</div>
												-->	
												<div class="row">											
													<section class="col col-2">
														<label class="label text-right"> 
														<font color="red">* </font> 
															总价:
														</label>
													</section>
													<section class="col col-4">
														<label class="input"> <i class=" "></i>
															<input type="text" name="sprices_CC_CALLCENTER_PSTN_PACKAGE" placeholder="请输入总价！" id="sprices_CC_CALLCENTER_PSTN_PACKAGE" maxlength="100">
															<b class="tooltip tooltip-top-left"> <i	class="fa fa-warning txt-color-teal"></i>总价长度不大于100个! </b>
															<span></span>
														</label>
													</section>
													
													<section class="col col-1">
														<label class="label text-left">														
															元
														</label>
													</section>
																									
												</div>
												
												
															
										
											</fieldset>
										</form>	
										
										</div>									
										
									</div>
								</div>
				
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-save-charge btn-primary btn-save-order-CC_CALLCENTER_PSTN_PACKAGE">	保存 </button>
								<input type="hidden" id="edit_userid_order_CC_CALLCENTER_PSTN_PACKAGE" value=""/>
								<input type="hidden" id="edit_username" value=""/>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->								