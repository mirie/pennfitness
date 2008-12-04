<%@ page import="entities.User, entities.Event, model.DBUtilEvent, org.json.simple.JSONObject" %>

<% 
	JSONObject result = new JSONObject();	

	String eventID = request.getParameter("eventID");	
    User user =(User) session.getAttribute("user");
    
    if (user == null) { // not logged in        
		result.put("STATUS","Failure");
		result.put("MSG", "You must be logged in to register for an event."); 
		out.println(result);
		return;
    } 
	
	if (eventID != null){
		JSONObject data = new JSONObject();	 
		boolean registerResult = DBUtilEvent.registerForEvent(eventID, user.getUserID());
		
		if (registerResult) {
			result.put("STATUS","Success");
		}
		else {
			result.put("STATUS","Failure");
			result.put("MSG", "You are already registered for this event.");
		}
	}
	else {
		result.put("STATUS","Failure");
		result.put("MSG", "You must select an event before registering"); // should never happen
	}
		
	out.println(result);
		
%>