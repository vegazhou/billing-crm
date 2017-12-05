<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

				<!-- Input Modal -->
				<div class="modal fade" id="myModalSelectFile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel1">批量导入用户</h4>
							</div>
							<div class="modal-body">
				
								
								<div class="row fileSelect">
									<div class="col-md-12">
										<div class="well well-sm well-primary">
											<form id="checkout-form" class="smart-form" novalidate="novalidate">
											<fieldset>
												<div class="row">
													<section class="col col-3"></section>
													<section class="col col-8"><span style="float:right"><font color=red size='1'>*为必填项</font></span>
													</section>
												</div>												
												<div class="row" id="schoolRow">
													<section class="col col-3">
														<div class="label-text"><font color=red>* </font>选择导入的Excel文件:</div>
													</section>
													<section class="col col-8">
														
														<label class="input input-file state-success" >
															<div class="button">
																  <input type="file" id="file" name="file"/>选择
															</div>
															<input type="text" id="filetxt" readonly=""/><span id="fileError"></span>
														</label>
														
													</section>
												</div>
												
												
											</fieldset>
										</form>
										</div>
									</div>
								</div>
								<div>
									<a href='<c:url value="/static/template/demo_user_template.xls"/>'>文件样式参考范本下载</a>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-primary btn-upload">
									上传
								</button>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>
		<!-- End Input Modal -->
			<div class="modal fade" id="myModalErrorMessage" tabindex="-2" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel2">批量导入失败</h4>
							</div>
							<div class="modal-body">
								<div class="row errorMessage ">
									<font color=red></font>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">
									关闭
								</button>
								<button type="button" class="btn btn-primary btn-reupload">
									重新上传
								</button>
								
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>	
		<!-- End Input Modal -->
			<div class="modal fade" id="myModalfileinprogress" tabindex="-3" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									
								</button>
								<h4 class="modal-title" id="myModalLabel3">批量导入</h4>
							</div>
							<div class="modal-body">
								<div style='height:50px'>
									正在检查并导入用户列表，请勿返回或关闭。
									
								</div>
								<div><img src='<c:url value="/static/img/lding.gif"/>' style='margin-left:263px;margin-right:263px'/></div>
							</div>
							<div class="modal-footer">
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal-dialog -->
				</div>	
		<!-- End Input Modal -->
