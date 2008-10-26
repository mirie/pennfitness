function initializeDialog() {

	var handleNew = function() {
		alert("Clicked New");
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

YAHOO.util.Event.onDOMReady(initializeDialog);