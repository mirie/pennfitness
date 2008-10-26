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
		this.submit();
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
//YAHOO.util.Event.onDOMReady(routeDetailDialog);