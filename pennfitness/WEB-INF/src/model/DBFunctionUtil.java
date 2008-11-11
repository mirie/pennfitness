package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.Route;

public class DBFunctionUtil {
	
	
	public static boolean saveRoute( Route route ){
		
		List<String> params = new ArrayList<String>();
		params.add( "1" );
		params.add( route.getName() );
		params.add( route.getColor() );
		params.add( route.getRouteInfo() );
		
		if(DBConnector.executeUpdateQuery( createInsertQuery( "routes", params ) ))
			return true;

		
		return false;
			
	}
	
	public static Route getRouteById( String id ){
	
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Routes WHERE id='"+id+"'" );
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
	
	public static List<String> getRouteNames(){
		
		List<Route> routes = getAllRoutes();
		List<String> names = new ArrayList<String>();
		
		Iterator<Route> iterator = routes.iterator();
		while(iterator.hasNext()){
			names.add( iterator.next().getName() );
		}
		
		return names;
		
	}
	
	public static List<Route> getAllRoutes( ){
		
		List<Route> routes = new ArrayList<Route>();
		ResultSet resultSet = DBConnector.getQueryResult( "SELECT * FROM Routes" );	
		
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
	
	private static Route resultSetToRoute( ResultSet resultSet ){
		String id, name, color, routeInfo;
		try {
			id = resultSet.getString("routeID");
			name = resultSet.getString( "name" );
			color = resultSet.getString( "routeColor" );
			routeInfo = resultSet.getString( "points" );
			
			return new Route( id, name, color, routeInfo );
			
		} 
		catch (SQLException e) {
			System.out.println("DBFunctionUtil.resultSetToRoute() : Error getting route");
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String createInsertQuery( String tableName, List<String> parameters ){
		
		Iterator<String> iterator = parameters.iterator();
		
		String params = "";
		while( iterator.hasNext() ){
			params += "'" + iterator.next() +"',";
		}
		
		if( !params.equals("") ){
			params = params.substring( 0, params.length()-1 );
		}
		
		return "INSERT INTO "+ tableName + " VALUES(" + params + ")";
		
	}
	

}
