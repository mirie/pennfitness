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
    		if( action.equals("login") ){
    			User user = DBUtilUser.loginUser(userID, password);
    	    	
    	    	if( user == null ) {
    	    		result.put("STATUS", "Failure");
    	    		result.put("MSG", "Wrong password");
    	    	}
    	    	else {
    	    		// login successful
    	    		result.put("STATUS", "Success");
    	    		result.put("MSG", "");
    	    		result.put("DATA","Welcome " + user.getUserName() + "(" + user.getUserID() + ")!" +
    	    					"<a href=\"javascript:ShowMyAccountDialog('"+user.getUserID()+"');\"/>My Account</a> " );
    	    		
    	    		// store user in HttpSession
    	    		session.setAttribute("user", user);
    	    	}
    		}
    		else if( action.equals("getPersonalInfo") ){
    			User user = DBUtilUser.getUserById( userID );
    			
    			if( user == null ){
    				result.put("STATUS", "Failure");
    	    		result.put("MSG", "Personal information cannot be retrieved");
    			}
    			else{
    				result.put("STATUS", "Success");
    	    		result.put("MSG", "");
    	    		result.put("DATA",
    	    		"	<center> " +
	                "    	<p><b>Username :</b><span class=\"userName\">"+ user.getUserID() + "</span><br> " +
	                "        <b>Email :</b><span class=\"userEmail\">"+user.getEmail()+"</span><br> " +
	                "		  <span class=\"userGender\">" +
	                		  getGenderRadio(user.getGender())+"<br>"+
	                		  getPublicityCheckBox( user.getPublicEventNotify() )+"<br>"+
	                "        <b>Member since :</b><span class=\"since\">"+user.getRegisteredDate()+"</span><br> " +
	                "        <b>Height :</b><span class=\"userHeight\">"+user.getHeight()+"</span><br>"+
	                "        <b>Weight :</b><span class=\"userWeight\">"+user.getWeight()+"</span><br>"+
	                "        </p>" +
                    "    </center>");
    			}
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
    
    private String getGenderRadio( char gender ){
    	 	
    	if(gender == 'M')
    		return "<label for=\"gender\"><b>Gender :</b></label>"+
            	   "<input type=\"radio\" name=\"gender\" value=\"N\" /> N/A"+
            	   "<input type=\"radio\" name=\"gender\" value=\"M\" checked/> Male"+
            	   "<input type=\"radio\" name=\"gender\" value=\"F\" /> Female";  
    	else if( gender == 'F' )
    		return "<label for=\"gender\"><b>Gender :</b></label>"+
     	   		   "<input type=\"radio\" name=\"gender\" value=\"N\" /> N/A"+
     	   		   "<input type=\"radio\" name=\"gender\" value=\"M\" /> Male"+
     	   		   "<input type=\"radio\" name=\"gender\" value=\"F\" checked/> Female";  
    	
		return "<label for=\"gender\"><b>Gender :</b></label>"+
 	   		   "<input type=\"radio\" name=\"gender\" value=\"N\" checked/> N/A"+
 	   		   "<input type=\"radio\" name=\"gender\" value=\"M\" /> Male"+
 	   		   "<input type=\"radio\" name=\"gender\" value=\"F\" /> Female";  
    }
    
    private String getPublicityCheckBox( char publicEventNotify ){
    	if( publicEventNotify == 'Y' || publicEventNotify == 'y')
    		return "<b>Event publicity:</b> <input type=\"checkbox\" name=userPublicity value=\"Y\" checked>";
    	return "<b>Event publicity:</b> <input type=\"checkbox\" name=userPublicity value=\"N\">";
    }
}






