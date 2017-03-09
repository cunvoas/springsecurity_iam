<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:accesscontrollist domainObject="${IamKeys.IAM_ACCUEIL}" hasPermission="${IamKeys.VISIBLE}" >
	<c:redirect url="/admin-iam/home.do" />
</security:accesscontrollist>


<c:redirect url="/admin-iam/errors/403.do" />