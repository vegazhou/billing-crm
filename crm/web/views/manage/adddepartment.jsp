<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addDepartment">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title org-modal-title">部门新增:</h4>
			</div>
			<div class="modal-body">
				<!--  <p><font color='red'>正在建设中&hellip;</font></p> -->
				<div class="widget-body">
					<form action id="org-form" class="smart-form" novalidate="novalidate">
						<fieldset>
							<input type='hidden' id='inusrustryId' name='industryId'/>
							<input type='hidden' id='dictId' name='dictId'/>

							<div class="row">
								<section class="col col-4">
									<label class="label text-right">选择行业：</label>
								</section>
								<section class="col col-4">
									<label class="label text-left"> <select id="industry2" name="industry2" class="form-control">
											<option value='' selected>请选择行业</option>
									</select>
									</label>
								</section>
							</div>

							<div class="row subin-div" style='display:none;'>
								<section class="col col-4">
									<label class="label text-right">子行业：</label>
								</section>
								<section class="col col-4">
									<label class="label text-left"> <select id="subindustry2" name="subindustry2" class="form-control">
											<option value='' selected>请选择行业</option>
									</select>
									</label>
								</section>
							</div>

							<div class="row subin-div">
								<section class="col col-4">
									<label class="label text-right">上级部门：</label>
								</section>
								<section class="col col-4">
									<label class="label text-left"> <select id="parentDepartment" name="parentDepartment" class="form-control">
											<option value='' selected>不选</option>
									</select>
									</label>
								</section>
							</div>
							
							<div class="row">
								<section class="col col-4">
									<label class="label text-right">部门名称：</label>
								</section>
								<section class="col col-4">
									<label class="input"> <input type="text" name="departmentName" placeholder="请输入部门名称" maxlength="20" id="departmentName"> <span></span>
									</label>
								</section>
							</div>
							<div class="row">
								<section class="col col-4">
									<label class="label text-right">选择职能：</label>
								</section>
								<section class="col col-4">
									<label class="label text-left functionSelect"> <select id="functionId" name="functionId" multiple="multiple" class="form-control"></select>
									</label>
									<br><span class='warning'></span>
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
