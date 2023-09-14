package com.servermonks.assetmanagement.dao;

import com.servermonks.assetmanagement.beans.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

	void addUser(User user) throws SQLException;

    void updateUser(User user) throws SQLException;

    User getUserById(int userId) throws SQLException;

    User getUserByEmail(String email) throws SQLException;

    void deleteUser(int userId) throws SQLException;

    List<User> getAllUsers() throws SQLException;

	User getUserByUsername(String username) throws SQLException;

	boolean isUsernamePresent(String username) throws SQLException;

	boolean isUserIdPresent(int userId) throws SQLException;

	boolean isUserEmailPresent(String email) throws SQLException;

	User getUserByUsernameAndPassword(String username, String password) throws SQLException;
	
	
}

