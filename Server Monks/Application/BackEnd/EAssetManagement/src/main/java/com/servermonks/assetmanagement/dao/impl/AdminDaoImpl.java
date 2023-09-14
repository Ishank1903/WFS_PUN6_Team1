package com.servermonks.assetmanagement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.servermonks.assetmanagement.dao.AdminDao;
import com.servermonks.assetmanagement.utils.DBUtil;

public class AdminDaoImpl implements AdminDao {
    
    private Connection connection;

    public AdminDaoImpl() throws SQLException {
        connection = DBUtil.createConnection();
    }

    @Override
    public boolean isAdminByUserId(int userId) throws SQLException {
    	String sqlQuery = "SELECT * FROM Admin WHERE userid = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
            	
                return true; // Create an Admin object or return the data as needed
            
            }
        } catch (SQLException e) {
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
        
        return false; // Return null if no admin with the specified user ID is found
    }
    
   
}
