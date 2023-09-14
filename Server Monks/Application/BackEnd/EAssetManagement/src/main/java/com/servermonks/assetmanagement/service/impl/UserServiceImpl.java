package com.servermonks.assetmanagement.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.servermonks.assetmanagement.beans.User;
import com.servermonks.assetmanagement.dao.UserDao;
import com.servermonks.assetmanagement.dao.impl.UserDaoImpl;
import com.servermonks.assetmanagement.exceptions.InternalServerError;
import com.servermonks.assetmanagement.exceptions.UserEmailIdAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.UserIDAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.UsernameAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.WrongInputException;
import com.servermonks.assetmanagement.service.CryptographyService;
import com.servermonks.assetmanagement.service.UserService;
import com.servermonks.assetmanagement.storage.StorageFactory;

public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public UserServiceImpl() throws SQLException {
		
		userDao = (UserDaoImpl) StorageFactory.getStorage(5);	
		
	}

	@Override
	public void createUser(User user) throws UsernameAlreadyPresentException, UserEmailIdAlreadyPresentException, UserIDAlreadyPresentException, SQLException, InternalServerError {
		
		if(	userDao.isUsernamePresent(user.getUsername()) ){
			
			throw new UsernameAlreadyPresentException("Username already Present");
		
		}
		if(userDao.isUserIdPresent(user.getUserId())) {
			
			throw new UserIDAlreadyPresentException("User ID already Present.");
			
		}
		if( userDao.isUserEmailPresent(user.getEmail())) {
			
			throw new UserEmailIdAlreadyPresentException("User Email ID already Present.");

		}
		//Add User
		user.setUsername(CryptographyService.encryptString(user.getUsername()));
		user.setPassword(CryptographyService.encryptString(user.getPassword()));
		userDao.addUser(user);		
		
	}

	@Override
	public void updateUser(User user,int code) throws UsernameAlreadyPresentException, UserIDAlreadyPresentException, UserEmailIdAlreadyPresentException, WrongInputException, SQLException, InternalServerError {
		/*
		 -------------  Sensitive User Details Update  --------------------
		 
		 	code: 1 = updating Username;
		 	code: 2 = updating email;
			code: 3 = updating Username + email;
			code: 4 = WrongInputException
			
		*/
		
		if(code == 1 || code==3 ) {
			if(	userDao.isUsernamePresent(user.getUsername()) ){
				
				throw new UsernameAlreadyPresentException("Username already Present");
			
			}	
		}
		if(code == 2 || code==3 ) {
			if( userDao.isUserEmailPresent(user.getEmail())) {	
				
				throw new UserEmailIdAlreadyPresentException("User Email ID already Present.");
			
			}
		}
		
		if(code >=4 || code<=0) {
			throw new WrongInputException("Invalid Code Value to update sensitive information Valid Range [1 to 3]");
		}
		user.setUsername(CryptographyService.encryptString(user.getUsername()));
		user.setPassword(CryptographyService.encryptString(user.getPassword()));
		userDao.updateUser(user);
		
	}
	@Override
	public void updateUser(User user) throws SQLException, InternalServerError {
		// Don't use this method for updating sensitive information of User 
		
		User temp = this.getUserById(user.getUserId());
	
		user.setUsername(temp.getUsername());
		user.setEmail(temp.getEmail());
		
		user.setUsername(CryptographyService.encryptString(user.getUsername()));
		user.setPassword(CryptographyService.encryptString(user.getPassword()));
		userDao.updateUser(user);
		
	}

	@Override
	public User getUserById(int userId) throws SQLException {
		
		return userDao.getUserById(userId);
	
	}

	@Override
	public User getUserByEmail(String email) throws SQLException {
		
		return userDao.getUserByEmail(email);
	
	}

	@Override
	public void deleteUser(int userId) throws SQLException {
		
		userDao.deleteUser(userId);
	
	}

	@Override
	public List<User> getAllUsers() throws SQLException {
		return userDao.getAllUsers();
	}

	@Override
	public User verifyUser(String uname,String password) throws SQLException, InternalServerError {
		
		return userDao.getUserByUsernameAndPassword(CryptographyService.encryptString(uname), CryptographyService.encryptString(password));
	}

}
