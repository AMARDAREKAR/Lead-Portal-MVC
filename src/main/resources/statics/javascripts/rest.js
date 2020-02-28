function getLoginInfo(){	
	var prefix = "http://localhost:8080/LeadPortal/getLoginInfo";
	 		$.ajax({
			        type: 'GET',
			        url:  prefix,
			        dataType: 'json',
			        async: false,
			        success: function(data) {	
			        	var temp1 = JSON.stringify(data);
			        	var temp2 = JSON.parse(temp1);
			        	document.getElementById("welcomeMessage").innerHTML=temp2;
			        }
	 		});
}
function getTlLeadsByDate() {	
	var url = "http://localhost:8080/LeadPortal/tl.searchLeadsByDate";
	getLeadsForTL(url);
}
function getLeadsForTL(pre) {			
	var fromDate=document.getElementById("fromDate").value, 
		toDate=document.getElementById("toDate").value;
	var prefix = pre+"?fromDate="+fromDate+"&toDate="+toDate;
	
	    $.ajax({
        type: 'GET',
        url:  prefix,
        dataType: 'json',
        async: false,
        success: function(data) {
        	
        	var temp1 = JSON.stringify(data);
        	var temp2 = JSON.parse(temp1, function (key, value) {
        		  if (key == "date") {
        			  		        			  
        			  var d = new Date(value),
        			  month = '' + (d.getMonth() + 1),
        		      day = '' + d.getDate(),
        		      year = d.getFullYear();

        		    if (month.length < 2) month = '0' + month;
        		    if (day.length < 2) day = '0' + day;

        		    return [year, month, day].join('-');
        		    
        			  
        		  } else {
        			    return value;
        			  }
        			});
        	
        	var row = '';
            $.each(temp2, function (i, item) {
            	row = row + 
            	'<tr><td>'  + item.date + 
                '</td><td>' + '<a href="tl.deleteLead?leadID='+item.leadID+'">Delete</a>' +
                '</td><td>' + '<a href="tl.updateLeadPage?leadID='+item.leadID+'">Update</a>'+
                '</td><td>' + item.agent.employeeId + 
                '</td><td>' + item.agent.firstName +" "+ item.agent.lastName +
                '</td><td>' + item.teamLead.employeeId + 
                '</td><td>' + item.department + 
                '</td><td>' + item.campaign.campaignName + 
                '</td><td>' + item.asset.assetName + 
                '</td><td>' + item.prospect.firstName + 
                '</td><td>' + item.prospect.lastName + 
                '</td><td>' + item.prospect.email + 
                '</td><td>' + item.company.companyName + 		                 		    
                '</td><td>' + item.prospect.jobTitle + 
                '</td><td>' + item.prospect.jobLevel +
                '</td><td>' + item.prospect.jobDepartment +
                '</td><td>' + item.company.address.addressLine1 + 
                '</td><td>' + item.company.address.addressLine2 + 		                
                '</td><td>' + item.company.address.state + 
                '</td><td>' + item.company.address.city + 
                '</td><td>' + item.company.address.zipcode +
                '</td><td>' + item.company.address.country + 
                '</td><td>' + item.prospect.phoneNumber + 
                '</td><td>' + item.prospect.directNumber +
                '</td><td>' + item.company.employeeSize + 
                '</td><td>' + item.company.revenueSize + 
                '</td><td>' + item.company.industryType + 
                '</td><td>' + item.company.sicCode + 
                '</td><td>' + item.company.naicsCode + 
                '</td><td>' + item.prospect.jobTitleLink +            		               
                '</td><td>' + item.company.employeeSizeLink + 
                '</td><td>' + item.company.revenueSizeLink + 
                '</td><td>' + item.company.industryTypeLink + 
                '</td><td>' + item.company.sicCodeLink + 
                '</td><td>' + item.company.naicsCodeLink + 
                '</td><td>' + item.company.domain +
                '</td><td>' + item.leadStatus +'<a href="tl.changeSatus?leadID='+item.leadID+'"> Change</a>' +
                '</td></tr>';
            });
            $("#leadTableTL tbody tr").remove(); 
            $('#leadTableTL').append(row);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
   });
}