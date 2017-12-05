<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en-us">
<head>
  <meta charset="utf-8">
  <!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

  <title> 客户管理 </title>
  <meta name="description" content="">
  <meta name="author" content="">

  <!-- Use the correct meta names below for your web application
       Ref: http://davidbcalhoun.com/2010/viewport-metatag

  <meta name="HandheldFriendly" content="True">
  <meta name="MobileOptimized" content="320">-->

  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

  <jsp:include page="../public/css.jsp" />
</head>
<body class="">
<div id="content" style="height:100%">

  <div class="row">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
      <h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i> 账龄查询 </h1>
    </div>

  </div>

  <!-- 提示信息 -->

  <div class="alert alert-danger fade in hidden" id="warning-box">
    <button class="close" data-dismiss="alert">
      ×
    </button>
    <i class="fa-fw fa fa-warning"></i>
    <strong id="warning-box-title"></strong><span id="warning-box-body"></span>
  </div>

  <div class="alert alert-success fade in hidden" id="success-box">
    <button class="close" data-dismiss="alert">
      ×
    </button>
    <i class="fa-fw fa fa-warning"></i>
    <strong id="success-box-title"></strong><span id="success-box-body"></span>
  </div>


  <!-- search widget -->
  <section id="widget-grid" class="">

    <!-- widget grid -->


    <!-- row -->


    <!-- NEW WIDGET START -->
    <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">


      <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-c"
           data-widget-colorbutton="false" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-fullscreenbutton="false"
           data-widget-deletebutton="false">
        <header>
										<span class="widget-icon"> <i class="fa fa-table"></i>
										</span>
          
        </header>

        <!-- widget div-->
        <div>

          <!-- widget edit box -->
          <div class="jarviswidget-editbox">
            <!-- This area used as dropdown edit box -->

          </div>
          <!-- end widget edit box -->
          </br>
          <!-- widget content -->
          <div class="widget-body no-padding">

            <ul class="nav nav-tabs slide-tab">
              <li class="active last-child"><a href="javascript:void(0);"><i
                      class="fa fa-search"></i></i>&nbsp;&nbsp;查询条件<b class="caret"></b></a></li>
              <li class="extend-btn"></li>
            </ul>
            <!-- Toolbar -->
            <div class="hide-tab-panel">
              <form id="searchForm" method="POST" action="#" class="smart-form" novalidate="novalidate" onsubmit="return search()">
                <div class="row">


                  <section class="col col-3 form-group">
                    <label class="label text-left">客户名称:</label>
                    <label class="input"> <i class="icon-prepend fa fa-user"></i>
                      <input type="text" name="s_customer" placeholder="请输入客户名称" data-pure-clear-button>
                    </label>
                  </section>

				




                  <section class="col col-3">
                    <label class="label text-left">&nbsp;</label>

                    <span class="btn btn-primary btn-sm btn-search"><i class="fa fa-search"></i> 查询 </span>

                  </section>
                </div>
              </form>
            </div>
            <div class="well well-sm well-light " id="wid-id-c2">
              <div class="btn-group btn-group-md">

                <!-- <span class="btn btn-danger btn-delete"><i class="fa fa-trash-o "></i> 批量删除 </span>	 -->





              </div>
            </div>
            <!-- Toolbar End -->
            <table id="datatable_tabletools" class="table table-bordered table-hover">
              <thead>

              </thead>
              <tbody>

              </tbody>
              <tfoot>

              </tfoot>
            </table>

          </div>
          <!-- end widget content -->

        </div>
        <!-- end widget div -->

      </div>
      <!-- end widget -->

    </article>
    <!-- WIDGET END -->
    <!-- end row -->

  </section>
  <!-- end widget grid -->


</div>
<!-- End Input Modal -->

<!-- Input Modal -->

<!-- End Input Modal -->


<input type="hidden" id="loginuserid" value=${userid}>
<jsp:include page="../public/js.jsp" />

<script type="text/javascript">

  var updateObj = null;

  function htmlencode(s){
    var div = document.createElement('div');
    div.appendChild(document.createTextNode(s));
    return div.innerHTML;
  }
  function htmldecode(s){
    var div = document.createElement('div');
    div.innerHTML = s;
    return div.innerText || div.textContent;
  }

  <c:if test="${Root == true}">
  var isRoot=true;
  </c:if>

  <c:if test="${Root == false}">
  var isRoot=false;
  </c:if>







</script>
<script src="<c:url value="/static/js/page/report/billage.js" />${randomstr}" type="text/javascript"></script>
<!-- Search Ajax Example End -->
</body>
</html>