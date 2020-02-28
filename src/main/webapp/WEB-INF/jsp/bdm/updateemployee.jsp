<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<body>
	<h3>Update Employee</h3>
	<form:form action="bdm.updateUser" modelAttribute="updateEmployeeByBDM"
		method="POST">
		<table>
			<tbody>
				<tr>
					<td>Employee Id</td>
					<td>: ${employeeId}<form:hidden path="employeeId" /></td>
				</tr>
				<tr>
					<td>First Name</td>
					<td>: ${firstName}<form:hidden path="firstName" /></td>
				</tr>
				<tr>
					<td>Last Name</td>
					<td>: ${lastName}<form:hidden path="lastName" /></td>
				</tr>
				<tr>
					<td>Designation</td>
					<td>: ${designation}<form:hidden path="designation" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td>: <form:password path="password" /></td>
					<td><form:errors path="password" cssClass="error"/></td>
				</tr>
			</tbody>
		</table>
		<br />
		<input type="submit" value="UPDATE EMPLOYEE" />
	</form:form>
</body>
</html>
