<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
	<style>  .error{color:red}  </style>  
	<title>Modify Lead</title>
	<script src="<spring:url value="/resources/javascripts/validation.js"/>"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
	<script src="<spring:url value="/resources/javascripts/agent.js"/>"></script>

</head>
<h4>Modify Lead</h4>
<body>
	<form:form id="leadForm" name="leadForm" action="agent.updateLead" method="POST" modelAttribute="updateLead">
		<c:set var="Date" value="<%=new java.util.Date()%>" />
		<table>
			<tbody >			
				<tr>
					<td>Lead Date</td>
					<td>: <form:input id="date" type="date" path="date" /></td>
				</tr>
				<tr>
					<td>Agent Employee Id</td>
					<td>: <form:input id="agentID" path="agent.employeeId" /></td>
				</tr>
				<tr>
					<td>Team Lead Employee Id</td>
					<td>: <form:select type="number" path="teamLead.employeeId">
							<form:options items="${teamLeads}" />
						</form:select></td>
				</tr>	
				<tr>
					<td>Campaign Name:</td>
					<td>: <form:select id="selectCampaignName" path="campaign.campaignID" onchange="getAssetNames()">
							<form:options items="${campaignNames}" />
						</form:select></td>
				</tr>
				<tr>
					<td>Asset Name:</td>
					<td>: <form:select id="selectAssetName" path="asset.assetID" onchange="getCustomQuestions()">
							<form:options items="${assetNames}" />
						</form:select></td>
				</tr>
				<tr>
					<td>Department:</td>
					<td>: <form:select path="department">
							<form:option value="Tele Marketing"/>
							<form:option value="Email Marketing"/>
							<form:option value="CD"/>
						</form:select></td>
					<td><form:errors path="department" cssClass="error"/></td>
				</tr>
				<tr>
					<td>First name</td>
					<td>: <form:input path="prospect.firstName" /></td>
					<td><form:errors path="prospect.firstName" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Last name</td>
					<td>: <form:input path="prospect.lastName" /></td>
					<td><form:errors path="prospect.lastName" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Email Id</td>
					<td>: <form:input type="email" path="prospect.email" /></td>
					<td><form:errors path="prospect.email" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Phone Number</td>
					<td>: <form:input id="phoneNumber" path="prospect.phoneNumber" /></td>
					<td><form:errors path="prospect.phoneNumber" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Direct Number</td>
					<td>: <form:input id="directNumber" path="prospect.directNumber" /></td>
					<td><form:errors path="prospect.directNumber" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Job Title</td>
					<td>: <form:input path="prospect.jobTitle" /></td>
					<td><form:errors path="prospect.jobTitle" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Job Level</td>
					<td>: <form:select path="prospect.jobLevel">
							<form:option value="Staff" label="Staff" />
							<form:option value="Manager" label="Manager" />
							<form:option value="Director" label="Director" />
							<form:option value="Vice President" label="Vice President" />
							<form:option value="C-Level" label="C-Level" />
							<form:option value="Others" label="Others" />
						</form:select></td>
					<td><form:errors path="prospect.jobLevel" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Job Department</td>
					<td>: <form:select path="prospect.jobDepartment">
							<form:option value="Operations" label="Operations" />
							<form:option value="Information Technology" label="Information Technology" />
							<form:option value="Finance" label="Finance" />
							<form:option value="Marketing" label="Marketing" />
							<form:option value="Sales" label="Sales" />
							<form:option value="Human Resources" label="Human Resources" />
							<form:option value="Facility" label="Facility" />
							<form:option value="Others" label="Others" />
						</form:select></td>
					<td><form:errors path="prospect.jobDepartment" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Job Title Link</td>
					<td>: <form:input path="prospect.jobTitleLink" /></td>
					<td><form:errors path="prospect.jobTitleLink" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Company Name</td>
					<td>: <form:input path="company.companyName" /></td>
					<td><form:errors path="company.companyName" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Industry Type</td>
					<td>: <form:input path="company.industryType" /></td>
					<td><form:errors path="company.industryType" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Industry Type Link</td>
					<td>: <form:input path="company.industryTypeLink" /></td>
					<td><form:errors path="company.industryTypeLink" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Company Employee Size</td>
					<td>: <form:select path="company.employeeSize">
							<form:option value="1-10" label="1-10" />
							<form:option value="11-50" label="11-50" />
							<form:option value="51-200" label="51-200" />
							<form:option value="201-500" label="201-500" />
							<form:option value="501-1000" label="501-1000" />
							<form:option value="1001-5000" label="1001-2000" />
							<form:option value="5001-10000" label="5001-10000" />
							<form:option value="10000+" label="10000+" />
						</form:select></td>
					<td><form:errors path="company.employeeSize" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Employee Size Link</td>
					<td>: <form:input path="company.employeeSizeLink" /></td>
					<td><form:errors path="company.employeeSizeLink" cssClass="error"/></td> 
				</tr>
				<tr>
					<td>Revenue Size</td>
					<td>: <form:select path="company.revenueSize">
							<form:option value="$1K-$500K" label="$1K-$500K" />
							<form:option value="$500K-$1M" label="$500K-$1M" />
							<form:option value="$1M-$5M" label="$1M-$5M" />
							<form:option value="$5M-$10M" label="$5M-$10M" />
							<form:option value="$10M-$20M" label="$10M-$20M" />
							<form:option value="$20M-$50M" label="$20M-$50M" />
							<form:option value="$50M-$100M" label="$50M-$100M" />
							<form:option value="$100M-$250M" label="$100M-$250M" />
							<form:option value="$250M-$500M" label="$250M-$500M" />
							<form:option value="$500M-$1B" label="$500M-$1B" />
							<form:option value="$1B-$5B" label="$1B-$5B" />
							<form:option value="$5B+" label="$5B+" />
						</form:select></td>
					<td><form:errors path="company.revenueSize" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Revenue Size Link</td>
					<td>: <form:input path="company.revenueSizeLink" /></td>
					<td><form:errors path="company.revenueSizeLink" cssClass="error"/></td>
				</tr>
				<tr>
					<td>SIC Code</td>
					<td>: <form:input path="company.sicCode" /></td>
					<td><form:errors path="company.sicCode" cssClass="error"/></td>
				</tr>
				<tr>
					<td>SIC Code Link</td>
					<td>: <form:input path="company.sicCodeLink" /></td>
					<td><form:errors path="company.sicCodeLink" cssClass="error"/></td>
				</tr>
				<tr>
					<td>NAICS Code</td>
					<td>: <form:input path="company.naicsCode" /></td>
					<td><form:errors path="company.naicsCode" cssClass="error"/></td>
				</tr>
				<tr>
					<td>NAICS Code Link</td>
					<td>: <form:input path="company.naicsCodeLink" /></td>
					<td><form:errors path="company.naicsCodeLink" cssClass="error"/></td>
				</tr>
				
				<tr>
					<td>Address Line 1</td>
					<td>: <form:input path="company.address.addressLine1" /></td>
					<td><form:errors path="company.address.addressLine1" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Address Line 2</td>
					<td>: <form:input path="company.address.addressLine2" /></td>
					<td><form:errors path="company.address.addressLine2" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Country</td>
					<td>: <form:input path="company.address.country" /></td>
					<td><form:errors path="company.address.country" cssClass="error"/></td>
				</tr>
				<tr>
					<td>State</td>
					<td>: <form:input path="company.address.state" /></td>
					<td><form:errors path="company.address.state" cssClass="error"/></td>
				</tr>
				<tr>
					<td>City</td>
					<td>: <form:input path="company.address.city" /></td>
					<td><form:errors path="company.address.city" cssClass="error"/></td>
				</tr>
				<tr>
					<td>ZipCode</td>
					<td>: <form:input path="company.address.zipcode" /></td>
					<td><form:errors path="company.address.zipcode" cssClass="error"/></td>
				</tr>
				<tr>
					<td>Domain</td>
					<td>: <form:input path="company.domain" /></td>
					<td><form:errors path="company.domain" cssClass="error"/></td>
				</tr>
			</tbody>
		</table>
		<form:hidden value="PENDING" path="leadStatus" />
		<form:hidden path="leadID" />
		
		<div>
			<table id="customQuestions">
			<c:forEach items="${updateLead.answer}" var="answer" varStatus="status">
				<tr>
					<td>${answer.questionID.question}</td>
					<td>: <input name="answerSet" value="${answer.answer}"/></td>
				</tr>
			</c:forEach>
			</table>
		</div>
		<input type="submit" value="UPDATE LEAD" />
	</form:form>
	<script>
	document.getElementById("date").readOnly = true;
	document.getElementById("agentID").readOnly = true;
	</script>
</body>
</html>