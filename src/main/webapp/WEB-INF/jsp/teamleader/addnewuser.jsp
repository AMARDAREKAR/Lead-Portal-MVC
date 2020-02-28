<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
</head>
<script src="<spring:url value="/resources/javascripts/validation.js"/>"></script>
<body>
	<h3>Register New Employee</h3>
	<form:form name="addEmployeeForm" onsubmit="return validateEmployeeForm()"
		action="tl.AddNewUser" modelAttribute="newuserbytl" method="POST">
		<table>
			<tbody>
				<tr>
					<td>First Name</td>
					<td>: <form:input path="firstName" /></td>
				</tr>
				<tr>
					<td>Last Name</td>
					<td>: <form:input path="lastName" /></td>
				</tr>
				<tr>
					<td>Employee Id</td>
					<td>: <form:input path="employeeId" /></td>
				</tr>
				<tr>
					<td>Designation</td>
					<td>: <form:select path="designation">
							<form:option value="AGENT" label="AGENT" />
						</form:select></td>
				</tr>
				<tr>
					<td>Password</td>
					<td>: <form:password path="password" /></td>
				</tr>
			</tbody>
		</table>
		<br />
		<input type="submit" value="ADD NEW EMPLOYEE" />
	</form:form>

	<h3>EMPLOYEE LIST</h3>
	<table border="2" width="70%" cellpadding="2">
		<tr>
			<th>Employee Id</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Designation</th>
			<th>Update</th>
		</tr>
		<c:forEach var="employee" items="${employeeList}">
			<!-- construct an "update" link with customer id -->
			<c:url var="updateLink" value="/tl.updateEmployee">
				<c:param name="employeeId" value="${employee.employeeId}" />
			</c:url>
			<!-- construct an "delete" link with employeeId id -->
			<c:url var="deleteLink" value="/tl.deleteEmployee">
				<c:param name="employeeId" value="${employee.employeeId}" />
			</c:url>
			<tr>
				<td>${employee.employeeId}</td>
				<td>${employee.firstName}</td>
				<td>${employee.lastName}</td>
				<td>${employee.designation}</td>
				<td>
					<!-- display the update link --> <a href="${updateLink}"
					onclick="if (!(confirm('Are you sure you want to update this employee ?'))) return false">Update</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br />
</body>
</html>
