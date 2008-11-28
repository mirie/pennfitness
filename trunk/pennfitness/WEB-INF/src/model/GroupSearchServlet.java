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
import entities.Paging;
import entities.Route;

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
    	StringBuffer sbuf = new StringBuffer();
   	
    	String keyword = req.getParameter("keyword");    	
    	String creatorID = req.getParameter("creatorID");
    	
    	List<QueryParameter> params = new ArrayList<QueryParameter>();
    	
		DBUtil.addQueryParam(params, keyword, " ( name", " LIKE '%"+keyword+"%' OR  description LIKE '%"+keyword+"%')" ); 
		DBUtil.addQueryParam(params, creatorID, " creatorID", " LIKE '%"+creatorID+"%'" );

		// For paging
		Paging paging = new Paging(req, DBUtilGroup.getSearchForGroupsCount(params));
		
		if( paging.getTotalRecordCnt() > 0 ) {
			List<Group> groups = DBUtilGroup.searchForGroups( params, paging.getRecsPerPage(), paging.getCurPage() );			  	
			if( groups != null ){
		    	Iterator<Group> iterator = groups.iterator();
		    	
		    	Group group;
				int cnt = (paging.getCurPage()-1)*paging.getRecsPerPage() + 1;
				
		    	while( iterator.hasNext() ){
		    		group = iterator.next(); 		

		    		sbuf.append("\t\t<div class=\"myGroupItem\">\n").
	    			 append("\t\t\t<span class=\"number\">"+ (cnt++) +".</span>").
	    			 append("\t\t\t<a href=\"javascript:joinGroup(" + group.getId() + ")\" title=\"Join this group\">"+ group.getName() +"</a>\n").
	    			 append("\t\t\t<span class=\"createdDate\"> since "+group.getCreatedDate()+"</span>").
	    			 append("\t\t\t<span class=\"createdBy\"> by "+ group.getCreatorID() +"</span>").
	    			 append("<p>"+group.getDescription()+"</p>").
	    			 append("\t\t</div>\n");
		    	}
			}
		} // end of if( totalRecordCnt > 0 ) {

		JSONObject data = new JSONObject();
		data.put("CONTENT", sbuf.toString());
		data.put("CURPAGE", paging.getCurPage());
		data.put("TOTALRECCNT", paging.getTotalRecordCnt());

		// Can this fail?
    	result.put( "STATUS", "Success");
    	result.put( "DATA", data );
    	
		
/*
		if( paging.getTotalRecordCnt() > 0 ) {
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
*/    	
    	out.println( result );
    	       
    }		
	
}
