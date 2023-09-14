package com.servermonks.assetmanagement.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	static String CONN_URL = "jdbc:mysql://localhost:3306/demo?autoReconnect=true&useSSL=false";
	static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	static String DB_USER = "root";
	static String PASSWORD = "12345678";
	private static Connection con;
	
	static {
		con = null;
	}
	public static Connection createConnection() throws SQLException {
		if (con == null) {
			
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				con = DriverManager.getConnection(CONN_URL, DB_USER, PASSWORD);
				if(con != null) {
					System.out.println("Connection Done...");
				}
	
		}
		return con;
	}
	
	public static void closeConnection() throws SQLException {
		
		if(con != null) {
				con.close();
				con = null;
		}
		
	}
	
}

