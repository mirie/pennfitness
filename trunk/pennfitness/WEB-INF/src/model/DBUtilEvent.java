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
	public static List<Event> getAllEvents( ){
		
		List<Event> events = new ArrayList<Event>();
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Event" );	
		
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
	public static List<Event> searchForEvents( List<QueryParameter> params ){
		String searchQuery = 
			"SELECT * " +
			"FROM Event " +
			"WHERE " + DBUtil.getSearchCriteria( params );
		
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
