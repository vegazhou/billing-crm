<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="editOrg">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title org-modal-title">编辑企业信息</h4>
			</div>
			<div class="modal-body">
				<!--  <p><font color='red'>正在建设中&hellip;</font></p> -->
				<div class="widget-body">
					<form action id="org-form" class="smart-form" novalidate="novalidate">
						<fieldset>
							<input type='hidden' id='orgId' name='orgId'/>
							<div class="row">
								<section class="col col-3">
									<label class="label text-right">企业名称：</label>
								</section>
								<section class="col col-6">
									<label class="input"> <input type="text" name="orgName" placeholder="请输入企业名称" maxlength="20" id="orgName"> <span></span>
									</label>
								</section>
							</div>
							<div class="row">
								<section class="col col-3"><label class="label text-right">logo：</label></section>
								<section class="col col-6"><label class="label text-left companyLogo">未设置</label></section>
							</div>
							<div class="row">
								<section class="col col-3">
									<label class="label text-right">联系人姓名：</label>
								</section>
								<section class="col col-6">
									<label class="input"> <input type="text" name="contactName" placeholder="请输入联系人姓名" maxlength="20" id="contactName"> <span></span>
									</label>
								</section>
							</div>
							<div class="row">
								<section class="col col-3">
									<label class="label text-right">联系人电话：</label>
								</section>
								<section class="col col-6">
									<label class="input"> <input type="text" name="contactPhone" placeholder="请输入联系人电话" maxlength="20" id="contactPhone"> <span></span>
									</label>
								</section>
							</div>
							<div class="row">
								<section class="col col-3">
									<label class="label text-right">联系邮件地址：</label>
								</section>
								<section class="col col-6">
									<label class="input"> <input type="text" name="contactEmail" placeholder="请输入联系人邮件地址" maxlength="20" id="contactEmail"> <span></span>
									</label>
								</section>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-save">保存</button>
			</div>
		</div>
	</div>
</div>
