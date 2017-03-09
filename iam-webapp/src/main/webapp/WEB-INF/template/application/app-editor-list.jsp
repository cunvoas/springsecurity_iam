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
              <li class="active"><i class="fa fa-desktop"></i> <fmt:message key="list.application.title" /></li>
              <li><i class="fa fa-plus-square"></i> <fmt:message key="form.application.titleAdd" /></li>
              <li><i class="fa fa-edit"></i> <fmt:message key="form.application.title" /></li>
              <li><i class="fa fa-upload"></i> <fmt:message key="upload.application.title" /></li>
            </ol>
        </div><!-- /.row -->
        
        <div class="row">
          <div class="col-lg-12">
          
            <h4><fmt:message key="list.application.title" /></h4>
        
            <div class="table-responsive">
              <table>
                <thead>
                  <tr>
                    <th><fmt:message key="form.application.code"/> </th>
                    <th><fmt:message key="form.application.description"/></th>
                    <th data-sorter="false" class="remove-me"><fmt:message key="form.application.action"/><i class="fa fa-cogs"></i></th>
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
<c:forEach var="appli" items="${applications}">
                  <tr>
                    <td>${appli.code}</td>
                    <td>${appli.descriptionShort}</td>
                    <td>
                    	&nbsp;
                    	
	<security:accesscontrollist domainObject="${IamKeys.IAM_APPLICATION_CREER}" hasPermission="${IamKeys.ACTION}" >
                    	<a href="<c:url value="/admin-iam/application/edit.do" />?appId=${appli.code}" title="<fmt:message key="icon.application.edit.title" />">
                    		<i class="fa fa-edit"></i>
                    	</a>
                    	&nbsp;
	</security:accesscontrollist>
	
	<security:accesscontrollist domainObject="${IamKeys.IAM_APPLICATION_SUPPRIMER}" hasPermission="${IamKeys.ACTION}" >
                    	<a href="#" title="<fmt:message key="icon.application.delete.title" />"
                    	   data-toggle="modal" data-target="#modalDialog" data-calllink="<c:url value="/admin-iam/application/delete.do" />?appId=${appli.code}">
                    		<i class="fa fa-trash-o"></i>
                    	</a>
                    	&nbsp;
	</security:accesscontrollist>
	
	<security:accesscontrollist domainObject="${IamKeys.IAM_APPLICATION_UPLOAD}" hasPermission="${IamKeys.ACTION}" >
                    	<a href="<c:url value="/admin-iam/application/dwdXlsFile.do" />?appId=${appli.code}" title="<fmt:message key="icon.application.download.title" />">
                    		<i class="fa fa-download"></i>
                    	</a>
                    	&nbsp;
                    	<a href="<c:url value="/admin-iam/application/upload.do" />?appId=${appli.code}" title="<fmt:message key="icon.application.upload.title" />">
                    		<i class="fa fa-upload"></i>
                    	</a>
                    	&nbsp;
	</security:accesscontrollist>
	
	<security:accesscontrollist domainObject="${IamKeys.IAM_RESSOURCE_CREER}" hasPermission="${IamKeys.ACTION}" >
                    	<a href="<c:url value="/admin-iam/resource/findResource.do" />?appId=${appli.code}" title="<fmt:message key="icon.ressource.edit.title" />">
                    		<i class="fa fa-list-ul"></i>
                    	</a>
                    	&nbsp;
	</security:accesscontrollist>
    
    <security:accesscontrollist domainObject="${IamKeys.IAM_RESSOURCE_AFFECTER_ROLE}" hasPermission="${IamKeys.VISIBLE}" >
                        <a href="<c:url value="/admin-iam/resource/values.do" />?appId=${appli.code}" title="<fmt:message key="icon.ressourceValue.edit.title" />">
                            <i class="fa fa-list-ol"></i>
                        </a>
                        &nbsp;
    </security:accesscontrollist>
	
	<security:accesscontrollist domainObject="${IamKeys.IAM_ROLE_CREER}" hasPermission="${IamKeys.ACTION}" >
                    	<a href="<c:url value="/admin-iam/role/list.do" />?appId=${appli.code}" title="<fmt:message key="icon.role.edit.title" />">
                    		<i class="fa fa-group"></i>
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
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-hidden="true" aria-labelledby="<fmt:message key="form.application.confirm.msg"/>">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><fmt:message key="form.application.confirm.warn"/></h4>

            </div>
            <div class="modal-body">
                <p><fmt:message key="form.application.confirm.msg"/></p> 
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="btnModelOk"><fmt:message key="form.application.confirm.ok"/></button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="form.application.confirm.cancel"/></button>
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

