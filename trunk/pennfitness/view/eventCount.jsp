<%@ page import="model.DBUtilRoute, entities.Paging, org.json.simple.JSONObject" %>

<% 
	String routeID = request.getParameter("routeID");
	
	if( routeID != null ){
		int eventCount = DBUtilRoute.getEventCountByRouteId( routeID ); 

//	  	 Output format
//	  	 {
//	  		 "STATUS":"Success",
//	  		 "DATA": {
//	  			"EVENT_COUNT": 4
//			 }
//	  	  } 	
		
		JSONObject data = new JSONObject();
		data.put( "EVENT_COUNT", eventCount);
		
	  	JSONObject result = new JSONObject();
	  	result.put("STATUS","Success");
	  	result.put("DATA",data );  	
		
		out.println(result);
		
	}
%>