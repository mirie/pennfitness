// Global Map Variables
var map;
var phillyLat = 39.952202;
var phillyLng = -75.175406;
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


// Other variables
var distance;

//  ***********************************************************************
//  Function: init()
//  Initialize Google Map objects.
//  ***********************************************************************
function setupMap() {
    if (GBrowserIsCompatible()) {
        map = new GMap2(document.getElementById("map"), {draggableCursor:"auto", draggingCursor:"move"});

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

		// Handles resizing full-screen map more efficiently
		if (window.attachEvent) {
			window.attachEvent("onresize", function() { map.onResize() });
			window.attachEvent("onload", function() { map.onResize() });
		} else if (window.addEventListener) {
			window.addEventListener("resize", function() { map.onResize() }, false);
			window.attachEvent("onload", function() { map.onResize() }, false);
		}
        
    } else { 
        alert("Sorry, the Google Maps API is not compatible with this browser"); 
    }
}


function disableMarkerListeners() {
	for (i = 0; i < markers.length; i++) {
		GEvent.clearListeners(marker[i], "drag");
		GEvent.clearListeners(marker[i], "click");
	}
}

function enableMarkerListeners() {
	for (i = 0; i < markers.length; i++) {
		GEvent.addListener(marker[i], "drag", function() {  drawOverlay();  });
		GEvent.addListener(marker[i], "click", deleteMarkerClick() );
	}
}




//  ***********************************************************************
//  Function: addMarker( GPoint point)
//  Handles adding and removing markers to the map using a given GPoint object.
//  ***********************************************************************
function addMarker(point)
{
	// Create new marker and keep track of all markers
	var marker = new GMarker(point, {draggable:true, bouncy:true});
	map.addOverlay(marker);
	markers.push(marker);
	
	// Drag listener
	GEvent.addListener(marker, "drag", function() {  drawOverlay();  });

	// Second click listener: for deleting the marker
	GEvent.addListener(marker, "click", deleteMarkerClick()); // end of second click
}

function deleteMarkerClick() {
	for (i = 0; i < markers.length; i++) {
			if (markers[i] == marker) {
				map.removeOverlay(markers[i]);
				break;						
			}
		}
		
		// Shorten array of markers
		markers.splice(i, 1);
		
		drawOverlay();
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
	//var distanceDiv = document.getElementById("distance");
	// unit = " miles";
	
	var latlng;
	
	if (polylines) {  map.removeOverlay(polylines); }	
	points.length = 0;
	
	for (var i = 0; i < markers.length; i++) {
		latlng = markers[i].getLatLng();
		points.push(latlng);
	//	pointsDiv.innerHTML += "(" + Math.round(latlng.lat() * 100000)/100000 +
	//						   "," + Math.round(latlng.lng() * 100000)/100000 + ")<br />";
	}
	polylines = new GPolyline(points, lineColor, lineWeight, lineOpacity);
	map.addOverlay(polylines);
	
	// Round and convert distance to miles
	//distance = Math.round(polylines.getLength() * 0.00062137119 * 1000)/1000;
	//distanceDiv.innerHTML = "Distance: " + distance + unit;
}
