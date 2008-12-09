
YAHOO.namespace("yuibook.tabs");
YAHOO.util.Event.onDOMReady(initTabs);
YAHOO.util.Event.onDOMReady(initPaginators);
YAHOO.util.Event.onDOMReady(switchTabs);

// paginators
var pagNewRoutes, pagPopularRoutes, pagNewEvents, pagRouteSearch, pagGroupSearch, pagEventListByRoute;

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
		success:successHandler
	}
	YAHOO.util.Connect.setForm("frmEventSearchData");
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "searchEvent.do", callback);
}

function joinGroup(groupID) {
	var successHandler = function(o) {	
		var response;
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;

		alert("Successfully joined to the group!");	
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}

	var callback = {
		failure:failureHandler,
		success:successHandler
	}

	var strData = "action=joinGroup&notify=Y&groupID=" + groupID;
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "mgGroupReg.do", callback, strData);
	
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
		success:successHandler
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
		success:successHandler		
	}
	YAHOO.util.Connect.setForm("frmGroupSearchData");
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "searchGroup.do", callback);
}


function initPaginators()
{

	// Paginator for event search
	pagEventSearch = new YAHOO.widget.Paginator({
		rowsPerPage  : 3,
	    totalRecords : 1,
	    containers   : ["pag_eventSearchResult"], // or idStr or elem or [ elem, elem ]
	});		
	YAHOO.util.Dom.get('ESrecsPerPage').value = pagEventSearch.getState().rowsPerPage;
	pagEventSearch.render();
	pagEventSearch.subscribe('changeRequest',pagEventSearchHandler); 
	
	
	// Paginator for event list for a particular route
	pagEventListByRoute = new YAHOO.widget.Paginator({
	    rowsPerPage  : 5,
	    totalRecords : 1,
	    containers   : ["pag_eventsListByRoute"]
	});		
	//YAHOO.util.Dom.get('ELBRrecsPerPage').value = pagEventListByRoute.getState().rowsPerPage;
	pagEventListByRoute.render();
	pagEventListByRoute.subscribe('changeRequest',pagEventListByRouteHandler);

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

// Paginator handler for group search
function pagEventListByRouteHandler(newState)
{
	// Set paging values
	YAHOO.util.Dom.get("ELBRrecsPerPage").value = newState.rowsPerPage;
	YAHOO.util.Dom.get("ELBRcurPage").value = newState.page;

	displayEventList();
	newState.paginator.setState(newState);
}

function clearEventSearch()
{
	document.getElementById("ESevtType").options[0].selected = true;
	document.getElementById("ESKeyword").value = "";
	document.getElementById("ESFromDate").value = "From (MM/DD/YYYY)";
	document.getElementById("ESToDate").value = "To (MM/DD/YYYY)";
	
}

function clearGroupSearch()
{
	document.getElementById("GSKeyword").value = "";
	document.getElementById("GSCreator").value = "";
}

function clearRouteSearch()
{
	document.getElementById("RSKeyword").value = "";
	document.getElementById("RSFromDistance").value = "";
	document.getElementById("RSToDistance").value = "";
	document.getElementById("RSFromDate").value = "From (MM/DD/YYYY)";
	document.getElementById("RSToDate").value = "To (MM/DD/YYYY)";
}