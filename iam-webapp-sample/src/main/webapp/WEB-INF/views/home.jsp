<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello  <security:authentication property="principal.firstname"/>!
</h1>

<P>  
The time on the server is ${serverTime}. 
</P>

<P>  
The time persisted is ${persistedTime}.
<br>
<form action="./" method="post">
	<input type="submit" value="Save new date" /> 
</form> 
</P>


<br />
<a href="./secured/">secure page</a>
</body>
</html>
