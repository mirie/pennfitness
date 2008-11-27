



YAHOO.namespace("yuibook.tabs");
YAHOO.util.Event.onDOMReady(initTabs);
YAHOO.util.Event.onDOMReady(switchTabs);


YAHOO.namespace("example.container");
YAHOO.example.container.InfoToSearch = function() {
	YAHOO.example.container.panel1.show();
	YAHOO.example.container.panel2.hide();
}
YAHOO.example.container.SearchToInfo = function() {
	YAHOO.example.container.panel2.show();
	YAHOO.example.container.panel1.hide();
}

function initTabs(){
	var tabs = new YAHOO.widget.TabView('overlayTabOne'); 
	var tabs = new YAHOO.widget.TabView('overlayTabTwo'); 
	var tabs = new YAHOO.widget.TabView('GroupInfoTab'); 
	var tabs = new YAHOO.widget.TabView('EventInfoTab'); 
}

function switchTabs() {
	// Instantiate a Panel from markup
	YAHOO.example.container.panel1 = new YAHOO.widget.Panel("panel1", {  visible:false, fixedcenter : true, constraintoviewport:true } );
	YAHOO.example.container.panel1.render();
	YAHOO.example.container.panel2 = new YAHOO.widget.Panel("panel2", {  visible:false, fixedcenter : true, constraintoviewport:true } );
	YAHOO.example.container.panel2.render(); 
//	YAHOO.util.Event.addListener("myInfo", "click", YAHOO.example.container.InfoToSearch);
//	YAHOO.util.Event.addListener("search", "click", YAHOO.example.container.SearchToInfo);
}


function ShowSearchDialog() {
	YAHOO.example.container.SearchToInfo();
}

