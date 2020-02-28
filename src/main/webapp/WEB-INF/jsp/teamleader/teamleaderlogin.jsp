<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>

<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
	<script src="<spring:url value="/resources/javascripts/rest.js"/>"></script>
	<link rel="stylesheet" type="text/css" href="<spring:url value="/resources/css/leadportal.css"/>"></link>
</head>

<body>
	<form>
		<input type="date" value="${fromDate}" id="fromDate" name="fromDate" />
		<input type="date" value="${toDate}" id="toDate" name="toDate" />
		<button type=button id="teamleadsearch" onclick='getTlLeadsByDate()'>SEARCH</button>
	</form>

	<h3>Leads</h3>
	<div style="white-space: nowrap; overflow-x: auto; overflow-y: visible; height: 50vh;">
		<table id="leadTableTL">
			<thead>
				<tr>
					<th>Lead Date</th>
					<th>Delete</th>
					<th>Update</th>
					<th>RA Employee Id</th>
					<th>RA Name</th>
					<th>Team Lead Employee Id</th>
					<th>Department</th>
					<th>Campaign ID</th>
					<th>Asset Title</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>					
					<th>Company Name</th>
					<th>Job Title</th>
					<th>Job Level</th>
					<th>Job Department</th>
					<th>Address Line1</th>
					<th>Address Line2</th>
					<th>State</th>
					<th>City</th>
					<th>Zip Code</th>
					<th>Country</th>
					<th>Phone Number</th>
					<th>Direct Number</th>
					<th>Employee Size</th>
					<th>Revenue Size</th>
					<th>Industry Type</th>
					<th>SIC Code </th>
					<th>NAICS Code </th>
					<th>Job Title Link</th>
					<th>Employee Size Link</th>
					<th>Revenue Size Link</th>
					<th>Industry Type Link</th>
					<th>SIC Code Link</th>
					<th>NAICS Code Link</th>
					<th>Domain</th>
					<th>Lead Status</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<script>
	getTlLeadsByDate()
	
	var input = document.getElementById("fromDate");
	input.addEventListener("keyup", function(event) {
  	if (event.keyCode === 13) {
   	event.preventDefault();
   	document.getElementById("teamleadsearch").click();
  	}
	});
	input = document.getElementById("toDate");
	input.addEventListener("keyup", function(event) {
  	if (event.keyCode === 13) {
   	event.preventDefault();
   	document.getElementById("teamleadsearch").click();
  	}
	});
	</script>	
	<br />
</body>
</html>