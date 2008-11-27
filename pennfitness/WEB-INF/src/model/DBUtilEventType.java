package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.EventType;


public class DBUtilEventType {
	
	public static EventType getEventTypeById( String id ){
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM EventType WHERE eventTypeID='"+id+"'" );
		try {
			if( resultSet.next() ){
				return resultSetToEventType( resultSet );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBUtilEventType.getEventTypeById() : Error getting eventType");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return null;
	}
	
	
	public static EventType getEventTypeByDesc( String description ){
		
		EventType returnEventType = null;
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM EventType WHERE description='"+ description +"'" );
		try {
			if( resultSet.next() ){
				returnEventType = resultSetToEventType( resultSet );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBUtilEventType.getEventTypeByDesc() : Error getting EventType");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return returnEventType;
	}
	
	
	public static List<String> getEventTypeDesc(){
		
		List<EventType> eventTypes = getAllEventTypes();
		List<String> descriptions = new ArrayList<String>();
		
		Iterator<EventType> iterator = eventTypes.iterator();
		while(iterator.hasNext()){
			descriptions.add( iterator.next().getDescription() );
		}
		
		return descriptions;
	}
	
	public static List<EventType> getAllEventTypes( ){
		
		List<EventType> eventTypes = new ArrayList<EventType>();
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM EventType ORDER BY description" );	
		
		try {
			while( resultSet.next() ){				
				eventTypes.add( resultSetToEventType( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBUtilEventType.getAllEventTypes() : Error getting EventType");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
			
		return eventTypes;
	}
	
	/*
	 * Returns all event types in OPTION format 
	 * <option value="...">...</option>
	 * <option value="...">...</option>
	 * ...
	 */
	public static String getAllEventTypesOptions(){
		StringBuffer sb = new StringBuffer();
		List<EventType> eventTypeList = DBUtilEventType.getAllEventTypes(); 	
		Iterator<EventType> iterator = eventTypeList.iterator();
		
		EventType eventType;
		while(iterator.hasNext()){
			eventType = iterator.next();
			sb.append("<option value\"" + eventType.getEventTypeID() + "\">" + eventType.getDescription() + "</option>\n");
		}
		
		return sb.toString();
	}
	
	
	
	/**
	 * Utility function that gets EventType object from a resultset row
	 * 
	 * @param resultSet
	 * @return
	 */
	private static EventType resultSetToEventType( ResultSet resultSet ){
		
		int id;
		String description;
		
		try {
			id = resultSet.getInt("eventTypeID");
			description = resultSet.getString("description");
			
			return new EventType( id, description );			
		} 
		catch (SQLException e) {
			System.out.println("DBUtilEventType.resultSetToEventType() : Error getting EventType");
			e.printStackTrace();
		}
		
		return null;
	}

}