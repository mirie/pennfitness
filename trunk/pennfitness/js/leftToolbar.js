// Left Toolbar functions (new routes/events, favorites, etc)

YAHOO.namespace("leftTB.route");

leftTB.route.getNewRouteNames = function() {

	var successHandler = function(o) {		
		var strList = o.responseText.split(";");
		var rlist = document.getElementById("newRtList");
		rlist.innerHTML = "";
		
		for (var i = 0 ; i < strList.length ; i++) {
			var strRoute = strList[i].split("-"); // temp: expecting routeID:routeName
			rlist.innerHTML += (i + 1) + ". <a href=\"javascript:leftTB.route.getRoute('" + strRoute[0] + "')\">" + strRoute[1] + "</a><br>";
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
	
var transaction = YAHOO.util.Connect.asyncRequest("GET", "", callback); //TODO: put the appropriate servlet/jsp
}


leftTB.route.getRoute = function(route) {
	var successHandler = function(o) {
		// TEMP: currently expecting the following order: distance?
		// routeID, routeName, routeDesc, routeColor
		
		routeData = o.responseText.split(";");
		document.getElementById("routeID").value = routeData[0];
		document.getElementById("routeName").value = routeData[1];
		document.getElementById("routeDesc").value = routeData[2];
		document.getElementById("routeColor").value = routeData[3];
		
		document.getElementById("current-color").style.backgroundColor = routeColor; // Slight BUG HERE... need minor fix: colorpicker.setValue() not working...
		lineColor = routeColor;
	
		removeRoute();

		// add markers
		for (int i = 4; i < routeData.length; i++) {
			if (routeData[i] == "") continue;
			var lat = routeData[i].split(",")[0];
			var lng = routeData[i].split(",")[1];
			addMarkerPoint(lat, lng);
		}

		drawOverlay();
		  
		hideNewRtTool();
		showRtDetailsTool();
		enableEditRtDetail(false);
		toggleModifyRtDetail(true);
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
		timeout:3000
	}
	
	var transaction = YAHOO.util.Connect.asyncRequest("GET", "?routeName=" + route, callback); //TODO: put the appropriate servlet/jsp
}

//YAHOO.util.Event.addListener("GetRouteNames", "click", YAHOO.leftTB.route.getRouteNames);