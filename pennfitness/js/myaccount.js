var saveGp;

var sendEmailToGroup, unsubscribeGroup;

// Functions for my account dialog
YAHOO.util.Event.onDOMReady(initPagForMyAccount);

YAHOO.util.Event.addListener("sendEmailToGroupBtn", "click", sendEmailToGroup);
YAHOO.util.Event.addListener("unsubscribeGroupBtn", "click", unsubscribeGroup);
YAHOO.util.Event.addListener("deleteGroupBtn", "click", deleteGroup);



// Listeners
YAHOO.util.Event.addListener("createGroup", "click", saveGp);


function initPagForMyAccount()
{
	// Paginator for event registered
	pagEVTregistered = new YAHOO.widget.Paginator({
		rowsPerPage  : 3,
	    totalRecords : 1,
	    containers   : ["pag_EVTregistered"], // or idStr or elem or [ elem, elem ]
	});		
	pagEVTregistered.render();
	pagEVTregistered.subscribe('changeRequest',pagEVTregisteredHandler); 

	// Paginator for event registered
	pagEVTcreated = new YAHOO.widget.Paginator({
		rowsPerPage  : 3,
	    totalRecords : 1,
	    containers   : ["pag_EVTcreated"], // or idStr or elem or [ elem, elem ]
	});		
	pagEVTcreated.render();
	pagEVTcreated.subscribe('changeRequest',pagEVTcreatedHandler); 

	// Paginator for my routes
	pagMyRouteList = new YAHOO.widget.Paginator({
		rowsPerPage  : 3,
	    totalRecords : 1,
	    containers   : ["pag_myRouteList"], // or idStr or elem or [ elem, elem ]
	});		
	pagMyRouteList.render();
	pagMyRouteList.subscribe('changeRequest',pagMyRouteListHandler);
	
	// Paginator for my routes
	pagMyGroupList = new YAHOO.widget.Paginator({
		rowsPerPage  : 3,
	    totalRecords : 1,
	    containers   : ["pag_myGroups"], // or idStr or elem or [ elem, elem ]
	});		
	pagMyGroupList.render();
	pagMyGroupList.subscribe('changeRequest',pagMyGroupListHandler); 
	
	// Paginator for my routes
	pagmyCreatedGroupList = new YAHOO.widget.Paginator({
		rowsPerPage  : 3,
	    totalRecords : 1,
	    containers   : ["pag_myCreatedGroupList"], // or idStr or elem or [ elem, elem ]
	});		
	pagmyCreatedGroupList.render();
	pagmyCreatedGroupList.subscribe('changeRequest',pagmyCreatedGroupListHandler); 
	
	
}

// Paginator handler for event search
function pagEVTregisteredHandler(newState)
{
	populateRegisteredEventByUserIDN(newState.rowsPerPage, newState.page);
	newState.paginator.setState(newState);
}

// Paginator handler for event search
function pagEVTcreatedHandler(newState)
{
	populateCreatedEventByUserID(newState.rowsPerPage, newState.page);
	newState.paginator.setState(newState);
}

// Paginator handler for event search
function pagMyRouteListHandler(newState)
{
	populateRouteByUserIDN(newState.rowsPerPage, newState.page);
	newState.paginator.setState(newState);
}

// Paginator handler for event search
function pagMyGroupListHandler(newState)
{
	populateMyRegisteredGroupByUserIDN(newState.rowsPerPage, newState.page);
	newState.paginator.setState(newState);
}

// Paginator handler for event search
function pagmyCreatedGroupListHandler(newState)
{
	populateMyCreatedGroupByUserIDN(newState.rowsPerPage, newState.page);
	newState.paginator.setState(newState);
}


function saveGp() {
	if (YAHOO.util.Dom.get("groupName").value == "") {
		alert("please give the group a name before saving!");
		return;
	}
			
	// FOR JSON Handling
	var successHandler = function(o) {	
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		alert("Group saved successfully");
		
		YAHOO.util.Dom.get("groupName").value = "";
		YAHOO.util.Dom.get("groupDescription").value = "";
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000,
	}
	
	YAHOO.util.Connect.setForm("frmCreateGroupData");
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgGroup.do", callback);
}

function populateMyCreatedGroupByUserID()
{
	populateMyCreatedGroupByUserIDN(3,1);
}

function populateMyCreatedGroupByUserIDN(recsPerPage, curPage)
{		
	var successHandler = function(o) {
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		var groupSelect = document.getElementById("myCreatedGroupList");
		while(groupSelect.hasChildNodes() == true){
			groupSelect.removeChild(groupSelect.childNodes[0]);
		}
		
	    pagmyCreatedGroupList.set('totalRecords',jResponse.DATA.TOTALRECCNT); 
		groupSelect.innerHTML = jResponse.DATA.CONTENT;
		
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	var strData = "action=getMyCreatedGroups&recsPerPage=" + recsPerPage + "&curPage=" + curPage;	
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgGroupReg.do", callback, strData);
}

function populateMyRegisteredGroupByUserID()
{
	populateMyRegisteredGroupByUserIDN(5,1);
}

function populateMyRegisteredGroupByUserIDN(recsPerPage, curPage)
{		
	var successHandler = function(o) {
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		var groupSelect = document.getElementById("myRegisteredGroupList");
		while(groupSelect.hasChildNodes() == true){
			groupSelect.removeChild(groupSelect.childNodes[0]);
		}
		
		pagMyGroupList.set('totalRecords',jResponse.DATA.TOTALRECCNT); 
		groupSelect.innerHTML = jResponse.DATA.CONTENT;
		
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	var strData = "action=getMyRegisteredGroups&recsPerPage=" + recsPerPage + "&curPage=" + curPage;	
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgGroupReg.do", callback, strData);
}

function populateRouteByUserID()
{
	populateRouteByUserIDN(3,1);
}

function populateRouteByUserIDN(recsPerPage, curPage)
{		

	var successHandler = function(o) {
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		var groupSelect = document.getElementById("myRouteList");
		while(groupSelect.hasChildNodes() == true){
			groupSelect.removeChild(groupSelect.childNodes[0]);
		}
		
		pagMyRouteList.set('totalRecords',jResponse.DATA.TOTALRECCNT); 
		groupSelect.innerHTML = jResponse.DATA.CONTENT;
		
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	var strData = "action=getRoutes&recsPerPage=" + recsPerPage + "&curPage=" + curPage;	
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "searchRoute.do", callback, strData);
}

function populateRegisteredEventByUserID()
{
	populateRegisteredEventByUserIDN(3,1);
}

function populateRegisteredEventByUserIDN(recsPerPage, curPage)
{		

	var successHandler = function(o) {
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		var groupSelect = document.getElementById("myRegisteredEventList");
		while(groupSelect.hasChildNodes() == true){
			groupSelect.removeChild(groupSelect.childNodes[0]);
		}
		
		pagEVTregistered.set('totalRecords',jResponse.DATA.TOTALRECCNT); 
		groupSelect.innerHTML = jResponse.DATA.CONTENT;
		
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	var strData = "action=getEventsRegistered&recsPerPage=" + recsPerPage + "&curPage=" + curPage;	
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "searchEvent.do", callback, strData);
}

function populateCreatedEventByUserIDN()
{
	populateCreatedEventByUserID(3,1);
}

function populateCreatedEventByUserID(recsPerPage, curPage)
{		

	var successHandler = function(o) {
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		var groupSelect = document.getElementById("myCreatedEventList");
		while(groupSelect.hasChildNodes() == true){
			groupSelect.removeChild(groupSelect.childNodes[0]);
		}
		
		pagEVTcreated.set('totalRecords',jResponse.DATA.TOTALRECCNT); 
		groupSelect.innerHTML = jResponse.DATA.CONTENT;
		
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	var strData = "action=getEventsCreated&recsPerPage=" + recsPerPage + "&curPage=" + curPage;	
	
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

	document.getElementById("modifyUserGender").style.display = "block";
	document.getElementById("modifyUserEmail").style.display = "block";
	document.getElementById("modifyUserPassword").style.display = "block";
	document.getElementById("modifyUserNotified").style.display = "block";
	document.getElementById("modifyUserHeight").style.display = "block";
	document.getElementById("modifyUserWeight").style.display = "block";
	
	document.getElementById("userGender").style.display = "none";
	document.getElementById("userName").style.display = "none";
	document.getElementById("userEmail").style.display = "none";
//	document.getElementById("userPassword").style.display = "none";
	document.getElementById("userHeight").style.display = "none";
	document.getElementById("userWeight").style.display = "none";
	
	document.getElementById("savePersonalInfoBtns").style.display = "block";
	document.getElementById("modifyPersonalInfoBtns").style.display = "none";

}
function cancelPersonalInfo() {	

	document.getElementById("userGender").style.display = "block";
	document.getElementById("userName").style.display = "block";
	document.getElementById("userEmail").style.display = "block";
//	document.getElementById("userPassword").style.display = "block";
	document.getElementById("userHeight").style.display = "block";
	document.getElementById("userWeight").style.display = "block";
	
	document.getElementById("modifyUserGender").style.display = "none";
	document.getElementById("modifyUserEmail").style.display = "none";
	document.getElementById("modifyUserPassword").style.display = "none";
	document.getElementById("modifyUserNotified").style.display = "none";
	document.getElementById("modifyUserHeight").style.display = "none";
	document.getElementById("modifyUserWeight").style.display = "none";
	
	document.getElementById("savePersonalInfoBtns").style.display = "none";
	document.getElementById("modifyPersonalInfoBtns").style.display = "block";

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
	};

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	};

	var callback = {
		failure:failureHandler,
		success:successHandler,
		//timeout:3000,
	};
	
	// POST string data
	var strData = "";
	strData += "gender=" + document.getElementById("userGenderTxt").value + "&";
	strData += "email=" + document.getElementById("userEmailTxt").value + "&";
	strData += "password=" + document.getElementById("userPasswordTxt").value + "&";
	strData += "height=" + document.getElementById("HeightInfoTxt").value + "&";
	strData += "weight=" + document.getElementById("WeightInfoTxt").value + "&";
	strData += "publicEventNotify=" + document.getElementById("UserNotifiedEventsTxt").value + "&";
	
	// Append GroupID to strData
	//strData += "userID=" + document.getElementById("user.getUserID()").value.trim();
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "modifyUser.do", callback, strData);

	document.getElementById("userGender").style.display = "block";
	document.getElementById("userName").style.display = "block";
	document.getElementById("userEmail").style.display = "block";
//	document.getElementById("userPassword").style.display = "block";
	document.getElementById("userHeight").style.display = "block";
	document.getElementById("userWeight").style.display = "block";
	
	document.getElementById("modifyUserGender").style.display = "none";
	document.getElementById("modifyUserEmail").style.display = "none";
	document.getElementById("modifyUserPassword").style.display = "none";
	document.getElementById("modifyUserNotified").style.display = "none";
	document.getElementById("modifyUserHeight").style.display = "none";
	document.getElementById("modifyUserWeight").style.display = "none";
	
	document.getElementById("savePersonalInfoBtns").style.display = "none";
	document.getElementById("modifyPersonalInfoBtns").style.display = "block";
}



function sendEmailToGroup(groupID) {
	var successHandler = function(o) {	
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;

		alert("Successfully sent an email to the group!");	
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:10000,
	}

	YAHOO.util.Connect.setForm("frmGroupSendEmail");
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgGroupReg.do", callback);
	
}


function sendEmailToGroup() {
	var successHandler = function(o) {	
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;

		alert("Successfully sent an email to the group!");	
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000,
	}

	YAHOO.util.Connect.setForm("frmGroupSendEmail");
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgGroupReg.do", callback);
	
}

function unsubscribeGroup() {
	var successHandler = function(o) {	
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;

		alert("Successfully unsubscribe from the group!");	
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000,
	}

	YAHOO.util.Connect.setForm("frmGroupRegisteredUnsubscribe");
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgGroupReg.do", callback);
	
}

function deleteGroup() {
	var successHandler = function(o) {	
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;

		alert("Successfully deleted selected group");	
		
		populateMyCreatedGroupByUserID();
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000,
	}

	radios = document.getElementById("frmGroupSendEmail")["selectedRadioGroup"];
	var selectedGroup;
	
	if( radios.type != "radio" ) // if there are many radio buttons
	{
		try 
		{
			for(i = 0 ; i < radios.length ; i++) {
				if(radios[i].checked)
				{
					selectedGroup = radios[i].value;
					break;
				}
			}
		}
		catch(err)
		{
			alert("There is no group to delete!");
			return;
		}
		
		if( i == radios.length )
		{
			alert("Please select a group to delete!");
			return;
		}
	}
	else // if only one radio button
	{
		if( !radios.checked )
		{
			alert("Please select a group to delete!");
			return;
		}
		
		selectedGroup = radios.value;
	}
	
	var strData = "action=delete&groupID="+selectedGroup;
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgGroup.do", callback, strData);

}



//Listeners
YAHOO.util.Event.addListener("modifyPersonalInfoBtn", "click", modifyPersonalInfo);	
YAHOO.util.Event.addListener("cancelPersonalInfoBtn", "click", cancelPersonalInfo);
YAHOO.util.Event.addListener("savePersonalInfoBtn", "click", savePersonalInfo);
