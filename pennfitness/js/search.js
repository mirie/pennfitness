
YAHOO.namespace("yuibook.tabs");
YAHOO.util.Event.onDOMReady(initTabs);
YAHOO.util.Event.onDOMReady(initPaginators);
YAHOO.util.Event.onDOMReady(switchTabs);

// paginators
var pagNewRoutes, pagPopularRoutes, pagNewEvents, pagRouteSearch, pagGroupSearch;

YAHOO.namespace("search");
YAHOO.search.InfoToSearch = function() {
	YAHOO.search.panel1.show();
	YAHOO.search.panel2.hide();
}
YAHOO.search.SearchToInfo = function() {
	YAHOO.search.panel2.show();
	YAHOO.search.panel1.hide();
}

function initTabs(){
	var tabs = new YAHOO.widget.TabView('overlayTabOne'); 
	var tabs = new YAHOO.widget.TabView('overlayTabTwo'); 
	var tabs = new YAHOO.widget.TabView('GroupInfoTab'); 
	var tabs = new YAHOO.widget.TabView('EventInfoTab'); 
}

function switchTabs() {
	// Instantiate a Panel from markup
	YAHOO.search.panel1 = new YAHOO.widget.Panel("panel1", {  visible:false, fixedcenter : true, constraintoviewport:true } );
	YAHOO.search.panel1.render();
	YAHOO.search.panel2 = new YAHOO.widget.Panel("panel2", {  visible:false, fixedcenter : true, constraintoviewport:true } );
	YAHOO.search.panel2.render(); 
//	YAHOO.util.Event.addListener("myInfo", "click", YAHOO.search.InfoToSearch);
//	YAHOO.util.Event.addListener("search", "click", YAHOO.search.SearchToInfo);
}


function ShowSearchDialog() {
	YAHOO.search.SearchToInfo();
}

function searchEvent() {
	var successHandler = function(o) {	
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;

		YAHOO.util.Dom.get("eventSearchResult").innerHTML = jResponse.DATA.CONTENT;
		pagEventSearch.set('totalRecords',jResponse.DATA.TOTALRECCNT); 		
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000,
	}
	YAHOO.util.Connect.setForm("frmEventSearchData");
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "searchEvent.do", callback);
}


function searchRoute() {
	var successHandler = function(o) {	
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;

		YAHOO.util.Dom.get("routeSearchResult").innerHTML = jResponse.DATA.CONTENT;
		pagRouteSearch.set('totalRecords',jResponse.DATA.TOTALRECCNT); 		
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000,
	}
	YAHOO.util.Connect.setForm("frmRouteSearchData");
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "searchRoute.do", callback);
}

function searchGroup() {
	var successHandler = function(o) {	
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;

		YAHOO.util.Dom.get("groupSearchResult").innerHTML = jResponse.DATA.CONTENT;
		pagGroupSearch.set('totalRecords',jResponse.DATA.TOTALRECCNT); 		
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000,
	}
	YAHOO.util.Connect.setForm("frmGroupSearchData");
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "searchGroup.do", callback);
}


function initPaginators()
{
	// Paginator for new route list
	pagNewRoutes = new YAHOO.widget.Paginator({
	    rowsPerPage  : 5,
	    totalRecords : parseInt(YAHOO.util.Dom.get('totalRouteCnt').value),
	    template : "{PreviousPageLink} {PageLinks} {NextPageLink}",
		previousPageLinkLabel : "&lt;",
		nextPageLinkLabel : "&gt;",
		pageLinks    : 6,
	    containers   : ["pag_newRoutesList"] // or idStr or elem or [ elem, elem ]
	});	
	pagNewRoutes.render();
	pagNewRoutes.subscribe('changeRequest',pagNewRoutesHandler);

	// Paginator for popular route list
	pagPopularRoutes = new YAHOO.widget.Paginator({
	    rowsPerPage  : 5,
	    totalRecords : parseInt(YAHOO.util.Dom.get('totalRouteCnt').value),
	    template : "{PreviousPageLink} {PageLinks} {NextPageLink}",
		previousPageLinkLabel : "&lt;",
		nextPageLinkLabel : "&gt;",
		pageLinks    : 6,
	    containers   : ["pag_popularRoutesList"] // or idStr or elem or [ elem, elem ]
	});	
	pagPopularRoutes.render();
	pagPopularRoutes.subscribe('changeRequest',pagPopularRoutesHandler);

	// Paginator for event search
	pagEventSearch = new YAHOO.widget.Paginator({
		rowsPerPage  : 5,
	    totalRecords : 1,
	    containers   : ["pag_eventSearchResult"], // or idStr or elem or [ elem, elem ]
	});		
	YAHOO.util.Dom.get('ESrecsPerPage').value = pagEventSearch.getState().rowsPerPage;
	pagEventSearch.render();
	pagEventSearch.subscribe('changeRequest',pagEventSearchHandler); 
	
	// Paginator for new event list
	pagNewEvents = new YAHOO.widget.Paginator({
	    rowsPerPage  : 5,
	    totalRecords : parseInt(YAHOO.util.Dom.get('totalEventCnt').value),
	    template : "{PreviousPageLink} {PageLinks} {NextPageLink}",
		previousPageLinkLabel : "&lt;",
		nextPageLinkLabel : "&gt;",
		pageLinks    : 6,
	    containers   : ["pag_newEventsList"] 
	});	
	pagNewEvents.render();
	pagNewEvents.subscribe('changeRequest',pagNewEventsHandler);

	// Paginator for eventsOnDateList
	pagEventsOnDate = new YAHOO.widget.Paginator({
	    rowsPerPage  : 5,
	    totalRecords : parseInt(YAHOO.util.Dom.get('totalEventCnt').value),
	    template : "{PreviousPageLink} {PageLinks} {NextPageLink}",
		previousPageLinkLabel : "&lt;",
		nextPageLinkLabel : "&gt;",
		pageLinks    : 6,
	    containers   : ["pag_eventsOnDateList"] // or idStr or elem or [ elem, elem ]
	});	
	pagEventsOnDate.render();
	pagEventsOnDate.subscribe('changeRequest',pagEventsOnDateHandler);
	
	// Paginator for event list for a particular route
	pagEventListByRoute = new YAHOO.widget.Paginator({
	    rowsPerPage  : 5,
	    //totalRecords : 1,
	    containers   : ["pag_eventsListByRoute"]
	});		
	
	YAHOO.util.Dom.get('ELBRrecsPerPage').value = pagEventSearch.getState().rowsPerPage;
	pagNewEvents.render();
	//pagNewEvents.subscribe('changeRequest',pagEventListByRouteHandler);


	// Paginator for route search
	pagRouteSearch = new YAHOO.widget.Paginator({
		rowsPerPage  : 5,
	    totalRecords : 1,
	    containers   : ["pag_routeSearchResult"], // or idStr or elem or [ elem, elem ]
//	    template     : "{FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}",
//	    rowsPerPageOptions : [ 10, 25, 50, 100 ] 
	});		
	YAHOO.util.Dom.get('RSrecsPerPage').value = pagRouteSearch.getState().rowsPerPage;
	pagRouteSearch.render();
	pagRouteSearch.subscribe('changeRequest',pagRouteSearchHandler); 

	// Paginator for group search
	pagGroupSearch = new YAHOO.widget.Paginator({
		rowsPerPage  : 5,
	    totalRecords : 1,
	    containers   : ["pag_groupSearchResult"] // or idStr or elem or [ elem, elem ]
	});
	YAHOO.util.Dom.get('GSrecsPerPage').value = pagGroupSearch.getState().rowsPerPage;
	pagGroupSearch.render();
	pagGroupSearch.subscribe('changeRequest',pagGroupSearchHandler); 

}

// Paginator handler for new routes list
function pagNewRoutesHandler(newState)
{
	YAHOO.leftMenu.route.getNewRouteNamesN(newState.rowsPerPage, newState.page);
	newState.paginator.setState(newState);
}

// Paginator handler for new routes list
function pagPopularRoutesHandler(newState)
{
	YAHOO.leftMenu.route.getPopularRouteNamesN(newState.rowsPerPage, newState.page);
	newState.paginator.setState(newState);
}

// Paginator handler for new events list
function pagNewEventsHandler(newState)
{
//	YAHOO.leftMenu.route.getNewRouteNamesN(newState.rowsPerPage, newState.page);
//	newState.paginator.setState(newState);
	//TODO:
}

// Paginator handler for event search
function pagEventSearchHandler(newState)
{
	// Set paging values
	YAHOO.util.Dom.get("ESrecsPerPage").value = newState.rowsPerPage;
	YAHOO.util.Dom.get("EScurPage").value = newState.page;

	searchEvent();
	newState.paginator.setState(newState);
}


// Paginator handler for route search
function pagRouteSearchHandler(newState)
{
	// Set paging values
	YAHOO.util.Dom.get("RSrecsPerPage").value = newState.rowsPerPage;
	YAHOO.util.Dom.get("RScurPage").value = newState.page;

	searchRoute();
	newState.paginator.setState(newState);
}

// Paginator handler for group search
function pagGroupSearchHandler(newState)
{
	// Set paging values
	YAHOO.util.Dom.get("GSrecsPerPage").value = newState.rowsPerPage;
	YAHOO.util.Dom.get("GScurPage").value = newState.page;

	searchGroup();
	newState.paginator.setState(newState);
}





