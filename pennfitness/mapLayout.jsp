<!-- Version: 11/12/08, Last Edited by: Mai Irie -->
<%@ page language="java" import="entities.User" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
							"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>Penn Fitness Map</title>

<!-- =============================== CSS files =============================== -->
<link rel="stylesheet" type="text/css" href="css/styles.css" />

<!-- =============================== Google Map Scripts =============================== -->
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAwNRsCttn_9209vEUNVvtyxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxQkSsxzavUfI4qYgHd_KyuAMbdCzQ" 
        type="text/javascript"></script>

<!--  =============================== Yahoo YUI Scripts =============================== -->

<!-- Combo-handled YUI CSS files: -->
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/combo?2.6.0/build/reset-fonts-grids/reset-fonts-grids.css&2.6.0/build/base/base-min.css&2.6.0/build/assets/skins/sam/skin.css">
<!-- Combo-handled YUI JS files: -->
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.6.0/build/utilities/utilities.js&2.6.0/build/container/container-min.js&2.6.0/build/menu/menu-min.js&2.6.0/build/button/button-min.js&2.6.0/build/calendar/calendar-min.js&2.6.0/build/slider/slider-min.js&2.6.0/build/colorpicker/colorpicker-min.js&2.6.0/build/cookie/cookie-min.js&2.6.0/build/json/json-min.js&2.6.0/build/paginator/paginator-min.js"></script>


<!--  ================================= Custom Scripts ================================ -->
<script type="text/javascript" src="js/map.js"></script>
<script type="text/javascript" src="js/routeToolbar.js"></script>
<script type="text/javascript" src="js/accordion-menu-v2.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/leftToolbar.js"></script>

</head>

<!-- <body class="yui-skin-sam" onload="setupMap()" onunload="GUnload()"> -->
<body class="yui-skin-sam" >
<div id="doc" class="yui=tl">
<div id="hd">This is the header defined by Architecture</div>
<div id="bd">

<!-- =============================== Upper Menu Bar =============================== -->
<div id="upperbar">

<!-- Login input or user information -->
	<span id="user">
<%
User user=(User) session.getAttribute("user");
if(user == null) {
	// if not logged in
%>
ID: <input type="text" name="userID" id="userID" size=5 maxlength="10" style="width:5em"/>
PASS: <input type="password" name="password" id="password" size="10" maxlength="10" style="width:5em"/>
<input type="button" name="login" id="loginBtn" value="Login" onclick="Login()"/>
<%
} // end of if(user == null) 
else {
	// if logged in
%>
Welcome <%=user.getUserName() %> (<%=user.getUserID()%>)!
<input type="button" name="logout" id="logoutBtn" value="Logout" onclick="Logout()"/>
<% // end of else
}
%>
	</span>
	<span id="showbtns">
		<input type="button" name="temp" id="temp" value="Show New" onclick="showNewRtTool()"/>
		<input type="button" name="temp2" id="temp2" value="Hide New" onclick="hideNewRtTool()"/>
	</span>
</div>

<!-- ========================= Route List TOOLBAR Structure: Select New, Favorite Route, etc ============================= -->
	<dl class="accordion-menu" id="my-dl">
		<dt class="a-m-t" id="my-dt-1">New Routes </dt>
		<dd class="a-m-d">
			<div class="bd" id="newRtList">New routes appear here</div>
		</dd>

		<dt class="a-m-t" id="my-dt-2"> Favourite Routes </dt>
		<dd class="a-m-d">
			<div class="bd">
				<div><strong>1)</strong> Around campus</br></div>
				<div><strong>2)</strong> After midterm run</br></div>
				<div><strong>3)</strong> Quakers jog</br></div>
				<div><strong>4)</strong> Downtown walk</br></div>
				<div><strong>5)</strong> Fairmount run</br></div>
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
				<div><strong>1)</strong> 9am this weekend</br></div>
				<div><strong>2)</strong> Around Campus</br></div>
				<div><strong>3)</strong> Jogging1</br></div>
				<div></br></div>
				<div></br></div>
				<div></br></div>
				<div></br></div>
				<div></br></div>
				<div></br></div>
				</br>
			</div>
		</dd>
		
		<dt class="a-m-t" id="my-dt-4"> New Events </dt>
		<dd class="a-m-d">
			<div class="bd">
				<div><strong>1)</strong> Dec Jogging</br></div>
				<div><strong>2)</strong> Outdoor1</br></div>
				<div><strong>3)</strong> West Philly</br></div>
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


<!-- =============================== Initial TOOLBAR Structure: Select or Create New Route =============================== -->

<div id="createRtTool">
<div id="createRt">
	<div class="handle" id="createHandle"></div>
	<div class="bd">
		<p>Please Select a Route or Create a New Route.</p>
		<input type="button" id="newRoute" value="New"/>		
	</div>
	</div>
</div>

<!-- ================================= Route/Event Detail TOOLBAR Structure:  ============================================ -->

<div id="rtDetailTool">
	<div id="rtDetails">
		<div class="handle" id="rtHandle">Route Details <input type="button" id="finishRoute" value="x"/> <!-- Temporary 'close' button --></div>
		<form name="frmRouteDetails" id="frmRouteDetails" method="POST" action="#">
			<input type="hidden" name="routeID" id="routeID" value="-1"/>
			<input type="hidden" name="routeColor" id="routeColor" value="-1"/>
			Route Name: <input type="text" name="routeName" id="routeName" size="10" maxlength="30" /> <div id="stars"></div><br />
			<!-- <div id="rtcreatorID">made by _____</div>--><div id="rtDist">0 miles</div>
			Description:<br />
			<textarea id="routeDesc" name="routeDesc" rows="2" cols="20"></textarea><input type="button" id="magnify" value="?" disabled="disabled"/> <!-- Temporary 'magnify' button --><br />
			<div id="rtColor-container">Route Color: </div>
			<div id="saveRtDiv">
				<input type="button" id="saveRoute" value="Save"/>
				<input type="reset"  id="clearRoute" value="Reset" />
				<input type="button" id="cancelRoute" value="Cancel" />
			</div>
			<div id="modifyRtDiv">
				<input type="button" id="modifyRoute" value="Modify" />				
				<input type="button" id="createEvent" value="Create Event" />				
			</div>
		</form>
	</div>
	<div id="newEvent">
		<strong> Event Details </strong>
		<hr />
		<form name="frmCreateEvent" id="frmCreateEvent" method="POST" action="#">
			<input type="hidden" name="eventID" id="eventID" value="-1" />
			Event Name: <input type="text" name="eventName" id="eventName" size="10" maxlength="30" /><br />
			<!-- <div id="eventcreatorID">made by: userID</div> --> <!-- for the username -->
			<!-- Group Name: <select name="evtGroup"> <option value="None" selected="selected">None</option>
			
			WORKING HERE 
			
			input type="hidden" name="groupID" id="groupID" value="-1" /> <!-- this group pulldown is populated based on user id -->
			Event Time: <input type="text" name="eventTime" id="eventTime" size="10" maxlength="30" /><br />
			Duration: <input type="text" name="eventDuration" id="eventDuration" size="10" maxlength="30" /><br />
			<div id="date">Event Date: <input type="text" name="eventDate" id="eventDate"  size="10" maxlength="10"/></div>
			Description: <br /><textarea id="eventDesc" name="eventDesc" rows="2" cols="20"></textarea><br />			
			<div id="saveEventDiv">
				<input type="button" id="saveEvent" value="Save" />
				<input type="reset"  id="clearEvent" value="Reset" />
				<input type="button" id="cancelEvent" value="Cancel" />
			</div>
			<div id="modifyEventDiv">
				<input type="button" id="modifyEvent" value="Modify" />
				<input type="button" id="finishEvent" value="Finished!"/> <!-- Temporary 'close' button -->
			</div>
		</form>
		</div>
</div>



<div id="map">mapArea</div>
</div> <!-- End of body div tag -->
<!--<div id="ft">This is the footer area defined by Architecture</div> -->
</div>
</html>
</body>