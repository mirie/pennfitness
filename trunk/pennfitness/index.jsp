<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="entities.User" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="stylesheet" type="text/css" href="css/styles_user.css" />
<link rel="stylesheet" type="text/css" href="css/styles_overlay.css" />
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
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/combo?2.6.0/build/reset-fonts-grids/reset-fonts-grids.css&2.6.0/build/assets/skins/sam/skin.css">
<!-- Combo-handled YUI JS files: -->
<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.6.0/build/utilities/utilities.js&2.6.0/build/container/container-min.js&2.6.0/build/menu/menu-min.js&2.6.0/build/button/button-min.js&2.6.0/build/calendar/calendar-min.js&2.6.0/build/slider/slider-min.js&2.6.0/build/colorpicker/colorpicker-min.js&2.6.0/build/json/json-min.js&2.6.0/build/layout/layout-min.js&2.6.0/build/paginator/paginator-min.js&2.6.0/build/tabview/tabview-min.js"></script>

<!--  ================================= Custom Scripts ================================ -->
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/layout.js"></script>
<script type="text/javascript" src="js/map.js"></script>
<script type="text/javascript" src="js/floatingToolbar.js"></script>
<script type="text/javascript" src="js/leftToolbar.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/search.js"></script>
<script type="text/javascript" src="js/accordion-menu-v2.js"></script>


<title>Penn Fitness</title>

</head>

<body>

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
            <a class="link" onmouseover="this.className='link2'" onmouseout="this.className='link'" href="http://spam.seas.upenn.edu/home/index.htm"> Penn Fitness </a> | 
            <a class="link" onmouseover="this.className='link2'" onmouseout="this.className='link'" href="http://spam.seas.upenn.edu/home/index.htm"> Penn CISRS </a> | 
            <a class="link" onmouseover="this.className='link2'" onmouseout="this.className='link'" href="http://spam.seas.upenn.edu/home/index.htm"> Penn Chingu </a> | 
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
                username: <input type="text" name="userID" id="userID" size=5 maxlength="10" style="width:5em"/> 
                password: <input type="password" name="password" id="password" size="10" maxlength="10" style="width:5em"/> 
                <a href="javascript:Login()"/>Login</a>
				<a href="javascript:ShowUserRegDialog()"/>Sign Up!</a>
                <%
                } // end of if(user == null) 
                else {
                    // if logged in
                %>
                Welcome <%=user.getUserName() %> (<%=user.getUserID()%>)!
   				<a href="javascript:ShowMyAccountDialog()"/>My Account</a>
                <a href="javascript:createRt()"/>Create a New Route</a>
				<a href="javascript:Logout()"/>Logout</a>
                <% // end of else
                }
                %>
            </span>
            <span id="showbtns">
            	<a href="javascript:ShowSearchDialog()"/>Search</a>
                <!--<input type="button" name="searchDialogBtn" id="searchDialogBtn" value="Search" onclick="ShowSearchDialog()"/>
                 <input type="button" name="temp" id="temp" value="Show New" onclick="showNewRtTool()"/>
                <input type="button" name="temp2" id="temp2" value="Hide New" onclick="hideNewRtTool()"/> -->
            </span>
	</div>
</div>

      <div id="archFooter" class="archFooter">
        <table class="bottom" >
        
        <tr align="center">
        
        <td class="spacing" align="left">Copyright © System for Penn Active Maps</td>
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
        <dl class="accordion-menu" id="my-dl">
            <dt class="a-m-t" id="my-dt-1">Events</dt>
            <dd class="a-m-d">
                <div class="bd" id="evtListSection">
                    <div id="eventListTabArea" class="yui-navset">
                    	<ul class="yui-nav">
                        	<li class="selected"><a href="#dateEvtListTab"><em>By Date</em></a></li>
                            <li><a href="#newEvtListTab"><em>New</em></a></li>
                        </ul>
                        <div class="yui-content">
	                        <div id="dateEvtListTab"><p>Events Listed By Date Here</p></div>
    	                    <div id="newEvtListTab"><p>Events (new) listed by Creation Date Here</p></div>
                        </div>
                	</div>
                </div>
            </dd>  
            <dt class="a-m-t" id="my-dt-2">Routes</dt>
            <dd class="a-m-d">
                <div class="bd" id="rtListSection">
                    <div id="routeListTabArea" class="yui-navset">
	                    <ul class="yui-nav">
       	                   	<li><a href="#popularRtListTab"><em>Popular</em></a></li>
                            <li class="selected"><a href="#newRtListTab"><em>New</em></a></li>
                        </ul>
                        <div class="yui-content">
	                        <div id="popularRtListTab"><p>Popular Routes Listed Here</p></div>
    	                    <div id="newRtListTab"><p>New Routes listed here</p></div>
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
                    <span id="routeName">Mai's Route</span> <span id="routeCreator">by need from server</span><br />
                </div>
                <div id="routeNameTxtDiv">
                	<span id="routeNameLabel">Route Name: </span>
                    <input type="text" name="routeName" id="routeNameTxt" maxlength="20" />
                </div>
                <div id="routeTimeDist">
                	<span id="routeCreatedDate">created need date from server</span> <span id="routeDistance">(1.5 miles)</span>
                </div>
                <div class="ratings" id="rtRatings">
                    <span id="overallRating"></span>
                    <span id="sceneryRating"></span>
                    <span id="difficultyRating"></span>
                    <span id="safetyRating"></span>
                </div>
                <div id="routeDesc">
                    Route Description here...	  		        
                </div>
                <textarea name="routeDesc" id="routeDescTxt" wrap="virtual"></textarea>
                <div id="rtColor-container">Route Color: </div>
                <div class="toolbarBtns">
                    <div id="saveRoute">
                        <input type="button" id="saveRouteBtn" value="Save" />
                        <input type="button" id="cancelRouteBtn" value="Cancel" />
                    </div>
                    <div id="modifyRoute">
                        <input type="button" id="modifyRouteBtn" value="Modify" />    
						<input type="button" id="deleteRouteBtn" value="Delete" />                 
                    </div>
                </div>
            </form>
            <div id="routeEvents">
                <span id="totalEvents">0 Events</span> 
                <span id="newEventBtn">New Event</span>
            </div>

        
    <!-- ======================== Event Detail TOOLBAR:  ===================== --> 
                   
            <div id="eventDetails" style="display:none">
                 <h3 class="toolbarHd">Event Details</h3><input type="button" id="closeEventBtn" value="X" />
                 <div class="toolbarPages" id="eventList">
                    Some Event Listing here...
                 </div>
                 <div class="generalInfo" id="eventGeneral">
                    <span id="eventName">Event Name</span> by <span id="eventCreator">Mai</span><br />
                    created <span id="eventCreatedDate">11/30/2008</span><br />
                </div>
                <div class="eventDetails">
                    <span id="eventDuration">12:00AM - 1:00PM (1 hour)</span>
                    <span id="eventPrivacy">Public</span> <span id="eventType">Casual Run</span> <span id="eventGroup">Group 1</span>
                </div>
                <div class="description" id="eventDesc">
                    Event Description here...
                </div>
                <div class="toolbarBtns">
                    <input type="button" id="modifyEventBtn" value="Modify" />
                    <input type="button" id="deleteEventBtn" value="Delete" />
                </div>                 
            </div>                   
	    </div>
    </div>

    <!-- =================== Event Create/Modify Dialog:  =================== -->

    <div id="eventDialog">
        <div class="hd">Edit Event Details</div>
        <div class="hd">
            <form name="frmCreateEvent" id="frmCreateEvent" method="post" action="#">
                <label for="eventName">Event Name:</label> <input type="text" name="eventName" id="eventName" size="10" maxlength="30" /><br />
                <select id="publicity" name="publicity">
                    <option value="Y" selected="selected">Public Event</option>
                    <option value="N">Private Event</option>
                </select><br />
                <label for="evtGroup">Group Name:</label> <select id="evtGroup" name="groupName">
                    <option value="-1" selected="selected">None</option>
                </select><br />
                 <label for="evtGroup">Event Type:</label> <select id="evtType" name="eventTypeID">
                    <option value="-1" selected="selected"></option>
                </select><br />
                <label for="eventName">Event Time:</label> <input type="text" name="eventTime" id="eventTime" size="10" maxlength="30" /><br />
                <label for="eventDuration">Duration:</label> <input type="text" name="eventDuration" id="eventDuration" size="10" maxlength="30" /><br />
                <div id="date">
                    <label for="eventDate">Event Date:</label> <input type="text" name="eventDate" id="eventDate"  size="10" maxlength="10"/>
                </div>
                <label for="eventDesc">Description:</label><br />
                <textarea id="eventDesc" name="eventDesc" rows="2" cols="20"></textarea><br />			
            </form>
        </div>
    </div>        


<!-- =============================== User Registration DIALOG Structure:  =============================== -->
    <div id="userRegDialog">
        <div class="hd">New User Registration</div>
        <div class="bd">
            <form method="POST" action="registerUser.do">
                <label for="userName">Name:</label><input type="text" name="userName" />
                <label for="userID">User ID:</label><input type="text" name="userID" />
                <label for="password">Password:</label> <input type="password" name="password" />                    
                <label for="email">E-mail:</label><input type="text" name="email" /> 
    
                <div class="clear"></div>
    
                <label for="publicNotify">Notified for public events:</label> <input type="checkbox" name="publicNotify" value="Y" checked/>
    
                <div class="clear"></div>
    
                <label for="height">Height:</label> <input type="text" name="height" />
                <label for="weight">Weight:</label> <input type="text" name="weight" />
                    <div class="clear"></div>
                <label for="gender">Gender:</label>
                <input type="radio" name="gender" value="N" /> N/A
                <input type="radio" name="gender" value="M" /> Male
                <input type="radio" name="gender" value="F" /> Female    
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
                                        <input type="button" value="Send Emails" id="SendEmails" />
                                        <input type="button" value="Unsubscribe" id="Unsubscribe" />
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
                                        <input type="button" value="Send Emails" id="SendEmails" />
                                        <input type="button" value="Modify" id="Modify" />
                                    </div>

                                </div>
                                <div class = "createAGroup">
                                        <form name="frmCreateGroupData" id="frmCreateGroupData">
                                        Name <input type="text" name="Group Name" size="10" id="groupName" /><br>
                                        Description <input type="text" name="Description" size="30" id="Description" /><br>
                                        <input type="button" name="Create" value="Create" id="Create" /><br>
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
                        Key Word <input type="text" name="Key Word" size="10" id="keyWord" /><br>
                        Type <input type="text" name="Type" size="10" id="Type" /><br>
                        <div>
                            Date
                            <label>from: </label>
                            <input type = "text" name = "dobfield" id = "dobfield" />
                            <img id = "calico" src = "calendar_icon.jpg" alt = "Open the Calendar control" />
                        </div>
                        <div id = "eventcalFrom"></div>
                        <div>
                            <label>to: </label>
                            <input type = "text" name = "dobfield" id = "dobfield" />
                            <img id = "calico" src = "calendar_icon.jpg" alt = "Open the Calendar control" />
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
                        Key Word <input type="text" name="Key Word" size="10" id="keyWord" /><br>
                        Member <input type="text" name="Member" size="10" id="Member" /><br>
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
                                        <p>This is an awesome group where members meet every Wednesday night in front of Towne to start a one hour jogging trip</p>
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
                        Key Word <input type="text" name="Key Word" size="10" id="keyWord" /><br>
                        Distance <input type="text" name="Distance" size="10" id="Distance" /><br>
                        <div>
                            Date
                            <label>from: </label>
                            <input type = "text" name = "dobfield" id = "dobfield" />
                            <img id = "calico" src = "calendar_icon.jpg" alt = "Open the Calendar control" />
                        </div>
                        <div id = "mycalFrom"></div>
                        <div>
                            <label>to: </label>
                            <input type = "text" name = "dobfield" id = "dobfield" />
                            <img id = "calico" src = "calendar_icon.jpg" alt = "Open the Calendar control" />
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
</body>
</html>
