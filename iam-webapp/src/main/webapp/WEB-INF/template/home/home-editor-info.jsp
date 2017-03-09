<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="row">
	<div class="col-lg-12">
		<h1>
			<fmt:message key="menu.accueil" />
		</h1>
	</div>
	<ol class="breadcrumb">
		<li class="active"><i class="fa fa-dashboard"></i> <fmt:message key="menu.accueil" /></li>
	</ol>
	<div class="alert alert-success alert-dismissable">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		<fmt:message key="menu.accueil.welcome" />
	</div>
</div>
<!-- /.row -->



<div class="row">

	<div class="col-lg-4">
		<div class="panel panel-info">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-4">
						<i class="fa fa-users fa-5x"></i>
					</div>
					<div class="col-xs-8 text-right">
						<p class="announcement-heading">
							<c:out value="${dashboard.nbNewUser}" />
						</p>
						<p class="announcement-text">
							<fmt:message key="dashboard.application.uniqueUser" />
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-lg-4">
		<div class="panel panel-success">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-4">
						<i class="fa fa-unlock fa-5x"></i>
					</div>
					<div class="col-xs-8 text-right">
						<p class="announcement-heading">
							<c:out value="${dashboard.nbVector}" />
						</p>
						<p class="announcement-text">
							<fmt:message key="dashboard.application.access" />
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-lg-4">
		<div class="panel panel-danger">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-4">
						<i class="fa fa-lock fa-5x"></i>
					</div>
					<div class="col-xs-8 text-right">
						<p class="announcement-heading">
							<c:out value="${dashboard.nbVectorRedused}" />
						</p>
						<p class="announcement-text">
							<fmt:message key="dashboard.application.refused" />
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /.row -->




<div class="row">

	<div class="col-lg-4">
		<div class="panel panel-info">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-4">
						<i class="fa fa-desktop fa-5x"></i>
					</div>
					<div class="col-xs-8 text-right">
						<p class="announcement-heading">
							<c:out value="${dashboard.nbApplication}" />
						</p>
						<p class="announcement-text">
							<fmt:message key="dashboard.application.managed" />
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-lg-4">
		<div class="panel panel-info">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-4">
						<i class="fa fa-share fa-5x"></i>
					</div>
					<div class="col-xs-8 text-right">
						<p class="announcement-heading">
							<c:out value="${dashboard.nbNewDelegationDemand}" />
						</p>
						<p class="announcement-text">
							<fmt:message key="dashboard.application.delegationActs" />
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-lg-4">
		<div class="panel panel-info">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-4">
						<i class="fa fa-gears fa-5x"></i>
					</div>
					<div class="col-xs-8 text-right">
						<p class="announcement-heading">
							<c:out value="${dashboard.nbAdminActivity}" />
						</p>
						<p class="announcement-text">
							<fmt:message key="dashboard.application.adminActs" />
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /.row -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">
					<i class="fa fa-bar-chart-o"></i>
					<fmt:message key="dashboard.application.activityGraph" />
				</h3>
			</div>
			<div class="panel-body">
				<div id="iam-activity-chart"></div>
			</div>
		</div>
	</div>
</div>
<!-- /.row -->

<!--
        <div class="row">
          <div class="col-lg-4">
          	<div class="panel panel-info">
          	  <div class="panel-success">
                <div class="row">
                  <div class="col-xs-4">
                    <i class="fa fa-qrcode fa-5x"></i>
                  </div>
                  <div class="col-xs-8 text-right">
                    <p class="announcement-heading"><img src="http://chart.apis.google.com/chart?cht=qr&chs=100x100&chl=http%3A%2F%2Flocalhost%3A8080%2Fiam-webapp&choe=UTF-8&chld=L" /></p>
                    <p class="announcement-text">QRCode</p>
                  </div>
                </div>
              </div>
          	</div>
          </div>
        </div>
 -->

<script type="text/javascript">
	var activity_data = <c:out value="${dashboardChart}" escapeXml="false"  />;
</script>
