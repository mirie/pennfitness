package entities;


public class EventType {
	
	private int id;
	private String description;
	
	public EventType(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public int getEventTypeID() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}

}
