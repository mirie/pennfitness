package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import entities.Event;
import entities.Route;
import entities.User;



public class RouteMgmtServlet extends HttpServlet{
	
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
    	
    	String routeID = req.getParameter("routeID");
    	
    	String routeName  = req.getParameter("routeName");
    	String routeDist  = req.getParameter("distance");
    	String routeDesc  = req.getParameter("routeDesc");
    	
    	String routeColor = req.getParameter("routeColor");
    	String routePts   = req.getParameter("pvalue");
    	
    	String deleteAction = req.getParameter("action");
    	
    	int transaction = 0;
    	
    	//RouteID of the currently saved route is going to be returned as result.
    	//-1 is returned if there is an error saving route
		//The output format is as follows:
		// {"DATA":{"RouteID":68},"STATUS":"Success"}
        
        JSONObject result = new JSONObject();
		JSONObject data = new JSONObject();
    		
    	if (deleteAction != null && deleteAction.equals("delete")) { // delete route
    		if (user != null) { // user logged in
    			List<QueryParameter> params = new ArrayList<QueryParameter>();
    			params.add(new QueryParameter("routeID", "= " + routeID));
    			int eventCnt = DBUtilEvent.getSearchForEventsCount(params);
    			
    			if (eventCnt == 0) { // there are NO events associated with this route 
    				Route route = DBUtilRoute.getRouteById(routeID);
    				String creatorID = route.getCreatorID();
    				if (user.getUserID().trim().equalsIgnoreCase(creatorID.trim())){ // userID == creatorID
    					transaction = DBUtilRoute.deleteRoute(route);
        				if (transaction == -1) {
        					result.put("STATUS", "Failure");
        					result.put("MSG", "Could not delete route for a valid creator");
        				} else {
        					result.put("STATUS", "Success");
        					result.put("MSG", "");    					
        				}    					
    				} else { // userID != creatorID for given routeID
    					result.put("STATUS", "Failure");
    					result.put("MSG", "You do not have privileges to delete this route!");    			
    				}
    				
    			} else { // there are events associated with this route (can't delete)
    				result.put("STATUS", "Failure");
					result.put("MSG", "You cannot delete a route that has events associated with it.");
    			} // end user logged-in block
    		
    		} else { // user is not logged in
    				result.put("STATUS", "Failure");
    				result.put("MSG", "You must be logged in to delete a route!");
    			}     		
 
    		out.println( result );  // end delete route
    		
    	} else { // Either saving or modifying route	    
	    	Route route;
	    	if( user == null ) {
	    		result.put("STATUS", "Failure");
				result.put("MSG", "You must be logged in to delete a route!");
				out.println(result);
				return;
	    	}
	    	 		
	    	route = new Route(routeName, routeColor, routePts, Float.valueOf(routeDist), routeDesc, user.getUserID() );
	    	
	    	//Save route
	    	if( routeID.equals("-1") ){

	    		transaction = DBUtilRoute.saveRoute( route );
	    		if( transaction == -1 )
	    			result.put( "STATUS", "Failure" );		
	    		else
	    			result.put( "STATUS", "Success");
	    		
	    		data.put( "RouteID", transaction );
	    		result.put( "DATA",  data );
	    		
	            //out.println( result );
	            
	    	}
	    	//Modify route
	    	else{
	    		route.setId( Integer.valueOf( routeID.trim() ) );	    		
	    		
	    		transaction = DBUtilRoute.modifyRoute( route );
	    		
	    		if( transaction == -1 )
	    			result.put( "STATUS", "Failure" );		
	    		else
	    			result.put( "STATUS", "Success");
	    		
	    		data.put( "RouteID", routeID.trim() );
	    		result.put( "DATA",  data );
	    		
	          //  out.println( result );
	    	}
	    	
	    	out.println( result );
    	}
    }
}
