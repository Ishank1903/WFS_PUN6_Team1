package com.servermonks.assetmanagement.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.servermonks.assetmanagement.beans.Asset;
import com.servermonks.assetmanagement.dao.AssetDao;
import com.servermonks.assetmanagement.dao.impl.AssetDaoImpl;
import com.servermonks.assetmanagement.exceptions.AssetNotFoundException;
import com.servermonks.assetmanagement.exceptions.CategoryNotFoundException;
import com.servermonks.assetmanagement.service.AssetService;
import com.servermonks.assetmanagement.service.CategoryService;
import com.servermonks.assetmanagement.storage.StorageFactory;

public class AssetServiceImpl implements AssetService{

    private AssetDao assetDao;
    private CategoryService categoryService;
    
    public AssetServiceImpl() {
        try {
        	
            assetDao = (AssetDaoImpl) StorageFactory.getStorage(2);
            categoryService = new CategoryServiceImpl();
            
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error Connecting Database ... Contact Admin");
        }
    }
    
    public boolean addAsset(Asset asset) throws CategoryNotFoundException, SQLException {
    	if(asset.getCategory() == null || asset.getCategory().getCategoryId() == 0) {
    		throw new CategoryNotFoundException("Category not Found Please add Category before adding Asset");
    	}
    	if(null == categoryService.getCategoryById(asset.getCategory().getCategoryId())) {
    		throw new CategoryNotFoundException("Category not Found Please add Category before adding Asset");
    	}
    	return assetDao.addAsset(asset);
	}
    

	public Asset getAssetById(int assetID) throws SQLException, CategoryNotFoundException, AssetNotFoundException {
    
    	Asset asset = assetDao.getAssetById(assetID);
    	if(asset == null) {
    		throw new AssetNotFoundException("The Asset Not Found");
    	}
    	asset.setCategory(categoryService.getCategoryById(asset.getCategory().getCategoryId()));
    	return asset;
    
    }

    public List<Asset> getAllAssets() throws SQLException, CategoryNotFoundException {
    	
    	List<Asset> assetList = assetDao.getAllAssets();
    	for(Asset asset : assetList) {
    		asset.setCategory(categoryService.getCategoryById(asset.getCategory().getCategoryId()));
    	}
    	return assetList;
    
    }

    public boolean updateAsset(Asset asset) throws SQLException, CategoryNotFoundException {
    	
    	if(asset.getCategory() == null || asset.getCategory().getCategoryId() == 0) {
    		throw new CategoryNotFoundException("Category not Found Please add Category before adding Asset");
    	}
    	if(null == categoryService.getCategoryById(asset.getCategory().getCategoryId())) {
    		throw new CategoryNotFoundException("Category not Found Please add Category before adding Asset");
    	}
    	return assetDao.updateAsset(asset);
    	
    }

    
    public boolean deleteAsset(int assetID) throws SQLException {
        return assetDao.deleteAsset(assetID);
    }

	@Override
	public int getCoutOfAvailableAssets() throws SQLException {
		return assetDao.getCountTotalAvailableAssets();
	}

	@Override
	public List<Asset> getAssetByCategoryId(int typeId) throws SQLException, CategoryNotFoundException {
		int count= 0;
		List <Asset> alist = new ArrayList<>();
		for(Asset a: getAllAssets()) {
			if(a.getCategory().getCategoryId() == typeId) {
				alist.add(a);
				count+=1;
			}
		}
		if(count >= 1) {
			return alist;
		}
		return null;
		
	}
	
	@Override
    public boolean setIsAvailable(int assetID) throws SQLException, AssetNotFoundException {
        // Check if the asset exists before updating its availability
        Asset asset = assetDao.getAssetById(assetID);
        if (assetDao.getAssetById(assetID) == null) {
            throw new AssetNotFoundException("Asset with ID " + assetID + " not found.");
        }
        // Update the availability status of the asset
        return assetDao.setIsAvailable(assetID);
    }
	
	@Override
    public boolean getIsAvailable(int assetID) throws SQLException, AssetNotFoundException {
        
        if (assetDao.getAssetById(assetID) == null) {
            throw new AssetNotFoundException("Asset with ID " + assetID + " not found.");
        }
        return assetDao.getIsAvailable(assetID);
    }
	
	
}
