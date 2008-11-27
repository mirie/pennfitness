package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entities.Event;
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
		
		List<Route> routes = getAllRoutes();
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
	public static List<Route> getAllRoutes( ){
		
		List<Route> routes = new ArrayList<Route>();
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Routes ORDER BY createdDate DESC" );	
		
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
	
	/**
	 * Saves a new route with the given information
	 * 
	 * @param route
	 * @return the ID of newly inserted route if transaction is successful. -1 if not.
	 */
	public static int saveRoute( Route route ){
			
		String saveQuery = 
			"INSERT INTO Routes ( name, creatorID, points, description, routeColor, distance," +
								" pt_scenery, pt_difficulty, pt_safety, pt_rate, routeDate, createdDate, modifiedDate)" +
								" VALUES ('"+ route.getName()+"'," +
										"'"+ route.getCreatorID()+"'," +
										"'"+ route.getPtValues()+"'," +
										"'"+ route.getDescription()+"'," +
										"'"+ route.getColor()+"'," +
										"'"+ route.getDistance()+"'," +
										"'"+ 0 +"','"+ 0 +"','"+ 0 +"','"+ 0 +"'," +
										"NOW()," + 
										"NOW()," +
										"NOW())";
		
		if( DBConnector.executeUpdateQuery( saveQuery ) > 0 ){
			
			try {
				ResultSet  result = DBConnector.getQueryResult( "SELECT MAX(routeID) FROM Routes" );
				if( result != null )
					if( result.next() )
						return result.getInt( 1 );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
				"description='"+ route.getDescription() + "' " +
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
	public static List<Route> searchForRoutes( List<QueryParameter> params ){
		String searchQuery = 
			"SELECT * " +
			"FROM Routes " +
			"WHERE " + DBUtil.getSearchCriteria( params );
		
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
		
		Date routeDate, createdDate, modifiedDate;
		
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
			routeDate = resultSet.getDate("routeDate");
			createdDate = resultSet.getDate("createdDate");
			modifiedDate = resultSet.getDate("modifiedDate");
			
			return new Route( id, name, creatorId, ptValues, description, color, distance, 
							  ps_scenery, pt_difficulty, pt_safety, pt_rate, 
							  routeDate, createdDate, modifiedDate  );
			
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.resultSetToRoute() : Error getting route");
			e.printStackTrace();
		}
		
		return null;
	}
	
}
