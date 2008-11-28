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

public class EventSaveServlet extends HttpServlet {

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
    	String routeID = req.getParameter("routeID").trim();
    	
    	String eventName = req.getParameter("eventName");
    	
    	String publicity = req.getParameter("publicity");
    	String groupID = req.getParameter("groupID");    	    	    	
    	String eventTypeID = req.getParameter("eventTypeID");
    	
    	String eventDate = req.getParameter("eventDate"); //expected format = yyyy-mm-dd
    	String eventTime = req.getParameter("eventTime"); //expected format = hh:mm:ss 

    	String eventDuration = req.getParameter("eventDuration");    	
    	
    	String eventDescription = req.getParameter("eventDesc");
    	    	
    	Event event;
    	
    	int transaction = 0;
    	
    	JSONObject result = new JSONObject();
    	JSONObject data = new JSONObject();

    	if( user != null ){
    		System.out.println(eventDate);
    		
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
    	}  
    	else{
    		event = new Event( eventName,  
					 Float.valueOf(eventDuration), 
					 eventDescription, 
					 Integer.valueOf( routeID ), 
					 Integer.valueOf( groupID ), 
					 "defaultUser",			
					 Date.valueOf(eventDate),
					 Time.valueOf(eventTime),
					 publicity.charAt(0),
					 Integer.valueOf(eventTypeID));
    	}
    	
    	//Save event
    	if( eventID.equals("-1") ){    
        	//EventID of the currently saved event is going to be returned as result.
        	//-1 is returned if there is an error saving event
    		//The output format is as follows:
    		// {"DATA":{"EventID":68},"STATUS":"Success"}    		
    		
    		transaction = DBUtilEvent.saveEvent( event );
    		if( transaction == -1 )
    			result.put( "STATUS", "Failure" );		
    		else
    			result.put( "STATUS", "Success");
    		
    		data.put( "EventID", transaction );
    		result.put( "DATA",  data );
    		
    		//System.out.println( result );
            out.println( result );
            
    	}
    	//Modify event
    	else{
    		event.setEventID( Integer.valueOf( eventID.trim() ) );

    		transaction = DBUtilEvent.modifyEvent( event );
    		
    		if( transaction == -1 )
    			result.put( "STATUS", "Failure" );		
    		else
    			result.put( "STATUS", "Success");
    		
    		data.put( "EventID", routeID.trim() );
    		result.put( "DATA",  data );
    			
            out.println( result );
    	}
    	       
    }	
}
