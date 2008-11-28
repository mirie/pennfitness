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

import org.json.simple.JSONObject;

import entities.Group;


public class GroupSearchServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {    
		doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {      
    	JSONObject result = new JSONObject();
    	PrintWriter out = resp.getWriter();
    	
    	String keyword = req.getParameter("keyword");    	
    	String user	   = req.getParameter("member");
    	
    	List<QueryParameter> params = new ArrayList<QueryParameter>();
    	
		DBUtil.addQueryParam(params, keyword, " ( name", " LIKE '%"+keyword+"%' OR  description LIKE '%"+keyword+"%')" ); 
		DBUtil.addQueryParam(params, user, " creatorID", " LIKE '%"+user+"%'" );

    	List<Group> groups = DBUtilGroup.searchForGroups( params, 5, 1 );
    	  	
    	if( groups != null ){
    	
	    	Iterator<Group> iterator = groups.iterator();
	    	
	    	StringBuffer sbuf = new StringBuffer();
	    	sbuf.append("<div class = \"Result\">\n").
	    		 append("\t<div class=\"myGroupList\">\n");

	    	Group group;
	    	int counter = 1;
	    	String desc;
	    	while( iterator.hasNext() ){
	    		group = iterator.next();
	    		
	    		if( group.getDescription().length() > 20 ){
	    			desc = group.getDescription().substring(0,20) +"...";
	    		}
	    		else
	    			desc = group.getDescription();
	    		
	    		sbuf.append("\t\t<div class=\"myGroupItem\">\n").
	    			 append("\t\t\t<span class=\"number\">"+ counter++ +"</span>").
	    			 append("\t\t\t<a href=\"function_to_show_event\">"+ group.getName() +"</a>\n").
	    			 append("\t\t\t<span class=\"createdDate\"> since "+group.getCreatedDate()+"</span>").
	    			 append("\t\t\t<span class=\"createdBy\"> by "+ group.getCreatorID() +"</span>").
	    			 append("<p>"+desc+"</p>").
	    			 append("\t\t</div>\n");
	    	}
	    	
	    	sbuf.append("\t</div>\n").
	    		 append("</div>\n");
	    	
	    	
	    	result.put( "STATUS", "Success");
	    	result.put( "DATA", sbuf.toString() );
	    	
    	}
    	else{
    		result.put( "STATUS", "Failure");
    		//TODO
    		//I'll put more detailed MSG here
    		result.put( "MSG", "Error in getting group search results DB" );
    	}
    	
    	out.println( result );
    	       
    }		
	
}
