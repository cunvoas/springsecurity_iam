<%@ page language="java" %>
<%@ page contentType="application/javascript; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

$(function () {
  
  Morris.Line({
    element: 'iam-activity-chart',
    data: activity_data,
    xkey: 'hour',
    ykeys: ['unique', 'granted', 'refused'],
    labels: ['<fmt:message key="dashboard.application.activity.unique" />', '<fmt:message key="dashboard.application.activity.granted" />', '<fmt:message key="dashboard.application.activity.refused" />']
  });
});