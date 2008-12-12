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

public class UserModifyServlet extends HttpServlet {

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
    	User user = (User)session.getAttribute("user");
    	String userID   = user.getUserID();
		//String userID   = req.getParameter("userID");
		String userName = req.getParameter("name");
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		String publicEventNotify = req.getParameter("publicEventNotify") == null ? "N" : req.getParameter("publicEventNotify");
		
		float height;
		if( req.getParameter("height") == null )
		{
			height = 0;
		}
		else {
			try {
				height = Float.parseFloat(req.getParameter("height"));
			} catch(NumberFormatException ex)
			{
				height = 0;
			}
		}
		
		float weight;
		if( req.getParameter("weight") == null )
		{
			weight = 0;
		}
		else {
			try {
				weight = Float.parseFloat(req.getParameter("weight"));
			} catch(NumberFormatException ex)
			{
				weight = 0;
			}
		}
		String gender = req.getParameter("gender") == null ? "N" : req.getParameter("gender");

		// have to have userID in order to modify
		if( userID == null )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "Have to have userID in order to modify");
			
			out.print(result);
			return;
		}
		
		// Check whether the user exists
		if( !DBUtilUser.checkUser(userID) )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "Cannot modify user information because user " + userID + " does not exist.");
			
			out.print(result);
			return;
		}

		// Insert into database
		if( !DBUtilUser.modifyUser(
				new User(userID, userName, password, email, height, weight, gender.charAt(0), publicEventNotify.charAt(0))
				) )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "User modification failed.");
			
			out.print(result);
			return;
		}

		result.put("STATUS", "Success");
		result.put("MSG", "User " + userID + " modification succesfully.");
		
		out.print(result);
		return;
    	       
    }	
}