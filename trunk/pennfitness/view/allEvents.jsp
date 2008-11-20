<%@ page import="java.util.List, java.util.Iterator,  model.DBUtilEvent, entities.Event, org.json.simple.JSONObject" %>

<% 
	List<Event> events = DBUtilEvent.getAllEvents(); 	
	Iterator<Event> iterator = events.iterator();
	
	Event event;
	String resultStr = "";
	while(iterator.hasNext()){
		event = iterator.next();
		resultStr += event.getEventID() + "-" + event.getName() + ";";
	}
	
	JSONObject data = new JSONObject();
	data.put("EVENTS", resultStr);
	
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
		// 	   "EVENTS:"1-evt1;..."
		// 	  }
		//  } 
	
	out.println(result);
%>