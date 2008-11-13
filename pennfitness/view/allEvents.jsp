<%@ page import="java.util.List, java.util.Iterator,  model.DBUtilEvent, entities.Event" %>

<% 
	List<Event> events = DBUtilEvent.getAllEvents(); 	
	Iterator<Event> iterator = events.iterator();
	
	Event event;
	Strng result = "";
	while(iterator.hasNext()){
		event; = iterator.next();
		result += event.getEventID() + "-" + event.getName() + ";"
	}
	
	out.println(result);
%>