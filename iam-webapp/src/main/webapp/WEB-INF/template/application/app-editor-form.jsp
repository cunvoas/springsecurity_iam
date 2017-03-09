<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

        <div class="row">
          <div class="col-lg-12">
            <h2><fmt:message key="menu.application.title" /></h2>
          </div>
           <ol class="breadcrumb">
              <li><i class="fa fa-check-square"></i> <fmt:message key="list.application.title" /></li>
              <li><i class="fa fa-plus-square"></i> <fmt:message key="form.application.titleAdd" /></li>
              <li class="active"><i class="fa fa-edit"></i> <fmt:message key="form.application.title" /></li>
              <li><i class="fa fa-upload"></i> <fmt:message key="upload.application.title" /></li>
            </ol>
        </div><!-- /.row -->
        
        <div class="row">
          <div class="col-lg-12">
          	
          </div>
        </div><!-- /.row -->
        
        <div class="row">
          <div class="col-lg-12">
        
            <h4><fmt:message key="form.application.title" /></h4>
            
			<form:form method="POST" id="applicationForm" name="applicationForm" modelAttribute="applicationForm" action="./edit.do" role="form">
				
			<security:accesscontrollist domainObject="${IamKeys.IAM_APPLICATION_CREER}" hasPermission="${!IamKeys.ACTION}" >
				<fieldset disabled>
			</security:accesscontrollist>
			
				<spring:hasBindErrors name="applicationForm">
			  		<center>
                        <div class="clear error">
                             <c:forEach items="${errors.globalErrors}" var="error"><%--  errors.allErrors or errors.globalErrors or errors.fieldErrors  --%>
                                <spring:message code="${error.defaultMessage}" /><br />
                             </c:forEach>
                        </div>
					</center>
				</spring:hasBindErrors>

              <div class="form-group">
              	<form:label class="control-label" path="code"> 
              		<fmt:message key="form.application.code"/> 
              	</form:label>
              	<form:errors path="code" cssClass="error" />
              	<spring:message code="form.application.code.placeholder" var="i18n.code.placeholder"/> 
                <form:input path="code" class="form-control" placeholder="${i18n.code.placeholder}" />
                <form:hidden path="id"/>
              </div>
              
              <div class="form-group">
              	<form:label class="control-label" path="description"> 
              		<fmt:message key="form.application.description"/> <form:errors path="description" cssClass="error" />
              	</form:label>
                <form:textarea path="description" class="form-control" />
              </div>
              
              <div class="form-group">
              	<form:label class="control-label" path="url"> 
              		<fmt:message key="form.application.url"/> <form:errors path="url" cssClass="error" />
              	</form:label>
                <form:textarea path="url" class="form-control" />
              </div>
              
              <button type="submit" class="btn btn-default"><fmt:message key="form.submit"/>
              
              <button type="reset" class="btn btn-default"><fmt:message key="form.reset"/>
              
            <security:accesscontrollist domainObject="${IamKeys.IAM_APPLICATION_CREER}" hasPermission="${!IamKeys.ACTION}" >
				</fieldset>
			</security:accesscontrollist>
            </form:form>
            
          </div>
        </div>