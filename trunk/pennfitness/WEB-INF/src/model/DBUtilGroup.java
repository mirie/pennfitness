package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import util.SMTPMail;

import entities.Group;
import entities.Route;
import entities.User;

public class DBUtilGroup {

	/**
	 *
	 * 
	 * @param id
	 * @return
	 */
	public static Group getGroupById( String id ){
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Groups WHERE groupID='"+id+"'" );
		try {
			if( resultSet.next() ){
				return resultSetToGroup( resultSet );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getGroupById() : Error getting group");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return null;
	}
	/**
	 *
	 * 
	 * @param id
	 * @return
	 */
	public static Boolean checkGroupByID( String id ) {
		if( id == null ) return false;
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Groups WHERE groupID='"+id+"'" );
		try {
			if( resultSet.next() ){
				return true;
			}
			else return false;
		} 
		catch (SQLException e) {
			System.out.println("DBUtilGroup.checkGroup() : Error getting group");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}

		return false;
	}
	/**
	 *
	 * 
	 * @param id
	 * @return
	 */
	public static Boolean checkGroupByName( String groupName ) {
		if( groupName == null ) return false;
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Groups WHERE name='"+groupName+"'" );
		try {
			if( resultSet.next() ){
				return true;
			}
			else return false;
		} 
		catch (SQLException e) {
			System.out.println("DBUtilGroup.checkGroupByName(String groupName) : Error getting group");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}

		return false;
	}
	/**
	 *
	 * 
	 * @param id
	 * @return
	 */
	public static Boolean checkGroupReg( String groupID, String userID ) {
		if( groupID == null || userID==null ) return false;
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM GroupReg WHERE groupID='"+groupID+"'and userID='"+userID+"'" );
		try {
			if( resultSet.next() ){
				return true;
			}
			else return false;
		} 
		catch (SQLException e) {
			System.out.println("DBUtilGroup.checkGroup() : Error getting group");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}

		return false;
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static Group getGroupByName( String name ){
		
		Group returnGroup = null;
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Groups WHERE name='"+name+"'" );
		try {
			if( resultSet.next() ){
				returnGroup = resultSetToGroup( resultSet );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getGroupById() : Error getting group");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return returnGroup;
	}
	public static List<String> getGroupNames(){
		
		List<Group> groups = getAllGroups();
		List<String> names = new ArrayList<String>();
		
		Iterator<Group> iterator = groups.iterator();
		while(iterator.hasNext()){
			names.add( iterator.next().getName() );
		}
		
		return names;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<Group> getAllGroups( ){
		
		List<Group> groups = new ArrayList<Group>();
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Groups" );	
		
		try {
			while( resultSet.next() ){				
				groups.add( resultSetToGroup( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getAllGroups() : Error getting group");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
			
		return groups;
	}
	
	/**
	 * Saves a new group with the given information
	 * 
	 * @param group
	 * @return the ID of newly inserted route if transaction is successful. -1 if not.
	 */
	public static int saveGroup( Group group ){
			
		String saveQuery = 
			"INSERT INTO Groups ( name, creatorID,  description, createdDate)" +
								" VALUES ('"+ group.getName()+"'," +
										"'"+ group.getCreatorID()+"'," +
										"'"+ group.getDescription()+"'," +
										" NOW() )";
		if( DBConnector.executeUpdateQuery( saveQuery ) > 0 ){
			
			//creator should automatically be registered for the created group
			User user = DBUtilUser.getUserById( group.getCreatorID() );
			if( user == null)
				return -1;
			int join = joinGroup(group.getId()+"", group.getCreatorID(),user.getPublicEventNotify()+"" );
			
			try {
				ResultSet  result = DBConnector.getQueryResult( "SELECT MAX(groupID) FROM Groups" );
				if( result != null )
					if( result.next() && join != -1 ){
						return result.getInt( 1 );
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("DBUtilGroup.saveGroup() : Error getting ID of saved group"  );
				e.printStackTrace();
			}
			
		}
		return -1;
	}
	public static int modifyGroup( Group group ){
		
		String updateQuery = 
			"UPDATE Groups " +
			"SET name='" + group.getName()+ "', " +
			"description='"+ group.getDescription() + "' "+
			"WHERE groupID='"+ group.getId() +"'"; 
		return DBConnector.executeUpdateQuery( updateQuery );
			
	}
	public static int deleteGroup( Group group){
		String deleteGroupQuery = 
			"DELETE FROM Groups WHERE groupID='"+group.getId()+"';";
		return DBConnector.executeUpdateQuery( deleteGroupQuery );
	}
	
	public static int joinGroup( String groupID, String userID, String notify){
		String saveQuery = 
			"INSERT INTO GroupReg ( groupID, userID,  notify, registeredDate)" +
								" VALUES ('"+ groupID+"'," +
										"'"+ userID+"'," +
										"'"+ notify+"'," +
										"NOW() )";
		if( DBConnector.executeUpdateQuery( saveQuery ) > 0 ){
			
			try {
				ResultSet  result = DBConnector.getQueryResult( "SELECT MAX(groupID) FROM Groups" );
				if( result != null )
					if( result.next() )
						return result.getInt( 1 );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("DBUtilGroup.joinGroup() : Error getting ID of group to join"  );
				e.printStackTrace();
			}
			
		}
		return -1;
	}
	//This function can only be called by group creator
	public static int deleteGroupReg( String groupID){
		String deleteGroupRegQuery = 
			"DELETE FROM GroupReg WHERE groupID='"+groupID+"';";
		return DBConnector.executeUpdateQuery( deleteGroupRegQuery );
	}

	public static int leaveGroup( Group group, String userID){
		String saveQuery = 
			"DELETE FROM GroupReg WHERE groupID='"+group.getId()+"'and userID='"+userID+"';";
		if( DBConnector.executeUpdateQuery( saveQuery ) > 0 ){
			
			try {
				ResultSet  result = DBConnector.getQueryResult( "SELECT MAX(groupID) FROM Groups" );
				if( result != null )
					if( result.next() )
						return result.getInt( 1 );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("DBUtilGroup.joinGroup() : Error removing GroupReg entry"  );
				e.printStackTrace();
			}
			
		}
		return -1;
	}

/**
 * Function that search DB for groups that meets given search criteria
 * 
 * @param params
 * @return
 */
	public static List<Group> searchForGroups( List<QueryParameter> params, int recordPerPage, int currentPage ){
		String searchQuery = 
			"SELECT * " +
			"FROM Groups " +
			"WHERE " + DBUtil.getSearchCriteria( params ) +
			" ORDER BY createdDate DESC " +
			"LIMIT " + (currentPage-1) * recordPerPage + ", " + recordPerPage;
	
		System.out.println("Search Query:" + searchQuery);
	
		List<Group> groups = new ArrayList<Group>();
		ResultSet resultSet = DBConnector.getQueryResult( searchQuery );	
	
		try {
			while( resultSet.next() ){				
				groups.add( resultSetToGroup( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBUtilRoute.searchForGroups() : Error searching for groups");
			e.printStackTrace();
			return null;
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return groups;
	}
	public static int getSearchForGroupsCount(List<QueryParameter> params ){
		String searchQuery = 
			"SELECT count(*) CNT " +
			"FROM Groups " +
			"WHERE " + DBUtil.getSearchCriteria( params );
		
		ResultSet resultSet = DBConnector.getQueryResult(searchQuery);
		int recCount = 0;
		
		try {
			while( resultSet.next() ){				
				recCount = resultSet.getInt("CNT");
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getSearchForGroupsCount() : Error getting route count");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		return recCount;
	}
	
	public static boolean sendEmailToGroupMembers( String msgSubject, String msgText, int groupID ){
		String searchQuery =
			"SELECT U.email " +
			"FROM User U, GroupReg GR "+ 
			"WHERE U.userID = GR.userID "+ 
			"AND GR.groupID = '"+groupID+"'";
		
		
		ResultSet resultSet = DBConnector.getQueryResult(searchQuery);
		List<String> emails = new ArrayList<String>();
		
		String email;
		try {
			while( resultSet.next() ){				
				email = resultSet.getString( "email" );
				if( email != null )
					emails.add( email );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.sendEmailToGroupMembers() : Error getting email addresses before sending");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		try {
			SMTPMail.send( emails , msgSubject, msgText);
		} catch (Exception e) {
			System.out.println("DBUtilGroup.sendEmailToGroupMembers() : Error sending email" );
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}

	public static List<Group> getGroupListByUserIDForEvent(String userID) {
		List<Group> groups = new ArrayList<Group>();
		String query = "SELECT * FROM Groups WHERE creatorID='" + userID + "'ORDER BY name "; 
				
		ResultSet resultSet = DBConnector.getQueryResult( query );
		
		try {
			while( resultSet.next() ){				
				groups.add( DBUtilGroup.resultSetToGroup( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getGroupByUserID() : Error getting group list for creatorID: " + userID);
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
				
		return groups;
	}	
	
	public static List<Group> getGroupByUserID(String userID, int recordPerPage, int currentPage) {
		List<Group> groups = new ArrayList<Group>();
		String query = "SELECT * FROM Groups WHERE creatorID='" + userID + "' ORDER BY name " +
			"LIMIT " + (currentPage-1) * recordPerPage + ", " + recordPerPage;
				
		ResultSet resultSet = DBConnector.getQueryResult( query );
		
		try {
			while( resultSet.next() ){				
				groups.add( DBUtilGroup.resultSetToGroup( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getGroupByUserID() : Error getting group list for creatorID: " + userID);
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
				
		return groups;
	}

	public static int getGroupByUserIDCount(List<QueryParameter> params) {
		List<Group> groups = new ArrayList<Group>();
		String query = "SELECT COUNT(*) CNT FROM Groups" +
			" WHERE " + DBUtil.getSearchCriteria( params );
				
		ResultSet resultSet = DBConnector.getQueryResult( query );
		
		try {
			while( resultSet.next() ){				
				return resultSet.getInt("CNT");
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getGroupByUserIDCount() : Error getting group list count");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
				
		return 0;
	}

	
	public static int getMemberCount( int groupID ){
		String query =
			"SELECT COUNT(*) AS count " +
			"FROM GroupReg G " +
			"WHERE G.groupID = '"+groupID+"'";
		
		ResultSet resultSet = DBConnector.getQueryResult( query );
		
		try {
			if( resultSet.next() ){				
				return resultSet.getInt("count");
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getMemberCount() : Error getting member count");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return -1;
	} 
	
	/**
	 * Comma separated list of a particular group
	 * 
	 * @param groupID
	 * @return
	 */
	public static String getMemberList( int groupID ){
		String query =
			"SELECT GR.userID " +
			"FROM GroupReg GR " +
			"WHERE GR.groupID = '"+groupID+"'";
		
		ResultSet resultSet = DBConnector.getQueryResult( query );
		StringBuffer sbuf = new StringBuffer();
		
		try {
			while( resultSet.next() ){				
				sbuf.append(resultSet.getString("userID")).append(",");
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getMemberList() : Error getting member list");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		if( sbuf.length() > 1 )
			return sbuf.toString().substring( 0, sbuf.length() - 1 );
		return sbuf.toString();
	}

	/**
	 * Utility function that gets Group object from a resultset row
	 * 
	 * @param resultSet
	 * @return
	 */
	private static Group resultSetToGroup( ResultSet resultSet ){
		
		int id;
		String creatorId;
		String name;
		String description;
		Date createdDate;
		
		try {
			id = resultSet.getInt("groupID");
			name = resultSet.getString( "name" );
			creatorId = resultSet.getString("creatorId");
			description = resultSet.getString("description");
			createdDate = resultSet.getDate("createdDate");
			
			return new Group( id, name, creatorId, description, createdDate );
			
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.resultSetToGroup() : Error getting group");
			e.printStackTrace();
		}
		
		return null;
	}
	
}
