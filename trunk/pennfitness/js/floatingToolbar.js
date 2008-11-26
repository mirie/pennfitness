// Floating Toolbar (Route Details and Event Details)

// ******************************************************
// Variables
// ******************************************************
YAHOO.namespace("pennfitness.float");
var colorButton, oColorPicker;
var routeID = -1, eventID = -1;

String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, ''); }


// ********************************************************
// TEMPORARY STUFF HERE
// ********************************************************
// Make a list of (groupNames to display in event detail) 
var fakeGrps = ['group1', 'group2', 'group3', 'group3', 'group4'];

function populateGroup() {
	var groupSelect = document.getElementById("evtGroup");
	for (var i = 0; i < fakeGrps.length; i++) {
		var option = document.createElement('option');
		option.setAttribute('value',fakeGrps[i]);
		option.appendChild(document.createTextNode(fakeGrps[i]));
		groupSelect.appendChild(option);
	}
}

// Make a list of (eventTypes to display in event detail) 
var fakeEventTypes = ['Casual', 'General Workout', 'Fat-Burning', 'Intense Workout'];

function populateEventType() {
	var eventTypeSelect = document.getElementById("evtType");
	for (var i = 0; i < fakeEventTypes.length; i++) {
		var option = document.createElement('option');
		option.setAttribute('value', i);
		option.appendChild(document.createTextNode(fakeEventTypes[i]));
		eventTypeSelect.appendChild(option);
	}
}
// END OF TEMPORARY SECTION



// *******************************************************
// Function: initToolbar
// Initializes the route detail toolbar
// *******************************************************
function initToolbar() {
//Instantiate a Panel from markup
YAHOO.pennfitness.float.toolbar = new YAHOO.widget.Panel("floatingToolbar", 
		{	visible: false, 
			constraintoviewport: true,
			width: "260px",
			autofillheight: "body",
			context: ['map', 'tr', 'tr'],
			effect:{effect:YAHOO.widget.ContainerEffect.FADE,duration:0.25}
		});
	YAHOO.pennfitness.float.toolbar.render();
	YAHOO.pennfitness.float.toolbar.hideEvent.subscribe(cancelRt);
}

//***********************************************************************
//Function: Color picker functions for the route color.
//***********************************************************************/
YAHOO.util.Event.onContentReady("rtColor-container", function () {

function onButtonOption() {
	/* Create a new ColorPicker instance, placing it inside the body 
	   element of the Menu instance.
	*/
	oColorPicker = new YAHOO.widget.ColorPicker(oColorPickerMenu.body.id, {
						red: 0, green: 0, blue: 175,
						showcontrols: false,
						images: {
							PICKER_THUMB: "assets/picker_thumb.png",
							HUE_THUMB: "assets/hue_thumb.png"
						}
					});
	
	/* Add a listener for the ColorPicker instance's "rgbChange" event
	   to update the background color and text of the Button's 
	   label to reflect the change in the value of the ColorPicker.
	*/
	oColorPicker.on("rgbChange", function (p_oEvent) {
		var sColor = "#" + this.get("hex");
		
		lineColor = sColor;
		drawOverlay();
		
		colorButton.set("value", sColor);
		YAHOO.util.Dom.setStyle("current-color", "backgroundColor", sColor);
		YAHOO.util.Dom.get("current-color").innerHTML = "Current color is " + sColor;
	});
	        
	// Remove this event listener so that this code runs only once
	this.unsubscribe("option", onButtonOption);	
}
	
//Create a Menu instance to house the ColorPicker instance
var oColorPickerMenu = new YAHOO.widget.Menu("color-picker-menu");

//Create a Button instance of type "split"
colorButton = new YAHOO.widget.Button({ 
			type: "split", 
			id: "color-picker-button", 
			label: "<em id=\"current-color\">Current color is #0000AF.</em>", 
			menu: oColorPickerMenu, 
			container: "rtColor-container" });
		
	colorButton.on("appendTo", function () {
	/* Create an empty body element for the Menu instance in order to 
	   reserve space to render the ColorPicker instance into.
	*/
	oColorPickerMenu.setBody("&#32;");	
	oColorPickerMenu.body.id = "color-picker-container";
	
	// Render the Menu into the Button instance's parent element
	oColorPickerMenu.render(this.get("container"));

});
        
/* Add a listener for the "option" event.  This listener will be
   used to defer the creation the ColorPicker instance until the 
   first time the Button's Menu instance is requested to be displayed
   by the user.
*/ 
colorButton.on("option", onButtonOption);
    
/* Add a listener for the "click" event.
*/
colorButton.on("click", function () {
	lineColor = this.get("value");
	drawOverlay();
	});
});

// ***********************************************************************
// Function: Resets route details toolbar for editing
// ***********************************************************************
function resetRtDetail() {
	routeID = -1;
	lineColor = "#0000af";
	
	document.getElementById("routeGeneral").style.display = "none";
	document.getElementById("routeNameTxtDiv").style.display = "block";
	document.getElementById("routeNameTxt").value = "";
	
	document.getElementById("routeCreatedDate").innerHTML = "";
	document.getElementById("routeDistance").innerHTML = "0 miles";
	
	document.getElementById("rtRatings").innerHTML = "";
		
	document.getElementById("routeDesc").innerHTML = "Route Description: <br />";
	document.getElementById("routeDesc").style.paddingBottom = "5px";
	document.getElementById("routeDescTxt").style.display = "block";
	document.getElementById("routeDescTxt").value = "";
	
	document.getElementById("routeNameTxt").disabled = false;
	document.getElementById("routeDescTxt").disabled = false;
	
	document.getElementById("saveRoute").style.display = "block";
	document.getElementById("modifyRoute").style.display = "none";
	
	var rtColor = document.getElementById("rtColor-container");
	rtColor.style.display = "block";
	
	colorButton.disabled = false;
	colorButton.set("value", "#0000af");
	YAHOO.util.Dom.setStyle("current-color", "backgroundColor", "#0000af");
	YAHOO.util.Dom.get("current-color").innerHTML = "Current color is " + "#0000af";
	
	removeRoute();
}

// *******************************************************
// Function: createRt
// Enables the map for route creation and displays route 
// detail toolbar.
// *******************************************************
function createRt() {
	resetRtDetail();
	YAHOO.pennfitness.float.toolbar.show();
	enableMap();
}

//  ***********************************************************************
//  function: YAHOO.pennfitness.float.getRoute 
//  Used to draw selected route and display its associated route details
//  ***********************************************************************
YAHOO.pennfitness.float.getRoute = function(routeIDArg) {
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
		
		if (response.STATUS == 'Success') {
			removeRoute();
			
			document.getElementById("routeGeneral").style.display = "block";
			document.getElementById("routeNameTxtDiv").style.display = "none";
			document.getElementById("routeName").innerHTML = response.DATA.ROUTE_NAME;
			document.getElementById("routeCreator").innerHTML = " by " + response.DATA.ROUTE_CREATOR;
			document.getElementById("routeCreatedDate").innerHTML = "Created on: " + response.DATA.ROUTE_DATE;		
			
			document.getElementById("rtRatings").innerHTML = "Avg.Rating: "+response.DATA.ROUTE_RATING;

			document.getElementById("routeDesc").innerHTML = "Description:"+response.DATA.ROUTE_DESCRIPTION;
			document.getElementById("routeDescTxt").style.display = "none";

			document.getElementById("rtColor-container").style.display = "none";
			
			document.getElementById("saveRoute").style.display = "none";
			document.getElementById("modifyRoute").style.display = "block";
			
			enableMap();
			// Add markers and draw route
			var pointData = response.DATA.ROUTE_PTS.split(";");
			for (var i = 0; i < pointData.length; i++) {
				var point = pointData[i].split(",");
				var lat = point[0];
				var lng = point[1];
				addMarkerPoint(lat, lng);
			}

			lineColor = response.DATA.ROUTE_COLOR;
			drawOverlay();
			disableMap();  
			YAHOO.pennfitness.float.toolbar.show();
			
		} else {
			alert("Retrieving routeID: " + routeID + "failed!");
		}
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
		timeout:3000
	}
	
	routeID = routeIDArg;
	var transaction = YAHOO.util.Connect.asyncRequest("GET", "view/routeByID.jsp?routeID=" + routeID, callback); 
}



function modifyRt() {	
	document.getElementById("routeGeneral").style.display = "none";
	
	document.getElementById("routeNameTxtDiv").style.display = "block";
	document.getElementById("routeNameTxt").value = document.getElementById("routeName").innerHTML;
	
	document.getElementById("routeDescTxt").value = document.getElementById("routeDesc").innerHTML;
	document.getElementById("routeDesc").innerHTML = "Route Description: ";
	document.getElementById("routeDesc").style.paddingBottom = "5px";
	document.getElementById("routeDescTxt").style.display = "block";
	
	var rtColor = document.getElementById("rtColor-container");
	rtColor.style.display = "block";
	
	colorButton.set("value", lineColor);
	YAHOO.util.Dom.setStyle("current-color", "backgroundColor", lineColor);
	YAHOO.util.Dom.get("current-color").innerHTML = "Current color is " + lineColor;
	
	document.getElementById("saveRoute").style.display = "block";
	document.getElementById("modifyRoute").style.display = "none";
	
	enableMap();
}

function cancelRt() {
	removeRoute();

// commented by inseob : should call removeRoute when closed 
//	if (routeID == -1) {
		removeRoute();
//	}
	
	disableMap();
	YAHOO.pennfitness.float.toolbar.hide();
}

// ***********************************************************************
// Function: saveRt
// Saves the drawn route and related route information. Disables map.
// Displays alert if missing route name or a drawing for the route 
// (at least 2 points).
// ***********************************************************************
function saveRt() {
	if (YAHOO.util.Dom.get("routeNameTxt").value == "") {
		alert("Please give the route a name before saving!");
		return;
	}
	if (points.length < 2) {
		alert("Please draw a route before saving! (at least 2 points)");
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
	
	    if (response.STATUS == 'Success') { // RouteID passed back if just saved a new route
			alert("Route saved successfully");
			if (routeID == -1) {
				routeID = response.DATA.RouteID;
			} 
			disableMap();
			colorButton.disabled = true; //TODO: Bug -- not disabled.
			
			document.getElementById("routeGeneral").style.display = "block";
			document.getElementById("routeName").innerHTML = document.getElementById("routeNameTxt").value;
			document.getElementById("routeNameTxtDiv").style.display = "none";
			
			document.getElementById("routeCreator").innerHTML = "need user from server";
			document.getElementById("routeCreatedDate").innerHTML = "need date from server";		
			
			document.getElementById("rtRatings").innerHTML = "Ratings: need from server";

			document.getElementById("routeDesc").innerHTML = document.getElementById("routeDescTxt").value;
			document.getElementById("routeDescTxt").style.display = "none";

			document.getElementById("rtColor-container").style.display = "none";
			
			document.getElementById("saveRoute").style.display = "none";
			document.getElementById("modifyRoute").style.display = "block";
			
			YAHOO.leftMenu.route.getNewRouteNames();
		}
		else {
			alert("Route was not saved!");
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
	strData += "routeName=" + document.getElementById("routeNameTxt").value + "&";
	strData += "routeDesc=" + document.getElementById("routeDescTxt").value + "&";
	
	// Append routeColor to strData
	strData += "routeColor=" + colorButton.get("value") + "&";
	
	// Append distance to strData
	var strDist = document.getElementById("routeDistance").innerHTML;
	index = strDist.indexOf("miles");
	strDist = strDist.substring(0, index);
	
	strData += "distance=" + strDist + "&";
	
	// Append pvalue to strData
	var strPt = "";
	
	for (var i = 0 ; i < markers.length ;i++) {		
		if (i < markers.length - 1) {
			strPt += markers[i].getLatLng().lat() + "," + markers[i].getLatLng().lng() + ";";	
		} else {
			strPt += markers[i].getLatLng().lat() + "," + markers[i].getLatLng().lng(); 
		}		
	}
	
	strData += "pvalue=" + strPt + "&";
	
	// Append RouteID to strData
	strData += "routeID=" + routeID;
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "saveRoute.do", callback, strData);
}


YAHOO.util.Event.onDOMReady(initToolbar);


// Listeners
YAHOO.util.Event.addListener("cancelRouteBtn", "click", cancelRt);
YAHOO.util.Event.addListener("saveRouteBtn", "click", saveRt);	
YAHOO.util.Event.addListener("modifyRouteBtn", "click", modifyRt);	
YAHOO.util.Event.addListener("deleteRouteBtn", "click", function() {
	alert("not implemented yet!");});


// MORE TEMPORARY JUNK HERE
YAHOO.util.Event.onDOMReady(populateGroup);
YAHOO.util.Event.onDOMReady(populateEventType);