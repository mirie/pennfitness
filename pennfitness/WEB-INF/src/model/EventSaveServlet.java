package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Event;

public class EventSaveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {    
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {      
    	
    	PrintWriter out = resp.getWriter();
    	
    	String eventID = req.getParameter("eventID");    	
    	String routeID = req.getParameter("routeID");
    	String groupID = req.getParameter("groupID");
    	
    	String eventName        = req.getParameter("eventName");
    	String eventDuration    = req.getParameter("eventDuration");
    	String eventDescription = req.getParameter("eventDesc");
    	String eventTime        = req.getParameter("eventTime");
    	//String eventDate = req.getParameter("eventDate");
    	//String publicity = req.getParameter("publicity");
    	
    	//TODO
    	//HttpSession
    	//creatorId will be read from cookies. 
    	Event event = new Event( eventName,  
    							 Float.valueOf(eventDuration), 
    							 eventDescription, 
    							 Integer.valueOf( routeID ), 
    							 Integer.valueOf( groupID ), 
    							 1/*creatorId*/,
    							 eventTime,
    							 new Date(System.currentTimeMillis())/*eventDate*/,
    							 'Y'/*char publicity*/);
    	
    	//Save event
    	if( eventID.equals("-1") ){    
        	//EventID of the currently saved event is going to be returned as result.
        	//-1 is returned if there is an error saving event
            out.println( DBUtilEvent.saveEvent( event ) );
    	}
    	//Modify event
    	else{
    		event.setEventID( Integer.valueOf( eventID.trim() ) );
    		out.println( DBUtilEvent.modifyEvent( event ) );
    	}
    	       
    }	
}
