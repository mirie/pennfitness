
<%@ page import="java.util.*, model.*, entities.Route, org.json.simple.JSONObject" %>

<% 
	String routeID = request.getParameter("routeID");
	
	if( routeID != null ){
		Route route = DBUtilRoute.getRouteById( routeID ); 

		System.out.println(route.getName());

		JSONObject data = new JSONObject();
		data.put("ROUTE_NAME",route.getName());
		data.put("ROUTE_COLOR",route.getColor());
		data.put("ROUTE_PTS",route.getPtValues());
	  	
	  	JSONObject result = new JSONObject();
	  	result.put("STATUS","Success");
	  	result.put("DATA",data );
	  	
		
		// The output is of format
		// {"STATUS":"Success",
		//  "DATA":
		//    {
		// 	   "ROUTE_NAME":"drfft",
		// 	   "ROUTE_PTS":"39.9558,-75.17420768737793;39.94840452554649,
		// 	   "ROUTE_COLOR":"#0000AF"
		// 	  }
		//  } 
		
		out.println(result);
		
	}
%>