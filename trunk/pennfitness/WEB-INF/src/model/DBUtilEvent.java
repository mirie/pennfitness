package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entities.Event;
import entities.Route;

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
			" ORDER BY eventDate DESC, createdDate DESC " +
			"LIMIT " + (currentPage-1) * recordPerPage + ", " + recordPerPage;
		
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
			   append((cnt++)+ ". <a href=\"javascript:YAHOO.pennfitness.float.getEvent(" + event.getEventID() + ", false)\" class=\"AEREventName\">" + event.getName() + "</a> by <span class=\"AERuserID\">" + event.getCreatorID() + "</span>\n</div>\n");
		}
		return sb.toString();
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
