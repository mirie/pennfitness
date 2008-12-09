package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import entities.Event;
import entities.User;

public class EventMgmtServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
		doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {      
    	
    	PrintWriter out = resp.getWriter();

    	HttpSession session = req.getSession();
    	User user = (User)session.getAttribute("user");
    	
    	String eventID = req.getParameter("eventID");    	
    	String routeID = req.getParameter("routeID");
    	if (routeID != null) routeID = routeID.trim();
    	
    	String eventName = req.getParameter("eventName");
    	
    	String publicity = req.getParameter("publicity");
    	String groupID = req.getParameter("groupID");    	    	    	
    	String eventTypeID = req.getParameter("eventTypeID");
    	
    	String eventDate = req.getParameter("eventDate"); //expected format = yyyy-mm-dd
    	String eventTime = req.getParameter("eventTime"); //expected format = hh:mm:ss 

    	String eventDuration = req.getParameter("eventDuration");    	
    	
    	String eventDescription = req.getParameter("eventDesc");
    	    	
    	String deleteAction = req.getParameter("action");
    	
    	Event event;
    	
    	int transaction = 0;
    	
    	JSONObject result = new JSONObject();
    	JSONObject data = new JSONObject();

    	if (deleteAction != null && deleteAction.equals("delete")) { // delete event
    		if( user != null ){    		
    			event = DBUtilEvent.getEventById(eventID);
    			String creatorID = event.getCreatorID();
    			if (user.getUserID().trim().equalsIgnoreCase(creatorID)) { // userID == creatorID
    				transaction = DBUtilEvent.deleteEvent(event);
    				if (transaction == -1) {
    					result.put("STATUS", "Failure");
    					result.put("MSG", "Could not delete event for a valid creator");
    				} else {	
    						if( event.getGroupID() > 0 ){
    							if(DBUtilGroup.sendEmailToGroupMembers("Event notification", 
    																   "Event \""+event.getName()+"\" deleted\n\nPlease check out website for further details.", 
    																   event.getGroupID()));
    							result.put("STATUS", "Success");
    	    					result.put("MSG", "");
    						}
    						else{
    							result.put("STATUS", "Success");
    	    					result.put("MSG", "Event deleted but subscribers an error occured while notifying subscribers!");					
    						}			
    				}    				
    			} else { // userID != creatorID for given routeID
    					result.put("STATUS", "Failure");
    					result.put("MSG", "You do not have privileges to delete this event!");    			
    				}
    		} else { // user is not logged in
				result.put("STATUS", "Failure");
				result.put("MSG", "You must be logged in to delete a event!");
			}
    		
    		out.println( result );  // end delete event
    			
    	} else { // Either saving or modifying event
    		if( user == null ) {
	    		result.put("STATUS", "Failure");
				result.put("MSG", "You must be logged in to save or modify an event!");
				out.println(result);
				return;
	    	}
    		
    		float duration;
			Date date;
			try {
				duration = Float.valueOf(eventDuration);
			} catch (NumberFormatException e) {
				result.put( "STATUS", "Failure" );	
				result.put( "MSG", "Invalid Event Duration: " + eventDuration + ". Please type the duration as a decimal." );
				out.println( result );
				return;
			}
    		
			try {
				date = Date.valueOf(eventDate);
			} catch (IllegalArgumentException e) {
				result.put( "STATUS", "Failure" );	
				result.put( "MSG", "Invalid Event Date: " + eventDate + ". Please use the calendar, or type the event as MM/DD/YYYY." );
				out.println( result );
				return;
			}
    		
    		//Save event
    		if( eventID.equals("-1") ){
    			    			
    			event = new Event( eventName,  
    					duration, 
    					eventDescription, 
    					Integer.valueOf( routeID ), 
    					Integer.valueOf( groupID ), 
    					user.getUserID(),
    					date,
    					Time.valueOf(eventTime),
    					publicity.charAt(0),
    					Integer.valueOf(eventTypeID));
    			
    			
    			//EventID of the currently saved event is going to be returned as result.
    			//-1 is returned if there is an error saving event
    			//The output format is as follows:
    			// {"DATA":{"EventID":68},"STATUS":"Success"}    		

    			transaction = DBUtilEvent.saveEvent( event );
    			if( transaction == -1 ) {
    				result.put( "STATUS", "Failure" );	
    				result.put( "MSG", "Could not save event to server!" );
    			}
    			else {
    				result.put( "STATUS", "Success");
    				result.put( "MSG", "Event saved successfully!" );
    			}
    			data.put( "EventID", event.getEventID() );
    			result.put( "DATA",  data );
    		}
    		//Modify event
    		else {    		
    			event = DBUtilEvent.getEventById(eventID);
    			String creatorID = event.getCreatorID();
    			
    			if (user.getUserID().trim().equalsIgnoreCase(creatorID)) { // userID == creatorID

        			event.setName(eventName);  
    				event.setDuration(duration); 
    				event.setDescription(eventDescription); 
    				event.setGroupID(Integer.valueOf( groupID )); 
    				event.setEventDate(date);
    				event.setEventTime(Time.valueOf(eventTime));
    				event.setPublicity(publicity.charAt(0));
    				event.setEventTypeID( Integer.valueOf(eventTypeID));    				
    				
    				transaction = DBUtilEvent.modifyEvent( event );

        			if( transaction == -1 ) {
        				result.put( "STATUS", "Failure" );
        				result.put( "MSG", "Could not save event to server!" );
        			} else {
						if( event.getGroupID() > 0 ){
							if(DBUtilGroup.sendEmailToGroupMembers("Event notification", 
																   "Event \""+event.getName()+"\" modified.\n\nPlease check our website for further details.", 
																   event.getGroupID()));
							result.put("STATUS", "Success");
	    					result.put("MSG", "Event modified and group members notified successfully");
						}
						else{
							result.put( "STATUS", "Success");
	        				result.put( "MSG", "Event modified successfully but an error occured while notifying subscribers !" );
						}	
        			}
    			} else { // userID != creatorID for given routeID
					result.put("STATUS", "Failure");
					result.put("MSG", "You do not have privileges to delete this event!");    			
				}
    			    			
    			data.put( "EventID", transaction );
    			result.put( "DATA",  data );
    		}
    		
    		out.println( result );
    	}
    }	
}
