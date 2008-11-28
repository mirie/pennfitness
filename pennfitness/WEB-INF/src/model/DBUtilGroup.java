package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	public static Boolean checkGroup( String id ) {
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
	
	/**
	 * 
	 * @return
	 */
	public static int modifyGroup( Group group ){
		
		String updateQuery = 
			"UPDATE Groups " +
			"SET name='" + group.getName()+ "', " +
			"description='"+ group.getDescription() + "' "+
			"WHERE groupID='"+ group.getId() +"'"; 
		return DBConnector.executeUpdateQuery( updateQuery );
			
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
	 * Saves a new route with the given information
	 * 
	 * @param route
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
			
			try {
				ResultSet  result = DBConnector.getQueryResult( "SELECT MAX(groupID) FROM Groups" );
				if( result != null )
					if( result.next() )
						return result.getInt( 1 );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("DBUtilGroup.saveGroup() : Error getting ID of saved goup"  );
				e.printStackTrace();
			}
			
		}
			
		return -1;
	}
	public static int joinGroup( Group group, String userID, String notify){
		String saveQuery = 
			"INSERT INTO GroupReg ( groupID, userID,  notify, registeredDate)" +
								" VALUES ('"+ group.getId()+"'," +
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

	
	
	/**
	 * Utility function that gets Group object from a resultset row
	 * 
	 * @param resultSet
	 * @return
	 */
	private static Group resultSetToGroup( ResultSet resultSet ){
		
		String id, creatorId;
		String name;
		String description;
		Date createdDate;
		
		try {
			id = resultSet.getString("groupID");
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
