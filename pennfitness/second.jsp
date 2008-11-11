
<%@ page import="java.util.List, java.util.Iterator, model.DBConnector, model.DBFunctionUtil, entities.Route" %>

<% 
	List<Route> routes = DBFunctionUtil.getAllRoutes(); 	
	Iterator<Route> iterator = routes.iterator();
	while(iterator.hasNext()){
		out.print(iterator.next().getName()+";");
	}
%>