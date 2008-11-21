package model;

import java.sql.Date;
import java.text.SimpleDateFormat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.Route;
import entities.User;

public class DBUtilUser {

	/**
	 * 
	 * 
	 * @param id, password
	 * @return a valid user object when id and password matches
	 */
	public static User loginUser( String id, String password ){
		User returnUser = null;
		
		if( id == null || password == null ) return null;

		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM User WHERE userID='"+id+"' AND password=PASSWORD('"+password+"')" );
		try {
			if( resultSet.next() ){
				returnUser = resultSetToUser( resultSet );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBUtilUser.loginUser() : Error getting route");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return returnUser;
	}
	
	/**
	 * Check whether the user exists or not
	 * @param id
	 * @return
	 */
	public static Boolean checkUser( String id ) {
		if( id == null ) return false;
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM User WHERE userID='"+id+"'" );
		try {
			if( resultSet.next() ){
				return true;
			}
			else return false;
		} 
		catch (SQLException e) {
			System.out.println("DBUtilUser.loginUser() : Error getting route");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}

		return false;
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static User getUserById( String id ){
		User returnUser = null;
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM User WHERE userID='"+id+"'" );
		try {
			if( resultSet == null ) return null;
			
			if( resultSet.next() ){
				returnUser = resultSetToUser( resultSet );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBUtilUser.getUserById() : Error getting route");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return returnUser;
	}
	
	
	/**
	 * Registers a new user
	 * 
	 * @param user
	 * @return the ID of newly inserted route if transaction is successful. -1 if not.
	 */
	public static boolean registerUser( User user ){
		
		String registerQuery = 
			"INSERT INTO User VALUES ('" + user.getUserID()   + "'," +
									 "'" + user.getUserName() + "'," +
									 "PASSWORD('" + user.getPassword() + "')," +
									 "'" + user.getEmail() + "'," +
									 "NOW()," + // lastLoginDate
									 "NOW()," + // registeredDate
									 "'" + user.getHeight() + "'," +
									 "'" + user.getWeight() + "'," +
									 "'" + user.getGender() + "'," +
									 "'" + user.getPublicEventNotify() + "'," +
									 "'" + 0 + "'" + // user rating
									 ")";
		
		return DBConnector.executeUpdateQuery( registerQuery ) == 1;
	}
	
	
	
	/**
	 * Utility function that gets User object from a ResultSet row
	 * 
	 * @param resultSet
	 * @return new User object
	 */
	private static User resultSetToUser( ResultSet resultSet ){
		
		String userID, userName, password, email;
		Date lastLoginDate, registeredDate;
		float height, weight, userRating;
		char gender, publicEventNotify;
		
		try {
			userID   = resultSet.getString("userID");
			userName = resultSet.getString("userName");
			password = resultSet.getString("password");
			email    = resultSet.getString("email");
			
			height = resultSet.getFloat("height");
			weight = resultSet.getFloat("weight");
			gender = resultSet.getString("gender").charAt(0);
			userRating = resultSet.getFloat("userRating");
			publicEventNotify = resultSet.getString("publicEventNotify").charAt(0);

			lastLoginDate  = resultSet.getDate("lastLoginDate");
			registeredDate = resultSet.getDate("registeredDate");
			
			return new User( userID, userName, password, email, height, weight, gender,
							 publicEventNotify, userRating, lastLoginDate, registeredDate);
				
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.resultSetToUser() : Error getting route");
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
}
