package com.revature.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Used to retrieve a connection to the database
public class ConnectionUtil {
	
	// Reuse this connection instead of creating more connections to the database
	private static Connection con;
	
	// Default method for establishing connection
	public static Connection getConnection() throws SQLException {
		return getConnectionFromEnv();
		//return getConnectionFromFile();
	}
	
	// Ugly and gross; don't use
//	public static Connection getHardCodedConnection() throws SQLException {
//		String url = "";
//		String username = "";
//		String password = "";
//		
//		if (con == null || con.isClosed()) {
//			con = DriverManager.getConnection(url, username, password);			
//		}
//		return con;
//	}
	
	// Remember to .gitignore the .properties file to prevent credential leak when pushing
	public static Connection getConnectionFromFile() throws IOException, SQLException {
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		prop.load(loader.getResourceAsStream("creds.properties"));
		
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		
		if (con == null || con.isClosed()) {
			con = DriverManager.getConnection(url, username, password);			
		}
		return con;
	}
	
	// Preferred way, requires editing value in Windows
	public static Connection getConnectionFromEnv() throws SQLException {
		/*
		 * Search for Edit System Environment Variables
		 * 		- click on Environment Variables
		 * 		- under System Variables, click New
		 * 		- enter a variable name and its value
		 * 			- i.e.: "DB_URL" : "localhost:5432"
		 * 		- press OK
		 */
		String url = System.getenv("DB_URL");
		String username = System.getenv("DB_USER");
		String password = System.getenv("DB_PASS");
		
		if (con == null || con.isClosed()) {
			con = DriverManager.getConnection(url, username, password);			
		}
		return con;
	}
}
