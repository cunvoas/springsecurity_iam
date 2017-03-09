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
            <h2><fmt:message key="menu.role.title" /></h2>
          </div>
           <ol class="breadcrumb">
              <li class="active"><i class="fa fa-group"></i> <fmt:message key="list.role.title" /></li>
              <li><i class="fa fa-plus-square"></i> <fmt:message key="form.role.titleAdd" /></li>
              <li><i class="fa fa-edit"></i> <fmt:message key="form.role.title" /></li>
            </ol>
        </div><!-- /.row -->
        
		<div class="row">
			<div class="col-lg-12">
			<h4><fmt:message key="role.application.title" /></h4>
			<div>
				<form id="formSearch" action="<c:url value="/admin-iam/role/list.do" />" method="GET">
					<input id="appId" type="hidden" value="${application.id}" />
					<input id="appSearch" name="appId" type="text" value="${application.code}" />
					<input id="appDesc" type="text" disabled="disabled" style="width: 50em;" value="${application.description}" />
				</form>
			</div>
			</div>
		</div>
		
        <div class="row">
          <div class="col-lg-12">
          
            <h4><fmt:message key="list.role.title" /></h4>
            <div class="table-responsive">
              <table>
                <thead>
                  <tr>
                    <th><fmt:message key="form.role.code"/> </th>
                    <th><fmt:message key="form.role.description"/></th>
                    <th data-sorter="false" class="remove-me"><fmt:message key="form.role.action"/><i class="fa fa-cogs"></i></th>
                  </tr>
                </thead>
                <tfoot>
                	<tr>
						<th colspan="3" class="ts-pager form-horizontal">
							<button type="button" class="btn first"><i class="fa fa-fast-backward"></i></button>
							<button type="button" class="btn prev"><i class="fa fa-step-backward"></i></button>
							<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
							<button type="button" class="btn next"><i class="fa fa-step-forward"></i></button>
							<button type="button" class="btn last"><i class="fa fa-fast-forward"></i></button>
							<select class="pagesize input-mini" title="<fmt:message key="tablesorter.select.pagesize"/>">
								<option selected="selected" value="5">5</option>
								<option value="10">10</option>
								<option value="20">20</option>
							</select>
							<select class="pagenum input-mini" title="<fmt:message key="tablesorter.select.pagenumber"/>"></select>
						</th>
					</tr>
                </tfoot>
                <tbody>
<c:forEach var="role" items="${roles}">
                  <tr>
                    <td>${role.code}</td>
                    <td>${role.descriptionShort}</td>
                    <td>
                    	&nbsp;
                    	
	<security:accesscontrollist domainObject="${IamKeys.IAM_ROLE_CREER}" hasPermission="${IamKeys.ACTION}" >
                    	<a href="<c:url value="/admin-iam/role/edit.do" />?roleId=${role.id}" title="<fmt:message key="icon.application.edit.title" />">
                    		<i class="fa fa-edit"></i>
                    	</a>
                    	&nbsp;
	</security:accesscontrollist>
	
	<security:accesscontrollist domainObject="${IamKeys.IAM_ROLE_SUPPRIMER}" hasPermission="${IamKeys.ACTION}" >
                    	<a href="#" title="<fmt:message key="icon.application.delete.title" />"
                    	   data-toggle="modal" data-target="#modalDialog" data-calllink="<c:url value="/admin-iam/role/delete.do" />?roleId=${role.id}">
                    		<i class="fa fa-trash-o"></i>
                    	</a>
                    	&nbsp;
	</security:accesscontrollist>
		
    
    <security:accesscontrollist domainObject="${IamKeys.IAM_RESSOURCE_AFFECTER_ROLE}" hasPermission="${IamKeys.VISIBLE}" >
                        <a href="<c:url value="/admin-iam/resource/values.do" />?appId=${SESSION_APPLICATION.code}&roleId=${role.id}" title="<fmt:message key="icon.ressourceValue.edit.title" />">
                            <i class="fa fa-list-ol"></i>
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


<!-- Modal -->
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-hidden="true" aria-labelledby="<fmt:message key="role.form.confirm.msg"/>">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><fmt:message key="role.form.confirm.warn"/></h4>

            </div>
            <div class="modal-body">
                <p><fmt:message key="role.form.confirm.msg"/></p> 
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="btnModelOk"><fmt:message key="role.form.confirm.ok"/></button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="role.form.confirm.cancel"/></button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<script>
//Make sure the DOM elements are loaded and accounted for
$(document).ready(function() {

  // Match to Bootstraps data-toggle for the modal
  // and attach an onclick event handler
  $('a[data-toggle="modal"]').on('click', function(e) {

   var send_modal = $(e.currentTarget).data('calllink');
    $("#btnModelOk").click(function(){
       window.location.href= send_modal ;
    });
   

    // From the clicked element, get the data-target arrtibute
    // which BS3 uses to determine the target modal
    var target_modal = $(e.currentTarget).data('target');
    // also get the remote content's URL
    var remote_content = e.currentTarget.href;

    // Find the target modal in the DOM
    var modal = $(target_modal);
    // Find the modal's <div class="modal-body"> so we can populate it
    var modalBody = $(target_modal + ' .modal-body');

    // Capture BS3's show.bs.modal which is fires
    // immediately when, you guessed it, the show instance method
    // for the modal is called
 
    modal.on('show.bs.modal', function () {
            // use your remote content URL to load the modal body
        //    modalBody.load(remote_content);
        }).modal();
        // and show the modal


    // Now return a false (negating the link action) to prevent Bootstrap's JS 3.1.1
    // from throwing a 'preventDefault' error due to us overriding the anchor usage.
    return false;
  });
  
});
</script>

