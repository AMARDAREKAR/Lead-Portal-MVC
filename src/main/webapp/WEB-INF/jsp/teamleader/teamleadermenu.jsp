<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<c:url value="/logout" var="logoutUrl" />
	<a href="tl.leadManagemet">Lead Management</a>
	<br></br>
	<a href="tl.addnewuser">Employee Management</a>
	<br></br>
	<a href="tl.changepassword">Change Password</a>
	<br></br>
	<a href="${logoutUrl}">Sign Out</a>
</body>
</html>
