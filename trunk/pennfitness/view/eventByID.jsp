
<%@ page import="java.util.*, model.*, entities.Event, org.json.simple.JSONObject" %>

<% 
	String eventID = request.getParameter("eventID");
	
	if( eventID != null ){
		Event event = DBUtilEvent.getEventById( eventID ); 

		JSONObject data = new JSONObject();
		data.put( "EVENT_ID", new Integer( event.getEventID() ));
		data.put( "EVENT_NAME", event.getName());
		data.put( "EVENT_DESCRIPTION", event.getDescription());
		data.put( "EVENT_GROUP_ID", new Integer( event.getGroupID() ) );
		data.put( "EVENT_PUBLICITY", new Character( event.getPublicity() ) ) ;
		data.put( "EVENT_ROUTE_ID", new Integer( event.getRouteID() ) );
		data.put( "EVENT_EVENT_TYPE_ID", new Integer( event.getEventTypeID() ) );
		data.put( "EVENT_DATE", event.getEventDate() );
		data.put( "EVENT_DURATION", new Float( event.getDuration() ) );
		data.put( "EVENT_TIME", event.getEventTime() );
		
	  	
	  	JSONObject result = new JSONObject();
	  	result.put("STATUS","Success");
	  	result.put("DATA",data );
	  	
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
//	  		 "EVENT_PUBLICITY":y
//	  		},
//	  		 "STATUS":"Success"
//	  	  } 	  	
		
		out.println(result);
		
	}
%>