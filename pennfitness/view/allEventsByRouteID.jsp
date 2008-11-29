<%@ page import="java.util.List, java.text.*, java.util.Iterator, model.DBUtilEvent, entities.Event, entities.Paging, org.json.simple.JSONObject" %>

<%
	String routeID = request.getParameter("routeID");

	int eventCount = DBUtilEvent.getEventCountByRouteId(routeID); 
	
	Paging paging = new Paging(req, eventCount);
	
	
	List<Event> events = DBUtilEvent.getEventsByRouteId(routeID);
	
	//String eventList = DBUtilEvent.getAllEventsHTML(5, 1, true, routeID);
	
	
	JSONObject data = new JSONObject();
	data.put("EVENT_COUNT", eventCount );
	//data.put("EVENT_LIST", eventList);
	
  	JSONObject result = new JSONObject();
  
  	result.put("STATUS","Success");
  	result.put("MSG", "");
  	
  	result.put("DATA",data );
  	
	
	out.println(result);
%>