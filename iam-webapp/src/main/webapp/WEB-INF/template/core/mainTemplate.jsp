<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
  <head>
<!-- AppDynamics EUM
<script>window['adrum-start-time'] = new Date().getTime();</script><script src="<c:url value="/resources/js/appdyn/adrum.js" />"></script>
 / AppDynamics EUM -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="IAM Identity Acess Management - Spring Security and CAS integration">
    <meta name="keywords" content="IAM,Identity Acess Management,Authentication,Authorization,Non-Repudiation,Delegation">
    <meta name="author" content="CUNVOAS">
    <link rel="shortcut icon" href="<c:url value="/resources/ico/favicon.ico" />">

    <title><fmt:message key="page.title" /></title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet">
    <!-- Add custom CSS here -->
    <link href="<c:url value="/resources/css/sb-admin.css" />" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/resources/font-awesome/css/font-awesome.min.css" />">

 	<!-- Page Specific CSS -->
	<tiles:insertAttribute name="specificCss" defaultValue="/WEB-INF/template/core/blank.jsp" />
	
    <!-- JavaScript -->
    <script src="<c:url value="/resources/js/jquery-1.10.2.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.js" />"></script>

    <!-- Page Specific Plugins -->
    <tiles:insertAttribute name="specificJS" defaultValue="/WEB-INF/template/core/blank.jsp" />
  </head>

  <body>

    <div id="wrapper">

      <!-- Sidebar -->
      <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<c:url value="/admin-iam/home.do" />"><img src="<c:url value="/resources/ico/iam_32x32.png" />" width="32" height="32" border="0" title="<fmt:message key="logo.title" />"/> &nbsp; <fmt:message key="page.title" /></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          <ul class="nav navbar-nav side-nav">
     		 <tiles:insertAttribute name="leftMenu" defaultValue="/WEB-INF/template/core/blank.jsp" />
          </ul>

          <ul class="nav navbar-nav navbar-right navbar-user">
          	 <tiles:insertAttribute name="topMenu" defaultValue="/WEB-INF/template/core/blank.jsp" />
          
                      
          </ul>
        </div><!-- /.navbar-collapse -->
      </nav>

      <div id="page-wrapper">

		<tiles:insertAttribute name="editor" defaultValue="/WEB-INF/template/core/blank.jsp" />

      </div><!-- /#page-wrapper -->

    </div><!-- /#wrapper -->

  </body>
</html>
