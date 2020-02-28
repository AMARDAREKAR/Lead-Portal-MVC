<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:url value="/loginAction" var="loginurl" />

<html>

<head>
<title>Login Page</title>
<script src="<spring:url value="/resources/javascripts/validation.js"/>"></script>
</head>

<body>
	<h3>Login Page</h3>
	<form action="${loginurl}" method="POST">
		<table>
			<tbody>
				<tr>
					<td>Employee Id</td>
					<td>: <input id="username" name="username" required /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td>: <input type="Password" name="password" required /></td>
				</tr>
				<tr>
					<td> <label for="remember"> Remember me</label> </td>  
        			<td>: <input type="checkbox" name="remember" /> </td>  
				</tr>
			</tbody>
		</table>
		<br> <input type="submit" value="LOGIN">
		<script>
			// Integer values (positive and up to a particular limit 10000 over here):
			setInputFilter(document.getElementById("username"),
					function(value) {
						return /^\d*$/.test(value)
								&& (value === "" || parseInt(value) <= 10000);
					});
		</script>
	</form>
</body>
</html>
