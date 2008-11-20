
<%@ page import="java.util.List, java.util.Iterator, model.DBConnector, model.DBUtilRoute, entities.Route, org.json.simple.JSONObject" %>

<% 
	List<Route> routes = DBUtilRoute.getAllRoutes(); 	

	Iterator<Route> iterator = routes.iterator();
	
	Route route;
	String resultStr = "";
	while(iterator.hasNext()){
		route = iterator.next();
		resultStr += route.getId() + "-" + route.getName() + ";" ;
	}
	
	JSONObject data = new JSONObject();
	data.put("ROUTES"resultStr);
	
  	JSONObject result = new JSONObject();
  	if( resultStr.equals("") )
  		result.put("STATUS","Failure");
  	else
  		result.put("STATUS","Success");
  	result.put("DATA",data );
  	
  		// The output is of format
		// {"STATUS":"Success",
		//  "DATA":
		//    {
		// 	   "ROUTES:"1-rt1;..."
		// 	  }
		//  } 
	
	out.println(result);
%>