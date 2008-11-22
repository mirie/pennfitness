<%@ page language="java" import="entities.User" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
							"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>Penn Fitness Map</title>

<!-- =============================== CSS files =============================== -->
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link rel="stylesheet" type="text/css" href="css/styles_user.css" />
<link rel="stylesheet" type="text/css" href="css/styles_overlay.css" />

<!-- =============================== Google Map Scripts =============================== -->
<%-- Change Google Map key --%>
<% if( request.getLocalAddr().equals("158.130.6.223") ) { %>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAiUy24FJ2pI4aK2GRl7uYJRRyJaKxKEBa7ZCCRoWpGBgWzNSuoBTM3814vdtXsPWQBBJcr7o22Ufqfw" 
        type="text/javascript"></script>
<% } else { %>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAwNRsCttn_9209vEUNVvtyxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxQkSsxzavUfI4qYgHd_KyuAMbdCzQ" 
        type="text/javascript"></script>
<% } %>
<!--  =============================== Yahoo YUI Scripts =============================== -->

<!-- Combo-handled YUI CSS files: -->
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/combo?2.6.0/build/reset-fonts-grids/reset-fonts-grids.css&2.6.0/build/base/base-min.css&2.6.0/build/assets/skins/sam/skin.css">
<!-- Combo-handled YUI JS files: -->
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.6.0/build/utilities/utilities.js&2.6.0/build/container/container-min.js&2.6.0/build/menu/menu-min.js&2.6.0/build/button/button-min.js&2.6.0/build/calendar/calendar-min.js&2.6.0/build/slider/slider-min.js&2.6.0/build/colorpicker/colorpicker-min.js&2.6.0/build/cookie/cookie-min.js&2.6.0/build/json/json-min.js&2.6.0/build/paginator/paginator-min.js&2.6.0/build/tabview/tabview-min.js"></script>


<!--  ================================= Custom Scripts ================================ -->
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/map.js"></script>
<script type="text/javascript" src="js/routeToolbar.js"></script>
<script type="text/javascript" src="js/accordion-menu-v2.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/leftToolbar.js"></script>
<script type="text/javascript" src="js/search.js"></script>

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
<input type="button" name="userRegDialogBtn" id="userRegDialogBtn" value="Sign up" onclick="ShowUserRegDialog()"/>
<%
} // end of if(user == null) 
else {
	// if logged in
%>
Welcome <%=user.getUserName() %> (<%=user.getUserID()%>)!
<input type="button" name="myAccountDialogBtn" id="myAccountDialogBtn" value="My Account" onclick="ShowMyAccountDialog()"/>
<input type="button" name="logout" id="logoutBtn" value="Logout" onclick="Logout()"/>
<% // end of else
}
%>
	</span>
	<span id="showbtns">
		<input type="button" name="searchDialogBtn" id="searchDialogBtn" value="Search" onclick="ShowSearchDialog()"/>
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

		<dt class="a-m-t" id="my-dt-2"> Favorite Routes </dt>
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
			<select id="publicity" name="publicity">
				<option value="Y selected="selected">Public Event</option>
				<option value="N">Private Event</option>
			</select><br />
			Group Name: <select id="evtGroup" name="groupName"><option value="-1" selected="selected">None</option></select><br />
			<!-- <input type="hidden" name="groupID" id="groupID" value="-1" /> --> <!-- this group pulldown is populated based on user id -->
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

<!-- =============================== User Registration DIALOG Structure:  =============================== -->
<div id="userRegDialog">
	<div class="hd">New User Registration</div>
	<div class="bd">
		<form method="POST" action="">
			<label for="reg_userName">Name:</label><input type="textbox" name="reg_userName" />
			<label for="reg_userID">User ID:</label><input type="textbox" name="reg_userID" />
			<label for="reg_password">Password:</label><input type="password" name="reg_password" />
			<label for="reg_email">E-mail:</label><input type="textbox" name="reg_email" /> 

			<div class="clear"></div>

			<label for="reg_publicNotify">Notified for public events:</label><input type="checkbox" name="reg_publicNotify" value="Y" />

			<div class="clear"></div>

			<label for="reg_height">Height:</label><input type="textbox" name="reg_height" />
			<label for="reg_weight">Weight:</label><input type="textbox" name="reg_weight" />
				<div class="clear"></div>
			<label for="reg_gender">Gender:</label>
			<input type="radio" name="reg_gender[]" value="N" checked/> N/A
			<input type="radio" name="reg_gender[]" value="M"/> Male
			<input type="radio" name="reg_gender[]" value="F" /> Female

		</form>
	</div>
</div>

<!-- =============================== My Account DIALOG Structure:  =============================== -->
	<div id="panel1">
		<div id = "overlayTabOne" class="overlayOne">
			<ul class = "yui-nav">
				<li class = "selected">
					<a href = "#tab1"><em>Personal Information</em></a>
				</li>
				<li><a href = "#tab2"><em>Group Information</em></a>
				</li>
				<li><a href = "#tab3"><em>Event Information</em></a>
				</li>
				<li><a href = "#tab4"><em>Route Information</em></a>
				</li>
			</ul>
			<div class = "yui-content">
				<div>
					<div class = "personalInfo">
						<p>Name:
						<span class="userName">Sandy Qian Liu</span><br>
						Gender:<span class="userGender">Female</span><br>
						Hobbies:<span class="userHobbies">Jogging, Cooking, Japanese Anime</span><br>
						</p>
					</div>
				</div>		
				<div>
						<div id = "GroupInfoTab" class="GroupInfo">
							<ul class = "yui-nav">
								<li class = "selected">
									<a href = "#tab1"><em>My Groups</em></a>
								</li>
								<li><a href = "#tab2"><em>Created Groups</em></a>
								</li>
								<li><a href = "#tab3"><em>Create a Group</em></a>
								</li>
							</ul>
							<div class = "yui-content">
								<div class = "myGroups">
									<div class="myGroupList">
										<div class="myGroupItem">
											<span class="number">1.</span>
									  		<a href="function_to_show_group_detail">jogging group 1</a>
									  		<span class="createdDate">since Nov. 1st, 2008</span>
									  		<span class="createdBy">by lq</span>
									  		<span class="memberCount"># 10</span>
									  		<p>This is an awesome group where members meet every Wednesday night in front of Towne to start a one hour jogging 
												trip</p>
									 	</div>
									 	<div class="myGroupItem">
									 		<span class="number">2.</span>
									  		<a href="function_to_show_group_detail">crazy buddies</a>
									  		<span class="createdDate">since May. 25th, 2009</span>
									  		<span class="createdBy">by Mai</span>
									  		<span class="memberCount"># 7</span>
									  		<a href="function_to_show_group_detail">more...</a>
									 	</div>
										<div class="myGroupItem">
									 		<span class="number">3.</span>
									  		<a href="function_to_show_group_detail">stress out run</a>
									  		<span class="createdDate">since Jan. 15th, 2009</span>
									  		<span class="createdBy">by Inseob</span>
									  		<span class="memberCount"># 13</span>
									  		<a href="function_to_show_group_detail">more...</a>
									 	</div>
									</div>
									<div class="myGroupsButtons">
										<input type="button" value="Send Emails" id="SendEmails">
										<input type="button" value="Unsubscribe" id="Unsubscribe">
									</div>					
								</div>
								<div class = "createdGroups">
									<div class="createdGroupList">
										<div class="createdGroupItem">
											<span class="number">1.</span>
									  		<a href="function_to_show_group_detail">my Created Group 1</a>
									  		<span class="createdDate">since Nov. 1st, 2008</span>
									  		<span class="memberCount"># 5</span>
									  		<p>This is an awesome group where members meet every Wednesday night in front of Towne to start a one hour jogging 
												trip</p>
									 	</div>
									 	<div class="createdGroupItem">
									 		<span class="number">2.</span>
									  		<a href="function_to_show_group_detail">my Created Group 2</a>
									  		<span class="createdDate">since May. 25th, 2009</span>
									  		<span class="memberCount">#8</span>
									  		<a href="function_to_show_group_detail">more...</a>
									 	</div>
										<div class="myGroupItem">
									 		<span class="number">3.</span>
									  		<a href="function_to_show_group_detail">my Created Group 3</a>
									  		<span class="createdDate">since Jan. 15th, 2009</span>
									  		<span class="memberCount"># 15</span>
									  		<a href="function_to_show_group_detail">more...</a>
									 	</div>
									</div>
									<div id="buttons">
										<input type="button" value="Send Emails" id="SendEmails">
										<input type="button" value="Modify" id="Modify">
									</div>

								</div>
								<div class = "createAGroup">
										<form name="frmCreateGroupData" id="frmCreateGroupData">
										Name <input type="text" name="Group Name" size="10" id="groupName"><br>
										Description <input type="text" name="Description" size="30" id="Description"><br>
										<input type="button" name="Create" value="Create" id="Create"><br>
										</form>
								</div>

							</div>
						</div>

				</div>
				<div>
					<div id ="EventInfoTab" class="EventInfo">
						<ul class = "yui-nav">
							<li class = "selected">
								<a href = "#tab1"><em>Registered Events</em></a>
							</li>
							<li><a href = "#tab2"><em>Created Events</em></a>
							</li>
						</ul>
						<div class = "yui-content">
							<div class = "registeredEvents">
								<div class="registeredEventList">
									<div class="registeredEventItem">
										<a href="function_to_show_event">Inseob Event 007</a>
									</div>
									<div class="RouteInfoItem">
										<a href="function_to_show_event">Girls go jogging</a>
									</div>
									<div class="RouteInfoItem">
										<a href="function_to_show_event">Sansom E. Halloween jogging</a>
									</div>
								</div>
							</div>
							<div class = "createdEvents">
								<div class="createdEventList">
									<div class="createdEventItem">
										<a href="function_to_show_event">Sandy Event 1</a>
									</div>
									<div class="createdEventItem">
										<a href="function_to_show_event">Sandy Event 2</a>
									</div>
									<div class="createdEventItem">
										<a href="function_to_show_event">Sandy Event 3</a>
									</div>
									<div class="createdEventItem">
										<a href="function_to_show_event">Sandy Event 4</a>
									</div>
									<div class="createdEventItem">
										<a href="function_to_show_event">Sandy Event 5</a>
									</div>
									<div class="createdEventItem">
										<a href="function_to_show_event">Sandy Event 6</a>
									</div>
									<div class="createdEventItem">
										<a href="function_to_show_event">Sandy Event 7</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div>
					<div class="RouteInfoList">
						<div class="RouteInfoItem">
							<a href="function_to_show_route">Difficult Route 001</a>
						</div>
						<div class="RouteInfoItem">
							<a href="function_to_show_route">Coolest Route Ever</a>
						</div>
						<div class="RouteInfoItem">
							<a href="function_to_show_route">Sweet Path in Philly</a>
						</div>
					</div>


				</div>
			</div>
		</div>
		
	</div>
	
<!-- =============================== Search DIALOG Structure:  =============================== -->	
	<div id="panel2">
		<div id = "overlayTabTwo" class="overlayTwo">
			<ul class = "yui-nav">
				<li class = "selected">
					<a href = "#tab1"><em>Event Search</em></a>
				</li>
				<li><a href = "#tab2"><em>Group Search</em></a>
				</li>
				<li><a href = "#tab3"><em>Route Search</em></a>
				</li>
			</ul>
			<div class = "yui-content">
				<div>
					<div class = "eventSearch">
						<form name="frmGroupSearchData" id="frmGroupSearchData">
						Key Word <input type="text" name="Key Word" size="10" id="keyWord"><br>
					 	Type <input type="text" name="Type" size="10" id="Type"><br>
						<div>
							Date
							<label>from: </label>
							<input type = "text" name = "dobfield" id = "dobfield">
							<img id = "calico" src = "calendar_icon.jpg"
									alt = "Open the Calendar control">
						</div>
						<div id = "eventcalFrom"></div>
						<div>
							<label>to: </label>
							<input type = "text" name = "dobfield" id = "dobfield">
							<img id = "calico" src = "calendar_icon.jpg"
									alt = "Open the Calendar control">
						</div>
						<div id = "eventcalTo"></div>
						<input type="submit" value="Search"/>
						</form>
						<div class = "Result">
							<div class="EventInfoList">
								<div class="registeredEventItem">
									<a href="function_to_show_event">Inseob Event 007</a>
								</div>
								<div class="RouteInfoItem">
									<a href="function_to_show_event">Girls go jogging</a>
								</div>
								<div class="RouteInfoItem">
									<a href="function_to_show_event">Sansom E. Halloween jogging</a>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div>
					<div class = "groupSearch">
						<form name="frmGroupSearchData" id="frmGroupSearchData">
						Key Word <input type="text" name="Key Word" size="10" id="keyWord"><br>
						Member <input type="text" name="Member" size="10" id="Member"><br>
						<input type="submit" value="Search"/>
						</form>
						<div class = "Result">
							<div class = "myGroups">
								<div class="myGroupList">
									<div class="myGroupItem">
										<span class="number">1.</span>
								  		<a href="function_to_show_group_detail">jogging group 1</a>
								  		<span class="createdDate">since Nov. 1st, 2008</span>
								  		<span class="createdBy">by lq</span>
								  		<span class="memberCount"># 10</span>
								  		<p>This is an awesome group where members meet every Wednesday night in front of Towne to start a one hour jogging 
											trip</p>
								 	</div>
								 	<div class="myGroupItem">
								 		<span class="number">2.</span>
								  		<a href="function_to_show_group_detail">crazy buddies</a>
								  		<span class="createdDate">since May. 25th, 2009</span>
								  		<span class="createdBy">by Mai</span>
								  		<span class="memberCount"># 7</span>
								  		<a href="function_to_show_group_detail">more...</a>
								 	</div>
									<div class="myGroupItem">
								 		<span class="number">3.</span>
								  		<a href="function_to_show_group_detail">stress out run</a>
								  		<span class="createdDate">since Jan. 15th, 2009</span>
								  		<span class="createdBy">by Inseob</span>
								  		<span class="memberCount"># 13</span>
								  		<a href="function_to_show_group_detail">more...</a>
								 	</div>
								</div>					
							</div>
						</div>
					</div>
				</div>
				<div>
					<div class = "routeSearch">	
						<form name="frmRouteSearchData" id="frmRouteSearchData">
						Key Word <input type="text" name="Key Word" size="10" id="keyWord"><br>
						Distance <input type="text" name="Distance" size="10" id="Distance"><br>
						<div>
							Date
							<label>from: </label>
							<input type = "text" name = "dobfield" id = "dobfield">
							<img id = "calico" src = "calendar_icon.jpg"
									alt = "Open the Calendar control">
						</div>
						<div id = "mycalFrom"></div>
						<div>
							<label>to: </label>
							<input type = "text" name = "dobfield" id = "dobfield">
							<img id = "calico" src = "calendar_icon.jpg"
									alt = "Open the Calendar control">
						</div>
						<div id = "mycalTo"></div>
						<input type="submit" value="Search"/>
						</form>
						<div class = "Result">
							<div class="RouteInfoList">
								<div class="RouteInfoItem">
									<a href="function_to_show_route">Difficult Route 001</a>
								</div>
								<div class="RouteInfoItem">
									<a href="function_to_show_route">Coolest Route Ever</a>
								</div>
								<div class="RouteInfoItem">
									<a href="function_to_show_route">Sweet Path in Philly</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


<div id="map">mapArea</div>
</div> <!-- End of body div tag -->
<!--<div id="ft">This is the footer area defined by Architecture</div> -->
</div>
</html>
</body>