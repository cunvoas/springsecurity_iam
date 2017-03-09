<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  
Securized page : ${securized}. 
</P>

<P>  
The time on the server is ${serverTime}. 
</P>

<p>securized by taglib authorize</p>
<security:authorize>
	securized item
</security:authorize>

<p>securized by taglib ACL</p>
<security:accesscontrollist hasPermission="VISIBLE" domainObject="IAM.APPLICATION.LISTER" >
VISIBLE securized ACL
</security:accesscontrollist>
<br />

<security:accesscontrollist hasPermission="MODIFICATION" domainObject="IAM.APPLICATION.LISTER" >
MODIFICATION securized ACL
</security:accesscontrollist>
<br />

<security:accesscontrollist hasPermission="VISIBLE,!MODIFICATION" domainObject="IAM.APPLICATION.LISTER" >
VISIBLE,!MODIFICATION securized ACL
</security:accesscontrollist>


<br />

<a href="../">unsecured page</a>
</body>
</html>
