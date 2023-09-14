package com.servermonks.assetmanagement.storage;

import java.sql.SQLException;

import com.servermonks.assetmanagement.dao.impl.*;

public class StorageFactory {
	
	public static Object getStorage(int i ) throws SQLException {
		//return storage according to need;
		switch(i) {
			case 1:
				return new AdminDaoImpl();
		
			case 2:
				return new AssetDaoImpl();
		
			case 3:
				return new BorrowHistoryDaoImpl();
			
			case 4:
				return new CategoryDaoImpl();
			
			case 5:
				return new UserDaoImpl();
			
			default:
				return null;
		}
		
	}
}
