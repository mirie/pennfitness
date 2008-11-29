<%@ page import="java.util.List, java.text.*, java.util.Iterator, model.DBUtilEvent, entities.Event, entities.Paging, org.json.simple.JSONObject" %>

<%
	String year = request.getParameter("year");
	String month = request.getParameter("month");

  	JSONObject result = new JSONObject();

  	if( year == null || year.length() == 0 || month == null || month.length() == 0 ) {
	  	result.put("STATUS","Failure");
	  	result.put("MSG", "Invalid arguments");
	}
	else {
		try {
		  	result.put("STATUS","Success");
		  	result.put("MSG", "");
	  		result.put("DATA", DBUtilEvent.getEventDatesByMonth(Integer.parseInt(year), Integer.parseInt(month)));
		} 
		catch(Exception ex)
		{
		  	result.put("STATUS","Failure");
		  	result.put("MSG", "Invalid arguments");
		}
	}
	
	out.println(result);

%>