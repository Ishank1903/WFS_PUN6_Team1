package com.servermonks.assetmanagement.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.servermonks.assetmanagement.beans.Asset;
import com.servermonks.assetmanagement.beans.BorrowHistory;
import com.servermonks.assetmanagement.beans.Category;
import com.servermonks.assetmanagement.beans.User;
import com.servermonks.assetmanagement.exceptions.AssetAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.AssetIsNotAvailableException;
import com.servermonks.assetmanagement.exceptions.AssetNotFoundException;
import com.servermonks.assetmanagement.exceptions.CategoryAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.CategoryNotFoundException;
import com.servermonks.assetmanagement.exceptions.InternalServerError;
import com.servermonks.assetmanagement.exceptions.UserAccessDeniedException;
import com.servermonks.assetmanagement.exceptions.UserAlreadyHaveSameCategoryAsset;
import com.servermonks.assetmanagement.exceptions.UserEmailIdAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.UserFailedLoginException;
import com.servermonks.assetmanagement.exceptions.UserIDAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.UserIsBannedException;
import com.servermonks.assetmanagement.exceptions.UsernameAlreadyPresentException;

public interface ManagerService{
	/*
	AssetService assetService;
	CategoryService categoryService;
	Use above in class 
	*/

	// Please check User usrObject.ROle == "User" or "Admin" before every function so the  only admin get access to special functionalities.
	
	public List<Asset>  showAllAvailableAsset() throws SQLException, CategoryNotFoundException;    				
	
	public List<Asset> showAllAvailableAssetByType(int typeId) throws SQLException, CategoryNotFoundException;     
	
	public List<BorrowHistory> getIssuedAssets(User u) throws SQLException;         		 
	
	public Date daysOfBanRemaining(User u , int categoryId) throws SQLException;      
	
	void addUser(User admin, User user) throws UsernameAlreadyPresentException, UserEmailIdAlreadyPresentException,
	
	UserIDAlreadyPresentException, SQLException, UserAccessDeniedException, InternalServerError;
	
	int addUsers(User admin, List<User> userList) throws UserAccessDeniedException;						   
	
	void modifyAsset(User admin, Asset asset) throws SQLException, CategoryNotFoundException, UserAccessDeniedException;            
	
	void modifyCategory(User admin, Category c) throws UserAccessDeniedException, SQLException, CategoryAlreadyPresentException;			
	
	public int totalAssetsAvailable() throws SQLException;					
	
	public void delete(User u) throws SQLException;						
	
	public boolean delete(Asset a) throws SQLException; 					
	
	public boolean delete(Category c) throws SQLException;					
	
	void addAsset(User user, Asset asset) throws UserAccessDeniedException, CategoryNotFoundException, SQLException ;
	
	boolean addCategory(User user, Category category) throws SQLException, CategoryAlreadyPresentException, UserAccessDeniedException;
	
	int addAssets(User u, List<Asset> assetList) throws UserAccessDeniedException;
	
	void borrowAsset(User user, int assetId) throws SQLException, UserAlreadyHaveSameCategoryAsset, UserIsBannedException, CategoryNotFoundException, AssetNotFoundException, UserFailedLoginException, AssetIsNotAvailableException, InternalServerError;
	
	User getUserObject(String userid, String password) throws SQLException, UserFailedLoginException, InternalServerError;

	double getUserFeesByAssetId(User u, int assetId) throws SQLException;
	
	void returnUsingAssetId(User u, int assetid) throws SQLException;
	
}
