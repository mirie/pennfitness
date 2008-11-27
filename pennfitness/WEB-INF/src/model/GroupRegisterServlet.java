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

public class GroupRegisterServlet extends HttpServlet {

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
		String registeredDate = req.getParameter("registeredDate");

		String notify = req.getParameter("notify") == null ? "N" : req.getParameter("notify");
		
		
		// check required parameters
		if( userID == null || groupID == null )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "Invalid parameter");
			
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
		if( !DBUtilGroup.checkGroup(groupID) )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "Group " + groupID + " does not exist.");
			
			out.print(result);
			return;
		}
		Group group = DBUtilGroup.getGroupById(groupID);
		// Insert into database
		if( DBUtilGroup.joinGroup(group,userID,notify)<0 )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "Group Registration failed.");
			
			out.print(result);
			return;
		}

		result.put("STATUS", "Success");
		result.put("MSG", "User " + userID + " registered succesfully.");
		
		out.print(result);
		return;
    	       
    }	
}
