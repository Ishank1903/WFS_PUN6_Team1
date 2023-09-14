package com.servermonks.assetmanagement.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.servermonks.assetmanagement.beans.Asset;
import com.servermonks.assetmanagement.dao.AssetDao;
import com.servermonks.assetmanagement.utils.DBUtil;

public class AssetDaoImpl implements AssetDao{

    private Connection connection;

    public AssetDaoImpl() throws SQLException {
        connection = DBUtil.createConnection(); 
    }

    public boolean addAsset(Asset asset) throws SQLException {
        String sql = "INSERT INTO Asset (assetId,name, description, dateAdded, isAvailable, typeId) VALUES (? , ? , ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, asset.getAssetID());
            preparedStatement.setString(2, asset.getName());
            preparedStatement.setString(3, asset.getDescription());
            preparedStatement.setDate(4, new java.sql.Date(asset.getDateAdded().getTime()));
            preparedStatement.setBoolean(5, asset.isAvailable());
            preparedStatement.setInt(6, asset.getCategory().getCategoryId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        	e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        
        }
    }

    public Asset getAssetById(int assetID) throws SQLException {
        String sql = "SELECT * FROM Asset WHERE assetId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, assetID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToAsset(resultSet);
            }
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        
        }
        return null;
    }

    public List<Asset> getAllAssets() throws SQLException {
        List<Asset> assets = new ArrayList<>();
        String sql = "SELECT * FROM Asset";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                assets.add(mapResultSetToAsset(resultSet));
            }
        } 
        catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
        return assets;
    }

    public boolean updateAsset(Asset asset) {
        String sql = "UPDATE Asset SET name = ?, description = ?, dateAdded = ?, isAvailable = ?, typeId = ? WHERE assetId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, asset.getName());
            preparedStatement.setString(2, asset.getDescription());
            preparedStatement.setDate(3, new java.sql.Date(asset.getDateAdded().getTime()));
            preparedStatement.setBoolean(4, asset.isAvailable());
            preparedStatement.setInt(5, asset.getCategory().getCategoryId());
            preparedStatement.setInt(6, asset.getAssetID());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        	//e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
      
        }
    }

    public boolean deleteAsset(int assetID) throws SQLException {
        String sql = "DELETE FROM Asset WHERE assetId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, assetID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
    }

    
    public int getCountTotalAvailableAssets() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Asset WHERE isAvailable = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, true);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        
        }
        return 0; // Error or no available assets found
    }

    public boolean setIsAvailable(int assetID) throws SQLException {
        String sql = "UPDATE Asset SET isAvailable = false WHERE assetId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, assetID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Handle the exception or rethrow it as needed
            throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
    }

    public boolean getIsAvailable(int assetID) throws SQLException {
        String sql = "SELECT isAvailable FROM Asset WHERE assetId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, assetID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("isAvailable");
            }
        } catch (SQLException e) {
            throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
        return false; // Return false if the asset is not found or the query fails
    }

    
    private Asset mapResultSetToAsset(ResultSet resultSet) throws SQLException {
        Asset asset = new Asset();
        asset.setAssetID(resultSet.getInt("assetID"));
        asset.setName(resultSet.getString("name"));
        asset.setDescription(resultSet.getString("description"));
        asset.setDateAdded(resultSet.getDate("dateAdded"));
        asset.setAvailable(resultSet.getBoolean("isAvailable"));
        asset.setCategory(resultSet.getInt("typeId"));
        return asset;
    }

	
}
