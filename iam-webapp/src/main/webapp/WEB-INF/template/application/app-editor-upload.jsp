<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
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
              <li><i class="fa fa-desktop"></i> <fmt:message key="list.application.title" /></li>
              <li><i class="fa fa-plus-square"></i> <fmt:message key="form.application.titleAdd" /></li>
              <li><i class="fa fa-edit"></i> <fmt:message key="form.application.title" /></li>
              <li class="active"><i class="fa fa-upload"></i> <fmt:message key="upload.application.title" /></li>
            </ol>
        </div><!-- /.row -->
        
        <div class="row">
          <div class="col-lg-12">
        
            <h4><fmt:message key="upload.application.title" /></h4>
            
			<form:form method="POST" enctype="multipart/form-data" id="applicationUploadForm" name="applicationUploadForm" modelAttribute="applicationUploadForm" action="./upload.do" role="form">
				
			<spring:hasBindErrors name="applicationUploadForm">
		  		<center>
                       <div class="clear error">
                            <c:forEach items="${errors.globalErrors}" var="error"><%--  errors.allErrors or errors.globalErrors or errors.fieldErrors  --%>
                               <spring:message code="${error.defaultMessage}" /><br />
                            </c:forEach>
                       </div>
				</center>
			</spring:hasBindErrors>
			
			<security:accesscontrollist domainObject="${IamKeys.IAM_APPLICATION_UPLOAD}" hasPermission="${!IamKeys.ACTION}" >
				<fieldset disabled>
			</security:accesscontrollist>

              <div class="form-group">
              	<form:label class="control-label" path="file"> 
              		<fmt:message key="form.application.file"/> 
              	</form:label>
              	<form:errors path="file" cssClass="error" />
              	<input type="file" name="file" />
                <form:hidden path="id"/>
              </div>
              
              <button type="submit" class="btn btn-default"><fmt:message key="form.upload"/>
              <button type="reset" class="btn btn-default"><fmt:message key="form.reset"/>
              
            <security:accesscontrollist domainObject="${IamKeys.IAM_APPLICATION_UPLOAD}" hasPermission="${!IamKeys.ACTION}" >
				</fieldset>
			</security:accesscontrollist>
            </form:form>
            
          </div>
        </div>