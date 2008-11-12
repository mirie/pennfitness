
<%@ page import="java.util.List, java.util.Iterator, model.DBConnector, model.DBUtilRoute, entities.Route" %>

<% 
	List<Route> routes = DBUtilRoute.getAllRoutes(); 	
	Iterator<Route> iterator = routes.iterator();
	while(iterator.hasNext()){
		out.print(iterator.next().getName()+";");
	}
%>