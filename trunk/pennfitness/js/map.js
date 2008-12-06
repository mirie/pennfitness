/* Map Functions */


// Global Map Variables
var map;
var svOverlay;
var phillyLat = 39.955702;
var phillyLng = -75.179999;
var phillyZoomLev = 15;
var mapControl_x = 10;
var mapControl_y = 35;
var zoomControl_x = 15;
var zoomControl_y = 65;

// Global marker and polyline variables
var polylines;
var points = new Array();
var markers = new Array();
var lineColor = "#0000af";
var lineWeight = 3;
var lineOpacity = .8;
var fillOpacity = .2;
var startIcon;
var url = "http://localhost/pennfitness/assets/";
// Other variables
var distance;
var mapLimits;

// Listener functions
var drawOverlay;
var removeMarker;

//  ***********************************************************************
//  Function: init()
//  Initialize Google Map objects.
//  ***********************************************************************
function setupMap() {
    if (GBrowserIsCompatible()) {
        map = new GMap2(document.getElementById("map"), {draggableCursor:"auto", draggingCursor:"move"});

        map.disableDoubleClickZoom();

        // Load initial map (philadelphia) and controls
        map.addMapType(G_PHYSICAL_MAP); // Add the relief map view option
        map.addControl(new GScaleControl());
        map.addControl(new GMapTypeControl(), new GControlPosition(G_ANCHOR_TOP_RIGHT, new GSize(mapControl_x, mapControl_y)));
        var mapControl = new GLargeMapControl();

        // Have the zoom control on the right side underneath the map view controls
        var topRight = new GControlPosition(G_ANCHOR_TOP_RIGHT, new GSize(zoomControl_x, zoomControl_y)); 
        map.addControl(mapControl, topRight);

        // Center on Philly
        map.setCenter(new GLatLng(phillyLat, phillyLng), phillyZoomLev);       
		mapLimits = YAHOO.util.Dom.getRegion("map");
		
		// Street View overlay
		svOverlay = new GStreetviewOverlay();
		
		// Marker Icon Options
		startIcon = new GIcon(G_DEFAULT_ICON);
		startIcon.image = url + "marker_Start.png";
		//startIcon.image = "../assets/marker_Start.png";
		
		
    } else { 
        alert("Sorry, the Google Maps API is not compatible with this browser"); 
    }
}

//  ***********************************************************************
//  Function: addMarker( GPoint point)
//  Handles adding and removing markers to the map using a given GPoint object.
//  ***********************************************************************
function addMarker(point)
{
	// Create new marker and keep track of all markers
	var marker = new GMarker(point, { icon:startIcon, draggable:true, bouncy:true });
	
	map.addOverlay(marker);
	markers.push(marker);
    
	// Update icons for markers
	for (var i = 0; i < markers.length; i++ )
	{
		if (i == 0)
			markers[i].setImage(url + "marker_Start.png");	
		else if (i > 0 && i < markers.length - 1)
			markers[i].setImage(url + "marker_mid.png");
		else
			markers[i].setImage(url + "marker_stop.png");
	}	

	// Drag listener
	GEvent.addListener(marker, "drag", drawOverlay);

	// Second click listener: for deleting the marker
	GEvent.addListener(marker, "click", removeMarker); // end of second click
}

//  ***********************************************************************
//  Function: addMarkerPoint( number x, number y)
//  Handles adding a marker using a given x, y coordinate.
//  ***********************************************************************
function addMarkerPoint(lat, lng)
{
	addMarker(new GLatLng(lat, lng));
}

//  ***********************************************************************
//  Function: leftClick( GOverlay overlay, GPoint point)
//  Handles leftClick event when user clicks on map: Adds/Removes marker and redraws
//  overlay (marker icons/connecting lines).
//  ***********************************************************************
function leftClick(overlay, point) {
	if (point) {
		addMarker(point);
		drawOverlay();
	}
}

//  ***********************************************************************
//  Function: drawOverlay()
//  Handles drawing the marker icons and connecting lines.
//  ***********************************************************************
function drawOverlay(){
	var distance;
	var distanceDiv = document.getElementById("routeDistance");
	unit = " miles";
        
	var latlng;
        
	if (polylines) {  map.removeOverlay(polylines); }       
	points.length = 0;
        
	for (var i = 0; i < markers.length; i++) {
		latlng = markers[i].getLatLng();
		points.push(latlng);
	}
	polylines = new GPolyline(points, lineColor, lineWeight, lineOpacity);
	map.addOverlay(polylines);	
	// Round and convert distance to miles
    distance = Math.round(polylines.getLength() * 0.00062137119 * 1000)/1000;
    distanceDiv.innerHTML = distance + unit;
}

//  ***********************************************************************
//  Function: Removes a route from the map.
//
//  ***********************************************************************
function removeRoute() {
	for (i = 0; i < markers.length; i++) {
		map.removeOverlay(markers[i]);
	}
	points.length = 0;
	markers.length = 0;
	if (polylines) {  map.removeOverlay(polylines); }       
}

/***************************************************************************
 * Function: Removes the selected marker (Should be used as a listener for click event)
 ***************************************************************************/
function removeMarker() {
	for (i = 0; i < markers.length; i++) {
		if (markers[i] == this) {
			map.removeOverlay(this);
			break;                                          
		}
	}
	            
	// Shorten array of markers
	markers.splice(i, 1);
    
	for (var i = 0; i < markers.length; i++ )
	{
		if (i == 0)
			markers[i].setImage(url + "marker_Start.png");	
		else if (i > 0 && i < markers.length - 1)
			markers[i].setImage(url + "marker_mid.png");
		else
			markers[i].setImage(url + "marker_stop.png");
	}	
	
	
	drawOverlay();
}


//  ***********************************************************************
//  Function: Disable map and map marker functions.
//  
//  ***********************************************************************
function disableMap() {
	disableMarkerListeners();
	GEvent.clearListeners(map, "click");
}

function enableMap() {	
	GEvent.addListener(map, "click", leftClick);
	enableMarkerListeners();
}

function disableMarkerListeners() {
	for (var i = 0; i < markers.length; i++) {		
		GEvent.clearListeners(markers[i], "click");
		markers[i].disableDragging();
	}
}

function enableMarkerListeners() {
	for (var i = 0; i < markers.length; i++) {
		GEvent.addListener(markers[i], "click", removeMarker);
		markers[i].enableDragging();
	}
}




YAHOO.util.Event.onDOMReady(setupMap);