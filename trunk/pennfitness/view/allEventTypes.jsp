<%@ page import="java.util.List, java.util.Iterator,  model.DBUtilEventType, entities.EventType, org.json.simple.JSONObject" %>

<% 
	List<EventType> eventTypeList = DBUtilEventType.getAllEventTypes(); 	
	Iterator<EventType> iterator = eventTypeList.iterator();
	
	EventType eventType;
	String resultStr = "";
	while(iterator.hasNext()){
		eventType = iterator.next();
		resultStr += eventType.getEventTypeID() + "-" + eventType.getDescription() + ";";
	}
	
	JSONObject data = new JSONObject();
	data.put("EVENT_TYPES", resultStr);
	
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
		// 	   "EVENT_TYPES:"1-evtTypeDesc;..."
		// 	  }
		//  } 
	
	out.println(result);
%>