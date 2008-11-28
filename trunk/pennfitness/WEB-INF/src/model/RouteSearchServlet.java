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
import entities.Paging;

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
    	StringBuffer sbuf = new StringBuffer();
		PrintWriter out = resp.getWriter();
		
		String keyword 	= req.getParameter("keyword");    	
		String fromDistance	= req.getParameter("fromDistance");
		String toDistance   = req.getParameter("toDistance");
		String fromDate = req.getParameter("fromDate");
		String toDate   = req.getParameter("toDate");
		
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		
		DBUtil.addQueryParam(params, keyword, " ( name", " LIKE '%"+keyword+"%' OR  description LIKE '%"+keyword+"%')" ); 
		DBUtil.addQueryParam(params, fromDistance, " distance", " >= '"+fromDistance+"'" );
		DBUtil.addQueryParam(params, toDistance, " distance"," <= '"+toDistance+"'" );
		DBUtil.addQueryParam(params, fromDate, " createdDate", " >= '" +fromDate+ "'" );
		DBUtil.addQueryParam(params, toDate, " createdDate", " <= '" +toDate+ "'" );
		
		// For paging
		Paging paging = new Paging(req, DBUtilRoute.getSearchForRoutesCount( params )); 
		
		if( paging.getTotalRecordCnt() > 0 ) {
			List<Route> routes = DBUtilRoute.searchForRoutes( params, paging.getRecsPerPage(), paging.getCurPage() );			  	
			if( routes != null ){
		    	Iterator<Route> iterator = routes.iterator();
		    	
		    	Route route;
		    	while( iterator.hasNext() ){
		    		route = iterator.next(); 		
					sbuf.append("<div class=\"RouteResultItem\">\n").
						append("<a href=\"javascript:YAHOO.pennfitness.float.getRoute('" + route.getId() + "')\" class=\"RRrouteName\">" + route.getName() + "</a> by ").
						append("<span class=\"RRuserID\">" + route.getCreatorID() + "</span> on ").
						append("<span class=\"RRcreatedDate\">" + route.getCreatedDate().toString() + "</span>").
						append("<span class=\"RRdistance\">(" + route.getDistance() + " miles)</span> ").
						append("<span class=\"RRrating\">Avg rating : " + route.getPt_rate() + "</span><br \\>\n").
						append("<span class=\"RRdescription\">" + route.getDescription() + "</span>\n").
						append("</div>\n");
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
		       
	}	

}
