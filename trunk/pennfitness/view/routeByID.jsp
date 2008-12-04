
<%@ page import="java.util.*, model.*, entities.Route, entities.User, org.json.simple.JSONObject" %>

<% 
	String routeID = request.getParameter("routeID");

	JSONObject result = new JSONObject();
	
	if( routeID != null ){
		Route route = DBUtilRoute.getRouteById( routeID ); 

		System.out.println(route.getName());

		JSONObject data = new JSONObject();
		data.put("ROUTE_NAME",route.getName());
		data.put("ROUTE_COLOR",route.getColor());
		data.put("ROUTE_PTS",route.getPtValues());
		data.put("ROUTE_DESCRIPTION", route.getDescription());
		data.put("ROUTE_DATE", route.getCreatedDate()+"" );
		data.put("ROUTE_RATING", new Float( route.getPt_rate() ));
		
		//System.out.println("Creator : " + route.getCreatorID());
		
	//	User user = DBUtilUser.getUserById( route.getCreatorID()+"" );
	//	if( user != null )
			data.put("ROUTE_CREATOR", route.getCreatorID());
	//	else
		//	data.put("ROUTE_CREATOR", "");
				
	  	
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
	}
	else {
		result.put("STATUS","Failure");
		result.put("MSG", "Unknown error");
	}		
	
	out.println(result);

%>