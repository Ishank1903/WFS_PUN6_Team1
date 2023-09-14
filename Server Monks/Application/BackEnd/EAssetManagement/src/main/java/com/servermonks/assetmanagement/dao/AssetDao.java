package com.servermonks.assetmanagement.dao;

import java.sql.SQLException;
import java.util.List;

import com.servermonks.assetmanagement.beans.Asset;

public interface AssetDao {

    boolean addAsset(Asset asset) throws SQLException;

    Asset getAssetById(int assetID) throws SQLException;

    List<Asset> getAllAssets() throws SQLException;

    boolean updateAsset(Asset asset);

    boolean deleteAsset(int assetID) throws SQLException;
    
    int getCountTotalAvailableAssets() throws SQLException;
    
    boolean setIsAvailable(int assetID) throws SQLException;
    
    public boolean getIsAvailable(int assetID) throws SQLException;
}

