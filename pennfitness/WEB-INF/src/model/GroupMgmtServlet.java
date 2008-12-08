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



public class GroupMgmtServlet extends HttpServlet{
	
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
    	User user = (User)session.getAttribute("user");
		
    	JSONObject result = new JSONObject();
    	String groupID = req.getParameter("groupID");
    	String userID = null;
    	if (user != null) 
    		userID = user.getUserID();
    	
    	String groupName  = req.getParameter("groupName");
    	String groupDesc  = req.getParameter("groupDesc");
    	
    	String action = req.getParameter("action");
    	//check if action is null
    	if( userID == null )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "You must be logged in to manage your groups.");
			
			out.print(result);
			return;
		}
    	//check if action is null
    	if( action == null )
		{
			result.put("STATUS", "Failure");
			result.put("MSG", "action cannot be null");
			
			out.print(result);
			return;
		}
    	Group group;
    	
    	
    	//if( user != null )
    	//	group = new Group(groupName, groupDesc, user.getUserID() );
    	//else
    	if("register".equalsIgnoreCase(action))	//Save Group
    	{
        	
    		//check if group name is null
    		if( groupName == null )
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Group name cannot be null when registering a group");
    			
    			out.print(result);
    			return;
    		}
    		// Check whether the group name exists
    		if( DBUtilGroup.checkGroupByName(groupName) )
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Group name " + groupName + " already exists.");
    			
    			out.print(result);
    			return;
    		}
    		group = new Group(groupName, groupDesc, userID );
        	//-1 is returned if there is an error saving group
    		//The output format is as follows:
    		// {"DATA":{"GroupID":68},"STATUS":"Success"}
    		//group.setId(Integer.valueOf( groupID.trim()));
    		JSONObject data = new JSONObject();  		
    		int transaction = DBUtilGroup.saveGroup( group );
    		if( transaction == -1 ) {
    			result.put( "STATUS", "Failure" );
    			result.put( "MSG", "Failed to save group");
    		}
    		else {
    			result.put( "STATUS", "Success");
	    		data.put( "GroupID", transaction );
	    		result.put( "DATA",  data );
    		}
    		
            out.println( result );
    	}
    	else if("modify".equalsIgnoreCase(action))	//Modify Group
    	{
        	//check if groupID is null
        	if( groupID == null )
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Have to have groupID for groupMgmt");
    			
    			out.print(result);
    			return;
    		}
    		// Check whether the group with given ID exists
    		if( !DBUtilGroup.checkGroupByID(groupID) )
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Group with groupId " + groupID + " does not exist.");
    			
    			out.print(result);
    			return;
    		}
    		group = DBUtilGroup.getGroupById(groupID);
    		if(groupName!=null)
    			group.setName(groupName);
    		if(groupDesc!=null)
    			group.setDescription(groupDesc);
    		//check if the current user is the creator
    		if( ! userID.equalsIgnoreCase( group.getCreatorID())  )
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Only the creator can modify a group. UserId here is "+ userID + ", and creatorID is: "+ group.getCreatorID());
    			
    			out.print(result);
    			return;
    		}

    		//group.setId( Integer.valueOf( groupID.trim() ) );
    		JSONObject data = new JSONObject();
    		int transaction = DBUtilGroup.modifyGroup( group );
    		if( transaction == -1 ) {
    			result.put( "STATUS", "Failure" );
    			result.put( "MSG", "Failed to save group");
    		}
    		else {
    			result.put( "STATUS", "Success");
	    		data.put( "GroupID", groupID.trim() );
	    		result.put( "DATA",  data );
    		}
    		
            out.println( result );
    	}
    	else if("delete".equalsIgnoreCase(action))	//Delete Group
    	{
        	//check if groupID is null
        	if( groupID == null )
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Have to have groupID for groupMgmt");
    			
    			out.print(result);
    			return;
    		}
    		// Check whether the group with given ID exists
    		if( !DBUtilGroup.checkGroupByID(groupID) )
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Group with groupId " + groupID + " does not exist.");
    			
    			out.print(result);
    			return;
    		}
    		group = DBUtilGroup.getGroupById(groupID);
    		//check if the current user is the creator
    		if( !userID.equalsIgnoreCase( group.getCreatorID())  )
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Only the creator can modify a group");
    			
    			out.print(result);
    			return;
    		}
    		JSONObject data = new JSONObject();      
    		// Delete Group entity from database
    		if( DBUtilGroup.deleteGroup(group)<0 )
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Group Registration failed.");
    			
    			out.print(result);
    			return;
    		}

    		// Delete GroupReg entity from database
    		if( DBUtilGroup.deleteGroupReg(groupID)<0 )
    		{
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Group Registration failed.");
    			
    			out.print(result);
    			return;
    		}

    		result.put("STATUS", "Success");
    		result.put("MSG", "GroupReg with groupID: " + groupID +" is deleted from database succesfully.");
    		
    		out.print(result);
    		return;  
    	}
    	
    	else if( "getDetailGroupInfo".equals(action)){
    		
    		group = DBUtilGroup.getGroupById( groupID );
    		if( group == null ){
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Error in getting detailed group information");
    		}
    		else{
    			result.put("STATUS", "Success");
    			
    			JSONObject data = new JSONObject();
    			data.put("NAME", group.getName());
    			data.put("CREATE_DATE", group.getCreatedDate());
    			data.put("CREATED_BY",group.getCreatorID());
    			data.put("DESCRIPTION",group.getDescription());
    			data.put("MEMBER_COUNT", new Integer( DBUtilGroup.getMemberCount( group.getId() ) ) );
    			
    			result.put("DATA", data);
    			
    			System.out.println(result.get("DATA"));
    			out.print(result);
    			return;
    		}
    		
    	}
    }
}
