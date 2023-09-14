package com.servermonks.assetmanagement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.servermonks.assetmanagement.beans.Category;
import com.servermonks.assetmanagement.dao.CategoryDao;
import com.servermonks.assetmanagement.utils.DBUtil;


public class CategoryDaoImpl  implements CategoryDao{

    // JDBC connection
    private static Connection connection;

    public CategoryDaoImpl() {
        try {
            connection = DBUtil.createConnection();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }
    
    @Override
    public boolean addCategory(Category category) throws SQLException {
        String sql = "INSERT INTO Category (categoryId, name, lendingPeriod, lateFees, noOfDaysBanned) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, category.getCategoryId());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setInt(3, category.getLendingPeriod());
            preparedStatement.setDouble(4, category.getLateFees());
            preparedStatement.setInt(5, category.getNoOfDaysBanned());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
    }

    
    @Override
    public Category getCategoryById(int categoryId) throws SQLException {
        String sql = "SELECT * FROM Category WHERE categoryId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToCategory(resultSet);
            }
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator"); 
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                categories.add(mapResultSetToCategory(resultSet));
            }
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator"); 
        }
        return categories;
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE Category SET name = ?, lendingPeriod = ?, lateFees = ?, noOfDaysBanned = ? WHERE categoryId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getLendingPeriod());
            preparedStatement.setDouble(3, category.getLateFees());
            preparedStatement.setInt(4, category.getNoOfDaysBanned());
            preparedStatement.setInt(5, category.getCategoryId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
    }

    @Override
    public boolean deleteCategory(int categoryId) throws SQLException {
        String sql = "DELETE FROM Category WHERE categoryId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
    }
    
    @Override
    public boolean isCategoryPresentById(int categoryId) throws SQLException{
        String sql = "SELECT COUNT(*) FROM Category WHERE categoryId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count is greater than 0, the category ID is present
                }
            }
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator"); 
        }
        return false; // Category ID not found or error occurred
    }

    
    private Category mapResultSetToCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setCategoryId(resultSet.getInt("categoryId"));
        category.setName(resultSet.getString("name"));
        category.setLendingPeriod(resultSet.getInt("lendingPeriod"));
        category.setLateFees(resultSet.getDouble("lateFees"));
        category.setNoOfDaysBanned(resultSet.getInt("noOfDaysBanned"));
        return category;
    }
}
