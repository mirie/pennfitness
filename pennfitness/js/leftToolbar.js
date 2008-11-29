// Left Toolbar functions (new routes/events, favorites, etc)

YAHOO.namespace("leftMenu.route");
 
var cal;
 
YAHOO.leftMenu.route.initCalendar = function() {
	cal = new YAHOO.widget.Calendar("calendar","leftEventCalendar");
	
	// If this month has some days with events
	if( YAHOO.util.Dom.get("eventDatesCurrentMonth").value.length > 0 )
	{
		cal.addRenderer(YAHOO.util.Dom.get("eventDatesCurrentMonth").value, cal.renderCellStyleHighlight4);
	}

	// Register handler for 'Next month', 'Prev month'
    cal.beforeRenderEvent.subscribe(beforeRenderEventHandler, cal, true);
    
    // Register handler for selectDate
    cal.selectEvent.subscribe(selectEventHandler, cal, true);

    cal.renderEvent.subscribe(function() {
        this._lastPageDate = this.cfg.getProperty("pageDate");
    }, cal, true);

	cal.render();
}

selectEventHandler = function(type, dates) {
	if( dates )
	{
		var datesToCompare = YAHOO.util.Dom.get("eventDatesCurrentMonth").value;
		var date = dates[0][0];
		var year = date[0], month = date[1], day = date[2];
		
		// If selected date has events
		if( datesToCompare.search(month+"/"+day+"/"+year) != -1 ) {
			alert('selected date has events!');
		}
		
	}
}

beforeRenderEventHandler = function(type, args) {
        var currentPage = this.cfg.getProperty("pageDate");
 
        if (this._lastPageDate) {
        	var mon = currentPage.getMonth() + 1;
	       	var year = currentPage.getFullYear();
			updateEventsOnCalendar(mon, year);                
        }
    }

updateEventsOnCalendar = function(month, year) {
	var successHandler = function(o) {			
		var jResponse;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
		if( jResponse.DATA.length > 0 )
		{
			cal.addRenderer(jResponse.DATA, cal.renderCellStyleHighlight4);
			// store returned dates
			YAHOO.util.Dom.get("eventDatesCurrentMonth").value = jResponse.DATA;
			
			// disable beforeRenderEvent handler
			cal.beforeRenderEvent.unsubscribe(beforeRenderEventHandler, cal);
			// render calendar
			cal.render();
			// enable beforeRenderEvent handler
		    cal.beforeRenderEvent.subscribe(beforeRenderEventHandler, cal, true);
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
	
	cal.removeRenderers(); // remove all custom renderer
	var connStr = "view/getEventDates.jsp?month="+month+"&year="+year;
	var transaction = YAHOO.util.Connect.asyncRequest("GET", connStr, callback);
}



var leftMenuEvtTab = new YAHOO.widget.TabView('eventListTabArea');
var leftMenuRtTab = new YAHOO.widget.TabView('routeListTabArea');


// ROUTE TAB
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

// EVENT TAB
YAHOO.leftMenu.route.getNewEventNames = function() {
	YAHOO.leftMenu.route.getNewEventNamesN(5,1);
}

YAHOO.leftMenu.route.getNewEventNamesN = function(recsPerPage, curPage) {
	var successHandler = function(o) {			
		var jResponse;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;
	    
	    YAHOO.util.Dom.get("newEventsList").innerHTML = jResponse.DATA.CONTENT; 
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
	
	var connStr = "view/allEvents.jsp?recsPerPage=" + recsPerPage + "&curPage=" + curPage;
	
	var transaction = YAHOO.util.Connect.asyncRequest("GET", connStr, callback);
}

YAHOO.util.Event.onDOMReady(YAHOO.leftMenu.route.initCalendar);

