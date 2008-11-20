// Left Toolbar functions (new routes/events, favorites, etc)

YAHOO.namespace("leftTB.route");

YAHOO.leftTB.route.getNewRouteNames = function() {
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
	    	var rlist = document.getElementById("newRtList");
	    	var routeList = response.DATA.ROUTES.split(";")
	    	var content ="";
	    	
	    	rlist.innerHTML = "";
			
			for (var i = 0 ; i < routeList.length -1; i++) {
				var k = i + 1;
				var route = routeList[i].split("-");
				content += k + ". <a href=\"javascript:YAHOO.leftTB.route.getRoute('" + route[0] + "')\">" + route[1] + "</a><br />";			
			}
			
			rlist.innerHTML = content;
	    	
	    } else {
	    	alert("Could not load routes!");
	    }	    
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
		timeout:3000,
	}
	
	var transaction = YAHOO.util.Connect.asyncRequest("GET", "view/allRoutes.jsp", callback); //TODO: put the appropriate servlet/jsp
}


YAHOO.leftTB.route.getRoute = function(routeID) {
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
			
			document.getElementById("routeName").value = response.DATA.ROUTE_NAME;			
			document.getElementById("routeColor").value = response.DATA.ROUTE_COLOR;

			
			//alert(lineColor);
			enableMap();
			// Add markers and draw route
			var pointData = response.DATA.ROUTE_PTS.split(";");
			for (var i = 0; i < pointData.length; i++) {
				//if (pointData[i] == "") break;
				var point = pointData[i].split(",");
				var lat = point[0];
				var lng = point[1];
				addMarkerPoint(lat, lng);
			}

			//alert(lineColor);
			//lineColor = response.DATA.ROUTE_COLOR; // routeColor;
			lineColor = response.DATA.ROUTE_COLOR;
			drawOverlay();
			disableMap();  
			hideNewRtTool();
			showRtDetailsTool();
			enableEditRtDetail(false);
			toggleModifyRtDetail(true);						
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
	
	var transaction = YAHOO.util.Connect.asyncRequest("GET", "view/routeByID.jsp?routeID=" + routeID, callback); //TODO: put the appropriate servlet/jsp
}


YAHOO.util.Event.onDOMReady(YAHOO.leftTB.route.getNewRouteNames);


