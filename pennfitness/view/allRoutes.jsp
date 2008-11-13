
<%@ page import="java.util.List, java.util.Iterator, model.DBConnector, model.DBUtilRoute, entities.Route" %>

<% 
	List<Route> routes = DBUtilRoute.getAllRoutes(); 	
	Iterator<Route> iterator = routes.iterator();
	
	Route route;
	Strng result = "";
	while(iterator.hasNext()){
		route = iterator.next();
		result += route.getId() + "-" + route.getName() + ";"
	}
	
	out.println(result);
%>