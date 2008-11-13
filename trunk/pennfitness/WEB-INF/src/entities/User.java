package entities;

import java.sql.Date;

public class User {

/*
	CREATE TABLE `User` (
		`userID` VARCHAR(20) NOT NULL ,
		`userName` VARCHAR(20) NOT NULL ,
		`password` VARCHAR(20) NOT NULL ,
		`email` VARCHAR(30) NOT NULL ,
		`lastLoginDate` DATETIME NOT NULL ,
		`registeredDate` DATETIME NOT NULL ,
		`height` FLOAT ,
		`weight` FLOAT ,
		`gender` CHAR(1) ,
		`publicEventNotify` CHAR(1) COMMENT 'Y/N' ,
		`userRating` FLOAT NOT NULL DEFAULT 0 ,
		PRIMARY KEY (`userID`)
	);
 */	
	
	private String userID;
	private String userName;
	private String password;
	private String email;

	private Date lastLoginDate;
	private Date registeredDate;
	
	private char publicEventNotify;
	private float userRating;
	
	// Optional data
	private float height;
	private float weight;
	private char gender;
		
	public User( String userID, String userName, String password, String email, float height, float weight, char gender ) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.email = email;
		
		this.height = height;
		this.weight = weight;
		this.gender = gender;
	}
	
	public User( String userID, String userName, String password, String email, float height, float weight, char gender,
				 char publicEventNotify, float userRating, Date lastLoginDate, Date registeredDate) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.email = email;
		
		this.height = height;
		this.weight = weight;
		this.gender = gender;
		this.publicEventNotify = publicEventNotify;
		this.userRating = userRating;
		
		this.lastLoginDate = lastLoginDate;
		this.registeredDate = registeredDate;
	}
	
	public String getUserID()
	{
		return userID; 
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public float getWeight()
	{
		return weight;
	}

	public float getUserRating()
	{
		return userRating;
	}

	public char getGender()
	{
		return gender;
	}
	
	public Date getLastLoginDate()
	{
		return lastLoginDate;
	}
	
	public Date getRegisteredDate()
	{
		return registeredDate;
	}
	
	public char getPublicEventNotify()
	{
		return publicEventNotify;
	}
		
	
}

