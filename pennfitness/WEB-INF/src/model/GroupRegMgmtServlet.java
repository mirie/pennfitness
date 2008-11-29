package model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;
import entities.Group;
import org.json.simple.JSONObject;

public class GroupRegMgmtServlet extends HttpServlet {

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
		String groupID = req.getParameter("groupID");
		String action = req.getParameter("action");
		String notify = req.getParameter("notify") == null ? "N" : req.getParameter("notify");
		
		// check required parameters
		if( userID == null || groupID == null )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "user ID and group ID cannot be null");
			
			out.print(result);
			return;
		}
		// Check whether the user exists
		if( !DBUtilUser.checkUser(userID) )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "User " + userID + " does not exist.");
			
			out.print(result);
			return;
		}
		// Check whether the group exists
		if( !DBUtilGroup.checkGroupByID(groupID) )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "Group " + groupID + " does not exist.");
			
			out.print(result);
			return;
		}
		Group group = DBUtilGroup.getGroupById(groupID);
		//check if action is null
    	if( action == null )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "action cannot be null");
			
			out.print(result);
			return;
		}
    	if("joinGroup".equalsIgnoreCase(action))
    	{
    		//check if notify string is valid
        	if((notify!=null) &&( !"N".equalsIgnoreCase(notify))&&(!"Y".equalsIgnoreCase(notify)))
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "undefined notify type");
    			
    			out.print(result);
    			return;
    		}
    		// Insert into database
    		if( DBUtilGroup.joinGroup(groupID,userID,notify)<0 )
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Group Registration failed because there the user has already joined the group.");
    			
    			out.print(result);
    			return;
    		}

    		result.put("STATUS", "Success");
    		result.put("MSG", "User " + userID + " registered succesfully.");
    		
    		out.print(result);
    		return;
    	}
    	else if ("leaveGroup".equalsIgnoreCase(action))
    	{
			// Delete from database
			if( DBUtilGroup.leaveGroup(group,userID)<0 )
			{
				result.put("STATUS", "Failure");
				result.put("MSG", "Group Registration failed.");
				
				out.print(result);
				return;
			}
	
			result.put("STATUS", "Success");
			result.put("MSG", "Group registration with userID: " + userID + "and groupID: "+ groupID +" removed succesfully.");
			
			out.print(result);
			return;
    	}
    	else
    	{
    		result.put("STATUS", "Failure");
			result.put("MSG", "undefined action");
			
			out.print(result);
			return;
    	}
    	       
    }	
}