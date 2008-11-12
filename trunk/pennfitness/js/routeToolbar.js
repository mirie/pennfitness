// Route Toolbar
// Mai Irie
// version 11/11/2008


//  ***********************************************************************
//  Variables
//  ***********************************************************************
var ddCreateRoute, ddRouteDetails;
YAHOO.namespace("route.toolbar");

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
	enableEditRtDetail("disabled");
	disableMap();
	
	// TODO: FINISH THIS: Save the information: name, description, points, distance, color
	
	var successHandler = function(o) {
		if (o.responseText == '1') {
			alert("Route saved successfully");
		}
		else {
		alert("Route is not saved");
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

	var form = document.getElementById("frmRouteDetails");

	// clear previous pvalues	
	while((pvalue = document.getElementById("pvalue")) != null)
	{
		form.removeChild(pvalue);
	}

	var newPoint;
	for(i = 0 ; i < markers.length ;i++) {

		newPoint = document.createElement("input");
		newPoint.type = "hidden";
		newPoint.name = "pvalue";
		newPoint.id = "pvalue";
		newPoint.value = markers[i].getLatLng().lat() + "," + markers[i].getLatLng().lng();

		form.appendChild(newPoint);			
	}

	if ( (rtColor = document.getElementById("routeColor")) != null) {
		form.removeChild(rtColor);
	}
	
	var routeColor = document.createElement("input");
	routeColor.type = "hidden";
	routeColor.name = "routeColor";
	routeColor.id = "routeColor";
	var strColor = document.getElementById("current-color").innerHTML;
	var index = strColor.indexOf("#");
	strColor = strColor.substring(index, strColor.length - 1);
	routeColor.value = strColor;
	form.appendChild(routeColor);
	
	YAHOO.util.Connect.setForm(form);
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "saveRoute.do", callback);
	
	toggleModifyRtDetail(true);
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
	showNewRtTool();	
	document.getElementById("modifyRtDiv").style.visibility = "hidden";
}

//  ***********************************************************************
//  Function: createEvent
//  Displays the New Event details form.
//  ***********************************************************************
function createEvent() {
	document.getElementById("modifyRtDiv").style.visibility = "hidden";
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
	
	enableEditNewEvent("disabled");
	
	// TODO: FINISH THIS: Save the information: name, description, time, date, duration, group		
	var successHandler = function(o) {
		if (o.responseText == '1') {
			alert("Event saved successfully");
		}
		else {
			alert("Route is not saved");
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

	var form = document.getElementById("frmCreateEvent");
	
	YAHOO.util.Connect.setForm(form);
	
	//var transaction = YAHOO.util.Connect.asyncRequest("POST", ".jsp", callback);
	
	toggleModifyEvtDetail(true);	
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
	document.getElementById("rtDetails").style.visibility = "hidden";	
}

function showRtDetailsTool() {
	document.getElementById("rtDetailTool").style.top = "135px";
	document.getElementById("rtDetailTool").style.right = "89px";
	document.getElementById("rtDetailTool").style.left = "";
	document.getElementById("rtDetails").style.visibility = "visible";
	document.getElementById("saveRtDiv").style.visibility = "visible";
	document.getElementById("modifyRtDiv").style.visibility = "hidden";
}

function hideNewEvent() {
	document.getElementById("newEvent").style.display = "none";	
}

function showNewEvent() {
	document.getElementById("newEvent").style.display = "block";
	document.getElementById("newEvent").style.visibility = "visible";
}

//  ***********************************************************************
//  Function: Enables/Disables all form elements for editing
//  ***********************************************************************
function enableEditRtDetail(enable) {
	document.getElementById("routeName").disabled = enable;
	document.getElementById("routeDesc").disabled = enable;
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
	document.getElementById("routeName").value = "";
	document.getElementById("routeDesc").value = "";
	document.getElementById("distance").innerHTML = "0 miles";
	removeRoute();
}

function resetNewEvent() {
	document.getElementById("eventName").value = "";
	document.getElementById("eventTime").value = "";
	document.getElementById("eventDuration").value = "";
	document.getElementById("eventDesc").value = "";
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
	if (enableModify) {
		document.getElementById("saveRtDiv").style.display = "none";
		document.getElementById("modifyRtDiv").style.display = "block";
		document.getElementById("modifyRtDiv").style.visibility = "visible";
		} else {
		document.getElementById("saveRtDiv").style.display = "block";
		document.getElementById("modifyRtDiv").style.display = "none";
		document.getElementById("modifyRtDiv").style.visibility = "hidden";
	}
}

function toggleModifyEvtDetail(enableModify) {
	if (enableModify) {
		document.getElementById("saveEventDiv").style.display = "none";
		document.getElementById("modifyEventDiv").style.display = "block";
		} 
	else {
		document.getElementById("saveEventDiv").style.display = "block";
		document.getElementById("modifyEventDiv").style.display = "none";
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
	var oColorPicker = new YAHOO.widget.ColorPicker(oColorPickerMenu.body.id, {
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