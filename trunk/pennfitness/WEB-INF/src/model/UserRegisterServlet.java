package model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;

public class UserRegisterServlet extends HttpServlet {

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

		// BEGIN TEMPORARY
		User ouser = (User) session.getAttribute("user");
		if(ouser != null ) {
			out.println("user is logged in");			
		}
		// END TEMPORARY
    	
    	String userID   = req.getParameter("userID");    	
    	String password = req.getParameter("password");
    	String action   = req.getParameter("action");
    	
    	// logout process
    	if(action != null && action.equals("logout")) {
    		session.invalidate();
    		out.println(1);
    		return;
    	}
    	
    	User user = DBUtilUser.loginUser(userID, password);
    	
    	if( user == null ) {
    		out.println(-1); // TODO: check error code
    	}
    	else {
    		// login successful
    		out.println("Welcome " + user.getUserName() + "(" + user.getUserID() + ")!"); // TODO: what information to return?

    		// store user in HttpSession
    		session.setAttribute("user", user);
    		
    	}
    	       
    }	
}
