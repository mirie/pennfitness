
<%@ page import="java.util.*, model.*, entities.Route" %>

<% 
	String routeName = request.getParameter("routeName");
	
	if( routeName != null ){
		Route route = DBUtilRoute.getRouteById( routeName ); 

%>
		<!--Map map = new HashMap();
		map.put( "routeName", route.getName() );
		map.put( "routeColor", route.getColor() );
		map.put( "routeInfo",route.getRouteInfo() );
		
		try{	
			JSONObject jsonObject = JSONObject.fromObject( map );
			out.println( jsonObject ); 
		}
		catch(Exception e){
			out.println(e.toString());
		}-->
<%		
		out.println( route.getName() + ";" + route.getColor() + ";" + route.getPtValues() );
	}
%>