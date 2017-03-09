<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

          
           <security:accesscontrollist domainObject="${IamKeys.IAM_ACCUEIL}" hasPermission="${IamKeys.VISIBLE}" >
           	<li <c:if test="${UC_ACTIVE eq 'home'}">class="active"</c:if>><a href="<c:url value="/admin-iam/home.do" />"><i class="fa fa-dashboard"></i> <fmt:message key="menu.accueil" /></a></li>
           </security:accesscontrollist>
           
           <security:accesscontrollist domainObject="${IamKeys.IAM_APPLICATION_LISTER}" hasPermission="${IamKeys.VISIBLE}" >
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-desktop"></i> <fmt:message key="menu.applications" /> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li <c:if test="${UC_ACTIVE eq 'application'}">class="active"</c:if>><a href="<c:url value="/admin-iam/application/list.do" />"><i class="fa fa-desktop"></i> <fmt:message key="menu.applications.list" /></a></li>
                
                <security:accesscontrollist domainObject="${IamKeys.IAM_APPLICATION_CREER}" hasPermission="${IamKeys.ACTION}" >
                <li <c:if test="${UC_ACTIVE eq 'applicationAdd'}">class="active"</c:if>><a href="<c:url value="/admin-iam/application/add.do" />"><i class="fa fa-plus-square"></i> <fmt:message key="menu.applications.add" /></a></li>
                </security:accesscontrollist>
                
                <security:accesscontrollist domainObject="${IamKeys.IAM_APPLICATION_UPLOAD}" hasPermission="${IamKeys.ACTION}" >
                <li <c:if test="${UC_ACTIVE eq 'applicationUpload'}">class="active"</c:if>><a href="<c:url value="/admin-iam/application/upload.do" />"><i class="fa fa-upload"></i> <fmt:message key="menu.applications.upload" /></a></li>
                </security:accesscontrollist>
              </ul>
            </li>
           </security:accesscontrollist>
           
 
           <security:accesscontrollist domainObject="${IamKeys.IAM_RESSOURCE_LISTER}" hasPermission="${IamKeys.VISIBLE}" >
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-list-ul"></i> <fmt:message key="menu.resources" /> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li <c:if test="${UC_ACTIVE eq 'resource'}">class="active"</c:if>><a href="<c:url value="/admin-iam/resource/findResource.do" />"><i class="fa fa-list-ul"></i>  <fmt:message key="menu.resources.app" /></a></li>
                
                <security:accesscontrollist domainObject="${IamKeys.IAM_RESSOURCE_AFFECTER_ROLE}" hasPermission="${IamKeys.VISIBLE}" >
                <li <c:if test="${UC_ACTIVE eq 'resourceVal'}">class="active"</c:if>><a href="<c:url value="/admin-iam/resource/values.do" />"><i class="fa fa-list-ol"></i> <fmt:message key="menu.resources.val" /></a></li>
                </security:accesscontrollist>
  
              </ul>
            </li>
           </security:accesscontrollist>
           
             
           <security:accesscontrollist domainObject="${IamKeys.IAM_ROLE_LISTER}" hasPermission="${IamKeys.VISIBLE}" >
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-group"></i> <fmt:message key="menu.roles" /> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li <c:if test="${UC_ACTIVE eq 'role'}">class="active"</c:if>><a href="<c:url value="/admin-iam/role/list.do" />"><i class="fa fa-group"></i> <fmt:message key="menu.roles.list" /></a></li>
                
                <security:accesscontrollist domainObject="${IamKeys.IAM_APPLICATION_CREER}" hasPermission="${IamKeys.ACTION}" >
                <li <c:if test="${UC_ACTIVE eq 'roleAdd'}">class="active"</c:if>><a href="<c:url value="/admin-iam/role/add.do" />"><i class="fa fa-plus-square"></i> <fmt:message key="menu.roles.add" /></a></li>
                </security:accesscontrollist>
  
              </ul>
            </li>
           </security:accesscontrollist>
           
           
             
           <security:accesscontrollist domainObject="${IamKeys.IAM_DELEGATION_LISTER}" hasPermission="${IamKeys.VISIBLE}" >
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-exchange"></i> <fmt:message key="menu.delegations" /> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li <c:if test="${UC_ACTIVE eq 'delegation'}">class="active"</c:if>><a href="<c:url value="/admin-iam/delegation/list.do" />"><i class="fa fa-check-square"></i> <fmt:message key="menu.delegations.list" /></a></li>
                
                <security:accesscontrollist domainObject="${IamKeys.IAM_DELEGATION_CREER}" hasPermission="${IamKeys.ACTION}" >
                <li <c:if test="${UC_ACTIVE eq 'delegationAdd'}">class="active"</c:if>><a href="<c:url value="/admin-iam/delegation/add.do" />"><i class="fa fa-plus-square"></i> <fmt:message key="menu.delegations.add" /></a></li>
                </security:accesscontrollist>
  
              </ul>
            </li>
           </security:accesscontrollist>