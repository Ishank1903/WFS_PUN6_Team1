package com.servermonks.assetmanagement.beans;

import java.util.Date;

public class Asset {
    private int assetID;
    private String name;
    private String description;
    private Date dateAdded;
    private boolean isAvailable;
    private Category category;

    public Asset(int assetID, String name, String description, Date dateAdded, boolean isAvailable, int categoryId) {
		
    	super();
		this.assetID = assetID;
		this.name = name;
		this.description = description;
		this.dateAdded = dateAdded;
		this.isAvailable = isAvailable;
		this.category = new Category(categoryId);
		
	}

	public int getAssetID() {
		return assetID;
	}

	public void setAssetID(int assetID) {
		this.assetID = assetID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setCategory(int  categoryId) {
		if(category == null) {
			this.category = new Category(categoryId);
		}
	}
	
	
	@Override
	public String toString() {
		return "Asset [assetID=" + assetID + ", name=" + name + ", description=" + description + ", dateAdded="
				+ dateAdded + ", isAvailable=" + isAvailable + ", category=" + category + "]";
	}

	// Constructors
    public Asset() {
    }

}

