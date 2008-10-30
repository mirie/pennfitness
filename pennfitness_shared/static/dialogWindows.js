var rtDetailDialog;
var mapClickHandle;

//  ***********************************************************************
//  Function: initializeDialog)
//  Display dialog for 'Select Route or Create New'.
//  ***********************************************************************
function initializeDialog() {

	var handleNew = function() {
		routeDetailDialog();
		mapClickHandle = GEvent.addListener(map, "click", leftClick());
		enableMarkerListeners();
		this.hide();
	}
	
	// Initialize Dialog
	var initDialog;
	initDialog = new YAHOO.widget.SimpleDialog("initDialog", { 
		width: "12em", 
		effect:{effect:YAHOO.widget.ContainerEffect.FADE,
	        duration:0.25}, 
		fixedcenter:false,
		modal:false,
		visible:false,
		draggable:true,
		constraintoviewport:true,
		xy: [981, 64] }
	initDialog.setBody("Please Select a Route or create a New Route.");
	
	
	var newRouteBtn = [ { text:"New", 
						handler:handleNew },
					  ];
					  
	initDialog.cfg.queueProperty("buttons", newRouteBtn);
	initDialog.render(); 

	YAHOO.util.Event.addListener("showDialog", "click", initDialog.show, initDialog, true);
}


//  ***********************************************************************
//  Function: routeDetailDialog)
//  Dialog window for route Detail
//  ***********************************************************************
function routeDetailDialog() {
	
	// Define various event handlers for Dialog
	var handleSubmit = function() {
		// save Route routine
		
		GEvent.removeListener(mapClickHandle);
		disableMarkerListeners();
		this.hide();
	};
	var handleCancel = function() {
		GEvent.removeListener(mapClickHandle);
		disableMarkerListeners();
		this.cancel();
	};
	var handleSuccess = function(o) {
		var response = o.responseText;
	};
	var handleFailure = function(o) {
		alert("Submission failed: " + o.status);
	};

	// Instantiate the Dialog
	rtDetailDialog = new YAHOO.widget.Dialog("routeDetailDialog", 
							{ width : "15em",
							  fixedcenter : false,
							  visible : false, 
							  constraintoviewport : true,
							  draggable: true,
							  xy: [800, 64],
							  buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
								      { text:"Cancel", handler:handleCancel } ]
							});


	// Wire up the success and failure handlers
	rtDetailDialog.callback = { success: handleSuccess,
						     failure: handleFailure };
	
	// Render the Dialog
	rtDetailDialog.render();
	rtDetailDialog.show();
	//YAHOO.util.Event.addListener("showDialog2", "click", rtDetailDialog.show, rtDetailDialog, true);
}

YAHOO.util.Event.onDOMReady(initializeDialog);

function saveRoute() {
	var successHandler = function(o) {
		alert(o.responseText);
		// if (o.responseText == '1') {
			// alert("Route saved successfully");
		// }
		// else {
			// alert("Route is not saved");
		// }
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		success:successHandler,
		failure:failureHandler,
		timeout:3000
	}

//	var routeName = document.getElementById("routeName").innerHTML;
	var distance = document.getElementById("distance").innerHTML;
//	var routeDesc = document.getElementById("routeDesc").innerHTML;
	var routeColor = "666FFF";
	var points;

	// if (routeName.innerHTML.length == 0 || markers.length <= 1) {
		// alert("Please set up a route before saving!");
		// return;

	 // var newPoint;
	 // var markers = allMarkers[currRouteIndex];
     // for (var i = 0; i < markers.length; i++) {
		// newPoint = document.createElement("input");
        // newPoint.type = "hidden";
        // newPoint.name = "pvalue";
        // newPoint.id = "pvalue";
        // newPoint.value = markers[i].getLatLng().lat() + "," + markers[i].getLatLng().lng();    
        // form.appendChild(newPoint);         
    // }
    
	// YAHOO.util.Connect.setForm(form);    



	
	
	// var form = document.getElementById("frmRoute");
	
	// var transaction = YAHOO.util.Connect.asyncRequest("POST", "registerRoute", callback, jsonPostData)
	}
	
    // clear previous pvalues   
    // while ((pvalue = document.getElementById("pvalue")) != null)
    // {
        // form.removeChild(pvalue);
    // }

