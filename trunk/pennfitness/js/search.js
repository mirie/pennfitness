



YAHOO.namespace("yuibook.tabs");
YAHOO.util.Event.onDOMReady(initTabs);
YAHOO.util.Event.onDOMReady(switchTabs);


YAHOO.namespace("example.container");
YAHOO.example.container.InfoToSearch = function() {
	YAHOO.example.container.panel1.show();
	YAHOO.example.container.panel2.hide();
}
YAHOO.example.container.SearchToInfo = function() {
	YAHOO.example.container.panel2.show();
	YAHOO.example.container.panel1.hide();
}

function initTabs(){
	var tabs = new YAHOO.widget.TabView('overlayTabOne'); 
	var tabs = new YAHOO.widget.TabView('overlayTabTwo'); 
	var tabs = new YAHOO.widget.TabView('GroupInfoTab'); 
	var tabs = new YAHOO.widget.TabView('EventInfoTab'); 
}

function switchTabs() {
	// Instantiate a Panel from markup
	YAHOO.example.container.panel1 = new YAHOO.widget.Panel("panel1", {  visible:false, fixedcenter : true, constraintoviewport:true } );
	YAHOO.example.container.panel1.render();
	YAHOO.example.container.panel2 = new YAHOO.widget.Panel("panel2", {  visible:false, fixedcenter : true, constraintoviewport:true } );
	YAHOO.example.container.panel2.render(); 
//	YAHOO.util.Event.addListener("myInfo", "click", YAHOO.example.container.InfoToSearch);
//	YAHOO.util.Event.addListener("search", "click", YAHOO.example.container.SearchToInfo);
}


function ShowSearchDialog() {
	YAHOO.example.container.SearchToInfo();
}
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
