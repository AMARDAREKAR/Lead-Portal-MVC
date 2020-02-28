<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<c:url value="/logout" var="logoutUrl" />
	<a href="bdm.leadManagement">Lead Management</a>
	<br></br>
	<a href="bdm.employeeManagement">Employee Management</a>
	<br></br>
	<a href="bdm.campaignManagement">Campaign Management</a>
	<br></br>
	<a href="bdm.changepassword">Change Password</a>
	<br></br>
	<a href="${logoutUrl}">Sign Out</a>
</body>
</html>
