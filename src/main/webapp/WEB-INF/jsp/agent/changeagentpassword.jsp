<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<style>
.error {
	color: red
}
</style>
<title>Change Password</title>
</head>
<h3>Change Password</h3>
<body>
	${message}

	<form action="agent.updatepassword" method="POST">
		<table>
			<tbody>
				<tr>
					<td>New Password</td>
					<td>: <input type="password" name="password1" required />
					</td>
				</tr>
				<tr>
					<td>Reconfirm Password</td>
					<td>: <input type="password" name="password2" required />
					</td>
				</tr>
			</tbody>
		</table>
		<br /> <input type="submit" value="CHANGE PASSWORD">
	</form>
</body>
</html>
