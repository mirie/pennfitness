package model;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import entities.Route;



public class RouteSaveServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {    
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {      
    	
    	PrintWriter out = resp.getWriter();
    	
    	
    	String routeID = req.getParameter("routeID");
    	
    	String routeName  = req.getParameter("routeName");
    	String routeDist  = req.getParameter("distance");
    	String routeDesc  = req.getParameter("routeDesc");
    	
    	String routeColor = req.getParameter("routeColor");
    	String routePts   = req.getParameter("pvalue");
    	
    	
    	//TODO
    	//HttpSession
    	//creatorId will be read from cookies. 
    	Route route = new Route(routeName, routeColor, routePts, Float.valueOf(routeDist), routeDesc, 1/*creatorId*/ );
    	
    	//Save route
    	if( routeID.equals("-1") ){
     
        	//RouteID of the currently saved route is going to be returned as result.
        	//-1 is returned if there is an error saving route
    		//The output format is as follows:
    		// {"DATA":{"RouteID":68},"STATUS":"Success"}
            
            JSONObject result = new JSONObject();
    		JSONObject data = new JSONObject();
    		
    		int transaction = DBUtilRoute.saveRoute( route );
    		if( transaction == -1 )
    			result.put( "STATUS", "Failure" );		
    		else
    			result.put( "STATUS", "Success");
    		
    		data.put( "RouteID", transaction );
    		result.put( "DATA",  data );
    		
            out.println( result );
            
    	}
    	//Modify route
    	else{
    		route.setId( Integer.valueOf( routeID.trim() ) );
    		
    		JSONObject result = new JSONObject();
    		JSONObject data = new JSONObject();
    		
    		int transaction = DBUtilRoute.saveRoute( route );
    		
    		if( transaction == -1 )
    			result.put( "STATUS", "Failure" );		
    		else
    			result.put( "STATUS", "Success");
    		
    		data.put( "RouteID", routeID.trim() );
    		result.put( "DATA",  data );
    		
            out.println( result );
    	}
    	       
    }
}
