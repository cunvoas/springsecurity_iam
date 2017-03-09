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
            <h2><fmt:message key="menu.delegation.title" /></h2>
          </div>
           <ol class="breadcrumb">
              <li><i class="fa fa-desktop"></i> <fmt:message key="menu.sub.delegation.title.list" /></li>
              <li><i class="fa fa-plus-square"></i> <fmt:message key="menu.sub.delegation.title.edit" /></li>
              <li class="active"><i class="fa fa-plus-square"></i> <fmt:message key="menu.sub.delegation.title.workflow" /></li>
            </ol>
        </div><!-- /.row -->
        
        <div class="row">
          <div class="col-lg-12">
            
          </div>
        </div><!-- /.row -->
        
        <div class="row">
          <div class="col-lg-12">
        
            <h4><fmt:message key="menu.sub.delegation.title.workflow" /></h4>
            
            <form:form method="POST" id="delegationForm" name="delegationForm" modelAttribute="delegationForm" action="./workflow.do" role="form">
                
            <security:accesscontrollist domainObject="${IamKeys.IAM_DELEGATION_MODIFIER}" hasPermission="${!IamKeys.ACTION}" >
                <fieldset disabled>
            </security:accesscontrollist>
            
                <spring:hasBindErrors name="delegationForm">
                    <center>
                        <div class="clear error">
                             <c:forEach items="${errors.globalErrors}" var="error"><%--  errors.allErrors or errors.globalErrors or errors.fieldErrors  --%>
                                <spring:message code="${error.defaultMessage}" /><br />
                             </c:forEach>
                        </div>
                    </center>
                </spring:hasBindErrors>

              <div class="form-group">
                <form:label class="control-label" path="applicationLabel"> 
                    <fmt:message key="delegation.form.application"/> 
                </form:label>
                <form:errors path="applicationLabel" cssClass="error" />
                <spring:message code="delegation.form.application.placeholder" var="i18n.application.placeholder"/> 
                <form:input readonly="readonly" path="applicationLabel" class="form-control" placeholder="${i18n.application.placeholder}" />
                <form:hidden path="idApplication" />
                <form:hidden path="id"/>
              </div>

              <div class="form-group">
                <form:label class="control-label" path="delegatorLabel"> 
                    <fmt:message key="delegation.form.delegator"/> 
                </form:label>
                <form:errors path="delegatorLabel" cssClass="error" />
                <spring:message code="delegation.form.delegator.placeholder" var="i18n.delegator.placeholder"/> 
                <form:input readonly="readonly" path="delegatorLabel" class="form-control" placeholder="${i18n.delegator.placeholder}" />
                <form:hidden path="delegator"/>
              </div>

              <div class="form-group">
                <form:label class="control-label" path="delegateLabel"> 
                    <fmt:message key="delegation.form.delegate"/> 
                </form:label>
                <form:errors path="delegateLabel" cssClass="error" />
                <spring:message code="delegation.form.delegate.placeholder" var="i18n.delegate.placeholder"/> 
                <form:input readonly="readonly" path="delegateLabel" class="form-control" placeholder="${i18n.delegate.placeholder}" />
                <form:hidden path="delegate"/>
              </div>

              <div class="form-group">
                <form:label class="control-label" path="debut"> 
                    <fmt:message key="delegation.form.startDate"/> 
                </form:label>
                <form:errors path="debut" cssClass="error" />
                <spring:message code="delegation.form.startDate.placeholder" var="i18n.startDate.placeholder"/> 
                <form:input readonly="readonly" path="debut" class="form-control" placeholder="${i18n.startDate.placeholder}" />
              </div>

              <div class="form-group">
                <form:label class="control-label" path="fin"> 
                    <fmt:message key="delegation.form.endDate"/> 
                </form:label>
                <form:errors path="fin" cssClass="error" />
                <spring:message code="delegation.form.endDate.placeholder" var="i18n.endDate.placeholder"/> 
                <form:input readonly="readonly" path="fin" class="form-control" placeholder="${i18n.endDate.placeholder}" />
              </div>
              
              <div class="form-group">
                <form:label class="control-label" path="statusLabel"> 
                    <fmt:message key="delegation.form.status"/> 
                </form:label>
                <form:errors path="statusLabel" cssClass="error" />
                <spring:message code="delegation.form.status.placeholder" var="i18n.status.placeholder"/> 
                <form:input readonly="readonly" path="statusLabel" class="form-control" placeholder="${i18n.status.placeholder}" />
                <form:hidden path="statusId"/>
              </div>
              

              
            <security:accesscontrollist domainObject="${IamKeys.IAM_DELEGATION_MODIFIER}" hasPermission="${!IamKeys.ACTION}" >
                </fieldset>
            </security:accesscontrollist>
            
            <security:accesscontrollist domainObject="${IamKeys.IAM_DELEGATION_MODIFIER}" hasPermission="${IamKeys.ACTION}" >
              <div class="form-group">
                  <c:if test="${delegationState.changeToCancel}">
                      <button type="submit" class="btn btn-default" name="BTN_ACT" value="CANC"><fmt:message key="form.cancel"/></button>
                  </c:if>
                  
                  <c:if test="${delegationState.changeToAccepted}">
                      <button type="submit" class="btn btn-default" name="BTN_ACT" value="ACCP"><fmt:message key="form.accept"/></button>
                  </c:if>
                  
                  <c:if test="${delegationState.changeToRefused}">
                      <button type="submit" class="btn btn-default" name="BTN_ACT" value="REFU"><fmt:message key="form.refuse"/></button>
                  </c:if>
              </div>
            </security:accesscontrollist>
              
            </form:form>
            
          </div>
        </div>
