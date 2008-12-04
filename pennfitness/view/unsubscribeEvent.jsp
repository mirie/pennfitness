<%@ page import="java.util.List, java.util.ArrayList, java.util.Iterator, entities.User, entities.Event, model.DBUtilEvent, org.json.simple.JSONObject" %>

<%
	JSONObject result = new JSONObject();
	
	User user =(User) session.getAttribute("user");	
	String events = request.getParameter("eventList");	
		
    int recordPerPage = 10;
    int currentPage = 1; 
    
    
    String rpp = request.getParameter("recordPerPage");
   	String cp = request.getParameter("currentPage"); 
    
    if (rpp != null) {
   		recordPerPage = new Integer(rpp);
   	}
    
    if (cp != null) {
    	currentPage = new Integer(cp);
    }
    

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
	
		List<Event> subscribedEvents = DBUtilEvent.unsubscribeFromEvents(eventIDs, user.getUserID());
		//List<Event> subscribedEvents = DBUtilEvent.unsubscribeFromEvents(eventIDs, "maiirie");
		
		StringBuffer sb = new StringBuffer();
		Event event;
		Iterator<Event> iterator = subscribedEvents.iterator();

		int cnt = (currentPage-1)*recordPerPage + 1;
		while(iterator.hasNext()){
			event = iterator.next();
			
			sb.append("<div class=\"SubscribedEvents\">\n").
			append((cnt++)+ ". <input type=\"checkbox\" name=\"subscribedEvents\" value=\"" + event.getEventID() + "\"> " + 
							"<a href=\"javascript:YAHOO.pennfitness.float.getEventLeftTB(" + event.getEventID() + "," + event.getRouteID() + ")\" class=\"AEREventName\">" + "</a> on <span class=\"AERDate\">" + event.getEventDate() + "</span>\n</div>\n");
		}
		
		data.put("EVENTS_SUBSCRIBED", sb.toString());
		result.put("STATUS","Success");
		result.put("DATA" , data);
	}
	else {
		result.put("STATUS","Failure");
		result.put("MSG", "You must select an event to unsubscribe from!");
	}  	
		
	out.println(result);
		
%>