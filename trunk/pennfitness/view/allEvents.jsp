<%@ page import="java.util.List, java.text.*, java.util.Iterator, model.DBUtilEvent, entities.Event, entities.Paging, org.json.simple.JSONObject" %>

<%
	// For paging
	Paging paging = new Paging(request, DBUtilEvent.getAllEventsCount() ); 

	String content = "";
	
	if( paging.getTotalRecordCnt() > 0 ) {

		content = DBUtilEvent.getAllEventsHTML(paging.getRecsPerPage(), paging.getCurPage());
	}
	
	JSONObject data = new JSONObject();
	data.put("CONTENT", content);
	data.put("CURPAGE", paging.getCurPage());
	data.put("TOTALRECCNT", paging.getTotalRecordCnt());
	
  	JSONObject result = new JSONObject();
  	// Can fail??
  	result.put("STATUS","Success");
  	result.put("MSG", "");
  	
  	result.put("DATA",data );
  	
	
	out.println(result);

//	List<Event> events = DBUtilEvent.getAllEvents(); 	
//	Iterator<Event> iterator = events.iterator();
//	
//	Event event;
//	String resultStr = "";
//	while(iterator.hasNext()){
//		event = iterator.next();
//		resultStr += event.getEventID() + "-" + event.getName() + ";";
//	}
//	
//	JSONObject data = new JSONObject();
//	data.put("EVENTS", resultStr);
//	
// 	JSONObject result = new JSONObject();
//  	if( resultStr.equals("") )
//  		result.put("STATUS","Failure");
// 	else
//  		result.put("STATUS","Success");
//  	result.put("DATA",data );
//  	
// 		// The output is of format
//		// {"STATUS":"Success",
//		//  "DATA":
//		//    {
//		// 	   "EVENTS:"1-evt1;..."
//		// 	  }
//		//  } 
//	
//	out.println(result);
%>