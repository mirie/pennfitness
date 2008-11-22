package model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;
import org.json.simple.JSONObject;

public class UserLoginServlet extends HttpServlet {

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

    	String userID   = req.getParameter("userID");    	
    	String password = req.getParameter("password");
    	String action   = req.getParameter("action");
    	
    	// logout process
    	if(action != null && action.equals("logout")) {
    		session.invalidate();
    		result.put("STATUS", "Success");
    		result.put("MSG", "");
    	}
    	// BEGIN login process
    	// Check arguments
    	else if( userID == null || password == null )
    	{
    		result.put("STATUS", "Failure");
    		result.put("MSG", "Invalid argument");
    	}
		// Check whether the user exists
    	else if( DBUtilUser.checkUser(userID) )
		{
	    	User user = DBUtilUser.loginUser(userID, password);
	    	
	    	if( user == null ) {
	    		result.put("STATUS", "Failure");
	    		result.put("MSG", "Wrong password");
	    	}
	    	else {
	    		// login successful
	    		result.put("STATUS", "Success");
	    		result.put("MSG", "");
	    		result.put("DATA","Welcome " + user.getUserName() + "(" + user.getUserID() + ")!");
	    		
	    		// store user in HttpSession
	    		session.setAttribute("user", user);
	    	}
		}
		else // if no user
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "User " + userID + " does not exists.");
		}
    	// END login process
    	
    	out.println( result );
    	       
    }	
}






