<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div class="jarviswidget jarviswidgetdiv companymanage" style="display:none;">
	<div class="header">
		<h1 class="page-title"><i class="fa fa-cogs"></i> 企业部门  <a href='javascript:void(0);' class="btn btn-success btn-xs btn-back2knowledge">显示企业知识</a> &nbsp;&nbsp; <a href='javascript:void(0);' class="  btn btn-success btn-xs btn-back2orglist">返回企业列表</a></h1>
		
		
	</div>
	<div class="row">
		<div class="col-sm-3">
			<div>当前企业:<b class='orgName'>总部</b> </div> 
		</div>
		<div class="col-sm-9">
			<div>当前部门:<b class='departmentName'>总部</b></div> 
			<input type='hidden' name='departmentId' id='departmentId'>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3">
			<div class="widget-body">
				<div class="tree smart-form">
					<ul role="tree">
						<li class="parent_li"><span id='0' title='折叠此部门'><i class="fa fa-lg fa-minus-circle"></i> 总部</span>
							<ul  class='tree_root_ul'>
							</ul></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="col-sm-9">
			<div class="row no-space">
				<table id="orgUserTable" class="table table-bordered table-hover">
					<thead>

					</thead>
					<tbody>

					</tbody>
					<tfoot>

					</tfoot>
				</table>
			</div>
		</div>
	</div>
</div>