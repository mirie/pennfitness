
// Functions for my account dialog

function saveGp() {
	if (YAHOO.util.Dom.get("groupName").value == "") {
		alert("please give the group a name before saving!");
		return;
	}
			
	// FOR JSON Handling
	var successHandler = function(o) {	
		var response;
		
	    // Use the JSON Utility to parse the data returned from the server
	    try {
	       response = YAHOO.lang.JSON.parse(o.responseText); 
	    }
	    catch (x) {
	        alert("JSON Parse failed!");
	        return;
	    }        
	
	    if (response.STATUS == 'Success') { // GroupID passed back if just saved a new group
			alert("Group saved successfully");
			if (document.getElementById("groupID").value == -1) {
				//alert('saving groupID: ' + response.DATA.GroupID);
				document.getElementById("groupID").value = response.DATA.GroupID;
			} 
			//YAHOO.leftTB.group.getNewGroupNames();
		}
		else {
			alert("Group was not saved!");
		}
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000,
	}
	
	// POST string data
	var strData = "";
	strData += "groupName=" + document.getElementById("groupName").value + "&";
	strData += "groupDesc=" + document.getElementById("groupDescription").value + "&";
	
	// Append GroupID to strData
	strData += "groupID=" + document.getElementById("groupID").value.trim();
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "saveGroup.do", callback, strData);
}

