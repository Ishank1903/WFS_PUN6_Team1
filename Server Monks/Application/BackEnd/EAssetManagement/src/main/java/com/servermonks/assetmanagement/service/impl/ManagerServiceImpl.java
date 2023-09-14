package com.servermonks.assetmanagement.service.impl;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.servermonks.assetmanagement.service.AdminService;
import com.servermonks.assetmanagement.service.AssetService;
import com.servermonks.assetmanagement.service.BorrowHistoryService;
import com.servermonks.assetmanagement.service.CategoryService;
import com.servermonks.assetmanagement.service.ManagerService;
import com.servermonks.assetmanagement.service.UserService;

public class ManagerServiceImpl implements ManagerService {

    private static AssetService assetService;
    private static CategoryService categoryService;
    private static UserService userService;
    private static BorrowHistoryService borrowService;
    private static AdminService adminService;
    
    public ManagerServiceImpl() throws SQLException{
    		
    		assetService = new AssetServiceImpl();
    		categoryService = new CategoryServiceImpl();
			userService = new UserServiceImpl();
			borrowService = new BorrowHistoryServiceImpl();
			adminService = new AdminServiceImpl();
    }

    @Override
    public List<Asset> showAllAvailableAsset() throws SQLException, CategoryNotFoundException {
    	return assetService.getAllAssets();
    }

    @Override
    public List<Asset> showAllAvailableAssetByType(int typeId) throws SQLException, CategoryNotFoundException {
        return assetService.getAssetByCategoryId(typeId);    
    }

    @Override
    public void borrowAsset(User user, int assetId ) throws SQLException, UserAlreadyHaveSameCategoryAsset, UserIsBannedException, CategoryNotFoundException, AssetNotFoundException, UserFailedLoginException, AssetIsNotAvailableException, InternalServerError {
    	
    	if(borrowService.isUserAlreadyHaveSameTypeAsset(user.getUserId(), assetId)) {
    		throw new UserAlreadyHaveSameCategoryAsset("Cannot Allocate Two Asset of Same type to User");
    	}
    	if(borrowService.isUserBannned(user.getUserId())) {
    		throw new UserIsBannedException("User is Banned for Specific Action Please Contact Admin");
    	}
		/*
		 * Future Update if required reverification of user while borrowing asset.
		 *   
		 * User u = userService.verifyUser(user.getUsername(), user.getPassword());
		 * if(u.getUserId() != user.getUserId()) { throw new
		 * UserFailedLoginException("The Username or Password provided is Wrong"); }
		 */
		if(!assetService.getIsAvailable(assetId)) {
			throw new AssetIsNotAvailableException("The Username or Password provided is Wrong");
		}
		
    	Asset a = assetService.getAssetById(assetId);
    	Date currentDate = new java.util.Date();
    	BorrowHistory borrowHistory = new BorrowHistory(user.getUserId() , assetId , new java.sql.Date(currentDate.getTime()), null,0, false, null);
    	//BorrowHistory( int userId, int assetId, Date borrowDate, Date returnDate, double lateReturnFee, boolean isBanned, Date bannedUntil)
    	borrowService.addBorrowHistory(borrowHistory);
    	
    }

    @Override
    public List<BorrowHistory> getIssuedAssets(User u) throws SQLException {
        return borrowService.getBorrowHistoryByUserId(u.getUserId());
    }

    @Override
    public Date daysOfBanRemaining(User u, int categoryId) throws SQLException {
        return borrowService.getNoOfDaysBanRemaining( u.getUserId() );
 
    }
  

    @Override
    public void addUser(User admin,User user) throws UsernameAlreadyPresentException, UserEmailIdAlreadyPresentException, UserIDAlreadyPresentException, SQLException, UserAccessDeniedException, InternalServerError {
        if(admin.getRole() == true) { // If role is admin execute below 
        	userService.createUser(user);
    	}
        throw new UserAccessDeniedException("Unauthorized to access feature please contact admin");
        
    }

    @Override
    public int addUsers(User admin,List<User> userList) throws UserAccessDeniedException {
    	
    	if(admin.getRole() == false) { // If role is not admin execute below 
    		throw new UserAccessDeniedException("Unauthorized to access feature please contact admin");
        }
    	
    	int count=0; // to let know the user how much successfully users added. 
    	 			// The exceptions are ignored 
    	for(User u : userList) {
    		try {
    			this.addUser(admin, u);
    			count++;
    		}
    		catch(Exception e) {
    			 // Ignore Exception 
    		}
    	}
        return count;
        
    }
    

    @Override
    public boolean addCategory(User admin, Category category) throws SQLException, CategoryAlreadyPresentException, UserAccessDeniedException {
    	if(admin.getRole() == false) { // If role is not admin execute below 
    		throw new UserAccessDeniedException("Unauthorized to access feature please contact admin");
        }
    	categoryService.addCategory(category);
    	return false;    
    }

    @Override
    public void modifyAsset(User admin,Asset asset) throws SQLException, CategoryNotFoundException, UserAccessDeniedException {
    	if(admin.getRole() == false) { // If role is not admin execute below 
    		throw new UserAccessDeniedException("Unauthorized to access feature please contact admin");
        }
    	assetService.updateAsset(asset);
        
    }

    @Override
    public void modifyCategory(User admin, Category c) throws UserAccessDeniedException, SQLException, CategoryAlreadyPresentException {
    	if(admin.getRole() == false) { // If role is not admin execute below 
    		throw new UserAccessDeniedException("Unauthorized to access feature please contact admin");
        }
    	categoryService.updateCategory(c);
    }

    @Override
    public int totalAssetsAvailable() throws SQLException {
    	// Can be Access by User
        assetService.getCoutOfAvailableAssets();
    	return 0;
    }

    @Override
    public void delete(User u) throws SQLException {
    	userService.deleteUser(u.getUserId());
    }

    @Override
    public boolean delete(Asset a) throws SQLException {
    	return assetService.deleteAsset(a.getAssetID());
        
    }

    @Override
    public boolean delete(Category c) throws SQLException {
    	return categoryService.deleteCategory(c.getCategoryId());  
    }
    
    @Override
    public int addAssets(User admin,List<Asset> assetList) throws UserAccessDeniedException {
    	if(admin.getRole() == false) { // If role is not admin execute below 
    		throw new UserAccessDeniedException("Unauthorized to access feature please contact admin");
        }
    	int count=0; // to let know the user how much successfully assets added. 
		// The exceptions are ignored 
		for(Asset u : assetList) {
			try {
				this.addAsset(admin, u);
				count++;
			}
			catch(Exception e) {
				 // Ignore Exception 
			}
		}
		return count;
    	
    }

	@Override
	public void addAsset(User admin, Asset asset) throws UserAccessDeniedException, CategoryNotFoundException, SQLException {
		if(admin.getRole() == false) { // If role is not admin execute below 
    		throw new UserAccessDeniedException("Unauthorized to access feature please contact admin");
        }
		assetService.addAsset(asset);
		
	}

	@Override
	public User getUserObject(String userid, String password) throws SQLException, UserFailedLoginException, InternalServerError {
		User u = userService.verifyUser(userid, password);
		if(u == null) {
		
			throw new UserFailedLoginException("The Username or Password provided is Wrong");
		
		}
		u.setRole(adminService.isAdminByUserId(u.getUserId()));
		return u;
	
	}

	@Override
	public double getUserFeesByAssetId(User u,int assetId) throws SQLException {
		borrowService.getBorrowHistoryByUserIdAndAssetId(u.getUserId(), assetId);
		return 0;
	}

	@Override
	public void returnUsingAssetId(User u,int assetid) throws SQLException {
		Date currentDate = new java.util.Date();
		borrowService.updateReturnDate(u.getUserId(), assetid);
		
	}
	
	
	
}
