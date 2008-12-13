

var doVirtualTrip, initVirtualTrip, prepareVT, clearVT;
var vtPanel, waitPanel;

var svPoints = new Array();
var pano;
var svOverlay;
var svIdx = 0;
var blueicon = new GIcon();
var redicon = new GIcon();
var manicon = new GIcon();
var panoClient;
var ptPerPath = 5; // sv points per segment
var bStreetviewOn = false;
var svUnit = 0.00036;
var angles = new Array();
var csvIdx = 0;
var man = null;
var timeUnit = 200;
var timer;

YAHOO.util.Event.onDOMReady(initVirtualTrip);
YAHOO.util.Event.addListener("virtualTripBtn", "click", doVirtualTrip);	
YAHOO.util.Event.addListener("vtPrepareBtn", "click", prepareVT);	

function initVirtualTrip()
{
	vtPanel = new YAHOO.widget.Panel("VTpanel", 
				{ width:"400px", 
				  visible:true, 
				  draggable:true, 
				  fixedcenter : true,
				  constraintoviewport:true,
				  modal:true,
				  visible:false,
				  effect:{effect:YAHOO.widget.ContainerEffect.FADE,duration:0.25}
				   } );
	
	vtPanel.hideEvent.subscribe(clearVT);
				   
	vtPanel.render();
	
	// Disable buttons
	vtEnableBtns(false);

	waitPanel = new YAHOO.widget.Panel("wait",
				{ //width:"240px", 
				  fixedcenter:true, 
				  close:false, 
				  draggable:false, 
				  zindex:4,
//				  modal:true,
				  visible:false
				} 
			);
	
	waitPanel.setHeader("Loading, please wait...");
	waitPanel.setBody('<img src="http://us.i1.yimg.com/us.yimg.com/i/us/per/gr/gp/rel_interstitial_loading.gif" />');

	// show YAHOO logger
	var myConfig = {
			width: "500px",
			top: "500px",
			verboseOutput: false,
			footerEnabled: false
	};
//	var myLogReader = new YAHOO.widget.LogReader(null, myConfig);


	// icons for sv points		
	blueicon.image = "./assets/blue.png"; 
	blueicon.iconSize = new GSize(12, 12);
	blueicon.iconAnchor = new GPoint(6, 6);
 
	redicon.image = "./assets/red.png"; 
	redicon.iconSize = new GSize(12, 12);
	redicon.iconAnchor = new GPoint(6, 6);
 
	manicon.image = "./assets/man.png"; 
	manicon.iconSize = new GSize(21, 28);
	manicon.iconAnchor = new GPoint(10, 14);
		
	// create SV client
	panoClient = new GStreetviewClient();

}

function clearVT()
{
	// clear intermediate points
	for(i = 0 ; i < svPoints.length ; i++) {
		map.removeOverlay(svPoints[i]); 
	}
	 
	svPoints.length = 0;
	angles.length = 0;
		
	if(man != null) map.removeOverlay(man);
	
	document.getElementById('svArea').innerHTML = "<br /><br /><br /><br /><br /><br /><br /><br />" +
	      "<input id=\"vtPrepareBtn\" type=\"button\" value=\"Prepare Virtual Trip\" onclick=\"vtPrepare()\" />";
// The code below has a layout problem when reopening...
//	document.getElementById('svArea').innerHTML = "<br /><br /><br /><br /><br /><br /><br /><br />" +
//	"<a class=\"button\" id=\"vtPrepareBtn\"><span>Click to Prepare Virtual Trip</span></a>";
	
	vtEnableBtns(false);
	
	if(timer)
	{
		clearInterval(timer);
	}
	
	svIdx = csvIdx = 0;
	
}


function doVirtualTrip()
{

	var dist = document.getElementById("dist");
	
	try
	{
		var distance = parseFloat(dist.innerHTML.substring(1, dist.innerHTML.length-7));
		//alert(distance)
		if( distance > 20 ) {
			alert("Sorry. Virtual trip is only supported for the route less than 20 miles");
			return;
		}	
	}
	catch(x)
	{
		alert("Couldn't get the distance. Try reload the route.")
		return;
	}

	vtPanel.show();

}

function vtEnableBtns(bEnabled)
{
	btns = YAHOO.util.Dom.getChildren("svControllers");
	
	for(i = 0 ; i < btns.length ; i++)
	{
		btns[i].disabled = !bEnabled;
	}
}

function vtPrepare()
{

	document.getElementById('svArea').innerHTML = "";
	waitPanel.render('svArea'); 

	// Show 'loading..'
	waitPanel.show();

	// Draw streetview
	drawSVOverlay();

}


/*
 * Draws StreetView points
 */
function drawSVOverlay() {
 
 // clear intermediate points
 for(i = 0 ; i < svPoints.length ; i++) {
 	map.removeOverlay(svPoints[i]); 
 }
 svPoints.length = 0;
 angles.length = 0; // clear angle
 
 // if there is only one marker, no need to draw sv points.
 if( markers.length < 2 ) return;
 YAHOO.log("creating sv points");
  
 YAHOO.log(markers.length + " markers on the map");
  for(i = 0 ; i < markers.length - 1 ; i++) {
 
	var p1 = markers[i].getLatLng();
	var p2 = markers[i+1].getLatLng();
	
	var x1 = p1.lat();
	var x2 = p2.lat();
	var y1 = p1.lng();
	var y2 = p2.lng();
	var xDiff = x2 - x1;
	var yDiff = y2 - y1;
	
	var angle = Math.atan2(xDiff, yDiff);
	var angle_degree = angle * 180 / Math.PI;
//	YAHOO.log("angle = " + angle_degree);
	
	var k = 0;
	while(true) {
		pt3_lat = x1 + k*svUnit*Math.sin(angle);
		pt3_lng = eval(y1) + k*svUnit*Math.cos(angle);
		if((x2 >= x1 && pt3_lat > x2) || (x2 <= x1 && pt3_lat < x2)) {
			break;
		}
//		YAHOO.log("Point : " + pt3_lat + "," + pt3_lng);
		var tMarker = new GMarker(new GLatLng(pt3_lat, pt3_lng), {icon:redicon})
		svPoints.push(tMarker);
		angles.push(angle_degree);
		k++;
	}
		
  }
 
	YAHOO.log( svPoints.length + " sv points are added");
 
    // if sv points were added, check whether streetviews are available for those points
	if(svPoints.length > 1 ) {
		svIdx = 0;
		panoClient.getNearestPanorama(svPoints[0].getLatLng(), addIntMarker);
	}
	
}

function addIntMarker(panoData)
{
	if( panoData.code != 200 ) {
	// delete marker
	// Shorten array of markers and adjust counter
	// svPoints.splice(svIdx, 1);
//	  YAHOO.log("No SV data : " + svIdx);
	}
	else {
		svPoints[svIdx] = new GMarker(panoData.location.latlng, {icon:blueicon});
	}
 
	svIdx++;
	
	if(svIdx < svPoints.length) {
//		sleep(10);
		// retrieve next marker
		panoClient.getNearestPanorama(svPoints[svIdx].getLatLng(), addIntMarker);
	}
	else {
		// If every sv points were retrieved
		waitPanel.hide();
		
		// Enable buttons
		vtEnableBtns(true);
		
		
		for(i = 0 ; i < svPoints.length ; i++) {
			if( svPoints[i].getIcon() == redicon ) {
//				YAHOO.log("Ignoring sv points : " + i);
			}
			map.addOverlay(svPoints[i]);
		}
		
		// set street view
		var cyaw = 90 - angles[0];
//		YAHOO.log("yaw : " + cyaw);
	
		if(man != null) map.removeOverlay(man);
		mLatLng = new GLatLng(svPoints[csvIdx].getLatLng().lat(), svPoints[csvIdx].getLatLng().lng() + 0.0000001);
		man =  new GMarker(mLatLng, {icon:manicon});
		map.addOverlay(man);
 
		pano = new GStreetviewPanorama(document.getElementById("svArea"), 
			{ latlng:svPoints[0].getLatLng(),
			   pov : {yaw: cyaw}
			});
 
		
	}
}

function next()
{
	if( csvIdx < svPoints.length -1 ) {
		csvIdx++;
		updateStreetView();
		return true;
	}
	
	return false;
 
}
 
function prev()
{
	if( csvIdx > 0 ) {
		csvIdx--;
		updateStreetView();
		return true;
	}
	
	return false;
}
 
function goStart()
{
	csvIdx = 0;
	updateStreetView();
}
 
function goEnd()
{
	csvIdx = svPoints.length - 1;
	updateStreetView();
}
 
function updateStreetView()
{
//		alert(csvIdx);
		if(man != null) map.removeOverlay(man);
		mLatLng = new GLatLng(svPoints[csvIdx].getLatLng().lat(), svPoints[csvIdx].getLatLng().lng() + 0.0000001);
		man =  new GMarker(mLatLng, {icon:manicon});
		map.addOverlay(man);
 
		var cyaw = 90 - angles[csvIdx];
//		YAHOO.log("yaw : " + cyaw);
		pano.setLocationAndPOV(svPoints[csvIdx].getLatLng(), {yaw: cyaw});	
}
 
function vtStart()
{
	timer = window.setInterval(vtProcess, document.getElementById('tbxSpeed').value * timeUnit);
	YAHOO.util.Dom.get("vtStartBtn").disabled = true;
	YAHOO.util.Dom.get("vtStopBtn").disabled = false;
}
 
function vtProcess()
{
	if(!next()) vtPause();
}
 
function vtPause()
{
	clearInterval(timer);
	YAHOO.util.Dom.get("vtStartBtn").disabled = false;
	YAHOO.util.Dom.get("vtStopBtn").disabled = true;
}
 
function vtChangeTimer()
{
//	vtPause();
//	vtStart();
}


function enableSV() {
	if(bStreetviewOn) {
		map.removeOverlay(svOverlay);
		document.getElementById('btnToggleSV').value = "StreetView On";
	}
	else {
		map.addOverlay(svOverlay);
		document.getElementById('btnToggleSV').value = "StreetView Off";
	}
	
	bStreetviewOn = !bStreetviewOn;
}



