package model;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;


import entities.User;
import entities.Group;



public class GroupSaveServlet extends HttpServlet{
	
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
    	// User user = (User)session.getAttribute("user");
    	
    	String groupID = req.getParameter("groupID");
    	String creatorID = req.getParameter("userID");
    	
    	String groupName  = req.getParameter("groupName");
    	String groupDesc  = req.getParameter("groupDesc");
    	
    	Group group;
    	//if( user != null )
    	//	group = new Group(groupName, groupDesc, user.getUserID() );
    	//else
    		group = new Group(groupName, groupDesc, 1 );
    	
    	//Save group
    	if( groupID.equals("-1") ){
     
        	//GroupID of the currently saved group is going to be returned as result.
        	//-1 is returned if there is an error saving group
    		//The output format is as follows:
    		// {"DATA":{"GroupID":68},"STATUS":"Success"}
            
            JSONObject result = new JSONObject();
    		JSONObject data = new JSONObject();
    		
    		int transaction = DBUtilGroup.saveGroup( group );
    		if( transaction == -1 )
    			result.put( "STATUS", "Failure" );		
    		else
    			result.put( "STATUS", "Success");
    		
    		data.put( "GroupID", transaction );
    		result.put( "DATA",  data );
    		
            out.println( result );
            
    	}
    	//Modify group
    	else{
    		group.setId( Integer.valueOf( groupID.trim() ) );
    		
    		JSONObject result = new JSONObject();
    		JSONObject data = new JSONObject();
    		
    		int transaction = DBUtilGroup.modifyGroup( group );
    		
    		if( transaction == -1 )
    			result.put( "STATUS", "Failure" );		
    		else
    			result.put( "STATUS", "Modified Successfully");
    		
    		data.put( "GroupID", groupID.trim() );
    		result.put( "DATA",  data );
    		
            out.println( result );
    	}
    	       
    }
}
