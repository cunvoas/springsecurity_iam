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
            <h2><fmt:message key="menu.delegation.title" /></h2>
          </div>
           <ol class="breadcrumb">
              <li class="active"><i class="fa fa-desktop"></i> <fmt:message key="menu.sub.delegation.title.list" /></li>
              <li><i class="fa fa-plus-square"></i> <fmt:message key="menu.sub.delegation.title.edit" /></li>
              <li><i class="fa fa-plus-square"></i> <fmt:message key="menu.sub.delegation.title.workflow" /></li>
            </ol>
        </div><!-- /.row -->
        
<%-- 
    Delegation I give
--%>
        <div class="row">
          <div class="col-lg-12">
          
            <h4><fmt:message key="list.delegation.title.delegate" /></h4>
        
            <div class="table-responsive">
              <table>
                <thead>
                  <tr>
                    <th><fmt:message key="form.delegation.application"/> </th>
                    <th><fmt:message key="form.delegation.role"/></th>
                    <th><fmt:message key="form.delegation.delegate"/></th>
                    <th><fmt:message key="form.delegation.startDate"/></th>
                    <th><fmt:message key="form.delegation.endDate"/></th>
                    <th><fmt:message key="form.delegation.status"/></th>
                    <th data-sorter="false" class="remove-me"><fmt:message key="form.delegation.action"/><i class="fa fa-cogs"></i></th>
                  </tr>
                </thead>
                <tfoot>
                	<tr>
						<th colspan="7" class="ts-pager form-horizontal">
							<button type="button" class="btn first"><i class="fa fa-fast-backward"></i></button>
							<button type="button" class="btn prev"><i class="fa fa-step-backward"></i></button>
							<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
							<button type="button" class="btn next"><i class="fa fa-step-forward"></i></button>
							<button type="button" class="btn last"><i class="fa fa-fast-forward"></i></button>
							<select class="pagesize input-mini" title="<fmt:message key="tablesorter.select.pagesize"/>">
								<option selected="selected" value="10">10</option>
								<option value="20">20</option>
								<option value="50">50</option>
								<option value="100">100</option>
							</select>
							<select class="pagenum input-mini" title="<fmt:message key="tablesorter.select.pagenumber"/>"></select>
						</th>
					</tr>
                </tfoot>
                <tbody>
<c:forEach var="deleg" items="${delegations}">
                  <tr>
                    <td>${deleg.appName}</td>
                    <td>${deleg.roleName}</td>
                    <td>${deleg.delegate}</td>
                    <td>${deleg.startDate}</td>
                    <td>${deleg.endDate}</td>
                    <td>${deleg.status}</td>
                    <td>
                    	&nbsp;
                    	
	<security:accesscontrollist domainObject="${IamKeys.IAM_DELEGATION_CREER}" hasPermission="${IamKeys.ACTION}" >
                    	<a href="<c:url value="/admin-iam/delegation/workflow.do" />?dlgId=${deleg.id}" title="<fmt:message key="icon.delegation.edit.title" />">
                    		<i class="fa fa-edit"></i>
                    	</a>
                    	&nbsp;
	</security:accesscontrollist>
	
                    </td>
                  </tr>
</c:forEach>
                 </tbody>
               </table>
			</div><!-- /.table responsive -->

               
            </div><!-- /.col-12 -->
        </div><!-- /.row -->
        
<%-- 
    Delegation I receive
--%>
        <div class="row">
          <div class="col-lg-12">
          
            <h4><fmt:message key="list.delegation.title.delegated" /></h4>
        
            <div class="table-responsive">
              <table>
                <thead>
                  <tr>
                    <th><fmt:message key="form.delegation.application"/> </th>
                    <th><fmt:message key="form.delegation.role"/></th>
                    <th><fmt:message key="form.delegation.delegated"/></th>
                    <th><fmt:message key="form.delegation.startDate"/></th>
                    <th><fmt:message key="form.delegation.endDate"/></th>
                    <th><fmt:message key="form.delegation.status"/></th>
                    <th data-sorter="false" class="remove-me"><fmt:message key="form.delegation.action"/><i class="fa fa-cogs"></i></th>
                  </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th colspan="7" class="ts-pager form-horizontal">
                            <button type="button" class="btn first"><i class="fa fa-fast-backward"></i></button>
                            <button type="button" class="btn prev"><i class="fa fa-step-backward"></i></button>
                            <span class="pagedisplay"></span> <!-- this can be any element, including an input -->
                            <button type="button" class="btn next"><i class="fa fa-step-forward"></i></button>
                            <button type="button" class="btn last"><i class="fa fa-fast-forward"></i></button>
                            <select class="pagesize input-mini" title="<fmt:message key="tablesorter.select.pagesize"/>">
                                <option selected="selected" value="10">10</option>
                                <option value="20">20</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select>
                            <select class="pagenum input-mini" title="<fmt:message key="tablesorter.select.pagenumber"/>"></select>
                        </th>
                    </tr>
                </tfoot>
                <tbody>
<c:forEach var="deleg" items="${delegationsAffected}">
                  <tr>
                    <td>${deleg.appName}</td>
                    <td>${deleg.roleName}</td>
                    <td>${deleg.delegator}</td>
                    <td>${deleg.startDate}</td>
                    <td>${deleg.endDate}</td>
                    <td>${deleg.status}</td>
                    <td>
                        &nbsp;
    <security:accesscontrollist domainObject="${IamKeys.IAM_DELEGATION_MODIFIER}" hasPermission="${IamKeys.ACTION}" >
                        <a href="<c:url value="/admin-iam/delegation/workflow.do" />?dlgId=${deleg.id}" title="<fmt:message key="icon.delegation.workflow.title" />">
                            <i class="fa fa-edit"></i>
                        </a>
                        &nbsp;
    </security:accesscontrollist>
    
                    </td>
                  </tr>
</c:forEach>
                 </tbody>
               </table>
            </div><!-- /.table responsive -->

               
            </div><!-- /.col-12 -->
        </div><!-- /.row -->
