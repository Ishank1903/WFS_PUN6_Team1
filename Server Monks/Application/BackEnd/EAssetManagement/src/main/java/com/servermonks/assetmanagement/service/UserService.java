package com.servermonks.assetmanagement.service;

import com.servermonks.assetmanagement.beans.Asset;
import com.servermonks.assetmanagement.beans.User;
import com.servermonks.assetmanagement.exceptions.InternalServerError;
import com.servermonks.assetmanagement.exceptions.UserEmailIdAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.UserIDAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.UserNotFoundException;
import com.servermonks.assetmanagement.exceptions.UsernameAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.WrongInputException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    // Create a new user
    void createUser(User user) throws UsernameAlreadyPresentException, UserEmailIdAlreadyPresentException, UserIDAlreadyPresentException, SQLException, InternalServerError;

    // Update an existing user
    void updateUser(User user, int code) throws UsernameAlreadyPresentException, UserIDAlreadyPresentException, UserEmailIdAlreadyPresentException, WrongInputException, SQLException, InternalServerError;

    // Retrieve a user by their unique ID
    User getUserById(int userId) throws SQLException;

    // Retrieve a user by their email address
    User getUserByEmail(String email) throws SQLException;

    // Delete a user by their unique ID
    void deleteUser(int userId) throws SQLException;

    // Retrieve a list of all users
    List<User> getAllUsers() throws SQLException;

	void updateUser(User user) throws SQLException, InternalServerError;
	
	public  User verifyUser(String uname,String password) throws SQLException, InternalServerError;

	
}
