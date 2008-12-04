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
    	    		"	<div>" +
    	    		"<form name=\"frmUserInfoModify\" id=\"frmUserInfoModify\"" +
    	    		"<center> " +
	                "    	<p><span class=\"userName\"id=\"userName\">"+"<b>Username :</b>"+ user.getUserID() + "</span><br> " +
	                "        <span class=\"userEmail\" id=\"userEmail\">"+"<b>Email :</b>"+user.getEmail()+"</span><br> " +
	                "		  <span class=\"userGender\"id=\"userGender\">" +
	                		  //getGenderRadio(user.getGender())+"<br>"+
	                "		<b>Gender :</b>"+user.getGender()+"<br>"+
	                		  getPublicityCheckBox( user.getPublicEventNotify() )+"<br>"+
	                "        <b>Member since :</b><span class=\"since\">"+user.getRegisteredDate()+"</span><br> " +
	                "        <span class=\"userHeight\"id=\"userHeight\">"+"<b>Height :</b>"+user.getHeight()+"</span><br>"+
	                "        <span class=\"userWeight\"id=\"userWeight\">"+"<b>Weight :</b>"+user.getWeight()+"</span><br>"+
	                "        </p>" +
	                "		<div class = \"personalInfo\">" +
	                		
	                "		<p><span class=\"modifyUserName\" id=\"modifyUserName\">"+
	                "		<b>Username :</b>"+                
	                "		<textarea id=\"userNameTxt\"></textarea>" +	                		
                    "		</span><br> " +
 
	                "		<p><span class=\"modifyUserGender\" id=\"modifyUserGender\">"+                   		
            	 "<label for=\"genderBtn\"><b>Gender :</b></label>"+
             	   "<input type=\"radio\" value=\"N\" checked/> N/A"+
             	   "<input type=\"radio\" value=\"M\" /> Male"+
             	   "<input type=\"radio\" value=\"F\" /> Female"+  
                  		
                    "		</span><br>" +
                    
                    "		<p><span class=\"modifyUserEmail\" id=\"modifyUserEmail\">"+
                    "		<b>Email:</b>" +                   		
                    "		<textarea id=\"userEmailTxt\"></textarea>" +
                    "		</span><br>" +
                    
                    "		<p><span class=\"modifyUserPassword\" id=\"modifyUserPassword\">"+
                    "		<b>Password:</b>" +                    		
                    "		<textarea id=\"userPasswordTxt\"></textarea>" +                    		
                    "		</span><br>" +
                    
                    "		<p><span class=\"modifyUserNotified\" id=\"modifyUserNotified\">"+
                    "		<b>Notified for public events:</b>" +                    		
                    "		<textarea id=\"UserNotifiedEventsTxt\"></textarea>" +                    		
                    "		</span><br>" +
                    
                    "		<p><span class=\"modifyUserHeight\" id=\"modifyUserHeight\">"+
                    "		<b>Height:</b>" +                    		
                    "		<textarea id=\"HeightInfoTxt\"></textarea>" +                    		
                    "		</span><br>" +
                    
                    "		<p><span class=\"modifyUserWeight\" id=\"modifyUserWeight\">"+
                    "		<b>Weight:</b>" +                    
                    "		<textarea id=\"WeightInfoTxt\"></textarea>" +                    		
                    "		<span><br>" +
                    
                    "		</p>" +
                    "		</div>" +
                    
	                " <div class=\"personalInfoBtns\">"+
            		"	<div id=\"savePersonalInfoBtns\">"+
                	"		<input type=\"button\" id=\"savePersonalInfoBtn\" value=\"Save\" />"+
                	"		<input type=\"button\" id=\"cancelPersonalInfoBtn\" value=\"Cancel\" />"+
            		"	</div>"+
            		"	<div id=\"modifyPersonalInfoBtns\">"+
                	"		<input type=\"button\" id=\"modifyPersonalInfoBtn\" value=\"Modify\" /> " +            
            		"	</div>"+
        		    "</div></form></center></div>"+
                    "    ");
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






