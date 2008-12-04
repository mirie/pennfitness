package model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import entities.User;
import org.json.simple.JSONObject;

public class RouteRateServlet extends HttpServlet {

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
		JSONObject result = new JSONObject();
		JSONObject data = new JSONObject();

    	String sceneryStr	= req.getParameter("scenery");    	
    	String diffStr 		= req.getParameter("difficulty");
    	String safetyStr	= req.getParameter("safety");
    	String rateStr		= req.getParameter("rate");
    	String routeID		= req.getParameter("routeID");
    	User user			= (User)session.getAttribute("user");
    	String userID		= "";

    	int scenery, difficulty, safety, rate;
    	
    	if( user == null )
    	{
    		result.put("STATUS", "Failure");
    		result.put("MSG", "Please login to rate a route");
        	out.print(result);
        	return;
    	}
    	
    	try
    	{
        	userID		= user.getUserID();
    		scenery		= Integer.parseInt(sceneryStr);
    		difficulty	= Integer.parseInt(diffStr);
    		safety		= Integer.parseInt(safetyStr);
    		rate		= Integer.parseInt(rateStr);
    		
    		if(routeID == null || routeID.length() == 0) throw new Exception();
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		result.put("STATUS", "Failure");
    		result.put("MSG", "Invalid argument");
        	out.print(result);
        	return;
    	}
    	
    	if( DBUtilRoute.checkIfRatedRoute(routeID, userID) )
    	{
    		result.put("STATUS", "Failure");
    		result.put("MSG", "You can rate a route only once!");
        	out.print(result);
        	return;
    	}
    	
    	List<Float> rates = DBUtilRoute.rateRoute(routeID, userID, scenery, difficulty, safety, rate);
    	
    	if(rates == null)
    	{
    		result.put("STATUS", "Failure");
    		result.put("MSG", "Error while updating ratings");
        	out.print(result);
        	return;
    	}
    	
    	data.put("ROUTE_RATING_SCENERY", rates.get(0));
    	data.put("ROUTE_RATING_DIFF", rates.get(1));
    	data.put("ROUTE_RATING_SAFETY", rates.get(2));
    	data.put("ROUTE_RATING_OVERALL", rates.get(3));
    	
		result.put("STATUS", "Success");
		result.put("DATA", data);
    	out.print(result);
    	
    }


}






