<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="entities.User, model.DBUtilEventType, model.DBUtilRoute, model.DBUtilEvent" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="stylesheet" type="text/css" href="css/styles_user.css" />
<link rel="stylesheet" type="text/css" href="css/basic.css" />
<link rel="stylesheet" type="text/css" href="css/toolbarStyle.css" />
 
<!-- =============================== Google Map Scripts =============================== -->
<%-- Change Google Map key --%>
<% if( request.getLocalAddr().equals("158.130.6.223") ) { %>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAiUy24FJ2pI4aK2GRl7uYJRRyJaKxKEBa7ZCCRoWpGBgWzNSuoBTM3814vdtXsPWQBBJcr7o22Ufqfw" 
        type="text/javascript"></script>
<% } else { %>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAwNRsCttn_9209vEUNVvtyxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxQkSsxzavUfI4qYgHd_KyuAMbdCzQ" 
        type="text/javascript"></script>
<% } %>


<!-- Combo-handled YUI CSS files: -->
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/combo?2.6.0/build/reset-fonts-grids/reset-fonts-grids.css&2.6.0/build/assets/skins/sam/skin.css&2.6.0/build/logger/assets/skins/sam/logger.css">
<!-- Combo-handled YUI JS files: -->
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.6.0/build/utilities/utilities.js&2.6.0/build/container/container-min.js&2.6.0/build/menu/menu-min.js&2.6.0/build/button/button-min.js&2.6.0/build/calendar/calendar-min.js&2.6.0/build/slider/slider-min.js&2.6.0/build/colorpicker/colorpicker-min.js&2.6.0/build/json/json-min.js&2.6.0/build/layout/layout-min.js&2.6.0/build/paginator/paginator-min.js&2.6.0/build/tabview/tabview-min.js&2.6.0/build/logger/logger-min.js"></script>

<!--  ================================= Custom CSS to override YUI CSS style ========== -->
<link rel="stylesheet" type="text/css" href="css/calendar.css" />
<link rel="stylesheet" type="text/css" href="css/styles_overlay.css" />

<!--  ================================= Custom Scripts ================================ -->
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/layout.js"></script>
<script type="text/javascript" src="js/map.js"></script>
<script type="text/javascript" src="js/floatingToolbar.js"></script>
<script type="text/javascript" src="js/leftToolbar.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/search.js"></script>
<script type="text/javascript" src="js/myaccount.js"></script>
<script type="text/javascript" src="js/accordion-menu-v2.js"></script>
<script type="text/javascript" src="js/virtualtrip.js"></script>


<title>Penn Fitness</title>

</head>

<body onunload="cleanUpMyAccountInformation()">

<body class=" yui-skin-sam"> 
<div id="header">
	<div class="archHeader">
        <table class="top">
            <tr>
            <td class="im"><a class="image" href="http://spam.seas.upenn.edu/home/index.htm">
            <img src="assets/spamlogo.png" alt="SPAM Home"/></a>
            </td>
          
            <td class="appname" align="center" valign="middle">Penn Fitness</td>
              
            <td align=right valign=top class="links"> 
            <a class="link" onmouseover="this.className='link2'" onmouseout="this.className='link'" href="http://spam.seas.upenn.edu:8088/Fitness/"> Penn Fitness </a> | 
            <a class="link" onmouseover="this.className='link2'" onmouseout="this.className='link'" href="http://spam.seas.upenn.edu:8088/PennShare"> Penn CISRS </a> | 
            <a class="link" onmouseover="this.className='link2'" onmouseout="this.className='link'" href="http://spam.seas.upenn.edu/~appdev3/"> Penn Chingu </a> | 
            <a class="link" onmouseover="this.className='link2'" onmouseout="this.className='link'" href="http://spam.seas.upenn.edu/home/index.htm"> Older SPAM </a> 
            </td>
            </tr>            
        </table>
    </div>
	
	<div id="topMenuBar">
    <!-- Login input or user information -->
            <span id="user">
                <%
                User user=(User) session.getAttribute("user");
                if(user == null) {
                    // if not logged in
                %>
                username: <input type="text" name="userID" id="userID" size=10 maxlength="20" /> 
                password: <input type="password" name="password" id="password" size=10 maxlength="41" /> 
                <a href="javascript:Login()"/>Login</a>
				<a href="javascript:ShowUserRegDialog()"/>Sign Up!</a>
                <%
                } // end of if(user == null) 
                else {
                    // if logged in
                %>
                Welcome <%=user.getUserName() %> (<%=user.getUserID()%>)!
                <% out.println("<a href=\"javascript:ShowMyAccountDialog('"+user.getUserID()+"')\"/>My Account</a>"); %>
                <a href="javascript:createRt()"/>Create a New Route</a>
				<a href="javascript:Logout()"/>Logout</a>
                <% // end of else
                }
                %>
            </span>
            <span id="showbtns">
            	<a href="javascript:ShowSearchDialog()"/>Search</a>
            </span>
	</div>
</div>

      <div id="archFooter" class="archFooter">
        <table class="bottom" >
        
        <tr align="center">
        
        <td class="spacing" align="left">Copyright (c) System for Penn Active Maps</td>
        <td></td><td></td>
        <td align = "right">
        <a class="ft" onmouseover="this.className='ft2'" onmouseout="this.className='ft'" href="http://spam.seas.upenn.edu/about">About</a> | 
        <a class="ft" onmouseover="this.className='ft2'" onmouseout="this.className='ft'" href="http://www.upenn.edu/privacy/">Privacy</a> | 
        <a class="ft" onmouseover="this.className='ft2'" onmouseout="this.className='ft'" href="http://www.upenn.edu/about/disclaimer.php">Disclaimer</a> | 
        <a class="ft" onmouseover="this.className='ft2'" onmouseout="this.className='ft'" href="http://www.upenn.edu/">Penn Home</a> | 
        <a class="ft" onmouseover="this.className='ft2'" onmouseout="this.className='ft'" href="http://www.cis.upenn.edu/">CIS Home</a>
        </td>
              
        </tr>
        </table>
      </div>    


<div id="centerArea">
	<div id="leftMenu" class="leftMenuTitle">
        <div id="leftEventCalendar"></div>
        <input type="hidden" id="eventDatesCurrentMonth" value="<%= DBUtilEvent.getEventDatesForCurMonth() %>" />
        <dl class="accordion-menu" id="my-dl">
            <dt class="a-m-t" id="my-dt-1">Events</dt>
            <input type="hidden" id="totalEventCnt" value=<%= DBUtilEvent.getAllEventsCount() %> />
            <dd class="a-m-d">
                <div class="bd" id="evtListSection">
                    <div id="eventListTabArea" class="yui-navset">
                    	<ul class="yui-nav">
                        	<li class="selected"><a href="#dateEvtListTab"><em>By Date</em></a></li>
                            <li><a href="#newEvtListTab"><em>New</em></a></li>
                        </ul>
                        <div class="yui-content">
	                        <div id="dateEvtListTab">
	                        	<div id="eventsOnDateList">
	                        		<!-- Shows events on selected date -->
	                        	</div>
    	                    	<div id="pag_eventsOnDateList" class="paginator">
    	                    		<!-- paginator -->
    	                    	</div
	                        </div>
    	                    <div id="newEvtListTab">
    	                    	<div id="newEventsList">
    	                    		<%= DBUtilEvent.getAllEventsHTML(5,1) %>
    	                    	</div>
    	                    	<div id="pag_newEventsList" class="paginator">
    	                    		<!-- paginator -->
    	                    	</div
    	                    </div>
                        </div>
                	</div>
                </div>
            </dd>  
            <dt class="a-m-t" id="my-dt-2">Routes</dt>
			<input type="hidden" id="totalRouteCnt" value=<%= DBUtilRoute.getAllRoutesCount() %> />
            <dd class="a-m-d">
                <div class="bd" id="rtListSection">
                    <div id="routeListTabArea" class="yui-navset">
	                    <ul class="yui-nav">
       	                   	<li><a href="#popularRtListTab"><em>Popular</em></a></li>
                            <li class="selected"><a href="#newRtListTab"><em>New</em></a></li>
                        </ul>
                        <div class="yui-content">
	                        <div id="popularRtListTab">
	                        	<div id="popularRoutesList">
									<%= DBUtilRoute.getPopularRoutesHTML(5, 1) %>
	                        	</div>
	    	                    <div id="pag_popularRoutesList" class="paginator">
									<!-- paginator -->
			                	</div>
	                        </div>
    	                    <div id="newRtListTab">
								<div id="newRoutesList">
									<%= DBUtilRoute.getAllRoutesHTML(5, 1) %>
								</div>
	    	                    <div id="pag_newRoutesList" class="paginator">
									<!-- paginator -->
			                	</div>
    	                    </div>
                        </div>
                    </div>
              	</div>
            </dd>
	    </dl>    
    </div>
	<div id="map">map area</div>
</div> 

           <!-- ======================== Floating TOOLBAR:  ===================== -->

    <div id="floatingToolbar">
    
    <!-- ======================== Route Detail TOOLBAR:  ===================== -->
        <div class="hd">Route Details</div>
        <div class="bd">
            <form name="frmRouteDetails" id="frmRouteDetails" method="post" action="#">
                <div class="generalInfo" id="routeGeneral">
                    <span id="routeName" class="nameDisplay">Mai's Route</span> <span id="routeCreator">by need from server</span><br />
                </div>
                <div id="routeNameTxtDiv">
                	<label for="routeNameTxt" class="nameDisplay">Route Name: </label>
                    <input type="text" name="routeName" id="routeNameTxt" maxlength="30" />
                </div>
                <div id="routeDateDistance"><span id="Rtdate">date</span> <span id="dist">distance</span></div>
                <div id="routeDistance">(1.5 miles)</div>
                <div class="ratings" id="rtRatings">         
                <a class="button" id="rateRouteBtn"><span>Rate!</span></a>  
                    	<span id="overallRating"></span><br /></br />
                    	<span id="sceneryRating"></span><br />
                    	<span id="difficultyRating"></span><br />
                    	<span id="safetyRating"></span><br />                    	
                </div>
                <div id="routeDesc">
                    Route Description here...	  		        
                </div>
                <div id="routeDescLabel">Route Description:</div>
                <textarea name="routeDesc" id="routeDescTxt" wrap="virtual"></textarea>
                <div id="rtColor-container" class="nameDisplay">Route Color: </div>
                <div class="toolbarBtns">
                    <div id="saveRoute">
                    	<a class="button" id="cancelRouteBtn"><span>Cancel</span></a>
                    	<a class="button" id="saveRouteBtn"><span>Save</span></a>
                    </div>
                    <div id="modifyRoute">
                    	
                    	<a class="button" id="deleteRouteBtn"><span>Delete</span></a>
                    	<a class="button" id="modifyRouteBtn"><span>Modify</span></a>
                    	<a class="button" id="virtualTripBtn"><span>Virtual Trip</span></a>                   
                 	</div>
                </div>
            </form>
            	<div class="eventdiv">
                	<span id="totalEvents">0 Events</span>
                	<a href="javascript:createEvt()" id="newEventLink" />Create New Event</a>
                </div>
                 				
        
    <!-- ======================== Event Detail TOOLBAR:  ===================== --> 
                   
            <div id="eventDetails" style="display:none">
            	<hr />
            	 <div id="hideEvtList"><a href="javascript:hideEventList()">Hide Event List</a></div>
                 <div class="eventHd">Event List</div>
                 <div class="toolbarPages" id="eventList">
                 	<input type="hidden" id="ELBRrecsPerPage" name="recsPerPage" />
					<input type="hidden" id="ELBRcurPage" name="curPage" value="1" />
                 	<div id="eventListByRoute" class="eventList"></div>
					<div id="pag_eventsListByRoute" class="paginator">
						<!-- paginator -->
					</div>
                </div>
            </div>   
                <div id="specificEvent" style="display:none">
                	<hr />
                	 <div id="hideEvtDetail" ><a href="javascript:displayEventList()">Back to List</a></span><br /></div>
	                 <div class="generalInfo" id="eventGeneral">
	                 	<div class="eventHd" style="color:navy;">Event Details</div>          	
	                    <span id="eventName">Event Name</span> <span id="eventCreator">by Mai</span><br />
	                    <span id="eventCreatedDate">Created on: 11/30/2008</span><br />
	                </div>
	                <div id="evtContainer">
		                <div class="eventDetails">
		                    <span id="eventStart">1:00 PM</span><span id="eventDuration">(1 hour)</span><br />
		                    <span id="eventType">Casual Run</span> <span id="eventPrivacy">Public</span> <span id="eventGroup">Group 1</span>
		                </div>
		                <div class="description" id="eventDesc">
		                    Event Description here...
		                </div>
	                </div>
	                <div class="toolbarBtns">
                    	<a class="button" id="deleteEventBtn"><span>Delete</span></a>
                    	<a class="button" id="modifyEventBtn"><span>Modify</span></a>
                    	<a class="button" id="registerEventBtn"><span>Register!</span></a>
	                
	                    <!-- <input type="button" id="modifyEventBtn" value="Modify" />
	                    <input type="button" id="deleteEventBtn" value="Delete" />
	                    <input type="button" id="registerEventBtn" value="Register" />  -->	                    
	                </div>
                </div>                 
                            
	    </div>
	    <div class="ft">&nbsp;</div>
    </div>

    <!-- =================== Event Create/Modify Dialog:  =================== -->

    <div id="eventDialog">
        <div class="hd">Edit Event Details</div>
        <div class="bd">
            <form name="frmCreateEvent" id="frmCreateEvent" method="post" action="mgEvent.do">
                <label for="eventNameTxt">Event Name:</label> <input type="text" name="eventName" id="eventNameTxt" size="30" maxlength="30" /><br />                                

				<label for="publicity">Publicity: </label>
                <input type="radio" name="publicity" id="publicEvt" value="Y" checked="checked"/> Public Event
                <input type="radio" name="publicity" id="privateEvt" value="N" /> Private Event <br />                
                <label for="evtGroup">Group Name: </label> <select id="evtGroup" name="groupID">
                    <option value="-1" selected="selected">None</option>
                </select><br />
                 <label for="evtType">Event Type: </label><select id="evtType" name="eventTypeID">
                    <option value="-1" selected="selected">Please select a type</option>
                    <%= DBUtilEventType.getAllEventTypesOptions() %>
                </select><br />                
                <label for="eventTimeStart">Event Start Time:</label> <select id="eventTimeStart" name="eventTimeStart" ></select>
                <select name="AM_PM" id="AM_PM">
                	<option value="AM" selected="selected">AM</option>
                	<option value="PM">PM</option>
                </select><br />
                <label for="eventDurationTxt">Duration: </label> 
                <input type="text" name="eventDuration" id="eventDurationTxt" size="10" maxlength="30" /> (in hours - e.g., 1.5)<br />
                <div id="date">
                    <label for="evtCalTxt">Event Date: </label> <input type="text" name="evtCalTxt" id="evtCalTxt"  size="10" maxlength="10" onclick="this.value=''"/>
                </div> <div id="buttoncalendar"></div>          
                <label for="eventDescTxt">Description: </label> 
                <textarea id="eventDescTxt" name="eventDesc" wrap="virtual"></textarea><br />
                <input type="hidden" name="eventID" id="eventIDTxt" value="-1"/>
				<input type="hidden" name="routeID" id="routeIDTxt" value="-1"/>
                <input type="hidden" name="eventTime" id="evtStartTxt" value="" />
                <input type="hidden" name="eventDate" id="evtDateTxt" value="" />			
            </form>
        </div>
    </div>        

    <div id="rateDialog">
        <div class="hd">Rate This Route!</div>
        <div class="bd">
			<form name="frmRateRoute" id="frmRateRoute" method="post" action="rateRoute.do">
				<input type="hidden" name="routeID" id="rateRouteID" value="-1"/>
				<p>Please rate this route on a scale of 0 - 5. 0 is the worst rating, and 5 is the best.</p>
				<p>&nbsp;</p>
				<table>
					<tr>
						<td>&nbsp;</td><th>0</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th>
					</tr>
					<tr>
						<th>Overall:</th>
                		<td><input type="radio" name="rate" id="defaultOverall" value="0" checked="checked" /></td>
                		<td><input type="radio" name="rate" value="1" /></td>
                		<td><input type="radio" name="rate" value="2" /></td>
                		<td><input type="radio" name="rate" value="3" /></td>
                		<td><input type="radio" name="rate" value="4" /></td>
                		<td><input type="radio" name="rate" value="5" /></td>
					</tr>
					<tr>
						<th>Scenery:</th>
                		<td><input type="radio" name="scenery" id="defaultScenery" value="0" checked="checked" /></td>
                		<td><input type="radio" name="scenery" value="1" /></td>
                		<td><input type="radio" name="scenery" value="2" /></td>
                		<td><input type="radio" name="scenery" value="3" /></td>
                		<td><input type="radio" name="scenery" value="4" /></td>
                		<td><input type="radio" name="scenery" value="5" /></td>                		
					</tr>
					<tr>
					<th>Difficulty:</th>
                		<td><input type="radio" name="difficulty" value="0" id="defaultDifficulty" checked="checked" /></td>
                		<td><input type="radio" name="difficulty" value="1" /></td>
                		<td><input type="radio" name="difficulty" value="2" /></td>
                		<td><input type="radio" name="difficulty" value="3" /></td>
                		<td><input type="radio" name="difficulty" value="4" /></td>
                		<td><input type="radio" name="difficulty" value="5" /></td>                		
					</tr>
					<tr>
						<th>Safety:</th>
                		<td><input type="radio" name="safety" value="0" id="defaultSafety" checked="checked" /></td>
                		<td><input type="radio" name="safety" value="1" /></td>
                		<td><input type="radio" name="safety" value="2" /></td>
                		<td><input type="radio" name="safety" value="3" /></td>
                		<td><input type="radio" name="safety" value="4" /></td>
                		<td><input type="radio" name="safety" value="5" /></td>                		
					</tr>					
				</table>
			</form>
		</div>
	</div>


<!-- =============================== User Registration DIALOG Structure:  =============================== -->
    <div id="userRegDialog">
        <div class="hd">New User Registration</div>
        <div class="bd">
            <form id="frmRegistration" method="POST" action="registerUser.do">
                <label for="userName">Name:</label><input type="text" name="userName" maxlength="20"/>
                <label for="userID">User ID:</label><input type="text" name="userID"  maxlength="20"/>
                <label for="password">Password:</label> <input type="password" name="password" maxlength=20"/>                    
                <label for="passwordConfirm">Confirm Password:</label> <input type="password" name="passwordConfirm" maxlength="20"/>
                <label for="email">E-mail:</label><input type="text" name="email" maxlength="40"/> 
    
                <div class="clear"></div>
    
                <label for="publicNotify">Notified for public events:</label> <input type="checkbox" name="publicNotify" value="Y" checked/>
    
                <div class="clear"></div>
    
                <label for="height">Height:</label> <input type="text" name="height" maxlength="5"/>
                <label for="weight">Weight:</label> <input type="text" name="weight" maxlength="5"/>
                    <div class="clear"></div>
                <label for="gender">Gender:</label>
                <input id="nGender" type="radio" name="gender" value="N" checked/> N/A
                <input type="radio" name="gender" value="M" /> Male
                <input type="radio" name="gender" value="F" /> Female    
            </form>
        </div>
    </div>
    
    <!-- =============================== My Account DIALOG Structure:  =============================== -->
    <div id="panel1">
        <div id = "overlayTabOne" class="overlayOne" style="background-color:#003;">
            <ul class = "yui-nav">
                <li class = "selected">
                    <a href = "#tab1"><em>Personal Information</em></a>
                </li>
                <li><a href = "#tab2" onclick="populateMyRegisteredGroupByUserID();"><em>Group Information</em></a>
                </li>
                <li><a href = "#tab3" onclick="populateRegisteredEventByUserID()"><em>Event Information</em></a>
                </li>
                <li><a href = "#tab4" onclick="populateRouteByUserID();"><em>Route Information</em></a>
                </li>
            </ul>
            <div class = "yui-content" style="background-color:#003; color:#FFFE02 ">
                <div>
                    <div id = "personalInfo">

                    </div>
                </div>		
                <div>
                        <div id = "GroupInfoTab" class="GroupInfo">
                            <ul class = "yui-nav">
                                <li class = "selected">
                                    <a href = "#tab1" onclick="populateMyRegisteredGroupByUserID();"><em>My Groups</em></a>
                                </li>
                                <li>
                                	<a href = "#tab2" onclick="populateMyCreatedGroupByUserID();"><em>Created Groups</em></a>
                                </li>
                                <li>
                                	<a href = "#tab3"><em>Create a Group</em></a>
                                </li>
                            </ul>
								<div class = "yui-content" style="background-color:#003; color:#FFFE02 ">
	                                <div class = "myGroups">
	                                 	<form name="frmGroupRegisteredUnsubscribe" id="frmGroupRegisteredUnsubscribe">                         
	                                    <input type="hidden" name="action" value="leaveGroup">
	                                   	 	<div id="myRegisteredGroupList">
	                                   	 		<!-- my group(s) detail list --> 
	                                    	</div>
	                                    	<div id="pag_myGroups" class="paginator">
	                            				<!-- paginator -->
	                        				</div>           
	            							<input type="button" id="unsubscribeGroupBtn" value="Unsubscribe"/>
	                                    </form>                                    				
                                </div>
                                <div class = "createdGroups">
                                    <form name="frmGroupSendEmail" id="frmGroupSendEmail">
                                    <input type="hidden" name="action" value="sendEmailToGroup">
                                    <div id="myCreatedGroupList">
                                    </div>
                                    <div id="pag_myCreatedGroupList" class="paginator">
                            			<!-- paginator -->
			                        </div>           
										Title  : <input type="text" size="10" maxlength="40" name="emailTitle" id="emailTitle"> <br />
										Message: <textarea rows="5" cols="20" wrap="physical" name="emailText" id="emailbody"></textarea>

										<input type="button" id="sendEmailToGroupBtn" value="Send Email to group"> 
										<input type="button" id="deleteGroupBtn" value="Delete group"> 
									</form>

                                </div>
                                <div class = "createAGroup">
                                        <form name="frmCreateGroupData" id="frmCreateGroupData">
                                        Name <input type="text" name="groupName" size="10" maxlength=30 id="groupName" /><br>
                                        Description <input type="text" name="groupDesc" size="30" maxlength=300 id="groupDescription" /><br>
                                        <input type="hidden" name="action" value="register" />
                                        <input type="button" name="Create" value="Create" id="createGroup" /><br>
                                        </form>
                                </div>

                            </div>
                        </div>

                </div>
                <div>
                    <div id ="EventInfoTab" class="EventInfo" >
                        <ul class = "yui-nav">
                            <li class = "selected">
                                <a href = "#tab1" onclick="populateRegisteredEventByUserID()"><em>Registered Events</em></a>
                            </li>
                            <li><a href = "#tab2" onclick="populateCreatedEventByUserID()"><em>Created Events</em></a>
                            </li>
                        </ul>
                        <div class = "yui-content" style="background-color:#003; color:#FFFE02; height:350px ">
                        		<div id="EVTregistered">
	                                <div id="myRegisteredEventList">
										 <!--my registered event(s) detail list --> 
	                                </div>
		                            <div id="pag_EVTregistered" class="paginator">
		                            	<!-- paginator -->
		                            </div>
                                </div>
                                <div id="EVTcreated">
	                                <div id="myCreatedEventList">
	                      				<!--my registered event(s) detail list --> 
	                      			</div>
	                                <div id="pag_EVTcreated" class="paginator">
		                                	<!-- paginator -->
	                                </div>
                                </div>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="RouteInfoList">
                        <div id="myRouteList">
                            <!--my route(s) detail list --> 
                        </div>
                        <div id="pag_myRouteList" class="paginator">
                            	<!-- paginator -->
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
            <div class = "yui-content" style="background-color:#003; color:#FFFE02 ">
                <div class = "eventSearch">
                    <form name="frmEventSearchData" id="frmEventSearchData">
                    	Keyword :<input type="text" size=10 name="keyword" id="ESKeyword" />&nbsp;&nbsp;
                    	<label for="evtType">Event Type:</label><select id="ESevtType" name="type">
                    	<option value="" selected="selected">All types</option>
                  		  <%= DBUtilEventType.getAllEventTypesOptions() %>
                		</select><br />
                    	Date :<input type="text" size=10 maxlength=10 name="fromDate" id="ESFromDate" />
                    		  <input type="text" size=10 maxlength=10 name="toDate" id="ESToDate" />
						<input type="button" value="Search" onclick="searchEvent()">
						<input type="hidden" id="ESrecsPerPage" name="recsPerPage" />
						<input type="hidden" id="EScurPage" name="curPage" value="1" />
                    </form>
                    <br />
                    <div id="eventSearchResult" class="Result">
                    	<!-- group search result -->
                    </div>
	                <div id="pag_eventSearchResult" class="paginator">
						<!-- paginator -->
	                </div>
                </div>

                <div>
                    <div class = "groupSearch">
                        <form name="frmGroupSearchData" id="frmGroupSearchData">
	                    	Keyword :<input type="text" size=10 name="keyword" id="GSKeyword" />&nbsp;&nbsp;
	                    	Creator ID :<input type="text" size=10 name="creatorID" id="GSCreator" />
							<input type="button" value="Search" onclick="searchGroup()" />
							<input type="hidden" id="GSrecsPerPage" name="recsPerPage" />
							<input type="hidden" id="GScurPage" name="curPage" value="1" />
                        </form>
	                    <br />
                        <div id="groupSearchResult" class="Result">
                        	<!-- group search result -->
                        </div>
		                <div id="pag_groupSearchResult" class="paginator">
							<!-- paginator -->
		                </div>
                    </div>
                </div>
                <div>
                    <div class = "routeSearch">	
                        <form name="frmRouteSearchData" id="frmRouteSearchData">
	                    	Keyword :<input type="text" size=10 name="keyword" id="RSKeyword" />&nbsp;&nbsp;
	                    	Distance :<input type="text" size=3 maxlength=3 name="fromDistance" id="RSFromDistance" />~
	                    			  <input type="text" size=3 maxlength=3 name="toDistance" id="RSToDistance" /><br />
	                    	Date :<input type="text" size=10 maxlength=10 name="fromDate" id="RSFromDate" />
	                    		  <input type="text" size=10 maxlength=10 name="toDate" id="RSToDate" />
							<input type="button" value="Search" onclick="searchRoute()" />
							<input type="hidden" id="RSrecsPerPage" name="recsPerPage"  />
							<input type="hidden" id="RScurPage" name="curPage" value="1" />
	                    <br />
                        </form>
                        <div id="routeSearchResult" class = "Result">
                        	<!-- route search result -->
                        </div>
		                <div id="pag_routeSearchResult" class="paginator">
							<!-- paginator -->
		                </div>
                    </div>
                </div>
            </div>
        </div>
    </div> <!-- End of search dialog -->

    <!-- =============================== Virtual Trip DIALOG Structure:  =============================== -->	
	<div id="VTpanel">
	    <div class="hd">Virtual Trip</div> 
	    <div id="VTbd" class="bd"> 
	      <div id="svArea">
	      	<br /><br /><br /><br /><br /><br /><br /><br />
	      	<input id="vtPrepareBtn" type="button" value="Prepare Virtual Trip" onclick="vtPrepare()" />
	      </div> 
	      <div id="svControllers"> 
	        <input type="button" id="vtStartBtn" value="START" onclick="vtStart()" /> 
	        &nbsp;
	        <input type="button" id="vtStopBtn" value="STOP"  onclick="vtPause()"/> 
	        &nbsp;
	        SPEED
	        <input type="text" value="5" size="3" maxlength="3" id="tbxSpeed" onchange="vtChangeTimer()"/> 
	        &nbsp;&nbsp;
	        <input type="button" value="&lt;&lt;" onclick="goStart()"/> 
	        &nbsp;
	        <input type="button" value=" &lt; " onclick="prev()"/> 
	        &nbsp;
	        <input type="button" value=" &gt; " onclick="next()"/> 
	        &nbsp;
	        <input type="button" value="&gt;&gt;" onclick="goEnd()"/> 
	        <br /> 
	        <input type="button" value="StreetView On" id="btnToggleSV" onclick="enableSV();"/> 
	        &nbsp; 
	      </div> 
	    </div> 
	</div> 
</body>
</html>
