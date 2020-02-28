// Restricts input for the given textbox to the given inputFilter.
function setInputFilter(textbox, inputFilter) {
	[ "input", "keydown", "keyup", "mousedown", "mouseup", "select",
			"contextmenu", "drop" ].forEach(function(event) {
		textbox.addEventListener(event, function() {
			if (inputFilter(this.value)) {
				this.oldValue = this.value;
				this.oldSelectionStart = this.selectionStart;
				this.oldSelectionEnd = this.selectionEnd;
			} else if (this.hasOwnProperty("oldValue")) {
				this.value = this.oldValue;
				this.setSelectionRange(this.oldSelectionStart,
						this.oldSelectionEnd);
			}
		});
	});
}

function validateEmployeeForm() {
	  var firstName = document.forms["addEmployeeForm"]["firstName"].value;
	  var lastName = document.forms["addEmployeeForm"]["lastName"].value;
	  var employeeId = document.forms["addEmployeeForm"]["employeeId"].value;
	  var password = document.forms["addEmployeeForm"]["password"].value;
	   
	  if (firstName == "" || lastName == "" || employeeId == "" || password == "" ){
	    alert("Please fill the complete form");
	    return false;
	  }
	}

function validateLeadForm() {
	  var firstName = document.forms["leadForm"]["firstName"].value;
	  var lastName = document.forms["leadForm"]["lastName"].value;
	  var email = document.forms["leadForm"]["email"].value;
	  var phoneNumber = document.forms["leadForm"]["phoneNumber"].value;
	  var jobTitle = document.forms["leadForm"]["jobTitle"].value;
	  var jobTitleLink = document.forms["leadForm"]["jobTitleLink"].value;
	  var companyname = document.forms["leadForm"]["companyname"].value;
	  var industrytype = document.forms["leadForm"]["industrytype"].value;
	  var companynamelink = document.forms["leadForm"]["companynamelink"].value;
	  var addressLine1 = document.forms["leadForm"]["addressLine1"].value;
	  var addressLine2= document.forms["leadForm"]["addressLine2"].value;
	  var country = document.forms["leadForm"]["country"].value;
	  var state = document.forms["leadForm"]["state"].value;
	  var city = document.forms["leadForm"]["city"].value;
	  var zipcode = document.forms["leadForm"]["zipcode"].value;
	  
	  if (firstName == "" || lastName == "" || email == "" || phoneNumber == "" || jobTitle == "" || jobTitleLink == "" || companyname == "" || industrytype == "" || companynamelink == "" || address == "") {
	    alert("Please fill the complete form");
	    return false;
	  }
	}