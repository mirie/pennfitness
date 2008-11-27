package entities;

import java.sql.Date;

public class Route {
	
	private int id;
	private String creatorID;
	
	private String name;
	private String color;
	private String ptValues;
	private String description;
	
	private float distance;
	private int ps_scenery;
    private int pt_difficulty;	
    private int pt_safety;
    private float pt_rate;
	
	private Date createdDate;
	private Date modifiedDate;
	
	public Route( String name, String color, String ptValues, float distance, String description, String creatorID ){
		this.name = name;
		this.color = color;
		this.ptValues = ptValues;
		this.distance = distance;
		this.description = description;
		this.creatorID = creatorID;
	}
	
	
	public Route( int id, String name, String creatorID, String ptValues, String description, String color, 
				  float distance, int ps_scenery, int pt_difficulty, int pt_safety, float pt_rate,
				  Date createdDate, Date modifiedDate ){
		
		this.id = id;
		this.name = name;
		this.creatorID = creatorID;
		this.ptValues = ptValues;
		this.description = description;
		this.color = color;
		this.distance = distance;
		this.ps_scenery = ps_scenery;
		this.pt_difficulty = pt_difficulty;
		this.pt_safety = pt_safety;
		this.pt_rate = pt_rate;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPtValues() {
		return ptValues;
	}

	public void setPtValues(String ptValues) {
		this.ptValues = ptValues;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public int getPs_scenery() {
		return ps_scenery;
	}

	public void setPs_scenery(int ps_scenery) {
		this.ps_scenery = ps_scenery;
	}

	public int getPt_difficulty() {
		return pt_difficulty;
	}

	public void setPt_difficulty(int pt_difficulty) {
		this.pt_difficulty = pt_difficulty;
	}

	public int getPt_safety() {
		return pt_safety;
	}

	public void setPt_safety(int pt_safety) {
		this.pt_safety = pt_safety;
	}

	public float getPt_rate() {
		return pt_rate;
	}

	public void setPt_rate(float pt_rate) {
		this.pt_rate = pt_rate;
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

	public String getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}
	
}
