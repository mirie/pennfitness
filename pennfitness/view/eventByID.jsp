
<%@ page import="java.util.*, model.*, entities.Event, entities.Group, org.json.simple.JSONObject" %>

<% 
	String eventID = request.getParameter("eventID");
	JSONObject result = new JSONObject();
	
	if( eventID != null ){
		Event event = DBUtilEvent.getEventById( eventID ); 
		Group group = DBUtilGroup.getGroupById(event.getGroupID() + "");
		
		JSONObject data = new JSONObject();
		data.put( "EVENT_ID", new Integer( event.getEventID() ));
		data.put( "EVENT_NAME", event.getName());
		data.put( "EVENT_DESCRIPTION", event.getDescription());
		data.put( "EVENT_GROUP_ID", new Integer( event.getGroupID() ) );
  		data.put( "EVENT_PUBLICITY", event.getPublicity() + "") ;
		data.put( "EVENT_ROUTE_ID", new Integer( event.getRouteID() ) );
		data.put( "EVENT_EVENT_TYPE_ID", new Integer( event.getEventTypeID() ) );
		data.put( "EVENT_DATE", event.getEventDate() + "");
		data.put( "EVENT_DURATION", new Float( event.getDuration() ) );
		data.put( "EVENT_TIME", event.getEventTime() + "" );
		data.put( "EVENT_CREATOR_ID", event.getCreatorID());
		if (group != null)
			data.put( "GROUP_NAME", group.getName());
	  	
	  	result = new JSONObject();
	  	result.put("STATUS","Success");
	  	result.put("DATA",data );
	}
	else {
		result.put("STATUS","Failure");
		result.put("MSG", "");
	}
	  	
	  	
//	  	 Output format
//	  	 {
//	  		"DATA":
//	  		{
//	  		 "EVENT_GROUP_ID":1,
//	  		 "EVENT_NAME":"first event",
//	  		 "EVENT_DURATION":5.2,
//	  		 "EVENT_DATE":2008-11-12,
//	  		 "EVENT_DESCRIPTION":"desc",
//	  		 "EVENT_ID":1,
//	  		 "EVENT_ROUTE_ID":1,
//	  		 "EVENT_EVENT_TYPE_ID":1,
//	  		 "EVENT_TIME":"",
//	  		 "EVENT_PUBLICITY":y,
//			 "EVENT_CREATOR_ID" : defaultUser
//	  		},
//	  		 "STATUS":"Success"
//	  	  } 	  	
		
		out.println(result);
		
%>