package com.servermonks.assetmanagement.test;

import java.sql.SQLException;

import com.servermonks.assetmanagement.beans.Asset;
import com.servermonks.assetmanagement.beans.User;
import com.servermonks.assetmanagement.exceptions.AssetIsNotAvailableException;
import com.servermonks.assetmanagement.exceptions.AssetNotFoundException;
import com.servermonks.assetmanagement.exceptions.CategoryNotFoundException;
import com.servermonks.assetmanagement.exceptions.InternalServerError;
import com.servermonks.assetmanagement.exceptions.UserAccessDeniedException;
import com.servermonks.assetmanagement.exceptions.UserAlreadyHaveSameCategoryAsset;
import com.servermonks.assetmanagement.exceptions.UserFailedLoginException;
import com.servermonks.assetmanagement.exceptions.UserIsBannedException;
import com.servermonks.assetmanagement.service.ManagerService;
import com.servermonks.assetmanagement.service.impl.ManagerServiceImpl;

public class TestManagerService {
	
	public static void main(String args[]) {
		try {
			ManagerService managerService = new ManagerServiceImpl();
			User u = managerService.getUserObject("johndoe", "password123");
			System.out.println(u);
			managerService.addAsset(u,new Asset(99,"Jabra","Evolve 2050", new java.sql.Date(new java.util.Date().getTime()),true, 3));
			System.out.println(managerService.getIssuedAssets(u));
			
			managerService.showAllAvailableAsset().forEach(System.out::println);
			managerService.showAllAvailableAssetByType(3).forEach(System.out::println);
			
			managerService.borrowAsset(u,99);
			//managerService.borrowAsset(u, 99); exception cannot add the data of same type again 
			System.out.println(managerService.getUserFeesByAssetId(u,99));
			managerService.returnUsingAssetId(u, 99);
		
		} catch (SQLException | UserFailedLoginException | CategoryNotFoundException | UserAlreadyHaveSameCategoryAsset | UserIsBannedException | AssetNotFoundException | AssetIsNotAvailableException | UserAccessDeniedException | InternalServerError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
