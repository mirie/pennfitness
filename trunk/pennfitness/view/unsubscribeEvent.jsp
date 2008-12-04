<%@ page import="java.util.List, java.util.ArrayList, java.util.Iterator, entities.User, entities.Event, model.DBUtilEvent, org.json.simple.JSONObject" %>

<%
	JSONObject result = new JSONObject();
	
	User user =(User) session.getAttribute("user");	
	String events = request.getParameter("eventList");	
    
   if (user == null) { // not logged in        
		result.put("STATUS","Failure"); // Should not happen...
		result.put("MSG", "You must be logged in to unsubscribe from an event."); 
		out.println(result);
		return;
   } 
	
	if (events != null && events.length() > 0 ){ // must have some events!
		JSONObject data = new JSONObject();	 
		
		// eventList => eventID1;eventID2;eventID3
		List<String> eventIDs = new ArrayList<String>();
		
		String[] eventList = events.split(";");
		for (int i = 0; i < eventList.length; i++) {
			eventIDs.add(eventList[i]);
		}
	
		String subscribedEvents = DBUtilEvent.unsubscribeFromEvents(eventIDs, user.getUserID());

		data.put("EVENTS_SUBSCRIBED", subscribedEvents);
		result.put("STATUS","Success");
		result.put("DATA" , data);
	}
	else {
		result.put("STATUS","Failure");
		result.put("MSG", "You must select an event to unsubscribe from!");
	}  	
		
	out.println(result);
		
%>