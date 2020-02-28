function getAgentLeadsByDate() {	
	var url = "http://localhost:8080/LeadPortal/agent.searchLeadsByDate";
	getLeadsForAgent(url);
}
function getLeadsForAgent(pre) {
	var fromDate = document.getElementById("fromDate").value, toDate = document
			.getElementById("toDate").value;
	var prefix = pre + "?fromDate=" + fromDate + "&toDate=" + toDate;

	$.ajax({
		type : 'GET',
		url : prefix,
		dataType : 'json',
		async : true,
		success : function(data) {

			var temp1 = JSON.stringify(data);
			var temp2 = JSON.parse(temp1,
					function(key, value) {
						if (key == "date") {

							var d = new Date(value), month = ''
									+ (d.getMonth() + 1), day = ''
									+ d.getDate(), year = d.getFullYear();

							if (month.length < 2)
								month = '0' + month;
							if (day.length < 2)
								day = '0' + day;

							return [ year, month, day ].join('-');

						} else {
							return value;
						}
					});

			var row = '';
			$.each(temp2, function(i, item) {

				row = row + '<tr><td>' 
						+ item.date + '</td><td>'
						+ '<a href="agent.deleteLead?leadID='+ item.leadID + '">Delete</a>' + '</td><td>'
						+ '<a href="agent.updateLeadPage?leadID=' + item.leadID	+ '">Update</a>'+'</td><td>'
						+ item.agent.employeeId + '</td><td>'
						+ item.agent.firstName + " " 
						+ item.agent.lastName + '</td><td>' 
						+ item.teamLead.employeeId + '</td><td>' 
						+ item.department + '</td><td>'
						+ item.campaign.campaignName + '</td><td>'
						+ item.asset.assetName + '</td><td>'
						+ item.prospect.firstName + '</td><td>'
						+ item.prospect.lastName + '</td><td>'
						+ item.prospect.email + '</td><td>'
						+ item.company.companyName + '</td><td>'
						+ item.prospect.jobTitle + '</td><td>'
						+ item.prospect.jobLevel + '</td><td>'
						+ item.prospect.jobDepartment + '</td><td>'
						+ item.company.address.addressLine1 + '</td><td>'
						+ item.company.address.addressLine2 + '</td><td>'
						+ item.company.address.state + '</td><td>'
						+ item.company.address.city + '</td><td>'
						+ item.company.address.zipcode + '</td><td>'
						+ item.company.address.country + '</td><td>'
						+ item.prospect.phoneNumber + '</td><td>'
						+ item.prospect.directNumber + '</td><td>'
						+ item.company.employeeSize + '</td><td>'
						+ item.company.revenueSize + '</td><td>'
						+ item.company.industryType + '</td><td>'
						+ item.company.sicCode + '</td><td>'
						+ item.company.naicsCode + '</td><td>'
						+ item.prospect.jobTitleLink + '</td><td>'
						+ item.company.employeeSizeLink + '</td><td>'
						+ item.company.revenueSizeLink + '</td><td>'
						+ item.company.industryTypeLink + '</td><td>'
						+ item.company.sicCodeLink + '</td><td>'
						+ item.company.naicsCodeLink + '</td><td>'
						+ item.company.domain + '</td><td>' 
						+ item.leadStatus
						+ '</td></tr>';
			});
			$("#leadTable tbody tr").remove();
			$('#leadTable').append(row);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
}
//upload lead
function getCampaignN(){	
	var prefix = "http://localhost:8080/LeadPortal/getCampaigns";	
	$.ajax({
        type: 'GET',
        url:  prefix,
        dataType: 'json',
        async: false,
        success: function(data) {	
        	var temp1 = JSON.stringify(data);
        	var temp2 = JSON.parse(temp1);
        	var campaignList = new Array();
        	$.each(temp2, function (i, item) {
        		campaignList.push(item.campaignName);
            });
        	console.log(campaignList);
        	return campaignList;
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
        });
}

function getCampaignNames(){	
	var prefix = "http://localhost:8080/LeadPortal/getCampaigns";	
	var getcampaigndropdown = document.getElementById('selectCampaignName');
	while (getcampaigndropdown.hasChildNodes()) {
		getcampaigndropdown.removeChild(getcampaigndropdown.lastChild);
    }
	 		$.ajax({
			        type: 'GET',
			        url:  prefix,
			        dataType: 'json',
			        async: false,
			        success: function(data) {	
			        	var temp1 = JSON.stringify(data);
			        	var temp2 = JSON.parse(temp1);
			        	
			        	$.each(temp2, function (i, item) {
			        		getcampaigndropdown.innerHTML = getcampaigndropdown.innerHTML +
			        		"<option value="+item.campaignID+" label='"+item.campaignName+"'/>";
			            });
			        	getAssetNames();
			        },
			        error: function(jqXHR, textStatus, errorThrown) {
			            alert(jqXHR.status + ' ' + jqXHR.responseText);
			        }
			        });
}
function getAssetNames(){
	var prefix = "http://localhost:8080/LeadPortal/getAssetNames?campaignID="+document.getElementById("selectCampaignName").value;
	var selectassetdropdown = document.getElementById('selectAssetName');
	while (selectassetdropdown.hasChildNodes()) {
		selectassetdropdown.removeChild(selectassetdropdown.lastChild);
    }
			$.ajax({
			        type: 'GET',
			        url:  prefix,
			        dataType: 'json',
			        async: false,
			        success: function(data) {	
			        	var temp1 = JSON.stringify(data);
			        	var temp2 = JSON.parse(temp1);
			        	
			        	$.each(temp2, function (i, item) {
			        		var option = document.createElement("option");
			        		option.value = item.assetID;
			        		option.label = item.assetName;
			        		selectassetdropdown.appendChild(option);
			            });
			        	getCustomQuestions();
			        },
			        error: function(jqXHR, textStatus, errorThrown) {
			            alert(jqXHR.status + ' ' + jqXHR.responseText);
			        }
			        });
}
function getCustomQuestions() {
	var prefix = "http://localhost:8080/LeadPortal/getCustomQuestions?assetID="+ document.getElementById("selectAssetName").value;
	var customQuestions = document.getElementById('customQuestions');
	while (customQuestions.hasChildNodes()) {
		customQuestions.removeChild(customQuestions.lastChild);
    }

	$.ajax({
		async: false,
		type : 'GET',
		url : prefix,
		dataType : 'json',
		success : function(data) {
			var temp1 = JSON.stringify(data);
			var questions = JSON.parse(temp1);
			
			if(questions.length>0)
			{
				$.each(questions,function(i, item) {					
					var prefix1 = "http://localhost:8080/LeadPortal/getOptionsForCustomQuestion?questionID="+item.questionID;
					$.ajax({
						async: false,
						type : 'GET',
						url : prefix1,
						dataType : 'json',
						success : function(data1) {
							var temp3 = JSON.stringify(data1);
							var options = JSON.parse(temp3);							
							
							var row = document.createElement("tr");
							var col1 = document.createElement("td");
							var label = document.createElement("label");
							label.innerText=item.question;
							col1.appendChild(label);
							row.appendChild(col1);
							
							if(options.length>0)
							{								
								var col2 = document.createElement("td");
								var input = document.createElement("select");
								input.name="answerSet";
								input.id=item.questionID;
								
								$.each(options,function(j, items) {	
									
									var option = document.createElement("option");
									option.value = items.optionName;
									option.label = items.optionName;
									input.appendChild(option);
									});
								
								col2.appendChild(input);
								row.appendChild(col2);									
								customQuestions.appendChild(row);
							}
							else
							{		
								var col2 = document.createElement("td");
								var input = document.createElement("input");
								input.name = "answerSet";
								input.id=item.questionID;
								col2.appendChild(input);
								row.appendChild(col2);
								customQuestions.appendChild(row)
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							return true;
						}
					});
					
				});
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
		
	});
}