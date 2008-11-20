// Left Toolbar functions (new routes/events, favorites, etc)

YAHOO.namespace("leftTB.route");

var getNewRouteNames = function() {

	var successHandler = function(o) {		
		var strList = o.responseText.split(";");
		var rlist = document.getElementById("newRtList");
		var content = "";
		rlist.innerHTML = "";
		
		for (var i = 0 ; i < strList.length -1; i++) {
			var k = i + 1;
			//TODO bug here --> link is NOT working
//			content += k + ". <a href=\"javascript:view/routeByName.jsp?routeName=" + strList[i] + ";\">" + strList[i] + "</a><br />";

			content += k + ". <a href=\"javascript:getRoute('" + strList[i] + "')\">" + strList[i] + "</a><br />";
			
			//content += k + ". <a href=\"javascript:view/routeByName.jsp?routeName=" + strList[i] + "\>" + strList[i] + "</a><br />";
			
			
			//var strRoute = strList[i].split("-"); // temp: expecting routeID:routeName
			//content += (k) + ". <a href=\"javascript:getRoute('" + strRoute[0] + "')\">" + strRoute[1] + "</a><br>";
		}
		
		rlist.innerHTML = content;
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


var getRoute = function(route) {
	var successHandler = function(o) {
		// TEMP: currently expecting the following order: distance?
		// routeID, routeName, routeDesc, routeColor
		// out.println( route.getName() + ";" + route.getColor() + ";" + route.getPtValues()
		routeData = o.responseText.split(";");
		
		//document.getElementById("routeID").value = routeData[0];
		document.getElementById("routeName").value = routeData[0];
		//document.getElementById("routeDesc").value = routeData[2];
		document.getElementById("routeColor").value = routeData[1]; //TODO: not working - inseob
		
//		document.getElementById("current-color").style.backgroundColor = routeColor; // Slight BUG HERE... need minor fix: colorpicker.setValue() not working...
		lineColor = routeData[1]; // routeColor;
	
		removeRoute();

		var pointData = routeData[2];
		// add markers
		for (var i = 2; i < pointData.length; i++) {
			if (pointData[i] == "") break;
			var lat = pointData[i].split(",")[0];
			var lng = pointData[i].split(",")[1];
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
	
	var transaction = YAHOO.util.Connect.asyncRequest("GET", "view/routeByName.jsp?routeName=" + route, callback); //TODO: put the appropriate servlet/jsp
}

//YAHOO.util.Event.addListener("GetRouteNames", "click", YAHOO.leftTB.route.getRouteNames);

// Turn on this to get new route when loading(should fix bug) - inseob
YAHOO.util.Event.onContentReady(getNewRouteNames());


