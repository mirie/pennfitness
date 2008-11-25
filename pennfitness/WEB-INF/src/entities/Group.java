package entities;

import java.sql.Date;

public class Group {
	
	private int id;
	private int creatorID;
	
	private String name;
	private String description;
	private Date createdDate;
	
	public Group( String name, String description, int creatorID){		
		this.name = name;
		this.description = description;
		this.creatorID = creatorID;
	}
	public Group( int id, String name, int creatorID, String description, Date createdDate ){		
		this.id = id;
		this.name = name;
		this.creatorID = creatorID;
		this.description = description;
		this.createdDate = createdDate;
	}
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}
	
}
