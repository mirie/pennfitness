<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>PennFitness 2nd example</title>
<link rel="stylesheet" type="text/css" href="css/accordion-menu-v2.css" />
<style type="text/css">
<!--
.Header {
	height: 30px;
	vertical-align: middle;
	cursor: move;
	font-family:Arial, Helvetica, sans-serif;
	text-align: center;	
	line-height: 30px;
}

.Toolbar {
	background-color:#FFFFFF;
	border: 1px solid #000000;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size:12px;
}

#Routes {
	position:absolute;
	left:16px;
	top:49px;
	width:171px;
	z-index:1;
}
#RHeader {
	background-color:#00FF00;
}

#NewRoute {
	position:absolute;
	left:659px;
	top:55px;
	width:174px;
	z-index:2;
}
#NRHeader {
	background-color: #00FFFF;
}
#NewRoute #PointsHeader {
	background-color: #00FFFF;
}
#map {
	position:absolute;
	left:5%;
	top:5%;
	width:90%;
	height:90%;
	z-index:0;
}

-->
</style>
<link href="http://yui.yahooapis.com/2.6.0/build/reset/reset-min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="http://yui.yahooapis.com/2.6.0/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.6.0/build/element/element-beta-min.js"></script> 
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAa4hhebteU8gfF26s-F8yRxRi_j0U6kJrkFvY4-OX2XYmEAa76BSap98EWKTMZCV7YM0hBbWJRb-A5g" type="text/javascript"></script>

<!-- Dependencies --> 
<script type="text/javascript" src="http://yui.yahooapis.com/2.6.0/build/utilities/utilities.js" ></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.6.0/build/slider/slider-min.js" ></script>

<!-- Color Picker source files for CSS and JavaScript -->
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.6.0/build/colorpicker/assets/skins/sam/colorpicker.css">
<script type="text/javascript" src="http://yui.yahooapis.com/2.6.0/build/colorpicker/colorpicker-min.js" ></script>

<!-- Accordion libraries--> 
<script type="text/javascript" src="http://us.js2.yimg.com/us.js.yimg.com/lib/common/utils/2/yahoo_2.0.0-b2.js"></script>
<script type="text/javascript" src="http://us.js2.yimg.com/us.js.yimg.com/lib/common/utils/2/event_2.0.0-b2.js" ></script>
<script type="text/javascript" src="http://us.js2.yimg.com/us.js.yimg.com/lib/common/utils/2/dom_2.0.2-b3.js"></script>
<script type="text/javascript" src="http://us.js2.yimg.com/us.js.yimg.com/lib/common/utils/2/animation_2.0.0-b3.js"></script>
<script type="text/javascript" src="js/accordion-menu-v2.js"></script>


<!-- Drag and Drop source file --> 
<script type="text/javascript" src="http://yui.yahooapis.com/2.6.0/build/yahoo-dom-event/yahoo-dom-event.js" ></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.6.0/build/dragdrop/dragdrop-min.js" ></script>

<!-- Paginator-->
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.6.0/build/paginator/assets/skins/sam/paginator.css">
<script type="text/javascript" src="http://yui.yahooapis.com/2.6.0/build/element/element-min.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.6.0/build/paginator/paginator-min.js"></script>

<script type="text/javascript">

// Global variables
var map;
var poly;
var count = 0;
var points = new Array();
var markers = new Array();

var markersMultiple = new Array();
var pointsMultiple = new Array();

var lineColor = "#0000af";
var lineWeight = 3;
var lineOpacity = .8;
var fillOpacity = .2;
var distance;

var registeredRoutes = new Array();

var multiple = false;

</script>

<script type="text/javascript">

// Our custom drag and drop implementation, extending YAHOO.util.DD
YAHOO.example.DDOnTop = function(id, sGroup, config) {
    YAHOO.example.DDOnTop.superclass.constructor.apply(this, arguments);
};

YAHOO.extend(YAHOO.example.DDOnTop, YAHOO.util.DD, {
    startDrag: function(x, y) {
        YAHOO.log(this.id + " startDrag", "info", "example");

        var style = this.getEl().style;
        // The z-index needs to be set very high so the element will indeed be on top
        style.zIndex = 999;
    }
});

</script>


<script type="text/javascript">

// Google map related

function init()
{

	// Load Google map
	if (GBrowserIsCompatible()) {
		map = new GMap2(document.getElementById("map"), {draggableCursor:"auto", draggingCursor:"move"});
		map.setCenter(new GLatLng(39.95005, -75.191674), 15);
		map.addControl(new GScaleControl());
		map.disableDoubleClickZoom();
		
		GEvent.addListener(map, "click", leftClick);
	}
	
	var dd1 = new YAHOO.example.DDOnTop("Routes");
	dd1.setHandleElId("RHeader");
	var dd2 = new YAHOO.example.DDOnTop("NewRoute");
	dd2.setHandleElId("NRHeader");
	
	distance = document.getElementById("DistanceDiv");
	
	YAHOO.pf.route.getRouteNames();
}

function addMarker(point)
{
  // Make markers draggable
  var marker = new GMarker(point, {draggable:true, bouncy:false});
  map.addOverlay(marker);
  marker.content = count;
  markers.push(marker);
  marker.tooltip = "Point "+ count;

  // Drag listener
  GEvent.addListener(marker, "drag", function() {
   drawOverlay();
  });

  // Second click listener
  GEvent.addListener(marker, "click", function() {
	  // Find out which marker to remove
	  for(var n = 0; n < markers.length; n++) {
	   if(markers[n] == marker) {
		map.removeOverlay(markers[n]);
		break;
	   }
	  }
	
	  // Shorten array of markers and adjust counter
	  markers.splice(n, 1);
	  if(markers.length == 0) {
		count = 0;
	  }
	   else {
		count = markers[markers.length-1].content;
	  }
      drawOverlay();
  }); // end of second click
}

function addMarker2(lat, lng)
{
	addMarker(new GLatLng(lat, lng));
}



function leftClick(overlay, point) {

 if(point) {
  count++;

  addMarker(point);

 drawOverlay();
 }
}

function drawOverlay(){


 //if(poly) { map.removeOverlay(poly); }
 //points.length = 0;

 
 var pointDiv = document.getElementById("PointsDiv");
 pointDiv.innerHTML = "";
 var temp, strLatLng;

 for(i = 0; i < markers.length; i++) {
    points.push(markers[i].getLatLng());
	temp = markers[i].getLatLng();
	strLatLng = "(" + parseInt(temp.lat()*100000)/100000 + "," + parseInt(temp.lng()*100000)/100000 + ")";
	pointDiv.innerHTML += (i+1) + ". " + strLatLng + "<br>";
	
 }
  poly = new GPolyline(points, lineColor, lineWeight, lineOpacity);
  map.addOverlay(poly);

  var length = poly.getLength()/1000;
  var unit = " km";

  distance.innerHTML = "Distance : " + length.toFixed(3) + unit;



}



</script>

<script type="text/javascript">
YAHOO.namespace("pf.route");

YAHOO.pf.route.submitForm = function() {
	
	var successHandler = function(o) {
		// alert(o.responseText);
		if(o.responseText == '1') {
			alert("Route saved successfully");
			YAHOO.pf.route.getRouteNames();
		}
		else {
			alert("Route is not saved");
		}
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000
	}
	
	var form = document.getElementById("frmNewRoute");

	// clear previous pvalues	
	while((pvalue = document.getElementById("pvalue")) != null)
	{
		form.removeChild(pvalue);
	}

	var newPoint;
	for(i = 0 ; i < markers.length ;i++) {

		newPoint = document.createElement("input");
		newPoint.type = "hidden";
		newPoint.name = "pvalue";
		newPoint.id = "pvalue";
		newPoint.value = markers[i].getLatLng().lat() + "," + markers[i].getLatLng().lng();
	
		form.appendChild(newPoint);			
	}
	
	YAHOO.util.Connect.setForm(form);
	
	var transaction = YAHOO.util.Connect.asyncRequest("POST", "view/allRoutes.jsp", callback);
}

YAHOO.util.Event.addListener("Save", "click", YAHOO.pf.route.submitForm);


YAHOO.pf.route.getRouteNames = function() {


	var successHandler = function(o) {
		strs = o.responseText.split(";");
		rlist = document.getElementById("RoutesList");
		rlist.innerHTML = "";
		for(i = 0 ; i < strs.length ; i++) {
			rlist.innerHTML += (i+1) + ". <input type=checkbox name=c"+i+" id=c"+i+"> <a href=\"javascript:YAHOO.pf.route.getRoute('" + strs[i] + "')\">" + strs[i] + "</a><br>";
			registeredRoutes.push(strs[i]);
		}
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
		timeout:3000
	}
	
	var form = document.getElementById("frmRoutes");

	var transaction = YAHOO.util.Connect.asyncRequest("GET", "view/allRoutes.jsp", callback);
}

YAHOO.util.Event.addListener("Refresh", "click", YAHOO.pf.route.getRouteNames);


YAHOO.pf.route.getMultipleRouteNames = function(){

    var str = "";
	
	for( i = 0 ; i < registeredRoutes.length; i++){
		if( document.getElementById("c"+i).checked == true ){ 
			YAHOO.pf.route.getRouteMultiple(registeredRoutes[i]);
		}
		else{
			str += "0";
		}
	}
	
}

YAHOO.util.Event.addListener("Multiple", "click", YAHOO.pf.route.getMultipleRouteNames);


function drawOverlayMultiple(){

 var pointDiv = document.getElementById("PointsDiv");
 pointDiv.innerHTML = "";
 var temp, strLatLng;

 var tmpPoints = new Array();
 
 for(i = 0; i < markersMultiple.length; i++) {
	for(j=0; j < markersMultiple.length; j++){
		tmpPoints.push(markers[i][j].getLatLng());
		temp = markers[i][j].getLatLng();
		strLatLng = "(" + parseInt(temp.lat()*100000)/100000 + "," + parseInt(temp.lng()*100000)/100000 + ")";
		pointDiv.innerHTML += (i+1) + ". " + strLatLng + "<br>";
	}
	pointsMultiple.push(tmpPoints);
	tmpPoints.length = 0;
 }
 
 for( k=0; k < pointsMultiple.length; k++ ){
  polyM = new GPolyline(pointsMultiple[k], lineColor, lineWeight, lineOpacity);
  map.addOverlay(polyM);
 }
  var length = poly.getLength()/1000;
  var unit = " km";

  distance.innerHTML = "Distance : " + length.toFixed(3) + unit;



}
YAHOO.pf.route.getRouteMultiple = function(route) {
	var successHandler = function(o) {
// 		alert(o.responseText);
		strs = o.responseText.split(";");
		
		alert(strs);
		document.getElementById("routeName").value = strs[0];
		document.getElementById("routeColor").value = strs[1];
		

	  var tmpArray = new Array();
	  for(i=2;i<strs.length;i++) {
	    if(strs[i] == "") continue;
	    lat = strs[i].split(",")[0];
		lng = strs[i].split(",")[1];
	    addMarker(new GLatLng(lat, lng));
 	  }

	  drawOverlay();
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
		timeout:3000
	}
	
	var transaction = YAHOO.util.Connect.asyncRequest("GET", "view/routeByName.jsp?routeName=" + route, callback);
}



YAHOO.pf.route.getRoute = function(route) {
	var successHandler = function(o) {
// 		alert(o.responseText);
		strs = o.responseText.split(";");
		
		alert(strs);
		document.getElementById("routeName").value = strs[0];
		document.getElementById("routeColor").value = strs[1];
		
		 //clear markers
		for(var n = 0; n < markers.length; n++) {
			map.removeOverlay(markers[n]);
		}
		markers.length = 0;
	  //add markers
	  for(i=2;i<strs.length;i++) {
	    if(strs[i] == "") continue;
	    lat = strs[i].split(",")[0];
		lng = strs[i].split(",")[1];
	    addMarker2(lat, lng);
 	  }

	  drawOverlay();
	}
	
	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
	}
	
	var callback = {
		success:successHandler,
		failure:failureHandler,
		timeout:3000
	}
	
	var transaction = YAHOO.util.Connect.asyncRequest("GET", "view/routeByName.jsp?routeName=" + route, callback);
}

YAHOO.pf.route.clear = function(route) {

	map.removeOverlay();

      // clear markers
	  for(var n = 0; n < markers.length; n++) {
	   map.removeOverlay(markers[n]);
	  }
	  markers.length = 0;

	document.getElementById("routeName").value = "";
	document.getElementById("routeColor").value = "";

	if(poly) { map.removeOverlay(poly); }
    points.length = 0;
	
  drawOverlay();


}

YAHOO.util.Event.addListener("Clear", "click", YAHOO.pf.route.clear);


var dd1 = new YAHOO.util.DD("my-dl");

</script>


</head>

<body class="yui-skin-sam" onLoad="init();" >
<div class="Toolbar" id="Routes">
    <form name="frmRoutes" method="post" action="">
  <div class="Header" id="RHeader">Registered Routes</div>
  <div id="RoutesList">
  </div>
  <div align="center">
    <p>
	  <input name="Multiple" type="button" id="Multiple" value="Get Multiple Routes">
      <input name="Refresh" type="button" id="Refresh" value="Refresh">
    </p>
  </div>
  </form>
</div>
</div>

<dl class="accordion-menu" id="my-dl"> 
	
	
	<dt class="a-m-t" id="my-dt-1">New Routes </dt>
	<dd class="a-m-d">
	<div class="bd">
		<div><b>1)</b> Election path</br></div>
		<div><b>2)</b> Art museum</br></div>
		<div><b>3)</b> Downtown3</br></div>
		<div><b>4)</b> Fairmount</br></div>
		<div><b>5)</b> Penn's Landing area</br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
	</div>
	</dd>
	
	
	
	<dt class="a-m-t" id="my-dt-2"> Favourite Routes </dt>
	<dd class="a-m-d">
	<div class="bd">
		<div><b>1)</b> Around campus</br></div>
		<div><b>2)</b> After midterm run</br></div>
		<div><b>3)</b> Quakers jog</br></div>
		<div><b>4)</b> Downtown walk</br></div>
		<div><b>5)</b> Fairmount run</br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
	</div>
	</dd>
	
	
	
	<dt class="a-m-t" id="my-dt-3"> Upcoming Events</dt>
	<dd class="a-m-d">
	<div class="bd">
		<div><b>1)</b> 9am this weekend</br></div>
		<div><b>2)</b> Around Campus</br></div>
		<div><b>3)</b> Jogging1</br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
	</div>
	</dd>
	
	
	
	<dt class="a-m-t" id="my-dt-4"> New Events </dt>
	<dd class="a-m-d">
	<div class="bd">
		<div><b>1)</b> Dec Jogging</br></div>
		<div><b>2)</b> Outdoor1</br></div>
		<div><b>3)</b> West Philly</br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
		<div></br></div>
	</div>
	</dd>
	
</dl>



<div class="Toolbar" id="NewRoute">
  <form name="frmNewRoute" id="frmNewRoute" method="post" action="#">
  <div class="Header" id="NRHeader">Routes</div>
  <div>
      <p>Name 
        <input name="routeName" type="text" id="routeName" size="10" maxlength="30">
        <br>
      Color # 
      <input name="routeColor" type="text" id="routeColor" size="6" maxlength="6">
  </p>
      <p><div id="DistanceDiv">Distance : </div></p>
  </div>
  <div align="center" id="PointsHeader">Points</div>
  <div id="PointsDiv"></div>
    <div>
      <p align="center">
        <input name="Save" type="button" id="Save" value="Save">
      </p>
      <p align="center">&nbsp;</p>
      <p align="center">
        <input name="Clear" type="button" id="Clear" value="Clear">
      </p>
    </div>
    </form>
</div>
<div id="map">Map area</div>
</body>
</html>