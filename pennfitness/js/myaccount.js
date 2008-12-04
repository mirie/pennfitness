
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
function populateMyGroupByUserID()
{		
	var successHandler = function(o) {
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		var groupSelect = document.getElementById("myGroupList");
		while(groupSelect.hasChildNodes() == true){
			groupSelect.removeChild(groupSelect.childNodes[0]);
		}
		
		groupSelect.innerHTML = jResponse.DATA;
		
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	var strData = "action=getGroups";
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgGroupReg.do", callback, strData);
}

function populateRouteByUserID()
{		

	var successHandler = function(o) {
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		var groupSelect = document.getElementById("myRouteList");
		while(groupSelect.hasChildNodes() == true){
			groupSelect.removeChild(groupSelect.childNodes[0]);
		}
		
		groupSelect.innerHTML = jResponse.DATA.CONTENT;
		
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	var strData = "action=getRoutes";
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "searchRoute.do", callback, strData);
}

function populateRegisteredEventByUserID()
{		

	var successHandler = function(o) {
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		var groupSelect = document.getElementById("myRegisteredEventList");
		while(groupSelect.hasChildNodes() == true){
			groupSelect.removeChild(groupSelect.childNodes[0]);
		}
		
		groupSelect.innerHTML = jResponse.DATA.CONTENT;
		
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	var strData = "action=getEventsRegistered";
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "searchEvent.do", callback, strData);
}

function populateCreatedEventByUserID()
{		

	var successHandler = function(o) {
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		var groupSelect = document.getElementById("myCreatedEventList");
		while(groupSelect.hasChildNodes() == true){
			groupSelect.removeChild(groupSelect.childNodes[0]);
		}
		
		groupSelect.innerHTML = jResponse.DATA.CONTENT;
		
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	var strData = "action=getEventsCreated";
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "searchEvent.do", callback, strData);
}

function GetGroupDetail( groupID )
{
		var successHandler = function(o) {
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		var groupDetail = document.getElementById("myGroupDetail");
		while(groupDetail.hasChildNodes() == true){
			groupDetail.removeChild( groupDetail.childNodes[0] );
		}
		
		groupDetail.appendChild( document.createTextNode(jResponse.DATA.NAME) );
		groupDetail.appendChild( document.createTextNode(jResponse.DATA.CREATE_DATE) );
		groupDetail.appendChild( document.createTextNode(jResponse.DATA.CREATED_BY) );
		groupDetail.appendChild( document.createTextNode(jResponse.DATA.DESCRIPTION) );
		groupDetail.appendChild( document.createTextNode(jResponse.DATA.MEMBER_COUNT) );

	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	var strData = "action=getDetailGroupInfo&groupID="+groupID;
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgGroup.do", callback, strData);

}
function modifyPersonalInfo() {	

	document.getElementById("userGenderTxt").style.display = "block";
	document.getElementById("userEmailTxt").style.display = "block";
	document.getElementById("userPasswordTxt").style.display = "block";
	
	document.getElementById("userGender").style.display = "none";
	document.getElementById("userEmail").style.display = "none";
	document.getElementById("userPassword").style.display = "none";
	
	document.getElementById("savePersonalInfo").style.display = "block";
	document.getElementById("modifyPersonalInfo").style.display = "none";

}
function cancelPersonalInfo() {	

	document.getElementById("userGenderTxt").style.display = "none";
	document.getElementById("userEmailTxt").style.display = "none";
	document.getElementById("userPasswordTxt").style.display = "none";
	
	document.getElementById("userName").style.display = "block";
	document.getElementById("userGender").style.display = "block";
	document.getElementById("userEmail").style.display = "block";
	document.getElementById("userPassword").style.display = "block";
	
	document.getElementById("savePersonalInfo").style.display = "none";
	document.getElementById("modifyPersonalInfo").style.display = "block";

}
// ***********************************************************************
// Function: savePersonalInfo
// Saves the modified personal information. 
// Displays alert if missing user name. 
// ***********************************************************************
function savePersonalInfo() {
			
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
	
	    if (response.STATUS == 'Success') { // Modified UserID passed back
			alert("User Information modified successfully");
			
			//YAHOO.leftTB.group.getNewGroupNames();
		}
		else {
			alert("Personal Information was not saved!");
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
	strData += "gender=" + document.getElementById("userGenderTxt").value + "&";
	strData += "email=" + document.getElementById("userEmailTxt").value + "&";
	strData += "password=" + document.getElementById("userPasswordTxt").value + "&";
	
	// Append GroupID to strData
	//strData += "userID=" + document.getElementById("user.getUserID()").value.trim();
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "modifyUser.do", callback, strData);
}

//Listeners
YAHOO.util.Event.addListener("modifyPersonalInfoBtn", "click", modifyPersonalInfo);	
YAHOO.util.Event.addListener("cancelPersonalInfoBtn", "click", cancelPersonalInfo);
YAHOO.util.Event.addListener("savePersonalInfoBtn", "click", savePersonalInfo);
