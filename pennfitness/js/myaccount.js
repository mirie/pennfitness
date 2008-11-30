
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

function populateGroupByUserID()
{		

	var successHandler = function(o) {
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	
		var groupSelect = document.getElementById("myGroupList");
		while(groupSelect.hasChildNodes() == true){
			groupSelect.removeChild(groupSelect.childNodes[0]);
		}
		
		
		var groupList = jResponse.DATA.GROUPS.split(";");
		groupSelect.length = 1;
		
		for (var i = 0; i < groupList.length - 1; i++) {
			var group = groupList[i].split("-");	
			
			/*var checkbox = document.createElement('input');
			checkbox.type = 'checkbox';
			checkbox.name = 'checkboxName';
			checkbox.defaultChecked = false;
			checkbox.value = 'Kibology';
			checkbox.onclick =  function(){ alert('kerem') };*/
			
			var link = document.createElement('a');
			link.setAttribute('href','javascript:GetGroupDetail(\'' +group[0]+ '\')');
			link.appendChild(document.createTextNode(group[1]));
			link.appendChild(document.createElement('br'));
			
			groupSelect.appendChild(link);
		}
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

















