package entities;

import java.sql.Date;

public class Event {
	
	private int eventID; 	
	private String name; 	
	private String description; 	
	private int groupID;	
	private char publicity;	
	private int creatorID; 	
	private Date createdDate; 	
	private Date modifiedDate; 	
	private int routeID;	
	private int eventTypeID; 	
	private Date eventDate;
	private String eventTime;
	private float duration;
	

	public Event( String name, float duration, String description, int routeID, int groupID, int creatorID, 
				  String eventTime, Date eventDate, char publicity ){
		this.name = name;
		this.description = description;
		this.routeID = routeID;
		this.groupID = groupID;
		this.creatorID = creatorID;
		this.eventTime = eventTime;
		this.eventDate = eventDate;
		this.publicity = publicity;
	}
	
	public Event( int eventID, String name, String description, int groupID, char publicity, int creatorID, 
				  Date createdDate, Date modifiedDate,  int routeID, int eventTypeID, Date eventDate, String eventTime, float duration){
		this.eventID = eventID;
		this.name = name;
		this.description = description;
		this.groupID = groupID;
		this.publicity = publicity;
		this.creatorID = creatorID;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.routeID = routeID;
		this.eventTypeID = eventTypeID;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.duration = duration;
	}
	
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public char getPublicity() {
		return publicity;
	}
	public void setPublicity(char publicity) {
		this.publicity = publicity;
	}
	public int getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public int getRouteID() {
		return routeID;
	}
	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}
	public int getEventTypeID() {
		return eventTypeID;
	}
	public void setEventTypeID(int eventTypeID) {
		this.eventTypeID = eventTypeID;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public float getDuration() {
		return duration;
	}
	public void setDuration(float duration) {
		this.duration = duration;
	} 	
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

}
