package com.servermonks.assetmanagement.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.servermonks.assetmanagement.beans.User;
import com.servermonks.assetmanagement.dao.UserDao;
import com.servermonks.assetmanagement.utils.DBUtil;

public class UserDaoImpl implements UserDao {

    private static Connection connection;

    public UserDaoImpl() throws SQLException {
        // Get the database connection from the singleton class
        try {
			connection = DBUtil.createConnection();
		} catch (SQLException e) {
			throw new SQLException("Failed to connect SQL Server please try again...");
			//e.printStackTrace();
		}
    }

    @Override
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO User (userId, name, telephone, email, username, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getTelephone());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getUsername());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        	//e.printStackTrace();
            throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE User SET name=?, telephone=?, email=?, username=?, password=? WHERE userId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getTelephone());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
    }

    @Override
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM User WHERE userId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
        return null; // User not found
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM User WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
        return null; // User not found
    }

    @Override
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM User WHERE userId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator"); 
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
        return users;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM User WHERE username=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
        return null; // User not found
    }
    
    @Override
    public boolean isUsernamePresent(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM User WHERE username=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count is greater than 0, the username is present
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
        return false; // Username not found or error occurred
    }

    @Override
    public boolean isUserIdPresent(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM User WHERE userId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count is greater than 0, the user ID is present
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
        return false; // User ID not found or error occurred
    }
    
    @Override
    public boolean isUserEmailPresent(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM User WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count is greater than 0, the email is present
                }
            }
        } catch (SQLException e) {
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        	//e.printStackTrace();
        }
        return false; // Email not found or error occurred
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM User WHERE username=? AND password=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("SQL Exception... Please Contact Administrator");
        }
        return null; // User not found or password doesn't match
    }

    
    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("userId");
        String name = resultSet.getString("name");
        String telephone = resultSet.getString("telephone");
        String email = resultSet.getString("email");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");

        return new User(userId,name,false, telephone, email, username, password);
    }
}
