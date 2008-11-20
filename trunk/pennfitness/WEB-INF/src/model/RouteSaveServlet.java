package model;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            out.println( DBUtilRoute.saveRoute( route ) );
    	}
    	//Modify route
    	else{
    		route.setId( Integer.valueOf( routeID.trim() ) );
    		out.println( DBUtilRoute.modifyRoute( route ) );  // Kerem: is it possible to just change
    	}
    	       
    }
}
