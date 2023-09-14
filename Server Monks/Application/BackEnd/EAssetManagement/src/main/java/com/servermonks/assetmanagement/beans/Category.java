package com.servermonks.assetmanagement.beans;

public class Category {
	
	private int categoryId;
	private String name;
	private int lendingPeriod;
	private double lateFees;
    private int noOfDaysBanned;
    
    
    public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int i) {
		this.categoryId = i;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLendingPeriod() {
		return lendingPeriod;
	}
	public void setLendingPeriod(int lendingPeriod) {
		this.lendingPeriod = lendingPeriod;
	}
	public double getLateFees() {
		return lateFees;
	}
	public void setLateFees(double lateFees) {
		this.lateFees = lateFees;
	}
	public int getNoOfDaysBanned() {
		return noOfDaysBanned;
	}
	public void setNoOfDaysBanned(int noOfDaysBanned) {
		this.noOfDaysBanned = noOfDaysBanned;
	}
	
	public Category(int categoryId, String name, int lendingPeriod, double lateFees, int noOfDaysBanned) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.lendingPeriod = lendingPeriod;
		this.lateFees = lateFees;
		this.noOfDaysBanned = noOfDaysBanned;
	}
	public Category(int categoryId) {
		super();
		this.categoryId = categoryId;
		this.name = "";
		this.lendingPeriod =0;
		this.lateFees = 0;
		this.noOfDaysBanned = 0;
	}
	
	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", name=" + name + ", lendingPeriod=" + lendingPeriod
				+ ", lateFees=" + lateFees + ", noOfDaysBanned=" + noOfDaysBanned + "]";
	}
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
