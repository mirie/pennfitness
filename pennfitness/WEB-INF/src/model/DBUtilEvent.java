package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import entities.Event;
import entities.Route;
import util.StringUtil;

public class DBUtilEvent {

	
	/**
	 * Saves a new event with the given information
	 * 
	 * @param event
	 * @return the ID of newly inserted event if transaction is successful. -1 if not.
	 */
	public static int saveEvent( Event event ){
					
		String saveQuery = 
			"INSERT INTO Event ( name, description, groupID, publicity, creatorID, createdDate, modifiedDate, " +
								"routeID, eventTypeID, eventDate, eventTime, duration  )"+
							   " VALUES ('"+ event.getName()+"'," +
										"'"+ event.getDescription()+"'," +
										"'"+ event.getGroupID()+"'," +
										"'"+ event.getPublicity()+"'," +
										"'"+ event.getCreatorID()+"'," +
										" NOW(), " + 
										" NOW(), " +
										"'"+ event.getRouteID() +"'," +
										"'"+ event.getEventTypeID() +"'," +
										"'"+ event.getEventDate() +"'," +
										"'"+ event.getEventTime() +"'," +
										"'"+ event.getDuration() +"')"; 
												
		if( DBConnector.executeUpdateQuery( saveQuery ) > 0 ){
			
			try {
				ResultSet  result = DBConnector.getQueryResult( "SELECT MAX( eventID ) FROM Event" );
				if( result != null )
					if( result.next() )
						return result.getInt( 1 );
			} catch (SQLException e) {
				System.out.println("DBUtilEvent.saveEvent() : Error getting ID of saved event"  );
				e.printStackTrace();
			}
			
		}
			
		return -1;
	}
	
	
	/**
	 * Updates the given event with the given ID.
	 * 
	 * @param event
	 * @return 1 if successful, -1 otherwise.
	 */
	public static int modifyEvent( Event event ){
		
		String updateQuery = 
			"UPDATE Event " +
			"SET name='" + event.getName() + "'," +
			    "duration='" + event.getDuration() + "'," +
			    "description='" + event.getDescription() + "'," +
			    "routeID='" + event.getRouteID() + "'," +
			    "groupID='" + event.getGroupID() + "'," +
			    "creatorID='" + event.getCreatorID() + "'," +
			    "eventTime='" + event.getEventTime() + "'," +
			    "eventDate='" + event.getEventDate() + "'," +
			    "modifiedDate=NOW()," +
			    "publicity='" + event.getPublicity() + "' " +
			"WHERE eventID='" + event.getEventID() + "'";
		
		return DBConnector.executeUpdateQuery( updateQuery );
		
	}

	/**
	 * Deletes the given event with the given ID.
	 * 
	 * @param event
	 * @return 1 if the delete is successful. -1 if not
	 */
	public static int deleteEvent( Event event ){
		
		String deleteQuery = 
			"DELETE FROM Event " +
			"WHERE eventID='"+ event.getEventID() +"'"; 
		
		return DBConnector.executeUpdateQuery( deleteQuery );		
	}	
	
	
	/**
	 * Function that gets all the events registered so far
	 * 
	 * @return
	 */
	public static List<Event> getAllEvents(int recordPerPage, int currentPage){
		
		List<Event> events = new ArrayList<Event>();
		String query = "SELECT * FROM Event ORDER BY modifiedDate DESC ";	
		if( recordPerPage != 0 || currentPage != 0 ) query += "LIMIT " + (currentPage-1) * recordPerPage + ", " + recordPerPage;
		ResultSet resultSet = DBConnector.getQueryResult( query );		
		
		try {
			while( resultSet.next() ){				
				events.add( resultSetToEvent( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBUtilEvent.getAllEvents() : Error getting all events");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
			
		return events;
	}
	
	/*
	 * Get the dates of events for current month as string '11/15/2008,11/31/2008,...'
	 */
	public static String getEventDatesForCurMonth() {
		Calendar cal = Calendar.getInstance();
		
//		System.out.println(cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH)+1);
		
		return getEventDatesByMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1);
	}
	
	
	/*
	 * Get the dates of events for given year and month as string '11/15/2008,11/31/2008,...'
	 */
	public static String getEventDatesByMonth( int year, int month ) {
		String searchQuery = 
			"SELECT distinct(eventDate) EVENTDATE " +
			"FROM Event " +
			"WHERE eventDate >= '" + year +"-" + month + "-01' AND " +
			"eventDate <= '" + year + "-" + month + "-31' " +
			"ORDER BY eventDate ";
		
		StringBuffer sbuf = new StringBuffer();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
		
//		System.out.println("Search Query:" + searchQuery);

		List<Event> events = new ArrayList<Event>();
		ResultSet resultSet = DBConnector.getQueryResult( searchQuery );	
		
		try {
			while( resultSet.next() ){
				sbuf.append(dateFormat.format(resultSet.getDate("EVENTDATE")) + ",");
			}
		} 
		catch (SQLException e) {
			System.out.println("DBUtilEvent.searchForEvents() : Error searching for events");
			e.printStackTrace();
			return null;
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
//		System.out.println("result = " + sbuf.toString());
		
		if( sbuf.length() == 0 ) return "";
		else return sbuf.toString().substring(0, sbuf.length()-1 );
	}
	
	/**
	 * Function that search DB for events that meets given search criteria
	 * 
	 * @param params
	 * @return
	 */
	public static List<Event> searchForEvents( List<QueryParameter> params, int recordPerPage, int currentPage ) {
		String searchQuery = 
			"SELECT * " +
			"FROM Event " +
			"WHERE " + DBUtil.getSearchCriteria( params ) +
			" ORDER BY eventDate DESC, createdDate DESC ";
			// don't apply LIMIT when recordPerPage or currentPage is 0
			if( recordPerPage != 0 || currentPage != 0 ) searchQuery += "LIMIT " + (currentPage-1) * recordPerPage + ", " + recordPerPage;
		
		System.out.println("Search Query:" + searchQuery);

		List<Event> events = new ArrayList<Event>();
		ResultSet resultSet = DBConnector.getQueryResult( searchQuery );	
		
		try {
			while( resultSet.next() ){				
				events.add( resultSetToEvent( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBUtilEvent.searchForEvents() : Error searching for events");
			e.printStackTrace();
			return null;
		}
		finally{
			DBConnector.closeDBConnection();
		}
			
		return events;
	}

	public static int getSearchForEventsCount(List<QueryParameter> params) {
		/* comment */
		String searchQuery = 
			"SELECT count(*) CNT " +
			"FROM Event " +
			"WHERE " + DBUtil.getSearchCriteria( params );
		
		ResultSet resultSet = DBConnector.getQueryResult(searchQuery);

		int recCount = 0;
		
		try {
			while( resultSet.next() ){				
				recCount = resultSet.getInt("CNT");
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getSearchForEventsCount() : Error getting route count");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		return recCount;
	}

	  
	
	/**
	 *
	 * 
	 * @param id
	 * @return
	 */
	public static Event getEventById( String id ){
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Event WHERE eventID='"+id+"'" );
		try {
			if( resultSet.next() ){
				return resultSetToEvent( resultSet );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getEventById() : Error getting event");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return null;
	}
	
	public static int getAllEventsCount() {
		String searchQuery = "SELECT count(*) CNT FROM Event";
		
		ResultSet resultSet = DBConnector.getQueryResult(searchQuery);
		int recCount = 0;
		
		try {
			while( resultSet.next() ){				
				recCount = resultSet.getInt("CNT");
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getAllEventsCount() : Error getting event count");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		return recCount;
	}
	
	public static String getAllEventsHTML(int recordPerPage, int currentPage) {
		List<Event> events = DBUtilEvent.getAllEvents(recordPerPage, currentPage);
		
		StringBuffer sb = new StringBuffer();
		Event event;
		Iterator<Event> iterator = events.iterator();

		int cnt = (currentPage-1)*recordPerPage + 1;
		while(iterator.hasNext()){
			event = iterator.next();
			
			sb.append("<div class=\"AllEventResultItem\">\n").
			   append((cnt++)+ ". <a href=\"javascript:YAHOO.pennfitness.float.getEvent(" + event.getEventID() + ", false)\" class=\"AEREventName\">" + StringUtil.fitString(event.getName(), 13) + "</a> on <span class=\"AERuserID\">" + event.getEventDate() + "</span>\n</div>\n");
		}
		return sb.toString();
	}
	
	public static int getEventCountByRouteId(String routeId) {
		int eventCount = 0;		
		
		String query = "SELECT COUNT(routeID) CNT FROM Event WHERE routeID='"+ routeId +"'";
		
		ResultSet resultSet = DBConnector.getQueryResult(query);
		try {
			while( resultSet.next() ){
				eventCount = resultSet.getInt("CNT");
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getEventCountByRouteId() : Error getting event count for given routeID");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return eventCount;		
	}

	public static List<Event> getEventsByRouteId(String id, int recordPerPage, int currentPage) {
		List<Event> events = new ArrayList<Event>();
		String query = "SELECT * FROM Event WHERE routeID='" + id + "' ORDER BY eventDate DESC ";
		
		if( recordPerPage != 0 || currentPage != 0 ) query += "LIMIT " + (currentPage-1) * recordPerPage + ", " + recordPerPage;
		ResultSet resultSet = DBConnector.getQueryResult( query );
		
		try {
			while( resultSet.next() ){				
				events.add( DBUtilEvent.resultSetToEvent( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getEventListByRouteId() : Error getting event list for routeID: " + id);
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
			
		return events;
		
	}
	
	
	/**
	 * Utility function that gets Event object from a resultset row
	 * 
	 * @param resultSet
	 * @return
	 */
	private static Event resultSetToEvent( ResultSet resultSet ){
		
		
		int eventID, groupID, routeID, eventTypeID;
		float duration;
		char publicity;
		String name, description, creatorID;
		Date createdDate, modifiedDate, eventDate; 	 	 	 	 	 	 	 	
		Time eventTime;
		
		try {
			eventID = resultSet.getInt("eventID");
			name = resultSet.getString("name");
			description = resultSet.getString("description");
			groupID = resultSet.getInt("groupID");
			publicity = resultSet.getString("publicity").charAt(0);
			creatorID = resultSet.getString("creatorID");
			createdDate = resultSet.getDate("createdDate");
			modifiedDate = resultSet.getDate("modifiedDate");
			routeID = resultSet.getInt("routeID");
			eventTypeID = resultSet.getInt("eventTypeID");
			eventDate = resultSet.getDate("eventDate");
			eventTime = resultSet.getTime("eventTime");
			duration = resultSet.getFloat("duration");
			
			
			return new Event( eventID, name, description, groupID, publicity, creatorID, 
					  		  createdDate, modifiedDate,  routeID, eventTypeID, eventDate, eventTime, duration);
			
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.resultSetToEvent() : Error converting resultset to event");
			e.printStackTrace();
		}
		
		return null;
	}
	
}
