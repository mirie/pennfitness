package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnector {

	//DB parameters
	private static String hostName = "fling-l.seas.upenn.edu/inseob";
	private static String dbUserName = "inseob";
	private static String dbUserPassword = "zzzz1234";
	
	//Connection parameters
	private static Statement s = null ;
	private static Connection con = null ;

	
	//TODO
	//Connection pool
	public static boolean initializeDBConnection(){
		try {
			// check whether a connection was already made
			if( con != null && con.isValid(0) ) return true; // Should check s too? - inseob
			
			Class.forName("com.mysql.jdbc.Driver");
//			con=DriverManager.getConnection(
//					"jdbc:mysql://" + hostName, dbUserName, dbUserPassword); 
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev1?user=appdev1&password=appdev1");
			
			s = con.createStatement();
			
		} catch (Exception e) {
			System.out.println("DBConnector.initializeDBConnection() : Error initializing DB connection ");
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	} 
	

	public static ResultSet getQueryResult( String query ){	
		
		initializeDBConnection();
		
		try {	
			return s.executeQuery( query );	
		}
		catch (SQLException e) {
			System.out.println("DBConnector.getQueryResult() : Error executing resultset query" + query);
			e.printStackTrace();
		}
		
		return null;	
		
	}
	

	public static int executeUpdateQuery( String query ){		
		
		initializeDBConnection();
		
		try {
			return s.executeUpdate( query );
		} 
		catch (SQLException e) {
			System.out.println("DBConnector.sth() :  Error executing resultset query" + query);
			e.printStackTrace();
			closeDBConnection();
		}
		
		return -1;
	}
	
	
	public static boolean closeDBConnection(){
		try {
			if( s != null ) {
				s.close();
				s = null;
			}
			if( con != null ) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			System.out.println("DBConnector.closeDBConnection() : Error closing DB connection");
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
}
