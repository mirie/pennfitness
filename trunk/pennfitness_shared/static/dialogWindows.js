var rtDetailDialog;

//  ***********************************************************************
//  Function: initializeDialog)
//  Display dialog for 'Select Route or Create New'.
//  ***********************************************************************
function initializeDialog() {

	var handleNew = function() {
		//alert("Clicked New");
		
		routeDetailDialog();
		this.hide();
	}
	
	// Initialize Dialog
	var initDialog;
	initDialog = new YAHOO.widget.SimpleDialog("initDialog", { 
		width: "20em", 
		effect:{effect:YAHOO.widget.ContainerEffect.FADE,
	        duration:0.25}, 
		fixedcenter:true,
		modal:false,
		visible:false,
		draggable:true });
		
	initDialog.setBody("Please Select a route or create New");

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
		saveRoute();
	};
	var handleCancel = function() {
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
							{ width : "30em",
							  fixedcenter : true,
							  visible : false, 
							  constraintoviewport : true,
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
		success:successHandler,
		failure:failureHandler,
		timeout:3000
	}

	//var routeName = document.getElementById("routeName").innerHTML;
	//var creatorID = document.getElementById("creatorID").innerHTML;
	//var distance = document.getElementById("distance").innerHTML;
	//var routeDesc = document.getElementById("routeDesc").innerHTML;
	//var routeColor = "666FFF";
	
	var routeName = "r1";
	var routeDistance = 10;
	var routeDescrip = "some text in here...";
	var routeColor = "666FFF";
	var points = [1,2,3];
	
	// JSON
	var formData =
		{
			"routeName" : routeName,
			"distance" : routeDistance,
			"routeDesc" : routeDescrip,
			"routeColor" : routeColor,
			"points" : points
		};
	
	var jsonPostData = YAHOO.lang.JSON.stringify(formData);

	var transaction = YAHOO.util.Connect.asyncRequest("POST", "registerRoute", callback, jsonPostData);
}








	
    // clear previous pvalues   
    // while ((pvalue = document.getElementById("pvalue")) != null)
    // {
        // form.removeChild(pvalue);
    // }
	

	// // var routeColor = document.getElementById("routeColorChange");
	
	// //if (routeName.innerHTML.length == 0 || routeColor.value.length == 0 || markers.length <= 1) {
	// //	alert("Please set up a route before saving!");
	// //	return;
	// if ((val = document.getElementById("passroutename")) != null) {
		// form.removeChild(val);
	// }

	// var hiddenName;
	// hiddenName = document.createElement("input");
	// hiddenName.type = "hidden";
	// hiddenName.name = "passroutename";
	// hiddenName.id = "passroutename";
	// hiddenName.value = routeName.innerHTML;
	// form.appendChild(hiddenName);
	// pointsDiv.innerHTML += "here<br />";
    
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
// }
