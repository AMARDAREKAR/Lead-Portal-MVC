// display leads on login page
function getBdmLeadsByDate() {	
	var url = "http://localhost:8080/LeadPortal/bdm.searchLeadsByDate";
	getLeadsForBdm(url);
}
function getAssetsForFilter()
{
	var filter = document.getElementById('filter');
	while (filter.hasChildNodes()) {
		filter.removeChild(filter.lastChild);
    }
	
	filter.innerHTML = filter.innerHTML + "<option value=0 label='ALL LEADS'/>";
	
	var prefix = "http://localhost:8080/LeadPortal/getAllAssets";		
    $.ajax({
    	type: 'GET',
    	url:  prefix,
    	dataType: 'json',
    	async: false,
    	success: function(data)
    	{
    		var temp1 = JSON.stringify(data);
        	var assetList = JSON.parse(temp1);			        		        	
        	
        	$.each(assetList, function (j, assetData) 
        	{
        		filter.innerHTML = filter.innerHTML +
        		"<option value="+assetData.assetID+" label='"+assetData.assetName+"'/>";
        	});
        }
    });
}
function exportToExcel()
{
	var pre = "http://localhost:8080/LeadPortal/bdm.exporttoexcel",
	fromDate=document.getElementById("fromDate").value, 
	toDate=document.getElementById("toDate").value;
	assetID=document.getElementById("filter").value;
	var url = pre+"?fromDate="+fromDate+"&toDate="+toDate+"&assetID="+assetID;
	window.open(url);
}
function getLeadsForBdm(pre) {			
	var fromDate=document.getElementById("fromDate").value, 
		toDate=document.getElementById("toDate").value
		assetID=document.getElementById("filter").value;
	var prefix = pre+"?fromDate="+fromDate+"&toDate="+toDate+"&assetID="+assetID;
	
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
                '</td><td>' + item.leadStatus +
                '</td></tr>';
            });
            $("#leadTable tbody tr").remove(); 
            $('#leadTable').append(row);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
   });
}

// upload campaign, asset, question, option
function getCampaignInformation()
{
	var prefix = "http://localhost:8080/LeadPortal/getCampaigns";
	$.ajax({
		type: 'GET',
		url:  prefix,
		dataType: 'json',
		async: false,
		success: function(data) 	
		{
			$("#campaignTable tbody tr").remove(); 
			
			var temp1 = JSON.stringify(data);
			var campaignList = JSON.parse(temp1);
			
			$.each(campaignList, function (i, campaignData) 
			{
				getAssetInformation(campaignData);
			});
		}
	});
}
function getAssetInformation(campaignData)
{
	var campaignText;
	var prefix = "http://localhost:8080/LeadPortal/getAssetNames?campaignID="+campaignData.campaignID;		
    $.ajax({
    	type: 'GET',
    	url:  prefix,
    	dataType: 'json',
    	async: false,
    	success: function(data)
    	{
    		var temp1 = JSON.stringify(data);
        	var assetList = JSON.parse(temp1);			        		        	
        	
        	$.each(assetList, function (j, assetData) 
        	{
        		if(j==0)
        			campaignText = campaignData.campaignName;
			    else
			    	campaignText = "";
        		getQuestionInformation(campaignText,assetData);
        	});
        }
    });
}
function getQuestionInformation(campaignText,assetData)
{
	var assetText;
	var prefix = "http://localhost:8080/LeadPortal/getCustomQuestions?assetID="+assetData.assetID;
	$.ajax({
		type: 'GET',
		url:  prefix,
		dataType: 'json',
		async: false,
		success: function(data) 
		{
			var temp1 = JSON.stringify(data);
	    	var customQuestionList = JSON.parse(temp1);    
	    	
	    	if(customQuestionList.length>0)
	    	{
		    	$.each(customQuestionList, function (k, questionData)
		    	{				    
		    		if(k==0)
		    		{	
		    			assetText = assetData.assetName;
		    		}
		    		else
				    {
				    	assetText = "";
				    	campaignText = "";
				    }
		    		getOptionInformation(campaignText,assetText,questionData);	
		    	});
	    	}
	    	else
	    	{
	    		printRow(campaignText,assetData.assetName,"","");
	    	}
		}
	});
}
function getOptionInformation(campaignText,assetText,questionData)
{
	var questionText;
    
	var prefix = "http://localhost:8080/LeadPortal/getOptionsForCustomQuestion?questionID="+questionData.questionID;
	$.ajax({
		type: 'GET',
		url:  prefix,
		dataType: 'json',
		async: false,
		success: function(data) 
		{
			var temp1 = JSON.stringify(data);
	    	var optionList = JSON.parse(temp1);    
	    	
	    	if(optionList.length>0)
	    	{
		    	$.each(optionList, function (l, optionData)
		    	{			
		    		if(l==0)
		    		{
		    			questionText = questionData.question;
		    		}
		    		else
    				{
		    			questionText = ""; 
		    			assetText = "";
    				}
		    			printRow(campaignText,assetText,questionText,optionData.optionName);	
		    	});
	    	}
	    	else
	    		printRow(campaignText,assetText,questionData.question,"");
		}
	});
}
function printRow(campaignText,assetText,questionText,optionText)
{
	var campaigninsertrow,campaignrow,assetrow,questionrow,optionrow,campaigncol,assetcol,questioncol,optioncol,questiontext,optiontext;
	
	campaigninsertrow = document.createElement("tr");
	
	campaignrow = document.createElement("tr");	
	campaigncol = document.createElement("td");	
	campaigntext = document.createTextNode(campaignText);
	assetrow = document.createElement("tr");
    assetcol = document.createElement("td");
    assettext = document.createTextNode(assetText);
    questionrow = document.createElement("tr");
    questioncol = document.createElement("td");
    questiontext = document.createTextNode(questionText);
    optionrow = document.createElement("tr");
    optioncol = document.createElement("td");
    optiontext = document.createTextNode(optionText);
    
	campaignrow.appendChild(campaigntext);
	campaigncol.appendChild(campaignrow);	
	assetrow.appendChild(assettext);
	assetcol.appendChild(assetrow);
	questionrow.appendChild(questiontext);
    questioncol.appendChild(questionrow);	
    optionrow.appendChild(optiontext);
    optioncol.appendChild(optionrow);
    
    campaigninsertrow.appendChild(campaigncol);
    campaigninsertrow.appendChild(assetcol);
    campaigninsertrow.appendChild(questioncol);
    campaigninsertrow.appendChild(optioncol);
    
    $('#campaignTable').append(campaigninsertrow);
}

// display campaign information
function getCampaignName(){	
	var prefix = "http://localhost:8080/LeadPortal/getCampaigns";	
	var selectcampaigndropdown = document.getElementById('selectCampaign');
	while (selectcampaigndropdown.hasChildNodes()) {
		selectcampaigndropdown.removeChild(selectcampaigndropdown.lastChild);
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
        		selectcampaigndropdown.innerHTML = selectcampaigndropdown.innerHTML +
        		"<option value="+item.campaignID+" label='"+item.campaignName+"'/>";
            });
        	getAssetName();
        }
	});
}
function getAssetName(){
	var prefix = "http://localhost:8080/LeadPortal/getAssetNames?campaignID="+document.getElementById("selectCampaign").value;
	var selectassetdropdown = document.getElementById('selectAsset');
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
        		selectassetdropdown.innerHTML = selectassetdropdown.innerHTML +
        		"<option value="+item.assetID+" label='"+item.assetName+"'/>";
            });
        	getCustomQuestion();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
        });
}
function getCustomQuestion(){
	var prefix = "http://localhost:8080/LeadPortal/getCustomQuestions?assetID="+document.getElementById("selectAsset").value;
	var selectquestiondropdown = document.getElementById('getCustomQuestion');
	while (selectquestiondropdown.hasChildNodes()) 
	{
		selectquestiondropdown.removeChild(selectquestiondropdown.lastChild);
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
        		selectquestiondropdown.innerHTML = selectquestiondropdown.innerHTML +
        		"<option value="+item.questionID+" label='"+item.question+"'/>";
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}