package model;

import java.io.IOException;
import java.io.PrintWriter;
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
import entities.Route;

public class RouteSearchServlet extends HttpServlet{
	
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
		
		String keyword 	= req.getParameter("keyword");    	
		String fromDistance	= req.getParameter("fromDistance");
		String toDistance   = req.getParameter("toDistance");
		String fromDate = req.getParameter("fromDate");
		String toDate   = req.getParameter("toDate");
		
		
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		params.add( new QueryParameter(" ( name", " LIKE '%"+keyword+"%' OR  description LIKE '%"+keyword+"%')" ) );
		params.add( new QueryParameter(" distance", " >= '"+fromDistance+"'" ) );
		params.add( new QueryParameter(" distance"," <= '"+toDistance+"'" ) );
		params.add( new QueryParameter(" createdDate", " >= '" +fromDate+ "'" ) );
		params.add( new QueryParameter(" createdDate", " <= '" +toDate+ "'" ) );
	
		
		List<Route> routes = DBUtilRoute.searchForRoutes( params );
		  	
		if( routes != null ){
		
	    	Iterator<Route> iterator = routes.iterator();
	    	
	    	StringBuffer sbuf = new StringBuffer();
	    	sbuf.append("<div class = \"Result\">\n").
	    		 append("\t<div class=\"RouteInfoList\">\n");
	    		 
	    	Route route;
	    	while( iterator.hasNext() ){
	    		route = iterator.next();
	    		sbuf.append("\t\t<div class=\"RouteInfoItem\">\n").
	    			 append("\t\t\t<a href=\"function_to_show_route\">"+ route.getName() +"</a>\n").
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
			result.put( "MSG", "Error in searching routes in DB" );
		}
		
		out.println( result );
		       
	}	

}
