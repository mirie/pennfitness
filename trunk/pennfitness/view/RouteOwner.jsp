
<%@ page import="java.util.*, model.*, entities.Route, entities.User, org.json.simple.JSONObject" %>

<% 
	String routeID = request.getParameter("routeID");

	JSONObject result = new JSONObject();
	
	if( routeID != null ){
		Route route = DBUtilRoute.getRouteById( routeID ); 
		
		User user = (User) session.getAttribute("user");
		JSONObject data = new JSONObject();
		
		if (user != null && user.getUserID().equals(route.getCreatorID())) {
			result.put("STATUS","Success");
			data.put("IS_OWNER","true");
		  	result.put("DATA",data );
		} else {
			result.put("STATUS","Failure");
			result.put("MSG", "You do not have priviledges to modify this route.");
			data.put("IS_OWNER","true");
		  	result.put("DATA",data );
		}
	}
	else {
		result.put("STATUS","Failure");
		result.put("MSG", "Unknown error");
	}		
	
	out.println(result);

%>