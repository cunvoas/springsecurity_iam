<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
<script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
<script src="http://cdn.oesmith.co.uk/morris-0.4.3.min.js"></script>
--%>
<script src="<c:url value="/resources/js/morris/raphael-min.js" />"></script>
<script src="<c:url value="/resources/js/morris/morris-0.4.3.min.js" />"></script>

<!--[if lte IE 8]><script src="<c:url value="/resources/js/flot/excanvas.min.js" />"></script><![endif]-->
<script src="<c:url value="/resources/js/flot/jquery.flot.js" />"></script>
<script src="<c:url value="/resources/js/flot/jquery.flot.tooltip.min.js" />"></script>
<script src="<c:url value="/resources/js/flot/jquery.flot.resize.js" />"></script>

<script src="<c:url value="/resources/js/morris/jsDataChart.jsp" />"></script>