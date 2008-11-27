package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import entities.Event;

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
    	
    	String keyword  = req.getParameter("keyword");    	
    	String type     = req.getParameter("type");
    	String fromDate = req.getParameter("fromDate");
    	String toDate   = req.getParameter("toDate");
    	
    	
    	List<QueryParameter> params = new ArrayList<QueryParameter>();
    	params.add( new QueryParameter(" name", " LIKE '%"+keyword+"%' OR  description LIKE '%"+keyword+"%'" ) );
    	params.add( new QueryParameter(" groupID", " = '"+type+"'" ) );
    	params.add( new QueryParameter(" eventDate", " > '" +fromDate+ "'") );
    	params.add( new QueryParameter(" eventDate", " < '" +toDate+ "'") );

    	
    	List<Event> events = DBUtilEvent.searchForEvents( params );
    	  	
    	if( events != null ){
    	
	    	Iterator<Event> iterator = events.iterator();
	    	
	    	StringBuffer sbuf = new StringBuffer();
	    	sbuf.append("<div class = \"Result\">\n").
	    		 append("\t<div class=\"EventInfoList\">\n");
	    		 
	    	Event event;
	    	while( iterator.hasNext() ){
	    		event = iterator.next();
	    		sbuf.append("\t\t<div class=\"registeredEventItem\">\n").
	    			 append("\t\t\t<a href=\"function_to_show_event\">"+ event.getName() +"</a>\n").
	    			 append("\t\t</div>\n");
	    	}
	    	
	    	sbuf.append("\t</div>\n").
	    		 append("</div>\n");
	    	
	    	
	    	result.put( "STATUS", "Success");
	    	result.put( "DATA", sbuf.toString() );
	    	
    	}
    	else{
    		result.put( "STATUS", "Failure");
    		//TODO
    		//I'll put more detailed MSG here
    		result.put( "MSG", "Error in DB" );
    	}
    	
    	out.println( result );
    	       
    }	
	
}
