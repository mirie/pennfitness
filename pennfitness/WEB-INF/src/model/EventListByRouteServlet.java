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

import org.json.simple.JSONObject;

import entities.Event;
import entities.Paging;

public class EventListByRouteServlet extends HttpServlet{

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
    	
    	String routeID = req.getParameter("routeID");
    	
    	int eventCount = DBUtilEvent.getEventCountByRouteId(routeID); 
    	
    	Paging paging = new Paging(req, eventCount);
    	
    	if (paging.getTotalRecordCnt() > 0) {
    		List<Event> events = DBUtilEvent.getEventsByRouteId(routeID, paging.getRecsPerPage(), paging.getCurPage());
    		
    		if (events != null) {
    			Iterator <Event> iterator = events.iterator();
    			
    			Event event;
    			while (iterator.hasNext()) {
    				event = iterator.next();
		    		sbuf.append("<div class=\"EventListByRoute\">\n").
		    		append("<a href=\"javascript:YAHOO.pennfitness.float.getEvent(" + event.getEventID() + ", false)\" class=\"ELBREventName\">" + event.getName() + "</a> by <span class=\"ELBRuserID\">" + event.getCreatorID() + "</span>\n").
	    			append("</div>\n");    				
    			}
    		}
    	} // end getTotalRecordCnt() > 0
    	
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