<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="editUser">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title user-modal-title">编辑用户信息</h4>
			</div>
			<div class="modal-body">
				<!-- <p><font color='red'>正在建设中&hellip;</font></p> -->
				<div class="widget-body">
					<form action id="user-form" class="smart-form" novalidate="novalidate">
						<fieldset>
							<input type='hidden' id='userId' name='userId' />
							<div class="row">
								<section class="col col-2">
									<label class="label text-right">用户姓名：</label>
								</section>
								<section class="col col-4">
									<label class="input"> <input type="text" name="fullName" placeholder="请输入用户姓名" maxlength="20" id="fullName"> <span></span>
									</label>
								</section>
								<section class="col col-2">
									<label class="label text-right">用户性别：</label>
								</section>
								<section class="col col-4">
									<div class="inline-group">
										<label class="radio"><input name="gendar" type="radio" value="1"><i></i>男</label> <label class="radio"><input name="gendar" type="radio" value="0"><i></i>女</label>
									</div>
									<div class="label-text text-left">
										<span></span>
									</div>

								</section>
							</div>
							<div class="row">
								<section class="col col-2">
									<label class="label text-right">电话号码：</label>
								</section>
								<section class="col col-4">
									<label class="input"> <input type="text" name="phone" placeholder="请输入电话号码" maxlength="20" id="phone"> <span></span>
									</label>
								</section>
								<section class="col col-2">
									<label class="label text-right">手机号码：</label>
								</section>
								<section class="col col-4">
									<label class="input"> <input type="text" name="mobile" placeholder="请输入手机号码" maxlength="11" id="mobile"> <span></span>
									</label>
								</section>

							</div>

							<div class="row">
								<section class="col col-2">
									<label class="label text-right">邮箱地址：</label>
								</section>
								<section class="col col-4">
									<label class="input"> <input type="text" name="email" placeholder="请输入邮箱地址" maxlength="60" id="email"> <span></span>
									</label>
								</section>
							</div>
							<div class="row">
								<section class="col col-2">
									<label class="label text-right">所在岗位：</label>
								</section>
								<section class="col col-4">
									<label class="input"> <input type="text" name="positionName" placeholder="请输入所在岗位" maxlength="60" id="positionName"> <span></span>
									</label> <input type='hidden' id='positionId' value='' name='positionId'>
								</section>
								<section class="col col-2">
									<label class="label text-right">用户角色：</label>
								</section>
								<section class="col col-4">
									<label class="input"> <input type="text" name="roleName" placeholder="请输入用户角色" maxlength="60" id="roleName"> <span></span>
									</label> <input type='hidden' id='roleId' value='' name='roleId'>
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
