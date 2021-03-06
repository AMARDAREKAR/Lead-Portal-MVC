<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/css/leadportal.css"/>"></link>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script src="<spring:url value="/resources/javascripts/bdm.js"/>"></script>
</head>
<form>
	<input type="date" value="${fromDate}" id="fromDate" name="fromDate" />
	<input type="date" value="${toDate}" id="toDate" name="toDate" />
	<button type=button id="bdmleadsearch" onclick='getBdmLeadsByDate()'>SEARCH</button>
	
	Filter : 
	<select id="filter" onchange="getBdmLeadsByDate()">
		<option value="ALL">ALL LEADS</option>
	</select>
	<script>getAssetsForFilter()</script>
</form>

<h3>Leads</h3>
<div id=leadTableDiv>
	<table id="leadTable">
		<thead>
			<tr>
					<th>Lead Date</th>
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
getBdmLeadsByDate()
var input = document.getElementById("fromDate");
	input.addEventListener("keyup", function(event) {
  	if (event.keyCode === 13) {
   	event.preventDefault();
   	document.getElementById("bdmleadsearch").click();
  	}
	});
	input = document.getElementById("toDate");
	input.addEventListener("keyup", function(event) {
  	if (event.keyCode === 13) {
   	event.preventDefault();
   	document.getElementById("bdmleadsearch").click();
  	}
	});
</script>	
<br></br>
<button type=button onclick='exportToExcel()'>EXPORT LEADS</button>
</body>
</html>
