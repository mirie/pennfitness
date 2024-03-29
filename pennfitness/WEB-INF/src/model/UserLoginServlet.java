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
    	    		result.put("DATA","Welcome " + user.getUserName() + " (" + user.getUserID() + ")!" +
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
//    	    	    		"<center> " +
    		                "    	<p><span class=\"userID\"id=\"userID\">"+"<span style=\"color:white; margin-left:20px\">UserID:</span> "+ user.getUserID() + "</span><br>" +
    		                "		<span class=\"userName\"id=\"userName\">"+"<span style=\"color:white; margin-left:20px\">Username:</span> "+ user.getUserName() + "</span><br>" +
    		                "        <span class=\"userEmail\" id=\"userEmail\">"+"<span style=\"color:white; margin-left:20px\">Email:</span> "+user.getEmail()+"</span><br> " +
    		                "		  <span class=\"userGender\"id=\"userGender\">" +
    		                		  //getGenderRadio(user.getGender())+"<br>"+
    		                "		<span style=\"color:white;margin-left:20px\">Gender:</span> "+userID+"<br>"+
    		                		 // getPublicityCheckBox( user.getPublicEventNotify() )+"<br>"+
    		                "		<span style=\"color:white;margin-left:20px\">Public Event Notify Type:</span> "+ user.getPublicEventNotify() +"<br>"+
    		                "        <span style=\"color:white;margin-left:20px\">Member since:</span> <span class=\"since\">"+user.getRegisteredDate()+"</span><br> " +
    		                "        <span class=\"userHeight\"id=\"userHeight\">"+"<span style=\"color:white;margin-left:20px\">Height:</span> "+user.getHeight()+"</span><br>"+
    		                "        <span class=\"userWeight\"id=\"userWeight\">"+"<span style=\"color:white;margin-left:20px\">Weight:</span> "+user.getWeight()+"</span><br>"+
    		                "        </p>" +
    		                "		<div class = \"personalInfo\">" +
    		                
    		                "		<p><span class=\"modifyUserID\" id=\"modifyUserID\">"+
    		                "		<b>UserID :</b>"+                
    		                "		<input type=\"text\" id=\"userIDTxt\">" +	                		
    	                    "		</span><br> " +
    	                    
    		                "		<p><span class=\"modifyUserName\" id=\"modifyUserName\">"+
    		                "		<b>Username :</b>"+                
    		                "		<input type=\"text\" id=\"userNameTxt\">" +	                		
    	                    "		</span><br> " +
    	 
    		                "		<p><span class=\"modifyUserGender\" id=\"modifyUserGender\">"+                   		
    		                "		<b>Gender: (please type F/M/N)</b>" +                    		
    	                    "		<input type=\"text\" size=\"1\" id=\"userGenderTxt\">"+
    	                    "		</span><br>" +
    	                    
    	                    "		<p><span class=\"modifyUserEmail\" id=\"modifyUserEmail\">"+
    	                    "		<b>Email:</b>" +                   		
    	                    "		<input type=\"text\" id=\"userEmailTxt\">"+
    	                    "		</span><br>" +
    	                    
    	                    "		<p><span class=\"modifyUserPassword\" id=\"modifyUserPassword\">"+
    	                    "		<b>Password:</b>" +                    		
    	                    //"		<text size=10 id=\"userPasswordTxt\"></text>" +   
    	                    "		<input type=\"text\" id=\"userPasswordTxt\">"+
    	                    "		</span><br>" +
    	                    
    	                    "		<p><span class=\"modifyUserNotified\" id=\"modifyUserNotified\">"+
    	                    "		<b>Notified for public events: (please type Y/N)</b>" +                    		
    	                    "		<input type=\"text\"size=\"1\" id=\"UserNotifiedEventsTxt\">"+                   		
    	                    "		</span><br>" +
    	                    
    	                    "		<p><span class=\"modifyUserHeight\" id=\"modifyUserHeight\">"+
    	                    "		<b>Height:</b>" +                    		
    	                    "		<input type=\"text\" id=\"HeightInfoTxt\">"+                   		
    	                    "		</span><br>" +
    	                    
    	                    "		<p><span class=\"modifyUserWeight\" id=\"modifyUserWeight\">"+
    	                    "		<b>Weight:</b>" +                    
    	                    "		<input type=\"text\" id=\"WeightInfoTxt\">"+                  		
    	                    "		<span><br>" +
    	                    
    	                    "		</p>" +
    	                    "		</div>" +
    	                    
    		                " <div class=\"personalInfoBtns\">"+
    	            		"	<div id=\"savePersonalInfoBtns\">" +
    	            		"<a id=\"savePersonalInfoBtn\" href=\"javascript:savePersonalInfo()\"/>Save</a>" +
    	            		"    " +
    	            		
    	            		"<a id=\"cancelPersonalInfoBtn\" href=\"javascript:cancelPersonalInfo()\"/>Cancel</a>"+
    	                	//"		<input type=\"button\" id=\"savePersonalInfoBtn\" value=\"Save\" />"+
    	                	//"		<input type=\"button\" id=\"cancelPersonalInfoBtn\" value=\"Cancel\" />"+
    	            		"	</div>"+
    	            		"	<div id=\"modifyPersonalInfoBtns\">" +
    	            		"<a id=\"modifyPersonalInfoBtn\" href=\"javascript:modifyPersonalInfo()\"/>Modify</a>"+
    	               // 	"		<input type=\"button\" id=\"modifyPersonalInfoBtn\" value=\"Modify\" /> " +            
    	            		"	</div>"+
    	        		    "</div></form></center></div>"+
    	            		"</div></form></div>"+
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






