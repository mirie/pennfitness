package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.Group;
import entities.Route;

public class DBUtilRoute {

	/**
	 *
	 * 
	 * @param id
	 * @return
	 */
	public static Route getRouteById( String id ){
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Routes WHERE routeID='"+id+"'" );
		try {
			if( resultSet.next() ){
				return resultSetToRoute( resultSet );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getRouteById() : Error getting route");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static Route getRouteByName( String name ){
		
		Route returnRoute = null;
		
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Routes WHERE name='"+name+"'" );
		try {
			if( resultSet.next() ){
				returnRoute = resultSetToRoute( resultSet );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getRouteById() : Error getting route");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		
		return returnRoute;
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<String> getRouteNames(){
		
		List<Route> routes = getAllRoutes(0, 0);
		List<String> names = new ArrayList<String>();
		
		Iterator<Route> iterator = routes.iterator();
		while(iterator.hasNext()){
			names.add( iterator.next().getName() );
		}
		
		return names;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<Route> getAllRoutes(int recordPerPage, int currentPage){
		
		List<Route> routes = new ArrayList<Route>();
		String query = "SELECT * FROM Routes ORDER BY modifiedDate DESC ";
		if( recordPerPage != 0 || currentPage != 0 ) query += "LIMIT " + (currentPage-1) * recordPerPage + ", " + recordPerPage;
		ResultSet resultSet = DBConnector.getQueryResult( query );
		
		try {
			while( resultSet.next() ){				
				routes.add( resultSetToRoute( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getAllRoutes() : Error getting route");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
			
		return routes;
	}
	
	public static String getAllRoutesHTML(int recordPerPage, int currentPage) {
		List<Route> routes = DBUtilRoute.getAllRoutes(recordPerPage, currentPage); 	
		StringBuffer sb = new StringBuffer();
		Route route;
		Iterator<Route> iterator = routes.iterator();

		int cnt = (currentPage-1)*recordPerPage + 1;
		while(iterator.hasNext()){
			route = iterator.next();
			
			sb.append("<div class=\"AllRouteResultItem\">\n").
			   append((cnt++)+ ". <a href=\"javascript:YAHOO.pennfitness.float.getRoute(" + route.getId() + ", false)\" class=\"ARRrouteName\">" + route.getName() + "</a> by <span class=\"ARRuserID\">" + route.getCreatorID() + "</span>\n</div>\n");
		}
		return sb.toString();
	}
	
	public static int getAllRoutesCount() {
		String searchQuery = "SELECT count(*) CNT FROM Routes";
		
		ResultSet resultSet = DBConnector.getQueryResult(searchQuery);
		int recCount = 0;
		
		try {
			while( resultSet.next() ){				
				recCount = resultSet.getInt("CNT");
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getAllRoutesCount() : Error getting route count");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		return recCount;
	}
	
	public static List<Route> getPopularRoutes(int recordPerPage, int currentPage){
		
		List<Route> routes = new ArrayList<Route>();
		String query = "SELECT * FROM Routes ORDER BY pt_rate DESC, createdDate DESC ";
		if( recordPerPage != 0 || currentPage != 0 ) query += "LIMIT " + (currentPage-1) * recordPerPage + ", " + recordPerPage;
		ResultSet resultSet = DBConnector.getQueryResult( query );
		
		try {
			while( resultSet.next() ){				
				routes.add( resultSetToRoute( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getPopularRoutes() : Error getting route");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
			
		return routes;
	}

	public static String getPopularRoutesHTML(int recordPerPage, int currentPage) {
		List<Route> routes = DBUtilRoute.getPopularRoutes(recordPerPage, currentPage); 	
		StringBuffer sb = new StringBuffer();
		Route route;
		Iterator<Route> iterator = routes.iterator();

		int cnt = (currentPage-1)*recordPerPage + 1;
		while(iterator.hasNext()){
			route = iterator.next();
			
			sb.append("<div class=\"PopularRouteResultItem\">\n").
			   append((cnt++)+ ". <a href=\"javascript:YAHOO.pennfitness.float.getRoute(" + route.getId() + ")\" class=\"PRRrouteName\">" + route.getName() + "</a> by <span class=\"PRRuserID\">" + route.getCreatorID() + "</span>\n</div>\n");
		}
		return sb.toString();
	}

	
	/**
	 * Saves a new route with the given information
	 * 
	 * @param route
	 * @return the ID of newly inserted route if transaction is successful. -1 if not.
	 */
	public static int saveRoute( Route route ){
			
		String saveQuery = 
			"INSERT INTO Routes ( name, creatorID, points, description, routeColor, distance," +
								" pt_scenery, pt_difficulty, pt_safety, pt_rate, createdDate, modifiedDate)" +
								" VALUES ('"+ route.getName()+"'," +
										"'"+ route.getCreatorID()+"'," +
										"'"+ route.getPtValues()+"'," +
										"\""+ route.getDescription()+"\"," +
										"'"+ route.getColor()+"'," +
										"'"+ route.getDistance()+"'," +
										"'"+ 0 +"','"+ 0 +"','"+ 0 +"','"+ 0 +"'," +
										"NOW()," +
										"NOW())";
		
		if( DBConnector.executeUpdateQuery( saveQuery ) > 0 ){
			
			try {
				ResultSet  result = DBConnector.getQueryResult( "SELECT MAX(routeID) FROM Routes" );
				if( result != null )
					if( result.next() )
						return result.getInt( 1 );
			} catch (SQLException e) {
				System.out.println("DBUtilRoute.saveRoute() : Error getting ID of saved route"  );
				e.printStackTrace();
			}			
		}
			
		return -1;
	}
	
	
	/**
	 * Updates the given route with the given ID.
	 * 
	 * @param route
	 * @return 1 if the update is successful. -1 if not
	 */
	public static int modifyRoute( Route route ){
		
		String updateQuery = 
			"UPDATE Routes " +
			"SET name='" + route.getName()+ "', " +
				"routeColor='"+ route.getColor() +"'," +
				"points='"+ route.getPtValues() + "'," +
				"distance='"+ route.getDistance() + "'," +
				"description=\""+ route.getDescription() + "\", " +
				"modifiedDate= NOW()" +
			"WHERE routeID='"+ route.getId() +"'"; 
		
		return DBConnector.executeUpdateQuery( updateQuery );
			
	}
	
	/**
	 * Deletes the given route with the given ID.
	 * 
	 * @param route
	 * @return 1 if the delete is successful. -1 if not
	 */
	public static int deleteRoute( Route route ){
		
		String deleteQuery = 
			"DELETE FROM Routes " +
			"WHERE routeID='"+ route.getId() +"'"; 
		
		return DBConnector.executeUpdateQuery( deleteQuery );		
	}
		
	/**
	 * Function that search DB for routes that meets given search criteria
	 * 
	 * @param params
	 * @return
	 */
	public static List<Route> searchForRoutes( List<QueryParameter> params, int recordPerPage, int currentPage ){
		String searchQuery = 
			"SELECT * " +
			"FROM Routes " +
			"WHERE " + DBUtil.getSearchCriteria( params ) +
			" ORDER BY createdDate DESC " +
			"LIMIT " + (currentPage-1) * recordPerPage + ", " + recordPerPage;
		
		System.out.println("Search Query:" + searchQuery);
		
		List<Route> routes = new ArrayList<Route>();
		ResultSet resultSet = DBConnector.getQueryResult( searchQuery );	
		
		try {
			while( resultSet.next() ){				
				routes.add( resultSetToRoute( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBUtilRoute.searchForRoutes() : Error searching for routes");
			e.printStackTrace();
			return null;
		}
		finally{
			DBConnector.closeDBConnection();
		}
			
		return routes;
	}
	
	public static int getSearchForRoutesCount(List<QueryParameter> params) {
		String searchQuery = 
			"SELECT count(*) CNT " +
			"FROM Routes " +
			"WHERE " + DBUtil.getSearchCriteria( params );
		
		ResultSet resultSet = DBConnector.getQueryResult(searchQuery);

		int recCount = 0;
		
		try {
			while( resultSet.next() ){				
				recCount = resultSet.getInt("CNT");
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getSearchForRoutesCount() : Error getting route count");
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
		return recCount;
	}
	
	public static List<Route> getRouteByUserID( String userID, int recordPerPage, int currentPage ) {
		List<Route> routes = new ArrayList<Route>();
		String query = "SELECT * FROM Routes WHERE creatorID='" + userID + "' ORDER BY name " +
						"LIMIT " + (currentPage-1) * recordPerPage + ", " + recordPerPage;
				
		ResultSet resultSet = DBConnector.getQueryResult( query );
		
		try {
			while( resultSet.next() ){				
				routes.add( DBUtilRoute.resultSetToRoute( resultSet ) );
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getGroupByUserID() : Error getting group list for creatorID: " + userID);
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
				
		return routes;
	}
	
	public static int getRouteByUserIDCount( String userID ) {
		List<Route> routes = new ArrayList<Route>();
		String query = "SELECT COUNT(*) count FROM Routes WHERE creatorID='" + userID + "'";
		ResultSet resultSet = DBConnector.getQueryResult( query );
		
		try {
			if( resultSet.next() ){				
				return resultSet.getInt("count");
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getGroupByUserID() : Error getting group list for creatorID: " + userID);
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
				
		return 0;
	}

	
	public static boolean checkIfRatedRoute( String routeID, String userID ) {
		List<Route> routes = new ArrayList<Route>();
		String query = "SELECT COUNT(*) count FROM RateHistory WHERE userID='" + userID + "' AND routeID = '" + routeID + "'";
		ResultSet resultSet = DBConnector.getQueryResult( query );
		
		try {
			if( resultSet.next() ){				
				return resultSet.getInt("count") > 0;
			}
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.getGroupByUserID() : Error getting group list for creatorID: " + userID);
			e.printStackTrace();
		}
		finally{
			DBConnector.closeDBConnection();
		}
				
		return false;
	}	
	
	
	public static List<Float> rateRoute( String routeID, String userID, int scenery, int difficulty, int safety, int rate ) {
		List<Float> rates = new ArrayList<Float>();
		float fScenery, fDifficulty, fSafety, fRate;

		String saveQuery = "INSERT INTO RateHistory ( userID, routeID, pt_scenery, pt_difficulty, pt_safety, pt_rate, ratedDate )" +
						" VALUES ('"+ userID+"'," +
								"'"+ routeID +"'," +
								""+ scenery +"," +
								""+ difficulty+"," +
								""+ safety+"," +
								""+ rate+"," +
								"NOW())";

		System.out.println("query : " + saveQuery);
		if( DBConnector.executeUpdateQuery( saveQuery ) < 1 ){
			return null;
		}
		
		String query = "SELECT AVG(pt_scenery) SCENERY, AVG(pt_difficulty) DIFFICULTY, AVG(pt_safety) SAFETY, AVG(pt_rate) RATE FROM RateHistory WHERE routeID ='" + routeID + "'";
		
		ResultSet resultSet = DBConnector.getQueryResult( query );		
		try {
			if( !resultSet.first() ) throw new SQLException();

			fScenery = resultSet.getFloat("SCENERY");
			fDifficulty = resultSet.getFloat("DIFFICULTY");
			fSafety = resultSet.getFloat("SAFETY");
			fRate = resultSet.getFloat("RATE");
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.rateRoute() : Error getting average values");
			e.printStackTrace();
			DBConnector.closeDBConnection();
			return null;
		}

		String updateQuery = "UPDATE Routes " +
							"SET pt_scenery=" + fScenery +
								" , pt_difficulty=" + fDifficulty +
								" , pt_safety=" + fSafety +
								" , pt_rate=" + fRate +
								" WHERE routeID = '" + routeID + "'";

		System.out.println("query : " + updateQuery);
		if( DBConnector.executeUpdateQuery( updateQuery ) < 1 ){
			return null;
		}

		rates.add(fScenery);
		rates.add(fDifficulty);
		rates.add(fSafety);
		rates.add(fRate);
		
		return rates;
	}
	
	/**
	 * Utility function that gets Route object from a resultset row
	 * 
	 * @param resultSet
	 * @return
	 */
	private static Route resultSetToRoute( ResultSet resultSet ){
		
		int id;
		String name;
		String color, ptValues, description,creatorId;
		
		float distance;
		int ps_scenery, pt_difficulty, pt_safety;
	    float pt_rate;
		
		Date createdDate, modifiedDate;
		
		try {
			id = resultSet.getInt("routeID");
			name = resultSet.getString( "name" );
			creatorId = resultSet.getString("creatorId");
			ptValues = resultSet.getString( "points" );
			description = resultSet.getString("description");
			color = resultSet.getString( "routeColor" );
			distance = resultSet.getFloat("distance");
			ps_scenery = resultSet.getInt("pt_scenery");
			pt_difficulty = resultSet.getInt("pt_difficulty");
			pt_safety = resultSet.getInt("pt_safety");
			pt_rate = resultSet.getFloat("pt_rate");
			createdDate = resultSet.getDate("createdDate");
			modifiedDate = resultSet.getDate("modifiedDate");
			
			return new Route( id, name, creatorId, ptValues, description, color, distance, 
							  ps_scenery, pt_difficulty, pt_safety, pt_rate, 
							  createdDate, modifiedDate  );
			
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.resultSetToRoute() : Error getting route");
			e.printStackTrace();
		}
		
		return null;
	}
	
}
