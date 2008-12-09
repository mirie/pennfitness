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
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import entities.Event;
import entities.Route;
import entities.Paging;
import entities.User;

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
		
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		String userID = null;
		if (user != null) userID   = user.getUserID();
	
		String keyword 	= req.getParameter("keyword");    	
		String fromDistance	= req.getParameter("fromDistance");
		String toDistance   = req.getParameter("toDistance");
		String fromDate = req.getParameter("fromDate");
		String toDate   = req.getParameter("toDate");
		
		String action = req.getParameter("action");
		
		Paging paging;
		//user is requesting his personal route information
		if( "getRoutes".equals(action) ){
			paging = new Paging(req, DBUtilRoute.getRouteByUserIDCount( userID )); 
			
			if( paging.getTotalRecordCnt() > 0 ) {
				
				List<Route> routes = DBUtilRoute.getRouteByUserID( userID, paging.getRecsPerPage(), paging.getCurPage() );
				Iterator<Route> iterator = routes.iterator();
				
				Route route;
				int counter = 1;
				while( iterator.hasNext() ){
					route = iterator.next();
					String[] dateParts = route.getCreatedDate().toString().split("-");
					String date = dateParts[1] + "/" + dateParts[2] + "/" + dateParts[0];	
					date = "Created on " + date + " ";
					
					sbuf.append("<div class=\"RouteResultItem\">\n").
					append("<b>").append(counter++).append(")</b> ").
					append("<a style=\"color:red; font-weight:bold\" href=\"javascript:YAHOO.pennfitness.float.getRoute(" + route.getId() + ", false)\">" + route.getName()+ "</a> by ").
					append("<span class=\"RRuserID\" style=\"color:blue; font-style:italic\" >" + route.getCreatorID() + "</span><br \\> ").
					append("<span class=\"RRcreatedDate\" style=\"margin-left:10px\">" + date + "</span>").
					append("<span class=\"RRdistance\">(" + route.getDistance() + " miles)</span><br \\> ").
					append("<span class=\"RRrating\" style=\"margin-left:10px\">Average rating : " + route.getPt_rate() + "</span><br \\>").
					append("<span style=\"margin-left:10px\" class=\"RRdescription\">" + route.getDescription() + "</span>\n").
					append("</div>\n");
				}
			}
			
		}
		else{
			List<QueryParameter> params = new ArrayList<QueryParameter>();
			
			DBUtil.addQueryParam(params, keyword, " ( name", " LIKE '%"+keyword+"%' OR  description LIKE '%"+keyword+"%')" ); 
			DBUtil.addQueryParam(params, fromDistance, " distance", " >= '"+fromDistance+"'" );
			DBUtil.addQueryParam(params, toDistance, " distance"," <= '"+toDistance+"'" );
			DBUtil.addQueryParam(params, fromDate, " createdDate", " >= '" +fromDate+ "'" );
			DBUtil.addQueryParam(params, toDate, " createdDate", " <= '" +toDate+ "'" );
			
			// For paging
			paging = new Paging(req, DBUtilRoute.getSearchForRoutesCount( params )); 
			
			if( paging.getTotalRecordCnt() > 0 ) {
				List<Route> routes = DBUtilRoute.searchForRoutes( params, paging.getRecsPerPage(), paging.getCurPage() );			  	
				if( routes != null ){
			    	Iterator<Route> iterator = routes.iterator();
			    	
			    	Route route;
			    	while( iterator.hasNext() ){
			    		route = iterator.next(); 		
						sbuf.append("<div class=\"RouteResultItem\">\n").
							append("<a href=\"javascript:YAHOO.pennfitness.float.getRoute('" + route.getId() + "', false)\" class=\"RRrouteName\">" + route.getName() + "</a> by ").
							append("<span class=\"RRuserID\">" + route.getCreatorID() + "</span> on ").
							append("<span class=\"RRcreatedDate\">" + route.getCreatedDate().toString() + "</span>").
							append("<span class=\"RRdistance\">(" + route.getDistance() + " miles)</span> ").
							append("<span class=\"RRrating\">Avg rating : " + route.getPt_rate() + "</span><br \\>\n").
							append("<span class=\"RRdescription\">" + route.getDescription() + "</span>\n").
							append("</div>\n");
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
