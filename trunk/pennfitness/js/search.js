
YAHOO.namespace("yuibook.tabs");
YAHOO.util.Event.onDOMReady(initTabs);
YAHOO.util.Event.onDOMReady(initPaginators);
YAHOO.util.Event.onDOMReady(switchTabs);

// paginators
var pagNewRoutes;

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

function searchRoute() {

	// FOR JSON Handling
	var successHandler = function(o) {	
		var response;
//		alert(o.responseText);
		
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;

		YAHOO.util.Dom.get("routeSearchResult").innerHTML = jResponse.DATA.ROUTES;
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


function initPaginators()
{
	// Paginator for new route list
	pagNewRoutes = new YAHOO.widget.Paginator({
	    rowsPerPage  : 5,
	    totalRecords : 5,
	    template : "{PreviousPageLink} {PageLinks} {NextPageLink}",
		previousPageLinkLabel : "&lt;",
		nextPageLinkLabel : "&gt;",
	    containers   : ["pag_newRoutesList"
						] // or idStr or elem or [ elem, elem ]
	});	
	pagNewRoutes.render();
	pagNewRoutes.subscribe('changeRequest',pagNewRoutesHandler);
	
	// Paginator for route search
	pagRouteSearch = new YAHOO.widget.Paginator({
	    rowsPerPage  : 5,
	    totalRecords : 5,
//	    template : "{PreviousPageLink} {PageLinks} {NextPageLink}",
//		previousPageLinkLabel : "&lt;",
//		nextPageLinkLabel : "&gt;",
	    containers   : ["pag_routeSearchResult"
						] // or idStr or elem or [ elem, elem ]
	});	
	pagRouteSearch.render();
	pagRouteSearch.subscribe('changeRequest',pagRouteSearchHandler); 
}

// Paginator handler for new routes list
function pagNewRoutesHandler(newState)
{
	YAHOO.leftMenu.route.getNewRouteNamesN(newState.rowsPerPage, newState.page);
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






