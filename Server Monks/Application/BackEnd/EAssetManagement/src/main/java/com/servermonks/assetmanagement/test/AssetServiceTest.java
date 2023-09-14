package com.servermonks.assetmanagement.test;

import java.sql.SQLException;
import java.util.List;

import com.servermonks.assetmanagement.beans.Asset;
import com.servermonks.assetmanagement.exceptions.AssetNotFoundException;
import com.servermonks.assetmanagement.exceptions.CategoryNotFoundException;
import com.servermonks.assetmanagement.service.impl.AssetServiceImpl;

public class AssetServiceTest {

    public static void main(String[] args) throws SQLException, CategoryNotFoundException, AssetNotFoundException {
        // Initialize the AssetService
        
    	AssetServiceImpl assetService = new AssetServiceImpl();

        // Test adding an asset
        Asset newAsset = new Asset();
        newAsset.setAssetID(1);
        newAsset.setName("Test Laptop");
        newAsset.setDescription("Testing asset");
        newAsset.setDateAdded(new java.util.Date());
        newAsset.setAvailable(true);
        newAsset.setCategory(1); // Set the category ID (replace with a valid value)
        boolean added = false;
		try {
			added = assetService.addAsset(newAsset);
		} catch (CategoryNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (added) {
            System.out.println("Asset added successfully.");
        } else {
            System.out.println("Failed to add asset.");
        }

        // Test getting an asset by ID
        Asset retrievedAsset = assetService.getAssetById(1); // Replace with a valid asset ID
        if (retrievedAsset != null) {
            System.out.println("Retrieved Asset: " + retrievedAsset.getName());
        } else {
            System.out.println("Asset not found.");
        }

        // Test getting all assets
        List<Asset> allAssets = assetService.getAllAssets();
        if (!allAssets.isEmpty()) {
            System.out.println("All Assets:");
            for (Asset asset : allAssets) {
                System.out.println(asset);
            }
        } else {
            System.out.println("No assets found.");
        }

        if (retrievedAsset != null) {
            retrievedAsset.setName("Updated Laptop");
            boolean updated = assetService.updateAsset(retrievedAsset);
            if (updated) {
                System.out.println("Asset updated successfully.");
            } else {
                System.out.println("Failed to update asset.");
            }
        }
        
        // Test deleting an asset
        boolean deleted = assetService.deleteAsset(1); // Replace with a valid asset ID
        if (deleted) {

            System.out.println("Asset deleted successfully.");
        } else {
            System.out.println("Failed to delete asset.");
        }
    }
}
