// Floating Toolbar (Route Details and Event Details)

// ******************************************************
// Variables
// ******************************************************
YAHOO.namespace("pennfitness.float");
var colorButton, oColorPicker;
var routeID = -1, eventID = -1, eventCount = 0;

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
// END OF TEMPORARY SECTION

function populateTimeRange() {
	var evtTimeStartSelect = document.getElementById("eventTimeStart");
//	var evtTimeStopSelect = document.getElementById("evtTimeStop");	
	
	for (var hour = 1; hour <= 12; hour++) {
		
		//var optionStop = document.createElement('option');
		
		for (var min = 0; min <= 45; min = min + 15 ) {
			var optionStart = document.createElement('option');
			if (min < 15) {
				optionStart.setAttribute('value', hour + ":" + min + "0");
				optionStart.appendChild(document.createTextNode(hour + ":" + min + "0"));
			}
			else {
				optionStart.setAttribute('value', hour + ":" + min);
				optionStart.appendChild(document.createTextNode(hour + ":" + min));
			}
			evtTimeStartSelect.appendChild(optionStart);
		}
		
	}	
}

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

//***********************************************************************
//Function: Calendar for Events
//***********************************************************************
(function () {	
var Event = YAHOO.util.Event,
	Dom = YAHOO.util.Dom;

Event.onDOMReady(function () {
	var oCalendarMenu;

	var onButtonClick = function () {			
		// Create a Calendar instance and render it into the body 
		// element of the Overlay.
		var oCalendar = new YAHOO.widget.Calendar("buttoncalendar", oCalendarMenu.body.id);
		oCalendar.render();

		// Subscribe to the Calendar instance's "select" event to 
		// update the month, day, year form fields when the user
		// selects a date.
		oCalendar.selectEvent.subscribe(function (p_sType, p_aArgs) {
			var aDate;

			if (p_aArgs) {
				var date = p_aArgs[0][0];
				var year = date[0], month = date[1], day = date[2];

				var txtDate1 = document.getElementById("evtCalTxt");
				txtDate1.value = month + "/" + day + "/" + year;
//				var txtDateHidden = document.getElementById("evtDateTxt");
//				txtDateHidden.value = year + "-" + month + "-" + day;
			}
			
			oCalendarMenu.hide();			
		});

		// Pressing the Esc key will hide the Calendar Menu and send focus back to 
		// its parent Button
		Event.on(oCalendarMenu.element, "keydown", function (p_oEvent) {			
			if (Event.getCharCode(p_oEvent) === 27) {
				oCalendarMenu.hide();
				this.focus();
			}			
		}, null, this);
					
		var focusDay = function () {
			var oCalendarTBody = Dom.get("buttoncalendar").tBodies[0],
				aElements = oCalendarTBody.getElementsByTagName("a"),
				oAnchor;
			
			if (aElements.length > 0) {				
				Dom.batch(aElements, function (element) {					
					if (Dom.hasClass(element.parentNode, "today")) {
						oAnchor = element;
					}
				
				});
									
				if (!oAnchor) {
					oAnchor = aElements[0];
				}

				// Focus the anchor element using a timer since Calendar will try 
				// to set focus to its next button by default				
				YAHOO.lang.later(0, oAnchor, function () {
					try {
						oAnchor.focus();
					}
					catch(e) {}
				});				
			}				
		};

		// Set focus to either the current day, or first day of the month in 
		// the Calendar	when it is made visible or the month changes
		oCalendarMenu.subscribe("show", focusDay);
		oCalendar.renderEvent.subscribe(focusDay, oCalendar, true);

		// Give the Calendar an initial focus			
		focusDay.call(oCalendar);

		// Re-align the CalendarMenu to the Button to ensure that it is in the correct
		// position when it is initial made visible			
		oCalendarMenu.align();
		
		// Unsubscribe from the "click" event so that this code is 
		// only executed once
		this.unsubscribe("click", onButtonClick);			
	};

	// Create an Overlay instance to house the Calendar instance
	oCalendarMenu = new YAHOO.widget.Overlay("calendarmenu", { visible: false });

	// Create a Button instance of type "menu"	
	var calButton = new YAHOO.widget.Button({ 
										type: "menu", 
										id: "calendarpicker", 
										menu: oCalendarMenu, 
										container: "date" });

	calButton.on("appendTo", function () {
		// Create an empty body element for the Overlay instance in order 
		// to reserve space to render the Calendar instance into.		
		oCalendarMenu.setBody("&#32;");		
		oCalendarMenu.body.id = "calendarcontainer";			
	});

	// Add a "click" event listener that will render the Overlay, and 
	// instantiate the Calendar the first time the Button instance is 
	// clicked.
	calButton.on("click", onButtonClick);
});	
}());


//***********************************************************************
//Function: Initializes the create event dialog
//***********************************************************************
function setupNewEvtDialog(){

	// Define various event handlers for Dialog
	var handleSubmit = function() {
		
		document.getElementById("routeIDTxt").value = routeID;
		document.getElementById("eventIDTxt").value = eventID;
		
		// change time and date to: mm/dd/yyyy --> yyyy-mm-dd; time from hh:mm, am/pm --> hh:mm:ss
		
		var eventDate = document.getElementById("evtCalTxt").value; // mm/dd/yyyy
		
		var firstSlashIndex = eventDate.indexOf('/');		
		var lastSlashIndex = eventDate.lastIndexOf('/');
		var dateFormatted = eventDate.substring(lastSlashIndex +1) + "-" + eventDate.substring(0, firstSlashIndex) +
							"-" + eventDate.substring(firstSlashIndex + 1, lastSlashIndex);
		
		document.getElementById("evtDateTxt").value = dateFormatted;
		
		var am_pm = document.getElementById("AM_PM");
		var x = am_pm.selectedIndex;		
		
		var time = document.getElementById("eventTimeStart");
		var y = time.selectedIndex;
		
		var timeFormatted = time[y].value;
		
		var hour = timeFormatted.substring(0, timeFormatted.indexOf(':'));
		var min = timeFormatted.substring(timeFormatted.indexOf(':'));
		
		if (am_pm[x].value == 'PM') { // hh:mm
			hour = parseInt(hour, 10) + 12;
			timeFormatted = hour + min + ':00'; 
		} else {	
			if (hour < 10)
				hour = '0' + hour;
			timeFormatted = hour + min + ':00';
		}

		document.getElementById("evtStartTxt").value = timeFormatted;
		
		this.submit();
	};
	
	var handleCancel = function() {
		this.cancel();
	};
	
	var handleSuccess = function(o) {
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
			alert("Event saved successfully");
			
			if (eventID == -1) {
				eventID = response.DATA.EventID;
			} 			
		}
		else {
			alert("Event was not saved!");
		}
	};
	 
	var handleFailure = function(o) { 
		alert("Error + " + o.status + " : " + o.statusText);
	}; 
	
	// Instantiate the Dialog
	YAHOO.pennfitness.float.newEventPanel = new YAHOO.widget.Dialog("eventDialog", 
				{ width : "400px",
				  fixedcenter : true,
				  visible : false, 
				  modal : true,
				  constraintoviewport : true,
				  buttons : [ { text:"Save", handler:handleSubmit, isDefault:true },
							  { text:"Cancel", handler:handleCancel } ]
				 } );	

	
	// Validate the entries in the form 
	YAHOO.pennfitness.float.newEventPanel.validate = function() { 

		var data = this.getData(); 

		if (data.routeID == -1) { 
			alert("You cannot create an event for a nonexistent route."); 
			return false; 
		} 
		
		if (data.eventName == "") { 
			alert("Please enter the Event Name."); 
			return false; 
		} 
		
		if (data.eventTypeID == -1) {
			alert("Please choose an event type."); 
			return false; 
		}
		
		else if(data.eventDuration == "" ) { //TODO: check that it's a float (##.##)
			alert("Please enter a duration for the event."); 
			return false; 
		}
		else if(data.evtCalTxt == "") { //TODO: double-check format: ##/##/#### and that date >= curr date
			alert("Please enter a date for the event."); 
			return false; 
		}

 		return true;
	}; 

	// Wire up the success and failure handlers 
	YAHOO.pennfitness.float.newEventPanel.callback = { success: handleSuccess, 
						     						   failure: handleFailure };

	YAHOO.pennfitness.float.newEventPanel.render("bd");
}

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

YAHOO.pennfitness.float.getEventCount = function() {
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
			document.getElementById("totalEvents").innerHTML = response.DATA.EVENT_COUNT + " Events";			
		} else {
			alert("Retrieving event count by routeID: " + routeID + "failed!");
		}
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	//routeID = routeIDArg;
	var transaction = YAHOO.util.Connect.asyncRequest("GET", "view/eventCount.jsp?routeID=" + routeID, callback); 
}






//  ***********************************************************************
//  function: YAHOO.pennfitness.float.getRoute 
//  Used to draw selected route and display its associated route details
//  ***********************************************************************
YAHOO.pennfitness.float.getRoute = function(routeIDArg, bCallGetNewRoutes) {
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

			document.getElementById("routeDesc").innerHTML = response.DATA.ROUTE_DESCRIPTION;
			document.getElementById("routeDescTxt").style.display = "none";

			YAHOO.pennfitness.float.getEventCount();
			
			//document.getElementById("rtColor-container").style.display = "none";
			
			document.getElementById("saveRoute").style.display = "none";
			document.getElementById("modifyRoute").style.display = "block";
			
			//enableMap();
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
			// Calls getNewRouteNames only when necessary
			if(bCallGetNewRoutes) YAHOO.leftMenu.route.getNewRouteNames();
			
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
			
			YAHOO.pennfitness.float.getRoute(routeID, true);	
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
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgRoute.do", callback, strData);
}

function deleteRt() {
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
			alert("Route deleted successfully");
			routeID = -1;
			YAHOO.pennfitness.float.toolbar.hide();
			removeRoute();
			
			YAHOO.leftMenu.route.getNewRouteNames();			
		}
		else {
			alert(response.MSG);
		}
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		failure:failureHandler,
		success:successHandler,
	}
	var strData = "routeID=" + routeID + "&";
	strData += "action=delete";
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgRoute.do", callback, strData);
}

//*******************************************************
//Function: createEvt
//*******************************************************
function createEvt() {
	resetNewEvt();	
	YAHOO.pennfitness.float.newEventPanel.show();
}


//***********************************************************************
//Function: Resets new event details dialog for editing
//***********************************************************************
function resetNewEvt() {
	eventID = -1;

	document.getElementById("eventNameTxt").value = "";
	
	document.getElementById("publicEvt").checked = true;
	document.getElementById("evtGroup").options[0].selected = true;
	document.getElementById("evtType").options[0].selected = true;
	
	document.getElementById("eventTimeStart").options[0].selected = true;
	document.getElementById("AM_PM").options[0].selected = true;
	
	document.getElementById("eventDurationTxt").value = "";
	document.getElementById("evtCalTxt").value = "";
	document.getElementById("eventDescTxt").value = "";
}

//***********************************************************************
//function: YAHOO.pennfitness.float.getEvent 
//***********************************************************************
YAHOO.pennfitness.float.getEvent = function(eventIDArg, bCallGetNewEvents) {
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
	
			document.getElementById("routeDesc").innerHTML = response.DATA.ROUTE_DESCRIPTION;
			document.getElementById("routeDescTxt").style.display = "none";
	
			document.getElementById("rtColor-container").style.display = "none";
			
			document.getElementById("saveRoute").style.display = "none";
			document.getElementById("modifyRoute").style.display = "block";
			
			//enableMap();
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
			// Calls getNewRouteNames only when necessary
			if(bCallGetNewRoutes) YAHOO.leftMenu.route.getNewRouteNames();
			
		} else {
			alert("Retrieving eventID: " + eventID + "failed!");
		}
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
	}
	
	routeID = routeIDArg;
	var transaction = YAHOO.util.Connect.asyncRequest("GET", "view/eventByID.jsp?eventID=" + eventID, callback); 
}


YAHOO.util.Event.onDOMReady(initToolbar);
YAHOO.util.Event.onDOMReady(setupNewEvtDialog);


// Listeners
YAHOO.util.Event.addListener("cancelRouteBtn", "click", cancelRt);
YAHOO.util.Event.addListener("saveRouteBtn", "click", saveRt);	
YAHOO.util.Event.addListener("modifyRouteBtn", "click", modifyRt);	
YAHOO.util.Event.addListener("deleteRouteBtn", "click", deleteRt);

YAHOO.util.Event.onDOMReady(populateTimeRange);

// MORE TEMPORARY JUNK HERE
YAHOO.util.Event.onDOMReady(populateGroup);