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
import entities.Paging;
import entities.Route;

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
    	
    	List<QueryParameter> params = new ArrayList<QueryParameter>();
		DBUtil.addQueryParam(params, keyword,	" ( name", " LIKE '%"+keyword+"%' OR  description LIKE '%"+keyword+"%')" ); 
		DBUtil.addQueryParam(params, type,		" eventTypeID",	" = '"+type+"'" );
		DBUtil.addQueryParam(params, fromDate,	" eventDate",	" >= '" +fromDate+ "'" );
		DBUtil.addQueryParam(params, toDate,	" eventDate",	" <= '" +toDate+ "'" );

		// For paging
		Paging paging = new Paging(req, DBUtilEvent.getSearchForEventsCount( params )); 
		
		if( paging.getTotalRecordCnt() > 0 ) {
			List<Event> events = DBUtilEvent.searchForEvents( params, paging.getRecsPerPage(), paging.getCurPage() );			  	
			if( events != null ){
		    	Iterator<Event> iterator = events.iterator();
		    	
		    	Event event;
		    	while( iterator.hasNext() ){
		    		event = iterator.next(); 	
		    		
		    		sbuf.append("<div class=\"EventResultItem\">\n").
		    			append("<a href=\"javascript:registerEvent(" + event.getEventID() + ")\" title=\"Register to this event!\">" + event.getName() + "</a>").
		    			append(" by <span class=\"ERUserID\">" + event.getCreatorID() + "</span> created on <span class=\"ERcreatedDate\">" + event.getCreatedDate() + "</span>").
		    			append(" for <span class=\"ERgroupName\">" + event.getGroupID() + "</span> Type: <span class=\"EReventType\">" + event.getEventTypeID() + "</span><br />\n").
		    			append("On <span class=\"EReventDate\">" + event.getEventDate() + " " + event.getEventTime() + "</span> for <span class=\"ERduration\">" + event.getDuration() + " hours</span><br />\n").
		    			append("<span class=\"ERdescription\">description</span>\n").
		    			append("</div>\n");
		    		
//					sbuf.append("<div class=\"RouteResultItem\">\n").
//						append("<a href=\"javascript:YAHOO.pennfitness.float.getRoute('" + route.getId() + "', false)\" class=\"RRrouteName\">" + route.getName() + "</a> by ").
//						append("<span class=\"RRuserID\">" + route.getCreatorID() + "</span> on ").
//						append("<span class=\"RRcreatedDate\">" + route.getCreatedDate().toString() + "</span>").
//						append("<span class=\"RRdistance\">(" + route.getDistance() + " miles)</span> ").
//						append("<span class=\"RRrating\">Avg rating : " + route.getPt_rate() + "</span><br \\>\n").
//						append("<span class=\"RRdescription\">" + route.getDescription() + "</span>\n").
//						append("</div>\n");
		    	}
			}
		} // end of if( totalRecordCnt > 0 ) {

		JSONObject data = new JSONObject();
		data.put("CONTENT", sbuf.toString());
		data.put("CURPAGE", paging.getCurPage());
		data.put("TOTALRECCNT", paging.getTotalRecordCnt());

		// Can this fail?
    	result.put( "STATUS", "Success");
    	result.put( "DATA", data );
		
		out.println( result );

/*		
		
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
*/
    }	
	
}
