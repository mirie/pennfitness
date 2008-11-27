package entities;

import java.sql.Date;

public class Group {
	
	private String groupID;
	private String creatorID;
	
	private String name;
	private String description;
	private Date createdDate;
	
	public Group(String groupID){		
		this.groupID = groupID;
	}
	public Group( String name, String description, String creatorID){			
		this.name = name;
		this.description = description;
		this.creatorID = creatorID;
	}
	public Group( String groupID, String name, String creatorID, String description, Date createdDate ){		
		this.groupID = groupID;
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

	public String getId() {
		return groupID;
	}

	public void setId(String groupID) {
		this.groupID = groupID;
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

	public String getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}
	
}
