<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- bootstrap widget theme -->
<link rel="stylesheet" href="<c:url value="/resources/css/tablesorter/theme.bootstrap.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/tablesorter/jquery.tablesorter.pager.css" />">

<!-- autocomplete theme -->
<link rel="stylesheet" href="<c:url value="/resources/css/autocomplete/jquery-ui-1.10.4.custom.min.css" />">
<style>
	.ui-autocomplete-loading {
		background: white url('<c:url value="/resources/css/autocomplete/images/ui-anim_basic_16x16.gif" />') right center no-repeat;
	}
</style>