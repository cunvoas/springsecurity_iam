<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


		<div class="row">
          <div class="col-lg-12">
            <h2>
            	<fmt:message key="accueil.erreur.h2" />  <c:out value="${pageContext.errorData.statusCode}" />
            </h2>
          </div>
        </div>
        
		<div class="row">
          <div class="col-lg-4">
            <h4><fmt:message key="${statusMessageKey}" /></h4>
          </div>
          <div class="col-lg-8">
            <h4><fmt:message key="${typeException}" /></h4>
          </div>
        </div>
                
		<div class="row">
          <div class="col-lg-12">
            <c:out value="${traceException}" />
          </div>
        </div>
