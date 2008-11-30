package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;

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
    					result.put("STATUS", "Success");
    					result.put("MSG", "");    					
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
				result.put("MSG", "You must be logged in to delete a event!");
				out.println(result);
				return;
	    	}
			event = new Event( eventName,  
					Float.valueOf(eventDuration), 
					eventDescription, 
					Integer.valueOf( routeID ), 
					Integer.valueOf( groupID ), 
					user.getUserID(),
					Date.valueOf(eventDate),
					Time.valueOf(eventTime),
					publicity.charAt(0),
					Integer.valueOf(eventTypeID));
    		
    		//Save event
    		if( eventID.equals("-1") ){    
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
    				result.put( "MSG", "Event saved successfully" );
    			}
    			data.put( "EventID", transaction );
    			result.put( "DATA",  data );
    		}
    		//Modify event
    		else{
    			event.setEventID( Integer.valueOf( eventID.trim() ) );

    			transaction = DBUtilEvent.modifyEvent( event );

    			if( transaction == -1 ) {
    				result.put( "STATUS", "Failure" );
    				result.put( "MSG", "Could not save event to server!" );
    			} else {
    				result.put( "STATUS", "Success");
    				result.put( "MSG", "Event saved successfully" );
    			}
    			
    			data.put( "EventID", routeID.trim() );
    			result.put( "DATA",  data );
    		}
    		
    		out.println( result );
    	}
    }	
}
