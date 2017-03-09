<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

            <li class="dropdown messages-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-flag"></i> <img class="flag" src="<c:url value="/resources/imgs/flag/" />flag.<c:out value="${SESSION_LOCALE}" />.png" alt="<c:out value="=${SESSION_LOCALE}" />" />  <b class="caret"></b></a>
              <c:if test="${not empty SESSION_LOCALES}">
              <ul class="dropdown-menu">
              	<c:forEach var="loc" items="${SESSION_LOCALES}">
                <li><a href="<c:url value="/admin-iam/changeLanguage.do?locale=" /><c:out value="${loc}" />"><img class="flag" src="<c:url value="/resources/imgs/flag/flag." /><c:out value="${loc}" />.png" alt="<c:out value="${loc}" />" /></a></li>
                </c:forEach>
              </ul>
              </c:if>
            </li>
            
            <li class="dropdown alerts-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-group"></i> <security:authentication property="principal.currentRole"/> <b class="caret"></b></a>
              <ul class="dropdown-menu">
              <c:forEach var="role" items="${principal.authorities}">
              	 <li><a href="<c:url value="/admin-iam/changeRole.do" />?role=${role.id}">${role}</a></li>
              </c:forEach>
              </ul>
            </li>
            <li class="dropdown user-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <security:authentication property="principal.firstname"/> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
                <security:accesscontrollist domainObject="${IamKeys.IAM_DELEGATION_LISTER}" hasPermission="${IamKeys.VISIBLE}" >
                <li><a href="<c:url value="/admin-iam/delegation/list.do" />"><i class="fa fa-exchange"></i> Delegation <span class="badge">1</span></a></li>
                </security:accesscontrollist>
                <li><a href="#"><i class="fa fa-gear"></i> Settings</a></li>
                <li class="divider"></li>
                <li><a href="#"><i class="fa fa-code-fork"></i> <fmt:message key="version.application"/> ${SESSION_APP_VERSION}</a></li>
                 <li class="divider"></li>
                 <li><a href="<c:url value="/j_spring_security_logout"/>" ><i class="fa fa-power-off"></i> Log Out</a></li>
              </ul>
            </li>
