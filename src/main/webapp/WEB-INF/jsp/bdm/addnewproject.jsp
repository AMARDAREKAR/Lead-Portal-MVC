<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script src="<spring:url value="/resources/javascripts/bdm.js"/>"></script>
<title>Add New Campaign</title>
</head>
<body>
	<table>

		<tr>
			<td>
				<table>
					<tbody>
						<form:form action="bdm.uploadproject" modelAttribute="newproject"
							method="POST">
							<tr>
								<td><h4>Add New Campaign</h4></td>
							</tr>
							<tr>
								<td>Campaign Name</td>
								<td>: <form:input path="campaignName" /></td>
							</tr>
							<tr>
								<td><h4><input type="submit" value="ADD NEW CAMPAIGN" /></h4></td>
							</tr>							
						</form:form>
					</tbody>
				</table>	
			</td>
			<td>
				<form action="bdm.addAsset" method="POST">
						<h4>Add New Asset</h4>
						<table>
						<tbody>
						<tr>
							<td>
							Campaign Name :
							<select id="selectCampaign" name="campaignID" onchange="getAssetName()">
									
								</select>
							</td>
						</tr>
						<tr>
							<td>
							Asset Name : <input name="assetName" />
							<h4><input type="submit" value="ADD NEW ASSET" /></h4>	
							</td>
						</tr>
						</tbody>
						</table>							
				</form>
			</td>
		</tr>
		
		
		<tr>
			<td>
				<form action="bdm.addQuestion" method="POST">
						<h4>Add Custom Questions</h4>
						<table>
						<tbody>
						<tr>
							<td>
							Asset Name :
							<select id="selectAsset" name="selectAsset" onchange="getCustomQuestion()">
									
								</select>
							</td>
						</tr>
						<tr>
							<td>
							Question : <input name="question" />
							<h4><input type="submit" value="ADD NEW QUESTION" /></h4>	
							</td>
						</tr>
						</tbody>
						</table>					
				</form>
			</td>
			
			<td>
				<form action="bdm.addOption" method="POST">
						<h4>Add Options For Custom Questions</h4>
						<table>
						<tbody>
						<tr>
							<td>
							Question :
							<select id="getCustomQuestion" name="getCustomQuestion">
									
								</select>
							</td>
						</tr>
						<tr>
							<td>
							Option : <input id="optionName" name="optionName" />
							<h4><input type="submit" value="ADD NEW OPTION" /></h4>	
							</td>
						</tr>
						</tbody>
						</table>					
				</form>
				<script>getCampaignName()</script>
			</td>
		</tr>
	</table>
	
	<h3>CAMPAIGN LIST</h3>
	<div id='campaignTableDiv'>
		<table id="campaignTable" border="2" width="100%">
			<thead>
				<tr>
					<th>Campaigns</th>
					<th>Assets</th>
					<th>Questions</th>
					<th>Options</th>
				</tr>
			</thead>

			<tbody>
			</tbody>
		</table>
	</div>
	<script>getCampaignInformation()</script>
</body>
</html>
	