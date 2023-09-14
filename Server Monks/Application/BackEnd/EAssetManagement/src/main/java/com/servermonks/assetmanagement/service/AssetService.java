package com.servermonks.assetmanagement.service;


import java.sql.SQLException;
import java.util.List;

import com.servermonks.assetmanagement.beans.Asset;
import com.servermonks.assetmanagement.exceptions.AssetNotFoundException;
import com.servermonks.assetmanagement.exceptions.CategoryNotFoundException;

public interface AssetService {

    boolean addAsset(Asset asset) throws CategoryNotFoundException, SQLException;

    Asset getAssetById(int assetID) throws CategoryNotFoundException, SQLException, AssetNotFoundException;

    List<Asset> getAllAssets() throws SQLException, CategoryNotFoundException;

    boolean updateAsset(Asset asset) throws SQLException, CategoryNotFoundException;

    boolean deleteAsset(int assetID) throws SQLException;
    
    int getCoutOfAvailableAssets() throws SQLException;

	List<Asset> getAssetByCategoryId(int typeId) throws SQLException, CategoryNotFoundException;

	boolean setIsAvailable(int assetID) throws SQLException, AssetNotFoundException;

	boolean getIsAvailable(int assetID) throws SQLException, AssetNotFoundException;
	
	
	
}
