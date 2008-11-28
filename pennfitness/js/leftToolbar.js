// Left Toolbar functions (new routes/events, favorites, etc)

YAHOO.namespace("leftMenu.route");
 
YAHOO.leftMenu.route.initCalendar = function() {
	YAHOO.leftMenu.route.calendar = new YAHOO.widget.Calendar("calendar","leftEventCalendar");
	YAHOO.leftMenu.route.calendar.render();
}


var leftMenuEvtTab = new YAHOO.widget.TabView('eventListTabArea');
var leftMenuRtTab = new YAHOO.widget.TabView('routeListTabArea');


YAHOO.leftMenu.route.getNewRouteNames = function() {
	YAHOO.leftMenu.route.getNewRouteNamesN(5,1);
}

YAHOO.leftMenu.route.getNewRouteNamesN = function(recsPerPage, curPage) {
	var successHandler = function(o) {			
		var jResponse;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	    
	    YAHOO.util.Dom.get("newRoutesList").innerHTML = jResponse.DATA.CONTENT; 
	    pagNewRoutes.set('totalRecords',jResponse.DATA.TOTALRECCNT); 
	    pagNewRoutes.setPage(jResponse.DATA.CURPAGE, true);
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
		timeout:3000,
	}
	
	var connStr = "view/allRoutes.jsp?recsPerPage=" + recsPerPage + "&curPage=" + curPage;
	
	var transaction = YAHOO.util.Connect.asyncRequest("GET", connStr, callback);
}

YAHOO.leftMenu.route.getPopularRouteNamesN = function(recsPerPage, curPage) {
	var successHandler = function(o) {			
		var jResponse;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	    
	    YAHOO.util.Dom.get("popularRoutesList").innerHTML = jResponse.DATA.CONTENT; 
	    pagNewRoutes.set('totalRecords',jResponse.DATA.TOTALRECCNT); 
	    pagPopularRoutes.set('page', jResponse.DATA.CURPAGE);
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
		timeout:3000,
	}
	
	var connStr = "view/popularRoutes.jsp?recsPerPage=" + recsPerPage + "&curPage=" + curPage;
	
	var transaction = YAHOO.util.Connect.asyncRequest("GET", connStr, callback);
}

// YAHOO.util.Event.onDOMReady(YAHOO.leftMenu.route.getNewRouteNames);
YAHOO.util.Event.onDOMReady(YAHOO.leftMenu.route.initCalendar);

