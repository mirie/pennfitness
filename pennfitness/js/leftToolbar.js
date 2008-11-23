// Left Toolbar functions (new routes/events, favorites, etc)

YAHOO.namespace("leftMenu.route");
 
YAHOO.leftMenu.route.initCalendar = function() {
	YAHOO.leftMenu.route.calendar = new YAHOO.widget.Calendar("calendar","leftEventCalendar");
	YAHOO.leftMenu.route.calendar.render();
}


var leftMenuEvtTab = new YAHOO.widget.TabView('eventListTabArea');
var leftMenuRtTab = new YAHOO.widget.TabView('routeListTabArea');



YAHOO.leftMenu.route.getNewRouteNames = function() {
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
	    	var rlist = document.getElementById("newRtListTab");
	    	var routeList = response.DATA.ROUTES.split(";")
	    	var content ="";
	    	
	    	rlist.innerHTML = "";
			
			for (var i = 0 ; i < routeList.length -1; i++) {
				var k = i + 1;
				var route = routeList[i].split("-");
				content += k + ". <a href=\"javascript:YAHOO.pennfitness.float.getRoute('" + route[0] + "')\">" + route[1] + "</a><br />";			
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


YAHOO.util.Event.onDOMReady(YAHOO.leftMenu.route.getNewRouteNames);
YAHOO.util.Event.onDOMReady(YAHOO.leftMenu.route.initCalendar);

