<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!-- Message Modal -->
		<div class="modal fade" id="alertModal" tabindex="-1" role="dialog" 
		   aria-labelledby="alertModelLabel" aria-hidden="true">
		   <div class="modal-dialog">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" 
		               data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="alertModelLabel">
		             
		            </h4>
		         </div>
		         <div class="modal-body text-center" id="alertModelBody">
	
		         </div>
		         <div class="modal-footer">
		            <button type="button" class="btn btn-primary" 
		               data-dismiss="modal" id="alertModelBtnClose">
		            </button>
		         </div>
		      </div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		<!-- Message Modal End -->
		<!-- Confirm Modal -->
		<div class="modal fade" id="confirmModal"  role="dialog" aria-labelledby="confirmModelLabel" aria-hidden="true">
		   <div class="modal-dialog">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" 
		               data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="confirmModelLabel">
		               
		            </h4>
		         </div>
		         <div class="modal-body text-center" id="confirmModelBody">
		            
		         </div>
		         <div class="modal-footer">
		            <button type="button" class="btn btn-default" 
		               data-dismiss="modal" id="confirmModelBtnClose">
		            </button>

		            <button type="button" class="btn btn-primary" 
		               data-dismiss="modal" id="confirmModelBtnActive">
		            </button>
		         </div>
		      </div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		<!-- Confirm Modal End -->

		<!--======================SCRIPT============================ -->

		<!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
		<input type="hidden" value="1" id="isHistory">
		
		<script src="<c:url value="/static/js/libs/jquery-1.9.1.min.js"/>${randomstr}"></script>
		<script src="<c:url value="/static/js/libs/jquery-ui-1.10.3.min.js"/>${randomstr}"></script>
				<script src="<c:url value="/static/js/libs/jquery.cookie.js"/>${randomstr}"></script>
		<!-- BOOTSTRAP JS -->
		<script src="<c:url value="/static/js/bootstrap/bootstrap.min.js"/>${randomstr}"></script>
		<script src="<c:url value="/static/js/plugin/bootstrap-pureClearButton/bootstrap-pureClearButton.js"/>${randomstr}"></script>

		<!-- CUSTOM NOTIFICATION -->
		<script src="<c:url value="/static/js/notification/SmartNotification.min.js"/>${randomstr}"></script>

		<!-- JARVIS WIDGETS -->
		<script src="<c:url value="/static/js/smartwidgets/jarvis.widget.min.js" />${randomstr}"></script>

		

		<!-- SPARKLINES -->
		<script src="<c:url value="/static/js/plugin/sparkline/jquery.sparkline.min.js" />${randomstr}"></script>

		<!-- JQUERY VALIDATE -->
		<script src="<c:url value="/static/js/plugin/jquery-validate/jquery.validate.min.js"/>${randomstr}"></script>

		<!-- JQUERY MASKED INPUT -->
		<script src="<c:url value="/static/js/plugin/masked-input/jquery.maskedinput.min.js" />${randomstr}"></script>

		<!-- JQUERY SELECT2 INPUT -->
		<script src="<c:url value="/static/js/plugin/select2/select2.min.js" />${randomstr} "></script>

		<!-- JQUERY UI + Bootstrap Slider -->
		<script src="<c:url value="/static/js/plugin/bootstrap-slider/bootstrap-slider.min.js"/>${randomstr}" ></script>

		<!-- browser msie issue fix -->
		<script src="<c:url value="/static/js/plugin/msie-fix/jquery.mb.browser.min.js" />${randomstr}" ></script>

		<!-- FastClick: For mobile devices -->
		<script src="<c:url value="/static/js/plugin/fastclick/fastclick.js" />${randomstr}"></script>

		<!--[if IE 7]>



		<![endif]-->
 

		<!-- MAIN APP JS FILE -->
		<script src="<c:url value="/static/js/app.js" />${randomstr}"></script>
		<!-- Demo purpose only -->

		<!-- PAGE RELATED PLUGIN(S) -->
		<script src="<c:url value ="/static/js/plugin/jquery-form/jquery-form.min.js" />${randomstr}"></script>

		<!-- PAGE RELATED PLUGIN(S) -->

		<script src="<c:url value="/static/js/plugin/datatables-1.10.3/js/jquery.dataTables.min.js" />${randomstr}"></script>
		<!-- <script src="<c:url value="/static/global.js" />${randomstr} " type="text/javascript"></script>  -->
		<script src="<c:url value="/static/js/common/static.js" />${randomstr}" type="text/javascript"></script>
		<script src="<c:url value="/static/js/common/common.js" />${randomstr}" type="text/javascript"></script>