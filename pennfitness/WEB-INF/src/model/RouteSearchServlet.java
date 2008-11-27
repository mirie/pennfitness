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
		
		/* get recsPerPage */
		String recsPerPageString = req.getParameter("recsPerPage");
		int recsPerPage = recsPerPageString == null ? DBConnector.DEFAULTRECSPERPAGE : Integer.parseInt(recsPerPageString);
		
		/* get curPage */
		String curPageString = req.getParameter("curPage");
		int curPage = curPageString == null ? DBConnector.DEFAULTCURRENTPAGE : Integer.parseInt(curPageString);

		/* get total num of records */
		int totalRecordCnt = DBUtilRoute.getSearchForRoutesCount( params );
		
		/* check if exceeding total num pages */
		if( totalRecordCnt == 0 ) {
			curPage = 1;
		}
		else if((curPage-1) * recsPerPage >= totalRecordCnt) {
			curPage = (int)Math.ceil((double)totalRecordCnt/(double)recsPerPage);
		}
		
		if( totalRecordCnt > 0 ) {
			List<Route> routes = DBUtilRoute.searchForRoutes( params, recsPerPage, curPage );			  	
			if( routes != null ){
		    	Iterator<Route> iterator = routes.iterator();
		    	
		    	Route route;
		    	while( iterator.hasNext() ){
		    		route = iterator.next(); 		
	/*
	<div class="RouteResults">
	  <div class="RouteResultItem">
	  	<a href="#" class="RRrouteName">This is the route name</a> by <span class="RRuserID">UserID</span> on <span class="RRcreatedDate">2008-11-27</span> Avg.Rate: 0.0 <br/>
	    <span class="RRdescription">This is description</span>
	  </div>
	</div>
	javascript:YAHOO.pennfitness.float.getRoute('156')
	 */	    		
					sbuf.append("<div class=\"RouteResultItem\">\n").
						append("<a href=\"javascript:YAHOO.pennfitness.float.getRoute('" + route.getId() + "')\" class=\"RRrouteName\">" + route.getName() + "</a> by <span class=\"RRuserID\">").
						append(route.getCreatorID() + "</span> on <span class=\"RRcreatedDate\">").
						append(route.getCreatedDate().toString() + "</span> Avg.Rate: " + route.getDistance() + "<br/>\n").
						append("<span class=\"RRdescription\">" + route.getDescription() + "</span>\n").
						append("</div>\n");
		    	}
			}
		} // end of if( totalRecordCnt > 0 ) {

		JSONObject data = new JSONObject();
		data.put("ROUTES", sbuf.toString());
		data.put("CURPAGE", curPage);
		data.put("TOTALRECCNT", totalRecordCnt);

		// Can this fail?
    	result.put( "STATUS", "Success");
    	result.put( "DATA", data );
		
		out.println( result );
		       
	}	

}
