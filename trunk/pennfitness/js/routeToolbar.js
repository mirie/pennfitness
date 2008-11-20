// Route Toolbar


//  ***********************************************************************
//  Variables
//  ***********************************************************************
var ddCreateRoute, ddRouteDetails;
YAHOO.namespace("route.toolbar");
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, ''); }

//  ***********************************************************************
//  TEMPORARY STUFF HERE
//  ***********************************************************************
//  Make a list of (groupNames to display in event detail) 
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



//  ***********************************************************************
//  Function: createNewRt
//  Enables the map for route creation and displays route detail toolbar.
//  ***********************************************************************
function createRt() {
	hideNewRtTool();
	resetRtDetail();
	showRtDetailsTool();
	enableEditRtDetail("");
	enableMap();
}

//  ***********************************************************************
//  Function: saveRt
//  Saves the drawn route and related route information. Disables map.
//  Displays alert if missing route name or a drawing for the route 
//  (at least 2 points).
//  ***********************************************************************
function saveRt() {
	if (YAHOO.util.Dom.get("routeName").value == "") {
		alert("please give the route a name before saving!");
		return;
	}
	if (points.length < 2) {
		alert("please draw a route before saving!");
		return;
	}
		
	// var successHandler = function(o) {
		// if (o.responseText != '-1') { // RouteID passed back
			// alert("Route saved successfully");
			// if (document.getElementById("routeID").value == '-1') 
				// document.getElementById("routeID").value = o.responseText;
			// disableMap();
			// enableEditRtDetail("disabled");
			// toggleModifyRtDetail(true);
			// //leftTB.route.getNewRouteNames();
		// }
		// else {
		// alert("Route is not saved");
		// }
	// }
	
		// FOR JSON STUFF WHEN IMPLEMENTED BY KEREM
	var successHandler = function(o) {
        var response = [];
		
        // Use the JSON Utility to parse the data returned from the server
        try {
            response = YAHOO.lang.JSON.parse(o.responseText);
        }
        catch (x) {
            alert("JSON Parse failed!");
            return;
        }
		var m = response[0];
		if (m.returnCode == '1') { // RouteID passed back if just saved a new route
			alert("Route saved successfully");
			if (document.getElementById("routeID").value == -1) {
				document.getElementById("routeID").value = m.routeID;
		} 
			disableMap();
			enableEditRtDetail("disabled");
			toggleModifyRtDetail(true);
			//leftTB.route.getNewRouteNames();
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
	strData += "routeName=" + document.getElementById("routeName").value + "&";
	strData += "routeDesc=" + document.getElementById("routeDesc").value + "&";
	
	// Append routeColor to strData
	var strColor = document.getElementById("current-color").innerHTML;
	var index = strColor.indexOf("#");
	strColor = strColor.substring(index, strColor.length - 1);
	
	strData += "routeColor=" + strColor + "&";
	
	// Append distance to strData
	var strDist = document.getElementById("rtDist").innerHTML;
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
	strData += "routeID=" + document.getElementById("routeID").value.trim();
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "saveRoute.do", callback, strData);
	
	// TEMP --> ERASE THIS LATER...used to continue on in case error saving
	//disableMap();
	//enableEditRtDetail("disabled");
	//toggleModifyRtDetail(true);
}

//  ***********************************************************************
//  Function: modifyRt
//  Enables the map and allows editing of the related route details.
//  ***********************************************************************
function modifyRt() {
	enableMap();
	enableEditRtDetail("");
	toggleModifyRtDetail(false);
}

//  ***********************************************************************
//  Function: cancelRoute
//  Disables map and redisplays the select/create new route toolbar.
//  ***********************************************************************
function cancelRoute() {
	enableEditRtDetail("disabled");
	removeRoute();
	disableMap();
	hideRtDetailsTool();
	showNewRtTool();	
}

//  ***********************************************************************
//  Function: finishRoute
//  Basically a 'close' button for the route details toolbar.
//  ***********************************************************************
function finishRoute() {
	cancelEvent();	
	enableEditRtDetail("disabled");
	disableMap();
	hideRtDetailsTool();
	removeRoute();
	showNewRtTool();	
}

//  ***********************************************************************
//  Function: createEvent
//  Displays the New Event details form.
//  ***********************************************************************
function createEvent() {
	document.getElementById("modifyRtDiv").style.display = "none";
	enableEditRtDetail("disabled");
	resetNewEvent();
	enableEditNewEvent("");
	showNewEvent();
	disableMap();
}

//  ***********************************************************************
//  Function: saveEvent
//  Saves the event.
//  ***********************************************************************
function saveEvent() {
	if (YAHOO.util.Dom.get("eventName").value == "" || YAHOO.util.Dom.get("eventTime").value == "" ||
													   YAHOO.util.Dom.get("eventDuration").value == "") {
		alert("Please complete the event details before saving!");
		return;
	}
	
	var successHandler = function(o) {
		var response = [];
		
        // Use the JSON Utility to parse the data returned from the server
        try {
            response = YAHOO.lang.JSON.parse(o.responseText);
        }
        catch (x) {
            alert("JSON Parse failed!");
            return;
        }
		
		var m = response[0];
		if (m.returnCode == '1') { 
			alert("Event saved successfully");
			if (document.getElementById("eventID").value == -1) {
				document.getElementById("eventID").value = m.eventID;
			} 
			enableEditNewEvent("disabled");
			toggleModifyEvtDetail(true);	
		}
		else {
			alert("Event was not saved!");
		}
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000
	}

	// Connection manager will automatically get the following from form:
	// eventID, eventName, groupID, eventTime, eventDuration, eventDate, eventDesc
	var form = document.getElementById("frmCreateEvent");
	
	// TODO:: NO ERROR CHECKING YET!!!!!!
	if ( (evtRtID = document.getElementById("evtRouteID")) != null) {	
		evtRtID.value = document.getElementById("routeID").value;
	} else {
		var evtRtID = document.createElement("input");
		evtRtID.type = "hidden";
		evtRtID.name = "routeID";
		evtRtID.id = "evtRouteID";
		evtRtID.value = document.getElementById("routeID").value;
		form.appendChild(evtRtID);
		}
	
	YAHOO.util.Connect.setForm(form);
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "saveEvent.do", callback);
	
	// temp HERE: ERASE THIS:
	//enableEditNewEvent("disabled");
	//toggleModifyEvtDetail(true);	
}

//  ***********************************************************************
//  Function: modifyEvent
//  Allows editing of the related event details.
//  ***********************************************************************
function modifyEvent() {
	enableEditNewEvent("");
	toggleModifyEvtDetail(false);
}

//  ***********************************************************************
//  Function: cancelEvent
//  ***********************************************************************
function cancelEvent() {
	document.getElementById("modifyRtDiv").style.visibility = "visible";
	enableEditNewEvent("disabled");
	hideNewEvent();
}

//  ***********************************************************************
//  Function: finishEvent
//  Basically a 'close' button for the event details toolbar.
//  ***********************************************************************
function finishEvent() {
	cancelEvent();	
}

//  ***********************************************************************
//  Function: Show and Hide
//  Displays or hides the toolbars.
//  ***********************************************************************
function hideNewRtTool() {
	document.getElementById("createRtTool").style.visibility = "hidden";
}

function showNewRtTool() {
	document.getElementById("createRtTool").style.top = "135px";
	document.getElementById("createRtTool").style.right = "100px";
	document.getElementById("createRtTool").style.left = "";
	document.getElementById("createRtTool").style.visibility = "visible";
}

function hideRtDetailsTool() {
	document.getElementById("rtDetailTool").style.display = "none";	
}

function showRtDetailsTool() {
	document.getElementById("rtDetails").style.visibility = "visible";
	
	toggleModifyRtDetail(false);
	
	div = document.getElementById("rtDetailTool");
	div.style.top = "135px";
	div.style.right = "89px";
	div.style.left = "";
	div.style.display = "block";
}

function hideNewEvent() {
	document.getElementById("newEvent").style.display = "none";
	toggleModifyRtDetail(true);
}

function showNewEvent() {
	document.getElementById("newEvent").style.display = "block";
	document.getElementById("newEvent").style.visibility = "visible";
	toggleModifyEvtDetail(false);
}

//  ***********************************************************************
//  Function: Enables/Disables all form elements for editing
//  ***********************************************************************
function enableEditRtDetail(enable) {
	document.getElementById("routeName").disabled = enable;
	document.getElementById("routeDesc").disabled = enable;
	document.getElementById("color-picker-button-button").disabled = enable;
}

function enableEditNewEvent(enable) {
	document.getElementById("eventName").disabled = enable;
	document.getElementById("eventTime").disabled = enable;
	document.getElementById("eventDuration").disabled = enable;
	document.getElementById("eventDesc").disabled = enable;
}

//  ***********************************************************************
//  Function: Resets form elements
//  ***********************************************************************
function resetRtDetail() {
	document.getElementById("routeID").value = "-1";
	document.getElementById("routeName").value = "";
	document.getElementById("routeDesc").value = "";
	document.getElementById("rtDist").innerHTML = "0 miles";
	removeRoute();
	document.getElementById("current-color").style.backgroundColor = " #0000AF"; // Slight BUG HERE... need minor fix: colorpicker.setValue([0, 0, 175], false) not working...
	lineColor = "#0000af";
}

function resetNewEvent() {
	document.getElementById("eventName").value = "";
	document.getElementById("eventTime").value = "";
	document.getElementById("eventDuration").value = "";
	document.getElementById("eventDesc").value = "";
	document.getElementById("eventDate").value = "";
	document.getElementById("publicity").selectedIndex = 1;
	document.getElementById("evtGroup").selectedIndex = 1;
	document.getElementById("eventID").value = "-1";
}

//  ***********************************************************************
//  Function: blur functions for the form elements --> TODO:  Not correctly defined yet...
//  ***********************************************************************
/* 
	function m(el) {
      if ( el.defaultValue == el.value ) el.value = "";
}*/

//  ***********************************************************************
//  Function: switch out buttons dynamically (save, cancel --> modify or create event)
//  ***********************************************************************
function toggleModifyRtDetail(enableModify) {
	var savDiv = document.getElementById("saveRtDiv");
	var modifyDiv = document.getElementById("modifyRtDiv");
	
	if (enableModify) {
		savDiv.style.display = "none";
		modifyDiv.style.display = "block";
		modifyDiv.style.visibility = "visible";
		} else {
		savDiv.style.display = "block";
		savDiv.style.visibility = "visible";
		modifyDiv.style.display = "none";
	}
}

function toggleModifyEvtDetail(enableModify) {
	var savDiv = document.getElementById("saveEventDiv");
	var modifyDiv = document.getElementById("modifyEventDiv");
	
	if (enableModify) {
		savDiv.style.display = "none";
		modifyDiv.style.display = "block";
		} 
	else {
		savDiv.style.display = "block";
		modifyDiv.style.display = "none";
	}
}

//  ***********************************************************************
//  Function: Color picker functions for the route color.
//  ***********************************************************************
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
		
		oButton.set("value", sColor);
		YAHOO.util.Dom.setStyle("current-color", "backgroundColor", sColor);
		YAHOO.util.Dom.get("current-color").innerHTML = "Current color is " + sColor;
	});
            
	// Remove this event listener so that this code runs only once
	this.unsubscribe("option", onButtonOption);	
}

// Create a Menu instance to house the ColorPicker instance
var oColorPickerMenu = new YAHOO.widget.Menu("color-picker-menu");

// Create a Button instance of type "split"
var oButton = new YAHOO.widget.Button({ 
				type: "split", 
				id: "color-picker-button", 
				label: "<em id=\"current-color\">Current color is #0000AF.</em>", 
				menu: oColorPickerMenu, 
				container: "rtColor-container" });
			
oButton.on("appendTo", function () {
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
	oButton.on("option", onButtonOption);
        
	/* Add a listener for the "click" event.
	*/
	oButton.on("click", function () {
		lineColor = this.get("value");
		drawOverlay();
	});  
});

//  ***********************************************************************
//  Function: Calendar for Events
//  ***********************************************************************
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
						
					aDate = p_aArgs[0][0];
						
					//Dom.get("month-field").value = aDate[1];
					//Dom.get("day-field").value = aDate[2];
					//Dom.get("year-field").value = aDate[0];
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
			
			
			var handleSelect =  function(type,args,obj) {
				var dates = args[0];
				var date = dates[0];
				var year = date[0], month = date[1], day = date[2];

				var txtDate1 = document.getElementById("eventDate");
				txtDate1.value = month + "/" + day + "/" + year;
			}
			
			oCalendarMenu.selectEvent.subscribe(handleSelect, oCalendar, true);

		};

		// Create an Overlay instance to house the Calendar instance
		oCalendarMenu = new YAHOO.widget.Overlay("calendarmenu", { visible: false });

		// Create a Button instance of type "menu"	
		var oButton = new YAHOO.widget.Button({ 
											type: "menu", 
											id: "calendarpicker", 
											menu: oCalendarMenu, 
											container: "date" });

		oButton.on("appendTo", function () {
			// Create an empty body element for the Overlay instance in order 
			// to reserve space to render the Calendar instance into.		
			oCalendarMenu.setBody("&#32;");		
			oCalendarMenu.body.id = "calendarcontainer";			
		});

		// Add a "click" event listener that will render the Overlay, and 
		// instantiate the Calendar the first time the Button instance is 
		// clicked.
		oButton.on("click", onButtonClick);
	});	
}());



//  ***********************************************************************
//  Drag and Drop Utility --> TODO: STILL PROBLEMS WITH DRAG AND DROP BOUNDARIES!!!
//  ***********************************************************************
function setupdd() {
	ddCreateRoute = new YAHOO.util.DD("createRtTool");
	ddCreateRoute.setHandleElId("createHandle");
	ddRouteDetails = new YAHOO.util.DD("rtDetailTool");
	ddRouteDetails.setHandleElId("rtHandle");
	
	ddSetConstraints(ddCreateRoute, "createRt");
	ddSetConstraints(ddRouteDetails, "rtDetails");
	YAHOO.util.DDM.useShim = true; 
}

//  ***********************************************************************
//  Function: ddSetConstraints
//  Limits the drag area for the toolbars to the space the map takes.
//  ***********************************************************************
function ddSetConstraints(dd, ddID) {
	var element = document.getElementById(ddID);
	var xy = YAHOO.util.Dom.getXY(element);
	var width = parseInt(YAHOO.util.Dom.getStyle(element, 'width'), 10) + 20;
	var height = parseInt(YAHOO.util.Dom.getStyle(element, 'height'), 10) + 80;

	//Set left to x minus left
	var left = xy[0] - mapLimits.left;

	//Set right to right minus x minus width
	var right = mapLimits.right - xy[0] - width;

	//Set top to y minus top
	var top = xy[1] - mapLimits.top;

	//Set bottom to bottom minus y minus height
	var bottom = mapLimits.bottom - xy[1] - height;
	//var bottom = mapLimits.bottom - height; // <-- Both this the the line above are not behaving properly
	
	//Set the constraints based on the above calculations 
	dd.setXConstraint(left, right); 	
	dd.setYConstraint(top, bottom); 
}

// TODO: NEED TO RESET BOUNDARIES ON WINDOW RESIZE FOR DRAGGABLE TOOLBARS
/*YAHOO.util.Event.on(window, 'resize', function() { 
						ddSetConstraints(ddRouteDetails);
						ddSetConstraints(ddRouteDetails);}, this, true); */
																			
							
//  ***********************************************************************
//  Attach Listeners
//  ***********************************************************************
YAHOO.util.Event.addListener("newRoute", "click", createRt);
YAHOO.util.Event.addListener("cancelRoute", "click", cancelRoute);
YAHOO.util.Event.addListener("saveRoute", "click", saveRt);																		
YAHOO.util.Event.addListener("clearRoute", "click", resetRtDetail);
YAHOO.util.Event.addListener("modifyRoute", "click", modifyRt);
YAHOO.util.Event.addListener("finishRoute", "click", finishRoute);
YAHOO.util.Event.addListener("createEvent", "click", createEvent);
YAHOO.util.Event.addListener("saveEvent", "click", saveEvent);
YAHOO.util.Event.addListener("modifyEvent", "click", modifyEvent);
YAHOO.util.Event.addListener("cancelEvent", "click", cancelEvent);
YAHOO.util.Event.addListener("finishEvent", "click", finishEvent);

YAHOO.util.Event.onDOMReady(setupMap);
YAHOO.util.Event.onDOMReady(setupdd);


// TEMPORARY JUNK HERE
YAHOO.util.Event.onDOMReady(populateGroup);