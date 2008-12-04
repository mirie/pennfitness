package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Paging;
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
		
		User user = (User)session.getAttribute("user");
		Group group;
		
		String userID = null;
		if (user != null) userID   = user.getUserID();
		String groupID = req.getParameter("groupID");
		String action = req.getParameter("action");
		String notify = req.getParameter("notify") == null ? "N" : req.getParameter("notify");

		
    	if("getMyCreatedGroups".equalsIgnoreCase(action))	//Get groups for a particular user
    	{
    		if(userID != null){
				StringBuffer sbuf = new StringBuffer();
				
				List<QueryParameter> params = new ArrayList<QueryParameter>();
				DBUtil.addQueryParam(params, userID, " creatorID", " = '"+userID+"'" );
				
				// For paging
				Paging paging = new Paging(req, DBUtilGroup.getGroupByUserIDCount( params )); 
	
				if( paging.getTotalRecordCnt() > 0 ) {
					List<Group> groupList = DBUtilGroup.getGroupByUserID(userID, paging.getRecsPerPage(), paging.getCurPage()); 	
					Iterator<Group> iterator = groupList.iterator();
					
					while( iterator.hasNext() ){
						group = iterator.next();
						
						sbuf.append( "<div class=\"groupDetailedInfo\"><input type=\"radio\" name=\"groupID\" id=\"selectedRadioGroup\"value=\""+group.getId()+"\"><b>"+group.getName()+"</b><br>\n" )
						.append("created by ").append(group.getCreatorID())
						.append(" and active since ").append(group.getCreatedDate())
						.append(" with ").append(DBUtilGroup.getMemberCount( group.getId())).append(" member(s)<br>\n")
						.append("Description :").append(group.getDescription()).append("<br>\n")
						.append("Member(s)     :").append(DBUtilGroup.getMemberList( group.getId() )).append("<br></div>");
					}
				}			
				JSONObject data = new JSONObject();
				
		  		result.put("STATUS","Success");
		  		result.put("DATA", sbuf.toString() );
				data.put("CURPAGE", paging.getCurPage());
				data.put("TOTALRECCNT", paging.getTotalRecordCnt());
	
				out.println(result);
	
				return;	
    		}
    	}
    	
    	if("getMyRegisteredGroups".equals(action)){
    		
    		if(userID != null){
				StringBuffer sbuf = new StringBuffer();
				
				// For paging
				Paging paging = new Paging(req, DBUtilGroup.getGroupRegisteredByUserIDCount(userID)); 
	
				if( paging.getTotalRecordCnt() > 0 ) {
					List<Group> groupList = DBUtilGroup.getGroupRegisteredByUserID(userID, paging.getRecsPerPage(), paging.getCurPage()); 	
					Iterator<Group> iterator = groupList.iterator();
					
					while( iterator.hasNext() ){
						group = iterator.next();
						
						sbuf.append( "<div class=\"groupRegisteredDetailedInfo\"><input type=\"radio\" name=\"groupID\" id=\"selectedRadioGroupRegistered\"value=\""+group.getId()+"\"><b>"+group.getName()+"</b><br>\n" )
						.append("created by ").append(group.getCreatorID())
						.append(" and active since ").append(group.getCreatedDate())
						.append(" with ").append(DBUtilGroup.getMemberCount( group.getId())).append(" member(s)<br>\n")
						.append("Description :").append(group.getDescription()).append("<br>\n")
						.append("Member(s)     :").append(DBUtilGroup.getMemberList( group.getId() )).append("<br></div>");
					}
				}			
				JSONObject data = new JSONObject();
				
		  		result.put("STATUS","Success");
		  		result.put("DATA", sbuf.toString() );
				data.put("CURPAGE", paging.getCurPage());
				data.put("TOTALRECCNT", paging.getTotalRecordCnt());
	
				out.println(result);
	
				return;	
    		}
    	}
    	
    	if("getGroupsForCreateEvent".equalsIgnoreCase(action))	//Get groups for a particular user
    	{
    		if( groupID == null ){
    			result.put("STATUS", "Failure");
    			result.put("MSG", "Error handling action="+action);
    			
    			out.print(result);
    			return;
    		}
    		
			List<Group> groupList = DBUtilGroup.getGroupListByUserIDForEvent(userID); 	
			Iterator<Group> iterator = groupList.iterator();
		
			String resultStr = "";
			while(iterator.hasNext()){
				group = iterator.next();
				resultStr += group.getId() + "-" + group.getName() + ";";
			}
			
			JSONObject data = new JSONObject();
			data.put("GROUPS", resultStr);
			
	  		result.put("STATUS","Success");
		  	result.put("DATA",data );

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
		group = DBUtilGroup.getGroupById(groupID);
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
    	else if("sendEmailToGroup".equalsIgnoreCase(action)){
    		String title = req.getParameter("emailTitle");
    		String text = req.getParameter("emailText");
    		
    		if( title == null || text == null ){
    			result.put("STATUS", "Failure");
    			result.put("MSG", "missing data for sending email");
    			return;
    		}
    		else{
    			if(DBUtilGroup.sendEmailToGroupMembers(title, text, group.getId())){
    				result.put("STATUS", "Success");
    				result.put("MSG", "Email successfully sent to members of \""+group.getName()+"\"");
    				out.print(result);
    				return;
    			}
    			else{
    				result.put("STATUS", "Failure");
        			result.put("MSG", "Error sending email to group members");
        			out.print(result);
        			return;
    			}
    		}
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