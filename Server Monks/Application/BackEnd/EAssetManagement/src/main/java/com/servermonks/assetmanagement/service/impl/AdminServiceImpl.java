package com.servermonks.assetmanagement.service.impl;

import com.servermonks.assetmanagement.dao.AdminDao;
import com.servermonks.assetmanagement.dao.impl.AdminDaoImpl;
import com.servermonks.assetmanagement.service.AdminService;
import com.servermonks.assetmanagement.storage.StorageFactory;

import java.sql.SQLException;

public class AdminServiceImpl implements AdminService{

    private static  AdminDao adminDao;
    
    public AdminServiceImpl() throws SQLException{
        
    	adminDao = (AdminDaoImpl) StorageFactory.getStorage(1); 
    }
    
    @Override
    public boolean isAdminByUserId(int userId) throws SQLException {
    	return adminDao.isAdminByUserId(userId);
    }
    
}

