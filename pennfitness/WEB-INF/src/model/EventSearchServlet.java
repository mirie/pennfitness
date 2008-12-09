package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import entities.Event;
import entities.EventType;
import entities.Group;
import entities.Paging;
import entities.Route;
import entities.User;
import util.StringUtil;

public class EventSearchServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {    
		doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {      
    	JSONObject result = new JSONObject();
    	PrintWriter out = resp.getWriter();
    	StringBuffer sbuf = new StringBuffer();
    	
    	String keyword  = req.getParameter("keyword");    	
    	String type     = req.getParameter("type");
    	String fromDate = req.getParameter("fromDate");
    	String toDate   = req.getParameter("toDate");
    	String simple	= req.getParameter("simple");
    	String action   = req.getParameter("action");
    	
    	HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		String userID = null;
		if (user != null) 
			userID  = user.getUserID();
    	
    	boolean bSimple = (simple == null || simple.charAt(0) != 'Y') ? false : true;
    	
    	
		
		Paging paging = null;
		//user is requesting his personal route information
		if( action != null ){
			if( action.startsWith("getEvents") ){
				if( action.endsWith("Registered") )
					paging = new Paging(req, DBUtilEvent.getRegisteredEventByUserIDCount( userID ) );
				else //created
					paging = new Paging(req, DBUtilEvent.getCreatedEventByUserIDCount( userID ) ); 
				
				if( paging.getTotalRecordCnt() > 0 ) {

					List<Event> events;
					if( action.endsWith("Registered") )
						events = DBUtilEvent.getRegisteredEventByUserID( userID, paging.getRecsPerPage(), paging.getCurPage() );
					else // created
						events = DBUtilEvent.getCreatedEventByUserID( userID, paging.getRecsPerPage(), paging.getCurPage() );
					Iterator<Event> iterator = events.iterator();
					
					Event event;
					int counter=1;
					while( iterator.hasNext() ){
						event = iterator.next();
						
						Group group = DBUtilGroup.getGroupById(event.getGroupID()+ "");
						EventType evtType = DBUtilEventType.getEventTypeById(event.getEventTypeID()+ "");
						
						String[] dateParts = event.getCreatedDate().toString().split("-");
						String date = dateParts[1] + "/" + dateParts[2] + "/" + dateParts[0];	
						date = "Created on " + date + " ";
						
						dateParts = event.getEventDate().toString().split("-");
						String date2 = dateParts[1] + "/" + dateParts[2] + "/" + dateParts[0];	
						date2 = "On " + date2 + " ";
						
						String[] timeParts = event.getEventTime().toString().split(":");
						String time = "";
						int intHour = Integer.valueOf(timeParts[0]); 
						
						if ( intHour > 12 ){
							String hour = (intHour - 12) + "";
							time += hour +  ":" + timeParts[1];							
							time += " PM";
						}
						else {
							time += timeParts[0] + ":" + timeParts[1];
							time += " AM";			
						}
						
						String groupName = "None";
						if (group != null)
							groupName = group.getName();
						
			    		sbuf.append("<div class=\"EventResultItem\">\n").
			    			 append("<b>").append(counter++).append(") </b>").
				    		 append("<a style=\"color:red\" href=\"javascript:YAHOO.pennfitness.float.getEventLeftTB(" + event.getEventID()+ "," + event.getRouteID() + ")\" title=\"Event Details\">" + event.getName() + "</a>").
		    				 append(" by <span style=\"color:blue; font-style:italic\" class=\"ERUserID\">" + event.getCreatorID() + "</span><br /> <span class=\"ERcreatedDate\" style=\"margin-left:10px\" >" + date + "</span>").		    				 
		    				 append(" for Group: <span class=\"ERgroupName\">" + groupName + "</span>, Type: <span class=\"EReventType\">" + evtType.getDescription() + "</span><br />\n").
		    				 append("<span class=\"EReventDate\" style=\"margin-left:10px\" >" + date2 + "At " + time + "</span> for <span class=\"ERduration\">" + event.getDuration() + " hours</span><br /><br />\n").
		    				 append("<span class=\"ERdescription\" style=\"margin-left:10px\" >description</span><br />").
		    				 append("</div><br />");
					}
				}
			}
		}
		else{
			
			List<QueryParameter> params = new ArrayList<QueryParameter>();
			DBUtil.addQueryParam(params, keyword,	" ( name", " LIKE '%"+keyword+"%' OR  description LIKE '%"+keyword+"%')" ); 
			DBUtil.addQueryParam(params, type,		" eventTypeID",	" = '"+type+"'" );
			DBUtil.addQueryParam(params, fromDate,	" eventDate",	" >= '" +fromDate+ "'" );
			DBUtil.addQueryParam(params, toDate,	" eventDate",	" <= '" +toDate+ "'" );
			
			// For paging
			paging = new Paging(req, DBUtilEvent.getSearchForEventsCount( params )); 
			
			if( paging.getTotalRecordCnt() > 0 ) {
				List<Event> events = DBUtilEvent.searchForEvents( params, paging.getRecsPerPage(), paging.getCurPage() );			  	
				if( events != null ){
			    	Iterator<Event> iterator = events.iterator();
			    	
					int cnt = (paging.getCurPage()-1)*paging.getRecsPerPage() + 1;
					Event event;
					int counter=1;
			    	while( iterator.hasNext() ){
			    		
			    		event = iterator.next(); 	
						Group group = DBUtilGroup.getGroupById(event.getGroupID()+ "");
						EventType evtType = DBUtilEventType.getEventTypeById(event.getEventTypeID()+ "");
						
						String[] dateParts = event.getCreatedDate().toString().split("-");
						String date = dateParts[1] + "/" + dateParts[2] + "/" + dateParts[0];	
						date = "Created on " + date + " ";
						
						dateParts = event.getEventDate().toString().split("-");
						String date2 = dateParts[1] + "/" + dateParts[2] + "/" + dateParts[0];	
						date2 = "On " + date2 + " ";
						
						String[] timeParts = event.getEventTime().toString().split(":");
						String time = "";
						int intHour = Integer.valueOf(timeParts[0]); 
						
						if ( intHour > 12 ){
							String hour = (intHour - 12) + "";
							time += hour +  ":" + timeParts[1];							
							time += " PM";
						}
						else {
							time += timeParts[0] + ":" + timeParts[1];
							time += " AM";			
						}
			    		
						String groupName = "None";
						if (group != null)
							groupName = group.getName();
			    		
			    		if( !bSimple ) {
//				    		sbuf.append("<div class=\"EventResultItem\">\n").
//				    			append("<a href=\"javascript:YAHOO.pennfitness.float.getEventLeftTB(" + event.getEventID()+ "," + event.getRouteID() + ")\" title=\"Event Details\">" + event.getName() + "</a>").
//				    			append(" by <span class=\"ERUserID\">" + event.getCreatorID() + "</span> created on <span class=\"ERcreatedDate\">" + event.getCreatedDate() + "</span>").
//				    			append(" for <span class=\"ERgroupName\">" + event.getGroupID() + "</span> Type: <span class=\"EReventType\">" + event.getEventTypeID() + "</span><br />\n").
//				    			append("On <span class=\"EReventDate\">" + event.getEventDate() + " " + event.getEventTime() + "</span> for <span class=\"ERduration\">" + event.getDuration() + " hours</span><br />\n").
//				    			append("<span class=\"ERdescription\">description</span>\n").
//				    			append("</div>\n");
			    			
				    		sbuf.append("<div class=\"EventResultItem\">\n").
			    			 append("<b>").append(counter++).append(") </b>").
				    		 append("<a style=\"color:red\" href=\"javascript:YAHOO.pennfitness.float.getEventLeftTB(" + event.getEventID()+ "," + event.getRouteID() + ")\" title=\"Event Details\">" + event.getName() + "</a>").
		    				 append(" by <span style=\"color:blue; font-style:italic\" class=\"ERUserID\">" + event.getCreatorID() + "</span><br /> <span class=\"ERcreatedDate\" style=\"margin-left:10px\" >" + date + "</span>").		    				 
		    				 append(" for Group: <span class=\"ERgroupName\">" + groupName + "</span>, Type: <span class=\"EReventType\">" + evtType.getDescription() + "</span><br />\n").
		    				 append("<span class=\"EReventDate\" style=\"margin-left:10px\" >" + date2 + "At " + time + "</span> for <span class=\"ERduration\">" + event.getDuration() + " hours</span><br /><br />\n").
		    				 append("<span class=\"ERdescription\" style=\"margin-left:10px\">description</span><br />").
		    				 append("</div><br />");
			    		}
			    		else {
			    			// use simple form for left toolbar listing
			    			sbuf.append("<div class=\"EventsOnDateItem\">\n").
			    				append((cnt++)+ ". <a href=\"javascript:YAHOO.pennfitness.float.getEventLeftTB(" + event.getEventID() + "," + event.getRouteID() + ")\" class=\"EODEventName\">" + StringUtil.fitString(event.getName(), 15) + "</a> by <span class=\"EODuserID\">" + event.getCreatorID() + "</span>\n</div>\n");
			    			
			    		}
			    	}
				}
			} // end of if( totalRecordCnt > 0 ) {
		}
		


		JSONObject data = new JSONObject();
		data.put("CONTENT", sbuf.toString());
		data.put("CURPAGE", paging.getCurPage());
		data.put("TOTALRECCNT", paging.getTotalRecordCnt());

		// Can this fail?
    	result.put( "STATUS", "Success");
    	result.put( "DATA", data );
		
		out.println( result );


    }	
	
}
